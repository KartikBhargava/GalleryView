package bhargava.kartik.gallerview.ui.gallerviewpackage

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import bhargava.kartik.gallerview.R
import bhargava.kartik.gallerview.databinding.ActivityGalleryViewBinding
import bhargava.kartik.gallerview.ui.gallerviewpackage.viewmodels.GalleryViewModel
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class GalleryViewActivity : AppCompatActivity() {
    private val viewModel: GalleryViewModel by viewModels()
    private lateinit var navController: NavController
    private lateinit var binding: ActivityGalleryViewBinding
    private val mAppUnitId: String by lazy {

        "ca-app-pub-8998944149281536~8274132140"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_gallery_view
        )
        binding.lifecycleOwner = this
        val navController = findNavController(R.id.galleryViewActivityNavHostFragment)
        binding.bottomNavBar.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.previewFragment,
                R.id.fullScreenImageFragment -> {
                    binding.bottomNavBar.isVisible = false
                }
                else -> {
                    binding.bottomNavBar.isVisible = true
                }
            }
        }
        val requestConfiguration = RequestConfiguration.Builder()
            .setTestDeviceIds(Arrays.asList("BEF8F16498D1D71534F1170DEF9CA3A1")).build()
        MobileAds.initialize(this) {
            Log.e("Admobs", it.toString())
        }
        MobileAds.setRequestConfiguration(requestConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}