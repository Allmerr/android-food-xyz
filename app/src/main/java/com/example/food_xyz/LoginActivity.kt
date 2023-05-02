package com.example.food_xyz

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        findViewById<Button>(R.id.RegisterButton).setOnClickListener {
            startActivity(Intent(this,DaftarActivity::class.java))
        }

        findViewById<Button>(R.id.loginButton).setOnClickListener {
            val usernameText : EditText = findViewById(R.id.usernameText)
            val passwordText : EditText = findViewById(R.id.passwordText)

            if(usernameText.text.toString() != "" && passwordText.text.toString() != ""){
                GlobalScope.launch {
                    doAuth()
                }
            }else{

                Toast.makeText(this,"Data diri kurang lengkap!", Toast.LENGTH_LONG).show()
            }
        }
    }

    private suspend fun doAuth()  {

        val usernameText : EditText = findViewById(R.id.usernameText)
        val passwordText : EditText = findViewById(R.id.passwordText)

        val queue = Volley.newRequestQueue(this)

        val baseUrl = BaseUrl().BASEURL
        val url = "$baseUrl/Login?username=${usernameText.text.toString()}&password=${passwordText.text.toString()}";
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            {response ->
                Toast.makeText(this,response.toString(), Toast.LENGTH_LONG).show()

                val statusCode = response["status_code"].toString()
                val message = response["message"].toString()
                val jsonObjectResponse = response.getJSONObject("data")
                val idUser = jsonObjectResponse["Id_User"].toString()


                if (statusCode == "200" && message == "success" ){
                    val intent = Intent(this,MenuActivity::class.java)
                    intent.putExtra("id_user", idUser)
                    startActivity(intent)
                }else{
                    Toast.makeText(this,"username atau password salah!", Toast.LENGTH_SHORT).show()
                }
            }, {
                error ->
                findViewById<TextView>(R.id.textView).text = error.toString()
                Toast.makeText(this,error.toString(), Toast.LENGTH_SHORT).show()
            }
        )

        queue.add(jsonObjectRequest)

    }
}