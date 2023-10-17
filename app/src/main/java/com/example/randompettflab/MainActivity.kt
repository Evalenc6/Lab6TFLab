package com.example.randompettflab

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    //Global Variables
    var petImageURL = ""
    private lateinit var petList :  MutableList<String>
    private lateinit var rvPets: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Code after we have set the content view
        petList = mutableListOf()
        rvPets = findViewById(R.id.petList)

        getDogImageURL()
    }

    private fun getDogImageURL(){
        val client = AsyncHttpClient()

        client["https://dog.ceo/api/breeds/image/random/20", object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                val petImageArray = json.jsonObject.getJSONArray("message")
                Log.d("petImageURL","pet image URL set: $petImageArray")
                for (i in 0 until petImageArray.length()){
                    petList.add(petImageArray.getString(i))
                }

                val adapter = PetAdapter(petList)
                rvPets.adapter = adapter
                rvPets.layoutManager = LinearLayoutManager(this@MainActivity)

                Log.d("petList" , "Petlist:$petList")
            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                throwable: Throwable?
            ) {
                Log.d("Dog Error", errorResponse)
            }

        }]
    }


    /*
    import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler

    client["https://dog.ceo/api/breeds/image/random", object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                Log.d("Dog", "response successful $json")
                petImageURL = json.jsonObject.getString("message")
                Log.d("petImageURL","pet image URL set: $petImageURL")
            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                throwable: Throwable?
            ) {
                Log.d("Dog Error", errorResponse)
            }

        }]
     */


    private fun getNextImage(button: Button, imageView: ImageView){

        button.setOnClickListener{
            getDogImageURL()
            /*val random = Random.nextInt(0,2)
            if(random == 0){
                getDogImageURL()
            }else if( random == 1){
                getCatImageURL()
            }*/
            Glide.with(this)
                .load(petImageURL)
                .fitCenter()
                .into(imageView)
        }
    }

    private fun getCatImageURL(){
        val client = AsyncHttpClient()

        client["https://dog.ceo/api/breeds/image/random/20", object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                Log.d("Cat", "response successful $json")
                var resultJSON = json.jsonArray.getJSONObject(0)
                petImageURL = resultJSON.getString("url")
                Log.d("petImageURL","pet image URL set: $petImageURL")
            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                throwable: Throwable?
            ) {
                Log.d("Dog Error", errorResponse)
            }

        }]
    }
}