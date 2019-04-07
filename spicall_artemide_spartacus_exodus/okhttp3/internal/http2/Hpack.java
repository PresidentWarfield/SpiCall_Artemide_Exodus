package okhttp3.internal.http2;

import a.c;
import a.e;
import a.f;
import a.l;
import a.s;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import okhttp3.internal.Util;

final class Hpack
{
  static final Map<f, Integer> NAME_TO_FIRST_INDEX = nameToFirstIndex();
  private static final int PREFIX_4_BITS = 15;
  private static final int PREFIX_5_BITS = 31;
  private static final int PREFIX_6_BITS = 63;
  private static final int PREFIX_7_BITS = 127;
  static final Header[] STATIC_HEADER_TABLE = { new Header(Header.TARGET_AUTHORITY, ""), new Header(Header.TARGET_METHOD, "GET"), new Header(Header.TARGET_METHOD, "POST"), new Header(Header.TARGET_PATH, "/"), new Header(Header.TARGET_PATH, "/index.html"), new Header(Header.TARGET_SCHEME, "http"), new Header(Header.TARGET_SCHEME, "https"), new Header(Header.RESPONSE_STATUS, "200"), new Header(Header.RESPONSE_STATUS, "204"), new Header(Header.RESPONSE_STATUS, "206"), new Header(Header.RESPONSE_STATUS, "304"), new Header(Header.RESPONSE_STATUS, "400"), new Header(Header.RESPONSE_STATUS, "404"), new Header(Header.RESPONSE_STATUS, "500"), new Header("accept-charset", ""), new Header("accept-encoding", "gzip, deflate"), new Header("accept-language", ""), new Header("accept-ranges", ""), new Header("accept", ""), new Header("access-control-allow-origin", ""), new Header("age", ""), new Header("allow", ""), new Header("authorization", ""), new Header("cache-control", ""), new Header("content-disposition", ""), new Header("content-encoding", ""), new Header("content-language", ""), new Header("content-length", ""), new Header("content-location", ""), new Header("content-range", ""), new Header("content-type", ""), new Header("cookie", ""), new Header("date", ""), new Header("etag", ""), new Header("expect", ""), new Header("expires", ""), new Header("from", ""), new Header("host", ""), new Header("if-match", ""), new Header("if-modified-since", ""), new Header("if-none-match", ""), new Header("if-range", ""), new Header("if-unmodified-since", ""), new Header("last-modified", ""), new Header("link", ""), new Header("location", ""), new Header("max-forwards", ""), new Header("proxy-authenticate", ""), new Header("proxy-authorization", ""), new Header("range", ""), new Header("referer", ""), new Header("refresh", ""), new Header("retry-after", ""), new Header("server", ""), new Header("set-cookie", ""), new Header("strict-transport-security", ""), new Header("transfer-encoding", ""), new Header("user-agent", ""), new Header("vary", ""), new Header("via", ""), new Header("www-authenticate", "") };
  
  static f checkLowercase(f paramf)
  {
    int i = paramf.h();
    for (int j = 0; j < i; j++)
    {
      int k = paramf.a(j);
      if ((k >= 65) && (k <= 90))
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("PROTOCOL_ERROR response malformed: mixed case name: ");
        localStringBuilder.append(paramf.a());
        throw new IOException(localStringBuilder.toString());
      }
    }
    return paramf;
  }
  
  private static Map<f, Integer> nameToFirstIndex()
  {
    LinkedHashMap localLinkedHashMap = new LinkedHashMap(STATIC_HEADER_TABLE.length);
    for (int i = 0;; i++)
    {
      Header[] arrayOfHeader = STATIC_HEADER_TABLE;
      if (i >= arrayOfHeader.length) {
        break;
      }
      if (!localLinkedHashMap.containsKey(arrayOfHeader[i].name)) {
        localLinkedHashMap.put(STATIC_HEADER_TABLE[i].name, Integer.valueOf(i));
      }
    }
    return Collections.unmodifiableMap(localLinkedHashMap);
  }
  
  static final class Reader
  {
    Header[] dynamicTable = new Header[8];
    int dynamicTableByteCount = 0;
    int headerCount = 0;
    private final List<Header> headerList = new ArrayList();
    private final int headerTableSizeSetting;
    private int maxDynamicTableByteCount;
    int nextHeaderIndex = this.dynamicTable.length - 1;
    private final e source;
    
    Reader(int paramInt1, int paramInt2, s params)
    {
      this.headerTableSizeSetting = paramInt1;
      this.maxDynamicTableByteCount = paramInt2;
      this.source = l.a(params);
    }
    
    Reader(int paramInt, s params)
    {
      this(paramInt, paramInt, params);
    }
    
    private void adjustDynamicTableByteCount()
    {
      int i = this.maxDynamicTableByteCount;
      int j = this.dynamicTableByteCount;
      if (i < j) {
        if (i == 0) {
          clearDynamicTable();
        } else {
          evictToRecoverBytes(j - i);
        }
      }
    }
    
    private void clearDynamicTable()
    {
      Arrays.fill(this.dynamicTable, null);
      this.nextHeaderIndex = (this.dynamicTable.length - 1);
      this.headerCount = 0;
      this.dynamicTableByteCount = 0;
    }
    
    private int dynamicTableIndex(int paramInt)
    {
      return this.nextHeaderIndex + 1 + paramInt;
    }
    
    private int evictToRecoverBytes(int paramInt)
    {
      int i = 0;
      int j = 0;
      if (paramInt > 0)
      {
        i = this.dynamicTable.length - 1;
        int k = paramInt;
        paramInt = j;
        while ((i >= this.nextHeaderIndex) && (k > 0))
        {
          k -= this.dynamicTable[i].hpackSize;
          this.dynamicTableByteCount -= this.dynamicTable[i].hpackSize;
          this.headerCount -= 1;
          paramInt++;
          i--;
        }
        Header[] arrayOfHeader = this.dynamicTable;
        i = this.nextHeaderIndex;
        System.arraycopy(arrayOfHeader, i + 1, arrayOfHeader, i + 1 + paramInt, this.headerCount);
        this.nextHeaderIndex += paramInt;
        i = paramInt;
      }
      return i;
    }
    
    private f getName(int paramInt)
    {
      if (isStaticHeader(paramInt)) {
        return Hpack.STATIC_HEADER_TABLE[paramInt].name;
      }
      int i = dynamicTableIndex(paramInt - Hpack.STATIC_HEADER_TABLE.length);
      if (i >= 0)
      {
        localObject = this.dynamicTable;
        if (i < localObject.length) {
          return localObject[i].name;
        }
      }
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append("Header index too large ");
      ((StringBuilder)localObject).append(paramInt + 1);
      throw new IOException(((StringBuilder)localObject).toString());
    }
    
    private void insertIntoDynamicTable(int paramInt, Header paramHeader)
    {
      this.headerList.add(paramHeader);
      int i = paramHeader.hpackSize;
      int j = i;
      if (paramInt != -1) {
        j = i - this.dynamicTable[dynamicTableIndex(paramInt)].hpackSize;
      }
      i = this.maxDynamicTableByteCount;
      if (j > i)
      {
        clearDynamicTable();
        return;
      }
      int k = evictToRecoverBytes(this.dynamicTableByteCount + j - i);
      if (paramInt == -1)
      {
        paramInt = this.headerCount;
        Header[] arrayOfHeader1 = this.dynamicTable;
        if (paramInt + 1 > arrayOfHeader1.length)
        {
          Header[] arrayOfHeader2 = new Header[arrayOfHeader1.length * 2];
          System.arraycopy(arrayOfHeader1, 0, arrayOfHeader2, arrayOfHeader1.length, arrayOfHeader1.length);
          this.nextHeaderIndex = (this.dynamicTable.length - 1);
          this.dynamicTable = arrayOfHeader2;
        }
        paramInt = this.nextHeaderIndex;
        this.nextHeaderIndex = (paramInt - 1);
        this.dynamicTable[paramInt] = paramHeader;
        this.headerCount += 1;
      }
      else
      {
        i = dynamicTableIndex(paramInt);
        this.dynamicTable[(paramInt + (i + k))] = paramHeader;
      }
      this.dynamicTableByteCount += j;
    }
    
    private boolean isStaticHeader(int paramInt)
    {
      boolean bool = true;
      if ((paramInt < 0) || (paramInt > Hpack.STATIC_HEADER_TABLE.length - 1)) {
        bool = false;
      }
      return bool;
    }
    
    private int readByte()
    {
      return this.source.h() & 0xFF;
    }
    
    private void readIndexedHeader(int paramInt)
    {
      if (isStaticHeader(paramInt))
      {
        localObject = Hpack.STATIC_HEADER_TABLE[paramInt];
        this.headerList.add(localObject);
      }
      else
      {
        int i = dynamicTableIndex(paramInt - Hpack.STATIC_HEADER_TABLE.length);
        if (i < 0) {
          break label68;
        }
        localObject = this.dynamicTable;
        if (i >= localObject.length) {
          break label68;
        }
        this.headerList.add(localObject[i]);
      }
      return;
      label68:
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append("Header index too large ");
      ((StringBuilder)localObject).append(paramInt + 1);
      throw new IOException(((StringBuilder)localObject).toString());
    }
    
    private void readLiteralHeaderWithIncrementalIndexingIndexedName(int paramInt)
    {
      insertIntoDynamicTable(-1, new Header(getName(paramInt), readByteString()));
    }
    
    private void readLiteralHeaderWithIncrementalIndexingNewName()
    {
      insertIntoDynamicTable(-1, new Header(Hpack.checkLowercase(readByteString()), readByteString()));
    }
    
    private void readLiteralHeaderWithoutIndexingIndexedName(int paramInt)
    {
      f localf1 = getName(paramInt);
      f localf2 = readByteString();
      this.headerList.add(new Header(localf1, localf2));
    }
    
    private void readLiteralHeaderWithoutIndexingNewName()
    {
      f localf1 = Hpack.checkLowercase(readByteString());
      f localf2 = readByteString();
      this.headerList.add(new Header(localf1, localf2));
    }
    
    public List<Header> getAndResetHeaderList()
    {
      ArrayList localArrayList = new ArrayList(this.headerList);
      this.headerList.clear();
      return localArrayList;
    }
    
    int maxDynamicTableByteCount()
    {
      return this.maxDynamicTableByteCount;
    }
    
    f readByteString()
    {
      int i = readByte();
      int j;
      if ((i & 0x80) == 128) {
        j = 1;
      } else {
        j = 0;
      }
      i = readInt(i, 127);
      if (j != 0) {
        return f.a(Huffman.get().decode(this.source.h(i)));
      }
      return this.source.d(i);
    }
    
    void readHeaders()
    {
      while (!this.source.e())
      {
        int i = this.source.h() & 0xFF;
        if (i != 128)
        {
          if ((i & 0x80) == 128)
          {
            readIndexedHeader(readInt(i, 127) - 1);
          }
          else if (i == 64)
          {
            readLiteralHeaderWithIncrementalIndexingNewName();
          }
          else if ((i & 0x40) == 64)
          {
            readLiteralHeaderWithIncrementalIndexingIndexedName(readInt(i, 63) - 1);
          }
          else if ((i & 0x20) == 32)
          {
            this.maxDynamicTableByteCount = readInt(i, 31);
            i = this.maxDynamicTableByteCount;
            if ((i >= 0) && (i <= this.headerTableSizeSetting))
            {
              adjustDynamicTableByteCount();
            }
            else
            {
              StringBuilder localStringBuilder = new StringBuilder();
              localStringBuilder.append("Invalid dynamic table size update ");
              localStringBuilder.append(this.maxDynamicTableByteCount);
              throw new IOException(localStringBuilder.toString());
            }
          }
          else if ((i != 16) && (i != 0))
          {
            readLiteralHeaderWithoutIndexingIndexedName(readInt(i, 15) - 1);
          }
          else
          {
            readLiteralHeaderWithoutIndexingNewName();
          }
        }
        else {
          throw new IOException("index == 0");
        }
      }
    }
    
    int readInt(int paramInt1, int paramInt2)
    {
      paramInt1 &= paramInt2;
      if (paramInt1 < paramInt2) {
        return paramInt1;
      }
      int i;
      for (paramInt1 = 0;; paramInt1 += 7)
      {
        i = readByte();
        if ((i & 0x80) == 0) {
          break;
        }
        paramInt2 += ((i & 0x7F) << paramInt1);
      }
      return paramInt2 + (i << paramInt1);
    }
  }
  
  static final class Writer
  {
    private static final int SETTINGS_HEADER_TABLE_SIZE = 4096;
    private static final int SETTINGS_HEADER_TABLE_SIZE_LIMIT = 16384;
    Header[] dynamicTable = new Header[8];
    int dynamicTableByteCount = 0;
    private boolean emitDynamicTableSizeUpdate;
    int headerCount = 0;
    int headerTableSizeSetting;
    int maxDynamicTableByteCount;
    int nextHeaderIndex = this.dynamicTable.length - 1;
    private final c out;
    private int smallestHeaderTableSizeSetting = Integer.MAX_VALUE;
    private final boolean useCompression;
    
    Writer(int paramInt, boolean paramBoolean, c paramc)
    {
      this.headerTableSizeSetting = paramInt;
      this.maxDynamicTableByteCount = paramInt;
      this.useCompression = paramBoolean;
      this.out = paramc;
    }
    
    Writer(c paramc)
    {
      this(4096, true, paramc);
    }
    
    private void adjustDynamicTableByteCount()
    {
      int i = this.maxDynamicTableByteCount;
      int j = this.dynamicTableByteCount;
      if (i < j) {
        if (i == 0) {
          clearDynamicTable();
        } else {
          evictToRecoverBytes(j - i);
        }
      }
    }
    
    private void clearDynamicTable()
    {
      Arrays.fill(this.dynamicTable, null);
      this.nextHeaderIndex = (this.dynamicTable.length - 1);
      this.headerCount = 0;
      this.dynamicTableByteCount = 0;
    }
    
    private int evictToRecoverBytes(int paramInt)
    {
      int i = 0;
      int j = 0;
      if (paramInt > 0)
      {
        i = this.dynamicTable.length - 1;
        int k = paramInt;
        paramInt = j;
        while ((i >= this.nextHeaderIndex) && (k > 0))
        {
          k -= this.dynamicTable[i].hpackSize;
          this.dynamicTableByteCount -= this.dynamicTable[i].hpackSize;
          this.headerCount -= 1;
          paramInt++;
          i--;
        }
        Header[] arrayOfHeader = this.dynamicTable;
        i = this.nextHeaderIndex;
        System.arraycopy(arrayOfHeader, i + 1, arrayOfHeader, i + 1 + paramInt, this.headerCount);
        arrayOfHeader = this.dynamicTable;
        i = this.nextHeaderIndex;
        Arrays.fill(arrayOfHeader, i + 1, i + 1 + paramInt, null);
        this.nextHeaderIndex += paramInt;
        i = paramInt;
      }
      return i;
    }
    
    private void insertIntoDynamicTable(Header paramHeader)
    {
      int i = paramHeader.hpackSize;
      int j = this.maxDynamicTableByteCount;
      if (i > j)
      {
        clearDynamicTable();
        return;
      }
      evictToRecoverBytes(this.dynamicTableByteCount + i - j);
      j = this.headerCount;
      Header[] arrayOfHeader1 = this.dynamicTable;
      if (j + 1 > arrayOfHeader1.length)
      {
        Header[] arrayOfHeader2 = new Header[arrayOfHeader1.length * 2];
        System.arraycopy(arrayOfHeader1, 0, arrayOfHeader2, arrayOfHeader1.length, arrayOfHeader1.length);
        this.nextHeaderIndex = (this.dynamicTable.length - 1);
        this.dynamicTable = arrayOfHeader2;
      }
      j = this.nextHeaderIndex;
      this.nextHeaderIndex = (j - 1);
      this.dynamicTable[j] = paramHeader;
      this.headerCount += 1;
      this.dynamicTableByteCount += i;
    }
    
    void setHeaderTableSizeSetting(int paramInt)
    {
      this.headerTableSizeSetting = paramInt;
      int i = Math.min(paramInt, 16384);
      paramInt = this.maxDynamicTableByteCount;
      if (paramInt == i) {
        return;
      }
      if (i < paramInt) {
        this.smallestHeaderTableSizeSetting = Math.min(this.smallestHeaderTableSizeSetting, i);
      }
      this.emitDynamicTableSizeUpdate = true;
      this.maxDynamicTableByteCount = i;
      adjustDynamicTableByteCount();
    }
    
    void writeByteString(f paramf)
    {
      if ((this.useCompression) && (Huffman.get().encodedLength(paramf) < paramf.h()))
      {
        c localc = new c();
        Huffman.get().encode(paramf, localc);
        paramf = localc.p();
        writeInt(paramf.h(), 127, 128);
        this.out.a(paramf);
      }
      else
      {
        writeInt(paramf.h(), 127, 0);
        this.out.a(paramf);
      }
    }
    
    void writeHeaders(List<Header> paramList)
    {
      int i;
      if (this.emitDynamicTableSizeUpdate)
      {
        i = this.smallestHeaderTableSizeSetting;
        if (i < this.maxDynamicTableByteCount) {
          writeInt(i, 31, 32);
        }
        this.emitDynamicTableSizeUpdate = false;
        this.smallestHeaderTableSizeSetting = Integer.MAX_VALUE;
        writeInt(this.maxDynamicTableByteCount, 31, 32);
      }
      int j = paramList.size();
      for (int k = 0; k < j; k++)
      {
        Header localHeader = (Header)paramList.get(k);
        f localf1 = localHeader.name.g();
        f localf2 = localHeader.value;
        Integer localInteger = (Integer)Hpack.NAME_TO_FIRST_INDEX.get(localf1);
        int m;
        if (localInteger != null)
        {
          m = localInteger.intValue() + 1;
          if ((m > 1) && (m < 8))
          {
            if (Util.equal(Hpack.STATIC_HEADER_TABLE[(m - 1)].value, localf2))
            {
              i = m;
              break label205;
            }
            if (Util.equal(Hpack.STATIC_HEADER_TABLE[m].value, localf2))
            {
              i = m;
              m++;
              break label205;
            }
          }
          i = m;
          m = -1;
        }
        else
        {
          m = -1;
          i = -1;
        }
        label205:
        int n = m;
        int i1 = i;
        if (m == -1)
        {
          int i2 = this.nextHeaderIndex + 1;
          int i3 = this.dynamicTable.length;
          for (;;)
          {
            n = m;
            i1 = i;
            if (i2 >= i3) {
              break;
            }
            i1 = i;
            if (Util.equal(this.dynamicTable[i2].name, localf1))
            {
              if (Util.equal(this.dynamicTable[i2].value, localf2))
              {
                m = this.nextHeaderIndex;
                n = Hpack.STATIC_HEADER_TABLE.length + (i2 - m);
                i1 = i;
                break;
              }
              i1 = i;
              if (i == -1) {
                i1 = i2 - this.nextHeaderIndex + Hpack.STATIC_HEADER_TABLE.length;
              }
            }
            i2++;
            i = i1;
          }
        }
        if (n != -1)
        {
          writeInt(n, 127, 128);
        }
        else if (i1 == -1)
        {
          this.out.b(64);
          writeByteString(localf1);
          writeByteString(localf2);
          insertIntoDynamicTable(localHeader);
        }
        else if ((localf1.a(Header.PSEUDO_PREFIX)) && (!Header.TARGET_AUTHORITY.equals(localf1)))
        {
          writeInt(i1, 15, 0);
          writeByteString(localf2);
        }
        else
        {
          writeInt(i1, 63, 64);
          writeByteString(localf2);
          insertIntoDynamicTable(localHeader);
        }
      }
    }
    
    void writeInt(int paramInt1, int paramInt2, int paramInt3)
    {
      if (paramInt1 < paramInt2)
      {
        this.out.b(paramInt1 | paramInt3);
        return;
      }
      this.out.b(paramInt3 | paramInt2);
      paramInt1 -= paramInt2;
      while (paramInt1 >= 128)
      {
        this.out.b(0x80 | paramInt1 & 0x7F);
        paramInt1 >>>= 7;
      }
      this.out.b(paramInt1);
    }
  }
}


/* Location:              ~/okhttp3/internal/http2/Hpack.class
 *
 * Reversed by:           J
 */