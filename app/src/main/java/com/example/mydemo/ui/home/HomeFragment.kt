package com.example.mydemo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mydemo.R
import com.example.mydemo.base.BaseFragment
import com.example.mydemo.data.entity.LockInfo
import com.example.mydemo.data.remote.Resource
import com.example.mydemo.data.remote.remote.model.RepoCallback
import com.example.mydemo.ui.home.adapter.HomeAdapter

class HomeFragment : BaseFragment() {

    lateinit var viewModel: HomeFragmentViewModel;
    lateinit var btn_reset: Button
    lateinit var rv_view: RecyclerView
    lateinit var homeAdapter: HomeAdapter
    var info = mutableListOf<LockInfo>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_home, container, false)

        btn_reset = view.findViewById(R.id.btn_reset)
        rv_view = view.findViewById(R.id.rv_view)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel =
            ViewModelProviders.of(this).get(HomeFragmentViewModel::class.java)

        fetchItems()
        updateDataOnUi()

        btn_reset.setOnClickListener {
            viewModel.reset(context)
        }
        viewModel.getLockDetals()
            .observe(viewLifecycleOwner, object : Observer<MutableList<LockInfo>> {
                override fun onChanged(t: MutableList<LockInfo>?) {
                    info.addAll(t!!)
                    rv_view.post {
                        homeAdapter.notifyDataSetChanged()

                    }

                }

            })
    }

    fun fetchItems() {
        viewModel.fetchData(context, object : RepoCallback<MutableList<LockInfo>> {
            override fun onResult(result: Resource<MutableList<LockInfo>, Resource.Status>) {
                when (result.action) {
                    Resource.Status.SUCCESS -> {
                        val model = result.payload!! as MutableList<LockInfo>
                        if (model == null || model.size == 0) {
                            displayToast("Data not found.")
                        } else {
                            info.addAll(model)
                            homeAdapter.notifyDataSetChanged()
                        }

                    }
                    Resource.Status.FAIL -> {
                        displayToast("Something went wrong")
                    }
                }
            }

        })
    }

    private fun updateDataOnUi() {

        rv_view.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        homeAdapter = HomeAdapter(info, context)
        homeAdapter.setInfoListener(object : HomeAdapter.InfoClickedListener {
            override fun onInfoClickedListener(postion: Int) {
                val bundle = Bundle()
                bundle.putParcelable("model", info.get(postion))
                findNavController().navigate(R.id.action_nav_first_to_nav_second, bundle)
            }

        })
        rv_view.adapter = homeAdapter
    }

}
