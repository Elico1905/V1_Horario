package com.example.horario

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {

    private val GOOGLE_SING_IN = 100
    private val bd = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(R.layout.activity_login)
        leerPrefs()
        cardView_google.setOnClickListener {
            val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build()
            val googleClient = GoogleSignIn.getClient(this, googleConf)
            googleClient.signOut()
            startActivityForResult(googleClient.signInIntent, GOOGLE_SING_IN)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GOOGLE_SING_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                if (account != null) {
                    //MostrarAnimacion()
                    val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                    FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener {
                        if (it.isSuccessful) {
                            bd.collection("users").document(account.email ?: "").get().addOnSuccessListener {
                                val email: String = it.get("correo").toString()
                                val nombre: String = it.get("nombre").toString()
                                val apellidos: String = it.get("apellidos").toString()

                                if (email.equals("null") || nombre.equals("null") || apellidos.equals("null")) {
                                    val intent:Intent = Intent(this, Registrar::class.java)
                                    intent.putExtra("correo", account.email ?: "")
                                    intent.putExtra("nombre", account.givenName ?: "")
                                    intent.putExtra("apellidos", account.familyName ?: "")
                                    startActivity(intent)
                                    finish()
                                } else {
                                    val prefs =getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
                                    prefs.putString("correo", email)
                                    prefs.putString("nombre", nombre)
                                    prefs.putString("apellidos", apellidos)
                                    prefs.apply()
                                    val intent: Intent = Intent(this, Home::class.java)
                                    startActivity(intent)
                                    finish()
                                }
                            }
                        } else {
                            //error de que no hay cuenta seleccionada
                        }
                    }

                }
            } catch (e: ApiException) {
            }

        }
    }
    private fun leerPrefs(){
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        val localCorreo: String? = prefs.getString("correo", "2021ff")
        val localNombre: String? = prefs.getString("nombre", "2021ff")
        val localApellidos: String? = prefs.getString("apellidos", "2021ff")
        if (!localCorreo.equals("2021ff") || !localNombre.equals("2021ff") || !localApellidos.equals("2021ff")){

            val intent: Intent = Intent(this, Home::class.java)
            startActivity(intent)
            finish()
        }
    }
}