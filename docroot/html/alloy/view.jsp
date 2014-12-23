<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib uri="http://alloy.liferay.com/tld/aui" prefix="aui"%>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>

<%@ taglib uri="http://liferay.com/tld/theme" prefix="theme"%>

<%@ page import="com.liferay.portal.kernel.util.StringUtil"%>
<%@ page import="com.liferay.portal.kernel.util.PrefsPropsUtil"%>
<%@page import="com.liferay.portal.kernel.util.PropsUtil"%>
<%@page import="com.liferay.portal.kernel.util.PropsKeys"%>
<%@page import="com.liferay.portal.kernel.util.StringPool"%>
<%@taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet"%>
<%@page import="com.liferay.portal.kernel.repository.model.FileEntry"%>
<%@page import="com.liferay.portal.kernel.bean.BeanParamUtil"%>
<%@page import="com.liferay.portal.kernel.language.LanguageUtil"%>

<theme:defineObjects />


<portlet:defineObjects />

<%
	FileEntry fileEntry = (FileEntry) request
			.getAttribute("DOCUMENT_LIBRARY_FILE_ENTRY");
	long folderId = BeanParamUtil.getLong(fileEntry, request,
			"folderId");
%>


<liferay-portlet:actionURL doAsUserId="<%=user.getUserId()%>"
		windowState="pop_up" name="uploadAlloyFile" var="uploadFileURL">
	<portlet:param name="commandName" value="addFile" />
	<portlet:param name="folderId" value="<%=String.valueOf(folderId)%>" />
</liferay-portlet:actionURL>


<div class="lfr-dynamic-uploader">
	<div class="lfr-upload-container" id="<portlet:namespace />fileUpload"></div>
<aui:form action="<%=uploadFileURL%>" enctype="multipart/form-data">
	<aui:input name="formFileUploader" type="file"></aui:input>
	<!-- Look at http://drewblessing.com/blog/-/blogs/34509 for more options -->
	<aui:button name="submit" type="submit" />
</aui:form>
</div>
