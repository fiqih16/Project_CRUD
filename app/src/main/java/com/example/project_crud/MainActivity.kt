package com.example.project_crud

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.idescout.sql.SqlScoutServer
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_detail.*
import kotlinx.android.synthetic.main.dialog_update.*
import kotlinx.android.synthetic.main.dialog_update.tvCancel
import kotlinx.android.synthetic.main.dialog_update.tvUpdated
import kotlinx.android.synthetic.main.item_row.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        SqlScoutServer.create(this, getPackageName());
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupListOfDataIntoRecyclerView()
        btnR.setOnClickListener{
            addRecord()
            closeKeyboard()
            setupListOfDataIntoRecyclerView()
        }
    }

    private fun addRecord(){

        val name = TextNama.text.toString()
        val email = TextEmail.text.toString()

        val phone = TextPhone.text.toString()
        val alamat = TextAlamat.text.toString()

        val databaseHandler: DatabaseHandler = DatabaseHandler(this)
        if (!name.isEmpty() && !email.isEmpty()){
            val status = databaseHandler.addEmployee(EmpModel(0, name, email, phone, alamat))
            if (status > -1){
                Toast.makeText( this, "Berhasil Menambahkan Record", Toast.LENGTH_SHORT).show()
                TextNama.text.clear()
                TextEmail.text.clear()

                TextPhone.text.clear()
                TextAlamat.text.clear()

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

    // method untuk menampilkan dialog konfirmasi delete
    fun deleteRecordAlertDialog(empModel: EmpModel) {
        val builder = AlertDialog.Builder(this)

        builder.setTitle("Hapus?")
        builder.setMessage("Hapus Data Terpilih?")
        builder.setIcon(android.R.drawable.ic_delete)

        // menampilkan tombol yes
        builder.setPositiveButton("Yes") { dialog: DialogInterface, which ->
            val databaseHandler : DatabaseHandler = DatabaseHandler(this)
            val status = databaseHandler.deleteEmployee(EmpModel(empModel.id,"","","",""))

            if (status > -1){
                Toast.makeText(this, "Berhasil menghapus", Toast.LENGTH_SHORT).show()
                setupListOfDataIntoRecyclerView()
            }

            dialog.dismiss()
        }
        // menampilkan tombol no
        builder.setNegativeButton("No") { dialog: DialogInterface, which ->
            //Toast.makeText(this, "No", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        val alertDialog: AlertDialog = builder.create()
        // menampilkan user menekan tombol yes or no
        alertDialog.setCancelable(false)
        // menampilkan kotak dialog
        alertDialog.show()
    }

    // metode untuk menampilkan dialog pembaruan khusus
    fun updateRecordDialog(empModel: EmpModel){
        val updateDialog = Dialog(this,R.style.Theme_Dialog)

        updateDialog.setCancelable(false)
        updateDialog.setContentView(R.layout.dialog_update)

        updateDialog.etUpdateteName.setText(empModel.nama)
        updateDialog.etUpdateteEmail.setText(empModel.email)

        updateDialog.etUpdatetePhone.setText(empModel.phone)
        updateDialog.etUpdateteAlamat.setText(empModel.alamat)

        updateDialog.tvUpdated.setOnClickListener{
            val name = updateDialog.etUpdateteName.text.toString()
            val email = updateDialog.etUpdateteEmail.text.toString()

            val phone = updateDialog.etUpdatetePhone.text.toString()
            val alamat = updateDialog.etUpdateteAlamat.text.toString()



            // Menghubungkan ke database
            val databaseHandler: DatabaseHandler = DatabaseHandler(this)

            if(!name.isEmpty() && !email.isEmpty() && !phone.isEmpty() && !alamat.isEmpty()){
                val status = databaseHandler.updateEmployee(EmpModel(empModel.id,name,email,phone,alamat))
                if(status > -1){
                    Toast.makeText(this, "Berhasil Edit",Toast.LENGTH_SHORT).show()
                    setupListOfDataIntoRecyclerView()
                    updateDialog.dismiss()
                    closeKeyboard()
                }
            }else{
                Toast.makeText(this, "Nama is email cannot be",Toast.LENGTH_SHORT).show()
            }

            Toast.makeText( this, "Berhasil Menambahkan",Toast.LENGTH_SHORT).show()
        }
        updateDialog.tvCancel.setOnClickListener{
            updateDialog.dismiss()
        }

        updateDialog.show()
        closeKeyboard()
    }

    fun showDetail(empModel: EmpModel){
        val showdetail = Dialog(this,R.style.Theme_Dialog)

        showdetail.setCancelable(false)
        showdetail.setContentView(R.layout.dialog_detail)

        showdetail.etUpdateteNameMore.setText(empModel.nama)
        showdetail.etUpdateteEmailMore.setText(empModel.email)

        showdetail.etUpdatetePhoneMore.setText(empModel.phone)
        showdetail.etUpdateteAlamatMore.setText(empModel.alamat)

        showdetail.tvSelesai.setOnClickListener{
            showdetail.dismiss()
        }
        showdetail.show()
    }


    // method to close keyboard
    private fun closeKeyboard(){
        val view = this.currentFocus
        if (view!= null){
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken,0)
        }
    }

}