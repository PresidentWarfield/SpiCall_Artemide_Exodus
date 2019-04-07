package io.fabric.sdk.android.services.b;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.IInterface;
import io.fabric.sdk.android.c;
import io.fabric.sdk.android.k;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

class e
  implements f
{
  private final Context a;
  
  public e(Context paramContext)
  {
    this.a = paramContext.getApplicationContext();
  }
  
  /* Error */
  public b a()
  {
    // Byte code:
    //   0: invokestatic 42	android/os/Looper:myLooper	()Landroid/os/Looper;
    //   3: invokestatic 45	android/os/Looper:getMainLooper	()Landroid/os/Looper;
    //   6: if_acmpne +17 -> 23
    //   9: invokestatic 51	io/fabric/sdk/android/c:g	()Lio/fabric/sdk/android/k;
    //   12: ldc 53
    //   14: ldc 55
    //   16: invokeinterface 60 3 0
    //   21: aconst_null
    //   22: areturn
    //   23: aload_0
    //   24: getfield 28	io/fabric/sdk/android/services/b/e:a	Landroid/content/Context;
    //   27: invokevirtual 64	android/content/Context:getPackageManager	()Landroid/content/pm/PackageManager;
    //   30: ldc 66
    //   32: iconst_0
    //   33: invokevirtual 72	android/content/pm/PackageManager:getPackageInfo	(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;
    //   36: pop
    //   37: new 10	io/fabric/sdk/android/services/b/e$a
    //   40: dup
    //   41: aconst_null
    //   42: invokespecial 75	io/fabric/sdk/android/services/b/e$a:<init>	(Lio/fabric/sdk/android/services/b/e$1;)V
    //   45: astore_1
    //   46: new 77	android/content/Intent
    //   49: dup
    //   50: ldc 79
    //   52: invokespecial 82	android/content/Intent:<init>	(Ljava/lang/String;)V
    //   55: astore_2
    //   56: aload_2
    //   57: ldc 84
    //   59: invokevirtual 88	android/content/Intent:setPackage	(Ljava/lang/String;)Landroid/content/Intent;
    //   62: pop
    //   63: aload_0
    //   64: getfield 28	io/fabric/sdk/android/services/b/e:a	Landroid/content/Context;
    //   67: aload_2
    //   68: aload_1
    //   69: iconst_1
    //   70: invokevirtual 92	android/content/Context:bindService	(Landroid/content/Intent;Landroid/content/ServiceConnection;I)Z
    //   73: istore_3
    //   74: iload_3
    //   75: ifeq +84 -> 159
    //   78: new 13	io/fabric/sdk/android/services/b/e$b
    //   81: astore 4
    //   83: aload 4
    //   85: aload_1
    //   86: invokevirtual 95	io/fabric/sdk/android/services/b/e$a:a	()Landroid/os/IBinder;
    //   89: invokespecial 98	io/fabric/sdk/android/services/b/e$b:<init>	(Landroid/os/IBinder;)V
    //   92: new 100	io/fabric/sdk/android/services/b/b
    //   95: astore_2
    //   96: aload_2
    //   97: aload 4
    //   99: invokevirtual 103	io/fabric/sdk/android/services/b/e$b:a	()Ljava/lang/String;
    //   102: aload 4
    //   104: invokevirtual 106	io/fabric/sdk/android/services/b/e$b:b	()Z
    //   107: invokespecial 109	io/fabric/sdk/android/services/b/b:<init>	(Ljava/lang/String;Z)V
    //   110: aload_0
    //   111: getfield 28	io/fabric/sdk/android/services/b/e:a	Landroid/content/Context;
    //   114: aload_1
    //   115: invokevirtual 113	android/content/Context:unbindService	(Landroid/content/ServiceConnection;)V
    //   118: aload_2
    //   119: areturn
    //   120: astore_2
    //   121: goto +28 -> 149
    //   124: astore_2
    //   125: invokestatic 51	io/fabric/sdk/android/c:g	()Lio/fabric/sdk/android/k;
    //   128: ldc 53
    //   130: ldc 115
    //   132: aload_2
    //   133: invokeinterface 119 4 0
    //   138: aload_0
    //   139: getfield 28	io/fabric/sdk/android/services/b/e:a	Landroid/content/Context;
    //   142: aload_1
    //   143: invokevirtual 113	android/content/Context:unbindService	(Landroid/content/ServiceConnection;)V
    //   146: goto +42 -> 188
    //   149: aload_0
    //   150: getfield 28	io/fabric/sdk/android/services/b/e:a	Landroid/content/Context;
    //   153: aload_1
    //   154: invokevirtual 113	android/content/Context:unbindService	(Landroid/content/ServiceConnection;)V
    //   157: aload_2
    //   158: athrow
    //   159: invokestatic 51	io/fabric/sdk/android/c:g	()Lio/fabric/sdk/android/k;
    //   162: ldc 53
    //   164: ldc 121
    //   166: invokeinterface 60 3 0
    //   171: goto +17 -> 188
    //   174: astore_1
    //   175: invokestatic 51	io/fabric/sdk/android/c:g	()Lio/fabric/sdk/android/k;
    //   178: ldc 53
    //   180: ldc 121
    //   182: aload_1
    //   183: invokeinterface 123 4 0
    //   188: aconst_null
    //   189: areturn
    //   190: astore_1
    //   191: invokestatic 51	io/fabric/sdk/android/c:g	()Lio/fabric/sdk/android/k;
    //   194: ldc 53
    //   196: ldc 125
    //   198: aload_1
    //   199: invokeinterface 123 4 0
    //   204: aconst_null
    //   205: areturn
    //   206: astore_1
    //   207: invokestatic 51	io/fabric/sdk/android/c:g	()Lio/fabric/sdk/android/k;
    //   210: ldc 53
    //   212: ldc 127
    //   214: invokeinterface 60 3 0
    //   219: aconst_null
    //   220: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	221	0	this	e
    //   45	109	1	locala	a
    //   174	9	1	localThrowable	Throwable
    //   190	9	1	localException1	Exception
    //   206	1	1	localNameNotFoundException	android.content.pm.PackageManager.NameNotFoundException
    //   55	64	2	localObject1	Object
    //   120	1	2	localObject2	Object
    //   124	34	2	localException2	Exception
    //   73	2	3	bool	boolean
    //   81	22	4	localb	b
    // Exception table:
    //   from	to	target	type
    //   78	110	120	finally
    //   125	138	120	finally
    //   78	110	124	java/lang/Exception
    //   63	74	174	java/lang/Throwable
    //   110	118	174	java/lang/Throwable
    //   138	146	174	java/lang/Throwable
    //   149	159	174	java/lang/Throwable
    //   159	171	174	java/lang/Throwable
    //   23	37	190	java/lang/Exception
    //   23	37	206	android/content/pm/PackageManager$NameNotFoundException
  }
  
  private static final class a
    implements ServiceConnection
  {
    private boolean a = false;
    private final LinkedBlockingQueue<IBinder> b = new LinkedBlockingQueue(1);
    
    public IBinder a()
    {
      if (this.a) {
        c.g().e("Fabric", "getBinder already called");
      }
      this.a = true;
      try
      {
        IBinder localIBinder = (IBinder)this.b.poll(200L, TimeUnit.MILLISECONDS);
        return localIBinder;
      }
      catch (InterruptedException localInterruptedException) {}
      return null;
    }
    
    public void onServiceConnected(ComponentName paramComponentName, IBinder paramIBinder)
    {
      try
      {
        this.b.put(paramIBinder);
        return;
      }
      catch (InterruptedException paramComponentName)
      {
        for (;;) {}
      }
    }
    
    public void onServiceDisconnected(ComponentName paramComponentName)
    {
      this.b.clear();
    }
  }
  
  private static final class b
    implements IInterface
  {
    private final IBinder a;
    
    public b(IBinder paramIBinder)
    {
      this.a = paramIBinder;
    }
    
    /* Error */
    public String a()
    {
      // Byte code:
      //   0: invokestatic 28	android/os/Parcel:obtain	()Landroid/os/Parcel;
      //   3: astore_1
      //   4: invokestatic 28	android/os/Parcel:obtain	()Landroid/os/Parcel;
      //   7: astore_2
      //   8: aload_1
      //   9: ldc 30
      //   11: invokevirtual 34	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
      //   14: aload_0
      //   15: getfield 18	io/fabric/sdk/android/services/b/e$b:a	Landroid/os/IBinder;
      //   18: iconst_1
      //   19: aload_1
      //   20: aload_2
      //   21: iconst_0
      //   22: invokeinterface 40 5 0
      //   27: pop
      //   28: aload_2
      //   29: invokevirtual 43	android/os/Parcel:readException	()V
      //   32: aload_2
      //   33: invokevirtual 46	android/os/Parcel:readString	()Ljava/lang/String;
      //   36: astore_3
      //   37: aload_2
      //   38: invokevirtual 49	android/os/Parcel:recycle	()V
      //   41: aload_1
      //   42: invokevirtual 49	android/os/Parcel:recycle	()V
      //   45: goto +30 -> 75
      //   48: astore_3
      //   49: goto +28 -> 77
      //   52: astore_3
      //   53: invokestatic 55	io/fabric/sdk/android/c:g	()Lio/fabric/sdk/android/k;
      //   56: ldc 57
      //   58: ldc 59
      //   60: invokeinterface 64 3 0
      //   65: aload_2
      //   66: invokevirtual 49	android/os/Parcel:recycle	()V
      //   69: aload_1
      //   70: invokevirtual 49	android/os/Parcel:recycle	()V
      //   73: aconst_null
      //   74: astore_3
      //   75: aload_3
      //   76: areturn
      //   77: aload_2
      //   78: invokevirtual 49	android/os/Parcel:recycle	()V
      //   81: aload_1
      //   82: invokevirtual 49	android/os/Parcel:recycle	()V
      //   85: aload_3
      //   86: athrow
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	87	0	this	b
      //   3	79	1	localParcel1	android.os.Parcel
      //   7	71	2	localParcel2	android.os.Parcel
      //   36	1	3	str1	String
      //   48	1	3	localObject	Object
      //   52	1	3	localException	Exception
      //   74	12	3	str2	String
      // Exception table:
      //   from	to	target	type
      //   8	37	48	finally
      //   53	65	48	finally
      //   8	37	52	java/lang/Exception
    }
    
    public IBinder asBinder()
    {
      return this.a;
    }
    
    /* Error */
    public boolean b()
    {
      // Byte code:
      //   0: invokestatic 28	android/os/Parcel:obtain	()Landroid/os/Parcel;
      //   3: astore_1
      //   4: invokestatic 28	android/os/Parcel:obtain	()Landroid/os/Parcel;
      //   7: astore_2
      //   8: iconst_0
      //   9: istore_3
      //   10: aload_1
      //   11: ldc 30
      //   13: invokevirtual 34	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
      //   16: aload_1
      //   17: iconst_1
      //   18: invokevirtual 71	android/os/Parcel:writeInt	(I)V
      //   21: aload_0
      //   22: getfield 18	io/fabric/sdk/android/services/b/e$b:a	Landroid/os/IBinder;
      //   25: iconst_2
      //   26: aload_1
      //   27: aload_2
      //   28: iconst_0
      //   29: invokeinterface 40 5 0
      //   34: pop
      //   35: aload_2
      //   36: invokevirtual 43	android/os/Parcel:readException	()V
      //   39: aload_2
      //   40: invokevirtual 75	android/os/Parcel:readInt	()I
      //   43: istore 4
      //   45: iload 4
      //   47: ifeq +27 -> 74
      //   50: iconst_1
      //   51: istore_3
      //   52: goto +22 -> 74
      //   55: astore 5
      //   57: goto +27 -> 84
      //   60: astore 5
      //   62: invokestatic 55	io/fabric/sdk/android/c:g	()Lio/fabric/sdk/android/k;
      //   65: ldc 57
      //   67: ldc 77
      //   69: invokeinterface 64 3 0
      //   74: aload_2
      //   75: invokevirtual 49	android/os/Parcel:recycle	()V
      //   78: aload_1
      //   79: invokevirtual 49	android/os/Parcel:recycle	()V
      //   82: iload_3
      //   83: ireturn
      //   84: aload_2
      //   85: invokevirtual 49	android/os/Parcel:recycle	()V
      //   88: aload_1
      //   89: invokevirtual 49	android/os/Parcel:recycle	()V
      //   92: aload 5
      //   94: athrow
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	95	0	this	b
      //   3	86	1	localParcel1	android.os.Parcel
      //   7	78	2	localParcel2	android.os.Parcel
      //   9	74	3	bool	boolean
      //   43	3	4	i	int
      //   55	1	5	localObject	Object
      //   60	33	5	localException	Exception
      // Exception table:
      //   from	to	target	type
      //   10	45	55	finally
      //   62	74	55	finally
      //   10	45	60	java/lang/Exception
    }
  }
}


/* Location:              ~/io/fabric/sdk/android/services/b/e.class
 *
 * Reversed by:           J
 */