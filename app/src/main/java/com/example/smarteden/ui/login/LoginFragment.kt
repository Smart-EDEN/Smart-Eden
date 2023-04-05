package com.example.smarteden.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.example.smarteden.R
import com.example.smarteden.data.FireStoreViewModel
import com.example.smarteden.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null

    private val fireStoreViewModel: FireStoreViewModel by activityViewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        if(auth.currentUser?.uid != null) {
            val fireStoreViewModel: FireStoreViewModel by activityViewModels()
            fireStoreViewModel.setUser(auth.currentUser?.uid.toString(), auth)
            navigateToHomeScreen()
        }

        /*binding.login.setOnClickListener {
            navigateToHomeScreen()
        }*/

        binding.register.setOnClickListener {
            val email = binding.email.editText?.text.toString()
            val password = binding.password.editText?.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                registerUser(email, password)
            } else {
                Toast.makeText(activity, "Please enter email and password", Toast.LENGTH_SHORT).show()
            }
        }

        binding.login.setOnClickListener {
            val email = binding.email.editText?.text.toString()
            val password = binding.password.editText?.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                loginUser(email, password)
            } else {
                Toast.makeText(activity, "Please enter email and password", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    fireStoreViewModel.setUser(auth.currentUser!!.uid, auth)
                    navigateToHomeScreen()
                    // Benutzer erfolgreich angemeldet
                } else {
                    Toast.makeText(activity, "Authentication failed", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun registerUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    fireStoreViewModel.addUser(auth.currentUser!!.uid, email, auth)
                    navigateToHomeScreen()
                    // Benutzer erfolgreich registriert und angemeldet
                } else {
                    Toast.makeText(activity, "Registration failed", Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun navigateToHomeScreen() {
        findNavController().navigate(
            R.id.action_loginFragment_to_HomeFragment, null,
            navOptions { // Use the Kotlin DSL for building NavOptions
                anim {
                    enter = android.R.animator.fade_in
                    exit = android.R.animator.fade_out
                }
            }
        )
        /*parentFragmentManager.beginTransaction()
            .replace(this.id, homeFragment)
            .commit()*/
        //findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
    }
}
