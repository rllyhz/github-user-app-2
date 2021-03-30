package id.rllyhz.githubuserapp.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import id.rllyhz.githubuserapp.R

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var runnable: Runnable
    private lateinit var handler: Handler
    private lateinit var ivSplashScreen: ImageView

    companion object {
        const val TIME_IN_MILLIS = 3000L
    }

    @SuppressLint("UseCompatLoadingForDrawables", "CheckResult", "ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        window.statusBarColor =
            ContextCompat.getColor(applicationContext, R.color.background_color_dark)
        supportActionBar?.hide()

        // this spesific view dosn't use view binding, because only one component in it
        ivSplashScreen = findViewById(R.id.iv_splash_screen)

        Glide.with(this)
            .load(getDrawable(R.drawable.image_app_logo))
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(ivSplashScreen)

        runnable = Runnable {
            startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
            finish()

            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }

        handler = Handler(mainLooper).apply {
            postDelayed(runnable, TIME_IN_MILLIS)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        handler.removeCallbacks(runnable) // avoiding memory leaks
    }
}