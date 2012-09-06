package com.rcs.portlet.showusersby;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portlet.asset.NoSuchEntryException;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetTag;
import com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.rcs.enums.ShowUsersByConfigurationEnum;

/**
 * @author Prj.M@x <pablo.rendon@rotterdam-cs.com>
 */
@Controller
@Scope("request")
@RequestMapping("EDIT")
public class EditShowUsersByController {
	private static Log log = LogFactoryUtil.getLog(EditShowUsersByController.class);
			
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
		PortletPreferences preferences = request.getPreferences();		
		
		modelAttrs.put("roles", getAllRoles());
		modelAttrs.put("jobtitles", getAllJobTitles());
		modelAttrs.put("groups", getAllGroups());		
		modelAttrs.put("userstags", getAllUserTags());
		modelAttrs.put("categories", getAllCategory());
		
		String role = preferences.getValue(ShowUsersByConfigurationEnum.ROLE.getKey(),"");
		String jobtitle = preferences.getValue(ShowUsersByConfigurationEnum.JOB_TITLE.getKey(),"");
		String group =  preferences.getValue(ShowUsersByConfigurationEnum.GROUP.getKey(),"");
		String linktype =  preferences.getValue(ShowUsersByConfigurationEnum.LINK_TYPE.getKey(),"");
		String customlink =  preferences.getValue(ShowUsersByConfigurationEnum.CUSTOM_LINK.getKey(),"");
		String displaystyle =  preferences.getValue(ShowUsersByConfigurationEnum.DISPLAY_STYLE.getKey(),"1");
		String filtertype =  preferences.getValue(ShowUsersByConfigurationEnum.FILTER_TYPE.getKey(),"0");
		String tags =  preferences.getValue(ShowUsersByConfigurationEnum.TAGS.getKey(),"");
		String category =  preferences.getValue(ShowUsersByConfigurationEnum.CATEGORY.getKey(),"");
		String show_email =  preferences.getValue(ShowUsersByConfigurationEnum.SHOW_EMAIL.getKey(),"0");
		String show_phone =  preferences.getValue(ShowUsersByConfigurationEnum.SHOW_PHONE.getKey(),"0");
		
		modelAttrs.put("selectedrole", role);
		modelAttrs.put("selectedjobtitle", jobtitle);
		modelAttrs.put("selectedgroup",group);		
		modelAttrs.put("linktype", linktype);
		modelAttrs.put("customlink", customlink);
		modelAttrs.put("displaystyle", displaystyle);
		modelAttrs.put("filtertype", filtertype);
		modelAttrs.put("tags", tags);
		modelAttrs.put("selectedcategory", category);
		modelAttrs.put("selectedshow_email", show_email);
		modelAttrs.put("selectedshow_phone", show_phone);
		
		return new ModelAndView("showusersby/edit", modelAttrs);
	}
	
	
	/**
	 * Store Portlet Configuration
	 * @param role
	 * @param jobtitle
	 * @param group
	 * @param customlink
	 * @param linktype
	 * @param displaystyle
	 * @param filtertype
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResourceMapping(value = "saveConfiguration")
    public ModelAndView saveConfigurationController(
             String role
            ,String jobtitle
            ,String group
            ,String customlink
            ,String linktype
            ,String displaystyle
            ,String filtertype
            ,String tags
            ,String category
            ,String show_email
            ,String show_phone
            ,ResourceRequest request
            ,ResourceResponse response
    ) throws Exception {        
		PortletPreferences preferences = request.getPreferences();
	    preferences.setValue(ShowUsersByConfigurationEnum.ROLE.getKey(), role);
	    preferences.setValue(ShowUsersByConfigurationEnum.JOB_TITLE.getKey(), jobtitle);
	    preferences.setValue(ShowUsersByConfigurationEnum.GROUP.getKey(), group);
	    preferences.setValue(ShowUsersByConfigurationEnum.LINK_TYPE.getKey(), linktype);
	    preferences.setValue(ShowUsersByConfigurationEnum.CUSTOM_LINK.getKey(), customlink);
	    preferences.setValue(ShowUsersByConfigurationEnum.DISPLAY_STYLE.getKey(), displaystyle);
	    preferences.setValue(ShowUsersByConfigurationEnum.FILTER_TYPE.getKey(), filtertype);
	    preferences.setValue(ShowUsersByConfigurationEnum.TAGS.getKey(), tags);
	    preferences.setValue(ShowUsersByConfigurationEnum.CATEGORY.getKey(), category);
	    preferences.setValue(ShowUsersByConfigurationEnum.SHOW_EMAIL.getKey(), show_email);
	    preferences.setValue(ShowUsersByConfigurationEnum.SHOW_PHONE.getKey(), show_phone);
        preferences.store();	    
        return null;       
    }
	
	
	/**
	 * Get All Roles
	 * @return
	 */
	private List<Role> getAllRoles() {
		List<Role> resultRoles = new ArrayList<Role>();
		try {
            List<Company> companies = CompanyLocalServiceUtil.getCompanies();
            for(Company company:companies) {
                List<Role> roles = RoleLocalServiceUtil.getRoles(company.getCompanyId());                
                for(Role role:roles) {
                	resultRoles.add(role);
                    //log.error(role.getRoleId()+" "+role.getName());
                }
            }
        } catch (SystemException e) {
        	log.error(e);
        }
		return resultRoles;
    }
	
	
	/**
	 * Get All Job Titles
	 * @return
	 */
	private List<String> getAllJobTitles(){
		List<String> resultJobTitles = new ArrayList<String>();
		try {
			List <User> users = UserLocalServiceUtil.getUsers(0, UserLocalServiceUtil.getUsersCount());			
			for (User user : users) {
				String jobtitle = user.getJobTitle();
				if (!jobtitle.isEmpty() && !resultJobTitles.contains(jobtitle)){
					resultJobTitles.add(jobtitle);
					//log.error(jobtitle);
				}
			}
		} catch (SystemException e) {
			log.error(e);
		}		
		return resultJobTitles;
	}
	
	
	/**
	 * Get All Groups
	 * @return
	 */
	private List<Group> getAllGroups(){
		List<Group> returnGroups = new ArrayList<Group>();				
		try {
			returnGroups = GroupLocalServiceUtil.getGroups(0, GroupLocalServiceUtil.getGroupsCount());
//			for (Group group : returnGroups) {
//				group.getName()
//				group.getGroupId()
//			}
		} catch (SystemException e) {
			log.error(e);
		}
		return returnGroups;
	}
	
	/**
	 * Get All User's tags
	 * @return
	 */
	private List<String> getAllUserTags() {
		List<String> tags = new ArrayList<String>();
		List<User> users;
		try {
			users = UserLocalServiceUtil.getUsers(0, UserLocalServiceUtil.getUsersCount());
			for (User user : users) {
				if (!user.getFirstName().isEmpty()){
					AssetEntry assetEntry = AssetEntryLocalServiceUtil.getEntry(User.class.getName(), user.getPrimaryKey());
					List<AssetTag> assetTags;
					assetTags = assetEntry.getTags();
					for(AssetTag tag : assetTags) {
		            	if (!tags.contains(tag.getName())) {
		            		tags.add(tag.getName());
		            	}						               
		            }			
				}
			}		
		} catch(NoSuchEntryException e){			
			log.error(e);
		} catch (SystemException e) {
			log.error(e);
		} catch (PortalException e) {
			log.error(e);
		} catch (Error e) {
			log.error(e);
		}
		return tags;
	}
	
	/**
	 * Get All Categories
	 * @return
	 */
	private List<AssetCategory> getAllCategory() {		
		ClassLoader classLoader = PortalClassLoaderUtil.getClassLoader();
		DynamicQuery query2 = DynamicQueryFactoryUtil.forClass(AssetCategory.class,classLoader);
		List<AssetCategory> allAssetCategories = new ArrayList<AssetCategory>();
		try {
			allAssetCategories = AssetCategoryLocalServiceUtil.dynamicQuery(query2);
		} catch (SystemException e) {
			log.error(e);
		}		
		return allAssetCategories;
	}
	
}