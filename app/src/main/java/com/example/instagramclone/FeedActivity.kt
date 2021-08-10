package com.example.instagramclone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instagramclone.databinding.ActivityFeedBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase

class FeedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFeedBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db : FirebaseFirestore
    private lateinit var postArray : ArrayList<Post>
    private lateinit var postAdaptor: RecyclerAdaptor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        postArray = ArrayList<Post>()

        auth = Firebase.auth

        db = FirebaseFirestore.getInstance()

        getData()

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        postAdaptor = RecyclerAdaptor(postArray)
        binding.recyclerView.adapter = postAdaptor

    }

    private fun getData(){
        db.collection("Post").orderBy("date", Query.Direction.DESCENDING).addSnapshotListener { value, error ->
            if(error != null){
                Toast.makeText(this@FeedActivity,error.localizedMessage,Toast.LENGTH_LONG).show()
            }else{

                if(value != null){
                    if(!value.isEmpty){
                        val documents = value.documents

                        postArray.clear() //sayfayi acarken arrayi temizliyor.

                        for(document in documents){
                            val comment = document.get("comment") as String
                            val userEmail = document.get("email") as String
                            val downloadUrl = document.get("downloadUrl") as String


                            val post = Post(userEmail,comment,downloadUrl)
                            postArray.add(post)

                        }

                        postAdaptor.notifyDataSetChanged()
                    }
                }

            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.insta_menu,menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == R.id.add_post){
            val intent = Intent(this,UploadActivity::class.java)
            startActivity(intent)
        }else if(item.itemId == R.id.sign_out){
            auth.signOut()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }


        return super.onOptionsItemSelected(item)
    }
}