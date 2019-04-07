package com.crashlytics.android.core;

import android.app.ActivityManager.RunningAppProcessInfo;
import io.fabric.sdk.android.c;
import io.fabric.sdk.android.k;
import io.fabric.sdk.android.services.b.r.a;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

class SessionProtobufHelper
{
  private static final String SIGNAL_DEFAULT = "0";
  private static final ByteString SIGNAL_DEFAULT_BYTE_STRING = ByteString.copyFromUtf8("0");
  private static final ByteString UNITY_PLATFORM_BYTE_STRING = ByteString.copyFromUtf8("Unity");
  
  private static int getBinaryImageSize(ByteString paramByteString1, ByteString paramByteString2)
  {
    int i = CodedOutputStream.computeUInt64Size(1, 0L) + 0 + CodedOutputStream.computeUInt64Size(2, 0L) + CodedOutputStream.computeBytesSize(3, paramByteString1);
    int j = i;
    if (paramByteString2 != null) {
      j = i + CodedOutputStream.computeBytesSize(4, paramByteString2);
    }
    return j;
  }
  
  private static int getDeviceIdentifierSize(r.a parama, String paramString)
  {
    return CodedOutputStream.computeEnumSize(1, parama.h) + CodedOutputStream.computeBytesSize(2, ByteString.copyFromUtf8(paramString));
  }
  
  private static int getEventAppCustomAttributeSize(String paramString1, String paramString2)
  {
    int i = CodedOutputStream.computeBytesSize(1, ByteString.copyFromUtf8(paramString1));
    paramString1 = paramString2;
    if (paramString2 == null) {
      paramString1 = "";
    }
    return i + CodedOutputStream.computeBytesSize(2, ByteString.copyFromUtf8(paramString1));
  }
  
  private static int getEventAppExecutionExceptionSize(TrimmedThrowableData paramTrimmedThrowableData, int paramInt1, int paramInt2)
  {
    int i = CodedOutputStream.computeBytesSize(1, ByteString.copyFromUtf8(paramTrimmedThrowableData.className));
    int j = 0;
    int k = i + 0;
    Object localObject = paramTrimmedThrowableData.localizedMessage;
    i = k;
    if (localObject != null) {
      i = k + CodedOutputStream.computeBytesSize(3, ByteString.copyFromUtf8((String)localObject));
    }
    localObject = paramTrimmedThrowableData.stacktrace;
    int m = localObject.length;
    for (k = 0; k < m; k++)
    {
      int n = getFrameSize(localObject[k], true);
      i += CodedOutputStream.computeTagSize(4) + CodedOutputStream.computeRawVarint32Size(n) + n;
    }
    localObject = paramTrimmedThrowableData.cause;
    k = i;
    if (localObject != null)
    {
      k = j;
      paramTrimmedThrowableData = (TrimmedThrowableData)localObject;
      if (paramInt1 < paramInt2)
      {
        paramInt1 = getEventAppExecutionExceptionSize((TrimmedThrowableData)localObject, paramInt1 + 1, paramInt2);
        k = i + (CodedOutputStream.computeTagSize(6) + CodedOutputStream.computeRawVarint32Size(paramInt1) + paramInt1);
      }
      else
      {
        while (paramTrimmedThrowableData != null)
        {
          paramTrimmedThrowableData = paramTrimmedThrowableData.cause;
          k++;
        }
        k = i + CodedOutputStream.computeUInt32Size(7, k);
      }
    }
    return k;
  }
  
  private static int getEventAppExecutionSignalSize()
  {
    return CodedOutputStream.computeBytesSize(1, SIGNAL_DEFAULT_BYTE_STRING) + 0 + CodedOutputStream.computeBytesSize(2, SIGNAL_DEFAULT_BYTE_STRING) + CodedOutputStream.computeUInt64Size(3, 0L);
  }
  
  private static int getEventAppExecutionSize(TrimmedThrowableData paramTrimmedThrowableData, Thread paramThread, StackTraceElement[] paramArrayOfStackTraceElement, Thread[] paramArrayOfThread, List<StackTraceElement[]> paramList, int paramInt, ByteString paramByteString1, ByteString paramByteString2)
  {
    int i = getThreadSize(paramThread, paramArrayOfStackTraceElement, 4, true);
    int j = CodedOutputStream.computeTagSize(1);
    int k = CodedOutputStream.computeRawVarint32Size(i);
    int m = paramArrayOfThread.length;
    j = j + k + i + 0;
    for (k = 0; k < m; k++)
    {
      i = getThreadSize(paramArrayOfThread[k], (StackTraceElement[])paramList.get(k), 0, false);
      j += CodedOutputStream.computeTagSize(1) + CodedOutputStream.computeRawVarint32Size(i) + i;
    }
    paramInt = getEventAppExecutionExceptionSize(paramTrimmedThrowableData, 1, paramInt);
    int n = CodedOutputStream.computeTagSize(2);
    int i1 = CodedOutputStream.computeRawVarint32Size(paramInt);
    i = getEventAppExecutionSignalSize();
    k = CodedOutputStream.computeTagSize(3);
    m = CodedOutputStream.computeRawVarint32Size(i);
    int i2 = getBinaryImageSize(paramByteString1, paramByteString2);
    return j + (n + i1 + paramInt) + (k + m + i) + (CodedOutputStream.computeTagSize(3) + CodedOutputStream.computeRawVarint32Size(i2) + i2);
  }
  
  private static int getEventAppSize(TrimmedThrowableData paramTrimmedThrowableData, Thread paramThread, StackTraceElement[] paramArrayOfStackTraceElement, Thread[] paramArrayOfThread, List<StackTraceElement[]> paramList, int paramInt1, ByteString paramByteString1, ByteString paramByteString2, Map<String, String> paramMap, ActivityManager.RunningAppProcessInfo paramRunningAppProcessInfo, int paramInt2)
  {
    paramInt1 = getEventAppExecutionSize(paramTrimmedThrowableData, paramThread, paramArrayOfStackTraceElement, paramArrayOfThread, paramList, paramInt1, paramByteString1, paramByteString2);
    int i = CodedOutputStream.computeTagSize(1);
    int j = CodedOutputStream.computeRawVarint32Size(paramInt1);
    boolean bool = false;
    i = i + j + paramInt1 + 0;
    paramInt1 = i;
    if (paramMap != null)
    {
      paramTrimmedThrowableData = paramMap.entrySet().iterator();
      for (;;)
      {
        paramInt1 = i;
        if (!paramTrimmedThrowableData.hasNext()) {
          break;
        }
        paramThread = (Map.Entry)paramTrimmedThrowableData.next();
        paramInt1 = getEventAppCustomAttributeSize((String)paramThread.getKey(), (String)paramThread.getValue());
        i += CodedOutputStream.computeTagSize(2) + CodedOutputStream.computeRawVarint32Size(paramInt1) + paramInt1;
      }
    }
    i = paramInt1;
    if (paramRunningAppProcessInfo != null)
    {
      if (paramRunningAppProcessInfo.importance != 100) {
        bool = true;
      }
      i = paramInt1 + CodedOutputStream.computeBoolSize(3, bool);
    }
    return i + CodedOutputStream.computeUInt32Size(4, paramInt2);
  }
  
  private static int getEventDeviceSize(Float paramFloat, int paramInt1, boolean paramBoolean, int paramInt2, long paramLong1, long paramLong2)
  {
    int i = 0;
    if (paramFloat != null) {
      i = 0 + CodedOutputStream.computeFloatSize(1, paramFloat.floatValue());
    }
    return i + CodedOutputStream.computeSInt32Size(2, paramInt1) + CodedOutputStream.computeBoolSize(3, paramBoolean) + CodedOutputStream.computeUInt32Size(4, paramInt2) + CodedOutputStream.computeUInt64Size(5, paramLong1) + CodedOutputStream.computeUInt64Size(6, paramLong2);
  }
  
  private static int getEventLogSize(ByteString paramByteString)
  {
    return CodedOutputStream.computeBytesSize(1, paramByteString);
  }
  
  private static int getFrameSize(StackTraceElement paramStackTraceElement, boolean paramBoolean)
  {
    boolean bool = paramStackTraceElement.isNativeMethod();
    int i = 0;
    if (bool) {
      j = CodedOutputStream.computeUInt64Size(1, Math.max(paramStackTraceElement.getLineNumber(), 0)) + 0;
    } else {
      j = CodedOutputStream.computeUInt64Size(1, 0L) + 0;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramStackTraceElement.getClassName());
    localStringBuilder.append(".");
    localStringBuilder.append(paramStackTraceElement.getMethodName());
    int k = j + CodedOutputStream.computeBytesSize(2, ByteString.copyFromUtf8(localStringBuilder.toString()));
    int j = k;
    if (paramStackTraceElement.getFileName() != null) {
      j = k + CodedOutputStream.computeBytesSize(3, ByteString.copyFromUtf8(paramStackTraceElement.getFileName()));
    }
    k = j;
    if (!paramStackTraceElement.isNativeMethod())
    {
      k = j;
      if (paramStackTraceElement.getLineNumber() > 0) {
        k = j + CodedOutputStream.computeUInt64Size(4, paramStackTraceElement.getLineNumber());
      }
    }
    j = i;
    if (paramBoolean) {
      j = 2;
    }
    return k + CodedOutputStream.computeUInt32Size(5, j);
  }
  
  private static int getSessionAppOrgSize(ByteString paramByteString)
  {
    return CodedOutputStream.computeBytesSize(1, paramByteString) + 0;
  }
  
  private static int getSessionAppSize(ByteString paramByteString1, ByteString paramByteString2, ByteString paramByteString3, ByteString paramByteString4, ByteString paramByteString5, int paramInt, ByteString paramByteString6)
  {
    int i = CodedOutputStream.computeBytesSize(1, paramByteString1);
    int j = CodedOutputStream.computeBytesSize(2, paramByteString3);
    int k = CodedOutputStream.computeBytesSize(3, paramByteString4);
    int m = getSessionAppOrgSize(paramByteString2);
    m = i + 0 + j + k + (CodedOutputStream.computeTagSize(5) + CodedOutputStream.computeRawVarint32Size(m) + m) + CodedOutputStream.computeBytesSize(6, paramByteString5);
    i = m;
    if (paramByteString6 != null) {
      i = m + CodedOutputStream.computeBytesSize(8, UNITY_PLATFORM_BYTE_STRING) + CodedOutputStream.computeBytesSize(9, paramByteString6);
    }
    return i + CodedOutputStream.computeEnumSize(10, paramInt);
  }
  
  private static int getSessionDeviceSize(int paramInt1, ByteString paramByteString1, int paramInt2, long paramLong1, long paramLong2, boolean paramBoolean, Map<r.a, String> paramMap, int paramInt3, ByteString paramByteString2, ByteString paramByteString3)
  {
    int i = CodedOutputStream.computeEnumSize(3, paramInt1);
    int j = 0;
    if (paramByteString1 == null) {
      paramInt1 = 0;
    } else {
      paramInt1 = CodedOutputStream.computeBytesSize(4, paramByteString1);
    }
    paramInt1 = i + 0 + paramInt1 + CodedOutputStream.computeUInt32Size(5, paramInt2) + CodedOutputStream.computeUInt64Size(6, paramLong1) + CodedOutputStream.computeUInt64Size(7, paramLong2) + CodedOutputStream.computeBoolSize(10, paramBoolean);
    paramInt2 = paramInt1;
    if (paramMap != null)
    {
      paramMap = paramMap.entrySet().iterator();
      for (;;)
      {
        paramInt2 = paramInt1;
        if (!paramMap.hasNext()) {
          break;
        }
        paramByteString1 = (Map.Entry)paramMap.next();
        paramInt2 = getDeviceIdentifierSize((r.a)paramByteString1.getKey(), (String)paramByteString1.getValue());
        paramInt1 += CodedOutputStream.computeTagSize(11) + CodedOutputStream.computeRawVarint32Size(paramInt2) + paramInt2;
      }
    }
    i = CodedOutputStream.computeUInt32Size(12, paramInt3);
    if (paramByteString2 == null) {
      paramInt1 = 0;
    } else {
      paramInt1 = CodedOutputStream.computeBytesSize(13, paramByteString2);
    }
    if (paramByteString3 == null) {
      paramInt3 = j;
    } else {
      paramInt3 = CodedOutputStream.computeBytesSize(14, paramByteString3);
    }
    return paramInt2 + i + paramInt1 + paramInt3;
  }
  
  private static int getSessionEventSize(long paramLong1, String paramString, TrimmedThrowableData paramTrimmedThrowableData, Thread paramThread, StackTraceElement[] paramArrayOfStackTraceElement, Thread[] paramArrayOfThread, List<StackTraceElement[]> paramList, int paramInt1, Map<String, String> paramMap, ActivityManager.RunningAppProcessInfo paramRunningAppProcessInfo, int paramInt2, ByteString paramByteString1, ByteString paramByteString2, Float paramFloat, int paramInt3, boolean paramBoolean, long paramLong2, long paramLong3, ByteString paramByteString3)
  {
    int i = CodedOutputStream.computeUInt64Size(1, paramLong1);
    int j = CodedOutputStream.computeBytesSize(2, ByteString.copyFromUtf8(paramString));
    int k = getEventAppSize(paramTrimmedThrowableData, paramThread, paramArrayOfStackTraceElement, paramArrayOfThread, paramList, paramInt1, paramByteString1, paramByteString2, paramMap, paramRunningAppProcessInfo, paramInt2);
    int m = CodedOutputStream.computeTagSize(3);
    paramInt1 = CodedOutputStream.computeRawVarint32Size(k);
    paramInt2 = getEventDeviceSize(paramFloat, paramInt3, paramBoolean, paramInt2, paramLong2, paramLong3);
    paramInt2 = i + 0 + j + (m + paramInt1 + k) + (CodedOutputStream.computeTagSize(5) + CodedOutputStream.computeRawVarint32Size(paramInt2) + paramInt2);
    paramInt1 = paramInt2;
    if (paramByteString3 != null)
    {
      paramInt1 = getEventLogSize(paramByteString3);
      paramInt1 = paramInt2 + (CodedOutputStream.computeTagSize(6) + CodedOutputStream.computeRawVarint32Size(paramInt1) + paramInt1);
    }
    return paramInt1;
  }
  
  private static int getSessionOSSize(ByteString paramByteString1, ByteString paramByteString2, boolean paramBoolean)
  {
    return CodedOutputStream.computeEnumSize(1, 3) + 0 + CodedOutputStream.computeBytesSize(2, paramByteString1) + CodedOutputStream.computeBytesSize(3, paramByteString2) + CodedOutputStream.computeBoolSize(4, paramBoolean);
  }
  
  private static int getThreadSize(Thread paramThread, StackTraceElement[] paramArrayOfStackTraceElement, int paramInt, boolean paramBoolean)
  {
    int i = CodedOutputStream.computeBytesSize(1, ByteString.copyFromUtf8(paramThread.getName())) + CodedOutputStream.computeUInt32Size(2, paramInt);
    int j = paramArrayOfStackTraceElement.length;
    for (paramInt = 0; paramInt < j; paramInt++)
    {
      int k = getFrameSize(paramArrayOfStackTraceElement[paramInt], paramBoolean);
      i += CodedOutputStream.computeTagSize(3) + CodedOutputStream.computeRawVarint32Size(k) + k;
    }
    return i;
  }
  
  private static ByteString stringToByteString(String paramString)
  {
    if (paramString == null) {
      paramString = null;
    } else {
      paramString = ByteString.copyFromUtf8(paramString);
    }
    return paramString;
  }
  
  public static void writeBeginSession(CodedOutputStream paramCodedOutputStream, String paramString1, String paramString2, long paramLong)
  {
    paramCodedOutputStream.writeBytes(1, ByteString.copyFromUtf8(paramString2));
    paramCodedOutputStream.writeBytes(2, ByteString.copyFromUtf8(paramString1));
    paramCodedOutputStream.writeUInt64(3, paramLong);
  }
  
  private static void writeFrame(CodedOutputStream paramCodedOutputStream, int paramInt, StackTraceElement paramStackTraceElement, boolean paramBoolean)
  {
    paramCodedOutputStream.writeTag(paramInt, 2);
    paramCodedOutputStream.writeRawVarint32(getFrameSize(paramStackTraceElement, paramBoolean));
    if (paramStackTraceElement.isNativeMethod()) {
      paramCodedOutputStream.writeUInt64(1, Math.max(paramStackTraceElement.getLineNumber(), 0));
    } else {
      paramCodedOutputStream.writeUInt64(1, 0L);
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramStackTraceElement.getClassName());
    localStringBuilder.append(".");
    localStringBuilder.append(paramStackTraceElement.getMethodName());
    paramCodedOutputStream.writeBytes(2, ByteString.copyFromUtf8(localStringBuilder.toString()));
    if (paramStackTraceElement.getFileName() != null) {
      paramCodedOutputStream.writeBytes(3, ByteString.copyFromUtf8(paramStackTraceElement.getFileName()));
    }
    boolean bool = paramStackTraceElement.isNativeMethod();
    paramInt = 4;
    if ((!bool) && (paramStackTraceElement.getLineNumber() > 0)) {
      paramCodedOutputStream.writeUInt64(4, paramStackTraceElement.getLineNumber());
    }
    if (!paramBoolean) {
      paramInt = 0;
    }
    paramCodedOutputStream.writeUInt32(5, paramInt);
  }
  
  public static void writeSessionApp(CodedOutputStream paramCodedOutputStream, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, int paramInt, String paramString6)
  {
    ByteString localByteString = ByteString.copyFromUtf8(paramString1);
    paramString2 = ByteString.copyFromUtf8(paramString2);
    paramString3 = ByteString.copyFromUtf8(paramString3);
    paramString4 = ByteString.copyFromUtf8(paramString4);
    paramString5 = ByteString.copyFromUtf8(paramString5);
    if (paramString6 != null) {
      paramString1 = ByteString.copyFromUtf8(paramString6);
    } else {
      paramString1 = null;
    }
    paramCodedOutputStream.writeTag(7, 2);
    paramCodedOutputStream.writeRawVarint32(getSessionAppSize(localByteString, paramString2, paramString3, paramString4, paramString5, paramInt, paramString1));
    paramCodedOutputStream.writeBytes(1, localByteString);
    paramCodedOutputStream.writeBytes(2, paramString3);
    paramCodedOutputStream.writeBytes(3, paramString4);
    paramCodedOutputStream.writeTag(5, 2);
    paramCodedOutputStream.writeRawVarint32(getSessionAppOrgSize(paramString2));
    paramCodedOutputStream.writeBytes(1, paramString2);
    paramCodedOutputStream.writeBytes(6, paramString5);
    if (paramString1 != null)
    {
      paramCodedOutputStream.writeBytes(8, UNITY_PLATFORM_BYTE_STRING);
      paramCodedOutputStream.writeBytes(9, paramString1);
    }
    paramCodedOutputStream.writeEnum(10, paramInt);
  }
  
  public static void writeSessionDevice(CodedOutputStream paramCodedOutputStream, int paramInt1, String paramString1, int paramInt2, long paramLong1, long paramLong2, boolean paramBoolean, Map<r.a, String> paramMap, int paramInt3, String paramString2, String paramString3)
  {
    ByteString localByteString = stringToByteString(paramString1);
    paramString1 = stringToByteString(paramString3);
    paramString2 = stringToByteString(paramString2);
    paramCodedOutputStream.writeTag(9, 2);
    paramCodedOutputStream.writeRawVarint32(getSessionDeviceSize(paramInt1, localByteString, paramInt2, paramLong1, paramLong2, paramBoolean, paramMap, paramInt3, paramString2, paramString1));
    paramCodedOutputStream.writeEnum(3, paramInt1);
    paramCodedOutputStream.writeBytes(4, localByteString);
    paramCodedOutputStream.writeUInt32(5, paramInt2);
    paramCodedOutputStream.writeUInt64(6, paramLong1);
    paramCodedOutputStream.writeUInt64(7, paramLong2);
    paramCodedOutputStream.writeBool(10, paramBoolean);
    paramMap = paramMap.entrySet().iterator();
    while (paramMap.hasNext())
    {
      paramString3 = (Map.Entry)paramMap.next();
      paramCodedOutputStream.writeTag(11, 2);
      paramCodedOutputStream.writeRawVarint32(getDeviceIdentifierSize((r.a)paramString3.getKey(), (String)paramString3.getValue()));
      paramCodedOutputStream.writeEnum(1, ((r.a)paramString3.getKey()).h);
      paramCodedOutputStream.writeBytes(2, ByteString.copyFromUtf8((String)paramString3.getValue()));
    }
    paramCodedOutputStream.writeUInt32(12, paramInt3);
    if (paramString2 != null) {
      paramCodedOutputStream.writeBytes(13, paramString2);
    }
    if (paramString1 != null) {
      paramCodedOutputStream.writeBytes(14, paramString1);
    }
  }
  
  public static void writeSessionEvent(CodedOutputStream paramCodedOutputStream, long paramLong1, String paramString1, TrimmedThrowableData paramTrimmedThrowableData, Thread paramThread, StackTraceElement[] paramArrayOfStackTraceElement, Thread[] paramArrayOfThread, List<StackTraceElement[]> paramList, Map<String, String> paramMap, LogFileManager paramLogFileManager, ActivityManager.RunningAppProcessInfo paramRunningAppProcessInfo, int paramInt1, String paramString2, String paramString3, Float paramFloat, int paramInt2, boolean paramBoolean, long paramLong2, long paramLong3)
  {
    ByteString localByteString = ByteString.copyFromUtf8(paramString2);
    if (paramString3 == null) {
      paramString2 = null;
    } else {
      paramString2 = ByteString.copyFromUtf8(paramString3.replace("-", ""));
    }
    paramString3 = paramLogFileManager.getByteStringForLog();
    if (paramString3 == null) {
      c.g().a("CrashlyticsCore", "No log data to include with this event.");
    }
    paramLogFileManager.clearLog();
    paramCodedOutputStream.writeTag(10, 2);
    paramCodedOutputStream.writeRawVarint32(getSessionEventSize(paramLong1, paramString1, paramTrimmedThrowableData, paramThread, paramArrayOfStackTraceElement, paramArrayOfThread, paramList, 8, paramMap, paramRunningAppProcessInfo, paramInt1, localByteString, paramString2, paramFloat, paramInt2, paramBoolean, paramLong2, paramLong3, paramString3));
    paramCodedOutputStream.writeUInt64(1, paramLong1);
    paramCodedOutputStream.writeBytes(2, ByteString.copyFromUtf8(paramString1));
    writeSessionEventApp(paramCodedOutputStream, paramTrimmedThrowableData, paramThread, paramArrayOfStackTraceElement, paramArrayOfThread, paramList, 8, localByteString, paramString2, paramMap, paramRunningAppProcessInfo, paramInt1);
    writeSessionEventDevice(paramCodedOutputStream, paramFloat, paramInt2, paramBoolean, paramInt1, paramLong2, paramLong3);
    writeSessionEventLog(paramCodedOutputStream, paramString3);
  }
  
  private static void writeSessionEventApp(CodedOutputStream paramCodedOutputStream, TrimmedThrowableData paramTrimmedThrowableData, Thread paramThread, StackTraceElement[] paramArrayOfStackTraceElement, Thread[] paramArrayOfThread, List<StackTraceElement[]> paramList, int paramInt1, ByteString paramByteString1, ByteString paramByteString2, Map<String, String> paramMap, ActivityManager.RunningAppProcessInfo paramRunningAppProcessInfo, int paramInt2)
  {
    paramCodedOutputStream.writeTag(3, 2);
    paramCodedOutputStream.writeRawVarint32(getEventAppSize(paramTrimmedThrowableData, paramThread, paramArrayOfStackTraceElement, paramArrayOfThread, paramList, paramInt1, paramByteString1, paramByteString2, paramMap, paramRunningAppProcessInfo, paramInt2));
    writeSessionEventAppExecution(paramCodedOutputStream, paramTrimmedThrowableData, paramThread, paramArrayOfStackTraceElement, paramArrayOfThread, paramList, paramInt1, paramByteString1, paramByteString2);
    if ((paramMap != null) && (!paramMap.isEmpty())) {
      writeSessionEventAppCustomAttributes(paramCodedOutputStream, paramMap);
    }
    if (paramRunningAppProcessInfo != null)
    {
      boolean bool;
      if (paramRunningAppProcessInfo.importance != 100) {
        bool = true;
      } else {
        bool = false;
      }
      paramCodedOutputStream.writeBool(3, bool);
    }
    paramCodedOutputStream.writeUInt32(4, paramInt2);
  }
  
  private static void writeSessionEventAppCustomAttributes(CodedOutputStream paramCodedOutputStream, Map<String, String> paramMap)
  {
    Iterator localIterator = paramMap.entrySet().iterator();
    while (localIterator.hasNext())
    {
      paramMap = (Map.Entry)localIterator.next();
      paramCodedOutputStream.writeTag(2, 2);
      paramCodedOutputStream.writeRawVarint32(getEventAppCustomAttributeSize((String)paramMap.getKey(), (String)paramMap.getValue()));
      paramCodedOutputStream.writeBytes(1, ByteString.copyFromUtf8((String)paramMap.getKey()));
      String str = (String)paramMap.getValue();
      paramMap = str;
      if (str == null) {
        paramMap = "";
      }
      paramCodedOutputStream.writeBytes(2, ByteString.copyFromUtf8(paramMap));
    }
  }
  
  private static void writeSessionEventAppExecution(CodedOutputStream paramCodedOutputStream, TrimmedThrowableData paramTrimmedThrowableData, Thread paramThread, StackTraceElement[] paramArrayOfStackTraceElement, Thread[] paramArrayOfThread, List<StackTraceElement[]> paramList, int paramInt, ByteString paramByteString1, ByteString paramByteString2)
  {
    paramCodedOutputStream.writeTag(1, 2);
    paramCodedOutputStream.writeRawVarint32(getEventAppExecutionSize(paramTrimmedThrowableData, paramThread, paramArrayOfStackTraceElement, paramArrayOfThread, paramList, paramInt, paramByteString1, paramByteString2));
    writeThread(paramCodedOutputStream, paramThread, paramArrayOfStackTraceElement, 4, true);
    int i = paramArrayOfThread.length;
    for (int j = 0; j < i; j++) {
      writeThread(paramCodedOutputStream, paramArrayOfThread[j], (StackTraceElement[])paramList.get(j), 0, false);
    }
    writeSessionEventAppExecutionException(paramCodedOutputStream, paramTrimmedThrowableData, 1, paramInt, 2);
    paramCodedOutputStream.writeTag(3, 2);
    paramCodedOutputStream.writeRawVarint32(getEventAppExecutionSignalSize());
    paramCodedOutputStream.writeBytes(1, SIGNAL_DEFAULT_BYTE_STRING);
    paramCodedOutputStream.writeBytes(2, SIGNAL_DEFAULT_BYTE_STRING);
    paramCodedOutputStream.writeUInt64(3, 0L);
    paramCodedOutputStream.writeTag(4, 2);
    paramCodedOutputStream.writeRawVarint32(getBinaryImageSize(paramByteString1, paramByteString2));
    paramCodedOutputStream.writeUInt64(1, 0L);
    paramCodedOutputStream.writeUInt64(2, 0L);
    paramCodedOutputStream.writeBytes(3, paramByteString1);
    if (paramByteString2 != null) {
      paramCodedOutputStream.writeBytes(4, paramByteString2);
    }
  }
  
  private static void writeSessionEventAppExecutionException(CodedOutputStream paramCodedOutputStream, TrimmedThrowableData paramTrimmedThrowableData, int paramInt1, int paramInt2, int paramInt3)
  {
    paramCodedOutputStream.writeTag(paramInt3, 2);
    paramCodedOutputStream.writeRawVarint32(getEventAppExecutionExceptionSize(paramTrimmedThrowableData, 1, paramInt2));
    paramCodedOutputStream.writeBytes(1, ByteString.copyFromUtf8(paramTrimmedThrowableData.className));
    Object localObject = paramTrimmedThrowableData.localizedMessage;
    if (localObject != null) {
      paramCodedOutputStream.writeBytes(3, ByteString.copyFromUtf8((String)localObject));
    }
    localObject = paramTrimmedThrowableData.stacktrace;
    int i = localObject.length;
    int j = 0;
    for (paramInt3 = 0; paramInt3 < i; paramInt3++) {
      writeFrame(paramCodedOutputStream, 4, localObject[paramInt3], true);
    }
    localObject = paramTrimmedThrowableData.cause;
    if (localObject != null)
    {
      paramInt3 = j;
      paramTrimmedThrowableData = (TrimmedThrowableData)localObject;
      if (paramInt1 < paramInt2)
      {
        writeSessionEventAppExecutionException(paramCodedOutputStream, (TrimmedThrowableData)localObject, paramInt1 + 1, paramInt2, 6);
      }
      else
      {
        while (paramTrimmedThrowableData != null)
        {
          paramTrimmedThrowableData = paramTrimmedThrowableData.cause;
          paramInt3++;
        }
        paramCodedOutputStream.writeUInt32(7, paramInt3);
      }
    }
  }
  
  private static void writeSessionEventDevice(CodedOutputStream paramCodedOutputStream, Float paramFloat, int paramInt1, boolean paramBoolean, int paramInt2, long paramLong1, long paramLong2)
  {
    paramCodedOutputStream.writeTag(5, 2);
    paramCodedOutputStream.writeRawVarint32(getEventDeviceSize(paramFloat, paramInt1, paramBoolean, paramInt2, paramLong1, paramLong2));
    if (paramFloat != null) {
      paramCodedOutputStream.writeFloat(1, paramFloat.floatValue());
    }
    paramCodedOutputStream.writeSInt32(2, paramInt1);
    paramCodedOutputStream.writeBool(3, paramBoolean);
    paramCodedOutputStream.writeUInt32(4, paramInt2);
    paramCodedOutputStream.writeUInt64(5, paramLong1);
    paramCodedOutputStream.writeUInt64(6, paramLong2);
  }
  
  private static void writeSessionEventLog(CodedOutputStream paramCodedOutputStream, ByteString paramByteString)
  {
    if (paramByteString != null)
    {
      paramCodedOutputStream.writeTag(6, 2);
      paramCodedOutputStream.writeRawVarint32(getEventLogSize(paramByteString));
      paramCodedOutputStream.writeBytes(1, paramByteString);
    }
  }
  
  public static void writeSessionOS(CodedOutputStream paramCodedOutputStream, String paramString1, String paramString2, boolean paramBoolean)
  {
    paramString1 = ByteString.copyFromUtf8(paramString1);
    paramString2 = ByteString.copyFromUtf8(paramString2);
    paramCodedOutputStream.writeTag(8, 2);
    paramCodedOutputStream.writeRawVarint32(getSessionOSSize(paramString1, paramString2, paramBoolean));
    paramCodedOutputStream.writeEnum(1, 3);
    paramCodedOutputStream.writeBytes(2, paramString1);
    paramCodedOutputStream.writeBytes(3, paramString2);
    paramCodedOutputStream.writeBool(4, paramBoolean);
  }
  
  public static void writeSessionUser(CodedOutputStream paramCodedOutputStream, String paramString1, String paramString2, String paramString3)
  {
    Object localObject = paramString1;
    if (paramString1 == null) {
      localObject = "";
    }
    localObject = ByteString.copyFromUtf8((String)localObject);
    ByteString localByteString = stringToByteString(paramString2);
    paramString1 = stringToByteString(paramString3);
    int i = CodedOutputStream.computeBytesSize(1, (ByteString)localObject) + 0;
    int j = i;
    if (paramString2 != null) {
      j = i + CodedOutputStream.computeBytesSize(2, localByteString);
    }
    i = j;
    if (paramString3 != null) {
      i = j + CodedOutputStream.computeBytesSize(3, paramString1);
    }
    paramCodedOutputStream.writeTag(6, 2);
    paramCodedOutputStream.writeRawVarint32(i);
    paramCodedOutputStream.writeBytes(1, (ByteString)localObject);
    if (paramString2 != null) {
      paramCodedOutputStream.writeBytes(2, localByteString);
    }
    if (paramString3 != null) {
      paramCodedOutputStream.writeBytes(3, paramString1);
    }
  }
  
  private static void writeThread(CodedOutputStream paramCodedOutputStream, Thread paramThread, StackTraceElement[] paramArrayOfStackTraceElement, int paramInt, boolean paramBoolean)
  {
    paramCodedOutputStream.writeTag(1, 2);
    paramCodedOutputStream.writeRawVarint32(getThreadSize(paramThread, paramArrayOfStackTraceElement, paramInt, paramBoolean));
    paramCodedOutputStream.writeBytes(1, ByteString.copyFromUtf8(paramThread.getName()));
    paramCodedOutputStream.writeUInt32(2, paramInt);
    int i = paramArrayOfStackTraceElement.length;
    for (paramInt = 0; paramInt < i; paramInt++) {
      writeFrame(paramCodedOutputStream, 3, paramArrayOfStackTraceElement[paramInt], paramBoolean);
    }
  }
}


/* Location:              ~/com/crashlytics/android/core/SessionProtobufHelper.class
 *
 * Reversed by:           J
 */