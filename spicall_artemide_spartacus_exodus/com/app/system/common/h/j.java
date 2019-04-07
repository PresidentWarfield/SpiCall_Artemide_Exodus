package com.app.system.common.h;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import com.app.system.common.entity.IPInfo;
import com.security.d.a;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class j
  extends b
{
  public j(Context paramContext)
  {
    super(paramContext);
  }
  
  private String b(List<IPInfo> paramList)
  {
    if ((paramList != null) && (!paramList.isEmpty()))
    {
      if (paramList.size() == 1)
      {
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("=");
        localStringBuilder.append(((IPInfo)paramList.get(0)).rowId);
        return localStringBuilder.toString();
      }
      StringBuilder localStringBuilder = new StringBuilder();
      Iterator localIterator = paramList.iterator();
      while (localIterator.hasNext())
      {
        paramList = (IPInfo)localIterator.next();
        localStringBuilder.append(",");
        localStringBuilder.append(paramList.rowId);
      }
      paramList = localStringBuilder.substring(1);
      localStringBuilder = new StringBuilder();
      localStringBuilder.append(" IN (");
      localStringBuilder.append(paramList);
      localStringBuilder.append(')');
      return localStringBuilder.toString();
    }
    return "";
  }
  
  public List<IPInfo> a(int paramInt)
  {
    localArrayList = new ArrayList();
    try
    {
      b();
      Object localObject = new java/lang/StringBuilder;
      ((StringBuilder)localObject).<init>();
      ((StringBuilder)localObject).append("SELECT rowId, ipAddress, timestamp, synchronized FROM IPAddresses WHERE synchronized = 0 ORDER BY timestamp LIMIT ");
      ((StringBuilder)localObject).append(paramInt);
      localObject = ((StringBuilder)localObject).toString();
      Cursor localCursor = this.a.rawQuery(((String)localObject).toLowerCase(), null);
      while (localCursor.moveToNext())
      {
        localObject = new com/app/system/common/entity/IPInfo;
        ((IPInfo)localObject).<init>(localCursor);
        localArrayList.add(localObject);
      }
      return localArrayList;
    }
    catch (SQLException localSQLException)
    {
      a.a("TempDbIP", "getUnsynchronizedEntries:", new Object[] { localSQLException });
    }
  }
  
  public boolean a(IPInfo paramIPInfo)
  {
    boolean bool = false;
    try
    {
      b();
      SQLiteStatement localSQLiteStatement = this.a.compileStatement("INSERT INTO ipaddresses VALUES (?, ?, ?)");
      localSQLiteStatement.bindString(1, paramIPInfo.mIpAddress);
      localSQLiteStatement.bindLong(2, paramIPInfo.mTimestamp);
      if (paramIPInfo.mSynchronized) {
        l = 1L;
      } else {
        l = 0L;
      }
      localSQLiteStatement.bindLong(3, l);
      long l = localSQLiteStatement.executeInsert();
      if (l != -1L) {
        bool = true;
      }
      return bool;
    }
    catch (SQLException paramIPInfo)
    {
      a.a("TempDbIP", "insert:", new Object[] { paramIPInfo });
    }
    return false;
  }
  
  public boolean a(List<IPInfo> paramList)
  {
    boolean bool = false;
    try
    {
      String str = b(paramList);
      if (str.isEmpty()) {
        return true;
      }
      b();
      SQLiteDatabase localSQLiteDatabase = this.a;
      StringBuilder localStringBuilder = new java/lang/StringBuilder;
      localStringBuilder.<init>();
      localStringBuilder.append("UPDATE ipaddresses SET synchronized=1 WHERE rowid");
      localStringBuilder.append(str);
      int i = localSQLiteDatabase.compileStatement(localStringBuilder.toString()).executeUpdateDelete();
      int j = paramList.size();
      if (i == j) {
        bool = true;
      }
      return bool;
    }
    catch (SQLException paramList)
    {
      a.a("TempDbIP", "mark as synchronized:", new Object[] { paramList });
    }
    return false;
  }
}


/* Location:              ~/com/app/system/common/h/j.class
 *
 * Reversed by:           J
 */