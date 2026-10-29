package br.edu.fatecpg.appfirebase

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

class CadastroActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var edtEmail: EditText
    private lateinit var edtSenha: EditText
    private lateinit var edtConfirmar: EditText
    private lateinit var progress: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        auth = FirebaseAuth.getInstance()

        edtEmail = findViewById(R.id.edtEmailCadastro)
        edtSenha = findViewById(R.id.edtSenhaCadastro)
        edtConfirmar = findViewById(R.id.edtConfirmarSenha)
        progress = findViewById(R.id.progressCadastro)

        findViewById<MaterialButton>(R.id.btnCadastrar).setOnClickListener { criarUsuario() }
        findViewById<MaterialButton>(R.id.btnVoltarLogin).setOnClickListener { finish() }
    }

    private fun criarUsuario() {
        val email = edtEmail.text.toString().trim()
        val senha = edtSenha.text.toString()
        val confirmar = edtConfirmar.text.toString()

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edtEmail.error = "Informe um e-mail válido"
            return
        }
        if (senha.length < 6) {
            edtSenha.error = "A senha deve ter pelo menos 6 caracteres"
            return
        }
        if (senha != confirmar) {
            edtConfirmar.error = "As senhas não conferem"
            return
        }

        progress.visibility = View.VISIBLE
        auth.createUserWithEmailAndPassword(email, senha)
            .addOnCompleteListener(this) { task ->
                progress.visibility = View.GONE
                if (task.isSuccessful) {
                    Toast.makeText(this, "Conta criada com sucesso!", Toast.LENGTH_SHORT).show()
                    // Após criar, o Firebase já deixa o usuário logado
                    val intent = Intent(this@CadastroActivity, PerfilActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                } else {
                    val msg = when (task.exception) {
                        is FirebaseAuthUserCollisionException -> "Este e-mail já está cadastrado."
                        is FirebaseAuthWeakPasswordException -> "Senha fraca. Use pelo menos 6 caracteres."
                        else -> "Erro ao cadastrar: ${task.exception?.localizedMessage}"
                    }
                    Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
                }
            }
    }
}