package bhargava.kartik.gallerview.ui.onboarding.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import bhargava.kartik.gallerview.R
import bhargava.kartik.gallerview.databinding.ActivityMainBinding
import bhargava.kartik.gallerview.databinding.FragmentOptionsBinding
import bhargava.kartik.gallerview.ui.onboarding.adapters.ImageScrollAdapter

class OptionsFragment : Fragment() {

    private lateinit var binding: FragmentOptionsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOptionsBinding.inflate(inflater)
        binding.lifecycleOwner = this
        val images = listOf(
            R.drawable.first,
            R.drawable.second,
            R.drawable.third,
            R.drawable.fourthimage,
            R.drawable.fifth,
            R.drawable.sixth,
            R.drawable.seventh
        )
        val adapter = ImageScrollAdapter(images)
        binding.optionsFragmentViewPager.adapter = adapter
        binding.optionsFragmentViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
            }
        })
        binding.optionsFragmentViewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.wormDotsIndicator.setViewPager2(binding.optionsFragmentViewPager)
        return binding.root
    }
}