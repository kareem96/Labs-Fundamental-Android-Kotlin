package com.kareemdev.mysharedpreference

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.kareemdev.mysharedpreference.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mUserPreference: UserPreference
    private var isPreferenceEmpty = false
    private lateinit var userModel: UserModel

    private lateinit var binding: ActivityMainBinding

    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result: ActivityResult ->
        if(result.data != null && result.resultCode == FormUserPreferenceActivity.RESULT_CODE){
            userModel = result.data?.getParcelableExtra<UserModel>(FormUserPreferenceActivity.EXTRA_RESULT) as UserModel
            populateView(userModel)
            checkForm(userModel)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSave.setOnClickListener(this)

        supportActionBar?.title = "My User Preference"
        mUserPreference = UserPreference(this)
        showExistingPreference()
    }

    private fun showExistingPreference() {
        userModel = mUserPreference.getUser()
        populateView(userModel)
        checkForm(userModel)
    }

    private fun checkForm(userModel: UserModel) {
        when{
            userModel.name.toString().isNotEmpty() -> {
                binding.btnSave.text = getString(R.string.change)
                isPreferenceEmpty = false
            } else -> {
                binding.btnSave.text = getString(R.string.save)
            isPreferenceEmpty = true
            }
        }
    }

    private fun populateView(userModel: UserModel) {
        binding.tvName.text = if(userModel.name.toString().isEmpty()) "Tidak ada" else userModel.name
        binding.tvAge.text = if(userModel.age.toString().isEmpty()) "Tidak ada" else userModel.age.toString()
        binding.tvIsLoveMu.text = if(userModel.isLove) "Ya" else "Tidak"
        binding.tvEmail.text = if(userModel.email.toString().isEmpty()) "Tidak ada" else userModel.email
        binding.tvPhone.text = if(userModel.phoneNumber.toString().isEmpty()) "Tidak ada" else userModel.phoneNumber
    }

    override fun onClick(view: View?) {
        if(view?.id == R.id.btn_save){
            val intent = Intent(this@MainActivity, FormUserPreferenceActivity::class.java)
            when{
                isPreferenceEmpty -> {
                    intent.putExtra(
                        FormUserPreferenceActivity.EXTRA_TYPE_FORM,
                        FormUserPreferenceActivity.TYPE_ADD,
                    )
                    intent.putExtra("USER", userModel)
                }else -> {
                    intent.putExtra(
                        FormUserPreferenceActivity.EXTRA_TYPE_FORM,
                        FormUserPreferenceActivity.TYPE_EDIT
                    )
                intent.putExtra("USER", userModel)
                }
            }
            resultLauncher.launch(intent)
        }
    }
}