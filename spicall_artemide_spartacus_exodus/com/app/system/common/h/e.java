package com.app.system.common.h;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import com.app.system.common.entity.CellInfo;
import com.security.d.a;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class e
  extends b
{
  public e(Context paramContext)
  {
    super(paramContext);
  }
  
  private String b(List<CellInfo> paramList)
  {
    if ((paramList != null) && (!paramList.isEmpty()))
    {
      if (paramList.size() == 1)
      {
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("=");
        localStringBuilder.append(((CellInfo)paramList.get(0)).rowId);
        return localStringBuilder.toString();
      }
      StringBuilder localStringBuilder = new StringBuilder();
      Iterator localIterator = paramList.iterator();
      while (localIterator.hasNext())
      {
        paramList = (CellInfo)localIterator.next();
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
  
  public boolean a(CellInfo paramCellInfo)
  {
    boolean bool = false;
    try
    {
      b();
      SQLiteStatement localSQLiteStatement = this.a.compileStatement("INSERT INTO cellinfo VALUES (?,?,?,?,?)");
      localSQLiteStatement.bindLong(1, paramCellInfo.date);
      localSQLiteStatement.bindLong(2, paramCellInfo.LAC);
      localSQLiteStatement.bindLong(3, paramCellInfo.CID);
      localSQLiteStatement.bindLong(4, paramCellInfo.MCC);
      localSQLiteStatement.bindLong(5, paramCellInfo.MNC);
      paramCellInfo.rowId = localSQLiteStatement.executeInsert();
      long l = paramCellInfo.rowId;
      if (l != -1L) {
        bool = true;
      }
      return bool;
    }
    catch (SQLException paramCellInfo)
    {
      a.a("DBCellInfo", "insert:", new Object[] { paramCellInfo });
    }
    return false;
  }
  
  public boolean a(List<CellInfo> paramList)
  {
    String str = b(paramList);
    if (str.isEmpty()) {
      return true;
    }
    boolean bool = false;
    try
    {
      b();
      Object localObject = new java/lang/StringBuilder;
      ((StringBuilder)localObject).<init>();
      ((StringBuilder)localObject).append("DELETE FROM cellinfo WHERE rowid");
      ((StringBuilder)localObject).append(str);
      localObject = ((StringBuilder)localObject).toString();
      int i = this.a.compileStatement((String)localObject).executeUpdateDelete();
      a.d("DBCellInfo", String.format("Cancellati %d record", new Object[] { Integer.valueOf(i) }), new Object[0]);
      int j = paramList.size();
      if (i == j) {
        bool = true;
      }
      return bool;
    }
    catch (SQLException paramList)
    {
      a.a("DBCellInfo", "deleteByList:", new Object[] { paramList });
    }
    return false;
  }
  
  public List<CellInfo> c()
  {
    localArrayList = new ArrayList();
    try
    {
      b();
      Cursor localCursor = this.a.rawQuery("SELECT rowid, date, lac, cid, mcc, mnc FROM cellinfo ORDER BY date", null);
      while (localCursor.moveToNext())
      {
        CellInfo localCellInfo = new com/app/system/common/entity/CellInfo;
        localCellInfo.<init>(localCursor);
        localArrayList.add(localCellInfo);
      }
      return localArrayList;
    }
    catch (SQLException localSQLException)
    {
      a.a("DBCellInfo", "selectAll:", new Object[] { localSQLException });
    }
  }
}


/* Location:              ~/com/app/system/common/h/e.class
 *
 * Reversed by:           J
 */