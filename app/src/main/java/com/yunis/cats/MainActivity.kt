package com.yunis.cats

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.*
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {

    private lateinit var myImageView: ImageView
    private lateinit var randomImageButton: Button


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        myImageView = findViewById(R.id.imageView)

        randomImageButton = findViewById(R.id.button)


        // Instantiate the cache
        val cache = DiskBasedCache(cacheDir, 1024 * 1024) // 1MB cap

// Set up the network to use HttpURLConnection as the HTTP client.
        val network = BasicNetwork(HurlStack())

// Instantiate the RequestQueue with the cache and network. Start the queue.
        RequestQueue(cache, network).apply {
            start()
        }
        randomImageButton.setOnClickListener {

        val url = "https://api.thecatapi.com/v1/images/search?api_key=9e4dbed9-3653-426f-9503-6a495e1f2659"

            val jsonArrayRequest = JsonArrayRequest(Request.Method.GET, url, null,
                { response ->

                    val imgUrl = response.getJSONObject(0).getString("url").toString()
                    Picasso.get().load(imgUrl).resize(600, 600)
                        .into(myImageView)

                    Toast.makeText(this,"New random image loaded",Toast.LENGTH_SHORT).show()

                },

                { error ->
                    // TODO: Handle error
                Toast.makeText(this,error.toString(),Toast.LENGTH_SHORT).show()

                }

            )
            MySingleton.getInstance(this).addToRequestQueue(jsonArrayRequest)

        }

    }
}