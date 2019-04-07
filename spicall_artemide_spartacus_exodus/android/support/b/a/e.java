package android.support.b.a;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.os.Build.VERSION;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v4.graphics.PathParser;
import android.support.v4.graphics.PathParser.PathDataNode;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.util.Xml;
import android.view.InflateException;
import java.util.ArrayList;
import java.util.Iterator;
import org.xmlpull.v1.XmlPullParser;

public class e
{
  private static int a(Resources paramResources, Resources.Theme paramTheme, AttributeSet paramAttributeSet, XmlPullParser paramXmlPullParser)
  {
    paramResources = TypedArrayUtils.obtainAttributes(paramResources, paramTheme, paramAttributeSet, a.j);
    int i = 0;
    paramTheme = TypedArrayUtils.peekNamedValue(paramResources, paramXmlPullParser, "value", 0);
    int j;
    if (paramTheme != null) {
      j = 1;
    } else {
      j = 0;
    }
    int k = i;
    if (j != 0)
    {
      k = i;
      if (a(paramTheme.type)) {
        k = 3;
      }
    }
    paramResources.recycle();
    return k;
  }
  
  private static int a(TypedArray paramTypedArray, int paramInt1, int paramInt2)
  {
    TypedValue localTypedValue = paramTypedArray.peekValue(paramInt1);
    int i = 1;
    int j = 0;
    if (localTypedValue != null) {
      paramInt1 = 1;
    } else {
      paramInt1 = 0;
    }
    int k;
    if (paramInt1 != 0) {
      k = localTypedValue.type;
    } else {
      k = 0;
    }
    paramTypedArray = paramTypedArray.peekValue(paramInt2);
    if (paramTypedArray != null) {
      paramInt2 = i;
    } else {
      paramInt2 = 0;
    }
    if (paramInt2 != 0) {
      i = paramTypedArray.type;
    } else {
      i = 0;
    }
    if ((paramInt1 == 0) || (!a(k)))
    {
      paramInt1 = j;
      if (paramInt2 != 0)
      {
        paramInt1 = j;
        if (!a(i)) {}
      }
    }
    else
    {
      paramInt1 = 3;
    }
    return paramInt1;
  }
  
  public static Animator a(Context paramContext, int paramInt)
  {
    if (Build.VERSION.SDK_INT >= 24) {
      paramContext = AnimatorInflater.loadAnimator(paramContext, paramInt);
    } else {
      paramContext = a(paramContext, paramContext.getResources(), paramContext.getTheme(), paramInt);
    }
    return paramContext;
  }
  
  public static Animator a(Context paramContext, Resources paramResources, Resources.Theme paramTheme, int paramInt)
  {
    return a(paramContext, paramResources, paramTheme, paramInt, 1.0F);
  }
  
  /* Error */
  public static Animator a(Context paramContext, Resources paramResources, Resources.Theme paramTheme, int paramInt, float paramFloat)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 5
    //   3: aconst_null
    //   4: astore 6
    //   6: aconst_null
    //   7: astore 7
    //   9: aload_1
    //   10: iload_3
    //   11: invokevirtual 86	android/content/res/Resources:getAnimation	(I)Landroid/content/res/XmlResourceParser;
    //   14: astore 8
    //   16: aload 8
    //   18: astore 7
    //   20: aload 8
    //   22: astore 5
    //   24: aload 8
    //   26: astore 6
    //   28: aload_0
    //   29: aload_1
    //   30: aload_2
    //   31: aload 8
    //   33: fload 4
    //   35: invokestatic 89	android/support/b/a/e:a	(Landroid/content/Context;Landroid/content/res/Resources;Landroid/content/res/Resources$Theme;Lorg/xmlpull/v1/XmlPullParser;F)Landroid/animation/Animator;
    //   38: astore_0
    //   39: aload 8
    //   41: ifnull +10 -> 51
    //   44: aload 8
    //   46: invokeinterface 94 1 0
    //   51: aload_0
    //   52: areturn
    //   53: astore_0
    //   54: goto +157 -> 211
    //   57: astore_2
    //   58: aload 5
    //   60: astore 7
    //   62: new 96	android/content/res/Resources$NotFoundException
    //   65: astore_1
    //   66: aload 5
    //   68: astore 7
    //   70: new 98	java/lang/StringBuilder
    //   73: astore_0
    //   74: aload 5
    //   76: astore 7
    //   78: aload_0
    //   79: invokespecial 101	java/lang/StringBuilder:<init>	()V
    //   82: aload 5
    //   84: astore 7
    //   86: aload_0
    //   87: ldc 103
    //   89: invokevirtual 107	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   92: pop
    //   93: aload 5
    //   95: astore 7
    //   97: aload_0
    //   98: iload_3
    //   99: invokestatic 113	java/lang/Integer:toHexString	(I)Ljava/lang/String;
    //   102: invokevirtual 107	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   105: pop
    //   106: aload 5
    //   108: astore 7
    //   110: aload_1
    //   111: aload_0
    //   112: invokevirtual 117	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   115: invokespecial 120	android/content/res/Resources$NotFoundException:<init>	(Ljava/lang/String;)V
    //   118: aload 5
    //   120: astore 7
    //   122: aload_1
    //   123: aload_2
    //   124: invokevirtual 124	android/content/res/Resources$NotFoundException:initCause	(Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   127: pop
    //   128: aload 5
    //   130: astore 7
    //   132: aload_1
    //   133: athrow
    //   134: astore_0
    //   135: aload 6
    //   137: astore 7
    //   139: new 96	android/content/res/Resources$NotFoundException
    //   142: astore_1
    //   143: aload 6
    //   145: astore 7
    //   147: new 98	java/lang/StringBuilder
    //   150: astore_2
    //   151: aload 6
    //   153: astore 7
    //   155: aload_2
    //   156: invokespecial 101	java/lang/StringBuilder:<init>	()V
    //   159: aload 6
    //   161: astore 7
    //   163: aload_2
    //   164: ldc 103
    //   166: invokevirtual 107	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   169: pop
    //   170: aload 6
    //   172: astore 7
    //   174: aload_2
    //   175: iload_3
    //   176: invokestatic 113	java/lang/Integer:toHexString	(I)Ljava/lang/String;
    //   179: invokevirtual 107	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   182: pop
    //   183: aload 6
    //   185: astore 7
    //   187: aload_1
    //   188: aload_2
    //   189: invokevirtual 117	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   192: invokespecial 120	android/content/res/Resources$NotFoundException:<init>	(Ljava/lang/String;)V
    //   195: aload 6
    //   197: astore 7
    //   199: aload_1
    //   200: aload_0
    //   201: invokevirtual 124	android/content/res/Resources$NotFoundException:initCause	(Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   204: pop
    //   205: aload 6
    //   207: astore 7
    //   209: aload_1
    //   210: athrow
    //   211: aload 7
    //   213: ifnull +10 -> 223
    //   216: aload 7
    //   218: invokeinterface 94 1 0
    //   223: aload_0
    //   224: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	225	0	paramContext	Context
    //   0	225	1	paramResources	Resources
    //   0	225	2	paramTheme	Resources.Theme
    //   0	225	3	paramInt	int
    //   0	225	4	paramFloat	float
    //   1	128	5	localObject1	Object
    //   4	202	6	localObject2	Object
    //   7	210	7	localObject3	Object
    //   14	31	8	localXmlResourceParser	android.content.res.XmlResourceParser
    // Exception table:
    //   from	to	target	type
    //   9	16	53	finally
    //   28	39	53	finally
    //   62	66	53	finally
    //   70	74	53	finally
    //   78	82	53	finally
    //   86	93	53	finally
    //   97	106	53	finally
    //   110	118	53	finally
    //   122	128	53	finally
    //   132	134	53	finally
    //   139	143	53	finally
    //   147	151	53	finally
    //   155	159	53	finally
    //   163	170	53	finally
    //   174	183	53	finally
    //   187	195	53	finally
    //   199	205	53	finally
    //   209	211	53	finally
    //   9	16	57	java/io/IOException
    //   28	39	57	java/io/IOException
    //   9	16	134	org/xmlpull/v1/XmlPullParserException
    //   28	39	134	org/xmlpull/v1/XmlPullParserException
  }
  
  private static Animator a(Context paramContext, Resources paramResources, Resources.Theme paramTheme, XmlPullParser paramXmlPullParser, float paramFloat)
  {
    return a(paramContext, paramResources, paramTheme, paramXmlPullParser, Xml.asAttributeSet(paramXmlPullParser), null, 0, paramFloat);
  }
  
  private static Animator a(Context paramContext, Resources paramResources, Resources.Theme paramTheme, XmlPullParser paramXmlPullParser, AttributeSet paramAttributeSet, AnimatorSet paramAnimatorSet, int paramInt, float paramFloat)
  {
    int i = paramXmlPullParser.getDepth();
    Object localObject1 = null;
    Object localObject2 = null;
    int k;
    int m;
    for (;;)
    {
      int j = paramXmlPullParser.next();
      k = 0;
      m = 0;
      if (((j == 3) && (paramXmlPullParser.getDepth() <= i)) || (j == 1)) {
        break label342;
      }
      if (j == 2)
      {
        Object localObject3 = paramXmlPullParser.getName();
        if (((String)localObject3).equals("objectAnimator"))
        {
          localObject3 = a(paramContext, paramResources, paramTheme, paramAttributeSet, paramFloat, paramXmlPullParser);
        }
        else if (((String)localObject3).equals("animator"))
        {
          localObject3 = a(paramContext, paramResources, paramTheme, paramAttributeSet, null, paramFloat, paramXmlPullParser);
        }
        else if (((String)localObject3).equals("set"))
        {
          localObject3 = new AnimatorSet();
          localObject1 = TypedArrayUtils.obtainAttributes(paramResources, paramTheme, paramAttributeSet, a.h);
          k = TypedArrayUtils.getNamedInt((TypedArray)localObject1, paramXmlPullParser, "ordering", 0, 0);
          a(paramContext, paramResources, paramTheme, paramXmlPullParser, paramAttributeSet, (AnimatorSet)localObject3, k, paramFloat);
          ((TypedArray)localObject1).recycle();
        }
        else
        {
          if (!((String)localObject3).equals("propertyValuesHolder")) {
            break;
          }
          localObject3 = a(paramContext, paramResources, paramTheme, paramXmlPullParser, Xml.asAttributeSet(paramXmlPullParser));
          if ((localObject3 != null) && (localObject1 != null) && ((localObject1 instanceof ValueAnimator))) {
            ((ValueAnimator)localObject1).setValues((PropertyValuesHolder[])localObject3);
          }
          m = 1;
          localObject3 = localObject1;
        }
        localObject1 = localObject3;
        if (paramAnimatorSet != null)
        {
          localObject1 = localObject3;
          if (m == 0)
          {
            Object localObject4 = localObject2;
            if (localObject2 == null) {
              localObject4 = new ArrayList();
            }
            ((ArrayList)localObject4).add(localObject3);
            localObject1 = localObject3;
            localObject2 = localObject4;
          }
        }
      }
    }
    paramContext = new StringBuilder();
    paramContext.append("Unknown animator name: ");
    paramContext.append(paramXmlPullParser.getName());
    throw new RuntimeException(paramContext.toString());
    label342:
    if ((paramAnimatorSet != null) && (localObject2 != null))
    {
      paramContext = new Animator[((ArrayList)localObject2).size()];
      paramResources = ((ArrayList)localObject2).iterator();
      for (m = k; paramResources.hasNext(); m++) {
        paramContext[m] = ((Animator)paramResources.next());
      }
      if (paramInt == 0) {
        paramAnimatorSet.playTogether(paramContext);
      } else {
        paramAnimatorSet.playSequentially(paramContext);
      }
    }
    return (Animator)localObject1;
  }
  
  private static Keyframe a(Keyframe paramKeyframe, float paramFloat)
  {
    if (paramKeyframe.getType() == Float.TYPE) {
      paramKeyframe = Keyframe.ofFloat(paramFloat);
    } else if (paramKeyframe.getType() == Integer.TYPE) {
      paramKeyframe = Keyframe.ofInt(paramFloat);
    } else {
      paramKeyframe = Keyframe.ofObject(paramFloat);
    }
    return paramKeyframe;
  }
  
  private static Keyframe a(Context paramContext, Resources paramResources, Resources.Theme paramTheme, AttributeSet paramAttributeSet, int paramInt, XmlPullParser paramXmlPullParser)
  {
    paramTheme = TypedArrayUtils.obtainAttributes(paramResources, paramTheme, paramAttributeSet, a.j);
    float f = TypedArrayUtils.getNamedFloat(paramTheme, paramXmlPullParser, "fraction", 3, -1.0F);
    paramResources = TypedArrayUtils.peekNamedValue(paramTheme, paramXmlPullParser, "value", 0);
    int i;
    if (paramResources != null) {
      i = 1;
    } else {
      i = 0;
    }
    int j = paramInt;
    if (paramInt == 4) {
      if ((i != 0) && (a(paramResources.type))) {
        j = 3;
      } else {
        j = 0;
      }
    }
    if (i != 0)
    {
      if (j != 3) {
        switch (j)
        {
        default: 
          paramResources = null;
          break;
        case 0: 
          paramResources = Keyframe.ofFloat(f, TypedArrayUtils.getNamedFloat(paramTheme, paramXmlPullParser, "value", 0, 0.0F));
          break;
        }
      } else {
        paramResources = Keyframe.ofInt(f, TypedArrayUtils.getNamedInt(paramTheme, paramXmlPullParser, "value", 0, 0));
      }
    }
    else if (j == 0) {
      paramResources = Keyframe.ofFloat(f);
    } else {
      paramResources = Keyframe.ofInt(f);
    }
    paramInt = TypedArrayUtils.getNamedResourceId(paramTheme, paramXmlPullParser, "interpolator", 1, 0);
    if (paramInt > 0) {
      paramResources.setInterpolator(d.a(paramContext, paramInt));
    }
    paramTheme.recycle();
    return paramResources;
  }
  
  private static ObjectAnimator a(Context paramContext, Resources paramResources, Resources.Theme paramTheme, AttributeSet paramAttributeSet, float paramFloat, XmlPullParser paramXmlPullParser)
  {
    ObjectAnimator localObjectAnimator = new ObjectAnimator();
    a(paramContext, paramResources, paramTheme, paramAttributeSet, localObjectAnimator, paramFloat, paramXmlPullParser);
    return localObjectAnimator;
  }
  
  private static PropertyValuesHolder a(Context paramContext, Resources paramResources, Resources.Theme paramTheme, XmlPullParser paramXmlPullParser, String paramString, int paramInt)
  {
    Object localObject1 = null;
    Object localObject2 = null;
    int i = paramInt;
    for (;;)
    {
      paramInt = paramXmlPullParser.next();
      if ((paramInt == 3) || (paramInt == 1)) {
        break;
      }
      if (paramXmlPullParser.getName().equals("keyframe"))
      {
        paramInt = i;
        if (i == 4) {
          paramInt = a(paramResources, paramTheme, Xml.asAttributeSet(paramXmlPullParser), paramXmlPullParser);
        }
        Keyframe localKeyframe = a(paramContext, paramResources, paramTheme, Xml.asAttributeSet(paramXmlPullParser), paramInt, paramXmlPullParser);
        Object localObject3 = localObject2;
        if (localKeyframe != null)
        {
          localObject3 = localObject2;
          if (localObject2 == null) {
            localObject3 = new ArrayList();
          }
          ((ArrayList)localObject3).add(localKeyframe);
        }
        paramXmlPullParser.next();
        i = paramInt;
        localObject2 = localObject3;
      }
    }
    paramContext = (Context)localObject1;
    if (localObject2 != null)
    {
      int j = ((ArrayList)localObject2).size();
      paramContext = (Context)localObject1;
      if (j > 0)
      {
        int k = 0;
        paramContext = (Keyframe)((ArrayList)localObject2).get(0);
        paramResources = (Keyframe)((ArrayList)localObject2).get(j - 1);
        float f = paramResources.getFraction();
        paramInt = j;
        if (f < 1.0F) {
          if (f < 0.0F)
          {
            paramResources.setFraction(1.0F);
            paramInt = j;
          }
          else
          {
            ((ArrayList)localObject2).add(((ArrayList)localObject2).size(), a(paramResources, 1.0F));
            paramInt = j + 1;
          }
        }
        f = paramContext.getFraction();
        j = paramInt;
        if (f != 0.0F) {
          if (f < 0.0F)
          {
            paramContext.setFraction(0.0F);
            j = paramInt;
          }
          else
          {
            ((ArrayList)localObject2).add(0, a(paramContext, 0.0F));
            j = paramInt + 1;
          }
        }
        paramContext = new Keyframe[j];
        ((ArrayList)localObject2).toArray(paramContext);
        for (paramInt = k; paramInt < j; paramInt++)
        {
          paramResources = paramContext[paramInt];
          if (paramResources.getFraction() < 0.0F) {
            if (paramInt == 0)
            {
              paramResources.setFraction(0.0F);
            }
            else
            {
              int m = j - 1;
              if (paramInt == m)
              {
                paramResources.setFraction(1.0F);
              }
              else
              {
                k = paramInt + 1;
                int n = paramInt;
                while ((k < m) && (paramContext[k].getFraction() < 0.0F))
                {
                  n = k;
                  k++;
                }
                a(paramContext, paramContext[(n + 1)].getFraction() - paramContext[(paramInt - 1)].getFraction(), paramInt, n);
              }
            }
          }
        }
        paramResources = PropertyValuesHolder.ofKeyframe(paramString, paramContext);
        paramContext = paramResources;
        if (i == 3)
        {
          paramResources.setEvaluator(f.a());
          paramContext = paramResources;
        }
      }
    }
    return paramContext;
  }
  
  private static PropertyValuesHolder a(TypedArray paramTypedArray, int paramInt1, int paramInt2, int paramInt3, String paramString)
  {
    Object localObject1 = paramTypedArray.peekValue(paramInt2);
    int i;
    if (localObject1 != null) {
      i = 1;
    } else {
      i = 0;
    }
    int j;
    if (i != 0) {
      j = ((TypedValue)localObject1).type;
    } else {
      j = 0;
    }
    localObject1 = paramTypedArray.peekValue(paramInt3);
    int k;
    if (localObject1 != null) {
      k = 1;
    } else {
      k = 0;
    }
    int m;
    if (k != 0) {
      m = ((TypedValue)localObject1).type;
    } else {
      m = 0;
    }
    int n = paramInt1;
    if (paramInt1 == 4) {
      if (((i != 0) && (a(j))) || ((k != 0) && (a(m)))) {
        n = 3;
      } else {
        n = 0;
      }
    }
    if (n == 0) {
      paramInt1 = 1;
    } else {
      paramInt1 = 0;
    }
    localObject1 = null;
    String str = null;
    Object localObject2;
    if (n == 2)
    {
      localObject2 = paramTypedArray.getString(paramInt2);
      str = paramTypedArray.getString(paramInt3);
      PathParser.PathDataNode[] arrayOfPathDataNode1 = PathParser.createNodesFromPathData((String)localObject2);
      PathParser.PathDataNode[] arrayOfPathDataNode2 = PathParser.createNodesFromPathData(str);
      if (arrayOfPathDataNode1 == null)
      {
        paramTypedArray = (TypedArray)localObject1;
        if (arrayOfPathDataNode2 == null) {}
      }
      else if (arrayOfPathDataNode1 != null)
      {
        paramTypedArray = new a(null);
        if (arrayOfPathDataNode2 != null)
        {
          if (PathParser.canMorph(arrayOfPathDataNode1, arrayOfPathDataNode2))
          {
            paramTypedArray = PropertyValuesHolder.ofObject(paramString, paramTypedArray, new Object[] { arrayOfPathDataNode1, arrayOfPathDataNode2 });
          }
          else
          {
            paramTypedArray = new StringBuilder();
            paramTypedArray.append(" Can't morph from ");
            paramTypedArray.append((String)localObject2);
            paramTypedArray.append(" to ");
            paramTypedArray.append(str);
            throw new InflateException(paramTypedArray.toString());
          }
        }
        else {
          paramTypedArray = PropertyValuesHolder.ofObject(paramString, paramTypedArray, new Object[] { arrayOfPathDataNode1 });
        }
      }
      else
      {
        paramTypedArray = (TypedArray)localObject1;
        if (arrayOfPathDataNode2 != null) {
          paramTypedArray = PropertyValuesHolder.ofObject(paramString, new a(null), new Object[] { arrayOfPathDataNode2 });
        }
      }
    }
    else
    {
      if (n == 3) {
        localObject2 = f.a();
      } else {
        localObject2 = null;
      }
      if (paramInt1 != 0)
      {
        float f1;
        if (i != 0)
        {
          if (j == 5) {
            f1 = paramTypedArray.getDimension(paramInt2, 0.0F);
          } else {
            f1 = paramTypedArray.getFloat(paramInt2, 0.0F);
          }
          if (k != 0)
          {
            float f2;
            if (m == 5) {
              f2 = paramTypedArray.getDimension(paramInt3, 0.0F);
            } else {
              f2 = paramTypedArray.getFloat(paramInt3, 0.0F);
            }
            localObject1 = PropertyValuesHolder.ofFloat(paramString, new float[] { f1, f2 });
          }
          else
          {
            localObject1 = PropertyValuesHolder.ofFloat(paramString, new float[] { f1 });
          }
        }
        else
        {
          if (m == 5) {
            f1 = paramTypedArray.getDimension(paramInt3, 0.0F);
          } else {
            f1 = paramTypedArray.getFloat(paramInt3, 0.0F);
          }
          localObject1 = PropertyValuesHolder.ofFloat(paramString, new float[] { f1 });
        }
      }
      else if (i != 0)
      {
        if (j == 5) {
          paramInt1 = (int)paramTypedArray.getDimension(paramInt2, 0.0F);
        } else if (a(j)) {
          paramInt1 = paramTypedArray.getColor(paramInt2, 0);
        } else {
          paramInt1 = paramTypedArray.getInt(paramInt2, 0);
        }
        if (k != 0)
        {
          if (m == 5) {
            paramInt2 = (int)paramTypedArray.getDimension(paramInt3, 0.0F);
          } else if (a(m)) {
            paramInt2 = paramTypedArray.getColor(paramInt3, 0);
          } else {
            paramInt2 = paramTypedArray.getInt(paramInt3, 0);
          }
          localObject1 = PropertyValuesHolder.ofInt(paramString, new int[] { paramInt1, paramInt2 });
        }
        else
        {
          localObject1 = PropertyValuesHolder.ofInt(paramString, new int[] { paramInt1 });
        }
      }
      else
      {
        localObject1 = str;
        if (k != 0)
        {
          if (m == 5) {
            paramInt1 = (int)paramTypedArray.getDimension(paramInt3, 0.0F);
          } else if (a(m)) {
            paramInt1 = paramTypedArray.getColor(paramInt3, 0);
          } else {
            paramInt1 = paramTypedArray.getInt(paramInt3, 0);
          }
          localObject1 = PropertyValuesHolder.ofInt(paramString, new int[] { paramInt1 });
        }
      }
      paramTypedArray = (TypedArray)localObject1;
      if (localObject1 != null)
      {
        paramTypedArray = (TypedArray)localObject1;
        if (localObject2 != null)
        {
          ((PropertyValuesHolder)localObject1).setEvaluator((TypeEvaluator)localObject2);
          paramTypedArray = (TypedArray)localObject1;
        }
      }
    }
    return paramTypedArray;
  }
  
  private static ValueAnimator a(Context paramContext, Resources paramResources, Resources.Theme paramTheme, AttributeSet paramAttributeSet, ValueAnimator paramValueAnimator, float paramFloat, XmlPullParser paramXmlPullParser)
  {
    TypedArray localTypedArray = TypedArrayUtils.obtainAttributes(paramResources, paramTheme, paramAttributeSet, a.g);
    paramTheme = TypedArrayUtils.obtainAttributes(paramResources, paramTheme, paramAttributeSet, a.k);
    paramResources = paramValueAnimator;
    if (paramValueAnimator == null) {
      paramResources = new ValueAnimator();
    }
    a(paramResources, localTypedArray, paramTheme, paramFloat, paramXmlPullParser);
    int i = TypedArrayUtils.getNamedResourceId(localTypedArray, paramXmlPullParser, "interpolator", 0, 0);
    if (i > 0) {
      paramResources.setInterpolator(d.a(paramContext, i));
    }
    localTypedArray.recycle();
    if (paramTheme != null) {
      paramTheme.recycle();
    }
    return paramResources;
  }
  
  private static void a(ValueAnimator paramValueAnimator, TypedArray paramTypedArray, int paramInt, float paramFloat, XmlPullParser paramXmlPullParser)
  {
    ObjectAnimator localObjectAnimator = (ObjectAnimator)paramValueAnimator;
    paramValueAnimator = TypedArrayUtils.getNamedString(paramTypedArray, paramXmlPullParser, "pathData", 1);
    if (paramValueAnimator != null)
    {
      String str = TypedArrayUtils.getNamedString(paramTypedArray, paramXmlPullParser, "propertyXName", 2);
      paramXmlPullParser = TypedArrayUtils.getNamedString(paramTypedArray, paramXmlPullParser, "propertyYName", 3);
      if ((paramInt == 2) || ((str == null) && (paramXmlPullParser == null)))
      {
        paramValueAnimator = new StringBuilder();
        paramValueAnimator.append(paramTypedArray.getPositionDescription());
        paramValueAnimator.append(" propertyXName or propertyYName is needed for PathData");
        throw new InflateException(paramValueAnimator.toString());
      }
      a(PathParser.createPathFromPathData(paramValueAnimator), localObjectAnimator, paramFloat * 0.5F, str, paramXmlPullParser);
    }
    else
    {
      localObjectAnimator.setPropertyName(TypedArrayUtils.getNamedString(paramTypedArray, paramXmlPullParser, "propertyName", 0));
    }
  }
  
  private static void a(ValueAnimator paramValueAnimator, TypedArray paramTypedArray1, TypedArray paramTypedArray2, float paramFloat, XmlPullParser paramXmlPullParser)
  {
    long l1 = TypedArrayUtils.getNamedInt(paramTypedArray1, paramXmlPullParser, "duration", 1, 300);
    long l2 = TypedArrayUtils.getNamedInt(paramTypedArray1, paramXmlPullParser, "startOffset", 2, 0);
    int i = TypedArrayUtils.getNamedInt(paramTypedArray1, paramXmlPullParser, "valueType", 7, 4);
    int j = i;
    if (TypedArrayUtils.hasAttribute(paramXmlPullParser, "valueFrom"))
    {
      j = i;
      if (TypedArrayUtils.hasAttribute(paramXmlPullParser, "valueTo"))
      {
        int k = i;
        if (i == 4) {
          k = a(paramTypedArray1, 5, 6);
        }
        PropertyValuesHolder localPropertyValuesHolder = a(paramTypedArray1, k, 5, 6, "");
        j = k;
        if (localPropertyValuesHolder != null)
        {
          paramValueAnimator.setValues(new PropertyValuesHolder[] { localPropertyValuesHolder });
          j = k;
        }
      }
    }
    paramValueAnimator.setDuration(l1);
    paramValueAnimator.setStartDelay(l2);
    paramValueAnimator.setRepeatCount(TypedArrayUtils.getNamedInt(paramTypedArray1, paramXmlPullParser, "repeatCount", 3, 0));
    paramValueAnimator.setRepeatMode(TypedArrayUtils.getNamedInt(paramTypedArray1, paramXmlPullParser, "repeatMode", 4, 1));
    if (paramTypedArray2 != null) {
      a(paramValueAnimator, paramTypedArray2, j, paramFloat, paramXmlPullParser);
    }
  }
  
  private static void a(Path paramPath, ObjectAnimator paramObjectAnimator, float paramFloat, String paramString1, String paramString2)
  {
    PathMeasure localPathMeasure = new PathMeasure(paramPath, false);
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(Float.valueOf(0.0F));
    float f1 = 0.0F;
    float f2;
    do
    {
      f2 = f1 + localPathMeasure.getLength();
      localArrayList.add(Float.valueOf(f2));
      f1 = f2;
    } while (localPathMeasure.nextContour());
    paramPath = new PathMeasure(paramPath, false);
    int i = Math.min(100, (int)(f2 / paramFloat) + 1);
    float[] arrayOfFloat1 = new float[i];
    float[] arrayOfFloat2 = new float[i];
    float[] arrayOfFloat3 = new float[2];
    f2 /= (i - 1);
    int j = 0;
    paramFloat = 0.0F;
    int n;
    for (int k = 0;; k = n)
    {
      localPathMeasure = null;
      if (j >= i) {
        break;
      }
      paramPath.getPosTan(paramFloat, arrayOfFloat3, null);
      paramPath.getPosTan(paramFloat, arrayOfFloat3, null);
      arrayOfFloat1[j] = arrayOfFloat3[0];
      arrayOfFloat2[j] = arrayOfFloat3[1];
      f1 = paramFloat + f2;
      int m = k + 1;
      paramFloat = f1;
      n = k;
      if (m < localArrayList.size())
      {
        paramFloat = f1;
        n = k;
        if (f1 > ((Float)localArrayList.get(m)).floatValue())
        {
          paramFloat = f1 - ((Float)localArrayList.get(m)).floatValue();
          paramPath.nextContour();
          n = m;
        }
      }
      j++;
    }
    if (paramString1 != null) {
      paramPath = PropertyValuesHolder.ofFloat(paramString1, arrayOfFloat1);
    } else {
      paramPath = null;
    }
    paramString1 = localPathMeasure;
    if (paramString2 != null) {
      paramString1 = PropertyValuesHolder.ofFloat(paramString2, arrayOfFloat2);
    }
    if (paramPath == null) {
      paramObjectAnimator.setValues(new PropertyValuesHolder[] { paramString1 });
    } else if (paramString1 == null) {
      paramObjectAnimator.setValues(new PropertyValuesHolder[] { paramPath });
    } else {
      paramObjectAnimator.setValues(new PropertyValuesHolder[] { paramPath, paramString1 });
    }
  }
  
  private static void a(Keyframe[] paramArrayOfKeyframe, float paramFloat, int paramInt1, int paramInt2)
  {
    paramFloat /= (paramInt2 - paramInt1 + 2);
    while (paramInt1 <= paramInt2)
    {
      paramArrayOfKeyframe[paramInt1].setFraction(paramArrayOfKeyframe[(paramInt1 - 1)].getFraction() + paramFloat);
      paramInt1++;
    }
  }
  
  private static boolean a(int paramInt)
  {
    boolean bool;
    if ((paramInt >= 28) && (paramInt <= 31)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private static PropertyValuesHolder[] a(Context paramContext, Resources paramResources, Resources.Theme paramTheme, XmlPullParser paramXmlPullParser, AttributeSet paramAttributeSet)
  {
    Object localObject1 = null;
    Object localObject2 = null;
    int i;
    int j;
    for (;;)
    {
      i = paramXmlPullParser.getEventType();
      j = 0;
      if ((i == 3) || (i == 1)) {
        break;
      }
      if (i != 2)
      {
        paramXmlPullParser.next();
      }
      else
      {
        if (paramXmlPullParser.getName().equals("propertyValuesHolder"))
        {
          TypedArray localTypedArray = TypedArrayUtils.obtainAttributes(paramResources, paramTheme, paramAttributeSet, a.i);
          String str = TypedArrayUtils.getNamedString(localTypedArray, paramXmlPullParser, "propertyName", 3);
          j = TypedArrayUtils.getNamedInt(localTypedArray, paramXmlPullParser, "valueType", 2, 4);
          Object localObject3 = a(paramContext, paramResources, paramTheme, paramXmlPullParser, str, j);
          Object localObject4 = localObject3;
          if (localObject3 == null) {
            localObject4 = a(localTypedArray, j, 0, 1, str);
          }
          localObject3 = localObject2;
          if (localObject4 != null)
          {
            localObject3 = localObject2;
            if (localObject2 == null) {
              localObject3 = new ArrayList();
            }
            ((ArrayList)localObject3).add(localObject4);
          }
          localTypedArray.recycle();
          localObject2 = localObject3;
        }
        paramXmlPullParser.next();
      }
    }
    paramContext = (Context)localObject1;
    if (localObject2 != null)
    {
      i = ((ArrayList)localObject2).size();
      paramResources = new PropertyValuesHolder[i];
      for (;;)
      {
        paramContext = paramResources;
        if (j >= i) {
          break;
        }
        paramResources[j] = ((PropertyValuesHolder)((ArrayList)localObject2).get(j));
        j++;
      }
    }
    return paramContext;
  }
  
  private static class a
    implements TypeEvaluator<PathParser.PathDataNode[]>
  {
    private PathParser.PathDataNode[] a;
    
    public PathParser.PathDataNode[] a(float paramFloat, PathParser.PathDataNode[] paramArrayOfPathDataNode1, PathParser.PathDataNode[] paramArrayOfPathDataNode2)
    {
      if (PathParser.canMorph(paramArrayOfPathDataNode1, paramArrayOfPathDataNode2))
      {
        PathParser.PathDataNode[] arrayOfPathDataNode = this.a;
        if ((arrayOfPathDataNode == null) || (!PathParser.canMorph(arrayOfPathDataNode, paramArrayOfPathDataNode1))) {
          this.a = PathParser.deepCopyNodes(paramArrayOfPathDataNode1);
        }
        for (int i = 0; i < paramArrayOfPathDataNode1.length; i++) {
          this.a[i].interpolatePathDataNode(paramArrayOfPathDataNode1[i], paramArrayOfPathDataNode2[i], paramFloat);
        }
        return this.a;
      }
      throw new IllegalArgumentException("Can't interpolate between two incompatible pathData");
    }
  }
}


/* Location:              ~/android/support/b/a/e.class
 *
 * Reversed by:           J
 */