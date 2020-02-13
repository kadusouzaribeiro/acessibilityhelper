package kdusouza.com.br.acessibilityhelper.service

import android.accessibilityservice.AccessibilityService
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import kdusouza.com.br.acessibilityhelper.MainApplication

class MainService : AccessibilityService() {

    val TAG = MainService::class.java.simpleName

    var application : MainApplication =
        MainApplication.applicationContext()

    override fun onCreate() {
        super.onCreate()
        application.ahService = this
    }

    override fun onInterrupt() {
        Log.d(TAG, "Serviço de Acessibilidade Interrompido!")
    }

    override fun onAccessibilityEvent(p0: AccessibilityEvent?) {
        Log.d(TAG, p0.toString())
    }

    fun backButton() {
        performGlobalAction(GLOBAL_ACTION_BACK)
    }

    override fun onServiceConnected() {
        Log.d(TAG, "Serviço de Acessibilidade Conectado!")
        Log.d(TAG, "Instância no Application: ${application.ahService}")
    }
}