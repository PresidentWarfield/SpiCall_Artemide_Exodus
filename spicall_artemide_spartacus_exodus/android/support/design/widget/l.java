package android.support.design.widget;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.util.StateSet;
import java.util.ArrayList;

final class l
{
  ValueAnimator a = null;
  private final ArrayList<a> b = new ArrayList();
  private a c = null;
  private final Animator.AnimatorListener d = new AnimatorListenerAdapter()
  {
    public void onAnimationEnd(Animator paramAnonymousAnimator)
    {
      if (l.this.a == paramAnonymousAnimator) {
        l.this.a = null;
      }
    }
  };
  
  private void a(a parama)
  {
    this.a = parama.b;
    this.a.start();
  }
  
  private void b()
  {
    ValueAnimator localValueAnimator = this.a;
    if (localValueAnimator != null)
    {
      localValueAnimator.cancel();
      this.a = null;
    }
  }
  
  public void a()
  {
    ValueAnimator localValueAnimator = this.a;
    if (localValueAnimator != null)
    {
      localValueAnimator.end();
      this.a = null;
    }
  }
  
  void a(int[] paramArrayOfInt)
  {
    int i = this.b.size();
    for (int j = 0; j < i; j++)
    {
      locala = (a)this.b.get(j);
      if (StateSet.stateSetMatches(locala.a, paramArrayOfInt))
      {
        paramArrayOfInt = locala;
        break label54;
      }
    }
    paramArrayOfInt = null;
    label54:
    a locala = this.c;
    if (paramArrayOfInt == locala) {
      return;
    }
    if (locala != null) {
      b();
    }
    this.c = paramArrayOfInt;
    if (paramArrayOfInt != null) {
      a(paramArrayOfInt);
    }
  }
  
  public void a(int[] paramArrayOfInt, ValueAnimator paramValueAnimator)
  {
    paramArrayOfInt = new a(paramArrayOfInt, paramValueAnimator);
    paramValueAnimator.addListener(this.d);
    this.b.add(paramArrayOfInt);
  }
  
  static class a
  {
    final int[] a;
    final ValueAnimator b;
    
    a(int[] paramArrayOfInt, ValueAnimator paramValueAnimator)
    {
      this.a = paramArrayOfInt;
      this.b = paramValueAnimator;
    }
  }
}


/* Location:              ~/android/support/design/widget/l.class
 *
 * Reversed by:           J
 */