package android.support.v4.os;

import android.os.Build.VERSION;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;

final class LocaleListHelper
{
  private static final Locale EN_LATN;
  private static final Locale LOCALE_AR_XB;
  private static final Locale LOCALE_EN_XA;
  private static final int NUM_PSEUDO_LOCALES = 2;
  private static final String STRING_AR_XB = "ar-XB";
  private static final String STRING_EN_XA = "en-XA";
  private static LocaleListHelper sDefaultAdjustedLocaleList = null;
  private static LocaleListHelper sDefaultLocaleList;
  private static final Locale[] sEmptyList = new Locale[0];
  private static final LocaleListHelper sEmptyLocaleList = new LocaleListHelper(new Locale[0]);
  private static Locale sLastDefaultLocale = null;
  private static LocaleListHelper sLastExplicitlySetLocaleList;
  private static final Object sLock;
  private final Locale[] mList;
  private final String mStringRepresentation;
  
  static
  {
    LOCALE_EN_XA = new Locale("en", "XA");
    LOCALE_AR_XB = new Locale("ar", "XB");
    EN_LATN = LocaleHelper.forLanguageTag("en-Latn");
    sLock = new Object();
    sLastExplicitlySetLocaleList = null;
    sDefaultLocaleList = null;
  }
  
  LocaleListHelper(Locale paramLocale, LocaleListHelper paramLocaleListHelper)
  {
    if (paramLocale != null)
    {
      int i = 0;
      int j;
      if (paramLocaleListHelper == null) {
        j = 0;
      } else {
        j = paramLocaleListHelper.mList.length;
      }
      for (int k = 0; k < j; k++) {
        if (paramLocale.equals(paramLocaleListHelper.mList[k])) {
          break label63;
        }
      }
      k = -1;
      label63:
      if (k == -1) {
        m = 1;
      } else {
        m = 0;
      }
      int n = m + j;
      Locale[] arrayOfLocale = new Locale[n];
      arrayOfLocale[0] = ((Locale)paramLocale.clone());
      if (k == -1) {
        for (k = 0; k < j; k = m)
        {
          m = k + 1;
          arrayOfLocale[m] = ((Locale)paramLocaleListHelper.mList[k].clone());
        }
      }
      int i1;
      for (int m = 0; m < k; m = i1)
      {
        i1 = m + 1;
        arrayOfLocale[i1] = ((Locale)paramLocaleListHelper.mList[m].clone());
      }
      k++;
      while (k < j)
      {
        arrayOfLocale[k] = ((Locale)paramLocaleListHelper.mList[k].clone());
        k++;
      }
      paramLocale = new StringBuilder();
      for (k = i; k < n; k++)
      {
        paramLocale.append(LocaleHelper.toLanguageTag(arrayOfLocale[k]));
        if (k < n - 1) {
          paramLocale.append(',');
        }
      }
      this.mList = arrayOfLocale;
      this.mStringRepresentation = paramLocale.toString();
      return;
    }
    throw new NullPointerException("topLocale is null");
  }
  
  LocaleListHelper(Locale... paramVarArgs)
  {
    if (paramVarArgs.length == 0)
    {
      this.mList = sEmptyList;
      this.mStringRepresentation = "";
    }
    else
    {
      Locale[] arrayOfLocale = new Locale[paramVarArgs.length];
      HashSet localHashSet = new HashSet();
      StringBuilder localStringBuilder = new StringBuilder();
      int i = 0;
      while (i < paramVarArgs.length)
      {
        Locale localLocale = paramVarArgs[i];
        if (localLocale != null)
        {
          if (!localHashSet.contains(localLocale))
          {
            localLocale = (Locale)localLocale.clone();
            arrayOfLocale[i] = localLocale;
            localStringBuilder.append(LocaleHelper.toLanguageTag(localLocale));
            if (i < paramVarArgs.length - 1) {
              localStringBuilder.append(',');
            }
            localHashSet.add(localLocale);
            i++;
          }
          else
          {
            paramVarArgs = new StringBuilder();
            paramVarArgs.append("list[");
            paramVarArgs.append(i);
            paramVarArgs.append("] is a repetition");
            throw new IllegalArgumentException(paramVarArgs.toString());
          }
        }
        else
        {
          paramVarArgs = new StringBuilder();
          paramVarArgs.append("list[");
          paramVarArgs.append(i);
          paramVarArgs.append("] is null");
          throw new NullPointerException(paramVarArgs.toString());
        }
      }
      this.mList = arrayOfLocale;
      this.mStringRepresentation = localStringBuilder.toString();
    }
  }
  
  private Locale computeFirstMatch(Collection<String> paramCollection, boolean paramBoolean)
  {
    int i = computeFirstMatchIndex(paramCollection, paramBoolean);
    if (i == -1) {
      paramCollection = null;
    } else {
      paramCollection = this.mList[i];
    }
    return paramCollection;
  }
  
  private int computeFirstMatchIndex(Collection<String> paramCollection, boolean paramBoolean)
  {
    Locale[] arrayOfLocale = this.mList;
    if (arrayOfLocale.length == 1) {
      return 0;
    }
    if (arrayOfLocale.length == 0) {
      return -1;
    }
    int i;
    if (paramBoolean)
    {
      i = findFirstMatchIndex(EN_LATN);
      if (i == 0) {
        return 0;
      }
      if (i < Integer.MAX_VALUE) {}
    }
    else
    {
      i = Integer.MAX_VALUE;
    }
    paramCollection = paramCollection.iterator();
    while (paramCollection.hasNext())
    {
      int j = findFirstMatchIndex(LocaleHelper.forLanguageTag((String)paramCollection.next()));
      if (j == 0) {
        return 0;
      }
      if (j < i) {
        i = j;
      }
    }
    if (i == Integer.MAX_VALUE) {
      return 0;
    }
    return i;
  }
  
  private int findFirstMatchIndex(Locale paramLocale)
  {
    for (int i = 0;; i++)
    {
      Locale[] arrayOfLocale = this.mList;
      if (i >= arrayOfLocale.length) {
        break;
      }
      if (matchScore(paramLocale, arrayOfLocale[i]) > 0) {
        return i;
      }
    }
    return Integer.MAX_VALUE;
  }
  
  static LocaleListHelper forLanguageTags(String paramString)
  {
    if ((paramString != null) && (!paramString.isEmpty()))
    {
      paramString = paramString.split(",");
      Locale[] arrayOfLocale = new Locale[paramString.length];
      for (int i = 0; i < arrayOfLocale.length; i++) {
        arrayOfLocale[i] = LocaleHelper.forLanguageTag(paramString[i]);
      }
      return new LocaleListHelper(arrayOfLocale);
    }
    return getEmptyLocaleList();
  }
  
  static LocaleListHelper getAdjustedDefault()
  {
    getDefault();
    synchronized (sLock)
    {
      LocaleListHelper localLocaleListHelper = sDefaultAdjustedLocaleList;
      return localLocaleListHelper;
    }
  }
  
  static LocaleListHelper getDefault()
  {
    Object localObject1 = Locale.getDefault();
    synchronized (sLock)
    {
      if (!((Locale)localObject1).equals(sLastDefaultLocale))
      {
        sLastDefaultLocale = (Locale)localObject1;
        if ((sDefaultLocaleList != null) && (((Locale)localObject1).equals(sDefaultLocaleList.get(0))))
        {
          localObject1 = sDefaultLocaleList;
          return (LocaleListHelper)localObject1;
        }
        LocaleListHelper localLocaleListHelper = new android/support/v4/os/LocaleListHelper;
        localLocaleListHelper.<init>((Locale)localObject1, sLastExplicitlySetLocaleList);
        sDefaultLocaleList = localLocaleListHelper;
        sDefaultAdjustedLocaleList = sDefaultLocaleList;
      }
      localObject1 = sDefaultLocaleList;
      return (LocaleListHelper)localObject1;
    }
  }
  
  static LocaleListHelper getEmptyLocaleList()
  {
    return sEmptyLocaleList;
  }
  
  private static String getLikelyScript(Locale paramLocale)
  {
    if (Build.VERSION.SDK_INT >= 21)
    {
      paramLocale = paramLocale.getScript();
      if (!paramLocale.isEmpty()) {
        return paramLocale;
      }
      return "";
    }
    return "";
  }
  
  private static boolean isPseudoLocale(String paramString)
  {
    boolean bool;
    if ((!"en-XA".equals(paramString)) && (!"ar-XB".equals(paramString))) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  private static boolean isPseudoLocale(Locale paramLocale)
  {
    boolean bool;
    if ((!LOCALE_EN_XA.equals(paramLocale)) && (!LOCALE_AR_XB.equals(paramLocale))) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  static boolean isPseudoLocalesOnly(String[] paramArrayOfString)
  {
    if (paramArrayOfString == null) {
      return true;
    }
    if (paramArrayOfString.length > 3) {
      return false;
    }
    int i = paramArrayOfString.length;
    for (int j = 0; j < i; j++)
    {
      String str = paramArrayOfString[j];
      if ((!str.isEmpty()) && (!isPseudoLocale(str))) {
        return false;
      }
    }
    return true;
  }
  
  private static int matchScore(Locale paramLocale1, Locale paramLocale2)
  {
    boolean bool = paramLocale1.equals(paramLocale2);
    int i = 1;
    if (bool) {
      return 1;
    }
    if (!paramLocale1.getLanguage().equals(paramLocale2.getLanguage())) {
      return 0;
    }
    if ((!isPseudoLocale(paramLocale1)) && (!isPseudoLocale(paramLocale2)))
    {
      String str = getLikelyScript(paramLocale1);
      if (str.isEmpty())
      {
        paramLocale1 = paramLocale1.getCountry();
        int j = i;
        if (!paramLocale1.isEmpty()) {
          if (paramLocale1.equals(paramLocale2.getCountry())) {
            j = i;
          } else {
            j = 0;
          }
        }
        return j;
      }
      return str.equals(getLikelyScript(paramLocale2));
    }
    return 0;
  }
  
  static void setDefault(LocaleListHelper paramLocaleListHelper)
  {
    setDefault(paramLocaleListHelper, 0);
  }
  
  static void setDefault(LocaleListHelper paramLocaleListHelper, int paramInt)
  {
    if (paramLocaleListHelper != null)
    {
      if (!paramLocaleListHelper.isEmpty()) {
        synchronized (sLock)
        {
          sLastDefaultLocale = paramLocaleListHelper.get(paramInt);
          Locale.setDefault(sLastDefaultLocale);
          sLastExplicitlySetLocaleList = paramLocaleListHelper;
          sDefaultLocaleList = paramLocaleListHelper;
          if (paramInt == 0)
          {
            sDefaultAdjustedLocaleList = sDefaultLocaleList;
          }
          else
          {
            paramLocaleListHelper = new android/support/v4/os/LocaleListHelper;
            paramLocaleListHelper.<init>(sLastDefaultLocale, sDefaultLocaleList);
            sDefaultAdjustedLocaleList = paramLocaleListHelper;
          }
          return;
        }
      }
      throw new IllegalArgumentException("locales is empty");
    }
    throw new NullPointerException("locales is null");
  }
  
  public boolean equals(Object paramObject)
  {
    if (paramObject == this) {
      return true;
    }
    if (!(paramObject instanceof LocaleListHelper)) {
      return false;
    }
    Locale[] arrayOfLocale = ((LocaleListHelper)paramObject).mList;
    if (this.mList.length != arrayOfLocale.length) {
      return false;
    }
    for (int i = 0;; i++)
    {
      paramObject = this.mList;
      if (i >= paramObject.length) {
        break;
      }
      if (!paramObject[i].equals(arrayOfLocale[i])) {
        return false;
      }
    }
    return true;
  }
  
  Locale get(int paramInt)
  {
    if (paramInt >= 0)
    {
      localObject = this.mList;
      if (paramInt < localObject.length) {
        return localObject[paramInt];
      }
    }
    Object localObject = null;
    return (Locale)localObject;
  }
  
  Locale getFirstMatch(String[] paramArrayOfString)
  {
    return computeFirstMatch(Arrays.asList(paramArrayOfString), false);
  }
  
  int getFirstMatchIndex(String[] paramArrayOfString)
  {
    return computeFirstMatchIndex(Arrays.asList(paramArrayOfString), false);
  }
  
  int getFirstMatchIndexWithEnglishSupported(Collection<String> paramCollection)
  {
    return computeFirstMatchIndex(paramCollection, true);
  }
  
  int getFirstMatchIndexWithEnglishSupported(String[] paramArrayOfString)
  {
    return getFirstMatchIndexWithEnglishSupported(Arrays.asList(paramArrayOfString));
  }
  
  Locale getFirstMatchWithEnglishSupported(String[] paramArrayOfString)
  {
    return computeFirstMatch(Arrays.asList(paramArrayOfString), true);
  }
  
  public int hashCode()
  {
    int i = 1;
    for (int j = 0;; j++)
    {
      Locale[] arrayOfLocale = this.mList;
      if (j >= arrayOfLocale.length) {
        break;
      }
      i = i * 31 + arrayOfLocale[j].hashCode();
    }
    return i;
  }
  
  int indexOf(Locale paramLocale)
  {
    for (int i = 0;; i++)
    {
      Locale[] arrayOfLocale = this.mList;
      if (i >= arrayOfLocale.length) {
        break;
      }
      if (arrayOfLocale[i].equals(paramLocale)) {
        return i;
      }
    }
    return -1;
  }
  
  boolean isEmpty()
  {
    boolean bool;
    if (this.mList.length == 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  int size()
  {
    return this.mList.length;
  }
  
  String toLanguageTags()
  {
    return this.mStringRepresentation;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("[");
    for (int i = 0;; i++)
    {
      Locale[] arrayOfLocale = this.mList;
      if (i >= arrayOfLocale.length) {
        break;
      }
      localStringBuilder.append(arrayOfLocale[i]);
      if (i < this.mList.length - 1) {
        localStringBuilder.append(',');
      }
    }
    localStringBuilder.append("]");
    return localStringBuilder.toString();
  }
}


/* Location:              ~/android/support/v4/os/LocaleListHelper.class
 *
 * Reversed by:           J
 */