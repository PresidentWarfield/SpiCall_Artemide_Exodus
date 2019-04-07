package io.fabric.sdk.android.services.e;

import io.fabric.sdk.android.h;
import io.fabric.sdk.android.k;
import io.fabric.sdk.android.services.b.a;
import io.fabric.sdk.android.services.b.i;
import io.fabric.sdk.android.services.network.HttpRequest;
import io.fabric.sdk.android.services.network.d;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

class l
  extends a
  implements x
{
  public l(h paramh, String paramString1, String paramString2, d paramd)
  {
    this(paramh, paramString1, paramString2, paramd, io.fabric.sdk.android.services.network.c.a);
  }
  
  l(h paramh, String paramString1, String paramString2, d paramd, io.fabric.sdk.android.services.network.c paramc)
  {
    super(paramh, paramString1, paramString2, paramd, paramc);
  }
  
  private HttpRequest a(HttpRequest paramHttpRequest, w paramw)
  {
    a(paramHttpRequest, "X-CRASHLYTICS-API-KEY", paramw.a);
    a(paramHttpRequest, "X-CRASHLYTICS-API-CLIENT-TYPE", "android");
    a(paramHttpRequest, "X-CRASHLYTICS-API-CLIENT-VERSION", this.kit.getVersion());
    a(paramHttpRequest, "Accept", "application/json");
    a(paramHttpRequest, "X-CRASHLYTICS-DEVICE-MODEL", paramw.b);
    a(paramHttpRequest, "X-CRASHLYTICS-OS-BUILD-VERSION", paramw.c);
    a(paramHttpRequest, "X-CRASHLYTICS-OS-DISPLAY-VERSION", paramw.d);
    a(paramHttpRequest, "X-CRASHLYTICS-INSTALLATION-ID", paramw.e);
    return paramHttpRequest;
  }
  
  private JSONObject a(String paramString)
  {
    try
    {
      localObject = new JSONObject(paramString);
      return (JSONObject)localObject;
    }
    catch (Exception localException)
    {
      k localk = io.fabric.sdk.android.c.g();
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append("Failed to parse settings JSON from ");
      ((StringBuilder)localObject).append(getUrl());
      localk.a("Fabric", ((StringBuilder)localObject).toString(), localException);
      localk = io.fabric.sdk.android.c.g();
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("Settings response ");
      ((StringBuilder)localObject).append(paramString);
      localk.a("Fabric", ((StringBuilder)localObject).toString());
    }
    return null;
  }
  
  private void a(HttpRequest paramHttpRequest, String paramString1, String paramString2)
  {
    if (paramString2 != null) {
      paramHttpRequest.a(paramString1, paramString2);
    }
  }
  
  private Map<String, String> b(w paramw)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("build_version", paramw.h);
    localHashMap.put("display_version", paramw.g);
    localHashMap.put("source", Integer.toString(paramw.i));
    if (paramw.j != null) {
      localHashMap.put("icon_hash", paramw.j);
    }
    paramw = paramw.f;
    if (!i.d(paramw)) {
      localHashMap.put("instance", paramw);
    }
    return localHashMap;
  }
  
  /* Error */
  public JSONObject a(w paramw)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_2
    //   2: aconst_null
    //   3: astore_3
    //   4: aload_0
    //   5: aload_1
    //   6: invokespecial 170	io/fabric/sdk/android/services/e/l:b	(Lio/fabric/sdk/android/services/e/w;)Ljava/util/Map;
    //   9: astore 4
    //   11: aload_0
    //   12: aload 4
    //   14: invokevirtual 174	io/fabric/sdk/android/services/e/l:getHttpRequest	(Ljava/util/Map;)Lio/fabric/sdk/android/services/network/HttpRequest;
    //   17: astore 5
    //   19: aload_0
    //   20: aload 5
    //   22: aload_1
    //   23: invokespecial 176	io/fabric/sdk/android/services/e/l:a	(Lio/fabric/sdk/android/services/network/HttpRequest;Lio/fabric/sdk/android/services/e/w;)Lio/fabric/sdk/android/services/network/HttpRequest;
    //   26: astore_1
    //   27: aload_1
    //   28: astore 5
    //   30: invokestatic 84	io/fabric/sdk/android/c:g	()Lio/fabric/sdk/android/k;
    //   33: astore 6
    //   35: aload_1
    //   36: astore 5
    //   38: new 86	java/lang/StringBuilder
    //   41: astore 7
    //   43: aload_1
    //   44: astore 5
    //   46: aload 7
    //   48: invokespecial 89	java/lang/StringBuilder:<init>	()V
    //   51: aload_1
    //   52: astore 5
    //   54: aload 7
    //   56: ldc -78
    //   58: invokevirtual 95	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   61: pop
    //   62: aload_1
    //   63: astore 5
    //   65: aload 7
    //   67: aload_0
    //   68: invokevirtual 98	io/fabric/sdk/android/services/e/l:getUrl	()Ljava/lang/String;
    //   71: invokevirtual 95	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   74: pop
    //   75: aload_1
    //   76: astore 5
    //   78: aload 6
    //   80: ldc 100
    //   82: aload 7
    //   84: invokevirtual 103	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   87: invokeinterface 113 3 0
    //   92: aload_1
    //   93: astore 5
    //   95: invokestatic 84	io/fabric/sdk/android/c:g	()Lio/fabric/sdk/android/k;
    //   98: astore 7
    //   100: aload_1
    //   101: astore 5
    //   103: new 86	java/lang/StringBuilder
    //   106: astore 6
    //   108: aload_1
    //   109: astore 5
    //   111: aload 6
    //   113: invokespecial 89	java/lang/StringBuilder:<init>	()V
    //   116: aload_1
    //   117: astore 5
    //   119: aload 6
    //   121: ldc -76
    //   123: invokevirtual 95	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   126: pop
    //   127: aload_1
    //   128: astore 5
    //   130: aload 6
    //   132: aload 4
    //   134: invokevirtual 183	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   137: pop
    //   138: aload_1
    //   139: astore 5
    //   141: aload 7
    //   143: ldc 100
    //   145: aload 6
    //   147: invokevirtual 103	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   150: invokeinterface 113 3 0
    //   155: aload_1
    //   156: astore 5
    //   158: aload_0
    //   159: aload_1
    //   160: invokevirtual 186	io/fabric/sdk/android/services/e/l:a	(Lio/fabric/sdk/android/services/network/HttpRequest;)Lorg/json/JSONObject;
    //   163: astore 4
    //   165: aload 4
    //   167: astore 5
    //   169: aload_1
    //   170: ifnull +126 -> 296
    //   173: invokestatic 84	io/fabric/sdk/android/c:g	()Lio/fabric/sdk/android/k;
    //   176: astore_2
    //   177: new 86	java/lang/StringBuilder
    //   180: dup
    //   181: invokespecial 89	java/lang/StringBuilder:<init>	()V
    //   184: astore_3
    //   185: aload 4
    //   187: astore 5
    //   189: aload_3
    //   190: ldc -68
    //   192: invokevirtual 95	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   195: pop
    //   196: aload_3
    //   197: aload_1
    //   198: ldc -66
    //   200: invokevirtual 193	io/fabric/sdk/android/services/network/HttpRequest:b	(Ljava/lang/String;)Ljava/lang/String;
    //   203: invokevirtual 95	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   206: pop
    //   207: aload_2
    //   208: ldc 100
    //   210: aload_3
    //   211: invokevirtual 103	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   214: invokeinterface 113 3 0
    //   219: goto +77 -> 296
    //   222: astore 4
    //   224: goto +26 -> 250
    //   227: astore_1
    //   228: goto +72 -> 300
    //   231: astore 4
    //   233: aload 5
    //   235: astore_1
    //   236: goto +14 -> 250
    //   239: astore_1
    //   240: aconst_null
    //   241: astore 5
    //   243: goto +57 -> 300
    //   246: astore 4
    //   248: aconst_null
    //   249: astore_1
    //   250: aload_1
    //   251: astore 5
    //   253: invokestatic 84	io/fabric/sdk/android/c:g	()Lio/fabric/sdk/android/k;
    //   256: ldc 100
    //   258: ldc -61
    //   260: aload 4
    //   262: invokeinterface 197 4 0
    //   267: aload_2
    //   268: astore 5
    //   270: aload_1
    //   271: ifnull +25 -> 296
    //   274: invokestatic 84	io/fabric/sdk/android/c:g	()Lio/fabric/sdk/android/k;
    //   277: astore_2
    //   278: new 86	java/lang/StringBuilder
    //   281: dup
    //   282: invokespecial 89	java/lang/StringBuilder:<init>	()V
    //   285: astore 4
    //   287: aload_3
    //   288: astore 5
    //   290: aload 4
    //   292: astore_3
    //   293: goto -104 -> 189
    //   296: aload 5
    //   298: areturn
    //   299: astore_1
    //   300: aload 5
    //   302: ifnull +48 -> 350
    //   305: invokestatic 84	io/fabric/sdk/android/c:g	()Lio/fabric/sdk/android/k;
    //   308: astore 4
    //   310: new 86	java/lang/StringBuilder
    //   313: dup
    //   314: invokespecial 89	java/lang/StringBuilder:<init>	()V
    //   317: astore_3
    //   318: aload_3
    //   319: ldc -68
    //   321: invokevirtual 95	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   324: pop
    //   325: aload_3
    //   326: aload 5
    //   328: ldc -66
    //   330: invokevirtual 193	io/fabric/sdk/android/services/network/HttpRequest:b	(Ljava/lang/String;)Ljava/lang/String;
    //   333: invokevirtual 95	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   336: pop
    //   337: aload 4
    //   339: ldc 100
    //   341: aload_3
    //   342: invokevirtual 103	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   345: invokeinterface 113 3 0
    //   350: aload_1
    //   351: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	352	0	this	l
    //   0	352	1	paramw	w
    //   1	277	2	localk	k
    //   3	339	3	localObject1	Object
    //   9	177	4	localObject2	Object
    //   222	1	4	localHttpRequestException1	io.fabric.sdk.android.services.network.HttpRequest.HttpRequestException
    //   231	1	4	localHttpRequestException2	io.fabric.sdk.android.services.network.HttpRequest.HttpRequestException
    //   246	15	4	localHttpRequestException3	io.fabric.sdk.android.services.network.HttpRequest.HttpRequestException
    //   285	53	4	localObject3	Object
    //   17	310	5	localObject4	Object
    //   33	113	6	localObject5	Object
    //   41	101	7	localObject6	Object
    // Exception table:
    //   from	to	target	type
    //   30	35	222	io/fabric/sdk/android/services/network/HttpRequest$HttpRequestException
    //   38	43	222	io/fabric/sdk/android/services/network/HttpRequest$HttpRequestException
    //   46	51	222	io/fabric/sdk/android/services/network/HttpRequest$HttpRequestException
    //   54	62	222	io/fabric/sdk/android/services/network/HttpRequest$HttpRequestException
    //   65	75	222	io/fabric/sdk/android/services/network/HttpRequest$HttpRequestException
    //   78	92	222	io/fabric/sdk/android/services/network/HttpRequest$HttpRequestException
    //   95	100	222	io/fabric/sdk/android/services/network/HttpRequest$HttpRequestException
    //   103	108	222	io/fabric/sdk/android/services/network/HttpRequest$HttpRequestException
    //   111	116	222	io/fabric/sdk/android/services/network/HttpRequest$HttpRequestException
    //   119	127	222	io/fabric/sdk/android/services/network/HttpRequest$HttpRequestException
    //   130	138	222	io/fabric/sdk/android/services/network/HttpRequest$HttpRequestException
    //   141	155	222	io/fabric/sdk/android/services/network/HttpRequest$HttpRequestException
    //   158	165	222	io/fabric/sdk/android/services/network/HttpRequest$HttpRequestException
    //   19	27	227	finally
    //   19	27	231	io/fabric/sdk/android/services/network/HttpRequest$HttpRequestException
    //   4	19	239	finally
    //   4	19	246	io/fabric/sdk/android/services/network/HttpRequest$HttpRequestException
    //   30	35	299	finally
    //   38	43	299	finally
    //   46	51	299	finally
    //   54	62	299	finally
    //   65	75	299	finally
    //   78	92	299	finally
    //   95	100	299	finally
    //   103	108	299	finally
    //   111	116	299	finally
    //   119	127	299	finally
    //   130	138	299	finally
    //   141	155	299	finally
    //   158	165	299	finally
    //   253	267	299	finally
  }
  
  JSONObject a(HttpRequest paramHttpRequest)
  {
    int i = paramHttpRequest.b();
    k localk = io.fabric.sdk.android.c.g();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Settings result was: ");
    localStringBuilder.append(i);
    localk.a("Fabric", localStringBuilder.toString());
    if (a(i))
    {
      paramHttpRequest = a(paramHttpRequest.d());
    }
    else
    {
      paramHttpRequest = io.fabric.sdk.android.c.g();
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("Failed to retrieve settings from ");
      localStringBuilder.append(getUrl());
      paramHttpRequest.e("Fabric", localStringBuilder.toString());
      paramHttpRequest = null;
    }
    return paramHttpRequest;
  }
  
  boolean a(int paramInt)
  {
    boolean bool;
    if ((paramInt != 200) && (paramInt != 201) && (paramInt != 202) && (paramInt != 203)) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
}


/* Location:              ~/io/fabric/sdk/android/services/e/l.class
 *
 * Reversed by:           J
 */