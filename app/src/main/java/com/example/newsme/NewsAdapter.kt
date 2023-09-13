package com.example.newsme



import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewsAdapter(clicked: OnClicked): RecyclerView.Adapter<NewsAdapter.NewsViewHolder>(){
    private val itemsData: ArrayList<Article> = ArrayList()
    private val clicked: OnClicked = clicked

    class NewsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val title:TextView = itemView.findViewById(R.id.title)
        val image: ImageView = itemView.findViewById(R.id.image)
        val author: TextView = itemView.findViewById(R.id.author)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.items_news, parent, false)
        val newsHolder: NewsViewHolder = NewsViewHolder(view)
        view.setOnClickListener(object: View.OnClickListener{
            override fun onClick(p0: View?) {
                clicked.onCLick(itemsData[newsHolder.adapterPosition])

            }

        })
        return newsHolder
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentItem = itemsData[position]
        holder.title.text = currentItem.title
        holder.author.text = currentItem.author
        Glide.with(holder.itemView.context).load(currentItem.urlToImage).into(holder.image)
    }

    override fun getItemCount(): Int {
        return itemsData.size
    }

    fun update(items: List<Article>){
        itemsData.clear()
        itemsData.addAll(items)
        notifyDataSetChanged()
    }

}

interface OnClicked{
    fun onCLick(newsItem: Article)
}