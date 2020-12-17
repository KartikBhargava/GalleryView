package bhargava.kartik.gallerview

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import bhargava.kartik.gallerview.databinding.ActivityMainBinding
import bhargava.kartik.gallerview.extras.FirebaseUtil.auth
import bhargava.kartik.gallerview.ui.gallerviewpackage.GalleryViewActivity
import bhargava.kartik.gallerview.ui.onboarding.OnBoardingActivity

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.galleryAnimation.setAnimation(R.raw.gallery_lottie)
        binding.galleryAnimation.playAnimation()
        binding.galleryAnimation.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {
                if (auth.currentUser != null) {
                    startActivity(Intent(this@SplashActivity, GalleryViewActivity::class.java))
                    finish()
                } else {
                    startActivity(Intent(this@SplashActivity, OnBoardingActivity::class.java))
                    finish()
                }
            }

            override fun onAnimationCancel(animation: Animator?) {

            }

            override fun onAnimationRepeat(animation: Animator?) {

            }

        })
        setContentView(binding.root)
    }
}