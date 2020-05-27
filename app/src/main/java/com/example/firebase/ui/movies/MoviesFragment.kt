package com.example.firebase.ui.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firebase.R
import com.example.firebase.database.Database
import com.example.pogodynka.recyclerViewAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_moviess.*

class MoviesFragment : Fragment() {

    private lateinit var dashboardViewModel: MoviesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val currentFragment = "Movies"
        val hobbys = ArrayList<Database>()
        dashboardViewModel =
            ViewModelProviders.of(this).get(MoviesViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_moviess, container, false)
        val firebase = FirebaseDatabase.getInstance()
        val firebaseAuth = FirebaseAuth.getInstance()
        val currentUser = firebaseAuth.currentUser!!.uid
        val myRef = firebase.getReference(currentUser)
        val fragmentData = myRef.child(currentFragment)
        val recyclerview: RecyclerView = root.findViewById(R.id.rv_movies)
        val adapter = recyclerViewAdapter(context,hobbys,currentFragment)

        recyclerview.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        fragmentData.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                hobbys.clear()
                for (i in dataSnapshot.children) {

                    hobbys.add(i.getValue(Database::class.java)!!)
                }

                setAdapter(recyclerview,adapter)
            }

        })
        val itemTouchCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val currentHobby = adapter.getHobbyAt(position).name
                fragmentData.child(currentHobby).addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        p0.ref.removeValue()
                    }
                })
                Toast.makeText(context,"Movie deleted successfully", Toast.LENGTH_LONG).show()

            }

        }
        val itemTouchHelper = ItemTouchHelper(itemTouchCallback)
        itemTouchHelper.attachToRecyclerView(recyclerview)

        return root


    }

    fun setAdapter(recyclerView: RecyclerView,adapter: recyclerViewAdapter){

        recyclerView.adapter = adapter

    }
}
