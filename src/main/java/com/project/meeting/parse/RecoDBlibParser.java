package com.project.meeting.parse;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.project.meeting.reco.dao.RecoDAO;
import com.project.meeting.reco.dto.RecoDTO;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;


public class RecoDBlibParser {
	
	
	
	/*
	 * public static void main(String[] args) throws IOException,
	 * InterruptedException { DBparser(1); }
	 */
	 
	 
	
    public static String naverImg(String title) throws IOException {
    	//���̹��̹��� �˻� API
    	String clientID = "35pKO_jU57WPSwxCblWm"; //���ø����̼� Ŭ���̾�Ʈ ���̵�"
        String clientSecret = "kkmJ_uImfj"; //���ø����̼� Ŭ���̾�Ʈ ��ũ����"
        
        String text = null;
        try {
            text = URLEncoder.encode(title, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("�˻��� ���ڵ� ����",e);
        }
        
        StringBuilder urlBuilder = new StringBuilder(("https://openapi.naver.com/v1/search/image?query=") + text); /*URL*/
        urlBuilder.append("&display=1");

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("X-Naver-Client-Id", clientID);
        conn.setRequestProperty("X-Naver-Client-Secret", clientSecret);
        conn.setRequestMethod("GET");
        
        
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream(),"UTF-8"));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        
        System.out.println(sb.toString());
        
        JsonParser Parser = new JsonParser();
        JsonObject jObj = (JsonObject) Parser.parse(sb.toString());
        JsonArray memberArray = (JsonArray) jObj.get("items");
        
        String result = "";
        
        if(jObj.get("total").getAsInt()!=0) {
        JsonObject object = (JsonObject) memberArray.get(0);
        System.out.println(object);
        RecoDTO dto = new RecoDTO();
        result =((object.get("thumbnail")).toString()).replace("\"", "");
        }
        
        return result;  
    }


	
    public static List<RecoDTO> DBparser(int pageNum) throws IOException, InterruptedException {
    	//������ API
        StringBuilder urlBuilder = new StringBuilder("http://api.data.go.kr/openapi/tn_pubr_public_lbrry_api"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=wx1FtCvq2AivHsuIAfn24wTlffKB2K7uVzmPcNxwKSbT2ZNKATW4WdEDX%2Fx7MPv4PTxrP3zUqPANPq%2Byqdz5tg%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + (pageNum-1)); /*������ ��ȣ*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("100", "UTF-8")); /*�� ������ ��� ��*/
        urlBuilder.append("&" + URLEncoder.encode("type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*XML/JSON ����*/

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
         
        //�Ľ�
        JsonParser Parser = new JsonParser();
        JsonObject jObj1 = (JsonObject) Parser.parse(sb.toString());
        jObj1 = (JsonObject) jObj1.get("response");
        jObj1 = (JsonObject) jObj1.get("body");
        JsonArray memberArray = (JsonArray) jObj1.get("items");
        
        List<RecoDTO> list = new ArrayList<RecoDTO>();
        for (int i = 0; i < memberArray.size(); i++) {
        	RecoDTO dto = new RecoDTO();
        	JsonObject object = (JsonObject) memberArray.get(i);
			
			dto.setRecoNum(i);
			dto.setSubject("study");
			dto.setKeyword("library");
        	dto.setTitle(((object.get("lbrryNm")).toString()).replace("\"", ""));//�������� title 
        	dto.setIntroduce("�õ��� : " + ((object.get("ctprvnNm")).toString()).replace("\"", "")//�õ��� introduce
        	+ "<br/>�ñ����� : " + ((object.get("signguNm")).toString()).replace("\"", "")//�ñ����� introduce
        	+ "<br/>������ ���� : " + ((object.get("lbrrySe")).toString()).replace("\"", ""));//������ ���� introduce
        	dto.setContent("�ް��� : " + ((object.get("closeDay")).toString()).replace("\"", "")//�ް��� content
        	+ "<br/>���Ͽ���۽ð� : " + ((object.get("weekdayOperOpenHhmm")).toString()).replace("\"", "")//���Ͽ���۽ð� content
        	+ "<br/>���Ͽ����ð� : " + ((object.get("weekdayOperColseHhmm")).toString()).replace("\"", "")//���Ͽ����ð� content
        	+ "<br/>����Ͽ���۽ð� : " + ((object.get("satOperOperOpenHhmm")).toString()).replace("\"", "")//����Ͽ���۽ð� content
        	+ "<br/>����Ͽ����ð� : " + ((object.get("satOperCloseHhmm")).toString()).replace("\"", "")//����Ͽ����ð� content
        	+ "<br/>�����Ͽ���۽ð� : " + ((object.get("holidayOperOpenHhmm")).toString()).replace("\"", "")//�����Ͽ���۽ð� content
        	+ "<br/>�����Ͽ����ð� : " + ((object.get("holidayCloseOpenHhmm")).toString()).replace("\"", "")//�����Ͽ����ð� content
        	+ "<br/>��������ȭ��ȣ : " + ((object.get("phoneNumber")).toString()).replace("\"", ""));////��������ȭ��ȣ content
        	dto.setLocation("���������θ��ּ� : " + ((object.get("rdnmadr")).toString()).replace("\"", ""));//���������θ��ּ� location
			dto.setImgUrl(naverImg(dto.getTitle()));  
        	
        	list.add(dto);
        	Thread.sleep(50);

        }
        
        return list;
		
    }
}
