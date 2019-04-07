package android.support.constraint;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build.VERSION;
import android.support.constraint.a.a.a;
import android.support.constraint.a.a.e.b;
import android.support.constraint.a.a.e.c;
import android.support.constraint.a.a.f.a;
import android.support.constraint.a.a.g;
import android.support.constraint.a.a.i;
import android.support.constraint.a.a.m;
import android.support.constraint.a.a.n;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import java.util.ArrayList;
import java.util.HashMap;

public class ConstraintLayout
  extends ViewGroup
{
  SparseArray<View> a = new SparseArray();
  g b = new g();
  int c = -1;
  int d = -1;
  int e = 0;
  int f = 0;
  private ArrayList<b> g = new ArrayList(4);
  private final ArrayList<android.support.constraint.a.a.f> h = new ArrayList(100);
  private int i = 0;
  private int j = 0;
  private int k = Integer.MAX_VALUE;
  private int l = Integer.MAX_VALUE;
  private boolean m = true;
  private int n = 7;
  private c o = null;
  private int p = -1;
  private HashMap<String, Integer> q = new HashMap();
  private int r = -1;
  private int s = -1;
  private android.support.constraint.a.f t;
  
  public ConstraintLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    b(paramAttributeSet);
  }
  
  private void a(int paramInt1, int paramInt2)
  {
    int i1 = getPaddingTop() + getPaddingBottom();
    int i2 = getPaddingLeft() + getPaddingRight();
    int i3 = getChildCount();
    for (int i4 = 0; i4 < i3; i4++)
    {
      View localView = getChildAt(i4);
      if (localView.getVisibility() != 8)
      {
        a locala = (a)localView.getLayoutParams();
        android.support.constraint.a.a.f localf = locala.al;
        if ((!locala.Y) && (!locala.Z))
        {
          localf.e(localView.getVisibility());
          int i5 = locala.width;
          int i6 = locala.height;
          int i7;
          if ((!locala.V) && (!locala.W) && ((locala.V) || (locala.I != 1)) && (locala.width != -1) && ((locala.W) || ((locala.J != 1) && (locala.height != -1)))) {
            i7 = 0;
          } else {
            i7 = 1;
          }
          int i8;
          int i9;
          int i10;
          if (i7 != 0)
          {
            if (i5 == 0)
            {
              i8 = getChildMeasureSpec(paramInt1, i2, -2);
              i7 = 1;
            }
            else if (i5 == -1)
            {
              i8 = getChildMeasureSpec(paramInt1, i2, -1);
              i7 = 0;
            }
            else
            {
              if (i5 == -2) {
                i7 = 1;
              } else {
                i7 = 0;
              }
              i8 = getChildMeasureSpec(paramInt1, i2, i5);
            }
            if (i6 == 0)
            {
              i9 = getChildMeasureSpec(paramInt2, i1, -2);
              i10 = 1;
            }
            else if (i6 == -1)
            {
              i9 = getChildMeasureSpec(paramInt2, i1, -1);
              i10 = 0;
            }
            else
            {
              if (i6 == -2) {
                i10 = 1;
              } else {
                i10 = 0;
              }
              i9 = getChildMeasureSpec(paramInt2, i1, i6);
            }
            localView.measure(i8, i9);
            android.support.constraint.a.f localf1 = this.t;
            if (localf1 != null) {
              localf1.a += 1L;
            }
            boolean bool;
            if (i5 == -2) {
              bool = true;
            } else {
              bool = false;
            }
            localf.b(bool);
            if (i6 == -2) {
              bool = true;
            } else {
              bool = false;
            }
            localf.c(bool);
            i8 = localView.getMeasuredWidth();
            i9 = localView.getMeasuredHeight();
          }
          else
          {
            i7 = 0;
            i10 = 0;
            i9 = i6;
            i8 = i5;
          }
          localf.j(i8);
          localf.k(i9);
          if (i7 != 0) {
            localf.n(i8);
          }
          if (i10 != 0) {
            localf.o(i9);
          }
          if (locala.X)
          {
            i7 = localView.getBaseline();
            if (i7 != -1) {
              localf.q(i7);
            }
          }
        }
      }
    }
  }
  
  private final android.support.constraint.a.a.f b(int paramInt)
  {
    if (paramInt == 0) {
      return this.b;
    }
    View localView = (View)this.a.get(paramInt);
    Object localObject = localView;
    if (localView == null)
    {
      localView = findViewById(paramInt);
      localObject = localView;
      if (localView != null)
      {
        localObject = localView;
        if (localView != this)
        {
          localObject = localView;
          if (localView.getParent() == this)
          {
            onViewAdded(localView);
            localObject = localView;
          }
        }
      }
    }
    if (localObject == this) {
      return this.b;
    }
    if (localObject == null) {
      localObject = null;
    } else {
      localObject = ((a)((View)localObject).getLayoutParams()).al;
    }
    return (android.support.constraint.a.a.f)localObject;
  }
  
  private void b()
  {
    int i1 = getChildCount();
    int i2 = 0;
    int i4;
    for (int i3 = 0;; i3++)
    {
      i4 = i2;
      if (i3 >= i1) {
        break;
      }
      if (getChildAt(i3).isLayoutRequested())
      {
        i4 = 1;
        break;
      }
    }
    if (i4 != 0)
    {
      this.h.clear();
      c();
    }
  }
  
  private void b(int paramInt1, int paramInt2)
  {
    Object localObject1 = this;
    int i1 = getPaddingTop() + getPaddingBottom();
    int i2 = getPaddingLeft() + getPaddingRight();
    int i3 = getChildCount();
    long l1;
    View localView;
    Object localObject2;
    android.support.constraint.a.a.f localf;
    int i5;
    int i6;
    int i7;
    int i9;
    Object localObject3;
    boolean bool;
    for (int i4 = 0;; i4++)
    {
      l1 = 1L;
      if (i4 >= i3) {
        break;
      }
      localView = ((ConstraintLayout)localObject1).getChildAt(i4);
      if (localView.getVisibility() != 8)
      {
        localObject2 = (a)localView.getLayoutParams();
        localf = ((a)localObject2).al;
        if ((!((a)localObject2).Y) && (!((a)localObject2).Z))
        {
          localf.e(localView.getVisibility());
          i5 = ((a)localObject2).width;
          i6 = ((a)localObject2).height;
          if ((i5 != 0) && (i6 != 0))
          {
            if (i5 == -2) {
              i7 = 1;
            } else {
              i7 = 0;
            }
            i8 = getChildMeasureSpec(paramInt1, i2, i5);
            if (i6 == -2) {
              i9 = 1;
            } else {
              i9 = 0;
            }
            localView.measure(i8, getChildMeasureSpec(paramInt2, i1, i6));
            localObject3 = ((ConstraintLayout)localObject1).t;
            if (localObject3 != null) {
              ((android.support.constraint.a.f)localObject3).a += 1L;
            }
            if (i5 == -2) {
              bool = true;
            } else {
              bool = false;
            }
            localf.b(bool);
            if (i6 == -2) {
              bool = true;
            } else {
              bool = false;
            }
            localf.c(bool);
            i6 = localView.getMeasuredWidth();
            i5 = localView.getMeasuredHeight();
            localf.j(i6);
            localf.k(i5);
            if (i7 != 0) {
              localf.n(i6);
            }
            if (i9 != 0) {
              localf.o(i5);
            }
            if (((a)localObject2).X)
            {
              i7 = localView.getBaseline();
              if (i7 != -1) {
                localf.q(i7);
              }
            }
            if ((((a)localObject2).V) && (((a)localObject2).W))
            {
              localf.i().a(i6);
              localf.j().a(i5);
            }
          }
          else
          {
            localf.i().e();
            localf.j().e();
          }
        }
      }
    }
    ((ConstraintLayout)localObject1).b.P();
    int i8 = 0;
    while (i8 < i3)
    {
      localView = ((ConstraintLayout)localObject1).getChildAt(i8);
      if (localView.getVisibility() == 8)
      {
        localObject3 = localObject1;
      }
      else
      {
        localObject3 = (a)localView.getLayoutParams();
        localf = ((a)localObject3).al;
        if (!((a)localObject3).Y)
        {
          if (((a)localObject3).Z)
          {
            localObject3 = localObject1;
          }
          else
          {
            localf.e(localView.getVisibility());
            int i10 = ((a)localObject3).width;
            int i11 = ((a)localObject3).height;
            if ((i10 != 0) && (i11 != 0))
            {
              localObject3 = localObject1;
            }
            else
            {
              m localm1 = localf.a(e.c.b).a();
              localObject2 = localf.a(e.c.d).a();
              if ((localf.a(e.c.b).g() != null) && (localf.a(e.c.d).g() != null)) {
                i7 = 1;
              } else {
                i7 = 0;
              }
              m localm2 = localf.a(e.c.c).a();
              m localm3 = localf.a(e.c.e).a();
              if ((localf.a(e.c.c).g() != null) && (localf.a(e.c.e).g() != null)) {
                i6 = 1;
              } else {
                i6 = 0;
              }
              if ((i10 == 0) && (i11 == 0) && (i7 != 0) && (i6 != 0))
              {
                l1 = 1L;
                localObject3 = localObject1;
              }
              else
              {
                if (((ConstraintLayout)localObject1).b.F() != f.a.b) {
                  i5 = 1;
                } else {
                  i5 = 0;
                }
                if (((ConstraintLayout)localObject1).b.G() != f.a.b) {
                  i4 = 1;
                } else {
                  i4 = 0;
                }
                if (i5 == 0) {
                  localf.i().e();
                }
                if (i4 == 0) {
                  localf.j().e();
                }
                int i12;
                if (i10 == 0)
                {
                  if ((i5 != 0) && (localf.d()) && (i7 != 0) && (localm1.g()) && (((m)localObject2).g()))
                  {
                    i10 = (int)(((m)localObject2).d() - localm1.d());
                    localf.i().a(i10);
                    i9 = getChildMeasureSpec(paramInt1, i2, i10);
                    i7 = 0;
                    i12 = i5;
                  }
                  else
                  {
                    i9 = getChildMeasureSpec(paramInt1, i2, -2);
                    i7 = 1;
                    i12 = 0;
                  }
                }
                else if (i10 == -1)
                {
                  i9 = getChildMeasureSpec(paramInt1, i2, -1);
                  i7 = 0;
                  i12 = i5;
                }
                else
                {
                  if (i10 == -2) {
                    i7 = 1;
                  } else {
                    i7 = 0;
                  }
                  i9 = getChildMeasureSpec(paramInt1, i2, i10);
                  i12 = i5;
                }
                if (i11 == 0)
                {
                  if ((i4 != 0) && (localf.e()) && (i6 != 0) && (localm2.g()) && (localm3.g()))
                  {
                    i11 = (int)(localm3.d() - localm2.d());
                    localf.j().a(i11);
                    i5 = getChildMeasureSpec(paramInt2, i1, i11);
                    i6 = i4;
                    i4 = 0;
                  }
                  else
                  {
                    i5 = getChildMeasureSpec(paramInt2, i1, -2);
                    i4 = 1;
                    i6 = 0;
                  }
                }
                else if (i11 == -1)
                {
                  i5 = getChildMeasureSpec(paramInt2, i1, -1);
                  i6 = i4;
                  i4 = 0;
                }
                else
                {
                  if (i11 == -2) {
                    i5 = 1;
                  } else {
                    i5 = 0;
                  }
                  int i13 = getChildMeasureSpec(paramInt2, i1, i11);
                  i6 = i4;
                  i4 = i5;
                  i5 = i13;
                }
                localView.measure(i9, i5);
                localObject1 = this;
                localObject2 = ((ConstraintLayout)localObject1).t;
                if (localObject2 != null) {
                  ((android.support.constraint.a.f)localObject2).a += 1L;
                }
                long l2 = 1L;
                if (i10 == -2) {
                  bool = true;
                } else {
                  bool = false;
                }
                localf.b(bool);
                if (i11 == -2) {
                  bool = true;
                } else {
                  bool = false;
                }
                localf.c(bool);
                i5 = localView.getMeasuredWidth();
                i9 = localView.getMeasuredHeight();
                localf.j(i5);
                localf.k(i9);
                if (i7 != 0) {
                  localf.n(i5);
                }
                if (i4 != 0) {
                  localf.o(i9);
                }
                if (i12 != 0) {
                  localf.i().a(i5);
                } else {
                  localf.i().c();
                }
                if (i6 != 0) {
                  localf.j().a(i9);
                } else {
                  localf.j().c();
                }
                if (((a)localObject3).X)
                {
                  i4 = localView.getBaseline();
                  localObject3 = localObject1;
                  l1 = l2;
                  if (i4 != -1)
                  {
                    localf.q(i4);
                    localObject3 = localObject1;
                    l1 = l2;
                  }
                }
                else
                {
                  localObject3 = localObject1;
                  l1 = l2;
                }
              }
            }
          }
        }
        else {
          localObject3 = localObject1;
        }
      }
      i8++;
      localObject1 = localObject3;
    }
  }
  
  private void b(AttributeSet paramAttributeSet)
  {
    this.b.a(this);
    this.a.put(getId(), this);
    this.o = null;
    if (paramAttributeSet != null)
    {
      paramAttributeSet = getContext().obtainStyledAttributes(paramAttributeSet, g.b.ConstraintLayout_Layout);
      int i1 = paramAttributeSet.getIndexCount();
      for (int i2 = 0; i2 < i1; i2++)
      {
        int i3 = paramAttributeSet.getIndex(i2);
        if (i3 == g.b.ConstraintLayout_Layout_android_minWidth)
        {
          this.i = paramAttributeSet.getDimensionPixelOffset(i3, this.i);
        }
        else if (i3 == g.b.ConstraintLayout_Layout_android_minHeight)
        {
          this.j = paramAttributeSet.getDimensionPixelOffset(i3, this.j);
        }
        else if (i3 == g.b.ConstraintLayout_Layout_android_maxWidth)
        {
          this.k = paramAttributeSet.getDimensionPixelOffset(i3, this.k);
        }
        else if (i3 == g.b.ConstraintLayout_Layout_android_maxHeight)
        {
          this.l = paramAttributeSet.getDimensionPixelOffset(i3, this.l);
        }
        else if (i3 == g.b.ConstraintLayout_Layout_layout_optimizationLevel)
        {
          this.n = paramAttributeSet.getInt(i3, this.n);
        }
        else if (i3 == g.b.ConstraintLayout_Layout_constraintSet)
        {
          i3 = paramAttributeSet.getResourceId(i3, 0);
          try
          {
            c localc = new android/support/constraint/c;
            localc.<init>();
            this.o = localc;
            this.o.a(getContext(), i3);
          }
          catch (Resources.NotFoundException localNotFoundException)
          {
            this.o = null;
          }
          this.p = i3;
        }
      }
      paramAttributeSet.recycle();
    }
    this.b.a(this.n);
  }
  
  private void c()
  {
    boolean bool = isInEditMode();
    int i1 = getChildCount();
    int i2 = 0;
    if (bool)
    {
      i3 = 0;
      while (i3 < i1)
      {
        Object localObject1 = getChildAt(i3);
        try
        {
          localObject2 = getResources().getResourceName(((View)localObject1).getId());
          a(0, localObject2, Integer.valueOf(((View)localObject1).getId()));
          i4 = ((String)localObject2).indexOf('/');
          localObject3 = localObject2;
          if (i4 != -1) {
            localObject3 = ((String)localObject2).substring(i4 + 1);
          }
          b(((View)localObject1).getId()).a((String)localObject3);
          i3++;
        }
        catch (Resources.NotFoundException localNotFoundException1)
        {
          Object localObject2;
          int i4;
          Object localObject3;
          int i5;
          for (;;) {}
        }
      }
    }
    for (int i3 = 0; i3 < i1; i3++)
    {
      localObject3 = a(getChildAt(i3));
      if (localObject3 != null) {
        ((android.support.constraint.a.a.f)localObject3).f();
      }
    }
    if (this.p != -1) {
      for (i3 = 0; i3 < i1; i3++)
      {
        localObject3 = getChildAt(i3);
        if ((((View)localObject3).getId() == this.p) && ((localObject3 instanceof d))) {
          this.o = ((d)localObject3).getConstraintSet();
        }
      }
    }
    localObject3 = this.o;
    if (localObject3 != null) {
      ((c)localObject3).a(this);
    }
    this.b.U();
    i4 = this.g.size();
    if (i4 > 0) {
      for (i3 = 0; i3 < i4; i3++) {
        ((b)this.g.get(i3)).a(this);
      }
    }
    for (i3 = 0; i3 < i1; i3++)
    {
      localObject3 = getChildAt(i3);
      if ((localObject3 instanceof f)) {
        ((f)localObject3).a(this);
      }
    }
    i5 = 0;
    for (i3 = i2; i5 < i1; i3 = i4)
    {
      localObject1 = getChildAt(i5);
      localObject2 = a((View)localObject1);
      if (localObject2 == null)
      {
        i4 = i3;
      }
      else
      {
        localObject3 = (a)((View)localObject1).getLayoutParams();
        ((a)localObject3).a();
        if (((a)localObject3).am) {
          ((a)localObject3).am = i3;
        } else if (bool) {
          try
          {
            String str = getResources().getResourceName(((View)localObject1).getId());
            a(i3, str, Integer.valueOf(((View)localObject1).getId()));
            str = str.substring(str.indexOf("id/") + 3);
            b(((View)localObject1).getId()).a(str);
          }
          catch (Resources.NotFoundException localNotFoundException2) {}
        }
        ((android.support.constraint.a.a.f)localObject2).e(((View)localObject1).getVisibility());
        if (((a)localObject3).aa) {
          ((android.support.constraint.a.a.f)localObject2).e(8);
        }
        ((android.support.constraint.a.a.f)localObject2).a(localObject1);
        this.b.b((android.support.constraint.a.a.f)localObject2);
        if ((!((a)localObject3).W) || (!((a)localObject3).V)) {
          this.h.add(localObject2);
        }
        float f1;
        if (((a)localObject3).Y)
        {
          localObject2 = (i)localObject2;
          i4 = ((a)localObject3).ai;
          i2 = ((a)localObject3).aj;
          f1 = ((a)localObject3).ak;
          if (Build.VERSION.SDK_INT < 17)
          {
            i4 = ((a)localObject3).a;
            i2 = ((a)localObject3).b;
            f1 = ((a)localObject3).c;
          }
          if (f1 != -1.0F)
          {
            ((i)localObject2).e(f1);
            i4 = i3;
          }
          else if (i4 != -1)
          {
            ((i)localObject2).u(i4);
            i4 = i3;
          }
          else
          {
            i4 = i3;
            if (i2 != -1)
            {
              ((i)localObject2).v(i2);
              i4 = i3;
            }
          }
        }
        else if ((((a)localObject3).d == -1) && (((a)localObject3).e == -1) && (((a)localObject3).f == -1) && (((a)localObject3).g == -1) && (((a)localObject3).q == -1) && (((a)localObject3).p == -1) && (((a)localObject3).r == -1) && (((a)localObject3).s == -1) && (((a)localObject3).h == -1) && (((a)localObject3).i == -1) && (((a)localObject3).j == -1) && (((a)localObject3).k == -1) && (((a)localObject3).l == -1) && (((a)localObject3).Q == -1) && (((a)localObject3).R == -1) && (((a)localObject3).m == -1) && (((a)localObject3).width != -1))
        {
          i4 = i3;
          if (((a)localObject3).height != -1) {}
        }
        else
        {
          int i6 = ((a)localObject3).ab;
          int i7 = ((a)localObject3).ac;
          i4 = ((a)localObject3).ad;
          i3 = ((a)localObject3).ae;
          i2 = ((a)localObject3).af;
          int i8 = ((a)localObject3).ag;
          f1 = ((a)localObject3).ah;
          if (Build.VERSION.SDK_INT < 17)
          {
            i2 = ((a)localObject3).d;
            i3 = ((a)localObject3).e;
            i7 = ((a)localObject3).f;
            i6 = ((a)localObject3).g;
            i8 = ((a)localObject3).t;
            i4 = ((a)localObject3).v;
            f1 = ((a)localObject3).z;
            if ((i2 == -1) && (i3 == -1))
            {
              if (((a)localObject3).q != -1)
              {
                i9 = ((a)localObject3).q;
                i2 = i3;
                i3 = i9;
                break label1005;
              }
              if (((a)localObject3).p != -1)
              {
                i9 = ((a)localObject3).p;
                i3 = i2;
                i2 = i9;
                break label1005;
              }
            }
            int i9 = i3;
            i3 = i2;
            i2 = i9;
            label1005:
            if ((i7 == -1) && (i6 == -1))
            {
              if (((a)localObject3).r != -1)
              {
                i7 = ((a)localObject3).r;
                i9 = i8;
                i8 = i4;
                i10 = i6;
                i4 = i7;
                i7 = i2;
                i6 = i3;
                i3 = i10;
                i2 = i9;
                break label1144;
              }
              if (((a)localObject3).s != -1)
              {
                i10 = ((a)localObject3).s;
                i9 = i8;
                i8 = i4;
                i4 = i7;
                i7 = i2;
                i6 = i3;
                i3 = i10;
                i2 = i9;
                break label1144;
              }
            }
            i9 = i8;
            i8 = i4;
            int i10 = i6;
            i4 = i7;
            i7 = i2;
            i6 = i3;
            i3 = i10;
            i2 = i9;
          }
          label1144:
          if (((a)localObject3).m != -1)
          {
            localObject1 = b(((a)localObject3).m);
            if (localObject1 != null) {
              ((android.support.constraint.a.a.f)localObject2).a((android.support.constraint.a.a.f)localObject1, ((a)localObject3).o, ((a)localObject3).n);
            }
          }
          else
          {
            if (i6 != -1)
            {
              localObject1 = b(i6);
              if (localObject1 != null) {
                ((android.support.constraint.a.a.f)localObject2).a(e.c.b, (android.support.constraint.a.a.f)localObject1, e.c.b, ((a)localObject3).leftMargin, i2);
              }
            }
            else
            {
              i6 = i3;
              i3 = i6;
              if (i7 != -1)
              {
                localObject1 = b(i7);
                i3 = i6;
                if (localObject1 != null)
                {
                  ((android.support.constraint.a.a.f)localObject2).a(e.c.b, (android.support.constraint.a.a.f)localObject1, e.c.d, ((a)localObject3).leftMargin, i2);
                  i3 = i6;
                }
              }
            }
            if (i4 != -1)
            {
              localObject1 = b(i4);
              if (localObject1 != null) {
                ((android.support.constraint.a.a.f)localObject2).a(e.c.d, (android.support.constraint.a.a.f)localObject1, e.c.b, ((a)localObject3).rightMargin, i8);
              }
            }
            else if (i3 != -1)
            {
              localObject1 = b(i3);
              if (localObject1 != null) {
                ((android.support.constraint.a.a.f)localObject2).a(e.c.d, (android.support.constraint.a.a.f)localObject1, e.c.d, ((a)localObject3).rightMargin, i8);
              }
            }
            if (((a)localObject3).h != -1)
            {
              localObject1 = b(((a)localObject3).h);
              if (localObject1 != null) {
                ((android.support.constraint.a.a.f)localObject2).a(e.c.c, (android.support.constraint.a.a.f)localObject1, e.c.c, ((a)localObject3).topMargin, ((a)localObject3).u);
              }
            }
            else if (((a)localObject3).i != -1)
            {
              localObject1 = b(((a)localObject3).i);
              if (localObject1 != null) {
                ((android.support.constraint.a.a.f)localObject2).a(e.c.c, (android.support.constraint.a.a.f)localObject1, e.c.e, ((a)localObject3).topMargin, ((a)localObject3).u);
              }
            }
            if (((a)localObject3).j != -1)
            {
              localObject1 = b(((a)localObject3).j);
              if (localObject1 != null) {
                ((android.support.constraint.a.a.f)localObject2).a(e.c.e, (android.support.constraint.a.a.f)localObject1, e.c.c, ((a)localObject3).bottomMargin, ((a)localObject3).w);
              }
            }
            else if (((a)localObject3).k != -1)
            {
              localObject1 = b(((a)localObject3).k);
              if (localObject1 != null) {
                ((android.support.constraint.a.a.f)localObject2).a(e.c.e, (android.support.constraint.a.a.f)localObject1, e.c.e, ((a)localObject3).bottomMargin, ((a)localObject3).w);
              }
            }
            if (((a)localObject3).l != -1)
            {
              Object localObject4 = (View)this.a.get(((a)localObject3).l);
              localObject1 = b(((a)localObject3).l);
              if ((localObject1 != null) && (localObject4 != null) && ((((View)localObject4).getLayoutParams() instanceof a)))
              {
                localObject4 = (a)((View)localObject4).getLayoutParams();
                ((a)localObject3).X = true;
                ((a)localObject4).X = true;
                ((android.support.constraint.a.a.f)localObject2).a(e.c.f).a(((android.support.constraint.a.a.f)localObject1).a(e.c.f), 0, -1, e.b.b, 0, true);
                ((android.support.constraint.a.a.f)localObject2).a(e.c.c).i();
                ((android.support.constraint.a.a.f)localObject2).a(e.c.e).i();
              }
            }
            if ((f1 >= 0.0F) && (f1 != 0.5F)) {
              ((android.support.constraint.a.a.f)localObject2).a(f1);
            }
            if ((((a)localObject3).A >= 0.0F) && (((a)localObject3).A != 0.5F)) {
              ((android.support.constraint.a.a.f)localObject2).b(((a)localObject3).A);
            }
          }
          if ((bool) && ((((a)localObject3).Q != -1) || (((a)localObject3).R != -1))) {
            ((android.support.constraint.a.a.f)localObject2).a(((a)localObject3).Q, ((a)localObject3).R);
          }
          if (!((a)localObject3).V)
          {
            if (((a)localObject3).width == -1)
            {
              ((android.support.constraint.a.a.f)localObject2).a(f.a.d);
              ((android.support.constraint.a.a.f)localObject2).a(e.c.b).d = ((a)localObject3).leftMargin;
              ((android.support.constraint.a.a.f)localObject2).a(e.c.d).d = ((a)localObject3).rightMargin;
            }
            else
            {
              ((android.support.constraint.a.a.f)localObject2).a(f.a.c);
              ((android.support.constraint.a.a.f)localObject2).j(0);
            }
          }
          else
          {
            ((android.support.constraint.a.a.f)localObject2).a(f.a.a);
            ((android.support.constraint.a.a.f)localObject2).j(((a)localObject3).width);
          }
          if (!((a)localObject3).W)
          {
            if (((a)localObject3).height == -1)
            {
              ((android.support.constraint.a.a.f)localObject2).b(f.a.d);
              ((android.support.constraint.a.a.f)localObject2).a(e.c.c).d = ((a)localObject3).topMargin;
              ((android.support.constraint.a.a.f)localObject2).a(e.c.e).d = ((a)localObject3).bottomMargin;
            }
            else
            {
              ((android.support.constraint.a.a.f)localObject2).b(f.a.c);
              ((android.support.constraint.a.a.f)localObject2).k(0);
            }
          }
          else
          {
            ((android.support.constraint.a.a.f)localObject2).b(f.a.a);
            ((android.support.constraint.a.a.f)localObject2).k(((a)localObject3).height);
          }
          i4 = 0;
          if (((a)localObject3).B != null) {
            ((android.support.constraint.a.a.f)localObject2).b(((a)localObject3).B);
          }
          ((android.support.constraint.a.a.f)localObject2).c(((a)localObject3).E);
          ((android.support.constraint.a.a.f)localObject2).d(((a)localObject3).F);
          ((android.support.constraint.a.a.f)localObject2).r(((a)localObject3).G);
          ((android.support.constraint.a.a.f)localObject2).s(((a)localObject3).H);
          ((android.support.constraint.a.a.f)localObject2).a(((a)localObject3).I, ((a)localObject3).K, ((a)localObject3).M, ((a)localObject3).O);
          ((android.support.constraint.a.a.f)localObject2).b(((a)localObject3).J, ((a)localObject3).L, ((a)localObject3).N, ((a)localObject3).P);
        }
      }
      i5++;
    }
  }
  
  private void c(int paramInt1, int paramInt2)
  {
    int i1 = View.MeasureSpec.getMode(paramInt1);
    paramInt1 = View.MeasureSpec.getSize(paramInt1);
    int i2 = View.MeasureSpec.getMode(paramInt2);
    paramInt2 = View.MeasureSpec.getSize(paramInt2);
    int i3 = getPaddingTop();
    int i4 = getPaddingBottom();
    int i5 = getPaddingLeft();
    int i6 = getPaddingRight();
    f.a locala1 = f.a.a;
    f.a locala2 = f.a.a;
    getLayoutParams();
    if (i1 != Integer.MIN_VALUE)
    {
      if (i1 != 0)
      {
        if (i1 != 1073741824) {
          paramInt1 = 0;
        } else {
          paramInt1 = Math.min(this.k, paramInt1) - (i5 + i6);
        }
      }
      else
      {
        locala1 = f.a.b;
        paramInt1 = 0;
      }
    }
    else {
      locala1 = f.a.b;
    }
    if (i2 != Integer.MIN_VALUE)
    {
      if (i2 != 0)
      {
        if (i2 != 1073741824) {
          paramInt2 = 0;
        } else {
          paramInt2 = Math.min(this.l, paramInt2) - (i3 + i4);
        }
      }
      else
      {
        locala2 = f.a.b;
        paramInt2 = 0;
      }
    }
    else {
      locala2 = f.a.b;
    }
    this.b.l(0);
    this.b.m(0);
    this.b.a(locala1);
    this.b.j(paramInt1);
    this.b.b(locala2);
    this.b.k(paramInt2);
    this.b.l(this.i - getPaddingLeft() - getPaddingRight());
    this.b.m(this.j - getPaddingTop() - getPaddingBottom());
  }
  
  private void d()
  {
    int i1 = getChildCount();
    int i2 = 0;
    for (int i3 = 0; i3 < i1; i3++)
    {
      View localView = getChildAt(i3);
      if ((localView instanceof f)) {
        ((f)localView).b(this);
      }
    }
    i1 = this.g.size();
    if (i1 > 0) {
      for (i3 = i2; i3 < i1; i3++) {
        ((b)this.g.get(i3)).c(this);
      }
    }
  }
  
  protected a a()
  {
    return new a(-2, -2);
  }
  
  public a a(AttributeSet paramAttributeSet)
  {
    return new a(getContext(), paramAttributeSet);
  }
  
  public final android.support.constraint.a.a.f a(View paramView)
  {
    if (paramView == this) {
      return this.b;
    }
    if (paramView == null) {
      paramView = null;
    } else {
      paramView = ((a)paramView.getLayoutParams()).al;
    }
    return paramView;
  }
  
  public View a(int paramInt)
  {
    return (View)this.a.get(paramInt);
  }
  
  public Object a(int paramInt, Object paramObject)
  {
    if ((paramInt == 0) && ((paramObject instanceof String)))
    {
      String str = (String)paramObject;
      paramObject = this.q;
      if ((paramObject != null) && (((HashMap)paramObject).containsKey(str))) {
        return this.q.get(str);
      }
    }
    return null;
  }
  
  public void a(int paramInt, Object paramObject1, Object paramObject2)
  {
    if ((paramInt == 0) && ((paramObject1 instanceof String)) && ((paramObject2 instanceof Integer)))
    {
      if (this.q == null) {
        this.q = new HashMap();
      }
      String str = (String)paramObject1;
      paramInt = str.indexOf("/");
      paramObject1 = str;
      if (paramInt != -1) {
        paramObject1 = str.substring(paramInt + 1);
      }
      paramInt = ((Integer)paramObject2).intValue();
      this.q.put(paramObject1, Integer.valueOf(paramInt));
    }
  }
  
  protected void a(String paramString)
  {
    this.b.N();
    paramString = this.t;
    if (paramString != null) {
      paramString.c += 1L;
    }
  }
  
  public void addView(View paramView, int paramInt, ViewGroup.LayoutParams paramLayoutParams)
  {
    super.addView(paramView, paramInt, paramLayoutParams);
    if (Build.VERSION.SDK_INT < 14) {
      onViewAdded(paramView);
    }
  }
  
  protected boolean checkLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
  {
    return paramLayoutParams instanceof a;
  }
  
  public void dispatchDraw(Canvas paramCanvas)
  {
    super.dispatchDraw(paramCanvas);
    if (isInEditMode())
    {
      int i1 = getChildCount();
      float f1 = getWidth();
      float f2 = getHeight();
      for (int i2 = 0; i2 < i1; i2++)
      {
        Object localObject = getChildAt(i2);
        if (((View)localObject).getVisibility() != 8)
        {
          localObject = ((View)localObject).getTag();
          if ((localObject != null) && ((localObject instanceof String)))
          {
            localObject = ((String)localObject).split(",");
            if (localObject.length == 4)
            {
              int i3 = Integer.parseInt(localObject[0]);
              int i4 = Integer.parseInt(localObject[1]);
              int i5 = Integer.parseInt(localObject[2]);
              int i6 = Integer.parseInt(localObject[3]);
              i3 = (int)(i3 / 1080.0F * f1);
              i4 = (int)(i4 / 1920.0F * f2);
              i5 = (int)(i5 / 1080.0F * f1);
              i6 = (int)(i6 / 1920.0F * f2);
              localObject = new Paint();
              ((Paint)localObject).setColor(-65536);
              float f3 = i3;
              float f4 = i4;
              float f5 = i3 + i5;
              paramCanvas.drawLine(f3, f4, f5, f4, (Paint)localObject);
              float f6 = i4 + i6;
              paramCanvas.drawLine(f5, f4, f5, f6, (Paint)localObject);
              paramCanvas.drawLine(f5, f6, f3, f6, (Paint)localObject);
              paramCanvas.drawLine(f3, f6, f3, f4, (Paint)localObject);
              ((Paint)localObject).setColor(-16711936);
              paramCanvas.drawLine(f3, f4, f5, f6, (Paint)localObject);
              paramCanvas.drawLine(f3, f6, f5, f4, (Paint)localObject);
            }
          }
        }
      }
    }
  }
  
  protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
  {
    return new a(paramLayoutParams);
  }
  
  public int getMaxHeight()
  {
    return this.l;
  }
  
  public int getMaxWidth()
  {
    return this.k;
  }
  
  public int getMinHeight()
  {
    return this.j;
  }
  
  public int getMinWidth()
  {
    return this.i;
  }
  
  public int getOptimizationLevel()
  {
    return this.b.J();
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    paramInt3 = getChildCount();
    paramBoolean = isInEditMode();
    paramInt2 = 0;
    for (paramInt1 = 0; paramInt1 < paramInt3; paramInt1++)
    {
      View localView = getChildAt(paramInt1);
      a locala = (a)localView.getLayoutParams();
      Object localObject = locala.al;
      if (((localView.getVisibility() != 8) || (locala.Y) || (locala.Z) || (paramBoolean)) && (!locala.aa))
      {
        int i1 = ((android.support.constraint.a.a.f)localObject).t();
        int i2 = ((android.support.constraint.a.a.f)localObject).u();
        paramInt4 = ((android.support.constraint.a.a.f)localObject).p() + i1;
        int i3 = ((android.support.constraint.a.a.f)localObject).r() + i2;
        localView.layout(i1, i2, paramInt4, i3);
        if ((localView instanceof f))
        {
          localObject = ((f)localView).getContent();
          if (localObject != null)
          {
            ((View)localObject).setVisibility(0);
            ((View)localObject).layout(i1, i2, paramInt4, i3);
          }
        }
      }
    }
    paramInt3 = this.g.size();
    if (paramInt3 > 0) {
      for (paramInt1 = paramInt2; paramInt1 < paramInt3; paramInt1++) {
        ((b)this.g.get(paramInt1)).b(this);
      }
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    System.currentTimeMillis();
    int i1 = View.MeasureSpec.getMode(paramInt1);
    int i2 = View.MeasureSpec.getSize(paramInt1);
    int i3 = View.MeasureSpec.getMode(paramInt2);
    int i4 = View.MeasureSpec.getSize(paramInt2);
    int i5 = getPaddingLeft();
    int i6 = getPaddingTop();
    this.b.h(i5);
    this.b.i(i6);
    this.b.c(this.k);
    this.b.d(this.l);
    Object localObject;
    if (Build.VERSION.SDK_INT >= 17)
    {
      localObject = this.b;
      boolean bool;
      if (getLayoutDirection() == 1) {
        bool = true;
      } else {
        bool = false;
      }
      ((g)localObject).a(bool);
    }
    c(paramInt1, paramInt2);
    int i7 = this.b.p();
    int i8 = this.b.r();
    if (this.m)
    {
      this.m = false;
      b();
      i9 = 1;
    }
    else
    {
      i9 = 0;
    }
    int i10;
    if ((this.n & 0x8) == 8) {
      i10 = 1;
    } else {
      i10 = 0;
    }
    if (i10 != 0)
    {
      this.b.O();
      this.b.f(i7, i8);
      b(paramInt1, paramInt2);
    }
    else
    {
      a(paramInt1, paramInt2);
    }
    d();
    if ((getChildCount() > 0) && (i9 != 0)) {
      a.a(this.b);
    }
    if (this.b.as)
    {
      if ((this.b.at) && (i1 == Integer.MIN_VALUE))
      {
        if (this.b.av < i2)
        {
          localObject = this.b;
          ((g)localObject).j(((g)localObject).av);
        }
        this.b.a(f.a.a);
      }
      if ((this.b.au) && (i3 == Integer.MIN_VALUE))
      {
        if (this.b.aw < i4)
        {
          localObject = this.b;
          ((g)localObject).k(((g)localObject).aw);
        }
        this.b.b(f.a.a);
      }
    }
    if ((this.n & 0x20) == 32)
    {
      i11 = this.b.p();
      i9 = this.b.r();
      if ((this.r != i11) && (i1 == 1073741824)) {
        a.a(this.b.ar, 0, i11);
      }
      if ((this.s != i9) && (i3 == 1073741824)) {
        a.a(this.b.ar, 1, i9);
      }
      if ((this.b.at) && (this.b.av > i2)) {
        a.a(this.b.ar, 0, i2);
      }
      if ((this.b.au) && (this.b.aw > i4)) {
        a.a(this.b.ar, 1, i4);
      } else {}
    }
    if (getChildCount() > 0) {
      a("First pass");
    }
    int i11 = this.h.size();
    int i12 = i6 + getPaddingBottom();
    int i13 = i5 + getPaddingRight();
    if (i11 > 0)
    {
      if (this.b.F() == f.a.b) {
        i2 = 1;
      } else {
        i2 = 0;
      }
      if (this.b.G() == f.a.b) {
        i3 = 1;
      } else {
        i3 = 0;
      }
      i6 = Math.max(this.b.p(), this.i);
      i5 = Math.max(this.b.r(), this.j);
      int i14 = 0;
      i1 = 0;
      i9 = 0;
      View localView;
      while (i14 < i11)
      {
        localObject = (android.support.constraint.a.a.f)this.h.get(i14);
        localView = (View)((android.support.constraint.a.a.f)localObject).B();
        if (localView != null)
        {
          a locala = (a)localView.getLayoutParams();
          if ((!locala.Z) && (!locala.Y))
          {
            i4 = localView.getVisibility();
            int i15 = i1;
            if ((i4 != 8) && ((i10 == 0) || (!((android.support.constraint.a.a.f)localObject).i().g()) || (!((android.support.constraint.a.a.f)localObject).j().g())))
            {
              if ((locala.width == -2) && (locala.V)) {
                i1 = getChildMeasureSpec(paramInt1, i13, locala.width);
              } else {
                i1 = View.MeasureSpec.makeMeasureSpec(((android.support.constraint.a.a.f)localObject).p(), 1073741824);
              }
              if ((locala.height == -2) && (locala.W)) {
                i4 = getChildMeasureSpec(paramInt2, i12, locala.height);
              } else {
                i4 = View.MeasureSpec.makeMeasureSpec(((android.support.constraint.a.a.f)localObject).r(), 1073741824);
              }
              localView.measure(i1, i4);
              android.support.constraint.a.f localf = this.t;
              if (localf != null) {
                localf.b += 1L;
              }
              int i16 = localView.getMeasuredWidth();
              int i17 = localView.getMeasuredHeight();
              i4 = i6;
              i1 = i15;
              if (i16 != ((android.support.constraint.a.a.f)localObject).p())
              {
                ((android.support.constraint.a.a.f)localObject).j(i16);
                if (i10 != 0) {
                  ((android.support.constraint.a.a.f)localObject).i().a(i16);
                }
                i4 = i6;
                if (i2 != 0)
                {
                  i4 = i6;
                  if (((android.support.constraint.a.a.f)localObject).x() > i6) {
                    i4 = Math.max(i6, ((android.support.constraint.a.a.f)localObject).x() + ((android.support.constraint.a.a.f)localObject).a(e.c.d).e());
                  }
                }
                i1 = 1;
              }
              i6 = i5;
              if (i17 != ((android.support.constraint.a.a.f)localObject).r())
              {
                ((android.support.constraint.a.a.f)localObject).k(i17);
                if (i10 != 0) {
                  ((android.support.constraint.a.a.f)localObject).j().a(i17);
                }
                i6 = i5;
                if (i3 != 0)
                {
                  i6 = i5;
                  if (((android.support.constraint.a.a.f)localObject).y() > i5) {
                    i6 = Math.max(i5, ((android.support.constraint.a.a.f)localObject).y() + ((android.support.constraint.a.a.f)localObject).a(e.c.e).e());
                  }
                }
                i1 = 1;
              }
              i5 = i1;
              if (locala.X)
              {
                i15 = localView.getBaseline();
                i5 = i1;
                if (i15 != -1)
                {
                  i5 = i1;
                  if (i15 != ((android.support.constraint.a.a.f)localObject).A())
                  {
                    ((android.support.constraint.a.a.f)localObject).q(i15);
                    i5 = 1;
                  }
                }
              }
              if (Build.VERSION.SDK_INT >= 11)
              {
                i9 = combineMeasuredStates(i9, localView.getMeasuredState());
                i1 = i5;
                i5 = i6;
                i6 = i4;
              }
              else
              {
                i1 = i5;
                i5 = i6;
                i6 = i4;
              }
            }
          }
        }
        i14++;
      }
      i4 = i9;
      if (i1 != 0)
      {
        this.b.j(i7);
        this.b.k(i8);
        if (i10 != 0) {
          this.b.P();
        }
        a("2nd pass");
        if (this.b.p() < i6)
        {
          this.b.j(i6);
          i9 = 1;
        }
        else
        {
          i9 = 0;
        }
        if (this.b.r() < i5)
        {
          this.b.k(i5);
          i9 = 1;
        }
        if (i9 != 0) {
          a("3rd pass");
        }
      }
      for (i5 = 0;; i5++)
      {
        i9 = i4;
        if (i5 >= i11) {
          break;
        }
        localObject = (android.support.constraint.a.a.f)this.h.get(i5);
        localView = (View)((android.support.constraint.a.a.f)localObject).B();
        if (localView != null)
        {
          if ((localView.getMeasuredWidth() == ((android.support.constraint.a.a.f)localObject).p()) && (localView.getMeasuredHeight() == ((android.support.constraint.a.a.f)localObject).r())) {}
          if (((android.support.constraint.a.a.f)localObject).l() != 8)
          {
            localView.measure(View.MeasureSpec.makeMeasureSpec(((android.support.constraint.a.a.f)localObject).p(), 1073741824), View.MeasureSpec.makeMeasureSpec(((android.support.constraint.a.a.f)localObject).r(), 1073741824));
            localObject = this.t;
            if (localObject != null) {
              ((android.support.constraint.a.f)localObject).b += 1L;
            }
          }
        }
      }
    }
    int i9 = 0;
    i5 = this.b.p() + i13;
    i6 = this.b.r() + i12;
    if (Build.VERSION.SDK_INT >= 11)
    {
      paramInt1 = resolveSizeAndState(i5, paramInt1, i9);
      i9 = resolveSizeAndState(i6, paramInt2, i9 << 16);
      paramInt2 = Math.min(this.k, paramInt1 & 0xFFFFFF);
      i9 = Math.min(this.l, i9 & 0xFFFFFF);
      paramInt1 = paramInt2;
      if (this.b.K()) {
        paramInt1 = paramInt2 | 0x1000000;
      }
      paramInt2 = i9;
      if (this.b.L()) {
        paramInt2 = i9 | 0x1000000;
      }
      setMeasuredDimension(paramInt1, paramInt2);
      this.r = paramInt1;
      this.s = paramInt2;
    }
    else
    {
      setMeasuredDimension(i5, i6);
      this.r = i5;
      this.s = i6;
    }
  }
  
  public void onViewAdded(View paramView)
  {
    if (Build.VERSION.SDK_INT >= 14) {
      super.onViewAdded(paramView);
    }
    Object localObject = a(paramView);
    if (((paramView instanceof e)) && (!(localObject instanceof i)))
    {
      localObject = (a)paramView.getLayoutParams();
      ((a)localObject).al = new i();
      ((a)localObject).Y = true;
      ((i)((a)localObject).al).a(((a)localObject).S);
    }
    if ((paramView instanceof b))
    {
      localObject = (b)paramView;
      ((b)localObject).a();
      ((a)paramView.getLayoutParams()).Z = true;
      if (!this.g.contains(localObject)) {
        this.g.add(localObject);
      }
    }
    this.a.put(paramView.getId(), paramView);
    this.m = true;
  }
  
  public void onViewRemoved(View paramView)
  {
    if (Build.VERSION.SDK_INT >= 14) {
      super.onViewRemoved(paramView);
    }
    this.a.remove(paramView.getId());
    android.support.constraint.a.a.f localf = a(paramView);
    this.b.c(localf);
    this.g.remove(paramView);
    this.h.remove(localf);
    this.m = true;
  }
  
  public void removeView(View paramView)
  {
    super.removeView(paramView);
    if (Build.VERSION.SDK_INT < 14) {
      onViewRemoved(paramView);
    }
  }
  
  public void requestLayout()
  {
    super.requestLayout();
    this.m = true;
    this.r = -1;
    this.s = -1;
    this.c = -1;
    this.d = -1;
    this.e = 0;
    this.f = 0;
  }
  
  public void setConstraintSet(c paramc)
  {
    this.o = paramc;
  }
  
  public void setId(int paramInt)
  {
    this.a.remove(getId());
    super.setId(paramInt);
    this.a.put(getId(), this);
  }
  
  public void setMaxHeight(int paramInt)
  {
    if (paramInt == this.l) {
      return;
    }
    this.l = paramInt;
    requestLayout();
  }
  
  public void setMaxWidth(int paramInt)
  {
    if (paramInt == this.k) {
      return;
    }
    this.k = paramInt;
    requestLayout();
  }
  
  public void setMinHeight(int paramInt)
  {
    if (paramInt == this.j) {
      return;
    }
    this.j = paramInt;
    requestLayout();
  }
  
  public void setMinWidth(int paramInt)
  {
    if (paramInt == this.i) {
      return;
    }
    this.i = paramInt;
    requestLayout();
  }
  
  public void setOptimizationLevel(int paramInt)
  {
    this.b.a(paramInt);
  }
  
  public boolean shouldDelayChildPressedState()
  {
    return false;
  }
  
  public static class a
    extends ViewGroup.MarginLayoutParams
  {
    public float A = 0.5F;
    public String B = null;
    float C = 0.0F;
    int D = 1;
    public float E = -1.0F;
    public float F = -1.0F;
    public int G = 0;
    public int H = 0;
    public int I = 0;
    public int J = 0;
    public int K = 0;
    public int L = 0;
    public int M = 0;
    public int N = 0;
    public float O = 1.0F;
    public float P = 1.0F;
    public int Q = -1;
    public int R = -1;
    public int S = -1;
    public boolean T = false;
    public boolean U = false;
    boolean V = true;
    boolean W = true;
    boolean X = false;
    boolean Y = false;
    boolean Z = false;
    public int a = -1;
    boolean aa = false;
    int ab = -1;
    int ac = -1;
    int ad = -1;
    int ae = -1;
    int af = -1;
    int ag = -1;
    float ah = 0.5F;
    int ai;
    int aj;
    float ak;
    android.support.constraint.a.a.f al = new android.support.constraint.a.a.f();
    public boolean am = false;
    public int b = -1;
    public float c = -1.0F;
    public int d = -1;
    public int e = -1;
    public int f = -1;
    public int g = -1;
    public int h = -1;
    public int i = -1;
    public int j = -1;
    public int k = -1;
    public int l = -1;
    public int m = -1;
    public int n = 0;
    public float o = 0.0F;
    public int p = -1;
    public int q = -1;
    public int r = -1;
    public int s = -1;
    public int t = -1;
    public int u = -1;
    public int v = -1;
    public int w = -1;
    public int x = -1;
    public int y = -1;
    public float z = 0.5F;
    
    public a(int paramInt1, int paramInt2)
    {
      super(paramInt2);
    }
    
    public a(Context paramContext, AttributeSet paramAttributeSet)
    {
      super(paramAttributeSet);
      paramContext = paramContext.obtainStyledAttributes(paramAttributeSet, g.b.ConstraintLayout_Layout);
      int i1 = paramContext.getIndexCount();
      for (int i2 = 0; i2 < i1; i2++)
      {
        int i3 = paramContext.getIndex(i2);
        String str;
        switch (a.a.get(i3))
        {
        case 43: 
        default: 
          break;
        case 50: 
          this.R = paramContext.getDimensionPixelOffset(i3, this.R);
          break;
        case 49: 
          this.Q = paramContext.getDimensionPixelOffset(i3, this.Q);
          break;
        case 48: 
          this.H = paramContext.getInt(i3, 0);
          break;
        case 47: 
          this.G = paramContext.getInt(i3, 0);
          break;
        case 46: 
          this.F = paramContext.getFloat(i3, this.F);
          break;
        case 45: 
          this.E = paramContext.getFloat(i3, this.E);
          break;
        case 44: 
          this.B = paramContext.getString(i3);
          this.C = NaN.0F;
          this.D = -1;
          paramAttributeSet = this.B;
          if (paramAttributeSet != null)
          {
            int i4 = paramAttributeSet.length();
            i3 = this.B.indexOf(',');
            if ((i3 > 0) && (i3 < i4 - 1))
            {
              paramAttributeSet = this.B.substring(0, i3);
              if (paramAttributeSet.equalsIgnoreCase("W")) {
                this.D = 0;
              } else if (paramAttributeSet.equalsIgnoreCase("H")) {
                this.D = 1;
              }
              i3++;
            }
            else
            {
              i3 = 0;
            }
            int i5 = this.B.indexOf(':');
            if ((i5 >= 0) && (i5 < i4 - 1))
            {
              paramAttributeSet = this.B.substring(i3, i5);
              str = this.B.substring(i5 + 1);
              if ((paramAttributeSet.length() <= 0) || (str.length() <= 0)) {
                continue;
              }
            }
          }
          break;
        }
        try
        {
          float f1 = Float.parseFloat(paramAttributeSet);
          f2 = Float.parseFloat(str);
          if ((f1 <= 0.0F) || (f2 <= 0.0F)) {
            continue;
          }
          if (this.D == 1) {
            this.C = Math.abs(f2 / f1);
          } else {
            this.C = Math.abs(f1 / f2);
          }
        }
        catch (NumberFormatException paramAttributeSet)
        {
          float f2;
          continue;
        }
        paramAttributeSet = this.B.substring(i3);
        if (paramAttributeSet.length() > 0)
        {
          this.C = Float.parseFloat(paramAttributeSet);
          continue;
          this.P = Math.max(0.0F, paramContext.getFloat(i3, this.P));
          continue;
          try
          {
            this.N = paramContext.getDimensionPixelSize(i3, this.N);
          }
          catch (Exception paramAttributeSet)
          {
            if (paramContext.getInt(i3, this.N) != -2) {
              continue;
            }
          }
          this.N = -2;
          continue;
          try
          {
            this.L = paramContext.getDimensionPixelSize(i3, this.L);
          }
          catch (Exception paramAttributeSet)
          {
            if (paramContext.getInt(i3, this.L) != -2) {
              continue;
            }
          }
          this.L = -2;
          continue;
          this.O = Math.max(0.0F, paramContext.getFloat(i3, this.O));
          continue;
          try
          {
            this.M = paramContext.getDimensionPixelSize(i3, this.M);
          }
          catch (Exception paramAttributeSet)
          {
            if (paramContext.getInt(i3, this.M) != -2) {
              continue;
            }
          }
          this.M = -2;
          continue;
          try
          {
            this.K = paramContext.getDimensionPixelSize(i3, this.K);
          }
          catch (Exception paramAttributeSet)
          {
            if (paramContext.getInt(i3, this.K) != -2) {
              continue;
            }
          }
          this.K = -2;
          continue;
          this.J = paramContext.getInt(i3, 0);
          if (this.J == 1)
          {
            Log.e("ConstraintLayout", "layout_constraintHeight_default=\"wrap\" is deprecated.\nUse layout_height=\"WRAP_CONTENT\" and layout_constrainedHeight=\"true\" instead.");
            continue;
            this.I = paramContext.getInt(i3, 0);
            if (this.I == 1)
            {
              Log.e("ConstraintLayout", "layout_constraintWidth_default=\"wrap\" is deprecated.\nUse layout_width=\"WRAP_CONTENT\" and layout_constrainedWidth=\"true\" instead.");
              continue;
              this.A = paramContext.getFloat(i3, this.A);
              continue;
              this.z = paramContext.getFloat(i3, this.z);
              continue;
              this.U = paramContext.getBoolean(i3, this.U);
              continue;
              this.T = paramContext.getBoolean(i3, this.T);
              continue;
              this.y = paramContext.getDimensionPixelSize(i3, this.y);
              continue;
              this.x = paramContext.getDimensionPixelSize(i3, this.x);
              continue;
              this.w = paramContext.getDimensionPixelSize(i3, this.w);
              continue;
              this.v = paramContext.getDimensionPixelSize(i3, this.v);
              continue;
              this.u = paramContext.getDimensionPixelSize(i3, this.u);
              continue;
              this.t = paramContext.getDimensionPixelSize(i3, this.t);
              continue;
              this.s = paramContext.getResourceId(i3, this.s);
              if (this.s == -1)
              {
                this.s = paramContext.getInt(i3, -1);
                continue;
                this.r = paramContext.getResourceId(i3, this.r);
                if (this.r == -1)
                {
                  this.r = paramContext.getInt(i3, -1);
                  continue;
                  this.q = paramContext.getResourceId(i3, this.q);
                  if (this.q == -1)
                  {
                    this.q = paramContext.getInt(i3, -1);
                    continue;
                    this.p = paramContext.getResourceId(i3, this.p);
                    if (this.p == -1)
                    {
                      this.p = paramContext.getInt(i3, -1);
                      continue;
                      this.l = paramContext.getResourceId(i3, this.l);
                      if (this.l == -1)
                      {
                        this.l = paramContext.getInt(i3, -1);
                        continue;
                        this.k = paramContext.getResourceId(i3, this.k);
                        if (this.k == -1)
                        {
                          this.k = paramContext.getInt(i3, -1);
                          continue;
                          this.j = paramContext.getResourceId(i3, this.j);
                          if (this.j == -1)
                          {
                            this.j = paramContext.getInt(i3, -1);
                            continue;
                            this.i = paramContext.getResourceId(i3, this.i);
                            if (this.i == -1)
                            {
                              this.i = paramContext.getInt(i3, -1);
                              continue;
                              this.h = paramContext.getResourceId(i3, this.h);
                              if (this.h == -1)
                              {
                                this.h = paramContext.getInt(i3, -1);
                                continue;
                                this.g = paramContext.getResourceId(i3, this.g);
                                if (this.g == -1)
                                {
                                  this.g = paramContext.getInt(i3, -1);
                                  continue;
                                  this.f = paramContext.getResourceId(i3, this.f);
                                  if (this.f == -1)
                                  {
                                    this.f = paramContext.getInt(i3, -1);
                                    continue;
                                    this.e = paramContext.getResourceId(i3, this.e);
                                    if (this.e == -1)
                                    {
                                      this.e = paramContext.getInt(i3, -1);
                                      continue;
                                      this.d = paramContext.getResourceId(i3, this.d);
                                      if (this.d == -1)
                                      {
                                        this.d = paramContext.getInt(i3, -1);
                                        continue;
                                        this.c = paramContext.getFloat(i3, this.c);
                                        continue;
                                        this.b = paramContext.getDimensionPixelOffset(i3, this.b);
                                        continue;
                                        this.a = paramContext.getDimensionPixelOffset(i3, this.a);
                                        continue;
                                        this.o = (paramContext.getFloat(i3, this.o) % 360.0F);
                                        f2 = this.o;
                                        if (f2 < 0.0F)
                                        {
                                          this.o = ((360.0F - f2) % 360.0F);
                                          continue;
                                          this.n = paramContext.getDimensionPixelSize(i3, this.n);
                                          continue;
                                          this.m = paramContext.getResourceId(i3, this.m);
                                          if (this.m == -1)
                                          {
                                            this.m = paramContext.getInt(i3, -1);
                                            continue;
                                            this.S = paramContext.getInt(i3, this.S);
                                          }
                                        }
                                      }
                                    }
                                  }
                                }
                              }
                            }
                          }
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
      paramContext.recycle();
      a();
    }
    
    public a(ViewGroup.LayoutParams paramLayoutParams)
    {
      super();
    }
    
    public void a()
    {
      this.Y = false;
      this.V = true;
      this.W = true;
      if ((this.width == -2) && (this.T))
      {
        this.V = false;
        this.I = 1;
      }
      if ((this.height == -2) && (this.U))
      {
        this.W = false;
        this.J = 1;
      }
      if ((this.width == 0) || (this.width == -1))
      {
        this.V = false;
        if ((this.width == 0) && (this.I == 1))
        {
          this.width = -2;
          this.T = true;
        }
      }
      if ((this.height == 0) || (this.height == -1))
      {
        this.W = false;
        if ((this.height == 0) && (this.J == 1))
        {
          this.height = -2;
          this.U = true;
        }
      }
      if ((this.c != -1.0F) || (this.a != -1) || (this.b != -1))
      {
        this.Y = true;
        this.V = true;
        this.W = true;
        if (!(this.al instanceof i)) {
          this.al = new i();
        }
        ((i)this.al).a(this.S);
      }
    }
    
    @TargetApi(17)
    public void resolveLayoutDirection(int paramInt)
    {
      int i1 = this.leftMargin;
      int i2 = this.rightMargin;
      super.resolveLayoutDirection(paramInt);
      this.ad = -1;
      this.ae = -1;
      this.ab = -1;
      this.ac = -1;
      this.af = -1;
      this.ag = -1;
      this.af = this.t;
      this.ag = this.v;
      this.ah = this.z;
      this.ai = this.a;
      this.aj = this.b;
      this.ak = this.c;
      paramInt = getLayoutDirection();
      int i3 = 0;
      if (1 == paramInt) {
        paramInt = 1;
      } else {
        paramInt = 0;
      }
      if (paramInt != 0)
      {
        paramInt = this.p;
        if (paramInt != -1)
        {
          this.ad = paramInt;
          paramInt = 1;
        }
        else
        {
          int i4 = this.q;
          paramInt = i3;
          if (i4 != -1)
          {
            this.ae = i4;
            paramInt = 1;
          }
        }
        i3 = this.r;
        if (i3 != -1)
        {
          this.ac = i3;
          paramInt = 1;
        }
        i3 = this.s;
        if (i3 != -1)
        {
          this.ab = i3;
          paramInt = 1;
        }
        i3 = this.x;
        if (i3 != -1) {
          this.ag = i3;
        }
        i3 = this.y;
        if (i3 != -1) {
          this.af = i3;
        }
        if (paramInt != 0) {
          this.ah = (1.0F - this.z);
        }
        if ((this.Y) && (this.S == 1))
        {
          float f1 = this.c;
          if (f1 != -1.0F)
          {
            this.ak = (1.0F - f1);
            this.ai = -1;
            this.aj = -1;
          }
          else
          {
            paramInt = this.a;
            if (paramInt != -1)
            {
              this.aj = paramInt;
              this.ai = -1;
              this.ak = -1.0F;
            }
            else
            {
              paramInt = this.b;
              if (paramInt != -1)
              {
                this.ai = paramInt;
                this.aj = -1;
                this.ak = -1.0F;
              }
            }
          }
        }
      }
      else
      {
        paramInt = this.p;
        if (paramInt != -1) {
          this.ac = paramInt;
        }
        paramInt = this.q;
        if (paramInt != -1) {
          this.ab = paramInt;
        }
        paramInt = this.r;
        if (paramInt != -1) {
          this.ad = paramInt;
        }
        paramInt = this.s;
        if (paramInt != -1) {
          this.ae = paramInt;
        }
        paramInt = this.x;
        if (paramInt != -1) {
          this.af = paramInt;
        }
        paramInt = this.y;
        if (paramInt != -1) {
          this.ag = paramInt;
        }
      }
      if ((this.r == -1) && (this.s == -1) && (this.q == -1) && (this.p == -1))
      {
        paramInt = this.f;
        if (paramInt != -1)
        {
          this.ad = paramInt;
          if ((this.rightMargin <= 0) && (i2 > 0)) {
            this.rightMargin = i2;
          }
        }
        else
        {
          paramInt = this.g;
          if (paramInt != -1)
          {
            this.ae = paramInt;
            if ((this.rightMargin <= 0) && (i2 > 0)) {
              this.rightMargin = i2;
            }
          }
        }
        paramInt = this.d;
        if (paramInt != -1)
        {
          this.ab = paramInt;
          if ((this.leftMargin <= 0) && (i1 > 0)) {
            this.leftMargin = i1;
          }
        }
        else
        {
          paramInt = this.e;
          if (paramInt != -1)
          {
            this.ac = paramInt;
            if ((this.leftMargin <= 0) && (i1 > 0)) {
              this.leftMargin = i1;
            }
          }
        }
      }
    }
    
    private static class a
    {
      public static final SparseIntArray a = new SparseIntArray();
      
      static
      {
        a.append(g.b.ConstraintLayout_Layout_layout_constraintLeft_toLeftOf, 8);
        a.append(g.b.ConstraintLayout_Layout_layout_constraintLeft_toRightOf, 9);
        a.append(g.b.ConstraintLayout_Layout_layout_constraintRight_toLeftOf, 10);
        a.append(g.b.ConstraintLayout_Layout_layout_constraintRight_toRightOf, 11);
        a.append(g.b.ConstraintLayout_Layout_layout_constraintTop_toTopOf, 12);
        a.append(g.b.ConstraintLayout_Layout_layout_constraintTop_toBottomOf, 13);
        a.append(g.b.ConstraintLayout_Layout_layout_constraintBottom_toTopOf, 14);
        a.append(g.b.ConstraintLayout_Layout_layout_constraintBottom_toBottomOf, 15);
        a.append(g.b.ConstraintLayout_Layout_layout_constraintBaseline_toBaselineOf, 16);
        a.append(g.b.ConstraintLayout_Layout_layout_constraintCircle, 2);
        a.append(g.b.ConstraintLayout_Layout_layout_constraintCircleRadius, 3);
        a.append(g.b.ConstraintLayout_Layout_layout_constraintCircleAngle, 4);
        a.append(g.b.ConstraintLayout_Layout_layout_editor_absoluteX, 49);
        a.append(g.b.ConstraintLayout_Layout_layout_editor_absoluteY, 50);
        a.append(g.b.ConstraintLayout_Layout_layout_constraintGuide_begin, 5);
        a.append(g.b.ConstraintLayout_Layout_layout_constraintGuide_end, 6);
        a.append(g.b.ConstraintLayout_Layout_layout_constraintGuide_percent, 7);
        a.append(g.b.ConstraintLayout_Layout_android_orientation, 1);
        a.append(g.b.ConstraintLayout_Layout_layout_constraintStart_toEndOf, 17);
        a.append(g.b.ConstraintLayout_Layout_layout_constraintStart_toStartOf, 18);
        a.append(g.b.ConstraintLayout_Layout_layout_constraintEnd_toStartOf, 19);
        a.append(g.b.ConstraintLayout_Layout_layout_constraintEnd_toEndOf, 20);
        a.append(g.b.ConstraintLayout_Layout_layout_goneMarginLeft, 21);
        a.append(g.b.ConstraintLayout_Layout_layout_goneMarginTop, 22);
        a.append(g.b.ConstraintLayout_Layout_layout_goneMarginRight, 23);
        a.append(g.b.ConstraintLayout_Layout_layout_goneMarginBottom, 24);
        a.append(g.b.ConstraintLayout_Layout_layout_goneMarginStart, 25);
        a.append(g.b.ConstraintLayout_Layout_layout_goneMarginEnd, 26);
        a.append(g.b.ConstraintLayout_Layout_layout_constraintHorizontal_bias, 29);
        a.append(g.b.ConstraintLayout_Layout_layout_constraintVertical_bias, 30);
        a.append(g.b.ConstraintLayout_Layout_layout_constraintDimensionRatio, 44);
        a.append(g.b.ConstraintLayout_Layout_layout_constraintHorizontal_weight, 45);
        a.append(g.b.ConstraintLayout_Layout_layout_constraintVertical_weight, 46);
        a.append(g.b.ConstraintLayout_Layout_layout_constraintHorizontal_chainStyle, 47);
        a.append(g.b.ConstraintLayout_Layout_layout_constraintVertical_chainStyle, 48);
        a.append(g.b.ConstraintLayout_Layout_layout_constrainedWidth, 27);
        a.append(g.b.ConstraintLayout_Layout_layout_constrainedHeight, 28);
        a.append(g.b.ConstraintLayout_Layout_layout_constraintWidth_default, 31);
        a.append(g.b.ConstraintLayout_Layout_layout_constraintHeight_default, 32);
        a.append(g.b.ConstraintLayout_Layout_layout_constraintWidth_min, 33);
        a.append(g.b.ConstraintLayout_Layout_layout_constraintWidth_max, 34);
        a.append(g.b.ConstraintLayout_Layout_layout_constraintWidth_percent, 35);
        a.append(g.b.ConstraintLayout_Layout_layout_constraintHeight_min, 36);
        a.append(g.b.ConstraintLayout_Layout_layout_constraintHeight_max, 37);
        a.append(g.b.ConstraintLayout_Layout_layout_constraintHeight_percent, 38);
        a.append(g.b.ConstraintLayout_Layout_layout_constraintLeft_creator, 39);
        a.append(g.b.ConstraintLayout_Layout_layout_constraintTop_creator, 40);
        a.append(g.b.ConstraintLayout_Layout_layout_constraintRight_creator, 41);
        a.append(g.b.ConstraintLayout_Layout_layout_constraintBottom_creator, 42);
        a.append(g.b.ConstraintLayout_Layout_layout_constraintBaseline_creator, 43);
      }
    }
  }
}


/* Location:              ~/android/support/constraint/ConstraintLayout.class
 *
 * Reversed by:           J
 */