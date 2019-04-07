package io.fabric.sdk.android.services.network;

import io.fabric.sdk.android.k;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

public class b
  implements d
{
  private final k a;
  private f b;
  private SSLSocketFactory c;
  private boolean d;
  
  public b()
  {
    this(new io.fabric.sdk.android.b());
  }
  
  public b(k paramk)
  {
    this.a = paramk;
  }
  
  private void a()
  {
    try
    {
      this.d = false;
      this.c = null;
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  private boolean a(String paramString)
  {
    boolean bool;
    if ((paramString != null) && (paramString.toLowerCase(Locale.US).startsWith("https"))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private SSLSocketFactory b()
  {
    try
    {
      if ((this.c == null) && (!this.d)) {
        this.c = c();
      }
      SSLSocketFactory localSSLSocketFactory = this.c;
      return localSSLSocketFactory;
    }
    finally {}
  }
  
  /* Error */
  private SSLSocketFactory c()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: iconst_1
    //   4: putfield 31	io/fabric/sdk/android/services/network/b:d	Z
    //   7: aload_0
    //   8: getfield 58	io/fabric/sdk/android/services/network/b:b	Lio/fabric/sdk/android/services/network/f;
    //   11: invokestatic 63	io/fabric/sdk/android/services/network/e:a	(Lio/fabric/sdk/android/services/network/f;)Ljavax/net/ssl/SSLSocketFactory;
    //   14: astore_1
    //   15: aload_0
    //   16: getfield 29	io/fabric/sdk/android/services/network/b:a	Lio/fabric/sdk/android/k;
    //   19: ldc 65
    //   21: ldc 67
    //   23: invokeinterface 72 3 0
    //   28: aload_0
    //   29: monitorexit
    //   30: aload_1
    //   31: areturn
    //   32: astore_1
    //   33: aload_0
    //   34: getfield 29	io/fabric/sdk/android/services/network/b:a	Lio/fabric/sdk/android/k;
    //   37: ldc 65
    //   39: ldc 74
    //   41: aload_1
    //   42: invokeinterface 78 4 0
    //   47: aload_0
    //   48: monitorexit
    //   49: aconst_null
    //   50: areturn
    //   51: astore_1
    //   52: aload_0
    //   53: monitorexit
    //   54: aload_1
    //   55: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	56	0	this	b
    //   14	17	1	localSSLSocketFactory	SSLSocketFactory
    //   32	10	1	localException	Exception
    //   51	4	1	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   7	28	32	java/lang/Exception
    //   2	7	51	finally
    //   7	28	51	finally
    //   33	47	51	finally
  }
  
  public HttpRequest a(c paramc, String paramString)
  {
    return a(paramc, paramString, Collections.emptyMap());
  }
  
  public HttpRequest a(c paramc, String paramString, Map<String, String> paramMap)
  {
    switch (1.a[paramc.ordinal()])
    {
    default: 
      throw new IllegalArgumentException("Unsupported HTTP method!");
    case 4: 
      paramc = HttpRequest.e(paramString);
      break;
    case 3: 
      paramc = HttpRequest.d(paramString);
      break;
    case 2: 
      paramc = HttpRequest.b(paramString, paramMap, true);
      break;
    case 1: 
      paramc = HttpRequest.a(paramString, paramMap, true);
    }
    if ((a(paramString)) && (this.b != null))
    {
      paramString = b();
      if (paramString != null) {
        ((HttpsURLConnection)paramc.a()).setSSLSocketFactory(paramString);
      }
    }
    return paramc;
  }
  
  public void a(f paramf)
  {
    if (this.b != paramf)
    {
      this.b = paramf;
      a();
    }
  }
}


/* Location:              ~/io/fabric/sdk/android/services/network/b.class
 *
 * Reversed by:           J
 */