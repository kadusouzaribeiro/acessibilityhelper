package kdusouza.com.br.acessibilityhelper.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import kdusouza.com.br.acessibilityhelper.helper.AlterarConfigHotSpot

class EDHotSpotReceiver : BroadcastReceiver() {

    val TAG : String = EDHotSpotReceiver::class.java.simpleName

    val ENABLE_HOTSPOT : String = "kdusouza.com.br.acessibilityhelper.SET_HOTSPOT_CONFIG.enable"
    val DISABLE_HOTSPOT : String = "kdusouza.com.br.acessibilityhelper.SET_HOTSPOT_CONFIG.disable"

    val alterarConfigHotSpot : AlterarConfigHotSpot =
        AlterarConfigHotSpot()

    override fun onReceive(context: Context?, intent: Intent?) {
        val action = intent!!.action
        Log.d(TAG, "Action: $action")

        action.let {
            if (it.equals(ENABLE_HOTSPOT)) {
                var placa : String = intent!!.getStringExtra("placa")
                var senha : String = intent.getStringExtra("senha")
                Log.d(TAG, "Placa: $placa  - Senha: $senha")
                alterarConfigHotSpot.startConfiguration(placa, senha)
            } else {
                alterarConfigHotSpot.disableHotSpot()
            }
        }
    }

}