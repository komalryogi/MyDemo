package com.example.mydemo

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mydemo.data.InfoDatabase
import com.example.mydemo.data.entity.LockInfo
import com.example.mydemo.data.remote.Resource
import com.example.mydemo.data.remote.remote.model.RepoCallback
import io.reactivex.schedulers.Schedulers

class MainActivityViewModel : ViewModel() {


    var lockDetails = MutableLiveData<MutableList<LockInfo>>()

    fun getLockDetals(): MutableLiveData<MutableList<LockInfo>> {
        return lockDetails;
    }

    fun insertDataFirstTime(ctx: Context) {
        Thread() {
            val lockInfo = LockInfo(0, false, "Item1")
            val lockInfo2 = LockInfo(0, true, "Item2")
            val lockInfo3 = LockInfo(0, true, "Item3")
            val lockInfo4 = LockInfo(0, true, "Item4")
            val lockInfo5 = LockInfo(0, true, "Item5")
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

            lockDetails.postValue(listItems)

        }.start()
    }

    fun updateData(context: Context, lockInfo: LockInfo) {
        Thread() {
            var db = InfoDatabase.getInstance(context)
            db.lockInfoDao().update(lockInfo)
        }.start()

    }

    fun fetchData(context: Context, callback: RepoCallback<MutableList<LockInfo>>) {
        Thread() {


        }.start()

        InfoDatabase.getInstance(context).lockInfoDao().fetchAll()
            .subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).onErrorReturn {
                return@onErrorReturn mutableListOf<LockInfo>()
            }.subscribe { it ->
                if (it == null) {
                    callback.onResult(Resource(it, Resource.Status.FAIL, ""))
                } else callback.onResult(Resource(it, Resource.Status.SUCCESS, ""))
            }
    }

}