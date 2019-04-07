package android.support.v7.widget;

import android.view.View;

class v
{
  boolean a = true;
  int b;
  int c;
  int d;
  int e;
  int f = 0;
  int g = 0;
  boolean h;
  boolean i;
  
  View a(RecyclerView.Recycler paramRecycler)
  {
    paramRecycler = paramRecycler.getViewForPosition(this.c);
    this.c += this.d;
    return paramRecycler;
  }
  
  boolean a(RecyclerView.State paramState)
  {
    int j = this.c;
    boolean bool;
    if ((j >= 0) && (j < paramState.getItemCount())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("LayoutState{mAvailable=");
    localStringBuilder.append(this.b);
    localStringBuilder.append(", mCurrentPosition=");
    localStringBuilder.append(this.c);
    localStringBuilder.append(", mItemDirection=");
    localStringBuilder.append(this.d);
    localStringBuilder.append(", mLayoutDirection=");
    localStringBuilder.append(this.e);
    localStringBuilder.append(", mStartLine=");
    localStringBuilder.append(this.f);
    localStringBuilder.append(", mEndLine=");
    localStringBuilder.append(this.g);
    localStringBuilder.append('}');
    return localStringBuilder.toString();
  }
}


/* Location:              ~/android/support/v7/widget/v.class
 *
 * Reversed by:           J
 */