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

public class RecoDBSportsParser {
	
	public static int totalCount;
	
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
	
    public static List<RecoDTO> spoParser(int pageNo) throws IOException, InterruptedException {
    	//관광지 API의 데이터를 dto에 담는다.
    	StringBuilder urlBuilder = new StringBuilder("http://www.kspo.or.kr/openapi/service/sportsNewFacilInfoService/getNewFacilInfoList"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=wx1FtCvq2AivHsuIAfn24wTlffKB2K7uVzmPcNxwKSbT2ZNKATW4WdEDX%2Fx7MPv4PTxrP3zUqPANPq%2Byqdz5tg%3D%3D"); /*공공데이터포털에서 받은 인증키*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("100", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + pageNo); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("_type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*한 페이지 결과 수*/

        
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
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
        jObj1 = (JsonObject) jObj1.get("items");
        JsonArray memberArray = (JsonArray) jObj1.get("item");
        
        List<RecoDTO> list = new ArrayList<RecoDTO>();
        
        
        	for (int i = 0; i < memberArray.size(); i++) {
            	RecoDTO dto = new RecoDTO();
            	JsonObject object = (JsonObject) memberArray.get(i);
    			
    			dto.setRecoNum(((pageNo-1)*100)+RecoDBStudyParser.totalCount+RecoDBTravelParser.totalCount+i);
    			dto.setSubject("sports");
    			dto.setKeyword(((object.get("ftypeNm")).toString()).replace("\"", ""));
    			dto.setTitle(((object.get("faciNm")).toString()).replace("\"", ""));
            	dto.setIntroduce("시설상태 : " + ((object.get("faciStat")).toString()).replace("\"", "")
            	+ "<br/>실내외 구분 : " + ((object.get("inoutGbn")).toString()).replace("\"", "")
            	+ "<br/>시설연락처 : " + ((object.get("faciTel")).toString()).replace("\"", ""));
            	dto.setContent("");
            	dto.setLocation("소재지도로명주소 : " + ((object.get("faciRoadAddr1")).toString()).replace("\"", ""));//소재지도로명주소 location
            	dto.setLat(((object.get("faciPointY")).toString()).replace("\"", "").replace("-",""));
            	dto.setLon(((object.get("faciPointX")).toString()).replace("\"", "").replace("-",""));
            	
            	list.add(dto);
            	
            	
            }
		
        
        
        
       
        return list;
    }
}
