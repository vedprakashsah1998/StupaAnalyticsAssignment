package com.infinity8.cameraassignment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.infinity8.cameraassignment.databinding.ActivitySplashActivtyBinding

class SplashActivty : AppCompatActivity() {
    var binding: ActivitySplashActivtyBinding? = null
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySplashActivtyBinding.inflate(layoutInflater)
        val view: View = binding!!.root
        setContentView(view)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        mAuth = FirebaseAuth.getInstance()
        val currentuser = mAuth!!.currentUser

        Handler().postDelayed({
            if (currentuser != null) {
                startActivity(Intent(this@SplashActivty, HomeActivity::class.java))
            } else {
                startActivity(Intent(this@SplashActivty, MainActivity::class.java))
                Toast.makeText(this@SplashActivty, "Failed", Toast.LENGTH_SHORT).show()

            }
            finish()
        }, 3000)


    }
}