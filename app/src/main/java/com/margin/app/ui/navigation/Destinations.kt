package com.margin.app.ui.navigation

object Destinations {
    const val DASHBOARD = "dashboard"
    const val INVENTORY = "inventory"
    const val ANALYTICS = "analytics"
    const val SETTINGS = "settings"
    const val MORE = "more"

    const val ITEM_DETAIL = "item_detail/{itemId}"
    const val ADD_EDIT_ITEM = "add_edit_item?itemId={itemId}&haulId={haulId}"
    const val SELL_ITEM = "sell_item/{itemId}"

    // Hauls
    const val HAULS = "hauls"
    const val ADD_HAUL = "add_haul"
    const val HAUL_DETAIL = "haul_detail/{haulId}"

    // Bid Calculator
    const val BID_CALCULATOR = "bid_calculator"

    // KPI Detail (from dashboard/analytics tile clicks)
    const val KPI_DETAIL = "kpi_detail/{kpiType}"

    fun itemDetail(itemId: Long) = "item_detail/$itemId"
    fun editItem(itemId: Long) = "add_edit_item?itemId=$itemId"
    const val NEW_ITEM = "add_edit_item"
    fun sellItem(itemId: Long) = "sell_item/$itemId"
    fun haulDetail(haulId: Long) = "haul_detail/$haulId"
    fun kpiDetail(kpiType: String) = "kpi_detail/$kpiType"

    const val NO_ITEM_ID = -1L
}

data class BottomNavItem(
    val route: String,
    val label: String,
    val iconSelector: BottomIcon
)

enum class BottomIcon { DASHBOARD, INVENTORY, HAULS, BID_CALCULATOR, MORE }

val bottomNavItems = listOf(
    BottomNavItem(Destinations.DASHBOARD, "Home", BottomIcon.DASHBOARD),
    BottomNavItem(Destinations.INVENTORY, "Inventory", BottomIcon.INVENTORY),
    BottomNavItem(Destinations.BID_CALCULATOR, "Bid Calc", BottomIcon.BID_CALCULATOR),
    BottomNavItem(Destinations.HAULS, "Hauls", BottomIcon.HAULS),
    BottomNavItem(Destinations.MORE, "More", BottomIcon.MORE)
)
