package com.bogdan.motivation.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.bogdan.motivation.R
import com.bogdan.motivation.data.repositories.RepositoryProvider
import com.bogdan.motivation.helpers.Constants

class AppWidget : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(
                context,
                appWidgetManager,
                appWidgetId
            )
        }
    }

    override fun onReceive(context: Context, intent: Intent?) {
        if (Constants.REFRESH_TEXT == intent?.action) {
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
    val intent = Intent(context, AppWidget::class.java)
    intent.action = Constants.REFRESH_TEXT
    intent.putExtra("appWidgetId", appWidgetId)
    val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
    val views = RemoteViews(
        context.packageName,
        R.layout.widget_app
    )
    views.setTextViewText(
        R.id.tv_appwidget,
        RepositoryProvider.quotesRepository.getRandomQuote().quote
    )
    views.setOnClickPendingIntent(R.id.tv_appwidget, pendingIntent)
    appWidgetManager.updateAppWidget(appWidgetId, views)
}