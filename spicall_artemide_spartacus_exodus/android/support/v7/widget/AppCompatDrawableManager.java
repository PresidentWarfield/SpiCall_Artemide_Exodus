package android.support.v7.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.content.res.XmlResourceParser;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.ConstantState;
import android.graphics.drawable.LayerDrawable;
import android.os.Build.VERSION;
import android.support.b.a.c;
import android.support.b.a.i;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.ColorUtils;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.util.ArrayMap;
import android.support.v4.util.LongSparseArray;
import android.support.v4.util.LruCache;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.a.a.a;
import android.support.v7.a.a.c;
import android.support.v7.a.a.e;
import android.support.v7.c.a.b;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.util.Xml;
import java.lang.ref.WeakReference;
import java.util.WeakHashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public final class AppCompatDrawableManager
{
  private static final int[] COLORFILTER_COLOR_BACKGROUND_MULTIPLY;
  private static final int[] COLORFILTER_COLOR_CONTROL_ACTIVATED;
  private static final int[] COLORFILTER_TINT_COLOR_CONTROL_NORMAL;
  private static final b COLOR_FILTER_CACHE;
  private static final boolean DEBUG = false;
  private static final PorterDuff.Mode DEFAULT_MODE = PorterDuff.Mode.SRC_IN;
  private static AppCompatDrawableManager INSTANCE;
  private static final String PLATFORM_VD_CLAZZ = "android.graphics.drawable.VectorDrawable";
  private static final String SKIP_DRAWABLE_TAG = "appcompat_skip_skip";
  private static final String TAG = "AppCompatDrawableManager";
  private static final int[] TINT_CHECKABLE_BUTTON_LIST = { a.e.abc_btn_check_material, a.e.abc_btn_radio_material };
  private static final int[] TINT_COLOR_CONTROL_NORMAL;
  private static final int[] TINT_COLOR_CONTROL_STATE_LIST;
  private ArrayMap<String, c> mDelegates;
  private final Object mDrawableCacheLock = new Object();
  private final WeakHashMap<Context, LongSparseArray<WeakReference<Drawable.ConstantState>>> mDrawableCaches = new WeakHashMap(0);
  private boolean mHasCheckedVectorDrawableSetup;
  private SparseArrayCompat<String> mKnownDrawableIdTags;
  private WeakHashMap<Context, SparseArrayCompat<ColorStateList>> mTintLists;
  private TypedValue mTypedValue;
  
  static
  {
    COLOR_FILTER_CACHE = new b(6);
    COLORFILTER_TINT_COLOR_CONTROL_NORMAL = new int[] { a.e.abc_textfield_search_default_mtrl_alpha, a.e.abc_textfield_default_mtrl_alpha, a.e.abc_ab_share_pack_mtrl_alpha };
    TINT_COLOR_CONTROL_NORMAL = new int[] { a.e.abc_ic_commit_search_api_mtrl_alpha, a.e.abc_seekbar_tick_mark_material, a.e.abc_ic_menu_share_mtrl_alpha, a.e.abc_ic_menu_copy_mtrl_am_alpha, a.e.abc_ic_menu_cut_mtrl_alpha, a.e.abc_ic_menu_selectall_mtrl_alpha, a.e.abc_ic_menu_paste_mtrl_am_alpha };
    COLORFILTER_COLOR_CONTROL_ACTIVATED = new int[] { a.e.abc_textfield_activated_mtrl_alpha, a.e.abc_textfield_search_activated_mtrl_alpha, a.e.abc_cab_background_top_mtrl_alpha, a.e.abc_text_cursor_material, a.e.abc_text_select_handle_left_mtrl_dark, a.e.abc_text_select_handle_middle_mtrl_dark, a.e.abc_text_select_handle_right_mtrl_dark, a.e.abc_text_select_handle_left_mtrl_light, a.e.abc_text_select_handle_middle_mtrl_light, a.e.abc_text_select_handle_right_mtrl_light };
    COLORFILTER_COLOR_BACKGROUND_MULTIPLY = new int[] { a.e.abc_popup_background_mtrl_mult, a.e.abc_cab_background_internal_bg, a.e.abc_menu_hardkey_panel_mtrl_mult };
    TINT_COLOR_CONTROL_STATE_LIST = new int[] { a.e.abc_tab_indicator_material, a.e.abc_textfield_search_material };
  }
  
  private void addDelegate(String paramString, c paramc)
  {
    if (this.mDelegates == null) {
      this.mDelegates = new ArrayMap();
    }
    this.mDelegates.put(paramString, paramc);
  }
  
  private boolean addDrawableToCache(Context paramContext, long paramLong, Drawable paramDrawable)
  {
    Drawable.ConstantState localConstantState = paramDrawable.getConstantState();
    if (localConstantState != null) {
      synchronized (this.mDrawableCacheLock)
      {
        LongSparseArray localLongSparseArray = (LongSparseArray)this.mDrawableCaches.get(paramContext);
        paramDrawable = localLongSparseArray;
        if (localLongSparseArray == null)
        {
          paramDrawable = new android/support/v4/util/LongSparseArray;
          paramDrawable.<init>();
          this.mDrawableCaches.put(paramContext, paramDrawable);
        }
        paramContext = new java/lang/ref/WeakReference;
        paramContext.<init>(localConstantState);
        paramDrawable.put(paramLong, paramContext);
        return true;
      }
    }
    return false;
  }
  
  private void addTintListToCache(Context paramContext, int paramInt, ColorStateList paramColorStateList)
  {
    if (this.mTintLists == null) {
      this.mTintLists = new WeakHashMap();
    }
    SparseArrayCompat localSparseArrayCompat1 = (SparseArrayCompat)this.mTintLists.get(paramContext);
    SparseArrayCompat localSparseArrayCompat2 = localSparseArrayCompat1;
    if (localSparseArrayCompat1 == null)
    {
      localSparseArrayCompat2 = new SparseArrayCompat();
      this.mTintLists.put(paramContext, localSparseArrayCompat2);
    }
    localSparseArrayCompat2.append(paramInt, paramColorStateList);
  }
  
  private static boolean arrayContains(int[] paramArrayOfInt, int paramInt)
  {
    int i = paramArrayOfInt.length;
    for (int j = 0; j < i; j++) {
      if (paramArrayOfInt[j] == paramInt) {
        return true;
      }
    }
    return false;
  }
  
  private void checkVectorDrawableSetup(Context paramContext)
  {
    if (this.mHasCheckedVectorDrawableSetup) {
      return;
    }
    this.mHasCheckedVectorDrawableSetup = true;
    paramContext = getDrawable(paramContext, a.e.abc_vector_test);
    if ((paramContext != null) && (isVectorDrawable(paramContext))) {
      return;
    }
    this.mHasCheckedVectorDrawableSetup = false;
    throw new IllegalStateException("This app has been built with an incorrect configuration. Please configure your build for VectorDrawableCompat.");
  }
  
  private ColorStateList createBorderlessButtonColorStateList(Context paramContext)
  {
    return createButtonColorStateList(paramContext, 0);
  }
  
  private ColorStateList createButtonColorStateList(Context paramContext, int paramInt)
  {
    int i = ad.a(paramContext, a.a.colorControlHighlight);
    int j = ad.c(paramContext, a.a.colorButtonNormal);
    int[] arrayOfInt1 = ad.a;
    int[] arrayOfInt2 = ad.d;
    int k = ColorUtils.compositeColors(i, paramInt);
    paramContext = ad.b;
    i = ColorUtils.compositeColors(i, paramInt);
    return new ColorStateList(new int[][] { arrayOfInt1, arrayOfInt2, paramContext, ad.h }, new int[] { j, k, i, paramInt });
  }
  
  private static long createCacheKey(TypedValue paramTypedValue)
  {
    return paramTypedValue.assetCookie << 32 | paramTypedValue.data;
  }
  
  private ColorStateList createColoredButtonColorStateList(Context paramContext)
  {
    return createButtonColorStateList(paramContext, ad.a(paramContext, a.a.colorAccent));
  }
  
  private ColorStateList createDefaultButtonColorStateList(Context paramContext)
  {
    return createButtonColorStateList(paramContext, ad.a(paramContext, a.a.colorButtonNormal));
  }
  
  private Drawable createDrawableIfNeeded(Context paramContext, int paramInt)
  {
    if (this.mTypedValue == null) {
      this.mTypedValue = new TypedValue();
    }
    TypedValue localTypedValue = this.mTypedValue;
    paramContext.getResources().getValue(paramInt, localTypedValue, true);
    long l = createCacheKey(localTypedValue);
    Object localObject = getCachedDrawable(paramContext, l);
    if (localObject != null) {
      return (Drawable)localObject;
    }
    if (paramInt == a.e.abc_cab_background_top_material) {
      localObject = new LayerDrawable(new Drawable[] { getDrawable(paramContext, a.e.abc_cab_background_internal_bg), getDrawable(paramContext, a.e.abc_cab_background_top_mtrl_alpha) });
    }
    if (localObject != null)
    {
      ((Drawable)localObject).setChangingConfigurations(localTypedValue.changingConfigurations);
      addDrawableToCache(paramContext, l, (Drawable)localObject);
    }
    return (Drawable)localObject;
  }
  
  private ColorStateList createSwitchThumbColorStateList(Context paramContext)
  {
    int[][] arrayOfInt = new int[3][];
    int[] arrayOfInt1 = new int[3];
    ColorStateList localColorStateList = ad.b(paramContext, a.a.colorSwitchThumbNormal);
    if ((localColorStateList != null) && (localColorStateList.isStateful()))
    {
      arrayOfInt[0] = ad.a;
      arrayOfInt1[0] = localColorStateList.getColorForState(arrayOfInt[0], 0);
      arrayOfInt[1] = ad.e;
      arrayOfInt1[1] = ad.a(paramContext, a.a.colorControlActivated);
      arrayOfInt[2] = ad.h;
      arrayOfInt1[2] = localColorStateList.getDefaultColor();
    }
    else
    {
      arrayOfInt[0] = ad.a;
      arrayOfInt1[0] = ad.c(paramContext, a.a.colorSwitchThumbNormal);
      arrayOfInt[1] = ad.e;
      arrayOfInt1[1] = ad.a(paramContext, a.a.colorControlActivated);
      arrayOfInt[2] = ad.h;
      arrayOfInt1[2] = ad.a(paramContext, a.a.colorSwitchThumbNormal);
    }
    return new ColorStateList(arrayOfInt, arrayOfInt1);
  }
  
  private static PorterDuffColorFilter createTintFilter(ColorStateList paramColorStateList, PorterDuff.Mode paramMode, int[] paramArrayOfInt)
  {
    if ((paramColorStateList != null) && (paramMode != null)) {
      return getPorterDuffColorFilter(paramColorStateList.getColorForState(paramArrayOfInt, 0), paramMode);
    }
    return null;
  }
  
  public static AppCompatDrawableManager get()
  {
    if (INSTANCE == null)
    {
      INSTANCE = new AppCompatDrawableManager();
      installDefaultInflateDelegates(INSTANCE);
    }
    return INSTANCE;
  }
  
  private Drawable getCachedDrawable(Context paramContext, long paramLong)
  {
    synchronized (this.mDrawableCacheLock)
    {
      LongSparseArray localLongSparseArray = (LongSparseArray)this.mDrawableCaches.get(paramContext);
      if (localLongSparseArray == null) {
        return null;
      }
      Object localObject2 = (WeakReference)localLongSparseArray.get(paramLong);
      if (localObject2 != null)
      {
        localObject2 = (Drawable.ConstantState)((WeakReference)localObject2).get();
        if (localObject2 != null)
        {
          paramContext = ((Drawable.ConstantState)localObject2).newDrawable(paramContext.getResources());
          return paramContext;
        }
        localLongSparseArray.delete(paramLong);
      }
      return null;
    }
  }
  
  public static PorterDuffColorFilter getPorterDuffColorFilter(int paramInt, PorterDuff.Mode paramMode)
  {
    PorterDuffColorFilter localPorterDuffColorFilter1 = COLOR_FILTER_CACHE.a(paramInt, paramMode);
    PorterDuffColorFilter localPorterDuffColorFilter2 = localPorterDuffColorFilter1;
    if (localPorterDuffColorFilter1 == null)
    {
      localPorterDuffColorFilter2 = new PorterDuffColorFilter(paramInt, paramMode);
      COLOR_FILTER_CACHE.a(paramInt, paramMode, localPorterDuffColorFilter2);
    }
    return localPorterDuffColorFilter2;
  }
  
  private ColorStateList getTintListFromCache(Context paramContext, int paramInt)
  {
    Object localObject1 = this.mTintLists;
    Object localObject2 = null;
    if (localObject1 != null)
    {
      localObject1 = (SparseArrayCompat)((WeakHashMap)localObject1).get(paramContext);
      paramContext = (Context)localObject2;
      if (localObject1 != null) {
        paramContext = (ColorStateList)((SparseArrayCompat)localObject1).get(paramInt);
      }
      return paramContext;
    }
    return null;
  }
  
  static PorterDuff.Mode getTintMode(int paramInt)
  {
    PorterDuff.Mode localMode;
    if (paramInt == a.e.abc_switch_thumb_material) {
      localMode = PorterDuff.Mode.MULTIPLY;
    } else {
      localMode = null;
    }
    return localMode;
  }
  
  private static void installDefaultInflateDelegates(AppCompatDrawableManager paramAppCompatDrawableManager)
  {
    if (Build.VERSION.SDK_INT < 24)
    {
      paramAppCompatDrawableManager.addDelegate("vector", new d());
      if (Build.VERSION.SDK_INT >= 11) {
        paramAppCompatDrawableManager.addDelegate("animated-vector", new a());
      }
    }
  }
  
  private static boolean isVectorDrawable(Drawable paramDrawable)
  {
    boolean bool;
    if ((!(paramDrawable instanceof i)) && (!"android.graphics.drawable.VectorDrawable".equals(paramDrawable.getClass().getName()))) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  private Drawable loadDrawableFromDelegates(Context paramContext, int paramInt)
  {
    Object localObject1 = this.mDelegates;
    if ((localObject1 != null) && (!((ArrayMap)localObject1).isEmpty()))
    {
      localObject1 = this.mKnownDrawableIdTags;
      if (localObject1 != null)
      {
        localObject1 = (String)((SparseArrayCompat)localObject1).get(paramInt);
        if (("appcompat_skip_skip".equals(localObject1)) || ((localObject1 != null) && (this.mDelegates.get(localObject1) == null))) {
          return null;
        }
      }
      else
      {
        this.mKnownDrawableIdTags = new SparseArrayCompat();
      }
      if (this.mTypedValue == null) {
        this.mTypedValue = new TypedValue();
      }
      TypedValue localTypedValue = this.mTypedValue;
      localObject1 = paramContext.getResources();
      ((Resources)localObject1).getValue(paramInt, localTypedValue, true);
      long l = createCacheKey(localTypedValue);
      Drawable localDrawable = getCachedDrawable(paramContext, l);
      if (localDrawable != null) {
        return localDrawable;
      }
      Object localObject2 = localDrawable;
      if (localTypedValue.string != null)
      {
        localObject2 = localDrawable;
        if (localTypedValue.string.toString().endsWith(".xml"))
        {
          localObject2 = localDrawable;
          try
          {
            XmlResourceParser localXmlResourceParser = ((Resources)localObject1).getXml(paramInt);
            localObject2 = localDrawable;
            AttributeSet localAttributeSet = Xml.asAttributeSet(localXmlResourceParser);
            int i;
            do
            {
              localObject2 = localDrawable;
              i = localXmlResourceParser.next();
            } while ((i != 2) && (i != 1));
            if (i == 2)
            {
              localObject2 = localDrawable;
              localObject1 = localXmlResourceParser.getName();
              localObject2 = localDrawable;
              this.mKnownDrawableIdTags.append(paramInt, localObject1);
              localObject2 = localDrawable;
              c localc = (c)this.mDelegates.get(localObject1);
              localObject1 = localDrawable;
              if (localc != null)
              {
                localObject2 = localDrawable;
                localObject1 = localc.a(paramContext, localXmlResourceParser, localAttributeSet, paramContext.getTheme());
              }
              localObject2 = localObject1;
              if (localObject1 != null)
              {
                localObject2 = localObject1;
                ((Drawable)localObject1).setChangingConfigurations(localTypedValue.changingConfigurations);
                localObject2 = localObject1;
                addDrawableToCache(paramContext, l, (Drawable)localObject1);
                localObject2 = localObject1;
              }
            }
            else
            {
              localObject2 = localDrawable;
              paramContext = new org/xmlpull/v1/XmlPullParserException;
              localObject2 = localDrawable;
              paramContext.<init>("No start tag found");
              localObject2 = localDrawable;
              throw paramContext;
            }
          }
          catch (Exception paramContext)
          {
            Log.e("AppCompatDrawableManager", "Exception while inflating drawable", paramContext);
          }
        }
      }
      if (localObject2 == null) {
        this.mKnownDrawableIdTags.append(paramInt, "appcompat_skip_skip");
      }
      return (Drawable)localObject2;
    }
    return null;
  }
  
  private void removeDelegate(String paramString, c paramc)
  {
    ArrayMap localArrayMap = this.mDelegates;
    if ((localArrayMap != null) && (localArrayMap.get(paramString) == paramc)) {
      this.mDelegates.remove(paramString);
    }
  }
  
  private static void setPorterDuffColorFilter(Drawable paramDrawable, int paramInt, PorterDuff.Mode paramMode)
  {
    Drawable localDrawable = paramDrawable;
    if (DrawableUtils.canSafelyMutateDrawable(paramDrawable)) {
      localDrawable = paramDrawable.mutate();
    }
    paramDrawable = paramMode;
    if (paramMode == null) {
      paramDrawable = DEFAULT_MODE;
    }
    localDrawable.setColorFilter(getPorterDuffColorFilter(paramInt, paramDrawable));
  }
  
  private Drawable tintDrawable(Context paramContext, int paramInt, boolean paramBoolean, Drawable paramDrawable)
  {
    Object localObject = getTintList(paramContext, paramInt);
    if (localObject != null)
    {
      paramContext = paramDrawable;
      if (DrawableUtils.canSafelyMutateDrawable(paramDrawable)) {
        paramContext = paramDrawable.mutate();
      }
      paramContext = DrawableCompat.wrap(paramContext);
      DrawableCompat.setTintList(paramContext, (ColorStateList)localObject);
      paramDrawable = getTintMode(paramInt);
      localObject = paramContext;
      if (paramDrawable != null)
      {
        DrawableCompat.setTintMode(paramContext, paramDrawable);
        localObject = paramContext;
      }
    }
    else if (paramInt == a.e.abc_seekbar_track_material)
    {
      localObject = (LayerDrawable)paramDrawable;
      setPorterDuffColorFilter(((LayerDrawable)localObject).findDrawableByLayerId(16908288), ad.a(paramContext, a.a.colorControlNormal), DEFAULT_MODE);
      setPorterDuffColorFilter(((LayerDrawable)localObject).findDrawableByLayerId(16908303), ad.a(paramContext, a.a.colorControlNormal), DEFAULT_MODE);
      setPorterDuffColorFilter(((LayerDrawable)localObject).findDrawableByLayerId(16908301), ad.a(paramContext, a.a.colorControlActivated), DEFAULT_MODE);
      localObject = paramDrawable;
    }
    else if ((paramInt != a.e.abc_ratingbar_material) && (paramInt != a.e.abc_ratingbar_indicator_material) && (paramInt != a.e.abc_ratingbar_small_material))
    {
      localObject = paramDrawable;
      if (!tintDrawableUsingColorFilter(paramContext, paramInt, paramDrawable))
      {
        localObject = paramDrawable;
        if (paramBoolean) {
          localObject = null;
        }
      }
    }
    else
    {
      localObject = (LayerDrawable)paramDrawable;
      setPorterDuffColorFilter(((LayerDrawable)localObject).findDrawableByLayerId(16908288), ad.c(paramContext, a.a.colorControlNormal), DEFAULT_MODE);
      setPorterDuffColorFilter(((LayerDrawable)localObject).findDrawableByLayerId(16908303), ad.a(paramContext, a.a.colorControlActivated), DEFAULT_MODE);
      setPorterDuffColorFilter(((LayerDrawable)localObject).findDrawableByLayerId(16908301), ad.a(paramContext, a.a.colorControlActivated), DEFAULT_MODE);
      localObject = paramDrawable;
    }
    return (Drawable)localObject;
  }
  
  static void tintDrawable(Drawable paramDrawable, ae paramae, int[] paramArrayOfInt)
  {
    if ((DrawableUtils.canSafelyMutateDrawable(paramDrawable)) && (paramDrawable.mutate() != paramDrawable))
    {
      Log.d("AppCompatDrawableManager", "Mutated drawable is not the same instance as the input.");
      return;
    }
    if ((!paramae.d) && (!paramae.c))
    {
      paramDrawable.clearColorFilter();
    }
    else
    {
      ColorStateList localColorStateList;
      if (paramae.d) {
        localColorStateList = paramae.a;
      } else {
        localColorStateList = null;
      }
      if (paramae.c) {
        paramae = paramae.b;
      } else {
        paramae = DEFAULT_MODE;
      }
      paramDrawable.setColorFilter(createTintFilter(localColorStateList, paramae, paramArrayOfInt));
    }
    if (Build.VERSION.SDK_INT <= 23) {
      paramDrawable.invalidateSelf();
    }
  }
  
  static boolean tintDrawableUsingColorFilter(Context paramContext, int paramInt, Drawable paramDrawable)
  {
    PorterDuff.Mode localMode = DEFAULT_MODE;
    boolean bool = arrayContains(COLORFILTER_TINT_COLOR_CONTROL_NORMAL, paramInt);
    int i = 16842801;
    int j;
    if (bool)
    {
      i = a.a.colorControlNormal;
      j = 1;
      paramInt = -1;
    }
    else if (arrayContains(COLORFILTER_COLOR_CONTROL_ACTIVATED, paramInt))
    {
      i = a.a.colorControlActivated;
      j = 1;
      paramInt = -1;
    }
    else if (arrayContains(COLORFILTER_COLOR_BACKGROUND_MULTIPLY, paramInt))
    {
      localMode = PorterDuff.Mode.MULTIPLY;
      j = 1;
      paramInt = -1;
    }
    else if (paramInt == a.e.abc_list_divider_mtrl_alpha)
    {
      i = 16842800;
      paramInt = Math.round(40.8F);
      j = 1;
    }
    else if (paramInt == a.e.abc_dialog_material_background)
    {
      j = 1;
      paramInt = -1;
    }
    else
    {
      j = 0;
      paramInt = -1;
      i = 0;
    }
    if (j != 0)
    {
      Drawable localDrawable = paramDrawable;
      if (DrawableUtils.canSafelyMutateDrawable(paramDrawable)) {
        localDrawable = paramDrawable.mutate();
      }
      localDrawable.setColorFilter(getPorterDuffColorFilter(ad.a(paramContext, i), localMode));
      if (paramInt != -1) {
        localDrawable.setAlpha(paramInt);
      }
      return true;
    }
    return false;
  }
  
  public Drawable getDrawable(Context paramContext, int paramInt)
  {
    return getDrawable(paramContext, paramInt, false);
  }
  
  Drawable getDrawable(Context paramContext, int paramInt, boolean paramBoolean)
  {
    checkVectorDrawableSetup(paramContext);
    Object localObject1 = loadDrawableFromDelegates(paramContext, paramInt);
    Object localObject2 = localObject1;
    if (localObject1 == null) {
      localObject2 = createDrawableIfNeeded(paramContext, paramInt);
    }
    localObject1 = localObject2;
    if (localObject2 == null) {
      localObject1 = ContextCompat.getDrawable(paramContext, paramInt);
    }
    localObject2 = localObject1;
    if (localObject1 != null) {
      localObject2 = tintDrawable(paramContext, paramInt, paramBoolean, (Drawable)localObject1);
    }
    if (localObject2 != null) {
      DrawableUtils.fixDrawable((Drawable)localObject2);
    }
    return (Drawable)localObject2;
  }
  
  ColorStateList getTintList(Context paramContext, int paramInt)
  {
    ColorStateList localColorStateList1 = getTintListFromCache(paramContext, paramInt);
    ColorStateList localColorStateList2 = localColorStateList1;
    if (localColorStateList1 == null)
    {
      if (paramInt == a.e.abc_edit_text_material) {
        localColorStateList1 = b.a(paramContext, a.c.abc_tint_edittext);
      } else if (paramInt == a.e.abc_switch_track_mtrl_alpha) {
        localColorStateList1 = b.a(paramContext, a.c.abc_tint_switch_track);
      } else if (paramInt == a.e.abc_switch_thumb_material) {
        localColorStateList1 = createSwitchThumbColorStateList(paramContext);
      } else if (paramInt == a.e.abc_btn_default_mtrl_shape) {
        localColorStateList1 = createDefaultButtonColorStateList(paramContext);
      } else if (paramInt == a.e.abc_btn_borderless_material) {
        localColorStateList1 = createBorderlessButtonColorStateList(paramContext);
      } else if (paramInt == a.e.abc_btn_colored_material) {
        localColorStateList1 = createColoredButtonColorStateList(paramContext);
      } else if ((paramInt != a.e.abc_spinner_mtrl_am_alpha) && (paramInt != a.e.abc_spinner_textfield_background_material))
      {
        if (arrayContains(TINT_COLOR_CONTROL_NORMAL, paramInt)) {
          localColorStateList1 = ad.b(paramContext, a.a.colorControlNormal);
        } else if (arrayContains(TINT_COLOR_CONTROL_STATE_LIST, paramInt)) {
          localColorStateList1 = b.a(paramContext, a.c.abc_tint_default);
        } else if (arrayContains(TINT_CHECKABLE_BUTTON_LIST, paramInt)) {
          localColorStateList1 = b.a(paramContext, a.c.abc_tint_btn_checkable);
        } else if (paramInt == a.e.abc_seekbar_thumb_material) {
          localColorStateList1 = b.a(paramContext, a.c.abc_tint_seek_thumb);
        }
      }
      else {
        localColorStateList1 = b.a(paramContext, a.c.abc_tint_spinner);
      }
      localColorStateList2 = localColorStateList1;
      if (localColorStateList1 != null)
      {
        addTintListToCache(paramContext, paramInt, localColorStateList1);
        localColorStateList2 = localColorStateList1;
      }
    }
    return localColorStateList2;
  }
  
  public void onConfigurationChanged(Context paramContext)
  {
    synchronized (this.mDrawableCacheLock)
    {
      paramContext = (LongSparseArray)this.mDrawableCaches.get(paramContext);
      if (paramContext != null) {
        paramContext.clear();
      }
      return;
    }
  }
  
  Drawable onDrawableLoadedFromResources(Context paramContext, VectorEnabledTintResources paramVectorEnabledTintResources, int paramInt)
  {
    Drawable localDrawable1 = loadDrawableFromDelegates(paramContext, paramInt);
    Drawable localDrawable2 = localDrawable1;
    if (localDrawable1 == null) {
      localDrawable2 = paramVectorEnabledTintResources.superGetDrawable(paramInt);
    }
    if (localDrawable2 != null) {
      return tintDrawable(paramContext, paramInt, false, localDrawable2);
    }
    return null;
  }
  
  private static class a
    implements AppCompatDrawableManager.c
  {
    public Drawable a(Context paramContext, XmlPullParser paramXmlPullParser, AttributeSet paramAttributeSet, Resources.Theme paramTheme)
    {
      try
      {
        paramContext = c.a(paramContext, paramContext.getResources(), paramXmlPullParser, paramAttributeSet, paramTheme);
        return paramContext;
      }
      catch (Exception paramContext)
      {
        Log.e("AvdcInflateDelegate", "Exception while inflating <animated-vector>", paramContext);
      }
      return null;
    }
  }
  
  private static class b
    extends LruCache<Integer, PorterDuffColorFilter>
  {
    public b(int paramInt)
    {
      super();
    }
    
    private static int b(int paramInt, PorterDuff.Mode paramMode)
    {
      return (paramInt + 31) * 31 + paramMode.hashCode();
    }
    
    PorterDuffColorFilter a(int paramInt, PorterDuff.Mode paramMode)
    {
      return (PorterDuffColorFilter)get(Integer.valueOf(b(paramInt, paramMode)));
    }
    
    PorterDuffColorFilter a(int paramInt, PorterDuff.Mode paramMode, PorterDuffColorFilter paramPorterDuffColorFilter)
    {
      return (PorterDuffColorFilter)put(Integer.valueOf(b(paramInt, paramMode)), paramPorterDuffColorFilter);
    }
  }
  
  private static abstract interface c
  {
    public abstract Drawable a(Context paramContext, XmlPullParser paramXmlPullParser, AttributeSet paramAttributeSet, Resources.Theme paramTheme);
  }
  
  private static class d
    implements AppCompatDrawableManager.c
  {
    public Drawable a(Context paramContext, XmlPullParser paramXmlPullParser, AttributeSet paramAttributeSet, Resources.Theme paramTheme)
    {
      try
      {
        paramContext = i.a(paramContext.getResources(), paramXmlPullParser, paramAttributeSet, paramTheme);
        return paramContext;
      }
      catch (Exception paramContext)
      {
        Log.e("VdcInflateDelegate", "Exception while inflating <vector>", paramContext);
      }
      return null;
    }
  }
}


/* Location:              ~/android/support/v7/widget/AppCompatDrawableManager.class
 *
 * Reversed by:           J
 */