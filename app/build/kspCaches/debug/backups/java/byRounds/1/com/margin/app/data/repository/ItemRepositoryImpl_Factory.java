package com.margin.app.data.repository;

import com.margin.app.data.local.dao.ItemDao;
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
public final class ItemRepositoryImpl_Factory implements Factory<ItemRepositoryImpl> {
  private final Provider<ItemDao> daoProvider;

  public ItemRepositoryImpl_Factory(Provider<ItemDao> daoProvider) {
    this.daoProvider = daoProvider;
  }

  @Override
  public ItemRepositoryImpl get() {
    return newInstance(daoProvider.get());
  }

  public static ItemRepositoryImpl_Factory create(Provider<ItemDao> daoProvider) {
    return new ItemRepositoryImpl_Factory(daoProvider);
  }

  public static ItemRepositoryImpl newInstance(ItemDao dao) {
    return new ItemRepositoryImpl(dao);
  }
}
