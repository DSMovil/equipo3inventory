package com.example.inventoryapp.view


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
    }

    inner class WidgetUtils {
        fun isEyeOpen(context: Context): Boolean {
            val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, 0)
            return prefs.getBoolean(PREF_EYE_OPEN, true)
        }

        fun setEyeOpen(context: Context, isEyeOpen: Boolean) {
            val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, 0)
            prefs.edit().putBoolean(PREF_EYE_OPEN, isEyeOpen).apply()
        }

        fun updateAppWidget(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetId: Int,
            isEyeOpen: Boolean
        ) {
            val views = RemoteViews(context.packageName, R.layout.widget_layout)

            // Cambiar la imagen del ojo
            val eyeDrawable = if (isEyeOpen) R.drawable.eye_open else R.drawable.eye_closed
            views.setImageViewResource(R.id.eyeIcon, eyeDrawable)

            // Actualizar el widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
    private val widgetUtils = WidgetUtils()

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        for (appWidgetId in appWidgetIds) {
            val isEyeOpen = widgetUtils.isEyeOpen(context)
            widgetUtils.updateAppWidget(context, appWidgetManager, appWidgetId, isEyeOpen)
        }
    }
}


