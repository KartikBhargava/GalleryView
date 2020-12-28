package bhargava.kartik.gallerview.ui.gallerviewpackage.fragments

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import bhargava.kartik.gallerview.R
import bhargava.kartik.gallerview.databinding.FragmentPrevieBinding
import bhargava.kartik.gallerview.dataclasses.PhotoItem
import bhargava.kartik.gallerview.extras.Constants.ALL_PICTURES_FRAGMENT
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import org.ocpsoft.prettytime.PrettyTime
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class PreviewFragment : Fragment(R.layout.fragment_previe) {

    private var _binding: FragmentPrevieBinding? = null
    private val binding get() = _binding!!
    private var photo: PhotoItem? = null
    var mydownloadid: Long = 0
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
        }
    }

    private fun downloadImage() {
        val request = DownloadManager.Request(
            Uri.parse("${photo?.links?.downloadLocation}")
        )
            .setTitle("Download Image")
            .setDescription("Please wait while image is downloading...")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
            .setAllowedOverMetered(true)
        request.setDestinationInExternalPublicDir(
            requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                .toString(), "/Wallpro/${photo?.id}"
        )
        val dm = requireContext().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        mydownloadid = dm.enqueue(request)
        var br = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
                if (id == mydownloadid) {
                    Toast.makeText(requireContext(), "Download Completed", Toast.LENGTH_SHORT)
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
    }

}