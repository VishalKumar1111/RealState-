package com.rlogixx.realstate.Property

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rlogixx.realstate.R

class PropertyAdapter(private val context: Context, private val arrList: List<FlatDataItem>?):RecyclerView.Adapter<PropertyAdapter.FoodViewHolder>() {

  var onItemClick: ((FlatDataItem) -> Unit)? = null
    class FoodViewHolder(itemview:View):RecyclerView.ViewHolder(itemview){
        val imageView:ImageView=itemview.findViewById(R.id.image_vi)
        val textview:TextView=itemview.findViewById(R.id.text_vi)
//        init {
//            itemview.setOnClickListener {
//                listener.onItemClick(adapterPosition)
//            }
//        }

//            itemview.setOnClickListener {
//                val position = adapterPosition
//                if(position != RecyclerView.NO_POSITION){
//
//                }
//            }
//
//        val progress :ProgressBar = itemview.findViewById(R.id.progressBar2)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.item,parent,false)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
//        val arrPosition = arrList?.get(position)
//        var arrposition = arrList?.get(position)
        Glide.with(context).load(arrList!!.get(position).files).into(holder.imageView)

        holder.textview.text = arrList[position].landmark
//        holder.progress.visibility = View.VISIBLE
//        holder.itemView.setOnClickListener {
////            if (arrPosition != null) {
////                onItemClickListener?.invoke(arrPosition)
////            }
//            onItemClickListener!!.invoke(arrPosition!!)
//
//        }
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(arrList[position])
        }



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