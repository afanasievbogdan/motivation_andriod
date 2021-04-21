package com.bogdan.motivation.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.bogdan.motivation.R
import com.bogdan.motivation.db.DBManager

//TODO мы так поняли ты этот класс просто скопировал, так что вернемся к нему позже
/**
 * Implementation of App Widget functionality.
 */
const val REFRESH_TEXT = "REFRESH_TEXT"

class AppWidget : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(
                context,
                appWidgetManager,
                appWidgetId
            )
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    override fun onReceive(context: Context, intent: Intent?) {
        if (REFRESH_TEXT == intent?.action) {
            val appWidgetId = intent.getIntExtra("appWidgetId", 0)
            updateAppWidget(
                context,
                AppWidgetManager.getInstance(context),
                appWidgetId
            )
        }
        super.onReceive(context, intent)
    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {

    val dbManager = DBManager(context)
    dbManager.openDb()
    val intent = Intent(context, AppWidget::class.java)
    intent.action = REFRESH_TEXT
    intent.putExtra("appWidgetId", appWidgetId)
    val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
    val views = RemoteViews(context.packageName,
        R.layout.widget_app
    )
    views.setTextViewText(R.id.appwidget_text, dbManager.readRandomQuoteFromQuotesDb())
    views.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent)
    appWidgetManager.updateAppWidget(appWidgetId, views)
}
