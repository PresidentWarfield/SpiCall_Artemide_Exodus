package com.crashlytics.android.core;

import android.content.Context;
import io.fabric.sdk.android.services.b.i;
import io.fabric.sdk.android.services.e.o;

class DialogStringResolver
{
  private static final String PROMPT_MESSAGE_RES_NAME = "com.crashlytics.CrashSubmissionPromptMessage";
  private static final String PROMPT_TITLE_RES_NAME = "com.crashlytics.CrashSubmissionPromptTitle";
  private static final String SUBMISSION_ALWAYS_SEND_RES_NAME = "com.crashlytics.CrashSubmissionAlwaysSendTitle";
  private static final String SUBMISSION_CANCEL_RES_NAME = "com.crashlytics.CrashSubmissionCancelTitle";
  private static final String SUBMISSION_SEND_RES_NAME = "com.crashlytics.CrashSubmissionSendTitle";
  private final Context context;
  private final o promptData;
  
  public DialogStringResolver(Context paramContext, o paramo)
  {
    this.context = paramContext;
    this.promptData = paramo;
  }
  
  private boolean isNullOrEmpty(String paramString)
  {
    boolean bool;
    if ((paramString != null) && (paramString.length() != 0)) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  private String resourceOrFallbackValue(String paramString1, String paramString2)
  {
    return stringOrFallback(i.b(this.context, paramString1), paramString2);
  }
  
  private String stringOrFallback(String paramString1, String paramString2)
  {
    String str = paramString1;
    if (isNullOrEmpty(paramString1)) {
      str = paramString2;
    }
    return str;
  }
  
  public String getAlwaysSendButtonTitle()
  {
    return resourceOrFallbackValue("com.crashlytics.CrashSubmissionAlwaysSendTitle", this.promptData.g);
  }
  
  public String getCancelButtonTitle()
  {
    return resourceOrFallbackValue("com.crashlytics.CrashSubmissionCancelTitle", this.promptData.e);
  }
  
  public String getMessage()
  {
    return resourceOrFallbackValue("com.crashlytics.CrashSubmissionPromptMessage", this.promptData.b);
  }
  
  public String getSendButtonTitle()
  {
    return resourceOrFallbackValue("com.crashlytics.CrashSubmissionSendTitle", this.promptData.c);
  }
  
  public String getTitle()
  {
    return resourceOrFallbackValue("com.crashlytics.CrashSubmissionPromptTitle", this.promptData.a);
  }
}


/* Location:              ~/com/crashlytics/android/core/DialogStringResolver.class
 *
 * Reversed by:           J
 */