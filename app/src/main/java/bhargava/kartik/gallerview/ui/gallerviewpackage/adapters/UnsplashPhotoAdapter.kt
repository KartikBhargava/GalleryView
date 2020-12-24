package bhargava.kartik.gallerview.ui.gallerviewpackage.adapters

import android.R
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import bhargava.kartik.gallerview.databinding.SingleItemRecyclerviewBinding
import bhargava.kartik.gallerview.dataclasses.PhotoItem
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions


class UnsplashPhotoAdapter :
    PagingDataAdapter<PhotoItem, UnsplashPhotoAdapter.PhotoViewHolder>(PHOTO_COMPARATOR) {
    private var lastPosition = -1

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

    class PhotoViewHolder(private val binding: SingleItemRecyclerviewBinding) :
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
                )
                    .placeholder(BitmapDrawable(bitmap)).into(imageView)
            }
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