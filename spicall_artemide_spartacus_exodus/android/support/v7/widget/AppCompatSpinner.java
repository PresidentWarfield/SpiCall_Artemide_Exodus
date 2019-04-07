package android.support.v7.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.database.DataSetObserver;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.v4.view.TintableBackgroundView;
import android.support.v4.view.ViewCompat;
import android.support.v7.a.a.a;
import android.support.v7.c.a.b;
import android.support.v7.view.menu.s;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

public class AppCompatSpinner
  extends Spinner
  implements TintableBackgroundView
{
  private static final int[] ATTRS_ANDROID_SPINNERMODE = { 16843505 };
  private static final int MAX_ITEMS_MEASURED = 15;
  private static final int MODE_DIALOG = 0;
  private static final int MODE_DROPDOWN = 1;
  private static final int MODE_THEME = -1;
  private static final String TAG = "AppCompatSpinner";
  private final e mBackgroundTintHelper;
  private int mDropDownWidth;
  private ForwardingListener mForwardingListener;
  private b mPopup;
  private final Context mPopupContext;
  private final boolean mPopupSet;
  private SpinnerAdapter mTempAdapter;
  private final Rect mTempRect;
  
  public AppCompatSpinner(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public AppCompatSpinner(Context paramContext, int paramInt)
  {
    this(paramContext, null, a.a.spinnerStyle, paramInt);
  }
  
  public AppCompatSpinner(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, a.a.spinnerStyle);
  }
  
  public AppCompatSpinner(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    this(paramContext, paramAttributeSet, paramInt, -1);
  }
  
  public AppCompatSpinner(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2)
  {
    this(paramContext, paramAttributeSet, paramInt1, paramInt2, null);
  }
  
  /* Error */
  public AppCompatSpinner(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2, Resources.Theme paramTheme)
  {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: aload_2
    //   3: iload_3
    //   4: invokespecial 79	android/widget/Spinner:<init>	(Landroid/content/Context;Landroid/util/AttributeSet;I)V
    //   7: aload_0
    //   8: new 81	android/graphics/Rect
    //   11: dup
    //   12: invokespecial 83	android/graphics/Rect:<init>	()V
    //   15: putfield 85	android/support/v7/widget/AppCompatSpinner:mTempRect	Landroid/graphics/Rect;
    //   18: aload_1
    //   19: aload_2
    //   20: getstatic 90	android/support/v7/a/a$j:Spinner	[I
    //   23: iload_3
    //   24: iconst_0
    //   25: invokestatic 96	android/support/v7/widget/TintTypedArray:obtainStyledAttributes	(Landroid/content/Context;Landroid/util/AttributeSet;[III)Landroid/support/v7/widget/TintTypedArray;
    //   28: astore 6
    //   30: aload_0
    //   31: new 98	android/support/v7/widget/e
    //   34: dup
    //   35: aload_0
    //   36: invokespecial 101	android/support/v7/widget/e:<init>	(Landroid/view/View;)V
    //   39: putfield 103	android/support/v7/widget/AppCompatSpinner:mBackgroundTintHelper	Landroid/support/v7/widget/e;
    //   42: aload 5
    //   44: ifnull +20 -> 64
    //   47: aload_0
    //   48: new 105	android/support/v7/view/d
    //   51: dup
    //   52: aload_1
    //   53: aload 5
    //   55: invokespecial 108	android/support/v7/view/d:<init>	(Landroid/content/Context;Landroid/content/res/Resources$Theme;)V
    //   58: putfield 110	android/support/v7/widget/AppCompatSpinner:mPopupContext	Landroid/content/Context;
    //   61: goto +59 -> 120
    //   64: aload 6
    //   66: getstatic 113	android/support/v7/a/a$j:Spinner_popupTheme	I
    //   69: iconst_0
    //   70: invokevirtual 117	android/support/v7/widget/TintTypedArray:getResourceId	(II)I
    //   73: istore 7
    //   75: iload 7
    //   77: ifeq +20 -> 97
    //   80: aload_0
    //   81: new 105	android/support/v7/view/d
    //   84: dup
    //   85: aload_1
    //   86: iload 7
    //   88: invokespecial 119	android/support/v7/view/d:<init>	(Landroid/content/Context;I)V
    //   91: putfield 110	android/support/v7/widget/AppCompatSpinner:mPopupContext	Landroid/content/Context;
    //   94: goto +26 -> 120
    //   97: getstatic 124	android/os/Build$VERSION:SDK_INT	I
    //   100: bipush 23
    //   102: if_icmpge +9 -> 111
    //   105: aload_1
    //   106: astore 5
    //   108: goto +6 -> 114
    //   111: aconst_null
    //   112: astore 5
    //   114: aload_0
    //   115: aload 5
    //   117: putfield 110	android/support/v7/widget/AppCompatSpinner:mPopupContext	Landroid/content/Context;
    //   120: aload_0
    //   121: getfield 110	android/support/v7/widget/AppCompatSpinner:mPopupContext	Landroid/content/Context;
    //   124: ifnull +250 -> 374
    //   127: iload 4
    //   129: istore 7
    //   131: iload 4
    //   133: iconst_m1
    //   134: if_icmpne +137 -> 271
    //   137: getstatic 124	android/os/Build$VERSION:SDK_INT	I
    //   140: bipush 11
    //   142: if_icmplt +126 -> 268
    //   145: aload_1
    //   146: aload_2
    //   147: getstatic 55	android/support/v7/widget/AppCompatSpinner:ATTRS_ANDROID_SPINNERMODE	[I
    //   150: iload_3
    //   151: iconst_0
    //   152: invokevirtual 129	android/content/Context:obtainStyledAttributes	(Landroid/util/AttributeSet;[III)Landroid/content/res/TypedArray;
    //   155: astore 5
    //   157: iload 4
    //   159: istore 8
    //   161: aload 5
    //   163: astore 9
    //   165: aload 5
    //   167: iconst_0
    //   168: invokevirtual 135	android/content/res/TypedArray:hasValue	(I)Z
    //   171: ifeq +16 -> 187
    //   174: aload 5
    //   176: astore 9
    //   178: aload 5
    //   180: iconst_0
    //   181: iconst_0
    //   182: invokevirtual 138	android/content/res/TypedArray:getInt	(II)I
    //   185: istore 8
    //   187: iload 8
    //   189: istore 7
    //   191: aload 5
    //   193: ifnull +78 -> 271
    //   196: iload 8
    //   198: istore 4
    //   200: aload 5
    //   202: invokevirtual 141	android/content/res/TypedArray:recycle	()V
    //   205: iload 4
    //   207: istore 7
    //   209: goto +62 -> 271
    //   212: astore 10
    //   214: goto +15 -> 229
    //   217: astore_1
    //   218: aconst_null
    //   219: astore 9
    //   221: goto +35 -> 256
    //   224: astore 10
    //   226: aconst_null
    //   227: astore 5
    //   229: aload 5
    //   231: astore 9
    //   233: ldc 35
    //   235: ldc -113
    //   237: aload 10
    //   239: invokestatic 149	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   242: pop
    //   243: iload 4
    //   245: istore 7
    //   247: aload 5
    //   249: ifnull +22 -> 271
    //   252: goto -52 -> 200
    //   255: astore_1
    //   256: aload 9
    //   258: ifnull +8 -> 266
    //   261: aload 9
    //   263: invokevirtual 141	android/content/res/TypedArray:recycle	()V
    //   266: aload_1
    //   267: athrow
    //   268: iconst_1
    //   269: istore 7
    //   271: iload 7
    //   273: iconst_1
    //   274: if_icmpne +100 -> 374
    //   277: new 13	android/support/v7/widget/AppCompatSpinner$b
    //   280: dup
    //   281: aload_0
    //   282: aload_0
    //   283: getfield 110	android/support/v7/widget/AppCompatSpinner:mPopupContext	Landroid/content/Context;
    //   286: aload_2
    //   287: iload_3
    //   288: invokespecial 152	android/support/v7/widget/AppCompatSpinner$b:<init>	(Landroid/support/v7/widget/AppCompatSpinner;Landroid/content/Context;Landroid/util/AttributeSet;I)V
    //   291: astore 9
    //   293: aload_0
    //   294: getfield 110	android/support/v7/widget/AppCompatSpinner:mPopupContext	Landroid/content/Context;
    //   297: aload_2
    //   298: getstatic 90	android/support/v7/a/a$j:Spinner	[I
    //   301: iload_3
    //   302: iconst_0
    //   303: invokestatic 96	android/support/v7/widget/TintTypedArray:obtainStyledAttributes	(Landroid/content/Context;Landroid/util/AttributeSet;[III)Landroid/support/v7/widget/TintTypedArray;
    //   306: astore 5
    //   308: aload_0
    //   309: aload 5
    //   311: getstatic 155	android/support/v7/a/a$j:Spinner_android_dropDownWidth	I
    //   314: bipush -2
    //   316: invokevirtual 158	android/support/v7/widget/TintTypedArray:getLayoutDimension	(II)I
    //   319: putfield 160	android/support/v7/widget/AppCompatSpinner:mDropDownWidth	I
    //   322: aload 9
    //   324: aload 5
    //   326: getstatic 163	android/support/v7/a/a$j:Spinner_android_popupBackground	I
    //   329: invokevirtual 167	android/support/v7/widget/TintTypedArray:getDrawable	(I)Landroid/graphics/drawable/Drawable;
    //   332: invokevirtual 171	android/support/v7/widget/AppCompatSpinner$b:setBackgroundDrawable	(Landroid/graphics/drawable/Drawable;)V
    //   335: aload 9
    //   337: aload 6
    //   339: getstatic 174	android/support/v7/a/a$j:Spinner_android_prompt	I
    //   342: invokevirtual 178	android/support/v7/widget/TintTypedArray:getString	(I)Ljava/lang/String;
    //   345: invokevirtual 181	android/support/v7/widget/AppCompatSpinner$b:a	(Ljava/lang/CharSequence;)V
    //   348: aload 5
    //   350: invokevirtual 182	android/support/v7/widget/TintTypedArray:recycle	()V
    //   353: aload_0
    //   354: aload 9
    //   356: putfield 184	android/support/v7/widget/AppCompatSpinner:mPopup	Landroid/support/v7/widget/AppCompatSpinner$b;
    //   359: aload_0
    //   360: new 8	android/support/v7/widget/AppCompatSpinner$1
    //   363: dup
    //   364: aload_0
    //   365: aload_0
    //   366: aload 9
    //   368: invokespecial 187	android/support/v7/widget/AppCompatSpinner$1:<init>	(Landroid/support/v7/widget/AppCompatSpinner;Landroid/view/View;Landroid/support/v7/widget/AppCompatSpinner$b;)V
    //   371: putfield 189	android/support/v7/widget/AppCompatSpinner:mForwardingListener	Landroid/support/v7/widget/ForwardingListener;
    //   374: aload 6
    //   376: getstatic 192	android/support/v7/a/a$j:Spinner_android_entries	I
    //   379: invokevirtual 196	android/support/v7/widget/TintTypedArray:getTextArray	(I)[Ljava/lang/CharSequence;
    //   382: astore 5
    //   384: aload 5
    //   386: ifnull +28 -> 414
    //   389: new 198	android/widget/ArrayAdapter
    //   392: dup
    //   393: aload_1
    //   394: ldc -57
    //   396: aload 5
    //   398: invokespecial 202	android/widget/ArrayAdapter:<init>	(Landroid/content/Context;I[Ljava/lang/Object;)V
    //   401: astore_1
    //   402: aload_1
    //   403: getstatic 207	android/support/v7/a/a$g:support_simple_spinner_dropdown_item	I
    //   406: invokevirtual 211	android/widget/ArrayAdapter:setDropDownViewResource	(I)V
    //   409: aload_0
    //   410: aload_1
    //   411: invokevirtual 215	android/support/v7/widget/AppCompatSpinner:setAdapter	(Landroid/widget/SpinnerAdapter;)V
    //   414: aload 6
    //   416: invokevirtual 182	android/support/v7/widget/TintTypedArray:recycle	()V
    //   419: aload_0
    //   420: iconst_1
    //   421: putfield 217	android/support/v7/widget/AppCompatSpinner:mPopupSet	Z
    //   424: aload_0
    //   425: getfield 219	android/support/v7/widget/AppCompatSpinner:mTempAdapter	Landroid/widget/SpinnerAdapter;
    //   428: astore_1
    //   429: aload_1
    //   430: ifnull +13 -> 443
    //   433: aload_0
    //   434: aload_1
    //   435: invokevirtual 215	android/support/v7/widget/AppCompatSpinner:setAdapter	(Landroid/widget/SpinnerAdapter;)V
    //   438: aload_0
    //   439: aconst_null
    //   440: putfield 219	android/support/v7/widget/AppCompatSpinner:mTempAdapter	Landroid/widget/SpinnerAdapter;
    //   443: aload_0
    //   444: getfield 103	android/support/v7/widget/AppCompatSpinner:mBackgroundTintHelper	Landroid/support/v7/widget/e;
    //   447: aload_2
    //   448: iload_3
    //   449: invokevirtual 222	android/support/v7/widget/e:a	(Landroid/util/AttributeSet;I)V
    //   452: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	453	0	this	AppCompatSpinner
    //   0	453	1	paramContext	Context
    //   0	453	2	paramAttributeSet	AttributeSet
    //   0	453	3	paramInt1	int
    //   0	453	4	paramInt2	int
    //   0	453	5	paramTheme	Resources.Theme
    //   28	387	6	localTintTypedArray	TintTypedArray
    //   73	202	7	i	int
    //   159	38	8	j	int
    //   163	204	9	localObject	Object
    //   212	1	10	localException1	Exception
    //   224	14	10	localException2	Exception
    // Exception table:
    //   from	to	target	type
    //   165	174	212	java/lang/Exception
    //   178	187	212	java/lang/Exception
    //   145	157	217	finally
    //   145	157	224	java/lang/Exception
    //   165	174	255	finally
    //   178	187	255	finally
    //   233	243	255	finally
  }
  
  int compatMeasureContentWidth(SpinnerAdapter paramSpinnerAdapter, Drawable paramDrawable)
  {
    int i = 0;
    if (paramSpinnerAdapter == null) {
      return 0;
    }
    int j = View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 0);
    int k = View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 0);
    int m = Math.max(0, getSelectedItemPosition());
    int n = Math.min(paramSpinnerAdapter.getCount(), m + 15);
    int i1 = Math.max(0, m - (15 - (n - m)));
    View localView = null;
    m = 0;
    while (i1 < n)
    {
      int i2 = paramSpinnerAdapter.getItemViewType(i1);
      int i3 = i;
      if (i2 != i)
      {
        localView = null;
        i3 = i2;
      }
      localView = paramSpinnerAdapter.getView(i1, localView, this);
      if (localView.getLayoutParams() == null) {
        localView.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
      }
      localView.measure(j, k);
      m = Math.max(m, localView.getMeasuredWidth());
      i1++;
      i = i3;
    }
    i1 = m;
    if (paramDrawable != null)
    {
      paramDrawable.getPadding(this.mTempRect);
      i1 = m + (this.mTempRect.left + this.mTempRect.right);
    }
    return i1;
  }
  
  protected void drawableStateChanged()
  {
    super.drawableStateChanged();
    e locale = this.mBackgroundTintHelper;
    if (locale != null) {
      locale.c();
    }
  }
  
  public int getDropDownHorizontalOffset()
  {
    b localb = this.mPopup;
    if (localb != null) {
      return localb.getHorizontalOffset();
    }
    if (Build.VERSION.SDK_INT >= 16) {
      return super.getDropDownHorizontalOffset();
    }
    return 0;
  }
  
  public int getDropDownVerticalOffset()
  {
    b localb = this.mPopup;
    if (localb != null) {
      return localb.getVerticalOffset();
    }
    if (Build.VERSION.SDK_INT >= 16) {
      return super.getDropDownVerticalOffset();
    }
    return 0;
  }
  
  public int getDropDownWidth()
  {
    if (this.mPopup != null) {
      return this.mDropDownWidth;
    }
    if (Build.VERSION.SDK_INT >= 16) {
      return super.getDropDownWidth();
    }
    return 0;
  }
  
  public Drawable getPopupBackground()
  {
    b localb = this.mPopup;
    if (localb != null) {
      return localb.getBackground();
    }
    if (Build.VERSION.SDK_INT >= 16) {
      return super.getPopupBackground();
    }
    return null;
  }
  
  public Context getPopupContext()
  {
    if (this.mPopup != null) {
      return this.mPopupContext;
    }
    if (Build.VERSION.SDK_INT >= 23) {
      return super.getPopupContext();
    }
    return null;
  }
  
  public CharSequence getPrompt()
  {
    Object localObject = this.mPopup;
    if (localObject != null) {
      localObject = ((b)localObject).a();
    } else {
      localObject = super.getPrompt();
    }
    return (CharSequence)localObject;
  }
  
  public ColorStateList getSupportBackgroundTintList()
  {
    Object localObject = this.mBackgroundTintHelper;
    if (localObject != null) {
      localObject = ((e)localObject).a();
    } else {
      localObject = null;
    }
    return (ColorStateList)localObject;
  }
  
  public PorterDuff.Mode getSupportBackgroundTintMode()
  {
    Object localObject = this.mBackgroundTintHelper;
    if (localObject != null) {
      localObject = ((e)localObject).b();
    } else {
      localObject = null;
    }
    return (PorterDuff.Mode)localObject;
  }
  
  protected void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    b localb = this.mPopup;
    if ((localb != null) && (localb.isShowing())) {
      this.mPopup.dismiss();
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    super.onMeasure(paramInt1, paramInt2);
    if ((this.mPopup != null) && (View.MeasureSpec.getMode(paramInt1) == Integer.MIN_VALUE)) {
      setMeasuredDimension(Math.min(Math.max(getMeasuredWidth(), compatMeasureContentWidth(getAdapter(), getBackground())), View.MeasureSpec.getSize(paramInt1)), getMeasuredHeight());
    }
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    ForwardingListener localForwardingListener = this.mForwardingListener;
    if ((localForwardingListener != null) && (localForwardingListener.onTouch(this, paramMotionEvent))) {
      return true;
    }
    return super.onTouchEvent(paramMotionEvent);
  }
  
  public boolean performClick()
  {
    b localb = this.mPopup;
    if (localb != null)
    {
      if (!localb.isShowing()) {
        this.mPopup.show();
      }
      return true;
    }
    return super.performClick();
  }
  
  public void setAdapter(SpinnerAdapter paramSpinnerAdapter)
  {
    if (!this.mPopupSet)
    {
      this.mTempAdapter = paramSpinnerAdapter;
      return;
    }
    super.setAdapter(paramSpinnerAdapter);
    if (this.mPopup != null)
    {
      Context localContext1 = this.mPopupContext;
      Context localContext2 = localContext1;
      if (localContext1 == null) {
        localContext2 = getContext();
      }
      this.mPopup.setAdapter(new a(paramSpinnerAdapter, localContext2.getTheme()));
    }
  }
  
  public void setBackgroundDrawable(Drawable paramDrawable)
  {
    super.setBackgroundDrawable(paramDrawable);
    e locale = this.mBackgroundTintHelper;
    if (locale != null) {
      locale.a(paramDrawable);
    }
  }
  
  public void setBackgroundResource(int paramInt)
  {
    super.setBackgroundResource(paramInt);
    e locale = this.mBackgroundTintHelper;
    if (locale != null) {
      locale.a(paramInt);
    }
  }
  
  public void setDropDownHorizontalOffset(int paramInt)
  {
    b localb = this.mPopup;
    if (localb != null) {
      localb.setHorizontalOffset(paramInt);
    } else if (Build.VERSION.SDK_INT >= 16) {
      super.setDropDownHorizontalOffset(paramInt);
    }
  }
  
  public void setDropDownVerticalOffset(int paramInt)
  {
    b localb = this.mPopup;
    if (localb != null) {
      localb.setVerticalOffset(paramInt);
    } else if (Build.VERSION.SDK_INT >= 16) {
      super.setDropDownVerticalOffset(paramInt);
    }
  }
  
  public void setDropDownWidth(int paramInt)
  {
    if (this.mPopup != null) {
      this.mDropDownWidth = paramInt;
    } else if (Build.VERSION.SDK_INT >= 16) {
      super.setDropDownWidth(paramInt);
    }
  }
  
  public void setPopupBackgroundDrawable(Drawable paramDrawable)
  {
    b localb = this.mPopup;
    if (localb != null) {
      localb.setBackgroundDrawable(paramDrawable);
    } else if (Build.VERSION.SDK_INT >= 16) {
      super.setPopupBackgroundDrawable(paramDrawable);
    }
  }
  
  public void setPopupBackgroundResource(int paramInt)
  {
    setPopupBackgroundDrawable(b.b(getPopupContext(), paramInt));
  }
  
  public void setPrompt(CharSequence paramCharSequence)
  {
    b localb = this.mPopup;
    if (localb != null) {
      localb.a(paramCharSequence);
    } else {
      super.setPrompt(paramCharSequence);
    }
  }
  
  public void setSupportBackgroundTintList(ColorStateList paramColorStateList)
  {
    e locale = this.mBackgroundTintHelper;
    if (locale != null) {
      locale.a(paramColorStateList);
    }
  }
  
  public void setSupportBackgroundTintMode(PorterDuff.Mode paramMode)
  {
    e locale = this.mBackgroundTintHelper;
    if (locale != null) {
      locale.a(paramMode);
    }
  }
  
  private static class a
    implements ListAdapter, SpinnerAdapter
  {
    private SpinnerAdapter a;
    private ListAdapter b;
    
    public a(SpinnerAdapter paramSpinnerAdapter, Resources.Theme paramTheme)
    {
      this.a = paramSpinnerAdapter;
      if ((paramSpinnerAdapter instanceof ListAdapter)) {
        this.b = ((ListAdapter)paramSpinnerAdapter);
      }
      if (paramTheme != null) {
        if ((Build.VERSION.SDK_INT >= 23) && ((paramSpinnerAdapter instanceof android.widget.ThemedSpinnerAdapter)))
        {
          paramSpinnerAdapter = (android.widget.ThemedSpinnerAdapter)paramSpinnerAdapter;
          if (paramSpinnerAdapter.getDropDownViewTheme() != paramTheme) {
            paramSpinnerAdapter.setDropDownViewTheme(paramTheme);
          }
        }
        else if ((paramSpinnerAdapter instanceof ThemedSpinnerAdapter))
        {
          paramSpinnerAdapter = (ThemedSpinnerAdapter)paramSpinnerAdapter;
          if (paramSpinnerAdapter.getDropDownViewTheme() == null) {
            paramSpinnerAdapter.setDropDownViewTheme(paramTheme);
          }
        }
      }
    }
    
    public boolean areAllItemsEnabled()
    {
      ListAdapter localListAdapter = this.b;
      if (localListAdapter != null) {
        return localListAdapter.areAllItemsEnabled();
      }
      return true;
    }
    
    public int getCount()
    {
      SpinnerAdapter localSpinnerAdapter = this.a;
      int i;
      if (localSpinnerAdapter == null) {
        i = 0;
      } else {
        i = localSpinnerAdapter.getCount();
      }
      return i;
    }
    
    public View getDropDownView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      SpinnerAdapter localSpinnerAdapter = this.a;
      if (localSpinnerAdapter == null) {
        paramView = null;
      } else {
        paramView = localSpinnerAdapter.getDropDownView(paramInt, paramView, paramViewGroup);
      }
      return paramView;
    }
    
    public Object getItem(int paramInt)
    {
      Object localObject = this.a;
      if (localObject == null) {
        localObject = null;
      } else {
        localObject = ((SpinnerAdapter)localObject).getItem(paramInt);
      }
      return localObject;
    }
    
    public long getItemId(int paramInt)
    {
      SpinnerAdapter localSpinnerAdapter = this.a;
      long l;
      if (localSpinnerAdapter == null) {
        l = -1L;
      } else {
        l = localSpinnerAdapter.getItemId(paramInt);
      }
      return l;
    }
    
    public int getItemViewType(int paramInt)
    {
      return 0;
    }
    
    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      return getDropDownView(paramInt, paramView, paramViewGroup);
    }
    
    public int getViewTypeCount()
    {
      return 1;
    }
    
    public boolean hasStableIds()
    {
      SpinnerAdapter localSpinnerAdapter = this.a;
      boolean bool;
      if ((localSpinnerAdapter != null) && (localSpinnerAdapter.hasStableIds())) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public boolean isEmpty()
    {
      boolean bool;
      if (getCount() == 0) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public boolean isEnabled(int paramInt)
    {
      ListAdapter localListAdapter = this.b;
      if (localListAdapter != null) {
        return localListAdapter.isEnabled(paramInt);
      }
      return true;
    }
    
    public void registerDataSetObserver(DataSetObserver paramDataSetObserver)
    {
      SpinnerAdapter localSpinnerAdapter = this.a;
      if (localSpinnerAdapter != null) {
        localSpinnerAdapter.registerDataSetObserver(paramDataSetObserver);
      }
    }
    
    public void unregisterDataSetObserver(DataSetObserver paramDataSetObserver)
    {
      SpinnerAdapter localSpinnerAdapter = this.a;
      if (localSpinnerAdapter != null) {
        localSpinnerAdapter.unregisterDataSetObserver(paramDataSetObserver);
      }
    }
  }
  
  private class b
    extends ListPopupWindow
  {
    ListAdapter a;
    private CharSequence c;
    private final Rect d = new Rect();
    
    public b(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
    {
      super(paramAttributeSet, paramInt);
      setAnchorView(AppCompatSpinner.this);
      setModal(true);
      setPromptPosition(0);
      setOnItemClickListener(new AdapterView.OnItemClickListener()
      {
        public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
        {
          AppCompatSpinner.this.setSelection(paramAnonymousInt);
          if (AppCompatSpinner.this.getOnItemClickListener() != null) {
            AppCompatSpinner.this.performItemClick(paramAnonymousView, paramAnonymousInt, AppCompatSpinner.b.this.a.getItemId(paramAnonymousInt));
          }
          AppCompatSpinner.b.this.dismiss();
        }
      });
    }
    
    public CharSequence a()
    {
      return this.c;
    }
    
    public void a(CharSequence paramCharSequence)
    {
      this.c = paramCharSequence;
    }
    
    boolean a(View paramView)
    {
      boolean bool;
      if ((ViewCompat.isAttachedToWindow(paramView)) && (paramView.getGlobalVisibleRect(this.d))) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    void b()
    {
      Object localObject = getBackground();
      int i = 0;
      if (localObject != null)
      {
        ((Drawable)localObject).getPadding(AppCompatSpinner.this.mTempRect);
        if (ViewUtils.isLayoutRtl(AppCompatSpinner.this)) {
          i = AppCompatSpinner.this.mTempRect.right;
        } else {
          i = -AppCompatSpinner.this.mTempRect.left;
        }
      }
      else
      {
        localObject = AppCompatSpinner.this.mTempRect;
        AppCompatSpinner.this.mTempRect.right = 0;
        ((Rect)localObject).left = 0;
      }
      int j = AppCompatSpinner.this.getPaddingLeft();
      int k = AppCompatSpinner.this.getPaddingRight();
      int m = AppCompatSpinner.this.getWidth();
      if (AppCompatSpinner.this.mDropDownWidth == -2)
      {
        int n = AppCompatSpinner.this.compatMeasureContentWidth((SpinnerAdapter)this.a, getBackground());
        int i1 = AppCompatSpinner.this.getContext().getResources().getDisplayMetrics().widthPixels - AppCompatSpinner.this.mTempRect.left - AppCompatSpinner.this.mTempRect.right;
        int i2 = n;
        if (n > i1) {
          i2 = i1;
        }
        setContentWidth(Math.max(i2, m - j - k));
      }
      else if (AppCompatSpinner.this.mDropDownWidth == -1)
      {
        setContentWidth(m - j - k);
      }
      else
      {
        setContentWidth(AppCompatSpinner.this.mDropDownWidth);
      }
      if (ViewUtils.isLayoutRtl(AppCompatSpinner.this)) {
        i += m - k - getWidth();
      } else {
        i += j;
      }
      setHorizontalOffset(i);
    }
    
    public void setAdapter(ListAdapter paramListAdapter)
    {
      super.setAdapter(paramListAdapter);
      this.a = paramListAdapter;
    }
    
    public void show()
    {
      boolean bool = isShowing();
      b();
      setInputMethodMode(2);
      super.show();
      getListView().setChoiceMode(1);
      setSelection(AppCompatSpinner.this.getSelectedItemPosition());
      if (bool) {
        return;
      }
      ViewTreeObserver localViewTreeObserver = AppCompatSpinner.this.getViewTreeObserver();
      if (localViewTreeObserver != null)
      {
        final ViewTreeObserver.OnGlobalLayoutListener local2 = new ViewTreeObserver.OnGlobalLayoutListener()
        {
          public void onGlobalLayout()
          {
            AppCompatSpinner.b localb = AppCompatSpinner.b.this;
            if (!localb.a(localb.b))
            {
              AppCompatSpinner.b.this.dismiss();
            }
            else
            {
              AppCompatSpinner.b.this.b();
              AppCompatSpinner.b.a(AppCompatSpinner.b.this);
            }
          }
        };
        localViewTreeObserver.addOnGlobalLayoutListener(local2);
        setOnDismissListener(new PopupWindow.OnDismissListener()
        {
          public void onDismiss()
          {
            ViewTreeObserver localViewTreeObserver = AppCompatSpinner.this.getViewTreeObserver();
            if (localViewTreeObserver != null) {
              localViewTreeObserver.removeGlobalOnLayoutListener(local2);
            }
          }
        });
      }
    }
  }
}


/* Location:              ~/android/support/v7/widget/AppCompatSpinner.class
 *
 * Reversed by:           J
 */