package io.fabric.sdk.android.services.network;

import java.io.InputStream;

public abstract interface f
{
  public abstract String getKeyStorePassword();
  
  public abstract InputStream getKeyStoreStream();
  
  public abstract long getPinCreationTimeInMillis();
  
  public abstract String[] getPins();
}


/* Location:              ~/io/fabric/sdk/android/services/network/f.class
 *
 * Reversed by:           J
 */