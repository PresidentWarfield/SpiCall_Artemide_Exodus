package okhttp3.internal.http2;

import a.f;
import java.io.IOException;
import okhttp3.internal.Util;

public final class Http2
{
  static final String[] BINARY;
  static final f CONNECTION_PREFACE = f.a("PRI * HTTP/2.0\r\n\r\nSM\r\n\r\n");
  static final String[] FLAGS;
  static final byte FLAG_ACK = 1;
  static final byte FLAG_COMPRESSED = 32;
  static final byte FLAG_END_HEADERS = 4;
  static final byte FLAG_END_PUSH_PROMISE = 4;
  static final byte FLAG_END_STREAM = 1;
  static final byte FLAG_NONE = 0;
  static final byte FLAG_PADDED = 8;
  static final byte FLAG_PRIORITY = 32;
  private static final String[] FRAME_NAMES = { "DATA", "HEADERS", "PRIORITY", "RST_STREAM", "SETTINGS", "PUSH_PROMISE", "PING", "GOAWAY", "WINDOW_UPDATE", "CONTINUATION" };
  static final int INITIAL_MAX_FRAME_SIZE = 16384;
  static final byte TYPE_CONTINUATION = 9;
  static final byte TYPE_DATA = 0;
  static final byte TYPE_GOAWAY = 7;
  static final byte TYPE_HEADERS = 1;
  static final byte TYPE_PING = 6;
  static final byte TYPE_PRIORITY = 2;
  static final byte TYPE_PUSH_PROMISE = 5;
  static final byte TYPE_RST_STREAM = 3;
  static final byte TYPE_SETTINGS = 4;
  static final byte TYPE_WINDOW_UPDATE = 8;
  
  static
  {
    FLAGS = new String[64];
    BINARY = new String['Ā'];
    int i = 0;
    for (int j = 0;; j++)
    {
      localObject1 = BINARY;
      if (j >= localObject1.length) {
        break;
      }
      localObject1[j] = Util.format("%8s", new Object[] { Integer.toBinaryString(j) }).replace(' ', '0');
    }
    Object localObject2 = FLAGS;
    localObject2[0] = "";
    localObject2[1] = "END_STREAM";
    Object localObject1 = new int[1];
    localObject1[0] = 1;
    localObject2[8] = "PADDED";
    int k = localObject1.length;
    Object localObject3;
    for (j = 0; j < k; j++)
    {
      m = localObject1[j];
      localObject2 = FLAGS;
      localObject3 = new StringBuilder();
      ((StringBuilder)localObject3).append(FLAGS[m]);
      ((StringBuilder)localObject3).append("|PADDED");
      localObject2[(m | 0x8)] = ((StringBuilder)localObject3).toString();
    }
    localObject2 = FLAGS;
    localObject2[4] = "END_HEADERS";
    localObject2[32] = "PRIORITY";
    localObject2[36] = "END_HEADERS|PRIORITY";
    localObject2 = new int[3];
    Object tmp255_254 = localObject2;
    tmp255_254[0] = 4;
    Object tmp259_255 = tmp255_254;
    tmp259_255[1] = 32;
    Object tmp264_259 = tmp259_255;
    tmp264_259[2] = 36;
    tmp264_259;
    int m = localObject2.length;
    for (j = 0;; j++)
    {
      k = i;
      if (j >= m) {
        break;
      }
      int n = localObject2[j];
      int i1 = localObject1.length;
      for (k = 0; k < i1; k++)
      {
        int i2 = localObject1[k];
        localObject3 = FLAGS;
        int i3 = i2 | n;
        Object localObject4 = new StringBuilder();
        ((StringBuilder)localObject4).append(FLAGS[i2]);
        ((StringBuilder)localObject4).append('|');
        ((StringBuilder)localObject4).append(FLAGS[n]);
        localObject3[i3] = ((StringBuilder)localObject4).toString();
        localObject4 = FLAGS;
        localObject3 = new StringBuilder();
        ((StringBuilder)localObject3).append(FLAGS[i2]);
        ((StringBuilder)localObject3).append('|');
        ((StringBuilder)localObject3).append(FLAGS[n]);
        ((StringBuilder)localObject3).append("|PADDED");
        localObject4[(i3 | 0x8)] = ((StringBuilder)localObject3).toString();
      }
    }
    for (;;)
    {
      localObject1 = FLAGS;
      if (k >= localObject1.length) {
        break;
      }
      if (localObject1[k] == null) {
        localObject1[k] = BINARY[k];
      }
      k++;
    }
  }
  
  static String formatFlags(byte paramByte1, byte paramByte2)
  {
    if (paramByte2 == 0) {
      return "";
    }
    switch (paramByte1)
    {
    case 5: 
    default: 
      localObject = FLAGS;
      if (paramByte2 < localObject.length) {
        localObject = localObject[paramByte2];
      }
      break;
    case 4: 
    case 6: 
      if (paramByte2 == 1) {
        localObject = "ACK";
      } else {
        localObject = BINARY[paramByte2];
      }
      return (String)localObject;
    case 2: 
    case 3: 
    case 7: 
    case 8: 
      return BINARY[paramByte2];
    }
    Object localObject = BINARY[paramByte2];
    if ((paramByte1 == 5) && ((paramByte2 & 0x4) != 0)) {
      return ((String)localObject).replace("HEADERS", "PUSH_PROMISE");
    }
    if ((paramByte1 == 0) && ((paramByte2 & 0x20) != 0)) {
      return ((String)localObject).replace("PRIORITY", "COMPRESSED");
    }
    return (String)localObject;
  }
  
  static String frameLog(boolean paramBoolean, int paramInt1, int paramInt2, byte paramByte1, byte paramByte2)
  {
    Object localObject = FRAME_NAMES;
    if (paramByte1 < localObject.length) {
      localObject = localObject[paramByte1];
    } else {
      localObject = Util.format("0x%02x", new Object[] { Byte.valueOf(paramByte1) });
    }
    String str1 = formatFlags(paramByte1, paramByte2);
    String str2;
    if (paramBoolean) {
      str2 = "<<";
    } else {
      str2 = ">>";
    }
    return Util.format("%s 0x%08x %5d %-13s %s", new Object[] { str2, Integer.valueOf(paramInt1), Integer.valueOf(paramInt2), localObject, str1 });
  }
  
  static IllegalArgumentException illegalArgument(String paramString, Object... paramVarArgs)
  {
    throw new IllegalArgumentException(Util.format(paramString, paramVarArgs));
  }
  
  static IOException ioException(String paramString, Object... paramVarArgs)
  {
    throw new IOException(Util.format(paramString, paramVarArgs));
  }
}


/* Location:              ~/okhttp3/internal/http2/Http2.class
 *
 * Reversed by:           J
 */