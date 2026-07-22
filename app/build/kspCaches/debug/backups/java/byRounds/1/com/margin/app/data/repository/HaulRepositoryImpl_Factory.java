package com.margin.app.data.repository;

import com.margin.app.data.local.dao.HaulDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class HaulRepositoryImpl_Factory implements Factory<HaulRepositoryImpl> {
  private final Provider<HaulDao> daoProvider;

  public HaulRepositoryImpl_Factory(Provider<HaulDao> daoProvider) {
    this.daoProvider = daoProvider;
  }

  @Override
  public HaulRepositoryImpl get() {
    return newInstance(daoProvider.get());
  }

  public static HaulRepositoryImpl_Factory create(Provider<HaulDao> daoProvider) {
    return new HaulRepositoryImpl_Factory(daoProvider);
  }

  public static HaulRepositoryImpl newInstance(HaulDao dao) {
    return new HaulRepositoryImpl(dao);
  }
}
