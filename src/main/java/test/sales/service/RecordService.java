package test.sales.service;

import test.sales.FilterExpr;
import test.sales.OrderByListExpr;
import test.sales.mask.RecordMask;
import test.sales.bean.Record;
import test.sales.mapper.db01.RecordMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Set;

@Service("recordService")
public class RecordService {
  @Transactional(value = "db01_transaction", readOnly = true)
  public Record get(Integer reid, RecordMask mask) {
    return recordMapper.get(reid, mask);
  }

  @Transactional(value = "db01_transaction")
  public Record add(Record bean) {
    if (!validatorFactory.getValidator().validate(bean, Insert.class).isEmpty()) {
      return null;
    }
    recordMapper.add(bean);
    return bean;
  }

  @Transactional(value = "db01_transaction")
  public Record update(Record bean, RecordMask mask) {
    Set<ConstraintViolation<Record>> violationSet =
        validatorFactory.getValidator().validate(bean, Update.class);
    for (ConstraintViolation<Record> violation : violationSet)
      if (mask.get(violation.getPropertyPath().toString())) return null;
    recordMapper.update(bean, mask);
    return bean;
  }

  public boolean delete(Integer reid) {
    return recordMapper.delete(reid);
  }

  @Transactional(value = "db01_transaction")
  public void delete(FilterExpr filter) {
    recordMapper.deleteWhere(filter);
  }

  @Transactional(value = "db01_transaction", readOnly = true)
  public long count(FilterExpr filter) {
    return recordMapper.count(filter);
  }

  @Transactional(value = "db01_transaction", readOnly = true)
  public List<Record> query(
      FilterExpr filter, OrderByListExpr orderByList, long start, long count, RecordMask mask) {
    return recordMapper.query(filter, orderByList, start, count, mask);
  }

  @Transactional(value = "db01_transaction", readOnly = true)
  public boolean exists(Integer reid) {
    return recordMapper.exists(reid);
  }

  @Transactional(value = "db01_transaction", readOnly = true)
  public Integer minReid(FilterExpr filter, Integer defaultValue) {
    return recordMapper.minReid(filter, defaultValue);
  }

  @Transactional(value = "db01_transaction", readOnly = true)
  public Integer maxReid(FilterExpr filter, Integer defaultValue) {
    return recordMapper.maxReid(filter, defaultValue);
  }

  @Transactional(value = "db01_transaction", readOnly = true)
  public Integer minReimid(FilterExpr filter, Integer defaultValue) {
    return recordMapper.minReimid(filter, defaultValue);
  }

  @Transactional(value = "db01_transaction", readOnly = true)
  public Integer maxReimid(FilterExpr filter, Integer defaultValue) {
    return recordMapper.maxReimid(filter, defaultValue);
  }

  @Transactional(value = "db01_transaction", readOnly = true)
  public Integer minReemid(FilterExpr filter, Integer defaultValue) {
    return recordMapper.minReemid(filter, defaultValue);
  }

  @Transactional(value = "db01_transaction", readOnly = true)
  public Integer maxReemid(FilterExpr filter, Integer defaultValue) {
    return recordMapper.maxReemid(filter, defaultValue);
  }

  @Transactional(value = "db01_transaction", readOnly = true)
  public Integer minReqty(FilterExpr filter, Integer defaultValue) {
    return recordMapper.minReqty(filter, defaultValue);
  }

  @Transactional(value = "db01_transaction", readOnly = true)
  public Integer maxReqty(FilterExpr filter, Integer defaultValue) {
    return recordMapper.maxReqty(filter, defaultValue);
  }

  @Transactional(value = "db01_transaction", readOnly = true)
  public java.util.Date minRecreated(FilterExpr filter, java.util.Date defaultValue) {
    return recordMapper.minRecreated(filter, defaultValue);
  }

  @Transactional(value = "db01_transaction", readOnly = true)
  public java.util.Date maxRecreated(FilterExpr filter, java.util.Date defaultValue) {
    return recordMapper.maxRecreated(filter, defaultValue);
  }

  @Value(value = "#{recordMapper}")
  protected RecordMapper recordMapper;

  @Value(value = "#{validatorFactory}")
  protected LocalValidatorFactoryBean validatorFactory;
}
