package com.project.parse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.project.dto.RecoDTO;

public class RecoDBTravelParser {
	
	public static int totalCount;//관광지 totalCount
	
	public static void totalCountParser() throws IOException, InterruptedException {
    	//관광지 API의 totalCount를 가져온다.
    	
		StringBuilder urlBuilder = new StringBuilder("http://api.data.go.kr/openapi/tn_pubr_public_trrsrt_api"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=wx1FtCvq2AivHsuIAfn24wTlffKB2K7uVzmPcNxwKSbT2ZNKATW4WdEDX%2Fx7MPv4PTxrP3zUqPANPq%2Byqdz5tg%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*json요청*/

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
		
		  JsonParser Parser = new JsonParser(); JsonObject jObj1 = (JsonObject)
		  Parser.parse(sb.toString()); jObj1 = (JsonObject) jObj1.get("response");
		  jObj1 = (JsonObject) jObj1.get("body");
		  
		  totalCount =
		  Integer.parseInt((((jObj1.get("totalCount")).toString()).replace("\"",
		  "")));//데이터 전체 갯수
		  
		 
	}
	
    public static List<RecoDTO> traParser(int pageNo) throws IOException, InterruptedException {
    	//관광지 API의 데이터를 dto에 담는다.
        StringBuilder urlBuilder = new StringBuilder("http://api.data.go.kr/openapi/tn_pubr_public_trrsrt_api"); /*URL*/
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
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
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
    			
    			dto.setRecoNum(((pageNo-1)*100)+RecoDBStudyParser.totalCount+i);
    			dto.setSubject("travel");
    			dto.setKeyword(((object.get("trrsrtSe")).toString()).replace("\"", ""));
    			dto.setTitle(((object.get("trrsrtNm")).toString()).replace("\"", ""));//관광지명 title 
            	dto.setIntroduce("관광지소개<br/>" + ((object.get("trrsrtIntrcn")).toString()).replace("\"", "")//시도명 introduce
            	+ "<br/>관리기관전화번호 : " + ((object.get("phoneNumber")).toString()).replace("\"", ""));//시군구명 introduce
            	dto.setContent("공공편익시설정보 : " + ((object.get("cnvnncFclty")).toString()).replace("\"", "")//휴관일 content
            	+ "<br/>숙박시설정보 : " + ((object.get("stayngInfo")).toString()).replace("\"", "")//평일운영시작시간 content
            	+ "<br/>운동및오락시설정보 : " + ((object.get("mvmAmsmtFclty")).toString()).replace("\"", "")//평일운영종료시간 content
            	+ "<br/>휴양및문화시설정보 : " + ((object.get("recrtClturFclty")).toString()).replace("\"", "")//토요일운영시작시각 content
            	+ "<br/>접객시설정보 : " + ((object.get("hospitalityFclty")).toString()).replace("\"", "")//토요일운영종료시각 content
            	+ "<br/>지원시설정보 : " + ((object.get("sportFclty")).toString()).replace("\"", "")//공휴일운영시작시각 content
            	+ "<br/>수용인원수 : " + ((object.get("aceptncCo")).toString()).replace("\"", "")//공휴일운영종료시각 content
            	+ "<br/>주차가능수 : " + ((object.get("prkplceCo")).toString()).replace("\"", ""));////도서관전화번호 content
            	dto.setLocation("소재지도로명주소 : " + ((object.get("rdnmadr")).toString()).replace("\"", ""));//소재지도로명주소 location
            	dto.setLat(((object.get("latitude")).toString()).replace("\"", "").replace("-",""));
            	dto.setLon(((object.get("longitude")).toString()).replace("\"", "").replace("-",""));
            	
            	list.add(dto);
            	
            	
            }
		
        
        
        
        
        return list;
    }
}
