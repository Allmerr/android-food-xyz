package com.example.food_xyz.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.food_xyz.R
import com.example.food_xyz.model.Barang

class BarangAdapter(private val context: Context, private var dataset: List<Barang>): RecyclerView.Adapter<BarangAdapter.BarangViewHolder>() {

    private var selectedItems = mutableMapOf<Barang,Int>()

    class BarangViewHolder(private val view: View): RecyclerView.ViewHolder(view){
        val tvNamaBarang = view.findViewById<TextView>(R.id.tv_nama_barang)
        val tvHargaBarang = view.findViewById<TextView>(R.id.tv_harga_barang)
        val btnTambahBarang = view.findViewById<Button>(R.id.btn_tambah_barang)
        val btnKurangBarang = view.findViewById<Button>(R.id.btn_kurang_barang)
        val tvJumlahDibeli = view.findViewById<TextView>(R.id.tv_jumlah_dibeli)
        val btnAddToCart = view.findViewById<ImageView>(R.id.btn_add_to_cart)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BarangViewHolder {
        val barangView = LayoutInflater.from(parent.context).inflate(R.layout.list_barang,parent,false)
        return BarangViewHolder(barangView)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: BarangViewHolder, position: Int) {
        val data = dataset[position]

        holder.tvNamaBarang.text = data.namaBarang
        holder.tvHargaBarang.text = data.hargaSatuan

        val harga = data.hargaSatuan.toInt()

        holder.btnTambahBarang.setOnClickListener {
            holder.tvJumlahDibeli.text = dataset[position].jumlahBarangYangDibeli.toString()
            dataset[position].jumlahBarangYangDibeli += 1
            selectedItems[data] = dataset[position].jumlahBarangYangDibeli
            //notifyItemChanged(position)
        }

        holder.btnKurangBarang.setOnClickListener {
            dataset[position].jumlahBarangYangDibeli -= 1

            if(dataset[position].jumlahBarangYangDibeli < 0){
                dataset[position].jumlahBarangYangDibeli = 0
            }
            holder.tvJumlahDibeli.text = dataset[position].jumlahBarangYangDibeli.toString()
            selectedItems[data] = dataset[position].jumlahBarangYangDibeli
            //notifyItemChanged(position)
        }

        holder.btnAddToCart.setOnClickListener {
            Toast.makeText(context,(harga * dataset[position].jumlahBarangYangDibeli).toString(), Toast.LENGTH_LONG).show()
        }

    }

    public fun getSelectedItem(): List<Barang> = selectedItems.filterValues{it>0}.keys.toList()
    public fun getTotalPrice(): Int = selectedItems.map { it.key.hargaSatuan.toInt() * it.value }.sum()

}