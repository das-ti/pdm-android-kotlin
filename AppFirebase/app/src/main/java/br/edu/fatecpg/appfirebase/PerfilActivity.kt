package br.edu.fatecpg.appfirebase

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException

class PerfilActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var progress: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        auth = FirebaseAuth.getInstance()
        progress = findViewById(R.id.progressPerfil)

        val usuario = auth.currentUser
        if (usuario == null) {
            voltarParaLogin()
            return
        }

        findViewById<TextView>(R.id.txtEmailUsuario).text = "E-mail: ${usuario.email}"
        findViewById<TextView>(R.id.txtUid).text = "UID: ${usuario.uid}"

        findViewById<MaterialButton>(R.id.btnSair).setOnClickListener {
            auth.signOut()
            Toast.makeText(this, "Você saiu da conta.", Toast.LENGTH_SHORT).show()
            voltarParaLogin()
        }

        findViewById<MaterialButton>(R.id.btnExcluirConta).setOnClickListener {
            confirmarExclusao()
        }
    }

    // ---------- 4) EXCLUIR A PRÓPRIA CONTA ----------
    private fun confirmarExclusao() {
        AlertDialog.Builder(this)
            .setTitle("Excluir conta")
            .setMessage("Tem certeza? Esta ação é permanente e não pode ser desfeita.")
            .setPositiveButton("Excluir") { _, _ -> excluirConta() }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun excluirConta() {
        val usuario = auth.currentUser ?: return
        progress.visibility = View.VISIBLE

        usuario.delete().addOnCompleteListener { task ->
            progress.visibility = View.GONE
            if (task.isSuccessful) {
                Toast.makeText(this, "Conta excluída com sucesso.", Toast.LENGTH_LONG).show()
                voltarParaLogin()
            } else if (task.exception is FirebaseAuthRecentLoginRequiredException) {
                // O Firebase exige login recente para excluir: pede a senha e reautentica
                pedirSenhaEReautenticar()
            } else {
                Toast.makeText(
                    this,
                    "Erro ao excluir: ${task.exception?.localizedMessage}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun pedirSenhaEReautenticar() {
        val usuario = auth.currentUser ?: return
        val campoSenha = EditText(this).apply {
            hint = "Sua senha"
            inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            setPadding(48, 32, 48, 32)
        }

        AlertDialog.Builder(this)
            .setTitle("Confirme sua senha")
            .setMessage("Por segurança, digite sua senha novamente para excluir a conta.")
            .setView(campoSenha)
            .setPositiveButton("Confirmar") { _, _ ->
                val senha = campoSenha.text.toString()
                if (senha.isEmpty() || usuario.email == null) return@setPositiveButton

                val credencial = EmailAuthProvider.getCredential(usuario.email!!, senha)
                progress.visibility = View.VISIBLE
                usuario.reauthenticate(credencial).addOnCompleteListener { reauth ->
                    progress.visibility = View.GONE
                    if (reauth.isSuccessful) {
                        excluirConta()
                    } else {
                        Toast.makeText(this, "Senha incorreta.", Toast.LENGTH_LONG).show()
                    }
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun voltarParaLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
