package com.project.meeting.reco.controller;

/* Java ���� �ڵ� */

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.IOException;

public class ApiExplorer {
	public static void main(String[] args) throws IOException {
		StringBuilder urlBuilder = new StringBuilder(
				"http://api.data.go.kr/openapi/tn_pubr_public_lbrry_api"); /* URL */
		urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8")
				+ "=wx1FtCvq2AivHsuIAfn24wTlffKB2K7uVzmPcNxwKSbT2ZNKATW4WdEDX%2Fx7MPv4PTxrP3zUqPANPq%2Byqdz5tg%3D%3D"); /*
																														 * Service
																														 * Key
																														 */
		urlBuilder.append(
				"&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("0", "UTF-8")); /* ������ ��ȣ */
		urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "="
				+ URLEncoder.encode("100", "UTF-8")); /* �� ������ ��� �� */
		urlBuilder.append(
				"&" + URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /* XML/JSON ���� */
		/*
		 * urlBuilder.append("&" + URLEncoder.encode("LBRRY_NM","UTF-8") + "=" +
		 * URLEncoder.encode("�����ִ³�������������", "UTF-8")); �������� urlBuilder.append("&" +
		 * URLEncoder.encode("CTPRVN_NM","UTF-8") + "=" + URLEncoder.encode("����ϵ�",
		 * "UTF-8")); �õ��� urlBuilder.append("&" + URLEncoder.encode("SIGNGU_NM","UTF-8")
		 * + "=" + URLEncoder.encode("���ֽ�", "UTF-8")); �ñ����� urlBuilder.append("&" +
		 * URLEncoder.encode("LBRRY_SE","UTF-8") + "=" + URLEncoder.encode("����������",
		 * "UTF-8")); ���������� urlBuilder.append("&" +
		 * URLEncoder.encode("CLOSE_DAY","UTF-8") + "=" +
		 * URLEncoder.encode("�����Ͽ���, ����������", "UTF-8")); �ް���
		 */
		urlBuilder.append("&" + URLEncoder.encode("WEEKDAY_OPER_OPEN_HHMM", "UTF-8") + "="
				+ URLEncoder.encode("10:00", "UTF-8")); /* ���Ͽ���۽ð� */
		urlBuilder.append("&" + URLEncoder.encode("WEEKDAY_OPER_COLSE_HHMM", "UTF-8") + "="
				+ URLEncoder.encode("18:00", "UTF-8")); /* ���Ͽ����ð� */
		urlBuilder.append("&" + URLEncoder.encode("SAT_OPER_OPER_OPEN_HHMM", "UTF-8") + "="
				+ URLEncoder.encode("10:00", "UTF-8")); /* ����Ͽ���۽ð� */
		urlBuilder.append("&" + URLEncoder.encode("SAT_OPER_CLOSE_HHMM", "UTF-8") + "="
				+ URLEncoder.encode("15:00", "UTF-8")); /* ����Ͽ����ð� */
		urlBuilder.append("&" + URLEncoder.encode("HOLIDAY_OPER_OPEN_HHMM", "UTF-8") + "="
				+ URLEncoder.encode("00:00", "UTF-8")); /* �����Ͽ���۽ð� */
		urlBuilder.append("&" + URLEncoder.encode("HOLIDAY_CLOSE_OPEN_HHMM", "UTF-8") + "="
				+ URLEncoder.encode("00:00", "UTF-8")); /* �����Ͽ����ð� */
		urlBuilder.append(
				"&" + URLEncoder.encode("SEAT_CO", "UTF-8") + "=" + URLEncoder.encode("100", "UTF-8")); /* �����¼��� */
		urlBuilder.append(
				"&" + URLEncoder.encode("BOOK_CO", "UTF-8") + "=" + URLEncoder.encode("12574", "UTF-8")); /* �ڷ��(����) */
		urlBuilder.append("&" + URLEncoder.encode("PBLICTN_CO", "UTF-8") + "="
				+ URLEncoder.encode("0", "UTF-8")); /* �ڷ��(���Ӱ��๰) */
		urlBuilder.append("&" + URLEncoder.encode("NONE_BOOK_CO", "UTF-8") + "="
				+ URLEncoder.encode("0", "UTF-8")); /* �ڷ��(�񵵼�) */
		urlBuilder.append(
				"&" + URLEncoder.encode("LON_CO", "UTF-8") + "=" + URLEncoder.encode("5", "UTF-8")); /* ���Ⱑ�ɱǼ� */
		urlBuilder.append(
				"&" + URLEncoder.encode("LON_DAYCNT", "UTF-8") + "=" + URLEncoder.encode("14", "UTF-8")); /* ���Ⱑ���ϼ� */
		urlBuilder.append("&" + URLEncoder.encode("RDNMADR", "UTF-8") + "="
				+ URLEncoder.encode("����ϵ� ���ֽ� �ϻ걸 ���ͷ� 122-11", "UTF-8")); /* ���������θ��ּ� */
		urlBuilder.append("&" + URLEncoder.encode("OPER_INSTITUTION_NM", "UTF-8") + "="
				+ URLEncoder.encode("(��)������", "UTF-8")); /* ������ */
		urlBuilder.append("&" + URLEncoder.encode("PHONE_NUMBER", "UTF-8") + "="
				+ URLEncoder.encode("063-229-6511", "UTF-8")); /* ��������ȭ��ȣ */
		urlBuilder
				.append("&" + URLEncoder.encode("PLOT_AR", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /* �������� */
		urlBuilder.append(
				"&" + URLEncoder.encode("BULD_AR", "UTF-8") + "=" + URLEncoder.encode("347", "UTF-8")); /* �ǹ����� */
		urlBuilder.append(
				"&" + URLEncoder.encode("HOMEPAGE_URL", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /* Ȩ�������ּ� */
		urlBuilder.append(
				"&" + URLEncoder.encode("LATITUDE", "UTF-8") + "=" + URLEncoder.encode("35.8351264", "UTF-8")); /* ���� */
		urlBuilder.append("&" + URLEncoder.encode("LONGITUDE", "UTF-8") + "="
				+ URLEncoder.encode("127.1206937649", "UTF-8")); /* �浵 */
		urlBuilder.append("&" + URLEncoder.encode("REFERENCE_DATE", "UTF-8") + "="
				+ URLEncoder.encode("2020-08-31", "UTF-8")); /* �����ͱ������� */
		urlBuilder.append("&" + URLEncoder.encode("instt_code", "UTF-8") + "="
				+ URLEncoder.encode("4640000", "UTF-8")); /* ��������ڵ� */
		urlBuilder.append("&" + URLEncoder.encode("instt_nm", "UTF-8") + "="
				+ URLEncoder.encode("����ϵ� ���ֽ�", "UTF-8")); /* ������������ */
		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		System.out.println("Response code: " + conn.getResponseCode());
		BufferedReader rd;
		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
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
		System.out.println(sb.toString());
	}
}
