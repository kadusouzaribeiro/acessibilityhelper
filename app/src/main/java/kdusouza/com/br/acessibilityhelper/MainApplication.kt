package kdusouza.com.br.acessibilityhelper

import android.app.Application
import kdusouza.com.br.acessibilityhelper.helper.Util
import kdusouza.com.br.acessibilityhelper.service.MainService

class MainApplication : Application() {

    lateinit var ahService: MainService

    init {
        instance = this
    }

    companion object {
        private var instance : MainApplication? = null

        fun applicationContext() : MainApplication {
            return instance as MainApplication
        }
    }

    override fun onCreate() {
        super.onCreate()
        Util.verifyAcessibilityEnable(this)
    }
}