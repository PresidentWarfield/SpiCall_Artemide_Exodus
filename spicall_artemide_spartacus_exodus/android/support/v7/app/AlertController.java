package android.support.v7.app;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnKeyListener;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.NestedScrollView.OnScrollChangeListener;
import android.support.v7.a.a.a;
import android.support.v7.a.a.f;
import android.support.v7.a.a.j;
import android.support.v7.widget.LinearLayoutCompat.LayoutParams;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.view.ViewStub;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.CursorAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import java.lang.ref.WeakReference;

class AlertController
{
  private boolean A = false;
  private CharSequence B;
  private CharSequence C;
  private CharSequence D;
  private int E = 0;
  private Drawable F;
  private ImageView G;
  private TextView H;
  private TextView I;
  private View J;
  private int K;
  private int L;
  private boolean M;
  private int N = 0;
  private final View.OnClickListener O = new View.OnClickListener()
  {
    public void onClick(View paramAnonymousView)
    {
      if ((paramAnonymousView == AlertController.this.c) && (AlertController.this.d != null)) {
        paramAnonymousView = Message.obtain(AlertController.this.d);
      } else if ((paramAnonymousView == AlertController.this.e) && (AlertController.this.f != null)) {
        paramAnonymousView = Message.obtain(AlertController.this.f);
      } else if ((paramAnonymousView == AlertController.this.g) && (AlertController.this.h != null)) {
        paramAnonymousView = Message.obtain(AlertController.this.h);
      } else {
        paramAnonymousView = null;
      }
      if (paramAnonymousView != null) {
        paramAnonymousView.sendToTarget();
      }
      AlertController.this.p.obtainMessage(1, AlertController.this.a).sendToTarget();
    }
  };
  final n a;
  ListView b;
  Button c;
  Message d;
  Button e;
  Message f;
  Button g;
  Message h;
  NestedScrollView i;
  ListAdapter j;
  int k = -1;
  int l;
  int m;
  int n;
  int o;
  Handler p;
  private final Context q;
  private final Window r;
  private CharSequence s;
  private CharSequence t;
  private View u;
  private int v;
  private int w;
  private int x;
  private int y;
  private int z;
  
  public AlertController(Context paramContext, n paramn, Window paramWindow)
  {
    this.q = paramContext;
    this.a = paramn;
    this.r = paramWindow;
    this.p = new b(paramn);
    paramContext = paramContext.obtainStyledAttributes(null, a.j.AlertDialog, a.a.alertDialogStyle, 0);
    this.K = paramContext.getResourceId(a.j.AlertDialog_android_layout, 0);
    this.L = paramContext.getResourceId(a.j.AlertDialog_buttonPanelSideLayout, 0);
    this.l = paramContext.getResourceId(a.j.AlertDialog_listLayout, 0);
    this.m = paramContext.getResourceId(a.j.AlertDialog_multiChoiceItemLayout, 0);
    this.n = paramContext.getResourceId(a.j.AlertDialog_singleChoiceItemLayout, 0);
    this.o = paramContext.getResourceId(a.j.AlertDialog_listItemLayout, 0);
    this.M = paramContext.getBoolean(a.j.AlertDialog_showTitle, true);
    paramContext.recycle();
    paramn.a(1);
  }
  
  private ViewGroup a(View paramView1, View paramView2)
  {
    if (paramView1 == null)
    {
      paramView1 = paramView2;
      if ((paramView2 instanceof ViewStub)) {
        paramView1 = ((ViewStub)paramView2).inflate();
      }
      return (ViewGroup)paramView1;
    }
    if (paramView2 != null)
    {
      ViewParent localViewParent = paramView2.getParent();
      if ((localViewParent instanceof ViewGroup)) {
        ((ViewGroup)localViewParent).removeView(paramView2);
      }
    }
    paramView2 = paramView1;
    if ((paramView1 instanceof ViewStub)) {
      paramView2 = ((ViewStub)paramView1).inflate();
    }
    return (ViewGroup)paramView2;
  }
  
  static void a(View paramView1, View paramView2, View paramView3)
  {
    int i1 = 0;
    int i2;
    if (paramView2 != null)
    {
      if (paramView1.canScrollVertically(-1)) {
        i2 = 0;
      } else {
        i2 = 4;
      }
      paramView2.setVisibility(i2);
    }
    if (paramView3 != null)
    {
      if (paramView1.canScrollVertically(1)) {
        i2 = i1;
      } else {
        i2 = 4;
      }
      paramView3.setVisibility(i2);
    }
  }
  
  private void a(ViewGroup paramViewGroup)
  {
    View localView = this.u;
    int i1 = 0;
    if (localView == null) {
      if (this.v != 0) {
        localView = LayoutInflater.from(this.q).inflate(this.v, paramViewGroup, false);
      } else {
        localView = null;
      }
    }
    if (localView != null) {
      i1 = 1;
    }
    if ((i1 == 0) || (!a(localView))) {
      this.r.setFlags(131072, 131072);
    }
    if (i1 != 0)
    {
      FrameLayout localFrameLayout = (FrameLayout)this.r.findViewById(a.f.custom);
      localFrameLayout.addView(localView, new ViewGroup.LayoutParams(-1, -1));
      if (this.A) {
        localFrameLayout.setPadding(this.w, this.x, this.y, this.z);
      }
      if (this.b != null) {
        ((LinearLayoutCompat.LayoutParams)paramViewGroup.getLayoutParams()).weight = 0.0F;
      }
    }
    else
    {
      paramViewGroup.setVisibility(8);
    }
  }
  
  private void a(ViewGroup paramViewGroup, final View paramView, int paramInt1, int paramInt2)
  {
    View localView = this.r.findViewById(a.f.scrollIndicatorUp);
    final Object localObject = this.r.findViewById(a.f.scrollIndicatorDown);
    if (Build.VERSION.SDK_INT >= 23)
    {
      ViewCompat.setScrollIndicators(paramView, paramInt1, paramInt2);
      if (localView != null) {
        paramViewGroup.removeView(localView);
      }
      if (localObject != null) {
        paramViewGroup.removeView((View)localObject);
      }
    }
    else
    {
      ListView localListView = null;
      paramView = localView;
      if (localView != null)
      {
        paramView = localView;
        if ((paramInt1 & 0x1) == 0)
        {
          paramViewGroup.removeView(localView);
          paramView = null;
        }
      }
      if ((localObject != null) && ((paramInt1 & 0x2) == 0))
      {
        paramViewGroup.removeView((View)localObject);
        localObject = localListView;
      }
      if ((paramView != null) || (localObject != null)) {
        if (this.t != null)
        {
          this.i.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener()
          {
            public void onScrollChange(NestedScrollView paramAnonymousNestedScrollView, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, int paramAnonymousInt4)
            {
              AlertController.a(paramAnonymousNestedScrollView, paramView, localObject);
            }
          });
          this.i.post(new Runnable()
          {
            public void run()
            {
              AlertController.a(AlertController.this.i, paramView, localObject);
            }
          });
        }
        else
        {
          localListView = this.b;
          if (localListView != null)
          {
            localListView.setOnScrollListener(new AbsListView.OnScrollListener()
            {
              public void onScroll(AbsListView paramAnonymousAbsListView, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
              {
                AlertController.a(paramAnonymousAbsListView, paramView, localObject);
              }
              
              public void onScrollStateChanged(AbsListView paramAnonymousAbsListView, int paramAnonymousInt) {}
            });
            this.b.post(new Runnable()
            {
              public void run()
              {
                AlertController.a(AlertController.this.b, paramView, localObject);
              }
            });
          }
          else
          {
            if (paramView != null) {
              paramViewGroup.removeView(paramView);
            }
            if (localObject != null) {
              paramViewGroup.removeView((View)localObject);
            }
          }
        }
      }
    }
  }
  
  private void a(Button paramButton)
  {
    LinearLayout.LayoutParams localLayoutParams = (LinearLayout.LayoutParams)paramButton.getLayoutParams();
    localLayoutParams.gravity = 1;
    localLayoutParams.weight = 0.5F;
    paramButton.setLayoutParams(localLayoutParams);
  }
  
  private static boolean a(Context paramContext)
  {
    TypedValue localTypedValue = new TypedValue();
    paramContext = paramContext.getTheme();
    int i1 = a.a.alertDialogCenterButtons;
    boolean bool = true;
    paramContext.resolveAttribute(i1, localTypedValue, true);
    if (localTypedValue.data == 0) {
      bool = false;
    }
    return bool;
  }
  
  static boolean a(View paramView)
  {
    if (paramView.onCheckIsTextEditor()) {
      return true;
    }
    if (!(paramView instanceof ViewGroup)) {
      return false;
    }
    paramView = (ViewGroup)paramView;
    int i1 = paramView.getChildCount();
    while (i1 > 0)
    {
      int i2 = i1 - 1;
      i1 = i2;
      if (a(paramView.getChildAt(i2))) {
        return true;
      }
    }
    return false;
  }
  
  private int b()
  {
    int i1 = this.L;
    if (i1 == 0) {
      return this.K;
    }
    if (this.N == 1) {
      return i1;
    }
    return this.K;
  }
  
  private void b(ViewGroup paramViewGroup)
  {
    if (this.J != null)
    {
      ViewGroup.LayoutParams localLayoutParams = new ViewGroup.LayoutParams(-1, -2);
      paramViewGroup.addView(this.J, 0, localLayoutParams);
      this.r.findViewById(a.f.title_template).setVisibility(8);
    }
    else
    {
      this.G = ((ImageView)this.r.findViewById(16908294));
      if (((TextUtils.isEmpty(this.s) ^ true)) && (this.M))
      {
        this.H = ((TextView)this.r.findViewById(a.f.alertTitle));
        this.H.setText(this.s);
        int i1 = this.E;
        if (i1 != 0)
        {
          this.G.setImageResource(i1);
        }
        else
        {
          paramViewGroup = this.F;
          if (paramViewGroup != null)
          {
            this.G.setImageDrawable(paramViewGroup);
          }
          else
          {
            this.H.setPadding(this.G.getPaddingLeft(), this.G.getPaddingTop(), this.G.getPaddingRight(), this.G.getPaddingBottom());
            this.G.setVisibility(8);
          }
        }
      }
      else
      {
        this.r.findViewById(a.f.title_template).setVisibility(8);
        this.G.setVisibility(8);
        paramViewGroup.setVisibility(8);
      }
    }
  }
  
  private void c()
  {
    Object localObject1 = this.r.findViewById(a.f.parentPanel);
    Object localObject2 = ((View)localObject1).findViewById(a.f.topPanel);
    Object localObject3 = ((View)localObject1).findViewById(a.f.contentPanel);
    Object localObject4 = ((View)localObject1).findViewById(a.f.buttonPanel);
    localObject1 = (ViewGroup)((View)localObject1).findViewById(a.f.customPanel);
    a((ViewGroup)localObject1);
    View localView1 = ((ViewGroup)localObject1).findViewById(a.f.topPanel);
    View localView2 = ((ViewGroup)localObject1).findViewById(a.f.contentPanel);
    View localView3 = ((ViewGroup)localObject1).findViewById(a.f.buttonPanel);
    localObject2 = a(localView1, (View)localObject2);
    localObject3 = a(localView2, (View)localObject3);
    localObject4 = a(localView3, (View)localObject4);
    c((ViewGroup)localObject3);
    d((ViewGroup)localObject4);
    b((ViewGroup)localObject2);
    int i1 = 0;
    int i2;
    if ((localObject1 != null) && (((ViewGroup)localObject1).getVisibility() != 8)) {
      i2 = 1;
    } else {
      i2 = 0;
    }
    int i3;
    if ((localObject2 != null) && (((ViewGroup)localObject2).getVisibility() != 8)) {
      i3 = 1;
    } else {
      i3 = 0;
    }
    boolean bool;
    if ((localObject4 != null) && (((ViewGroup)localObject4).getVisibility() != 8)) {
      bool = true;
    } else {
      bool = false;
    }
    if ((!bool) && (localObject3 != null))
    {
      localObject1 = ((ViewGroup)localObject3).findViewById(a.f.textSpacerNoButtons);
      if (localObject1 != null) {
        ((View)localObject1).setVisibility(0);
      }
    }
    if (i3 != 0)
    {
      localObject1 = this.i;
      if (localObject1 != null) {
        ((NestedScrollView)localObject1).setClipToPadding(true);
      }
      localObject1 = null;
      if ((this.t != null) || (this.b != null)) {
        localObject1 = ((ViewGroup)localObject2).findViewById(a.f.titleDividerNoCustom);
      }
      if (localObject1 != null) {
        ((View)localObject1).setVisibility(0);
      }
    }
    else if (localObject3 != null)
    {
      localObject1 = ((ViewGroup)localObject3).findViewById(a.f.textSpacerNoTitle);
      if (localObject1 != null) {
        ((View)localObject1).setVisibility(0);
      }
    }
    localObject1 = this.b;
    if ((localObject1 instanceof RecycleListView)) {
      ((RecycleListView)localObject1).a(i3, bool);
    }
    if (i2 == 0)
    {
      localObject1 = this.b;
      if (localObject1 == null) {
        localObject1 = this.i;
      }
      if (localObject1 != null)
      {
        i2 = i1;
        if (bool) {
          i2 = 2;
        }
        a((ViewGroup)localObject3, (View)localObject1, i3 | i2, 3);
      }
    }
    localObject3 = this.b;
    if (localObject3 != null)
    {
      localObject1 = this.j;
      if (localObject1 != null)
      {
        ((ListView)localObject3).setAdapter((ListAdapter)localObject1);
        i2 = this.k;
        if (i2 > -1)
        {
          ((ListView)localObject3).setItemChecked(i2, true);
          ((ListView)localObject3).setSelection(i2);
        }
      }
    }
  }
  
  private void c(ViewGroup paramViewGroup)
  {
    this.i = ((NestedScrollView)this.r.findViewById(a.f.scrollView));
    this.i.setFocusable(false);
    this.i.setNestedScrollingEnabled(false);
    this.I = ((TextView)paramViewGroup.findViewById(16908299));
    TextView localTextView = this.I;
    if (localTextView == null) {
      return;
    }
    CharSequence localCharSequence = this.t;
    if (localCharSequence != null)
    {
      localTextView.setText(localCharSequence);
    }
    else
    {
      localTextView.setVisibility(8);
      this.i.removeView(this.I);
      if (this.b != null)
      {
        paramViewGroup = (ViewGroup)this.i.getParent();
        int i1 = paramViewGroup.indexOfChild(this.i);
        paramViewGroup.removeViewAt(i1);
        paramViewGroup.addView(this.b, i1, new ViewGroup.LayoutParams(-1, -1));
      }
      else
      {
        paramViewGroup.setVisibility(8);
      }
    }
  }
  
  private void d(ViewGroup paramViewGroup)
  {
    this.c = ((Button)paramViewGroup.findViewById(16908313));
    this.c.setOnClickListener(this.O);
    boolean bool = TextUtils.isEmpty(this.B);
    int i1 = 1;
    int i2;
    if (bool)
    {
      this.c.setVisibility(8);
      i2 = 0;
    }
    else
    {
      this.c.setText(this.B);
      this.c.setVisibility(0);
      i2 = 1;
    }
    this.e = ((Button)paramViewGroup.findViewById(16908314));
    this.e.setOnClickListener(this.O);
    if (TextUtils.isEmpty(this.C))
    {
      this.e.setVisibility(8);
    }
    else
    {
      this.e.setText(this.C);
      this.e.setVisibility(0);
      i2 |= 0x2;
    }
    this.g = ((Button)paramViewGroup.findViewById(16908315));
    this.g.setOnClickListener(this.O);
    if (TextUtils.isEmpty(this.D))
    {
      this.g.setVisibility(8);
    }
    else
    {
      this.g.setText(this.D);
      this.g.setVisibility(0);
      i2 |= 0x4;
    }
    if (a(this.q)) {
      if (i2 == 1) {
        a(this.c);
      } else if (i2 == 2) {
        a(this.e);
      } else if (i2 == 4) {
        a(this.g);
      }
    }
    if (i2 != 0) {
      i2 = i1;
    } else {
      i2 = 0;
    }
    if (i2 == 0) {
      paramViewGroup.setVisibility(8);
    }
  }
  
  public void a()
  {
    int i1 = b();
    this.a.setContentView(i1);
    c();
  }
  
  public void a(int paramInt)
  {
    this.u = null;
    this.v = paramInt;
    this.A = false;
  }
  
  public void a(int paramInt, CharSequence paramCharSequence, DialogInterface.OnClickListener paramOnClickListener, Message paramMessage)
  {
    Message localMessage = paramMessage;
    if (paramMessage == null)
    {
      localMessage = paramMessage;
      if (paramOnClickListener != null) {
        localMessage = this.p.obtainMessage(paramInt, paramOnClickListener);
      }
    }
    switch (paramInt)
    {
    default: 
      throw new IllegalArgumentException("Button does not exist");
    case -1: 
      this.B = paramCharSequence;
      this.d = localMessage;
      break;
    case -2: 
      this.C = paramCharSequence;
      this.f = localMessage;
      break;
    case -3: 
      this.D = paramCharSequence;
      this.h = localMessage;
    }
  }
  
  public void a(Drawable paramDrawable)
  {
    this.F = paramDrawable;
    this.E = 0;
    ImageView localImageView = this.G;
    if (localImageView != null) {
      if (paramDrawable != null)
      {
        localImageView.setVisibility(0);
        this.G.setImageDrawable(paramDrawable);
      }
      else
      {
        localImageView.setVisibility(8);
      }
    }
  }
  
  public void a(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.u = paramView;
    this.v = 0;
    this.A = true;
    this.w = paramInt1;
    this.x = paramInt2;
    this.y = paramInt3;
    this.z = paramInt4;
  }
  
  public void a(CharSequence paramCharSequence)
  {
    this.s = paramCharSequence;
    TextView localTextView = this.H;
    if (localTextView != null) {
      localTextView.setText(paramCharSequence);
    }
  }
  
  public boolean a(int paramInt, KeyEvent paramKeyEvent)
  {
    NestedScrollView localNestedScrollView = this.i;
    boolean bool;
    if ((localNestedScrollView != null) && (localNestedScrollView.executeKeyEvent(paramKeyEvent))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public void b(int paramInt)
  {
    this.F = null;
    this.E = paramInt;
    ImageView localImageView = this.G;
    if (localImageView != null) {
      if (paramInt != 0)
      {
        localImageView.setVisibility(0);
        this.G.setImageResource(this.E);
      }
      else
      {
        localImageView.setVisibility(8);
      }
    }
  }
  
  public void b(View paramView)
  {
    this.J = paramView;
  }
  
  public void b(CharSequence paramCharSequence)
  {
    this.t = paramCharSequence;
    TextView localTextView = this.I;
    if (localTextView != null) {
      localTextView.setText(paramCharSequence);
    }
  }
  
  public boolean b(int paramInt, KeyEvent paramKeyEvent)
  {
    NestedScrollView localNestedScrollView = this.i;
    boolean bool;
    if ((localNestedScrollView != null) && (localNestedScrollView.executeKeyEvent(paramKeyEvent))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public int c(int paramInt)
  {
    TypedValue localTypedValue = new TypedValue();
    this.q.getTheme().resolveAttribute(paramInt, localTypedValue, true);
    return localTypedValue.resourceId;
  }
  
  public void c(View paramView)
  {
    this.u = paramView;
    this.v = 0;
    this.A = false;
  }
  
  public static class RecycleListView
    extends ListView
  {
    private final int a;
    private final int b;
    
    public RecycleListView(Context paramContext, AttributeSet paramAttributeSet)
    {
      super(paramAttributeSet);
      paramContext = paramContext.obtainStyledAttributes(paramAttributeSet, a.j.RecycleListView);
      this.b = paramContext.getDimensionPixelOffset(a.j.RecycleListView_paddingBottomNoButtons, -1);
      this.a = paramContext.getDimensionPixelOffset(a.j.RecycleListView_paddingTopNoTitle, -1);
    }
    
    public void a(boolean paramBoolean1, boolean paramBoolean2)
    {
      if ((!paramBoolean2) || (!paramBoolean1))
      {
        int i = getPaddingLeft();
        int j;
        if (paramBoolean1) {
          j = getPaddingTop();
        } else {
          j = this.a;
        }
        int k = getPaddingRight();
        int m;
        if (paramBoolean2) {
          m = getPaddingBottom();
        } else {
          m = this.b;
        }
        setPadding(i, j, k, m);
      }
    }
  }
  
  public static class a
  {
    public int A;
    public boolean B = false;
    public boolean[] C;
    public boolean D;
    public boolean E;
    public int F = -1;
    public DialogInterface.OnMultiChoiceClickListener G;
    public Cursor H;
    public String I;
    public String J;
    public AdapterView.OnItemSelectedListener K;
    public a L;
    public boolean M = true;
    public final Context a;
    public final LayoutInflater b;
    public int c = 0;
    public Drawable d;
    public int e = 0;
    public CharSequence f;
    public View g;
    public CharSequence h;
    public CharSequence i;
    public DialogInterface.OnClickListener j;
    public CharSequence k;
    public DialogInterface.OnClickListener l;
    public CharSequence m;
    public DialogInterface.OnClickListener n;
    public boolean o;
    public DialogInterface.OnCancelListener p;
    public DialogInterface.OnDismissListener q;
    public DialogInterface.OnKeyListener r;
    public CharSequence[] s;
    public ListAdapter t;
    public DialogInterface.OnClickListener u;
    public int v;
    public View w;
    public int x;
    public int y;
    public int z;
    
    public a(Context paramContext)
    {
      this.a = paramContext;
      this.o = true;
      this.b = ((LayoutInflater)paramContext.getSystemService("layout_inflater"));
    }
    
    private void b(final AlertController paramAlertController)
    {
      final AlertController.RecycleListView localRecycleListView = (AlertController.RecycleListView)this.b.inflate(paramAlertController.l, null);
      if (this.D)
      {
        localObject = this.H;
        if (localObject == null) {
          localObject = new ArrayAdapter(this.a, paramAlertController.m, 16908308, this.s)
          {
            public View getView(int paramAnonymousInt, View paramAnonymousView, ViewGroup paramAnonymousViewGroup)
            {
              paramAnonymousView = super.getView(paramAnonymousInt, paramAnonymousView, paramAnonymousViewGroup);
              if ((AlertController.a.this.C != null) && (AlertController.a.this.C[paramAnonymousInt] != 0)) {
                localRecycleListView.setItemChecked(paramAnonymousInt, true);
              }
              return paramAnonymousView;
            }
          };
        } else {
          localObject = new CursorAdapter(this.a, (Cursor)localObject, false)
          {
            private final int d;
            private final int e;
            
            public void bindView(View paramAnonymousView, Context paramAnonymousContext, Cursor paramAnonymousCursor)
            {
              ((CheckedTextView)paramAnonymousView.findViewById(16908308)).setText(paramAnonymousCursor.getString(this.d));
              paramAnonymousView = localRecycleListView;
              int i = paramAnonymousCursor.getPosition();
              int j = paramAnonymousCursor.getInt(this.e);
              boolean bool = true;
              if (j != 1) {
                bool = false;
              }
              paramAnonymousView.setItemChecked(i, bool);
            }
            
            public View newView(Context paramAnonymousContext, Cursor paramAnonymousCursor, ViewGroup paramAnonymousViewGroup)
            {
              return AlertController.a.this.b.inflate(paramAlertController.m, paramAnonymousViewGroup, false);
            }
          };
        }
      }
      else
      {
        int i1;
        if (this.E) {
          i1 = paramAlertController.n;
        } else {
          i1 = paramAlertController.o;
        }
        localObject = this.H;
        if (localObject != null)
        {
          localObject = new SimpleCursorAdapter(this.a, i1, (Cursor)localObject, new String[] { this.I }, new int[] { 16908308 });
        }
        else
        {
          localObject = this.t;
          if (localObject == null) {
            localObject = new AlertController.c(this.a, i1, 16908308, this.s);
          }
        }
      }
      a locala = this.L;
      if (locala != null) {
        locala.a(localRecycleListView);
      }
      paramAlertController.j = ((ListAdapter)localObject);
      paramAlertController.k = this.F;
      if (this.u != null) {
        localRecycleListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
          public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
          {
            AlertController.a.this.u.onClick(paramAlertController.a, paramAnonymousInt);
            if (!AlertController.a.this.E) {
              paramAlertController.a.dismiss();
            }
          }
        });
      } else if (this.G != null) {
        localRecycleListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
          public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
          {
            if (AlertController.a.this.C != null) {
              AlertController.a.this.C[paramAnonymousInt] = localRecycleListView.isItemChecked(paramAnonymousInt);
            }
            AlertController.a.this.G.onClick(paramAlertController.a, paramAnonymousInt, localRecycleListView.isItemChecked(paramAnonymousInt));
          }
        });
      }
      Object localObject = this.K;
      if (localObject != null) {
        localRecycleListView.setOnItemSelectedListener((AdapterView.OnItemSelectedListener)localObject);
      }
      if (this.E) {
        localRecycleListView.setChoiceMode(1);
      } else if (this.D) {
        localRecycleListView.setChoiceMode(2);
      }
      paramAlertController.b = localRecycleListView;
    }
    
    public void a(AlertController paramAlertController)
    {
      Object localObject = this.g;
      int i1;
      if (localObject != null)
      {
        paramAlertController.b((View)localObject);
      }
      else
      {
        localObject = this.f;
        if (localObject != null) {
          paramAlertController.a((CharSequence)localObject);
        }
        localObject = this.d;
        if (localObject != null) {
          paramAlertController.a((Drawable)localObject);
        }
        i1 = this.c;
        if (i1 != 0) {
          paramAlertController.b(i1);
        }
        i1 = this.e;
        if (i1 != 0) {
          paramAlertController.b(paramAlertController.c(i1));
        }
      }
      localObject = this.h;
      if (localObject != null) {
        paramAlertController.b((CharSequence)localObject);
      }
      localObject = this.i;
      if (localObject != null) {
        paramAlertController.a(-1, (CharSequence)localObject, this.j, null);
      }
      localObject = this.k;
      if (localObject != null) {
        paramAlertController.a(-2, (CharSequence)localObject, this.l, null);
      }
      localObject = this.m;
      if (localObject != null) {
        paramAlertController.a(-3, (CharSequence)localObject, this.n, null);
      }
      if ((this.s != null) || (this.H != null) || (this.t != null)) {
        b(paramAlertController);
      }
      localObject = this.w;
      if (localObject != null)
      {
        if (this.B) {
          paramAlertController.a((View)localObject, this.x, this.y, this.z, this.A);
        } else {
          paramAlertController.c((View)localObject);
        }
      }
      else
      {
        i1 = this.v;
        if (i1 != 0) {
          paramAlertController.a(i1);
        }
      }
    }
    
    public static abstract interface a
    {
      public abstract void a(ListView paramListView);
    }
  }
  
  private static final class b
    extends Handler
  {
    private WeakReference<DialogInterface> a;
    
    public b(DialogInterface paramDialogInterface)
    {
      this.a = new WeakReference(paramDialogInterface);
    }
    
    public void handleMessage(Message paramMessage)
    {
      int i = paramMessage.what;
      if (i != 1) {
        switch (i)
        {
        default: 
          break;
        case -3: 
        case -2: 
        case -1: 
          ((DialogInterface.OnClickListener)paramMessage.obj).onClick((DialogInterface)this.a.get(), paramMessage.what);
          break;
        }
      } else {
        ((DialogInterface)paramMessage.obj).dismiss();
      }
    }
  }
  
  private static class c
    extends ArrayAdapter<CharSequence>
  {
    public c(Context paramContext, int paramInt1, int paramInt2, CharSequence[] paramArrayOfCharSequence)
    {
      super(paramInt1, paramInt2, paramArrayOfCharSequence);
    }
    
    public long getItemId(int paramInt)
    {
      return paramInt;
    }
    
    public boolean hasStableIds()
    {
      return true;
    }
  }
}


/* Location:              ~/android/support/v7/app/AlertController.class
 *
 * Reversed by:           J
 */