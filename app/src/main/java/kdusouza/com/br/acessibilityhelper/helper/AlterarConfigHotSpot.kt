package kdusouza.com.br.acessibilityhelper.helper

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.accessibility.AccessibilityNodeInfo
import androidx.core.content.ContextCompat.startActivity
import kdusouza.com.br.acessibilityhelper.MainApplication
import kdusouza.com.br.acessibilityhelper.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.system.measureTimeMillis


class AlterarConfigHotSpot {

    private val TAG : String = AlterarConfigHotSpot::class.java.simpleName

    private val application : MainApplication =
        MainApplication.applicationContext()

    @Throws(Exception::class)
    private fun getAcessibilidade(): AccessibilityNodeInfo? {
        Log.d(TAG, "Buscando contexto de acessibilidade")
        return application.ahService.rootInActiveWindow
    }

    private fun openWIFISettings() {
        Log.d(TAG, "Abrindo tela de configuração das conexões")
        val intent = Intent(Settings.ACTION_WIRELESS_SETTINGS).apply { addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) }
        startActivity(application.applicationContext, intent, null)
    }

    @Throws(Exception::class)
    private fun verifyHotSpotSettingsOpenandActive() : Boolean {
        return getAcessibilidade()!!.findAccessibilityNodeInfosByText("Roteador Wi-Fi, Ativado, Alternar").size > 0
    }

    @Throws(Exception::class)
    private fun openWiFiRouterSettings() {
        Log.d(TAG, "Abrindo tela Roteador WiFi e Ancoragem")
        getAcessibilidade()!!.findAccessibilityNodeInfosByText(application.getString(
            R.string.wifihotspot_settings
        ))[0].parent.parent.parent?.also { nodes ->
            if (nodes.isClickable) {
                nodes.performAction(AccessibilityNodeInfo.ACTION_CLICK)
            } else if (nodes.parent != null && nodes.parent.isClickable) {
                nodes.parent.performAction(AccessibilityNodeInfo.ACTION_CLICK)
            }
        }
    }

    @Throws(Exception::class)
    private fun openHotSpotSettings() {
        Log.d(TAG, "Abrindo tela de configuração do HotSpot")
        val nodes =
            getAcessibilidade()!!.findAccessibilityNodeInfosByText(application.getString(
                R.string.hotspot_settings
            ))[2].parent.parent
        if (nodes.isClickable) {
            nodes.performAction(AccessibilityNodeInfo.ACTION_CLICK)
        } else if (nodes.parent != null && nodes.parent.isClickable) {
            nodes.parent.performAction(AccessibilityNodeInfo.ACTION_CLICK)
        }
    }

    @Throws(java.lang.Exception::class)
    private suspend fun changeNameHotSpot(ssid: String) {
        Log.d(TAG, "Modificando nome do HotSpot")
        val nodes =
            getAcessibilidade()!!.findAccessibilityNodeInfosByViewId(application.getString(
                R.string.textview_nome_password_hotspot
            ))[0].parent.parent
        if (nodes.isClickable) {
            nodes.performAction(AccessibilityNodeInfo.ACTION_CLICK)
        } else if (nodes.parent != null && nodes.parent.isClickable) {
            nodes.parent.performAction(AccessibilityNodeInfo.ACTION_CLICK)
        }

        delay(1000)

        val nodeSSID =
            getAcessibilidade()!!.findAccessibilityNodeInfosByViewId(application.getString(
                R.string.edittext_nome_hotspot
            ))[0]
        val arguments = Bundle()
        arguments.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, ssid)
        nodeSSID.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments)
        val nodeButton =
            getAcessibilidade()!!.findAccessibilityNodeInfosByViewId(application.getString(
                R.string.button_nome_password_salvar_hotspot
            ))[0]
        nodeButton.performAction(AccessibilityNodeInfo.ACTION_CLICK)
    }

    @Throws(java.lang.Exception::class)
    private suspend fun changePasswordHotSpot(password: String) {
        Log.d(TAG, "Modificando senha do HotSpot contexto de acessibilidade")

        val nodes =
            getAcessibilidade()!!.findAccessibilityNodeInfosByViewId(application.getString(
                R.string.textview_nome_password_hotspot
            ))[1].parent.parent
        if (nodes.isClickable) {
            nodes.performAction(AccessibilityNodeInfo.ACTION_CLICK)
        } else if (nodes.parent != null && nodes.parent.isClickable) {
            nodes.parent.performAction(AccessibilityNodeInfo.ACTION_CLICK)
        }

        delay(1000)

        val nodeSSID =
            getAcessibilidade()!!.findAccessibilityNodeInfosByViewId(application.getString(
                R.string.edittext_password_hotspot
            ))[0]
        val arguments = Bundle()
        arguments.putCharSequence(
            AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE,
            password
        )
        nodeSSID.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments)

        delay(1000)

        val nodeButton =
            getAcessibilidade()!!.findAccessibilityNodeInfosByViewId(application.getString(
                R.string.button_nome_password_salvar_hotspot
            ))[0]
        nodeButton.performAction(AccessibilityNodeInfo.ACTION_CLICK)
    }

    @Throws(java.lang.Exception::class)
    private fun rolarTela() {
        getAcessibilidade()!!.findAccessibilityNodeInfosByViewId(application.getString(
            R.string.reciclerview_hotspot
        ))[0].performAction(
            AccessibilityNodeInfo.ACTION_SCROLL_FORWARD
        )
    }

    @Throws(java.lang.Exception::class)
    private suspend fun enableDisableHotSpot() {
        getAcessibilidade()!!.findAccessibilityNodeInfosByViewId(application.getString(
            R.string.switch_enable_hotspot
        ))[0].performAction(
            AccessibilityNodeInfo.ACTION_CLICK
        )
        delay(3000)
    }

    @Throws(java.lang.Exception::class)
    private suspend fun clickButtonSave() {
        getAcessibilidade()!!.findAccessibilityNodeInfosByViewId(application.getString(
            R.string.button_nome_password_salvar_hotspot
        )).let {
            if (it.size > 0) {
                it[0].performAction(AccessibilityNodeInfo.ACTION_CLICK)
                delay(1000)
            }
        }
    }

    fun startConfiguration(ssid: String, password: String) {
        CoroutineScope(IO).launch {
            val executionTime = measureTimeMillis {

                openWIFISettings()
                delay(3000)

                try {
                    if (getAcessibilidade() == null) {
                        Log.d(TAG, "Serviço de acessibilidade não ativo!")
                    }
                } catch (e: Exception) {
                    Log.d(TAG, "Erro ao configurar HotSpot usando Acessibilidade")
                    return@launch
                }

                try {
                    if (!verifyHotSpotSettingsOpenandActive()) {
                        openWiFiRouterSettings()
                        delay(1000)

                        openHotSpotSettings()
                        delay(1000)

                        changeNameHotSpot(ssid)
                        delay(1000)

                        rolarTela()
                        delay(1000)

                        changePasswordHotSpot(password)
                        delay(1000)

                        enableDisableHotSpot()

                        clickButtonSave()

                    } else {
                        enableDisableHotSpot()
                        delay(1000)

                        changeNameHotSpot(ssid)
                        delay(1000)

                        rolarTela()
                        delay(1000)

                        changePasswordHotSpot(password)
                        delay(1000)

                        enableDisableHotSpot()

                        clickButtonSave()
                    }
                } catch (e: Exception) {
                    Log.d(TAG, "Erro ao verificar as configurações do HotSpot")
                    return@launch
                }

                application.ahService.backButton()
                delay(1000)
                application.ahService.backButton()
                delay(1000)
                application.ahService.backButton()
                delay(1000)

            }
            Log.d(TAG, "Finalizada configuração HotSpot -> $executionTime ms")
        }
    }

    fun disableHotSpot() {
        CoroutineScope(IO).launch {
            val executionTime = measureTimeMillis {
                openWIFISettings()
                delay(3000)

                try {
                    if (getAcessibilidade() == null) {
                        Log.d(TAG, "Serviço de acessibilidade não ativo!")
                    }
                } catch (e: Exception) {
                    Log.d(TAG, "Erro ao configurar HotSpot usando Acessibilidade")
                    return@launch
                }

                openWiFiRouterSettings()
                delay(1000)

                getAcessibilidade()!!.findAccessibilityNodeInfosByViewId("android:id/switch_widget")[0].performAction(AccessibilityNodeInfo.ACTION_CLICK)
                delay(1000)

                clickButtonSave()

                application.ahService.backButton()
                delay(1000)
                application.ahService.backButton()
                delay(1000)
            }
            Log.d(TAG, "Finalizada configuração HotSpot -> $executionTime ms")
        }
    }
}
