package android.support.b.a;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.FillType;
import android.graphics.PathMeasure;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.ConstantState;
import android.graphics.drawable.VectorDrawable;
import android.os.Build.VERSION;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v4.graphics.PathParser;
import android.support.v4.graphics.PathParser.PathDataNode;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.util.ArrayMap;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class i
  extends h
{
  static final PorterDuff.Mode a = PorterDuff.Mode.SRC_IN;
  private f c;
  private PorterDuffColorFilter d;
  private ColorFilter e;
  private boolean f;
  private boolean g = true;
  private Drawable.ConstantState h;
  private final float[] i = new float[9];
  private final Matrix j = new Matrix();
  private final Rect k = new Rect();
  
  i()
  {
    this.c = new f();
  }
  
  i(f paramf)
  {
    this.c = paramf;
    this.d = a(this.d, paramf.c, paramf.d);
  }
  
  static int a(int paramInt, float paramFloat)
  {
    return paramInt & 0xFFFFFF | (int)(Color.alpha(paramInt) * paramFloat) << 24;
  }
  
  private static PorterDuff.Mode a(int paramInt, PorterDuff.Mode paramMode)
  {
    if (paramInt != 3)
    {
      if (paramInt != 5)
      {
        if (paramInt != 9)
        {
          switch (paramInt)
          {
          default: 
            return paramMode;
          case 16: 
            if (Build.VERSION.SDK_INT >= 11) {
              return PorterDuff.Mode.ADD;
            }
            return paramMode;
          case 15: 
            return PorterDuff.Mode.SCREEN;
          }
          return PorterDuff.Mode.MULTIPLY;
        }
        return PorterDuff.Mode.SRC_ATOP;
      }
      return PorterDuff.Mode.SRC_IN;
    }
    return PorterDuff.Mode.SRC_OVER;
  }
  
  public static i a(Resources paramResources, int paramInt, Resources.Theme paramTheme)
  {
    Object localObject;
    if (Build.VERSION.SDK_INT >= 24)
    {
      localObject = new i();
      ((i)localObject).b = ResourcesCompat.getDrawable(paramResources, paramInt, paramTheme);
      ((i)localObject).h = new g(((i)localObject).b.getConstantState());
      return (i)localObject;
    }
    try
    {
      XmlResourceParser localXmlResourceParser = paramResources.getXml(paramInt);
      localObject = Xml.asAttributeSet(localXmlResourceParser);
      do
      {
        paramInt = localXmlResourceParser.next();
      } while ((paramInt != 2) && (paramInt != 1));
      if (paramInt == 2) {
        return a(paramResources, localXmlResourceParser, (AttributeSet)localObject, paramTheme);
      }
      paramResources = new org/xmlpull/v1/XmlPullParserException;
      paramResources.<init>("No start tag found");
      throw paramResources;
    }
    catch (IOException paramResources)
    {
      Log.e("VectorDrawableCompat", "parser error", paramResources);
    }
    catch (XmlPullParserException paramResources)
    {
      Log.e("VectorDrawableCompat", "parser error", paramResources);
    }
    return null;
  }
  
  public static i a(Resources paramResources, XmlPullParser paramXmlPullParser, AttributeSet paramAttributeSet, Resources.Theme paramTheme)
  {
    i locali = new i();
    locali.inflate(paramResources, paramXmlPullParser, paramAttributeSet, paramTheme);
    return locali;
  }
  
  private void a(TypedArray paramTypedArray, XmlPullParser paramXmlPullParser)
  {
    f localf = this.c;
    e locale = localf.b;
    localf.d = a(TypedArrayUtils.getNamedInt(paramTypedArray, paramXmlPullParser, "tintMode", 6, -1), PorterDuff.Mode.SRC_IN);
    ColorStateList localColorStateList = paramTypedArray.getColorStateList(1);
    if (localColorStateList != null) {
      localf.c = localColorStateList;
    }
    localf.e = TypedArrayUtils.getNamedBoolean(paramTypedArray, paramXmlPullParser, "autoMirrored", 5, localf.e);
    locale.d = TypedArrayUtils.getNamedFloat(paramTypedArray, paramXmlPullParser, "viewportWidth", 7, locale.d);
    locale.e = TypedArrayUtils.getNamedFloat(paramTypedArray, paramXmlPullParser, "viewportHeight", 8, locale.e);
    if (locale.d > 0.0F)
    {
      if (locale.e > 0.0F)
      {
        locale.b = paramTypedArray.getDimension(3, locale.b);
        locale.c = paramTypedArray.getDimension(2, locale.c);
        if (locale.b > 0.0F)
        {
          if (locale.c > 0.0F)
          {
            locale.setAlpha(TypedArrayUtils.getNamedFloat(paramTypedArray, paramXmlPullParser, "alpha", 4, locale.getAlpha()));
            paramTypedArray = paramTypedArray.getString(0);
            if (paramTypedArray != null)
            {
              locale.g = paramTypedArray;
              locale.h.put(paramTypedArray, locale);
            }
            return;
          }
          paramXmlPullParser = new StringBuilder();
          paramXmlPullParser.append(paramTypedArray.getPositionDescription());
          paramXmlPullParser.append("<vector> tag requires height > 0");
          throw new XmlPullParserException(paramXmlPullParser.toString());
        }
        paramXmlPullParser = new StringBuilder();
        paramXmlPullParser.append(paramTypedArray.getPositionDescription());
        paramXmlPullParser.append("<vector> tag requires width > 0");
        throw new XmlPullParserException(paramXmlPullParser.toString());
      }
      paramXmlPullParser = new StringBuilder();
      paramXmlPullParser.append(paramTypedArray.getPositionDescription());
      paramXmlPullParser.append("<vector> tag requires viewportHeight > 0");
      throw new XmlPullParserException(paramXmlPullParser.toString());
    }
    paramXmlPullParser = new StringBuilder();
    paramXmlPullParser.append(paramTypedArray.getPositionDescription());
    paramXmlPullParser.append("<vector> tag requires viewportWidth > 0");
    throw new XmlPullParserException(paramXmlPullParser.toString());
  }
  
  private boolean a()
  {
    int m = Build.VERSION.SDK_INT;
    boolean bool1 = false;
    if (m >= 17)
    {
      boolean bool2 = bool1;
      if (isAutoMirrored())
      {
        bool2 = bool1;
        if (DrawableCompat.getLayoutDirection(this) == 1) {
          bool2 = true;
        }
      }
      return bool2;
    }
    return false;
  }
  
  private void b(Resources paramResources, XmlPullParser paramXmlPullParser, AttributeSet paramAttributeSet, Resources.Theme paramTheme)
  {
    f localf = this.c;
    e locale = localf.b;
    Stack localStack = new Stack();
    localStack.push(locale.a);
    int m = paramXmlPullParser.getEventType();
    int n = paramXmlPullParser.getDepth();
    int i2;
    for (int i1 = 1; (m != 1) && ((paramXmlPullParser.getDepth() >= n + 1) || (m != 3)); i1 = i2)
    {
      if (m == 2)
      {
        Object localObject = paramXmlPullParser.getName();
        c localc = (c)localStack.peek();
        if ("path".equals(localObject))
        {
          localObject = new b();
          ((b)localObject).a(paramResources, paramAttributeSet, paramTheme, paramXmlPullParser);
          localc.a.add(localObject);
          if (((b)localObject).getPathName() != null) {
            locale.h.put(((b)localObject).getPathName(), localObject);
          }
          i2 = 0;
          i1 = localf.a;
          localf.a = (((b)localObject).o | i1);
        }
        else if ("clip-path".equals(localObject))
        {
          localObject = new a();
          ((a)localObject).a(paramResources, paramAttributeSet, paramTheme, paramXmlPullParser);
          localc.a.add(localObject);
          if (((a)localObject).getPathName() != null) {
            locale.h.put(((a)localObject).getPathName(), localObject);
          }
          i2 = localf.a;
          localf.a = (((a)localObject).o | i2);
          i2 = i1;
        }
        else
        {
          i2 = i1;
          if ("group".equals(localObject))
          {
            localObject = new c();
            ((c)localObject).a(paramResources, paramAttributeSet, paramTheme, paramXmlPullParser);
            localc.a.add(localObject);
            localStack.push(localObject);
            if (((c)localObject).getGroupName() != null) {
              locale.h.put(((c)localObject).getGroupName(), localObject);
            }
            i2 = localf.a;
            localf.a = (((c)localObject).c | i2);
            i2 = i1;
          }
        }
      }
      else
      {
        i2 = i1;
        if (m == 3)
        {
          i2 = i1;
          if ("group".equals(paramXmlPullParser.getName()))
          {
            localStack.pop();
            i2 = i1;
          }
        }
      }
      m = paramXmlPullParser.next();
    }
    if (i1 != 0)
    {
      paramResources = new StringBuffer();
      if (paramResources.length() > 0) {
        paramResources.append(" or ");
      }
      paramResources.append("path");
      paramXmlPullParser = new StringBuilder();
      paramXmlPullParser.append("no ");
      paramXmlPullParser.append(paramResources);
      paramXmlPullParser.append(" defined");
      throw new XmlPullParserException(paramXmlPullParser.toString());
    }
  }
  
  PorterDuffColorFilter a(PorterDuffColorFilter paramPorterDuffColorFilter, ColorStateList paramColorStateList, PorterDuff.Mode paramMode)
  {
    if ((paramColorStateList != null) && (paramMode != null)) {
      return new PorterDuffColorFilter(paramColorStateList.getColorForState(getState(), 0), paramMode);
    }
    return null;
  }
  
  Object a(String paramString)
  {
    return this.c.b.h.get(paramString);
  }
  
  void a(boolean paramBoolean)
  {
    this.g = paramBoolean;
  }
  
  public boolean canApplyTheme()
  {
    if (this.b != null) {
      DrawableCompat.canApplyTheme(this.b);
    }
    return false;
  }
  
  public void draw(Canvas paramCanvas)
  {
    if (this.b != null)
    {
      this.b.draw(paramCanvas);
      return;
    }
    copyBounds(this.k);
    if ((this.k.width() > 0) && (this.k.height() > 0))
    {
      ColorFilter localColorFilter = this.e;
      Object localObject = localColorFilter;
      if (localColorFilter == null) {
        localObject = this.d;
      }
      paramCanvas.getMatrix(this.j);
      this.j.getValues(this.i);
      float f1 = Math.abs(this.i[0]);
      float f2 = Math.abs(this.i[4]);
      float f3 = Math.abs(this.i[1]);
      float f4 = Math.abs(this.i[3]);
      if ((f3 != 0.0F) || (f4 != 0.0F))
      {
        f1 = 1.0F;
        f2 = 1.0F;
      }
      int m = (int)(this.k.width() * f1);
      int n = (int)(this.k.height() * f2);
      m = Math.min(2048, m);
      n = Math.min(2048, n);
      if ((m > 0) && (n > 0))
      {
        int i1 = paramCanvas.save();
        paramCanvas.translate(this.k.left, this.k.top);
        if (a())
        {
          paramCanvas.translate(this.k.width(), 0.0F);
          paramCanvas.scale(-1.0F, 1.0F);
        }
        this.k.offsetTo(0, 0);
        this.c.b(m, n);
        if (!this.g)
        {
          this.c.a(m, n);
        }
        else if (!this.c.b())
        {
          this.c.a(m, n);
          this.c.c();
        }
        this.c.a(paramCanvas, (ColorFilter)localObject, this.k);
        paramCanvas.restoreToCount(i1);
        return;
      }
      return;
    }
  }
  
  public int getAlpha()
  {
    if (this.b != null) {
      return DrawableCompat.getAlpha(this.b);
    }
    return this.c.b.getRootAlpha();
  }
  
  public int getChangingConfigurations()
  {
    if (this.b != null) {
      return this.b.getChangingConfigurations();
    }
    return super.getChangingConfigurations() | this.c.getChangingConfigurations();
  }
  
  public Drawable.ConstantState getConstantState()
  {
    if ((this.b != null) && (Build.VERSION.SDK_INT >= 24)) {
      return new g(this.b.getConstantState());
    }
    this.c.a = getChangingConfigurations();
    return this.c;
  }
  
  public int getIntrinsicHeight()
  {
    if (this.b != null) {
      return this.b.getIntrinsicHeight();
    }
    return (int)this.c.b.c;
  }
  
  public int getIntrinsicWidth()
  {
    if (this.b != null) {
      return this.b.getIntrinsicWidth();
    }
    return (int)this.c.b.b;
  }
  
  public int getOpacity()
  {
    if (this.b != null) {
      return this.b.getOpacity();
    }
    return -3;
  }
  
  public void inflate(Resources paramResources, XmlPullParser paramXmlPullParser, AttributeSet paramAttributeSet)
  {
    if (this.b != null)
    {
      this.b.inflate(paramResources, paramXmlPullParser, paramAttributeSet);
      return;
    }
    inflate(paramResources, paramXmlPullParser, paramAttributeSet, null);
  }
  
  public void inflate(Resources paramResources, XmlPullParser paramXmlPullParser, AttributeSet paramAttributeSet, Resources.Theme paramTheme)
  {
    if (this.b != null)
    {
      DrawableCompat.inflate(this.b, paramResources, paramXmlPullParser, paramAttributeSet, paramTheme);
      return;
    }
    f localf = this.c;
    localf.b = new e();
    TypedArray localTypedArray = TypedArrayUtils.obtainAttributes(paramResources, paramTheme, paramAttributeSet, a.a);
    a(localTypedArray, paramXmlPullParser);
    localTypedArray.recycle();
    localf.a = getChangingConfigurations();
    localf.k = true;
    b(paramResources, paramXmlPullParser, paramAttributeSet, paramTheme);
    this.d = a(this.d, localf.c, localf.d);
  }
  
  public void invalidateSelf()
  {
    if (this.b != null)
    {
      this.b.invalidateSelf();
      return;
    }
    super.invalidateSelf();
  }
  
  public boolean isAutoMirrored()
  {
    if (this.b != null) {
      return DrawableCompat.isAutoMirrored(this.b);
    }
    return this.c.e;
  }
  
  public boolean isStateful()
  {
    if (this.b != null) {
      return this.b.isStateful();
    }
    if (!super.isStateful())
    {
      f localf = this.c;
      if ((localf == null) || (localf.c == null) || (!this.c.c.isStateful())) {
        return false;
      }
    }
    boolean bool = true;
    return bool;
  }
  
  public Drawable mutate()
  {
    if (this.b != null)
    {
      this.b.mutate();
      return this;
    }
    if ((!this.f) && (super.mutate() == this))
    {
      this.c = new f(this.c);
      this.f = true;
    }
    return this;
  }
  
  protected void onBoundsChange(Rect paramRect)
  {
    if (this.b != null) {
      this.b.setBounds(paramRect);
    }
  }
  
  protected boolean onStateChange(int[] paramArrayOfInt)
  {
    if (this.b != null) {
      return this.b.setState(paramArrayOfInt);
    }
    paramArrayOfInt = this.c;
    if ((paramArrayOfInt.c != null) && (paramArrayOfInt.d != null))
    {
      this.d = a(this.d, paramArrayOfInt.c, paramArrayOfInt.d);
      invalidateSelf();
      return true;
    }
    return false;
  }
  
  public void scheduleSelf(Runnable paramRunnable, long paramLong)
  {
    if (this.b != null)
    {
      this.b.scheduleSelf(paramRunnable, paramLong);
      return;
    }
    super.scheduleSelf(paramRunnable, paramLong);
  }
  
  public void setAlpha(int paramInt)
  {
    if (this.b != null)
    {
      this.b.setAlpha(paramInt);
      return;
    }
    if (this.c.b.getRootAlpha() != paramInt)
    {
      this.c.b.setRootAlpha(paramInt);
      invalidateSelf();
    }
  }
  
  public void setAutoMirrored(boolean paramBoolean)
  {
    if (this.b != null)
    {
      DrawableCompat.setAutoMirrored(this.b, paramBoolean);
      return;
    }
    this.c.e = paramBoolean;
  }
  
  public void setColorFilter(ColorFilter paramColorFilter)
  {
    if (this.b != null)
    {
      this.b.setColorFilter(paramColorFilter);
      return;
    }
    this.e = paramColorFilter;
    invalidateSelf();
  }
  
  public void setTint(int paramInt)
  {
    if (this.b != null)
    {
      DrawableCompat.setTint(this.b, paramInt);
      return;
    }
    setTintList(ColorStateList.valueOf(paramInt));
  }
  
  public void setTintList(ColorStateList paramColorStateList)
  {
    if (this.b != null)
    {
      DrawableCompat.setTintList(this.b, paramColorStateList);
      return;
    }
    f localf = this.c;
    if (localf.c != paramColorStateList)
    {
      localf.c = paramColorStateList;
      this.d = a(this.d, paramColorStateList, localf.d);
      invalidateSelf();
    }
  }
  
  public void setTintMode(PorterDuff.Mode paramMode)
  {
    if (this.b != null)
    {
      DrawableCompat.setTintMode(this.b, paramMode);
      return;
    }
    f localf = this.c;
    if (localf.d != paramMode)
    {
      localf.d = paramMode;
      this.d = a(this.d, localf.c, paramMode);
      invalidateSelf();
    }
  }
  
  public boolean setVisible(boolean paramBoolean1, boolean paramBoolean2)
  {
    if (this.b != null) {
      return this.b.setVisible(paramBoolean1, paramBoolean2);
    }
    return super.setVisible(paramBoolean1, paramBoolean2);
  }
  
  public void unscheduleSelf(Runnable paramRunnable)
  {
    if (this.b != null)
    {
      this.b.unscheduleSelf(paramRunnable);
      return;
    }
    super.unscheduleSelf(paramRunnable);
  }
  
  private static class a
    extends i.d
  {
    public a() {}
    
    public a(a parama)
    {
      super();
    }
    
    private void a(TypedArray paramTypedArray)
    {
      String str = paramTypedArray.getString(0);
      if (str != null) {
        this.n = str;
      }
      paramTypedArray = paramTypedArray.getString(1);
      if (paramTypedArray != null) {
        this.m = PathParser.createNodesFromPathData(paramTypedArray);
      }
    }
    
    public void a(Resources paramResources, AttributeSet paramAttributeSet, Resources.Theme paramTheme, XmlPullParser paramXmlPullParser)
    {
      if (!TypedArrayUtils.hasAttribute(paramXmlPullParser, "pathData")) {
        return;
      }
      paramResources = TypedArrayUtils.obtainAttributes(paramResources, paramTheme, paramAttributeSet, a.d);
      a(paramResources);
      paramResources.recycle();
    }
    
    public boolean a()
    {
      return true;
    }
  }
  
  private static class b
    extends i.d
  {
    int a = 0;
    float b = 0.0F;
    int c = 0;
    float d = 1.0F;
    int e = 0;
    float f = 1.0F;
    float g = 0.0F;
    float h = 1.0F;
    float i = 0.0F;
    Paint.Cap j = Paint.Cap.BUTT;
    Paint.Join k = Paint.Join.MITER;
    float l = 4.0F;
    private int[] p;
    
    public b() {}
    
    public b(b paramb)
    {
      super();
      this.p = paramb.p;
      this.a = paramb.a;
      this.b = paramb.b;
      this.d = paramb.d;
      this.c = paramb.c;
      this.e = paramb.e;
      this.f = paramb.f;
      this.g = paramb.g;
      this.h = paramb.h;
      this.i = paramb.i;
      this.j = paramb.j;
      this.k = paramb.k;
      this.l = paramb.l;
    }
    
    private Paint.Cap a(int paramInt, Paint.Cap paramCap)
    {
      switch (paramInt)
      {
      default: 
        return paramCap;
      case 2: 
        return Paint.Cap.SQUARE;
      case 1: 
        return Paint.Cap.ROUND;
      }
      return Paint.Cap.BUTT;
    }
    
    private Paint.Join a(int paramInt, Paint.Join paramJoin)
    {
      switch (paramInt)
      {
      default: 
        return paramJoin;
      case 2: 
        return Paint.Join.BEVEL;
      case 1: 
        return Paint.Join.ROUND;
      }
      return Paint.Join.MITER;
    }
    
    private void a(TypedArray paramTypedArray, XmlPullParser paramXmlPullParser)
    {
      this.p = null;
      if (!TypedArrayUtils.hasAttribute(paramXmlPullParser, "pathData")) {
        return;
      }
      String str = paramTypedArray.getString(0);
      if (str != null) {
        this.n = str;
      }
      str = paramTypedArray.getString(2);
      if (str != null) {
        this.m = PathParser.createNodesFromPathData(str);
      }
      this.c = TypedArrayUtils.getNamedColor(paramTypedArray, paramXmlPullParser, "fillColor", 1, this.c);
      this.f = TypedArrayUtils.getNamedFloat(paramTypedArray, paramXmlPullParser, "fillAlpha", 12, this.f);
      this.j = a(TypedArrayUtils.getNamedInt(paramTypedArray, paramXmlPullParser, "strokeLineCap", 8, -1), this.j);
      this.k = a(TypedArrayUtils.getNamedInt(paramTypedArray, paramXmlPullParser, "strokeLineJoin", 9, -1), this.k);
      this.l = TypedArrayUtils.getNamedFloat(paramTypedArray, paramXmlPullParser, "strokeMiterLimit", 10, this.l);
      this.a = TypedArrayUtils.getNamedColor(paramTypedArray, paramXmlPullParser, "strokeColor", 3, this.a);
      this.d = TypedArrayUtils.getNamedFloat(paramTypedArray, paramXmlPullParser, "strokeAlpha", 11, this.d);
      this.b = TypedArrayUtils.getNamedFloat(paramTypedArray, paramXmlPullParser, "strokeWidth", 4, this.b);
      this.h = TypedArrayUtils.getNamedFloat(paramTypedArray, paramXmlPullParser, "trimPathEnd", 6, this.h);
      this.i = TypedArrayUtils.getNamedFloat(paramTypedArray, paramXmlPullParser, "trimPathOffset", 7, this.i);
      this.g = TypedArrayUtils.getNamedFloat(paramTypedArray, paramXmlPullParser, "trimPathStart", 5, this.g);
      this.e = TypedArrayUtils.getNamedInt(paramTypedArray, paramXmlPullParser, "fillType", 13, this.e);
    }
    
    public void a(Resources paramResources, AttributeSet paramAttributeSet, Resources.Theme paramTheme, XmlPullParser paramXmlPullParser)
    {
      paramResources = TypedArrayUtils.obtainAttributes(paramResources, paramTheme, paramAttributeSet, a.c);
      a(paramResources, paramXmlPullParser);
      paramResources.recycle();
    }
    
    float getFillAlpha()
    {
      return this.f;
    }
    
    int getFillColor()
    {
      return this.c;
    }
    
    float getStrokeAlpha()
    {
      return this.d;
    }
    
    int getStrokeColor()
    {
      return this.a;
    }
    
    float getStrokeWidth()
    {
      return this.b;
    }
    
    float getTrimPathEnd()
    {
      return this.h;
    }
    
    float getTrimPathOffset()
    {
      return this.i;
    }
    
    float getTrimPathStart()
    {
      return this.g;
    }
    
    void setFillAlpha(float paramFloat)
    {
      this.f = paramFloat;
    }
    
    void setFillColor(int paramInt)
    {
      this.c = paramInt;
    }
    
    void setStrokeAlpha(float paramFloat)
    {
      this.d = paramFloat;
    }
    
    void setStrokeColor(int paramInt)
    {
      this.a = paramInt;
    }
    
    void setStrokeWidth(float paramFloat)
    {
      this.b = paramFloat;
    }
    
    void setTrimPathEnd(float paramFloat)
    {
      this.h = paramFloat;
    }
    
    void setTrimPathOffset(float paramFloat)
    {
      this.i = paramFloat;
    }
    
    void setTrimPathStart(float paramFloat)
    {
      this.g = paramFloat;
    }
  }
  
  private static class c
  {
    final ArrayList<Object> a = new ArrayList();
    float b = 0.0F;
    int c;
    private final Matrix d = new Matrix();
    private float e = 0.0F;
    private float f = 0.0F;
    private float g = 1.0F;
    private float h = 1.0F;
    private float i = 0.0F;
    private float j = 0.0F;
    private final Matrix k = new Matrix();
    private int[] l;
    private String m = null;
    
    public c() {}
    
    public c(c paramc, ArrayMap<String, Object> paramArrayMap)
    {
      this.b = paramc.b;
      this.e = paramc.e;
      this.f = paramc.f;
      this.g = paramc.g;
      this.h = paramc.h;
      this.i = paramc.i;
      this.j = paramc.j;
      this.l = paramc.l;
      this.m = paramc.m;
      this.c = paramc.c;
      Object localObject = this.m;
      if (localObject != null) {
        paramArrayMap.put(localObject, this);
      }
      this.k.set(paramc.k);
      localObject = paramc.a;
      int n = 0;
      while (n < ((ArrayList)localObject).size())
      {
        paramc = ((ArrayList)localObject).get(n);
        if ((paramc instanceof c))
        {
          paramc = (c)paramc;
          this.a.add(new c(paramc, paramArrayMap));
        }
        else
        {
          if ((paramc instanceof i.b))
          {
            paramc = new i.b((i.b)paramc);
          }
          else
          {
            if (!(paramc instanceof i.a)) {
              break label313;
            }
            paramc = new i.a((i.a)paramc);
          }
          this.a.add(paramc);
          if (paramc.n != null) {
            paramArrayMap.put(paramc.n, paramc);
          }
        }
        n++;
        continue;
        label313:
        throw new IllegalStateException("Unknown object in the tree!");
      }
    }
    
    private void a()
    {
      this.k.reset();
      this.k.postTranslate(-this.e, -this.f);
      this.k.postScale(this.g, this.h);
      this.k.postRotate(this.b, 0.0F, 0.0F);
      this.k.postTranslate(this.i + this.e, this.j + this.f);
    }
    
    private void a(TypedArray paramTypedArray, XmlPullParser paramXmlPullParser)
    {
      this.l = null;
      this.b = TypedArrayUtils.getNamedFloat(paramTypedArray, paramXmlPullParser, "rotation", 5, this.b);
      this.e = paramTypedArray.getFloat(1, this.e);
      this.f = paramTypedArray.getFloat(2, this.f);
      this.g = TypedArrayUtils.getNamedFloat(paramTypedArray, paramXmlPullParser, "scaleX", 3, this.g);
      this.h = TypedArrayUtils.getNamedFloat(paramTypedArray, paramXmlPullParser, "scaleY", 4, this.h);
      this.i = TypedArrayUtils.getNamedFloat(paramTypedArray, paramXmlPullParser, "translateX", 6, this.i);
      this.j = TypedArrayUtils.getNamedFloat(paramTypedArray, paramXmlPullParser, "translateY", 7, this.j);
      paramTypedArray = paramTypedArray.getString(0);
      if (paramTypedArray != null) {
        this.m = paramTypedArray;
      }
      a();
    }
    
    public void a(Resources paramResources, AttributeSet paramAttributeSet, Resources.Theme paramTheme, XmlPullParser paramXmlPullParser)
    {
      paramResources = TypedArrayUtils.obtainAttributes(paramResources, paramTheme, paramAttributeSet, a.b);
      a(paramResources, paramXmlPullParser);
      paramResources.recycle();
    }
    
    public String getGroupName()
    {
      return this.m;
    }
    
    public Matrix getLocalMatrix()
    {
      return this.k;
    }
    
    public float getPivotX()
    {
      return this.e;
    }
    
    public float getPivotY()
    {
      return this.f;
    }
    
    public float getRotation()
    {
      return this.b;
    }
    
    public float getScaleX()
    {
      return this.g;
    }
    
    public float getScaleY()
    {
      return this.h;
    }
    
    public float getTranslateX()
    {
      return this.i;
    }
    
    public float getTranslateY()
    {
      return this.j;
    }
    
    public void setPivotX(float paramFloat)
    {
      if (paramFloat != this.e)
      {
        this.e = paramFloat;
        a();
      }
    }
    
    public void setPivotY(float paramFloat)
    {
      if (paramFloat != this.f)
      {
        this.f = paramFloat;
        a();
      }
    }
    
    public void setRotation(float paramFloat)
    {
      if (paramFloat != this.b)
      {
        this.b = paramFloat;
        a();
      }
    }
    
    public void setScaleX(float paramFloat)
    {
      if (paramFloat != this.g)
      {
        this.g = paramFloat;
        a();
      }
    }
    
    public void setScaleY(float paramFloat)
    {
      if (paramFloat != this.h)
      {
        this.h = paramFloat;
        a();
      }
    }
    
    public void setTranslateX(float paramFloat)
    {
      if (paramFloat != this.i)
      {
        this.i = paramFloat;
        a();
      }
    }
    
    public void setTranslateY(float paramFloat)
    {
      if (paramFloat != this.j)
      {
        this.j = paramFloat;
        a();
      }
    }
  }
  
  private static class d
  {
    protected PathParser.PathDataNode[] m = null;
    String n;
    int o;
    
    public d() {}
    
    public d(d paramd)
    {
      this.n = paramd.n;
      this.o = paramd.o;
      this.m = PathParser.deepCopyNodes(paramd.m);
    }
    
    public void a(Path paramPath)
    {
      paramPath.reset();
      PathParser.PathDataNode[] arrayOfPathDataNode = this.m;
      if (arrayOfPathDataNode != null) {
        PathParser.PathDataNode.nodesToPath(arrayOfPathDataNode, paramPath);
      }
    }
    
    public boolean a()
    {
      return false;
    }
    
    public PathParser.PathDataNode[] getPathData()
    {
      return this.m;
    }
    
    public String getPathName()
    {
      return this.n;
    }
    
    public void setPathData(PathParser.PathDataNode[] paramArrayOfPathDataNode)
    {
      if (!PathParser.canMorph(this.m, paramArrayOfPathDataNode)) {
        this.m = PathParser.deepCopyNodes(paramArrayOfPathDataNode);
      } else {
        PathParser.updateNodes(this.m, paramArrayOfPathDataNode);
      }
    }
  }
  
  private static class e
  {
    private static final Matrix k = new Matrix();
    final i.c a;
    float b = 0.0F;
    float c = 0.0F;
    float d = 0.0F;
    float e = 0.0F;
    int f = 255;
    String g = null;
    final ArrayMap<String, Object> h = new ArrayMap();
    private final Path i;
    private final Path j;
    private final Matrix l = new Matrix();
    private Paint m;
    private Paint n;
    private PathMeasure o;
    private int p;
    
    public e()
    {
      this.a = new i.c();
      this.i = new Path();
      this.j = new Path();
    }
    
    public e(e parame)
    {
      this.a = new i.c(parame.a, this.h);
      this.i = new Path(parame.i);
      this.j = new Path(parame.j);
      this.b = parame.b;
      this.c = parame.c;
      this.d = parame.d;
      this.e = parame.e;
      this.p = parame.p;
      this.f = parame.f;
      this.g = parame.g;
      parame = parame.g;
      if (parame != null) {
        this.h.put(parame, this);
      }
    }
    
    private static float a(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
    {
      return paramFloat1 * paramFloat4 - paramFloat2 * paramFloat3;
    }
    
    private float a(Matrix paramMatrix)
    {
      float[] arrayOfFloat = new float[4];
      float[] tmp5_4 = arrayOfFloat;
      tmp5_4[0] = 0.0F;
      float[] tmp9_5 = tmp5_4;
      tmp9_5[1] = 1.0F;
      float[] tmp13_9 = tmp9_5;
      tmp13_9[2] = 1.0F;
      float[] tmp17_13 = tmp13_9;
      tmp17_13[3] = 0.0F;
      tmp17_13;
      paramMatrix.mapVectors(arrayOfFloat);
      float f1 = (float)Math.hypot(arrayOfFloat[0], arrayOfFloat[1]);
      float f2 = (float)Math.hypot(arrayOfFloat[2], arrayOfFloat[3]);
      float f3 = a(arrayOfFloat[0], arrayOfFloat[1], arrayOfFloat[2], arrayOfFloat[3]);
      f2 = Math.max(f1, f2);
      f1 = 0.0F;
      if (f2 > 0.0F) {
        f1 = Math.abs(f3) / f2;
      }
      return f1;
    }
    
    private void a(i.c paramc, Matrix paramMatrix, Canvas paramCanvas, int paramInt1, int paramInt2, ColorFilter paramColorFilter)
    {
      i.c.a(paramc).set(paramMatrix);
      i.c.a(paramc).preConcat(i.c.b(paramc));
      paramCanvas.save();
      for (int i1 = 0; i1 < paramc.a.size(); i1++)
      {
        paramMatrix = paramc.a.get(i1);
        if ((paramMatrix instanceof i.c)) {
          a((i.c)paramMatrix, i.c.a(paramc), paramCanvas, paramInt1, paramInt2, paramColorFilter);
        } else if ((paramMatrix instanceof i.d)) {
          a(paramc, (i.d)paramMatrix, paramCanvas, paramInt1, paramInt2, paramColorFilter);
        }
      }
      paramCanvas.restore();
    }
    
    private void a(i.c paramc, i.d paramd, Canvas paramCanvas, int paramInt1, int paramInt2, ColorFilter paramColorFilter)
    {
      float f1 = paramInt1 / this.d;
      float f2 = paramInt2 / this.e;
      float f3 = Math.min(f1, f2);
      paramc = i.c.a(paramc);
      this.l.set(paramc);
      this.l.postScale(f1, f2);
      f2 = a(paramc);
      if (f2 == 0.0F) {
        return;
      }
      paramd.a(this.i);
      paramc = this.i;
      this.j.reset();
      if (paramd.a())
      {
        this.j.addPath(paramc, this.l);
        paramCanvas.clipPath(this.j);
      }
      else
      {
        paramd = (i.b)paramd;
        if ((paramd.g != 0.0F) || (paramd.h != 1.0F))
        {
          float f4 = paramd.g;
          float f5 = paramd.i;
          float f6 = paramd.h;
          float f7 = paramd.i;
          if (this.o == null) {
            this.o = new PathMeasure();
          }
          this.o.setPath(this.i, false);
          f1 = this.o.getLength();
          f5 = (f4 + f5) % 1.0F * f1;
          f7 = (f6 + f7) % 1.0F * f1;
          paramc.reset();
          if (f5 > f7)
          {
            this.o.getSegment(f5, f1, paramc, true);
            this.o.getSegment(0.0F, f7, paramc, true);
          }
          else
          {
            this.o.getSegment(f5, f7, paramc, true);
          }
          paramc.rLineTo(0.0F, 0.0F);
        }
        this.j.addPath(paramc, this.l);
        if (paramd.c != 0)
        {
          if (this.n == null)
          {
            this.n = new Paint();
            this.n.setStyle(Paint.Style.FILL);
            this.n.setAntiAlias(true);
          }
          Paint localPaint = this.n;
          localPaint.setColor(i.a(paramd.c, paramd.f));
          localPaint.setColorFilter(paramColorFilter);
          Path localPath = this.j;
          if (paramd.e == 0) {
            paramc = Path.FillType.WINDING;
          } else {
            paramc = Path.FillType.EVEN_ODD;
          }
          localPath.setFillType(paramc);
          paramCanvas.drawPath(this.j, localPaint);
        }
        if (paramd.a != 0)
        {
          if (this.m == null)
          {
            this.m = new Paint();
            this.m.setStyle(Paint.Style.STROKE);
            this.m.setAntiAlias(true);
          }
          paramc = this.m;
          if (paramd.k != null) {
            paramc.setStrokeJoin(paramd.k);
          }
          if (paramd.j != null) {
            paramc.setStrokeCap(paramd.j);
          }
          paramc.setStrokeMiter(paramd.l);
          paramc.setColor(i.a(paramd.a, paramd.d));
          paramc.setColorFilter(paramColorFilter);
          paramc.setStrokeWidth(paramd.b * (f3 * f2));
          paramCanvas.drawPath(this.j, paramc);
        }
      }
    }
    
    public void a(Canvas paramCanvas, int paramInt1, int paramInt2, ColorFilter paramColorFilter)
    {
      a(this.a, k, paramCanvas, paramInt1, paramInt2, paramColorFilter);
    }
    
    public float getAlpha()
    {
      return getRootAlpha() / 255.0F;
    }
    
    public int getRootAlpha()
    {
      return this.f;
    }
    
    public void setAlpha(float paramFloat)
    {
      setRootAlpha((int)(paramFloat * 255.0F));
    }
    
    public void setRootAlpha(int paramInt)
    {
      this.f = paramInt;
    }
  }
  
  private static class f
    extends Drawable.ConstantState
  {
    int a;
    i.e b;
    ColorStateList c = null;
    PorterDuff.Mode d = i.a;
    boolean e;
    Bitmap f;
    ColorStateList g;
    PorterDuff.Mode h;
    int i;
    boolean j;
    boolean k;
    Paint l;
    
    public f()
    {
      this.b = new i.e();
    }
    
    public f(f paramf)
    {
      if (paramf != null)
      {
        this.a = paramf.a;
        this.b = new i.e(paramf.b);
        if (i.e.a(paramf.b) != null) {
          i.e.a(this.b, new Paint(i.e.a(paramf.b)));
        }
        if (i.e.b(paramf.b) != null) {
          i.e.b(this.b, new Paint(i.e.b(paramf.b)));
        }
        this.c = paramf.c;
        this.d = paramf.d;
        this.e = paramf.e;
      }
    }
    
    public Paint a(ColorFilter paramColorFilter)
    {
      if ((!a()) && (paramColorFilter == null)) {
        return null;
      }
      if (this.l == null)
      {
        this.l = new Paint();
        this.l.setFilterBitmap(true);
      }
      this.l.setAlpha(this.b.getRootAlpha());
      this.l.setColorFilter(paramColorFilter);
      return this.l;
    }
    
    public void a(int paramInt1, int paramInt2)
    {
      this.f.eraseColor(0);
      Canvas localCanvas = new Canvas(this.f);
      this.b.a(localCanvas, paramInt1, paramInt2, null);
    }
    
    public void a(Canvas paramCanvas, ColorFilter paramColorFilter, Rect paramRect)
    {
      paramColorFilter = a(paramColorFilter);
      paramCanvas.drawBitmap(this.f, null, paramRect, paramColorFilter);
    }
    
    public boolean a()
    {
      boolean bool;
      if (this.b.getRootAlpha() < 255) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public void b(int paramInt1, int paramInt2)
    {
      if ((this.f == null) || (!c(paramInt1, paramInt2)))
      {
        this.f = Bitmap.createBitmap(paramInt1, paramInt2, Bitmap.Config.ARGB_8888);
        this.k = true;
      }
    }
    
    public boolean b()
    {
      return (!this.k) && (this.g == this.c) && (this.h == this.d) && (this.j == this.e) && (this.i == this.b.getRootAlpha());
    }
    
    public void c()
    {
      this.g = this.c;
      this.h = this.d;
      this.i = this.b.getRootAlpha();
      this.j = this.e;
      this.k = false;
    }
    
    public boolean c(int paramInt1, int paramInt2)
    {
      return (paramInt1 == this.f.getWidth()) && (paramInt2 == this.f.getHeight());
    }
    
    public int getChangingConfigurations()
    {
      return this.a;
    }
    
    public Drawable newDrawable()
    {
      return new i(this);
    }
    
    public Drawable newDrawable(Resources paramResources)
    {
      return new i(this);
    }
  }
  
  private static class g
    extends Drawable.ConstantState
  {
    private final Drawable.ConstantState a;
    
    public g(Drawable.ConstantState paramConstantState)
    {
      this.a = paramConstantState;
    }
    
    public boolean canApplyTheme()
    {
      return this.a.canApplyTheme();
    }
    
    public int getChangingConfigurations()
    {
      return this.a.getChangingConfigurations();
    }
    
    public Drawable newDrawable()
    {
      i locali = new i();
      locali.b = ((VectorDrawable)this.a.newDrawable());
      return locali;
    }
    
    public Drawable newDrawable(Resources paramResources)
    {
      i locali = new i();
      locali.b = ((VectorDrawable)this.a.newDrawable(paramResources));
      return locali;
    }
    
    public Drawable newDrawable(Resources paramResources, Resources.Theme paramTheme)
    {
      i locali = new i();
      locali.b = ((VectorDrawable)this.a.newDrawable(paramResources, paramTheme));
      return locali;
    }
  }
}


/* Location:              ~/android/support/b/a/i.class
 *
 * Reversed by:           J
 */