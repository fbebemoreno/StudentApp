package unj.cs.student

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.google.android.material.textfield.TextInputEditText
import unj.cs.student.data.Student
import unj.cs.student.databinding.FragmentStudentFormBinding
import unj.cs.student.model.StudentViewModel

private const val ID = "argStudentId"
private const val NAME = "argStudentName"
private const val POSITION = "argStudentPosition"

class StudentFormFragment : Fragment() {

    private var _binding: FragmentStudentFormBinding? = null
    private val binding get() = _binding!!

    private val studentViewModel: StudentViewModel by viewModels()

//    private var studentList: LiveData<MutableList<Student>> = studentViewModel.studentList
//    private var studentList: MutableList<Student> = StudentList.list

    private var studentId: String? = null
    private var studentName: String? = null
    private var studentPosition: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            studentId = it.getString(ID)
            studentName = it.getString(NAME)
            studentPosition = it.getInt(POSITION)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStudentFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val addStudentBtn: Button = view.findViewById(R.id.addStudentButton)
        val idText: TextInputEditText = view.findViewById(R.id.inputStudentId)
        val nameText: TextInputEditText = view.findViewById(R.id.inputStudentName)

        if (studentPosition != -1) {
            addStudentBtn.text = getString(R.string.edit)
            idText.setText(studentViewModel.getStudent(studentPosition).value!!.uid)
            nameText.setText(studentViewModel.getStudent(studentPosition).value!!.name)

            addStudentBtn.setOnClickListener {
                if (idText.text.toString() == "" || nameText.text.toString() == "") {
                    idText.error = "ID tidak boleh kosong!"
                    nameText.error = "Nama tidak boleh kosong!"

                    Toast.makeText(context, "ID atau Nama tidak boleh kosong!", Toast.LENGTH_LONG).show()
                } else {
                    val student = Student(uid = idText.text.toString(), name = nameText.text.toString())
                    studentViewModel.setStudent(student)

                    val action = StudentFormFragmentDirections
                        .actionStudentFormFragmentToStudentListFragment()
                    view.findNavController().navigate(action)

                    Toast.makeText(context, "Data berhasil diedit!", Toast.LENGTH_LONG).show()
                }
            }
        } else {
            addStudentBtn.setOnClickListener {
                if (idText.text.toString() == "" || nameText.text.toString() == "") {
                    idText.error = "ID tidak boleh kosong!"
                    nameText.error = "Nama tidak boleh kosong!"

                    Toast.makeText(context, "ID atau Nama tidak boleh kosong!", Toast.LENGTH_LONG).show()
                } else {
                    val student = Student(uid = idText.text.toString(), name = nameText.text.toString())
                    studentViewModel.addStudent(uid = idText.text.toString(), name = nameText.text.toString())

                    val action = StudentFormFragmentDirections
                        .actionStudentFormFragmentToStudentListFragment()
                    view.findNavController().navigate(action)

                    Toast.makeText(context, "${student.name} berhasil dibuat!", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

//    companion object {
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            StudentFormFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ID, studentId)
//                    putString(NAME, studentName)
//                    studentPosition?.let { putInt(POSITION, it) }
//                }
//            }
//    }
}