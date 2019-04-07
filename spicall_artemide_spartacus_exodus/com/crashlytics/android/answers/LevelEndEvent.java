package com.crashlytics.android.answers;

public class LevelEndEvent
  extends PredefinedEvent<LevelEndEvent>
{
  static final String LEVEL_NAME_ATTRIBUTE = "levelName";
  static final String SCORE_ATTRIBUTE = "score";
  static final String SUCCESS_ATTRIBUTE = "success";
  static final String TYPE = "levelEnd";
  
  String getPredefinedType()
  {
    return "levelEnd";
  }
  
  public LevelEndEvent putLevelName(String paramString)
  {
    this.predefinedAttributes.put("levelName", paramString);
    return this;
  }
  
  public LevelEndEvent putScore(Number paramNumber)
  {
    this.predefinedAttributes.put("score", paramNumber);
    return this;
  }
  
  public LevelEndEvent putSuccess(boolean paramBoolean)
  {
    AnswersAttributes localAnswersAttributes = this.predefinedAttributes;
    String str;
    if (paramBoolean) {
      str = "true";
    } else {
      str = "false";
    }
    localAnswersAttributes.put("success", str);
    return this;
  }
}


/* Location:              ~/com/crashlytics/android/answers/LevelEndEvent.class
 *
 * Reversed by:           J
 */