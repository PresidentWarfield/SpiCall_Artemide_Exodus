package com.app.system.common.h;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import com.app.system.common.entity.FileEntry;
import com.security.d.a;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class g
  extends b
{
  public g(Context paramContext)
  {
    super(paramContext);
  }
  
  private String a(List<FileEntry> paramList)
  {
    if ((paramList != null) && (!paramList.isEmpty()))
    {
      if (paramList.size() == 1)
      {
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("=");
        localStringBuilder.append(((FileEntry)paramList.get(0)).rowId);
        return localStringBuilder.toString();
      }
      StringBuilder localStringBuilder = new StringBuilder();
      paramList = paramList.iterator();
      while (paramList.hasNext())
      {
        FileEntry localFileEntry = (FileEntry)paramList.next();
        localStringBuilder.append(",");
        localStringBuilder.append(localFileEntry.rowId);
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
  
  private boolean a(List<FileEntry> paramList, String paramString)
  {
    boolean bool = false;
    try
    {
      String str = a(paramList);
      if (str.isEmpty()) {
        return true;
      }
      b();
      SQLiteDatabase localSQLiteDatabase = this.a;
      StringBuilder localStringBuilder = new java/lang/StringBuilder;
      localStringBuilder.<init>();
      localStringBuilder.append("UPDATE FileSystem SET ");
      localStringBuilder.append(paramString);
      localStringBuilder.append("=1 WHERE rowId");
      localStringBuilder.append(str);
      int i = localSQLiteDatabase.compileStatement(localStringBuilder.toString().toLowerCase()).executeUpdateDelete();
      int j = paramList.size();
      if (i == j) {
        bool = true;
      }
      return bool;
    }
    catch (SQLException localSQLException)
    {
      paramList = new StringBuilder();
      paramList.append("mark as ");
      paramList.append(paramString);
      paramList.append(":");
      a.a("FS", paramList.toString(), new Object[] { localSQLException });
    }
    return false;
  }
  
  public List<FileEntry> a(int paramInt)
  {
    localArrayList = new ArrayList();
    try
    {
      b();
      Object localObject = new java/lang/StringBuilder;
      ((StringBuilder)localObject).<init>();
      ((StringBuilder)localObject).append("SELECT rowId, fullPath, size, lastMod, notified, synchronized, priority FROM FileSystem WHERE size > 0 AND synchronized = 0 ORDER BY priority DESC, fullPath LIMIT ");
      ((StringBuilder)localObject).append(paramInt);
      localObject = ((StringBuilder)localObject).toString();
      Cursor localCursor = this.a.rawQuery(((String)localObject).toLowerCase(), null);
      while (localCursor.moveToNext())
      {
        localObject = new com/app/system/common/entity/FileEntry;
        ((FileEntry)localObject).<init>(localCursor);
        localArrayList.add(localObject);
      }
      return localArrayList;
    }
    catch (SQLException localSQLException)
    {
      a.a("FS", "getUnsynchronizedEntries:", new Object[] { localSQLException });
    }
  }
  
  public boolean a(FileEntry paramFileEntry)
  {
    try
    {
      long l1 = paramFileEntry.mSize;
      long l2 = 0L;
      if (l1 == 0L) {
        return true;
      }
      b();
      SQLiteStatement localSQLiteStatement = this.a.compileStatement("INSERT INTO filesystem VALUES (?, ?, ?, ?, ?, ?)");
      localSQLiteStatement.bindString(1, paramFileEntry.mFileName);
      localSQLiteStatement.bindLong(2, paramFileEntry.mSize);
      localSQLiteStatement.bindLong(3, paramFileEntry.mLastMod);
      if (paramFileEntry.mNotified) {
        l1 = 1L;
      } else {
        l1 = 0L;
      }
      localSQLiteStatement.bindLong(4, l1);
      l1 = l2;
      if (paramFileEntry.mSynchronized) {
        l1 = 1L;
      }
      localSQLiteStatement.bindLong(5, l1);
      localSQLiteStatement.bindLong(6, paramFileEntry.mPriority);
      if (localSQLiteStatement.executeInsert() == -1L)
      {
        boolean bool = b(paramFileEntry);
        return bool;
      }
      return true;
    }
    catch (SQLException paramFileEntry)
    {
      a.a("FS", "addOrUpdate:", new Object[] { paramFileEntry });
      return false;
    }
    catch (SQLiteConstraintException localSQLiteConstraintException) {}
    return b(paramFileEntry);
  }
  
  public boolean b(FileEntry paramFileEntry)
  {
    boolean bool1 = false;
    try
    {
      b();
      SQLiteStatement localSQLiteStatement = this.a.compileStatement("UPDATE filesystem SET size=?, lastmod=?, notified=?, synchronized=?, priority=? WHERE fullpath=? AND lastmod<?");
      localSQLiteStatement.bindLong(1, paramFileEntry.mSize);
      localSQLiteStatement.bindLong(2, paramFileEntry.mLastMod);
      boolean bool2 = paramFileEntry.mNotified;
      long l1 = 1L;
      long l2;
      if (bool2) {
        l2 = 1L;
      } else {
        l2 = 0L;
      }
      localSQLiteStatement.bindLong(3, l2);
      if (paramFileEntry.mSynchronized) {
        l2 = l1;
      } else {
        l2 = 0L;
      }
      localSQLiteStatement.bindLong(4, l2);
      localSQLiteStatement.bindLong(5, paramFileEntry.mPriority);
      localSQLiteStatement.bindString(6, paramFileEntry.mFileName);
      localSQLiteStatement.bindLong(7, paramFileEntry.mLastMod);
      int i = localSQLiteStatement.executeUpdateDelete();
      if (i == 1) {
        bool1 = true;
      }
      return bool1;
    }
    catch (SQLException paramFileEntry)
    {
      a.a("FS", "update:", new Object[] { paramFileEntry });
    }
    return false;
  }
  
  public boolean b(String paramString)
  {
    try
    {
      b();
      SQLiteStatement localSQLiteStatement = this.a.compileStatement("DELETE FROM filesystem WHERE fullpath=?");
      localSQLiteStatement.bindString(1, paramString);
      int i = localSQLiteStatement.executeUpdateDelete();
      if (i != 1)
      {
        a.a("FS", String.format("delete() ha eliminato %d record!", new Object[] { Integer.valueOf(i) }), new Object[0]);
        return false;
      }
      return true;
    }
    catch (SQLException paramString)
    {
      a.a("FS", "delete:", new Object[] { paramString });
    }
    return false;
  }
  
  public FileEntry c(String paramString)
  {
    try
    {
      b();
      paramString = this.a.rawQuery("SELECT rowId, fullPath, size, lastMod, notified, synchronized, priority FROM FileSystem WHERE fullPath=?".toLowerCase(), new String[] { paramString });
      if (paramString.moveToNext())
      {
        paramString = new FileEntry(paramString);
        return paramString;
      }
    }
    catch (SQLException paramString)
    {
      a.a("FS", "getUnsynchronizedEntries:", new Object[] { paramString });
    }
    return null;
  }
  
  public boolean c(FileEntry paramFileEntry)
  {
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(paramFileEntry);
    return a(localArrayList, "synchronized");
  }
}


/* Location:              ~/com/app/system/common/h/g.class
 *
 * Reversed by:           J
 */