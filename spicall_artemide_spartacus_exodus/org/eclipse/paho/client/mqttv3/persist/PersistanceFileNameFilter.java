package org.eclipse.paho.client.mqttv3.persist;

import java.io.File;
import java.io.FilenameFilter;

public class PersistanceFileNameFilter
  implements FilenameFilter
{
  private final String fileExtension;
  
  public PersistanceFileNameFilter(String paramString)
  {
    this.fileExtension = paramString;
  }
  
  public boolean accept(File paramFile, String paramString)
  {
    return paramString.endsWith(this.fileExtension);
  }
}


/* Location:              ~/org/eclipse/paho/client/mqttv3/persist/PersistanceFileNameFilter.class
 *
 * Reversed by:           J
 */