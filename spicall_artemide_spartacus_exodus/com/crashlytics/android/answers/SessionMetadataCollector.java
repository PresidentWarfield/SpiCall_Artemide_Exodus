package com.crashlytics.android.answers;

import android.content.Context;
import io.fabric.sdk.android.services.b.i;
import io.fabric.sdk.android.services.b.r;
import io.fabric.sdk.android.services.b.r.a;
import java.util.Map;
import java.util.UUID;

class SessionMetadataCollector
{
  private final Context context;
  private final r idManager;
  private final String versionCode;
  private final String versionName;
  
  public SessionMetadataCollector(Context paramContext, r paramr, String paramString1, String paramString2)
  {
    this.context = paramContext;
    this.idManager = paramr;
    this.versionCode = paramString1;
    this.versionName = paramString2;
  }
  
  public SessionEventMetadata getMetadata()
  {
    Object localObject = this.idManager.h();
    String str1 = this.idManager.c();
    String str2 = this.idManager.b();
    Boolean localBoolean = this.idManager.j();
    String str3 = (String)((Map)localObject).get(r.a.c);
    String str4 = i.m(this.context);
    String str5 = this.idManager.d();
    localObject = this.idManager.g();
    return new SessionEventMetadata(str1, UUID.randomUUID().toString(), str2, localBoolean, str3, str4, str5, (String)localObject, this.versionCode, this.versionName);
  }
}


/* Location:              ~/com/crashlytics/android/answers/SessionMetadataCollector.class
 *
 * Reversed by:           J
 */