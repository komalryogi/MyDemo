package com.example.mydemo.ui.home

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mydemo.data.InfoDatabase
import com.example.mydemo.data.entity.LockInfo
import com.example.mydemo.data.remote.Resource
import com.example.mydemo.data.remote.remote.model.RepoCallback
import io.reactivex.schedulers.Schedulers

class HomeFragmentViewModel : ViewModel() {

    var lockDetails = MutableLiveData<MutableList<LockInfo>>()

    fun getLockDetals(): MutableLiveData<MutableList<LockInfo>> {
        return lockDetails
    }

    fun fetchData(context: Context?, callback: RepoCallback<MutableList<LockInfo>>) {


        InfoDatabase.getInstance(context!!).lockInfoDao().fetchAll()
            .subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).onErrorReturn {
                return@onErrorReturn mutableListOf<LockInfo>()
            }.subscribe { it ->
                if (it == null) {
                    callback.onResult(Resource(it, Resource.Status.FAIL, ""))
                } else callback.onResult(Resource(it, Resource.Status.SUCCESS, ""))
            }
    }

    fun reset(context: Context?){
        Thread(){
            InfoDatabase.getInstance(context!!).lockInfoDao().reset()
            InfoDatabase.getInstance(context!!).lockInfoDao().resetNext()
        }.start()

    }
}