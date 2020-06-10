package com.example.mydemo.base

import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.mydemo.MainActivity

open class BaseFragment : Fragment() {

    fun getMainActivity(): MainActivity? {

        if (!isValidActivity()!!) return null
        return activity as MainActivity?
    }

    fun isValidActivity(): Boolean? {
        if (activity == null || requireActivity().isFinishing)
            return false;
        return true
    }

    fun displayToast(msg: String){
        Toast.makeText(
            activity,
            msg,
            Toast.LENGTH_LONG
        ).show()
    }
}