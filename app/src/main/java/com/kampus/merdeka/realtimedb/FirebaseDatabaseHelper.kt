package com.kampus.merdeka.realtimedb

import android.os.Parcelable
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(val id: String? = null, val name: String? = null): Parcelable

class FirebaseDatabaseHelper {
    private val databaseRef = FirebaseDatabase.getInstance().reference

    fun createUser(user: User, callback: (Boolean, String?) -> Unit) {
        val childRef = databaseRef.child("users").push()
        val userId = childRef.key // Mendapatkan ID yang dihasilkan secara otomatis
        val newUser = User(userId, user.name)
        childRef.setValue(newUser)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(true, userId)
                } else {
                    callback(false, null)
                }
            }
    }

    fun readUsers(callback: (List<User>) -> Unit) {
        val usersRef = databaseRef.child("users")
        usersRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val userList = mutableListOf<User>()
                if (snapshot.exists()) {
                    for (childSnapshot in snapshot.children) {
                        val user = childSnapshot.getValue(User::class.java)
                        user?.let {
                            userList.add(it)
                        }
                    }
                }
                callback(userList)
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle the error
            }
        })
    }

    fun updateUser(user: User, callback: (Boolean) -> Unit) {
        val userRef = databaseRef.child("users").child(user.id!!)
        userRef.setValue(user)
            .addOnCompleteListener { task ->
                callback(task.isSuccessful)
            }
    }

    fun deleteUser(userId: String, callback: (Boolean) -> Unit) {
        val userRef = databaseRef.child("users").child(userId)
        userRef.removeValue()
            .addOnCompleteListener { task ->
                callback(task.isSuccessful)
            }
    }
}
