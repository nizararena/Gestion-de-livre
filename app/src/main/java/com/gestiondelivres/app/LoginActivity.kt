package com.gestiondelivres.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.annotation.NonNull
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.AuthResult
import android.text.TextUtils
import android.util.Patterns
import com.google.firebase.auth.FirebaseAuth
import com.google.android.gms.tasks.OnCompleteListener





class LoginActivity : AppCompatActivity() {

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        findViewById<Button>(R.id.signup_btn)
            .setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            })

        findViewById<Button>(R.id.login_btn)
            .setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View) {
                    var loginValid = true

                    val email = findViewById<TextInputEditText>(R.id.input_email)
                        .getText().toString();
                    val emailField = findViewById<TextInputLayout>(R.id.email_field)
                    if (email.isEmpty()) {

                        emailField
                            .setError("L'email est vide");
                        loginValid = false;
                    }
                    if (!isEmailValid(email)) {

                        emailField
                            .setError("L'email est vide");
                        loginValid = false;
                    } else {
                        emailField.setError(null);
                    }


                    val password = findViewById<TextInputEditText>(R.id.input_password)
                        .getText().toString()
                    val passwordField = findViewById<TextInputLayout>(R.id.password_field)
                    if (password.isEmpty() || password.length < 6) {
                        passwordField.setError("Le mot de passe est vide");
                        loginValid = false;

                    } else {
                        passwordField.setError(null);
                    }

                    if (loginValid) {
                        //proceed with login
                        mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(
                                this@LoginActivity
                            ) { task ->
                                if (task.isSuccessful) {
                                    // Sign in success, update UI with the signed-in user's information
                                    val intent = Intent(this@LoginActivity, ListBookActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                    // FirebaseUser user = mAuth.getCurrentUser();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Snackbar.make(
                                        v,
                                        "Mauvais login / mot de passe",
                                        Snackbar.LENGTH_SHORT
                                    ).show()

                                }
                            }
                    }
                }
            })
    }

    private fun isEmailValid(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email)
            .matches()
    }

}