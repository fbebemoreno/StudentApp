package unj.cs.student.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Dao
import unj.cs.student.R
import unj.cs.student.StudentListFragmentDirections
import unj.cs.student.data.Student
import unj.cs.student.data.StudentDao
import unj.cs.student.data.StudentDb
import unj.cs.student.model.StudentViewModel

class StudentAdapter(context: Context, viewModel: StudentViewModel):
    RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    private val studentViewModel = viewModel

    private var studentList: MutableLiveData<List<Student>> = studentViewModel.studentList
            as MutableLiveData<List<Student>>

    init {
        if (studentList.value?.isEmpty() == true) {
            val names = context.resources.getStringArray(R.array.student_names).toList()
            val ids = context.resources.getStringArray(R.array.student_ids).toList()

            for (i in names.indices) {
                studentViewModel.addStudent(ids[i], names[i])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view_student, parent, false)
        return StudentViewHolder(layout)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val id: String = studentViewModel.getStudent(position).value!!.uid
        val name: String = studentViewModel.getStudent(position).value!!.name

        holder.idTextView.text = id
        holder.nameTextView.text = name

        holder.itemView.setOnClickListener {
            val action = StudentListFragmentDirections
                .actionStudentListFragmentToStudentFormFragment(
                    null, null, position
                )
            holder.view.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int = studentViewModel.getStudentListCount()!!


    class StudentViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        val idTextView: TextView = view.findViewById(R.id.idTextView)
        val nameTextView: TextView = view.findViewById(R.id.nameTextView)
    }

}