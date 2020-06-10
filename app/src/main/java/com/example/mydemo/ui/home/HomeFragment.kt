package com.example.mydemo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
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
                when (result.action) {
                    Resource.Status.SUCCESS -> {
                        val info = result.payload!! as MutableList<LockInfo>
                        if (info == null || info.size == 0) {
                            displayToast("Data not found.")
                        } else {
                            updateDataOnUi(info)
                        }

                    }
                    Resource.Status.FAIL -> {
                        displayToast("Something went wrong")
                    }
                }
            }

        })
    }

    private fun updateDataOnUi(info: MutableList<LockInfo>) {
        rv_view.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val homeAdapter = HomeAdapter(info, context)
        homeAdapter.setInfoListener(object : HomeAdapter.InfoClickedListener {
            override fun onInfoClickedListener(info: LockInfo) {
                displayToast("Clicked")
                val bundle = Bundle()
                bundle.putParcelable("model", info)
                findNavController().navigate(R.id.action_nav_first_to_nav_second, bundle)
            }

        })
        rv_view.adapter = homeAdapter

    }

}
