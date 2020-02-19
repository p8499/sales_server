package test.sales.controller.base;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import test.sales.*;
import test.sales.bean.Employee;
import test.sales.mask.EmployeeMask;
import test.sales.service.EmployeeService;

public abstract class EmployeeControllerBase {
  protected static final String path = "api/empl";
  protected static final String listPath = "api/empl_list";
  protected static final String attachmentPath = "api/empl_attachment";
  protected static final String pathKey = "/{emid}";

  @CrossOrigin(origins = "http://192.168.100.43:8080")
  @RequestMapping(
    value = path + pathKey,
    method = RequestMethod.GET,
    produces = "application/json;charset=UTF-8"
  )
  public String get(
      HttpSession session,
      HttpServletRequest request,
      HttpServletResponse response,
      @PathVariable Integer emid,
      @RequestParam(required = false) String mask)
      throws Exception {
    EmployeeMask maskObj =
        mask == null || mask.equals("")
            ? new EmployeeMask().all(true)
            : new EmployeeMask(Long.valueOf(mask));
    Employee bean = onGet(session, request, response, emid, maskObj);
    return jackson.writeValueAsString(bean);
  }

  protected abstract Employee onGet(
      HttpSession session,
      HttpServletRequest request,
      HttpServletResponse response,
      Integer emid,
      EmployeeMask mask)
      throws Exception;

  @CrossOrigin(origins = "http://192.168.100.43:8080")
  @RequestMapping(
    value = path + pathKey,
    method = RequestMethod.POST,
    produces = "application/json;charset=UTF-8"
  )
  public String add(
      HttpSession session,
      HttpServletRequest request,
      HttpServletResponse response,
      @PathVariable Integer emid,
      @RequestBody Employee bean)
      throws Exception {
    onAdd(session, request, response, emid, bean);
    return jackson.writeValueAsString(bean);
  }

  protected abstract Employee onAdd(
      HttpSession session,
      HttpServletRequest request,
      HttpServletResponse response,
      Integer emid,
      Employee bean)
      throws Exception;

  @CrossOrigin(origins = "http://192.168.100.43:8080")
  @RequestMapping(
    value = path + pathKey,
    method = RequestMethod.PUT,
    produces = "application/json;charset=UTF-8"
  )
  public String update(
      HttpSession session,
      HttpServletRequest request,
      HttpServletResponse response,
      @PathVariable Integer emid,
      @RequestBody Employee bean,
      @RequestParam(required = false) String mask)
      throws Exception {
    EmployeeMask maskObj =
        mask == null || mask.equals("")
            ? new EmployeeMask().all(true)
            : new EmployeeMask(Long.valueOf(mask));
    onUpdate(session, request, response, emid, bean, maskObj);
    return jackson.writeValueAsString(bean);
  }

  protected abstract Employee onUpdate(
      HttpSession session,
      HttpServletRequest request,
      HttpServletResponse response,
      Integer emid,
      Employee bean,
      EmployeeMask mask)
      throws Exception;

  @CrossOrigin(origins = "http://192.168.100.43:8080")
  @RequestMapping(
    value = path + pathKey,
    method = RequestMethod.DELETE,
    produces = "application/json;charset=UTF-8"
  )
  public void delete(
      HttpSession session,
      HttpServletRequest request,
      HttpServletResponse response,
      @PathVariable Integer emid)
      throws Exception {
    onDelete(session, request, response, emid);
  }

  protected abstract void onDelete(
      HttpSession session, HttpServletRequest request, HttpServletResponse response, Integer emid)
      throws Exception;

  @CrossOrigin(origins = "http://192.168.100.43:8080", exposedHeaders = "Content-Range")
  @RequestMapping(
    value = listPath,
    method = {RequestMethod.GET, RequestMethod.POST},
    produces = "application/json;charset=UTF-8"
  )
  public String query(
      HttpSession session,
      HttpServletRequest request,
      HttpServletResponse response,
      @RequestParam(required = false, name = "filter") String paramFilter,
      @RequestBody(required = false) String bodyFilter,
      @RequestParam(required = false) String orderBy,
      @RequestHeader(required = false, name = "Range", defaultValue = "items=0-9") String range,
      @RequestParam(required = false) String mask)
      throws Exception {
    String filter = paramFilter == null || paramFilter.equals("") ? bodyFilter : paramFilter;
    FilterExpr filterObj =
        filter == null || filter.equals("") ? null : jackson.readValue(filter, FilterExpr.class);
    OrderByListExpr orderByListObj =
        orderBy == null || orderBy.equals("") ? null : OrderByListExpr.fromQuery(orderBy);
    RangeExpr rangeObj = RangeExpr.fromQuery(range);
    EmployeeMask maskObj =
        mask == null || mask.equals("")
            ? new EmployeeMask().all(true)
            : new EmployeeMask(Long.valueOf(mask));
    Long total = onCount(session, request, response, filterObj);
    if (total == null) return null;
    long start = rangeObj.getStart(total);
    long count = rangeObj.getCount(total);
    List<Employee> results =
        onQuery(session, request, response, filterObj, orderByListObj, start, count, maskObj);
    response.setHeader(
        "Content-Range", RangeListExpr.getContentRange(start, results.size(), total));
    return jackson.writeValueAsString(results);
  }

  protected abstract Long onCount(
      HttpSession session,
      HttpServletRequest request,
      HttpServletResponse response,
      FilterExpr filter)
      throws Exception;

  protected abstract List<Employee> onQuery(
      HttpSession session,
      HttpServletRequest request,
      HttpServletResponse response,
      FilterExpr filter,
      OrderByListExpr orderByList,
      long start,
      long count,
      EmployeeMask mask)
      throws Exception;

  @CrossOrigin(origins = "http://192.168.100.43:8080", exposedHeaders = "Content-Disposition")
  @RequestMapping(
    value = attachmentPath + pathKey,
    method = RequestMethod.GET,
    produces = "application/octet-stream"
  )
  public void downloadAttachment(
      HttpSession session,
      HttpServletRequest request,
      HttpServletResponse response,
      @PathVariable Integer emid,
      @RequestParam(required = true) String name)
      throws Exception {
    InputStream input = inputStream(session, request, response, emid, name);
    if (input == null) return;
    String contentType = URLConnection.guessContentTypeFromName(name);
    response.setContentType(contentType == null ? "application/octet-stream" : contentType);
    response.setHeader("Content-Disposition", "attachment;fileName=" + name);
    StreamUtils.copy(input, response.getOutputStream());
    input.close();
    response.getOutputStream().close();
  }

  protected abstract InputStream inputStream(
      HttpSession session,
      HttpServletRequest request,
      HttpServletResponse response,
      Integer emid,
      String name)
      throws Exception;

  @CrossOrigin(origins = "http://192.168.100.43:8080")
  @RequestMapping(
    value = attachmentPath + pathKey,
    method = RequestMethod.PUT,
    produces = "application/json;charset=UTF-8"
  )
  public void uploadAttachment(
      HttpSession session,
      HttpServletRequest request,
      HttpServletResponse response,
      @PathVariable Integer emid,
      @RequestParam(required = true) String name)
      throws Exception {
    OutputStream output = outputStream(session, request, response, emid, name);
    if (output == null) return;
    StreamUtils.copy(request.getInputStream(), output);
    request.getInputStream().close();
    output.close();
  }

  protected abstract OutputStream outputStream(
      HttpSession session,
      HttpServletRequest request,
      HttpServletResponse response,
      Integer emid,
      String name)
      throws Exception;

  @CrossOrigin(origins = "http://192.168.100.43:8080")
  @RequestMapping(
    value = attachmentPath + pathKey,
    method = RequestMethod.DELETE,
    produces = "application/json;charset=UTF-8"
  )
  public void deleteAttachment(
      HttpSession session,
      HttpServletRequest request,
      HttpServletResponse response,
      @PathVariable Integer emid,
      @RequestParam(required = true) String name)
      throws Exception {
    onDeleteAttachment(session, request, response, emid, name);
  }

  protected abstract void onDeleteAttachment(
      HttpSession session,
      HttpServletRequest request,
      HttpServletResponse response,
      Integer emid,
      String name)
      throws Exception;

  @CrossOrigin(origins = "http://192.168.100.43:8080")
  @RequestMapping(
    value = attachmentPath + pathKey,
    method = RequestMethod.GET,
    produces = "application/json;charset=UTF-8"
  )
  public String listAttachments(
      HttpSession session,
      HttpServletRequest request,
      HttpServletResponse response,
      @PathVariable Integer emid)
      throws Exception {
    List<String> result = onListAttachments(session, request, response, emid);
    return jackson.writeValueAsString(result);
  }

  protected abstract List<String> onListAttachments(
      HttpSession session, HttpServletRequest request, HttpServletResponse response, Integer emid)
      throws Exception;

  @Value(value = "#{jackson}")
  protected ObjectMapper jackson;

  @Value(value = "#{employeeService}")
  protected EmployeeService employeeService;
}
