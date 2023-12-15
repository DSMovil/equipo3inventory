package com.example.inventoryapp.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import com.example.inventoryapp.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class Detail : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var cardViewProducto: CardView
    private lateinit var btnEliminar: Button
    private lateinit var btnEditar: FloatingActionButton

    private lateinit var txtNombreProducto: TextView
    private lateinit var txtPrecioUnidad: TextView
    private lateinit var txtCantidadDisponible: TextView
    private lateinit var txtTotal: TextView

    private lateinit var producto: Producto

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        toolbar = findViewById(R.id.toolbar)
        cardViewProducto = findViewById(R.id.cardViewProducto)
        btnEliminar = findViewById(R.id.btnEliminar)
        btnEditar = findViewById(R.id.btnEditar)
/*
        txtNombreProducto = findViewById(R.id.txtNombreProducto)
        txtPrecioUnidad = findViewById(R.id.txtPrecioUnidad)
        txtCantidadDisponible = findViewById(R.id.txtCantidadDisponible)
        txtTotal = findViewById(R.id.txtTotal)

 */

        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = "Detalle del producto"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        // Obtén el producto seleccionado de la lista (puedes pasarlo a través de Intent)
        producto = obtenerProductoSeleccionado()

        // Muestra los detalles del producto en las TextViews
        mostrarDetallesProducto()

        // Botón Eliminar
        btnEliminar.setOnClickListener {
            // Aquí debes implementar la lógica para eliminar el producto de la base de datos
            // y luego volver a la ventana principal (Ventana Home Inventario)
            eliminarProducto()
        }

        // Botón Editar
        btnEditar.setOnClickListener {
            // Aquí debes implementar la lógica para dirigir al usuario a la ventana de edición (HU:6.0)
            // Puedes utilizar un Intent similar al que se usa en MainActivity al hacer clic en un ítem de la lista
            abrirVentanaEdicion()
        }
    }

    private fun obtenerProductoSeleccionado(): Producto {
        // Aquí debes implementar la lógica para obtener el producto seleccionado de la lista.
        // Esto podría involucrar pasar el producto como parámetro a través de Intent.
        // Por ahora, devolveré un producto ficticio.
        return Producto(10.0, 12345, "Producto de ejemplo", 100.0)
    }

    private fun mostrarDetallesProducto() {
        txtNombreProducto.text = producto.nombre
        txtPrecioUnidad.text = "Precio Unidad: $${producto.precio}"
        txtCantidadDisponible.text = "Cantidad Disponible: ${producto.cantidad}"
        actualizarTotal(producto.precio, producto.cantidad)
    }

    private fun actualizarTotal(precioUnidad: Double, cantidad: Double) {
        val total = precioUnidad * cantidad
        txtTotal.text = "Total: $$total"
    }

    private fun eliminarProducto() {        // Aquí debes implementar la lógica para eliminar el producto de la base de datos.
        // Luego, vuelve a la ventana principal (Ventana Home Inventario).
        // Puedes usar un Intent similar al que se usa en MainActivity para iniciar la nueva actividad.
        // ¡Recuerda actualizar la lista en la ventana principal después de eliminar el producto!
        Toast.makeText(this, "Producto eliminado", Toast.LENGTH_SHORT).show()
        // Implementa aquí la lógica para volver a la ventana principal
    }

    private fun abrirVentanaEdicion() {

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()  // Vuelve atrás cuando se presiona la flecha de la barra de herramientas
        return true
    }
}
