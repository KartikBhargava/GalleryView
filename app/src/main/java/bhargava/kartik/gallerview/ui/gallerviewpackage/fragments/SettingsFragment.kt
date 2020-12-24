package bhargava.kartik.gallerview.ui.gallerviewpackage.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import bhargava.kartik.gallerview.BuildConfig
import bhargava.kartik.gallerview.R
import bhargava.kartik.gallerview.databinding.FragmentPrevieBinding
import bhargava.kartik.gallerview.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSettingsBinding.bind(view)
        binding.tvVersionNumber.text = "Version ${BuildConfig.VERSION_NAME}"
    }
}