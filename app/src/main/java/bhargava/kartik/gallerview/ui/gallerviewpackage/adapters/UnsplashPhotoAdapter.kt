package bhargava.kartik.gallerview.ui.gallerviewpackage.adapters

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import bhargava.kartik.gallerview.R
import bhargava.kartik.gallerview.databinding.SingleItemRecyclerviewBinding
import bhargava.kartik.gallerview.dataclasses.PhotoItem
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import org.ocpsoft.prettytime.PrettyTime
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class UnsplashPhotoAdapter(
    private val listener: (CardView, PhotoItem) -> Unit
) :
    PagingDataAdapter<PhotoItem, UnsplashPhotoAdapter.PhotoViewHolder>(PHOTO_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding =
            SingleItemRecyclerviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        return PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    inner class PhotoViewHolder(private val binding: SingleItemRecyclerviewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(photo: PhotoItem) {
            binding.apply {
                val bitmap: Bitmap? = BlurHashDecoder.decode(
                    photo.blurHash,
                    80,
                    80
                )
                Glide.with(imageView.context).load(photo.urls?.regular).transition(
                        DrawableTransitionOptions.withCrossFade()
                ).diskCacheStrategy(DiskCacheStrategy.DATA)
                    .placeholder(BitmapDrawable(bitmap)).into(imageView)
                Glide.with(ivUserProfile.context).load(photo.user?.profileImage?.medium).transition(
                    DrawableTransitionOptions.withCrossFade()
                ).apply(
                    RequestOptions()
                        .fitCenter()
                        .placeholder(R.drawable.loading_animation)
                        .circleCrop()
                ).diskCacheStrategy(DiskCacheStrategy.DATA).into(ivUserProfile)
                tvUsername.text = "${photo.user?.username}"
                val prettyTime = PrettyTime()
                val dateFormat: DateFormat = SimpleDateFormat(
                    "yyyy-MM-dd'T'HH:mm:ss",
                    Locale.getDefault()
                )
                dateFormat.timeZone = TimeZone.getTimeZone("UTC")
                val timeString = photo.createdAt
                val date: Date
                try {
                    date = dateFormat.parse(timeString!!)!!
                    val getTime = prettyTime.format(date)
                    tvCreatedAt.text = getTime
                } catch (e: ParseException) {
                    e.printStackTrace()
                }
            }
            listener(binding.cardView, photo)
        }
    }

    companion object {
        private val PHOTO_COMPARATOR = object : DiffUtil.ItemCallback<PhotoItem>() {
            override fun areItemsTheSame(oldItem: PhotoItem, newItem: PhotoItem) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: PhotoItem, newItem: PhotoItem) =
                oldItem == newItem
        }
    }
}