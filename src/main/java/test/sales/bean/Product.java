package test.sales.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Product {
  public static final String TABLE = "F4101";
  public static final String VIEW = "F4101VIEW";
  public static final String NAME = "PROD";

  //region imid Product ID
  public static final String FIELD_IMID = "IMID";
  protected Integer imid = null;
  public static final int CONSTRAINT_IMID_LENGTH_INTEGER = 8;
  public static final int CONSTRAINT_IMID_MIN = -99999999;
  public static final int CONSTRAINT_IMID_MAX = 99999999;

  @javax.validation.constraints.Null(groups = {Insert.class})
  @javax.validation.constraints.NotNull(groups = {Update.class})
  public Integer getImid() {
    return imid;
  }

  public Product setImid(Integer imid) {
    this.imid = imid;
    return this;
  }
  //endregion

  //region imname Product Name
  public static final String FIELD_IMNAME = "IMNAME";
  protected String imname = null;
  public static final int CONSTRAINT_IMNAME_LENGTH_STRING = 64;

  @javax.validation.constraints.Size(max = CONSTRAINT_IMNAME_LENGTH_STRING)
  public String getImname() {
    return imname;
  }

  public Product setImname(String imname) {
    this.imname = imname;
    return this;
  }
  //endregion

  //region imprice Product Price
  public static final String FIELD_IMPRICE = "IMPRICE";
  public static final Double DEFAULT_IMPRICE = 0.00;
  protected Double imprice = DEFAULT_IMPRICE;
  public static final int CONSTRAINT_IMPRICE_LENGTH_INTEGER = 6;
  public static final int CONSTRAINT_IMPRICE_LENGTH_FRACTION = 2;

  @javax.validation.constraints.NotNull(groups = {Insert.class, Update.class})
  @javax.validation.constraints.Digits(
    integer = CONSTRAINT_IMPRICE_LENGTH_FRACTION,
    fraction = CONSTRAINT_IMPRICE_LENGTH_FRACTION
  )
  public Double getImprice() {
    return imprice;
  }

  public Product setImprice(Double imprice) {
    this.imprice = imprice;
    return this;
  }
  //endregion

  //region imamount Total Sales Amount
  public static final String FIELD_IMAMOUNT = "IMAMOUNT";
  protected Double imamount = null;

  @javax.validation.constraints.Null(groups = {Insert.class, Update.class})
  public Double getImamount() {
    return imamount;
  }

  public Product setImamount(Double imamount) {
    this.imamount = imamount;
    return this;
  }
  //endregion

  public Product(Integer imid, String imname, Double imprice, Double imamount) {
    if (imid != null) this.imid = imid;
    if (imname != null) this.imname = imname;
    if (imprice != null) this.imprice = imprice;
    if (imamount != null) this.imamount = imamount;
  }

  public Product() {
    this(null, null, null, null);
  }

  public Product clone() {
    return new Product(imid, imname, imprice, imamount);
  }
}
