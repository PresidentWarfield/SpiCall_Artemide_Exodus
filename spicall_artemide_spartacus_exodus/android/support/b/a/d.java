package android.support.b.a;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.util.Xml;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import org.xmlpull.v1.XmlPullParser;

public class d
{
  /* Error */
  public static Interpolator a(Context paramContext, int paramInt)
  {
    // Byte code:
    //   0: getstatic 16	android/os/Build$VERSION:SDK_INT	I
    //   3: bipush 21
    //   5: if_icmplt +9 -> 14
    //   8: aload_0
    //   9: iload_1
    //   10: invokestatic 21	android/view/animation/AnimationUtils:loadInterpolator	(Landroid/content/Context;I)Landroid/view/animation/Interpolator;
    //   13: areturn
    //   14: aconst_null
    //   15: astore_2
    //   16: aconst_null
    //   17: astore_3
    //   18: aconst_null
    //   19: astore 4
    //   21: iload_1
    //   22: ldc 22
    //   24: if_icmpne +23 -> 47
    //   27: new 24	android/support/v4/view/animation/FastOutLinearInInterpolator
    //   30: dup
    //   31: invokespecial 28	android/support/v4/view/animation/FastOutLinearInInterpolator:<init>	()V
    //   34: areturn
    //   35: astore_0
    //   36: goto +233 -> 269
    //   39: astore_3
    //   40: goto +84 -> 124
    //   43: astore_2
    //   44: goto +153 -> 197
    //   47: iload_1
    //   48: ldc 29
    //   50: if_icmpne +11 -> 61
    //   53: new 31	android/support/v4/view/animation/FastOutSlowInInterpolator
    //   56: dup
    //   57: invokespecial 32	android/support/v4/view/animation/FastOutSlowInInterpolator:<init>	()V
    //   60: areturn
    //   61: iload_1
    //   62: ldc 33
    //   64: if_icmpne +11 -> 75
    //   67: new 35	android/support/v4/view/animation/LinearOutSlowInInterpolator
    //   70: dup
    //   71: invokespecial 36	android/support/v4/view/animation/LinearOutSlowInInterpolator:<init>	()V
    //   74: areturn
    //   75: aload_0
    //   76: invokevirtual 42	android/content/Context:getResources	()Landroid/content/res/Resources;
    //   79: iload_1
    //   80: invokevirtual 48	android/content/res/Resources:getAnimation	(I)Landroid/content/res/XmlResourceParser;
    //   83: astore 5
    //   85: aload 5
    //   87: astore 4
    //   89: aload 5
    //   91: astore_2
    //   92: aload 5
    //   94: astore_3
    //   95: aload_0
    //   96: aload_0
    //   97: invokevirtual 42	android/content/Context:getResources	()Landroid/content/res/Resources;
    //   100: aload_0
    //   101: invokevirtual 52	android/content/Context:getTheme	()Landroid/content/res/Resources$Theme;
    //   104: aload 5
    //   106: invokestatic 55	android/support/b/a/d:a	(Landroid/content/Context;Landroid/content/res/Resources;Landroid/content/res/Resources$Theme;Lorg/xmlpull/v1/XmlPullParser;)Landroid/view/animation/Interpolator;
    //   109: astore_0
    //   110: aload 5
    //   112: ifnull +10 -> 122
    //   115: aload 5
    //   117: invokeinterface 60 1 0
    //   122: aload_0
    //   123: areturn
    //   124: aload_2
    //   125: astore 4
    //   127: new 62	android/content/res/Resources$NotFoundException
    //   130: astore_0
    //   131: aload_2
    //   132: astore 4
    //   134: new 64	java/lang/StringBuilder
    //   137: astore 5
    //   139: aload_2
    //   140: astore 4
    //   142: aload 5
    //   144: invokespecial 65	java/lang/StringBuilder:<init>	()V
    //   147: aload_2
    //   148: astore 4
    //   150: aload 5
    //   152: ldc 67
    //   154: invokevirtual 71	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   157: pop
    //   158: aload_2
    //   159: astore 4
    //   161: aload 5
    //   163: iload_1
    //   164: invokestatic 77	java/lang/Integer:toHexString	(I)Ljava/lang/String;
    //   167: invokevirtual 71	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   170: pop
    //   171: aload_2
    //   172: astore 4
    //   174: aload_0
    //   175: aload 5
    //   177: invokevirtual 81	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   180: invokespecial 84	android/content/res/Resources$NotFoundException:<init>	(Ljava/lang/String;)V
    //   183: aload_2
    //   184: astore 4
    //   186: aload_0
    //   187: aload_3
    //   188: invokevirtual 88	android/content/res/Resources$NotFoundException:initCause	(Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   191: pop
    //   192: aload_2
    //   193: astore 4
    //   195: aload_0
    //   196: athrow
    //   197: aload_3
    //   198: astore 4
    //   200: new 62	android/content/res/Resources$NotFoundException
    //   203: astore 5
    //   205: aload_3
    //   206: astore 4
    //   208: new 64	java/lang/StringBuilder
    //   211: astore_0
    //   212: aload_3
    //   213: astore 4
    //   215: aload_0
    //   216: invokespecial 65	java/lang/StringBuilder:<init>	()V
    //   219: aload_3
    //   220: astore 4
    //   222: aload_0
    //   223: ldc 67
    //   225: invokevirtual 71	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   228: pop
    //   229: aload_3
    //   230: astore 4
    //   232: aload_0
    //   233: iload_1
    //   234: invokestatic 77	java/lang/Integer:toHexString	(I)Ljava/lang/String;
    //   237: invokevirtual 71	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   240: pop
    //   241: aload_3
    //   242: astore 4
    //   244: aload 5
    //   246: aload_0
    //   247: invokevirtual 81	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   250: invokespecial 84	android/content/res/Resources$NotFoundException:<init>	(Ljava/lang/String;)V
    //   253: aload_3
    //   254: astore 4
    //   256: aload 5
    //   258: aload_2
    //   259: invokevirtual 88	android/content/res/Resources$NotFoundException:initCause	(Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   262: pop
    //   263: aload_3
    //   264: astore 4
    //   266: aload 5
    //   268: athrow
    //   269: aload 4
    //   271: ifnull +10 -> 281
    //   274: aload 4
    //   276: invokeinterface 60 1 0
    //   281: aload_0
    //   282: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	283	0	paramContext	Context
    //   0	283	1	paramInt	int
    //   15	1	2	localObject1	Object
    //   43	1	2	localXmlPullParserException	org.xmlpull.v1.XmlPullParserException
    //   91	168	2	localObject2	Object
    //   17	1	3	localObject3	Object
    //   39	1	3	localIOException	java.io.IOException
    //   94	170	3	localObject4	Object
    //   19	256	4	localObject5	Object
    //   83	184	5	localObject6	Object
    // Exception table:
    //   from	to	target	type
    //   27	35	35	finally
    //   53	61	35	finally
    //   67	75	35	finally
    //   75	85	35	finally
    //   95	110	35	finally
    //   127	131	35	finally
    //   134	139	35	finally
    //   142	147	35	finally
    //   150	158	35	finally
    //   161	171	35	finally
    //   174	183	35	finally
    //   186	192	35	finally
    //   195	197	35	finally
    //   200	205	35	finally
    //   208	212	35	finally
    //   215	219	35	finally
    //   222	229	35	finally
    //   232	241	35	finally
    //   244	253	35	finally
    //   256	263	35	finally
    //   266	269	35	finally
    //   27	35	39	java/io/IOException
    //   53	61	39	java/io/IOException
    //   67	75	39	java/io/IOException
    //   75	85	39	java/io/IOException
    //   95	110	39	java/io/IOException
    //   27	35	43	org/xmlpull/v1/XmlPullParserException
    //   53	61	43	org/xmlpull/v1/XmlPullParserException
    //   67	75	43	org/xmlpull/v1/XmlPullParserException
    //   75	85	43	org/xmlpull/v1/XmlPullParserException
    //   95	110	43	org/xmlpull/v1/XmlPullParserException
  }
  
  private static Interpolator a(Context paramContext, Resources paramResources, Resources.Theme paramTheme, XmlPullParser paramXmlPullParser)
  {
    int i = paramXmlPullParser.getDepth();
    paramResources = null;
    for (;;)
    {
      int j = paramXmlPullParser.next();
      if (((j == 3) && (paramXmlPullParser.getDepth() <= i)) || (j == 1)) {
        return paramResources;
      }
      if (j == 2)
      {
        paramTheme = Xml.asAttributeSet(paramXmlPullParser);
        paramResources = paramXmlPullParser.getName();
        if (paramResources.equals("linearInterpolator"))
        {
          paramResources = new LinearInterpolator();
        }
        else if (paramResources.equals("accelerateInterpolator"))
        {
          paramResources = new AccelerateInterpolator(paramContext, paramTheme);
        }
        else if (paramResources.equals("decelerateInterpolator"))
        {
          paramResources = new DecelerateInterpolator(paramContext, paramTheme);
        }
        else if (paramResources.equals("accelerateDecelerateInterpolator"))
        {
          paramResources = new AccelerateDecelerateInterpolator();
        }
        else if (paramResources.equals("cycleInterpolator"))
        {
          paramResources = new CycleInterpolator(paramContext, paramTheme);
        }
        else if (paramResources.equals("anticipateInterpolator"))
        {
          paramResources = new AnticipateInterpolator(paramContext, paramTheme);
        }
        else if (paramResources.equals("overshootInterpolator"))
        {
          paramResources = new OvershootInterpolator(paramContext, paramTheme);
        }
        else if (paramResources.equals("anticipateOvershootInterpolator"))
        {
          paramResources = new AnticipateOvershootInterpolator(paramContext, paramTheme);
        }
        else if (paramResources.equals("bounceInterpolator"))
        {
          paramResources = new BounceInterpolator();
        }
        else
        {
          if (!paramResources.equals("pathInterpolator")) {
            break;
          }
          paramResources = new g(paramContext, paramTheme, paramXmlPullParser);
        }
      }
    }
    paramContext = new StringBuilder();
    paramContext.append("Unknown interpolator name: ");
    paramContext.append(paramXmlPullParser.getName());
    throw new RuntimeException(paramContext.toString());
    return paramResources;
  }
}


/* Location:              ~/android/support/b/a/d.class
 *
 * Reversed by:           J
 */