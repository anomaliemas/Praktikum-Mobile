package com.example.humasdirectorycompose

data class Proker(
    val name: String,
    val pj: String,
    val description: String,
    val date: String,
    val photo: Int,
    val link: String
)

object ProkerData {
    val listData = listOf(
        Proker("BENCHMARKING 2026", "Nabilla Putri Nugraha", "Kunjungan dari prodi lain untuk observasi manajemen organisasi sebagai bahan evaluasi dan komparasi HMTI.", "26 April 2026", R.drawable.img_benchmarking, "https://www.instagram.com/nputrinugraha1223"),
        Proker("BAKTI", "Amalia Soraya", "Memberikan keterampilan praktis pemasaran digital bagi pelaku usaha lokal.", "13 - 12 Juli 2026", R.drawable.img_bakti, "https://www.instagram.com/soraawistaria"),
        Proker("TEKNISI", "Muhammad Zaini Hafidz", "Mengoptimalkan citra diri profesional mahasiswa untuk meningkatkan daya saing.", "29-30 September 2026", R.drawable.img_teknisi, "https://www.instagram.com/zoestar.7z"),
        Proker("Workshop Branding", "Nasywa Ardelia Putri", "Memberikan panduan praktis tentang citra diri profesional mahasiswa.", "31 Oktober 2026", R.drawable.img_workshop_branding, "https://www.instagram.com/nasydl"),
        Proker("KOKI", "Muhammad Naufal Khalish, Maula Muhammad Farrel r., & Salma Nur Lathifah", "Program publikasi informasi secara insidental melalui media sosial HMTI.", "Insidental", R.drawable.img_koki, "https://www.instagram.com/khliisshh"),
        Proker("Relasi", "Nazwa Amanda", "Menghadiri undangan eksternal untuk memperluas jejaring networking.", "Insidental", R.drawable.img_relasi, "https://www.instagram.com/nzwaamndaa")
    )
}