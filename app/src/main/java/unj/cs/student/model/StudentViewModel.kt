package unj.cs.student.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import unj.cs.student.data.Student
import unj.cs.student.data.StudentDao

class StudentViewModel(private val studentDao: StudentDao): ViewModel() {

    val studentList: LiveData<List<Student>> = studentDao.getAllStudents().asLiveData()

    fun loadStudent(): List<Student>? {
        return studentList.value
    }

    fun setStudent(student: Student) {
        viewModelScope.launch {
            studentDao.update(student)
        }
    }

    fun addStudent(uid: String, name: String) {
        val newStudent: Student = Student(uid = uid, name = name)

        viewModelScope.launch {
            studentDao.insert(newStudent)
        }
    }

    fun getStudent(_id: Int): LiveData<Student> {
        return studentDao.getStudentById(_id).asLiveData()
    }

    fun getStudentListCount(): Int? {
        return studentList.value?.size
    }
}