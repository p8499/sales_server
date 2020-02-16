package test.sales.mask;

public class RecordMask {
  protected long value = 0b00000;

  public boolean getReid() {
    return (value >> 4 & 1) == 1;
  }

  public RecordMask setReid(boolean reid) {
    if (reid) value |= 0b10000;
    else value &= 0b01111;
    return this;
  }

  public boolean getReimid() {
    return (value >> 3 & 1) == 1;
  }

  public RecordMask setReimid(boolean reimid) {
    if (reimid) value |= 0b01000;
    else value &= 0b10111;
    return this;
  }

  public boolean getReemid() {
    return (value >> 2 & 1) == 1;
  }

  public RecordMask setReemid(boolean reemid) {
    if (reemid) value |= 0b00100;
    else value &= 0b11011;
    return this;
  }

  public boolean getReqty() {
    return (value >> 1 & 1) == 1;
  }

  public RecordMask setReqty(boolean reqty) {
    if (reqty) value |= 0b00010;
    else value &= 0b11101;
    return this;
  }

  public boolean getRecreated() {
    return (value >> 0 & 1) == 1;
  }

  public RecordMask setRecreated(boolean recreated) {
    if (recreated) value |= 0b00001;
    else value &= 0b11110;
    return this;
  }

  public RecordMask(
      boolean reid, boolean reimid, boolean reemid, boolean reqty, boolean recreated) {
    setReid(reid);
    setReimid(reimid);
    setReemid(reemid);
    setReqty(reqty);
    setRecreated(recreated);
  }

  public RecordMask(long v) {
    value = v;
  }

  public RecordMask() {}

  public RecordMask all(boolean b) {
    setReid(b);
    setReimid(b);
    setReemid(b);
    setReqty(b);
    setRecreated(b);
    return this;
  }

  public RecordMask keys(boolean b) {
    setReid(b);
    return this;
  }

  public RecordMask attributes(boolean b) {
    setReimid(b);
    setReemid(b);
    setReqty(b);
    setRecreated(b);
    return this;
  }

  public RecordMask physicals(boolean b) {
    setReid(b);
    setReimid(b);
    setReemid(b);
    setReqty(b);
    setRecreated(b);
    return this;
  }

  public RecordMask virtuals(boolean b) {
    return this;
  }

  public boolean get(String p) {
    switch (p) {
      case "reid":
        return getReid();
      case "reimid":
        return getReimid();
      case "reemid":
        return getReemid();
      case "reqty":
        return getReqty();
      case "recreated":
        return getRecreated();
    }
    return false;
  }

  public RecordMask set(String p, boolean b) {
    switch (p) {
      case "reid":
        setReid(b);
        break;
      case "reimid":
        setReimid(b);
        break;
      case "reemid":
        setReemid(b);
        break;
      case "reqty":
        setReqty(b);
        break;
      case "recreated":
        setRecreated(b);
        break;
    }
    return this;
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }
}
