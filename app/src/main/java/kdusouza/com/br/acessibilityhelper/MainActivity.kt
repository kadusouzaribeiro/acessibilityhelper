package kdusouza.com.br.acessibilityhelper

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kdusouza.com.br.acessibilityhelper.helper.AlterarConfigHotSpot
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val alterarConfigHotSpot : AlterarConfigHotSpot =
        AlterarConfigHotSpot()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btAtivar.setOnClickListener { ativar() }

        btDesativar.setOnClickListener { desativar() }

        finish()
    }

    fun ativar() {
        alterarConfigHotSpot.startConfiguration("ABC1E23", "11111111")
        finish()
    }

    fun desativar() {
        alterarConfigHotSpot.disableHotSpot()
        finish()
    }
}
