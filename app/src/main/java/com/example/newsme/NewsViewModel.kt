package com.example.newsme



import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.*
import retrofit2.create

class NewsViewModel(application: Application) : AndroidViewModel(application) {
    val items: MutableLiveData<List<Article?>> = MutableLiveData<List<Article?>>()

    /*fun fetchData(){
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

        MySingleton.getInstance(getApplication()).addToRequestQueue(jsonObject)

    }*/

    fun fetchData(){
        val newsAPI = RetrofitHelper.getInstance().create(NewsInterface :: class.java)
        GlobalScope.launch(Dispatchers.IO) {
            val result = newsAPI.getArticles("us", "dd8a43ea1ad547079ae4b1d7e43f4a7f")
            if (result != null) {
                val newsArticles = result.result
                if (newsArticles != null) {
                    withContext(Dispatchers.Main) {
                        items.value = newsArticles.articles
                    }
                }
            }
        }
    }
}