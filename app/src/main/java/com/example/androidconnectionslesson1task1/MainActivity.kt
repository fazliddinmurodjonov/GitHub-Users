package com.example.androidconnectionslesson1task1

import android.content.Context
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.annotation.RequiresApi
import by.kirich1409.viewbindingdelegate.viewBinding
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.androidconnectionslesson1task1.adapters.UserAdapter
import com.example.androidconnectionslesson1task1.databinding.ActivityMainBinding
import com.example.androidconnectionslesson1task1.models.User
import com.example.androidconnectionslesson1task1.utils.NetworkHelper
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.reflect.TypeToken
import org.json.JSONArray

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private val binding: ActivityMainBinding by viewBinding()
    lateinit var requestQueue: RequestQueue
    val url = "https://api.github.com/users"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestQueue = Volley.newRequestQueue(this)

        val jsonArrayRequest =
            JsonArrayRequest(Request.Method.GET, url, null, object : Response.Listener<JSONArray> {
                override fun onResponse(response: JSONArray?) {
                    val type = object : TypeToken<ArrayList<User>>() {}.type
                    val userList = Gson().fromJson<ArrayList<User>>(response.toString(), type)
                    val userAdapter = UserAdapter(userList, this@MainActivity)
                    binding.userRV.adapter = userAdapter
                }
            }, object : Response.ErrorListener {
                override fun onErrorResponse(error: VolleyError?) {

                }
            })

        requestQueue.add(jsonArrayRequest)


//           val networkHelper = NetworkHelper(this)
//        if (networkHelper.isNetworkConnected()) {
//            binding.tv.text = "Connected"
//            requestQueue = Volley.newRequestQueue(this)
//            fetchImage()
//        } else {
//            binding.tv.text = "Disconnected"
//        }


    }
//    private fun fetchImage() {
//        val imageRequest =
//            ImageRequest("https://images.pexels.com/photos/9754/mountains-clouds-forest-fog.jpg",
//                Response.Listener<Bitmap> {
//
//                    binding.image.setImageBitmap(it)
//                },
//                0,
//                0,
//                ImageView.ScaleType.CENTER_CROP,
//                Bitmap.Config.ARGB_8888,
//                Response.ErrorListener {
//                    binding.tv.text = it?.message
//                })
//        requestQueue.add(imageRequest)
//    }


    fun isNetworkConnected(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork
            val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
            networkCapabilities != null && networkCapabilities.hasCapability(
                NetworkCapabilities.NET_CAPABILITY_INTERNET)

        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            activeNetworkInfo != null && activeNetworkInfo.isConnected
        }
    }
}