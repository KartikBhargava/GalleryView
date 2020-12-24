package bhargava.kartik.gallerview.ui.gallerviewpackage

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import bhargava.kartik.gallerview.R
import bhargava.kartik.gallerview.databinding.ActivityGalleryViewBinding
import bhargava.kartik.gallerview.ui.gallerviewpackage.viewmodels.GalleryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GalleryViewActivity : AppCompatActivity() {
    private val viewModel: GalleryViewModel by viewModels()
    private lateinit var navController: NavController
    private lateinit var binding: ActivityGalleryViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_gallery_view
        )
        val navController = findNavController(R.id.galleryViewActivityNavHostFragment)
        binding.bottomNavBar.setupWithNavController(navController)
        binding.lifecycleOwner = this
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}