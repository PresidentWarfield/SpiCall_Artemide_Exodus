package com.crashlytics.android.core;

import java.io.InputStream;

public abstract interface PinningInfoProvider
{
  public abstract String getKeyStorePassword();
  
  public abstract InputStream getKeyStoreStream();
  
  public abstract String[] getPins();
}


/* Location:              ~/com/crashlytics/android/core/PinningInfoProvider.class
 *
 * Reversed by:           J
 */