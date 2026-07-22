package com.margin.app.ui.screens.more;

import android.content.Context;
import com.margin.app.domain.repository.ItemRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class MoreViewModel_Factory implements Factory<MoreViewModel> {
  private final Provider<ItemRepository> repositoryProvider;

  private final Provider<Context> contextProvider;

  public MoreViewModel_Factory(Provider<ItemRepository> repositoryProvider,
      Provider<Context> contextProvider) {
    this.repositoryProvider = repositoryProvider;
    this.contextProvider = contextProvider;
  }

  @Override
  public MoreViewModel get() {
    return newInstance(repositoryProvider.get(), contextProvider.get());
  }

  public static MoreViewModel_Factory create(Provider<ItemRepository> repositoryProvider,
      Provider<Context> contextProvider) {
    return new MoreViewModel_Factory(repositoryProvider, contextProvider);
  }

  public static MoreViewModel newInstance(ItemRepository repository, Context context) {
    return new MoreViewModel(repository, context);
  }
}
