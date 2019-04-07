package android.support.v4.content;

import android.content.SharedPreferences.Editor;

public final class SharedPreferencesCompat
{
  public static final class EditorCompat
  {
    private static EditorCompat sInstance;
    private final Helper mHelper = new Helper();
    
    public static EditorCompat getInstance()
    {
      if (sInstance == null) {
        sInstance = new EditorCompat();
      }
      return sInstance;
    }
    
    public void apply(SharedPreferences.Editor paramEditor)
    {
      this.mHelper.apply(paramEditor);
    }
    
    private static class Helper
    {
      public void apply(SharedPreferences.Editor paramEditor)
      {
        try
        {
          paramEditor.apply();
        }
        catch (AbstractMethodError localAbstractMethodError)
        {
          paramEditor.commit();
        }
      }
    }
  }
}


/* Location:              ~/android/support/v4/content/SharedPreferencesCompat.class
 *
 * Reversed by:           J
 */