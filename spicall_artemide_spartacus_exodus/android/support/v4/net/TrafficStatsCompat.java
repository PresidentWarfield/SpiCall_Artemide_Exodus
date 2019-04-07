package android.support.v4.net;

import android.net.TrafficStats;
import android.os.Build.VERSION;
import android.os.ParcelFileDescriptor;
import java.net.DatagramSocket;
import java.net.Socket;

public final class TrafficStatsCompat
{
  private static final TrafficStatsCompatBaseImpl IMPL;
  
  static
  {
    if (Build.VERSION.SDK_INT >= 24) {
      IMPL = new TrafficStatsCompatApi24Impl();
    } else {
      IMPL = new TrafficStatsCompatBaseImpl();
    }
  }
  
  @Deprecated
  public static void clearThreadStatsTag() {}
  
  @Deprecated
  public static int getThreadStatsTag()
  {
    return TrafficStats.getThreadStatsTag();
  }
  
  @Deprecated
  public static void incrementOperationCount(int paramInt)
  {
    TrafficStats.incrementOperationCount(paramInt);
  }
  
  @Deprecated
  public static void incrementOperationCount(int paramInt1, int paramInt2)
  {
    TrafficStats.incrementOperationCount(paramInt1, paramInt2);
  }
  
  @Deprecated
  public static void setThreadStatsTag(int paramInt)
  {
    TrafficStats.setThreadStatsTag(paramInt);
  }
  
  public static void tagDatagramSocket(DatagramSocket paramDatagramSocket)
  {
    IMPL.tagDatagramSocket(paramDatagramSocket);
  }
  
  @Deprecated
  public static void tagSocket(Socket paramSocket)
  {
    TrafficStats.tagSocket(paramSocket);
  }
  
  public static void untagDatagramSocket(DatagramSocket paramDatagramSocket)
  {
    IMPL.untagDatagramSocket(paramDatagramSocket);
  }
  
  @Deprecated
  public static void untagSocket(Socket paramSocket)
  {
    TrafficStats.untagSocket(paramSocket);
  }
  
  static class TrafficStatsCompatApi24Impl
    extends TrafficStatsCompat.TrafficStatsCompatBaseImpl
  {
    public void tagDatagramSocket(DatagramSocket paramDatagramSocket)
    {
      TrafficStats.tagDatagramSocket(paramDatagramSocket);
    }
    
    public void untagDatagramSocket(DatagramSocket paramDatagramSocket)
    {
      TrafficStats.untagDatagramSocket(paramDatagramSocket);
    }
  }
  
  static class TrafficStatsCompatBaseImpl
  {
    public void tagDatagramSocket(DatagramSocket paramDatagramSocket)
    {
      ParcelFileDescriptor localParcelFileDescriptor = ParcelFileDescriptor.fromDatagramSocket(paramDatagramSocket);
      TrafficStats.tagSocket(new DatagramSocketWrapper(paramDatagramSocket, localParcelFileDescriptor.getFileDescriptor()));
      localParcelFileDescriptor.detachFd();
    }
    
    public void untagDatagramSocket(DatagramSocket paramDatagramSocket)
    {
      ParcelFileDescriptor localParcelFileDescriptor = ParcelFileDescriptor.fromDatagramSocket(paramDatagramSocket);
      TrafficStats.untagSocket(new DatagramSocketWrapper(paramDatagramSocket, localParcelFileDescriptor.getFileDescriptor()));
      localParcelFileDescriptor.detachFd();
    }
  }
}


/* Location:              ~/android/support/v4/net/TrafficStatsCompat.class
 *
 * Reversed by:           J
 */