package com.project.roomInfo;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RoomInfoController {
	
	@Autowired
	@Qualifier("roomInfoDAO")
	RoomInfoDAO dao;
	
//	�� ����Ʈ ����
	@RequestMapping(value="/roomInfoMain.action", method= {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView roomInfo(HttpServletRequest request) throws Exception {
		
//		int start = 0;
//		int end = 0;
//		String subject = "";
//		String keyword = "";
//		
//		List<RoomInfoDTO> lists = dao.getRoomList(start, end, subject, keyword);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("RoomInfo/roomInfoMain");
		
//		mav.addObject("lists", lists);
		
		return mav;
	}
	
//	�� ����
	@RequestMapping(value="/room.action", method= {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView room(HttpServletRequest request) throws Exception {
		
		int roomNum = 1;
//		int roomNum = Integer.parseInt(request.getParameter("roomNum"));
//		String subject = request.getParameter("subject");
//		String keyword = request.getParameter("keyword");
		
		List<RoomInfoDTO> lists = dao.getBoardList(roomNum);
		
//		System.out.println(String.format("roomȣ�� : roomNum = %s, subject = %s, keyword = %s", roomNum, subject, keyword));
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("RoomInfo/room");
		mav.addObject("lists", lists);
		
		return mav;
	}
	
//	�� ���� - ���� �Խ���
	@RequestMapping(value="/notice.action", method= {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView notice(HttpServletRequest request) throws Exception {
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("RoomInfo/notice");
		
		return mav;
	}
	
//	�� ���� - ���� �Խ���
	@RequestMapping(value="/schedule.action", method= {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView schedule(HttpServletRequest request) throws Exception {
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("RoomInfo/schedule");
		
		return mav;
	}
	
//	�� ���� - ��ǥ �Խ���
	@RequestMapping(value="/vote.action", method= {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView vote(HttpServletRequest request) throws Exception {
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("RoomInfo/vote");
		
		return mav;
	}
	
	@RequestMapping(value="/created.action", method= {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView craeted(HttpServletRequest request) throws Exception {
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("RoomInfo/created");
		
		return mav;
	}
	
//	getRoomList �׽�Ʈ�ϱ� ���� ����
	@RequestMapping(value="/test.action", method= {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView test(HttpServletRequest request) throws Exception {
		
//		keyword�κп� �� ��
//		name = "*"�� ��� ��ü ������ �˻�
//		name = "1|2"�� ��� 1 Ȥ�� 2�� �� �� �˻�
//		name = "111|222"�� ��� 111 Ȥ�� 222�� �� �� �˻�
		String name = "1 2";
		
		List<TestDTO> lists = dao.getTest(name);
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("RoomInfo/test");
		mav.addObject("lists", lists);
		
		return mav;
	}
}