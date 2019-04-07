package com.crashlytics.android.core;

import io.fabric.sdk.android.c;
import io.fabric.sdk.android.k;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class ProcMapEntryParser
{
  private static final Pattern MAP_REGEX = Pattern.compile("\\s*(\\p{XDigit}+)-\\s*(\\p{XDigit}+)\\s+(.{4})\\s+\\p{XDigit}+\\s+.+\\s+\\d+\\s+(.*)");
  
  public static ProcMapEntry parse(String paramString)
  {
    Object localObject = MAP_REGEX.matcher(paramString);
    if (!((Matcher)localObject).matches()) {
      return null;
    }
    try
    {
      long l = Long.valueOf(((Matcher)localObject).group(1), 16).longValue();
      localObject = new ProcMapEntry(l, Long.valueOf(((Matcher)localObject).group(2), 16).longValue() - l, ((Matcher)localObject).group(3), ((Matcher)localObject).group(4));
      return (ProcMapEntry)localObject;
    }
    catch (Exception localException)
    {
      k localk = c.g();
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Could not parse map entry: ");
      localStringBuilder.append(paramString);
      localk.a("CrashlyticsCore", localStringBuilder.toString());
    }
    return null;
  }
}


/* Location:              ~/com/crashlytics/android/core/ProcMapEntryParser.class
 *
 * Reversed by:           J
 */