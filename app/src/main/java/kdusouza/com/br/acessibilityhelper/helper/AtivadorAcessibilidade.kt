package kdusouza.com.br.acessibilityhelper.helper

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.core.content.ContextCompat
import kdusouza.com.br.acessibilityhelper.service.MainService

class AtivadorAcessibilidade(private val context: Context) {

    private var componentName: ComponentName? = null

    private val listServiceAccessibilityServices: String?
        get() =
            Settings.Secure.getString(
                context.contentResolver,
                Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
            )

    val isEnabledServiceMDM: Boolean
        get() = checkServiceEnabled(componentName!!.flattenToString())

    init {
        init()
    }

    private fun init() {
        componentName = createComponentName()
    }

    private fun createComponentName(): ComponentName {
        return ComponentName(context, MainService::class.java!!)
    }

    private fun checkServiceEnabled(flatName: String): Boolean {
        val enabledList = listServiceAccessibilityServices
        return enabledList != null && enabledList.contains(flatName)
    }

    fun openAccessibilitySettings() {
        val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        ContextCompat.startActivity(context, intent, null)
    }
}
