package br.gonzaga.autenticfirebase

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.gonzaga.autenticfirebase.MainActivity.Companion.TAG
import br.gonzaga.autenticfirebase.databinding.ActivityMainRegistroBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivityRegistro : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private var binding: ActivityMainRegistroBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainRegistroBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        // Inicializar Firebase Auth
        auth = Firebase.auth

        // Configurar o botão de registro
        binding?.registerButton?.setOnClickListener {
            val email = binding?.criaemail?.text.toString()
            val password = binding?.criasenha?.text.toString()
            val confirmPassword = binding?.confirmasenha?.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
                if (password == confirmPassword) {
                    createUserWithEmailAndPassword(email, password)
                } else {
                    Toast.makeText(this, "As senhas não coincidem", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            }
        }

        // Configurar o botão para voltar ao login
        binding?.backToLogin?.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun createUserWithEmailAndPassword(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "createUserWithEmailAndPassword: Success")
                    Toast.makeText(this, "Usuário criado com sucesso!", Toast.LENGTH_SHORT).show()

                    // Redirecionar para a tela de login
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish() // Impede que o usuário volte para esta tela ao pressionar "voltar"
                } else {
                    Log.d(TAG, "createUserWithEmailAndPassword: Failure", task.exception)
                    Toast.makeText(
                        this,
                        "Erro ao criar usuário: ${task.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
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
