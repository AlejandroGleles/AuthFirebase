package br.gonzaga.autenticfirebase

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.gonzaga.autenticfirebase.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private val REQ_ONE_TAP = 2
    private lateinit var auth: FirebaseAuth
    private var binding: ActivityMainBinding? = null

    private lateinit var oneTapClient: SignInClient
    private lateinit var signInRequest: BeginSignInRequest

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQ_ONE_TAP) {
            try {
                val credential = oneTapClient.getSignInCredentialFromIntent(data)
                val idToken = credential.googleIdToken

                if (idToken != null) {
                    Log.d(TAG, "Got ID token")
                    val firebaseAuthCredential = GoogleAuthProvider.getCredential(idToken, null)
                    auth.signInWithCredential(firebaseAuthCredential)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Log.d(TAG, "signInWithCredential: Success")
                                // Navegar para a próxima tela
                                val intent = Intent(this, MainActivityhome::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                Log.w(TAG, "signInWithCredential: Failure", task.exception)
                                Toast.makeText(
                                    this,
                                    "Falha na autenticação: ${task.exception?.message}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                } else {
                    Log.d(TAG, "No ID token")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error retrieving credentials: ${e.localizedMessage}")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        auth = Firebase.auth

        oneTapClient = Identity.getSignInClient(this)
        signInRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(getString(R.string.default_web_client_id))
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()

        binding?.button2?.setOnClickListener {
            val email = binding?.inputemail?.text.toString()
            val password = binding?.inputsenha?.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                // Ocultar o teclado
                hideKeyboard()

                // Mostrar o ProgressBar
                binding?.progressBar?.visibility = View.VISIBLE

                // Autenticar o usuário
                signInWithEmailAndPassword(email, password)
            } else {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            }
        }

        binding?.textView3?.setOnClickListener {
            val intent = Intent(this, MainActivityRegistro::class.java)
            startActivity(intent)
        }

        binding?.googleLoginButton?.setOnClickListener {
            showAuthGoogle()
        }
    }

    private fun showAuthGoogle() {
        oneTapClient.beginSignIn(signInRequest)
            .addOnSuccessListener { result ->
                try {
                    startIntentSenderForResult(
                        result.pendingIntent.intentSender,
                        REQ_ONE_TAP,
                        null,
                        0,
                        0,
                        0
                    )
                } catch (e: IntentSender.SendIntentException) {
                    Log.e(TAG, "Couldn't start One Tap UI: ${e.localizedMessage}")
                }
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Google Sign-In failed: ${e.localizedMessage}")
            }
    }

    private fun signInWithEmailAndPassword(email: String, password: String) {
        // Mostrar o ProgressBar antes de iniciar a requisição
        binding?.progressBar?.visibility = View.VISIBLE

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            // Ocultar o ProgressBar ao finalizar a requisição
            binding?.progressBar?.visibility = View.GONE

            if (task.isSuccessful) {
                // Se a autenticação for bem-sucedida, redirecionar para a próxima tela
                val intent = Intent(this, MainActivityhome::class.java)
                Log.d(TAG, "signInWithEmailAndPassword: Success")
                startActivity(intent)
                finish()
            } else {
                // Caso falhe, exibir um Toast com a mensagem de erro
                Log.e(TAG, "signInWithEmailAndPassword: Failure", task.exception)
                Toast.makeText(
                    this,
                    "Falha na autenticação: ${task.exception?.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }


    /**
     * Método para ocultar o teclado
     */
    private fun hideKeyboard() {
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        currentFocus?.let {
            inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }

    companion object {
        const val TAG = "EmailAndPassword"
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
