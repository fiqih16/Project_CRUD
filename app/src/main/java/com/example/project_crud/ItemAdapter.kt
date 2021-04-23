package com.example.project_crud

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_row.view.*

class ItemAdapter(val context: Context, val items: ArrayList<EmpModel>) : RecyclerView.Adapter<ItemAdapter.ViewHolder>(){
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val linlay = view.Linlay
        val tvnama = view.Tv_nama
//        val tvemail = view.Tv_email
        val ivEdit = view.iv_edit
        val ivDelete = view.iv_delete
        val ivDetail = view.iv_detail

//        val tvphone = view.Tv_Phone
//        val tvalamat = view.Tv_Alamat

    }

    // method untuk membuat view holder
    // inflate = memunculkan data
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_row, parent, false
            )
        )
    }

    // memasukkan data ke view holder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items.get(position)
        holder.tvnama.text = item.nama
//        holder.tvemail.text = item.email
//
//        holder.tvphone.text = item.phone
//        holder.tvalamat.text = item.alamat

        if(position % 2 == 0) {
            holder.linlay.setBackgroundColor(ContextCompat.getColor(context, R.color.kuninggelap))
        }else{
            holder.linlay.setBackgroundColor(ContextCompat.getColor(context,R.color.Abu))
        }
        holder.ivDelete.setOnClickListener{
            if (context is MainActivity)
            context.deleteRecordAlertDialog(item)
        }
        holder.ivEdit.setOnClickListener{
            if(context is MainActivity){
                context.updateRecordDialog(item)
            }
        }
        holder.ivDetail.setOnClickListener{
            if (context is MainActivity) {
                context.showDetail(item)
            }
        }


    }


    override fun getItemCount(): Int {
        return items.size
    }
}