package com.margin.app.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.margin.app.MainActivity
import com.margin.app.R

/**
 * Home screen widget that opens the Bid Calculator directly.
 *
 * Uses a custom intent action that [MainActivity] handles
 * to navigate to the Bid Calculator screen via Jetpack Navigation.
 */
class BidCalcWidgetProvider : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        for (appWidgetId in appWidgetIds) {
            val views = RemoteViews(context.packageName, R.layout.widget_bid_calc)

            val bidCalcIntent = Intent(context, MainActivity::class.java).apply {
                action = MarginWidgetProvider.ACTION_BID_CALC
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            }
            val bidCalcPending = PendingIntent.getActivity(
                context,
                0,
                bidCalcIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            views.setOnClickPendingIntent(R.id.widget_btn_bid_calc, bidCalcPending)

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}
