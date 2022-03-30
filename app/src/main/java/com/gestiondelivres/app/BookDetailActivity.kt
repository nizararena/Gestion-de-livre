package com.gestiondelivres.app

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class BookDetailActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_detail)

        val data = intent.extras
        val book = data?.getParcelable<Book>(EXTRA_BOOK)

        val db = Firebase.firestore

        val docRef = db.collection("books")

        val checkboxRead = findViewById<MaterialCheckBox>(R.id.checkboxRead)
        val BookTitle = findViewById<TextView>(R.id.BookTitle)
        val author = findViewById<TextView>(R.id.author)
        val description = findViewById<TextView>(R.id.description)
        val bookImage = findViewById<ImageView>(R.id.bookImage)


        if (book != null) {

            // check box to set read or not ... direct set read in firestore!
            checkboxRead.isChecked = book.read
            checkboxRead.setOnCheckedChangeListener { compoundButton, b ->
                val updateData = hashMapOf("read" to b)
                docRef.document(book.id).set(updateData, SetOptions.merge())

            }
            // title name of book
            title = book.title
            // title
            BookTitle.text = book.title
            //author
            author.text = book.author
            // image
            Glide.with(this).load(book.image).fitCenter().into(bookImage);
            // bood description ..
            description.text=book.description

        }

    }

    override fun onBackPressed() {

        val intent = Intent(this, ListBookActivity::class.java)
        startActivity(intent)
        finish()
    }
}