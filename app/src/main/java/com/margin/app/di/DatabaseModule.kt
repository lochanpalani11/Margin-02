package com.margin.app.di

import android.content.Context
import androidx.room.Room
import com.margin.app.data.local.MarginDatabase
import com.margin.app.data.local.dao.HaulDao
import com.margin.app.data.local.dao.ItemDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideMarginDatabase(@ApplicationContext context: Context): MarginDatabase =
        Room.databaseBuilder(
            context,
            MarginDatabase::class.java,
            MarginDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideItemDao(database: MarginDatabase): ItemDao = database.itemDao()

    @Provides
    @Singleton
    fun provideHaulDao(database: MarginDatabase): HaulDao = database.haulDao()
}
