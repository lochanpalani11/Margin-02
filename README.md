# Margin

A native Android app for reselling/flipping inventory — track what you buy, what you sell it for, and your profit margin, entirely offline and on-device.

Built exactly to the platform spec you provided: **Kotlin + Jetpack Compose only** — no React Native, Flutter, KMP UI, XML layouts, or WebViews anywhere in the codebase.

## What it does

- **Dashboard** — total profit, ROI, capital currently invested, revenue, in-stock/listed counts, average days to sell, and a 6-month profit trend chart.
- **Inventory** — searchable, filterable (All / In Stock / Listed / Sold) list of every item you've bought.
- **Add / Edit Item** — name, category (with quick-pick suggestions from your existing categories), purchase price, purchase date, where you bought it, notes, and an optional photo.
- **Mark as Sold** — sale price, fees, sale date, platform sold on, with a live projected-profit preview before you confirm.
- **Item Detail** — full breakdown of profit, margin %, ROI %, and days held for sold items; edit/delete/mark-as-listed actions for active items.
- **Analytics** — profit trend over 3/6/12 months and profit-by-category breakdown.
- **Settings** — export your entire inventory to a JSON file, import it back (with a confirmation before it overwrites anything), or clear all data. No account, no sign-in, no server — everything lives in the app's local Room database, exactly as you asked.

## Architecture

Clean, layered MVVM, per the spec:

```
data/
  local/          Room entities, DAO, TypeConverters, database
  repository/     ItemRepositoryImpl — maps entities <-> domain models, aggregate queries
  backup/         Local JSON export/import (Storage Access Framework, no network)
domain/
  model/          Pure Kotlin domain models (InventoryItem, DashboardStats, ...)
  repository/     ItemRepository interface (what the UI layer depends on)
di/               Hilt modules (DatabaseModule, RepositoryModule)
ui/
  theme/          Color, type, shape, spacing tokens — the whole design system
  components/     Reusable pieces: KpiCard, InventoryTile, StatusChip, ProfitIndicator,
                   ProfitBarChart, CategoryBreakdownList, form fields, buttons
  screens/        One package per screen, each with its own ViewModel
  navigation/     Navigation Compose graph + bottom nav
```

- **MVVM + Repository pattern**, Hilt for DI, StateFlow for all UI state, Coroutines/Flow throughout.
- **Room, offline-first**: every read is a `Flow` straight from SQLite via Room, so the UI updates the instant you change data — there's no separate "sync" step because there's nothing to sync to. Dashboard/analytics aggregates (totals, ROI, monthly profit, category breakdown) are computed with SQL `SUM`/`CASE WHEN` queries rather than in Kotlin, so it stays fast even with a large inventory.
- **No Retrofit/networking layer**: since Margin has no backend and needs none, I left networking code out entirely rather than including an unused library and dead code paths.
- **Charts are hand-built on Compose's `Canvas`**, not a third-party charting library. This was a deliberate call: I can't compile or run this project in the environment I built it in (see "About this build" below), and a bar chart on Canvas is something I can be fully confident is syntactically and behaviorally correct, whereas a third-party charting library's exact API surface is a real risk of subtle build errors I'd have no way to catch. It also means one less dependency to ever go stale.
- **Images** are copied into the app's private storage on pick (via Coil for display) so they persist reliably without relying on a content URI grant surviving app restarts.

## Design

Dark-first, matching the Linear/Vercel/Raycast/Arc reference points you gave: near-black layered surfaces, one accent green pulled straight from your app icon, generous spacing, 18–20dp rounded corners, restrained type scale with tight letter-spacing on headlines. No light theme — Margin always renders dark, by design.

## App icon

Your uploaded icon is wired in at every density (mdpi through xxxhdpi) as the launcher icon.

## Building it

1. Open the `Margin/` folder in **Android Studio** (Ladybug or newer). It will detect there's no Gradle wrapper jar and offer to generate one — accept that, or run `gradle wrapper` yourself first if you have Gradle installed locally.
2. Let Gradle sync — it needs network access the first time to pull dependencies (AndroidX, Compose, Room, Hilt, Coil, Kotlin serialization).
3. Run on a device or emulator running **Android 8.0 (API 26) or newer**.

No API keys, no backend setup, no accounts — it should run the first time you build it.

## About this build — please read

I wrote every file in this project by hand in a sandboxed environment with **no Android SDK and no internet access**, so I was not able to actually compile, run, or instrument-test this project before handing it to you. I went through the entire codebase afterward checking for the mistakes that setup makes likely — missing imports, mismatched types, unbalanced braces, wrong package references — and fixed everything I found. I'm confident in the architecture and the logic, but I can't promise it's 100% free of a build error the way I could if I'd been able to run `./gradlew assembleDebug` myself.

If Android Studio flags anything on first sync, it's almost certainly a small, mechanical fix (an import, a version mismatch) rather than a structural problem — happy to fix anything you run into.
