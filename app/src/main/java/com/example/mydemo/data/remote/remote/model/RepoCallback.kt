package com.example.mydemo.data.remote.remote.model

import com.example.mydemo.data.remote.Resource


interface RepoCallback<T> {
    fun onResult(result: Resource<T, Resource.Status>)
}