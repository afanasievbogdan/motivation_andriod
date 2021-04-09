package com.bogdan.motivation

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.widget.RemoteViews
import kotlin.random.Random

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
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    override fun onReceive(context: Context, intent: Intent?) {
        if (REFRESH_TEXT == intent?.action){
            val appWidgetId = intent.getIntExtra("appWidgetId", 0)
            updateAppWidget(context, AppWidgetManager.getInstance(context), appWidgetId)
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
    intent.action = REFRESH_TEXT
    intent.putExtra("appWidgetId", appWidgetId)
    val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
    val widgetText = context.getString(R.string.appwidget_text)
    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.app_widget)
    views.setTextViewText(R.id.appwidget_text, getRandomQuoteFromDb(context))
    views.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent)
    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}

private fun getRandomQuoteFromDb(context: Context) : String{
    val dbHelper: DBHelper? = DBHelper(context)
    val database: SQLiteDatabase = dbHelper!!.writableDatabase

    val cursor: Cursor = database.rawQuery(
        "SELECT COUNT(*) FROM $TABLE_ALL_QUOTES", null
    )

    var count = 0
    if (cursor.moveToFirst()) {
        count = cursor.getInt(0)
    }
    cursor.close()

    val mRandom = Random.nextInt(count)

    val cursor1: Cursor = database.rawQuery(
        "SELECT $KEY_QUOTE, $KEY_AUTHOR FROM $TABLE_ALL_QUOTES WHERE $KEY_ID = $mRandom", null
    )

    var text = "Мы сами должны стать теми переменами, которые хотим видеть в мире. Махатма Ганди"
    if (cursor1.moveToFirst()) {
        val quote = cursor1.getString(0)
        val author = cursor1.getString(1)
        text = "$quote $author"
    }

    cursor1.close()
    return text
}