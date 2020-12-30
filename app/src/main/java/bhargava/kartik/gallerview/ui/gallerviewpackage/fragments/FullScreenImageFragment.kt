package bhargava.kartik.gallerview.ui.gallerviewpackage.fragments

import android.app.WallpaperManager
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import bhargava.kartik.gallerview.R
import bhargava.kartik.gallerview.databinding.FragmentFullScreenImageBinding
import bhargava.kartik.gallerview.dataclasses.FullScreenImagePreview
import bhargava.kartik.gallerview.extras.Constants.PREVIEW_FRAGMENT
import com.bumptech.glide.Glide
import java.io.IOException

class FullScreenImageFragment : Fragment(R.layout.fragment_full_screen_image) {

    private var _binding: FragmentFullScreenImageBinding? = null
    private val binding get() = _binding!!
    private var fullScreenImagePreview: FullScreenImagePreview? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFullScreenImageBinding.bind(view)
        fullScreenImagePreview = arguments?.getParcelable(PREVIEW_FRAGMENT)
        binding.apply {
            Glide.with(requireContext()).load(fullScreenImagePreview?.showcaseUrl)
                .into(imageView)
        }
        when (fullScreenImagePreview?.type) {
            "Set Wall Paper" -> {
                binding.setWallpaperButton.setOnClickListener {
                    setImage()
                }
            }
            "View Image" -> {
                binding.setWallpaperButton.isInvisible = true
            }
        }
    }

    private fun setImage() {
        val wallpaperManager = WallpaperManager.getInstance(requireActivity())
        val bitmap = (binding.imageView.drawable as BitmapDrawable).bitmap
        try {
            wallpaperManager.setBitmap(bitmap)
            Toast.makeText(requireContext(), "Wallpaper Set", Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}