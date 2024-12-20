package com.example.anslab5

import ItemAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.anslab5.databinding.ActivityMainBinding
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var itemAdapter: ItemAdapter
    private val items = mutableListOf<Item>() // MutableList for dynamic updates

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialise 100 items first
        for (index in 1..100) {
            items.add(Item("Title $index", "Description $index", R.drawable.asuna))
        }

        //toast message when click on items
        itemAdapter = ItemAdapter(items) { clickedItem ->
            Toast.makeText(this, "Clicked: ${clickedItem.title}", Toast.LENGTH_SHORT).show()
        }

        //set up recycler view
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = itemAdapter

        // button to add new items
        binding.addButton.setOnClickListener {
            val newIndex = items.size + 1
            val newItem = Item("Title $newIndex", "Description $newIndex", R.drawable.asuna)

            //add new
            items.add(newItem)

            //auto scroll down
            binding.recyclerView.scrollToPosition(items.size - 1)
        }
    }
}
