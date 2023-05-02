package com.example.food_xyz.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.food_xyz.BaseUrl
import com.example.food_xyz.LoginActivity
import com.example.food_xyz.R
import com.example.food_xyz.adapter.BarangAdapter
import com.example.food_xyz.model.Barang
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainMenuActivity: Fragment(R.layout.main_menu_activity) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getDataProduct(view)
    }

    private fun getDataProduct(view: View) {
        val baseUrl = BaseUrl().BASEURL
        val queue = Volley.newRequestQueue(view.context)
        val url = "$baseUrl/Barang?search="
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null, { response ->
            val jsonArrayResponse = response.getJSONArray("data")

            val barangData : MutableList<Barang> = mutableListOf()

            for ( barang in 0 until jsonArrayResponse.length() ){
                var barang = jsonArrayResponse.getJSONObject(barang)

                val idBarang = barang["Id_Barang"].toString()
                val kodeBarang = barang["Kode_Barang"].toString()
                val namaBarang = barang["Nama_Barang"].toString()
                val jumlahBarang = barang["Jumlah_Barang"].toString()
                val satuan = barang["Satuan"].toString()
                val hargaSatuan = barang["Harga_Satuan"].toString()

                val barangModel = Barang(idBarang,kodeBarang, namaBarang , jumlahBarang,satuan,hargaSatuan, 0)
                barangData.add(barangModel)
            }

            var adapterBarang = BarangAdapter(view.context, barangData)
            var recyclerView = view.findViewById<RecyclerView>(R.id.rv_barang)
            recyclerView.adapter = adapterBarang
            recyclerView.setOnClickListener {
                Toast.makeText(view.context,it.toString(),Toast.LENGTH_LONG).show()
            }

            view.findViewById<Button>(R.id.btn_bayar_barang).setOnClickListener {
                Toast.makeText(view.context,"ii",Toast.LENGTH_LONG).show()

                var ii = 0
                adapterBarang.getSelectedItem().forEach {
                        val newQueue = Volley.newRequestQueue(view.context)
                        val newUrl = "$baseUrl/Transaksi?totalBarangDibeli=${it.jumlahBarangYangDibeli}&totalBayar=${it.jumlahBarangYangDibeli * it.hargaSatuan.toInt()}&idUser={-}&idBarang=${it.idBarang}"
                        val jsonObjectRequestnew = JsonObjectRequest(Request.Method.GET, newUrl, null, {newResponse ->
                            if(newResponse["message"] == "success" && newResponse["status_code"] == "200"){
                                ii++
                                Toast.makeText(view.context,ii,Toast.LENGTH_LONG).show()
                            }
                        }, {
                            Toast.makeText(view.context,ii,Toast.LENGTH_LONG).show()
                        })
                        newQueue.add(jsonObjectRequestnew)
                    Toast.makeText(view.context,"xx",Toast.LENGTH_LONG).show()

                }
            }

        },{

        })
        queue.add(jsonObjectRequest)


    }



}