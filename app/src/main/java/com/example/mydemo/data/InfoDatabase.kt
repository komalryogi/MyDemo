package com.example.mydemo.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mydemo.data.dao.LockInfoDao
import com.example.mydemo.data.entity.LockInfo

@Database(
    entities = [LockInfo::class],
    version = 1,
    exportSchema = false
)
abstract class InfoDatabase : RoomDatabase() {
    abstract fun lockInfoDao(): LockInfoDao

    companion object {
        fun getInstance(context: Context): InfoDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                InfoDatabase::class.java, "info.db"
            )/*.addCallback(object : Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {

                    if (ENABLE_MOCK_DATA) {
                          Executors.newSingleThreadScheduledExecutor()
                              .execute {
                                  getInstance(context.applicationContext).lockInfoDao()
                                      .insertAll(MockUpUtils.getCallList())
                            }
                    }
                    super.onCreate(db)
                }
            })*/.fallbackToDestructiveMigration().build()
        }
    }


}