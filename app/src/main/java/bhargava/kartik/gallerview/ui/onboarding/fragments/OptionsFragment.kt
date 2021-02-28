package bhargava.kartik.gallerview.ui.onboarding.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import bhargava.kartik.gallerview.databinding.FragmentOptionsBinding

class OptionsFragment : Fragment() {

    private lateinit var binding: FragmentOptionsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOptionsBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }
}