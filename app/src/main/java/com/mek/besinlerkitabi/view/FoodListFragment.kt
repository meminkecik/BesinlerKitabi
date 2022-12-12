package com.mek.besinlerkitabi.view

import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.mek.besinlerkitabi.R
import com.mek.besinlerkitabi.adapter.BesinRecyclerAdapter
import com.mek.besinlerkitabi.viewmodel.BesinViewModel
import kotlinx.android.synthetic.main.fragment_food_list.*


class FoodListFragment : Fragment() {
    private lateinit var viewModel : BesinViewModel
    private val besinRecyclerAdapter = BesinRecyclerAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_food_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(BesinViewModel::class.java)
        viewModel.refreshData()
        foodRecycler.layoutManager = LinearLayoutManager(context)
        foodRecycler.adapter = besinRecyclerAdapter
        observeLiveData()
        swipeRecycler.setOnRefreshListener {
            foodRecycler.visibility = View.GONE
            foodErrorMessage.visibility = View.GONE
            foodProgressBar.visibility = View.VISIBLE
            swipeRecycler.isRefreshing = false
            viewModel.refreshFromInternet()
        }

    }
    fun observeLiveData(){
        viewModel.besinler.observe(viewLifecycleOwner, Observer { besinler ->
            besinler?.let {
                foodRecycler.visibility = View.VISIBLE
                besinRecyclerAdapter.besinListesiniGuncelle(besinler)

            }

        })
        viewModel.besinHataMesaji.observe(viewLifecycleOwner, Observer {hata ->
            hata?.let {
                if (it){
                    foodErrorMessage.visibility = View.VISIBLE
                    foodRecycler.visibility = View.GONE
                }else{
                    foodErrorMessage.visibility = View.GONE
                }
            }
        })
        viewModel.besinYukleniyor.observe(viewLifecycleOwner, Observer { yukleniyor->
            yukleniyor?.let {
                if (it){
                    foodRecycler.visibility = View.GONE
                    foodErrorMessage.visibility = View.GONE
                    foodProgressBar.visibility = View.VISIBLE
                }else{
                    foodProgressBar.visibility = View.GONE
                }
            }
        })
    }
}
