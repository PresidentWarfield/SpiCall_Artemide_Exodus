package io.fabric.sdk.android.services.b;

import android.os.SystemClock;
import android.util.Log;

public class w
{
  private final String a;
  private final String b;
  private final boolean c;
  private long d;
  private long e;
  
  public w(String paramString1, String paramString2)
  {
    this.a = paramString1;
    this.b = paramString2;
    this.c = (Log.isLoggable(paramString2, 2) ^ true);
  }
  
  private void c()
  {
    String str = this.b;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(this.a);
    localStringBuilder.append(": ");
    localStringBuilder.append(this.e);
    localStringBuilder.append("ms");
    Log.v(str, localStringBuilder.toString());
  }
  
  public void a()
  {
    try
    {
      boolean bool = this.c;
      if (bool) {
        return;
      }
      this.d = SystemClock.elapsedRealtime();
      this.e = 0L;
      return;
    }
    finally {}
  }
  
  public void b()
  {
    try
    {
      boolean bool = this.c;
      if (bool) {
        return;
      }
      long l = this.e;
      if (l != 0L) {
        return;
      }
      this.e = (SystemClock.elapsedRealtime() - this.d);
      c();
      return;
    }
    finally {}
  }
}


/* Location:              ~/io/fabric/sdk/android/services/b/w.class
 *
 * Reversed by:           J
 */