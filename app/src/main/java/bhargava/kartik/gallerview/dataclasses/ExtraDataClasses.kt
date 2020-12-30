package bhargava.kartik.gallerview.dataclasses

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FullScreenImagePreview(
    var type: String?,
    var showcaseUrl: String?,
    var fullUrl: String?
) : Parcelable