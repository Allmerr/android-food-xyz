package com.example.food_xyz

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.food_xyz.adapter.InvoiceAdapter
import com.example.food_xyz.model.Barang

class InvoiceActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.invoice_activity)

        var idUser = intent?.getStringExtra("id_user")?.toString()
//        var BarangArray = intent?.getSerializableExtra("DataSet", InvoiceActivity::class.java) as Array<Barang>

//        findViewById<RecyclerView>(R.id.rv_invoice).adapter = InvoiceAdapter(this,)
    }
}