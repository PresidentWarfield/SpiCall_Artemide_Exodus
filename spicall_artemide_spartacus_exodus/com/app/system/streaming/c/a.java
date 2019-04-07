package com.app.system.streaming.c;

import android.annotation.SuppressLint;
import android.media.MediaCodecInfo;
import android.media.MediaCodecInfo.CodecCapabilities;
import android.media.MediaCodecList;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@SuppressLint({"InlinedApi"})
public class a
{
  public static final int[] a = { 21, 39, 19, 20, 2130706688 };
  private static a[] b = null;
  private static a[] c = null;
  
  @SuppressLint({"NewApi"})
  public static a[] a(String paramString)
  {
    try
    {
      if (b != null)
      {
        paramString = b;
        return paramString;
      }
      ArrayList localArrayList = new java/util/ArrayList;
      localArrayList.<init>();
      for (int i = MediaCodecList.getCodecCount() - 1; i >= 0; i--)
      {
        MediaCodecInfo localMediaCodecInfo = MediaCodecList.getCodecInfoAt(i);
        if (localMediaCodecInfo.isEncoder())
        {
          String[] arrayOfString = localMediaCodecInfo.getSupportedTypes();
          for (int j = 0; j < arrayOfString.length; j++)
          {
            boolean bool = arrayOfString[j].equalsIgnoreCase(paramString);
            if (bool) {
              try
              {
                Object localObject = localMediaCodecInfo.getCapabilitiesForType(paramString);
                HashSet localHashSet = new java/util/HashSet;
                localHashSet.<init>();
                for (int k = 0; k < ((MediaCodecInfo.CodecCapabilities)localObject).colorFormats.length; k++)
                {
                  int m = localObject.colorFormats[k];
                  for (int n = 0; n < a.length; n++) {
                    if (m == a[n]) {
                      localHashSet.add(Integer.valueOf(m));
                    }
                  }
                }
                localObject = new com/app/system/streaming/c/a$a;
                ((a)localObject).<init>(localMediaCodecInfo.getName(), (Integer[])localHashSet.toArray(new Integer[localHashSet.size()]));
                localArrayList.add(localObject);
              }
              catch (Exception localException)
              {
                com.security.d.a.d("CodecManager", localException.getMessage(), new Object[0]);
              }
            }
          }
        }
      }
      b = (a[])localArrayList.toArray(new a[localArrayList.size()]);
      paramString = b;
      return paramString;
    }
    finally {}
  }
  
  @SuppressLint({"NewApi"})
  public static a[] b(String paramString)
  {
    try
    {
      if (c != null)
      {
        paramString = c;
        return paramString;
      }
      ArrayList localArrayList = new java/util/ArrayList;
      localArrayList.<init>();
      for (int i = MediaCodecList.getCodecCount() - 1; i >= 0; i--)
      {
        MediaCodecInfo localMediaCodecInfo = MediaCodecList.getCodecInfoAt(i);
        if (!localMediaCodecInfo.isEncoder())
        {
          String[] arrayOfString = localMediaCodecInfo.getSupportedTypes();
          for (int j = 0; j < arrayOfString.length; j++)
          {
            boolean bool = arrayOfString[j].equalsIgnoreCase(paramString);
            if (bool) {
              try
              {
                Object localObject = localMediaCodecInfo.getCapabilitiesForType(paramString);
                HashSet localHashSet = new java/util/HashSet;
                localHashSet.<init>();
                for (int k = 0; k < ((MediaCodecInfo.CodecCapabilities)localObject).colorFormats.length; k++)
                {
                  int m = localObject.colorFormats[k];
                  for (int n = 0; n < a.length; n++) {
                    if (m == a[n]) {
                      localHashSet.add(Integer.valueOf(m));
                    }
                  }
                }
                localObject = new com/app/system/streaming/c/a$a;
                ((a)localObject).<init>(localMediaCodecInfo.getName(), (Integer[])localHashSet.toArray(new Integer[localHashSet.size()]));
                localArrayList.add(localObject);
              }
              catch (Exception localException)
              {
                com.security.d.a.d("CodecManager", localException.getMessage(), new Object[0]);
              }
            }
          }
        }
      }
      c = (a[])localArrayList.toArray(new a[localArrayList.size()]);
      for (i = 0; i < c.length; i++) {
        if (c[i].a.equalsIgnoreCase("omx.google.h264.decoder"))
        {
          paramString = c[0];
          c[0] = c[i];
          c[i] = paramString;
        }
      }
      paramString = c;
      return paramString;
    }
    finally {}
  }
  
  static class a
  {
    public String a;
    public Integer[] b;
    
    public a(String paramString, Integer[] paramArrayOfInteger)
    {
      this.a = paramString;
      this.b = paramArrayOfInteger;
    }
  }
}


/* Location:              ~/com/app/system/streaming/c/a.class
 *
 * Reversed by:           J
 */