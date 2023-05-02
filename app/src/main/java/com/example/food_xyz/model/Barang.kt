package com.example.food_xyz.model

import java.util.Date

data class Barang(
    val idBarang: String,
    val kodeBarang : String,
    val namaBarang: String,
    val jumlahBarang: String,
    val satuan: String,
    val hargaSatuan: String,
    var jumlahBarangYangDibeli: Int,
)