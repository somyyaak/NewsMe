package com.example.newsme



import android.content.Context
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject
import kotlin.jvm.Throws

class MyViewModel(context: Context): ViewModel() {
    val context: Context = context
    val items: MutableLiveData<ArrayList<News>> = MutableLiveData<ArrayList<News>>()

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun fetchData(){
        val url: String = "https://newsapi.org/v2/top-headlines?country=us&apiKey=dd8a43ea1ad547079ae4b1d7e43f4a7f"
        val list = ArrayList<News>()
        val jsonObject: JsonObjectRequest = object: JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            Response.Listener<JSONObject> {
                val jsonArray = it.getJSONArray("articles")
                for(i in 0 until jsonArray.length()){
                    val jsonItem: JSONObject = jsonArray.getJSONObject(i)
                    list.add(News(jsonItem.getString("title"), jsonItem.getString("url"), jsonItem.getString("urlToImage"), jsonItem.getString("author")))

                }
                items.value = list
                //list.add(News("Trump news – live: Ex-AG launches attack on Trump in new book as he blames Biden for war in Europe - The Independent","https://www.independent.co.uk/news/world/americas/us-politics/donald-trump-news-today-putin-latest-b2024378.html", "https://static.independent.co.uk/2022/02/20/11/SEI88647887.jpeg?quality=75&width=1200&auto=webp","Maroosha Muzaffar"))
                //newsAdapter.update(list)
            },
            Response.ErrorListener {
                Log.v("TestingIshita", it.message.toString())
            }
        )


        {
            @Throws(AuthFailureError :: class)
            override fun getHeaders(): MutableMap<String, String> {
                val headers: MutableMap<String, String> = HashMap()
                headers["User-Agent"] = "Mozilla/5.0"
                return headers
            }
        }

        MySingleton.getInstance(context).addToRequestQueue(jsonObject)

    }

}