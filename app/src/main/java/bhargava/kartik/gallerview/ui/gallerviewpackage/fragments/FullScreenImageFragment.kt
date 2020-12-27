package bhargava.kartik.gallerview.ui.gallerviewpackage.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import bhargava.kartik.gallerview.R
import bhargava.kartik.gallerview.databinding.FragmentFullScreenImageBinding

class FullScreenImageFragment : Fragment(R.layout.fragment_full_screen_image) {

    private var _binding: FragmentFullScreenImageBinding? = null
    private val binding get() = _binding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFullScreenImageBinding.bind(view)
    }
}