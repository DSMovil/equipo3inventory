package com.example.inventoryapp.view

import android.app.PendingIntent
import android.content.Intent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import com.example.inventoryapp.R

import android.content.SharedPreferences


class Mi_widget : AppWidgetProvider() {

    companion object {
        private const val PREFS_NAME = "InventoryWidgetPrefs"
        private const val PREF_EYE_OPEN = "eyeOpen"

        internal fun updateAppWidget(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetId: Int,
            isEyeOpen: Boolean
        ) {
            val views = RemoteViews(context.packageName, R.layout.widget_layout)

            // Cambiar la imagen del ojo
            val eyeDrawable = if (isEyeOpen) R.drawable.eye_open else R.drawable.eye_closed
            views.setImageViewResource(R.id.eyeIcon, eyeDrawable)

            // Configurar el PendingIntent para manejar clics en el icono del ojo
            val intent = Intent(context, Mi_widget::class.java)
            intent.action = "TOGGLE_EYE"
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                appWidgetId,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            views.setOnClickPendingIntent(R.id.eyeIcon, pendingIntent)

            // Actualizar el widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)

        if (intent.action == "TOGGLE_EYE") {
            // Acción de clic en el icono del ojo
            val appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, 0)

            // Obtener el estado actual del ojo desde SharedPreferences
            val isEyeOpen = isEyeOpen(context)

            // Cambiar el estado del ojo
            setEyeOpen(context, !isEyeOpen)

            // Actualizar el widget con el nuevo estado del ojo
            updateAppWidget(context, AppWidgetManager.getInstance(context), appWidgetId, !isEyeOpen)

            // Aquí también puedes realizar otras acciones relacionadas con el clic del ojo
        }
    }

    private fun isEyeOpen(context: Context): Boolean {
        val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, 0)
        return prefs.getBoolean(PREF_EYE_OPEN, true) // Valor predeterminado: ojo abierto
    }

    private fun setEyeOpen(context: Context, isEyeOpen: Boolean) {
        val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, 0)
        prefs.edit().putBoolean(PREF_EYE_OPEN, isEyeOpen).apply()
    }
}

