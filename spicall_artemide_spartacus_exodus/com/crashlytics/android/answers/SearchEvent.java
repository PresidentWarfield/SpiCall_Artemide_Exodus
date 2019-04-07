package com.crashlytics.android.answers;

public class SearchEvent
  extends PredefinedEvent<SearchEvent>
{
  static final String QUERY_ATTRIBUTE = "query";
  static final String TYPE = "search";
  
  String getPredefinedType()
  {
    return "search";
  }
  
  public SearchEvent putQuery(String paramString)
  {
    this.predefinedAttributes.put("query", paramString);
    return this;
  }
}


/* Location:              ~/com/crashlytics/android/answers/SearchEvent.class
 *
 * Reversed by:           J
 */