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

import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.model.Phone;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroupRole;
import com.liferay.portal.model.Website;
import com.liferay.portal.service.UserGroupRoleLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portlet.asset.NoSuchEntryException;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetTag;
import com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetVocabularyServiceUtil;
import com.rcs.dto.BloggerDTO;
import com.rcs.dto.BloggersDTO;
import com.rcs.enums.ShowUsersByConfigurationEnum;

/**
 * @author Prj.M@x <pablo.rendon@rotterdam-cs.com>
 */
@Controller
@Scope("request")
@RequestMapping("VIEW")
public class ShowUsersByController {
	private static Log log = LogFactoryUtil.getLog(ShowUsersByController.class);
			
	/**
	 * Main view  to retrieve static filtered users
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
        	
		modelAttrs.put("linktype",linktype);
		modelAttrs.put("displaystyle",displaystyle);
		modelAttrs.put("filtertype",filtertype);
		
		if (show_email == null) {
			show_email = "0";
		}
		if (show_phone == null) {
			show_phone = "0";
		}
		
		BloggersDTO usersDTO = getfilteredUsers(filtertype, group, role, jobtitle, tags, linktype, customlink, category, show_email, show_phone);		
		modelAttrs.put("filteredUsers",usersDTO);
		return new ModelAndView("showusersby/view", modelAttrs);
	}
	
	
	
	/**
	 * AJAX method to retrieve dynamic filtered users
	 * @param usersid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResourceMapping(value = "getFilteredUsers")
    public ModelAndView getFilteredUsersController(
             String usersid
            ,String group
            ,String role
            ,String jobtitle
            ,String tags
            ,String category
            ,ResourceRequest request
            ,ResourceResponse response
    ) throws Exception {        
		HashMap<String, Object> modelAttrs = new HashMap<String, Object>();
		
		PortletPreferences preferences = request.getPreferences();
		String linktype =  preferences.getValue(ShowUsersByConfigurationEnum.LINK_TYPE.getKey(),"");
		String customlink =  preferences.getValue(ShowUsersByConfigurationEnum.CUSTOM_LINK.getKey(),"");
		String displaystyle =  preferences.getValue(ShowUsersByConfigurationEnum.DISPLAY_STYLE.getKey(),"1");	
		String filtertype =  preferences.getValue(ShowUsersByConfigurationEnum.FILTER_TYPE.getKey(),"0");
		String show_email =  preferences.getValue(ShowUsersByConfigurationEnum.SHOW_EMAIL.getKey(),"0");
		String show_phone =  preferences.getValue(ShowUsersByConfigurationEnum.SHOW_PHONE.getKey(),"0");	
		
		modelAttrs.put("displaystyle",displaystyle);
				
		if (usersid != null && !usersid.isEmpty()) {
			String[] filteredUsersId;
			filteredUsersId = usersid.split(",");
			
			if (filteredUsersId.length > 0) {
				try{
					List<User> filteredUsers = new ArrayList<User>();
					for (String filteredUser : filteredUsersId) {
						if (!filteredUser.isEmpty()){
							User liferayUser = UserLocalServiceUtil.getUser(Long.parseLong(filteredUser));
							filteredUsers.add(liferayUser);
						}
					}			
					BloggersDTO usersDTO = fillUserDTO(filteredUsers, linktype, customlink, show_email, show_phone);
					modelAttrs.put("filteredUsers",usersDTO);
				} catch (NoSuchUserException e) {
					log.error(e);	
				} catch (NumberFormatException e) {
					log.error(e);
				} catch (PortalException e) {
					log.error(e);
				} catch (SystemException e) {
					log.error(e);
				} catch (Exception e) {
					log.error(e);
				}
			}		
		} else {
			BloggersDTO usersDTO = getfilteredUsers(filtertype, group, role, jobtitle, tags, linktype, customlink, category, show_email, show_phone);		
			modelAttrs.put("filteredUsers",usersDTO);
		}
        return new ModelAndView("showusersby/users", modelAttrs);  
    }
	
	
	/**
	 * Filter list of users by job title
	 * @param users
	 * @param jobTitle
	 * @return
	 */
	private List<User> filterJobTitle(List<User> users, String jobtitle){
		List<User> resultUsers = new ArrayList<User>();
		if (!jobtitle.isEmpty()) {
			for (User user : users) {
				if (user.getJobTitle().equals(jobtitle)){
					resultUsers.add(user);
				}
			}
		} else {
			resultUsers = users;
		}
		return resultUsers;
	}
	
	
	/**
	 * Filter list of users by Tags
	 * @param users
	 * @param tags
	 * @return
	 */
	private List<User> filterTag(List<User> users, String stringTags) {
		List<User> resultUsers = new ArrayList<User>();
		if (!stringTags.isEmpty()) {
			String[] tags = stringTags.split(",");
			for (User user : users) {				
				if (!user.getFirstName().isEmpty()) {
					try {
						AssetEntry assetEntry = AssetEntryLocalServiceUtil.getEntry(User.class.getName(), user.getPrimaryKey());
						List<AssetTag> assetTags;
						assetTags = assetEntry.getTags();
						for(AssetTag tag : assetTags) {
			            	for (String ctag : tags) {
			            		 if (!ctag.isEmpty() && tag.getName().equals(ctag)) {
			 						resultUsers.add(user);
			 					}
			            	}	               
			            }
					} catch(NoSuchEntryException e){			
						log.error(e);
					} catch (SystemException e) {
						log.error(e);
					} catch (PortalException e) {
						log.error(e);
					}  
				}
			}
		} else {
			resultUsers = users;
		}
		return resultUsers;
	}
	
	/**
	 * Filter list of users by Category
	 * @param users
	 * @param categoryId
	 * @return
	 */
	private List<User> filterCategory(List<User> users, String sCategoryId) {
		List<User> resultUsers = new ArrayList<User>();		
		if (sCategoryId != null && !sCategoryId.isEmpty() ) {		
			Long categoryId = Long.valueOf(sCategoryId);
			for (User user : users) {				
				if (!user.getFirstName().isEmpty()) {		
					try {
						AssetEntry assetEntry = AssetEntryLocalServiceUtil.getEntry(User.class.getName(), user.getPrimaryKey());
						List<AssetCategory> assetCategories;
						assetCategories = assetEntry.getCategories();
						for(AssetCategory cat : assetCategories) {		            	
							if (categoryId == cat.getCategoryId()){
								resultUsers.add(user);
							}													
			            }					
					} catch (SystemException e) {
						log.error(e);
					} catch (PortalException e) {
						log.error(e);
					}		
				}
			}
		} else {
			resultUsers = users;
		}
		return resultUsers;
	}
	
	/**
	 * get filtered Users
	 * @param filtertype
	 * @param group
	 * @param role
	 * @param jobtitle
	 * @param tags
	 * @param linktype
	 * @param customlink
	 * @return
	 */
	private BloggersDTO getfilteredUsers(
			 String filtertype
			,String group
			,String role
			,String jobtitle
			,String tags
			,String linktype
			,String customlink
			,String category
			,String show_email
			,String show_phone
	) {
		BloggersDTO usersDTO = null;
		try {						
			List<User> grouprolusers = new ArrayList<User>();
			if (group != null && !group.isEmpty() && role != null && !role.isEmpty()){
				List<UserGroupRole> userGroupRoles = UserGroupRoleLocalServiceUtil.getUserGroupRolesByGroupAndRole(Long.valueOf(group), Long.valueOf(role));			
				for(UserGroupRole userGroupRole:userGroupRoles){
					grouprolusers.add(userGroupRole.getUser());
				}
			} else if (group != null && !group.isEmpty()) {
				grouprolusers = UserLocalServiceUtil.getUserGroupUsers(Long.valueOf(group));
			} else if (role != null && !role.isEmpty()) {
				grouprolusers = UserLocalServiceUtil.getRoleUsers(Long.valueOf(role));
			} else {				 
				if (
					filtertype.equals("0") //Static Filter
					|| ( jobtitle != null && !jobtitle.isEmpty() ) 
					|| ( tags != null && !tags.isEmpty() ) 
					|| ( category != null && !category.isEmpty() ) 
				) {
					grouprolusers = UserLocalServiceUtil.getUsers(0, UserLocalServiceUtil.getUsersCount());
				}
			}			
			List<User> filteredUsers = filterJobTitle(grouprolusers, jobtitle);
			filteredUsers = filterTag(filteredUsers, tags);
			filteredUsers = filterCategory(filteredUsers, category);			
			
			if (filteredUsers.size() > 0) {			
				usersDTO = fillUserDTO(filteredUsers, linktype, customlink, show_email, show_phone);				
			}		
			
		}catch (PortalException e) {
			log.error(e);
		}catch (SystemException e) {
			log.error(e);
		}
		return usersDTO;
	}
	
	
	/**
	 * Fill Fltered Users to DTO
	 * @param filteredUsers
	 * @param linktype
	 * @param customlink
	 * @return
	 * @throws SystemException
	 */
	private BloggersDTO fillUserDTO(
			 List<User> filteredUsers
			,String linktype
			,String customlink
			,String show_email
			,String show_phone
	) throws SystemException {
		BloggersDTO usersDTO = new BloggersDTO();
		for (User filteredUser : filteredUsers) {
			BloggerDTO userDTO = new BloggerDTO();				
			userDTO.setUserId(filteredUser.getUserId());
			
			String comment = filteredUser.getJobTitle();
			if (show_email == null){
				show_email = "0";
			}
			if (show_phone == null){
				show_phone = "0";
			}
			if (show_email.equals("1") || show_phone.equals("1")) {
				comment += "<ul class=\"user-detail-comments\">";
				if (show_email.equals("1")){
					//comment += "<li class=\"email\"> <span><a href=\"mailto:" + filteredUser.getEmailAddress() + "\">" + filteredUser.getEmailAddress() + "</a></span></li>";
					//em-address
					comment += "<li class=\"email userd-detail-em-address\" id=\"userd-detail-em-address" + filteredUser.getUserId() + "\">" + filteredUser.getEmailAddress().replace("@", "[at]") + "</li>";
				}
				if (show_phone.equals("1")) {
					List<Phone> phones = filteredUser.getPhones();
		            if(!phones.isEmpty()) {
		                for(Phone phone : phones) {
		                    if(phone.isPrimary()) {
		                        comment += "<li class=\"phone\">" + phone.getNumber() + " " + phone.getExtension() + "</li>";
		                    }
		                }
		            }					
				}
			}			
			userDTO.setComment(comment);
			userDTO.setUrl("#" + filteredUser.getUserId());
			userDTO.setAuthorcategory(filteredUser.getScreenName());
			//User's website
			if (linktype.equals("1")) {
				List<Website> websites = filteredUser.getWebsites();
				for (Website website : websites) {
					userDTO.setUrl(website.getUrl());
				}	
			//IPC
			} else if (linktype.equals("2")) {
				
			//Custom
			} else if (linktype.equals("3")) {
				userDTO.setUrl(customlink.replace("{userid}", Long.toString(filteredUser.getUserId())));
			//Filter Blog or Asset
			} else if (linktype.equals("4")) {
				
			}				
			usersDTO.addBlogger(userDTO);
		}	
		return usersDTO;
	}
		
}