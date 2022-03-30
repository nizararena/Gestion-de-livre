package com.gestiondelivres.app

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

const val EXTRA_BOOK = "book"

class ListBookActivity : AppCompatActivity(){
        val booksList = arrayListOf<Book>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_list)


        val book_list = findViewById<RecyclerView>(R.id.book_list)
        val adapter = BookListAdapter(this)
        book_list.adapter = adapter
        val db = Firebase.firestore
        Log.e("ListBookActivity.kt", " calling firestore   ");
        val docRef = db.collection("books")
        docRef.get()
            .addOnSuccessListener { documents ->

                Log.e("ListBookActivity.kt", " do we have documents??  ${documents.isEmpty}   ");
                for (document in documents) {
                    if (document != null) {
                        val book = document.toObject<Book>()
                        book.id = document.id
                        booksList.add(book)
                        Log.d("firestore", "DocumentSnapshot data: ${document.data}")
                    } else {
                        Log.d("firestore", "No such document")
                    }
                }
            adapter.setBooksList(booksList)
            }
                    .addOnFailureListener { exception ->
                        Log.d("firestore", "get failed with ", exception)
                    }

    }
}