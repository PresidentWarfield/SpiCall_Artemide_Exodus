package com.crashlytics.android.core;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.Comparator;

final class Utils
{
  private static final FilenameFilter ALL_FILES_FILTER = new FilenameFilter()
  {
    public boolean accept(File paramAnonymousFile, String paramAnonymousString)
    {
      return true;
    }
  };
  
  static int capFileCount(File paramFile, int paramInt, Comparator<File> paramComparator)
  {
    return capFileCount(paramFile, ALL_FILES_FILTER, paramInt, paramComparator);
  }
  
  static int capFileCount(File paramFile, FilenameFilter paramFilenameFilter, int paramInt, Comparator<File> paramComparator)
  {
    paramFile = paramFile.listFiles(paramFilenameFilter);
    int i = 0;
    if (paramFile == null) {
      return 0;
    }
    int j = paramFile.length;
    Arrays.sort(paramFile, paramComparator);
    int k = paramFile.length;
    while (i < k)
    {
      paramFilenameFilter = paramFile[i];
      if (j <= paramInt) {
        return j;
      }
      paramFilenameFilter.delete();
      j--;
      i++;
    }
    return j;
  }
}


/* Location:              ~/com/crashlytics/android/core/Utils.class
 *
 * Reversed by:           J
 */