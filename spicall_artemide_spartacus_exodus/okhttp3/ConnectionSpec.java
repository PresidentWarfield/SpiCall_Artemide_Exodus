package okhttp3;

import java.util.Arrays;
import java.util.List;
import javax.annotation.Nullable;
import javax.net.ssl.SSLSocket;
import okhttp3.internal.Util;

public final class ConnectionSpec
{
  private static final CipherSuite[] APPROVED_CIPHER_SUITES;
  public static final ConnectionSpec CLEARTEXT = new Builder(false).build();
  public static final ConnectionSpec COMPATIBLE_TLS;
  public static final ConnectionSpec MODERN_TLS;
  private static final CipherSuite[] RESTRICTED_CIPHER_SUITES = { CipherSuite.TLS_AES_128_GCM_SHA256, CipherSuite.TLS_AES_256_GCM_SHA384, CipherSuite.TLS_CHACHA20_POLY1305_SHA256, CipherSuite.TLS_AES_128_CCM_SHA256, CipherSuite.TLS_AES_256_CCM_8_SHA256, CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384, CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384, CipherSuite.TLS_ECDHE_ECDSA_WITH_CHACHA20_POLY1305_SHA256, CipherSuite.TLS_ECDHE_RSA_WITH_CHACHA20_POLY1305_SHA256 };
  public static final ConnectionSpec RESTRICTED_TLS;
  @Nullable
  final String[] cipherSuites;
  final boolean supportsTlsExtensions;
  final boolean tls;
  @Nullable
  final String[] tlsVersions;
  
  static
  {
    APPROVED_CIPHER_SUITES = new CipherSuite[] { CipherSuite.TLS_AES_128_GCM_SHA256, CipherSuite.TLS_AES_256_GCM_SHA384, CipherSuite.TLS_CHACHA20_POLY1305_SHA256, CipherSuite.TLS_AES_128_CCM_SHA256, CipherSuite.TLS_AES_256_CCM_8_SHA256, CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384, CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384, CipherSuite.TLS_ECDHE_ECDSA_WITH_CHACHA20_POLY1305_SHA256, CipherSuite.TLS_ECDHE_RSA_WITH_CHACHA20_POLY1305_SHA256, CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA, CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA, CipherSuite.TLS_RSA_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_RSA_WITH_AES_256_GCM_SHA384, CipherSuite.TLS_RSA_WITH_AES_128_CBC_SHA, CipherSuite.TLS_RSA_WITH_AES_256_CBC_SHA, CipherSuite.TLS_RSA_WITH_3DES_EDE_CBC_SHA };
    RESTRICTED_TLS = new Builder(true).cipherSuites(RESTRICTED_CIPHER_SUITES).tlsVersions(new TlsVersion[] { TlsVersion.TLS_1_3, TlsVersion.TLS_1_2 }).supportsTlsExtensions(true).build();
    MODERN_TLS = new Builder(true).cipherSuites(APPROVED_CIPHER_SUITES).tlsVersions(new TlsVersion[] { TlsVersion.TLS_1_3, TlsVersion.TLS_1_2, TlsVersion.TLS_1_1, TlsVersion.TLS_1_0 }).supportsTlsExtensions(true).build();
    COMPATIBLE_TLS = new Builder(true).cipherSuites(APPROVED_CIPHER_SUITES).tlsVersions(new TlsVersion[] { TlsVersion.TLS_1_0 }).supportsTlsExtensions(true).build();
  }
  
  ConnectionSpec(Builder paramBuilder)
  {
    this.tls = paramBuilder.tls;
    this.cipherSuites = paramBuilder.cipherSuites;
    this.tlsVersions = paramBuilder.tlsVersions;
    this.supportsTlsExtensions = paramBuilder.supportsTlsExtensions;
  }
  
  private ConnectionSpec supportedSpec(SSLSocket paramSSLSocket, boolean paramBoolean)
  {
    String[] arrayOfString1;
    if (this.cipherSuites != null) {
      arrayOfString1 = Util.intersect(CipherSuite.ORDER_BY_NAME, paramSSLSocket.getEnabledCipherSuites(), this.cipherSuites);
    } else {
      arrayOfString1 = paramSSLSocket.getEnabledCipherSuites();
    }
    String[] arrayOfString2;
    if (this.tlsVersions != null) {
      arrayOfString2 = Util.intersect(Util.NATURAL_ORDER, paramSSLSocket.getEnabledProtocols(), this.tlsVersions);
    } else {
      arrayOfString2 = paramSSLSocket.getEnabledProtocols();
    }
    String[] arrayOfString3 = paramSSLSocket.getSupportedCipherSuites();
    int i = Util.indexOf(CipherSuite.ORDER_BY_NAME, arrayOfString3, "TLS_FALLBACK_SCSV");
    paramSSLSocket = arrayOfString1;
    if (paramBoolean)
    {
      paramSSLSocket = arrayOfString1;
      if (i != -1) {
        paramSSLSocket = Util.concat(arrayOfString1, arrayOfString3[i]);
      }
    }
    return new Builder(this).cipherSuites(paramSSLSocket).tlsVersions(arrayOfString2).build();
  }
  
  void apply(SSLSocket paramSSLSocket, boolean paramBoolean)
  {
    ConnectionSpec localConnectionSpec = supportedSpec(paramSSLSocket, paramBoolean);
    String[] arrayOfString = localConnectionSpec.tlsVersions;
    if (arrayOfString != null) {
      paramSSLSocket.setEnabledProtocols(arrayOfString);
    }
    arrayOfString = localConnectionSpec.cipherSuites;
    if (arrayOfString != null) {
      paramSSLSocket.setEnabledCipherSuites(arrayOfString);
    }
  }
  
  @Nullable
  public List<CipherSuite> cipherSuites()
  {
    Object localObject = this.cipherSuites;
    if (localObject != null) {
      localObject = CipherSuite.forJavaNames((String[])localObject);
    } else {
      localObject = null;
    }
    return (List<CipherSuite>)localObject;
  }
  
  public boolean equals(@Nullable Object paramObject)
  {
    if (!(paramObject instanceof ConnectionSpec)) {
      return false;
    }
    if (paramObject == this) {
      return true;
    }
    paramObject = (ConnectionSpec)paramObject;
    boolean bool = this.tls;
    if (bool != ((ConnectionSpec)paramObject).tls) {
      return false;
    }
    if (bool)
    {
      if (!Arrays.equals(this.cipherSuites, ((ConnectionSpec)paramObject).cipherSuites)) {
        return false;
      }
      if (!Arrays.equals(this.tlsVersions, ((ConnectionSpec)paramObject).tlsVersions)) {
        return false;
      }
      if (this.supportsTlsExtensions != ((ConnectionSpec)paramObject).supportsTlsExtensions) {
        return false;
      }
    }
    return true;
  }
  
  public int hashCode()
  {
    int i;
    if (this.tls) {
      i = ((527 + Arrays.hashCode(this.cipherSuites)) * 31 + Arrays.hashCode(this.tlsVersions)) * 31 + (this.supportsTlsExtensions ^ true);
    } else {
      i = 17;
    }
    return i;
  }
  
  public boolean isCompatible(SSLSocket paramSSLSocket)
  {
    if (!this.tls) {
      return false;
    }
    if ((this.tlsVersions != null) && (!Util.nonEmptyIntersection(Util.NATURAL_ORDER, this.tlsVersions, paramSSLSocket.getEnabledProtocols()))) {
      return false;
    }
    return (this.cipherSuites == null) || (Util.nonEmptyIntersection(CipherSuite.ORDER_BY_NAME, this.cipherSuites, paramSSLSocket.getEnabledCipherSuites()));
  }
  
  public boolean isTls()
  {
    return this.tls;
  }
  
  public boolean supportsTlsExtensions()
  {
    return this.supportsTlsExtensions;
  }
  
  @Nullable
  public List<TlsVersion> tlsVersions()
  {
    Object localObject = this.tlsVersions;
    if (localObject != null) {
      localObject = TlsVersion.forJavaNames((String[])localObject);
    } else {
      localObject = null;
    }
    return (List<TlsVersion>)localObject;
  }
  
  public String toString()
  {
    if (!this.tls) {
      return "ConnectionSpec()";
    }
    String str1;
    if (this.cipherSuites != null) {
      str1 = cipherSuites().toString();
    } else {
      str1 = "[all enabled]";
    }
    String str2;
    if (this.tlsVersions != null) {
      str2 = tlsVersions().toString();
    } else {
      str2 = "[all enabled]";
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("ConnectionSpec(cipherSuites=");
    localStringBuilder.append(str1);
    localStringBuilder.append(", tlsVersions=");
    localStringBuilder.append(str2);
    localStringBuilder.append(", supportsTlsExtensions=");
    localStringBuilder.append(this.supportsTlsExtensions);
    localStringBuilder.append(")");
    return localStringBuilder.toString();
  }
  
  public static final class Builder
  {
    @Nullable
    String[] cipherSuites;
    boolean supportsTlsExtensions;
    boolean tls;
    @Nullable
    String[] tlsVersions;
    
    public Builder(ConnectionSpec paramConnectionSpec)
    {
      this.tls = paramConnectionSpec.tls;
      this.cipherSuites = paramConnectionSpec.cipherSuites;
      this.tlsVersions = paramConnectionSpec.tlsVersions;
      this.supportsTlsExtensions = paramConnectionSpec.supportsTlsExtensions;
    }
    
    Builder(boolean paramBoolean)
    {
      this.tls = paramBoolean;
    }
    
    public Builder allEnabledCipherSuites()
    {
      if (this.tls)
      {
        this.cipherSuites = null;
        return this;
      }
      throw new IllegalStateException("no cipher suites for cleartext connections");
    }
    
    public Builder allEnabledTlsVersions()
    {
      if (this.tls)
      {
        this.tlsVersions = null;
        return this;
      }
      throw new IllegalStateException("no TLS versions for cleartext connections");
    }
    
    public ConnectionSpec build()
    {
      return new ConnectionSpec(this);
    }
    
    public Builder cipherSuites(String... paramVarArgs)
    {
      if (this.tls)
      {
        if (paramVarArgs.length != 0)
        {
          this.cipherSuites = ((String[])paramVarArgs.clone());
          return this;
        }
        throw new IllegalArgumentException("At least one cipher suite is required");
      }
      throw new IllegalStateException("no cipher suites for cleartext connections");
    }
    
    public Builder cipherSuites(CipherSuite... paramVarArgs)
    {
      if (this.tls)
      {
        String[] arrayOfString = new String[paramVarArgs.length];
        for (int i = 0; i < paramVarArgs.length; i++) {
          arrayOfString[i] = paramVarArgs[i].javaName;
        }
        return cipherSuites(arrayOfString);
      }
      throw new IllegalStateException("no cipher suites for cleartext connections");
    }
    
    public Builder supportsTlsExtensions(boolean paramBoolean)
    {
      if (this.tls)
      {
        this.supportsTlsExtensions = paramBoolean;
        return this;
      }
      throw new IllegalStateException("no TLS extensions for cleartext connections");
    }
    
    public Builder tlsVersions(String... paramVarArgs)
    {
      if (this.tls)
      {
        if (paramVarArgs.length != 0)
        {
          this.tlsVersions = ((String[])paramVarArgs.clone());
          return this;
        }
        throw new IllegalArgumentException("At least one TLS version is required");
      }
      throw new IllegalStateException("no TLS versions for cleartext connections");
    }
    
    public Builder tlsVersions(TlsVersion... paramVarArgs)
    {
      if (this.tls)
      {
        String[] arrayOfString = new String[paramVarArgs.length];
        for (int i = 0; i < paramVarArgs.length; i++) {
          arrayOfString[i] = paramVarArgs[i].javaName;
        }
        return tlsVersions(arrayOfString);
      }
      throw new IllegalStateException("no TLS versions for cleartext connections");
    }
  }
}


/* Location:              ~/okhttp3/ConnectionSpec.class
 *
 * Reversed by:           J
 */