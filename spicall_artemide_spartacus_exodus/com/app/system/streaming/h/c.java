package com.app.system.streaming.h;

import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import com.security.d.a;
import java.util.Iterator;
import java.util.List;

public class c
{
  public static final c a = new c(176, 144, 20, 500000);
  public int b = 0;
  public int c = 0;
  public int d = 0;
  public int e = 0;
  
  public c() {}
  
  public c(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.b = paramInt3;
    this.c = paramInt4;
    this.d = paramInt1;
    this.e = paramInt2;
  }
  
  public static c a(Camera.Parameters paramParameters, c paramc)
  {
    c localc = paramc.a();
    Object localObject = "Supported resolutions: ";
    Iterator localIterator = paramParameters.getSupportedPreviewSizes().iterator();
    int i = Integer.MAX_VALUE;
    paramParameters = (Camera.Parameters)localObject;
    while (localIterator.hasNext())
    {
      Camera.Size localSize = (Camera.Size)localIterator.next();
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append(paramParameters);
      ((StringBuilder)localObject).append(localSize.width);
      ((StringBuilder)localObject).append("x");
      ((StringBuilder)localObject).append(localSize.height);
      if (localIterator.hasNext()) {
        paramParameters = ", ";
      } else {
        paramParameters = "";
      }
      ((StringBuilder)localObject).append(paramParameters);
      localObject = ((StringBuilder)localObject).toString();
      int j = Math.abs(paramc.d - localSize.width);
      paramParameters = (Camera.Parameters)localObject;
      if (j < i)
      {
        localc.d = localSize.width;
        localc.e = localSize.height;
        i = j;
        paramParameters = (Camera.Parameters)localObject;
      }
    }
    a.e("VideoQuality", paramParameters, new Object[0]);
    if ((paramc.d != localc.d) || (paramc.e != localc.e))
    {
      paramParameters = new StringBuilder();
      paramParameters.append("Resolution modified: ");
      paramParameters.append(paramc.d);
      paramParameters.append("x");
      paramParameters.append(paramc.e);
      paramParameters.append("->");
      paramParameters.append(localc.d);
      paramParameters.append("x");
      paramParameters.append(localc.e);
      a.e("VideoQuality", paramParameters.toString(), new Object[0]);
    }
    return localc;
  }
  
  public static int[] a(Camera.Parameters paramParameters)
  {
    Object localObject1 = new int[2];
    Object tmp5_4 = localObject1;
    tmp5_4[0] = 0;
    Object tmp9_5 = tmp5_4;
    tmp9_5[1] = 0;
    tmp9_5;
    Object localObject2 = "Supported frame rates: ";
    Iterator localIterator = paramParameters.getSupportedPreviewFpsRange().iterator();
    paramParameters = (Camera.Parameters)localObject1;
    while (localIterator.hasNext())
    {
      localObject1 = (int[])localIterator.next();
      Object localObject3 = new StringBuilder();
      ((StringBuilder)localObject3).append((String)localObject2);
      ((StringBuilder)localObject3).append(localObject1[0] / 1000);
      ((StringBuilder)localObject3).append("-");
      ((StringBuilder)localObject3).append(localObject1[1] / 1000);
      ((StringBuilder)localObject3).append("fps");
      if (localIterator.hasNext()) {
        localObject2 = ", ";
      } else {
        localObject2 = "";
      }
      ((StringBuilder)localObject3).append((String)localObject2);
      localObject3 = ((StringBuilder)localObject3).toString();
      if (localObject1[1] <= paramParameters[1])
      {
        localObject2 = paramParameters;
        if (localObject1[0] > paramParameters[0])
        {
          localObject2 = paramParameters;
          if (localObject1[1] != paramParameters[1]) {}
        }
      }
      else
      {
        localObject2 = localObject1;
      }
      localObject1 = localObject3;
      paramParameters = (Camera.Parameters)localObject2;
      localObject2 = localObject1;
    }
    a.e("VideoQuality", (String)localObject2, new Object[0]);
    return paramParameters;
  }
  
  public c a()
  {
    return new c(this.d, this.e, this.b, this.c);
  }
  
  public boolean a(c paramc)
  {
    boolean bool1 = false;
    if (paramc == null) {
      return false;
    }
    boolean bool2 = bool1;
    if (paramc.d == this.d)
    {
      bool2 = bool1;
      if (paramc.e == this.e)
      {
        bool2 = bool1;
        if (paramc.b == this.b)
        {
          bool2 = bool1;
          if (paramc.c == this.c) {
            bool2 = true;
          }
        }
      }
    }
    return bool2;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(this.d);
    localStringBuilder.append("x");
    localStringBuilder.append(this.e);
    localStringBuilder.append(" px, ");
    localStringBuilder.append(this.b);
    localStringBuilder.append(" fps, ");
    localStringBuilder.append(this.c / 1000);
    localStringBuilder.append(" kbps");
    return localStringBuilder.toString();
  }
}


/* Location:              ~/com/app/system/streaming/h/c.class
 *
 * Reversed by:           J
 */