package android.portfolio.emergencyapp

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.portfolio.emergencyapp.databinding.ActivityEditBinding
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible

class EditActivity : AppCompatActivity() {
    private lateinit var binding : ActivityEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bloodTypeSpinner.adapter = ArrayAdapter.createFromResource(
            this,R.array.blood_types,
            android.R.layout.simple_list_item_1
        )

        binding.birthDatelayer.setOnClickListener{
            val listener = OnDateSetListener{
                    _, year, month, dayOfMonth ->
                binding.birthTextView.text="$year-${month.inc()}-$dayOfMonth"
            }
            DatePickerDialog(
                this,
                listener,
                1994,
                4,
                7
            ).show()

        }

        binding.checkboxCaution.setOnCheckedChangeListener{
            buttonView, isChecked ->
            binding.cautionEdit.isVisible = isChecked
        }

        binding.cautionEdit.isVisible = binding.checkboxCaution.isChecked

        binding.saveFloatingButton.setOnClickListener {
            saveData()
            finish()
        }
    }

    private fun saveData(){
        with(getSharedPreferences(USER_INFORMATION , Context.MODE_PRIVATE).edit()){
            putString(NAME ,binding.nameEdit.text.toString())
            putString(BIRTHDATE,binding.birthTextView.text.toString())
            putString(BLOOD_TYPE, getBlood())
            putString(EMERGENCY_CONTACT ,binding.phoneNumber.text.toString())
            putString(WARNING,getWarning())
            apply()
        }
        Toast.makeText(this,"저장을 완료했습니다",Toast.LENGTH_SHORT).show()
    }

    private fun getBlood() :  String{
        val bloodTypeAlphabet = binding.bloodTypeSpinner.selectedItem.toString()
        val bloodSign = if(binding.bloodTypeMinus.isChecked) "+" else "-"
        return "$bloodSign$bloodTypeAlphabet"
    }

    private fun getWarning() : String{
        return if(binding.checkboxCaution.isChecked) binding.cautionEdit.toString() else ""
    }
}