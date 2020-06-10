package com.example.mydemo.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
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
            ).addCallback(object : Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {

                    /*if (ENABLE_MOCK_DATA) {
                          Executors.newSingleThreadScheduledExecutor()
                              .execute {
                                  getInstance(context.applicationContext).lockInfoDao()
                                      .insertAll(MockUpUtils.getCallList())
                            }
                    }*/
                    insertDataFirstTime(context)
                    super.onCreate(db)
                }
            }).fallbackToDestructiveMigration().build()
        }

        fun insertDataFirstTime(ctx: Context) {
            Thread() {
                val lockInfo = LockInfo(0, false, "Unlock")
                val lockInfo2 = LockInfo(0, true, "Lock")
                val lockInfo3 = LockInfo(0, true, "Lock")
                val lockInfo4 = LockInfo(0, true, "Lock")
                val lockInfo5 = LockInfo(0, true, "Lock")
                val listItems = mutableListOf<LockInfo>()

                listItems.add(lockInfo)
                listItems.add(lockInfo2)
                listItems.add(lockInfo3)
                listItems.add(lockInfo4)
                listItems.add(lockInfo5)
                var db = InfoDatabase.getInstance(ctx)
                db.lockInfoDao().insert(lockInfo)
                db.lockInfoDao().insert(lockInfo2)
                db.lockInfoDao().insert(lockInfo3)
                db.lockInfoDao().insert(lockInfo4)
                db.lockInfoDao().insert(lockInfo5)

                //   lockDetails.postValue(listItems)

            }.start()
        }
    }


}