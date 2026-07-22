package com.margin.app.ui.screens.itemdetail;

import androidx.lifecycle.SavedStateHandle;
import com.margin.app.domain.repository.HaulRepository;
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
public final class ItemDetailViewModel_Factory implements Factory<ItemDetailViewModel> {
  private final Provider<ItemRepository> repositoryProvider;

  private final Provider<HaulRepository> haulRepositoryProvider;

  private final Provider<SavedStateHandle> savedStateHandleProvider;

  public ItemDetailViewModel_Factory(Provider<ItemRepository> repositoryProvider,
      Provider<HaulRepository> haulRepositoryProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    this.repositoryProvider = repositoryProvider;
    this.haulRepositoryProvider = haulRepositoryProvider;
    this.savedStateHandleProvider = savedStateHandleProvider;
  }

  @Override
  public ItemDetailViewModel get() {
    return newInstance(repositoryProvider.get(), haulRepositoryProvider.get(), savedStateHandleProvider.get());
  }

  public static ItemDetailViewModel_Factory create(Provider<ItemRepository> repositoryProvider,
      Provider<HaulRepository> haulRepositoryProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    return new ItemDetailViewModel_Factory(repositoryProvider, haulRepositoryProvider, savedStateHandleProvider);
  }

  public static ItemDetailViewModel newInstance(ItemRepository repository,
      HaulRepository haulRepository, SavedStateHandle savedStateHandle) {
    return new ItemDetailViewModel(repository, haulRepository, savedStateHandle);
  }
}
