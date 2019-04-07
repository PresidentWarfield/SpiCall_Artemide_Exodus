package android.support.b.a;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.Callback;
import android.graphics.drawable.Drawable.ConstantState;
import android.os.Build.VERSION;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.util.ArrayMap;
import android.util.AttributeSet;
import java.util.ArrayList;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;

public class c
  extends h
  implements b
{
  final Drawable.Callback a = new Drawable.Callback()
  {
    public void invalidateDrawable(Drawable paramAnonymousDrawable)
    {
      c.this.invalidateSelf();
    }
    
    public void scheduleDrawable(Drawable paramAnonymousDrawable, Runnable paramAnonymousRunnable, long paramAnonymousLong)
    {
      c.this.scheduleSelf(paramAnonymousRunnable, paramAnonymousLong);
    }
    
    public void unscheduleDrawable(Drawable paramAnonymousDrawable, Runnable paramAnonymousRunnable)
    {
      c.this.unscheduleSelf(paramAnonymousRunnable);
    }
  };
  private a c;
  private Context d;
  private ArgbEvaluator e = null;
  private Animator.AnimatorListener f = null;
  private ArrayList<Object> g = null;
  
  c()
  {
    this(null, null, null);
  }
  
  private c(Context paramContext)
  {
    this(paramContext, null, null);
  }
  
  private c(Context paramContext, a parama, Resources paramResources)
  {
    this.d = paramContext;
    if (parama != null) {
      this.c = parama;
    } else {
      this.c = new a(paramContext, parama, this.a, paramResources);
    }
  }
  
  public static c a(Context paramContext, Resources paramResources, XmlPullParser paramXmlPullParser, AttributeSet paramAttributeSet, Resources.Theme paramTheme)
  {
    paramContext = new c(paramContext);
    paramContext.inflate(paramResources, paramXmlPullParser, paramAttributeSet, paramTheme);
    return paramContext;
  }
  
  private void a(Animator paramAnimator)
  {
    Object localObject;
    if ((paramAnimator instanceof AnimatorSet))
    {
      localObject = ((AnimatorSet)paramAnimator).getChildAnimations();
      if (localObject != null) {
        for (int i = 0; i < ((List)localObject).size(); i++) {
          a((Animator)((List)localObject).get(i));
        }
      }
    }
    if ((paramAnimator instanceof ObjectAnimator))
    {
      paramAnimator = (ObjectAnimator)paramAnimator;
      localObject = paramAnimator.getPropertyName();
      if (("fillColor".equals(localObject)) || ("strokeColor".equals(localObject)))
      {
        if (this.e == null) {
          this.e = new ArgbEvaluator();
        }
        paramAnimator.setEvaluator(this.e);
      }
    }
  }
  
  private void a(String paramString, Animator paramAnimator)
  {
    paramAnimator.setTarget(this.c.b.a(paramString));
    if (Build.VERSION.SDK_INT < 21) {
      a(paramAnimator);
    }
    if (a.a(this.c) == null)
    {
      a.a(this.c, new ArrayList());
      this.c.d = new ArrayMap();
    }
    a.a(this.c).add(paramAnimator);
    this.c.d.put(paramAnimator, paramString);
  }
  
  public void applyTheme(Resources.Theme paramTheme)
  {
    if (this.b != null)
    {
      DrawableCompat.applyTheme(this.b, paramTheme);
      return;
    }
  }
  
  public boolean canApplyTheme()
  {
    if (this.b != null) {
      return DrawableCompat.canApplyTheme(this.b);
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
    this.c.b.draw(paramCanvas);
    if (this.c.c.isStarted()) {
      invalidateSelf();
    }
  }
  
  public int getAlpha()
  {
    if (this.b != null) {
      return DrawableCompat.getAlpha(this.b);
    }
    return this.c.b.getAlpha();
  }
  
  public int getChangingConfigurations()
  {
    if (this.b != null) {
      return this.b.getChangingConfigurations();
    }
    return super.getChangingConfigurations() | this.c.a;
  }
  
  public Drawable.ConstantState getConstantState()
  {
    if ((this.b != null) && (Build.VERSION.SDK_INT >= 24)) {
      return new b(this.b.getConstantState());
    }
    return null;
  }
  
  public int getIntrinsicHeight()
  {
    if (this.b != null) {
      return this.b.getIntrinsicHeight();
    }
    return this.c.b.getIntrinsicHeight();
  }
  
  public int getIntrinsicWidth()
  {
    if (this.b != null) {
      return this.b.getIntrinsicWidth();
    }
    return this.c.b.getIntrinsicWidth();
  }
  
  public int getOpacity()
  {
    if (this.b != null) {
      return this.b.getOpacity();
    }
    return this.c.b.getOpacity();
  }
  
  public void inflate(Resources paramResources, XmlPullParser paramXmlPullParser, AttributeSet paramAttributeSet)
  {
    inflate(paramResources, paramXmlPullParser, paramAttributeSet, null);
  }
  
  public void inflate(Resources paramResources, XmlPullParser paramXmlPullParser, AttributeSet paramAttributeSet, Resources.Theme paramTheme)
  {
    if (this.b != null)
    {
      DrawableCompat.inflate(this.b, paramResources, paramXmlPullParser, paramAttributeSet, paramTheme);
      return;
    }
    int i = paramXmlPullParser.getEventType();
    int j = paramXmlPullParser.getDepth();
    while ((i != 1) && ((paramXmlPullParser.getDepth() >= j + 1) || (i != 3)))
    {
      if (i == 2)
      {
        Object localObject1 = paramXmlPullParser.getName();
        Object localObject2;
        if ("animated-vector".equals(localObject1))
        {
          localObject1 = TypedArrayUtils.obtainAttributes(paramResources, paramTheme, paramAttributeSet, a.e);
          i = ((TypedArray)localObject1).getResourceId(0, 0);
          if (i != 0)
          {
            localObject2 = i.a(paramResources, i, paramTheme);
            ((i)localObject2).a(false);
            ((i)localObject2).setCallback(this.a);
            if (this.c.b != null) {
              this.c.b.setCallback(null);
            }
            this.c.b = ((i)localObject2);
          }
          ((TypedArray)localObject1).recycle();
        }
        else if ("target".equals(localObject1))
        {
          localObject2 = paramResources.obtainAttributes(paramAttributeSet, a.f);
          localObject1 = ((TypedArray)localObject2).getString(0);
          i = ((TypedArray)localObject2).getResourceId(1, 0);
          if (i != 0)
          {
            Context localContext = this.d;
            if (localContext != null)
            {
              a((String)localObject1, e.a(localContext, i));
            }
            else
            {
              ((TypedArray)localObject2).recycle();
              throw new IllegalStateException("Context can't be null when inflating animators");
            }
          }
          ((TypedArray)localObject2).recycle();
        }
      }
      i = paramXmlPullParser.next();
    }
    this.c.a();
  }
  
  public boolean isAutoMirrored()
  {
    if (this.b != null) {
      return DrawableCompat.isAutoMirrored(this.b);
    }
    return this.c.b.isAutoMirrored();
  }
  
  public boolean isRunning()
  {
    if (this.b != null) {
      return ((AnimatedVectorDrawable)this.b).isRunning();
    }
    return this.c.c.isRunning();
  }
  
  public boolean isStateful()
  {
    if (this.b != null) {
      return this.b.isStateful();
    }
    return this.c.b.isStateful();
  }
  
  public Drawable mutate()
  {
    if (this.b != null) {
      this.b.mutate();
    }
    return this;
  }
  
  protected void onBoundsChange(Rect paramRect)
  {
    if (this.b != null)
    {
      this.b.setBounds(paramRect);
      return;
    }
    this.c.b.setBounds(paramRect);
  }
  
  protected boolean onLevelChange(int paramInt)
  {
    if (this.b != null) {
      return this.b.setLevel(paramInt);
    }
    return this.c.b.setLevel(paramInt);
  }
  
  protected boolean onStateChange(int[] paramArrayOfInt)
  {
    if (this.b != null) {
      return this.b.setState(paramArrayOfInt);
    }
    return this.c.b.setState(paramArrayOfInt);
  }
  
  public void setAlpha(int paramInt)
  {
    if (this.b != null)
    {
      this.b.setAlpha(paramInt);
      return;
    }
    this.c.b.setAlpha(paramInt);
  }
  
  public void setAutoMirrored(boolean paramBoolean)
  {
    if (this.b != null)
    {
      DrawableCompat.setAutoMirrored(this.b, paramBoolean);
      return;
    }
    this.c.b.setAutoMirrored(paramBoolean);
  }
  
  public void setColorFilter(ColorFilter paramColorFilter)
  {
    if (this.b != null)
    {
      this.b.setColorFilter(paramColorFilter);
      return;
    }
    this.c.b.setColorFilter(paramColorFilter);
  }
  
  public void setTint(int paramInt)
  {
    if (this.b != null)
    {
      DrawableCompat.setTint(this.b, paramInt);
      return;
    }
    this.c.b.setTint(paramInt);
  }
  
  public void setTintList(ColorStateList paramColorStateList)
  {
    if (this.b != null)
    {
      DrawableCompat.setTintList(this.b, paramColorStateList);
      return;
    }
    this.c.b.setTintList(paramColorStateList);
  }
  
  public void setTintMode(PorterDuff.Mode paramMode)
  {
    if (this.b != null)
    {
      DrawableCompat.setTintMode(this.b, paramMode);
      return;
    }
    this.c.b.setTintMode(paramMode);
  }
  
  public boolean setVisible(boolean paramBoolean1, boolean paramBoolean2)
  {
    if (this.b != null) {
      return this.b.setVisible(paramBoolean1, paramBoolean2);
    }
    this.c.b.setVisible(paramBoolean1, paramBoolean2);
    return super.setVisible(paramBoolean1, paramBoolean2);
  }
  
  public void start()
  {
    if (this.b != null)
    {
      ((AnimatedVectorDrawable)this.b).start();
      return;
    }
    if (this.c.c.isStarted()) {
      return;
    }
    this.c.c.start();
    invalidateSelf();
  }
  
  public void stop()
  {
    if (this.b != null)
    {
      ((AnimatedVectorDrawable)this.b).stop();
      return;
    }
    this.c.c.end();
  }
  
  private static class a
    extends Drawable.ConstantState
  {
    int a;
    i b;
    AnimatorSet c;
    ArrayMap<Animator, String> d;
    private ArrayList<Animator> e;
    
    public a(Context paramContext, a parama, Drawable.Callback paramCallback, Resources paramResources)
    {
      if (parama != null)
      {
        this.a = parama.a;
        paramContext = parama.b;
        int i = 0;
        if (paramContext != null)
        {
          paramContext = paramContext.getConstantState();
          if (paramResources != null) {
            this.b = ((i)paramContext.newDrawable(paramResources));
          } else {
            this.b = ((i)paramContext.newDrawable());
          }
          this.b = ((i)this.b.mutate());
          this.b.setCallback(paramCallback);
          this.b.setBounds(parama.b.getBounds());
          this.b.a(false);
        }
        paramContext = parama.e;
        if (paramContext != null)
        {
          int j = paramContext.size();
          this.e = new ArrayList(j);
          this.d = new ArrayMap(j);
          while (i < j)
          {
            paramCallback = (Animator)parama.e.get(i);
            paramContext = paramCallback.clone();
            paramCallback = (String)parama.d.get(paramCallback);
            paramContext.setTarget(this.b.a(paramCallback));
            this.e.add(paramContext);
            this.d.put(paramContext, paramCallback);
            i++;
          }
          a();
        }
      }
    }
    
    public void a()
    {
      if (this.c == null) {
        this.c = new AnimatorSet();
      }
      this.c.playTogether(this.e);
    }
    
    public int getChangingConfigurations()
    {
      return this.a;
    }
    
    public Drawable newDrawable()
    {
      throw new IllegalStateException("No constant state support for SDK < 24.");
    }
    
    public Drawable newDrawable(Resources paramResources)
    {
      throw new IllegalStateException("No constant state support for SDK < 24.");
    }
  }
  
  private static class b
    extends Drawable.ConstantState
  {
    private final Drawable.ConstantState a;
    
    public b(Drawable.ConstantState paramConstantState)
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
      c localc = new c();
      localc.b = this.a.newDrawable();
      localc.b.setCallback(localc.a);
      return localc;
    }
    
    public Drawable newDrawable(Resources paramResources)
    {
      c localc = new c();
      localc.b = this.a.newDrawable(paramResources);
      localc.b.setCallback(localc.a);
      return localc;
    }
    
    public Drawable newDrawable(Resources paramResources, Resources.Theme paramTheme)
    {
      c localc = new c();
      localc.b = this.a.newDrawable(paramResources, paramTheme);
      localc.b.setCallback(localc.a);
      return localc;
    }
  }
}


/* Location:              ~/android/support/b/a/c.class
 *
 * Reversed by:           J
 */