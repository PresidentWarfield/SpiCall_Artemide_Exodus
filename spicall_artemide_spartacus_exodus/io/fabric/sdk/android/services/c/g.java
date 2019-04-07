package io.fabric.sdk.android.services.c;

import android.content.Context;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

public class g
  extends h
{
  public g(Context paramContext, File paramFile, String paramString1, String paramString2)
  {
    super(paramContext, paramFile, paramString1, paramString2);
  }
  
  public OutputStream a(File paramFile)
  {
    return new GZIPOutputStream(new FileOutputStream(paramFile));
  }
}


/* Location:              ~/io/fabric/sdk/android/services/c/g.class
 *
 * Reversed by:           J
 */