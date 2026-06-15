package com.example.handyhubke

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.handyhubke.data.local.PreferencesManager
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splashscreen)

        val preferencesManager = PreferencesManager(this)

        Handler(Looper.getMainLooper()).postDelayed({
            // Force logout and clear preferences to ensure login every time the app opens
            FirebaseAuth.getInstance().signOut()
            preferencesManager.clearAll()
            
            // Always redirect to LoginActivity
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }, 3000)
    }
}