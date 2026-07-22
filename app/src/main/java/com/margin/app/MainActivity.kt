package com.margin.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.margin.app.ui.navigation.Destinations
import com.margin.app.ui.navigation.MarginNavGraph
import com.margin.app.ui.theme.MarginTheme
import com.margin.app.widget.MarginWidgetProvider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    /**
     * Widget-initiated navigation target. Set by [onCreate] or [onNewIntent]
     * when the activity is launched from the home screen widget.
     * Uses Compose mutableStateOf so the composable observes changes when
     * the activity is already alive and [onNewIntent] delivers a new intent.
     */
    private var pendingWidgetRoute by mutableStateOf<String?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Check for widget-launched intent
        pendingWidgetRoute = resolveWidgetRoute(intent)

        enableEdgeToEdge()
        setContent {
            MarginTheme {
                // Pass the observable state so both onCreate and onNewIntent
                // widget launches trigger navigation. The route is consumed
                // after navigation to avoid re-triggering on configuration changes.
                MarginNavGraph(
                    pendingWidgetRoute = pendingWidgetRoute,
                    onWidgetRouteConsumed = { pendingWidgetRoute = null }
                )
            }
        }
    }

    override fun onNewIntent(intent: android.content.Intent) {
        super.onNewIntent(intent)
        // Widget taps come through onNewIntent when the activity is already alive.
        // Because pendingWidgetRoute is backed by mutableStateOf, updating it
        // triggers recomposition so the NavGraph's LaunchedEffect navigates.
        pendingWidgetRoute = resolveWidgetRoute(intent)
    }

    private fun resolveWidgetRoute(intent: android.content.Intent): String? {
        return when (intent.action) {
            MarginWidgetProvider.ACTION_QUICK_ADD -> Destinations.NEW_ITEM
            MarginWidgetProvider.ACTION_BID_CALC -> Destinations.BID_CALCULATOR
            MarginWidgetProvider.ACTION_QUICK_SELL -> Destinations.INVENTORY
            else -> null
        }
    }
}
