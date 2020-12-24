package bhargava.kartik.gallerview.ui.gallerviewpackage.adapters

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import bhargava.kartik.gallerview.databinding.SingleItemRecyclerviewBinding
import bhargava.kartik.gallerview.databinding.SingleItemRecyclerviewSearchBinding
import bhargava.kartik.gallerview.dataclasses.PhotoItem
import bhargava.kartik.gallerview.dataclasses.UnsplashPhoto
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class UnsplashPhotoSearchAdapter :
    PagingDataAdapter<UnsplashPhoto, UnsplashPhotoSearchAdapter.PhotoViewHolder>(PHOTO_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding =
            SingleItemRecyclerviewSearchBinding.inflate(
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

    class PhotoViewHolder(private val binding: SingleItemRecyclerviewSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(photo: UnsplashPhoto) {
            binding.apply {
                val bitmap: Bitmap? = BlurHashDecoder.decode(
                    photo.blur_hash,
                    20,
                    20
                )
                Glide.with(itemView)
                    .load(photo.urls.regular)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .placeholder(BitmapDrawable(bitmap)).into(imageView)
            }
        }
    }

    companion object {
        private val PHOTO_COMPARATOR = object : DiffUtil.ItemCallback<UnsplashPhoto>() {
            override fun areItemsTheSame(oldItem: UnsplashPhoto, newItem: UnsplashPhoto) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: UnsplashPhoto, newItem: UnsplashPhoto) =
                oldItem == newItem
        }
    }
}