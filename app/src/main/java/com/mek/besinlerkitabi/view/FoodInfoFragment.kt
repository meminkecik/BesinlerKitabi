package com.mek.besinlerkitabi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.mek.besinlerkitabi.R
import com.mek.besinlerkitabi.databinding.FragmentFoodInfoBinding
import com.mek.besinlerkitabi.util.gorselIndır
import com.mek.besinlerkitabi.util.placeHolderYap
import com.mek.besinlerkitabi.viewmodel.BesinDetayiViewModel
import kotlinx.android.synthetic.main.fragment_food_info.*


class FoodInfoFragment : Fragment() {
    private lateinit var besinDetayiViewModel: BesinDetayiViewModel
    private var foodId = 0
    private lateinit var databinding : FragmentFoodInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        databinding = DataBindingUtil.inflate(inflater, R.layout.fragment_food_info,container,false)
        return databinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            foodId = FoodInfoFragmentArgs.fromBundle(it).foodId
        }
        besinDetayiViewModel = ViewModelProvider(this).get(BesinDetayiViewModel::class.java)
        besinDetayiViewModel.roomVerisiniAl(foodId)
        observeLiveData()
    }
    fun observeLiveData(){
        besinDetayiViewModel.besinLiveData.observe(viewLifecycleOwner, Observer { besin ->
            besin?.let {
                databinding.secilenBesin = it
                /*
                besinDetayiIsim.text = it.besinIsmi
                besinDetayiKalori.text = it.besinKalori
                besinDetayiKarbonhidrat.text = it.besinKarbonhidrat
                besinDetayiProtein.text = it.besinProtein
                besinDetayiYag.text = it.besinYag
                context?.let {
                    besinDetayiImage.gorselIndır(besin.besinGorsel, placeHolderYap(it))
                }

                 */
            }
        })
    }

}