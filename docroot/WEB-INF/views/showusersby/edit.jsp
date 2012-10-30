<%--//@author Prj.M@x <pablo.rendon@rotterdam-cs.com> --%>
<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="portlet" uri="http://java.sun.com/portlet" %>
<%@taglib prefix="aui" uri="http://liferay.com/tld/aui" %>
<%@taglib prefix="liferay-ui" uri="http://liferay.com/tld/ui" %>
<%@taglib prefix="liferay-theme" uri="http://liferay.com/tld/theme" %>
<%@taglib prefix="liferay-util" uri="http://liferay.com/tld/util" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<fmt:setBundle basename="Language"/>
<portlet:defineObjects />
<portlet:resourceURL var="saveConfigurationURL" id="saveConfiguration" />

<div id="<portlet:namespace/>administration-container-mask">	
	<form class="well form-inline" id="<portlet:namespace/>configurationform">	    
    	<div class="control-group">
	    	<label for="<portlet:namespace/>filtertype"><fmt:message key="com.rcs.show.users.by.filter.type"/>:</label>
	    	<div class="controls">
		    	<select name="<portlet:namespace/>filtertype" id="<portlet:namespace/>filtertype">
		    		<option value="0" <c:if test="${filtertype == '0'}">selected="selected"</c:if>><fmt:message key="com.rcs.show.users.by.static.filter"/></option>
		    		<option value="1" <c:if test="${filtertype == '1'}">selected="selected"</c:if> ><fmt:message key="com.rcs.show.users.by.dynamic.filter"/></option>			    		
		    	</select>
	    	</div>
	    </div><br />
	    <fmt:message key="com.rcs.show.users.by.filter.configuration"/>:
	    <fieldset>
	    	<div id="<portlet:namespace/>dynamicfilter" class="dynamicfilter" <c:if test="${filtertype != '1'}">style="display:none;"</c:if> >
		    	<b><fmt:message key="com.rcs.show.users.by.dynamic.filter.ipc"/>:</b><br /><br />
			    <p>
				    <code>
				    	Liferay.fire('showFilteredUsers', {usersId: [1024,1151]});					    				
					</code>
				</p><br />
				<p>
				    <code>
				    	Liferay.fire('showFilteredUsers', {group: '10154', role: '10161', jobtitle: 'Engineer', tags: ['sales','featured'], category: 'employees' });					    				
					</code>
				</p><br />
				<p>
				    <code>
				    	Liferay.fire('showFilteredUsers', {role: '10161'});					    				
					</code>
				</p><br />
	    	</div>
	    	<div id="<portlet:namespace/>staticfilter" class="staticfilter" <c:if test="${filtertype != '0'}">style="display:none;"</c:if> >
			    <div class="control-group">
			    	<label for="<portlet:namespace/>role"><fmt:message key="com.rcs.show.users.by.filter.by.role"/>:</label>
			    	<div class="controls">
				    	<select name="<portlet:namespace/>role">
				    		<option value=""></option>
				    		<c:forEach items="${roles}" var="role">
								<option value="${role.roleId}" <c:if test="${role.roleId == selectedrole}">selected="selected"</c:if> >${role.name}</option>				
							</c:forEach>
				    	</select>
			    	</div>
			    </div>
			    <div class="control-group">
			    	<label for="<portlet:namespace/>jobtitle"><fmt:message key="com.rcs.show.users.by.filter.by.job"/>:</label>
			    	<div class="controls">
				    	<select name="<portlet:namespace/>jobtitle">
				    		<option value=""></option>
				    		<c:forEach items="${jobtitles}" var="jobtitle">
								<option value="${jobtitle}" <c:if test="${jobtitle == selectedjobtitle}">selected="selected"</c:if> >${jobtitle}</option>				
							</c:forEach>
			    	</select>
			    	</div>
			    </div>
			    <div class="control-group">
			    	<label for="<portlet:namespace/>group"><fmt:message key="com.rcs.show.users.by.filter.by.group"/>:</label>
			    	<div class="controls">
				    	<select name="<portlet:namespace/>group">
				    		<option value=""></option>
				    		<c:forEach items="${groups}" var="group">
								<option value="${group.groupId}" <c:if test="${group.groupId == selectedgroup}">selected="selected"</c:if> >${group.name}</option>				
							</c:forEach>
				    	</select>
			    	</div>
			    </div>
			    <div class="control-group">
			    	<label for="<portlet:namespace/>category"><fmt:message key="com.rcs.show.users.by.filter.by.category"/>:</label>
			    	<div class="controls">
				    	<select name="<portlet:namespace/>category">
				    		<option value=""></option>
				    		<c:forEach items="${categories}" var="category">
								<option value="${category.categoryId}" <c:if test="${category.categoryId == selectedcategory}">selected="selected"</c:if> >${category.name}</option>				
							</c:forEach>
				    	</select>
			    	</div>
			    </div>
			    <div class="control-group">
			    	<label for="<portlet:namespace/>tags"><fmt:message key="com.rcs.show.users.by.filter.by.tags"/>:</label>
			    	<div class="controls">
				    	<input name="<portlet:namespace/>tags" id="<portlet:namespace/>tags" value="${tags}">
			    	</div>
			    </div>			    
			    			    
		    </div>		    
	    </fieldset><br />
	    <fmt:message key="com.rcs.show.users.by.filter.general.configuration"/>:
	    <fieldset>
	    	<div class="control-group">
		    	<label for="<portlet:namespace/>linktype"><fmt:message key="com.rcs.show.users.by.filter.link.to"/>:</label>
		    	<div class="controls">
			    	<select name="<portlet:namespace/>linktype" id="<portlet:namespace/>linktype">
			    		<option value="" selected="selected"><fmt:message key="com.rcs.show.users.by.filter.link.disabled.link"/></option>
			    		<option value="1" <c:if test="${linktype == '1'}">selected="selected"</c:if> ><fmt:message key="com.rcs.show.users.by.filter.link.users.website"/></option>
			    		<option value="2" <c:if test="${linktype == '2'}">selected="selected"</c:if> ><fmt:message key="com.rcs.show.users.by.filter.link.ipc.message"/></option>
			    		<option value="3" <c:if test="${linktype == '3'}">selected="selected"</c:if> ><fmt:message key="com.rcs.show.users.by.filter.link.custom"/></option>	 
			    		<option value="4" <c:if test="${linktype == '4'}">selected="selected"</c:if> ><fmt:message key="com.rcs.show.users.by.filter.link.filterasset.blog"/></option>	   		
			    	</select>
		    	</div>
		    </div>
		    <div class="control-group" id="<portlet:namespace/>customlinkcontainer" <c:if test="${linktype != '3'}">style="display:none;"</c:if>>
		        <label for="<portlet:namespace/>customlink"><fmt:message key="com.rcs.show.users.by.filter.custom.link"/>:</label>
		        <div class="controls">
			        <input type="text" name="<portlet:namespace/>customlink" class="span6" id="<portlet:namespace/>customlink" value="${customlink}" /><br />
			        <small><fmt:message key="com.rcs.show.users.by.filter.custom.link.help"/></small>
		        </div>
		    </div>	    
		    <div id="<portlet:namespace/>ipccontainer" <c:if test="${linktype != '2'}">style="display:none;"</c:if>>
			    <b><fmt:message key="com.rcs.show.users.by.filter.usage.example"/>:</b><br /><br />
			    <p>
				    <code>	    
					    Liferay.on('filteredUsersMessage', function(event) {<br/>
				    		&nbsp;&nbsp; alert('A message has been sent by ' + event.senderPortletId + ' Clicked UserId: ' + event.userId);<br/>
					    });				
					</code>
				</p><br />
		    </div>		    
		    <div class="control-group">
		    	<label for="<portlet:namespace/>displaystyle"><fmt:message key="com.rcs.show.users.by.filter.display.style"/>:</label>
		    	<div class="controls">
			    	<select name="<portlet:namespace/>displaystyle" id="<portlet:namespace/>displaystyle">
			    		<option value="0" <c:if test="${displaystyle == '0'}">selected="selected"</c:if> ><fmt:message key="com.rcs.show.users.by.filter.display.style0"/></option>
			    		<option value="1" <c:if test="${displaystyle == '1'}">selected="selected"</c:if> ><fmt:message key="com.rcs.show.users.by.filter.display.style1"/></option>
			    		<option value="2" <c:if test="${displaystyle == '2'}">selected="selected"</c:if> ><fmt:message key="com.rcs.show.users.by.filter.display.style2"/></option>	    		
			    	</select>
		    	</div>
		    </div>		    
		    <div class="control-group">
		    	<div class="controls">		    		
		    		<input type="checkbox" value="1" name="<portlet:namespace/>show_email" id="<portlet:namespace/>show_email" <c:if test="${selectedshow_email == '1'}">checked="checked"</c:if> > 
		    		<fmt:message key="com.rcs.show.users.by.filter.show.email"/>			    	
		    	</div>
		    </div>
		    <div class="control-group">		    	
		    	<div class="controls">		    		
		    		<input type="checkbox" value="1" name="<portlet:namespace/>show_phone" id="<portlet:namespace/>show_phone" <c:if test="${selectedshow_phone == '1'}">checked="checked"</c:if> > 
		    		<fmt:message key="com.rcs.show.users.by.filter.show.phone"/>  	    	
		    	</div>
		    </div>		    
	    </fieldset>
	    <br/>
	    <div class="control-group">
	        <button type="button" class="btn" id="<portlet:namespace/>save-configuration" ><fmt:message key="com.rcs.general.save"/></button>       
	    </div>
	</form>
</div>
<script type="text/javascript">		
Liferay.on('portletReady', function(event) {
	if ('_' + event.portletId + '_' == '<portlet:namespace/>') {
		
		<%--//Tags--%>
		var availableTags = [<c:forEach items="${userstags}" var="tag">"${tag}",</c:forEach>""];
		availableTags.pop();
		function split( val ) {
			return val.split( /,\s*/ );
		}
		function extractLast( term ) {
			return split( term ).pop();
		}
		jQuery_1_7_1("#<portlet:namespace/>tags").bind( "keydown", function( event ) {
			if ( event.keyCode === jQuery_1_7_1.ui.keyCode.TAB && jQuery_1_7_1( this ).data( "autocomplete" ).menu.active ) {
				event.preventDefault();
			}
		}).autocomplete({
			minLength: 0,
			source: function( request, response ) {
				response( jQuery_1_7_1.ui.autocomplete.filter(
					availableTags, extractLast( request.term ) ) );
			},
			focus: function() {
				return false;
			},
			select: function( event, ui ) {
				var terms = split( this.value );
				terms.pop();
				terms.push( ui.item.value );
				terms.push( "" );
				this.value = terms.join( ", " );
				return false;
			}
		});
		
		
		
		
		<%--//Handle SAVE Response--%>
        function saveHandleResponse<portlet:namespace/>(responseText, statusText, xhr, form) {
        	jQuery_1_7_1("#<portlet:namespace/>administration-container-mask").unmask();
        }  
		
		<%--//Form Options for Save Button --%>
        var optionsSave = {
             url : '${saveConfigurationURL}'
            ,type : 'POST'
            ,success : saveHandleResponse<portlet:namespace/>
        };                      
      
        <%--//Link Type Listener --%>
        jQuery_1_7_1("#<portlet:namespace/>linktype").change(function() {
        	if(jQuery_1_7_1("#<portlet:namespace/>linktype").val() == 3) {
        		jQuery_1_7_1("#<portlet:namespace/>customlinkcontainer").show();
        	} else {
        		jQuery_1_7_1("#<portlet:namespace/>customlinkcontainer").hide();
        	}
        	if(jQuery_1_7_1("#<portlet:namespace/>linktype").val() == 2) {
        		jQuery_1_7_1("#<portlet:namespace/>ipccontainer").show();
        	} else {
        		jQuery_1_7_1("#<portlet:namespace/>ipccontainer").hide();
        	}
        });
        
        <%--//Filter Type Listener --%>
        jQuery_1_7_1("#<portlet:namespace/>filtertype").change(function() {
        	if(jQuery_1_7_1("#<portlet:namespace/>filtertype").val() == 0) {
        		jQuery_1_7_1("#<portlet:namespace/>staticfilter").show();
        		jQuery_1_7_1("#<portlet:namespace/>dynamicfilter").hide();
        	} else {
        		jQuery_1_7_1("#<portlet:namespace/>staticfilter").hide();
        		jQuery_1_7_1("#<portlet:namespace/>dynamicfilter").show();
        	}
        });
        
        <%--//Configuration Form Button Listener --%>
        jQuery_1_7_1("#<portlet:namespace/>save-configuration").click(function() {            
           	jQuery_1_7_1("#<portlet:namespace/>administration-container-mask").mask('<fmt:message key="com.rcs.general.mask.loading.text"/>');
           	jQuery_1_7_1('#<portlet:namespace/>configurationform').ajaxSubmit(optionsSave);            
        });   
		
	}
});
</script>