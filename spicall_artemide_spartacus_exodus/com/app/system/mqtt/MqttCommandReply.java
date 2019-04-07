package com.app.system.mqtt;

public class MqttCommandReply
{
  public static final int ALREADY_RECORDING = 313;
  public static final int CAMERA_ERROR = 317;
  public static final int ERROR_CANCELLING = 316;
  public static final int ERROR_RECORDING = 315;
  public static final int FILE_NOT_FOUND = 318;
  public static final int GENERIC_ERROR = 1;
  public static final int INVALID_COMMAND = 311;
  public static final int INVALID_PARAMS = 312;
  public static final int NEEDS_ROOT = 319;
  public static final int NOT_ACTIVE_FUNCTION = 320;
  public static final int NOT_RECORDING = 314;
  public static final int SERVER_ERROR = 302;
  public static final int SUCCESS = 0;
  public static final int SUCCESS_EXECUTING = 30;
  public String correlationID;
  public String imei;
  public String replyText;
  public int result;
  
  public static String a(int paramInt)
  {
    if (paramInt != 0)
    {
      switch (paramInt)
      {
      default: 
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("Errore sconosciuto n. ");
        localStringBuilder.append(paramInt);
        return localStringBuilder.toString();
      case 320: 
        return "Funzione non abilitata";
      case 319: 
        return "Il comando richiede i permessi di ROOT";
      case 318: 
        return "File non trovato";
      case 317: 
        return "Errore fotocamera";
      case 316: 
        return "Annullamento registrazione non riuscito";
      case 315: 
        return "Registrazione non riuscita";
      case 314: 
        return "Non c'è nessuna registrazione in corso";
      case 313: 
        return "E' già in corso una registrazione";
      case 312: 
        return "I parametri del comando sono errati";
      }
      return "Comando non valido";
    }
    return "OK";
  }
}


/* Location:              ~/com/app/system/mqtt/MqttCommandReply.class
 *
 * Reversed by:           J
 */