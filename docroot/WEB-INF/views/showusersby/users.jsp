<%--//@author Prj.M@x <pablo.rendon@rotterdam-cs.com> --%>
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

<c:forEach items="${filteredUsers.blogger}" var="user">
	<div class="<portlet:namespace/>filtered-users">
		<liferay-ui:user-display userId="${user.userId}" url="${user.url}" displayStyle="${displaystyle}">
		    <span class="user-details" rel="${user.userId}" id="categoryauthorlink-${user.authorcategory}"><c:out value="${user.comment}" escapeXml="false" /></span>
		</liferay-ui:user-display>
	</div>
	<div style="clear: both;"></div>
</c:forEach>

<script type="text/javascript">		
	jQuery_1_7_1(function() {//'img[src*="/icons/back.gif"
		jQuery_1_7_1(".userd-detail-em-address").each(function() {
			var em = jQuery_1_7_1(this).text();
			var id = jQuery_1_7_1(this).attr("id");
			addEmail(em, id);
		});		
	});	
</script>