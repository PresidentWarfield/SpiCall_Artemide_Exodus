package a;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;

public final class l
{
  static final Logger a = Logger.getLogger(l.class.getName());
  
  public static d a(r paramr)
  {
    return new m(paramr);
  }
  
  public static e a(s params)
  {
    return new n(params);
  }
  
  public static r a()
  {
    new r()
    {
      public void close() {}
      
      public void flush() {}
      
      public t timeout()
      {
        return t.NONE;
      }
      
      public void write(c paramAnonymousc, long paramAnonymousLong)
      {
        paramAnonymousc.i(paramAnonymousLong);
      }
    };
  }
  
  public static r a(OutputStream paramOutputStream)
  {
    return a(paramOutputStream, new t());
  }
  
  private static r a(final OutputStream paramOutputStream, t paramt)
  {
    if (paramOutputStream != null)
    {
      if (paramt != null) {
        new r()
        {
          public void close()
          {
            paramOutputStream.close();
          }
          
          public void flush()
          {
            paramOutputStream.flush();
          }
          
          public t timeout()
          {
            return l.this;
          }
          
          public String toString()
          {
            StringBuilder localStringBuilder = new StringBuilder();
            localStringBuilder.append("sink(");
            localStringBuilder.append(paramOutputStream);
            localStringBuilder.append(")");
            return localStringBuilder.toString();
          }
          
          public void write(c paramAnonymousc, long paramAnonymousLong)
          {
            u.a(paramAnonymousc.b, 0L, paramAnonymousLong);
            while (paramAnonymousLong > 0L)
            {
              l.this.throwIfReached();
              o localo = paramAnonymousc.a;
              int i = (int)Math.min(paramAnonymousLong, localo.c - localo.b);
              paramOutputStream.write(localo.a, localo.b, i);
              localo.b += i;
              long l1 = i;
              long l2 = paramAnonymousLong - l1;
              paramAnonymousc.b -= l1;
              paramAnonymousLong = l2;
              if (localo.b == localo.c)
              {
                paramAnonymousc.a = localo.c();
                p.a(localo);
                paramAnonymousLong = l2;
              }
            }
          }
        };
      }
      throw new IllegalArgumentException("timeout == null");
    }
    throw new IllegalArgumentException("out == null");
  }
  
  public static r a(Socket paramSocket)
  {
    if (paramSocket != null)
    {
      if (paramSocket.getOutputStream() != null)
      {
        a locala = c(paramSocket);
        return locala.sink(a(paramSocket.getOutputStream(), locala));
      }
      throw new IOException("socket's output stream == null");
    }
    throw new IllegalArgumentException("socket == null");
  }
  
  public static s a(File paramFile)
  {
    if (paramFile != null) {
      return a(new FileInputStream(paramFile));
    }
    throw new IllegalArgumentException("file == null");
  }
  
  public static s a(InputStream paramInputStream)
  {
    return a(paramInputStream, new t());
  }
  
  private static s a(final InputStream paramInputStream, t paramt)
  {
    if (paramInputStream != null)
    {
      if (paramt != null) {
        new s()
        {
          public void close()
          {
            paramInputStream.close();
          }
          
          public long read(c paramAnonymousc, long paramAnonymousLong)
          {
            if (paramAnonymousLong >= 0L)
            {
              if (paramAnonymousLong == 0L) {
                return 0L;
              }
              try
              {
                l.this.throwIfReached();
                o localo = paramAnonymousc.e(1);
                int i = (int)Math.min(paramAnonymousLong, 8192 - localo.c);
                i = paramInputStream.read(localo.a, localo.c, i);
                if (i == -1) {
                  return -1L;
                }
                localo.c += i;
                long l = paramAnonymousc.b;
                paramAnonymousLong = i;
                paramAnonymousc.b = (l + paramAnonymousLong);
                return paramAnonymousLong;
              }
              catch (AssertionError paramAnonymousc)
              {
                if (l.a(paramAnonymousc)) {
                  throw new IOException(paramAnonymousc);
                }
                throw paramAnonymousc;
              }
            }
            paramAnonymousc = new StringBuilder();
            paramAnonymousc.append("byteCount < 0: ");
            paramAnonymousc.append(paramAnonymousLong);
            throw new IllegalArgumentException(paramAnonymousc.toString());
          }
          
          public t timeout()
          {
            return l.this;
          }
          
          public String toString()
          {
            StringBuilder localStringBuilder = new StringBuilder();
            localStringBuilder.append("source(");
            localStringBuilder.append(paramInputStream);
            localStringBuilder.append(")");
            return localStringBuilder.toString();
          }
        };
      }
      throw new IllegalArgumentException("timeout == null");
    }
    throw new IllegalArgumentException("in == null");
  }
  
  static boolean a(AssertionError paramAssertionError)
  {
    boolean bool;
    if ((paramAssertionError.getCause() != null) && (paramAssertionError.getMessage() != null) && (paramAssertionError.getMessage().contains("getsockname failed"))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static r b(File paramFile)
  {
    if (paramFile != null) {
      return a(new FileOutputStream(paramFile));
    }
    throw new IllegalArgumentException("file == null");
  }
  
  public static s b(Socket paramSocket)
  {
    if (paramSocket != null)
    {
      if (paramSocket.getInputStream() != null)
      {
        a locala = c(paramSocket);
        return locala.source(a(paramSocket.getInputStream(), locala));
      }
      throw new IOException("socket's input stream == null");
    }
    throw new IllegalArgumentException("socket == null");
  }
  
  private static a c(Socket paramSocket)
  {
    new a()
    {
      protected IOException newTimeoutException(@Nullable IOException paramAnonymousIOException)
      {
        SocketTimeoutException localSocketTimeoutException = new SocketTimeoutException("timeout");
        if (paramAnonymousIOException != null) {
          localSocketTimeoutException.initCause(paramAnonymousIOException);
        }
        return localSocketTimeoutException;
      }
      
      protected void timedOut()
      {
        try
        {
          l.this.close();
        }
        catch (AssertionError localAssertionError)
        {
          if (l.a(localAssertionError))
          {
            localObject1 = l.a;
            Level localLevel2 = Level.WARNING;
            localObject2 = new StringBuilder();
            ((StringBuilder)localObject2).append("Failed to close timed out socket ");
            ((StringBuilder)localObject2).append(l.this);
            ((Logger)localObject1).log(localLevel2, ((StringBuilder)localObject2).toString(), localAssertionError);
          }
          else
          {
            throw localAssertionError;
          }
        }
        catch (Exception localException)
        {
          Object localObject2 = l.a;
          Level localLevel1 = Level.WARNING;
          Object localObject1 = new StringBuilder();
          ((StringBuilder)localObject1).append("Failed to close timed out socket ");
          ((StringBuilder)localObject1).append(l.this);
          ((Logger)localObject2).log(localLevel1, ((StringBuilder)localObject1).toString(), localException);
        }
      }
    };
  }
  
  public static r c(File paramFile)
  {
    if (paramFile != null) {
      return a(new FileOutputStream(paramFile, true));
    }
    throw new IllegalArgumentException("file == null");
  }
}


/* Location:              ~/a/l.class
 *
 * Reversed by:           J
 */