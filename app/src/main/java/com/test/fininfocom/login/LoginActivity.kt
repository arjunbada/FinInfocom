package com.test.fininfocom.login

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.test.fininfocom.R
import com.test.fininfocom.databinding.ActivityLoginBinding
import com.test.fininfocom.details.DetailsActivity
import com.test.fininfocom.utils.Constants
import com.test.fininfocom.utils.ShowAlert


class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    lateinit var showAlert: ShowAlert
    lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        checkIsAlreadyLoggedIn()
        setContentView(binding.root)
        setUpInitialization()
        setUpAllEvents()
    }

    private fun setUpInitialization() {
        title = getString(R.string.login)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back_24dp)

        showAlert = ShowAlert(this@LoginActivity)

    }

    private fun setUpAllEvents() {
        binding.buttonLogin.setOnClickListener {
            if (validation()) {
                //proceed
                loginUser(
                    binding.editTextUserName.text.toString(),
                    binding.editTextPassword.text.toString()
                )
            }
        }
    }

    private fun validation(): Boolean {
        val userName = binding.editTextUserName.text.toString().trim()
        val password = binding.editTextPassword.text.toString().trim()
        val passwordRegex = Regex("^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#\$%^&*]).{7}$")


        if ("" == userName) {
            showAlert.showMessageAutoDismiss(getString(R.string.enter_user_name))
            return false
        } else if ("" == password) {
            showAlert.showMessageAutoDismiss(getString(R.string.enter_password))
            return false
        } else if (userName.length != Constants.USER_NAME_LENGTH && userName.length != Constants.USER_NAME_LENGTH_FIREBASE) {
            showAlert.showMessageAutoDismiss(getString(R.string.user_name_length_error))
            return false
        } else if (password.length != Constants.PASSWORD_LENGTH) {
            showAlert.showMessageAutoDismiss(getString(R.string.password_length_error))
            return false
        } else if (!password.matches(passwordRegex)) {
            showAlert.showMessageAutoDismiss(getString(R.string.invalid_password))
            return false
        }
        return true
    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Login success
                    val intent = Intent(this@LoginActivity, DetailsActivity::class.java)
                    startActivity(intent)
                    finish() // Finish login activity
                } else {
                    // Login failed
                    Toast.makeText(
                        this,
                        getString(R.string.authentication_failed), Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun checkIsAlreadyLoggedIn(){
        // Check if user is already authenticated
        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            // User is already authenticated, navigate to next screen
            val intent = Intent(this, DetailsActivity::class.java)
            startActivity(intent)
            finish() // Finish current activity to prevent going back to login screen
            return // Exit onCreate method
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            onBackPressedDispatcher.onBackPressed()
        } else {
            onBackPressedDispatcher.onBackPressed()
        }
        return true
    }
}