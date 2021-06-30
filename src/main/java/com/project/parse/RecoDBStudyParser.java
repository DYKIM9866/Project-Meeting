package com.project.parse;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.project.controller.RecoController;
import com.project.dao.RecoDAO;
import com.project.dto.RecoDTO;

import java.awt.dnd.DropTargetListener;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;


public class RecoDBStudyParser {
	
	public static int totalCount;//도서관 totalCount
	
	 
	public static void totalCountParser() throws IOException, InterruptedException {
    	//도서관 API의 totalCount를 가져온다.
    	
        StringBuilder urlBuilder = new StringBuilder("http://api.data.go.kr/openapi/tn_pubr_public_lbrry_api"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=wx1FtCvq2AivHsuIAfn24wTlffKB2K7uVzmPcNxwKSbT2ZNKATW4WdEDX%2Fx7MPv4PTxrP3zUqPANPq%2Byqdz5tg%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + 1); /*페이지 번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("100", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*XML/JSON 여부*/

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
         
        //파싱
        JsonParser Parser = new JsonParser();
        JsonObject jObj1 = (JsonObject) Parser.parse(sb.toString());
        jObj1 = (JsonObject) jObj1.get("response");
        jObj1 = (JsonObject) jObj1.get("body");
        
        totalCount = Integer.parseInt((((jObj1.get("totalCount")).toString()).replace("\"", "")));//데이터 전체 갯수
        
	}
	
    public static List<RecoDTO> libParser(int pageNo) throws IOException, InterruptedException {
    	//도서관 API의 데이터를 dto에 담는다.
        StringBuilder urlBuilder = new StringBuilder("http://api.data.go.kr/openapi/tn_pubr_public_lbrry_api"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=wx1FtCvq2AivHsuIAfn24wTlffKB2K7uVzmPcNxwKSbT2ZNKATW4WdEDX%2Fx7MPv4PTxrP3zUqPANPq%2Byqdz5tg%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + pageNo); /*페이지 번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("100", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*XML/JSON 여부*/

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
         
        //파싱
        JsonParser Parser = new JsonParser();
        JsonObject jObj1 = (JsonObject) Parser.parse(sb.toString());
        jObj1 = (JsonObject) jObj1.get("response");
        jObj1 = (JsonObject) jObj1.get("body");
        JsonArray memberArray = (JsonArray) jObj1.get("items");
        
        List<RecoDTO> list = new ArrayList<RecoDTO>();
        
        for (int i = 0; i < memberArray.size(); i++) {
        	RecoDTO dto = new RecoDTO();
        	JsonObject object = (JsonObject) memberArray.get(i);
			
			dto.setRecoNum(((pageNo-1)*100)+i);
			dto.setSubject("study");
			dto.setKeyword(((object.get("lbrrySe")).toString()).replace("\"", ""));
        	dto.setTitle(((object.get("lbrryNm")).toString()).replace("\"", ""));//도서관명 title 
        	dto.setIntroduce("시도명 : " + ((object.get("ctprvnNm")).toString()).replace("\"", "")//시도명 introduce
        	+ "<br/>시군구명 : " + ((object.get("signguNm")).toString()).replace("\"", "")//시군구명 introduce
        	+ "<br/>도서관전화번호 : " + ((object.get("phoneNumber")).toString()).replace("\"", ""));////도서관전화번호 content
        	dto.setContent("휴관일 : " + ((object.get("closeDay")).toString()).replace("\"", "")//휴관일 content
        	+ "<br/>평일운영시작시간 : " + ((object.get("weekdayOperOpenHhmm")).toString()).replace("\"", "")//평일운영시작시간 content
        	+ "<br/>평일운영종료시간 : " + ((object.get("weekdayOperColseHhmm")).toString()).replace("\"", "")//평일운영종료시간 content
        	+ "<br/>토요일운영시작시각 : " + ((object.get("satOperOperOpenHhmm")).toString()).replace("\"", "")//토요일운영시작시각 content
        	+ "<br/>토요일운영종료시각 : " + ((object.get("satOperCloseHhmm")).toString()).replace("\"", "")//토요일운영종료시각 content
        	+ "<br/>공휴일운영시작시각 : " + ((object.get("holidayOperOpenHhmm")).toString()).replace("\"", "")//공휴일운영시작시각 content
        	+ "<br/>공휴일운영종료시각 : " + ((object.get("holidayCloseOpenHhmm")).toString()).replace("\"", ""));//공휴일운영종료시각 content
        	dto.setLocation("소재지도로명주소 : " + ((object.get("rdnmadr")).toString()).replace("\"", ""));//소재지도로명주소 location
        	dto.setLat(((object.get("latitude")).toString()).replace("\"", "").replace("-",""));
        	dto.setLon(((object.get("longitude")).toString()).replace("\"", "").replace("-",""));
        	
        	list.add(dto);
        	

        }
        
        
        
        return list;
        
        
		
    }
}
