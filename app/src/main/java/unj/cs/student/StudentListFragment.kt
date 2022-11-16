package unj.cs.student

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import unj.cs.student.adapter.StudentAdapter
import unj.cs.student.data.StudentDb
import unj.cs.student.databinding.FragmentStudentListBinding
import unj.cs.student.model.StudentViewModel

private const val ARG_PARAM1 = "argToast"

class StudentListFragment : Fragment() {

    private var _binding: FragmentStudentListBinding? = null
    private val binding get() = _binding!!

    private val studentViewModel: StudentViewModel by viewModels()

    private lateinit var studentRecyclerView: RecyclerView
    private lateinit var fab: FloatingActionButton

    private var toastParam: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            toastParam = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStudentListBinding.inflate(inflater, container, false)
        binding.viewModel = studentViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        studentRecyclerView = binding.recyclerViewStudent
        studentRecyclerView.layoutManager = LinearLayoutManager(context)
        studentRecyclerView.adapter = StudentAdapter(requireContext(), studentViewModel)
        studentRecyclerView.addItemDecoration(
            DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        )
        activity?.title = "Student List"

        fab = binding.fab
        fab.setOnClickListener {
            val action = StudentListFragmentDirections
                .actionStudentListFragmentToStudentFormFragment(null, null)
            view.findNavController().navigate(action)
        }
    }

    override fun onStart() {
        super.onStart()
        studentRecyclerView.invalidate()
//        Toast.makeText(context, toastParam, Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView(){
        super.onDestroyView()
        _binding = null
    }


//    companion object {
//        @JvmStatic
//        fun newInstance(param1: String) =
//            StudentListFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                }
//            }
//    }

}