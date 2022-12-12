package com.mek.besinlerkitabi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.mek.besinlerkitabi.R
import com.mek.besinlerkitabi.databinding.BesinRecyclerRowBinding
import com.mek.besinlerkitabi.model.Besin
import com.mek.besinlerkitabi.util.gorselIndır
import com.mek.besinlerkitabi.util.placeHolderYap
import com.mek.besinlerkitabi.view.FoodListFragmentDirections
import kotlinx.android.synthetic.main.besin_recycler_row.view.*
import java.util.ArrayList

class BesinRecyclerAdapter(val besinArrayList: ArrayList<Besin>) : RecyclerView.Adapter<BesinRecyclerAdapter.BesinViewHolder>(),BesinClickListener {
    class BesinViewHolder( var view : BesinRecyclerRowBinding) : RecyclerView.ViewHolder(view.root){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BesinViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        //val view = inflater.inflate(R.layout.besin_recycler_row,parent,false)
        val view = DataBindingUtil.inflate<BesinRecyclerRowBinding>(inflater,R.layout.besin_recycler_row,parent,false)
        return BesinViewHolder(view)
    }

    override fun onBindViewHolder(holder: BesinViewHolder, position: Int) {
        holder.view.besin = besinArrayList[position]
        holder.view.listener = this
        /*holder.itemView.recyclerBesinName.text = besinArrayList.get(position).besinIsmi
        holder.itemView.recyclerBesinDegeri.text = besinArrayList.get(position).besinKalori
        holder.itemView.setOnClickListener {
            val action = FoodListFragmentDirections.actionFoodListFragmentToFoodInfoFragment(besinArrayList.get(position).uuid)
            Navigation.findNavController(it).navigate(action)
        }
        holder.itemView.recyclerImage.gorselIndır(besinArrayList.get(position).besinGorsel,
            placeHolderYap(holder.itemView.context))

         */
    }
    fun besinListesiniGuncelle(yeniBesinListesi : List<Besin>){
        besinArrayList.clear()
        besinArrayList.addAll(yeniBesinListesi)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return besinArrayList.size
    }

    override fun besinTiklandi(view: View) {
        val uuid = view.besin_uuid.text.toString().toIntOrNull()
        uuid?.let {
            val action = FoodListFragmentDirections.actionFoodListFragmentToFoodInfoFragment(it)
            Navigation.findNavController(view).navigate(action)
        }


    }
}