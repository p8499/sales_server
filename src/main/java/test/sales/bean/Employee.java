package test.sales.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Employee {
  public static final String TABLE = "F0101";
  public static final String VIEW = "F0101VIEW";
  public static final String NAME = "EMPLOYEE";

  //region emid Employee ID
  public static final String FIELD_EMID = "EMID";
  protected Integer emid = null;
  public static final int CONSTRAINT_EMID_LENGTH_INTEGER = 4;
  public static final int CONSTRAINT_EMID_MIN = -9999;
  public static final int CONSTRAINT_EMID_MAX = 9999;

  @javax.validation.constraints.NotNull(groups = {Insert.class, Update.class})
  @javax.validation.constraints.Min(value = CONSTRAINT_EMID_MIN)
  @javax.validation.constraints.Max(value = CONSTRAINT_EMID_MAX)
  public Integer getEmid() {
    return emid;
  }

  public Employee setEmid(Integer emid) {
    this.emid = emid;
    return this;
  }
  //endregion

  //region emstatus Employee Status
  public static final String FIELD_EMSTATUS = "EMSTATUS";
  public static final Integer EMSTATUS_VALID = 0;
  public static final Integer EMSTATUS_INVALID = 1;
  public static final Integer DEFAULT_EMSTATUS = 0;
  protected Integer emstatus = DEFAULT_EMSTATUS;
  public static final int CONSTRAINT_EMSTATUS_LENGTH_INTEGER = 1;
  public static final int CONSTRAINT_EMSTATUS_MIN = -9;
  public static final int CONSTRAINT_EMSTATUS_MAX = 9;

  @javax.validation.constraints.NotNull(groups = {Insert.class, Update.class})
  @javax.validation.constraints.Min(value = CONSTRAINT_EMSTATUS_MIN)
  @javax.validation.constraints.Max(value = CONSTRAINT_EMSTATUS_MAX)
  public Integer getEmstatus() {
    return emstatus;
  }

  public Employee setEmstatus(Integer emstatus) {
    this.emstatus = emstatus;
    return this;
  }
  //endregion

  //region emgender Employee Gender
  public static final String FIELD_EMGENDER = "EMGENDER";
  public static final String EMGENDER_MALE = "M";
  public static final String EMGENDER_FEMALE = "F";
  protected String emgender = null;
  public static final int CONSTRAINT_EMGENDER_LENGTH_STRING = 1;

  @javax.validation.constraints.NotNull(groups = {Insert.class, Update.class})
  @javax.validation.constraints.Size(max = CONSTRAINT_EMGENDER_LENGTH_STRING)
  public String getEmgender() {
    return emgender;
  }

  public Employee setEmgender(String emgender) {
    this.emgender = emgender;
    return this;
  }
  //endregion

  //region emname Employee Name
  public static final String FIELD_EMNAME = "EMNAME";
  protected String emname = null;
  public static final int CONSTRAINT_EMNAME_LENGTH_STRING = 64;

  @javax.validation.constraints.Size(max = CONSTRAINT_EMNAME_LENGTH_STRING)
  public String getEmname() {
    return emname;
  }

  public Employee setEmname(String emname) {
    this.emname = emname;
    return this;
  }
  //endregion

  //region emamount Total Sales Amount
  public static final String FIELD_EMAMOUNT = "EMAMOUNT";
  protected Double emamount = null;

  @javax.validation.constraints.Null(groups = {Insert.class, Update.class})
  public Double getEmamount() {
    return emamount;
  }

  public Employee setEmamount(Double emamount) {
    this.emamount = emamount;
    return this;
  }
  //endregion

  //region embirthday Employee Birthday
  public static final String FIELD_EMBIRTHDAY = "EMBIRTHDAY";

  @com.fasterxml.jackson.annotation.JsonFormat(timezone = "GMT+8", pattern = "yyyyMMddHHmmss")
  protected java.util.Date embirthday = null;

  public java.util.Date getEmbirthday() {
    return embirthday;
  }

  public Employee setEmbirthday(java.util.Date embirthday) {
    this.embirthday = embirthday;
    return this;
  }
  //endregion

  public Employee(
      Integer emid,
      Integer emstatus,
      String emgender,
      String emname,
      Double emamount,
      java.util.Date embirthday) {
    if (emid != null) this.emid = emid;
    if (emstatus != null) this.emstatus = emstatus;
    if (emgender != null) this.emgender = emgender;
    if (emname != null) this.emname = emname;
    if (emamount != null) this.emamount = emamount;
    if (embirthday != null) this.embirthday = embirthday;
  }

  public Employee() {
    this(null, null, null, null, null, null);
  }

  public Employee clone() {
    return new Employee(emid, emstatus, emgender, emname, emamount, embirthday);
  }
}
