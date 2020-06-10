package com.example.mydemo.ui.second

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.mydemo.R
import com.example.mydemo.base.BaseFragment
import com.example.mydemo.data.entity.LockInfo

class SecondPageFragment : BaseFragment() {

    lateinit var data: LockInfo
    lateinit var btn_unlock: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view = inflater.inflate(R.layout.fragment_second_page, container, false)
        btn_unlock = view.findViewById(R.id.btn_unlock)
        updateUi()
        return view
    }

    private fun updateUi() {
        val arguments = arguments
        if (arguments != null) {
            val model = arguments.getParcelable<LockInfo>("model")
            data = model!!

        }
        setDataOnUi()

        btn_unlock.setOnClickListener {
            if (data.isLocked) {
                data.isLocked = false
                btn_unlock.setText("The Item is Already UnLocked")
            } else {
                data.isLocked = true
                btn_unlock.setText("Locked")
            }
            getMainActivity()!!.viewModel.updateData(getMainActivity()!!, data)
            getMainActivity()!!.onBackPressed()
        }

    }

    fun setDataOnUi() {
        if (data.isLocked) {
            btn_unlock.setText("The Item is Already UnLocked")
        } else {
            btn_unlock.setText("Locked")
        }
    }

}
