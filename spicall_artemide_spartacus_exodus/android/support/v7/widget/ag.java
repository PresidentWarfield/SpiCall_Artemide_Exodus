package android.support.v7.widget;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnAttachStateChangeListener;
import android.view.View.OnHoverListener;
import android.view.View.OnLongClickListener;
import android.view.ViewConfiguration;
import android.view.accessibility.AccessibilityManager;

class ag
  implements View.OnAttachStateChangeListener, View.OnHoverListener, View.OnLongClickListener
{
  private static ag i;
  private final View a;
  private final CharSequence b;
  private final Runnable c = new Runnable()
  {
    public void run()
    {
      ag.a(ag.this, false);
    }
  };
  private final Runnable d = new Runnable()
  {
    public void run()
    {
      ag.a(ag.this);
    }
  };
  private int e;
  private int f;
  private ah g;
  private boolean h;
  
  private ag(View paramView, CharSequence paramCharSequence)
  {
    this.a = paramView;
    this.b = paramCharSequence;
    this.a.setOnLongClickListener(this);
    this.a.setOnHoverListener(this);
  }
  
  private void a()
  {
    if (i == this)
    {
      i = null;
      ah localah = this.g;
      if (localah != null)
      {
        localah.a();
        this.g = null;
        this.a.removeOnAttachStateChangeListener(this);
      }
      else
      {
        Log.e("TooltipCompatHandler", "sActiveHandler.mPopup == null");
      }
    }
    this.a.removeCallbacks(this.c);
    this.a.removeCallbacks(this.d);
  }
  
  public static void a(View paramView, CharSequence paramCharSequence)
  {
    if (TextUtils.isEmpty(paramCharSequence))
    {
      paramCharSequence = i;
      if ((paramCharSequence != null) && (paramCharSequence.a == paramView)) {
        paramCharSequence.a();
      }
      paramView.setOnLongClickListener(null);
      paramView.setLongClickable(false);
      paramView.setOnHoverListener(null);
    }
    else
    {
      new ag(paramView, paramCharSequence);
    }
  }
  
  private void a(boolean paramBoolean)
  {
    if (!ViewCompat.isAttachedToWindow(this.a)) {
      return;
    }
    ag localag = i;
    if (localag != null) {
      localag.a();
    }
    i = this;
    this.h = paramBoolean;
    this.g = new ah(this.a.getContext());
    this.g.a(this.a, this.e, this.f, this.h, this.b);
    this.a.addOnAttachStateChangeListener(this);
    long l;
    if (this.h) {
      l = 2500L;
    } else if ((ViewCompat.getWindowSystemUiVisibility(this.a) & 0x1) == 1) {
      l = 3000L - ViewConfiguration.getLongPressTimeout();
    } else {
      l = 15000L - ViewConfiguration.getLongPressTimeout();
    }
    this.a.removeCallbacks(this.d);
    this.a.postDelayed(this.d, l);
  }
  
  public boolean onHover(View paramView, MotionEvent paramMotionEvent)
  {
    if ((this.g != null) && (this.h)) {
      return false;
    }
    paramView = (AccessibilityManager)this.a.getContext().getSystemService("accessibility");
    if ((paramView.isEnabled()) && (paramView.isTouchExplorationEnabled())) {
      return false;
    }
    int j = paramMotionEvent.getAction();
    if (j != 7)
    {
      if (j == 10) {
        a();
      }
    }
    else if ((this.a.isEnabled()) && (this.g == null))
    {
      this.e = ((int)paramMotionEvent.getX());
      this.f = ((int)paramMotionEvent.getY());
      this.a.removeCallbacks(this.c);
      this.a.postDelayed(this.c, ViewConfiguration.getLongPressTimeout());
    }
    return false;
  }
  
  public boolean onLongClick(View paramView)
  {
    this.e = (paramView.getWidth() / 2);
    this.f = (paramView.getHeight() / 2);
    a(true);
    return true;
  }
  
  public void onViewAttachedToWindow(View paramView) {}
  
  public void onViewDetachedFromWindow(View paramView)
  {
    a();
  }
}


/* Location:              ~/android/support/v7/widget/ag.class
 *
 * Reversed by:           J
 */