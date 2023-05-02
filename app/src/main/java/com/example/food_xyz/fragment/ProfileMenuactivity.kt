package com.example.food_xyz.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.food_xyz.BaseUrl
import com.example.food_xyz.LoginActivity
import com.example.food_xyz.R

class ProfileMenuactivity: Fragment(R.layout.profile_menu_activity) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(layoutInflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = arguments
        val idUser = bundle?.getString("id_user").toString()

        setUpProfile(view,idUser)

        view.findViewById<TextView>(R.id.tv_logout).setOnClickListener {
            val intent = Intent(view.context,LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    private fun setUpProfile(view: View, idUser : String) {
        val baseUrl = BaseUrl().BASEURL
        val queue = Volley.newRequestQueue(view.context)
        val url = "$baseUrl/User?id_user=$idUser"
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null, {response ->
            val jsonArrayResponse = response.getJSONObject("data")

            view.findViewById<TextView>(R.id.tv_name_profile).text = jsonArrayResponse.getString("Nama")
            view.findViewById<TextView>(R.id.tv_alamat_profile).text =  jsonArrayResponse.getString("Alamat")
            view.findViewById<TextView>(R.id.tv_phone_profile).text =  jsonArrayResponse.getString("Telpon")
            view.findViewById<ImageView>(R.id.iv_image_profile).setImageResource(R.drawable.ic_person)

        },{

        })
        queue.add(jsonObjectRequest)
    }
}