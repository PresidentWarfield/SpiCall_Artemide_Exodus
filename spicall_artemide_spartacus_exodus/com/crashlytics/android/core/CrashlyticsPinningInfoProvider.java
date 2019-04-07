package com.crashlytics.android.core;

import io.fabric.sdk.android.services.network.f;
import java.io.InputStream;

class CrashlyticsPinningInfoProvider
  implements f
{
  private final PinningInfoProvider pinningInfo;
  
  public CrashlyticsPinningInfoProvider(PinningInfoProvider paramPinningInfoProvider)
  {
    this.pinningInfo = paramPinningInfoProvider;
  }
  
  public String getKeyStorePassword()
  {
    return this.pinningInfo.getKeyStorePassword();
  }
  
  public InputStream getKeyStoreStream()
  {
    return this.pinningInfo.getKeyStoreStream();
  }
  
  public long getPinCreationTimeInMillis()
  {
    return -1L;
  }
  
  public String[] getPins()
  {
    return this.pinningInfo.getPins();
  }
}


/* Location:              ~/com/crashlytics/android/core/CrashlyticsPinningInfoProvider.class
 *
 * Reversed by:           J
 */