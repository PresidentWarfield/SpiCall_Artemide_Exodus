package okhttp3.internal.tls;

import javax.security.auth.x500.X500Principal;

final class DistinguishedNameParser
{
  private int beg;
  private char[] chars;
  private int cur;
  private final String dn;
  private int end;
  private final int length;
  private int pos;
  
  DistinguishedNameParser(X500Principal paramX500Principal)
  {
    this.dn = paramX500Principal.getName("RFC2253");
    this.length = this.dn.length();
  }
  
  private String escapedAV()
  {
    int i = this.pos;
    this.beg = i;
    this.end = i;
    label191:
    do
    {
      for (;;)
      {
        i = this.pos;
        if (i >= this.length)
        {
          arrayOfChar = this.chars;
          i = this.beg;
          return new String(arrayOfChar, i, this.end - i);
        }
        arrayOfChar = this.chars;
        j = arrayOfChar[i];
        if (j == 32) {
          break label191;
        }
        if (j == 59) {
          break;
        }
        if (j != 92) {}
        switch (j)
        {
        default: 
          j = this.end;
          this.end = (j + 1);
          arrayOfChar[j] = ((char)arrayOfChar[i]);
          this.pos = (i + 1);
          continue;
          i = this.end;
          this.end = (i + 1);
          arrayOfChar[i] = getEscaped();
          this.pos += 1;
        }
      }
      arrayOfChar = this.chars;
      i = this.beg;
      return new String(arrayOfChar, i, this.end - i);
      int j = this.end;
      this.cur = j;
      this.pos = (i + 1);
      this.end = (j + 1);
      arrayOfChar[j] = ((char)32);
      for (;;)
      {
        i = this.pos;
        if (i >= this.length) {
          break;
        }
        arrayOfChar = this.chars;
        if (arrayOfChar[i] != ' ') {
          break;
        }
        j = this.end;
        this.end = (j + 1);
        arrayOfChar[j] = ((char)32);
        this.pos = (i + 1);
      }
      i = this.pos;
      if (i == this.length) {
        break;
      }
      arrayOfChar = this.chars;
    } while ((arrayOfChar[i] != ',') && (arrayOfChar[i] != '+') && (arrayOfChar[i] != ';'));
    char[] arrayOfChar = this.chars;
    i = this.beg;
    return new String(arrayOfChar, i, this.cur - i);
  }
  
  private int getByte(int paramInt)
  {
    int i = paramInt + 1;
    if (i < this.length)
    {
      paramInt = this.chars[paramInt];
      if ((paramInt >= 48) && (paramInt <= 57))
      {
        paramInt -= 48;
      }
      else if ((paramInt >= 97) && (paramInt <= 102))
      {
        paramInt -= 87;
      }
      else
      {
        if ((paramInt < 65) || (paramInt > 70)) {
          break label170;
        }
        paramInt -= 55;
      }
      i = this.chars[i];
      if ((i >= 48) && (i <= 57))
      {
        i -= 48;
      }
      else if ((i >= 97) && (i <= 102))
      {
        i -= 87;
      }
      else
      {
        if ((i < 65) || (i > 70)) {
          break label134;
        }
        i -= 55;
      }
      return (paramInt << 4) + i;
      label134:
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("Malformed DN: ");
      localStringBuilder.append(this.dn);
      throw new IllegalStateException(localStringBuilder.toString());
      label170:
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("Malformed DN: ");
      localStringBuilder.append(this.dn);
      throw new IllegalStateException(localStringBuilder.toString());
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Malformed DN: ");
    localStringBuilder.append(this.dn);
    throw new IllegalStateException(localStringBuilder.toString());
  }
  
  private char getEscaped()
  {
    this.pos += 1;
    int i = this.pos;
    if (i != this.length)
    {
      i = this.chars[i];
      if ((i != 32) && (i != 37) && (i != 92) && (i != 95)) {
        switch (i)
        {
        default: 
          switch (i)
          {
          default: 
            switch (i)
            {
            default: 
              return getUTF8();
            }
            break;
          }
          break;
        }
      }
      return this.chars[this.pos];
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Unexpected end of DN: ");
    localStringBuilder.append(this.dn);
    throw new IllegalStateException(localStringBuilder.toString());
  }
  
  private char getUTF8()
  {
    int i = getByte(this.pos);
    this.pos += 1;
    if (i < 128) {
      return (char)i;
    }
    if ((i >= 192) && (i <= 247))
    {
      int j;
      if (i <= 223)
      {
        i &= 0x1F;
        j = 1;
      }
      else if (i <= 239)
      {
        j = 2;
        i &= 0xF;
      }
      else
      {
        j = 3;
        i &= 0x7;
      }
      int k = 0;
      while (k < j)
      {
        this.pos += 1;
        int m = this.pos;
        if ((m != this.length) && (this.chars[m] == '\\'))
        {
          this.pos = (m + 1);
          m = getByte(this.pos);
          this.pos += 1;
          if ((m & 0xC0) != 128) {
            return '?';
          }
          i = (i << 6) + (m & 0x3F);
          k++;
        }
        else
        {
          return '?';
        }
      }
      return (char)i;
    }
    return '?';
  }
  
  private String hexAV()
  {
    int i = this.pos;
    if (i + 4 < this.length)
    {
      this.beg = i;
      for (this.pos = (i + 1);; this.pos += 1)
      {
        i = this.pos;
        if (i == this.length) {
          break;
        }
        localObject = this.chars;
        if ((localObject[i] == '+') || (localObject[i] == ',') || (localObject[i] == ';')) {
          break;
        }
        if (localObject[i] == ' ')
        {
          this.end = i;
          for (this.pos = (i + 1);; this.pos = (i + 1))
          {
            i = this.pos;
            if ((i >= this.length) || (this.chars[i] != ' ')) {
              break;
            }
          }
        }
        if ((localObject[i] >= 'A') && (localObject[i] <= 'F')) {
          localObject[i] = ((char)(char)(localObject[i] + ' '));
        }
      }
      this.end = this.pos;
      i = this.end;
      int j = this.beg;
      int k = i - j;
      if ((k >= 5) && ((k & 0x1) != 0))
      {
        localObject = new byte[k / 2];
        i = 0;
        j++;
        while (i < localObject.length)
        {
          localObject[i] = ((byte)(byte)getByte(j));
          j += 2;
          i++;
        }
        return new String(this.chars, this.beg, k);
      }
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("Unexpected end of DN: ");
      ((StringBuilder)localObject).append(this.dn);
      throw new IllegalStateException(((StringBuilder)localObject).toString());
    }
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Unexpected end of DN: ");
    ((StringBuilder)localObject).append(this.dn);
    throw new IllegalStateException(((StringBuilder)localObject).toString());
  }
  
  private String nextAT()
  {
    for (;;)
    {
      i = this.pos;
      if ((i >= this.length) || (this.chars[i] != ' ')) {
        break;
      }
      this.pos = (i + 1);
    }
    int i = this.pos;
    if (i == this.length) {
      return null;
    }
    this.beg = i;
    for (this.pos = (i + 1);; this.pos = (i + 1))
    {
      i = this.pos;
      if (i >= this.length) {
        break;
      }
      localObject = this.chars;
      if ((localObject[i] == '=') || (localObject[i] == ' ')) {
        break;
      }
    }
    i = this.pos;
    if (i < this.length)
    {
      this.end = i;
      if (this.chars[i] == ' ')
      {
        for (;;)
        {
          i = this.pos;
          if (i >= this.length) {
            break;
          }
          localObject = this.chars;
          if ((localObject[i] == '=') || (localObject[i] != ' ')) {
            break;
          }
          this.pos = (i + 1);
        }
        localObject = this.chars;
        i = this.pos;
        if ((localObject[i] != '=') || (i == this.length))
        {
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append("Unexpected end of DN: ");
          ((StringBuilder)localObject).append(this.dn);
          throw new IllegalStateException(((StringBuilder)localObject).toString());
        }
      }
      for (this.pos += 1;; this.pos = (i + 1))
      {
        i = this.pos;
        if ((i >= this.length) || (this.chars[i] != ' ')) {
          break;
        }
      }
      i = this.end;
      int j = this.beg;
      if (i - j > 4)
      {
        localObject = this.chars;
        if ((localObject[(j + 3)] == '.') && ((localObject[j] == 'O') || (localObject[j] == 'o')))
        {
          localObject = this.chars;
          i = this.beg;
          if ((localObject[(i + 1)] == 'I') || (localObject[(i + 1)] == 'i'))
          {
            localObject = this.chars;
            i = this.beg;
            if ((localObject[(i + 2)] == 'D') || (localObject[(i + 2)] == 'd')) {
              this.beg += 4;
            }
          }
        }
      }
      localObject = this.chars;
      i = this.beg;
      return new String((char[])localObject, i, this.end - i);
    }
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Unexpected end of DN: ");
    ((StringBuilder)localObject).append(this.dn);
    throw new IllegalStateException(((StringBuilder)localObject).toString());
  }
  
  private String quotedAV()
  {
    this.pos += 1;
    this.beg = this.pos;
    for (this.end = this.beg;; this.end += 1)
    {
      int i = this.pos;
      if (i == this.length) {
        break;
      }
      localObject = this.chars;
      if (localObject[i] == '"')
      {
        for (this.pos = (i + 1);; this.pos = (i + 1))
        {
          i = this.pos;
          if ((i >= this.length) || (this.chars[i] != ' ')) {
            break;
          }
        }
        localObject = this.chars;
        i = this.beg;
        return new String((char[])localObject, i, this.end - i);
      }
      if (localObject[i] == '\\') {
        localObject[this.end] = getEscaped();
      } else {
        localObject[this.end] = ((char)localObject[i]);
      }
      this.pos += 1;
    }
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Unexpected end of DN: ");
    ((StringBuilder)localObject).append(this.dn);
    throw new IllegalStateException(((StringBuilder)localObject).toString());
  }
  
  public String findMostSpecific(String paramString)
  {
    this.pos = 0;
    this.beg = 0;
    this.end = 0;
    this.cur = 0;
    this.chars = this.dn.toCharArray();
    Object localObject1 = nextAT();
    Object localObject2 = localObject1;
    if (localObject1 == null) {
      return null;
    }
    do
    {
      localObject1 = "";
      int i = this.pos;
      if (i == this.length) {
        return null;
      }
      switch (this.chars[i])
      {
      default: 
        localObject1 = escapedAV();
        break;
      case '#': 
        localObject1 = hexAV();
        break;
      case '"': 
        localObject1 = quotedAV();
      }
      if (paramString.equalsIgnoreCase((String)localObject2)) {
        return (String)localObject1;
      }
      i = this.pos;
      if (i >= this.length) {
        return null;
      }
      localObject1 = this.chars;
      if ((localObject1[i] != ',') && (localObject1[i] != ';') && (localObject1[i] != '+'))
      {
        paramString = new StringBuilder();
        paramString.append("Malformed DN: ");
        paramString.append(this.dn);
        throw new IllegalStateException(paramString.toString());
      }
      this.pos += 1;
      localObject2 = nextAT();
    } while (localObject2 != null);
    paramString = new StringBuilder();
    paramString.append("Malformed DN: ");
    paramString.append(this.dn);
    throw new IllegalStateException(paramString.toString());
  }
}


/* Location:              ~/okhttp3/internal/tls/DistinguishedNameParser.class
 *
 * Reversed by:           J
 */