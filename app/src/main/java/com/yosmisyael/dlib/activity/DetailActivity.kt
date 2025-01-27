package com.yosmisyael.dlib.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.yosmisyael.dlib.data.Book
import com.yosmisyael.dlib.R
import com.yosmisyael.dlib.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    companion object {
        const val EXTRA_BOOK = "extra_book"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setTitle("Book Detail")
        }

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val book = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra<Book>(EXTRA_BOOK, Book::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<Book>(EXTRA_BOOK)
        }

        if (book != null) {
            binding.bookStat.bookCover.setImageResource(book.photo)
            binding.bookStat.bookTitle.text = book.title
            binding.bookDescription.description.text = book.description
            binding.bookStat.bookYear.text = book.year.toString()
            binding.bookStat.bookAuthor.text = book.author
            binding.bookDescription.category.text = book.category
        }

        binding.bookStat.actionShare.setOnClickListener {
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out this amazing book: ${book?.link.toString()}")
            shareIntent.type = "text/plain"
            startActivity(Intent.createChooser(shareIntent, "Share to:"))
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}