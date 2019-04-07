package android.support.v4.app;

import android.content.ClipData;
import android.content.ClipData.Item;
import android.content.ClipDescription;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

class RemoteInputCompatJellybean
{
  private static final String EXTRA_DATA_TYPE_RESULTS_DATA = "android.remoteinput.dataTypeResultsData";
  private static final String KEY_ALLOWED_DATA_TYPES = "allowedDataTypes";
  private static final String KEY_ALLOW_FREE_FORM_INPUT = "allowFreeFormInput";
  private static final String KEY_CHOICES = "choices";
  private static final String KEY_EXTRAS = "extras";
  private static final String KEY_LABEL = "label";
  private static final String KEY_RESULT_KEY = "resultKey";
  
  public static void addDataResultToIntent(RemoteInput paramRemoteInput, Intent paramIntent, Map<String, Uri> paramMap)
  {
    Object localObject1 = getClipDataIntentFromIntent(paramIntent);
    Object localObject2 = localObject1;
    if (localObject1 == null) {
      localObject2 = new Intent();
    }
    Iterator localIterator = paramMap.entrySet().iterator();
    while (localIterator.hasNext())
    {
      paramMap = (Map.Entry)localIterator.next();
      String str = (String)paramMap.getKey();
      Uri localUri = (Uri)paramMap.getValue();
      if (str != null)
      {
        localObject1 = ((Intent)localObject2).getBundleExtra(getExtraResultsKeyForData(str));
        paramMap = (Map<String, Uri>)localObject1;
        if (localObject1 == null) {
          paramMap = new Bundle();
        }
        paramMap.putString(paramRemoteInput.getResultKey(), localUri.toString());
        ((Intent)localObject2).putExtra(getExtraResultsKeyForData(str), paramMap);
      }
    }
    paramIntent.setClipData(ClipData.newIntent("android.remoteinput.results", (Intent)localObject2));
  }
  
  static void addResultsToIntent(RemoteInputCompatBase.RemoteInput[] paramArrayOfRemoteInput, Intent paramIntent, Bundle paramBundle)
  {
    Object localObject1 = getClipDataIntentFromIntent(paramIntent);
    Object localObject2 = localObject1;
    if (localObject1 == null) {
      localObject2 = new Intent();
    }
    Object localObject3 = ((Intent)localObject2).getBundleExtra("android.remoteinput.resultsData");
    localObject1 = localObject3;
    if (localObject3 == null) {
      localObject1 = new Bundle();
    }
    int i = paramArrayOfRemoteInput.length;
    for (int j = 0; j < i; j++)
    {
      localObject3 = paramArrayOfRemoteInput[j];
      Object localObject4 = paramBundle.get(((RemoteInputCompatBase.RemoteInput)localObject3).getResultKey());
      if ((localObject4 instanceof CharSequence)) {
        ((Bundle)localObject1).putCharSequence(((RemoteInputCompatBase.RemoteInput)localObject3).getResultKey(), (CharSequence)localObject4);
      }
    }
    ((Intent)localObject2).putExtra("android.remoteinput.resultsData", (Bundle)localObject1);
    paramIntent.setClipData(ClipData.newIntent("android.remoteinput.results", (Intent)localObject2));
  }
  
  static RemoteInputCompatBase.RemoteInput fromBundle(Bundle paramBundle, RemoteInputCompatBase.RemoteInput.Factory paramFactory)
  {
    Object localObject = paramBundle.getStringArrayList("allowedDataTypes");
    HashSet localHashSet = new HashSet();
    if (localObject != null)
    {
      localObject = ((ArrayList)localObject).iterator();
      while (((Iterator)localObject).hasNext()) {
        localHashSet.add((String)((Iterator)localObject).next());
      }
    }
    return paramFactory.build(paramBundle.getString("resultKey"), paramBundle.getCharSequence("label"), paramBundle.getCharSequenceArray("choices"), paramBundle.getBoolean("allowFreeFormInput"), paramBundle.getBundle("extras"), localHashSet);
  }
  
  static RemoteInputCompatBase.RemoteInput[] fromBundleArray(Bundle[] paramArrayOfBundle, RemoteInputCompatBase.RemoteInput.Factory paramFactory)
  {
    if (paramArrayOfBundle == null) {
      return null;
    }
    RemoteInputCompatBase.RemoteInput[] arrayOfRemoteInput = paramFactory.newArray(paramArrayOfBundle.length);
    for (int i = 0; i < paramArrayOfBundle.length; i++) {
      arrayOfRemoteInput[i] = fromBundle(paramArrayOfBundle[i], paramFactory);
    }
    return arrayOfRemoteInput;
  }
  
  private static Intent getClipDataIntentFromIntent(Intent paramIntent)
  {
    ClipData localClipData = paramIntent.getClipData();
    if (localClipData == null) {
      return null;
    }
    paramIntent = localClipData.getDescription();
    if (!paramIntent.hasMimeType("text/vnd.android.intent")) {
      return null;
    }
    if (!paramIntent.getLabel().equals("android.remoteinput.results")) {
      return null;
    }
    return localClipData.getItemAt(0).getIntent();
  }
  
  static Map<String, Uri> getDataResultsFromIntent(Intent paramIntent, String paramString)
  {
    Intent localIntent = getClipDataIntentFromIntent(paramIntent);
    Object localObject = null;
    if (localIntent == null) {
      return null;
    }
    paramIntent = new HashMap();
    Iterator localIterator = localIntent.getExtras().keySet().iterator();
    while (localIterator.hasNext())
    {
      String str1 = (String)localIterator.next();
      if (str1.startsWith("android.remoteinput.dataTypeResultsData"))
      {
        String str2 = str1.substring(39);
        if ((str2 != null) && (!str2.isEmpty()))
        {
          str1 = localIntent.getBundleExtra(str1).getString(paramString);
          if ((str1 != null) && (!str1.isEmpty())) {
            paramIntent.put(str2, Uri.parse(str1));
          }
        }
      }
    }
    if (paramIntent.isEmpty()) {
      paramIntent = (Intent)localObject;
    }
    return paramIntent;
  }
  
  private static String getExtraResultsKeyForData(String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("android.remoteinput.dataTypeResultsData");
    localStringBuilder.append(paramString);
    return localStringBuilder.toString();
  }
  
  static Bundle getResultsFromIntent(Intent paramIntent)
  {
    paramIntent = getClipDataIntentFromIntent(paramIntent);
    if (paramIntent == null) {
      return null;
    }
    return (Bundle)paramIntent.getExtras().getParcelable("android.remoteinput.resultsData");
  }
  
  static Bundle toBundle(RemoteInputCompatBase.RemoteInput paramRemoteInput)
  {
    Bundle localBundle = new Bundle();
    localBundle.putString("resultKey", paramRemoteInput.getResultKey());
    localBundle.putCharSequence("label", paramRemoteInput.getLabel());
    localBundle.putCharSequenceArray("choices", paramRemoteInput.getChoices());
    localBundle.putBoolean("allowFreeFormInput", paramRemoteInput.getAllowFreeFormInput());
    localBundle.putBundle("extras", paramRemoteInput.getExtras());
    Object localObject = paramRemoteInput.getAllowedDataTypes();
    if ((localObject != null) && (!((Set)localObject).isEmpty()))
    {
      paramRemoteInput = new ArrayList(((Set)localObject).size());
      localObject = ((Set)localObject).iterator();
      while (((Iterator)localObject).hasNext()) {
        paramRemoteInput.add((String)((Iterator)localObject).next());
      }
      localBundle.putStringArrayList("allowedDataTypes", paramRemoteInput);
    }
    return localBundle;
  }
  
  static Bundle[] toBundleArray(RemoteInputCompatBase.RemoteInput[] paramArrayOfRemoteInput)
  {
    if (paramArrayOfRemoteInput == null) {
      return null;
    }
    Bundle[] arrayOfBundle = new Bundle[paramArrayOfRemoteInput.length];
    for (int i = 0; i < paramArrayOfRemoteInput.length; i++) {
      arrayOfBundle[i] = toBundle(paramArrayOfRemoteInput[i]);
    }
    return arrayOfBundle;
  }
}


/* Location:              ~/android/support/v4/app/RemoteInputCompatJellybean.class
 *
 * Reversed by:           J
 */