package com.example.pogodynka

import android.app.Activity
import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.text.Layout
import android.util.Log
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.MainThread
import androidx.annotation.UiThread
import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.firebase.R
import com.example.firebase.database.Database
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.*
import java.net.URL
import kotlin.math.roundToInt

class recyclerViewAdapter internal constructor(context: Context?, hobbys : ArrayList<Database>,currentFragment: String) : RecyclerView.Adapter<recyclerViewAdapter.ViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    var navController: NavController?=null
    val hobbys = hobbys
    val currentFragment= currentFragment




    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var hobbyTextView = itemView.findViewById<TextView>(R.id.hobbyTextView)
        var checkbox:MaterialCheckBox = itemView.findViewById(R.id.hobby_row_checkbox)

    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = inflater.inflate(R.layout.hobby_row, parent,false)
        navController= Navigation.findNavController(parent)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val firebase = FirebaseDatabase.getInstance()
        val firebaseAuth = FirebaseAuth.getInstance()
        val currentUser = firebaseAuth.currentUser!!.uid
        val myRef = firebase.getReference(currentUser)


        val current = hobbys[position]

        holder.hobbyTextView.text = current.name
        holder.checkbox.isChecked=current.finished
        holder.checkbox.setOnCheckedChangeListener{buttonView, isChecked ->
            val changedData = Database(current.name,isChecked)
            myRef.child(currentFragment).child(current.name).setValue(changedData)

        }
        holder.itemView.setOnClickListener{

         //   navController!!.navigate(action)

        }



    }
    fun getHobbyAt(position: Int): Database{
        return hobbys[position]
    }


    override fun getItemCount(): Int {
        d("hobbys.size",hobbys.size.toString())
        return hobbys.size

    }

}








