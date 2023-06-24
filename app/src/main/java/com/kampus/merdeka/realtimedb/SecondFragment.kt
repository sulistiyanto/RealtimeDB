package com.kampus.merdeka.realtimedb

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kampus.merdeka.realtimedb.databinding.FragmentSecondBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val args : SecondFragmentArgs by navArgs()
    private var user: User? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        user = args.user
        // kita cek jika tire null maka tampilan default nambah tambal ban
        // jika tire tidak nul tampilan sedikit berubah ada tombol hapus dan bah
        if (user != null) {
            binding.deleteButton.visibility = View.VISIBLE
            binding.saveButton.text = "Ubah"
            binding.nameEditText.setText(user?.name)
        }
        val firebaseDatabaseHelper = FirebaseDatabaseHelper()
        binding.saveButton.setOnClickListener {
            val name = binding.nameEditText.text
            if (user == null) {
                val user = User(name = name.toString())
                firebaseDatabaseHelper.createUser(user) { isSuccess, userID ->

                }
            } else {
                val user = User(id = user?.id, name = name.toString())
                firebaseDatabaseHelper.updateUser(user) {}
            }
            findNavController().popBackStack()
        }

        binding.deleteButton.setOnClickListener {
            user?.id?.let { it1 ->
                firebaseDatabaseHelper.deleteUser(it1) {

                }
            }
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}