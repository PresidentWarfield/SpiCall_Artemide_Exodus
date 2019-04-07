package android.support.v4.widget;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;

final class SwipeProgressBar
{
  private static final int ANIMATION_DURATION_MS = 2000;
  private static final int COLOR1 = -1291845632;
  private static final int COLOR2 = Integer.MIN_VALUE;
  private static final int COLOR3 = 1291845632;
  private static final int COLOR4 = 436207616;
  private static final int FINISH_ANIMATION_DURATION_MS = 1000;
  private static final Interpolator INTERPOLATOR = new FastOutSlowInInterpolator();
  private Rect mBounds = new Rect();
  private final RectF mClipRect = new RectF();
  private int mColor1;
  private int mColor2;
  private int mColor3;
  private int mColor4;
  private long mFinishTime;
  private final Paint mPaint = new Paint();
  private View mParent;
  private boolean mRunning;
  private long mStartTime;
  private float mTriggerPercentage;
  
  SwipeProgressBar(View paramView)
  {
    this.mParent = paramView;
    this.mColor1 = -1291845632;
    this.mColor2 = Integer.MIN_VALUE;
    this.mColor3 = 1291845632;
    this.mColor4 = 436207616;
  }
  
  private void drawCircle(Canvas paramCanvas, float paramFloat1, float paramFloat2, int paramInt, float paramFloat3)
  {
    this.mPaint.setColor(paramInt);
    paramCanvas.save();
    paramCanvas.translate(paramFloat1, paramFloat2);
    paramFloat2 = INTERPOLATOR.getInterpolation(paramFloat3);
    paramCanvas.scale(paramFloat2, paramFloat2);
    paramCanvas.drawCircle(0.0F, 0.0F, paramFloat1, this.mPaint);
    paramCanvas.restore();
  }
  
  private void drawTrigger(Canvas paramCanvas, int paramInt1, int paramInt2)
  {
    this.mPaint.setColor(this.mColor1);
    float f = paramInt1;
    paramCanvas.drawCircle(f, paramInt2, this.mTriggerPercentage * f, this.mPaint);
  }
  
  void draw(Canvas paramCanvas)
  {
    int i = this.mBounds.width();
    int j = this.mBounds.height();
    int k = i / 2;
    int m = j / 2;
    i = paramCanvas.save();
    paramCanvas.clipRect(this.mBounds);
    float f1;
    if ((!this.mRunning) && (this.mFinishTime <= 0L))
    {
      f1 = this.mTriggerPercentage;
      j = i;
      if (f1 > 0.0F)
      {
        j = i;
        if (f1 <= 1.0D)
        {
          drawTrigger(paramCanvas, k, m);
          j = i;
        }
      }
    }
    else
    {
      long l1 = AnimationUtils.currentAnimationTimeMillis();
      long l2 = this.mStartTime;
      long l3 = (l1 - l2) / 2000L;
      f1 = (float)((l1 - l2) % 2000L) / 20.0F;
      boolean bool = this.mRunning;
      int n = 0;
      float f3;
      if (!bool)
      {
        l2 = this.mFinishTime;
        if (l1 - l2 >= 1000L)
        {
          this.mFinishTime = 0L;
          return;
        }
        float f2 = (float)((l1 - l2) % 1000L) / 10.0F / 100.0F;
        f3 = k;
        f2 = INTERPOLATOR.getInterpolation(f2) * f3;
        this.mClipRect.set(f3 - f2, 0.0F, f3 + f2, j);
        paramCanvas.saveLayerAlpha(this.mClipRect, 0, 0);
        n = 1;
      }
      if (l3 == 0L) {
        paramCanvas.drawColor(this.mColor1);
      } else if ((f1 >= 0.0F) && (f1 < 25.0F)) {
        paramCanvas.drawColor(this.mColor4);
      } else if ((f1 >= 25.0F) && (f1 < 50.0F)) {
        paramCanvas.drawColor(this.mColor1);
      } else if ((f1 >= 50.0F) && (f1 < 75.0F)) {
        paramCanvas.drawColor(this.mColor2);
      } else {
        paramCanvas.drawColor(this.mColor3);
      }
      if ((f1 >= 0.0F) && (f1 <= 25.0F))
      {
        f3 = (f1 + 25.0F) * 2.0F / 100.0F;
        drawCircle(paramCanvas, k, m, this.mColor1, f3);
      }
      if ((f1 >= 0.0F) && (f1 <= 50.0F))
      {
        f3 = f1 * 2.0F / 100.0F;
        drawCircle(paramCanvas, k, m, this.mColor2, f3);
      }
      if ((f1 >= 25.0F) && (f1 <= 75.0F))
      {
        f3 = (f1 - 25.0F) * 2.0F / 100.0F;
        drawCircle(paramCanvas, k, m, this.mColor3, f3);
      }
      if ((f1 >= 50.0F) && (f1 <= 100.0F))
      {
        f3 = (f1 - 50.0F) * 2.0F / 100.0F;
        drawCircle(paramCanvas, k, m, this.mColor4, f3);
      }
      if ((f1 >= 75.0F) && (f1 <= 100.0F))
      {
        f1 = (f1 - 75.0F) * 2.0F / 100.0F;
        drawCircle(paramCanvas, k, m, this.mColor1, f1);
      }
      j = i;
      if (this.mTriggerPercentage > 0.0F)
      {
        j = i;
        if (n != 0)
        {
          paramCanvas.restoreToCount(i);
          j = paramCanvas.save();
          paramCanvas.clipRect(this.mBounds);
          drawTrigger(paramCanvas, k, m);
        }
      }
      ViewCompat.postInvalidateOnAnimation(this.mParent, this.mBounds.left, this.mBounds.top, this.mBounds.right, this.mBounds.bottom);
    }
    paramCanvas.restoreToCount(j);
  }
  
  boolean isRunning()
  {
    boolean bool;
    if ((!this.mRunning) && (this.mFinishTime <= 0L)) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  void setBounds(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    Rect localRect = this.mBounds;
    localRect.left = paramInt1;
    localRect.top = paramInt2;
    localRect.right = paramInt3;
    localRect.bottom = paramInt4;
  }
  
  void setColorScheme(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.mColor1 = paramInt1;
    this.mColor2 = paramInt2;
    this.mColor3 = paramInt3;
    this.mColor4 = paramInt4;
  }
  
  void setTriggerPercentage(float paramFloat)
  {
    this.mTriggerPercentage = paramFloat;
    this.mStartTime = 0L;
    ViewCompat.postInvalidateOnAnimation(this.mParent, this.mBounds.left, this.mBounds.top, this.mBounds.right, this.mBounds.bottom);
  }
  
  void start()
  {
    if (!this.mRunning)
    {
      this.mTriggerPercentage = 0.0F;
      this.mStartTime = AnimationUtils.currentAnimationTimeMillis();
      this.mRunning = true;
      this.mParent.postInvalidate();
    }
  }
  
  void stop()
  {
    if (this.mRunning)
    {
      this.mTriggerPercentage = 0.0F;
      this.mFinishTime = AnimationUtils.currentAnimationTimeMillis();
      this.mRunning = false;
      this.mParent.postInvalidate();
    }
  }
}


/* Location:              ~/android/support/v4/widget/SwipeProgressBar.class
 *
 * Reversed by:           J
 */