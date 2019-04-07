package com.app.system.common.entity;

import android.database.Cursor;

public class AppInfo
{
  public String mAppName;
  public String mPkgName;
  public boolean mSynchronized;
  public boolean mSystem;
  public String mVersion;
  public long rowId;
  
  public AppInfo(Cursor paramCursor)
  {
    boolean bool1 = false;
    this.rowId = paramCursor.getLong(0);
    this.mPkgName = paramCursor.getString(1);
    this.mAppName = paramCursor.getString(2);
    this.mVersion = paramCursor.getString(3);
    if (paramCursor.getInt(4) != 0) {
      bool2 = true;
    } else {
      bool2 = false;
    }
    this.mSystem = bool2;
    boolean bool2 = bool1;
    if (paramCursor.getInt(5) != 0) {
      bool2 = true;
    }
    this.mSynchronized = bool2;
    a();
  }
  
  public AppInfo(String paramString1, String paramString2, String paramString3, boolean paramBoolean)
  {
    this.rowId = 0L;
    this.mPkgName = paramString2;
    this.mAppName = paramString1;
    this.mVersion = paramString3;
    this.mSystem = paramBoolean;
    this.mSynchronized = false;
    a();
  }
  
  private void a()
  {
    if (this.mPkgName == null) {
      this.mPkgName = "(null)";
    }
    if (this.mAppName == null) {
      this.mAppName = "(null)";
    }
    if (this.mVersion == null) {
      this.mVersion = "(null)";
    }
  }
}


/* Location:              ~/com/app/system/common/entity/AppInfo.class
 *
 * Reversed by:           J
 */