package com.example.kiddleapp

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.kiddleapp.model.Model_Jurnal
import com.example.kiddleapp.model.Model_Tugas
import kotlinx.android.synthetic.main.activity_detail_tugas.*
import kotlinx.android.synthetic.main.activity_edit_jurnal.*
import kotlinx.android.synthetic.main.activity_edit_tugas.*

import java.util.*
import java.text.SimpleDateFormat as SimpleDateFormat1


class Edit_Jurnal : AppCompatActivity() {

    private val PERMISSION_CODE = 1000;
    private val IMAGE_CAPTURE_CODE = 1001
    var image_uri: Uri? = null


    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_jurnal)

        if(intent.getStringExtra("jenis") == "EDIT_JURNAL") {
            //mengambil data dari halaman sebelumnya
            val data = intent.getParcelableExtra<Model_Jurnal>("data")
            tv_kelas_edit_jurnal.setText(data.kelas)
            tv_deskripsi_edit_jurnal.setText(data.isi)
            tv_aspek_edit_jurnal.setText(data.jenis)
            tv_judul_edit_jurnal.setText(data.judul)

            if (data.gambar != 0) {
                img_edit_jurnal.setVisibility(View.VISIBLE);
                vv_edit_jurnal.setVisibility(View.GONE);
                btn_tutup_edit_jurnal.setVisibility(View.VISIBLE);
                img_edit_jurnal.setImageResource(data.gambar)
            } else if (data.video != 0) {
                vv_edit_jurnal.setVisibility(View.VISIBLE);
                img_edit_jurnal.setVisibility(View.GONE);
                vv_edit_jurnal.setVideoURI(Uri.parse("android.resource://" + packageName + "/" + data.video))
                var media_Controller: MediaController = MediaController(this)
                vv_edit_jurnal.setMediaController(media_Controller)
                media_Controller.setAnchorView(vv_edit_jurnal)
                vv_edit_jurnal.seekTo(10);
                btn_tutup_edit_jurnal.setVisibility(View.VISIBLE);

            }
        }

        //tutup gambar
       // hapus video belum bisa
        btn_tutup_edit_jurnal.setOnClickListener {
            img_edit_jurnal.setImageResource(0)
            img_edit_jurnal.setVisibility(View.GONE);
            btn_tutup_edit_jurnal.setVisibility(View.GONE);
        }

        //kembali
        img_back_edit_jurnal.setOnClickListener {
            onBackPressed()
        }


        // simpan
        btn_simpan_edit_jurnal.setOnClickListener {
            Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show()
        }


        //untuk kelas
        val items = listOf("Bintang Kecil", "Bintang Besar", "Bulan Kecil", "Bulan Besar")
        val adapter = ArrayAdapter(this, R.layout.dropdown_text, items)
        (dropdown_kelas_edit_jurnal.editText as? AutoCompleteTextView)?.setAdapter(adapter)


        //untuk aspek
        val items2 = listOf("Keterampilan", "Motorik", "Kognitif", "Agama", "Berbahasa")
        val adapter2 = ArrayAdapter(this, R.layout.dropdown_text, items2)
        (dropdown_edit_jurnal_aspek.editText as? AutoCompleteTextView)?.setAdapter(adapter2)

        //galeri
        img_gambar_edit_jurnal.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            intent.type = "image/* video/*"
            startActivityForResult(intent, 1)
        }

        //kamera
        img_kamera_edit_jurnal.setOnClickListener {
            //if system os is Marshmallow or Above, we need to request runtime permission
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_DENIED ||
                    checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED
                ) {
                    //permission was not enabled
                    val permission = arrayOf(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                    //show popup to request permission
                    requestPermissions(permission, PERMISSION_CODE)
                } else {
                    //permission already granted
                    openCamera()
                }
            } else {
                //system os is < marshmallow
                openCamera()
            }
        }
    }

    //kamera
    private fun getFileExtension(imgLocation: Uri): String? {
        val contentResolver = contentResolver
        val mimeTypeMap = MimeTypeMap.getSingleton()
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(imgLocation))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //ambil gambar atau video galeri
        if (resultCode == Activity.RESULT_OK && requestCode == 1) {
            if (data?.data.toString().contains("image")) {
                img_edit_jurnal.setVisibility(View.VISIBLE);
                vv_edit_jurnal.setVisibility(View.GONE);
                img_edit_jurnal.setImageURI(data?.data)

            } else if (data?.data.toString().contains("video")) {
                vv_edit_jurnal.setVisibility(View.VISIBLE);
                img_edit_jurnal.setVisibility(View.GONE);
                vv_edit_jurnal.setVideoURI(data?.data)
                var media_Controller: MediaController = MediaController(this)
                vv_edit_jurnal.setMediaController(media_Controller)
                media_Controller.setAnchorView(vv_edit_jurnal)
                vv_edit_jurnal.seekTo(10);
            }
        }

        //ambil gambar kamera
        else if (resultCode == Activity.RESULT_OK) {
            //set image captured to image view
            img_edit_jurnal.setVisibility(View.VISIBLE);
            vv_edit_jurnal.setVisibility(View.GONE);
            img_edit_jurnal.setImageURI(image_uri)
        }

        btn_tutup_edit_jurnal.setVisibility(View.VISIBLE);
    }

    //kamera
    private fun openCamera() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")
        //camera intent
        image_uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri)
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE)
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        //called when user presses ALLOW or DENY from Permission Request Popup
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    //permission from popup was granted
                    openCamera()
                } else {
                    //permission from popup was denied
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }



    }
}