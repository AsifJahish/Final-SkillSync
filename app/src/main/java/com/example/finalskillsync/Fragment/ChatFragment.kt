package com.example.finalskillsync.Fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalskillsync.Adatpers.ChatAdapter
import com.example.finalskillsync.Adatpers.OppAdapter
import com.example.finalskillsync.Firebase.Models.Chat
import com.example.finalskillsync.Firebase.Models.Opportunity
import com.example.finalskillsync.Firebase.Models.Users
import com.example.finalskillsync.databinding.FragmentChatBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseException
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class ChatFragment : Fragment() {
    private lateinit var chatRef: DatabaseReference
    private lateinit var chatAdapter: ChatAdapter
    private var _binding: FragmentChatBinding? = null
    private lateinit var database: DatabaseReference
    private val binding get() = _binding!!
    private lateinit var userIdd:String
    private lateinit var userName:String



    companion object {
        fun newInstance() =
            ChatFragment().apply {
                // You can still perform any initialization here if needed
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.sendchat.setOnClickListener {

            sendMessage()
        }
        getChat()
        getIDName()
    }

    private fun getIDName() {

        database = FirebaseDatabase.getInstance().reference
        val sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val useremail = sharedPreferences.getString("useremail", "")
        val usersRef = database.child("Users")
        val usersQuery = usersRef.orderByChild("email").equalTo(useremail)

        usersQuery.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (userSnapshot in dataSnapshot.children) {
                        userIdd = (userSnapshot.child("userId").value as? Long).toString()
                        userName = (userSnapshot.child("name").value as? String).toString()
                        Toast.makeText(requireContext(), "$userIdd, $userName", Toast.LENGTH_SHORT)
                            .show()
                    }
                } else {
                    // Handle the case when the snapshot doesn't exist
                    Log.d("Firebase", "User data does not exist in the database.")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle database error
                Toast.makeText(
                    requireContext(),
                    "Database error: " + databaseError.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun sendMessage() {
        val title = arguments?.getString("titleForChat") ?: ""
        val databaseRef = FirebaseDatabase.getInstance().reference.child("Chat")
        val chat = Chat()
        val chatId= chat.chatId
        val time = chat.timestamp
        val message= binding.writeChat.text.toString()
        if(message.isEmpty()){
            Toast.makeText(requireContext(), "empty", Toast.LENGTH_SHORT).show()
        }else{ // Rest of your code, e.g., constructing and saving the chat message
            val comment = Chat(
                chatId = chatId,
                timestamp = time,
                chat = binding.writeChat.text.toString(),
                userName = userName,
                userId = userIdd.toLong(),
                oppTitle = title
                // Add other properties as needed
            )

            chatId?.let {
                databaseRef.child(it.toString()).setValue(comment)
                    .addOnSuccessListener {
                        Toast.makeText(
                            requireContext(),
                            "Comment saved successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                        getChat()
                    }.addOnFailureListener { exception ->
                        Toast.makeText(
                            requireContext(),
                            "Failed to save comment: ${exception.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
        }
        }
    }

    private fun getChat(){
        chatRef = FirebaseDatabase.getInstance().getReference("Chat")
        chatAdapter = ChatAdapter(requireContext(), mutableListOf())
        binding.recycleviewMessages.adapter = chatAdapter
        val title = arguments?.getString("titleForChat") ?: ""
        // Set up the layout manager for vertical display
        binding.recycleviewMessages.layoutManager = LinearLayoutManager(requireContext())


        chatRef.orderByChild("timestamp").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val chatList = mutableListOf<Chat>()

                for (oppSnapshot in snapshot.children) {
                    try {
                        val chat = oppSnapshot.getValue(Chat::class.java)
                        chat?.let {
                            if(it.oppTitle== title){

                            chatList.add(it)
                        }
                        }
                    } catch (e: DatabaseException) {
                        Log.e("HomeFragment", "Error converting Opportunity", e)
                    }
                }

                Log.d("HomeFragment", "Number of opportunities: ${chatList.size}")

                chatAdapter.updateData(chatList)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("HomeFragment", "Database error: ${error.message}")
            }
        })


}


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


