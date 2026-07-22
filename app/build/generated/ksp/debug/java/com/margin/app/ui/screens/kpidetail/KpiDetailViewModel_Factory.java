package com.margin.app.ui.screens.kpidetail;

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
public final class KpiDetailViewModel_Factory implements Factory<KpiDetailViewModel> {
  private final Provider<ItemRepository> repositoryProvider;

  public KpiDetailViewModel_Factory(Provider<ItemRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public KpiDetailViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static KpiDetailViewModel_Factory create(Provider<ItemRepository> repositoryProvider) {
    return new KpiDetailViewModel_Factory(repositoryProvider);
  }

  public static KpiDetailViewModel newInstance(ItemRepository repository) {
    return new KpiDetailViewModel(repository);
  }
}
