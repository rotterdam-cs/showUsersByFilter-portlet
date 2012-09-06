package com.rcs.portlet;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;
import com.google.gson.Gson;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.util.PortalUtil;
import com.rcs.common.LocalResponse;
import com.rcs.common.PortalInstanceIdentifier;
import com.rcs.common.ResourceBundleHelper;
import com.rcs.common.ServiceActionResult;
import com.rcs.enums.BasePortletSectionsEnum;
import com.rcs.enums.ConfigurationEnum;
import com.rcs.enums.MessagesEnum;
import com.rcs.expert.ConfigurationExpert;
import com.rcs.expert.UtilsExpert;
import com.rcs.service.model.Configuration;

/**
 * @author Prj.M@x <pablo.rendon@rotterdam-cs.com>
 */
@Controller
@Scope("request")
@RequestMapping("VIEW")
public class ExampleController {
	private static Log log = LogFactoryUtil.getLog(ExampleController.class);
	
	private Locale locale;
	private PortalInstanceIdentifier pII;
	private boolean authorized;
		
	@Autowired
    private UtilsExpert utilsExpert;
	
	@Autowired
    private ConfigurationExpert configurationExpert;
		
	/**
	 * Main view
	 * @param request
	 * @param response
	 * @return
	 * @throws PortalException
	 * @throws SystemException
	 */
	@RenderMapping
	public ModelAndView resolveView(PortletRequest request, PortletResponse response) throws PortalException, SystemException {
		HashMap<String, Object> modelAttrs = new HashMap<String, Object>();
		HttpServletRequest httpReq = PortalUtil.getOriginalServletRequest(PortalUtil.getHttpServletRequest(request));
		locale = LocaleUtil.fromLanguageId(LanguageUtil.getLanguageId(request));		
		pII = utilsExpert.getPortalInstanceIdentifier(request);
		
		String exampleGetParameter = httpReq.getParameter("test");
		modelAttrs.put("exampleGetParameter", exampleGetParameter);
		
	    String messagesJson = MessagesEnum.getMessagesDTO(locale);
	    modelAttrs.put("messages", messagesJson);
	    	    
		return new ModelAndView("example/view", modelAttrs);
	}
	
	
	
	/**
	 * Handle Ajax sections
	 * @param section
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResourceMapping(value = "adminSections")
    public ModelAndView adminSectionsController(
             String section
            ,ResourceRequest request
            ,ResourceResponse response
    ) throws Exception {		
	    HashMap<String, Object> modelAttrs = new HashMap<String, Object>();	
	    authorized = true;
        
	    //Configuration
        if (section.equals(BasePortletSectionsEnum.ADMIN_SECTION_CONFIGURATION.getKey())) {       	
        	
        	List<Configuration> configurations = configurationExpert.getAllConfigurations();
    		modelAttrs.put("configurations", configurations);
        
        //View Configuration
        } else if (section.equals(BasePortletSectionsEnum.ADMIN_SECTION_VIEW_REPORTS.getKey())) {        	
        	if (authorized){        	
        		List<Configuration> configurations = configurationExpert.getAllConfigurations();
        		modelAttrs.put("configurations", configurations);
        	} else {
        		String message = ResourceBundleHelper.getKeyLocalizedValue("com.rcs.general.authorization.required", locale);
                modelAttrs.put("errorMessage", message);
                return new ModelAndView("example/top_messages", modelAttrs);
        	}
        } 
        return new ModelAndView("example/" + section, modelAttrs);       
    }
	
	
	
	/**
	 * Save one configuration parameter
	 * @param configurationname
	 * @param configurationvalue
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResourceMapping(value = "adminSaveConfiguration")
    public ModelAndView adminSaveConfigurationController(
             String configurationname
            ,String configurationvalue
            ,ResourceRequest request
            ,ResourceResponse response
    ) throws Exception {
		LocalResponse result = new LocalResponse();
		String message = "";
       
        ServiceActionResult<Configuration> resultupdate = configurationExpert.updateConfiguration(pII, configurationname, configurationvalue);
        if (!resultupdate.isSuccess()) {
            result.setSuccess(false);                
            List<String> validationKeys = resultupdate.getValidationKeys();
            for (String key : validationKeys) {                    
                message += key + " ";
                log.error("ERROR " + key);
            }
            result.setMessage(message);        
            response.getWriter().write(utilsExpert.getJsonFromLocalResponse(result));
            return null;
        }        
               
        result.setSuccess(true);
        message += ResourceBundleHelper.getKeyLocalizedValue("com.rcs.admin.configuration.saved", locale);
        result.setMessage(message);
        log.error(message);
        response.getWriter().write(utilsExpert.getJsonFromLocalResponse(result));
        return null;	
	}
	
	/**
	 * Save several configurations
	 * @param client_id
	 * @param api_key
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResourceMapping(value = "adminSaveConfigurations")
    public ModelAndView adminSaveConfigurationsController(
             String config1
            ,String config2
            ,ResourceRequest request
            ,ResourceResponse response
    ) throws Exception {		
		HashMap<String, String> configurationOptions = new HashMap<String, String>();
		LocalResponse result = new LocalResponse();
		String message = "";
        
        //CONFIGURATION OPTIONS
		if (config1 != null) {
			configurationOptions.put(ConfigurationEnum.CONFIG1.getKey(), config1);
		}
		if (config2 != null) {
			configurationOptions.put(ConfigurationEnum.CONFIG2.getKey(), config2);
		}
        
        //Save all configuration options
        for (Map.Entry<String, String> entry : configurationOptions.entrySet()) {
            ServiceActionResult<Configuration> resultupdate = configurationExpert.updateConfiguration(pII, entry.getKey(), entry.getValue());
            if (!resultupdate.isSuccess()) {
                result.setSuccess(false);                
                List<String> validationKeys = resultupdate.getValidationKeys();
                for (String key : validationKeys) {                    
                    message += key + " ";
                    log.error("ERROR " + key);
                }
                result.setMessage(message);        
                response.getWriter().write(utilsExpert.getJsonFromLocalResponse(result));
                return null;
            }
        }
                
        result.setSuccess(true);
        message += ResourceBundleHelper.getKeyLocalizedValue("com.rcs.admin.configuration.saved", locale);
        result.setMessage(message);
        
    	Gson gson = new Gson();
        String body = gson.toJson(configurationExpert.getAllConfigurations());
        result.setBody(body);
        
        response.getWriter().write(utilsExpert.getJsonFromLocalResponse(result));
        return null;	
	}
	
		
}