package com.tharunbalaji.firebaseauth32

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.tharunbalaji.firebaseauth32.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding
    private lateinit var studentAdapter: StudentAdapter
    private lateinit var database: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        database = FirebaseFirestore.getInstance()
        studentAdapter = StudentAdapter()
        binding.rvStudents.adapter = studentAdapter
        binding.rvStudents.layoutManager = LinearLayoutManager(this)

        binding.btnAdd.setOnClickListener {
            val intent = Intent(this, FormActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogout.setOnClickListener {
            auth.signOut()
            Toast.makeText(this, "User logout successful", Toast.LENGTH_LONG).show()

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }
    }

    override fun onResume() {
        super.onResume()
        getStudents()
    }

    // READ operation
    private fun getStudents() {
        database.collection("students").get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val studentData = mutableListOf<Student>()
                    for (document in task.result.documents) {
                        val student = document.data

                        if (student != null) {
                            studentData.add(
                                Student(
                                    id = document.id,
                                    name = student.get("name").toString(),
                                    age = student.get("age").toString().toInt(),
                                    marks = student.get("marks").toString().toInt(),
                                    dept = student.get("dept").toString()
                                ))
                        }
                    }

                    studentAdapter.submitList(studentData)
                    Log.d("FIRESTORE", "Student Data: $studentData")
                } else {
                    Log.d("FIRESTORE", "Error occurred")
                }
            }
    }
}