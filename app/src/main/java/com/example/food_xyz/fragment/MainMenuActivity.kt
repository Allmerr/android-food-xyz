package com.example.food_xyz.fragment

import android.content.Context
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

    private suspend fun makeTrasaksi(context : Context, barang: Barang, onSuccess: () -> Unit, onFail: () -> Unit){

        var baseUrl = BaseUrl().BASEURL
        val queue = Volley.newRequestQueue(context)
        val url = "$baseUrl/Transaksi?totalBarangDibeli=${barang.jumlahBarangYangDibeli}&totalBayar=${barang.jumlahBarangYangDibeli * barang.hargaSatuan.toInt()}&idUser=0&idBarang=${barang.idBarang}"
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null, {response ->
            if(response["message"] == "success" && response["status_code"] == "200"){
                onSuccess()
                Toast.makeText(context,"testapi suc",Toast.LENGTH_LONG).show()

            }
        },{
            onFail()

            Toast.makeText(context,"testapi err",Toast.LENGTH_LONG).show()

        })
        queue.add(jsonObjectRequest)
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
                adapterBarang.getSelectedItem().forEach {barang ->
                    GlobalScope.launch {
                        makeTrasaksi(view.context,barang,{
                            Toast.makeText(view.context, "Berhasil", Toast.LENGTH_LONG).show()
                        }, {
                            Toast.makeText(view.context, "Fail", Toast.LENGTH_LONG).show()
                        })
                    }
                }
            }

        },{

        })
        queue.add(jsonObjectRequest)


    }



}