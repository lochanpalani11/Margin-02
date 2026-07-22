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
public final class AddHaulViewModel_Factory implements Factory<AddHaulViewModel> {
  private final Provider<HaulRepository> haulRepositoryProvider;

  public AddHaulViewModel_Factory(Provider<HaulRepository> haulRepositoryProvider) {
    this.haulRepositoryProvider = haulRepositoryProvider;
  }

  @Override
  public AddHaulViewModel get() {
    return newInstance(haulRepositoryProvider.get());
  }

  public static AddHaulViewModel_Factory create(Provider<HaulRepository> haulRepositoryProvider) {
    return new AddHaulViewModel_Factory(haulRepositoryProvider);
  }

  public static AddHaulViewModel newInstance(HaulRepository haulRepository) {
    return new AddHaulViewModel(haulRepository);
  }
}
