package io.fabric.sdk.android.services.e;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import io.fabric.sdk.android.c;
import io.fabric.sdk.android.k;
import io.fabric.sdk.android.services.b.i;

public class n
{
  public final String a;
  public final int b;
  public final int c;
  public final int d;
  
  public n(String paramString, int paramInt1, int paramInt2, int paramInt3)
  {
    this.a = paramString;
    this.b = paramInt1;
    this.c = paramInt2;
    this.d = paramInt3;
  }
  
  public static n a(Context paramContext, String paramString)
  {
    if (paramString != null) {
      try
      {
        int i = i.l(paramContext);
        Object localObject = c.g();
        StringBuilder localStringBuilder = new java/lang/StringBuilder;
        localStringBuilder.<init>();
        localStringBuilder.append("App icon resource ID is ");
        localStringBuilder.append(i);
        ((k)localObject).a("Fabric", localStringBuilder.toString());
        localObject = new android/graphics/BitmapFactory$Options;
        ((BitmapFactory.Options)localObject).<init>();
        ((BitmapFactory.Options)localObject).inJustDecodeBounds = true;
        BitmapFactory.decodeResource(paramContext.getResources(), i, (BitmapFactory.Options)localObject);
        paramContext = new io/fabric/sdk/android/services/e/n;
        paramContext.<init>(paramString, i, ((BitmapFactory.Options)localObject).outWidth, ((BitmapFactory.Options)localObject).outHeight);
      }
      catch (Exception paramContext)
      {
        c.g().e("Fabric", "Failed to load icon", paramContext);
      }
    } else {
      paramContext = null;
    }
    return paramContext;
  }
}


/* Location:              ~/io/fabric/sdk/android/services/e/n.class
 *
 * Reversed by:           J
 */