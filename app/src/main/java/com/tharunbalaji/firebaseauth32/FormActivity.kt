package com.tharunbalaji.firebaseauth32

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.tharunbalaji.firebaseauth32.databinding.ActivityFormBinding

// [Main]

class FormActivity: AppCompatActivity (){

    private lateinit var binding: ActivityFormBinding
    private lateinit var database: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database = FirebaseFirestore.getInstance()

        val id = intent.getStringExtra("id")
        val name = intent.getStringExtra("name")
        val age = intent.getIntExtra("age", 0).toString()
        val marks = intent.getIntExtra("marks", 0).toString()
        val dept = intent.getStringExtra("dept")

        if (id != null) {
            binding.btnCreate.text = "UPDATE"
        } else {
            binding.btnDelete.visibility = View.GONE
        }

        binding.etName.setText(name)
        binding.etAge.setText(age)
        binding.etMarks.setText(marks)
        binding.etDept.setText(dept)

        binding.btnCreate.setOnClickListener {
            val name = binding.etName.text.toString()
            val age = binding.etAge.text.toString()
            val dept = binding.etDept.text.toString()
            val marks = binding.etMarks.text.toString()

            if (id != null) {
                updateData(id, name, age, dept, marks)
            } else {
                createData(name, age, dept, marks)
            }
        }

        binding.btnDelete.setOnClickListener {
            // DELETE
            database.collection("students").document(id!!).delete()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Data deleted successfully!", Toast.LENGTH_LONG).show()
                        finish()
                    } else {
                        Toast.makeText(this, "Error occurred: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                    }
                }
        }
    }

    private fun updateData(id: String, name: String, age: String, dept: String, marks: String) {
        // data -> key : value
        val studentData = mapOf<String, Any>(
            "name" to name,
            "age" to age.toInt(),
            "dept" to dept,
            "marks" to marks.toInt()
        )

        // UPDATE operation
        database.collection("students").document(id).update(studentData)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Data updated successfully!", Toast.LENGTH_LONG).show()
                    finish()
                } else {
                    Toast.makeText(this, "Error occurred: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
    }


    private fun createData(name: String, age: String, dept: String, marks: String) {
        // data -> key : value
        val studentData = mapOf<String, Any>(
            "name" to name,
            "age" to age.toInt(),
            "dept" to dept,
            "marks" to marks.toInt()
        )

        // CREATE operation
        database.collection("students").add(studentData)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Data added successfully!", Toast.LENGTH_LONG).show()
                    finish()
                } else {
                    Toast.makeText(this, "Error occurred: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
    }
}