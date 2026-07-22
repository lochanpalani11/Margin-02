package com.margin.app;

import android.app.Activity;
import android.app.Service;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import com.margin.app.data.backup.BackupManager;
import com.margin.app.data.local.MarginDatabase;
import com.margin.app.data.local.dao.HaulDao;
import com.margin.app.data.local.dao.ItemDao;
import com.margin.app.data.repository.HaulRepositoryImpl;
import com.margin.app.data.repository.ItemRepositoryImpl;
import com.margin.app.di.DatabaseModule_ProvideHaulDaoFactory;
import com.margin.app.di.DatabaseModule_ProvideItemDaoFactory;
import com.margin.app.di.DatabaseModule_ProvideMarginDatabaseFactory;
import com.margin.app.ui.screens.additem.AddEditItemViewModel;
import com.margin.app.ui.screens.additem.AddEditItemViewModel_HiltModules;
import com.margin.app.ui.screens.analytics.AnalyticsViewModel;
import com.margin.app.ui.screens.analytics.AnalyticsViewModel_HiltModules;
import com.margin.app.ui.screens.bidcalculator.BidCalculatorViewModel;
import com.margin.app.ui.screens.bidcalculator.BidCalculatorViewModel_HiltModules;
import com.margin.app.ui.screens.dashboard.DashboardViewModel;
import com.margin.app.ui.screens.dashboard.DashboardViewModel_HiltModules;
import com.margin.app.ui.screens.hauls.AddHaulViewModel;
import com.margin.app.ui.screens.hauls.AddHaulViewModel_HiltModules;
import com.margin.app.ui.screens.hauls.HaulsViewModel;
import com.margin.app.ui.screens.hauls.HaulsViewModel_HiltModules;
import com.margin.app.ui.screens.inventory.InventoryViewModel;
import com.margin.app.ui.screens.inventory.InventoryViewModel_HiltModules;
import com.margin.app.ui.screens.itemdetail.ItemDetailViewModel;
import com.margin.app.ui.screens.itemdetail.ItemDetailViewModel_HiltModules;
import com.margin.app.ui.screens.kpidetail.KpiDetailViewModel;
import com.margin.app.ui.screens.kpidetail.KpiDetailViewModel_HiltModules;
import com.margin.app.ui.screens.more.MoreViewModel;
import com.margin.app.ui.screens.more.MoreViewModel_HiltModules;
import com.margin.app.ui.screens.sellitem.SellItemViewModel;
import com.margin.app.ui.screens.sellitem.SellItemViewModel_HiltModules;
import com.margin.app.ui.screens.settings.SettingsViewModel;
import com.margin.app.ui.screens.settings.SettingsViewModel_HiltModules;
import dagger.hilt.android.ActivityRetainedLifecycle;
import dagger.hilt.android.ViewModelLifecycle;
import dagger.hilt.android.internal.builders.ActivityComponentBuilder;
import dagger.hilt.android.internal.builders.ActivityRetainedComponentBuilder;
import dagger.hilt.android.internal.builders.FragmentComponentBuilder;
import dagger.hilt.android.internal.builders.ServiceComponentBuilder;
import dagger.hilt.android.internal.builders.ViewComponentBuilder;
import dagger.hilt.android.internal.builders.ViewModelComponentBuilder;
import dagger.hilt.android.internal.builders.ViewWithFragmentComponentBuilder;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories_InternalFactoryFactory_Factory;
import dagger.hilt.android.internal.managers.ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory;
import dagger.hilt.android.internal.managers.SavedStateHandleHolder;
import dagger.hilt.android.internal.modules.ApplicationContextModule;
import dagger.hilt.android.internal.modules.ApplicationContextModule_ProvideContextFactory;
import dagger.internal.DaggerGenerated;
import dagger.internal.DoubleCheck;
import dagger.internal.IdentifierNameString;
import dagger.internal.KeepFieldType;
import dagger.internal.LazyClassKeyMap;
import dagger.internal.MapBuilder;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

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
public final class DaggerMarginApplication_HiltComponents_SingletonC {
  private DaggerMarginApplication_HiltComponents_SingletonC() {
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private ApplicationContextModule applicationContextModule;

    private Builder() {
    }

    public Builder applicationContextModule(ApplicationContextModule applicationContextModule) {
      this.applicationContextModule = Preconditions.checkNotNull(applicationContextModule);
      return this;
    }

    public MarginApplication_HiltComponents.SingletonC build() {
      Preconditions.checkBuilderRequirement(applicationContextModule, ApplicationContextModule.class);
      return new SingletonCImpl(applicationContextModule);
    }
  }

  private static final class ActivityRetainedCBuilder implements MarginApplication_HiltComponents.ActivityRetainedC.Builder {
    private final SingletonCImpl singletonCImpl;

    private SavedStateHandleHolder savedStateHandleHolder;

    private ActivityRetainedCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ActivityRetainedCBuilder savedStateHandleHolder(
        SavedStateHandleHolder savedStateHandleHolder) {
      this.savedStateHandleHolder = Preconditions.checkNotNull(savedStateHandleHolder);
      return this;
    }

    @Override
    public MarginApplication_HiltComponents.ActivityRetainedC build() {
      Preconditions.checkBuilderRequirement(savedStateHandleHolder, SavedStateHandleHolder.class);
      return new ActivityRetainedCImpl(singletonCImpl, savedStateHandleHolder);
    }
  }

  private static final class ActivityCBuilder implements MarginApplication_HiltComponents.ActivityC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private Activity activity;

    private ActivityCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ActivityCBuilder activity(Activity activity) {
      this.activity = Preconditions.checkNotNull(activity);
      return this;
    }

    @Override
    public MarginApplication_HiltComponents.ActivityC build() {
      Preconditions.checkBuilderRequirement(activity, Activity.class);
      return new ActivityCImpl(singletonCImpl, activityRetainedCImpl, activity);
    }
  }

  private static final class FragmentCBuilder implements MarginApplication_HiltComponents.FragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private Fragment fragment;

    private FragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public FragmentCBuilder fragment(Fragment fragment) {
      this.fragment = Preconditions.checkNotNull(fragment);
      return this;
    }

    @Override
    public MarginApplication_HiltComponents.FragmentC build() {
      Preconditions.checkBuilderRequirement(fragment, Fragment.class);
      return new FragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragment);
    }
  }

  private static final class ViewWithFragmentCBuilder implements MarginApplication_HiltComponents.ViewWithFragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private View view;

    private ViewWithFragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;
    }

    @Override
    public ViewWithFragmentCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public MarginApplication_HiltComponents.ViewWithFragmentC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewWithFragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl, view);
    }
  }

  private static final class ViewCBuilder implements MarginApplication_HiltComponents.ViewC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private View view;

    private ViewCBuilder(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public ViewCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public MarginApplication_HiltComponents.ViewC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, view);
    }
  }

  private static final class ViewModelCBuilder implements MarginApplication_HiltComponents.ViewModelC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private SavedStateHandle savedStateHandle;

    private ViewModelLifecycle viewModelLifecycle;

    private ViewModelCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ViewModelCBuilder savedStateHandle(SavedStateHandle handle) {
      this.savedStateHandle = Preconditions.checkNotNull(handle);
      return this;
    }

    @Override
    public ViewModelCBuilder viewModelLifecycle(ViewModelLifecycle viewModelLifecycle) {
      this.viewModelLifecycle = Preconditions.checkNotNull(viewModelLifecycle);
      return this;
    }

    @Override
    public MarginApplication_HiltComponents.ViewModelC build() {
      Preconditions.checkBuilderRequirement(savedStateHandle, SavedStateHandle.class);
      Preconditions.checkBuilderRequirement(viewModelLifecycle, ViewModelLifecycle.class);
      return new ViewModelCImpl(singletonCImpl, activityRetainedCImpl, savedStateHandle, viewModelLifecycle);
    }
  }

  private static final class ServiceCBuilder implements MarginApplication_HiltComponents.ServiceC.Builder {
    private final SingletonCImpl singletonCImpl;

    private Service service;

    private ServiceCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ServiceCBuilder service(Service service) {
      this.service = Preconditions.checkNotNull(service);
      return this;
    }

    @Override
    public MarginApplication_HiltComponents.ServiceC build() {
      Preconditions.checkBuilderRequirement(service, Service.class);
      return new ServiceCImpl(singletonCImpl, service);
    }
  }

  private static final class ViewWithFragmentCImpl extends MarginApplication_HiltComponents.ViewWithFragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private final ViewWithFragmentCImpl viewWithFragmentCImpl = this;

    private ViewWithFragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;


    }
  }

  private static final class FragmentCImpl extends MarginApplication_HiltComponents.FragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl = this;

    private FragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        Fragment fragmentParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return activityCImpl.getHiltInternalFactoryFactory();
    }

    @Override
    public ViewWithFragmentComponentBuilder viewWithFragmentComponentBuilder() {
      return new ViewWithFragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl);
    }
  }

  private static final class ViewCImpl extends MarginApplication_HiltComponents.ViewC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final ViewCImpl viewCImpl = this;

    private ViewCImpl(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }
  }

  private static final class ActivityCImpl extends MarginApplication_HiltComponents.ActivityC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl = this;

    private ActivityCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, Activity activityParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;


    }

    @Override
    public void injectMainActivity(MainActivity mainActivity) {
    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return DefaultViewModelFactories_InternalFactoryFactory_Factory.newInstance(getViewModelKeys(), new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl));
    }

    @Override
    public Map<Class<?>, Boolean> getViewModelKeys() {
      return LazyClassKeyMap.<Boolean>of(MapBuilder.<String, Boolean>newMapBuilder(12).put(LazyClassKeyProvider.com_margin_app_ui_screens_additem_AddEditItemViewModel, AddEditItemViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_margin_app_ui_screens_hauls_AddHaulViewModel, AddHaulViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_margin_app_ui_screens_analytics_AnalyticsViewModel, AnalyticsViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_margin_app_ui_screens_bidcalculator_BidCalculatorViewModel, BidCalculatorViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_margin_app_ui_screens_dashboard_DashboardViewModel, DashboardViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_margin_app_ui_screens_hauls_HaulsViewModel, HaulsViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_margin_app_ui_screens_inventory_InventoryViewModel, InventoryViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_margin_app_ui_screens_itemdetail_ItemDetailViewModel, ItemDetailViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_margin_app_ui_screens_kpidetail_KpiDetailViewModel, KpiDetailViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_margin_app_ui_screens_more_MoreViewModel, MoreViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_margin_app_ui_screens_sellitem_SellItemViewModel, SellItemViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_margin_app_ui_screens_settings_SettingsViewModel, SettingsViewModel_HiltModules.KeyModule.provide()).build());
    }

    @Override
    public ViewModelComponentBuilder getViewModelComponentBuilder() {
      return new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public FragmentComponentBuilder fragmentComponentBuilder() {
      return new FragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @Override
    public ViewComponentBuilder viewComponentBuilder() {
      return new ViewCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @IdentifierNameString
    private static final class LazyClassKeyProvider {
      static String com_margin_app_ui_screens_hauls_AddHaulViewModel = "com.margin.app.ui.screens.hauls.AddHaulViewModel";

      static String com_margin_app_ui_screens_more_MoreViewModel = "com.margin.app.ui.screens.more.MoreViewModel";

      static String com_margin_app_ui_screens_itemdetail_ItemDetailViewModel = "com.margin.app.ui.screens.itemdetail.ItemDetailViewModel";

      static String com_margin_app_ui_screens_analytics_AnalyticsViewModel = "com.margin.app.ui.screens.analytics.AnalyticsViewModel";

      static String com_margin_app_ui_screens_dashboard_DashboardViewModel = "com.margin.app.ui.screens.dashboard.DashboardViewModel";

      static String com_margin_app_ui_screens_additem_AddEditItemViewModel = "com.margin.app.ui.screens.additem.AddEditItemViewModel";

      static String com_margin_app_ui_screens_bidcalculator_BidCalculatorViewModel = "com.margin.app.ui.screens.bidcalculator.BidCalculatorViewModel";

      static String com_margin_app_ui_screens_kpidetail_KpiDetailViewModel = "com.margin.app.ui.screens.kpidetail.KpiDetailViewModel";

      static String com_margin_app_ui_screens_inventory_InventoryViewModel = "com.margin.app.ui.screens.inventory.InventoryViewModel";

      static String com_margin_app_ui_screens_hauls_HaulsViewModel = "com.margin.app.ui.screens.hauls.HaulsViewModel";

      static String com_margin_app_ui_screens_sellitem_SellItemViewModel = "com.margin.app.ui.screens.sellitem.SellItemViewModel";

      static String com_margin_app_ui_screens_settings_SettingsViewModel = "com.margin.app.ui.screens.settings.SettingsViewModel";

      @KeepFieldType
      AddHaulViewModel com_margin_app_ui_screens_hauls_AddHaulViewModel2;

      @KeepFieldType
      MoreViewModel com_margin_app_ui_screens_more_MoreViewModel2;

      @KeepFieldType
      ItemDetailViewModel com_margin_app_ui_screens_itemdetail_ItemDetailViewModel2;

      @KeepFieldType
      AnalyticsViewModel com_margin_app_ui_screens_analytics_AnalyticsViewModel2;

      @KeepFieldType
      DashboardViewModel com_margin_app_ui_screens_dashboard_DashboardViewModel2;

      @KeepFieldType
      AddEditItemViewModel com_margin_app_ui_screens_additem_AddEditItemViewModel2;

      @KeepFieldType
      BidCalculatorViewModel com_margin_app_ui_screens_bidcalculator_BidCalculatorViewModel2;

      @KeepFieldType
      KpiDetailViewModel com_margin_app_ui_screens_kpidetail_KpiDetailViewModel2;

      @KeepFieldType
      InventoryViewModel com_margin_app_ui_screens_inventory_InventoryViewModel2;

      @KeepFieldType
      HaulsViewModel com_margin_app_ui_screens_hauls_HaulsViewModel2;

      @KeepFieldType
      SellItemViewModel com_margin_app_ui_screens_sellitem_SellItemViewModel2;

      @KeepFieldType
      SettingsViewModel com_margin_app_ui_screens_settings_SettingsViewModel2;
    }
  }

  private static final class ViewModelCImpl extends MarginApplication_HiltComponents.ViewModelC {
    private final SavedStateHandle savedStateHandle;

    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ViewModelCImpl viewModelCImpl = this;

    private Provider<AddEditItemViewModel> addEditItemViewModelProvider;

    private Provider<AddHaulViewModel> addHaulViewModelProvider;

    private Provider<AnalyticsViewModel> analyticsViewModelProvider;

    private Provider<BidCalculatorViewModel> bidCalculatorViewModelProvider;

    private Provider<DashboardViewModel> dashboardViewModelProvider;

    private Provider<HaulsViewModel> haulsViewModelProvider;

    private Provider<InventoryViewModel> inventoryViewModelProvider;

    private Provider<ItemDetailViewModel> itemDetailViewModelProvider;

    private Provider<KpiDetailViewModel> kpiDetailViewModelProvider;

    private Provider<MoreViewModel> moreViewModelProvider;

    private Provider<SellItemViewModel> sellItemViewModelProvider;

    private Provider<SettingsViewModel> settingsViewModelProvider;

    private ViewModelCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, SavedStateHandle savedStateHandleParam,
        ViewModelLifecycle viewModelLifecycleParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.savedStateHandle = savedStateHandleParam;
      initialize(savedStateHandleParam, viewModelLifecycleParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandle savedStateHandleParam,
        final ViewModelLifecycle viewModelLifecycleParam) {
      this.addEditItemViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 0);
      this.addHaulViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 1);
      this.analyticsViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 2);
      this.bidCalculatorViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 3);
      this.dashboardViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 4);
      this.haulsViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 5);
      this.inventoryViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 6);
      this.itemDetailViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 7);
      this.kpiDetailViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 8);
      this.moreViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 9);
      this.sellItemViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 10);
      this.settingsViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 11);
    }

    @Override
    public Map<Class<?>, javax.inject.Provider<ViewModel>> getHiltViewModelMap() {
      return LazyClassKeyMap.<javax.inject.Provider<ViewModel>>of(MapBuilder.<String, javax.inject.Provider<ViewModel>>newMapBuilder(12).put(LazyClassKeyProvider.com_margin_app_ui_screens_additem_AddEditItemViewModel, ((Provider) addEditItemViewModelProvider)).put(LazyClassKeyProvider.com_margin_app_ui_screens_hauls_AddHaulViewModel, ((Provider) addHaulViewModelProvider)).put(LazyClassKeyProvider.com_margin_app_ui_screens_analytics_AnalyticsViewModel, ((Provider) analyticsViewModelProvider)).put(LazyClassKeyProvider.com_margin_app_ui_screens_bidcalculator_BidCalculatorViewModel, ((Provider) bidCalculatorViewModelProvider)).put(LazyClassKeyProvider.com_margin_app_ui_screens_dashboard_DashboardViewModel, ((Provider) dashboardViewModelProvider)).put(LazyClassKeyProvider.com_margin_app_ui_screens_hauls_HaulsViewModel, ((Provider) haulsViewModelProvider)).put(LazyClassKeyProvider.com_margin_app_ui_screens_inventory_InventoryViewModel, ((Provider) inventoryViewModelProvider)).put(LazyClassKeyProvider.com_margin_app_ui_screens_itemdetail_ItemDetailViewModel, ((Provider) itemDetailViewModelProvider)).put(LazyClassKeyProvider.com_margin_app_ui_screens_kpidetail_KpiDetailViewModel, ((Provider) kpiDetailViewModelProvider)).put(LazyClassKeyProvider.com_margin_app_ui_screens_more_MoreViewModel, ((Provider) moreViewModelProvider)).put(LazyClassKeyProvider.com_margin_app_ui_screens_sellitem_SellItemViewModel, ((Provider) sellItemViewModelProvider)).put(LazyClassKeyProvider.com_margin_app_ui_screens_settings_SettingsViewModel, ((Provider) settingsViewModelProvider)).build());
    }

    @Override
    public Map<Class<?>, Object> getHiltViewModelAssistedMap() {
      return Collections.<Class<?>, Object>emptyMap();
    }

    @IdentifierNameString
    private static final class LazyClassKeyProvider {
      static String com_margin_app_ui_screens_additem_AddEditItemViewModel = "com.margin.app.ui.screens.additem.AddEditItemViewModel";

      static String com_margin_app_ui_screens_settings_SettingsViewModel = "com.margin.app.ui.screens.settings.SettingsViewModel";

      static String com_margin_app_ui_screens_bidcalculator_BidCalculatorViewModel = "com.margin.app.ui.screens.bidcalculator.BidCalculatorViewModel";

      static String com_margin_app_ui_screens_analytics_AnalyticsViewModel = "com.margin.app.ui.screens.analytics.AnalyticsViewModel";

      static String com_margin_app_ui_screens_inventory_InventoryViewModel = "com.margin.app.ui.screens.inventory.InventoryViewModel";

      static String com_margin_app_ui_screens_kpidetail_KpiDetailViewModel = "com.margin.app.ui.screens.kpidetail.KpiDetailViewModel";

      static String com_margin_app_ui_screens_hauls_HaulsViewModel = "com.margin.app.ui.screens.hauls.HaulsViewModel";

      static String com_margin_app_ui_screens_more_MoreViewModel = "com.margin.app.ui.screens.more.MoreViewModel";

      static String com_margin_app_ui_screens_hauls_AddHaulViewModel = "com.margin.app.ui.screens.hauls.AddHaulViewModel";

      static String com_margin_app_ui_screens_dashboard_DashboardViewModel = "com.margin.app.ui.screens.dashboard.DashboardViewModel";

      static String com_margin_app_ui_screens_sellitem_SellItemViewModel = "com.margin.app.ui.screens.sellitem.SellItemViewModel";

      static String com_margin_app_ui_screens_itemdetail_ItemDetailViewModel = "com.margin.app.ui.screens.itemdetail.ItemDetailViewModel";

      @KeepFieldType
      AddEditItemViewModel com_margin_app_ui_screens_additem_AddEditItemViewModel2;

      @KeepFieldType
      SettingsViewModel com_margin_app_ui_screens_settings_SettingsViewModel2;

      @KeepFieldType
      BidCalculatorViewModel com_margin_app_ui_screens_bidcalculator_BidCalculatorViewModel2;

      @KeepFieldType
      AnalyticsViewModel com_margin_app_ui_screens_analytics_AnalyticsViewModel2;

      @KeepFieldType
      InventoryViewModel com_margin_app_ui_screens_inventory_InventoryViewModel2;

      @KeepFieldType
      KpiDetailViewModel com_margin_app_ui_screens_kpidetail_KpiDetailViewModel2;

      @KeepFieldType
      HaulsViewModel com_margin_app_ui_screens_hauls_HaulsViewModel2;

      @KeepFieldType
      MoreViewModel com_margin_app_ui_screens_more_MoreViewModel2;

      @KeepFieldType
      AddHaulViewModel com_margin_app_ui_screens_hauls_AddHaulViewModel2;

      @KeepFieldType
      DashboardViewModel com_margin_app_ui_screens_dashboard_DashboardViewModel2;

      @KeepFieldType
      SellItemViewModel com_margin_app_ui_screens_sellitem_SellItemViewModel2;

      @KeepFieldType
      ItemDetailViewModel com_margin_app_ui_screens_itemdetail_ItemDetailViewModel2;
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final ViewModelCImpl viewModelCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          ViewModelCImpl viewModelCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.viewModelCImpl = viewModelCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.margin.app.ui.screens.additem.AddEditItemViewModel 
          return (T) new AddEditItemViewModel(singletonCImpl.itemRepositoryImplProvider.get(), singletonCImpl.haulRepositoryImplProvider.get(), viewModelCImpl.savedStateHandle);

          case 1: // com.margin.app.ui.screens.hauls.AddHaulViewModel 
          return (T) new AddHaulViewModel(singletonCImpl.haulRepositoryImplProvider.get());

          case 2: // com.margin.app.ui.screens.analytics.AnalyticsViewModel 
          return (T) new AnalyticsViewModel(singletonCImpl.itemRepositoryImplProvider.get());

          case 3: // com.margin.app.ui.screens.bidcalculator.BidCalculatorViewModel 
          return (T) new BidCalculatorViewModel();

          case 4: // com.margin.app.ui.screens.dashboard.DashboardViewModel 
          return (T) new DashboardViewModel(singletonCImpl.itemRepositoryImplProvider.get());

          case 5: // com.margin.app.ui.screens.hauls.HaulsViewModel 
          return (T) new HaulsViewModel(singletonCImpl.haulRepositoryImplProvider.get());

          case 6: // com.margin.app.ui.screens.inventory.InventoryViewModel 
          return (T) new InventoryViewModel(singletonCImpl.itemRepositoryImplProvider.get());

          case 7: // com.margin.app.ui.screens.itemdetail.ItemDetailViewModel 
          return (T) new ItemDetailViewModel(singletonCImpl.itemRepositoryImplProvider.get(), singletonCImpl.haulRepositoryImplProvider.get(), viewModelCImpl.savedStateHandle);

          case 8: // com.margin.app.ui.screens.kpidetail.KpiDetailViewModel 
          return (T) new KpiDetailViewModel(singletonCImpl.itemRepositoryImplProvider.get());

          case 9: // com.margin.app.ui.screens.more.MoreViewModel 
          return (T) new MoreViewModel(singletonCImpl.itemRepositoryImplProvider.get(), ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 10: // com.margin.app.ui.screens.sellitem.SellItemViewModel 
          return (T) new SellItemViewModel(singletonCImpl.itemRepositoryImplProvider.get(), viewModelCImpl.savedStateHandle);

          case 11: // com.margin.app.ui.screens.settings.SettingsViewModel 
          return (T) new SettingsViewModel(singletonCImpl.itemRepositoryImplProvider.get(), singletonCImpl.backupManagerProvider.get(), ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ActivityRetainedCImpl extends MarginApplication_HiltComponents.ActivityRetainedC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl = this;

    private Provider<ActivityRetainedLifecycle> provideActivityRetainedLifecycleProvider;

    private ActivityRetainedCImpl(SingletonCImpl singletonCImpl,
        SavedStateHandleHolder savedStateHandleHolderParam) {
      this.singletonCImpl = singletonCImpl;

      initialize(savedStateHandleHolderParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandleHolder savedStateHandleHolderParam) {
      this.provideActivityRetainedLifecycleProvider = DoubleCheck.provider(new SwitchingProvider<ActivityRetainedLifecycle>(singletonCImpl, activityRetainedCImpl, 0));
    }

    @Override
    public ActivityComponentBuilder activityComponentBuilder() {
      return new ActivityCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public ActivityRetainedLifecycle getActivityRetainedLifecycle() {
      return provideActivityRetainedLifecycleProvider.get();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // dagger.hilt.android.ActivityRetainedLifecycle 
          return (T) ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory.provideActivityRetainedLifecycle();

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ServiceCImpl extends MarginApplication_HiltComponents.ServiceC {
    private final SingletonCImpl singletonCImpl;

    private final ServiceCImpl serviceCImpl = this;

    private ServiceCImpl(SingletonCImpl singletonCImpl, Service serviceParam) {
      this.singletonCImpl = singletonCImpl;


    }
  }

  private static final class SingletonCImpl extends MarginApplication_HiltComponents.SingletonC {
    private final ApplicationContextModule applicationContextModule;

    private final SingletonCImpl singletonCImpl = this;

    private Provider<MarginDatabase> provideMarginDatabaseProvider;

    private Provider<ItemDao> provideItemDaoProvider;

    private Provider<ItemRepositoryImpl> itemRepositoryImplProvider;

    private Provider<HaulDao> provideHaulDaoProvider;

    private Provider<HaulRepositoryImpl> haulRepositoryImplProvider;

    private Provider<BackupManager> backupManagerProvider;

    private SingletonCImpl(ApplicationContextModule applicationContextModuleParam) {
      this.applicationContextModule = applicationContextModuleParam;
      initialize(applicationContextModuleParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final ApplicationContextModule applicationContextModuleParam) {
      this.provideMarginDatabaseProvider = DoubleCheck.provider(new SwitchingProvider<MarginDatabase>(singletonCImpl, 2));
      this.provideItemDaoProvider = DoubleCheck.provider(new SwitchingProvider<ItemDao>(singletonCImpl, 1));
      this.itemRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<ItemRepositoryImpl>(singletonCImpl, 0));
      this.provideHaulDaoProvider = DoubleCheck.provider(new SwitchingProvider<HaulDao>(singletonCImpl, 4));
      this.haulRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<HaulRepositoryImpl>(singletonCImpl, 3));
      this.backupManagerProvider = DoubleCheck.provider(new SwitchingProvider<BackupManager>(singletonCImpl, 5));
    }

    @Override
    public void injectMarginApplication(MarginApplication marginApplication) {
    }

    @Override
    public Set<Boolean> getDisableFragmentGetContextFix() {
      return Collections.<Boolean>emptySet();
    }

    @Override
    public ActivityRetainedComponentBuilder retainedComponentBuilder() {
      return new ActivityRetainedCBuilder(singletonCImpl);
    }

    @Override
    public ServiceComponentBuilder serviceComponentBuilder() {
      return new ServiceCBuilder(singletonCImpl);
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.margin.app.data.repository.ItemRepositoryImpl 
          return (T) new ItemRepositoryImpl(singletonCImpl.provideItemDaoProvider.get());

          case 1: // com.margin.app.data.local.dao.ItemDao 
          return (T) DatabaseModule_ProvideItemDaoFactory.provideItemDao(singletonCImpl.provideMarginDatabaseProvider.get());

          case 2: // com.margin.app.data.local.MarginDatabase 
          return (T) DatabaseModule_ProvideMarginDatabaseFactory.provideMarginDatabase(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 3: // com.margin.app.data.repository.HaulRepositoryImpl 
          return (T) new HaulRepositoryImpl(singletonCImpl.provideHaulDaoProvider.get());

          case 4: // com.margin.app.data.local.dao.HaulDao 
          return (T) DatabaseModule_ProvideHaulDaoFactory.provideHaulDao(singletonCImpl.provideMarginDatabaseProvider.get());

          case 5: // com.margin.app.data.backup.BackupManager 
          return (T) new BackupManager(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          default: throw new AssertionError(id);
        }
      }
    }
  }
}
