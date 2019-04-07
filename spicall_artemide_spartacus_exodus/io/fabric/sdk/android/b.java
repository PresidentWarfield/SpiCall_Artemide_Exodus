package io.fabric.sdk.android;

import android.util.Log;

public class b
  implements k
{
  private int a;
  
  public b()
  {
    this.a = 4;
  }
  
  public b(int paramInt)
  {
    this.a = paramInt;
  }
  
  public void a(int paramInt, String paramString1, String paramString2)
  {
    a(paramInt, paramString1, paramString2, false);
  }
  
  public void a(int paramInt, String paramString1, String paramString2, boolean paramBoolean)
  {
    if ((paramBoolean) || (a(paramString1, paramInt))) {
      Log.println(paramInt, paramString1, paramString2);
    }
  }
  
  public void a(String paramString1, String paramString2)
  {
    a(paramString1, paramString2, null);
  }
  
  public void a(String paramString1, String paramString2, Throwable paramThrowable)
  {
    if (a(paramString1, 3)) {
      Log.d(paramString1, paramString2, paramThrowable);
    }
  }
  
  public boolean a(String paramString, int paramInt)
  {
    boolean bool;
    if (this.a <= paramInt) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public void b(String paramString1, String paramString2)
  {
    b(paramString1, paramString2, null);
  }
  
  public void b(String paramString1, String paramString2, Throwable paramThrowable)
  {
    if (a(paramString1, 2)) {
      Log.v(paramString1, paramString2, paramThrowable);
    }
  }
  
  public void c(String paramString1, String paramString2)
  {
    c(paramString1, paramString2, null);
  }
  
  public void c(String paramString1, String paramString2, Throwable paramThrowable)
  {
    if (a(paramString1, 4)) {
      Log.i(paramString1, paramString2, paramThrowable);
    }
  }
  
  public void d(String paramString1, String paramString2)
  {
    d(paramString1, paramString2, null);
  }
  
  public void d(String paramString1, String paramString2, Throwable paramThrowable)
  {
    if (a(paramString1, 5)) {
      Log.w(paramString1, paramString2, paramThrowable);
    }
  }
  
  public void e(String paramString1, String paramString2)
  {
    e(paramString1, paramString2, null);
  }
  
  public void e(String paramString1, String paramString2, Throwable paramThrowable)
  {
    if (a(paramString1, 6)) {
      Log.e(paramString1, paramString2, paramThrowable);
    }
  }
}


/* Location:              ~/io/fabric/sdk/android/b.class
 *
 * Reversed by:           J
 */