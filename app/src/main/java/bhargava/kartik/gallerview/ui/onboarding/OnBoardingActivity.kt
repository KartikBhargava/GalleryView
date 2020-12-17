package bhargava.kartik.gallerview.ui.onboarding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import bhargava.kartik.gallerview.R
import bhargava.kartik.gallerview.databinding.ActivityOnBoardingBinding
import bhargava.kartik.gallerview.databinding.FragmentOptionsBinding

class OnBoardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnBoardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        setContentView(binding.root)

    }
}