package test.sales.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;
import test.sales.FilterExpr;
import test.sales.FilterLogicExpr;
import test.sales.OrderByListExpr;
import test.sales.bean.Employee;
import test.sales.bean.Record;
import test.sales.controller.base.EmployeeControllerBase;
import test.sales.mask.EmployeeMask;
import test.sales.service.RecordService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.Arrays;
import java.util.List;

@RestController
public class EmployeeController extends EmployeeControllerBase {
    @Override
    protected Employee onGet(HttpSession session, HttpServletRequest request, HttpServletResponse response, Integer emid, EmployeeMask mask) throws Exception {
        //just fetch the record and return
        Employee employee = employeeService.get(emid, mask);
        response.setStatus(employee == null ? HttpServletResponse.SC_NOT_FOUND : HttpServletResponse.SC_OK);
        return employee;
    }

    @Override
    protected Employee onAdd(HttpSession session, HttpServletRequest request, HttpServletResponse response, Integer emid, Employee bean) throws Exception {
        //add and return the bean itself
        if (employeeService.count(new FilterLogicExpr().equalsNumber(Employee.FIELD_EMID, bean.getEmid())) > 0) {
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            return null;
        } else {
            Employee employee = employeeService.add(bean);
            response.setStatus(employee == null ? HttpServletResponse.SC_BAD_REQUEST : HttpServletResponse.SC_OK);
            return employee;
        }
    }

    @Override
    protected Employee onUpdate(HttpSession session, HttpServletRequest request, HttpServletResponse response, Integer emid, Employee bean, EmployeeMask mask) throws Exception {
        //update and return the bean itself
        Employee employee = employeeService.update(bean, mask);
        response.setStatus(employee == null ? HttpServletResponse.SC_BAD_REQUEST : HttpServletResponse.SC_OK);
        return employee;
    }

    @Override
    protected void onDelete(HttpSession session, HttpServletRequest request, HttpServletResponse response, Integer emid) throws Exception {
        //here we simulate an HTTP-409 error code
        if (recordService.count(new FilterLogicExpr().equalsNumber(Record.FIELD_REEMID, emid)) == 0) {
            boolean deleted = employeeService.delete(emid);
            response.setStatus(deleted ? HttpServletResponse.SC_OK : HttpServletResponse.SC_NO_CONTENT);
        } else
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    }

    @Override
    protected Long onCount(HttpSession session, HttpServletRequest request, HttpServletResponse response, FilterExpr filter) throws Exception {
        //count and return
        return employeeService.count(filter);
    }

    @Override
    protected List<Employee> onQuery(HttpSession session, HttpServletRequest request, HttpServletResponse response, FilterExpr filter, OrderByListExpr orderByList, long start, long count, EmployeeMask mask) throws Exception {
        //query and return the list
        return employeeService.query(filter, orderByList, start, count, mask);
    }

    @Override
    protected InputStream inputStream(HttpSession session, HttpServletRequest request, HttpServletResponse response, Integer emid, String name) throws Exception {
        //when fetch employee attachment, this method will be invoked
        //each employee has a portrait
        //suppose we put these files in WEB-INF/attachment/employee
        //for eg, the portrait file is located in WEB-INF/attachment/employee/88/portrait.png for employee who's emid is 88
        //in this case, the client should request downloadAttachment method providing name="portrait.png"
        File file = new File(session.getServletContext().getRealPath(String.format("%s/employee/%d/%s", attachmentFolder, emid, name)));
        if (!file.exists()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        } else {
            return new FileInputStream(file);
        }
    }

    @Override
    protected OutputStream outputStream(HttpSession session, HttpServletRequest request, HttpServletResponse response, Integer emid, String name) throws Exception {
        //when add or update new employee attachment, this method will be invoked
        File file = new File(session.getServletContext().getRealPath(String.format("%s/employee/%d/%s", attachmentFolder, emid, name)));
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        return new FileOutputStream(file);
    }

    @Override
    protected void onDeleteAttachment(HttpSession session, HttpServletRequest request, HttpServletResponse response, Integer emid, String name) throws Exception {
        //delete employee attachment
        File file = new File(session.getServletContext().getRealPath(String.format("%s/employee/%d/%s", attachmentFolder, emid, name)));
        if (!file.exists())
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        else
            file.delete();
    }

    @Override
    protected List<String> onListAttachments(HttpSession session, HttpServletRequest request, HttpServletResponse response, Integer emid) throws Exception {
        //list all attachments of one employee
        File folder = new File(session.getServletContext().getRealPath(String.format("%s/employee/%d", attachmentFolder, emid)));
        if (folder.exists())
            return Arrays.asList(folder.list());
        else
            return null;
    }

    @Value(value = "${app.attachmentFolder}")
    protected String attachmentFolder;
    @Value(value = "#{recordService}")
    protected RecordService recordService;
}
