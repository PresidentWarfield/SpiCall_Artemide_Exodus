package android.support.constraint;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.util.Xml;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class c
{
  private static final int[] a = { 0, 4, 8 };
  private static SparseIntArray c = new SparseIntArray();
  private HashMap<Integer, a> b = new HashMap();
  
  static
  {
    c.append(g.b.ConstraintSet_layout_constraintLeft_toLeftOf, 25);
    c.append(g.b.ConstraintSet_layout_constraintLeft_toRightOf, 26);
    c.append(g.b.ConstraintSet_layout_constraintRight_toLeftOf, 29);
    c.append(g.b.ConstraintSet_layout_constraintRight_toRightOf, 30);
    c.append(g.b.ConstraintSet_layout_constraintTop_toTopOf, 36);
    c.append(g.b.ConstraintSet_layout_constraintTop_toBottomOf, 35);
    c.append(g.b.ConstraintSet_layout_constraintBottom_toTopOf, 4);
    c.append(g.b.ConstraintSet_layout_constraintBottom_toBottomOf, 3);
    c.append(g.b.ConstraintSet_layout_constraintBaseline_toBaselineOf, 1);
    c.append(g.b.ConstraintSet_layout_editor_absoluteX, 6);
    c.append(g.b.ConstraintSet_layout_editor_absoluteY, 7);
    c.append(g.b.ConstraintSet_layout_constraintGuide_begin, 17);
    c.append(g.b.ConstraintSet_layout_constraintGuide_end, 18);
    c.append(g.b.ConstraintSet_layout_constraintGuide_percent, 19);
    c.append(g.b.ConstraintSet_android_orientation, 27);
    c.append(g.b.ConstraintSet_layout_constraintStart_toEndOf, 32);
    c.append(g.b.ConstraintSet_layout_constraintStart_toStartOf, 33);
    c.append(g.b.ConstraintSet_layout_constraintEnd_toStartOf, 10);
    c.append(g.b.ConstraintSet_layout_constraintEnd_toEndOf, 9);
    c.append(g.b.ConstraintSet_layout_goneMarginLeft, 13);
    c.append(g.b.ConstraintSet_layout_goneMarginTop, 16);
    c.append(g.b.ConstraintSet_layout_goneMarginRight, 14);
    c.append(g.b.ConstraintSet_layout_goneMarginBottom, 11);
    c.append(g.b.ConstraintSet_layout_goneMarginStart, 15);
    c.append(g.b.ConstraintSet_layout_goneMarginEnd, 12);
    c.append(g.b.ConstraintSet_layout_constraintVertical_weight, 40);
    c.append(g.b.ConstraintSet_layout_constraintHorizontal_weight, 39);
    c.append(g.b.ConstraintSet_layout_constraintHorizontal_chainStyle, 41);
    c.append(g.b.ConstraintSet_layout_constraintVertical_chainStyle, 42);
    c.append(g.b.ConstraintSet_layout_constraintHorizontal_bias, 20);
    c.append(g.b.ConstraintSet_layout_constraintVertical_bias, 37);
    c.append(g.b.ConstraintSet_layout_constraintDimensionRatio, 5);
    c.append(g.b.ConstraintSet_layout_constraintLeft_creator, 75);
    c.append(g.b.ConstraintSet_layout_constraintTop_creator, 75);
    c.append(g.b.ConstraintSet_layout_constraintRight_creator, 75);
    c.append(g.b.ConstraintSet_layout_constraintBottom_creator, 75);
    c.append(g.b.ConstraintSet_layout_constraintBaseline_creator, 75);
    c.append(g.b.ConstraintSet_android_layout_marginLeft, 24);
    c.append(g.b.ConstraintSet_android_layout_marginRight, 28);
    c.append(g.b.ConstraintSet_android_layout_marginStart, 31);
    c.append(g.b.ConstraintSet_android_layout_marginEnd, 8);
    c.append(g.b.ConstraintSet_android_layout_marginTop, 34);
    c.append(g.b.ConstraintSet_android_layout_marginBottom, 2);
    c.append(g.b.ConstraintSet_android_layout_width, 23);
    c.append(g.b.ConstraintSet_android_layout_height, 21);
    c.append(g.b.ConstraintSet_android_visibility, 22);
    c.append(g.b.ConstraintSet_android_alpha, 43);
    c.append(g.b.ConstraintSet_android_elevation, 44);
    c.append(g.b.ConstraintSet_android_rotationX, 45);
    c.append(g.b.ConstraintSet_android_rotationY, 46);
    c.append(g.b.ConstraintSet_android_rotation, 60);
    c.append(g.b.ConstraintSet_android_scaleX, 47);
    c.append(g.b.ConstraintSet_android_scaleY, 48);
    c.append(g.b.ConstraintSet_android_transformPivotX, 49);
    c.append(g.b.ConstraintSet_android_transformPivotY, 50);
    c.append(g.b.ConstraintSet_android_translationX, 51);
    c.append(g.b.ConstraintSet_android_translationY, 52);
    c.append(g.b.ConstraintSet_android_translationZ, 53);
    c.append(g.b.ConstraintSet_layout_constraintWidth_default, 54);
    c.append(g.b.ConstraintSet_layout_constraintHeight_default, 55);
    c.append(g.b.ConstraintSet_layout_constraintWidth_max, 56);
    c.append(g.b.ConstraintSet_layout_constraintHeight_max, 57);
    c.append(g.b.ConstraintSet_layout_constraintWidth_min, 58);
    c.append(g.b.ConstraintSet_layout_constraintHeight_min, 59);
    c.append(g.b.ConstraintSet_layout_constraintCircle, 61);
    c.append(g.b.ConstraintSet_layout_constraintCircleRadius, 62);
    c.append(g.b.ConstraintSet_layout_constraintCircleAngle, 63);
    c.append(g.b.ConstraintSet_android_id, 38);
    c.append(g.b.ConstraintSet_layout_constraintWidth_percent, 69);
    c.append(g.b.ConstraintSet_layout_constraintHeight_percent, 70);
    c.append(g.b.ConstraintSet_chainUseRtl, 71);
    c.append(g.b.ConstraintSet_barrierDirection, 72);
    c.append(g.b.ConstraintSet_constraint_referenced_ids, 73);
    c.append(g.b.ConstraintSet_barrierAllowsGoneWidgets, 74);
  }
  
  private static int a(TypedArray paramTypedArray, int paramInt1, int paramInt2)
  {
    int i = paramTypedArray.getResourceId(paramInt1, paramInt2);
    paramInt2 = i;
    if (i == -1) {
      paramInt2 = paramTypedArray.getInt(paramInt1, -1);
    }
    return paramInt2;
  }
  
  private a a(Context paramContext, AttributeSet paramAttributeSet)
  {
    a locala = new a(null);
    paramContext = paramContext.obtainStyledAttributes(paramAttributeSet, g.b.ConstraintSet);
    a(locala, paramContext);
    paramContext.recycle();
    return locala;
  }
  
  private void a(a parama, TypedArray paramTypedArray)
  {
    int i = paramTypedArray.getIndexCount();
    for (int j = 0; j < i; j++)
    {
      int k = paramTypedArray.getIndex(j);
      int m = c.get(k);
      switch (m)
      {
      default: 
        switch (m)
        {
        default: 
          StringBuilder localStringBuilder;
          switch (m)
          {
          default: 
            localStringBuilder = new StringBuilder();
            localStringBuilder.append("Unknown attribute 0x");
            localStringBuilder.append(Integer.toHexString(k));
            localStringBuilder.append("   ");
            localStringBuilder.append(c.get(k));
            Log.w("ConstraintSet", localStringBuilder.toString());
            break;
          case 75: 
            localStringBuilder = new StringBuilder();
            localStringBuilder.append("unused attribute 0x");
            localStringBuilder.append(Integer.toHexString(k));
            localStringBuilder.append("   ");
            localStringBuilder.append(c.get(k));
            Log.w("ConstraintSet", localStringBuilder.toString());
            break;
          case 74: 
            parama.ar = paramTypedArray.getBoolean(k, parama.ar);
            break;
          case 73: 
            parama.av = paramTypedArray.getString(k);
            break;
          case 72: 
            parama.as = paramTypedArray.getInt(k, parama.as);
            break;
          case 71: 
            Log.e("ConstraintSet", "CURRENTLY UNSUPPORTED");
            break;
          case 70: 
            parama.aq = paramTypedArray.getFloat(k, 1.0F);
            break;
          case 69: 
            parama.ap = paramTypedArray.getFloat(k, 1.0F);
          }
          break;
        case 63: 
          parama.z = paramTypedArray.getFloat(k, parama.z);
          break;
        case 62: 
          parama.y = paramTypedArray.getDimensionPixelSize(k, parama.y);
          break;
        case 61: 
          parama.x = a(paramTypedArray, k, parama.x);
          break;
        case 60: 
          parama.X = paramTypedArray.getFloat(k, parama.X);
        }
        break;
      case 53: 
        parama.ag = paramTypedArray.getDimension(k, parama.ag);
        break;
      case 52: 
        parama.af = paramTypedArray.getDimension(k, parama.af);
        break;
      case 51: 
        parama.ae = paramTypedArray.getDimension(k, parama.ae);
        break;
      case 50: 
        parama.ad = paramTypedArray.getFloat(k, parama.ad);
        break;
      case 49: 
        parama.ac = paramTypedArray.getFloat(k, parama.ac);
        break;
      case 48: 
        parama.ab = paramTypedArray.getFloat(k, parama.ab);
        break;
      case 47: 
        parama.aa = paramTypedArray.getFloat(k, parama.aa);
        break;
      case 46: 
        parama.Z = paramTypedArray.getFloat(k, parama.Z);
        break;
      case 45: 
        parama.Y = paramTypedArray.getFloat(k, parama.Y);
        break;
      case 44: 
        parama.V = true;
        parama.W = paramTypedArray.getDimension(k, parama.W);
        break;
      case 43: 
        parama.U = paramTypedArray.getFloat(k, parama.U);
        break;
      case 42: 
        parama.T = paramTypedArray.getInt(k, parama.T);
        break;
      case 41: 
        parama.S = paramTypedArray.getInt(k, parama.S);
        break;
      case 40: 
        parama.Q = paramTypedArray.getFloat(k, parama.Q);
        break;
      case 39: 
        parama.R = paramTypedArray.getFloat(k, parama.R);
        break;
      case 38: 
        parama.d = paramTypedArray.getResourceId(k, parama.d);
        break;
      case 37: 
        parama.v = paramTypedArray.getFloat(k, parama.v);
        break;
      case 36: 
        parama.l = a(paramTypedArray, k, parama.l);
        break;
      case 35: 
        parama.m = a(paramTypedArray, k, parama.m);
        break;
      case 34: 
        parama.F = paramTypedArray.getDimensionPixelSize(k, parama.F);
        break;
      case 33: 
        parama.r = a(paramTypedArray, k, parama.r);
        break;
      case 32: 
        parama.q = a(paramTypedArray, k, parama.q);
        break;
      case 31: 
        parama.I = paramTypedArray.getDimensionPixelSize(k, parama.I);
        break;
      case 30: 
        parama.k = a(paramTypedArray, k, parama.k);
        break;
      case 29: 
        parama.j = a(paramTypedArray, k, parama.j);
        break;
      case 28: 
        parama.E = paramTypedArray.getDimensionPixelSize(k, parama.E);
        break;
      case 27: 
        parama.C = paramTypedArray.getInt(k, parama.C);
        break;
      case 26: 
        parama.i = a(paramTypedArray, k, parama.i);
        break;
      case 25: 
        parama.h = a(paramTypedArray, k, parama.h);
        break;
      case 24: 
        parama.D = paramTypedArray.getDimensionPixelSize(k, parama.D);
        break;
      case 23: 
        parama.b = paramTypedArray.getLayoutDimension(k, parama.b);
        break;
      case 22: 
        parama.J = paramTypedArray.getInt(k, parama.J);
        parama.J = a[parama.J];
        break;
      case 21: 
        parama.c = paramTypedArray.getLayoutDimension(k, parama.c);
        break;
      case 20: 
        parama.u = paramTypedArray.getFloat(k, parama.u);
        break;
      case 19: 
        parama.g = paramTypedArray.getFloat(k, parama.g);
        break;
      case 18: 
        parama.f = paramTypedArray.getDimensionPixelOffset(k, parama.f);
        break;
      case 17: 
        parama.e = paramTypedArray.getDimensionPixelOffset(k, parama.e);
        break;
      case 16: 
        parama.L = paramTypedArray.getDimensionPixelSize(k, parama.L);
        break;
      case 15: 
        parama.P = paramTypedArray.getDimensionPixelSize(k, parama.P);
        break;
      case 14: 
        parama.M = paramTypedArray.getDimensionPixelSize(k, parama.M);
        break;
      case 13: 
        parama.K = paramTypedArray.getDimensionPixelSize(k, parama.K);
        break;
      case 12: 
        parama.O = paramTypedArray.getDimensionPixelSize(k, parama.O);
        break;
      case 11: 
        parama.N = paramTypedArray.getDimensionPixelSize(k, parama.N);
        break;
      case 10: 
        parama.s = a(paramTypedArray, k, parama.s);
        break;
      case 9: 
        parama.t = a(paramTypedArray, k, parama.t);
        break;
      case 8: 
        parama.H = paramTypedArray.getDimensionPixelSize(k, parama.H);
        break;
      case 7: 
        parama.B = paramTypedArray.getDimensionPixelOffset(k, parama.B);
        break;
      case 6: 
        parama.A = paramTypedArray.getDimensionPixelOffset(k, parama.A);
        break;
      case 5: 
        parama.w = paramTypedArray.getString(k);
        break;
      case 4: 
        parama.n = a(paramTypedArray, k, parama.n);
        break;
      case 3: 
        parama.o = a(paramTypedArray, k, parama.o);
        break;
      case 2: 
        parama.G = paramTypedArray.getDimensionPixelSize(k, parama.G);
        break;
      case 1: 
        parama.p = a(paramTypedArray, k, parama.p);
      }
    }
  }
  
  private int[] a(View paramView, String paramString)
  {
    String[] arrayOfString = paramString.split(",");
    Context localContext = paramView.getContext();
    paramString = new int[arrayOfString.length];
    int i = 0;
    for (int j = 0; i < arrayOfString.length; j++)
    {
      Object localObject = arrayOfString[i].trim();
      try
      {
        k = g.a.class.getField((String)localObject).getInt(null);
      }
      catch (Exception localException)
      {
        k = 0;
      }
      int m = k;
      if (k == 0) {
        m = localContext.getResources().getIdentifier((String)localObject, "id", localContext.getPackageName());
      }
      int k = m;
      if (m == 0)
      {
        k = m;
        if (paramView.isInEditMode())
        {
          k = m;
          if ((paramView.getParent() instanceof ConstraintLayout))
          {
            localObject = ((ConstraintLayout)paramView.getParent()).a(0, localObject);
            k = m;
            if (localObject != null)
            {
              k = m;
              if ((localObject instanceof Integer)) {
                k = ((Integer)localObject).intValue();
              }
            }
          }
        }
      }
      paramString[j] = k;
      i++;
    }
    paramView = paramString;
    if (j != arrayOfString.length) {
      paramView = Arrays.copyOf(paramString, j);
    }
    return paramView;
  }
  
  public void a(Context paramContext, int paramInt)
  {
    XmlResourceParser localXmlResourceParser = paramContext.getResources().getXml(paramInt);
    try
    {
      for (paramInt = localXmlResourceParser.getEventType(); paramInt != 1; paramInt = localXmlResourceParser.next())
      {
        if (paramInt != 0) {}
        switch (paramInt)
        {
        default: 
          break;
        case 2: 
          String str = localXmlResourceParser.getName();
          a locala = a(paramContext, Xml.asAttributeSet(localXmlResourceParser));
          if (str.equalsIgnoreCase("Guideline")) {
            locala.a = true;
          }
          this.b.put(Integer.valueOf(locala.d), locala);
          continue;
          localXmlResourceParser.getName();
        }
      }
      return;
    }
    catch (IOException paramContext)
    {
      paramContext.printStackTrace();
    }
    catch (XmlPullParserException paramContext)
    {
      paramContext.printStackTrace();
    }
  }
  
  void a(ConstraintLayout paramConstraintLayout)
  {
    int i = paramConstraintLayout.getChildCount();
    Object localObject1 = new HashSet(this.b.keySet());
    int j = 0;
    Object localObject2;
    a locala;
    Object localObject3;
    while (j < i)
    {
      localObject2 = paramConstraintLayout.getChildAt(j);
      int k = ((View)localObject2).getId();
      if (k != -1)
      {
        if (this.b.containsKey(Integer.valueOf(k)))
        {
          ((HashSet)localObject1).remove(Integer.valueOf(k));
          locala = (a)this.b.get(Integer.valueOf(k));
          if ((localObject2 instanceof a)) {
            locala.at = 1;
          }
          if ((locala.at != -1) && (locala.at == 1))
          {
            localObject3 = (a)localObject2;
            ((a)localObject3).setId(k);
            ((a)localObject3).setType(locala.as);
            ((a)localObject3).setAllowsGoneWidget(locala.ar);
            if (locala.au != null)
            {
              ((a)localObject3).setReferencedIds(locala.au);
            }
            else if (locala.av != null)
            {
              locala.au = a((View)localObject3, locala.av);
              ((a)localObject3).setReferencedIds(locala.au);
            }
          }
          localObject3 = (ConstraintLayout.a)((View)localObject2).getLayoutParams();
          locala.a((ConstraintLayout.a)localObject3);
          ((View)localObject2).setLayoutParams((ViewGroup.LayoutParams)localObject3);
          ((View)localObject2).setVisibility(locala.J);
          if (Build.VERSION.SDK_INT >= 17)
          {
            ((View)localObject2).setAlpha(locala.U);
            ((View)localObject2).setRotation(locala.X);
            ((View)localObject2).setRotationX(locala.Y);
            ((View)localObject2).setRotationY(locala.Z);
            ((View)localObject2).setScaleX(locala.aa);
            ((View)localObject2).setScaleY(locala.ab);
            if (!Float.isNaN(locala.ac)) {
              ((View)localObject2).setPivotX(locala.ac);
            }
            if (!Float.isNaN(locala.ad)) {
              ((View)localObject2).setPivotY(locala.ad);
            }
            ((View)localObject2).setTranslationX(locala.ae);
            ((View)localObject2).setTranslationY(locala.af);
            if (Build.VERSION.SDK_INT >= 21)
            {
              ((View)localObject2).setTranslationZ(locala.ag);
              if (locala.V) {
                ((View)localObject2).setElevation(locala.W);
              }
            }
          }
        }
        j++;
      }
      else
      {
        throw new RuntimeException("All children of ConstraintLayout must have ids to use ConstraintSet");
      }
    }
    localObject1 = ((HashSet)localObject1).iterator();
    while (((Iterator)localObject1).hasNext())
    {
      localObject2 = (Integer)((Iterator)localObject1).next();
      locala = (a)this.b.get(localObject2);
      if ((locala.at != -1) && (locala.at == 1))
      {
        localObject3 = new a(paramConstraintLayout.getContext());
        ((a)localObject3).setId(((Integer)localObject2).intValue());
        if (locala.au != null)
        {
          ((a)localObject3).setReferencedIds(locala.au);
        }
        else if (locala.av != null)
        {
          locala.au = a((View)localObject3, locala.av);
          ((a)localObject3).setReferencedIds(locala.au);
        }
        ((a)localObject3).setType(locala.as);
        ConstraintLayout.a locala1 = paramConstraintLayout.a();
        ((a)localObject3).a();
        locala.a(locala1);
        paramConstraintLayout.addView((View)localObject3, locala1);
      }
      if (locala.a)
      {
        localObject3 = new e(paramConstraintLayout.getContext());
        ((e)localObject3).setId(((Integer)localObject2).intValue());
        localObject2 = paramConstraintLayout.a();
        locala.a((ConstraintLayout.a)localObject2);
        paramConstraintLayout.addView((View)localObject3, (ViewGroup.LayoutParams)localObject2);
      }
    }
  }
  
  public void a(d paramd)
  {
    int i = paramd.getChildCount();
    this.b.clear();
    int j = 0;
    while (j < i)
    {
      View localView = paramd.getChildAt(j);
      d.a locala = (d.a)localView.getLayoutParams();
      int k = localView.getId();
      if (k != -1)
      {
        if (!this.b.containsKey(Integer.valueOf(k))) {
          this.b.put(Integer.valueOf(k), new a(null));
        }
        a locala1 = (a)this.b.get(Integer.valueOf(k));
        if ((localView instanceof b)) {
          a.a(locala1, (b)localView, k, locala);
        }
        a.a(locala1, k, locala);
        j++;
      }
      else
      {
        throw new RuntimeException("All children of ConstraintLayout must have ids to use ConstraintSet");
      }
    }
  }
  
  private static class a
  {
    public int A = -1;
    public int B = -1;
    public int C = -1;
    public int D = -1;
    public int E = -1;
    public int F = -1;
    public int G = -1;
    public int H = -1;
    public int I = -1;
    public int J = 0;
    public int K = -1;
    public int L = -1;
    public int M = -1;
    public int N = -1;
    public int O = -1;
    public int P = -1;
    public float Q = 0.0F;
    public float R = 0.0F;
    public int S = 0;
    public int T = 0;
    public float U = 1.0F;
    public boolean V = false;
    public float W = 0.0F;
    public float X = 0.0F;
    public float Y = 0.0F;
    public float Z = 0.0F;
    boolean a = false;
    public float aa = 1.0F;
    public float ab = 1.0F;
    public float ac = NaN.0F;
    public float ad = NaN.0F;
    public float ae = 0.0F;
    public float af = 0.0F;
    public float ag = 0.0F;
    public boolean ah = false;
    public boolean ai = false;
    public int aj = 0;
    public int ak = 0;
    public int al = -1;
    public int am = -1;
    public int an = -1;
    public int ao = -1;
    public float ap = 1.0F;
    public float aq = 1.0F;
    public boolean ar = false;
    public int as = -1;
    public int at = -1;
    public int[] au;
    public String av;
    public int b;
    public int c;
    int d;
    public int e = -1;
    public int f = -1;
    public float g = -1.0F;
    public int h = -1;
    public int i = -1;
    public int j = -1;
    public int k = -1;
    public int l = -1;
    public int m = -1;
    public int n = -1;
    public int o = -1;
    public int p = -1;
    public int q = -1;
    public int r = -1;
    public int s = -1;
    public int t = -1;
    public float u = 0.5F;
    public float v = 0.5F;
    public String w = null;
    public int x = -1;
    public int y = 0;
    public float z = 0.0F;
    
    private void a(int paramInt, ConstraintLayout.a parama)
    {
      this.d = paramInt;
      this.h = parama.d;
      this.i = parama.e;
      this.j = parama.f;
      this.k = parama.g;
      this.l = parama.h;
      this.m = parama.i;
      this.n = parama.j;
      this.o = parama.k;
      this.p = parama.l;
      this.q = parama.p;
      this.r = parama.q;
      this.s = parama.r;
      this.t = parama.s;
      this.u = parama.z;
      this.v = parama.A;
      this.w = parama.B;
      this.x = parama.m;
      this.y = parama.n;
      this.z = parama.o;
      this.A = parama.Q;
      this.B = parama.R;
      this.C = parama.S;
      this.g = parama.c;
      this.e = parama.a;
      this.f = parama.b;
      this.b = parama.width;
      this.c = parama.height;
      this.D = parama.leftMargin;
      this.E = parama.rightMargin;
      this.F = parama.topMargin;
      this.G = parama.bottomMargin;
      this.Q = parama.F;
      this.R = parama.E;
      this.T = parama.H;
      this.S = parama.G;
      this.ah = parama.T;
      this.ai = parama.U;
      this.aj = parama.I;
      this.ak = parama.J;
      this.ah = parama.T;
      this.al = parama.M;
      this.am = parama.N;
      this.an = parama.K;
      this.ao = parama.L;
      this.ap = parama.O;
      this.aq = parama.P;
      if (Build.VERSION.SDK_INT >= 17)
      {
        this.H = parama.getMarginEnd();
        this.I = parama.getMarginStart();
      }
    }
    
    private void a(int paramInt, d.a parama)
    {
      a(paramInt, parama);
      this.U = parama.an;
      this.X = parama.aq;
      this.Y = parama.ar;
      this.Z = parama.as;
      this.aa = parama.at;
      this.ab = parama.au;
      this.ac = parama.av;
      this.ad = parama.aw;
      this.ae = parama.ax;
      this.af = parama.ay;
      this.ag = parama.az;
      this.W = parama.ap;
      this.V = parama.ao;
    }
    
    private void a(b paramb, int paramInt, d.a parama)
    {
      a(paramInt, parama);
      if ((paramb instanceof a))
      {
        this.at = 1;
        paramb = (a)paramb;
        this.as = paramb.getType();
        this.au = paramb.getReferencedIds();
      }
    }
    
    public a a()
    {
      a locala = new a();
      locala.a = this.a;
      locala.b = this.b;
      locala.c = this.c;
      locala.e = this.e;
      locala.f = this.f;
      locala.g = this.g;
      locala.h = this.h;
      locala.i = this.i;
      locala.j = this.j;
      locala.k = this.k;
      locala.l = this.l;
      locala.m = this.m;
      locala.n = this.n;
      locala.o = this.o;
      locala.p = this.p;
      locala.q = this.q;
      locala.r = this.r;
      locala.s = this.s;
      locala.t = this.t;
      locala.u = this.u;
      locala.v = this.v;
      locala.w = this.w;
      locala.A = this.A;
      locala.B = this.B;
      locala.u = this.u;
      locala.u = this.u;
      locala.u = this.u;
      locala.u = this.u;
      locala.u = this.u;
      locala.C = this.C;
      locala.D = this.D;
      locala.E = this.E;
      locala.F = this.F;
      locala.G = this.G;
      locala.H = this.H;
      locala.I = this.I;
      locala.J = this.J;
      locala.K = this.K;
      locala.L = this.L;
      locala.M = this.M;
      locala.N = this.N;
      locala.O = this.O;
      locala.P = this.P;
      locala.Q = this.Q;
      locala.R = this.R;
      locala.S = this.S;
      locala.T = this.T;
      locala.U = this.U;
      locala.V = this.V;
      locala.W = this.W;
      locala.X = this.X;
      locala.Y = this.Y;
      locala.Z = this.Z;
      locala.aa = this.aa;
      locala.ab = this.ab;
      locala.ac = this.ac;
      locala.ad = this.ad;
      locala.ae = this.ae;
      locala.af = this.af;
      locala.ag = this.ag;
      locala.ah = this.ah;
      locala.ai = this.ai;
      locala.aj = this.aj;
      locala.ak = this.ak;
      locala.al = this.al;
      locala.am = this.am;
      locala.an = this.an;
      locala.ao = this.ao;
      locala.ap = this.ap;
      locala.aq = this.aq;
      locala.as = this.as;
      locala.at = this.at;
      int[] arrayOfInt = this.au;
      if (arrayOfInt != null) {
        locala.au = Arrays.copyOf(arrayOfInt, arrayOfInt.length);
      }
      locala.x = this.x;
      locala.y = this.y;
      locala.z = this.z;
      locala.ar = this.ar;
      return locala;
    }
    
    public void a(ConstraintLayout.a parama)
    {
      parama.d = this.h;
      parama.e = this.i;
      parama.f = this.j;
      parama.g = this.k;
      parama.h = this.l;
      parama.i = this.m;
      parama.j = this.n;
      parama.k = this.o;
      parama.l = this.p;
      parama.p = this.q;
      parama.q = this.r;
      parama.r = this.s;
      parama.s = this.t;
      parama.leftMargin = this.D;
      parama.rightMargin = this.E;
      parama.topMargin = this.F;
      parama.bottomMargin = this.G;
      parama.x = this.P;
      parama.y = this.O;
      parama.z = this.u;
      parama.A = this.v;
      parama.m = this.x;
      parama.n = this.y;
      parama.o = this.z;
      parama.B = this.w;
      parama.Q = this.A;
      parama.R = this.B;
      parama.F = this.Q;
      parama.E = this.R;
      parama.H = this.T;
      parama.G = this.S;
      parama.T = this.ah;
      parama.U = this.ai;
      parama.I = this.aj;
      parama.J = this.ak;
      parama.M = this.al;
      parama.N = this.am;
      parama.K = this.an;
      parama.L = this.ao;
      parama.O = this.ap;
      parama.P = this.aq;
      parama.S = this.C;
      parama.c = this.g;
      parama.a = this.e;
      parama.b = this.f;
      parama.width = this.b;
      parama.height = this.c;
      if (Build.VERSION.SDK_INT >= 17)
      {
        parama.setMarginStart(this.I);
        parama.setMarginEnd(this.H);
      }
      parama.a();
    }
  }
}


/* Location:              ~/android/support/constraint/c.class
 *
 * Reversed by:           J
 */