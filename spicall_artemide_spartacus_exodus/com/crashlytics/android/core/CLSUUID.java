package com.crashlytics.android.core;

import android.os.Process;
import io.fabric.sdk.android.services.b.i;
import io.fabric.sdk.android.services.b.r;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicLong;

class CLSUUID
{
  private static String _clsId;
  private static final AtomicLong _sequenceNumber = new AtomicLong(0L);
  
  public CLSUUID(r paramr)
  {
    Object localObject = new byte[10];
    populateTime((byte[])localObject);
    populateSequenceNumber((byte[])localObject);
    populatePID((byte[])localObject);
    paramr = i.a(paramr.b());
    localObject = i.a((byte[])localObject);
    _clsId = String.format(Locale.US, "%s-%s-%s-%s", new Object[] { ((String)localObject).substring(0, 12), ((String)localObject).substring(12, 16), ((String)localObject).subSequence(16, 20), paramr.substring(0, 12) }).toUpperCase(Locale.US);
  }
  
  private static byte[] convertLongToFourByteBuffer(long paramLong)
  {
    ByteBuffer localByteBuffer = ByteBuffer.allocate(4);
    localByteBuffer.putInt((int)paramLong);
    localByteBuffer.order(ByteOrder.BIG_ENDIAN);
    localByteBuffer.position(0);
    return localByteBuffer.array();
  }
  
  private static byte[] convertLongToTwoByteBuffer(long paramLong)
  {
    ByteBuffer localByteBuffer = ByteBuffer.allocate(2);
    localByteBuffer.putShort((short)(int)paramLong);
    localByteBuffer.order(ByteOrder.BIG_ENDIAN);
    localByteBuffer.position(0);
    return localByteBuffer.array();
  }
  
  private void populatePID(byte[] paramArrayOfByte)
  {
    byte[] arrayOfByte = convertLongToTwoByteBuffer(Integer.valueOf(Process.myPid()).shortValue());
    paramArrayOfByte[8] = ((byte)arrayOfByte[0]);
    paramArrayOfByte[9] = ((byte)arrayOfByte[1]);
  }
  
  private void populateSequenceNumber(byte[] paramArrayOfByte)
  {
    byte[] arrayOfByte = convertLongToTwoByteBuffer(_sequenceNumber.incrementAndGet());
    paramArrayOfByte[6] = ((byte)arrayOfByte[0]);
    paramArrayOfByte[7] = ((byte)arrayOfByte[1]);
  }
  
  private void populateTime(byte[] paramArrayOfByte)
  {
    long l = new Date().getTime();
    byte[] arrayOfByte = convertLongToFourByteBuffer(l / 1000L);
    paramArrayOfByte[0] = ((byte)arrayOfByte[0]);
    paramArrayOfByte[1] = ((byte)arrayOfByte[1]);
    paramArrayOfByte[2] = ((byte)arrayOfByte[2]);
    paramArrayOfByte[3] = ((byte)arrayOfByte[3]);
    arrayOfByte = convertLongToTwoByteBuffer(l % 1000L);
    paramArrayOfByte[4] = ((byte)arrayOfByte[0]);
    paramArrayOfByte[5] = ((byte)arrayOfByte[1]);
  }
  
  public String toString()
  {
    return _clsId;
  }
}


/* Location:              ~/com/crashlytics/android/core/CLSUUID.class
 *
 * Reversed by:           J
 */