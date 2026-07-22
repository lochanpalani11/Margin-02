package com.margin.app.di;

import com.margin.app.data.local.MarginDatabase;
import com.margin.app.data.local.dao.HaulDao;
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
public final class DatabaseModule_ProvideHaulDaoFactory implements Factory<HaulDao> {
  private final Provider<MarginDatabase> databaseProvider;

  public DatabaseModule_ProvideHaulDaoFactory(Provider<MarginDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public HaulDao get() {
    return provideHaulDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideHaulDaoFactory create(
      Provider<MarginDatabase> databaseProvider) {
    return new DatabaseModule_ProvideHaulDaoFactory(databaseProvider);
  }

  public static HaulDao provideHaulDao(MarginDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideHaulDao(database));
  }
}
