package com.margin.app.ui.screens.hauls;

import com.margin.app.domain.repository.HaulRepository;
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
public final class HaulsViewModel_Factory implements Factory<HaulsViewModel> {
  private final Provider<HaulRepository> repositoryProvider;

  public HaulsViewModel_Factory(Provider<HaulRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public HaulsViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static HaulsViewModel_Factory create(Provider<HaulRepository> repositoryProvider) {
    return new HaulsViewModel_Factory(repositoryProvider);
  }

  public static HaulsViewModel newInstance(HaulRepository repository) {
    return new HaulsViewModel(repository);
  }
}
