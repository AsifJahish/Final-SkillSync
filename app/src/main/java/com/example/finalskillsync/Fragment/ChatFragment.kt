package com.example.finalskillsync.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.finalskillsync.Firebase.Models.Chat
import com.example.finalskillsync.Firebase.Models.Users

import com.example.finalskillsync.databinding.FragmentChatBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class ChatFragment : Fragment() {

    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!


    companion object {

        fun newInstance() =
            ChatFragment().apply {

            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.sendchat.setOnClickListener{
    /*        sendMessage()*/
        }


    }



    private fun getIDName(callback: (userName: String?, userId: Long?) -> Unit) {
        val email = arguments?.getString("Email") ?: ""
        val databaseRef = FirebaseDatabase.getInstance().reference.child("Users")
        val userRef = databaseRef.child(email)

        userRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val user = snapshot.getValue(Users::class.java)
                    val userName = user?.name
                    val userId = user?.userId

                    Toast.makeText(requireContext(), "$email, $userName, $userId", Toast.LENGTH_SHORT).show()

                    callback(userName, userId)
                } else {
                    // Handle the case when the snapshot doesn't exist
                    callback(null, null)
                }
            }


            override fun onCancelled(error: DatabaseError) {
                // Handle database error
                callback(null, null)
            }
        })
    }




    private fun sendMessage() {
        val title = arguments?.getString("Title") ?: ""
        val databaseRef = FirebaseDatabase.getInstance().reference.child("Chat")
        val chat = Chat()
        val time = chat.timestamp

        // Call getIDName to retrieve user data asynchronously
        getIDName { userName, userId ->
            if (userName != null && userId != null) {
                val commentId = databaseRef.push().key
                val comment = Chat(
                    chatId = commentId?.toLong(), // Generate a new chatId
                    timestamp = time,
                    chat = binding.writeChat.text.toString(),
                    userName = userName,
                    userId = userId,
                    oppTitle = title
                    // Add other properties as needed
                )

                commentId?.let {
                    databaseRef.child(it).setValue(comment)
                        .addOnSuccessListener {
                            // Handle success if needed
                            Log.d("Firebase", "Chat data inserted successfully.")
                        }
                        .addOnFailureListener { exception ->
                            // Handle failure if needed
                            Log.e("Firebase", "Failed to insert chat data: ${exception.message}")
                        }
                }
            }
        }
    }

}