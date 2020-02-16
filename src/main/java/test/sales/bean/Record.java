package test.sales.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Record {
  public static final String TABLE = "F4211";
  public static final String VIEW = "F4211VIEW";
  public static final String NAME = "RECORD";

  //region reid Record ID
  public static final String FIELD_REID = "REID";
  protected Integer reid = null;
  public static final int CONSTRAINT_REID_LENGTH_INTEGER = 8;
  public static final int CONSTRAINT_REID_MIN = -99999999;
  public static final int CONSTRAINT_REID_MAX = 99999999;

  @javax.validation.constraints.Null(groups = {Insert.class})
  @javax.validation.constraints.NotNull(groups = {Update.class})
  public Integer getReid() {
    return reid;
  }

  public Record setReid(Integer reid) {
    this.reid = reid;
    return this;
  }
  //endregion

  //region reimid Product ID
  public static final String FIELD_REIMID = "REIMID";
  protected Integer reimid = null;
  public static final int CONSTRAINT_REIMID_LENGTH_INTEGER = 8;
  public static final int CONSTRAINT_REIMID_MIN = -99999999;
  public static final int CONSTRAINT_REIMID_MAX = 99999999;

  @javax.validation.constraints.NotNull(groups = {Insert.class, Update.class})
  @javax.validation.constraints.Min(value = CONSTRAINT_REIMID_MIN)
  @javax.validation.constraints.Max(value = CONSTRAINT_REIMID_MAX)
  public Integer getReimid() {
    return reimid;
  }

  public Record setReimid(Integer reimid) {
    this.reimid = reimid;
    return this;
  }
  //endregion

  //region reemid Employee ID
  public static final String FIELD_REEMID = "REEMID";
  protected Integer reemid = null;
  public static final int CONSTRAINT_REEMID_LENGTH_INTEGER = 4;
  public static final int CONSTRAINT_REEMID_MIN = -9999;
  public static final int CONSTRAINT_REEMID_MAX = 9999;

  @javax.validation.constraints.NotNull(groups = {Insert.class, Update.class})
  @javax.validation.constraints.Min(value = CONSTRAINT_REEMID_MIN)
  @javax.validation.constraints.Max(value = CONSTRAINT_REEMID_MAX)
  public Integer getReemid() {
    return reemid;
  }

  public Record setReemid(Integer reemid) {
    this.reemid = reemid;
    return this;
  }
  //endregion

  //region reqty Quantity
  public static final String FIELD_REQTY = "REQTY";
  public static final Integer DEFAULT_REQTY = 1;
  protected Integer reqty = DEFAULT_REQTY;
  public static final int CONSTRAINT_REQTY_LENGTH_INTEGER = 4;
  public static final int CONSTRAINT_REQTY_MIN = -9999;
  public static final int CONSTRAINT_REQTY_MAX = 9999;

  @javax.validation.constraints.NotNull(groups = {Insert.class, Update.class})
  @javax.validation.constraints.Min(value = CONSTRAINT_REQTY_MIN)
  @javax.validation.constraints.Max(value = CONSTRAINT_REQTY_MAX)
  public Integer getReqty() {
    return reqty;
  }

  public Record setReqty(Integer reqty) {
    this.reqty = reqty;
    return this;
  }
  //endregion

  //region recreated Created Time
  public static final String FIELD_RECREATED = "RECREATED";

  @com.fasterxml.jackson.annotation.JsonFormat(timezone = "GMT+8", pattern = "yyyyMMddHHmmss")
  protected java.util.Date recreated = null;

  @javax.validation.constraints.NotNull(groups = {Insert.class, Update.class})
  public java.util.Date getRecreated() {
    return recreated;
  }

  public Record setRecreated(java.util.Date recreated) {
    this.recreated = recreated;
    return this;
  }
  //endregion

  public Record(
      Integer reid, Integer reimid, Integer reemid, Integer reqty, java.util.Date recreated) {
    if (reid != null) this.reid = reid;
    if (reimid != null) this.reimid = reimid;
    if (reemid != null) this.reemid = reemid;
    if (reqty != null) this.reqty = reqty;
    if (recreated != null) this.recreated = recreated;
  }

  public Record() {
    this(null, null, null, null, null);
  }

  public Record clone() {
    return new Record(reid, reimid, reemid, reqty, recreated);
  }
}
