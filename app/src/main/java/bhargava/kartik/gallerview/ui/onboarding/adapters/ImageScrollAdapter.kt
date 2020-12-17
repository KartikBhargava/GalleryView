package bhargava.kartik.gallerview.ui.onboarding.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import bhargava.kartik.gallerview.R

class ImageScrollAdapter(
    private var images: List<Int>
) : RecyclerView.Adapter<ImageScrollAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.ivViewPagerOnBoarding)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_item_onboarding_view_pager, parent, false);
        return ViewHolder(view);
    }

    override fun getItemCount(): Int {
        return images.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = images[position]
        holder.imageView.setImageResource(item)
    }
}