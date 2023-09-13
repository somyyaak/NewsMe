package com.example.newsme

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), OnClicked {
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var newsViewModel: NewsViewModel
    /*companion object {
        val listLive = MutableLiveData<ArrayList<News>>()
        val context = this

    }*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //val list = ArrayList<News>()
        //list.add(News("Trump news – live: Ex-AG launches attack on Trump in new book as he blames Biden for war in Europe - The Independent","https://www.independent.co.uk/news/world/americas/us-politics/donald-trump-news-today-putin-latest-b2024378.html", "https://static.independent.co.uk/2022/02/20/11/SEI88647887.jpeg?quality=75&width=1200&auto=webp","Maroosha Muzaffar"))
        newsAdapter = NewsAdapter(this)
        val recyclerView: RecyclerView = findViewById(R.id.recycle)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = newsAdapter
        newsViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))[NewsViewModel:: class.java]
        newsViewModel.fetchData()
        newsViewModel.items.observe(this, object: Observer<List<Article?>>{
            override fun onChanged(t: List<Article?>) {
                newsAdapter.update(t as List<Article>)

            }

        })


    }

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
                listLive.value = list
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

        MySingleton.getInstance(this).addToRequestQueue(jsonObject)

    }*/

    override fun onCLick(newsItem: Article) {
        val customsTabs: CustomTabsIntent.Builder = CustomTabsIntent.Builder()
        val customTabsIntent: CustomTabsIntent = customsTabs.build()
        customTabsIntent.launchUrl(this, Uri.parse(newsItem.url))

    }
}