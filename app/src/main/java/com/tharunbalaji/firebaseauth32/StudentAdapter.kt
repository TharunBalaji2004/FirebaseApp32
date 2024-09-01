package com.tharunbalaji.firebaseauth32

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.Normalizer.Form

class StudentViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val tvName: TextView = itemView.findViewById(R.id.tv_name)
    val tvAge: TextView = itemView.findViewById(R.id.tv_age)
    val tvMarks: TextView = itemView.findViewById(R.id.tv_marks)
    val tvDept: TextView = itemView.findViewById(R.id.tv_dept)

    fun bind(student: Student) {
        tvName.text = student.name
        tvAge.text = student.age.toString()
        tvMarks.text = student.marks.toString()
        tvDept.text = student.dept

        itemView.setOnClickListener {
            val intent = Intent(itemView.context, FormActivity::class.java)
            intent.putExtra("id", student.id)
            intent.putExtra("name", student.name)
            intent.putExtra("age", student.age)
            intent.putExtra("marks", student.marks)
            intent.putExtra("dept", student.dept)
            itemView.context.startActivity(intent)
        }
    }
}

class StudentAdapter: RecyclerView.Adapter<StudentViewHolder>() {

    private var studentsList = emptyList<Student>()

    fun submitList(studentsList: List<Student>) {
        this.studentsList = studentsList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_student, parent, false)
        return StudentViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return studentsList.size
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        holder.bind(studentsList.get(position))
    }
}