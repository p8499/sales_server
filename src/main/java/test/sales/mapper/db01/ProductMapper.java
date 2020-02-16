package test.sales.mapper.db01;

import java.util.List;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import test.sales.FilterExpr;
import test.sales.OrderByListExpr;
import test.sales.mask.ProductMask;
import test.sales.bean.Product;

@Component("productMapper")
public interface ProductMapper {
  @Select("SELECT COUNT(*)>0 FROM F4101VIEW WHERE IMID=#{imid}")
  boolean exists(@Param("imid") Integer imid);

  @Select(
      "<script><choose><when test='mask!=null'><if test='mask.imid or mask.imname or mask.imprice or mask.imamount'><trim prefix='SELECT' suffixOverrides=','><if test='mask.imid'>IMID, </if><if test='mask.imname'>IMNAME, </if><if test='mask.imprice'>IMPRICE, </if><if test='mask.imamount'>IMAMOUNT, </if></trim>FROM F4101VIEW WHERE IMID=#{imid}</if></when><otherwise>SELECTIMID,IMNAME,IMPRICE,IMAMOUNT FROM F4101VIEW WHERE IMID=#{imid}</otherwise></choose></script>")
  Product get(@Param("imid") Integer imid, @Param("mask") ProductMask mask);

  @org.apache.ibatis.annotations.SelectKey(
    statement = "SELECT F4101_IMID.nextval AS imid FROM DUAL",
    before = true,
    resultType = Integer.class,
    keyColumn = "imid",
    keyProperty = "bean.imid"
  )
  @Insert(
      "INSERT INTO F4101 (IMID,IMNAME,IMPRICE) VALUES (#{bean.imid,jdbcType=INTEGER},#{bean.imname,jdbcType=VARCHAR},#{bean.imprice,jdbcType=DOUBLE})")
  void add(@Param("bean") Product bean);

  @Update(
      "<script><choose><when test='mask!=null'><if test='mask.imname or mask.imprice'>UPDATE F4101 <set><if test='mask.imname'>IMNAME=#{bean.imname,jdbcType=VARCHAR}, </if><if test='mask.imprice'>IMPRICE=#{bean.imprice,jdbcType=DOUBLE}, </if></set>WHERE IMID=#{bean.imid}</if></when><otherwise>UPDATE F4101 SET IMNAME=#{bean.imname,jdbcType=VARCHAR}, IMPRICE=#{bean.imprice,jdbcType=DOUBLE} WHERE IMID=#{bean.imid}</otherwise></choose></script>")
  void update(@Param("bean") Product bean, @Param("mask") ProductMask mask);

  @Delete("DELETE FROM F4101 WHERE IMID=#{imid}")
  boolean delete(Integer imid);

  @Delete(
      "<script>DELETE FROM F4101<if test='filter!=null'> WHERE ${filter.toStringOracle()}</if></script>")
  void deleteWhere(@Param("filter") FilterExpr filter);

  @Select(
      "<script>SELECT A.* FROM (SELECT B.*, ROWNUM B_ROWNUM FROM (<choose><when test='mask!=null'><trim prefix='SELECT' suffixOverrides=','><if test='mask.imid'>imid,</if><if test='mask.imname'>imname,</if><if test='mask.imprice'>imprice,</if><if test='mask.imamount'>imamount,</if></trim></when><otherwise>SELECT imid, imname, imprice, imamount</otherwise></choose> FROM F4101VIEW<if test='filter!=null'> WHERE ${filter.toStringOracle()}</if><if test='order!=null'> ORDER BY ${order.toString()}</if>) B WHERE ROWNUM &lt;=#{start}+#{count}) A WHERE B_ROWNUM &gt;=#{start}+1</script>")
  List<Product> query(
      @Param("filter") FilterExpr filter,
      @Param("order") OrderByListExpr order,
      @Param("start") long start,
      @Param("count") long count,
      @Param("mask") ProductMask mask);

  @Select(
      "<script>SELECT COUNT(*) FROM F4101VIEW<if test='filter!=null'> WHERE ${filter.toStringOracle()}</if></script>")
  long count(@Param("filter") FilterExpr filter);

  @Select(
      "<script>SELECT DECODE(MIN(IMID),NULL,${defaultValue}) FROM F4101VIEW<if test='filter!=null'> WHERE ${filter.toStringOracle()}</if></script>")
  Integer minImid(@Param("filter") FilterExpr filter, @Param("defaultValue") Integer defaultValue);

  @Select(
      "<script>SELECT DECODE(MAX(IMID),NULL,${defaultValue}) FROM F4101VIEW<if test='filter!=null'> WHERE ${filter.toStringOracle()}</if></script>")
  Integer maxImid(@Param("filter") FilterExpr filter, @Param("defaultValue") Integer defaultValue);

  @Select(
      "<script>SELECT DECODE(MIN(IMPRICE),NULL,${defaultValue}) FROM F4101VIEW<if test='filter!=null'> WHERE ${filter.toStringOracle()}</if></script>")
  Double minImprice(@Param("filter") FilterExpr filter, @Param("defaultValue") Double defaultValue);

  @Select(
      "<script>SELECT DECODE(MAX(IMPRICE),NULL,${defaultValue}) FROM F4101VIEW<if test='filter!=null'> WHERE ${filter.toStringOracle()}</if></script>")
  Double maxImprice(@Param("filter") FilterExpr filter, @Param("defaultValue") Double defaultValue);

  @Select(
      "<script>SELECT DECODE(MIN(IMAMOUNT),NULL,${defaultValue}) FROM F4101VIEW<if test='filter!=null'> WHERE ${filter.toStringOracle()}</if></script>")
  Double minImamount(
      @Param("filter") FilterExpr filter, @Param("defaultValue") Double defaultValue);

  @Select(
      "<script>SELECT DECODE(MAX(IMAMOUNT),NULL,${defaultValue}) FROM F4101VIEW<if test='filter!=null'> WHERE ${filter.toStringOracle()}</if></script>")
  Double maxImamount(
      @Param("filter") FilterExpr filter, @Param("defaultValue") Double defaultValue);
}
