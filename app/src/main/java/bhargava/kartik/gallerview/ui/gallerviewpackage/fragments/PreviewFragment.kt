package bhargava.kartik.gallerview.ui.gallerviewpackage.fragments

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import bhargava.kartik.gallerview.R
import bhargava.kartik.gallerview.databinding.FragmentPrevieBinding
import bhargava.kartik.gallerview.dataclasses.FullScreenImagePreview
import bhargava.kartik.gallerview.dataclasses.PhotoItem
import bhargava.kartik.gallerview.extras.Constants.ALL_PICTURES_FRAGMENT
import bhargava.kartik.gallerview.extras.Constants.PREVIEW_FRAGMENT
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.google.android.gms.ads.*
import org.ocpsoft.prettytime.PrettyTime
import java.io.File
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class PreviewFragment : Fragment(R.layout.fragment_previe) {

    private var _binding: FragmentPrevieBinding? = null
    private val binding get() = _binding!!
    private lateinit var outputDirectory: File
    private var photo: PhotoItem? = null
    var mydownloadid: Long = 0


    private val mInterstitialAdUnitId: String by lazy {

        "ca-app-pub-3940256099942544/1033173712"
    }
    private lateinit var mInterstitialAd: InterstitialAd

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPrevieBinding.bind(view)
        photo = arguments?.getParcelable(ALL_PICTURES_FRAGMENT)
        binding.apply {
            Glide.with(requireContext()).load(photo?.urls?.regular)
                .into(imageView)
            backIcon.setOnClickListener {
                it.findNavController().popBackStack()
            }
            shareIcon.setOnClickListener {
                shareImage()
            }
            Glide.with(ivUserProfile.context).load(photo?.user?.profileImage?.medium).transition(
                DrawableTransitionOptions.withCrossFade()
            ).apply(
                RequestOptions()
                    .fitCenter()
                    .circleCrop()
            ).diskCacheStrategy(DiskCacheStrategy.DATA).into(ivUserProfile)
            tvUsername.text = "${photo?.user?.username}"
            val prettyTime = PrettyTime()
            val dateFormat: DateFormat = SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss",
                Locale.getDefault()
            )
            dateFormat.timeZone = TimeZone.getTimeZone("UTC")
            val timeString = photo?.createdAt
            val date: Date
            try {
                date = dateFormat.parse(timeString!!)!!
                val getTime = prettyTime.format(date)
                tvCreatedAt.text = getTime
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            tvDescription.text = " ${photo?.altDescription}"
            val attributionUrl =
                "https://unsplash.com/${photo?.user?.username}?utm_source=ImageSearchApp&utm_medium=referral"
            val uri = Uri.parse(attributionUrl)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            textViewCreator.apply {
                text = "Photo by ${photo?.user?.name} on Unsplash"
                setOnClickListener {
                    context.startActivity(intent)
                }
                paint.isUnderlineText = true
            }
            downloadBtn.setOnClickListener {
                downloadImage()
            }
            setWallpaperButton.setOnClickListener {
                loadInterstitialAd(mInterstitialAdUnitId)

            }
            imageView.setOnClickListener {
                val fullScreenImageFragment = FullScreenImagePreview(
                    "View Image",
                    photo?.urls?.regular,
                    photo?.urls?.full
                )
                val bundle = bundleOf(PREVIEW_FRAGMENT to fullScreenImageFragment)
                it.findNavController()
                    .navigate(R.id.action_previewFragment_to_fullScreenImageFragment, bundle)
            }
        }
        mInterstitialAd = InterstitialAd(requireActivity())
        runAdEvents()
    }

    private fun runAdEvents() {
        mInterstitialAd.adListener = object : AdListener() {

            // If user clicks on the ad and then presses the back, s/he is directed to DetailActivity.
            override fun onAdClicked() {
                super.onAdOpened()
                mInterstitialAd.adListener.onAdClosed()
            }

            // If user closes the ad, s/he is directed to DetailActivity.
            override fun onAdClosed() {
                val fullScreenImageFragment = FullScreenImagePreview(
                    "Set Wall Paper",
                    photo?.urls?.regular,
                    photo?.urls?.full
                )
                val bundle = bundleOf(PREVIEW_FRAGMENT to fullScreenImageFragment)
                findNavController()
                    .navigate(R.id.action_previewFragment_to_fullScreenImageFragment, bundle)
            }

            override fun onAdLoaded() {
                super.onAdLoaded()
                if (mInterstitialAd.isLoaded) {
                    mInterstitialAd.show()
                } else {
                    val fullScreenImageFragment = FullScreenImagePreview(
                        "Set Wall Paper",
                        photo?.urls?.regular,
                        photo?.urls?.full
                    )
                    val bundle = bundleOf(PREVIEW_FRAGMENT to fullScreenImageFragment)
                    findNavController()
                        .navigate(R.id.action_previewFragment_to_fullScreenImageFragment, bundle)
                }
            }
        }
    }

    private fun loadInterstitialAd(mInterstitialAdUnitId: String) {
        mInterstitialAd.adUnitId = mInterstitialAdUnitId
        mInterstitialAd.loadAd(
            AdRequest.Builder().build()
        )
        mInterstitialAd.adListener.onAdLoaded()
    }

    private fun downloadImage() {
        val request = DownloadManager.Request(
            Uri.parse("${photo?.links?.downloadLocation}")
        )
            .setTitle("Download Image")
            .setDescription("Please wait while image is downloading...")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
            .setAllowedOverMetered(true)


        val dm = requireContext().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        mydownloadid = dm.enqueue(request)
        val br = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
                if (id == mydownloadid) {
                    Toast.makeText(
                        requireContext(),
                        "Download Completed + ${intent.data}",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
        }
        requireContext().registerReceiver(
            br,
            IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        )
    }

    private fun shareImage() {
        val share = Intent(Intent.ACTION_SEND)
        share.type = "text/plain"
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        share.putExtra(Intent.EXTRA_SUBJECT, "Wallpro Image")
        share.putExtra(Intent.EXTRA_TEXT, photo?.urls?.full)
        startActivity(Intent.createChooser(share, "Share link!"))
    }
}