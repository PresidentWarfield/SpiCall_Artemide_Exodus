package android.support.v4.view.accessibility;

import android.view.accessibility.AccessibilityNodeInfo.RangeInfo;

class AccessibilityNodeInfoCompatKitKat
{
  static class RangeInfo
  {
    static float getCurrent(Object paramObject)
    {
      return ((AccessibilityNodeInfo.RangeInfo)paramObject).getCurrent();
    }
    
    static float getMax(Object paramObject)
    {
      return ((AccessibilityNodeInfo.RangeInfo)paramObject).getMax();
    }
    
    static float getMin(Object paramObject)
    {
      return ((AccessibilityNodeInfo.RangeInfo)paramObject).getMin();
    }
    
    static int getType(Object paramObject)
    {
      return ((AccessibilityNodeInfo.RangeInfo)paramObject).getType();
    }
  }
}


/* Location:              ~/android/support/v4/view/accessibility/AccessibilityNodeInfoCompatKitKat.class
 *
 * Reversed by:           J
 */