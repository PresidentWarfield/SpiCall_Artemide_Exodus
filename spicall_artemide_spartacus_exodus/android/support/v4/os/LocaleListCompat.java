package android.support.v4.os;

import android.os.Build.VERSION;
import android.os.LocaleList;
import java.util.Locale;

public final class LocaleListCompat
{
  static final LocaleListInterface IMPL;
  private static final LocaleListCompat sEmptyLocaleList = new LocaleListCompat();
  
  static
  {
    if (Build.VERSION.SDK_INT >= 24) {
      IMPL = new LocaleListCompatApi24Impl();
    } else {
      IMPL = new LocaleListCompatBaseImpl();
    }
  }
  
  public static LocaleListCompat create(Locale... paramVarArgs)
  {
    LocaleListCompat localLocaleListCompat = new LocaleListCompat();
    localLocaleListCompat.setLocaleListArray(paramVarArgs);
    return localLocaleListCompat;
  }
  
  public static LocaleListCompat forLanguageTags(String paramString)
  {
    if ((paramString != null) && (!paramString.isEmpty()))
    {
      String[] arrayOfString = paramString.split(",");
      Locale[] arrayOfLocale = new Locale[arrayOfString.length];
      for (int i = 0; i < arrayOfLocale.length; i++)
      {
        if (Build.VERSION.SDK_INT >= 21) {
          paramString = Locale.forLanguageTag(arrayOfString[i]);
        } else {
          paramString = LocaleHelper.forLanguageTag(arrayOfString[i]);
        }
        arrayOfLocale[i] = paramString;
      }
      paramString = new LocaleListCompat();
      paramString.setLocaleListArray(arrayOfLocale);
      return paramString;
    }
    return getEmptyLocaleList();
  }
  
  public static LocaleListCompat getAdjustedDefault()
  {
    if (Build.VERSION.SDK_INT >= 24) {
      return wrap(LocaleList.getAdjustedDefault());
    }
    return create(new Locale[] { Locale.getDefault() });
  }
  
  public static LocaleListCompat getDefault()
  {
    if (Build.VERSION.SDK_INT >= 24) {
      return wrap(LocaleList.getDefault());
    }
    return create(new Locale[] { Locale.getDefault() });
  }
  
  public static LocaleListCompat getEmptyLocaleList()
  {
    return sEmptyLocaleList;
  }
  
  private void setLocaleList(LocaleList paramLocaleList)
  {
    int i = paramLocaleList.size();
    if (i > 0)
    {
      Locale[] arrayOfLocale = new Locale[i];
      for (int j = 0; j < i; j++) {
        arrayOfLocale[j] = paramLocaleList.get(j);
      }
      IMPL.setLocaleList(arrayOfLocale);
    }
  }
  
  private void setLocaleListArray(Locale... paramVarArgs)
  {
    IMPL.setLocaleList(paramVarArgs);
  }
  
  public static LocaleListCompat wrap(Object paramObject)
  {
    LocaleListCompat localLocaleListCompat = new LocaleListCompat();
    if ((paramObject instanceof LocaleList)) {
      localLocaleListCompat.setLocaleList((LocaleList)paramObject);
    }
    return localLocaleListCompat;
  }
  
  public boolean equals(Object paramObject)
  {
    return IMPL.equals(paramObject);
  }
  
  public Locale get(int paramInt)
  {
    return IMPL.get(paramInt);
  }
  
  public Locale getFirstMatch(String[] paramArrayOfString)
  {
    return IMPL.getFirstMatch(paramArrayOfString);
  }
  
  public int hashCode()
  {
    return IMPL.hashCode();
  }
  
  public int indexOf(Locale paramLocale)
  {
    return IMPL.indexOf(paramLocale);
  }
  
  public boolean isEmpty()
  {
    return IMPL.isEmpty();
  }
  
  public int size()
  {
    return IMPL.size();
  }
  
  public String toLanguageTags()
  {
    return IMPL.toLanguageTags();
  }
  
  public String toString()
  {
    return IMPL.toString();
  }
  
  public Object unwrap()
  {
    return IMPL.getLocaleList();
  }
  
  static class LocaleListCompatApi24Impl
    implements LocaleListInterface
  {
    private LocaleList mLocaleList = new LocaleList(new Locale[0]);
    
    public boolean equals(Object paramObject)
    {
      return this.mLocaleList.equals(((LocaleListCompat)paramObject).unwrap());
    }
    
    public Locale get(int paramInt)
    {
      return this.mLocaleList.get(paramInt);
    }
    
    public Locale getFirstMatch(String[] paramArrayOfString)
    {
      LocaleList localLocaleList = this.mLocaleList;
      if (localLocaleList != null) {
        return localLocaleList.getFirstMatch(paramArrayOfString);
      }
      return null;
    }
    
    public Object getLocaleList()
    {
      return this.mLocaleList;
    }
    
    public int hashCode()
    {
      return this.mLocaleList.hashCode();
    }
    
    public int indexOf(Locale paramLocale)
    {
      return this.mLocaleList.indexOf(paramLocale);
    }
    
    public boolean isEmpty()
    {
      return this.mLocaleList.isEmpty();
    }
    
    public void setLocaleList(Locale... paramVarArgs)
    {
      this.mLocaleList = new LocaleList(paramVarArgs);
    }
    
    public int size()
    {
      return this.mLocaleList.size();
    }
    
    public String toLanguageTags()
    {
      return this.mLocaleList.toLanguageTags();
    }
    
    public String toString()
    {
      return this.mLocaleList.toString();
    }
  }
  
  static class LocaleListCompatBaseImpl
    implements LocaleListInterface
  {
    private LocaleListHelper mLocaleList = new LocaleListHelper(new Locale[0]);
    
    public boolean equals(Object paramObject)
    {
      return this.mLocaleList.equals(((LocaleListCompat)paramObject).unwrap());
    }
    
    public Locale get(int paramInt)
    {
      return this.mLocaleList.get(paramInt);
    }
    
    public Locale getFirstMatch(String[] paramArrayOfString)
    {
      LocaleListHelper localLocaleListHelper = this.mLocaleList;
      if (localLocaleListHelper != null) {
        return localLocaleListHelper.getFirstMatch(paramArrayOfString);
      }
      return null;
    }
    
    public Object getLocaleList()
    {
      return this.mLocaleList;
    }
    
    public int hashCode()
    {
      return this.mLocaleList.hashCode();
    }
    
    public int indexOf(Locale paramLocale)
    {
      return this.mLocaleList.indexOf(paramLocale);
    }
    
    public boolean isEmpty()
    {
      return this.mLocaleList.isEmpty();
    }
    
    public void setLocaleList(Locale... paramVarArgs)
    {
      this.mLocaleList = new LocaleListHelper(paramVarArgs);
    }
    
    public int size()
    {
      return this.mLocaleList.size();
    }
    
    public String toLanguageTags()
    {
      return this.mLocaleList.toLanguageTags();
    }
    
    public String toString()
    {
      return this.mLocaleList.toString();
    }
  }
}


/* Location:              ~/android/support/v4/os/LocaleListCompat.class
 *
 * Reversed by:           J
 */