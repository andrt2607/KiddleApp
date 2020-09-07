package com.example.kiddleapp.Murid

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kiddleapp.Murid.Model.Murid
import com.example.kiddleapp.R
import com.example.kiddleapp.Rapor.EditRaporActivity
import com.example.kiddleapp.RekapPresensi.RekapPresensiActivity
import kotlinx.android.synthetic.main.activity_detail__murid.*

class DetailMuridActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail__murid)

        //mendapatkan data dari intent
        val data = intent.getParcelableExtra<Murid>("data")

        img_back_murid.setOnClickListener {
            onBackPressed()
        }

        //isi value dari halaman sebelumnya
        img_avatar_detail_murid.setImageResource(data.avatar)
        tv_murid_nama.text = data.nama
        tv_murid_nomor.text = data.nomor
        tv_murid_kelas.text = data.kelas
        tv_murid_ttl.text = data.ttl
        tv_murid_alamat.text = data.alamat
        tv_murid_ayah.text = data.ayah
        tv_kontak_ayah.text = data.kontakAyah
        tv_murid_ibu.text = data.ibu
        tv_kontak_ibu.text = data.kontakIbu
        tv_murid_password.text = data.password

        //intent untuk manggil ortu
        btn_telepon_ayah.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:" + tv_kontak_ayah.text)
            startActivity(intent)
        }
        btn_telepon_ibu.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:" + tv_kontak_ibu.text)
            startActivity(intent)
        }

        //buat nampilin menu
        menu_murid.setOnClickListener {
            val popup: PopupMenu = PopupMenu(this, it)
            popup.setOnMenuItemClickListener {
                if(it.itemId == R.id.menu_edit) {
                    val intent = Intent(this@DetailMuridActivity, EditMuridActivity::class.java)

                    //assign value ke editView nanti. kurang gambar
                    intent.putExtra("jenis", "EDIT_PROFIL")
                    intent.putExtra("nama_murid", tv_murid_nama.text.toString())
                    intent.putExtra("nomor_murid", tv_murid_nomor.text.toString())
                    intent.putExtra("kelas_murid", tv_murid_kelas.text.toString())
                    intent.putExtra("ttl_murid", tv_murid_ttl.text.toString())
                    intent.putExtra("alamat_murid", tv_murid_alamat.text.toString())
                    intent.putExtra("ayah_murid", tv_murid_ayah.text.toString())
                    intent.putExtra("kontak_ayah", tv_kontak_ayah.text.toString())
                    intent.putExtra("ibu_murid", tv_murid_ibu.text.toString())
                    intent.putExtra("kontak_ibu", tv_kontak_ibu.text.toString())
                    intent.putExtra("password_murid", tv_murid_password.text.toString())
                    startActivity(intent)

                    return@setOnMenuItemClickListener true
                } else if(it.itemId == R.id.menu_hapus) {
                    Toast.makeText(this, "Hapus", Toast.LENGTH_SHORT).show()
                    return@setOnMenuItemClickListener true
                }
                return@setOnMenuItemClickListener false
            }
            popup.inflate(R.menu.menu_edit_hapus)
            popup.show()
        }

        //intent ke halaman rekap presensi
        btn_rekap_presensi.setOnClickListener {
            val intent: Intent = Intent(this@DetailMuridActivity, RekapPresensiActivity::class.java)
            startActivity(intent)
        }

        //intent ke halaman rapor
        btn_lihat_rapor.setOnClickListener {
            val intent: Intent =
                Intent(this@DetailMuridActivity, EditRaporActivity::class.java).putExtra(
                    "data",
                    data
                )
            startActivity(intent)
        }
    }
}