package uz.bdmgroup.barakasavdo.screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_about.*
import uz.bdmgroup.barakasavdo.R

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        backAbout.setOnClickListener {
            finish()
        }
    }
}