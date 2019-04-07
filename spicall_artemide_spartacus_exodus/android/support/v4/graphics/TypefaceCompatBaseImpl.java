package android.support.v4.graphics;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.support.v4.content.res.FontResourcesParserCompat.FontFamilyFilesResourceEntry;
import android.support.v4.content.res.FontResourcesParserCompat.FontFileResourceEntry;
import android.support.v4.provider.FontsContractCompat.FontInfo;

class TypefaceCompatBaseImpl
  implements TypefaceCompat.TypefaceCompatImpl
{
  private static final String CACHE_FILE_PREFIX = "cached_font_";
  private static final String TAG = "TypefaceCompatBaseImpl";
  
  private FontResourcesParserCompat.FontFileResourceEntry findBestEntry(FontResourcesParserCompat.FontFamilyFilesResourceEntry paramFontFamilyFilesResourceEntry, int paramInt)
  {
    (FontResourcesParserCompat.FontFileResourceEntry)findBestFont(paramFontFamilyFilesResourceEntry.getEntries(), paramInt, new StyleExtractor()
    {
      public int getWeight(FontResourcesParserCompat.FontFileResourceEntry paramAnonymousFontFileResourceEntry)
      {
        return paramAnonymousFontFileResourceEntry.getWeight();
      }
      
      public boolean isItalic(FontResourcesParserCompat.FontFileResourceEntry paramAnonymousFontFileResourceEntry)
      {
        return paramAnonymousFontFileResourceEntry.isItalic();
      }
    });
  }
  
  private static <T> T findBestFont(T[] paramArrayOfT, int paramInt, StyleExtractor<T> paramStyleExtractor)
  {
    int i;
    if ((paramInt & 0x1) == 0) {
      i = 400;
    } else {
      i = 700;
    }
    int j;
    if ((paramInt & 0x2) != 0) {
      j = 1;
    } else {
      j = 0;
    }
    int k = paramArrayOfT.length;
    Object localObject = null;
    int m = 0;
    int i1;
    for (paramInt = Integer.MAX_VALUE; m < k; paramInt = i1)
    {
      T ? = paramArrayOfT[m];
      int n = Math.abs(paramStyleExtractor.getWeight(?) - i);
      if (paramStyleExtractor.isItalic(?) == j) {
        i1 = 0;
      } else {
        i1 = 1;
      }
      n = n * 2 + i1;
      if (localObject != null)
      {
        i1 = paramInt;
        if (paramInt <= n) {}
      }
      else
      {
        localObject = ?;
        i1 = n;
      }
      m++;
    }
    return (T)localObject;
  }
  
  public Typeface createFromFontFamilyFilesResourceEntry(Context paramContext, FontResourcesParserCompat.FontFamilyFilesResourceEntry paramFontFamilyFilesResourceEntry, Resources paramResources, int paramInt)
  {
    paramFontFamilyFilesResourceEntry = findBestEntry(paramFontFamilyFilesResourceEntry, paramInt);
    if (paramFontFamilyFilesResourceEntry == null) {
      return null;
    }
    return TypefaceCompat.createFromResourcesFontFile(paramContext, paramResources, paramFontFamilyFilesResourceEntry.getResourceId(), paramFontFamilyFilesResourceEntry.getFileName(), paramInt);
  }
  
  /* Error */
  public Typeface createFromFontInfo(Context paramContext, android.os.CancellationSignal paramCancellationSignal, FontsContractCompat.FontInfo[] paramArrayOfFontInfo, int paramInt)
  {
    // Byte code:
    //   0: aload_3
    //   1: arraylength
    //   2: istore 5
    //   4: aconst_null
    //   5: astore 6
    //   7: iload 5
    //   9: iconst_1
    //   10: if_icmpge +5 -> 15
    //   13: aconst_null
    //   14: areturn
    //   15: aload_0
    //   16: aload_3
    //   17: iload 4
    //   19: invokevirtual 85	android/support/v4/graphics/TypefaceCompatBaseImpl:findBestInfo	([Landroid/support/v4/provider/FontsContractCompat$FontInfo;I)Landroid/support/v4/provider/FontsContractCompat$FontInfo;
    //   22: astore_2
    //   23: aload_1
    //   24: invokevirtual 91	android/content/Context:getContentResolver	()Landroid/content/ContentResolver;
    //   27: aload_2
    //   28: invokevirtual 97	android/support/v4/provider/FontsContractCompat$FontInfo:getUri	()Landroid/net/Uri;
    //   31: invokevirtual 103	android/content/ContentResolver:openInputStream	(Landroid/net/Uri;)Ljava/io/InputStream;
    //   34: astore_2
    //   35: aload_0
    //   36: aload_1
    //   37: aload_2
    //   38: invokevirtual 107	android/support/v4/graphics/TypefaceCompatBaseImpl:createFromInputStream	(Landroid/content/Context;Ljava/io/InputStream;)Landroid/graphics/Typeface;
    //   41: astore_1
    //   42: aload_2
    //   43: invokestatic 113	android/support/v4/graphics/TypefaceCompatUtil:closeQuietly	(Ljava/io/Closeable;)V
    //   46: aload_1
    //   47: areturn
    //   48: astore_1
    //   49: goto +7 -> 56
    //   52: astore_1
    //   53: aload 6
    //   55: astore_2
    //   56: aload_2
    //   57: invokestatic 113	android/support/v4/graphics/TypefaceCompatUtil:closeQuietly	(Ljava/io/Closeable;)V
    //   60: aload_1
    //   61: athrow
    //   62: astore_1
    //   63: aconst_null
    //   64: astore_2
    //   65: aload_2
    //   66: invokestatic 113	android/support/v4/graphics/TypefaceCompatUtil:closeQuietly	(Ljava/io/Closeable;)V
    //   69: aconst_null
    //   70: areturn
    //   71: astore_1
    //   72: goto -7 -> 65
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	75	0	this	TypefaceCompatBaseImpl
    //   0	75	1	paramContext	Context
    //   0	75	2	paramCancellationSignal	android.os.CancellationSignal
    //   0	75	3	paramArrayOfFontInfo	FontsContractCompat.FontInfo[]
    //   0	75	4	paramInt	int
    //   2	9	5	i	int
    //   5	49	6	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   35	42	48	finally
    //   23	35	52	finally
    //   23	35	62	java/io/IOException
    //   35	42	71	java/io/IOException
  }
  
  /* Error */
  protected Typeface createFromInputStream(Context paramContext, java.io.InputStream paramInputStream)
  {
    // Byte code:
    //   0: aload_1
    //   1: invokestatic 119	android/support/v4/graphics/TypefaceCompatUtil:getTempFile	(Landroid/content/Context;)Ljava/io/File;
    //   4: astore_1
    //   5: aload_1
    //   6: ifnonnull +5 -> 11
    //   9: aconst_null
    //   10: areturn
    //   11: aload_1
    //   12: aload_2
    //   13: invokestatic 123	android/support/v4/graphics/TypefaceCompatUtil:copyToFile	(Ljava/io/File;Ljava/io/InputStream;)Z
    //   16: istore_3
    //   17: iload_3
    //   18: ifne +10 -> 28
    //   21: aload_1
    //   22: invokevirtual 129	java/io/File:delete	()Z
    //   25: pop
    //   26: aconst_null
    //   27: areturn
    //   28: aload_1
    //   29: invokevirtual 132	java/io/File:getPath	()Ljava/lang/String;
    //   32: invokestatic 138	android/graphics/Typeface:createFromFile	(Ljava/lang/String;)Landroid/graphics/Typeface;
    //   35: astore_2
    //   36: aload_1
    //   37: invokevirtual 129	java/io/File:delete	()Z
    //   40: pop
    //   41: aload_2
    //   42: areturn
    //   43: astore_2
    //   44: aload_1
    //   45: invokevirtual 129	java/io/File:delete	()Z
    //   48: pop
    //   49: aload_2
    //   50: athrow
    //   51: astore_2
    //   52: aload_1
    //   53: invokevirtual 129	java/io/File:delete	()Z
    //   56: pop
    //   57: aconst_null
    //   58: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	59	0	this	TypefaceCompatBaseImpl
    //   0	59	1	paramContext	Context
    //   0	59	2	paramInputStream	java.io.InputStream
    //   16	2	3	bool	boolean
    // Exception table:
    //   from	to	target	type
    //   11	17	43	finally
    //   28	36	43	finally
    //   11	17	51	java/lang/RuntimeException
    //   28	36	51	java/lang/RuntimeException
  }
  
  /* Error */
  public Typeface createFromResourcesFontFile(Context paramContext, Resources paramResources, int paramInt1, String paramString, int paramInt2)
  {
    // Byte code:
    //   0: aload_1
    //   1: invokestatic 119	android/support/v4/graphics/TypefaceCompatUtil:getTempFile	(Landroid/content/Context;)Ljava/io/File;
    //   4: astore_1
    //   5: aload_1
    //   6: ifnonnull +5 -> 11
    //   9: aconst_null
    //   10: areturn
    //   11: aload_1
    //   12: aload_2
    //   13: iload_3
    //   14: invokestatic 141	android/support/v4/graphics/TypefaceCompatUtil:copyToFile	(Ljava/io/File;Landroid/content/res/Resources;I)Z
    //   17: istore 6
    //   19: iload 6
    //   21: ifne +10 -> 31
    //   24: aload_1
    //   25: invokevirtual 129	java/io/File:delete	()Z
    //   28: pop
    //   29: aconst_null
    //   30: areturn
    //   31: aload_1
    //   32: invokevirtual 132	java/io/File:getPath	()Ljava/lang/String;
    //   35: invokestatic 138	android/graphics/Typeface:createFromFile	(Ljava/lang/String;)Landroid/graphics/Typeface;
    //   38: astore_2
    //   39: aload_1
    //   40: invokevirtual 129	java/io/File:delete	()Z
    //   43: pop
    //   44: aload_2
    //   45: areturn
    //   46: astore_2
    //   47: aload_1
    //   48: invokevirtual 129	java/io/File:delete	()Z
    //   51: pop
    //   52: aload_2
    //   53: athrow
    //   54: astore_2
    //   55: aload_1
    //   56: invokevirtual 129	java/io/File:delete	()Z
    //   59: pop
    //   60: aconst_null
    //   61: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	62	0	this	TypefaceCompatBaseImpl
    //   0	62	1	paramContext	Context
    //   0	62	2	paramResources	Resources
    //   0	62	3	paramInt1	int
    //   0	62	4	paramString	String
    //   0	62	5	paramInt2	int
    //   17	3	6	bool	boolean
    // Exception table:
    //   from	to	target	type
    //   11	19	46	finally
    //   31	39	46	finally
    //   11	19	54	java/lang/RuntimeException
    //   31	39	54	java/lang/RuntimeException
  }
  
  protected FontsContractCompat.FontInfo findBestInfo(FontsContractCompat.FontInfo[] paramArrayOfFontInfo, int paramInt)
  {
    (FontsContractCompat.FontInfo)findBestFont(paramArrayOfFontInfo, paramInt, new StyleExtractor()
    {
      public int getWeight(FontsContractCompat.FontInfo paramAnonymousFontInfo)
      {
        return paramAnonymousFontInfo.getWeight();
      }
      
      public boolean isItalic(FontsContractCompat.FontInfo paramAnonymousFontInfo)
      {
        return paramAnonymousFontInfo.isItalic();
      }
    });
  }
  
  private static abstract interface StyleExtractor<T>
  {
    public abstract int getWeight(T paramT);
    
    public abstract boolean isItalic(T paramT);
  }
}


/* Location:              ~/android/support/v4/graphics/TypefaceCompatBaseImpl.class
 *
 * Reversed by:           J
 */