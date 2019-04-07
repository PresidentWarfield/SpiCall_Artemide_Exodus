package com.crashlytics.android.answers;

import java.util.HashSet;
import java.util.Set;

class SamplingEventFilter
  implements EventFilter
{
  static final Set<SessionEvent.Type> EVENTS_TYPE_TO_SAMPLE = new HashSet() {};
  final int samplingRate;
  
  public SamplingEventFilter(int paramInt)
  {
    this.samplingRate = paramInt;
  }
  
  public boolean skipEvent(SessionEvent paramSessionEvent)
  {
    boolean bool1 = EVENTS_TYPE_TO_SAMPLE.contains(paramSessionEvent.type);
    boolean bool2 = true;
    int i;
    if ((bool1) && (paramSessionEvent.sessionEventMetadata.betaDeviceToken == null)) {
      i = 1;
    } else {
      i = 0;
    }
    int j;
    if (Math.abs(paramSessionEvent.sessionEventMetadata.installationId.hashCode() % this.samplingRate) != 0) {
      j = 1;
    } else {
      j = 0;
    }
    if ((i == 0) || (j == 0)) {
      bool2 = false;
    }
    return bool2;
  }
}


/* Location:              ~/com/crashlytics/android/answers/SamplingEventFilter.class
 *
 * Reversed by:           J
 */