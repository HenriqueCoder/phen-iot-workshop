package com.phen.firebasesensor

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.firebase.ui.auth.AuthUI
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), ChildEventListener{

    private lateinit var adapter: AlertsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

         adapter = AlertsListAdapter()

        rv_lista.adapter = adapter
        val mLayoutManager = LinearLayoutManager(this)
        mLayoutManager.reverseLayout = true
        mLayoutManager.stackFromEnd = true
        rv_lista.layoutManager = mLayoutManager

        FirebaseDatabase.getInstance().getReference("alert").addChildEventListener( this )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.menu_logout -> {
                AuthUI.getInstance()
                        .signOut(this)
                        .addOnCompleteListener {
                            finish()
                        }
                return true
            }
        }
        return false
    }
    override fun onChildMoved(p0: DataSnapshot, p1: String?) {
    }

    override fun onChildChanged(p0: DataSnapshot, p1: String?) {
    }

    override fun onChildAdded(p0: DataSnapshot, p1: String?) {
        val alert = p0.getValue(Alerts::class.java)
        adapter.add(alert!!)
        rv_lista.smoothScrollToPosition(adapter.itemCount -1)
    }

    override fun onChildRemoved(p0: DataSnapshot) {
    }

    override fun onCancelled(p0: DatabaseError) {
    }
}
