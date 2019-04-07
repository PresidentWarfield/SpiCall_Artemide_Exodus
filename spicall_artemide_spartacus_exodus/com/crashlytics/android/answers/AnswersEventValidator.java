package com.crashlytics.android.answers;

import io.fabric.sdk.android.c;
import io.fabric.sdk.android.k;
import java.util.Locale;
import java.util.Map;

class AnswersEventValidator
{
  boolean failFast;
  final int maxNumAttributes;
  final int maxStringLength;
  
  public AnswersEventValidator(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    this.maxNumAttributes = paramInt1;
    this.maxStringLength = paramInt2;
    this.failFast = paramBoolean;
  }
  
  private void logOrThrowException(RuntimeException paramRuntimeException)
  {
    if (!this.failFast)
    {
      c.g().e("Answers", "Invalid user input detected", paramRuntimeException);
      return;
    }
    throw paramRuntimeException;
  }
  
  public boolean isFullMap(Map<String, Object> paramMap, String paramString)
  {
    if ((paramMap.size() >= this.maxNumAttributes) && (!paramMap.containsKey(paramString)))
    {
      logOrThrowException(new IllegalArgumentException(String.format(Locale.US, "Limit of %d attributes reached, skipping attribute", new Object[] { Integer.valueOf(this.maxNumAttributes) })));
      return true;
    }
    return false;
  }
  
  public boolean isNull(Object paramObject, String paramString)
  {
    if (paramObject == null)
    {
      paramObject = new StringBuilder();
      ((StringBuilder)paramObject).append(paramString);
      ((StringBuilder)paramObject).append(" must not be null");
      logOrThrowException(new NullPointerException(((StringBuilder)paramObject).toString()));
      return true;
    }
    return false;
  }
  
  public String limitStringLength(String paramString)
  {
    if (paramString.length() > this.maxStringLength)
    {
      logOrThrowException(new IllegalArgumentException(String.format(Locale.US, "String is too long, truncating to %d characters", new Object[] { Integer.valueOf(this.maxStringLength) })));
      return paramString.substring(0, this.maxStringLength);
    }
    return paramString;
  }
}


/* Location:              ~/com/crashlytics/android/answers/AnswersEventValidator.class
 *
 * Reversed by:           J
 */