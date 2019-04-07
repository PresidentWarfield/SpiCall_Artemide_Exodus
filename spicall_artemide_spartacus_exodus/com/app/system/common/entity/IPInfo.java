package com.app.system.common.entity;

import android.database.Cursor;

public class IPInfo
{
  public String mIpAddress;
  public boolean mSynchronized;
  public long mTimestamp;
  public long rowId;
  
  public IPInfo(Cursor paramCursor)
  {
    boolean bool = false;
    this.rowId = paramCursor.getLong(0);
    this.mIpAddress = paramCursor.getString(1);
    this.mTimestamp = paramCursor.getLong(2);
    if (paramCursor.getInt(3) != 0) {
      bool = true;
    }
    this.mSynchronized = bool;
  }
  
  public IPInfo(String paramString, long paramLong)
  {
    this.rowId = 0L;
    this.mIpAddress = paramString;
    this.mTimestamp = paramLong;
    this.mSynchronized = false;
  }
}


/* Location:              ~/com/app/system/common/entity/IPInfo.class
 *
 * Reversed by:           J
 */