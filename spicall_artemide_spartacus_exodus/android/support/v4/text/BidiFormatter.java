package android.support.v4.text;

import android.text.SpannableStringBuilder;
import java.util.Locale;

public final class BidiFormatter
{
  private static final int DEFAULT_FLAGS = 2;
  private static final BidiFormatter DEFAULT_LTR_INSTANCE = new BidiFormatter(false, 2, DEFAULT_TEXT_DIRECTION_HEURISTIC);
  private static final BidiFormatter DEFAULT_RTL_INSTANCE = new BidiFormatter(true, 2, DEFAULT_TEXT_DIRECTION_HEURISTIC);
  private static TextDirectionHeuristicCompat DEFAULT_TEXT_DIRECTION_HEURISTIC = TextDirectionHeuristicsCompat.FIRSTSTRONG_LTR;
  private static final int DIR_LTR = -1;
  private static final int DIR_RTL = 1;
  private static final int DIR_UNKNOWN = 0;
  private static final String EMPTY_STRING = "";
  private static final int FLAG_STEREO_RESET = 2;
  private static final char LRE = '‪';
  private static final char LRM = '‎';
  private static final String LRM_STRING = Character.toString('‎');
  private static final char PDF = '‬';
  private static final char RLE = '‫';
  private static final char RLM = '‏';
  private static final String RLM_STRING = Character.toString('‏');
  private final TextDirectionHeuristicCompat mDefaultTextDirectionHeuristicCompat;
  private final int mFlags;
  private final boolean mIsRtlContext;
  
  private BidiFormatter(boolean paramBoolean, int paramInt, TextDirectionHeuristicCompat paramTextDirectionHeuristicCompat)
  {
    this.mIsRtlContext = paramBoolean;
    this.mFlags = paramInt;
    this.mDefaultTextDirectionHeuristicCompat = paramTextDirectionHeuristicCompat;
  }
  
  private static int getEntryDir(CharSequence paramCharSequence)
  {
    return new DirectionalityEstimator(paramCharSequence, false).getEntryDir();
  }
  
  private static int getExitDir(CharSequence paramCharSequence)
  {
    return new DirectionalityEstimator(paramCharSequence, false).getExitDir();
  }
  
  public static BidiFormatter getInstance()
  {
    return new Builder().build();
  }
  
  public static BidiFormatter getInstance(Locale paramLocale)
  {
    return new Builder(paramLocale).build();
  }
  
  public static BidiFormatter getInstance(boolean paramBoolean)
  {
    return new Builder(paramBoolean).build();
  }
  
  private static boolean isRtlLocale(Locale paramLocale)
  {
    int i = TextUtilsCompat.getLayoutDirectionFromLocale(paramLocale);
    boolean bool = true;
    if (i != 1) {
      bool = false;
    }
    return bool;
  }
  
  private String markAfter(CharSequence paramCharSequence, TextDirectionHeuristicCompat paramTextDirectionHeuristicCompat)
  {
    boolean bool = paramTextDirectionHeuristicCompat.isRtl(paramCharSequence, 0, paramCharSequence.length());
    if ((!this.mIsRtlContext) && ((bool) || (getExitDir(paramCharSequence) == 1))) {
      return LRM_STRING;
    }
    if ((this.mIsRtlContext) && ((!bool) || (getExitDir(paramCharSequence) == -1))) {
      return RLM_STRING;
    }
    return "";
  }
  
  private String markBefore(CharSequence paramCharSequence, TextDirectionHeuristicCompat paramTextDirectionHeuristicCompat)
  {
    boolean bool = paramTextDirectionHeuristicCompat.isRtl(paramCharSequence, 0, paramCharSequence.length());
    if ((!this.mIsRtlContext) && ((bool) || (getEntryDir(paramCharSequence) == 1))) {
      return LRM_STRING;
    }
    if ((this.mIsRtlContext) && ((!bool) || (getEntryDir(paramCharSequence) == -1))) {
      return RLM_STRING;
    }
    return "";
  }
  
  public boolean getStereoReset()
  {
    boolean bool;
    if ((this.mFlags & 0x2) != 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean isRtl(CharSequence paramCharSequence)
  {
    return this.mDefaultTextDirectionHeuristicCompat.isRtl(paramCharSequence, 0, paramCharSequence.length());
  }
  
  public boolean isRtl(String paramString)
  {
    return isRtl(paramString);
  }
  
  public boolean isRtlContext()
  {
    return this.mIsRtlContext;
  }
  
  public CharSequence unicodeWrap(CharSequence paramCharSequence)
  {
    return unicodeWrap(paramCharSequence, this.mDefaultTextDirectionHeuristicCompat, true);
  }
  
  public CharSequence unicodeWrap(CharSequence paramCharSequence, TextDirectionHeuristicCompat paramTextDirectionHeuristicCompat)
  {
    return unicodeWrap(paramCharSequence, paramTextDirectionHeuristicCompat, true);
  }
  
  public CharSequence unicodeWrap(CharSequence paramCharSequence, TextDirectionHeuristicCompat paramTextDirectionHeuristicCompat, boolean paramBoolean)
  {
    if (paramCharSequence == null) {
      return null;
    }
    boolean bool = paramTextDirectionHeuristicCompat.isRtl(paramCharSequence, 0, paramCharSequence.length());
    SpannableStringBuilder localSpannableStringBuilder = new SpannableStringBuilder();
    if ((getStereoReset()) && (paramBoolean))
    {
      if (bool) {
        paramTextDirectionHeuristicCompat = TextDirectionHeuristicsCompat.RTL;
      } else {
        paramTextDirectionHeuristicCompat = TextDirectionHeuristicsCompat.LTR;
      }
      localSpannableStringBuilder.append(markBefore(paramCharSequence, paramTextDirectionHeuristicCompat));
    }
    if (bool != this.mIsRtlContext)
    {
      char c1;
      char c2;
      if (bool)
      {
        c1 = '‫';
        c2 = c1;
      }
      else
      {
        c1 = '‪';
        c2 = c1;
      }
      localSpannableStringBuilder.append(c2);
      localSpannableStringBuilder.append(paramCharSequence);
      localSpannableStringBuilder.append('‬');
    }
    else
    {
      localSpannableStringBuilder.append(paramCharSequence);
    }
    if (paramBoolean)
    {
      if (bool) {
        paramTextDirectionHeuristicCompat = TextDirectionHeuristicsCompat.RTL;
      } else {
        paramTextDirectionHeuristicCompat = TextDirectionHeuristicsCompat.LTR;
      }
      localSpannableStringBuilder.append(markAfter(paramCharSequence, paramTextDirectionHeuristicCompat));
    }
    return localSpannableStringBuilder;
  }
  
  public CharSequence unicodeWrap(CharSequence paramCharSequence, boolean paramBoolean)
  {
    return unicodeWrap(paramCharSequence, this.mDefaultTextDirectionHeuristicCompat, paramBoolean);
  }
  
  public String unicodeWrap(String paramString)
  {
    return unicodeWrap(paramString, this.mDefaultTextDirectionHeuristicCompat, true);
  }
  
  public String unicodeWrap(String paramString, TextDirectionHeuristicCompat paramTextDirectionHeuristicCompat)
  {
    return unicodeWrap(paramString, paramTextDirectionHeuristicCompat, true);
  }
  
  public String unicodeWrap(String paramString, TextDirectionHeuristicCompat paramTextDirectionHeuristicCompat, boolean paramBoolean)
  {
    if (paramString == null) {
      return null;
    }
    return unicodeWrap(paramString, paramTextDirectionHeuristicCompat, paramBoolean).toString();
  }
  
  public String unicodeWrap(String paramString, boolean paramBoolean)
  {
    return unicodeWrap(paramString, this.mDefaultTextDirectionHeuristicCompat, paramBoolean);
  }
  
  public static final class Builder
  {
    private int mFlags;
    private boolean mIsRtlContext;
    private TextDirectionHeuristicCompat mTextDirectionHeuristicCompat;
    
    public Builder()
    {
      initialize(BidiFormatter.isRtlLocale(Locale.getDefault()));
    }
    
    public Builder(Locale paramLocale)
    {
      initialize(BidiFormatter.isRtlLocale(paramLocale));
    }
    
    public Builder(boolean paramBoolean)
    {
      initialize(paramBoolean);
    }
    
    private static BidiFormatter getDefaultInstanceFromContext(boolean paramBoolean)
    {
      BidiFormatter localBidiFormatter;
      if (paramBoolean) {
        localBidiFormatter = BidiFormatter.DEFAULT_RTL_INSTANCE;
      } else {
        localBidiFormatter = BidiFormatter.DEFAULT_LTR_INSTANCE;
      }
      return localBidiFormatter;
    }
    
    private void initialize(boolean paramBoolean)
    {
      this.mIsRtlContext = paramBoolean;
      this.mTextDirectionHeuristicCompat = BidiFormatter.DEFAULT_TEXT_DIRECTION_HEURISTIC;
      this.mFlags = 2;
    }
    
    public BidiFormatter build()
    {
      if ((this.mFlags == 2) && (this.mTextDirectionHeuristicCompat == BidiFormatter.DEFAULT_TEXT_DIRECTION_HEURISTIC)) {
        return getDefaultInstanceFromContext(this.mIsRtlContext);
      }
      return new BidiFormatter(this.mIsRtlContext, this.mFlags, this.mTextDirectionHeuristicCompat, null);
    }
    
    public Builder setTextDirectionHeuristic(TextDirectionHeuristicCompat paramTextDirectionHeuristicCompat)
    {
      this.mTextDirectionHeuristicCompat = paramTextDirectionHeuristicCompat;
      return this;
    }
    
    public Builder stereoReset(boolean paramBoolean)
    {
      if (paramBoolean) {
        this.mFlags |= 0x2;
      } else {
        this.mFlags &= 0xFFFFFFFD;
      }
      return this;
    }
  }
  
  private static class DirectionalityEstimator
  {
    private static final byte[] DIR_TYPE_CACHE = new byte['܀'];
    private static final int DIR_TYPE_CACHE_SIZE = 1792;
    private int charIndex;
    private final boolean isHtml;
    private char lastChar;
    private final int length;
    private final CharSequence text;
    
    static
    {
      for (int i = 0; i < 1792; i++) {
        DIR_TYPE_CACHE[i] = Character.getDirectionality(i);
      }
    }
    
    DirectionalityEstimator(CharSequence paramCharSequence, boolean paramBoolean)
    {
      this.text = paramCharSequence;
      this.isHtml = paramBoolean;
      this.length = paramCharSequence.length();
    }
    
    private static byte getCachedDirectionality(char paramChar)
    {
      byte b1;
      byte b2;
      if (paramChar < '܀')
      {
        b1 = DIR_TYPE_CACHE[paramChar];
        b2 = b1;
      }
      else
      {
        b1 = Character.getDirectionality(paramChar);
        b2 = b1;
      }
      return b2;
    }
    
    private byte skipEntityBackward()
    {
      int i = this.charIndex;
      int j;
      do
      {
        j = this.charIndex;
        if (j <= 0) {
          break;
        }
        CharSequence localCharSequence = this.text;
        j--;
        this.charIndex = j;
        this.lastChar = localCharSequence.charAt(j);
        j = this.lastChar;
        if (j == 38) {
          return 12;
        }
      } while (j != 59);
      this.charIndex = i;
      this.lastChar = ((char)59);
      return 13;
    }
    
    private byte skipEntityForward()
    {
      int i;
      do
      {
        i = this.charIndex;
        if (i >= this.length) {
          break;
        }
        CharSequence localCharSequence = this.text;
        this.charIndex = (i + 1);
        i = localCharSequence.charAt(i);
        this.lastChar = ((char)i);
      } while (i != 59);
      return 12;
    }
    
    private byte skipTagBackward()
    {
      int i = this.charIndex;
      CharSequence localCharSequence;
      do
      {
        j = this.charIndex;
        if (j <= 0) {
          break;
        }
        localCharSequence = this.text;
        j--;
        this.charIndex = j;
        this.lastChar = localCharSequence.charAt(j);
        j = this.lastChar;
        if (j == 60) {
          return 12;
        }
        if (j == 62) {
          break;
        }
      } while ((j != 34) && (j != 39));
      int j = this.lastChar;
      for (;;)
      {
        int k = this.charIndex;
        if (k <= 0) {
          break;
        }
        localCharSequence = this.text;
        k--;
        this.charIndex = k;
        k = localCharSequence.charAt(k);
        this.lastChar = ((char)k);
        if (k == j) {
          break;
        }
      }
      this.charIndex = i;
      this.lastChar = ((char)62);
      return 13;
    }
    
    private byte skipTagForward()
    {
      int i = this.charIndex;
      CharSequence localCharSequence;
      do
      {
        j = this.charIndex;
        if (j >= this.length) {
          break;
        }
        localCharSequence = this.text;
        this.charIndex = (j + 1);
        this.lastChar = localCharSequence.charAt(j);
        j = this.lastChar;
        if (j == 62) {
          return 12;
        }
      } while ((j != 34) && (j != 39));
      int j = this.lastChar;
      for (;;)
      {
        int k = this.charIndex;
        if (k >= this.length) {
          break;
        }
        localCharSequence = this.text;
        this.charIndex = (k + 1);
        k = localCharSequence.charAt(k);
        this.lastChar = ((char)k);
        if (k == j) {
          break;
        }
      }
      this.charIndex = i;
      this.lastChar = ((char)60);
      return 13;
    }
    
    byte dirTypeBackward()
    {
      this.lastChar = this.text.charAt(this.charIndex - 1);
      int i;
      if (Character.isLowSurrogate(this.lastChar))
      {
        i = Character.codePointBefore(this.text, this.charIndex);
        this.charIndex -= Character.charCount(i);
        return Character.getDirectionality(i);
      }
      this.charIndex -= 1;
      byte b1 = getCachedDirectionality(this.lastChar);
      byte b2 = b1;
      if (this.isHtml)
      {
        i = this.lastChar;
        if (i == 62)
        {
          i = skipTagBackward();
          b2 = i;
        }
        else
        {
          b2 = b1;
          if (i == 59)
          {
            i = skipEntityBackward();
            b2 = i;
          }
        }
      }
      return b2;
    }
    
    byte dirTypeForward()
    {
      this.lastChar = this.text.charAt(this.charIndex);
      int i;
      if (Character.isHighSurrogate(this.lastChar))
      {
        i = Character.codePointAt(this.text, this.charIndex);
        this.charIndex += Character.charCount(i);
        return Character.getDirectionality(i);
      }
      this.charIndex += 1;
      byte b1 = getCachedDirectionality(this.lastChar);
      byte b2 = b1;
      if (this.isHtml)
      {
        i = this.lastChar;
        if (i == 60)
        {
          i = skipTagForward();
          b2 = i;
        }
        else
        {
          b2 = b1;
          if (i == 38)
          {
            i = skipEntityForward();
            b2 = i;
          }
        }
      }
      return b2;
    }
    
    int getEntryDir()
    {
      this.charIndex = 0;
      int i = 0;
      int j = 0;
      int k = 0;
      while ((this.charIndex < this.length) && (i == 0))
      {
        int m = dirTypeForward();
        if (m != 9) {
          switch (m)
          {
          default: 
            switch (m)
            {
            default: 
              break;
            case 18: 
              k--;
              j = 0;
              break;
            case 16: 
            case 17: 
              k++;
              j = 1;
              break;
            case 14: 
            case 15: 
              k++;
              j = -1;
            }
            break;
          case 1: 
          case 2: 
            if (k == 0) {
              return 1;
            }
          case 0: 
            if (k == 0) {
              return -1;
            }
            i = k;
          }
        }
      }
      if (i == 0) {
        return 0;
      }
      if (j != 0) {
        return j;
      }
      while (this.charIndex > 0) {
        switch (dirTypeBackward())
        {
        default: 
          break;
        case 18: 
          k++;
          break;
        case 16: 
        case 17: 
          if (i == k) {
            return 1;
          }
          k--;
          break;
        case 14: 
        case 15: 
          if (i == k) {
            return -1;
          }
          k--;
        }
      }
      return 0;
    }
    
    int getExitDir()
    {
      this.charIndex = this.length;
      int i = 0;
      int j = 0;
      while (this.charIndex > 0)
      {
        int k = dirTypeBackward();
        if (k != 9) {
          switch (k)
          {
          default: 
            switch (k)
            {
            default: 
              if (i != 0) {}
              break;
            case 18: 
              j++;
              break;
            case 16: 
            case 17: 
              if (i == j) {
                return 1;
              }
              j--;
              break;
            case 14: 
            case 15: 
              if (i == j) {
                return -1;
              }
              j--;
            }
            break;
          case 1: 
          case 2: 
            if (j == 0) {
              return 1;
            }
            if (i != 0) {}
            break;
          case 0: 
            if (j == 0) {
              return -1;
            }
            if (i == 0) {
              i = j;
            }
            break;
          }
        }
      }
      return 0;
    }
  }
}


/* Location:              ~/android/support/v4/text/BidiFormatter.class
 *
 * Reversed by:           J
 */