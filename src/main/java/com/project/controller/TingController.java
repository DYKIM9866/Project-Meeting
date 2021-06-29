package com.project.controller;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.project.dao.RegisterDAO;
import com.project.dao.RoomDAO;
import com.project.dao.TingDAO;
import com.project.dto.RoomDTO;
import com.project.dto.TingDTO;
import com.project.dto.UserDTO;
import com.project.dto.UserInfo;
import com.project.util.PageUtil;

@Controller
public class TingController {
	
	@Autowired
	@Qualifier("tingDAO")
	TingDAO dao;
	
	@Autowired
	RegisterDAO dao2;
	
	@Autowired
	@Qualifier("roomDAO")
	RoomDAO dao3;
	
	@Autowired
	@Qualifier("pageUtil")
	PageUtil pageUtil;
	
	@RequestMapping(value = "/tcreated.action")
	public String tcreated(HttpServletRequest request) throws Exception {
		
		//http://localhost:8080/meeting/tcreated.action
		
		int roomNum = Integer.parseInt(request.getParameter("roomNum"));
		
		//로그인 확인
		HttpSession session = request.getSession();
		UserInfo info = (UserInfo) session.getAttribute("userInfo");
		
		if(info == null) {
			
			return "login/login";
			
		}
		
		UserDTO dto = new UserDTO();
		
		dto = dao2.getUserInfo(info.getUserId());
		
		request.setAttribute("roomNum", roomNum);
		request.setAttribute("userId", dto.getUserId());
		request.setAttribute("name", dto.getName());
		request.setAttribute("email", dto.getEmail());
		request.setAttribute("ustoredFileName", dto.getUstoredFileName());
		
		return "ting/tcreated";
		
	}
	
	@RequestMapping(value = "/tcreated_ok.action", method = {RequestMethod.GET,RequestMethod.POST})
	public String tcreated_ok(TingDTO dto,HttpServletRequest request) throws Exception {
		
		int roomNum = Integer.parseInt(request.getParameter("roomNum"));
		
		dao.insertTingData(dto);
		
		return "redirect:/tmain.action?roomNum=" + roomNum;
		
	}
	
	@RequestMapping(value = "/tmain.action", method = {RequestMethod.GET,RequestMethod.POST})
	public String tlist(HttpServletRequest request) throws Exception {
		
		String cp = request.getContextPath();
		
		int roomNum = Integer.parseInt(request.getParameter("roomNum"));
		String pageNum = request.getParameter("pageNum");
		
		RoomDTO dto = new RoomDTO();
		dto = dao3.getReadData(roomNum);
		
		dto.setIntroduce(dto.getIntroduce().replaceAll("\n","<br/>"));
		
		//로그인 확인
		HttpSession session = request.getSession();
		UserInfo info = (UserInfo) session.getAttribute("userInfo");
		
		if(info == null) {
			
			return "login/login";
			
		}
		
		//유저정보 가져오기
		UserDTO dto1 = new UserDTO();
		
		dto1 = dao2.getUserInfo(info.getUserId());
		
		
		
		int currentPage = 1;
		
		if(pageNum!=null) {
			currentPage = Integer.parseInt(pageNum);
		}
		
		String searchKey = request.getParameter("searchKey");
		String searchValue = request.getParameter("searchValue");
		
		if(searchValue==null) {
			searchKey = "userId";
			searchValue = "";
		}else {
			if(request.getMethod().equalsIgnoreCase("GET")) {
				searchValue = URLDecoder.decode(searchValue, "UTF-8");
			}
		}
		
		int dataCount = dao.tingDataCount(searchKey, searchValue);
		
		int numPerPage = 5;
		int totalPage = pageUtil.getPageCount(numPerPage, dataCount);
		
		if(currentPage>totalPage) {
			currentPage = totalPage;
		}
		
		int start = (currentPage-1)*numPerPage+1;
		int end = currentPage*numPerPage;
		
		List<TingDTO> lists = dao.getTingLists(start, end, searchKey, searchValue);
		
		//param 사용자 정의
		String param = "";
		
		if(!searchValue.equals("")) {
			param = "searchKey=" + searchKey;
			param+= "&searchValue=" + URLEncoder.encode(searchValue, "UTF-8");
		}
		
		//url 사용자 정의
		String listUrl = cp + "/tmain.action?roomNum" + roomNum;
		
		if(!param.equals("")) {
			listUrl += "&" + param;
		}
		
		//페이징
		String pageIndexList = pageUtil.pageIndexList(currentPage, totalPage, listUrl);
		
		//포워딩할 페이지에 넘길 데이터
		request.setAttribute("subject", dto.getSubject());
		request.setAttribute("title", dto.getTitle());
		request.setAttribute("introduce", dto.getIntroduce());
		request.setAttribute("manager", dto.getManager());
		
		request.setAttribute("userId", dto1.getUserId());
		request.setAttribute("name", dto1.getName());
		
		request.setAttribute("roomNum", roomNum);
		request.setAttribute("lists", lists);
		request.setAttribute("pageIndexList", pageIndexList);
		request.setAttribute("dataCount", dataCount);
		request.setAttribute("pageNum", currentPage);
		
		return "ting/tmain";
		
	}
	
	//작성자만 수정 가능하게 하기.
	@RequestMapping(value = "/tupdated.action", method = {RequestMethod.GET,RequestMethod.POST})
	public String tupdated(HttpServletRequest request) throws Exception {
		
		//http://localhost:8080/meeting/tcreated.action
		
		int roomNum = Integer.parseInt(request.getParameter("roomNum"));
		
		//로그인 확인
		HttpSession session = request.getSession();
		UserInfo info = (UserInfo) session.getAttribute("userInfo");
		
		if(info == null) {
			
			return "login/login";
			
		}
		
		int tingNum = Integer.parseInt(request.getParameter("tingNum"));
		String pageNum = request.getParameter("pageNum");
		String searchKey = request.getParameter("searchKey");
		String searchValue = request.getParameter("searchValue");
				
		if(searchValue!=null) {
				
			if(request.getMethod().equalsIgnoreCase("get")) {
				searchValue = URLDecoder.decode(searchValue, "UTF-8");
			}
					
		}else {
			searchKey = "userId"; 
			searchValue = ""; 
		}
		
		TingDTO dto = dao.tingReadData(tingNum);
		
		if(dto==null) {
			return "redirect:/tmain.action";
		}
		
		String param = "pageNum=" + pageNum;
		
		if(!searchValue.equals("")) {
			param+= "&searchKey=" + searchKey;
			param+= "&searchValue=" + URLEncoder.encode(searchValue, "UTF-8");
		}
		
		request.setAttribute("roomNum", roomNum);
		request.setAttribute("dto", dto);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("params", param);
		request.setAttribute("searchKey", searchKey);
		request.setAttribute("searchValue", searchValue);
		
		return "ting/tupdated";
		
	}
	
	@RequestMapping(value = "/tupdated_ok.action", method = {RequestMethod.GET,RequestMethod.POST})
	public String tupdated_ok(TingDTO dto,HttpServletRequest request) throws Exception {
		
		int roomNum = Integer.parseInt(request.getParameter("roomNum"));
		String pageNum = request.getParameter("pageNum");
		String searchKey = request.getParameter("searchKey");
		String searchValue = request.getParameter("searchValue");
		
		System.out.println(dto.getContent());
		System.out.println(dto.getTitle());
		System.out.println(dto.getTingNum());
		
		dao.updateTingData(dto);
		/*
		String param = "&pageNum=" + pageNum;
		
		if(!searchValue.equals("")) {
			param += "&searchKey=" + searchKey;
			param += "&searchValue=" + URLEncoder.encode(searchValue, "UTF-8");
		}
		*/
		return "redirect:/tmain.action?roomNum=" + roomNum; //+ param
		
	}
	
	@RequestMapping(value = "/tdeleted.action", method = {RequestMethod.GET,RequestMethod.POST})
	public String tdeleted(HttpServletRequest request) throws Exception {
		
		int roomNum = Integer.parseInt(request.getParameter("roomNum"));
		int tingNum = Integer.parseInt(request.getParameter("tingNum"));
		String pageNum = request.getParameter("pageNum");
		String searchKey = request.getParameter("searchKey");
		String searchValue = request.getParameter("searchValue");
		
		dao.deleteTingData(tingNum);
		
		String param = "&pageNum=" + pageNum;
		
		if(searchValue!=null) {
			param += "&searchKey=" + searchKey;
			param += "&searchValue=" + searchValue;
		}
		
		return "redirect:/tmain.action?roomNum=" + roomNum + param;
		
	}
	
}