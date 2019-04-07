package org.eclipse.paho.client.mqttv3.logging;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.MemoryHandler;

public class JSR47Logger
  implements Logger
{
  private String catalogID = null;
  private java.util.logging.Logger julLogger = null;
  private ResourceBundle logMessageCatalog = null;
  private String loggerName = null;
  private String resourceName = null;
  private ResourceBundle traceMessageCatalog = null;
  
  protected static void dumpMemoryTrace47(java.util.logging.Logger arg0)
  {
    if (??? != null)
    {
      Handler[] arrayOfHandler = ???.getHandlers();
      for (int i = 0; i < arrayOfHandler.length; i++) {
        if ((arrayOfHandler[i] instanceof MemoryHandler)) {
          synchronized (arrayOfHandler[i])
          {
            ((MemoryHandler)arrayOfHandler[i]).push();
            return;
          }
        }
      }
      dumpMemoryTrace47(???.getParent());
    }
  }
  
  private String getResourceMessage(ResourceBundle paramResourceBundle, String paramString)
  {
    try
    {
      paramResourceBundle = paramResourceBundle.getString(paramString);
      paramString = paramResourceBundle;
    }
    catch (MissingResourceException paramResourceBundle)
    {
      for (;;) {}
    }
    return paramString;
  }
  
  private void logToJsr47(Level paramLevel, String paramString1, String paramString2, String paramString3, ResourceBundle paramResourceBundle, String paramString4, Object[] paramArrayOfObject, Throwable paramThrowable)
  {
    paramString3 = paramString4;
    if (paramString4.indexOf("=====") == -1) {
      paramString3 = MessageFormat.format(getResourceMessage(paramResourceBundle, paramString4), paramArrayOfObject);
    }
    paramResourceBundle = new StringBuilder();
    paramResourceBundle.append(this.resourceName);
    paramResourceBundle.append(": ");
    paramResourceBundle.append(paramString3);
    paramLevel = new LogRecord(paramLevel, paramResourceBundle.toString());
    paramLevel.setSourceClassName(paramString1);
    paramLevel.setSourceMethodName(paramString2);
    paramLevel.setLoggerName(this.loggerName);
    if (paramThrowable != null) {
      paramLevel.setThrown(paramThrowable);
    }
    this.julLogger.log(paramLevel);
  }
  
  private Level mapJULLevel(int paramInt)
  {
    Level localLevel;
    switch (paramInt)
    {
    default: 
      localLevel = null;
      break;
    case 7: 
      localLevel = Level.FINEST;
      break;
    case 6: 
      localLevel = Level.FINER;
      break;
    case 5: 
      localLevel = Level.FINE;
      break;
    case 4: 
      localLevel = Level.CONFIG;
      break;
    case 3: 
      localLevel = Level.INFO;
      break;
    case 2: 
      localLevel = Level.WARNING;
      break;
    case 1: 
      localLevel = Level.SEVERE;
    }
    return localLevel;
  }
  
  public void config(String paramString1, String paramString2, String paramString3)
  {
    log(4, paramString1, paramString2, paramString3, null, null);
  }
  
  public void config(String paramString1, String paramString2, String paramString3, Object[] paramArrayOfObject)
  {
    log(4, paramString1, paramString2, paramString3, paramArrayOfObject, null);
  }
  
  public void config(String paramString1, String paramString2, String paramString3, Object[] paramArrayOfObject, Throwable paramThrowable)
  {
    log(4, paramString1, paramString2, paramString3, paramArrayOfObject, paramThrowable);
  }
  
  public void dumpTrace()
  {
    dumpMemoryTrace47(this.julLogger);
  }
  
  public void fine(String paramString1, String paramString2, String paramString3)
  {
    trace(5, paramString1, paramString2, paramString3, null, null);
  }
  
  public void fine(String paramString1, String paramString2, String paramString3, Object[] paramArrayOfObject)
  {
    trace(5, paramString1, paramString2, paramString3, paramArrayOfObject, null);
  }
  
  public void fine(String paramString1, String paramString2, String paramString3, Object[] paramArrayOfObject, Throwable paramThrowable)
  {
    trace(5, paramString1, paramString2, paramString3, paramArrayOfObject, paramThrowable);
  }
  
  public void finer(String paramString1, String paramString2, String paramString3)
  {
    trace(6, paramString1, paramString2, paramString3, null, null);
  }
  
  public void finer(String paramString1, String paramString2, String paramString3, Object[] paramArrayOfObject)
  {
    trace(6, paramString1, paramString2, paramString3, paramArrayOfObject, null);
  }
  
  public void finer(String paramString1, String paramString2, String paramString3, Object[] paramArrayOfObject, Throwable paramThrowable)
  {
    trace(6, paramString1, paramString2, paramString3, paramArrayOfObject, paramThrowable);
  }
  
  public void finest(String paramString1, String paramString2, String paramString3)
  {
    trace(7, paramString1, paramString2, paramString3, null, null);
  }
  
  public void finest(String paramString1, String paramString2, String paramString3, Object[] paramArrayOfObject)
  {
    trace(7, paramString1, paramString2, paramString3, paramArrayOfObject, null);
  }
  
  public void finest(String paramString1, String paramString2, String paramString3, Object[] paramArrayOfObject, Throwable paramThrowable)
  {
    trace(7, paramString1, paramString2, paramString3, paramArrayOfObject, paramThrowable);
  }
  
  public String formatMessage(String paramString, Object[] paramArrayOfObject)
  {
    try
    {
      paramArrayOfObject = this.logMessageCatalog.getString(paramString);
      paramString = paramArrayOfObject;
    }
    catch (MissingResourceException paramArrayOfObject)
    {
      for (;;) {}
    }
    return paramString;
  }
  
  public void info(String paramString1, String paramString2, String paramString3)
  {
    log(3, paramString1, paramString2, paramString3, null, null);
  }
  
  public void info(String paramString1, String paramString2, String paramString3, Object[] paramArrayOfObject)
  {
    log(3, paramString1, paramString2, paramString3, paramArrayOfObject, null);
  }
  
  public void info(String paramString1, String paramString2, String paramString3, Object[] paramArrayOfObject, Throwable paramThrowable)
  {
    log(3, paramString1, paramString2, paramString3, paramArrayOfObject, paramThrowable);
  }
  
  public void initialise(ResourceBundle paramResourceBundle, String paramString1, String paramString2)
  {
    this.traceMessageCatalog = this.logMessageCatalog;
    this.resourceName = paramString2;
    this.loggerName = paramString1;
    this.julLogger = java.util.logging.Logger.getLogger(this.loggerName);
    this.logMessageCatalog = paramResourceBundle;
    this.traceMessageCatalog = paramResourceBundle;
    this.catalogID = this.logMessageCatalog.getString("0");
  }
  
  public boolean isLoggable(int paramInt)
  {
    return this.julLogger.isLoggable(mapJULLevel(paramInt));
  }
  
  public void log(int paramInt, String paramString1, String paramString2, String paramString3, Object[] paramArrayOfObject, Throwable paramThrowable)
  {
    Level localLevel = mapJULLevel(paramInt);
    if (this.julLogger.isLoggable(localLevel)) {
      logToJsr47(localLevel, paramString1, paramString2, this.catalogID, this.logMessageCatalog, paramString3, paramArrayOfObject, paramThrowable);
    }
  }
  
  public void setResourceName(String paramString)
  {
    this.resourceName = paramString;
  }
  
  public void severe(String paramString1, String paramString2, String paramString3)
  {
    log(1, paramString1, paramString2, paramString3, null, null);
  }
  
  public void severe(String paramString1, String paramString2, String paramString3, Object[] paramArrayOfObject)
  {
    log(1, paramString1, paramString2, paramString3, paramArrayOfObject, null);
  }
  
  public void severe(String paramString1, String paramString2, String paramString3, Object[] paramArrayOfObject, Throwable paramThrowable)
  {
    log(1, paramString1, paramString2, paramString3, paramArrayOfObject, paramThrowable);
  }
  
  public void trace(int paramInt, String paramString1, String paramString2, String paramString3, Object[] paramArrayOfObject, Throwable paramThrowable)
  {
    Level localLevel = mapJULLevel(paramInt);
    if (this.julLogger.isLoggable(localLevel)) {
      logToJsr47(localLevel, paramString1, paramString2, this.catalogID, this.traceMessageCatalog, paramString3, paramArrayOfObject, paramThrowable);
    }
  }
  
  public void warning(String paramString1, String paramString2, String paramString3)
  {
    log(2, paramString1, paramString2, paramString3, null, null);
  }
  
  public void warning(String paramString1, String paramString2, String paramString3, Object[] paramArrayOfObject)
  {
    log(2, paramString1, paramString2, paramString3, paramArrayOfObject, null);
  }
  
  public void warning(String paramString1, String paramString2, String paramString3, Object[] paramArrayOfObject, Throwable paramThrowable)
  {
    log(2, paramString1, paramString2, paramString3, paramArrayOfObject, paramThrowable);
  }
}


/* Location:              ~/org/eclipse/paho/client/mqttv3/logging/JSR47Logger.class
 *
 * Reversed by:           J
 */