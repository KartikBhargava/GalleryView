package bhargava.kartik.gallerview.ui.gallerviewpackage.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import bhargava.kartik.gallerview.R
import bhargava.kartik.gallerview.databinding.FragmentImageSearchBinding
import bhargava.kartik.gallerview.databinding.FragmentPrevieBinding
import bhargava.kartik.gallerview.ui.gallerviewpackage.viewmodels.ImageSearchViewModel

class PreviewFragment : Fragment(R.layout.fragment_previe) {

    private var _binding: FragmentPrevieBinding? = null
    private val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPrevieBinding.bind(view)
    }
}