package kdusouza.com.br.acessibilityhelper.helper

import android.content.Context
import android.os.Build
import android.util.Log

class Util {

    companion object {

        fun verifyAcessibilityEnable(context: Context) {

            val ativadorAcessibilidade = AtivadorAcessibilidade(context)

            if (Build.VERSION.SDK_INT == 28 && Build.MODEL == "SM-T295") {
                try {
                    Log.d("Main Activity", "Application: $this")
                    if (!ativadorAcessibilidade.isEnabledServiceMDM) {
                        ativadorAcessibilidade.openAccessibilitySettings()
                    }
                } catch (e: Exception) {
                    Log.e("Main Activity", "Erro: $e")
                }
            } else {
                Log.d("Util", "Não há serviços de acessibilidade disponíveis para esse aparelho!")
            }
        }
    }
}