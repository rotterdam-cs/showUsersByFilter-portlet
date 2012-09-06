package com.rcs.common;

import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import java.io.IOException;
import javax.portlet.PortletResponse;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author chuqui
 */
public class LiferayJSONResponse {

    private static final String CONTENT_TYPE_APPLICATION_JSON = "application/json";
    private static final Logger log = Logger.getLogger(LiferayJSONResponse.class);

    /**
     * Writes the provided string as an already formatted JSON string.
     *
     * @param liferayPortletResponse
     * @param freeJSONString
     */
    public static void write(PortletResponse liferayPortletResponse, String freeJSONString) {
        // Get the HttpServletResponse
        HttpServletResponse servletResponse = ((LiferayPortletResponse) liferayPortletResponse).getHttpServletResponse();

        // Sets the JSON Content Type
        servletResponse.setContentType(CONTENT_TYPE_APPLICATION_JSON);
        try {
            ServletResponseUtil.write(servletResponse, freeJSONString);
        } catch (IOException ex) {
            log.error("Error writing JSON response");
        }
    }

    /**
     * Writes to the provided response the success status and message. <br />
     * Generated JSON is going to be:<br/> {<br/> &nbsp;&nbsp;success:
     * &lt;success&gt;<br/> &nbsp;&nbsp;,msg: &lt;message&gt;<br/> }
     *
     * @param liferayPortletResponse
     * @param message
     */
    public static void write(PortletResponse liferayPortletResponse, boolean success, String message) {
        JSONResponse json = new JSONResponse();
        json.setMessage(message);
        json.setSuccess(success);
        write(liferayPortletResponse, json);
    }

    /**
     * Writes to the provided response the success status and message. <br />
     * Generated JSON is going to be:<br/> {<br/> &nbsp;&nbsp;success:
     * &lt;success&gt;<br/> &nbsp;&nbsp;,msg: &lt;message&gt;<br/>
     * &nbsp;&nbsp;,data: &lt;object generated JSON&gt;<br/> }
     *
     * @param liferayPortletResponse
     * @param message
     */
    public static void write(PortletResponse liferayPortletResponse, boolean success, String message, Object object) {
        JSONResponse json = new JSONResponse();
        json.setMessage(message);
        json.setSuccess(success);
        json.setData(object);
        write(liferayPortletResponse, json);
    }

    /**
     * Writes to the provided response the success status and message. <br />
     * Generated JSON is going to be:<br/> {<br/> &nbsp;&nbsp;success:
     * &lt;success&gt;<br/> &nbsp;&nbsp;,msg: &lt;message&gt;<br/>
     * &nbsp;&nbsp;,dataArray: &lt;object Array generated JSON&gt;<br/> }
     *
     * @param liferayPortletResponse
     * @param message
     */
    public static void write(PortletResponse liferayPortletResponse, boolean success, String message, Object[] objectArray) {
        JSONResponse json = new JSONResponse();
        json.setMessage(message);
        json.setSuccess(success);
        json.setDataArray(objectArray);
        write(liferayPortletResponse, json);
    }

    /**
     * Writes to the provided response the success status and message. <br />
     * Generated JSON is going to be:<br/> {<br/> &nbsp;&nbsp;success:
     * &lt;success&gt;<br/> &nbsp;&nbsp;,msg: &lt;message&gt;<br/>
     * &nbsp;&nbsp;,data: &lt;object Array generated JSON&gt;<br/>
     * &nbsp;&nbsp;,dataArray: &lt;object Array generated JSON&gt;<br/> }
     *
     * @param liferayPortletResponse
     * @param message
     */
    public static void write(PortletResponse liferayPortletResponse, boolean success, String message, Object object, Object[] objectArray) {
        JSONResponse json = new JSONResponse();
        json.setMessage(message);
        json.setSuccess(success);
        json.setData(object);
        json.setDataArray(objectArray);
        write(liferayPortletResponse, json);
    }

    /**
     * Prints a JSONResponse object to the provided response.
     *
     * @see JSONResponse
     * @param liferayPortletResponse
     * @param json
     */
    public static void write(PortletResponse liferayPortletResponse, JSONResponse json) {
        write(liferayPortletResponse, json.toString());
    }
}
