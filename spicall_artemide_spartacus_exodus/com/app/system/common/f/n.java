package com.app.system.common.f;

import android.content.Context;
import com.app.system.common.d.a.k;
import com.app.system.common.entity.ScreenStatus;
import com.app.system.common.h;

public class n
{
  private static ScreenStatus a;
  private static boolean b = false;
  
  public static void a(final Context paramContext)
  {
    if ((a != null) && (!b))
    {
      new Thread(new Runnable()
      {
        public void run()
        {
          try
          {
            if (this.a.a(paramContext, n.a()) == 1) {
              n.a(true);
            }
            return;
          }
          finally {}
        }
      }).start();
      return;
    }
  }
  
  public static void a(Context paramContext, ScreenStatus paramScreenStatus)
  {
    if (paramScreenStatus == null) {
      return;
    }
    try
    {
      if ((a == null) || (!paramScreenStatus.equals(a)))
      {
        a = paramScreenStatus;
        b = false;
      }
      if (!b) {
        a(paramContext);
      }
      return;
    }
    finally {}
  }
}


/* Location:              ~/com/app/system/common/f/n.class
 *
 * Reversed by:           J
 */