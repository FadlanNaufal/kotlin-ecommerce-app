package com.example.usmkpstore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_confirm.*

class ConfirmActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm)

        btn_back_home.setOnClickListener {
            var i = Intent(this,HomeActivity::class.java);
            startActivity(i);
        }
    }
}
