package test.sales.controller;

import org.springframework.web.bind.annotation.RestController;
import test.sales.FilterExpr;
import test.sales.OrderByListExpr;
import test.sales.bean.Record;
import test.sales.controller.base.RecordControllerBase;
import test.sales.mask.RecordMask;

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
public class RecordController extends RecordControllerBase {
    @Override
    protected Record onGet(HttpSession session, HttpServletRequest request, HttpServletResponse response, Integer reid, RecordMask mask) throws Exception {
        Record record = recordService.get(reid, mask);
        response.setStatus(record == null ? HttpServletResponse.SC_NOT_FOUND : HttpServletResponse.SC_OK);
        return record;
    }

    @Override
    protected Record onAdd(HttpSession session, HttpServletRequest request, HttpServletResponse response, Record bean) throws Exception {
        Record record = recordService.add(bean);
        response.setStatus(record == null ? HttpServletResponse.SC_BAD_REQUEST : HttpServletResponse.SC_OK);
        return record;
    }

    @Override
    protected Record onUpdate(HttpSession session, HttpServletRequest request, HttpServletResponse response, Integer reid, Record bean, RecordMask mask) throws Exception {
        Record record = recordService.update(bean, mask);
        response.setStatus(record == null ? HttpServletResponse.SC_BAD_REQUEST : HttpServletResponse.SC_OK);
        return record;
    }

    @Override
    protected void onDelete(HttpSession session, HttpServletRequest request, HttpServletResponse response, Integer reid) throws Exception {
        boolean deleted = recordService.delete(reid);
        response.setStatus(deleted ? HttpServletResponse.SC_OK : HttpServletResponse.SC_NO_CONTENT);
    }

    @Override
    protected Long onCount(HttpSession session, HttpServletRequest request, HttpServletResponse response, FilterExpr filter) throws Exception {
        return recordService.count(filter);
    }

    @Override
    protected List<Record> onQuery(HttpSession session, HttpServletRequest request, HttpServletResponse response, FilterExpr filter, OrderByListExpr orderByList, long start, long count, RecordMask mask) throws Exception {
        return recordService.query(filter, orderByList, start, count, mask);
    }

    @Override
    protected InputStream inputStream(HttpSession session, HttpServletRequest request, HttpServletResponse response, Integer reid, String name) throws Exception {
        return null;
    }

    @Override
    protected OutputStream outputStream(HttpSession session, HttpServletRequest request, HttpServletResponse response, Integer reid, String name) throws Exception {
        return null;
    }

    @Override
    protected void onDeleteAttachment(HttpSession session, HttpServletRequest request, HttpServletResponse response, Integer reid, String name) throws Exception {

    }

    @Override
    protected List<String> onListAttachments(HttpSession session, HttpServletRequest request, HttpServletResponse response, Integer reid) throws Exception {
        return null;
    }
}
