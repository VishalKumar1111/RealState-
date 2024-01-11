package com.rlogixx.realstate.Property

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rlogixx.realstate.R

class PropertyAdapter(private var context: Context, private val arrList: List<FlatDataItem>?):RecyclerView.Adapter<PropertyAdapter.FoodViewHolder>() {

   var onItemClickListener:((AdapterItem)->Unit)? =null
    class FoodViewHolder(itemview:View):RecyclerView.ViewHolder(itemview){
        val imageView:ImageView=itemview.findViewById(R.id.image_vi)
        val textview:TextView=itemview.findViewById(R.id.text_vi)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.item,parent,false)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
     Glide.with(context).load(arrList!!.get(position).files).into(holder.imageView)
        holder.textview.text = arrList!![position].section

        /*   val food=foodList[position]
        holder.imageView.setImageResource(food.image)
        holder.textview.text=food.detail
       // holder.textview.text=food.des
        holder.itemView.setOnClickListener {
             onItemClickListener?.invoke(food)

        }

      */
    }

    override fun getItemCount(): Int {
        return arrList!!.size
    }


}