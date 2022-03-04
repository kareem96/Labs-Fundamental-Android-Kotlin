package com.kareemdev.sharedprefkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.kareemdev.sharedprefkotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mUserPreference: UserPreference
    private var isPreferenceEmpty = false
    private lateinit var userModel: UserModel
    private lateinit var binding: ActivityMainBinding


    private val resultLauncher = registerForActivityResult(
        StartActivityForResult()
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "My User Preference"
        mUserPreference = UserPreference(this)
        showExistingPreference()
        binding.btnSave.setOnClickListener(this)
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
            }else -> {
            binding.btnSave.text = getString(R.string.save)
            isPreferenceEmpty = true
            }
        }

    }

    private fun populateView(userModel: UserModel) {
        binding.tvName.text = if (userModel.name.toString().isEmpty()) "Tidak ada" else userModel.name
        binding.tvEmail.text = if (userModel.email.toString().isEmpty()) "Tidak ada" else userModel.email
        binding.tvAge.text = if (userModel.age.toString().isEmpty()) "Tidak ada" else userModel.age.toString()
        binding.tvPhone.text = if (userModel.phoneNumber.toString().isEmpty()) "Tidak ada" else userModel.phoneNumber
        binding.tvFav.text = if (userModel.isLove) "Ya" else "Tidak"

    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }
}