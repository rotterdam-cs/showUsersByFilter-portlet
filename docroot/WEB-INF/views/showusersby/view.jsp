<%--//@author Prj.M@x <pablo.rendon@rotterdam-cs.com> --%>
<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8" import="javax.portlet.PortletURL" %>
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
<portlet:resourceURL var="getFilteredUsersURL" id="getFilteredUsers" />

<div class="filtered-users-container" id="<portlet:namespace/>filtered-users-container">
	<c:if test="${filtertype == '0'}">
		<%@include file="users.jsp" %>
	</c:if>
</div>

<script type="text/javascript">
	<%--//Listener to Show users by dynamic filter --%>    
	<c:if test="${filtertype == '1'}">
		Liferay.on('showFilteredUsers',	function(event) {
			var userId = "";
			var group = "";
			var role = "";
			var jobtitle = "";
			var tags = "";			
			if (event.usersId !== undefined) {
				userId = event.usersId.toString();
			}
			if (event.group !== undefined) {
				group = event.group;
			}
			if (event.role !== undefined) {
				role = event.role;
			}
			if (event.jobtitle !== undefined) {
				jobtitle = event.jobtitle;
			}
			if (event.tags !== undefined) {
				tags = event.tags.toString();
			}
			jQuery_1_7_1("#<portlet:namespace/>filtered-users-container").load("${getFilteredUsersURL}",{
            	 "usersid" : userId
            	,"group" : group
            	,"role" : role
            	,"jobtitle" : jobtitle
            	,"tags" : tags
		    });
		});		    		
		    
	</c:if>
	
	<%--//Sender Function If the link type is IPC (Inter Portlet Communication) --%> 
	<c:if test="${linktype == '2'}">
		function sendIPC<portlet:namespace/>(userId) {
		    var senderPortletId = '<portlet:namespace/>';
		    Liferay.fire('filteredUsersMessage', {
		    	 senderPortletId: senderPortletId
		    	,userId: userId
		   	});
		    console.log('IPC From: ' + senderPortletId + ' UserId: ' + userId);
		}
	</c:if>
	
	Liferay.on('portletReady', function(event) {
		if ('_' + event.portletId + '_' == '<portlet:namespace/>') {		
			
			<%--//If the link type is IPC (Inter Portlet Communication) --%> 
			<c:if test="${linktype == '2'}">
				jQuery_1_7_1(document).on("click", ".<portlet:namespace/>filtered-users", function(e) {
			    	e.preventDefault();
			    	var userId = jQuery_1_7_1(this).find(".user-details span").attr("rel");
			    	sendIPC<portlet:namespace/>(userId);
			    });
			</c:if>
			<%--//If the link type is Filter Blog or Asset--%> 
			<c:if test="${linktype == '4'}">
				jQuery_1_7_1(document).on("click", ".<portlet:namespace/>filtered-users", function(e) {
			    	e.preventDefault();
			    	var categorytagname = jQuery_1_7_1(this).find(".user-details span").attr("id");
			    	var id = categorytagname.replace("categoryauthorlink","categoryauthor");
			    	var categorylink = jQuery_1_7_1("#" + id).attr("href");
			    	if (categorylink != null && categorylink != "undefined"){
			    		window.location = categorylink;
			    	}
			    });
			</c:if>
		}
	});
</script>
