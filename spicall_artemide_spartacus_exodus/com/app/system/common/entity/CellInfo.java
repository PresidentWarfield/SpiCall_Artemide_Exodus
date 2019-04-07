package com.app.system.common.entity;

import android.database.Cursor;
import java.util.Date;

public class CellInfo
{
  public int CID;
  public int LAC;
  public int MCC;
  public int MNC;
  public long date;
  public long rowId;
  
  public CellInfo(Cursor paramCursor)
  {
    this.rowId = paramCursor.getLong(0);
    this.date = paramCursor.getLong(1);
    this.LAC = paramCursor.getInt(2);
    this.CID = paramCursor.getInt(3);
    this.MCC = paramCursor.getInt(4);
    this.MNC = paramCursor.getInt(5);
  }
  
  public CellInfo(Date paramDate, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.rowId = 0L;
    this.date = (paramDate.getTime() / 1000L);
    this.LAC = paramInt1;
    this.CID = paramInt2;
    this.MCC = paramInt3;
    this.MNC = paramInt4;
  }
}


/* Location:              ~/com/app/system/common/entity/CellInfo.class
 *
 * Reversed by:           J
 */