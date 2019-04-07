package android.support.v4.content.res;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.content.res.Resources.Theme;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.v4.graphics.TypefaceCompat;
import android.util.Log;
import android.util.TypedValue;
import android.widget.TextView;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;

public final class ResourcesCompat
{
  private static final String TAG = "ResourcesCompat";
  
  public static int getColor(Resources paramResources, int paramInt, Resources.Theme paramTheme)
  {
    if (Build.VERSION.SDK_INT >= 23) {
      return paramResources.getColor(paramInt, paramTheme);
    }
    return paramResources.getColor(paramInt);
  }
  
  public static ColorStateList getColorStateList(Resources paramResources, int paramInt, Resources.Theme paramTheme)
  {
    if (Build.VERSION.SDK_INT >= 23) {
      return paramResources.getColorStateList(paramInt, paramTheme);
    }
    return paramResources.getColorStateList(paramInt);
  }
  
  public static Drawable getDrawable(Resources paramResources, int paramInt, Resources.Theme paramTheme)
  {
    if (Build.VERSION.SDK_INT >= 21) {
      return paramResources.getDrawable(paramInt, paramTheme);
    }
    return paramResources.getDrawable(paramInt);
  }
  
  public static Drawable getDrawableForDensity(Resources paramResources, int paramInt1, int paramInt2, Resources.Theme paramTheme)
  {
    if (Build.VERSION.SDK_INT >= 21) {
      return paramResources.getDrawableForDensity(paramInt1, paramInt2, paramTheme);
    }
    if (Build.VERSION.SDK_INT >= 15) {
      return paramResources.getDrawableForDensity(paramInt1, paramInt2);
    }
    return paramResources.getDrawable(paramInt1);
  }
  
  public static Typeface getFont(Context paramContext, int paramInt)
  {
    if (paramContext.isRestricted()) {
      return null;
    }
    return loadFont(paramContext, paramInt, new TypedValue(), 0, null);
  }
  
  public static Typeface getFont(Context paramContext, int paramInt1, TypedValue paramTypedValue, int paramInt2, TextView paramTextView)
  {
    if (paramContext.isRestricted()) {
      return null;
    }
    return loadFont(paramContext, paramInt1, paramTypedValue, paramInt2, paramTextView);
  }
  
  private static Typeface loadFont(Context paramContext, int paramInt1, TypedValue paramTypedValue, int paramInt2, TextView paramTextView)
  {
    Resources localResources = paramContext.getResources();
    localResources.getValue(paramInt1, paramTypedValue, true);
    paramContext = loadFont(paramContext, localResources, paramTypedValue, paramInt1, paramInt2, paramTextView);
    if (paramContext != null) {
      return paramContext;
    }
    paramContext = new StringBuilder();
    paramContext.append("Font resource ID #0x");
    paramContext.append(Integer.toHexString(paramInt1));
    throw new Resources.NotFoundException(paramContext.toString());
  }
  
  private static Typeface loadFont(Context paramContext, Resources paramResources, TypedValue paramTypedValue, int paramInt1, int paramInt2, TextView paramTextView)
  {
    if (paramTypedValue.string != null)
    {
      paramTypedValue = paramTypedValue.string.toString();
      if (!paramTypedValue.startsWith("res/")) {
        return null;
      }
      Object localObject = TypefaceCompat.findFromCache(paramResources, paramInt1, paramInt2);
      if (localObject != null) {
        return (Typeface)localObject;
      }
      try
      {
        if (paramTypedValue.toLowerCase().endsWith(".xml"))
        {
          localObject = FontResourcesParserCompat.parse(paramResources.getXml(paramInt1), paramResources);
          if (localObject == null)
          {
            Log.e("ResourcesCompat", "Failed to find font-family tag");
            return null;
          }
          return TypefaceCompat.createFromResourcesFamilyXml(paramContext, (FontResourcesParserCompat.FamilyResourceEntry)localObject, paramResources, paramInt1, paramInt2, paramTextView);
        }
        paramContext = TypefaceCompat.createFromResourcesFontFile(paramContext, paramResources, paramInt1, paramTypedValue, paramInt2);
        return paramContext;
      }
      catch (IOException paramResources)
      {
        paramContext = new StringBuilder();
        paramContext.append("Failed to read xml resource ");
        paramContext.append(paramTypedValue);
        Log.e("ResourcesCompat", paramContext.toString(), paramResources);
      }
      catch (XmlPullParserException paramContext)
      {
        paramResources = new StringBuilder();
        paramResources.append("Failed to parse xml resource ");
        paramResources.append(paramTypedValue);
        Log.e("ResourcesCompat", paramResources.toString(), paramContext);
      }
      return null;
    }
    paramContext = new StringBuilder();
    paramContext.append("Resource \"");
    paramContext.append(paramResources.getResourceName(paramInt1));
    paramContext.append("\" (");
    paramContext.append(Integer.toHexString(paramInt1));
    paramContext.append(") is not a Font: ");
    paramContext.append(paramTypedValue);
    throw new Resources.NotFoundException(paramContext.toString());
  }
}


/* Location:              ~/android/support/v4/content/res/ResourcesCompat.class
 *
 * Reversed by:           J
 */