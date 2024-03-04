package com.niolan.text

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.niolan.MainActivity
import com.niolan.R
import com.niolan.databinding.ActivityAppBinding
import com.xxxd86.niobase.base.baseActivity.BaseActivityLate
import com.xxxd86.niobase.base.baseActivity.BaseVMActivity

class AppActivity : BaseVMActivity<AppViewModel,ActivityAppBinding>(ActivityAppBinding::inflate) {
    override fun initView() {
        binding.text.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivityForResult(intent,101)
        }

    }

    override fun onNightChange(onSuccess: () -> Unit): Boolean {
        TODO("Not yet implemented")
    }

    override fun onLanguageChange(onSuccess: () -> Unit): Boolean {
        TODO("Not yet implemented")
    }

    override fun onScreanChange(onSuccess: () -> Unit) {
        TODO("Not yet implemented")
    }
}