package okhttp3.internal.cache2;

import a.c;
import java.nio.channels.FileChannel;

final class FileOperator
{
  private final FileChannel fileChannel;
  
  FileOperator(FileChannel paramFileChannel)
  {
    this.fileChannel = paramFileChannel;
  }
  
  public void read(long paramLong1, c paramc, long paramLong2)
  {
    if (paramLong2 >= 0L)
    {
      while (paramLong2 > 0L)
      {
        long l = this.fileChannel.transferTo(paramLong1, paramLong2, paramc);
        paramLong1 += l;
        paramLong2 -= l;
      }
      return;
    }
    throw new IndexOutOfBoundsException();
  }
  
  public void write(long paramLong1, c paramc, long paramLong2)
  {
    if ((paramLong2 >= 0L) && (paramLong2 <= paramc.a()))
    {
      long l = paramLong2;
      paramLong2 = paramLong1;
      for (paramLong1 = l; paramLong1 > 0L; paramLong1 -= l)
      {
        l = this.fileChannel.transferFrom(paramc, paramLong2, paramLong1);
        paramLong2 += l;
      }
      return;
    }
    throw new IndexOutOfBoundsException();
  }
}


/* Location:              ~/okhttp3/internal/cache2/FileOperator.class
 *
 * Reversed by:           J
 */