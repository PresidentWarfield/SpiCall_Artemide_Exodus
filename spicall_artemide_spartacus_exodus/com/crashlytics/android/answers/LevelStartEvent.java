package com.crashlytics.android.answers;

public class LevelStartEvent
  extends PredefinedEvent<LevelStartEvent>
{
  static final String LEVEL_NAME_ATTRIBUTE = "levelName";
  static final String TYPE = "levelStart";
  
  String getPredefinedType()
  {
    return "levelStart";
  }
  
  public LevelStartEvent putLevelName(String paramString)
  {
    this.predefinedAttributes.put("levelName", paramString);
    return this;
  }
}


/* Location:              ~/com/crashlytics/android/answers/LevelStartEvent.class
 *
 * Reversed by:           J
 */