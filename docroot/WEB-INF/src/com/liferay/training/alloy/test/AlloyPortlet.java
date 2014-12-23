package com.liferay.training.alloy.test;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;

/**
 * Portlet implementation class AlloyPortlet
 */
public class AlloyPortlet extends MVCPortlet {

	public void uploadFormFile(ActionRequest actionRequest,
			ActionResponse actionResponse) throws IOException, PortletException, PortalException, SystemException {
		System.out.println("Processing form action");

		UploadPortletRequest uploadPortletRequest = PortalUtil
				.getUploadPortletRequest(actionRequest);
		File file = uploadPortletRequest.getFile("formFileUploader");
		System.out.println(file.getName());
		uploadAlloyFile(actionRequest, actionResponse);
	}

	public void uploadAlloyFile(ActionRequest actionRequest,
			ActionResponse actionResponse) throws IOException, PortletException, PortalException, SystemException {
		
		String cmd = ParamUtil.getString(actionRequest, "commandName");
		UploadPortletRequest uploadPortletRequest =	PortalUtil.getUploadPortletRequest(actionRequest);
		
		if(cmd.equals("addFile")) {
			
			InputStream inputStream = null;
			
			inputStream = uploadPortletRequest.getFileAsStream("formFileUploader");
			
			String sourceFileName = uploadPortletRequest.getFileName("formFileUploader");
			
			long size = uploadPortletRequest.getSize("formFileUploader");
			
			String mimeType = uploadPortletRequest.getContentType("formFileUploader");
			
			ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
			
			
			long folderId = ParamUtil.getInteger(actionRequest, "folderId");	
			
			long userId = themeDisplay.getUserId();
						
			ServiceContext serviceContext = ServiceContextFactory.getInstance(DLFileEntry.class.getName(), uploadPortletRequest);
			
			FileEntry fileEntry = DLAppLocalServiceUtil.addFileEntry(userId, themeDisplay.getScopeGroupId(), folderId, sourceFileName, mimeType, sourceFileName, "Test file", "No changes", inputStream, size, serviceContext);
			
			long groupId = fileEntry.getGroupId();
			
			AssetEntry assetEntry = AssetEntryLocalServiceUtil.getEntry(groupId, fileEntry.getUuid());
			
			long entryId = assetEntry.getClassPK();

			long[] categoryIds = { 10643L }; // hardcoded - added category through UI beforehand

			String[] tagNames = { "test" };

			AssetEntryLocalServiceUtil.updateEntry(assetEntry.getUserId(), assetEntry.getGroupId(), assetEntry.getClassName(), entryId, categoryIds, tagNames);
			
				//AssetEntryLocalServiceUtil.addAssetCategoryAssetEntry(categoryId, assetEntry);
			//AssetTag assetTag = AssetTagLocalServiceUtil.addTag(userId, "test", null, serviceContext);
			
//			AssetTagLocalServiceUtil.addAssetEntryAssetTag(fileEntry.getFileEntryId(), assetTag);
			
		}
	}

}
