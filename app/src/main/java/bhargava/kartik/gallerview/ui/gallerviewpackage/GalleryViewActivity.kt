package bhargava.kartik.gallerview.ui.gallerviewpackage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import bhargava.kartik.gallerview.R
import bhargava.kartik.gallerview.api.Status
import bhargava.kartik.gallerview.databinding.ActivityGalleryViewBinding
import bhargava.kartik.gallerview.ui.gallerviewpackage.viewmodels.GalleryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GalleryViewActivity : AppCompatActivity() {
    private val viewModel: GalleryViewModel by viewModels()
    private lateinit var binding: ActivityGalleryViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_gallery_view
        )
        binding.lifecycleOwner = this
    }
}