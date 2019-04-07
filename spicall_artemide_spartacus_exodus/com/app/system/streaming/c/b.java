package com.app.system.streaming.c;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.MediaCodec;
import android.media.MediaCodec.BufferInfo;
import android.media.MediaFormat;
import android.os.Build.VERSION;
import android.util.Base64;
import java.nio.ByteBuffer;

@SuppressLint({"NewApi"})
public class b
{
  private int a;
  private int b;
  private String c;
  private String d;
  private String e;
  private MediaCodec f;
  private MediaCodec g;
  private int h;
  private int i;
  private int j;
  private byte[] k;
  private byte[] l;
  private byte[] m;
  private byte[] n;
  private MediaFormat o;
  private c p;
  private SharedPreferences q;
  private byte[][] r;
  private byte[][] s;
  private String t;
  private String u;
  
  private b(SharedPreferences paramSharedPreferences, int paramInt1, int paramInt2)
  {
    this.q = paramSharedPreferences;
    this.h = paramInt1;
    this.i = paramInt2;
    this.j = (paramInt1 * paramInt2);
    f();
  }
  
  public static b a(SharedPreferences paramSharedPreferences, int paramInt1, int paramInt2)
  {
    try
    {
      b localb = new com/app/system/streaming/c/b;
      localb.<init>(paramSharedPreferences, paramInt1, paramInt2);
      localb.g();
      return localb;
    }
    finally
    {
      paramSharedPreferences = finally;
      throw paramSharedPreferences;
    }
  }
  
  private void a(int paramInt)
  {
    byte[] arrayOfByte = new byte[this.j * 3 / 2];
    int i1 = this.h;
    int i2 = this.i;
    int i3 = this.a;
    Object localObject = this.o;
    int i4 = i1;
    int i5 = i2;
    int i6 = i3;
    if (localObject != null)
    {
      i4 = i1;
      i5 = i2;
      i6 = i3;
      if (localObject != null)
      {
        if (((MediaFormat)localObject).containsKey("slice-height"))
        {
          i4 = ((MediaFormat)localObject).getInteger("slice-height");
          i5 = this.i;
          i2 = i4;
          if (i4 < i5) {
            i2 = i5;
          }
        }
        if (((MediaFormat)localObject).containsKey("stride"))
        {
          i5 = ((MediaFormat)localObject).getInteger("stride");
          i4 = this.h;
          i1 = i5;
          if (i5 < i4) {
            i1 = i4;
          }
        }
        i4 = i1;
        i5 = i2;
        i6 = i3;
        if (((MediaFormat)localObject).containsKey("color-format"))
        {
          i4 = i1;
          i5 = i2;
          i6 = i3;
          if (((MediaFormat)localObject).getInteger("color-format") > 0)
          {
            i6 = ((MediaFormat)localObject).getInteger("color-format");
            i5 = i2;
            i4 = i1;
          }
        }
      }
    }
    int i7 = 0;
    i3 = 0;
    if ((i6 != 39) && (i6 != 2130706688)) {}
    switch (i6)
    {
    default: 
      i2 = 0;
      break;
    case 19: 
    case 20: 
      i2 = 1;
      break;
    case 21: 
      i2 = 0;
    }
    for (i1 = 0; i1 < this.j; i1 = i6 + 1)
    {
      int i8 = this.h;
      i6 = i1;
      if (i1 % i8 == 0) {
        i6 = i1 + (i4 - i8);
      }
      arrayOfByte[i6] = ((byte)this.s[paramInt][i6]);
    }
    byte[][] arrayOfByte1;
    if (i2 == 0)
    {
      i2 = 0;
      for (i1 = i3; i1 < this.j / 4; i1++)
      {
        i3 = this.h;
        i6 = i2;
        if (i2 % i3 / 2 == 0) {
          i6 = i2 + (i4 - i3) / 2;
        }
        i2 = this.j;
        i3 = i1 * 2;
        localObject = this.s;
        arrayOfByte1 = localObject[paramInt];
        i7 = i4 * i5 + i6 * 2;
        arrayOfByte[(i2 + i3 + 1)] = ((byte)arrayOfByte1[i7]);
        arrayOfByte[(i2 + i3)] = ((byte)localObject[paramInt][(i7 + 1)]);
        i2 = i6 + 1;
      }
    }
    i2 = 0;
    for (i1 = i7; i1 < this.j / 4; i1++)
    {
      i3 = this.h;
      i6 = i2;
      if (i2 % i3 / 2 == 0) {
        i6 = i2 + (i4 - i3) / 2;
      }
      i7 = this.j;
      i2 = i1 * 2;
      arrayOfByte1 = this.s;
      localObject = arrayOfByte1[paramInt];
      i3 = i4 * i5;
      arrayOfByte[(i7 + i2 + 1)] = ((byte)localObject[(i3 + i6)]);
      arrayOfByte[(i7 + i2)] = ((byte)arrayOfByte1[paramInt][(i3 * 5 / 4 + i6)]);
      i2 = i6 + 1;
    }
    this.s[paramInt] = arrayOfByte;
  }
  
  private void a(MediaCodec paramMediaCodec)
  {
    MediaCodec.BufferInfo localBufferInfo = new MediaCodec.BufferInfo();
    int i1 = 0;
    while (i1 != -1)
    {
      int i2 = paramMediaCodec.dequeueOutputBuffer(localBufferInfo, 50000L);
      i1 = i2;
      if (i2 >= 0)
      {
        paramMediaCodec.releaseOutputBuffer(i2, false);
        i1 = i2;
      }
    }
  }
  
  private void a(boolean paramBoolean)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append(this.h);
    ((StringBuilder)localObject).append("x");
    ((StringBuilder)localObject).append(this.i);
    ((StringBuilder)localObject).append("-");
    String str = ((StringBuilder)localObject).toString();
    localObject = this.q.edit();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("libstreaming-");
    localStringBuilder.append(str);
    localStringBuilder.append("success");
    ((SharedPreferences.Editor)localObject).putBoolean(localStringBuilder.toString(), paramBoolean);
    if (paramBoolean)
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("libstreaming-");
      localStringBuilder.append(str);
      localStringBuilder.append("lastSdk");
      ((SharedPreferences.Editor)localObject).putInt(localStringBuilder.toString(), Build.VERSION.SDK_INT);
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("libstreaming-");
      localStringBuilder.append(str);
      localStringBuilder.append("lastVersion");
      ((SharedPreferences.Editor)localObject).putInt(localStringBuilder.toString(), 3);
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("libstreaming-");
      localStringBuilder.append(str);
      localStringBuilder.append("sliceHeight");
      ((SharedPreferences.Editor)localObject).putInt(localStringBuilder.toString(), this.p.c());
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("libstreaming-");
      localStringBuilder.append(str);
      localStringBuilder.append("stride");
      ((SharedPreferences.Editor)localObject).putInt(localStringBuilder.toString(), this.p.b());
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("libstreaming-");
      localStringBuilder.append(str);
      localStringBuilder.append("padding");
      ((SharedPreferences.Editor)localObject).putInt(localStringBuilder.toString(), this.p.d());
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("libstreaming-");
      localStringBuilder.append(str);
      localStringBuilder.append("planar");
      ((SharedPreferences.Editor)localObject).putBoolean(localStringBuilder.toString(), this.p.e());
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("libstreaming-");
      localStringBuilder.append(str);
      localStringBuilder.append("reversed");
      ((SharedPreferences.Editor)localObject).putBoolean(localStringBuilder.toString(), this.p.f());
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("libstreaming-");
      localStringBuilder.append(str);
      localStringBuilder.append("encoderName");
      ((SharedPreferences.Editor)localObject).putString(localStringBuilder.toString(), this.d);
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("libstreaming-");
      localStringBuilder.append(str);
      localStringBuilder.append("colorFormat");
      ((SharedPreferences.Editor)localObject).putInt(localStringBuilder.toString(), this.b);
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("libstreaming-");
      localStringBuilder.append(str);
      localStringBuilder.append("encoderName");
      ((SharedPreferences.Editor)localObject).putString(localStringBuilder.toString(), this.d);
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("libstreaming-");
      localStringBuilder.append(str);
      localStringBuilder.append("pps");
      ((SharedPreferences.Editor)localObject).putString(localStringBuilder.toString(), this.t);
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("libstreaming-");
      localStringBuilder.append(str);
      localStringBuilder.append("sps");
      ((SharedPreferences.Editor)localObject).putString(localStringBuilder.toString(), this.u);
    }
    ((SharedPreferences.Editor)localObject).commit();
  }
  
  private void a(boolean paramBoolean, String paramString)
  {
    if (paramBoolean) {
      return;
    }
    throw new IllegalStateException(paramString);
  }
  
  private boolean a(byte[] paramArrayOfByte)
  {
    boolean bool1 = false;
    boolean bool2 = bool1;
    if (paramArrayOfByte[0] == 0)
    {
      bool2 = bool1;
      if (paramArrayOfByte[1] == 0)
      {
        bool2 = bool1;
        if (paramArrayOfByte[2] == 0)
        {
          bool2 = bool1;
          if (paramArrayOfByte[3] == 1) {
            bool2 = true;
          }
        }
      }
    }
    return bool2;
  }
  
  private boolean b(boolean paramBoolean)
  {
    boolean bool = false;
    int i1 = 0;
    int i3;
    for (int i2 = 0; i1 < 34; i2 = i3)
    {
      i3 = i2;
      if (this.s[i1] != null)
      {
        int i5;
        if (!paramBoolean) {
          for (i4 = this.j;; i4++)
          {
            i3 = i2;
            if (i4 >= this.j * 3 / 2) {
              break;
            }
            i5 = (this.n[i4] & 0xFF) - (this.s[i1][i4] & 0xFF);
            i3 = i5;
            if (i5 < 0) {
              i3 = -i5;
            }
            if (i3 > 50)
            {
              i3 = i2 + 1;
              break;
            }
          }
        }
        int i4 = this.j;
        for (;;)
        {
          i3 = i2;
          if (i4 >= this.j * 3 / 2) {
            break;
          }
          i5 = (this.n[i4] & 0xFF) - (this.s[i1][(i4 + 1)] & 0xFF);
          i3 = i5;
          if (i5 < 0) {
            i3 = -i5;
          }
          i5 = i2;
          if (i3 > 50) {
            i5 = i2 + 1;
          }
          i4 += 2;
          i2 = i5;
        }
      }
      i1++;
    }
    paramBoolean = bool;
    if (i2 <= 17) {
      paramBoolean = true;
    }
    return paramBoolean;
  }
  
  private long c(boolean paramBoolean)
  {
    long l1 = s();
    ByteBuffer[] arrayOfByteBuffer1 = this.g.getInputBuffers();
    ByteBuffer[] arrayOfByteBuffer2 = this.g.getOutputBuffers();
    MediaCodec.BufferInfo localBufferInfo = new MediaCodec.BufferInfo();
    long l2 = 0L;
    int i1 = 0;
    int i2 = 0;
    int i3 = 0;
    while (l2 < 3000000L)
    {
      int i6;
      if (i1 < 50)
      {
        i4 = this.g.dequeueInputBuffer(50000L);
        if (i4 >= 0)
        {
          int i5 = arrayOfByteBuffer1[i4].capacity();
          i6 = this.r[i1].length;
          arrayOfByteBuffer1[i4].clear();
          boolean bool;
          Object localObject1;
          Object localObject2;
          if (((paramBoolean) && (a(this.r[i1]))) || ((!paramBoolean) && (!a(this.r[i1]))))
          {
            if (i5 >= i6) {
              bool = true;
            } else {
              bool = false;
            }
            localObject1 = new StringBuilder();
            ((StringBuilder)localObject1).append("The decoder input buffer is not big enough (nal=");
            ((StringBuilder)localObject1).append(i6);
            ((StringBuilder)localObject1).append(", capacity=");
            ((StringBuilder)localObject1).append(i5);
            ((StringBuilder)localObject1).append(").");
            a(bool, ((StringBuilder)localObject1).toString());
            localObject2 = arrayOfByteBuffer1[i4];
            localObject1 = this.r;
            ((ByteBuffer)localObject2).put(localObject1[i1], 0, localObject1[i1].length);
          }
          else
          {
            int i7;
            if ((paramBoolean) && (!a(this.r[i1])))
            {
              i7 = i6 + 4;
              if (i5 >= i7) {
                bool = true;
              } else {
                bool = false;
              }
              localObject1 = new StringBuilder();
              ((StringBuilder)localObject1).append("The decoder input buffer is not big enough (nal=");
              ((StringBuilder)localObject1).append(i7);
              ((StringBuilder)localObject1).append(", capacity=");
              ((StringBuilder)localObject1).append(i5);
              ((StringBuilder)localObject1).append(").");
              a(bool, ((StringBuilder)localObject1).toString());
              arrayOfByteBuffer1[i4].put(new byte[] { 0, 0, 0, 1 });
              localObject1 = arrayOfByteBuffer1[i4];
              localObject2 = this.r;
              ((ByteBuffer)localObject1).put(localObject2[i1], 0, localObject2[i1].length);
            }
            else if ((!paramBoolean) && (a(this.r[i1])))
            {
              i7 = i6 - 4;
              if (i5 >= i7) {
                bool = true;
              } else {
                bool = false;
              }
              localObject1 = new StringBuilder();
              ((StringBuilder)localObject1).append("The decoder input buffer is not big enough (nal=");
              ((StringBuilder)localObject1).append(i7);
              ((StringBuilder)localObject1).append(", capacity=");
              ((StringBuilder)localObject1).append(i5);
              ((StringBuilder)localObject1).append(").");
              a(bool, ((StringBuilder)localObject1).toString());
              localObject1 = arrayOfByteBuffer1[i4];
              localObject2 = this.r;
              ((ByteBuffer)localObject1).put(localObject2[i1], 4, localObject2[i1].length - 4);
            }
          }
          this.g.queueInputBuffer(i4, 0, i6, s(), 0);
          i6 = i1 + 1;
        }
        else
        {
          i6 = i1;
        }
      }
      else
      {
        i6 = i1;
      }
      int i4 = this.g.dequeueOutputBuffer(localBufferInfo, 50000L);
      if (i4 == -3)
      {
        arrayOfByteBuffer2 = this.g.getOutputBuffers();
      }
      else if (i4 == -2)
      {
        this.o = this.g.getOutputFormat();
      }
      else if (i4 >= 0)
      {
        i1 = i3;
        if (i2 > 2)
        {
          i1 = localBufferInfo.size;
          this.s[i3] = new byte[i1];
          arrayOfByteBuffer2[i4].clear();
          arrayOfByteBuffer2[i4].get(this.s[i3], 0, i1);
          a(i3);
          if (i3 >= 33)
          {
            a(this.g);
            return l2;
          }
          i1 = i3 + 1;
        }
        this.g.releaseOutputBuffer(i4, false);
        i2++;
        i3 = i1;
      }
      l2 = s() - l1;
      i1 = i6;
    }
    throw new RuntimeException("The decoder did not decode anything.");
  }
  
  private void f()
  {
    this.p = new c();
    this.r = new byte[50][];
    this.s = new byte[34][];
    this.e = "";
    this.l = null;
    this.k = null;
  }
  
  /* Error */
  private void g()
  {
    // Byte code:
    //   0: aload_0
    //   1: invokespecial 288	com/app/system/streaming/c/b:h	()Z
    //   4: ifne +651 -> 655
    //   7: new 100	java/lang/StringBuilder
    //   10: dup
    //   11: invokespecial 101	java/lang/StringBuilder:<init>	()V
    //   14: astore_1
    //   15: aload_1
    //   16: aload_0
    //   17: getfield 45	com/app/system/streaming/c/b:h	I
    //   20: invokevirtual 105	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   23: pop
    //   24: aload_1
    //   25: ldc 107
    //   27: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   30: pop
    //   31: aload_1
    //   32: aload_0
    //   33: getfield 47	com/app/system/streaming/c/b:i	I
    //   36: invokevirtual 105	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   39: pop
    //   40: aload_1
    //   41: ldc 112
    //   43: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   46: pop
    //   47: aload_1
    //   48: invokevirtual 116	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   51: astore_1
    //   52: aload_0
    //   53: getfield 43	com/app/system/streaming/c/b:q	Landroid/content/SharedPreferences;
    //   56: astore_2
    //   57: new 100	java/lang/StringBuilder
    //   60: dup
    //   61: invokespecial 101	java/lang/StringBuilder:<init>	()V
    //   64: astore_3
    //   65: aload_3
    //   66: ldc 124
    //   68: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   71: pop
    //   72: aload_3
    //   73: aload_1
    //   74: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   77: pop
    //   78: aload_3
    //   79: ldc 126
    //   81: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   84: pop
    //   85: aload_2
    //   86: aload_3
    //   87: invokevirtual 116	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   90: iconst_0
    //   91: invokeinterface 292 3 0
    //   96: ifeq +498 -> 594
    //   99: aload_0
    //   100: getfield 149	com/app/system/streaming/c/b:p	Lcom/app/system/streaming/c/c;
    //   103: aload_0
    //   104: getfield 45	com/app/system/streaming/c/b:h	I
    //   107: aload_0
    //   108: getfield 47	com/app/system/streaming/c/b:i	I
    //   111: invokevirtual 295	com/app/system/streaming/c/c:a	(II)V
    //   114: aload_0
    //   115: getfield 149	com/app/system/streaming/c/b:p	Lcom/app/system/streaming/c/c;
    //   118: astore_2
    //   119: aload_0
    //   120: getfield 43	com/app/system/streaming/c/b:q	Landroid/content/SharedPreferences;
    //   123: astore 4
    //   125: new 100	java/lang/StringBuilder
    //   128: dup
    //   129: invokespecial 101	java/lang/StringBuilder:<init>	()V
    //   132: astore_3
    //   133: aload_3
    //   134: ldc 124
    //   136: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   139: pop
    //   140: aload_3
    //   141: aload_1
    //   142: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   145: pop
    //   146: aload_3
    //   147: ldc -109
    //   149: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   152: pop
    //   153: aload_2
    //   154: aload 4
    //   156: aload_3
    //   157: invokevirtual 116	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   160: iconst_0
    //   161: invokeinterface 299 3 0
    //   166: invokevirtual 301	com/app/system/streaming/c/c:b	(I)V
    //   169: aload_0
    //   170: getfield 149	com/app/system/streaming/c/b:p	Lcom/app/system/streaming/c/c;
    //   173: astore_3
    //   174: aload_0
    //   175: getfield 43	com/app/system/streaming/c/b:q	Landroid/content/SharedPreferences;
    //   178: astore 4
    //   180: new 100	java/lang/StringBuilder
    //   183: dup
    //   184: invokespecial 101	java/lang/StringBuilder:<init>	()V
    //   187: astore_2
    //   188: aload_2
    //   189: ldc 124
    //   191: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   194: pop
    //   195: aload_2
    //   196: aload_1
    //   197: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   200: pop
    //   201: aload_2
    //   202: ldc 76
    //   204: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   207: pop
    //   208: aload_3
    //   209: aload 4
    //   211: aload_2
    //   212: invokevirtual 116	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   215: iconst_0
    //   216: invokeinterface 299 3 0
    //   221: invokevirtual 302	com/app/system/streaming/c/c:a	(I)V
    //   224: aload_0
    //   225: getfield 149	com/app/system/streaming/c/b:p	Lcom/app/system/streaming/c/c;
    //   228: astore_3
    //   229: aload_0
    //   230: getfield 43	com/app/system/streaming/c/b:q	Landroid/content/SharedPreferences;
    //   233: astore_2
    //   234: new 100	java/lang/StringBuilder
    //   237: dup
    //   238: invokespecial 101	java/lang/StringBuilder:<init>	()V
    //   241: astore 4
    //   243: aload 4
    //   245: ldc 124
    //   247: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   250: pop
    //   251: aload 4
    //   253: aload_1
    //   254: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   257: pop
    //   258: aload 4
    //   260: ldc -98
    //   262: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   265: pop
    //   266: aload_3
    //   267: aload_2
    //   268: aload 4
    //   270: invokevirtual 116	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   273: iconst_0
    //   274: invokeinterface 299 3 0
    //   279: invokevirtual 304	com/app/system/streaming/c/c:c	(I)V
    //   282: aload_0
    //   283: getfield 149	com/app/system/streaming/c/b:p	Lcom/app/system/streaming/c/c;
    //   286: astore_2
    //   287: aload_0
    //   288: getfield 43	com/app/system/streaming/c/b:q	Landroid/content/SharedPreferences;
    //   291: astore_3
    //   292: new 100	java/lang/StringBuilder
    //   295: dup
    //   296: invokespecial 101	java/lang/StringBuilder:<init>	()V
    //   299: astore 4
    //   301: aload 4
    //   303: ldc 124
    //   305: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   308: pop
    //   309: aload 4
    //   311: aload_1
    //   312: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   315: pop
    //   316: aload 4
    //   318: ldc -94
    //   320: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   323: pop
    //   324: aload_2
    //   325: aload_3
    //   326: aload 4
    //   328: invokevirtual 116	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   331: iconst_0
    //   332: invokeinterface 292 3 0
    //   337: invokevirtual 306	com/app/system/streaming/c/c:a	(Z)V
    //   340: aload_0
    //   341: getfield 149	com/app/system/streaming/c/b:p	Lcom/app/system/streaming/c/c;
    //   344: astore_2
    //   345: aload_0
    //   346: getfield 43	com/app/system/streaming/c/b:q	Landroid/content/SharedPreferences;
    //   349: astore 4
    //   351: new 100	java/lang/StringBuilder
    //   354: dup
    //   355: invokespecial 101	java/lang/StringBuilder:<init>	()V
    //   358: astore_3
    //   359: aload_3
    //   360: ldc 124
    //   362: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   365: pop
    //   366: aload_3
    //   367: aload_1
    //   368: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   371: pop
    //   372: aload_3
    //   373: ldc -89
    //   375: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   378: pop
    //   379: aload_2
    //   380: aload 4
    //   382: aload_3
    //   383: invokevirtual 116	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   386: iconst_0
    //   387: invokeinterface 292 3 0
    //   392: invokevirtual 308	com/app/system/streaming/c/c:b	(Z)V
    //   395: aload_0
    //   396: getfield 43	com/app/system/streaming/c/b:q	Landroid/content/SharedPreferences;
    //   399: astore_3
    //   400: new 100	java/lang/StringBuilder
    //   403: dup
    //   404: invokespecial 101	java/lang/StringBuilder:<init>	()V
    //   407: astore_2
    //   408: aload_2
    //   409: ldc 124
    //   411: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   414: pop
    //   415: aload_2
    //   416: aload_1
    //   417: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   420: pop
    //   421: aload_2
    //   422: ldc -85
    //   424: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   427: pop
    //   428: aload_0
    //   429: aload_3
    //   430: aload_2
    //   431: invokevirtual 116	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   434: ldc_w 278
    //   437: invokeinterface 312 3 0
    //   442: putfield 173	com/app/system/streaming/c/b:d	Ljava/lang/String;
    //   445: aload_0
    //   446: getfield 43	com/app/system/streaming/c/b:q	Landroid/content/SharedPreferences;
    //   449: astore_2
    //   450: new 100	java/lang/StringBuilder
    //   453: dup
    //   454: invokespecial 101	java/lang/StringBuilder:<init>	()V
    //   457: astore_3
    //   458: aload_3
    //   459: ldc 124
    //   461: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   464: pop
    //   465: aload_3
    //   466: aload_1
    //   467: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   470: pop
    //   471: aload_3
    //   472: ldc -77
    //   474: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   477: pop
    //   478: aload_0
    //   479: aload_2
    //   480: aload_3
    //   481: invokevirtual 116	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   484: iconst_0
    //   485: invokeinterface 299 3 0
    //   490: putfield 181	com/app/system/streaming/c/b:b	I
    //   493: aload_0
    //   494: getfield 43	com/app/system/streaming/c/b:q	Landroid/content/SharedPreferences;
    //   497: astore_2
    //   498: new 100	java/lang/StringBuilder
    //   501: dup
    //   502: invokespecial 101	java/lang/StringBuilder:<init>	()V
    //   505: astore_3
    //   506: aload_3
    //   507: ldc 124
    //   509: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   512: pop
    //   513: aload_3
    //   514: aload_1
    //   515: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   518: pop
    //   519: aload_3
    //   520: ldc -73
    //   522: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   525: pop
    //   526: aload_0
    //   527: aload_2
    //   528: aload_3
    //   529: invokevirtual 116	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   532: ldc_w 278
    //   535: invokeinterface 312 3 0
    //   540: putfield 185	com/app/system/streaming/c/b:t	Ljava/lang/String;
    //   543: aload_0
    //   544: getfield 43	com/app/system/streaming/c/b:q	Landroid/content/SharedPreferences;
    //   547: astore_3
    //   548: new 100	java/lang/StringBuilder
    //   551: dup
    //   552: invokespecial 101	java/lang/StringBuilder:<init>	()V
    //   555: astore_2
    //   556: aload_2
    //   557: ldc 124
    //   559: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   562: pop
    //   563: aload_2
    //   564: aload_1
    //   565: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   568: pop
    //   569: aload_2
    //   570: ldc -69
    //   572: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   575: pop
    //   576: aload_0
    //   577: aload_3
    //   578: aload_2
    //   579: invokevirtual 116	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   582: ldc_w 278
    //   585: invokeinterface 312 3 0
    //   590: putfield 189	com/app/system/streaming/c/b:u	Ljava/lang/String;
    //   593: return
    //   594: new 100	java/lang/StringBuilder
    //   597: dup
    //   598: invokespecial 101	java/lang/StringBuilder:<init>	()V
    //   601: astore_1
    //   602: aload_1
    //   603: ldc_w 314
    //   606: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   609: pop
    //   610: aload_1
    //   611: aload_0
    //   612: getfield 45	com/app/system/streaming/c/b:h	I
    //   615: invokevirtual 105	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   618: pop
    //   619: aload_1
    //   620: ldc 107
    //   622: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   625: pop
    //   626: aload_1
    //   627: aload_0
    //   628: getfield 47	com/app/system/streaming/c/b:i	I
    //   631: invokevirtual 105	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   634: pop
    //   635: aload_1
    //   636: ldc_w 316
    //   639: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   642: pop
    //   643: new 271	java/lang/RuntimeException
    //   646: dup
    //   647: aload_1
    //   648: invokevirtual 116	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   651: invokespecial 274	java/lang/RuntimeException:<init>	(Ljava/lang/String;)V
    //   654: athrow
    //   655: ldc_w 318
    //   658: invokestatic 323	com/app/system/streaming/c/a:a	(Ljava/lang/String;)[Lcom/app/system/streaming/c/a$a;
    //   661: astore_2
    //   662: ldc_w 318
    //   665: invokestatic 325	com/app/system/streaming/c/a:b	(Ljava/lang/String;)[Lcom/app/system/streaming/c/a$a;
    //   668: astore_1
    //   669: iconst_0
    //   670: istore 5
    //   672: iload 5
    //   674: aload_2
    //   675: arraylength
    //   676: if_icmpge +19 -> 695
    //   679: aload_2
    //   680: iload 5
    //   682: aaload
    //   683: getfield 330	com/app/system/streaming/c/a$a:b	[Ljava/lang/Integer;
    //   686: arraylength
    //   687: istore 6
    //   689: iinc 5 1
    //   692: goto -20 -> 672
    //   695: iconst_0
    //   696: istore 6
    //   698: iload 6
    //   700: aload_2
    //   701: arraylength
    //   702: if_icmpge +669 -> 1371
    //   705: iconst_0
    //   706: istore 7
    //   708: iload 7
    //   710: aload_2
    //   711: iload 6
    //   713: aaload
    //   714: getfield 330	com/app/system/streaming/c/a$a:b	[Ljava/lang/Integer;
    //   717: arraylength
    //   718: if_icmpge +647 -> 1365
    //   721: aload_0
    //   722: invokespecial 51	com/app/system/streaming/c/b:f	()V
    //   725: aload_0
    //   726: aload_2
    //   727: iload 6
    //   729: aaload
    //   730: getfield 332	com/app/system/streaming/c/a$a:a	Ljava/lang/String;
    //   733: putfield 173	com/app/system/streaming/c/b:d	Ljava/lang/String;
    //   736: aload_0
    //   737: aload_2
    //   738: iload 6
    //   740: aaload
    //   741: getfield 330	com/app/system/streaming/c/a$a:b	[Ljava/lang/Integer;
    //   744: iload 7
    //   746: aaload
    //   747: invokevirtual 337	java/lang/Integer:intValue	()I
    //   750: putfield 181	com/app/system/streaming/c/b:b	I
    //   753: aload_0
    //   754: getfield 149	com/app/system/streaming/c/b:p	Lcom/app/system/streaming/c/c;
    //   757: aload_0
    //   758: getfield 45	com/app/system/streaming/c/b:h	I
    //   761: aload_0
    //   762: getfield 47	com/app/system/streaming/c/b:i	I
    //   765: invokevirtual 295	com/app/system/streaming/c/c:a	(II)V
    //   768: aload_0
    //   769: getfield 149	com/app/system/streaming/c/b:p	Lcom/app/system/streaming/c/c;
    //   772: aload_0
    //   773: getfield 47	com/app/system/streaming/c/b:i	I
    //   776: invokevirtual 301	com/app/system/streaming/c/c:b	(I)V
    //   779: aload_0
    //   780: getfield 149	com/app/system/streaming/c/b:p	Lcom/app/system/streaming/c/c;
    //   783: aload_0
    //   784: getfield 45	com/app/system/streaming/c/b:h	I
    //   787: invokevirtual 302	com/app/system/streaming/c/c:a	(I)V
    //   790: aload_0
    //   791: getfield 149	com/app/system/streaming/c/b:p	Lcom/app/system/streaming/c/c;
    //   794: iconst_0
    //   795: invokevirtual 304	com/app/system/streaming/c/c:c	(I)V
    //   798: aload_0
    //   799: getfield 149	com/app/system/streaming/c/b:p	Lcom/app/system/streaming/c/c;
    //   802: aload_0
    //   803: getfield 181	com/app/system/streaming/c/b:b	I
    //   806: invokevirtual 339	com/app/system/streaming/c/c:d	(I)V
    //   809: aload_0
    //   810: invokespecial 341	com/app/system/streaming/c/b:i	()V
    //   813: aload_0
    //   814: aload_0
    //   815: getfield 149	com/app/system/streaming/c/b:p	Lcom/app/system/streaming/c/c;
    //   818: aload_0
    //   819: getfield 202	com/app/system/streaming/c/b:n	[B
    //   822: invokevirtual 344	com/app/system/streaming/c/c:a	([B)[B
    //   825: putfield 346	com/app/system/streaming/c/b:m	[B
    //   828: aload_0
    //   829: invokespecial 348	com/app/system/streaming/c/b:l	()V
    //   832: aload_0
    //   833: invokespecial 350	com/app/system/streaming/c/b:p	()J
    //   836: pop2
    //   837: aload_0
    //   838: invokespecial 352	com/app/system/streaming/c/b:q	()J
    //   841: pop2
    //   842: iconst_0
    //   843: istore 8
    //   845: iconst_0
    //   846: istore 5
    //   848: iload 8
    //   850: aload_1
    //   851: arraylength
    //   852: if_icmpge +113 -> 965
    //   855: iload 5
    //   857: ifne +108 -> 965
    //   860: iconst_0
    //   861: istore 9
    //   863: iload 9
    //   865: aload_1
    //   866: iload 8
    //   868: aaload
    //   869: getfield 330	com/app/system/streaming/c/a$a:b	[Ljava/lang/Integer;
    //   872: arraylength
    //   873: if_icmpge +86 -> 959
    //   876: iload 5
    //   878: ifne +81 -> 959
    //   881: aload_0
    //   882: aload_1
    //   883: iload 8
    //   885: aaload
    //   886: getfield 332	com/app/system/streaming/c/a$a:a	Ljava/lang/String;
    //   889: putfield 354	com/app/system/streaming/c/b:c	Ljava/lang/String;
    //   892: aload_0
    //   893: aload_1
    //   894: iload 8
    //   896: aaload
    //   897: getfield 330	com/app/system/streaming/c/a$a:b	[Ljava/lang/Integer;
    //   900: iload 9
    //   902: aaload
    //   903: invokevirtual 337	java/lang/Integer:intValue	()I
    //   906: putfield 60	com/app/system/streaming/c/b:a	I
    //   909: aload_0
    //   910: invokespecial 356	com/app/system/streaming/c/b:n	()V
    //   913: aload_0
    //   914: iconst_1
    //   915: invokespecial 358	com/app/system/streaming/c/b:c	(Z)J
    //   918: pop2
    //   919: aload_0
    //   920: invokespecial 360	com/app/system/streaming/c/b:o	()V
    //   923: iconst_1
    //   924: istore 5
    //   926: goto +16 -> 942
    //   929: astore_3
    //   930: goto +18 -> 948
    //   933: astore_3
    //   934: aload_3
    //   935: invokevirtual 363	java/lang/Exception:printStackTrace	()V
    //   938: aload_0
    //   939: invokespecial 360	com/app/system/streaming/c/b:o	()V
    //   942: iinc 9 1
    //   945: goto -82 -> 863
    //   948: aload_0
    //   949: invokespecial 360	com/app/system/streaming/c/b:o	()V
    //   952: aload_3
    //   953: athrow
    //   954: astore_3
    //   955: aload_0
    //   956: invokespecial 360	com/app/system/streaming/c/b:o	()V
    //   959: iinc 8 1
    //   962: goto -114 -> 848
    //   965: iload 5
    //   967: ifeq +216 -> 1183
    //   970: aload_0
    //   971: invokespecial 365	com/app/system/streaming/c/b:j	()Z
    //   974: ifeq +196 -> 1170
    //   977: aload_0
    //   978: invokespecial 367	com/app/system/streaming/c/b:k	()I
    //   981: istore 5
    //   983: iload 5
    //   985: ifle +59 -> 1044
    //   988: iload 5
    //   990: sipush 4096
    //   993: if_icmpge +38 -> 1031
    //   996: aload_0
    //   997: getfield 149	com/app/system/streaming/c/b:p	Lcom/app/system/streaming/c/c;
    //   1000: iload 5
    //   1002: invokevirtual 304	com/app/system/streaming/c/c:c	(I)V
    //   1005: aload_0
    //   1006: invokespecial 341	com/app/system/streaming/c/b:i	()V
    //   1009: aload_0
    //   1010: aload_0
    //   1011: getfield 149	com/app/system/streaming/c/b:p	Lcom/app/system/streaming/c/c;
    //   1014: aload_0
    //   1015: getfield 202	com/app/system/streaming/c/b:n	[B
    //   1018: invokevirtual 344	com/app/system/streaming/c/c:a	([B)[B
    //   1021: putfield 346	com/app/system/streaming/c/b:m	[B
    //   1024: aload_0
    //   1025: invokespecial 369	com/app/system/streaming/c/b:r	()V
    //   1028: goto +16 -> 1044
    //   1031: new 271	java/lang/RuntimeException
    //   1034: astore_3
    //   1035: aload_3
    //   1036: ldc_w 371
    //   1039: invokespecial 274	java/lang/RuntimeException:<init>	(Ljava/lang/String;)V
    //   1042: aload_3
    //   1043: athrow
    //   1044: aload_0
    //   1045: invokespecial 341	com/app/system/streaming/c/b:i	()V
    //   1048: aload_0
    //   1049: iconst_0
    //   1050: invokespecial 373	com/app/system/streaming/c/b:b	(Z)Z
    //   1053: ifne +35 -> 1088
    //   1056: aload_0
    //   1057: iconst_1
    //   1058: invokespecial 373	com/app/system/streaming/c/b:b	(Z)Z
    //   1061: ifeq +14 -> 1075
    //   1064: aload_0
    //   1065: getfield 149	com/app/system/streaming/c/b:p	Lcom/app/system/streaming/c/c;
    //   1068: iconst_1
    //   1069: invokevirtual 308	com/app/system/streaming/c/c:b	(Z)V
    //   1072: goto +16 -> 1088
    //   1075: new 271	java/lang/RuntimeException
    //   1078: astore_3
    //   1079: aload_3
    //   1080: ldc_w 375
    //   1083: invokespecial 274	java/lang/RuntimeException:<init>	(Ljava/lang/String;)V
    //   1086: aload_3
    //   1087: athrow
    //   1088: aload_0
    //   1089: iconst_1
    //   1090: invokespecial 376	com/app/system/streaming/c/b:a	(Z)V
    //   1093: new 100	java/lang/StringBuilder
    //   1096: astore_3
    //   1097: aload_3
    //   1098: invokespecial 101	java/lang/StringBuilder:<init>	()V
    //   1101: aload_3
    //   1102: ldc_w 378
    //   1105: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1108: pop
    //   1109: aload_3
    //   1110: aload_0
    //   1111: getfield 173	com/app/system/streaming/c/b:d	Ljava/lang/String;
    //   1114: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1117: pop
    //   1118: aload_3
    //   1119: ldc_w 380
    //   1122: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1125: pop
    //   1126: aload_3
    //   1127: aload_0
    //   1128: getfield 45	com/app/system/streaming/c/b:h	I
    //   1131: invokevirtual 105	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   1134: pop
    //   1135: aload_3
    //   1136: ldc 107
    //   1138: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1141: pop
    //   1142: aload_3
    //   1143: aload_0
    //   1144: getfield 47	com/app/system/streaming/c/b:i	I
    //   1147: invokevirtual 105	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   1150: pop
    //   1151: ldc_w 382
    //   1154: aload_3
    //   1155: invokevirtual 116	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1158: iconst_0
    //   1159: anewarray 4	java/lang/Object
    //   1162: invokestatic 387	com/security/d/a:e	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   1165: aload_0
    //   1166: invokespecial 389	com/app/system/streaming/c/b:m	()V
    //   1169: return
    //   1170: new 271	java/lang/RuntimeException
    //   1173: astore_3
    //   1174: aload_3
    //   1175: ldc_w 391
    //   1178: invokespecial 274	java/lang/RuntimeException:<init>	(Ljava/lang/String;)V
    //   1181: aload_3
    //   1182: athrow
    //   1183: new 271	java/lang/RuntimeException
    //   1186: astore_3
    //   1187: aload_3
    //   1188: ldc_w 393
    //   1191: invokespecial 274	java/lang/RuntimeException:<init>	(Ljava/lang/String;)V
    //   1194: aload_3
    //   1195: athrow
    //   1196: astore_1
    //   1197: goto +162 -> 1359
    //   1200: astore_3
    //   1201: new 395	java/io/StringWriter
    //   1204: astore 4
    //   1206: aload 4
    //   1208: invokespecial 396	java/io/StringWriter:<init>	()V
    //   1211: new 398	java/io/PrintWriter
    //   1214: astore 10
    //   1216: aload 10
    //   1218: aload 4
    //   1220: invokespecial 401	java/io/PrintWriter:<init>	(Ljava/io/Writer;)V
    //   1223: aload_3
    //   1224: aload 10
    //   1226: invokevirtual 404	java/lang/Exception:printStackTrace	(Ljava/io/PrintWriter;)V
    //   1229: aload 4
    //   1231: invokevirtual 405	java/io/StringWriter:toString	()Ljava/lang/String;
    //   1234: astore 4
    //   1236: new 100	java/lang/StringBuilder
    //   1239: astore 10
    //   1241: aload 10
    //   1243: invokespecial 101	java/lang/StringBuilder:<init>	()V
    //   1246: aload 10
    //   1248: ldc_w 407
    //   1251: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1254: pop
    //   1255: aload 10
    //   1257: aload_0
    //   1258: getfield 173	com/app/system/streaming/c/b:d	Ljava/lang/String;
    //   1261: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1264: pop
    //   1265: aload 10
    //   1267: ldc_w 409
    //   1270: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1273: pop
    //   1274: aload 10
    //   1276: aload_0
    //   1277: getfield 181	com/app/system/streaming/c/b:b	I
    //   1280: invokevirtual 105	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   1283: pop
    //   1284: aload 10
    //   1286: invokevirtual 116	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1289: astore 10
    //   1291: new 100	java/lang/StringBuilder
    //   1294: astore 11
    //   1296: aload 11
    //   1298: invokespecial 101	java/lang/StringBuilder:<init>	()V
    //   1301: aload 11
    //   1303: aload_0
    //   1304: getfield 280	com/app/system/streaming/c/b:e	Ljava/lang/String;
    //   1307: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1310: pop
    //   1311: aload 11
    //   1313: aload 10
    //   1315: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1318: pop
    //   1319: aload 11
    //   1321: ldc_w 411
    //   1324: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1327: pop
    //   1328: aload 11
    //   1330: aload 4
    //   1332: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1335: pop
    //   1336: aload_0
    //   1337: aload 11
    //   1339: invokevirtual 116	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1342: putfield 280	com/app/system/streaming/c/b:e	Ljava/lang/String;
    //   1345: aload_3
    //   1346: invokevirtual 363	java/lang/Exception:printStackTrace	()V
    //   1349: aload_0
    //   1350: invokespecial 389	com/app/system/streaming/c/b:m	()V
    //   1353: iinc 7 1
    //   1356: goto -648 -> 708
    //   1359: aload_0
    //   1360: invokespecial 389	com/app/system/streaming/c/b:m	()V
    //   1363: aload_1
    //   1364: athrow
    //   1365: iinc 6 1
    //   1368: goto -670 -> 698
    //   1371: aload_0
    //   1372: iconst_0
    //   1373: invokespecial 376	com/app/system/streaming/c/b:a	(Z)V
    //   1376: new 100	java/lang/StringBuilder
    //   1379: dup
    //   1380: invokespecial 101	java/lang/StringBuilder:<init>	()V
    //   1383: astore_1
    //   1384: aload_1
    //   1385: ldc_w 413
    //   1388: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1391: pop
    //   1392: aload_1
    //   1393: aload_0
    //   1394: getfield 45	com/app/system/streaming/c/b:h	I
    //   1397: invokevirtual 105	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   1400: pop
    //   1401: aload_1
    //   1402: ldc 107
    //   1404: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1407: pop
    //   1408: aload_1
    //   1409: aload_0
    //   1410: getfield 47	com/app/system/streaming/c/b:i	I
    //   1413: invokevirtual 105	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   1416: pop
    //   1417: ldc_w 382
    //   1420: aload_1
    //   1421: invokevirtual 116	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1424: iconst_0
    //   1425: anewarray 4	java/lang/Object
    //   1428: invokestatic 415	com/security/d/a:a	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   1431: new 100	java/lang/StringBuilder
    //   1434: dup
    //   1435: invokespecial 101	java/lang/StringBuilder:<init>	()V
    //   1438: astore_1
    //   1439: aload_1
    //   1440: ldc_w 413
    //   1443: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1446: pop
    //   1447: aload_1
    //   1448: aload_0
    //   1449: getfield 45	com/app/system/streaming/c/b:h	I
    //   1452: invokevirtual 105	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   1455: pop
    //   1456: aload_1
    //   1457: ldc 107
    //   1459: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1462: pop
    //   1463: aload_1
    //   1464: aload_0
    //   1465: getfield 47	com/app/system/streaming/c/b:i	I
    //   1468: invokevirtual 105	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   1471: pop
    //   1472: new 271	java/lang/RuntimeException
    //   1475: dup
    //   1476: aload_1
    //   1477: invokevirtual 116	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1480: invokespecial 274	java/lang/RuntimeException:<init>	(Ljava/lang/String;)V
    //   1483: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	1484	0	this	b
    //   14	880	1	localObject1	Object
    //   1196	168	1	localObject2	Object
    //   1383	94	1	localStringBuilder1	StringBuilder
    //   56	682	2	localObject3	Object
    //   64	514	3	localObject4	Object
    //   929	1	3	localObject5	Object
    //   933	20	3	localException1	Exception
    //   954	1	3	localException2	Exception
    //   1034	161	3	localObject6	Object
    //   1200	146	3	localException3	Exception
    //   123	1208	4	localObject7	Object
    //   670	331	5	i1	int
    //   687	679	6	i2	int
    //   706	648	7	i3	int
    //   843	117	8	i4	int
    //   861	82	9	i5	int
    //   1214	100	10	localObject8	Object
    //   1294	44	11	localStringBuilder2	StringBuilder
    // Exception table:
    //   from	to	target	type
    //   913	919	929	finally
    //   934	938	929	finally
    //   913	919	933	java/lang/Exception
    //   909	913	954	java/lang/Exception
    //   828	842	1196	finally
    //   848	855	1196	finally
    //   863	876	1196	finally
    //   881	909	1196	finally
    //   909	913	1196	finally
    //   919	923	1196	finally
    //   938	942	1196	finally
    //   948	954	1196	finally
    //   955	959	1196	finally
    //   970	983	1196	finally
    //   996	1028	1196	finally
    //   1031	1044	1196	finally
    //   1044	1072	1196	finally
    //   1075	1088	1196	finally
    //   1088	1165	1196	finally
    //   1170	1183	1196	finally
    //   1183	1196	1196	finally
    //   1201	1349	1196	finally
    //   828	842	1200	java/lang/Exception
    //   848	855	1200	java/lang/Exception
    //   863	876	1200	java/lang/Exception
    //   881	909	1200	java/lang/Exception
    //   919	923	1200	java/lang/Exception
    //   938	942	1200	java/lang/Exception
    //   948	954	1200	java/lang/Exception
    //   955	959	1200	java/lang/Exception
    //   970	983	1200	java/lang/Exception
    //   996	1028	1200	java/lang/Exception
    //   1031	1044	1200	java/lang/Exception
    //   1044	1072	1200	java/lang/Exception
    //   1075	1088	1200	java/lang/Exception
    //   1088	1165	1200	java/lang/Exception
    //   1170	1183	1200	java/lang/Exception
    //   1183	1196	1200	java/lang/Exception
  }
  
  private boolean h()
  {
    Object localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append(this.h);
    ((StringBuilder)localObject1).append("x");
    ((StringBuilder)localObject1).append(this.i);
    ((StringBuilder)localObject1).append("-");
    localObject1 = ((StringBuilder)localObject1).toString();
    Object localObject2 = this.q;
    if (localObject2 == null) {
      return true;
    }
    Object localObject3 = new StringBuilder();
    ((StringBuilder)localObject3).append("libstreaming-");
    ((StringBuilder)localObject3).append((String)localObject1);
    ((StringBuilder)localObject3).append("lastSdk");
    if (((SharedPreferences)localObject2).contains(((StringBuilder)localObject3).toString()))
    {
      localObject3 = this.q;
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("libstreaming-");
      ((StringBuilder)localObject2).append((String)localObject1);
      ((StringBuilder)localObject2).append("lastSdk");
      int i1 = ((SharedPreferences)localObject3).getInt(((StringBuilder)localObject2).toString(), 0);
      localObject3 = this.q;
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("libstreaming-");
      ((StringBuilder)localObject2).append((String)localObject1);
      ((StringBuilder)localObject2).append("lastVersion");
      int i2 = ((SharedPreferences)localObject3).getInt(((StringBuilder)localObject2).toString(), 0);
      return (Build.VERSION.SDK_INT > i1) || (3 > i2);
    }
    return true;
  }
  
  private void i()
  {
    this.n = new byte[this.j * 3 / 2];
    int i3;
    for (int i1 = 0;; i1++)
    {
      int i2 = this.j;
      i3 = i2;
      if (i1 >= i2) {
        break;
      }
      this.n[i1] = ((byte)(byte)(i1 % 199 + 40));
    }
    while (i3 < this.j * 3 / 2)
    {
      byte[] arrayOfByte = this.n;
      arrayOfByte[i3] = ((byte)(byte)(i3 % 200 + 40));
      arrayOfByte[(i3 + 1)] = ((byte)(byte)((i3 + 99) % 200 + 40));
      i3 += 2;
    }
  }
  
  private boolean j()
  {
    boolean bool = false;
    int i1 = 0;
    int i4;
    for (int i2 = 0; i1 < 34; i2 = i4)
    {
      for (int i3 = 0;; i3 += 10)
      {
        i4 = i2;
        if (i3 >= this.j) {
          break;
        }
        byte[] arrayOfByte = this.n;
        i4 = arrayOfByte[i3];
        byte[][] arrayOfByte1 = this.s;
        int i5 = (i4 & 0xFF) - (arrayOfByte1[i1][i3] & 0xFF);
        i4 = i3 + 1;
        int i6 = (arrayOfByte[i4] & 0xFF) - (arrayOfByte1[i1][i4] & 0xFF);
        i4 = i5;
        if (i5 < 0) {
          i4 = -i5;
        }
        i5 = i6;
        if (i6 < 0) {
          i5 = -i6;
        }
        if ((i4 > 50) && (i5 > 50))
        {
          this.s[i1] = null;
          i4 = i2 + 1;
          break;
        }
      }
      i1++;
    }
    if (i2 <= 17) {
      bool = true;
    }
    return bool;
  }
  
  private int k()
  {
    int i1 = this.j * 3 / 2 - 1;
    int[] arrayOfInt = new int[34];
    int i2 = 0;
    int i4;
    for (int i3 = 0; i2 < 34; i3 = i4)
    {
      i4 = i3;
      if (this.s[i2] != null)
      {
        for (int i5 = 0; (i5 < i1) && ((this.s[i2][(i1 - i5)] & 0xFF) < 50); i5 += 2) {}
        i4 = i3;
        if (i5 > 0)
        {
          arrayOfInt[i2] = (i5 >> 6 << 6);
          i4 = i3;
          if (arrayOfInt[i2] > i3) {
            i4 = arrayOfInt[i2];
          }
        }
      }
      i2++;
    }
    return i3 >> 6 << 6;
  }
  
  private void l()
  {
    this.f = MediaCodec.createByCodecName(this.d);
    MediaFormat localMediaFormat = MediaFormat.createVideoFormat("video/avc", this.h, this.i);
    localMediaFormat.setInteger("bitrate", 1000000);
    localMediaFormat.setInteger("frame-rate", 20);
    localMediaFormat.setInteger("color-format", this.b);
    localMediaFormat.setInteger("i-frame-interval", 1);
    this.f.configure(localMediaFormat, null, null, 1);
    this.f.start();
  }
  
  private void m()
  {
    MediaCodec localMediaCodec = this.f;
    if (localMediaCodec != null) {}
    try
    {
      localMediaCodec.stop();
    }
    catch (Exception localException1)
    {
      try
      {
        for (;;)
        {
          this.f.release();
          return;
          localException1 = localException1;
        }
      }
      catch (Exception localException2)
      {
        for (;;) {}
      }
    }
  }
  
  private void n()
  {
    byte[] arrayOfByte = new byte[4];
    byte[] tmp5_4 = arrayOfByte;
    tmp5_4[0] = 0;
    byte[] tmp10_5 = tmp5_4;
    tmp10_5[1] = 0;
    byte[] tmp15_10 = tmp10_5;
    tmp15_10[2] = 0;
    byte[] tmp20_15 = tmp15_10;
    tmp20_15[3] = 1;
    tmp20_15;
    ByteBuffer localByteBuffer = ByteBuffer.allocate(this.k.length + 4 + 4 + this.l.length);
    localByteBuffer.put(new byte[] { 0, 0, 0, 1 });
    localByteBuffer.put(this.k);
    localByteBuffer.put(new byte[] { 0, 0, 0, 1 });
    localByteBuffer.put(this.l);
    this.g = MediaCodec.createByCodecName(this.c);
    Object localObject = MediaFormat.createVideoFormat("video/avc", this.h, this.i);
    ((MediaFormat)localObject).setByteBuffer("csd-0", localByteBuffer);
    ((MediaFormat)localObject).setInteger("color-format", this.a);
    this.g.configure((MediaFormat)localObject, null, null, 0);
    this.g.start();
    localObject = this.g.getInputBuffers();
    int i1 = this.g.dequeueInputBuffer(50000L);
    if (i1 >= 0)
    {
      localObject[i1].clear();
      localObject[i1].put(arrayOfByte);
      localObject[i1].put(this.k);
      this.g.queueInputBuffer(i1, 0, localObject[i1].position(), s(), 0);
    }
    i1 = this.g.dequeueInputBuffer(50000L);
    if (i1 >= 0)
    {
      localObject[i1].clear();
      localObject[i1].put(arrayOfByte);
      localObject[i1].put(this.l);
      this.g.queueInputBuffer(i1, 0, localObject[i1].position(), s(), 0);
    }
  }
  
  private void o()
  {
    MediaCodec localMediaCodec = this.g;
    if (localMediaCodec != null) {}
    try
    {
      localMediaCodec.stop();
    }
    catch (Exception localException1)
    {
      try
      {
        for (;;)
        {
          this.g.release();
          return;
          localException1 = localException1;
        }
      }
      catch (Exception localException2)
      {
        for (;;) {}
      }
    }
  }
  
  private long p()
  {
    ByteBuffer[] arrayOfByteBuffer = this.f.getInputBuffers();
    Object localObject1 = this.f.getOutputBuffers();
    MediaCodec.BufferInfo localBufferInfo = new MediaCodec.BufferInfo();
    Object localObject2 = new byte[''];
    long l1 = s();
    long l2 = 0L;
    int i1 = 4;
    int i2 = 4;
    while ((l2 < 3000000L) && ((this.k == null) || (this.l == null)))
    {
      int i3 = this.f.dequeueInputBuffer(50000L);
      ByteBuffer localByteBuffer;
      Object localObject3;
      if (i3 >= 0)
      {
        if (arrayOfByteBuffer[i3].capacity() >= this.m.length) {
          bool = true;
        } else {
          bool = false;
        }
        a(bool, "The input buffer is not big enough.");
        arrayOfByteBuffer[i3].clear();
        localByteBuffer = arrayOfByteBuffer[i3];
        localObject3 = this.m;
        localByteBuffer.put((byte[])localObject3, 0, localObject3.length);
        this.f.queueInputBuffer(i3, 0, this.m.length, s(), 0);
      }
      int i4 = this.f.dequeueOutputBuffer(localBufferInfo, 50000L);
      if (i4 == -2)
      {
        localObject1 = this.f.getOutputFormat();
        localObject2 = ((MediaFormat)localObject1).getByteBuffer("csd-0");
        localObject1 = ((MediaFormat)localObject1).getByteBuffer("csd-1");
        this.k = new byte[((ByteBuffer)localObject2).capacity() - 4];
        ((ByteBuffer)localObject2).position(4);
        localObject3 = this.k;
        ((ByteBuffer)localObject2).get((byte[])localObject3, 0, localObject3.length);
        this.l = new byte[((ByteBuffer)localObject1).capacity() - 4];
        ((ByteBuffer)localObject1).position(4);
        localObject2 = this.l;
        ((ByteBuffer)localObject1).get((byte[])localObject2, 0, localObject2.length);
        break;
      }
      if (i4 == -3)
      {
        localObject1 = this.f.getOutputBuffers();
      }
      else if (i4 >= 0)
      {
        int i5 = localBufferInfo.size;
        int i6;
        if (i5 < 128)
        {
          localByteBuffer = localObject1[i4];
          localObject3 = localObject2;
          localByteBuffer.get((byte[])localObject3, 0, i5);
          i6 = i1;
          i3 = i2;
          if (i5 > 0)
          {
            i6 = i1;
            i3 = i2;
            if (localObject3[0] == 0)
            {
              i6 = i1;
              i3 = i2;
              if (localObject3[1] == 0)
              {
                i6 = i1;
                i3 = i2;
                if (localObject3[2] == 0)
                {
                  i6 = i1;
                  i3 = i2;
                  if (localObject3[3] == 1) {
                    for (;;)
                    {
                      i6 = i1;
                      i3 = i2;
                      if (i1 >= i5) {
                        break;
                      }
                      for (;;)
                      {
                        if ((localObject3[(i1 + 0)] == 0) && (localObject3[(i1 + 1)] == 0) && (localObject3[(i1 + 2)] == 0)) {
                          if (localObject3[(i1 + 3)] == 1) {
                            break;
                          }
                        }
                        if (i1 + 3 >= i5) {
                          break;
                        }
                        i1++;
                      }
                      i3 = i1;
                      if (i1 + 3 >= i5) {
                        i3 = i5;
                      }
                      if ((localObject3[i2] & 0x1F) == 7)
                      {
                        i1 = i3 - i2;
                        this.k = new byte[i1];
                        System.arraycopy(localObject3, i2, this.k, 0, i1);
                      }
                      else
                      {
                        i1 = i3 - i2;
                        this.l = new byte[i1];
                        System.arraycopy(localObject3, i2, this.l, 0, i1);
                      }
                      i2 = i3 + 4;
                      i1 = i2;
                    }
                  }
                }
              }
            }
          }
        }
        else
        {
          i3 = i2;
          i6 = i1;
        }
        this.f.releaseOutputBuffer(i4, false);
        i1 = i6;
        i2 = i3;
      }
      l2 = s() - l1;
    }
    boolean bool = true;
    if ((this.l == null) || (this.k == null)) {
      bool = false;
    }
    a(bool, "Could not determine the SPS & PPS.");
    localObject2 = this.l;
    this.t = Base64.encodeToString((byte[])localObject2, 0, localObject2.length, 2);
    localObject2 = this.k;
    this.u = Base64.encodeToString((byte[])localObject2, 0, localObject2.length, 2);
    return l2;
  }
  
  private long q()
  {
    long l1 = s();
    MediaCodec.BufferInfo localBufferInfo = new MediaCodec.BufferInfo();
    ByteBuffer[] arrayOfByteBuffer = this.f.getInputBuffers();
    Object localObject1 = this.f.getOutputBuffers();
    long l2 = 0L;
    int i2;
    for (int i1 = 0; l2 < 5000000L; i1 = i2)
    {
      i2 = this.f.dequeueInputBuffer(50000L);
      Object localObject2;
      Object localObject3;
      if (i2 >= 0)
      {
        boolean bool;
        if (arrayOfByteBuffer[i2].capacity() >= this.m.length) {
          bool = true;
        } else {
          bool = false;
        }
        a(bool, "The input buffer is not big enough.");
        arrayOfByteBuffer[i2].clear();
        localObject2 = arrayOfByteBuffer[i2];
        localObject3 = this.m;
        ((ByteBuffer)localObject2).put((byte[])localObject3, 0, localObject3.length);
        this.f.queueInputBuffer(i2, 0, this.m.length, s(), 0);
      }
      int i3 = this.f.dequeueOutputBuffer(localBufferInfo, 50000L);
      if (i3 == -3)
      {
        localObject2 = this.f.getOutputBuffers();
        i2 = i1;
      }
      else
      {
        localObject2 = localObject1;
        i2 = i1;
        if (i3 >= 0)
        {
          this.r[i1] = new byte[localBufferInfo.size];
          localObject1[i3].clear();
          localObject2 = localObject1[i3];
          localObject3 = this.r;
          i2 = i1 + 1;
          ((ByteBuffer)localObject2).get(localObject3[i1], 0, localBufferInfo.size);
          this.f.releaseOutputBuffer(i3, false);
          if (i2 >= 50)
          {
            a(this.f);
            return l2;
          }
          localObject2 = localObject1;
        }
      }
      l2 = s() - l1;
      localObject1 = localObject2;
    }
    throw new RuntimeException("The encoder is too slow.");
  }
  
  private void r()
  {
    q();
    try
    {
      n();
      c(true);
      return;
    }
    finally
    {
      o();
    }
  }
  
  private long s()
  {
    return System.nanoTime() / 1000L;
  }
  
  public String a()
  {
    return this.t;
  }
  
  public String b()
  {
    return this.u;
  }
  
  public String c()
  {
    return this.d;
  }
  
  public int d()
  {
    return this.b;
  }
  
  public c e()
  {
    return this.p;
  }
}


/* Location:              ~/com/app/system/streaming/c/b.class
 *
 * Reversed by:           J
 */