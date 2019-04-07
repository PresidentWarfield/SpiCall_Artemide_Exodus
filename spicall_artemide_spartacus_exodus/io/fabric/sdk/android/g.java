package io.fabric.sdk.android;

import io.fabric.sdk.android.services.b.w;
import io.fabric.sdk.android.services.concurrency.e;

class g<Result>
  extends io.fabric.sdk.android.services.concurrency.f<Void, Void, Result>
{
  final h<Result> a;
  
  public g(h<Result> paramh)
  {
    this.a = paramh;
  }
  
  private w a(String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(this.a.getIdentifier());
    localStringBuilder.append(".");
    localStringBuilder.append(paramString);
    paramString = new w(localStringBuilder.toString(), "KitInitialization");
    paramString.a();
    return paramString;
  }
  
  protected Result a(Void... paramVarArgs)
  {
    w localw = a("doInBackground");
    if (!d()) {
      paramVarArgs = this.a.doInBackground();
    } else {
      paramVarArgs = null;
    }
    localw.b();
    return paramVarArgs;
  }
  
  /* Error */
  protected void a()
  {
    // Byte code:
    //   0: aload_0
    //   1: invokespecial 72	io/fabric/sdk/android/services/concurrency/f:a	()V
    //   4: aload_0
    //   5: ldc 74
    //   7: invokespecial 56	io/fabric/sdk/android/g:a	(Ljava/lang/String;)Lio/fabric/sdk/android/services/b/w;
    //   10: astore_1
    //   11: aload_0
    //   12: getfield 15	io/fabric/sdk/android/g:a	Lio/fabric/sdk/android/h;
    //   15: invokevirtual 76	io/fabric/sdk/android/h:onPreExecute	()Z
    //   18: istore_2
    //   19: aload_1
    //   20: invokevirtual 66	io/fabric/sdk/android/services/b/w:b	()V
    //   23: iload_2
    //   24: ifne +37 -> 61
    //   27: aload_0
    //   28: iconst_1
    //   29: invokevirtual 79	io/fabric/sdk/android/g:a	(Z)Z
    //   32: pop
    //   33: goto +28 -> 61
    //   36: astore_3
    //   37: goto +28 -> 65
    //   40: astore_3
    //   41: invokestatic 85	io/fabric/sdk/android/c:g	()Lio/fabric/sdk/android/k;
    //   44: ldc 87
    //   46: ldc 89
    //   48: aload_3
    //   49: invokeinterface 95 4 0
    //   54: aload_1
    //   55: invokevirtual 66	io/fabric/sdk/android/services/b/w:b	()V
    //   58: goto -31 -> 27
    //   61: return
    //   62: astore_3
    //   63: aload_3
    //   64: athrow
    //   65: aload_1
    //   66: invokevirtual 66	io/fabric/sdk/android/services/b/w:b	()V
    //   69: aload_0
    //   70: iconst_1
    //   71: invokevirtual 79	io/fabric/sdk/android/g:a	(Z)Z
    //   74: pop
    //   75: aload_3
    //   76: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	77	0	this	g
    //   10	56	1	localw	w
    //   18	6	2	bool	boolean
    //   36	1	3	localObject	Object
    //   40	9	3	localException	Exception
    //   62	14	3	localUnmetDependencyException	io.fabric.sdk.android.services.concurrency.UnmetDependencyException
    // Exception table:
    //   from	to	target	type
    //   11	19	36	finally
    //   41	54	36	finally
    //   63	65	36	finally
    //   11	19	40	java/lang/Exception
    //   11	19	62	io/fabric/sdk/android/services/concurrency/UnmetDependencyException
  }
  
  protected void a(Result paramResult)
  {
    this.a.onPostExecute(paramResult);
    this.a.initializationCallback.a(paramResult);
  }
  
  protected void b(Result paramResult)
  {
    this.a.onCancelled(paramResult);
    paramResult = new StringBuilder();
    paramResult.append(this.a.getIdentifier());
    paramResult.append(" Initialization was cancelled");
    paramResult = new InitializationException(paramResult.toString());
    this.a.initializationCallback.a(paramResult);
  }
  
  public e getPriority()
  {
    return e.c;
  }
}


/* Location:              ~/io/fabric/sdk/android/g.class
 *
 * Reversed by:           J
 */