package io.fabric.sdk.android.services.d;

import android.content.Context;
import io.fabric.sdk.android.c;
import io.fabric.sdk.android.h;
import io.fabric.sdk.android.k;
import java.io.File;

public class b
  implements a
{
  private final Context a;
  private final String b;
  private final String c;
  
  public b(h paramh)
  {
    if (paramh.getContext() != null)
    {
      this.a = paramh.getContext();
      this.b = paramh.getPath();
      paramh = new StringBuilder();
      paramh.append("Android/");
      paramh.append(this.a.getPackageName());
      this.c = paramh.toString();
      return;
    }
    throw new IllegalStateException("Cannot get directory before context has been set. Call Fabric.with() first");
  }
  
  public File a()
  {
    return a(this.a.getFilesDir());
  }
  
  File a(File paramFile)
  {
    if (paramFile != null)
    {
      if ((!paramFile.exists()) && (!paramFile.mkdirs())) {
        c.g().d("Fabric", "Couldn't create file");
      } else {
        return paramFile;
      }
    }
    else {
      c.g().a("Fabric", "Null File");
    }
    return null;
  }
}


/* Location:              ~/io/fabric/sdk/android/services/d/b.class
 *
 * Reversed by:           J
 */