package android.support.v4.content.res;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.a.a.h;
import android.support.v4.provider.FontRequest;
import android.util.Base64;
import android.util.Xml;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class FontResourcesParserCompat
{
  private static final int DEFAULT_TIMEOUT_MILLIS = 500;
  public static final int FETCH_STRATEGY_ASYNC = 1;
  public static final int FETCH_STRATEGY_BLOCKING = 0;
  public static final int INFINITE_TIMEOUT_VALUE = -1;
  private static final int ITALIC = 1;
  private static final int NORMAL_WEIGHT = 400;
  
  public static FamilyResourceEntry parse(XmlPullParser paramXmlPullParser, Resources paramResources)
  {
    int i;
    do
    {
      i = paramXmlPullParser.next();
    } while ((i != 2) && (i != 1));
    if (i == 2) {
      return readFamilies(paramXmlPullParser, paramResources);
    }
    throw new XmlPullParserException("No start tag found");
  }
  
  public static List<List<byte[]>> readCerts(Resources paramResources, int paramInt)
  {
    Object localObject = null;
    ArrayList localArrayList = null;
    if (paramInt != 0)
    {
      TypedArray localTypedArray = paramResources.obtainTypedArray(paramInt);
      localObject = localArrayList;
      if (localTypedArray.length() > 0)
      {
        localArrayList = new ArrayList();
        int i;
        if (localTypedArray.getResourceId(0, 0) != 0) {
          i = 1;
        } else {
          i = 0;
        }
        if (i != 0) {
          for (paramInt = 0;; paramInt++)
          {
            localObject = localArrayList;
            if (paramInt >= localTypedArray.length()) {
              break;
            }
            localArrayList.add(toByteArrayList(paramResources.getStringArray(localTypedArray.getResourceId(paramInt, 0))));
          }
        }
        localArrayList.add(toByteArrayList(paramResources.getStringArray(paramInt)));
        localObject = localArrayList;
      }
      localTypedArray.recycle();
    }
    if (localObject == null) {
      localObject = Collections.emptyList();
    }
    return (List<List<byte[]>>)localObject;
  }
  
  private static FamilyResourceEntry readFamilies(XmlPullParser paramXmlPullParser, Resources paramResources)
  {
    paramXmlPullParser.require(2, null, "font-family");
    if (paramXmlPullParser.getName().equals("font-family")) {
      return readFamily(paramXmlPullParser, paramResources);
    }
    skip(paramXmlPullParser);
    return null;
  }
  
  private static FamilyResourceEntry readFamily(XmlPullParser paramXmlPullParser, Resources paramResources)
  {
    TypedArray localTypedArray = paramResources.obtainAttributes(Xml.asAttributeSet(paramXmlPullParser), a.h.FontFamily);
    String str1 = localTypedArray.getString(a.h.FontFamily_fontProviderAuthority);
    Object localObject = localTypedArray.getString(a.h.FontFamily_fontProviderPackage);
    String str2 = localTypedArray.getString(a.h.FontFamily_fontProviderQuery);
    int i = localTypedArray.getResourceId(a.h.FontFamily_fontProviderCerts, 0);
    int j = localTypedArray.getInteger(a.h.FontFamily_fontProviderFetchStrategy, 1);
    int k = localTypedArray.getInteger(a.h.FontFamily_fontProviderFetchTimeout, 500);
    localTypedArray.recycle();
    if ((str1 != null) && (localObject != null) && (str2 != null))
    {
      while (paramXmlPullParser.next() != 3) {
        skip(paramXmlPullParser);
      }
      return new ProviderResourceEntry(new FontRequest(str1, (String)localObject, str2, readCerts(paramResources, i)), j, k);
    }
    localObject = new ArrayList();
    while (paramXmlPullParser.next() != 3) {
      if (paramXmlPullParser.getEventType() == 2) {
        if (paramXmlPullParser.getName().equals("font")) {
          ((List)localObject).add(readFont(paramXmlPullParser, paramResources));
        } else {
          skip(paramXmlPullParser);
        }
      }
    }
    if (((List)localObject).isEmpty()) {
      return null;
    }
    return new FontFamilyFilesResourceEntry((FontFileResourceEntry[])((List)localObject).toArray(new FontFileResourceEntry[((List)localObject).size()]));
  }
  
  private static FontFileResourceEntry readFont(XmlPullParser paramXmlPullParser, Resources paramResources)
  {
    TypedArray localTypedArray = paramResources.obtainAttributes(Xml.asAttributeSet(paramXmlPullParser), a.h.FontFamilyFont);
    int i = localTypedArray.getInt(a.h.FontFamilyFont_fontWeight, 400);
    int j = localTypedArray.getInt(a.h.FontFamilyFont_fontStyle, 0);
    boolean bool = true;
    if (1 != j) {
      bool = false;
    }
    j = localTypedArray.getResourceId(a.h.FontFamilyFont_font, 0);
    paramResources = localTypedArray.getString(a.h.FontFamilyFont_font);
    localTypedArray.recycle();
    while (paramXmlPullParser.next() != 3) {
      skip(paramXmlPullParser);
    }
    return new FontFileResourceEntry(paramResources, i, bool, j);
  }
  
  private static void skip(XmlPullParser paramXmlPullParser)
  {
    int i = 1;
    while (i > 0) {
      switch (paramXmlPullParser.next())
      {
      default: 
        break;
      case 3: 
        i--;
        break;
      case 2: 
        i++;
      }
    }
  }
  
  private static List<byte[]> toByteArrayList(String[] paramArrayOfString)
  {
    ArrayList localArrayList = new ArrayList();
    int i = paramArrayOfString.length;
    for (int j = 0; j < i; j++) {
      localArrayList.add(Base64.decode(paramArrayOfString[j], 0));
    }
    return localArrayList;
  }
  
  public static abstract interface FamilyResourceEntry {}
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface FetchStrategy {}
  
  public static final class FontFamilyFilesResourceEntry
    implements FontResourcesParserCompat.FamilyResourceEntry
  {
    private final FontResourcesParserCompat.FontFileResourceEntry[] mEntries;
    
    public FontFamilyFilesResourceEntry(FontResourcesParserCompat.FontFileResourceEntry[] paramArrayOfFontFileResourceEntry)
    {
      this.mEntries = paramArrayOfFontFileResourceEntry;
    }
    
    public FontResourcesParserCompat.FontFileResourceEntry[] getEntries()
    {
      return this.mEntries;
    }
  }
  
  public static final class FontFileResourceEntry
  {
    private final String mFileName;
    private boolean mItalic;
    private int mResourceId;
    private int mWeight;
    
    public FontFileResourceEntry(String paramString, int paramInt1, boolean paramBoolean, int paramInt2)
    {
      this.mFileName = paramString;
      this.mWeight = paramInt1;
      this.mItalic = paramBoolean;
      this.mResourceId = paramInt2;
    }
    
    public String getFileName()
    {
      return this.mFileName;
    }
    
    public int getResourceId()
    {
      return this.mResourceId;
    }
    
    public int getWeight()
    {
      return this.mWeight;
    }
    
    public boolean isItalic()
    {
      return this.mItalic;
    }
  }
  
  public static final class ProviderResourceEntry
    implements FontResourcesParserCompat.FamilyResourceEntry
  {
    private final FontRequest mRequest;
    private final int mStrategy;
    private final int mTimeoutMs;
    
    public ProviderResourceEntry(FontRequest paramFontRequest, int paramInt1, int paramInt2)
    {
      this.mRequest = paramFontRequest;
      this.mStrategy = paramInt1;
      this.mTimeoutMs = paramInt2;
    }
    
    public int getFetchStrategy()
    {
      return this.mStrategy;
    }
    
    public FontRequest getRequest()
    {
      return this.mRequest;
    }
    
    public int getTimeout()
    {
      return this.mTimeoutMs;
    }
  }
}


/* Location:              ~/android/support/v4/content/res/FontResourcesParserCompat.class
 *
 * Reversed by:           J
 */