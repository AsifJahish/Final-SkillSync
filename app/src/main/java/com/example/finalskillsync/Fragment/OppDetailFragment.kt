package com.example.finalskillsync.Fragment

import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.finalskillsync.Model.Opportunity
import com.example.finalskillsync.R
import com.example.finalskillsync.databinding.FragmentOppDetailBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class OppDetailFragment : Fragment() {
    private lateinit var database: DatabaseReference
    private var _binding: FragmentOppDetailBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() =
            OppDetailFragment.apply {
                // You can still perform any initialization here if needed
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOppDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val title = arguments?.getString("Title") ?: ""

        // Now you have the title, you can use it as needed
        Toast.makeText(requireContext(), "Received Title: $title", Toast.LENGTH_SHORT).show()

        retrieveOpp(title)

    }

    private fun retrieveOpp(oppTitle: String) {
        val databaseref = FirebaseDatabase.getInstance().reference.child("Opportunity")
        val databaseRef = databaseref.child(oppTitle)
        databaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (oppSnapshot in snapshot.children) {
                        val opp = oppSnapshot.getValue(Opportunity::class.java)
                        if (opp != null) {
                            // Update the views with the retrieved scholarship details
                            /* ID.text = "ID:\n${scholarship.scholarshipId}"*/
                            binding.title.text = opp.title
                            binding.level.text = "Degree:\n${opp.level}"

                            val benefitsText = opp.benefit?.replace(",", "\n") ?: ""
                            val trim = benefitsText.trim('"') ?: ""
                            binding.benefit.text = "detail :  \n$trim"

                            val linkText = opp.link
                            val linkSpannable = SpannableString("--> Click here")
                            linkSpannable.setSpan(
                                ForegroundColorSpan(resources.getColor(R.color.link_color)),
                                0,
                                linkSpannable.length,
                                0
                            )
                            linkSpannable.setSpan(
                                RelativeSizeSpan(1.2f),
                                0,
                                linkSpannable.length,
                                0
                            )
                            linkSpannable.setSpan(
                                StyleSpan(Typeface.BOLD),
                                0,
                                linkSpannable.length,
                                0
                            )
                            binding.link.text = linkSpannable

                            binding.link.setOnClickListener {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(linkText))
                                startActivity(intent)
                            }

                            binding.deadline.text = "Deadline:\n${opp.deadline}"
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle database error
                Toast.makeText(
                    requireContext(),
                    "Database error: " + error.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}