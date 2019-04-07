package io.fabric.sdk.android.services.e;

import io.fabric.sdk.android.h;
import io.fabric.sdk.android.j;
import io.fabric.sdk.android.k;
import io.fabric.sdk.android.services.b.u;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.util.Locale;

abstract class a
  extends io.fabric.sdk.android.services.b.a
{
  public a(h paramh, String paramString1, String paramString2, io.fabric.sdk.android.services.network.d paramd, io.fabric.sdk.android.services.network.c paramc)
  {
    super(paramh, paramString1, paramString2, paramd, paramc);
  }
  
  private HttpRequest a(HttpRequest paramHttpRequest, d paramd)
  {
    return paramHttpRequest.a("X-CRASHLYTICS-API-KEY", paramd.a).a("X-CRASHLYTICS-API-CLIENT-TYPE", "android").a("X-CRASHLYTICS-API-CLIENT-VERSION", this.kit.getVersion());
  }
  
  /* Error */
  private HttpRequest b(HttpRequest paramHttpRequest, d paramd)
  {
    // Byte code:
    //   0: aload_1
    //   1: ldc 44
    //   3: aload_2
    //   4: getfield 46	io/fabric/sdk/android/services/e/d:b	Ljava/lang/String;
    //   7: invokevirtual 49	io/fabric/sdk/android/services/network/HttpRequest:e	(Ljava/lang/String;Ljava/lang/String;)Lio/fabric/sdk/android/services/network/HttpRequest;
    //   10: ldc 51
    //   12: aload_2
    //   13: getfield 54	io/fabric/sdk/android/services/e/d:f	Ljava/lang/String;
    //   16: invokevirtual 49	io/fabric/sdk/android/services/network/HttpRequest:e	(Ljava/lang/String;Ljava/lang/String;)Lio/fabric/sdk/android/services/network/HttpRequest;
    //   19: ldc 56
    //   21: aload_2
    //   22: getfield 59	io/fabric/sdk/android/services/e/d:c	Ljava/lang/String;
    //   25: invokevirtual 49	io/fabric/sdk/android/services/network/HttpRequest:e	(Ljava/lang/String;Ljava/lang/String;)Lio/fabric/sdk/android/services/network/HttpRequest;
    //   28: ldc 61
    //   30: aload_2
    //   31: getfield 64	io/fabric/sdk/android/services/e/d:d	Ljava/lang/String;
    //   34: invokevirtual 49	io/fabric/sdk/android/services/network/HttpRequest:e	(Ljava/lang/String;Ljava/lang/String;)Lio/fabric/sdk/android/services/network/HttpRequest;
    //   37: ldc 66
    //   39: aload_2
    //   40: getfield 70	io/fabric/sdk/android/services/e/d:g	I
    //   43: invokestatic 76	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   46: invokevirtual 79	io/fabric/sdk/android/services/network/HttpRequest:a	(Ljava/lang/String;Ljava/lang/Number;)Lio/fabric/sdk/android/services/network/HttpRequest;
    //   49: ldc 81
    //   51: aload_2
    //   52: getfield 84	io/fabric/sdk/android/services/e/d:h	Ljava/lang/String;
    //   55: invokevirtual 49	io/fabric/sdk/android/services/network/HttpRequest:e	(Ljava/lang/String;Ljava/lang/String;)Lio/fabric/sdk/android/services/network/HttpRequest;
    //   58: ldc 86
    //   60: aload_2
    //   61: getfield 89	io/fabric/sdk/android/services/e/d:i	Ljava/lang/String;
    //   64: invokevirtual 49	io/fabric/sdk/android/services/network/HttpRequest:e	(Ljava/lang/String;Ljava/lang/String;)Lio/fabric/sdk/android/services/network/HttpRequest;
    //   67: astore_3
    //   68: aload_2
    //   69: getfield 91	io/fabric/sdk/android/services/e/d:e	Ljava/lang/String;
    //   72: invokestatic 96	io/fabric/sdk/android/services/b/i:d	(Ljava/lang/String;)Z
    //   75: ifne +14 -> 89
    //   78: aload_3
    //   79: ldc 98
    //   81: aload_2
    //   82: getfield 91	io/fabric/sdk/android/services/e/d:e	Ljava/lang/String;
    //   85: invokevirtual 49	io/fabric/sdk/android/services/network/HttpRequest:e	(Ljava/lang/String;Ljava/lang/String;)Lio/fabric/sdk/android/services/network/HttpRequest;
    //   88: pop
    //   89: aload_2
    //   90: getfield 102	io/fabric/sdk/android/services/e/d:j	Lio/fabric/sdk/android/services/e/n;
    //   93: ifnull +192 -> 285
    //   96: aconst_null
    //   97: astore_1
    //   98: aconst_null
    //   99: astore 4
    //   101: aload_0
    //   102: getfield 33	io/fabric/sdk/android/services/e/a:kit	Lio/fabric/sdk/android/h;
    //   105: invokevirtual 106	io/fabric/sdk/android/h:getContext	()Landroid/content/Context;
    //   108: invokevirtual 112	android/content/Context:getResources	()Landroid/content/res/Resources;
    //   111: aload_2
    //   112: getfield 102	io/fabric/sdk/android/services/e/d:j	Lio/fabric/sdk/android/services/e/n;
    //   115: getfield 116	io/fabric/sdk/android/services/e/n:b	I
    //   118: invokevirtual 122	android/content/res/Resources:openRawResource	(I)Ljava/io/InputStream;
    //   121: astore 5
    //   123: aload 5
    //   125: astore 4
    //   127: aload 5
    //   129: astore_1
    //   130: aload_3
    //   131: ldc 124
    //   133: aload_2
    //   134: getfield 102	io/fabric/sdk/android/services/e/d:j	Lio/fabric/sdk/android/services/e/n;
    //   137: getfield 125	io/fabric/sdk/android/services/e/n:a	Ljava/lang/String;
    //   140: invokevirtual 49	io/fabric/sdk/android/services/network/HttpRequest:e	(Ljava/lang/String;Ljava/lang/String;)Lio/fabric/sdk/android/services/network/HttpRequest;
    //   143: ldc 127
    //   145: ldc -127
    //   147: ldc -125
    //   149: aload 5
    //   151: invokevirtual 134	io/fabric/sdk/android/services/network/HttpRequest:a	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/io/InputStream;)Lio/fabric/sdk/android/services/network/HttpRequest;
    //   154: ldc -120
    //   156: aload_2
    //   157: getfield 102	io/fabric/sdk/android/services/e/d:j	Lio/fabric/sdk/android/services/e/n;
    //   160: getfield 138	io/fabric/sdk/android/services/e/n:c	I
    //   163: invokestatic 76	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   166: invokevirtual 79	io/fabric/sdk/android/services/network/HttpRequest:a	(Ljava/lang/String;Ljava/lang/Number;)Lio/fabric/sdk/android/services/network/HttpRequest;
    //   169: ldc -116
    //   171: aload_2
    //   172: getfield 102	io/fabric/sdk/android/services/e/d:j	Lio/fabric/sdk/android/services/e/n;
    //   175: getfield 142	io/fabric/sdk/android/services/e/n:d	I
    //   178: invokestatic 76	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   181: invokevirtual 79	io/fabric/sdk/android/services/network/HttpRequest:a	(Ljava/lang/String;Ljava/lang/Number;)Lio/fabric/sdk/android/services/network/HttpRequest;
    //   184: pop
    //   185: aload 5
    //   187: astore_1
    //   188: goto +79 -> 267
    //   191: astore_1
    //   192: goto +84 -> 276
    //   195: astore 6
    //   197: aload_1
    //   198: astore 4
    //   200: invokestatic 147	io/fabric/sdk/android/c:g	()Lio/fabric/sdk/android/k;
    //   203: astore 7
    //   205: aload_1
    //   206: astore 4
    //   208: new 149	java/lang/StringBuilder
    //   211: astore 5
    //   213: aload_1
    //   214: astore 4
    //   216: aload 5
    //   218: invokespecial 152	java/lang/StringBuilder:<init>	()V
    //   221: aload_1
    //   222: astore 4
    //   224: aload 5
    //   226: ldc -102
    //   228: invokevirtual 158	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   231: pop
    //   232: aload_1
    //   233: astore 4
    //   235: aload 5
    //   237: aload_2
    //   238: getfield 102	io/fabric/sdk/android/services/e/d:j	Lio/fabric/sdk/android/services/e/n;
    //   241: getfield 116	io/fabric/sdk/android/services/e/n:b	I
    //   244: invokevirtual 161	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   247: pop
    //   248: aload_1
    //   249: astore 4
    //   251: aload 7
    //   253: ldc -93
    //   255: aload 5
    //   257: invokevirtual 166	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   260: aload 6
    //   262: invokeinterface 171 4 0
    //   267: aload_1
    //   268: ldc -83
    //   270: invokestatic 176	io/fabric/sdk/android/services/b/i:a	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   273: goto +12 -> 285
    //   276: aload 4
    //   278: ldc -83
    //   280: invokestatic 176	io/fabric/sdk/android/services/b/i:a	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   283: aload_1
    //   284: athrow
    //   285: aload_2
    //   286: getfield 180	io/fabric/sdk/android/services/e/d:k	Ljava/util/Collection;
    //   289: ifnull +63 -> 352
    //   292: aload_2
    //   293: getfield 180	io/fabric/sdk/android/services/e/d:k	Ljava/util/Collection;
    //   296: invokeinterface 186 1 0
    //   301: astore_2
    //   302: aload_2
    //   303: invokeinterface 192 1 0
    //   308: ifeq +44 -> 352
    //   311: aload_2
    //   312: invokeinterface 196 1 0
    //   317: checkcast 198	io/fabric/sdk/android/j
    //   320: astore_1
    //   321: aload_3
    //   322: aload_0
    //   323: aload_1
    //   324: invokevirtual 201	io/fabric/sdk/android/services/e/a:a	(Lio/fabric/sdk/android/j;)Ljava/lang/String;
    //   327: aload_1
    //   328: invokevirtual 203	io/fabric/sdk/android/j:b	()Ljava/lang/String;
    //   331: invokevirtual 49	io/fabric/sdk/android/services/network/HttpRequest:e	(Ljava/lang/String;Ljava/lang/String;)Lio/fabric/sdk/android/services/network/HttpRequest;
    //   334: pop
    //   335: aload_3
    //   336: aload_0
    //   337: aload_1
    //   338: invokevirtual 205	io/fabric/sdk/android/services/e/a:b	(Lio/fabric/sdk/android/j;)Ljava/lang/String;
    //   341: aload_1
    //   342: invokevirtual 207	io/fabric/sdk/android/j:c	()Ljava/lang/String;
    //   345: invokevirtual 49	io/fabric/sdk/android/services/network/HttpRequest:e	(Ljava/lang/String;Ljava/lang/String;)Lio/fabric/sdk/android/services/network/HttpRequest;
    //   348: pop
    //   349: goto -47 -> 302
    //   352: aload_3
    //   353: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	354	0	this	a
    //   0	354	1	paramHttpRequest	HttpRequest
    //   0	354	2	paramd	d
    //   67	286	3	localHttpRequest	HttpRequest
    //   99	178	4	localObject1	Object
    //   121	135	5	localObject2	Object
    //   195	66	6	localNotFoundException	android.content.res.Resources.NotFoundException
    //   203	49	7	localk	k
    // Exception table:
    //   from	to	target	type
    //   101	123	191	finally
    //   130	185	191	finally
    //   200	205	191	finally
    //   208	213	191	finally
    //   216	221	191	finally
    //   224	232	191	finally
    //   235	248	191	finally
    //   251	267	191	finally
    //   101	123	195	android/content/res/Resources$NotFoundException
    //   130	185	195	android/content/res/Resources$NotFoundException
  }
  
  String a(j paramj)
  {
    return String.format(Locale.US, "app[build][libraries][%s][version]", new Object[] { paramj.a() });
  }
  
  public boolean a(d paramd)
  {
    Object localObject1 = b(a(getHttpRequest(), paramd), paramd);
    Object localObject2 = io.fabric.sdk.android.c.g();
    Object localObject3 = new StringBuilder();
    ((StringBuilder)localObject3).append("Sending app info to ");
    ((StringBuilder)localObject3).append(getUrl());
    ((k)localObject2).a("Fabric", ((StringBuilder)localObject3).toString());
    if (paramd.j != null)
    {
      localObject2 = io.fabric.sdk.android.c.g();
      localObject3 = new StringBuilder();
      ((StringBuilder)localObject3).append("App icon hash is ");
      ((StringBuilder)localObject3).append(paramd.j.a);
      ((k)localObject2).a("Fabric", ((StringBuilder)localObject3).toString());
      localObject3 = io.fabric.sdk.android.c.g();
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("App icon size is ");
      ((StringBuilder)localObject2).append(paramd.j.c);
      ((StringBuilder)localObject2).append("x");
      ((StringBuilder)localObject2).append(paramd.j.d);
      ((k)localObject3).a("Fabric", ((StringBuilder)localObject2).toString());
    }
    int i = ((HttpRequest)localObject1).b();
    if ("POST".equals(((HttpRequest)localObject1).o())) {
      paramd = "Create";
    } else {
      paramd = "Update";
    }
    localObject3 = io.fabric.sdk.android.c.g();
    localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append(paramd);
    ((StringBuilder)localObject2).append(" app request ID: ");
    ((StringBuilder)localObject2).append(((HttpRequest)localObject1).b("X-REQUEST-ID"));
    ((k)localObject3).a("Fabric", ((StringBuilder)localObject2).toString());
    paramd = io.fabric.sdk.android.c.g();
    localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("Result was ");
    ((StringBuilder)localObject1).append(i);
    paramd.a("Fabric", ((StringBuilder)localObject1).toString());
    boolean bool;
    if (u.a(i) == 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  String b(j paramj)
  {
    return String.format(Locale.US, "app[build][libraries][%s][type]", new Object[] { paramj.a() });
  }
}


/* Location:              ~/io/fabric/sdk/android/services/e/a.class
 *
 * Reversed by:           J
 */