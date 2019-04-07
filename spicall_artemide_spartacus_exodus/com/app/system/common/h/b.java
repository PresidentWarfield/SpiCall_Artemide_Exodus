package com.app.system.common.h;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class b
{
  SQLiteDatabase a;
  a b;
  String c;
  
  public b(Context paramContext)
  {
    this.b = a.a(paramContext);
  }
  
  public b(String paramString)
  {
    this.c = paramString;
  }
  
  public static String a(String paramString)
  {
    return paramString.replace("'", "").replace("\"", "");
  }
  
  public void a() {}
  
  public void a(InputStream paramInputStream, OutputStream paramOutputStream)
  {
    byte[] arrayOfByte = new byte['Ѐ'];
    for (;;)
    {
      int i = paramInputStream.read(arrayOfByte);
      if (i <= 0) {
        break;
      }
      paramOutputStream.write(arrayOfByte, 0, i);
    }
    paramInputStream.close();
    paramOutputStream.close();
  }
  
  public boolean a(Context paramContext)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append(this.c);
    ((StringBuilder)localObject).append("core.db");
    if (!new File(((StringBuilder)localObject).toString()).exists())
    {
      new File(this.c).mkdirs();
      try
      {
        localObject = paramContext.getAssets().open("core.db");
        FileOutputStream localFileOutputStream = new java/io/FileOutputStream;
        paramContext = new java/lang/StringBuilder;
        paramContext.<init>();
        paramContext.append(this.c);
        paramContext.append("core.db");
        localFileOutputStream.<init>(paramContext.toString());
        a((InputStream)localObject, localFileOutputStream);
        localObject = new java/io/File;
        paramContext = new java/lang/StringBuilder;
        paramContext.<init>();
        paramContext.append(this.c);
        paramContext.append("core.db");
        ((File)localObject).<init>(paramContext.toString());
        if (((File)localObject).exists())
        {
          com.security.d.a.d("FileDB", "Successfull create database", new Object[0]);
          return true;
        }
      }
      catch (IOException paramContext)
      {
        paramContext.printStackTrace();
      }
      catch (FileNotFoundException paramContext)
      {
        paramContext.printStackTrace();
      }
    }
    return false;
  }
  
  public void b()
  {
    this.a = this.b.getWritableDatabase();
  }
}


/* Location:              ~/com/app/system/common/h/b.class
 *
 * Reversed by:           J
 */