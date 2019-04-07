package com.crashlytics.android;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import io.fabric.sdk.android.c;
import io.fabric.sdk.android.h;
import io.fabric.sdk.android.k;
import io.fabric.sdk.android.services.b.q;

public class CrashlyticsInitProvider
  extends ContentProvider
{
  private static final String TAG = "CrashlyticsInitProvider";
  
  public int delete(Uri paramUri, String paramString, String[] paramArrayOfString)
  {
    return 0;
  }
  
  public String getType(Uri paramUri)
  {
    return null;
  }
  
  public Uri insert(Uri paramUri, ContentValues paramContentValues)
  {
    return null;
  }
  
  public boolean onCreate()
  {
    Context localContext = getContext();
    if (shouldInitializeFabric(localContext, new q(), new ManifestEnabledCheckStrategy())) {
      try
      {
        Crashlytics localCrashlytics = new com/crashlytics/android/Crashlytics;
        localCrashlytics.<init>();
        c.a(localContext, new h[] { localCrashlytics });
        c.g().c("CrashlyticsInitProvider", "CrashlyticsInitProvider initialization successful");
      }
      catch (IllegalStateException localIllegalStateException)
      {
        c.g().c("CrashlyticsInitProvider", "CrashlyticsInitProvider initialization unsuccessful");
        return false;
      }
    }
    return true;
  }
  
  public Cursor query(Uri paramUri, String[] paramArrayOfString1, String paramString1, String[] paramArrayOfString2, String paramString2)
  {
    return null;
  }
  
  boolean shouldInitializeFabric(Context paramContext, q paramq, EnabledCheckStrategy paramEnabledCheckStrategy)
  {
    boolean bool;
    if ((paramq.b(paramContext)) && (paramEnabledCheckStrategy.isCrashlyticsEnabled(paramContext))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public int update(Uri paramUri, ContentValues paramContentValues, String paramString, String[] paramArrayOfString)
  {
    return 0;
  }
  
  static abstract interface EnabledCheckStrategy
  {
    public abstract boolean isCrashlyticsEnabled(Context paramContext);
  }
}


/* Location:              ~/com/crashlytics/android/CrashlyticsInitProvider.class
 *
 * Reversed by:           J
 */