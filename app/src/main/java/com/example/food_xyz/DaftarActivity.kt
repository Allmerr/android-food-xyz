package com.example.food_xyz

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Request.Method
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DaftarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.daftar_activity)

        findViewById<Button>(R.id.move_login_button).setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        findViewById<Button>(R.id.register_button_daftar).setOnClickListener {
            GlobalScope.launch {
                registerUser();
            }
        }
    }

    private suspend fun registerUser() {
        val nameText: String = findViewById<EditText>(R.id.nama_lengkap_text).text.toString()
        val usernameText: String = findViewById<EditText>(R.id.username_text).text.toString()
        val alamatText: String = findViewById<EditText>(R.id.alamat_text).text.toString()
        val telponText: String = findViewById<EditText>(R.id.telpon_text).text.toString()
        val passwordText: String = findViewById<EditText>(R.id.password_text).text.toString()
        val konfirmasiPasswordText: String =
            findViewById<EditText>(R.id.konfirmasi_password_text).text.toString()

        if (konfirmasiPasswordText != passwordText) {
            return Toast.makeText(
                this,
                "Confirm Password Fail",
                Toast.LENGTH_LONG
            ).show()
        }

        val queue = Volley.newRequestQueue(this)
        val baseUrl = BaseUrl().BASEURL
        val url =
            "$baseUrl/Register?nama=$nameText&alamat=$usernameText&telpon=$telponText&username=$usernameText&password=$passwordText"

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null, { response ->
            Toast.makeText(this, response.toString(), Toast.LENGTH_LONG).show()

            val statusCode = response["status_code"].toString()
            val message = response["message"].toString()

            if (statusCode == "200" && message == "success") {
                Toast.makeText(this, "Berhasil Mendaftarkan User Baru", Toast.LENGTH_LONG).show()
                startActivity(Intent(this, LoginActivity::class.java))
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            }
        }, { error ->
            Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show()
        })

        queue.add(jsonObjectRequest);
    }
}

