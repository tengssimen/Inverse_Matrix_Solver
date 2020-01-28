package aad.solver.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "Matrices")
data class Matrix(
    var a11: Double,
    var a12: Double,
    var a21: Double,
    var a22: Double,
    var isFav: Int,
    @PrimaryKey var id: Long? = null
) : Parcelable

