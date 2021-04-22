package com.example.project_crud

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.idescout.sql.SqlScoutServer
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        SqlScoutServer.create(this, getPackageName());
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupListOfDataIntoRecyclerView()
        btnR.setOnClickListener{
            addRecord()
            setupListOfDataIntoRecyclerView()
        }
    }

    private fun addRecord(){

        val name = TextNama.text.toString()
        val email = TextEmail.text.toString()
        val databaseHandler: DatabaseHandler = DatabaseHandler(this)
        if (!name.isEmpty() && !email.isEmpty()){
            val status = databaseHandler.addEmployee(EmpModel(0, name, email))
            if (status > -1){
                Toast.makeText( this, "Record Saved", Toast.LENGTH_SHORT).show()
                TextNama.text.clear()
                TextEmail.text.clear()
            }
        }else{
            Toast.makeText( this,"Masukkan Nama dan email anda", Toast.LENGTH_SHORT).show()
        }
    }

    // method untuk mendapatkan jumlah record
    private fun getItemList(): ArrayList<EmpModel>{
        val databaseHandler: DatabaseHandler = DatabaseHandler(this)
        val empList: ArrayList<EmpModel> = databaseHandler.viewEmployee()
        return empList
    }


    // method untuk menampilkan emplist ke recycler view
    private fun  setupListOfDataIntoRecyclerView(){
        if (getItemList().size > 0){
            rv_item.visibility = View.VISIBLE
            TV_No_record.visibility = View.GONE

            rv_item.layoutManager = LinearLayoutManager(this)
            rv_item.adapter =  ItemAdapter(this,getItemList())
        }else{
            rv_item.visibility = View.GONE
            TV_No_record.visibility = View.VISIBLE
        }
    }
}