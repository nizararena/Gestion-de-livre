package com.gestiondelivres.app

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat.getColor
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class BookListAdapter(
    cntxt: Context
) : RecyclerView.Adapter<BookViewHolder>() {
    var context = cntxt
    var books = listOf<Book>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val context = parent.context
        val itemView = LayoutInflater.from(context).inflate(R.layout.book_list_item, parent, false)
        return BookViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = books[position]
        holder.updateWithBook(book, context)

    }

    override fun getItemCount(): Int = books.size

    fun setBooksList(booksList: ArrayList<Book>) {
        books = booksList
        notifyDataSetChanged()
    }

}

class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var title: TextView
    var author: TextView
    var bookimage: ImageView
var layout: ConstraintLayout

    init {
        title = itemView.findViewById(R.id.title)
        author = itemView.findViewById(R.id.author)
        bookimage = itemView.findViewById(R.id.bookImage)
        layout = itemView.findViewById(R.id.layout)
    }

    fun updateWithBook(book: Book, context: Context,) {
        layout.setOnClickListener {
            val intent = Intent(context as ListBookActivity , BookDetailActivity::class.java).apply {
                putExtra(EXTRA_BOOK, book)
            }
           context. startActivity(intent)
           context.finish()
        }



        title.text = book.title
        author.text = book.author
        Glide.with(context)
            .load(book.image)
            .fitCenter()
            .into(bookimage);
        if (book.read) {
            title.setBackgroundColor(context.getColor(R.color.green))
            author.setBackgroundColor(context.getColor(R.color.green))
        }

    }
}