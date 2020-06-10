package com.example.mydemo.data.remote

class Resource<T, M>(val payload: T?, val action: M?, val message: String) {
    enum class Status {
        SUCCESS, FAIL, CONNECTION_ERROR, SERVER_ERROR, NOT_FOUND, FORBIDDEN
    }
}