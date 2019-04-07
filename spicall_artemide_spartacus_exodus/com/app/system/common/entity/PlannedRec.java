package com.app.system.common.entity;

import android.database.Cursor;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PlannedRec
{
  public static final int CANCELLED = 0;
  public static final int COMPLETED = 2;
  private static final SimpleDateFormat DF = new SimpleDateFormat("HH:mm:SS dd/MM/yyyy");
  public static final int FAILED = -1;
  public static final int PLANNED = 1;
  public int plannedDur;
  public long plannedStart;
  public int realDur;
  public long realStart;
  public long rowId;
  public int slice;
  public int status;
  public boolean synchronised;
  
  public PlannedRec(long paramLong, int paramInt1, int paramInt2)
  {
    this.rowId = 0L;
    this.plannedStart = 0L;
    this.plannedDur = 0;
    this.slice = 0;
    this.realStart = paramLong;
    this.realDur = paramInt1;
    this.status = paramInt2;
    this.synchronised = false;
  }
  
  public PlannedRec(Cursor paramCursor)
  {
    boolean bool = false;
    this.rowId = paramCursor.getLong(0);
    this.plannedStart = paramCursor.getLong(1);
    this.plannedDur = paramCursor.getInt(2);
    this.slice = paramCursor.getInt(3);
    this.realStart = paramCursor.getLong(4);
    this.realDur = paramCursor.getInt(5);
    this.status = paramCursor.getInt(6);
    if (paramCursor.getInt(7) != 0) {
      bool = true;
    }
    this.synchronised = bool;
  }
  
  public static String a(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("UNKNOWN ");
      localStringBuilder.append(paramInt);
      return localStringBuilder.toString();
    case 2: 
      return "COMPLETED";
    case 1: 
      return "PLANNED";
    case 0: 
      return "CANCELLED";
    }
    return "FAILED";
  }
  
  public String toString()
  {
    long l = this.plannedStart;
    String str1;
    if (l == 0L) {
      str1 = "0";
    } else {
      str1 = DF.format(new Date(l * 1000L));
    }
    l = this.realStart;
    String str2;
    if (l == 0L) {
      str2 = "0";
    } else {
      str2 = DF.format(new Date(l * 1000L));
    }
    return String.format("{ rowId=%d, plannedStart=%s, plannedDur=%d, slice=%d, realStart=%s, realDur=%d, status=%s, synchronised=%b", new Object[] { Long.valueOf(this.rowId), str1, Integer.valueOf(this.plannedDur), Integer.valueOf(this.slice), str2, Integer.valueOf(this.realDur), a(this.status), Boolean.valueOf(this.synchronised) });
  }
}


/* Location:              ~/com/app/system/common/entity/PlannedRec.class
 *
 * Reversed by:           J
 */