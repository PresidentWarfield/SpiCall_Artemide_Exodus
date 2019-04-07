package com.crashlytics.android.answers;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONObject;

class AnswersAttributes
{
  final Map<String, Object> attributes = new ConcurrentHashMap();
  final AnswersEventValidator validator;
  
  public AnswersAttributes(AnswersEventValidator paramAnswersEventValidator)
  {
    this.validator = paramAnswersEventValidator;
  }
  
  void put(String paramString, Number paramNumber)
  {
    if ((!this.validator.isNull(paramString, "key")) && (!this.validator.isNull(paramNumber, "value")))
    {
      putAttribute(this.validator.limitStringLength(paramString), paramNumber);
      return;
    }
  }
  
  void put(String paramString1, String paramString2)
  {
    if ((!this.validator.isNull(paramString1, "key")) && (!this.validator.isNull(paramString2, "value")))
    {
      putAttribute(this.validator.limitStringLength(paramString1), this.validator.limitStringLength(paramString2));
      return;
    }
  }
  
  void putAttribute(String paramString, Object paramObject)
  {
    if (!this.validator.isFullMap(this.attributes, paramString)) {
      this.attributes.put(paramString, paramObject);
    }
  }
  
  public String toString()
  {
    return new JSONObject(this.attributes).toString();
  }
}


/* Location:              ~/com/crashlytics/android/answers/AnswersAttributes.class
 *
 * Reversed by:           J
 */