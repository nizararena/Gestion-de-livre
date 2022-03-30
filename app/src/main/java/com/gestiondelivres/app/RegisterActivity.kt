package com.gestiondelivres.app

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class RegisterActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        mAuth = FirebaseAuth.getInstance();

        val signInBtn = findViewById<Button>(R.id.signin)
        val registerBtn = findViewById<Button>(R.id.btn_register)

        val inputEmail = findViewById<TextInputEditText>(R.id.input_email)
        val emailField = findViewById<TextInputLayout>(R.id.email_field)

        val inputPassword = findViewById<TextInputEditText>(R.id.input_password)
        val passwordField = findViewById<TextInputLayout>(R.id.password_field)

        val inputConfirmpassword = findViewById<TextInputEditText>(R.id.input_confirm_password)
        val confirmPasswordField = findViewById<TextInputLayout>(R.id.confirm_password_field)



        signInBtn.setOnClickListener {
            val i = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(i);
            finish();
        }



        registerBtn.setOnClickListener {
            var signupValid = true

            val email = inputEmail.getText().toString()
            if (email.isEmpty()) {

                emailField.setError("L'email est vide");
                signupValid = false;
            } else if (!isEmailValid(email)) {
                emailField.setError("L'email est vide");
                signupValid = false;
            }

            val password = inputPassword.getText().toString();
            if (password.isEmpty() || password.length < 6) {
                passwordField.setError("Le mot de passe est vide");
                signupValid = false;
            }
            val confirmpassword = inputConfirmpassword.getText().toString();
            if (!confirmpassword.equals(password)) {
                confirmPasswordField.setError("Le mot de passe est vide");
                signupValid = false;
            }

            if (signupValid) {
                mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(
                        this
                    ) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(
                                this@RegisterActivity,
                                "Registered successfully",
                                Toast.LENGTH_SHORT
                            ).show();
                            val intent = Intent(this@RegisterActivity, ListBookActivity::class.java)
                            startActivity(intent);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(
                                this@RegisterActivity, "Authentication failed.",
                                Toast.LENGTH_SHORT
                            ).show()

                        }
                    }

            }

        }
    }

    private fun isEmailValid(email: String): Boolean {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email)
            .matches();
    }


}

