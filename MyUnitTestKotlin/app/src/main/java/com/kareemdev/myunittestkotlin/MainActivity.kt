package com.kareemdev.myunittestkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.kareemdev.myunittestkotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        mainViewModel = MainViewModel(CuboidModel())

        activityMainBinding.btnSave.setOnClickListener(this)
        activityMainBinding.btnVolume.setOnClickListener(this)
        activityMainBinding.btnKeliling.setOnClickListener(this)
        activityMainBinding.btnLuas.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        val length = activityMainBinding.edtLength.text.toString().trim()
        val width = activityMainBinding.edtWidth.text.toString().trim()
        val height = activityMainBinding.edtHeight.text.toString().trim()

        when{
            TextUtils.isEmpty(length) -> {
                activityMainBinding.edtLength.error = "Field init tidak boleh kosong"
            }
            TextUtils.isEmpty(width) -> {
                activityMainBinding.edtWidth.error = "Field init tidak boleh kosong"
            }
            TextUtils.isEmpty(height) -> {
                activityMainBinding.edtHeight.error = "Field init tidak boleh kosong"
            }

            else -> {
                val valueLength = length.toDouble()
                val valueWidth = width.toDouble()
                val valueHeight = height.toDouble()

                when(view?.id){
                    R.id.btn_save -> {
                        mainViewModel.save(valueHeight, valueLength, valueWidth)
                        visible()
                    }

                    R.id.btn_keliling -> {
                        activityMainBinding.tvResult.text = mainViewModel.getCircumference().toString()
                        gone()
                    }

                    R.id.btn_volume -> {
                        activityMainBinding.tvResult.text =  mainViewModel.getVolume().toString()
                        gone()
                    }

                    R.id.btn_luas -> {
                        activityMainBinding.tvResult.text =  mainViewModel.getSurfaceArea().toString()
                        gone()
                    }
                }
            }
        }


    }

    private fun gone() {
        activityMainBinding.btnSave.visibility = View.VISIBLE
        activityMainBinding.btnLuas.visibility = View.VISIBLE
        activityMainBinding.btnVolume.visibility = View.VISIBLE
        activityMainBinding.btnKeliling.visibility = View.VISIBLE
    }

    private fun visible() {
        activityMainBinding.btnVolume.visibility = View.VISIBLE
        activityMainBinding.btnLuas.visibility = View.VISIBLE
        activityMainBinding.btnLuas.visibility = View.VISIBLE
        activityMainBinding.btnSave.visibility = View.GONE
    }

}