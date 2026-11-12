package com.bravohelper

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val phoneInput = findViewById<EditText>(R.id.phoneInput)
        val referralInput = findViewById<EditText>(R.id.referralInput)
        val startBtn = findViewById<Button>(R.id.startBtn)
        val accBtn = findViewById<Button>(R.id.accBtn)

        accBtn.setOnClickListener {
            startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
            Toast.makeText(this, "فعّل خدمة Bravo Helper", Toast.LENGTH_LONG).show()
        }

        startBtn.setOnClickListener {
            val phone = phoneInput.text.toString().trim()
            val ref = referralInput.text.toString().trim()
            if (phone.length < 9) {
                Toast.makeText(this, "أدخل رقم صحيح", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            State.phoneNumber = phone
            State.referralCode = ref
            State.step = State.STEP_START
            State.otp = ""
            
            val intent = packageManager.getLaunchIntentForPackage("istinara.bravocash")
            if (intent != null) {
                startActivity(intent)
            } else {
                Toast.makeText(this, "تطبيق Bravo غير مثبت", Toast.LENGTH_LONG).show()
            }
        }
    }
}
