package android.support.v7.widget;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.content.res.Resources.Theme;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.ConstantState;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.ResourceCursorAdapter;
import android.support.v7.a.a.a;
import android.support.v7.a.a.f;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.WeakHashMap;

class ac
  extends ResourceCursorAdapter
  implements View.OnClickListener
{
  private final SearchManager a = (SearchManager)this.mContext.getSystemService("search");
  private final SearchView b;
  private final SearchableInfo c;
  private final Context d;
  private final WeakHashMap<String, Drawable.ConstantState> e;
  private final int f;
  private boolean g = false;
  private int h = 1;
  private ColorStateList i;
  private int j = -1;
  private int k = -1;
  private int l = -1;
  private int m = -1;
  private int n = -1;
  private int o = -1;
  
  public ac(Context paramContext, SearchView paramSearchView, SearchableInfo paramSearchableInfo, WeakHashMap<String, Drawable.ConstantState> paramWeakHashMap)
  {
    super(paramContext, paramSearchView.getSuggestionRowLayout(), null, true);
    this.b = paramSearchView;
    this.c = paramSearchableInfo;
    this.f = paramSearchView.getSuggestionCommitIconResId();
    this.d = paramContext;
    this.e = paramWeakHashMap;
  }
  
  private Drawable a(ComponentName paramComponentName)
  {
    String str = paramComponentName.flattenToShortString();
    boolean bool = this.e.containsKey(str);
    Object localObject = null;
    Drawable localDrawable = null;
    if (bool)
    {
      paramComponentName = (Drawable.ConstantState)this.e.get(str);
      if (paramComponentName == null) {
        paramComponentName = localDrawable;
      } else {
        paramComponentName = paramComponentName.newDrawable(this.d.getResources());
      }
      return paramComponentName;
    }
    localDrawable = b(paramComponentName);
    if (localDrawable == null) {
      paramComponentName = (ComponentName)localObject;
    } else {
      paramComponentName = localDrawable.getConstantState();
    }
    this.e.put(str, paramComponentName);
    return localDrawable;
  }
  
  private Drawable a(String paramString)
  {
    if ((paramString != null) && (!paramString.isEmpty()) && (!"0".equals(paramString))) {
      try
      {
        int i1 = Integer.parseInt(paramString);
        Object localObject = new java/lang/StringBuilder;
        ((StringBuilder)localObject).<init>();
        ((StringBuilder)localObject).append("android.resource://");
        ((StringBuilder)localObject).append(this.d.getPackageName());
        ((StringBuilder)localObject).append("/");
        ((StringBuilder)localObject).append(i1);
        localObject = ((StringBuilder)localObject).toString();
        Drawable localDrawable2 = b((String)localObject);
        if (localDrawable2 != null) {
          return localDrawable2;
        }
        localDrawable2 = ContextCompat.getDrawable(this.d, i1);
        a((String)localObject, localDrawable2);
        return localDrawable2;
      }
      catch (Resources.NotFoundException localNotFoundException)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("Icon resource not found: ");
        localStringBuilder.append(paramString);
        Log.w("SuggestionsAdapter", localStringBuilder.toString());
        return null;
      }
      catch (NumberFormatException localNumberFormatException)
      {
        Drawable localDrawable1 = b(paramString);
        if (localDrawable1 != null) {
          return localDrawable1;
        }
        localDrawable1 = b(Uri.parse(paramString));
        a(paramString, localDrawable1);
        return localDrawable1;
      }
    }
    return null;
  }
  
  private CharSequence a(CharSequence paramCharSequence)
  {
    if (this.i == null)
    {
      localObject = new TypedValue();
      this.mContext.getTheme().resolveAttribute(a.a.textColorSearchUrl, (TypedValue)localObject, true);
      this.i = this.mContext.getResources().getColorStateList(((TypedValue)localObject).resourceId);
    }
    Object localObject = new SpannableString(paramCharSequence);
    ((SpannableString)localObject).setSpan(new TextAppearanceSpan(null, 0, 0, this.i, null), 0, paramCharSequence.length(), 33);
    return (CharSequence)localObject;
  }
  
  private static String a(Cursor paramCursor, int paramInt)
  {
    if (paramInt == -1) {
      return null;
    }
    try
    {
      paramCursor = paramCursor.getString(paramInt);
      return paramCursor;
    }
    catch (Exception paramCursor)
    {
      Log.e("SuggestionsAdapter", "unexpected error retrieving valid column from cursor, did the remote process die?", paramCursor);
    }
    return null;
  }
  
  public static String a(Cursor paramCursor, String paramString)
  {
    return a(paramCursor, paramCursor.getColumnIndex(paramString));
  }
  
  private void a(Cursor paramCursor)
  {
    if (paramCursor != null) {
      paramCursor = paramCursor.getExtras();
    } else {
      paramCursor = null;
    }
    if ((paramCursor != null) && (paramCursor.getBoolean("in_progress"))) {}
  }
  
  private void a(ImageView paramImageView, Drawable paramDrawable, int paramInt)
  {
    paramImageView.setImageDrawable(paramDrawable);
    if (paramDrawable == null)
    {
      paramImageView.setVisibility(paramInt);
    }
    else
    {
      paramImageView.setVisibility(0);
      paramDrawable.setVisible(false, false);
      paramDrawable.setVisible(true, false);
    }
  }
  
  private void a(TextView paramTextView, CharSequence paramCharSequence)
  {
    paramTextView.setText(paramCharSequence);
    if (TextUtils.isEmpty(paramCharSequence)) {
      paramTextView.setVisibility(8);
    } else {
      paramTextView.setVisibility(0);
    }
  }
  
  private void a(String paramString, Drawable paramDrawable)
  {
    if (paramDrawable != null) {
      this.e.put(paramString, paramDrawable.getConstantState());
    }
  }
  
  private Drawable b(ComponentName paramComponentName)
  {
    Object localObject = this.mContext.getPackageManager();
    try
    {
      ActivityInfo localActivityInfo = ((PackageManager)localObject).getActivityInfo(paramComponentName, 128);
      int i1 = localActivityInfo.getIconResource();
      if (i1 == 0) {
        return null;
      }
      localObject = ((PackageManager)localObject).getDrawable(paramComponentName.getPackageName(), i1, localActivityInfo.applicationInfo);
      if (localObject == null)
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("Invalid icon resource ");
        ((StringBuilder)localObject).append(i1);
        ((StringBuilder)localObject).append(" for ");
        ((StringBuilder)localObject).append(paramComponentName.flattenToShortString());
        Log.w("SuggestionsAdapter", ((StringBuilder)localObject).toString());
        return null;
      }
      return (Drawable)localObject;
    }
    catch (PackageManager.NameNotFoundException paramComponentName)
    {
      Log.w("SuggestionsAdapter", paramComponentName.toString());
    }
    return null;
  }
  
  private Drawable b(Cursor paramCursor)
  {
    int i1 = this.m;
    if (i1 == -1) {
      return null;
    }
    Drawable localDrawable = a(paramCursor.getString(i1));
    if (localDrawable != null) {
      return localDrawable;
    }
    return d(paramCursor);
  }
  
  private Drawable b(Uri paramUri)
  {
    try
    {
      boolean bool = "android.resource".equals(paramUri.getScheme());
      Object localObject1;
      if (bool) {
        try
        {
          Drawable localDrawable = a(paramUri);
          return localDrawable;
        }
        catch (Resources.NotFoundException localNotFoundException)
        {
          localObject3 = new java/io/FileNotFoundException;
          localObject1 = new java/lang/StringBuilder;
          ((StringBuilder)localObject1).<init>();
          ((StringBuilder)localObject1).append("Resource does not exist: ");
          ((StringBuilder)localObject1).append(paramUri);
          ((FileNotFoundException)localObject3).<init>(((StringBuilder)localObject1).toString());
          throw ((Throwable)localObject3);
        }
      }
      Object localObject3 = this.d.getContentResolver().openInputStream(paramUri);
      if (localObject3 != null) {
        try
        {
          localObject1 = Drawable.createFromStream((InputStream)localObject3, null);
          try
          {
            ((InputStream)localObject3).close();
          }
          catch (IOException localIOException1)
          {
            localStringBuilder2 = new java/lang/StringBuilder;
            localStringBuilder2.<init>();
            localStringBuilder2.append("Error closing icon stream for ");
            localStringBuilder2.append(paramUri);
            Log.e("SuggestionsAdapter", localStringBuilder2.toString(), localIOException1);
          }
          return (Drawable)localObject1;
        }
        finally
        {
          try
          {
            localIOException1.close();
          }
          catch (IOException localIOException2)
          {
            StringBuilder localStringBuilder2 = new java/lang/StringBuilder;
            localStringBuilder2.<init>();
            localStringBuilder2.append("Error closing icon stream for ");
            localStringBuilder2.append(paramUri);
            Log.e("SuggestionsAdapter", localStringBuilder2.toString(), localIOException2);
          }
        }
      }
      FileNotFoundException localFileNotFoundException1 = new java/io/FileNotFoundException;
      localStringBuilder1 = new java/lang/StringBuilder;
      localStringBuilder1.<init>();
      localStringBuilder1.append("Failed to open ");
      localStringBuilder1.append(paramUri);
      localFileNotFoundException1.<init>(localStringBuilder1.toString());
      throw localFileNotFoundException1;
    }
    catch (FileNotFoundException localFileNotFoundException2)
    {
      StringBuilder localStringBuilder1 = new StringBuilder();
      localStringBuilder1.append("Icon not found: ");
      localStringBuilder1.append(paramUri);
      localStringBuilder1.append(", ");
      localStringBuilder1.append(localFileNotFoundException2.getMessage());
      Log.w("SuggestionsAdapter", localStringBuilder1.toString());
    }
    return null;
  }
  
  private Drawable b(String paramString)
  {
    paramString = (Drawable.ConstantState)this.e.get(paramString);
    if (paramString == null) {
      return null;
    }
    return paramString.newDrawable();
  }
  
  private Drawable c(Cursor paramCursor)
  {
    int i1 = this.n;
    if (i1 == -1) {
      return null;
    }
    return a(paramCursor.getString(i1));
  }
  
  private Drawable d(Cursor paramCursor)
  {
    paramCursor = a(this.c.getSearchActivity());
    if (paramCursor != null) {
      return paramCursor;
    }
    return this.mContext.getPackageManager().getDefaultActivityIcon();
  }
  
  Cursor a(SearchableInfo paramSearchableInfo, String paramString, int paramInt)
  {
    if (paramSearchableInfo == null) {
      return null;
    }
    Object localObject = paramSearchableInfo.getSuggestAuthority();
    if (localObject == null) {
      return null;
    }
    localObject = new Uri.Builder().scheme("content").authority((String)localObject).query("").fragment("");
    String str = paramSearchableInfo.getSuggestPath();
    if (str != null) {
      ((Uri.Builder)localObject).appendEncodedPath(str);
    }
    ((Uri.Builder)localObject).appendPath("search_suggest_query");
    str = paramSearchableInfo.getSuggestSelection();
    if (str != null)
    {
      paramSearchableInfo = new String[] { paramString };
    }
    else
    {
      ((Uri.Builder)localObject).appendPath(paramString);
      paramSearchableInfo = null;
    }
    if (paramInt > 0) {
      ((Uri.Builder)localObject).appendQueryParameter("limit", String.valueOf(paramInt));
    }
    paramString = ((Uri.Builder)localObject).build();
    return this.mContext.getContentResolver().query(paramString, null, str, paramSearchableInfo, null);
  }
  
  Drawable a(Uri paramUri)
  {
    String str = paramUri.getAuthority();
    if (!TextUtils.isEmpty(str)) {
      try
      {
        Resources localResources = this.mContext.getPackageManager().getResourcesForApplication(str);
        List localList = paramUri.getPathSegments();
        if (localList != null)
        {
          int i1 = localList.size();
          if (i1 == 1)
          {
            try
            {
              i1 = Integer.parseInt((String)localList.get(0));
            }
            catch (NumberFormatException localNumberFormatException)
            {
              localStringBuilder1 = new StringBuilder();
              localStringBuilder1.append("Single path segment is not a resource ID: ");
              localStringBuilder1.append(paramUri);
              throw new FileNotFoundException(localStringBuilder1.toString());
            }
          }
          else
          {
            if (i1 != 2) {
              break label194;
            }
            i1 = localResources.getIdentifier((String)localStringBuilder1.get(1), (String)localStringBuilder1.get(0), str);
          }
          if (i1 != 0) {
            return localResources.getDrawable(i1);
          }
          localStringBuilder1 = new StringBuilder();
          localStringBuilder1.append("No resource found for: ");
          localStringBuilder1.append(paramUri);
          throw new FileNotFoundException(localStringBuilder1.toString());
          label194:
          localStringBuilder1 = new StringBuilder();
          localStringBuilder1.append("More than two path segments: ");
          localStringBuilder1.append(paramUri);
          throw new FileNotFoundException(localStringBuilder1.toString());
        }
        StringBuilder localStringBuilder1 = new StringBuilder();
        localStringBuilder1.append("No path: ");
        localStringBuilder1.append(paramUri);
        throw new FileNotFoundException(localStringBuilder1.toString());
      }
      catch (PackageManager.NameNotFoundException localNameNotFoundException)
      {
        localStringBuilder2 = new StringBuilder();
        localStringBuilder2.append("No package found for authority: ");
        localStringBuilder2.append(paramUri);
        throw new FileNotFoundException(localStringBuilder2.toString());
      }
    }
    StringBuilder localStringBuilder2 = new StringBuilder();
    localStringBuilder2.append("No authority: ");
    localStringBuilder2.append(paramUri);
    throw new FileNotFoundException(localStringBuilder2.toString());
  }
  
  public void a(int paramInt)
  {
    this.h = paramInt;
  }
  
  public void bindView(View paramView, Context paramContext, Cursor paramCursor)
  {
    paramContext = (a)paramView.getTag();
    int i1 = this.o;
    if (i1 != -1) {
      i1 = paramCursor.getInt(i1);
    } else {
      i1 = 0;
    }
    if (paramContext.a != null)
    {
      paramView = a(paramCursor, this.j);
      a(paramContext.a, paramView);
    }
    if (paramContext.b != null)
    {
      paramView = a(paramCursor, this.l);
      if (paramView != null) {
        paramView = a(paramView);
      } else {
        paramView = a(paramCursor, this.k);
      }
      if (TextUtils.isEmpty(paramView))
      {
        if (paramContext.a != null)
        {
          paramContext.a.setSingleLine(false);
          paramContext.a.setMaxLines(2);
        }
      }
      else if (paramContext.a != null)
      {
        paramContext.a.setSingleLine(true);
        paramContext.a.setMaxLines(1);
      }
      a(paramContext.b, paramView);
    }
    if (paramContext.c != null) {
      a(paramContext.c, b(paramCursor), 4);
    }
    if (paramContext.d != null) {
      a(paramContext.d, c(paramCursor), 8);
    }
    int i2 = this.h;
    if ((i2 != 2) && ((i2 != 1) || ((i1 & 0x1) == 0)))
    {
      paramContext.e.setVisibility(8);
    }
    else
    {
      paramContext.e.setVisibility(0);
      paramContext.e.setTag(paramContext.a.getText());
      paramContext.e.setOnClickListener(this);
    }
  }
  
  public void changeCursor(Cursor paramCursor)
  {
    if (this.g)
    {
      Log.w("SuggestionsAdapter", "Tried to change cursor after adapter was closed.");
      if (paramCursor != null) {
        paramCursor.close();
      }
      return;
    }
    try
    {
      super.changeCursor(paramCursor);
      if (paramCursor != null)
      {
        this.j = paramCursor.getColumnIndex("suggest_text_1");
        this.k = paramCursor.getColumnIndex("suggest_text_2");
        this.l = paramCursor.getColumnIndex("suggest_text_2_url");
        this.m = paramCursor.getColumnIndex("suggest_icon_1");
        this.n = paramCursor.getColumnIndex("suggest_icon_2");
        this.o = paramCursor.getColumnIndex("suggest_flags");
      }
    }
    catch (Exception paramCursor)
    {
      Log.e("SuggestionsAdapter", "error changing cursor and caching columns", paramCursor);
    }
  }
  
  public CharSequence convertToString(Cursor paramCursor)
  {
    if (paramCursor == null) {
      return null;
    }
    String str = a(paramCursor, "suggest_intent_query");
    if (str != null) {
      return str;
    }
    if (this.c.shouldRewriteQueryFromData())
    {
      str = a(paramCursor, "suggest_intent_data");
      if (str != null) {
        return str;
      }
    }
    if (this.c.shouldRewriteQueryFromText())
    {
      paramCursor = a(paramCursor, "suggest_text_1");
      if (paramCursor != null) {
        return paramCursor;
      }
    }
    return null;
  }
  
  public View getDropDownView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    try
    {
      paramView = super.getDropDownView(paramInt, paramView, paramViewGroup);
      return paramView;
    }
    catch (RuntimeException paramView)
    {
      Log.w("SuggestionsAdapter", "Search suggestions cursor threw exception.", paramView);
      paramViewGroup = newDropDownView(this.mContext, this.mCursor, paramViewGroup);
      if (paramViewGroup != null) {
        ((a)paramViewGroup.getTag()).a.setText(paramView.toString());
      }
    }
    return paramViewGroup;
  }
  
  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    try
    {
      paramView = super.getView(paramInt, paramView, paramViewGroup);
      return paramView;
    }
    catch (RuntimeException paramView)
    {
      Log.w("SuggestionsAdapter", "Search suggestions cursor threw exception.", paramView);
      paramViewGroup = newView(this.mContext, this.mCursor, paramViewGroup);
      if (paramViewGroup != null) {
        ((a)paramViewGroup.getTag()).a.setText(paramView.toString());
      }
    }
    return paramViewGroup;
  }
  
  public boolean hasStableIds()
  {
    return false;
  }
  
  public View newView(Context paramContext, Cursor paramCursor, ViewGroup paramViewGroup)
  {
    paramContext = super.newView(paramContext, paramCursor, paramViewGroup);
    paramContext.setTag(new a(paramContext));
    ((ImageView)paramContext.findViewById(a.f.edit_query)).setImageResource(this.f);
    return paramContext;
  }
  
  public void notifyDataSetChanged()
  {
    super.notifyDataSetChanged();
    a(getCursor());
  }
  
  public void notifyDataSetInvalidated()
  {
    super.notifyDataSetInvalidated();
    a(getCursor());
  }
  
  public void onClick(View paramView)
  {
    paramView = paramView.getTag();
    if ((paramView instanceof CharSequence)) {
      this.b.onQueryRefine((CharSequence)paramView);
    }
  }
  
  public Cursor runQueryOnBackgroundThread(CharSequence paramCharSequence)
  {
    if (paramCharSequence == null) {
      paramCharSequence = "";
    } else {
      paramCharSequence = paramCharSequence.toString();
    }
    if ((this.b.getVisibility() == 0) && (this.b.getWindowVisibility() == 0))
    {
      try
      {
        paramCharSequence = a(this.c, paramCharSequence, 50);
        if (paramCharSequence != null)
        {
          paramCharSequence.getCount();
          return paramCharSequence;
        }
      }
      catch (RuntimeException paramCharSequence)
      {
        Log.w("SuggestionsAdapter", "Search suggestions query threw an exception.", paramCharSequence);
      }
      return null;
    }
    return null;
  }
  
  private static final class a
  {
    public final TextView a;
    public final TextView b;
    public final ImageView c;
    public final ImageView d;
    public final ImageView e;
    
    public a(View paramView)
    {
      this.a = ((TextView)paramView.findViewById(16908308));
      this.b = ((TextView)paramView.findViewById(16908309));
      this.c = ((ImageView)paramView.findViewById(16908295));
      this.d = ((ImageView)paramView.findViewById(16908296));
      this.e = ((ImageView)paramView.findViewById(a.f.edit_query));
    }
  }
}


/* Location:              ~/android/support/v7/widget/ac.class
 *
 * Reversed by:           J
 */