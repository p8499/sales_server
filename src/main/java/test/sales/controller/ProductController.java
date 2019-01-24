package test.sales.controller;

import org.springframework.web.bind.annotation.RestController;
import test.sales.FilterExpr;
import test.sales.OrderByListExpr;
import test.sales.bean.Product;
import test.sales.controller.base.ProductControllerBase;
import test.sales.mask.ProductMask;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by Administrator on 7/2/2018.
 */
@RestController
public class ProductController extends ProductControllerBase {
    @Override
    protected Product onGet(HttpSession session, HttpServletRequest request, HttpServletResponse response, Integer imid, ProductMask mask) throws Exception {
        Product product = productService.get(imid, mask);
        response.setStatus(product == null ? HttpServletResponse.SC_NOT_FOUND : HttpServletResponse.SC_OK);
        return product;
    }

    @Override
    protected Product onAdd(HttpSession session, HttpServletRequest request, HttpServletResponse response, Product bean) throws Exception {
        Product product = productService.add(bean);
        response.setStatus(product == null ? HttpServletResponse.SC_BAD_REQUEST : HttpServletResponse.SC_OK);
        return product;
    }

    @Override
    protected Product onUpdate(HttpSession session, HttpServletRequest request, HttpServletResponse response, Integer imid, Product bean, ProductMask mask) throws Exception {
        Product product = productService.update(bean, mask);
        response.setStatus(product == null ? HttpServletResponse.SC_BAD_REQUEST : HttpServletResponse.SC_OK);
        return product;
    }

    @Override
    protected void onDelete(HttpSession session, HttpServletRequest request, HttpServletResponse response, Integer imid) throws Exception {
        boolean deleted = productService.delete(imid);
        response.setStatus(deleted ? HttpServletResponse.SC_OK : HttpServletResponse.SC_NO_CONTENT);
    }

    @Override
    protected Long onCount(HttpSession session, HttpServletRequest request, HttpServletResponse response, FilterExpr filter) throws Exception {
        return productService.count(filter);
    }

    @Override
    protected List<Product> onQuery(HttpSession session, HttpServletRequest request, HttpServletResponse response, FilterExpr filter, OrderByListExpr orderByList, long start, long count, ProductMask mask) throws Exception {
        return productService.query(filter, orderByList, start, count, mask);
    }

    @Override
    protected InputStream inputStream(HttpSession session, HttpServletRequest request, HttpServletResponse response, Integer imid, String name) throws Exception {
        return null;
    }

    @Override
    protected OutputStream outputStream(HttpSession session, HttpServletRequest request, HttpServletResponse response, Integer imid, String name) throws Exception {
        return null;
    }

    @Override
    protected void onDeleteAttachment(HttpSession session, HttpServletRequest request, HttpServletResponse response, Integer imid, String name) throws Exception {

    }

    @Override
    protected List<String> onListAttachments(HttpSession session, HttpServletRequest request, HttpServletResponse response, Integer imid) throws Exception {
        return null;
    }
}
