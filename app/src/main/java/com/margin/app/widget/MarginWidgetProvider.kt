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
 * Home screen widget that provides two quick actions:
 * - Quick Add Item → opens the Add Item screen directly
 * - Bid Calculator → opens the MacBid calculator
 *
 * Uses custom intent actions that [MainActivity] handles
 * to navigate to the correct screen via Jetpack Navigation.
 */
class MarginWidgetProvider : AppWidgetProvider() {

    companion object {
        const val ACTION_QUICK_ADD = "com.margin.app.action.QUICK_ADD_ITEM"
        const val ACTION_QUICK_SELL = "com.margin.app.action.QUICK_SELL"
        const val ACTION_BID_CALC = "com.margin.app.action.BID_CALCULATOR"
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        for (appWidgetId in appWidgetIds) {
            val views = RemoteViews(context.packageName, R.layout.widget_margin)

            // Quick Add button → opens Add Item screen
            val quickAddIntent = Intent(context, MainActivity::class.java).apply {
                action = ACTION_QUICK_ADD
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            }
            val quickAddPending = PendingIntent.getActivity(
                context,
                0,
                quickAddIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            views.setOnClickPendingIntent(R.id.widget_btn_quick_add, quickAddPending)

            // Bid Calculator button → opens Bid Calculator screen
            val bidCalcIntent = Intent(context, MainActivity::class.java).apply {
                action = ACTION_BID_CALC
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            }
            val bidCalcPending = PendingIntent.getActivity(
                context,
                1,
                bidCalcIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            views.setOnClickPendingIntent(R.id.widget_btn_bid_calc, bidCalcPending)

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}
