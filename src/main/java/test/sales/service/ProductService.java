package test.sales.service;

import test.sales.FilterExpr;
import test.sales.OrderByListExpr;
import test.sales.mask.ProductMask;
import test.sales.bean.Product;
import test.sales.mapper.db01.ProductMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Set;

@Service("productService")
public class ProductService {
  @Transactional(value = "db01_transaction", readOnly = true)
  public Product get(Integer imid, ProductMask mask) {
    return productMapper.get(imid, mask);
  }

  @Transactional(value = "db01_transaction")
  public Product add(Product bean) {
    if (!validatorFactory.getValidator().validate(bean, Insert.class).isEmpty()) {
      return null;
    }
    productMapper.add(bean);
    return bean;
  }

  @Transactional(value = "db01_transaction")
  public Product update(Product bean, ProductMask mask) {
    Set<ConstraintViolation<Product>> violationSet =
        validatorFactory.getValidator().validate(bean, Update.class);
    for (ConstraintViolation<Product> violation : violationSet)
      if (mask.get(violation.getPropertyPath().toString())) return null;
    productMapper.update(bean, mask);
    return bean;
  }

  public boolean delete(Integer imid) {
    return productMapper.delete(imid);
  }

  @Transactional(value = "db01_transaction")
  public void delete(FilterExpr filter) {
    productMapper.deleteWhere(filter);
  }

  @Transactional(value = "db01_transaction", readOnly = true)
  public long count(FilterExpr filter) {
    return productMapper.count(filter);
  }

  @Transactional(value = "db01_transaction", readOnly = true)
  public List<Product> query(
      FilterExpr filter, OrderByListExpr orderByList, long start, long count, ProductMask mask) {
    return productMapper.query(filter, orderByList, start, count, mask);
  }

  @Transactional(value = "db01_transaction", readOnly = true)
  public boolean exists(Integer imid) {
    return productMapper.exists(imid);
  }

  @Transactional(value = "db01_transaction", readOnly = true)
  public Integer minImid(FilterExpr filter, Integer defaultValue) {
    return productMapper.minImid(filter, defaultValue);
  }

  @Transactional(value = "db01_transaction", readOnly = true)
  public Integer maxImid(FilterExpr filter, Integer defaultValue) {
    return productMapper.maxImid(filter, defaultValue);
  }

  @Transactional(value = "db01_transaction", readOnly = true)
  public Double minImprice(FilterExpr filter, Double defaultValue) {
    return productMapper.minImprice(filter, defaultValue);
  }

  @Transactional(value = "db01_transaction", readOnly = true)
  public Double maxImprice(FilterExpr filter, Double defaultValue) {
    return productMapper.maxImprice(filter, defaultValue);
  }

  @Transactional(value = "db01_transaction", readOnly = true)
  public Double minImamount(FilterExpr filter, Double defaultValue) {
    return productMapper.minImamount(filter, defaultValue);
  }

  @Transactional(value = "db01_transaction", readOnly = true)
  public Double maxImamount(FilterExpr filter, Double defaultValue) {
    return productMapper.maxImamount(filter, defaultValue);
  }

  @Value(value = "#{productMapper}")
  protected ProductMapper productMapper;

  @Value(value = "#{validatorFactory}")
  protected LocalValidatorFactoryBean validatorFactory;
}
