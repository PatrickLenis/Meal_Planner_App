package com.example.patrickmealplanner_pmd

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val intent = Intent(this, MainActivity::class.java)
        val log_switch = Intent(this, RegisterActivity::class.java)

        val login = findViewById<Button>(R.id.login_button)
        val text = findViewById<TextView>(R.id.register_account)

        login.setOnClickListener { startActivity(intent) }
        text.setOnClickListener { startActivity(log_switch) }
    }
}