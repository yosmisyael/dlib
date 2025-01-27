package com.yosmisyael.dlib

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yosmisyael.dlib.activity.AboutActivity
import com.yosmisyael.dlib.adapter.ListBookAdapter
import com.yosmisyael.dlib.data.Book
import com.yosmisyael.dlib.databinding.ActivityMainBinding
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    private lateinit var rvBooks: RecyclerView
    private val list = ArrayList<Book>()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        Thread.sleep(2000)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rvBooks = findViewById(R.id.rv_books)
        rvBooks.setHasFixedSize(true)
        list.addAll(getListBooks())
        showRecyclerList()

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_list -> {
                rvBooks.layoutManager = LinearLayoutManager(this)
            }
            R.id.action_grid -> {
                rvBooks.layoutManager = GridLayoutManager(this, 2)
            }
            R.id.about_page -> {
                val aboutIntent = Intent(this@MainActivity, AboutActivity::class.java)
                startActivity(aboutIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showSelectedBook(book: Book) {
        Toast.makeText(this, "Opening " + book.title, Toast.LENGTH_SHORT).show()
    }

    private fun getListBooks(): ArrayList<Book> {
        val dataTitle = resources.getStringArray(R.array.data_title)
        val dataDescription = resources.getStringArray(R.array.data_description)
        val dataPhoto = resources.obtainTypedArray(R.array.data_photo)
        val dataAuthor = resources.getStringArray(R.array.data_author)
        val dataYear = resources.getIntArray(R.array.data_year)
        val dataCategory = resources.getStringArray(R.array.data_category)
        val listBook = ArrayList<Book>()
        for (i in dataTitle.indices) {
            val book = Book(
                dataTitle[i],
                dataDescription[i],
                dataPhoto.getResourceId(i, -1),
                dataAuthor[i],
                dataYear[i],
                dataCategory[i]
            )
            listBook.add(book)
        }
        return listBook
    }

    private fun showRecyclerList() {
        binding.rvBooks.layoutManager = LinearLayoutManager(this)
        val listBookAdapter = ListBookAdapter(list)
        binding.rvBooks.adapter = listBookAdapter

        listBookAdapter.setOnItemClickCallback(object: ListBookAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Book) {
                showSelectedBook(data)
            }
        })
    }
}