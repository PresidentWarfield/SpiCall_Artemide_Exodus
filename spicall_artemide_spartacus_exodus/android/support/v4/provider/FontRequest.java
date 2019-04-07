package android.support.v4.provider;

import android.support.v4.util.Preconditions;
import android.util.Base64;
import java.util.List;

public final class FontRequest
{
  private final List<List<byte[]>> mCertificates;
  private final int mCertificatesArray;
  private final String mIdentifier;
  private final String mProviderAuthority;
  private final String mProviderPackage;
  private final String mQuery;
  
  public FontRequest(String paramString1, String paramString2, String paramString3, int paramInt)
  {
    this.mProviderAuthority = ((String)Preconditions.checkNotNull(paramString1));
    this.mProviderPackage = ((String)Preconditions.checkNotNull(paramString2));
    this.mQuery = ((String)Preconditions.checkNotNull(paramString3));
    this.mCertificates = null;
    boolean bool;
    if (paramInt != 0) {
      bool = true;
    } else {
      bool = false;
    }
    Preconditions.checkArgument(bool);
    this.mCertificatesArray = paramInt;
    paramString1 = new StringBuilder(this.mProviderAuthority);
    paramString1.append("-");
    paramString1.append(this.mProviderPackage);
    paramString1.append("-");
    paramString1.append(this.mQuery);
    this.mIdentifier = paramString1.toString();
  }
  
  public FontRequest(String paramString1, String paramString2, String paramString3, List<List<byte[]>> paramList)
  {
    this.mProviderAuthority = ((String)Preconditions.checkNotNull(paramString1));
    this.mProviderPackage = ((String)Preconditions.checkNotNull(paramString2));
    this.mQuery = ((String)Preconditions.checkNotNull(paramString3));
    this.mCertificates = ((List)Preconditions.checkNotNull(paramList));
    this.mCertificatesArray = 0;
    paramString1 = new StringBuilder(this.mProviderAuthority);
    paramString1.append("-");
    paramString1.append(this.mProviderPackage);
    paramString1.append("-");
    paramString1.append(this.mQuery);
    this.mIdentifier = paramString1.toString();
  }
  
  public List<List<byte[]>> getCertificates()
  {
    return this.mCertificates;
  }
  
  public int getCertificatesArrayResId()
  {
    return this.mCertificatesArray;
  }
  
  public String getIdentifier()
  {
    return this.mIdentifier;
  }
  
  public String getProviderAuthority()
  {
    return this.mProviderAuthority;
  }
  
  public String getProviderPackage()
  {
    return this.mProviderPackage;
  }
  
  public String getQuery()
  {
    return this.mQuery;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("FontRequest {mProviderAuthority: ");
    ((StringBuilder)localObject).append(this.mProviderAuthority);
    ((StringBuilder)localObject).append(", mProviderPackage: ");
    ((StringBuilder)localObject).append(this.mProviderPackage);
    ((StringBuilder)localObject).append(", mQuery: ");
    ((StringBuilder)localObject).append(this.mQuery);
    ((StringBuilder)localObject).append(", mCertificates:");
    localStringBuilder.append(((StringBuilder)localObject).toString());
    for (int i = 0; i < this.mCertificates.size(); i++)
    {
      localStringBuilder.append(" [");
      localObject = (List)this.mCertificates.get(i);
      for (int j = 0; j < ((List)localObject).size(); j++)
      {
        localStringBuilder.append(" \"");
        localStringBuilder.append(Base64.encodeToString((byte[])((List)localObject).get(j), 0));
        localStringBuilder.append("\"");
      }
      localStringBuilder.append(" ]");
    }
    localStringBuilder.append("}");
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("mCertificatesArray: ");
    ((StringBuilder)localObject).append(this.mCertificatesArray);
    localStringBuilder.append(((StringBuilder)localObject).toString());
    return localStringBuilder.toString();
  }
}


/* Location:              ~/android/support/v4/provider/FontRequest.class
 *
 * Reversed by:           J
 */