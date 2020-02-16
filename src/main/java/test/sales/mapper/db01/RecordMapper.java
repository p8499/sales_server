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
import test.sales.mask.RecordMask;
import test.sales.bean.Record;

@Component("recordMapper")
public interface RecordMapper {
  @Select("SELECT COUNT(*)>0 FROM F4211VIEW WHERE REID=#{reid}")
  boolean exists(@Param("reid") Integer reid);

  @Select(
      "<script><choose><when test='mask!=null'><if test='mask.reid or mask.reimid or mask.reemid or mask.reqty or mask.recreated'><trim prefix='SELECT' suffixOverrides=','><if test='mask.reid'>REID, </if><if test='mask.reimid'>REIMID, </if><if test='mask.reemid'>REEMID, </if><if test='mask.reqty'>REQTY, </if><if test='mask.recreated'>RECREATED, </if></trim>FROM F4211VIEW WHERE REID=#{reid}</if></when><otherwise>SELECTREID,REIMID,REEMID,REQTY,RECREATED FROM F4211VIEW WHERE REID=#{reid}</otherwise></choose></script>")
  Record get(@Param("reid") Integer reid, @Param("mask") RecordMask mask);

  @org.apache.ibatis.annotations.SelectKey(
    statement = "SELECT F4211_REID.nextval AS reid FROM DUAL",
    before = true,
    resultType = Integer.class,
    keyColumn = "reid",
    keyProperty = "bean.reid"
  )
  @Insert(
      "INSERT INTO F4211 (REID,REIMID,REEMID,REQTY,RECREATED) VALUES (#{bean.reid,jdbcType=INTEGER},#{bean.reimid,jdbcType=INTEGER},#{bean.reemid,jdbcType=SMALLINT},#{bean.reqty,jdbcType=SMALLINT},#{bean.recreated,jdbcType=TIMESTAMP})")
  void add(@Param("bean") Record bean);

  @Update(
      "<script><choose><when test='mask!=null'><if test='mask.reimid or mask.reemid or mask.reqty or mask.recreated'>UPDATE F4211 <set><if test='mask.reimid'>REIMID=#{bean.reimid,jdbcType=INTEGER}, </if><if test='mask.reemid'>REEMID=#{bean.reemid,jdbcType=SMALLINT}, </if><if test='mask.reqty'>REQTY=#{bean.reqty,jdbcType=SMALLINT}, </if><if test='mask.recreated'>RECREATED=#{bean.recreated,jdbcType=TIMESTAMP}, </if></set>WHERE REID=#{bean.reid}</if></when><otherwise>UPDATE F4211 SET REIMID=#{bean.reimid,jdbcType=INTEGER}, REEMID=#{bean.reemid,jdbcType=SMALLINT}, REQTY=#{bean.reqty,jdbcType=SMALLINT}, RECREATED=#{bean.recreated,jdbcType=TIMESTAMP} WHERE REID=#{bean.reid}</otherwise></choose></script>")
  void update(@Param("bean") Record bean, @Param("mask") RecordMask mask);

  @Delete("DELETE FROM F4211 WHERE REID=#{reid}")
  boolean delete(Integer reid);

  @Delete(
      "<script>DELETE FROM F4211<if test='filter!=null'> WHERE ${filter.toStringOracle()}</if></script>")
  void deleteWhere(@Param("filter") FilterExpr filter);

  @Select(
      "<script>SELECT A.* FROM (SELECT B.*, ROWNUM B_ROWNUM FROM (<choose><when test='mask!=null'><trim prefix='SELECT' suffixOverrides=','><if test='mask.reid'>reid,</if><if test='mask.reimid'>reimid,</if><if test='mask.reemid'>reemid,</if><if test='mask.reqty'>reqty,</if><if test='mask.recreated'>recreated,</if></trim></when><otherwise>SELECT reid, reimid, reemid, reqty, recreated</otherwise></choose> FROM F4211VIEW<if test='filter!=null'> WHERE ${filter.toStringOracle()}</if><if test='order!=null'> ORDER BY ${order.toString()}</if>) B WHERE ROWNUM &lt;=#{start}+#{count}) A WHERE B_ROWNUM &gt;=#{start}+1</script>")
  List<Record> query(
      @Param("filter") FilterExpr filter,
      @Param("order") OrderByListExpr order,
      @Param("start") long start,
      @Param("count") long count,
      @Param("mask") RecordMask mask);

  @Select(
      "<script>SELECT COUNT(*) FROM F4211VIEW<if test='filter!=null'> WHERE ${filter.toStringOracle()}</if></script>")
  long count(@Param("filter") FilterExpr filter);

  @Select(
      "<script>SELECT DECODE(MIN(REID),NULL,${defaultValue}) FROM F4211VIEW<if test='filter!=null'> WHERE ${filter.toStringOracle()}</if></script>")
  Integer minReid(@Param("filter") FilterExpr filter, @Param("defaultValue") Integer defaultValue);

  @Select(
      "<script>SELECT DECODE(MAX(REID),NULL,${defaultValue}) FROM F4211VIEW<if test='filter!=null'> WHERE ${filter.toStringOracle()}</if></script>")
  Integer maxReid(@Param("filter") FilterExpr filter, @Param("defaultValue") Integer defaultValue);

  @Select(
      "<script>SELECT DECODE(MIN(REIMID),NULL,${defaultValue}) FROM F4211VIEW<if test='filter!=null'> WHERE ${filter.toStringOracle()}</if></script>")
  Integer minReimid(
      @Param("filter") FilterExpr filter, @Param("defaultValue") Integer defaultValue);

  @Select(
      "<script>SELECT DECODE(MAX(REIMID),NULL,${defaultValue}) FROM F4211VIEW<if test='filter!=null'> WHERE ${filter.toStringOracle()}</if></script>")
  Integer maxReimid(
      @Param("filter") FilterExpr filter, @Param("defaultValue") Integer defaultValue);

  @Select(
      "<script>SELECT DECODE(MIN(REEMID),NULL,${defaultValue}) FROM F4211VIEW<if test='filter!=null'> WHERE ${filter.toStringOracle()}</if></script>")
  Integer minReemid(
      @Param("filter") FilterExpr filter, @Param("defaultValue") Integer defaultValue);

  @Select(
      "<script>SELECT DECODE(MAX(REEMID),NULL,${defaultValue}) FROM F4211VIEW<if test='filter!=null'> WHERE ${filter.toStringOracle()}</if></script>")
  Integer maxReemid(
      @Param("filter") FilterExpr filter, @Param("defaultValue") Integer defaultValue);

  @Select(
      "<script>SELECT DECODE(MIN(REQTY),NULL,${defaultValue}) FROM F4211VIEW<if test='filter!=null'> WHERE ${filter.toStringOracle()}</if></script>")
  Integer minReqty(@Param("filter") FilterExpr filter, @Param("defaultValue") Integer defaultValue);

  @Select(
      "<script>SELECT DECODE(MAX(REQTY),NULL,${defaultValue}) FROM F4211VIEW<if test='filter!=null'> WHERE ${filter.toStringOracle()}</if></script>")
  Integer maxReqty(@Param("filter") FilterExpr filter, @Param("defaultValue") Integer defaultValue);

  @Select(
      "<script>SELECT DECODE(MIN(RECREATED),NULL,${defaultValue}) FROM F4211VIEW<if test='filter!=null'> WHERE ${filter.toStringOracle()}</if></script>")
  java.util.Date minRecreated(
      @Param("filter") FilterExpr filter, @Param("defaultValue") java.util.Date defaultValue);

  @Select(
      "<script>SELECT DECODE(MAX(RECREATED),NULL,${defaultValue}) FROM F4211VIEW<if test='filter!=null'> WHERE ${filter.toStringOracle()}</if></script>")
  java.util.Date maxRecreated(
      @Param("filter") FilterExpr filter, @Param("defaultValue") java.util.Date defaultValue);
}
