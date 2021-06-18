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
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.project.meeting.reco.controller.RecoController;
import com.project.meeting.reco.dao.RecoDAO;
import com.project.meeting.reco.dto.RecoDTO;

import java.awt.dnd.DropTargetListener;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;


public class RecoDBStudyParser {
	
	public static int totalCount;//������ totalCount
	
	
	/*
	 * public static void main(String[] args) throws IOException,
	 * InterruptedException { libParser(0); }
	 */
	 
	public static void totalCountParser() throws IOException, InterruptedException {
    	//������ API�� totalCount�� �����´�.
    	
        StringBuilder urlBuilder = new StringBuilder("http://api.data.go.kr/openapi/tn_pubr_public_lbrry_api"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=wx1FtCvq2AivHsuIAfn24wTlffKB2K7uVzmPcNxwKSbT2ZNKATW4WdEDX%2Fx7MPv4PTxrP3zUqPANPq%2Byqdz5tg%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + 1); /*������ ��ȣ*/
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
        
        totalCount = Integer.parseInt((((jObj1.get("totalCount")).toString()).replace("\"", "")));//������ ��ü ����
        
	}
	
    public static List<RecoDTO> libParser(int pageNo) throws IOException, InterruptedException {
    	//������ API�� �����͸� dto�� ��´�.
        StringBuilder urlBuilder = new StringBuilder("http://api.data.go.kr/openapi/tn_pubr_public_lbrry_api"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=wx1FtCvq2AivHsuIAfn24wTlffKB2K7uVzmPcNxwKSbT2ZNKATW4WdEDX%2Fx7MPv4PTxrP3zUqPANPq%2Byqdz5tg%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + pageNo); /*������ ��ȣ*/
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
        System.out.println(memberArray);
        List<RecoDTO> list = new ArrayList<RecoDTO>();
        
        for (int i = 0; i < memberArray.size(); i++) {
        	RecoDTO dto = new RecoDTO();
        	JsonObject object = (JsonObject) memberArray.get(i);
			
			dto.setRecoNum(((pageNo-1)*100)+i);
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
        	dto.setLat(((object.get("latitude")).toString()).replace("\"", ""));
        	dto.setLon(((object.get("longitude")).toString()).replace("\"", ""));
        	
        	list.add(dto);
        	

        }
        
        
        System.out.println(pageNo);
        return list;
        
        
		
    }
}
