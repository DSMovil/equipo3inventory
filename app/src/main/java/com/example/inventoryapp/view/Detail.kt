package com.example.inventoryapp.view
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Detail : AppCompatActivity() {

    private lateinit var btnEliminar: Button
    private lateinit var btnSumar: Button
    private lateinit var txtProducto: TextView
    private lateinit var txtPrecioUnidad: TextView
    private lateinit var txtCantidadDisponible: TextView
    private lateinit var txtTotal: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        btnEliminar = findViewById(R.id.btnEliminar)
        btnSumar = findViewById(R.id.btnSumar)
        txtProducto = findViewById(R.id.txtProducto)
        txtPrecioUnidad = findViewById(R.id.txtPrecioUnidad)
        txtCantidadDisponible = findViewById(R.id.txtCantidadDisponible)
        txtTotal = findViewById(R.id.txtTotal)

        txtProducto.text = "zapatos"
        txtPrecioUnidad.text = "Cantidad Disponible: 256"
        txtCantidadDisponible.text = "Precio Unidad: $ 23,000,00"

        btnSumar.setOnClickListener {
            var cantidad = txtCantidadDisponible.text.toString().toInt()
            cantidad++
            txtCantidadDisponible.text = "Cantidad Disponible: $cantidad"
            actualizarTotal()
        }

        btnEliminar.setOnClickListener {
            var cantidad = txtCantidadDisponible.text.toString().toInt()
            if (cantidad > 0) {
                cantidad--
                txtCantidadDisponible.text = "Cantidad Disponible: $cantidad"
                actualizarTotal()
            }
        }
    }

    private fun actualizarTotal() {
        var cantidad = txtCantidadDisponible.text.toString().toInt()
        var precioUnidad = txtPrecioUnidad.text.toString().toInt()
        var total = cantidad * precioUnidad
        txtTotal.text = "Total: $ $total"
    }
}