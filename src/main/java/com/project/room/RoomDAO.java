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
	
	//�Է�
	public void insertData(RoomDTO dto) {
		
		sessionTemplate.insert("com.roomMapper.insertData", dto);
		
	}
	
	//���� �Է�
	public void insertFile(Map<String,Object> map) {
		
		sessionTemplate.insert("com.roomMapper.insertFile", map);
		
	}
	
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
	
	public RoomDTO getReadData(int num) {
		
		RoomDTO dto = sessionTemplate.selectOne("com.roomMapper.getReadData", num);
		
		return dto;
		
	}

}
