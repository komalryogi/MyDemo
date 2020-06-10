package com.example.mydemo

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.mydemo.data.InfoDatabase
import com.example.mydemo.data.entity.LockInfo

class MainActivityViewModel : ViewModel() {
    
    fun updateData(context: Context, lockInfo: LockInfo) {
        Thread() {
            var db = InfoDatabase.getInstance(context)
            db.lockInfoDao().update(lockInfo)
        }.start()

    }


}