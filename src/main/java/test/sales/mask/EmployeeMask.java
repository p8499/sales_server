package test.sales.mask;

public class EmployeeMask {
  protected long value = 0b000000;

  public boolean getEmid() {
    return (value >> 5 & 1) == 1;
  }

  public EmployeeMask setEmid(boolean emid) {
    if (emid) value |= 0b100000;
    else value &= 0b011111;
    return this;
  }

  public boolean getEmstatus() {
    return (value >> 4 & 1) == 1;
  }

  public EmployeeMask setEmstatus(boolean emstatus) {
    if (emstatus) value |= 0b010000;
    else value &= 0b101111;
    return this;
  }

  public boolean getEmgender() {
    return (value >> 3 & 1) == 1;
  }

  public EmployeeMask setEmgender(boolean emgender) {
    if (emgender) value |= 0b001000;
    else value &= 0b110111;
    return this;
  }

  public boolean getEmname() {
    return (value >> 2 & 1) == 1;
  }

  public EmployeeMask setEmname(boolean emname) {
    if (emname) value |= 0b000100;
    else value &= 0b111011;
    return this;
  }

  public boolean getEmamount() {
    return (value >> 1 & 1) == 1;
  }

  public EmployeeMask setEmamount(boolean emamount) {
    if (emamount) value |= 0b000010;
    else value &= 0b111101;
    return this;
  }

  public boolean getEmbirthday() {
    return (value >> 0 & 1) == 1;
  }

  public EmployeeMask setEmbirthday(boolean embirthday) {
    if (embirthday) value |= 0b000001;
    else value &= 0b111110;
    return this;
  }

  public EmployeeMask(
      boolean emid,
      boolean emstatus,
      boolean emgender,
      boolean emname,
      boolean emamount,
      boolean embirthday) {
    setEmid(emid);
    setEmstatus(emstatus);
    setEmgender(emgender);
    setEmname(emname);
    setEmamount(emamount);
    setEmbirthday(embirthday);
  }

  public EmployeeMask(long v) {
    value = v;
  }

  public EmployeeMask() {}

  public EmployeeMask all(boolean b) {
    setEmid(b);
    setEmstatus(b);
    setEmgender(b);
    setEmname(b);
    setEmamount(b);
    setEmbirthday(b);
    return this;
  }

  public EmployeeMask keys(boolean b) {
    setEmid(b);
    return this;
  }

  public EmployeeMask attributes(boolean b) {
    setEmstatus(b);
    setEmgender(b);
    setEmname(b);
    setEmbirthday(b);
    return this;
  }

  public EmployeeMask physicals(boolean b) {
    setEmid(b);
    setEmstatus(b);
    setEmgender(b);
    setEmname(b);
    setEmbirthday(b);
    return this;
  }

  public EmployeeMask virtuals(boolean b) {
    setEmamount(b);
    return this;
  }

  public boolean get(String p) {
    switch (p) {
      case "emid":
        return getEmid();
      case "emstatus":
        return getEmstatus();
      case "emgender":
        return getEmgender();
      case "emname":
        return getEmname();
      case "emamount":
        return getEmamount();
      case "embirthday":
        return getEmbirthday();
    }
    return false;
  }

  public EmployeeMask set(String p, boolean b) {
    switch (p) {
      case "emid":
        setEmid(b);
        break;
      case "emstatus":
        setEmstatus(b);
        break;
      case "emgender":
        setEmgender(b);
        break;
      case "emname":
        setEmname(b);
        break;
      case "emamount":
        setEmamount(b);
        break;
      case "embirthday":
        setEmbirthday(b);
        break;
    }
    return this;
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }
}
