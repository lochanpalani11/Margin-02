package com.margin.app.di;

import com.margin.app.data.local.MarginDatabase;
import com.margin.app.data.local.dao.ItemDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation"
})
public final class DatabaseModule_ProvideItemDaoFactory implements Factory<ItemDao> {
  private final Provider<MarginDatabase> databaseProvider;

  public DatabaseModule_ProvideItemDaoFactory(Provider<MarginDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public ItemDao get() {
    return provideItemDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideItemDaoFactory create(
      Provider<MarginDatabase> databaseProvider) {
    return new DatabaseModule_ProvideItemDaoFactory(databaseProvider);
  }

  public static ItemDao provideItemDao(MarginDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideItemDao(database));
  }
}
