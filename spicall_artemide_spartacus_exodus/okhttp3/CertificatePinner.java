package okhttp3;

import a.f;
import java.security.Principal;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;
import javax.net.ssl.SSLPeerUnverifiedException;
import okhttp3.internal.Util;
import okhttp3.internal.tls.CertificateChainCleaner;

public final class CertificatePinner
{
  public static final CertificatePinner DEFAULT = new Builder().build();
  @Nullable
  private final CertificateChainCleaner certificateChainCleaner;
  private final Set<Pin> pins;
  
  CertificatePinner(Set<Pin> paramSet, @Nullable CertificateChainCleaner paramCertificateChainCleaner)
  {
    this.pins = paramSet;
    this.certificateChainCleaner = paramCertificateChainCleaner;
  }
  
  public static String pin(Certificate paramCertificate)
  {
    if ((paramCertificate instanceof X509Certificate))
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("sha256/");
      localStringBuilder.append(sha256((X509Certificate)paramCertificate).b());
      return localStringBuilder.toString();
    }
    throw new IllegalArgumentException("Certificate pinning requires X509 certificates");
  }
  
  static f sha1(X509Certificate paramX509Certificate)
  {
    return f.a(paramX509Certificate.getPublicKey().getEncoded()).d();
  }
  
  static f sha256(X509Certificate paramX509Certificate)
  {
    return f.a(paramX509Certificate.getPublicKey().getEncoded()).e();
  }
  
  public void check(String paramString, List<Certificate> paramList)
  {
    List localList = findMatchingPins(paramString);
    if (localList.isEmpty()) {
      return;
    }
    Object localObject1 = this.certificateChainCleaner;
    Object localObject2 = paramList;
    if (localObject1 != null) {
      localObject2 = ((CertificateChainCleaner)localObject1).clean(paramList, paramString);
    }
    int i = ((List)localObject2).size();
    int j = 0;
    for (int k = 0; k < i; k++)
    {
      X509Certificate localX509Certificate = (X509Certificate)((List)localObject2).get(k);
      int m = localList.size();
      localObject1 = null;
      paramList = (List<Certificate>)localObject1;
      n = 0;
      while (n < m)
      {
        Pin localPin = (Pin)localList.get(n);
        Object localObject3;
        if (localPin.hashAlgorithm.equals("sha256/"))
        {
          localObject3 = localObject1;
          if (localObject1 == null) {
            localObject3 = sha256(localX509Certificate);
          }
          localObject1 = localObject3;
          if (!localPin.hash.equals(localObject3)) {}
        }
        else
        {
          if (!localPin.hashAlgorithm.equals("sha1/")) {
            break label209;
          }
          localObject3 = paramList;
          if (paramList == null) {
            localObject3 = sha1(localX509Certificate);
          }
          paramList = (List<Certificate>)localObject3;
          if (localPin.hash.equals(localObject3)) {
            return;
          }
        }
        n++;
        continue;
        label209:
        paramString = new StringBuilder();
        paramString.append("unsupported hashAlgorithm: ");
        paramString.append(localPin.hashAlgorithm);
        throw new AssertionError(paramString.toString());
      }
    }
    paramList = new StringBuilder();
    paramList.append("Certificate pinning failure!");
    paramList.append("\n  Peer certificate chain:");
    int n = ((List)localObject2).size();
    for (k = 0; k < n; k++)
    {
      localObject1 = (X509Certificate)((List)localObject2).get(k);
      paramList.append("\n    ");
      paramList.append(pin((Certificate)localObject1));
      paramList.append(": ");
      paramList.append(((X509Certificate)localObject1).getSubjectDN().getName());
    }
    paramList.append("\n  Pinned certificates for ");
    paramList.append(paramString);
    paramList.append(":");
    n = localList.size();
    for (k = j; k < n; k++)
    {
      paramString = (Pin)localList.get(k);
      paramList.append("\n    ");
      paramList.append(paramString);
    }
    throw new SSLPeerUnverifiedException(paramList.toString());
  }
  
  public void check(String paramString, Certificate... paramVarArgs)
  {
    check(paramString, Arrays.asList(paramVarArgs));
  }
  
  public boolean equals(@Nullable Object paramObject)
  {
    boolean bool = true;
    if (paramObject == this) {
      return true;
    }
    if ((paramObject instanceof CertificatePinner))
    {
      CertificateChainCleaner localCertificateChainCleaner = this.certificateChainCleaner;
      paramObject = (CertificatePinner)paramObject;
      if ((Util.equal(localCertificateChainCleaner, ((CertificatePinner)paramObject).certificateChainCleaner)) && (this.pins.equals(((CertificatePinner)paramObject).pins))) {}
    }
    else
    {
      bool = false;
    }
    return bool;
  }
  
  List<Pin> findMatchingPins(String paramString)
  {
    Object localObject1 = Collections.emptyList();
    Iterator localIterator = this.pins.iterator();
    while (localIterator.hasNext())
    {
      Pin localPin = (Pin)localIterator.next();
      if (localPin.matches(paramString))
      {
        Object localObject2 = localObject1;
        if (((List)localObject1).isEmpty()) {
          localObject2 = new ArrayList();
        }
        ((List)localObject2).add(localPin);
        localObject1 = localObject2;
      }
    }
    return (List<Pin>)localObject1;
  }
  
  public int hashCode()
  {
    CertificateChainCleaner localCertificateChainCleaner = this.certificateChainCleaner;
    int i;
    if (localCertificateChainCleaner != null) {
      i = localCertificateChainCleaner.hashCode();
    } else {
      i = 0;
    }
    return i * 31 + this.pins.hashCode();
  }
  
  CertificatePinner withCertificateChainCleaner(@Nullable CertificateChainCleaner paramCertificateChainCleaner)
  {
    if (Util.equal(this.certificateChainCleaner, paramCertificateChainCleaner)) {
      paramCertificateChainCleaner = this;
    } else {
      paramCertificateChainCleaner = new CertificatePinner(this.pins, paramCertificateChainCleaner);
    }
    return paramCertificateChainCleaner;
  }
  
  public static final class Builder
  {
    private final List<CertificatePinner.Pin> pins = new ArrayList();
    
    public Builder add(String paramString, String... paramVarArgs)
    {
      if (paramString != null)
      {
        int i = paramVarArgs.length;
        for (int j = 0; j < i; j++)
        {
          String str = paramVarArgs[j];
          this.pins.add(new CertificatePinner.Pin(paramString, str));
        }
        return this;
      }
      throw new NullPointerException("pattern == null");
    }
    
    public CertificatePinner build()
    {
      return new CertificatePinner(new LinkedHashSet(this.pins), null);
    }
  }
  
  static final class Pin
  {
    private static final String WILDCARD = "*.";
    final String canonicalHostname;
    final f hash;
    final String hashAlgorithm;
    final String pattern;
    
    Pin(String paramString1, String paramString2)
    {
      this.pattern = paramString1;
      StringBuilder localStringBuilder;
      if (paramString1.startsWith("*."))
      {
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("http://");
        localStringBuilder.append(paramString1.substring(2));
        paramString1 = HttpUrl.get(localStringBuilder.toString()).host();
      }
      else
      {
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("http://");
        localStringBuilder.append(paramString1);
        paramString1 = HttpUrl.get(localStringBuilder.toString()).host();
      }
      this.canonicalHostname = paramString1;
      if (paramString2.startsWith("sha1/"))
      {
        this.hashAlgorithm = "sha1/";
        this.hash = f.b(paramString2.substring(5));
      }
      else
      {
        if (!paramString2.startsWith("sha256/")) {
          break label193;
        }
        this.hashAlgorithm = "sha256/";
        this.hash = f.b(paramString2.substring(7));
      }
      if (this.hash != null) {
        return;
      }
      paramString1 = new StringBuilder();
      paramString1.append("pins must be base64: ");
      paramString1.append(paramString2);
      throw new IllegalArgumentException(paramString1.toString());
      label193:
      paramString1 = new StringBuilder();
      paramString1.append("pins must start with 'sha256/' or 'sha1/': ");
      paramString1.append(paramString2);
      throw new IllegalArgumentException(paramString1.toString());
    }
    
    public boolean equals(Object paramObject)
    {
      if ((paramObject instanceof Pin))
      {
        String str = this.pattern;
        paramObject = (Pin)paramObject;
        if ((str.equals(((Pin)paramObject).pattern)) && (this.hashAlgorithm.equals(((Pin)paramObject).hashAlgorithm)) && (this.hash.equals(((Pin)paramObject).hash))) {
          return true;
        }
      }
      boolean bool = false;
      return bool;
    }
    
    public int hashCode()
    {
      return ((527 + this.pattern.hashCode()) * 31 + this.hashAlgorithm.hashCode()) * 31 + this.hash.hashCode();
    }
    
    boolean matches(String paramString)
    {
      if (this.pattern.startsWith("*."))
      {
        int i = paramString.indexOf('.');
        int j = paramString.length();
        boolean bool = true;
        if (j - i - 1 == this.canonicalHostname.length())
        {
          String str = this.canonicalHostname;
          if (paramString.regionMatches(false, i + 1, str, 0, str.length())) {}
        }
        else
        {
          bool = false;
        }
        return bool;
      }
      return paramString.equals(this.canonicalHostname);
    }
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(this.hashAlgorithm);
      localStringBuilder.append(this.hash.b());
      return localStringBuilder.toString();
    }
  }
}


/* Location:              ~/okhttp3/CertificatePinner.class
 *
 * Reversed by:           J
 */