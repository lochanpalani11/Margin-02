package com.margin.app.ui.screens.inventory;

import com.margin.app.domain.repository.ItemRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class InventoryViewModel_Factory implements Factory<InventoryViewModel> {
  private final Provider<ItemRepository> repositoryProvider;

  public InventoryViewModel_Factory(Provider<ItemRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public InventoryViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static InventoryViewModel_Factory create(Provider<ItemRepository> repositoryProvider) {
    return new InventoryViewModel_Factory(repositoryProvider);
  }

  public static InventoryViewModel newInstance(ItemRepository repository) {
    return new InventoryViewModel(repository);
  }
}
