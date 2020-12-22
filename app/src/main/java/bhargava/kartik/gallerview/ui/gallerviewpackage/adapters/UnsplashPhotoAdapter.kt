package bhargava.kartik.gallerview.ui.gallerviewpackage.adapters

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import bhargava.kartik.gallerview.databinding.SingleItemRecyclerviewBinding
import bhargava.kartik.gallerview.dataclasses.PhotoItem


class UnsplashPhotoAdapter :
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

    class PhotoViewHolder(private val binding: SingleItemRecyclerviewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(photo: PhotoItem) {
            binding.apply {
                var bitmap: Bitmap? = null
                bitmap = BlurHashDecoder.decode(
                    photo.blurHash,
                    imageView.layoutParams.width,
                    imageView.layoutParams.height
                )
                imageView.setImageBitmap(bitmap)
                // textView.text = photo.user?.username
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