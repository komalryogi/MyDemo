package com.example.mydemo.data.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "lock_info_table")
data class LockInfo(
    @PrimaryKey(autoGenerate = true) var id: Long,
    var isLocked: Boolean,
    var name: String
) : Parcelable {
    constructor() : this(0, true, "")
}