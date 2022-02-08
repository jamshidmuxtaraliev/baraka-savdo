package uz.bdmgroup.barakasavdo.screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import uz.bdmgroup.barakasavdo.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        supportActionBar?.hide()

        Handler().postDelayed({
            finish()
            startActivity(Intent(this, MainActivity::class.java))
        }, 4000)
    }
}