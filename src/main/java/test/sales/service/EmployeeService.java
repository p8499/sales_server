package test.sales.service;

import test.sales.FilterExpr;
import test.sales.OrderByListExpr;
import test.sales.mask.EmployeeMask;
import test.sales.bean.Employee;
import test.sales.mapper.db01.EmployeeMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Set;

@Service("employeeService")
public class EmployeeService {
  @Transactional(value = "db01_transaction", readOnly = true)
  public Employee get(Integer emid, EmployeeMask mask) {
    return employeeMapper.get(emid, mask);
  }

  @Transactional(value = "db01_transaction")
  public Employee add(Employee bean) {
    if (!validatorFactory.getValidator().validate(bean, Insert.class).isEmpty()) {
      return null;
    }
    employeeMapper.add(bean);
    return bean;
  }

  @Transactional(value = "db01_transaction")
  public Employee update(Employee bean, EmployeeMask mask) {
    Set<ConstraintViolation<Employee>> violationSet =
        validatorFactory.getValidator().validate(bean, Update.class);
    for (ConstraintViolation<Employee> violation : violationSet)
      if (mask.get(violation.getPropertyPath().toString())) return null;
    employeeMapper.update(bean, mask);
    return bean;
  }

  public boolean delete(Integer emid) {
    return employeeMapper.delete(emid);
  }

  @Transactional(value = "db01_transaction")
  public void delete(FilterExpr filter) {
    employeeMapper.deleteWhere(filter);
  }

  @Transactional(value = "db01_transaction", readOnly = true)
  public long count(FilterExpr filter) {
    return employeeMapper.count(filter);
  }

  @Transactional(value = "db01_transaction", readOnly = true)
  public List<Employee> query(
      FilterExpr filter, OrderByListExpr orderByList, long start, long count, EmployeeMask mask) {
    return employeeMapper.query(filter, orderByList, start, count, mask);
  }

  @Transactional(value = "db01_transaction", readOnly = true)
  public boolean exists(Integer emid) {
    return employeeMapper.exists(emid);
  }

  @Transactional(value = "db01_transaction", readOnly = true)
  public Integer minEmid(FilterExpr filter, Integer defaultValue) {
    return employeeMapper.minEmid(filter, defaultValue);
  }

  @Transactional(value = "db01_transaction", readOnly = true)
  public Integer maxEmid(FilterExpr filter, Integer defaultValue) {
    return employeeMapper.maxEmid(filter, defaultValue);
  }

  @Transactional(value = "db01_transaction", readOnly = true)
  public Integer minEmstatus(FilterExpr filter, Integer defaultValue) {
    return employeeMapper.minEmstatus(filter, defaultValue);
  }

  @Transactional(value = "db01_transaction", readOnly = true)
  public Integer maxEmstatus(FilterExpr filter, Integer defaultValue) {
    return employeeMapper.maxEmstatus(filter, defaultValue);
  }

  @Transactional(value = "db01_transaction", readOnly = true)
  public Double minEmamount(FilterExpr filter, Double defaultValue) {
    return employeeMapper.minEmamount(filter, defaultValue);
  }

  @Transactional(value = "db01_transaction", readOnly = true)
  public Double maxEmamount(FilterExpr filter, Double defaultValue) {
    return employeeMapper.maxEmamount(filter, defaultValue);
  }

  @Value(value = "#{employeeMapper}")
  protected EmployeeMapper employeeMapper;

  @Value(value = "#{validatorFactory}")
  protected LocalValidatorFactoryBean validatorFactory;
}