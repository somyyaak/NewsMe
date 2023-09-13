package com.example.newsme



import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class MySingleton (context: Context) {
    val requestQueue: RequestQueue by lazy{ Volley.newRequestQueue(context.applicationContext)}
    companion object {
        private var instance: MySingleton? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: MySingleton(context).also {
                    instance = it
                }
            }

    }

    fun <T> addToRequestQueue(request: Request<T>){
        requestQueue.add(request)
    }

}