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
import test.sales.mask.EmployeeMask;
import test.sales.bean.Employee;

@Component("employeeMapper")
public interface EmployeeMapper {
  @Select("SELECT COUNT(*)>0 FROM F0101View WHERE EMID=#{emid}")
  boolean exists(@Param("emid") Integer emid);

  @Select(
      "<script><choose><when test='mask!=null'><if test='mask.emid or mask.emstatus or mask.emgender or mask.emname or mask.emamount'><trim prefix='SELECT' suffixOverrides=','><if test='mask.emid'>EMID, </if><if test='mask.emstatus'>EMSTATUS, </if><if test='mask.emgender'>EMGENDER, </if><if test='mask.emname'>EMNAME, </if><if test='mask.emamount'>EMAMOUNT, </if></trim>FROM F0101View WHERE EMID=#{emid}</if></when><otherwise>SELECTEMID,EMSTATUS,EMGENDER,EMNAME,EMAMOUNT FROM F0101View WHERE EMID=#{emid}</otherwise></choose></script>")
  Employee get(@Param("emid") Integer emid, @Param("mask") EmployeeMask mask);

  @Insert(
      "INSERT INTO F0101 (EMID,EMSTATUS,EMGENDER,EMNAME) VALUES (#{bean.emid,jdbcType=SMALLINT},#{bean.emstatus,jdbcType=SMALLINT},#{bean.emgender,jdbcType=VARCHAR},#{bean.emname,jdbcType=VARCHAR})")
  void add(@Param("bean") Employee bean);

  @Update(
      "<script><choose><when test='mask!=null'><if test='mask.emstatus or mask.emgender or mask.emname'>UPDATE F0101 <set><if test='mask.emstatus'>EMSTATUS=#{bean.emstatus,jdbcType=SMALLINT}, </if><if test='mask.emgender'>EMGENDER=#{bean.emgender,jdbcType=VARCHAR}, </if><if test='mask.emname'>EMNAME=#{bean.emname,jdbcType=VARCHAR}, </if></set>WHERE EMID=#{bean.emid}</if></when><otherwise>UPDATE F0101 SET EMSTATUS=#{bean.emstatus,jdbcType=SMALLINT}, EMGENDER=#{bean.emgender,jdbcType=VARCHAR}, EMNAME=#{bean.emname,jdbcType=VARCHAR} WHERE EMID=#{bean.emid}</otherwise></choose></script>")
  void update(@Param("bean") Employee bean, @Param("mask") EmployeeMask mask);

  @Delete("DELETE FROM F0101 WHERE EMID=#{emid}")
  boolean delete(Integer emid);

  @Delete(
      "<script>DELETE FROM F0101<if test='filter!=null'> WHERE ${filter.toStringOracle()}</if></script>")
  void deleteWhere(@Param("filter") FilterExpr filter);

  @Select(
      "<script>SELECT A.* FROM (SELECT B.*, ROWNUM B_ROWNUM FROM (<choose><when test='mask!=null'><trim prefix='SELECT' suffixOverrides=','><if test='mask.emid'>emid,</if><if test='mask.emstatus'>emstatus,</if><if test='mask.emgender'>emgender,</if><if test='mask.emname'>emname,</if><if test='mask.emamount'>emamount,</if></trim></when><otherwise>SELECT emid, emstatus, emgender, emname, emamount</otherwise></choose> FROM F0101View<if test='filter!=null'> WHERE ${filter.toStringOracle()}</if><if test='order!=null'> ORDER BY ${order.toString()}</if>) B WHERE ROWNUM &lt;=#{start}+#{count}) A WHERE B_ROWNUM &gt;=#{start}+1</script>")
  List<Employee> query(
      @Param("filter") FilterExpr filter,
      @Param("order") OrderByListExpr order,
      @Param("start") long start,
      @Param("count") long count,
      @Param("mask") EmployeeMask mask);

  @Select(
      "<script>SELECT COUNT(*) FROM F0101View<if test='filter!=null'> WHERE ${filter.toStringOracle()}</if></script>")
  long count(@Param("filter") FilterExpr filter);

  @Select(
      "<script>SELECT DECODE(MIN(EMID),NULL,${defaultValue}) FROM F0101View<if test='filter!=null'> WHERE ${filter.toStringOracle()}</if></script>")
  Integer minEmid(@Param("filter") FilterExpr filter, @Param("defaultValue") Integer defaultValue);

  @Select(
      "<script>SELECT DECODE(MAX(EMID),NULL,${defaultValue}) FROM F0101View<if test='filter!=null'> WHERE ${filter.toStringOracle()}</if></script>")
  Integer maxEmid(@Param("filter") FilterExpr filter, @Param("defaultValue") Integer defaultValue);

  @Select(
      "<script>SELECT DECODE(MIN(EMSTATUS),NULL,${defaultValue}) FROM F0101View<if test='filter!=null'> WHERE ${filter.toStringOracle()}</if></script>")
  Integer minEmstatus(
      @Param("filter") FilterExpr filter, @Param("defaultValue") Integer defaultValue);

  @Select(
      "<script>SELECT DECODE(MAX(EMSTATUS),NULL,${defaultValue}) FROM F0101View<if test='filter!=null'> WHERE ${filter.toStringOracle()}</if></script>")
  Integer maxEmstatus(
      @Param("filter") FilterExpr filter, @Param("defaultValue") Integer defaultValue);

  @Select(
      "<script>SELECT DECODE(MIN(EMAMOUNT),NULL,${defaultValue}) FROM F0101View<if test='filter!=null'> WHERE ${filter.toStringOracle()}</if></script>")
  Double minEmamount(
      @Param("filter") FilterExpr filter, @Param("defaultValue") Double defaultValue);

  @Select(
      "<script>SELECT DECODE(MAX(EMAMOUNT),NULL,${defaultValue}) FROM F0101View<if test='filter!=null'> WHERE ${filter.toStringOracle()}</if></script>")
  Double maxEmamount(
      @Param("filter") FilterExpr filter, @Param("defaultValue") Double defaultValue);
}