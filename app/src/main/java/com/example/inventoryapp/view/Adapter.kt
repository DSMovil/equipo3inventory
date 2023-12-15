package com.example.inventoryapp.view

import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.inventoryapp.R

class Adapter (private val productList: ArrayList<Producto>, val listener: MyClickListener) : RecyclerView.Adapter<Adapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Adapter.MyViewHolder {
        val productView = LayoutInflater.from(parent.context).inflate(R.layout.layout_item,parent,false)
        return MyViewHolder(productView)
    }

    override fun onBindViewHolder(holder: Adapter.MyViewHolder, position: Int) {
        val producto : Producto = productList[position]
        holder.producto.text = producto.nombre
        holder.precio.text = producto.precio.toString()
        holder.codigo.text = producto.codigo.toString()
        holder.itemView.setOnClickListener {
            listener.OnClick(producto.nombre)
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    interface MyClickListener{
        fun OnClick(productoNombre: String)
    }

    inner  class  MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val producto : TextView = itemView.findViewById(R.id.productoNombre)
        val precio : TextView = itemView.findViewById(R.id.productoPrecio)
        val codigo : TextView = itemView.findViewById(R.id.productoId)
        init {
            itemView.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val productId = productList[position].nombre
                    listener.OnClick(productId)
                }
            }
        }


    }
}