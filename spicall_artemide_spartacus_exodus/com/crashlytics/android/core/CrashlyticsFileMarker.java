package com.crashlytics.android.core;

import io.fabric.sdk.android.c;
import io.fabric.sdk.android.k;
import io.fabric.sdk.android.services.d.a;
import java.io.File;
import java.io.IOException;

class CrashlyticsFileMarker
{
  private final a fileStore;
  private final String markerName;
  
  public CrashlyticsFileMarker(String paramString, a parama)
  {
    this.markerName = paramString;
    this.fileStore = parama;
  }
  
  private File getMarkerFile()
  {
    return new File(this.fileStore.a(), this.markerName);
  }
  
  public boolean create()
  {
    boolean bool;
    try
    {
      bool = getMarkerFile().createNewFile();
    }
    catch (IOException localIOException)
    {
      k localk = c.g();
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Error creating marker: ");
      localStringBuilder.append(this.markerName);
      localk.e("CrashlyticsCore", localStringBuilder.toString(), localIOException);
      bool = false;
    }
    return bool;
  }
  
  public boolean isPresent()
  {
    return getMarkerFile().exists();
  }
  
  public boolean remove()
  {
    return getMarkerFile().delete();
  }
}


/* Location:              ~/com/crashlytics/android/core/CrashlyticsFileMarker.class
 *
 * Reversed by:           J
 */