package com.example.instagramclone


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class RecyclerAdaptor(val posList : ArrayList<Post>) : RecyclerView.Adapter<RecyclerAdaptor.PostHolder>() {


    class PostHolder(val binding : RecylerRowBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val binding = RecyclerRowBinding.infalte(LayoutInflater.from(parent.context), parent,false)
        return PostHolder(binding)
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        holder.binding.recyclerReviewEmail.text = posList.get(position).email
        holder.binding.recyclerReviewComment.text = posList.get(position).comment
        Picasso.get().load(posList.get(position).downloadUrl).into(holder.binding.recyclerReviewImage)
    }

    override fun getItemCount(): Int {
        return posList.size
    }
}