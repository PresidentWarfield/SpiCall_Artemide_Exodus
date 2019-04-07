package com.app.system.common.entity;

import android.database.Cursor;
import android.os.Build.VERSION;
import android.os.Environment;
import java.io.File;
import java.nio.file.Files;

public class FileEntry
{
  public static final int PRIORITY_HIGH = 5;
  public static final int PRIORITY_HIGHEST = 10;
  public static final int PRIORITY_LOW = -5;
  public static final int PRIORITY_LOWEST = -10;
  public static final int PRIORITY_STANDARD = 0;
  public String mFileName;
  public long mLastMod;
  public boolean mNotified;
  public int mPriority;
  public long mSize;
  public boolean mSynchronized;
  public long rowId;
  
  public FileEntry(Cursor paramCursor)
  {
    boolean bool1 = false;
    this.rowId = paramCursor.getLong(0);
    this.mFileName = paramCursor.getString(1);
    this.mSize = paramCursor.getLong(2);
    this.mLastMod = paramCursor.getLong(3);
    if (paramCursor.getInt(4) != 0) {
      bool2 = true;
    } else {
      bool2 = false;
    }
    this.mNotified = bool2;
    boolean bool2 = bool1;
    if (paramCursor.getInt(5) != 0) {
      bool2 = true;
    }
    this.mSynchronized = bool2;
    this.mPriority = paramCursor.getInt(6);
  }
  
  public FileEntry(File paramFile)
  {
    this.rowId = 0L;
    this.mFileName = paramFile.getAbsolutePath();
    this.mSize = paramFile.length();
    this.mLastMod = (paramFile.lastModified() / 1000L);
    this.mNotified = false;
    this.mSynchronized = false;
    this.mPriority = FilePriority.a(this.mFileName);
  }
  
  public static boolean a(File paramFile)
  {
    if (Build.VERSION.SDK_INT >= 26) {
      return Files.isSymbolicLink(paramFile.toPath());
    }
    if (paramFile.getParent() != null) {
      paramFile = new File(paramFile.getParentFile().getCanonicalFile(), paramFile.getName());
    }
    return paramFile.getCanonicalFile().equals(paramFile.getAbsoluteFile()) ^ true;
  }
  
  private static class FilePriority
  {
    private static final FilePriority[] FILE_PRIORITIES = { new FilePriority("WhatsApp", 10), new FilePriority("DCIM", 5), new FilePriority("Pictures", 5), new FilePriority("Movies", -5), new FilePriority("Music", -5), new FilePriority("Notifications", -5), new FilePriority("Ringtones", -5), new FilePriority("Android", -10) };
    public int priority;
    public String rootDir;
    
    private FilePriority(String paramString, int paramInt)
    {
      this.rootDir = new File(Environment.getExternalStorageDirectory(), paramString).getAbsolutePath().toLowerCase();
      this.priority = paramInt;
    }
    
    public static int a(String paramString)
    {
      String str = paramString.toLowerCase();
      for (paramString : FILE_PRIORITIES) {
        if (str.startsWith(paramString.rootDir)) {
          return paramString.priority;
        }
      }
      return 0;
    }
  }
}


/* Location:              ~/com/app/system/common/entity/FileEntry.class
 *
 * Reversed by:           J
 */