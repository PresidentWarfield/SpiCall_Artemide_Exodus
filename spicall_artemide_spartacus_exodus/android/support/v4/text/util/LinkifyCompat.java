package android.support.v4.text.util;

import android.os.Build.VERSION;
import android.support.v4.util.PatternsCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.text.style.URLSpan;
import android.text.util.Linkify;
import android.text.util.Linkify.MatchFilter;
import android.text.util.Linkify.TransformFilter;
import android.widget.TextView;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class LinkifyCompat
{
  private static final Comparator<LinkSpec> COMPARATOR = new Comparator()
  {
    public final int compare(LinkifyCompat.LinkSpec paramAnonymousLinkSpec1, LinkifyCompat.LinkSpec paramAnonymousLinkSpec2)
    {
      if (paramAnonymousLinkSpec1.start < paramAnonymousLinkSpec2.start) {
        return -1;
      }
      if (paramAnonymousLinkSpec1.start > paramAnonymousLinkSpec2.start) {
        return 1;
      }
      if (paramAnonymousLinkSpec1.end < paramAnonymousLinkSpec2.end) {
        return 1;
      }
      if (paramAnonymousLinkSpec1.end > paramAnonymousLinkSpec2.end) {
        return -1;
      }
      return 0;
    }
  };
  private static final String[] EMPTY_STRING = new String[0];
  
  private static void addLinkMovementMethod(TextView paramTextView)
  {
    MovementMethod localMovementMethod = paramTextView.getMovementMethod();
    if (((localMovementMethod == null) || (!(localMovementMethod instanceof LinkMovementMethod))) && (paramTextView.getLinksClickable())) {
      paramTextView.setMovementMethod(LinkMovementMethod.getInstance());
    }
  }
  
  public static final void addLinks(TextView paramTextView, Pattern paramPattern, String paramString)
  {
    if (Build.VERSION.SDK_INT >= 26)
    {
      Linkify.addLinks(paramTextView, paramPattern, paramString);
      return;
    }
    addLinks(paramTextView, paramPattern, paramString, null, null, null);
  }
  
  public static final void addLinks(TextView paramTextView, Pattern paramPattern, String paramString, Linkify.MatchFilter paramMatchFilter, Linkify.TransformFilter paramTransformFilter)
  {
    if (Build.VERSION.SDK_INT >= 26)
    {
      Linkify.addLinks(paramTextView, paramPattern, paramString, paramMatchFilter, paramTransformFilter);
      return;
    }
    addLinks(paramTextView, paramPattern, paramString, null, paramMatchFilter, paramTransformFilter);
  }
  
  public static final void addLinks(TextView paramTextView, Pattern paramPattern, String paramString, String[] paramArrayOfString, Linkify.MatchFilter paramMatchFilter, Linkify.TransformFilter paramTransformFilter)
  {
    if (Build.VERSION.SDK_INT >= 26)
    {
      Linkify.addLinks(paramTextView, paramPattern, paramString, paramArrayOfString, paramMatchFilter, paramTransformFilter);
      return;
    }
    SpannableString localSpannableString = SpannableString.valueOf(paramTextView.getText());
    if (addLinks(localSpannableString, paramPattern, paramString, paramArrayOfString, paramMatchFilter, paramTransformFilter))
    {
      paramTextView.setText(localSpannableString);
      addLinkMovementMethod(paramTextView);
    }
  }
  
  public static final boolean addLinks(Spannable paramSpannable, int paramInt)
  {
    if (Build.VERSION.SDK_INT >= 26) {
      return Linkify.addLinks(paramSpannable, paramInt);
    }
    if (paramInt == 0) {
      return false;
    }
    Object localObject1 = (URLSpan[])paramSpannable.getSpans(0, paramSpannable.length(), URLSpan.class);
    for (int i = localObject1.length - 1; i >= 0; i--) {
      paramSpannable.removeSpan(localObject1[i]);
    }
    if ((paramInt & 0x4) != 0) {
      Linkify.addLinks(paramSpannable, 4);
    }
    ArrayList localArrayList = new ArrayList();
    Object localObject2;
    if ((paramInt & 0x1) != 0)
    {
      localObject1 = PatternsCompat.AUTOLINK_WEB_URL;
      localObject2 = Linkify.sUrlMatchFilter;
      gatherLinks(localArrayList, paramSpannable, (Pattern)localObject1, new String[] { "http://", "https://", "rtsp://" }, (Linkify.MatchFilter)localObject2, null);
    }
    if ((paramInt & 0x2) != 0) {
      gatherLinks(localArrayList, paramSpannable, PatternsCompat.AUTOLINK_EMAIL_ADDRESS, new String[] { "mailto:" }, null, null);
    }
    if ((paramInt & 0x8) != 0) {
      gatherMapLinks(localArrayList, paramSpannable);
    }
    pruneOverlaps(localArrayList, paramSpannable);
    if (localArrayList.size() == 0) {
      return false;
    }
    localObject1 = localArrayList.iterator();
    while (((Iterator)localObject1).hasNext())
    {
      localObject2 = (LinkSpec)((Iterator)localObject1).next();
      if (((LinkSpec)localObject2).frameworkAddedSpan == null) {
        applyLink(((LinkSpec)localObject2).url, ((LinkSpec)localObject2).start, ((LinkSpec)localObject2).end, paramSpannable);
      }
    }
    return true;
  }
  
  public static final boolean addLinks(Spannable paramSpannable, Pattern paramPattern, String paramString)
  {
    if (Build.VERSION.SDK_INT >= 26) {
      return Linkify.addLinks(paramSpannable, paramPattern, paramString);
    }
    return addLinks(paramSpannable, paramPattern, paramString, null, null, null);
  }
  
  public static final boolean addLinks(Spannable paramSpannable, Pattern paramPattern, String paramString, Linkify.MatchFilter paramMatchFilter, Linkify.TransformFilter paramTransformFilter)
  {
    if (Build.VERSION.SDK_INT >= 26) {
      return Linkify.addLinks(paramSpannable, paramPattern, paramString, paramMatchFilter, paramTransformFilter);
    }
    return addLinks(paramSpannable, paramPattern, paramString, null, paramMatchFilter, paramTransformFilter);
  }
  
  public static final boolean addLinks(Spannable paramSpannable, Pattern paramPattern, String paramString, String[] paramArrayOfString, Linkify.MatchFilter paramMatchFilter, Linkify.TransformFilter paramTransformFilter)
  {
    if (Build.VERSION.SDK_INT >= 26) {
      return Linkify.addLinks(paramSpannable, paramPattern, paramString, paramArrayOfString, paramMatchFilter, paramTransformFilter);
    }
    String str = paramString;
    if (paramString == null) {
      str = "";
    }
    if (paramArrayOfString != null)
    {
      paramString = paramArrayOfString;
      if (paramArrayOfString.length >= 1) {}
    }
    else
    {
      paramString = EMPTY_STRING;
    }
    String[] arrayOfString = new String[paramString.length + 1];
    arrayOfString[0] = str.toLowerCase(Locale.ROOT);
    int i = 0;
    while (i < paramString.length)
    {
      paramArrayOfString = paramString[i];
      i++;
      if (paramArrayOfString == null) {
        paramArrayOfString = "";
      } else {
        paramArrayOfString = paramArrayOfString.toLowerCase(Locale.ROOT);
      }
      arrayOfString[i] = paramArrayOfString;
    }
    paramPattern = paramPattern.matcher(paramSpannable);
    boolean bool1 = false;
    while (paramPattern.find())
    {
      i = paramPattern.start();
      int j = paramPattern.end();
      boolean bool2;
      if (paramMatchFilter != null) {
        bool2 = paramMatchFilter.acceptMatch(paramSpannable, i, j);
      } else {
        bool2 = true;
      }
      if (bool2)
      {
        applyLink(makeUrl(paramPattern.group(0), arrayOfString, paramPattern, paramTransformFilter), i, j, paramSpannable);
        bool1 = true;
      }
    }
    return bool1;
  }
  
  public static final boolean addLinks(TextView paramTextView, int paramInt)
  {
    if (Build.VERSION.SDK_INT >= 26) {
      return Linkify.addLinks(paramTextView, paramInt);
    }
    if (paramInt == 0) {
      return false;
    }
    Object localObject = paramTextView.getText();
    if ((localObject instanceof Spannable))
    {
      if (addLinks((Spannable)localObject, paramInt))
      {
        addLinkMovementMethod(paramTextView);
        return true;
      }
      return false;
    }
    localObject = SpannableString.valueOf((CharSequence)localObject);
    if (addLinks((Spannable)localObject, paramInt))
    {
      addLinkMovementMethod(paramTextView);
      paramTextView.setText((CharSequence)localObject);
      return true;
    }
    return false;
  }
  
  private static void applyLink(String paramString, int paramInt1, int paramInt2, Spannable paramSpannable)
  {
    paramSpannable.setSpan(new URLSpan(paramString), paramInt1, paramInt2, 33);
  }
  
  private static void gatherLinks(ArrayList<LinkSpec> paramArrayList, Spannable paramSpannable, Pattern paramPattern, String[] paramArrayOfString, Linkify.MatchFilter paramMatchFilter, Linkify.TransformFilter paramTransformFilter)
  {
    paramPattern = paramPattern.matcher(paramSpannable);
    while (paramPattern.find())
    {
      int i = paramPattern.start();
      int j = paramPattern.end();
      if ((paramMatchFilter == null) || (paramMatchFilter.acceptMatch(paramSpannable, i, j)))
      {
        LinkSpec localLinkSpec = new LinkSpec();
        localLinkSpec.url = makeUrl(paramPattern.group(0), paramArrayOfString, paramPattern, paramTransformFilter);
        localLinkSpec.start = i;
        localLinkSpec.end = j;
        paramArrayList.add(localLinkSpec);
      }
    }
  }
  
  /* Error */
  private static final void gatherMapLinks(ArrayList<LinkSpec> paramArrayList, Spannable paramSpannable)
  {
    // Byte code:
    //   0: aload_1
    //   1: invokevirtual 253	java/lang/Object:toString	()Ljava/lang/String;
    //   4: astore_1
    //   5: iconst_0
    //   6: istore_2
    //   7: aload_1
    //   8: invokestatic 259	android/webkit/WebView:findAddress	(Ljava/lang/String;)Ljava/lang/String;
    //   11: astore_3
    //   12: aload_3
    //   13: ifnull +115 -> 128
    //   16: aload_1
    //   17: aload_3
    //   18: invokevirtual 263	java/lang/String:indexOf	(Ljava/lang/String;)I
    //   21: istore 4
    //   23: iload 4
    //   25: ifge +6 -> 31
    //   28: goto +100 -> 128
    //   31: new 8	android/support/v4/text/util/LinkifyCompat$LinkSpec
    //   34: astore 5
    //   36: aload 5
    //   38: invokespecial 239	android/support/v4/text/util/LinkifyCompat$LinkSpec:<init>	()V
    //   41: aload_3
    //   42: invokevirtual 264	java/lang/String:length	()I
    //   45: iload 4
    //   47: iadd
    //   48: istore 6
    //   50: aload 5
    //   52: iload 4
    //   54: iload_2
    //   55: iadd
    //   56: putfield 172	android/support/v4/text/util/LinkifyCompat$LinkSpec:start	I
    //   59: iload_2
    //   60: iload 6
    //   62: iadd
    //   63: istore_2
    //   64: aload 5
    //   66: iload_2
    //   67: putfield 175	android/support/v4/text/util/LinkifyCompat$LinkSpec:end	I
    //   70: aload_1
    //   71: iload 6
    //   73: invokevirtual 267	java/lang/String:substring	(I)Ljava/lang/String;
    //   76: astore_1
    //   77: aload_3
    //   78: ldc_w 269
    //   81: invokestatic 275	java/net/URLEncoder:encode	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   84: astore 7
    //   86: new 277	java/lang/StringBuilder
    //   89: astore_3
    //   90: aload_3
    //   91: invokespecial 278	java/lang/StringBuilder:<init>	()V
    //   94: aload_3
    //   95: ldc_w 280
    //   98: invokevirtual 284	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   101: pop
    //   102: aload_3
    //   103: aload 7
    //   105: invokevirtual 284	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   108: pop
    //   109: aload 5
    //   111: aload_3
    //   112: invokevirtual 285	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   115: putfield 169	android/support/v4/text/util/LinkifyCompat$LinkSpec:url	Ljava/lang/String;
    //   118: aload_0
    //   119: aload 5
    //   121: invokevirtual 243	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   124: pop
    //   125: goto -118 -> 7
    //   128: return
    //   129: astore_0
    //   130: return
    //   131: astore 5
    //   133: goto -126 -> 7
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	136	0	paramArrayList	ArrayList<LinkSpec>
    //   0	136	1	paramSpannable	Spannable
    //   6	61	2	i	int
    //   11	101	3	localObject	Object
    //   21	35	4	j	int
    //   34	86	5	localLinkSpec	LinkSpec
    //   131	1	5	localUnsupportedEncodingException	java.io.UnsupportedEncodingException
    //   48	24	6	k	int
    //   84	20	7	str	String
    // Exception table:
    //   from	to	target	type
    //   7	12	129	java/lang/UnsupportedOperationException
    //   16	23	129	java/lang/UnsupportedOperationException
    //   31	59	129	java/lang/UnsupportedOperationException
    //   64	77	129	java/lang/UnsupportedOperationException
    //   77	86	129	java/lang/UnsupportedOperationException
    //   86	125	129	java/lang/UnsupportedOperationException
    //   77	86	131	java/io/UnsupportedEncodingException
  }
  
  private static String makeUrl(String paramString, String[] paramArrayOfString, Matcher paramMatcher, Linkify.TransformFilter paramTransformFilter)
  {
    String str = paramString;
    if (paramTransformFilter != null) {
      str = paramTransformFilter.transformUrl(paramMatcher, paramString);
    }
    for (int i = 0;; i++)
    {
      j = paramArrayOfString.length;
      int k = 1;
      if (i >= j) {
        break;
      }
      if (str.regionMatches(true, 0, paramArrayOfString[i], 0, paramArrayOfString[i].length()))
      {
        j = k;
        paramString = str;
        if (str.regionMatches(false, 0, paramArrayOfString[i], 0, paramArrayOfString[i].length())) {
          break label143;
        }
        paramString = new StringBuilder();
        paramString.append(paramArrayOfString[i]);
        paramString.append(str.substring(paramArrayOfString[i].length()));
        paramString = paramString.toString();
        j = k;
        break label143;
      }
    }
    int j = 0;
    paramString = str;
    label143:
    paramMatcher = paramString;
    if (j == 0)
    {
      paramMatcher = paramString;
      if (paramArrayOfString.length > 0)
      {
        paramMatcher = new StringBuilder();
        paramMatcher.append(paramArrayOfString[0]);
        paramMatcher.append(paramString);
        paramMatcher = paramMatcher.toString();
      }
    }
    return paramMatcher;
  }
  
  private static final void pruneOverlaps(ArrayList<LinkSpec> paramArrayList, Spannable paramSpannable)
  {
    int i = paramSpannable.length();
    int j = 0;
    Object localObject = (URLSpan[])paramSpannable.getSpans(0, i, URLSpan.class);
    LinkSpec localLinkSpec;
    for (i = 0; i < localObject.length; i++)
    {
      localLinkSpec = new LinkSpec();
      localLinkSpec.frameworkAddedSpan = localObject[i];
      localLinkSpec.start = paramSpannable.getSpanStart(localObject[i]);
      localLinkSpec.end = paramSpannable.getSpanEnd(localObject[i]);
      paramArrayList.add(localLinkSpec);
    }
    Collections.sort(paramArrayList, COMPARATOR);
    int k = paramArrayList.size();
    while (j < k - 1)
    {
      localLinkSpec = (LinkSpec)paramArrayList.get(j);
      int m = j + 1;
      localObject = (LinkSpec)paramArrayList.get(m);
      if ((localLinkSpec.start <= ((LinkSpec)localObject).start) && (localLinkSpec.end > ((LinkSpec)localObject).start))
      {
        if (((LinkSpec)localObject).end <= localLinkSpec.end) {
          i = m;
        } else if (localLinkSpec.end - localLinkSpec.start > ((LinkSpec)localObject).end - ((LinkSpec)localObject).start) {
          i = m;
        } else if (localLinkSpec.end - localLinkSpec.start < ((LinkSpec)localObject).end - ((LinkSpec)localObject).start) {
          i = j;
        } else {
          i = -1;
        }
        if (i != -1)
        {
          localObject = ((LinkSpec)paramArrayList.get(i)).frameworkAddedSpan;
          if (localObject != null) {
            paramSpannable.removeSpan(localObject);
          }
          paramArrayList.remove(i);
          k--;
          continue;
        }
      }
      j = m;
    }
  }
  
  private static class LinkSpec
  {
    int end;
    URLSpan frameworkAddedSpan;
    int start;
    String url;
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface LinkifyMask {}
}


/* Location:              ~/android/support/v4/text/util/LinkifyCompat.class
 *
 * Reversed by:           J
 */