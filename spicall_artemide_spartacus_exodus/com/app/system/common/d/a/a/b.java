package com.app.system.common.d.a.a;

import java.io.IOException;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request.Builder;
import okhttp3.Response;

public class b
{
  private static final OkHttpClient a = new OkHttpClient();
  
  public static Response a(String paramString)
  {
    try
    {
      Object localObject1 = a;
      Object localObject2 = new okhttp3/Request$Builder;
      ((Request.Builder)localObject2).<init>();
      paramString = ((OkHttpClient)localObject1).newCall(((Request.Builder)localObject2).url(paramString).build()).execute();
      if (paramString.isSuccessful()) {
        return paramString;
      }
      localObject1 = new java/io/IOException;
      localObject2 = new java/lang/StringBuilder;
      ((StringBuilder)localObject2).<init>();
      ((StringBuilder)localObject2).append("Unexpected code ");
      ((StringBuilder)localObject2).append(paramString);
      ((IOException)localObject1).<init>(((StringBuilder)localObject2).toString());
      throw ((Throwable)localObject1);
    }
    catch (IOException paramString)
    {
      paramString.printStackTrace();
    }
    return null;
  }
}


/* Location:              ~/com/app/system/common/d/a/a/b.class
 *
 * Reversed by:           J
 */