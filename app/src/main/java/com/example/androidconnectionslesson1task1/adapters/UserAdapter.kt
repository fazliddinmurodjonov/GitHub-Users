package com.example.androidconnectionslesson1task1.adapters

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.Volley
import com.example.androidconnectionslesson1task1.databinding.ItemUserBinding
import com.example.androidconnectionslesson1task1.models.User

class UserAdapter(var userList: ArrayList<User>, var context: Context) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    inner class UserViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(user: User) {
            var requestQueue: RequestQueue = Volley.newRequestQueue(context)

            val imageRequest =
                ImageRequest("${user.avatar_url}",
                    Response.Listener<Bitmap> {

                        binding.avatar.setImageBitmap(it)
                    },
                    0,
                    0,
                    ImageView.ScaleType.CENTER_CROP,
                    Bitmap.Config.ARGB_8888,
                    Response.ErrorListener {
                    })
            // binding.avatar.setImageURI(Uri.parse(user.avatar_url))
            binding.login.text = user.login
            requestQueue.add(imageRequest)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(ItemUserBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.onBind(userList[position])
    }

    override fun getItemCount(): Int = userList.size
}