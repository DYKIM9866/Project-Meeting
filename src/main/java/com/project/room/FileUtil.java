package com.project.room;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Component("fileUtil")
public class FileUtil {
	
	//������ ����� ��ġ
	//private static final String filePath = "D:\\sts-bundle\\Project-Meeting\\src\\main\\webapp\\resources\\upload\\";
	private static final String filePath = "D:\\sts-bundle\\work\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\Meeting\\resources\\upload\\";
	
	//���� ���ε�
	public List<Map<String,Object>> parseInsertFileInfo (RoomDTO dto,MultipartHttpServletRequest mpRequest) throws Exception {
		
		/*
		Iterator�� �����͵��� ����ü? ���� �÷������κ��� ������ ���� �� �ִ� �������̽��Դϴ�.
		List�� �迭�� ���������� �������� ������ ����������, Map���� Ŭ�������� ���������� ������ ���� �����ϴ�.
		Iterator�� �̿��Ͽ� Map�� �ִ� �����͵��� while���� �̿��Ͽ� ���������� �����մϴ�.
		*/
		
		Iterator<String> it = mpRequest.getFileNames();
		
		MultipartFile multipartFile = null;
		String originalFileName = null;
		String originalFileExtension = null;
		String storedFileName = null;
		
		List<Map<String,Object>> lists = new ArrayList<Map<String,Object>>();
		Map<String,Object> listsMap = null;
		
		//int roomNum = dto.getRoomNum();
		
		File file = new File(filePath);
		if(file.exists() == false) {
			file.mkdirs();
		}
		
		while(it.hasNext()) {
			
			multipartFile = mpRequest.getFile(it.next());
			
			if(multipartFile.isEmpty() == false) {
				
				originalFileName = multipartFile.getOriginalFilename();
				originalFileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
				storedFileName = getRandomString() + originalFileExtension;
				
				file = new File(filePath + storedFileName);
				multipartFile.transferTo(file);
				listsMap = new HashMap<String, Object>();
				listsMap.put("roomNum",dto.getRoomNum());
				listsMap.put("subject",dto.getSubject());
				listsMap.put("title",dto.getTitle());
				listsMap.put("keyword",dto.getKeyword());
				listsMap.put("introduce",dto.getIntroduce());
				listsMap.put("manager",dto.getManager());
				
				listsMap.put("originalFileName", originalFileName);
				listsMap.put("storedFileName", storedFileName);
				//listsMap.put("fileSize", multipartFile.getSize());
				
				lists.add(listsMap);
				
			}
			
		}
		
		return lists;
		
	}
	
	//���� ����
	public List<Map<String,Object>> parseUpdateFileInfo (RoomDTO dto,MultipartHttpServletRequest mpRequest) throws Exception {
		
		Iterator<String> it = mpRequest.getFileNames();
		
		MultipartFile multipartFile = null;
		String originalFileName = null;
		String originalFileExtension = null;
		String storedFileName = null;
		
		List<Map<String,Object>> lists = new ArrayList<Map<String,Object>>();
		Map<String,Object> listsMap = null;
		
		//���� ���� ����
		File deleteFile = new File(filePath + dto.getStoredFileName());
		if(deleteFile.exists()) {
			deleteFile.delete();
		}
		
		File file = new File(filePath);
		if(file.exists() == false) {
			file.mkdirs();
		}
		
		while(it.hasNext()) {
			
			multipartFile = mpRequest.getFile(it.next());
			
			if(multipartFile.isEmpty() == false) {
				
				originalFileName = multipartFile.getOriginalFilename();
				originalFileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
				storedFileName = getRandomString() + originalFileExtension;
				
				file = new File(filePath + storedFileName);
				multipartFile.transferTo(file);
				listsMap = new HashMap<String, Object>();
				listsMap.put("roomNum",dto.getRoomNum());
				listsMap.put("title",dto.getTitle());
				listsMap.put("keyword",dto.getKeyword());
				listsMap.put("introduce",dto.getIntroduce());
				
				listsMap.put("storedFileName", storedFileName);
				listsMap.put("originalFileName", originalFileName);
				
				/*
				if(keepStoredFileName!=null && keepOriginalFileName!=null) {
					listsMap.put("storedFileName", keepStoredFileName);
					listsMap.put("originalFileName", keepOriginalFileName);
				}else {
					listsMap.put("storedFileName", storedFileName);
					listsMap.put("originalFileName", originalFileName);
				}
				*/
				
				lists.add(listsMap);
				
			}
			
		}
		
		return lists;
		
	}
	
	//���� ����
	public void parseDeleteFileInfo (String storedFileName) {
		
		File file = new File(filePath + storedFileName);
		
		if(file.exists()) {
			file.delete();
		}
		
	}
	
	//32������ ������ ���ڿ�(��������)�� ���� ��ȯ���ִ� ���
	public static String getRandomString() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
}
