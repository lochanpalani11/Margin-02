package com.margin.app.ui.screens.additem;

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
public final class AddEditItemViewModel_Factory implements Factory<AddEditItemViewModel> {
  private final Provider<ItemRepository> repositoryProvider;

  private final Provider<HaulRepository> haulRepositoryProvider;

  private final Provider<SavedStateHandle> savedStateHandleProvider;

  public AddEditItemViewModel_Factory(Provider<ItemRepository> repositoryProvider,
      Provider<HaulRepository> haulRepositoryProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    this.repositoryProvider = repositoryProvider;
    this.haulRepositoryProvider = haulRepositoryProvider;
    this.savedStateHandleProvider = savedStateHandleProvider;
  }

  @Override
  public AddEditItemViewModel get() {
    return newInstance(repositoryProvider.get(), haulRepositoryProvider.get(), savedStateHandleProvider.get());
  }

  public static AddEditItemViewModel_Factory create(Provider<ItemRepository> repositoryProvider,
      Provider<HaulRepository> haulRepositoryProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    return new AddEditItemViewModel_Factory(repositoryProvider, haulRepositoryProvider, savedStateHandleProvider);
  }

  public static AddEditItemViewModel newInstance(ItemRepository repository,
      HaulRepository haulRepository, SavedStateHandle savedStateHandle) {
    return new AddEditItemViewModel(repository, haulRepository, savedStateHandle);
  }
}
