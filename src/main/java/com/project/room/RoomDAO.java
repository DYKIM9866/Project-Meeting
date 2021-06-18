package com.project.room;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;

public class RoomDAO {
	
private SqlSessionTemplate sessionTemplate;
	
	//������ ����
	public void setSessionTemplate(SqlSessionTemplate sessionTemplate) {
		
		this.sessionTemplate = sessionTemplate;
		
	}
	
	//��ü �� --------------------------------------------------------------
	//�Է�
	public void insertData(Map<String,Object> map) {
		
		sessionTemplate.insert("com.roomMapper.insertData", map);
		
	}
	
	/*
	//���� �Է�
	public void insertFile(Map<String,Object> map) {
		
		sessionTemplate.insert("com.roomMapper.insertFile", map);
		
	}
	
	//���� ���
	public List<Map<String,Object>> selectFileList(int roomNum) {
		
		List<Map<String,Object>> lists = sessionTemplate.selectList("com.roomMapper.selectFileList",roomNum);
		
		return lists;
		
	}
	*/
	
	//��ü ������ ����
	public int getDataCount(String searchKey,String searchValue) {
		
		int totalDataCount = 0;
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("searchKey", searchKey);
		params.put("searchValue", searchValue);
		
		totalDataCount = sessionTemplate.selectOne("com.roomMapper.getDataCount", params);
		
		return totalDataCount;
		
	}
	
	//ǥ���� ������ (rownum ����) ������
	public List<RoomDTO> getLists(int start,int end,String searchKey,String searchValue) {//����¡�� ��ȣ�� ���۰� ��
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("start", start);
		params.put("end", end);
		params.put("searchKey", searchKey);
		params.put("searchValue", searchValue);
		
		List<RoomDTO> lists = sessionTemplate.selectList("com.roomMapper.getLists", params);
			
		return lists;
			
	}
	
	//roomNum���� ��ȸ�� �� ���� ������
	public RoomDTO getReadData(int roomNum) {
		
		RoomDTO dto = sessionTemplate.selectOne("com.roomMapper.getReadData", roomNum);
		
		return dto;
		
	}
	
	//����
	public void updateData (Map<String,Object> map) {
		
		sessionTemplate.update("com.roomMapper.updateData", map);
		
	}
	
	//����
	public void deleteData(int roomNum) {
		
		sessionTemplate.delete("com.roomMapper.deleteData", roomNum);
		
	}
	
	
	//���� �� --------------------------------------------------------------
	public int travelDataCount(String searchKey,String searchValue) {
		
		int totalDataCount = 0;
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("searchKey", searchKey);
		params.put("searchValue", searchValue);
		
		totalDataCount = sessionTemplate.selectOne("com.roomMapper.travelDataCount", params);
		
		return totalDataCount;
		
	}
	
	public List<RoomDTO> travelGetLists(int start,int end,String searchKey,String searchValue) {
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("start", start);
		params.put("end", end);
		params.put("searchKey", searchKey);
		params.put("searchValue", searchValue);
		
		List<RoomDTO> lists = sessionTemplate.selectList("com.roomMapper.travelGetLists", params);
			
		return lists;
			
	}
	
	
	//���� �� --------------------------------------------------------------
	public int foodDataCount(String searchKey,String searchValue) {
		
		int totalDataCount = 0;
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("searchKey", searchKey);
		params.put("searchValue", searchValue);
		
		totalDataCount = sessionTemplate.selectOne("com.roomMapper.foodDataCount", params);
		
		return totalDataCount;
		
	}
	
	public List<RoomDTO> foodGetLists(int start,int end,String searchKey,String searchValue) {
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("start", start);
		params.put("end", end);
		params.put("searchKey", searchKey);
		params.put("searchValue", searchValue);
		
		List<RoomDTO> lists = sessionTemplate.selectList("com.roomMapper.foodGetLists", params);
			
		return lists;
			
	}
	
	
	//� �� --------------------------------------------------------------
	public int sportsDataCount(String searchKey,String searchValue) {
		
		int totalDataCount = 0;
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("searchKey", searchKey);
		params.put("searchValue", searchValue);
		
		totalDataCount = sessionTemplate.selectOne("com.roomMapper.sportsDataCount", params);
		
		return totalDataCount;
		
	}
	
	public List<RoomDTO> sportsGetLists(int start,int end,String searchKey,String searchValue) {
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("start", start);
		params.put("end", end);
		params.put("searchKey", searchKey);
		params.put("searchValue", searchValue);
		
		List<RoomDTO> lists = sessionTemplate.selectList("com.roomMapper.sportsGetLists", params);
			
		return lists;
			
	}
	
	
	//� �� --------------------------------------------------------------
	public int studyDataCount(String searchKey,String searchValue) {
		
		int totalDataCount = 0;
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("searchKey", searchKey);
		params.put("searchValue", searchValue);
		
		totalDataCount = sessionTemplate.selectOne("com.roomMapper.studyDataCount", params);
		
		return totalDataCount;
		
	}
	
	public List<RoomDTO> studyGetLists(int start,int end,String searchKey,String searchValue) {
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("start", start);
		params.put("end", end);
		params.put("searchKey", searchKey);
		params.put("searchValue", searchValue);
		
		List<RoomDTO> lists = sessionTemplate.selectList("com.roomMapper.studyGetLists", params);
			
		return lists;
			
	}

}
