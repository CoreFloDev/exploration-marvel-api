package io.coreflodev.exampleapplication.comics

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding3.view.clicks
import io.coreflodev.exampleapplication.R
import io.coreflodev.exampleapplication.comics.arch.ComicsViewModel
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import kotlinx.android.synthetic.main.comics_item.view.*

class ComicsAdapter : RecyclerView.Adapter<ComicsAdapter.PostViewHolder>() {

    private val inputs: Subject<String> = PublishSubject.create()
    private val subscriptions = CompositeDisposable()

    private val differ = AsyncListDiffer(this, object : DiffUtil.ItemCallback<ComicsViewModel>() {
        override fun areItemsTheSame(oldItem: ComicsViewModel, newItem: ComicsViewModel) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ComicsViewModel, newItem: ComicsViewModel) = oldItem == newItem
    })

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder =
        PostViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.comics_item, parent, false))

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val item = differ.currentList[position]

        subscriptions.add(holder.itemView.clicks()
            .map { item.id }
            .subscribe(inputs::onNext))

        holder.bind(item.content, item.url)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) = subscriptions.clear()


    fun update(model: List<ComicsViewModel>) = differ.submitList(model)

    fun onItemClicked(): Observable<String> = inputs


    inner class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val content = itemView.content_comics_item
        private val image = itemView.content_comics_image

        fun bind(comicsContent: String, url: String) {
            content.text = comicsContent
            image.load(url)
        }
    }
}
