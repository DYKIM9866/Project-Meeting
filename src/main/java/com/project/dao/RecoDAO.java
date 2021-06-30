package com.project.dao;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import org.mybatis.spring.SqlSessionTemplate;

import com.project.dto.RecoCoDTO;
import com.project.dto.RecoDTO;
import com.project.parse.RecoDBSportsParser;
import com.project.parse.RecoDBStudyParser;
import com.project.parse.RecoDBTravelParser;

public class RecoDAO{

	private SqlSessionTemplate sessionTemplate;

	public void setSessionTemplate(SqlSessionTemplate sessionTemplate) {
		this.sessionTemplate = sessionTemplate;
	}
	
	//끝페이지 구하기
	public int getMaxNum(String subject,String searchKey,String searchValue) {
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("subject", subject);
		map.put("searchKey", searchKey);
		map.put("searchValue", searchValue);
		
		int maxNum = sessionTemplate.selectOne("com.recoMapper.getMaxNum",map);

		
		
		maxNum = (maxNum/100)+1;
		
		return maxNum;
	}

	
	//서브젝트별 리스트 가져오기
	public List<RecoDTO> getLists(String subject,String searchKey, String sort,
								  String searchValue, int pageNum,
								  double lat, double lon) {

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("subject", subject);
		map.put("searchKey", searchKey);
		map.put("sort", sort);
		map.put("searchValue", searchValue);
		map.put("pageNum",pageNum);
		map.put("lat", lat);
		map.put("lon", lon);
		
		
		List<RecoDTO> lists = sessionTemplate.selectList("com.recoMapper.getLists", map);
		
		return lists;
	}
	
	//데이터 하나 가져오기
	public RecoDTO getReadData(int recoNum){
		
		RecoDTO list = sessionTemplate.selectOne("com.recoMapper.getReadData", recoNum);
		
		return list;
	}
	
	//데이터 업데이트
	public void insertData() throws IOException, InterruptedException{
		RecoDBStudyParser.totalCountParser();
		RecoDBTravelParser.totalCountParser();
		RecoDBSportsParser.totalCountParser();
		
		for (int i = 1; i < (RecoDBStudyParser.totalCount / 100) + 2; i++) {
			for (RecoDTO data : RecoDBStudyParser.libParser(i)) {
				sessionTemplate.insert("com.recoMapper.insertData", data);

			}
		}

		for (int i = 1; i < (RecoDBTravelParser.totalCount / 100) + 2; i++) {
			for (RecoDTO data : RecoDBTravelParser.traParser(i)) {
				sessionTemplate.insert("com.recoMapper.insertData", data);

			}
		}

		for (int i = 1; i < (RecoDBSportsParser.totalCount / 100) + 2; i++) {
			for (RecoDTO data : RecoDBSportsParser.spoParser(i)) {
				sessionTemplate.insert("com.recoMapper.insertData", data);

			}
		}

	}
	
	//comment삽입
	public void insertComment(int recoNum,String userId,double star,String content) {
		
		
		
		RecoCoDTO dto = new RecoCoDTO();
		dto.setRecoNum(recoNum);
		dto.setUserId(userId);
		dto.setStar(star);
		dto.setContent(content);
		
		sessionTemplate.insert("com.recoMapper.insertComment", dto);
	}


	
	//comment 불러오기
	public List<RecoCoDTO> getComment(int recoNum){
		
		List<RecoCoDTO> commentLists = sessionTemplate.selectList("com.recoMapper.getComment", recoNum);
		return commentLists;
	}
	
	//인기증가또는 삽입
	public void increasePop(int recoNum) {
		
		sessionTemplate.update("com.recoMapper.increasePop", recoNum);
	}

	
	
	
	


}
