package br.edu.utfpr.alunos.fernandohenriquecosta.gascalculator

import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class GasListActivity : AppCompatActivity() {
    private lateinit var lvGasTypes: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gas_list)

        lvGasTypes = findViewById(R.id.lvGas)
        val buttonId = intent.getIntExtra("button_id", 0).toInt()

        lvGasTypes.setOnItemClickListener { parent, view, position, id ->
            val selectedGas = lvGasTypes.getItemAtPosition(position).toString()
            intent.putExtra("gas_name", selectedGas)
            intent.putExtra("button_id", buttonId)
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}

