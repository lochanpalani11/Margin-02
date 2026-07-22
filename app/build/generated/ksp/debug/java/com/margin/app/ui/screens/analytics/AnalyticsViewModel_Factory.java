package com.margin.app.ui.screens.analytics;

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
public final class AnalyticsViewModel_Factory implements Factory<AnalyticsViewModel> {
  private final Provider<ItemRepository> repositoryProvider;

  public AnalyticsViewModel_Factory(Provider<ItemRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public AnalyticsViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static AnalyticsViewModel_Factory create(Provider<ItemRepository> repositoryProvider) {
    return new AnalyticsViewModel_Factory(repositoryProvider);
  }

  public static AnalyticsViewModel newInstance(ItemRepository repository) {
    return new AnalyticsViewModel(repository);
  }
}
