package com.margin.app.ui.screens.sellitem;

import androidx.lifecycle.SavedStateHandle;
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
public final class SellItemViewModel_Factory implements Factory<SellItemViewModel> {
  private final Provider<ItemRepository> repositoryProvider;

  private final Provider<SavedStateHandle> savedStateHandleProvider;

  public SellItemViewModel_Factory(Provider<ItemRepository> repositoryProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    this.repositoryProvider = repositoryProvider;
    this.savedStateHandleProvider = savedStateHandleProvider;
  }

  @Override
  public SellItemViewModel get() {
    return newInstance(repositoryProvider.get(), savedStateHandleProvider.get());
  }

  public static SellItemViewModel_Factory create(Provider<ItemRepository> repositoryProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    return new SellItemViewModel_Factory(repositoryProvider, savedStateHandleProvider);
  }

  public static SellItemViewModel newInstance(ItemRepository repository,
      SavedStateHandle savedStateHandle) {
    return new SellItemViewModel(repository, savedStateHandle);
  }
}
