package com.example.food_xyz.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.food_xyz.R
import com.example.food_xyz.model.Barang

class InvoiceAdapter(private val context: Context, private val dataset: List<Barang>): RecyclerView.Adapter<InvoiceAdapter.InvoiceViewHolder>() {

    class InvoiceViewHolder(private val view: View) : RecyclerView.ViewHolder(view){
        val tvNamaInvoice = view.findViewById<TextView>(R.id.tv_nama_invoice)
        val tvHargaInvoice = view.findViewById<TextView>(R.id.tv_harga_invoice)
        val tvQtyInvoice = view.findViewById<TextView>(R.id.tv_qty_invoice)
        val tvSubtotalInvoice = view.findViewById<TextView>(R.id.tv_subtotal_invoice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InvoiceViewHolder {
        val invoiceView = LayoutInflater.from(parent.context).inflate(R.layout.list_invoice,parent,false)
        return InvoiceViewHolder(invoiceView)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: InvoiceViewHolder, position: Int) {
        val data = dataset[position]

        holder.tvNamaInvoice.text = data.namaBarang
        holder.tvHargaInvoice.text = data.hargaSatuan
        holder.tvQtyInvoice.text = data.jumlahBarangYangDibeli.toString()
        holder.tvSubtotalInvoice.text = ((data.hargaSatuan.toInt() * data.jumlahBarangYangDibeli).toString())
    }

}