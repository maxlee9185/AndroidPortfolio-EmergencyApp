package android.portfolio.emergencyapp

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.TokenWatcher
import android.portfolio.emergencyapp.databinding.ActivityMainBinding
import android.widget.Toast
import androidx.core.view.isVisible

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.editFloatingButton.setOnClickListener {
            val intent = Intent(this, EditActivity::class.java )
            startActivity(intent)
        }
        binding.deleteFloatingButton.setOnClickListener {
            deleteData()
        }

        binding.emergencyContactLayer.setOnClickListener{
            with(Intent(Intent.ACTION_VIEW)){
                val phoneNumber=binding.phoneNumber.text.toString().replace("-","")
                data= Uri.parse("tel:$phoneNumber")
                startActivity(this)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        getDataAndUiUpdate()
    }

    private fun getDataAndUiUpdate() {
        with(getSharedPreferences(USER_INFORMATION, Context.MODE_PRIVATE)){
            binding.name.text = getString(NAME,"미정")
            binding.birth.text = getString(BIRTHDATE, "미정")
            binding.bloodType.text = getString(BLOOD_TYPE,"미정")
            binding.phoneNumber.text=getString(EMERGENCY_CONTACT,"미정")
            val warning=getString(WARNING,"미정")
            if(!warning.isNullOrEmpty()){
                binding.caution.isVisible = false
                binding.CautionTextView.isVisible= false
            }
            else{
                binding.caution.isVisible = true
                binding.CautionTextView.isVisible= true
                binding.caution.text= warning
            }
        }
    }

    private fun deleteData(){
        with(getSharedPreferences(USER_INFORMATION,Context.MODE_PRIVATE).edit()){
            clear()
            apply()
            getDataAndUiUpdate()
        }
        Toast.makeText(this,"초기화를 완료했습니다",Toast.LENGTH_SHORT).show()
    }
}