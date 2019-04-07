package io.fabric.sdk.android;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import java.io.File;

class d
  extends ContextWrapper
{
  private final String a;
  private final String b;
  
  public d(Context paramContext, String paramString1, String paramString2)
  {
    super(paramContext);
    this.b = paramString1;
    this.a = paramString2;
  }
  
  public File getCacheDir()
  {
    return new File(super.getCacheDir(), this.a);
  }
  
  public File getDatabasePath(String paramString)
  {
    File localFile = new File(super.getDatabasePath(paramString).getParentFile(), this.a);
    localFile.mkdirs();
    return new File(localFile, paramString);
  }
  
  @TargetApi(8)
  public File getExternalCacheDir()
  {
    return new File(super.getExternalCacheDir(), this.a);
  }
  
  @TargetApi(8)
  public File getExternalFilesDir(String paramString)
  {
    return new File(super.getExternalFilesDir(paramString), this.a);
  }
  
  public File getFilesDir()
  {
    return new File(super.getFilesDir(), this.a);
  }
  
  public SharedPreferences getSharedPreferences(String paramString, int paramInt)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(this.b);
    localStringBuilder.append(":");
    localStringBuilder.append(paramString);
    return super.getSharedPreferences(localStringBuilder.toString(), paramInt);
  }
  
  public SQLiteDatabase openOrCreateDatabase(String paramString, int paramInt, SQLiteDatabase.CursorFactory paramCursorFactory)
  {
    return SQLiteDatabase.openOrCreateDatabase(getDatabasePath(paramString), paramCursorFactory);
  }
  
  @TargetApi(11)
  public SQLiteDatabase openOrCreateDatabase(String paramString, int paramInt, SQLiteDatabase.CursorFactory paramCursorFactory, DatabaseErrorHandler paramDatabaseErrorHandler)
  {
    return SQLiteDatabase.openOrCreateDatabase(getDatabasePath(paramString).getPath(), paramCursorFactory, paramDatabaseErrorHandler);
  }
}


/* Location:              ~/io/fabric/sdk/android/d.class
 *
 * Reversed by:           J
 */