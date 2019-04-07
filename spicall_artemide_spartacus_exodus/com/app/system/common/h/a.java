package com.app.system.common.h;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.app.system.common.c;
import java.io.File;

public class a
  extends SQLiteOpenHelper
{
  private static a a;
  
  a(Context paramContext)
  {
    super(paramContext, "core.db", null, 2);
  }
  
  public static a a(Context paramContext)
  {
    if (a == null) {
      a = new a(paramContext.getApplicationContext());
    }
    return a;
  }
  
  public void onCreate(SQLiteDatabase paramSQLiteDatabase)
  {
    try
    {
      File localFile = new java/io/File;
      paramSQLiteDatabase = new java/lang/StringBuilder;
      paramSQLiteDatabase.<init>();
      paramSQLiteDatabase.append(c.h);
      paramSQLiteDatabase.append("core.db");
      localFile.<init>(paramSQLiteDatabase.toString());
      if (!localFile.exists()) {
        com.security.d.a.d("DbHelper", "CREATE DATABASE", new Object[0]);
      }
    }
    catch (SQLException paramSQLiteDatabase)
    {
      paramSQLiteDatabase.printStackTrace();
    }
  }
  
  public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Upgrading database from version ");
    localStringBuilder.append(paramInt1);
    localStringBuilder.append(" to ");
    localStringBuilder.append(paramInt2);
    localStringBuilder.append(", which will destroy all old data");
    com.security.d.a.b("DbHelper", localStringBuilder.toString(), new Object[0]);
    onCreate(paramSQLiteDatabase);
  }
}


/* Location:              ~/com/app/system/common/h/a.class
 *
 * Reversed by:           J
 */