package com.crashlytics.android.answers;

import io.fabric.sdk.android.h;
import io.fabric.sdk.android.k;
import io.fabric.sdk.android.services.b.a;
import io.fabric.sdk.android.services.b.u;
import io.fabric.sdk.android.services.c.f;
import io.fabric.sdk.android.services.network.HttpRequest;
import io.fabric.sdk.android.services.network.d;
import java.io.File;
import java.util.Iterator;
import java.util.List;

class SessionAnalyticsFilesSender
  extends a
  implements f
{
  static final String FILE_CONTENT_TYPE = "application/vnd.crashlytics.android.events";
  static final String FILE_PARAM_NAME = "session_analytics_file_";
  private final String apiKey;
  
  public SessionAnalyticsFilesSender(h paramh, String paramString1, String paramString2, d paramd, String paramString3)
  {
    super(paramh, paramString1, paramString2, paramd, io.fabric.sdk.android.services.network.c.b);
    this.apiKey = paramString3;
  }
  
  public boolean send(List<File> paramList)
  {
    Object localObject1 = getHttpRequest().a("X-CRASHLYTICS-API-CLIENT-TYPE", "android").a("X-CRASHLYTICS-API-CLIENT-VERSION", this.kit.getVersion()).a("X-CRASHLYTICS-API-KEY", this.apiKey);
    Object localObject2 = paramList.iterator();
    boolean bool = false;
    for (int i = 0; ((Iterator)localObject2).hasNext(); i++)
    {
      localObject3 = (File)((Iterator)localObject2).next();
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("session_analytics_file_");
      localStringBuilder.append(i);
      ((HttpRequest)localObject1).a(localStringBuilder.toString(), ((File)localObject3).getName(), "application/vnd.crashlytics.android.events", (File)localObject3);
    }
    Object localObject3 = io.fabric.sdk.android.c.g();
    localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append("Sending ");
    ((StringBuilder)localObject2).append(paramList.size());
    ((StringBuilder)localObject2).append(" analytics files to ");
    ((StringBuilder)localObject2).append(getUrl());
    ((k)localObject3).a("Answers", ((StringBuilder)localObject2).toString());
    i = ((HttpRequest)localObject1).b();
    localObject1 = io.fabric.sdk.android.c.g();
    paramList = new StringBuilder();
    paramList.append("Response code for analytics file send is ");
    paramList.append(i);
    ((k)localObject1).a("Answers", paramList.toString());
    if (u.a(i) == 0) {
      bool = true;
    }
    return bool;
  }
}


/* Location:              ~/com/crashlytics/android/answers/SessionAnalyticsFilesSender.class
 *
 * Reversed by:           J
 */