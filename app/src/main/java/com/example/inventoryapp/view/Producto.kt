package com.example.inventoryapp.view

import com.google.firebase.firestore.PropertyName

data class Producto(
    @get:PropertyName("cantidad") @set:PropertyName("cantidad") var cantidad: Double = 0.00,
    @get:PropertyName("codigo") @set:PropertyName("codigo") var codigo: Long = 0,
    @get:PropertyName("nombre") @set:PropertyName("nombre") var nombre: String = "",
    @get:PropertyName("precio") @set:PropertyName("precio") var precio: Double = 0.0,
) {
    // Constructor sin argumentos necesario para la deserialización de Firestore
    constructor() : this(0.0, 0, "", 0.0)
}
