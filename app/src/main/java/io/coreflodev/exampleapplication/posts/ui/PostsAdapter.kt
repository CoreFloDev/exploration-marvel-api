package io.coreflodev.exampleapplication.posts.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding3.view.clicks
import io.coreflodev.exampleapplication.R
import io.coreflodev.exampleapplication.posts.PostsViewModel
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import kotlinx.android.synthetic.main.posts_item.view.*

class PostsAdapter : RecyclerView.Adapter<PostsAdapter.PostViewHolder>() {

    private val inputs: Subject<String> = PublishSubject.create()
    private val subscriptions = CompositeDisposable()

    private val differ = AsyncListDiffer(this, object : DiffUtil.ItemCallback<PostsViewModel>() {
        override fun areItemsTheSame(oldItem: PostsViewModel, newItem: PostsViewModel) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: PostsViewModel, newItem: PostsViewModel) = oldItem == newItem
    })

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder =
        PostViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.posts_item, parent, false))

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val item = differ.currentList[position]

        subscriptions.add(holder.itemView.clicks()
            .map { item.id }
            .subscribe(inputs::onNext))

        holder.bind(item.content)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) = subscriptions.clear()


    fun update(model: List<PostsViewModel>) = differ.submitList(model)

    fun onItemClicked(): Observable<String> = inputs


    inner class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val content = itemView.content_posts_item

        fun bind(postContent: String) {
            content.text = postContent
        }
    }
}
