package br.edu.fatecpg.appfirebase

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var edtEmail: EditText
    private lateinit var edtSenha: EditText
    private lateinit var progress: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        edtEmail = findViewById(R.id.edtEmail)
        edtSenha = findViewById(R.id.edtSenha)
        progress = findViewById(R.id.progress)

        findViewById<MaterialButton>(R.id.btnEntrar).setOnClickListener { logar() }
        findViewById<MaterialButton>(R.id.btnIrCadastro).setOnClickListener {
            startActivity(Intent(this, CadastroActivity::class.java))
        }
        findViewById<TextView>(R.id.txtEsqueciSenha).setOnClickListener { redefinirSenha() }
    }

    override fun onStart() {
        super.onStart()
        // Se o usuário já estiver logado, vai direto para o perfil
        if (auth.currentUser != null) {
            irParaPerfil()
        }
    }

    // ---------- 1) LOGAR ----------
    private fun logar() {
        val email = edtEmail.text.toString().trim()
        val senha = edtSenha.text.toString()

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edtEmail.error = "Informe um e-mail válido"
            return
        }
        if (senha.isEmpty()) {
            edtSenha.error = "Informe a senha"
            return
        }

        carregando(true)
        auth.signInWithEmailAndPassword(email, senha)
            .addOnCompleteListener(this) { task ->
                carregando(false)
                if (task.isSuccessful) {
                    Toast.makeText(this, "Login realizado!", Toast.LENGTH_SHORT).show()
                    irParaPerfil()
                } else {
                    val msg = when (task.exception) {
                        is FirebaseAuthInvalidUserException -> "Usuário não encontrado ou desativado."
                        is FirebaseAuthInvalidCredentialsException -> "E-mail ou senha incorretos."
                        else -> "Erro ao entrar: ${task.exception?.localizedMessage}"
                    }
                    Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun redefinirSenha() {
        val campo = EditText(this).apply {
            hint = "seu@email.com"
            setText(edtEmail.text.toString().trim())
            inputType = android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            setPadding(48, 32, 48, 32)
        }

        AlertDialog.Builder(this)
            .setTitle("Redefinir senha")
            .setMessage("Enviaremos um link de redefinição para o seu e-mail.")
            .setView(campo)
            .setPositiveButton("Enviar") { _, _ ->
                val email = campo.text.toString().trim()
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(this, "E-mail inválido.", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }
                carregando(true)
                auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        carregando(false)
                        if (task.isSuccessful) {
                            Toast.makeText(
                                this,
                                "E-mail de redefinição enviado para $email. Verifique também o spam.",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            Toast.makeText(
                                this,
                                "Erro: ${task.exception?.localizedMessage}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun irParaPerfil() {
        startActivity(Intent(this, PerfilActivity::class.java))
        finish()
    }

    private fun carregando(ativo: Boolean) {
        progress.visibility = if (ativo) View.VISIBLE else View.GONE
    }
}
