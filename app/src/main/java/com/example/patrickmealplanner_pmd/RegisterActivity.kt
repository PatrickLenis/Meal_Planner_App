package com.example.patrickmealplanner_pmd

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val intent = Intent(this, MainActivity::class.java)
        val log_switch = Intent(this, LoginActivity::class.java)

        val register = findViewById<Button>(R.id.register_button)
        val text = findViewById<TextView>(R.id.account)

        register.setOnClickListener { startActivity(intent) }
        text.setOnClickListener { startActivity(log_switch) }
    }
}