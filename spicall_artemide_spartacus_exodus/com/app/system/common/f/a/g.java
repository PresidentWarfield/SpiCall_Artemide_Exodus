package com.app.system.common.f.a;

import android.content.Context;
import com.app.system.common.entity.PhotoLog;

public class g
  implements e
{
  private final String a;
  private final com.app.system.common.h.l b;
  private final PhotoLog c;
  
  public g(String paramString, com.app.system.common.h.l paraml, PhotoLog paramPhotoLog)
  {
    this.a = paramString;
    this.b = paraml;
    this.c = paramPhotoLog;
  }
  
  private void b(Context paramContext)
  {
    com.app.system.common.d.a.l locall = new com.app.system.common.d.a.l(null, this.a);
    PhotoLog localPhotoLog = this.c;
    if ((localPhotoLog != null) && (locall.a(paramContext, localPhotoLog) == 0)) {
      this.b.a(this.c.e());
    }
  }
  
  public void a(Context paramContext)
  {
    b(paramContext);
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool1 = false;
    if ((paramObject != null) && ((paramObject instanceof g)))
    {
      paramObject = (g)paramObject;
      boolean bool2 = bool1;
      if (this.a.equals(((g)paramObject).a))
      {
        bool2 = bool1;
        if (this.c.c().equals(((g)paramObject).c.c())) {
          bool2 = true;
        }
      }
      return bool2;
    }
    return false;
  }
  
  public String toString()
  {
    return String.format("FOTO=%s", new Object[] { this.c.c() });
  }
}


/* Location:              ~/com/app/system/common/f/a/g.class
 *
 * Reversed by:           J
 */