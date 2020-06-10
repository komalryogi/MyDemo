package com.example.mydemo.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mydemo.R
import com.example.mydemo.base.BaseFragment
import com.example.mydemo.data.entity.LockInfo
import com.example.mydemo.data.remote.Resource
import com.example.mydemo.data.remote.remote.model.RepoCallback
import com.example.mydemo.ui.home.adapter.HomeAdapter
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment() {

    lateinit var viewModel: HomeFragmentViewModel;
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_home, container, false)

        viewModel =
            ViewModelProviders.of(this).get(HomeFragmentViewModel::class.java)

        fetchItems()
        return view
    }

    fun fetchItems() {
        viewModel.fetchData(context, object : RepoCallback<MutableList<LockInfo>> {
            override fun onResult(result: Resource<MutableList<LockInfo>, Resource.Status>) {
                Log.e("komal", "data" + result.payload!!.size)

                when (result.action) {
                    Resource.Status.SUCCESS -> {
                        val info = result.payload!! as MutableList<LockInfo>
                        if (info == null || info.size == 0) {
                            Toast.makeText(
                                this@HomeFragment.context,
                                "No Data Found",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            updateDataOnUi(info)
                        }

                    }
                    Resource.Status.FAIL -> {
                        Toast.makeText(
                            this@HomeFragment.context,
                            "Something went wrong",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                }
            }

        })
    }

    private fun updateDataOnUi(info: MutableList<LockInfo>) {
        rv_view.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)


        rv_view.adapter = HomeAdapter(info, context)

    }

}
