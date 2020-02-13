package kdusouza.com.br.acessibilityhelper.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import kdusouza.com.br.acessibilityhelper.helper.Util

class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (context != null) {
            Util.verifyAcessibilityEnable(context)
        }
    }
}