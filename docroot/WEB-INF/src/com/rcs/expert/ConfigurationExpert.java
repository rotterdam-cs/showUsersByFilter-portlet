package com.rcs.expert;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.rcs.common.PortalInstanceIdentifier;
import com.rcs.common.ServiceActionResult;
import com.rcs.service.model.Configuration;
import com.rcs.service.service.ConfigurationLocalServiceUtil;

/**
* @author Prj.M@x <pablo.rendon@rotterdam-cs.com>
*/
@Component
public class ConfigurationExpert {
	private static Log log = LogFactoryUtil.getLog(ConfigurationExpert.class); 
	
	
	/**
	 * Get All configurations
	 * @return
	 */
	public List<Configuration> getAllConfigurations(){
		List<Configuration> result = new ArrayList<Configuration>();
		try {
			result = ConfigurationLocalServiceUtil.getConfigurations(0, ConfigurationLocalServiceUtil.getConfigurationsCount());
		} catch (SystemException e) {
			log.error(e);
		}		
		return result;
	}
	
	
	/**
	 * Get Configuration Object by property name
	 * @param propertyname
	 * @param portalInstanceIdentifier
	 * @return
	 */
	public Configuration getConfigurationByPropertyName(
			String propertyname
			,PortalInstanceIdentifier pII
	){
		long groupId = pII.getGroupId();
		long companyId = pII.getCompanyId();
		Configuration entity = null;
		if (pII.validateParameters()) {
			List<Configuration> configuration;
			try {
				configuration = ConfigurationLocalServiceUtil.getConfigurationByPropertyName(propertyname, groupId, companyId);
				if (!configuration.isEmpty()) {
					entity = configuration.get(0);
				}
			} catch (PortalException e) {
				log.error(e);
			} catch (SystemException e) {
				log.error(e);
			}		
		}
		return entity;
	}
		
	/**
	 * Get Configuration value by property name
	 * @param propertyname
	 * @param portalInstanceIdentifier
	 * @return
	 */
	public String getConfigurationValueByPropertyName(
			 String propertyname
			,PortalInstanceIdentifier pII
	){
		String value = "";
		Configuration configuration = getConfigurationByPropertyName(propertyname, pII);
		if (configuration != null){
			value = configuration.getPropertyvalue();
		}
		return value;
	}
	
	
	/**
	 * Update Configuration or create a new one if it does not exists
	 * @param portalInstanceIdentifier
	 * @param propretyName
	 * @param propertyValue
	 * @return
	 */
	public ServiceActionResult<Configuration> updateConfiguration (
			 PortalInstanceIdentifier pII
			, String propretyName
			, String propertyValue
	) {				
		ServiceActionResult<Configuration> resultupdate = null;
		try {
			if (pII.validateFullParameters()) {
				Configuration configuration = getConfigurationByPropertyName(propretyName, pII);
		        if (configuration == null) {
		        	configuration = ConfigurationLocalServiceUtil.addConfiguration(pII.getUserId(), pII.getGroupId(), propretyName, propertyValue);
		        } else {		        	
		        	configuration.setPropertyvalue(propertyValue);
	        		ConfigurationLocalServiceUtil.updateConfiguration(configuration);
	        		resultupdate = ServiceActionResult.buildSuccess(configuration);
		        }
		        resultupdate = ServiceActionResult.buildSuccess(configuration);
			} else {
				resultupdate = ServiceActionResult.buildFailure(null);
			}
	        
		} catch (NoSuchUserException e) {
			log.error("NoSuchUserException " + e.getMessage());
			resultupdate = ServiceActionResult.buildFailure(null);
		} catch (SystemException e) {
			log.error("SystemException " + e.getMessage());
			resultupdate = ServiceActionResult.buildFailure(null);
		}catch (PortalException e) {
			log.error("PortalException " + e.getMessage());
			resultupdate = ServiceActionResult.buildFailure(null);
		} catch (Exception e) {
			log.error("Exception " + e.getMessage());
			resultupdate = ServiceActionResult.buildFailure(null);
		} 
		return resultupdate;
    }
	
}
