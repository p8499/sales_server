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
import test.sales.bean.Record;
import test.sales.mask.RecordMask;
import test.sales.service.RecordService;

public abstract class RecordControllerBase {
  protected static final String path = "api/record";
  protected static final String attachmentPath = "api/record_attachment";
  protected static final String pathKey = "/{reid}";

  @CrossOrigin(origins = "http://localhost:8080")
  @RequestMapping(
    value = path + pathKey,
    method = RequestMethod.GET,
    produces = "application/json;charset=UTF-8"
  )
  public String get(
      HttpSession session,
      HttpServletRequest request,
      HttpServletResponse response,
      @PathVariable Integer reid,
      @RequestParam(required = false) String mask)
      throws Exception {
    RecordMask maskObj =
        mask == null || mask.equals("")
            ? new RecordMask().all(true)
            : new RecordMask(Long.valueOf(mask));
    Record bean = onGet(session, request, response, reid, maskObj);
    return jackson.writeValueAsString(bean);
  }

  protected abstract Record onGet(
      HttpSession session,
      HttpServletRequest request,
      HttpServletResponse response,
      Integer reid,
      RecordMask mask)
      throws Exception;

  @CrossOrigin(origins = "http://localhost:8080")
  @RequestMapping(
    value = path,
    method = RequestMethod.POST,
    produces = "application/json;charset=UTF-8"
  )
  public String add(
      HttpSession session,
      HttpServletRequest request,
      HttpServletResponse response,
      @RequestBody Record bean)
      throws Exception {
    onAdd(session, request, response, bean);
    return jackson.writeValueAsString(bean);
  }

  protected abstract Record onAdd(
      HttpSession session, HttpServletRequest request, HttpServletResponse response, Record bean)
      throws Exception;

  @CrossOrigin(origins = "http://localhost:8080")
  @RequestMapping(
    value = path + pathKey,
    method = RequestMethod.PUT,
    produces = "application/json;charset=UTF-8"
  )
  public String update(
      HttpSession session,
      HttpServletRequest request,
      HttpServletResponse response,
      @PathVariable Integer reid,
      @RequestBody Record bean,
      @RequestParam(required = false) String mask)
      throws Exception {
    RecordMask maskObj =
        mask == null || mask.equals("")
            ? new RecordMask().all(true)
            : new RecordMask(Long.valueOf(mask));
    onUpdate(session, request, response, reid, bean, maskObj);
    return jackson.writeValueAsString(bean);
  }

  protected abstract Record onUpdate(
      HttpSession session,
      HttpServletRequest request,
      HttpServletResponse response,
      Integer reid,
      Record bean,
      RecordMask mask)
      throws Exception;

  @CrossOrigin(origins = "http://localhost:8080")
  @RequestMapping(
    value = path + pathKey,
    method = RequestMethod.DELETE,
    produces = "application/json;charset=UTF-8"
  )
  public void delete(
      HttpSession session,
      HttpServletRequest request,
      HttpServletResponse response,
      @PathVariable Integer reid)
      throws Exception {
    onDelete(session, request, response, reid);
  }

  protected abstract void onDelete(
      HttpSession session, HttpServletRequest request, HttpServletResponse response, Integer reid)
      throws Exception;

  @CrossOrigin(origins = "http://localhost:8080", exposedHeaders = "Content-Range")
  @RequestMapping(
    value = path + "_list",
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
    RecordMask maskObj =
        mask == null || mask.equals("")
            ? new RecordMask().all(true)
            : new RecordMask(Long.valueOf(mask));
    Long total = onCount(session, request, response, filterObj);
    if (total == null) return null;
    long start = rangeObj.getStart(total);
    long count = rangeObj.getCount(total);
    List<Record> results =
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

  protected abstract List<Record> onQuery(
      HttpSession session,
      HttpServletRequest request,
      HttpServletResponse response,
      FilterExpr filter,
      OrderByListExpr orderByList,
      long start,
      long count,
      RecordMask mask)
      throws Exception;

  @CrossOrigin(origins = "http://localhost:8080", exposedHeaders = "Content-Disposition")
  @RequestMapping(
    value = attachmentPath + pathKey,
    method = RequestMethod.GET,
    produces = "application/octet-stream"
  )
  public void downloadAttachment(
      HttpSession session,
      HttpServletRequest request,
      HttpServletResponse response,
      @PathVariable Integer reid,
      @RequestParam(required = true) String name)
      throws Exception {
    InputStream input = inputStream(session, request, response, reid, name);
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
      Integer reid,
      String name)
      throws Exception;

  @CrossOrigin(origins = "http://localhost:8080")
  @RequestMapping(
    value = attachmentPath + pathKey,
    method = RequestMethod.PUT,
    produces = "application/json;charset=UTF-8"
  )
  public void uploadAttachment(
      HttpSession session,
      HttpServletRequest request,
      HttpServletResponse response,
      @PathVariable Integer reid,
      @RequestParam(required = true) String name)
      throws Exception {
    OutputStream output = outputStream(session, request, response, reid, name);
    if (output == null) return;
    StreamUtils.copy(request.getInputStream(), output);
    request.getInputStream().close();
    output.close();
  }

  protected abstract OutputStream outputStream(
      HttpSession session,
      HttpServletRequest request,
      HttpServletResponse response,
      Integer reid,
      String name)
      throws Exception;

  @CrossOrigin(origins = "http://localhost:8080")
  @RequestMapping(
    value = attachmentPath + pathKey,
    method = RequestMethod.DELETE,
    produces = "application/json;charset=UTF-8"
  )
  public void deleteAttachment(
      HttpSession session,
      HttpServletRequest request,
      HttpServletResponse response,
      @PathVariable Integer reid,
      @RequestParam(required = true) String name)
      throws Exception {
    onDeleteAttachment(session, request, response, reid, name);
  }

  protected abstract void onDeleteAttachment(
      HttpSession session,
      HttpServletRequest request,
      HttpServletResponse response,
      Integer reid,
      String name)
      throws Exception;

  @CrossOrigin(origins = "http://localhost:8080")
  @RequestMapping(
    value = attachmentPath + pathKey,
    method = RequestMethod.GET,
    produces = "application/json;charset=UTF-8"
  )
  public String listAttachments(
      HttpSession session,
      HttpServletRequest request,
      HttpServletResponse response,
      @PathVariable Integer reid)
      throws Exception {
    List<String> result = onListAttachments(session, request, response, reid);
    return jackson.writeValueAsString(result);
  }

  protected abstract List<String> onListAttachments(
      HttpSession session, HttpServletRequest request, HttpServletResponse response, Integer reid)
      throws Exception;

  @Value(value = "#{jackson}")
  protected ObjectMapper jackson;

  @Value(value = "#{recordService}")
  protected RecordService recordService;
}
