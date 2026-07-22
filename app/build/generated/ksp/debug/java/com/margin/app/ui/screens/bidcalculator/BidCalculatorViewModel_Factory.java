package com.margin.app.ui.screens.bidcalculator;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

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
public final class BidCalculatorViewModel_Factory implements Factory<BidCalculatorViewModel> {
  @Override
  public BidCalculatorViewModel get() {
    return newInstance();
  }

  public static BidCalculatorViewModel_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static BidCalculatorViewModel newInstance() {
    return new BidCalculatorViewModel();
  }

  private static final class InstanceHolder {
    private static final BidCalculatorViewModel_Factory INSTANCE = new BidCalculatorViewModel_Factory();
  }
}
