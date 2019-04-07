package com.app.system.common.h;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import com.android.system.PlannedRecTrigger;
import com.app.system.common.entity.PlannedRec;
import com.security.d.a;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class m
  extends b
{
  private final Context d;
  
  public m(Context paramContext)
  {
    super(paramContext);
    this.d = paramContext;
  }
  
  private String b(List<PlannedRec> paramList)
  {
    if ((paramList != null) && (!paramList.isEmpty()))
    {
      if (paramList.size() == 1)
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("=");
        ((StringBuilder)localObject).append(((PlannedRec)paramList.get(0)).rowId);
        return ((StringBuilder)localObject).toString();
      }
      Object localObject = new StringBuilder();
      paramList = paramList.iterator();
      while (paramList.hasNext())
      {
        PlannedRec localPlannedRec = (PlannedRec)paramList.next();
        ((StringBuilder)localObject).append(",");
        ((StringBuilder)localObject).append(localPlannedRec.rowId);
      }
      localObject = ((StringBuilder)localObject).substring(1);
      paramList = new StringBuilder();
      paramList.append(" IN (");
      paramList.append((String)localObject);
      paramList.append(')');
      return paramList.toString();
    }
    return "";
  }
  
  public PlannedRec a(long paramLong)
  {
    try
    {
      b();
      localObject = new java/lang/StringBuilder;
      ((StringBuilder)localObject).<init>();
      ((StringBuilder)localObject).append("SELECT rowid, plannedstart, planneddur, realstart, realdur, status, synchronized FROM plannedrecs WHERE rowid=");
      ((StringBuilder)localObject).append(paramLong);
      localObject = ((StringBuilder)localObject).toString();
      localObject = this.a.rawQuery((String)localObject, null);
      if (((Cursor)localObject).moveToNext())
      {
        localObject = new PlannedRec((Cursor)localObject);
        return (PlannedRec)localObject;
      }
    }
    catch (SQLException localSQLException)
    {
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append("selectRecord(");
      ((StringBuilder)localObject).append(paramLong);
      ((StringBuilder)localObject).append("): ");
      a.a("PlannedRecs", ((StringBuilder)localObject).toString(), new Object[] { localSQLException });
    }
    return null;
  }
  
  public boolean a(PlannedRec paramPlannedRec)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Inserimento nuova PlannedRec: ");
    ((StringBuilder)localObject).append(paramPlannedRec.toString());
    a.d("PlannedRecs", ((StringBuilder)localObject).toString(), new Object[0]);
    boolean bool = true;
    try
    {
      b();
      localObject = this.a.compileStatement("INSERT INTO plannedrecs VALUES (?,?,(select coalesce(max(slice)+1,1) from plannedrecs where plannedstart=?),?,?,?,?)");
      ((SQLiteStatement)localObject).bindLong(1, paramPlannedRec.plannedStart);
      ((SQLiteStatement)localObject).bindLong(2, paramPlannedRec.plannedDur);
      ((SQLiteStatement)localObject).bindLong(3, paramPlannedRec.plannedStart);
      ((SQLiteStatement)localObject).bindLong(4, paramPlannedRec.realStart);
      ((SQLiteStatement)localObject).bindLong(5, paramPlannedRec.realDur);
      ((SQLiteStatement)localObject).bindLong(6, paramPlannedRec.status);
      if (paramPlannedRec.synchronised) {
        l = 1L;
      } else {
        l = 0L;
      }
      ((SQLiteStatement)localObject).bindLong(7, l);
      paramPlannedRec.rowId = ((SQLiteStatement)localObject).executeInsert();
      long l = paramPlannedRec.rowId;
      if (l == -1L) {
        bool = false;
      }
      return bool;
    }
    catch (SQLException paramPlannedRec)
    {
      a.a("PlannedRecs", "insert:", new Object[] { paramPlannedRec });
    }
    return false;
  }
  
  public boolean a(List<PlannedRec> paramList)
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
      ((StringBuilder)localObject).append("UPDATE plannedrecs SET synchronized = 1 WHERE rowid");
      ((StringBuilder)localObject).append(str);
      localObject = ((StringBuilder)localObject).toString();
      int i = this.a.compileStatement((String)localObject).executeUpdateDelete();
      int j = paramList.size();
      if (i == j) {
        bool = true;
      }
      return bool;
    }
    catch (SQLException paramList)
    {
      a.a("PlannedRecs", "markAsSynchronized:", new Object[] { paramList });
    }
    return false;
  }
  
  public boolean b(PlannedRec paramPlannedRec)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Aggiornamento PlannedRec: ");
    ((StringBuilder)localObject).append(paramPlannedRec.toString());
    a.d("PlannedRecs", ((StringBuilder)localObject).toString(), new Object[0]);
    boolean bool = true;
    try
    {
      b();
      localObject = this.a.compileStatement("UPDATE plannedrecs SET plannedstart=?, planneddur=?, realstart=?, realdur=?, status=?, synchronized=? WHERE rowid=?");
      ((SQLiteStatement)localObject).bindLong(1, paramPlannedRec.plannedStart);
      ((SQLiteStatement)localObject).bindLong(2, paramPlannedRec.plannedDur);
      ((SQLiteStatement)localObject).bindLong(3, paramPlannedRec.realStart);
      ((SQLiteStatement)localObject).bindLong(4, paramPlannedRec.realDur);
      ((SQLiteStatement)localObject).bindLong(5, paramPlannedRec.status);
      long l;
      if (paramPlannedRec.synchronised) {
        l = 1L;
      } else {
        l = 0L;
      }
      ((SQLiteStatement)localObject).bindLong(6, l);
      ((SQLiteStatement)localObject).bindLong(7, paramPlannedRec.rowId);
      int i = ((SQLiteStatement)localObject).executeUpdateDelete();
      if (i != 1) {
        bool = false;
      }
      return bool;
    }
    catch (SQLException paramPlannedRec)
    {
      a.a("PlannedRecs", "update:", new Object[] { paramPlannedRec });
    }
    return false;
  }
  
  public List<PlannedRec> c()
  {
    localArrayList = new ArrayList();
    try
    {
      b();
      Cursor localCursor = this.a.rawQuery("SELECT rowid, plannedstart, planneddur, slice, realstart, realdur, status, synchronized FROM plannedrecs WHERE synchronized = 0 ORDER BY plannedstart", null);
      while (localCursor.moveToNext())
      {
        PlannedRec localPlannedRec = new com/app/system/common/entity/PlannedRec;
        localPlannedRec.<init>(localCursor);
        localArrayList.add(localPlannedRec);
      }
      return localArrayList;
    }
    catch (SQLException localSQLException)
    {
      a.a("PlannedRecs", "getAllPlannedRecs:", new Object[] { localSQLException });
    }
  }
  
  public List<PlannedRec> d()
  {
    localArrayList = new ArrayList();
    try
    {
      b();
      Cursor localCursor = this.a.rawQuery("SELECT rowid, plannedstart, planneddur, slice, realstart, realdur, status, synchronized FROM plannedrecs WHERE status = 1 ORDER BY plannedstart, slice", null);
      while (localCursor.moveToNext())
      {
        PlannedRec localPlannedRec = new com/app/system/common/entity/PlannedRec;
        localPlannedRec.<init>(localCursor);
        localArrayList.add(localPlannedRec);
      }
      return localArrayList;
    }
    catch (SQLException localSQLException)
    {
      a.a("PlannedRecs", "getAllPlannedRecs:", new Object[] { localSQLException });
    }
  }
  
  public void e()
  {
    try
    {
      b();
      int i = this.a.compileStatement("DELETE FROM plannedrecs WHERE status <> 1 AND synchronized = 1").executeUpdateDelete();
      StringBuilder localStringBuilder = new java/lang/StringBuilder;
      localStringBuilder.<init>();
      localStringBuilder.append("Cancellati ");
      localStringBuilder.append(i);
      localStringBuilder.append(" record da PlannedRecs");
      a.d("PlannedRecs", localStringBuilder.toString(), new Object[0]);
    }
    catch (SQLException localSQLException)
    {
      a.a("PlannedRecs", "deleteClosedRecs:", new Object[] { localSQLException });
    }
  }
  
  public void f()
  {
    a.d("PlannedRecs", "Schedulo registrazioni pianificate all'avvio:", new Object[0]);
    Object localObject = d();
    if (((List)localObject).isEmpty())
    {
      a.d("PlannedRecs", "Non ci sono registrazioni pianificate", new Object[0]);
    }
    else
    {
      Iterator localIterator = ((List)localObject).iterator();
      while (localIterator.hasNext())
      {
        localObject = (PlannedRec)localIterator.next();
        PlannedRecTrigger.a(this.d, (PlannedRec)localObject);
      }
    }
  }
}


/* Location:              ~/com/app/system/common/h/m.class
 *
 * Reversed by:           J
 */