package android.support.b.a;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v4.graphics.PathParser;
import android.util.AttributeSet;
import android.view.InflateException;
import android.view.animation.Interpolator;
import org.xmlpull.v1.XmlPullParser;

public class g
  implements Interpolator
{
  private float[] a;
  private float[] b;
  
  public g(Context paramContext, AttributeSet paramAttributeSet, XmlPullParser paramXmlPullParser)
  {
    this(paramContext.getResources(), paramContext.getTheme(), paramAttributeSet, paramXmlPullParser);
  }
  
  public g(Resources paramResources, Resources.Theme paramTheme, AttributeSet paramAttributeSet, XmlPullParser paramXmlPullParser)
  {
    paramResources = TypedArrayUtils.obtainAttributes(paramResources, paramTheme, paramAttributeSet, a.l);
    a(paramResources, paramXmlPullParser);
    paramResources.recycle();
  }
  
  private void a(float paramFloat1, float paramFloat2)
  {
    Path localPath = new Path();
    localPath.moveTo(0.0F, 0.0F);
    localPath.quadTo(paramFloat1, paramFloat2, 1.0F, 1.0F);
    a(localPath);
  }
  
  private void a(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    Path localPath = new Path();
    localPath.moveTo(0.0F, 0.0F);
    localPath.cubicTo(paramFloat1, paramFloat2, paramFloat3, paramFloat4, 1.0F, 1.0F);
    a(localPath);
  }
  
  private void a(TypedArray paramTypedArray, XmlPullParser paramXmlPullParser)
  {
    if (TypedArrayUtils.hasAttribute(paramXmlPullParser, "pathData"))
    {
      paramTypedArray = TypedArrayUtils.getNamedString(paramTypedArray, paramXmlPullParser, "pathData", 4);
      paramXmlPullParser = PathParser.createPathFromPathData(paramTypedArray);
      if (paramXmlPullParser != null)
      {
        a(paramXmlPullParser);
      }
      else
      {
        paramXmlPullParser = new StringBuilder();
        paramXmlPullParser.append("The path is null, which is created from ");
        paramXmlPullParser.append(paramTypedArray);
        throw new InflateException(paramXmlPullParser.toString());
      }
    }
    else
    {
      if (!TypedArrayUtils.hasAttribute(paramXmlPullParser, "controlX1")) {
        break label187;
      }
      if (!TypedArrayUtils.hasAttribute(paramXmlPullParser, "controlY1")) {
        break label177;
      }
      float f1 = TypedArrayUtils.getNamedFloat(paramTypedArray, paramXmlPullParser, "controlX1", 0, 0.0F);
      float f2 = TypedArrayUtils.getNamedFloat(paramTypedArray, paramXmlPullParser, "controlY1", 1, 0.0F);
      boolean bool = TypedArrayUtils.hasAttribute(paramXmlPullParser, "controlX2");
      if (bool != TypedArrayUtils.hasAttribute(paramXmlPullParser, "controlY2")) {
        break label167;
      }
      if (!bool) {
        a(f1, f2);
      } else {
        a(f1, f2, TypedArrayUtils.getNamedFloat(paramTypedArray, paramXmlPullParser, "controlX2", 2, 0.0F), TypedArrayUtils.getNamedFloat(paramTypedArray, paramXmlPullParser, "controlY2", 3, 0.0F));
      }
    }
    return;
    label167:
    throw new InflateException("pathInterpolator requires both controlX2 and controlY2 for cubic Beziers.");
    label177:
    throw new InflateException("pathInterpolator requires the controlY1 attribute");
    label187:
    throw new InflateException("pathInterpolator requires the controlX1 attribute");
  }
  
  private void a(Path paramPath)
  {
    int i = 0;
    paramPath = new PathMeasure(paramPath, false);
    float f1 = paramPath.getLength();
    int j = Math.min(3000, (int)(f1 / 0.002F) + 1);
    if (j > 0)
    {
      this.a = new float[j];
      this.b = new float[j];
      float[] arrayOfFloat = new float[2];
      for (int k = 0; k < j; k++)
      {
        paramPath.getPosTan(k * f1 / (j - 1), arrayOfFloat, null);
        this.a[k] = arrayOfFloat[0];
        this.b[k] = arrayOfFloat[1];
      }
      if ((Math.abs(this.a[0]) <= 1.0E-5D) && (Math.abs(this.b[0]) <= 1.0E-5D))
      {
        arrayOfFloat = this.a;
        k = j - 1;
        if ((Math.abs(arrayOfFloat[k] - 1.0F) <= 1.0E-5D) && (Math.abs(this.b[k] - 1.0F) <= 1.0E-5D))
        {
          k = 0;
          f1 = 0.0F;
          while (i < j)
          {
            arrayOfFloat = this.a;
            float f2 = arrayOfFloat[k];
            if (f2 >= f1)
            {
              arrayOfFloat[i] = f2;
              i++;
              f1 = f2;
              k++;
            }
            else
            {
              paramPath = new StringBuilder();
              paramPath.append("The Path cannot loop back on itself, x :");
              paramPath.append(f2);
              throw new IllegalArgumentException(paramPath.toString());
            }
          }
          if (!paramPath.nextContour()) {
            return;
          }
          throw new IllegalArgumentException("The Path should be continuous, can't have 2+ contours");
        }
      }
      paramPath = new StringBuilder();
      paramPath.append("The Path must start at (0,0) and end at (1,1) start: ");
      paramPath.append(this.a[0]);
      paramPath.append(",");
      paramPath.append(this.b[0]);
      paramPath.append(" end:");
      arrayOfFloat = this.a;
      k = j - 1;
      paramPath.append(arrayOfFloat[k]);
      paramPath.append(",");
      paramPath.append(this.b[k]);
      throw new IllegalArgumentException(paramPath.toString());
    }
    paramPath = new StringBuilder();
    paramPath.append("The Path has a invalid length ");
    paramPath.append(f1);
    throw new IllegalArgumentException(paramPath.toString());
  }
  
  public float getInterpolation(float paramFloat)
  {
    if (paramFloat <= 0.0F) {
      return 0.0F;
    }
    if (paramFloat >= 1.0F) {
      return 1.0F;
    }
    int i = 0;
    int j = this.a.length - 1;
    while (j - i > 1)
    {
      int k = (i + j) / 2;
      if (paramFloat < this.a[k]) {
        j = k;
      } else {
        i = k;
      }
    }
    float[] arrayOfFloat = this.a;
    float f = arrayOfFloat[j] - arrayOfFloat[i];
    if (f == 0.0F) {
      return this.b[i];
    }
    f = (paramFloat - arrayOfFloat[i]) / f;
    arrayOfFloat = this.b;
    paramFloat = arrayOfFloat[i];
    return paramFloat + f * (arrayOfFloat[j] - paramFloat);
  }
}


/* Location:              ~/android/support/b/a/g.class
 *
 * Reversed by:           J
 */