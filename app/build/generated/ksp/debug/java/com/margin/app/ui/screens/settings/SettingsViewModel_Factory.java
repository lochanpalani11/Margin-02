package com.margin.app.ui.screens.settings;

import android.content.Context;
import com.margin.app.data.backup.BackupManager;
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
public final class SettingsViewModel_Factory implements Factory<SettingsViewModel> {
  private final Provider<ItemRepository> repositoryProvider;

  private final Provider<BackupManager> backupManagerProvider;

  private final Provider<Context> contextProvider;

  public SettingsViewModel_Factory(Provider<ItemRepository> repositoryProvider,
      Provider<BackupManager> backupManagerProvider, Provider<Context> contextProvider) {
    this.repositoryProvider = repositoryProvider;
    this.backupManagerProvider = backupManagerProvider;
    this.contextProvider = contextProvider;
  }

  @Override
  public SettingsViewModel get() {
    return newInstance(repositoryProvider.get(), backupManagerProvider.get(), contextProvider.get());
  }

  public static SettingsViewModel_Factory create(Provider<ItemRepository> repositoryProvider,
      Provider<BackupManager> backupManagerProvider, Provider<Context> contextProvider) {
    return new SettingsViewModel_Factory(repositoryProvider, backupManagerProvider, contextProvider);
  }

  public static SettingsViewModel newInstance(ItemRepository repository,
      BackupManager backupManager, Context context) {
    return new SettingsViewModel(repository, backupManager, context);
  }
}
