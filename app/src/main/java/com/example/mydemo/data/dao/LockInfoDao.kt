package com.example.mydemo.data.dao

import androidx.room.*
import com.example.mydemo.data.entity.LockInfo
import io.reactivex.Single

@Dao
interface LockInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(LockInfo: LockInfo): Long


    @Query("SELECT * FROM `lock_info_table`")
    fun fetchAll(): Single<MutableList<LockInfo>>


    @Delete()
    fun delete(LockInfo: LockInfo)

    @Update
    fun update(LockInfo: LockInfo): Int


    @Query("SELECT * FROM `lock_info_table` WHERE id=:id")
    fun fetchById(id: Long): Single<MutableList<LockInfo>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(LockInfos: MutableList<LockInfo>): List<Long>

}