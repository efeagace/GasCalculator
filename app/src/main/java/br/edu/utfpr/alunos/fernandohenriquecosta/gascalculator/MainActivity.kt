package br.edu.utfpr.alunos.fernandohenriquecosta.gascalculator

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var tvComment: TextView
    private lateinit var tvResult: TextView
    private lateinit var etFirstGas: EditText
    private lateinit var etFirstGasValue: EditText
    private lateinit var etSecondGas: EditText
    private lateinit var etSecondGasValue: EditText

    private lateinit var btSearchFirstGas: Button
    private lateinit var btSearchSecondGas: Button
    private lateinit var btCalculate: Button
    val gasConsumptionMap = mapOf(
        "Gasoline" to "12",
        "Ethanol" to "9"
    )
    private lateinit var firstGas: String
    private lateinit var secondGas: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btSearchFirstGas = findViewById(R.id.btSearchFirstGas)
        btSearchFirstGas.setOnClickListener { view -> btSearchGas(view) }

        btSearchSecondGas = findViewById(R.id.btSearchSecondGas)
        btSearchSecondGas.setOnClickListener { view -> btSearchGas(view) }

        tvResult = findViewById(R.id.tvResult)
        tvComment = findViewById(R.id.tvComment)

        btCalculate = findViewById(R.id.btCalculate)
        btCalculate.setOnClickListener(View.OnClickListener {
            if (areValuesValid()){
                calculateConsumption()
            }else{
                Toast.makeText(this, "Check Fields!", Toast.LENGTH_SHORT).show()
            }
        })

        etFirstGas = findViewById(R.id.etFirstGas)
        etFirstGasValue = findViewById(R.id.etFirstGasValue)
        etSecondGas = findViewById(R.id.etSecondGas)
        etSecondGasValue = findViewById(R.id.etSecondGasValue)


    }

    private fun btSearchGas(view: View) {
        val intent = Intent(this, GasListActivity::class.java)
        intent.putExtra("button_id", view.id)
        getListResult.launch(intent)
    }

    private fun setConsumption(gas: String, id: Int) {
        val consumption =
            Editable.Factory.getInstance().newEditable(gasConsumptionMap.getValue(gas))
        when (id) {
            R.id.btSearchFirstGas -> {
                firstGas = gas
                etFirstGas.text = consumption
                etFirstGas.error = null
                etFirstGas.clearFocus()

            }

            R.id.btSearchSecondGas -> {
                secondGas = gas
                etSecondGas.text = consumption
                etSecondGas.error = null
                etSecondGas.clearFocus()
            }
        }
    }

    private val getListResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                if (it.data != null) {
                    val gas = it.data!!.getStringExtra("gas_name")
                    val buttonId = it.data!!.getIntExtra("button_id", 0)
                    if (gas != null) {
                        setConsumption(gas, buttonId)
                    }
                }
            }
        }

    private fun calculateConsumption() {
        var gasName =  ""
        var gasList: Set<String>
        var gasValue: Double

        val firstValueConsumption =
            etFirstGasValue.text.toString().toDouble() / etFirstGas.text.toString().toDouble()
        val secondValueConsumption =
            etSecondGasValue.text.toString().toDouble() / etSecondGas.text.toString().toDouble()

        gasValue = firstValueConsumption
        if (firstValueConsumption < secondValueConsumption) {
            gasName = firstGas
        } else if ((firstValueConsumption == secondValueConsumption)) {
            gasName = "Both"
        }else{
            gasValue = secondValueConsumption
            gasName = secondGas
        }

        val message = "Best Consumption Fuel: $gasName"
        tvResult.text = message

        val comment = "Value per Kilometer: $" + String.format("%.2f",gasValue)
        tvComment.text = comment

    }

    private fun areValuesValid(): Boolean {
        tvResult.text = ""
        tvComment.text = ""
        var count = 0
        var errorMesssage = "Field must not be empty!"
        if (etFirstGas.text.isNullOrEmpty()) {
            etFirstGas.error = errorMesssage
            count += 1
        }
        if (etSecondGas.text.isNullOrEmpty()) {
            etSecondGas.error = errorMesssage
            count += 1
        }
        if (etFirstGasValue.text.isNullOrEmpty()) {
            etFirstGasValue.error = errorMesssage
            count += 1
        }
        if (etSecondGasValue.text.isNullOrEmpty()) {
            etSecondGasValue.error = errorMesssage
            count += 1
        }
        if (count > 0){
            return false
        }
        return true
    }
}