package org.eclipse.paho.client.mqttv3.logging;

import java.util.logging.Formatter;

public class SimpleLogFormatter
  extends Formatter
{
  private static final String LS = System.getProperty("line.separator");
  
  public static String left(String paramString, int paramInt, char paramChar)
  {
    if (paramString.length() >= paramInt) {
      return paramString;
    }
    StringBuffer localStringBuffer = new StringBuffer(paramInt);
    localStringBuffer.append(paramString);
    paramInt -= paramString.length();
    for (;;)
    {
      paramInt--;
      if (paramInt < 0) {
        break;
      }
      localStringBuffer.append(paramChar);
    }
    return localStringBuffer.toString();
  }
  
  /* Error */
  public String format(java.util.logging.LogRecord paramLogRecord)
  {
    // Byte code:
    //   0: new 32	java/lang/StringBuffer
    //   3: dup
    //   4: invokespecial 51	java/lang/StringBuffer:<init>	()V
    //   7: astore_2
    //   8: aload_2
    //   9: aload_1
    //   10: invokevirtual 57	java/util/logging/LogRecord:getLevel	()Ljava/util/logging/Level;
    //   13: invokevirtual 62	java/util/logging/Level:getName	()Ljava/lang/String;
    //   16: invokevirtual 39	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   19: pop
    //   20: aload_2
    //   21: ldc 64
    //   23: invokevirtual 39	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   26: pop
    //   27: new 66	java/lang/StringBuilder
    //   30: dup
    //   31: invokespecial 67	java/lang/StringBuilder:<init>	()V
    //   34: astore_3
    //   35: aload_3
    //   36: ldc 69
    //   38: iconst_1
    //   39: anewarray 71	java/lang/Object
    //   42: dup
    //   43: iconst_0
    //   44: new 73	java/util/Date
    //   47: dup
    //   48: aload_1
    //   49: invokevirtual 77	java/util/logging/LogRecord:getMillis	()J
    //   52: invokespecial 80	java/util/Date:<init>	(J)V
    //   55: aastore
    //   56: invokestatic 85	java/text/MessageFormat:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   59: invokevirtual 88	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   62: pop
    //   63: aload_3
    //   64: ldc 64
    //   66: invokevirtual 88	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   69: pop
    //   70: aload_2
    //   71: aload_3
    //   72: invokevirtual 89	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   75: invokevirtual 39	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   78: pop
    //   79: aload_1
    //   80: invokevirtual 92	java/util/logging/LogRecord:getSourceClassName	()Ljava/lang/String;
    //   83: astore 4
    //   85: ldc 94
    //   87: astore_3
    //   88: aload 4
    //   90: ifnull +68 -> 158
    //   93: aload 4
    //   95: invokevirtual 30	java/lang/String:length	()I
    //   98: istore 5
    //   100: iload 5
    //   102: bipush 20
    //   104: if_icmple +19 -> 123
    //   107: aload_1
    //   108: invokevirtual 92	java/util/logging/LogRecord:getSourceClassName	()Ljava/lang/String;
    //   111: iload 5
    //   113: bipush 19
    //   115: isub
    //   116: invokevirtual 98	java/lang/String:substring	(I)Ljava/lang/String;
    //   119: astore_3
    //   120: goto +38 -> 158
    //   123: new 32	java/lang/StringBuffer
    //   126: dup
    //   127: invokespecial 51	java/lang/StringBuffer:<init>	()V
    //   130: astore_3
    //   131: aload_3
    //   132: aload 4
    //   134: invokevirtual 39	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   137: pop
    //   138: aload_3
    //   139: iconst_1
    //   140: newarray <illegal type>
    //   142: dup
    //   143: iconst_0
    //   144: bipush 32
    //   146: castore
    //   147: iconst_0
    //   148: iconst_1
    //   149: invokevirtual 101	java/lang/StringBuffer:append	([CII)Ljava/lang/StringBuffer;
    //   152: pop
    //   153: aload_3
    //   154: invokevirtual 46	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   157: astore_3
    //   158: aload_2
    //   159: aload_3
    //   160: invokevirtual 39	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   163: pop
    //   164: aload_2
    //   165: ldc 64
    //   167: invokevirtual 39	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   170: pop
    //   171: aload_2
    //   172: ldc 103
    //   174: invokevirtual 39	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   177: pop
    //   178: aload_2
    //   179: aload_1
    //   180: invokevirtual 106	java/util/logging/LogRecord:getSourceMethodName	()Ljava/lang/String;
    //   183: bipush 23
    //   185: bipush 32
    //   187: invokestatic 108	org/eclipse/paho/client/mqttv3/logging/SimpleLogFormatter:left	(Ljava/lang/String;IC)Ljava/lang/String;
    //   190: invokevirtual 39	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   193: pop
    //   194: aload_2
    //   195: ldc 64
    //   197: invokevirtual 39	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   200: pop
    //   201: aload_2
    //   202: aload_1
    //   203: invokevirtual 111	java/util/logging/LogRecord:getThreadID	()I
    //   206: invokevirtual 114	java/lang/StringBuffer:append	(I)Ljava/lang/StringBuffer;
    //   209: pop
    //   210: aload_2
    //   211: ldc 64
    //   213: invokevirtual 39	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   216: pop
    //   217: aload_2
    //   218: aload_0
    //   219: aload_1
    //   220: invokevirtual 117	org/eclipse/paho/client/mqttv3/logging/SimpleLogFormatter:formatMessage	(Ljava/util/logging/LogRecord;)Ljava/lang/String;
    //   223: invokevirtual 39	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   226: pop
    //   227: aload_2
    //   228: getstatic 18	org/eclipse/paho/client/mqttv3/logging/SimpleLogFormatter:LS	Ljava/lang/String;
    //   231: invokevirtual 39	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   234: pop
    //   235: aload_1
    //   236: invokevirtual 121	java/util/logging/LogRecord:getThrown	()Ljava/lang/Throwable;
    //   239: ifnull +79 -> 318
    //   242: aload_2
    //   243: ldc 123
    //   245: invokevirtual 39	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   248: pop
    //   249: aload_1
    //   250: invokevirtual 121	java/util/logging/LogRecord:getThrown	()Ljava/lang/Throwable;
    //   253: astore 6
    //   255: aconst_null
    //   256: astore_3
    //   257: new 125	java/io/StringWriter
    //   260: astore_1
    //   261: aload_1
    //   262: invokespecial 126	java/io/StringWriter:<init>	()V
    //   265: new 128	java/io/PrintWriter
    //   268: astore 4
    //   270: aload 4
    //   272: aload_1
    //   273: invokespecial 131	java/io/PrintWriter:<init>	(Ljava/io/Writer;)V
    //   276: aload 6
    //   278: aload 4
    //   280: invokevirtual 137	java/lang/Throwable:printStackTrace	(Ljava/io/PrintWriter;)V
    //   283: aload_2
    //   284: aload_1
    //   285: invokevirtual 138	java/io/StringWriter:toString	()Ljava/lang/String;
    //   288: invokevirtual 39	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   291: pop
    //   292: aload 4
    //   294: invokevirtual 141	java/io/PrintWriter:close	()V
    //   297: goto +21 -> 318
    //   300: astore_1
    //   301: aload 4
    //   303: astore_3
    //   304: goto +4 -> 308
    //   307: astore_1
    //   308: aload_3
    //   309: ifnull +7 -> 316
    //   312: aload_3
    //   313: invokevirtual 141	java/io/PrintWriter:close	()V
    //   316: aload_1
    //   317: athrow
    //   318: aload_2
    //   319: invokevirtual 46	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   322: areturn
    //   323: astore_1
    //   324: goto -6 -> 318
    //   327: astore_3
    //   328: goto -12 -> 316
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	331	0	this	SimpleLogFormatter
    //   0	331	1	paramLogRecord	java.util.logging.LogRecord
    //   7	312	2	localStringBuffer	StringBuffer
    //   34	279	3	localObject1	Object
    //   327	1	3	localException	Exception
    //   83	219	4	localObject2	Object
    //   98	18	5	i	int
    //   253	24	6	localThrowable	Throwable
    // Exception table:
    //   from	to	target	type
    //   276	292	300	finally
    //   257	276	307	finally
    //   292	297	323	java/lang/Exception
    //   312	316	327	java/lang/Exception
  }
}


/* Location:              ~/org/eclipse/paho/client/mqttv3/logging/SimpleLogFormatter.class
 *
 * Reversed by:           J
 */