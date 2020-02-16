package test.sales.mask;

public class ProductMask {
  protected long value = 0b0000;

  public boolean getImid() {
    return (value >> 3 & 1) == 1;
  }

  public ProductMask setImid(boolean imid) {
    if (imid) value |= 0b1000;
    else value &= 0b0111;
    return this;
  }

  public boolean getImname() {
    return (value >> 2 & 1) == 1;
  }

  public ProductMask setImname(boolean imname) {
    if (imname) value |= 0b0100;
    else value &= 0b1011;
    return this;
  }

  public boolean getImprice() {
    return (value >> 1 & 1) == 1;
  }

  public ProductMask setImprice(boolean imprice) {
    if (imprice) value |= 0b0010;
    else value &= 0b1101;
    return this;
  }

  public boolean getImamount() {
    return (value >> 0 & 1) == 1;
  }

  public ProductMask setImamount(boolean imamount) {
    if (imamount) value |= 0b0001;
    else value &= 0b1110;
    return this;
  }

  public ProductMask(boolean imid, boolean imname, boolean imprice, boolean imamount) {
    setImid(imid);
    setImname(imname);
    setImprice(imprice);
    setImamount(imamount);
  }

  public ProductMask(long v) {
    value = v;
  }

  public ProductMask() {}

  public ProductMask all(boolean b) {
    setImid(b);
    setImname(b);
    setImprice(b);
    setImamount(b);
    return this;
  }

  public ProductMask keys(boolean b) {
    setImid(b);
    return this;
  }

  public ProductMask attributes(boolean b) {
    setImname(b);
    setImprice(b);
    return this;
  }

  public ProductMask physicals(boolean b) {
    setImid(b);
    setImname(b);
    setImprice(b);
    return this;
  }

  public ProductMask virtuals(boolean b) {
    setImamount(b);
    return this;
  }

  public boolean get(String p) {
    switch (p) {
      case "imid":
        return getImid();
      case "imname":
        return getImname();
      case "imprice":
        return getImprice();
      case "imamount":
        return getImamount();
    }
    return false;
  }

  public ProductMask set(String p, boolean b) {
    switch (p) {
      case "imid":
        setImid(b);
        break;
      case "imname":
        setImname(b);
        break;
      case "imprice":
        setImprice(b);
        break;
      case "imamount":
        setImamount(b);
        break;
    }
    return this;
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }
}
