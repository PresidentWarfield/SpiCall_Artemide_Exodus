package org.eclipse.paho.client.mqttv3.persist;

import java.io.File;
import java.io.FileFilter;

public class PersistanceFileFilter
  implements FileFilter
{
  private final String fileExtension;
  
  public PersistanceFileFilter(String paramString)
  {
    this.fileExtension = paramString;
  }
  
  public boolean accept(File paramFile)
  {
    return paramFile.getName().endsWith(this.fileExtension);
  }
}


/* Location:              ~/org/eclipse/paho/client/mqttv3/persist/PersistanceFileFilter.class
 *
 * Reversed by:           J
 */