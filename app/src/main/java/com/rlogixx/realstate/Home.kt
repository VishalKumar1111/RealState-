package com.rlogixx.realstate

import android.content.Intent
import com.rlogixx.realstate.API.ApiInterface
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.rlogixx.realstate.Detail.DetailedActivity
import com.rlogixx.realstate.NewProperty.NewProperty
import com.rlogixx.realstate.Property.AdapterItem
import com.rlogixx.realstate.Property.FlatDataItem
import com.rlogixx.realstate.Property.PropertyAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Home : AppCompatActivity() {

    var baseUrl = "https://rashitalk.com/api/"
    lateinit var courseRV: RecyclerView
    lateinit var courseRVAdapter: PropertyAdapter
    lateinit var courseList: ArrayList<AdapterItem>
    lateinit var courseRV2: RecyclerView
    lateinit var courseRV3: RecyclerView
    lateinit var courseRVAdapter2: PropertyAdapter
    lateinit var courseList2: ArrayList<AdapterItem>
    val imageList = ArrayList<SlideModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val plus:Button = findViewById(R.id.btn_plus)
        plus.setOnClickListener {
            startActivity(Intent(this,NewProperty::class.java))
        }


//        val progress : ProgressBar =findViewById(R.id.progressBar2)
//RECYCLERVIEW2 ADAPTER

        courseRV = findViewById(R.id.recyclerView2)

        getAllData()
//        courseList = ArrayList()
//
        val layoutManager = LinearLayoutManager(this@Home,LinearLayoutManager.HORIZONTAL,true)
      //  val layoutManager = GridLayoutManager(this, 2)

         courseRV.layoutManager = layoutManager


//
//
//        courseRVAdapter = PropertyAdapter(courseList)
//
//
//        courseRV.adapter = courseRVAdapter
//
//
//        courseList.add(AdapterItem(R.drawable.img,"Android Development"))
//        courseList.add(AdapterItem( R.drawable.img,"C++ Development"))
//        courseList.add(AdapterItem( R.drawable.img,"Java Development"))
//        courseList.add(AdapterItem( R.drawable.img,"Python Development"))
//        courseList.add(AdapterItem( R.drawable.img,"JavaScript Development",))
//
//        courseRVAdapter.notifyDataSetChanged()

//RECYCLERVIEW3 ADAPTER
        val layoutManager2 = LinearLayoutManager(this@Home,LinearLayoutManager.HORIZONTAL,true)
        courseRV2 = findViewById(R.id.recyclerView3)
        courseRV2.layoutManager = layoutManager2

        val layoutManager3 = LinearLayoutManager(this@Home,LinearLayoutManager.HORIZONTAL,true)
        courseRV3 = findViewById(R.id.recyclerView4)
        courseRV3.layoutManager = layoutManager3



//        courseList2 = ArrayList()
//
//
//        val layoutManager2 = GridLayoutManager(this, 2)
//
//        courseRV2.layoutManager = layoutManager2
//
//
//        courseRVAdapter2 = PropertyAdapter(courseList2)
//
//
//        courseRV2.adapter = courseRVAdapter2
//
//
//        courseList2.add(AdapterItem(R.drawable.img,"Android Development"))
//        courseList2.add(AdapterItem( R.drawable.img,"C++ Development"))
//        courseList2.add(AdapterItem( R.drawable.img,"Java Development"))
//        courseList2.add(AdapterItem( R.drawable.img,"Python Development"))
//        courseList2.add(AdapterItem( R.drawable.img,"JavaScript Development",))
//
//        courseRVAdapter2.notifyDataSetChanged()








    }

    private fun getAllData(){

        var retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
        var retroData = retrofit.getPropertyData()




        retroData.enqueue(object: Callback<List<FlatDataItem>>{
            override fun onResponse(
                call: Call<List<FlatDataItem>>,
                response: Response<List<FlatDataItem>>
            ) {
                var data = response.body()


                if (data != null){


                    val bannerItems = data.filter { it.section == "banner" }
                    val oneBhk = data.filter { it.section == "1bhk"  }
                    val twoBhk = data.filter { it.section == "2bhk" }
                    val threeBhk = data.filter { it.section == "3bhk" }

                    if (bannerItems.isNotEmpty()) {
                        val imageSlider: ImageSlider = findViewById<ImageSlider>(R.id.image_slider)


                        val slideModels = bannerItems.map { SlideModel(it.files) }

                        imageSlider.setImageList(slideModels,ScaleTypes.FIT)
                    } else {

                        Log.e("Banner", "No items with the 'banner' section found.")
                    }
                    if (oneBhk.isNotEmpty()){
                        Log.d("data",data.toString())
                        courseRVAdapter = PropertyAdapter(baseContext,oneBhk)
                        courseRV.adapter = courseRVAdapter

                        courseRVAdapter.onItemClick = {
                            startActivity(Intent(this@Home,DetailedActivity::class.java))

                        }
                    }else {
                        Log.e("onebhk","No Images Found")
                    }
                    if (twoBhk.isNotEmpty()){
                        Log.d("data",data.toString())
                        courseRVAdapter = PropertyAdapter(baseContext,twoBhk)
                        courseRV2.adapter = courseRVAdapter

                    }else {
                        Log.e("twobhk","No Images Found")
                    }

                    if (threeBhk.isNotEmpty()){
                        Log.d("data",data.toString())
                        courseRVAdapter = PropertyAdapter(baseContext,threeBhk)
                        courseRV3.adapter = courseRVAdapter
//                        courseRVAdapter.onItemClickListener?.let {
//                            val intent = Intent(this@Home,DetailedActivity::class.java)
//                            startActivity(intent)
//
//                        }

                    }else {
                        Log.e("twobhk","No Images Found")
                    }





                }
//                Log.d("data",data.toString())
//                courseRVAdapter = PropertyAdapter(baseContext,data)
//                courseRV.adapter = courseRVAdapter

            }

            override fun onFailure(call: Call<List<FlatDataItem>>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })


    }
}