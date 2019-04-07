package org.eclipse.paho.client.mqttv3;

public class DisconnectedBufferOptions
{
  public static final boolean DELETE_OLDEST_MESSAGES_DEFAULT = false;
  public static final boolean DISCONNECTED_BUFFER_ENABLED_DEFAULT = false;
  public static final int DISCONNECTED_BUFFER_SIZE_DEFAULT = 5000;
  public static final boolean PERSIST_DISCONNECTED_BUFFER_DEFAULT = false;
  private boolean bufferEnabled = false;
  private int bufferSize = 5000;
  private boolean deleteOldestMessages = false;
  private boolean persistBuffer = false;
  
  public int getBufferSize()
  {
    return this.bufferSize;
  }
  
  public boolean isBufferEnabled()
  {
    return this.bufferEnabled;
  }
  
  public boolean isDeleteOldestMessages()
  {
    return this.deleteOldestMessages;
  }
  
  public boolean isPersistBuffer()
  {
    return this.persistBuffer;
  }
  
  public void setBufferEnabled(boolean paramBoolean)
  {
    this.bufferEnabled = paramBoolean;
  }
  
  public void setBufferSize(int paramInt)
  {
    if (paramInt >= 1)
    {
      this.bufferSize = paramInt;
      return;
    }
    throw new IllegalArgumentException();
  }
  
  public void setDeleteOldestMessages(boolean paramBoolean)
  {
    this.deleteOldestMessages = paramBoolean;
  }
  
  public void setPersistBuffer(boolean paramBoolean)
  {
    this.persistBuffer = paramBoolean;
  }
}


/* Location:              ~/org/eclipse/paho/client/mqttv3/DisconnectedBufferOptions.class
 *
 * Reversed by:           J
 */