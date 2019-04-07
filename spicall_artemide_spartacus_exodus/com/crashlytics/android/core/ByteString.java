package com.crashlytics.android.core;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FilterOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.List;

final class ByteString
{
  public static final ByteString EMPTY = new ByteString(new byte[0]);
  private final byte[] bytes;
  private volatile int hash = 0;
  
  private ByteString(byte[] paramArrayOfByte)
  {
    this.bytes = paramArrayOfByte;
  }
  
  public static ByteString copyFrom(String paramString1, String paramString2)
  {
    return new ByteString(paramString1.getBytes(paramString2));
  }
  
  public static ByteString copyFrom(ByteBuffer paramByteBuffer)
  {
    return copyFrom(paramByteBuffer, paramByteBuffer.remaining());
  }
  
  public static ByteString copyFrom(ByteBuffer paramByteBuffer, int paramInt)
  {
    byte[] arrayOfByte = new byte[paramInt];
    paramByteBuffer.get(arrayOfByte);
    return new ByteString(arrayOfByte);
  }
  
  public static ByteString copyFrom(List<ByteString> paramList)
  {
    if (paramList.size() == 0) {
      return EMPTY;
    }
    if (paramList.size() == 1) {
      return (ByteString)paramList.get(0);
    }
    Object localObject = paramList.iterator();
    int i = 0;
    while (((Iterator)localObject).hasNext()) {
      i += ((ByteString)((Iterator)localObject).next()).size();
    }
    localObject = new byte[i];
    Iterator localIterator = paramList.iterator();
    i = 0;
    while (localIterator.hasNext())
    {
      paramList = (ByteString)localIterator.next();
      System.arraycopy(paramList.bytes, 0, localObject, i, paramList.size());
      i += paramList.size();
    }
    return new ByteString((byte[])localObject);
  }
  
  public static ByteString copyFrom(byte[] paramArrayOfByte)
  {
    return copyFrom(paramArrayOfByte, 0, paramArrayOfByte.length);
  }
  
  public static ByteString copyFrom(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    byte[] arrayOfByte = new byte[paramInt2];
    System.arraycopy(paramArrayOfByte, paramInt1, arrayOfByte, 0, paramInt2);
    return new ByteString(arrayOfByte);
  }
  
  public static ByteString copyFromUtf8(String paramString)
  {
    try
    {
      paramString = new ByteString(paramString.getBytes("UTF-8"));
      return paramString;
    }
    catch (UnsupportedEncodingException paramString)
    {
      throw new RuntimeException("UTF-8 not supported.", paramString);
    }
  }
  
  static CodedBuilder newCodedBuilder(int paramInt)
  {
    return new CodedBuilder(paramInt, null);
  }
  
  public static Output newOutput()
  {
    return newOutput(32);
  }
  
  public static Output newOutput(int paramInt)
  {
    return new Output(new ByteArrayOutputStream(paramInt), null);
  }
  
  public ByteBuffer asReadOnlyByteBuffer()
  {
    return ByteBuffer.wrap(this.bytes).asReadOnlyBuffer();
  }
  
  public byte byteAt(int paramInt)
  {
    return this.bytes[paramInt];
  }
  
  public void copyTo(ByteBuffer paramByteBuffer)
  {
    byte[] arrayOfByte = this.bytes;
    paramByteBuffer.put(arrayOfByte, 0, arrayOfByte.length);
  }
  
  public void copyTo(byte[] paramArrayOfByte, int paramInt)
  {
    byte[] arrayOfByte = this.bytes;
    System.arraycopy(arrayOfByte, 0, paramArrayOfByte, paramInt, arrayOfByte.length);
  }
  
  public void copyTo(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3)
  {
    System.arraycopy(this.bytes, paramInt1, paramArrayOfByte, paramInt2, paramInt3);
  }
  
  public boolean equals(Object paramObject)
  {
    if (paramObject == this) {
      return true;
    }
    if (!(paramObject instanceof ByteString)) {
      return false;
    }
    Object localObject = (ByteString)paramObject;
    paramObject = this.bytes;
    int i = paramObject.length;
    localObject = ((ByteString)localObject).bytes;
    if (i != localObject.length) {
      return false;
    }
    for (int j = 0; j < i; j++) {
      if (paramObject[j] != localObject[j]) {
        return false;
      }
    }
    return true;
  }
  
  public int hashCode()
  {
    int i = this.hash;
    int j = i;
    if (i == 0)
    {
      byte[] arrayOfByte = this.bytes;
      int k = arrayOfByte.length;
      i = 0;
      j = k;
      while (i < k)
      {
        j = j * 31 + arrayOfByte[i];
        i++;
      }
      if (j == 0) {
        j = 1;
      }
      this.hash = j;
    }
    return j;
  }
  
  public boolean isEmpty()
  {
    boolean bool;
    if (this.bytes.length == 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public InputStream newInput()
  {
    return new ByteArrayInputStream(this.bytes);
  }
  
  public int size()
  {
    return this.bytes.length;
  }
  
  public byte[] toByteArray()
  {
    byte[] arrayOfByte1 = this.bytes;
    int i = arrayOfByte1.length;
    byte[] arrayOfByte2 = new byte[i];
    System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 0, i);
    return arrayOfByte2;
  }
  
  public String toString(String paramString)
  {
    return new String(this.bytes, paramString);
  }
  
  public String toStringUtf8()
  {
    try
    {
      String str = new String(this.bytes, "UTF-8");
      return str;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      throw new RuntimeException("UTF-8 not supported?", localUnsupportedEncodingException);
    }
  }
  
  static final class CodedBuilder
  {
    private final byte[] buffer;
    private final CodedOutputStream output;
    
    private CodedBuilder(int paramInt)
    {
      this.buffer = new byte[paramInt];
      this.output = CodedOutputStream.newInstance(this.buffer);
    }
    
    public ByteString build()
    {
      this.output.checkNoSpaceLeft();
      return new ByteString(this.buffer, null);
    }
    
    public CodedOutputStream getCodedOutput()
    {
      return this.output;
    }
  }
  
  static final class Output
    extends FilterOutputStream
  {
    private final ByteArrayOutputStream bout;
    
    private Output(ByteArrayOutputStream paramByteArrayOutputStream)
    {
      super();
      this.bout = paramByteArrayOutputStream;
    }
    
    public ByteString toByteString()
    {
      return new ByteString(this.bout.toByteArray(), null);
    }
  }
}


/* Location:              ~/com/crashlytics/android/core/ByteString.class
 *
 * Reversed by:           J
 */