package com.example.kiddleapp.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kiddleapp.Edit_Rapor
import com.example.kiddleapp.R
import com.example.kiddleapp.adapter.Adapter_Murid
import com.example.kiddleapp.model.Model_Murid
import kotlinx.android.synthetic.main.activity_rapor.*
import kotlinx.android.synthetic.main.fragment_murid.*
import kotlinx.android.synthetic.main.fragment_murid.view.*

class Fragment_Murid: Fragment() {

    //untuk menyimpan murid
    private var murid = ArrayList<Model_Murid>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_murid, container, false)

        //untuk dropdown. bisa ganti kelas yang ada di firebase nya untuk dropdown
        val items = listOf("Bintang Kecil", "Bintang Besar", "Bulan Kecil", "Bulan Besar")
        val adapter = ArrayAdapter<String>(activity!!.applicationContext, R.layout.dropdown_text, items)
        (view.dropdown_murid_kelas.editText as? AutoCompleteTextView)?.setAdapter(adapter)

        //recyclerView murid
        view.rv_murid.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        //mengkosongkan isi arraylist
        murid.clear()

        //bisa diganti dengan data dari firebase
        val temp = Model_Murid(R.drawable.avatar, "Lee Ji Eun", "Bintang Besar")
        murid.add(temp)

        val temp2 = Model_Murid(R.drawable.avatar, "IU", "Bintang Kecil")
        murid.add(temp2)

        //agar murid dapat di-click sekaligus mengisi adapter dengan data di arraylist tadi
        view.rv_murid.adapter = Adapter_Murid(murid){
            Toast.makeText(activity, "Clicked " + it.nama, Toast.LENGTH_SHORT).show()
        }

        return view
    }
}