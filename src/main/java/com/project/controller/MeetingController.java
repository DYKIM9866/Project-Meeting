package com.project.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.project.dao.RegisterDAO;
import com.project.dao.RoomDAO;
import com.project.dto.RoomDTO;
import com.project.dto.UserDTO;
import com.project.dto.UserInfo;
import com.project.dto.msgDTO;
import com.project.util.RoomFileUtil;
import com.project.util.UserFileUtil;

@Controller
public class MeetingController {

	@Autowired
	RegisterDAO dao;

	@Autowired
	RoomDAO roomDao;

	@Autowired
	JavaMailSender mailSender;

	@Autowired
	@Qualifier("userFileUtil")
	UserFileUtil userFileUtil;
	
	@RequestMapping(value = "/main.action", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView main(HttpServletRequest req) throws Exception {

		
		HttpSession session = req.getSession();
		UserInfo info = (UserInfo) session.getAttribute("userInfo");

		//로그인 확인
		if (info == null) {

			ModelAndView mav = new ModelAndView();
			mav.setViewName("/login/login");

			return mav;
		}

		ModelAndView mav = new ModelAndView();
		mav.setViewName("main");

		
		

		return mav;

	}

	@RequestMapping(value = "/register.action", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView register() throws Exception {

		ModelAndView mav = new ModelAndView();
		mav.setViewName("/register/Register");

		return mav;
	}

	@RequestMapping(value = "/register_ok.action", method = RequestMethod.POST)
	public String register_ok(UserDTO dto, HttpServletRequest req, MultipartHttpServletRequest mpRequest)
			throws Exception {

		// 값들이 존재하는 값들이 아닌지 구현
		if (dao.checkId(dto.getUserId()) != 0) {
			req.setAttribute("existId", "아이디가 이미 존재합니다.");
			return "/register/Register";
		}
		if (dao.checkEmail(dto.getEmail()) != 0) {
			req.setAttribute("existEmail", "이메일이 이미 존재합니다.");
			return "/register/Register";
		}

		dao.insertData(dto);
		
		return "redirect:/main.action";

	}

	@RequestMapping(value = "/login.action", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView login() throws Exception {

		ModelAndView mav = new ModelAndView();
		mav.setViewName("/login/login");
		return mav;
	}

	@RequestMapping(value = "/logout.action", method = { RequestMethod.POST, RequestMethod.GET })
	public String logout(HttpServletRequest req) throws Exception {

		HttpSession session = req.getSession();
		session.removeAttribute("userInfo");

		return "redirect:/login.action";
	}


	@RequestMapping(value = "/login_ok.action", method = RequestMethod.POST)
	public String login_ok(String userId, String userPwd, String email, String rememberBtn, HttpServletRequest req)
			throws Exception {
		
		//공백 거르기
		if(userId.equals("")) {
			req.setAttribute("noExistInfo", "공백이 입력 될 수 없습니다.");
			return "/login/login";
		}
		if(userPwd.equals("")) {
			req.setAttribute("noExistInfo", "공백이 입력 될 수 없습니다.");
			return "/login/login";
		}

		// 아이디 or 이메일 존재하는지 있다면 비밀번호 맞는지 확인하는 거 구현
		if (dao.checkId(userId) == 0 && dao.checkEmail(email) == 0) {
			req.setAttribute("noExistInfo", "아이디/이메일이나 비밀번호가 틀렸습니다.");
			return "/login/login";
		}

		if (!dao.checkPwd(userId).equals(userPwd)) {
			req.setAttribute("noExistInfo", "아이디/이메일이나 비밀번호가 틀렸습니다.");
			return "/login/login";
		}

		UserDTO dto = new UserDTO();

		dto = dao.getUserInfo(userId);

		UserInfo info = new UserInfo();

		info.setUserId(dto.getUserId());
		info.setUserName(dto.getName());
		info.setUstoredFileName(dto.getUstoredFileName());
		
		HttpSession session = req.getSession();

		session.setAttribute("userInfo", info);

		session.setMaxInactiveInterval(60 * 30); // 30분

		return "redirect:/main.action";

	}

	@RequestMapping(value = "/forgotPwd.action", method = { RequestMethod.GET, RequestMethod.POST })
	public String forgotPwd(String email, String wow, HttpServletRequest req) throws Exception {

		try {
			if (wow.equals("1")) {
				req.setAttribute("resetPwd", "resetPwd");
			}

		} catch (Exception e) {

		}

		req.setAttribute("email", email);

		return "/login/forgotPwd";
	}

	@RequestMapping(value = "/forgotPwd_ok.action", method = { RequestMethod.GET, RequestMethod.POST })
	public String forgotPwd_ok(String email, HttpServletRequest req) throws Exception {

		if (dao.checkEmail(email) == 0) {
			req.setAttribute("noExistEmail", "등록된 이메일이 없습니다.");
			return "/login/forgotPwd";
		} else {
			return "redirect:/sendEmail.action?email=" + email;
		}

	}

	@RequestMapping(value = "/sendEmail.action", method = { RequestMethod.GET, RequestMethod.POST })
	public String sendEmail(String email, String wow, HttpServletRequest req) throws Exception {

		System.out.println(req.getServerPort());

		try {
			MimeMessage mail = mailSender.createMimeMessage();
			MimeMessageHelper mailHelper = new MimeMessageHelper(mail, true, "UTF-8");

			mailHelper.setFrom("Meeting");
			mailHelper.setTo(email);
			mailHelper.setSubject("Meeting비밀번호 초기화 메일입니다.");
			mailHelper.setText(
					"http://localhost:" + req.getServerPort() + "/meeting/forgotPwd.action?email=" + email + "&wow=1",
					true);

			mailSender.send(mail);

		} catch (Exception e) {
			System.out.println(e.toString());
		}

		req.setAttribute("sendedEmail", "등록된 이메일주소로 메일을 보냈습니다. 확인해 주세요. <br> 이 창은 종료하셔도 좋습니다.");

		return "/login/forgotPwd";
	}

	@RequestMapping(value = "/resetPwd_ok.action", method = { RequestMethod.POST, RequestMethod.GET })
	public String resetPwd(String email, String userPwd, HttpServletRequest req) throws Exception {

		dao.resetPwd(email, userPwd);

		return "/login/login";
	}

	@RequestMapping(value = "/myPage.action", method = { RequestMethod.GET, RequestMethod.POST })
	public String mypage(HttpServletRequest request) throws Exception {

		// 로그인 확인
		HttpSession session = request.getSession();
		UserInfo info = (UserInfo) session.getAttribute("userInfo");

		if (info == null) {
			return "redirect:/login.action";
		}

		// 내메세지 가져오기
		List<msgDTO> msgList = roomDao.getMsgList(info.getUserId());
		List<RoomDTO> manageList = dao.getManageList(info.getUserId());
		List<msgDTO> requestList = dao.getRequestList(info.getUserId());
		List<RoomDTO> participateList = dao.getParticipateList(info.getUserId());
		
		String cp = request.getContextPath();

		UserDTO dto = dao.getUserInfo(info.getUserId());

		request.setAttribute("dto", dto);
		request.setAttribute("msgList", msgList);
		request.setAttribute("manageList", manageList);
		request.setAttribute("requestList", requestList);
		request.setAttribute("participateList", participateList);

		return "register/myPage";

	}
	@RequestMapping(value = "/alterationProfileImg.action", method = { RequestMethod.POST })
	public @ResponseBody String alterationProfileImg(MultipartHttpServletRequest mpRequest) 
			throws Exception {
		
		HttpSession session = mpRequest.getSession();
		UserInfo info = (UserInfo) session.getAttribute("userInfo");
		
		//파일 올리기
		Map<String, String> map = userFileUtil.changeProfile(mpRequest);
		UserDTO dto = dao.getUserInfo(info.getUserId());
		
		if(dto.getUstoredFileName() != "basic.png" && !dto.getUstoredFileName().equals("basic.png")) {
			userFileUtil.parseDeleteFileInfo(dto.getUstoredFileName());
		}
		
		map.put("userId", info.getUserId());
		dao.updateProfileImg(map);
		dto = dao.getUserInfo(info.getUserId());
		
		session.removeAttribute("userInfo");
		
		info.setUstoredFileName(dto.getUstoredFileName());
		
		session.setAttribute("userInfo", info);

		session.setMaxInactiveInterval(60 * 30);
		
		return "success";
	}
	
	@RequestMapping(value = "/updateBasic.action", method = { RequestMethod.POST })
	public @ResponseBody String updateBasic(HttpServletRequest request) 
			throws Exception {
		
		HttpSession session = request.getSession();
		UserInfo info = (UserInfo) session.getAttribute("userInfo");
	
		UserDTO dto = dao.getUserInfo(info.getUserId());
		
		if(dto.getUstoredFileName() != "basic.png" && !dto.getUstoredFileName().equals("basic.png")) {
			userFileUtil.parseDeleteFileInfo(dto.getUstoredFileName());
		}
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("ustoredFileName", "basic.png");
        map.put("uoriginalFileName", "basic.png");
        map.put("userId", info.getUserId());
		dao.updateProfileImg(map);
		
		dto = dao.getUserInfo(info.getUserId());
		
		session.removeAttribute("userInfo");
		
		info.setUstoredFileName(dto.getUstoredFileName());
		
		session.setAttribute("userInfo", info);

		session.setMaxInactiveInterval(60 * 30);	
		
		return "success";
	}
	

	@RequestMapping(value = "/userUpdated.action", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String userUpdated(HttpServletRequest request,String userId,String userPwd,String email,String tel) 
			throws Exception {

		// 로그인 확인
		HttpSession session = request.getSession();
		UserInfo info = (UserInfo) session.getAttribute("userInfo");

		if (info == null) {

			return "redirect:/login.action";

		}
		
		UserDTO dto = dao.getUserInfo(userId);
		UserDTO inputDTO = new UserDTO();
		
		inputDTO.setUserId(userId);
		inputDTO.setUserPwd(userPwd);
		if(userPwd.equals("")) 
			inputDTO.setUserPwd(dto.getUserPwd());
		inputDTO.setEmail(email);
		if(dto.getEmail() != email) 
			inputDTO.setRight(0);
		else inputDTO.setRight(dto.getRight());
		inputDTO.setTel(tel);
		
		dao.updateUserData(inputDTO);

		return "success";

	}


	@RequestMapping(value = "/userDeleted.action", method = { RequestMethod.POST })
	public @ResponseBody String userDeleted(HttpServletRequest request, String userPwd) throws Exception {

		// 로그인 확인
		HttpSession session = request.getSession();
		UserInfo info = (UserInfo) session.getAttribute("userInfo");

		if (info == null) {
			return "redirect:/login.action";
		}
		
		UserDTO dto = dao.getUserInfo(info.getUserId());
		
		if (!dto.getUserPwd().equals(userPwd)){
			return "false";
		}
		
		if(!dto.getUstoredFileName().equals("basic.png")) { 
			userFileUtil.parseDeleteFileInfo(dto.getUstoredFileName());
		}

		dao.deleteUserData(dto.getUserId());
		session.removeAttribute("userInfo");

		return "success";

	}
	@RequestMapping(value = "/certification.action", method = { RequestMethod.GET })
	public String certification(HttpServletRequest request,@RequestParam("email") String email) throws Exception {
		
		HttpSession session = request.getSession();
		UserInfo info = (UserInfo) session.getAttribute("userInfo");
		
		String randomStr = "";
		

		try {
			MimeMessage mail = mailSender.createMimeMessage();
			MimeMessageHelper mailHelper = new MimeMessageHelper(mail, true, "UTF-8");
			randomStr = UserFileUtil.getRandomString();
			
			mailHelper.setFrom("Meeting");
			mailHelper.setTo(email);
			mailHelper.setSubject("이메일 인증 메일입니다. 아래의 문자를 정확히 입력하세요");
			mailHelper.setText(randomStr);

			mailSender.send(mail);

		} catch (Exception e) {
			System.out.println(e.toString());
		}

		request.setAttribute("sendedEmail", "등록된 이메일주소로 메일을 보냈습니다. 확인해 주세요.");
		request.setAttribute("randomStr", randomStr);
		
		
		return "register/certification";
	}
	
	@RequestMapping(value = "/certification_ok.action", method = { RequestMethod.POST})
	public @ResponseBody String certification_ok(HttpServletRequest request) throws Exception {
		
		HttpSession session = request.getSession();
		UserInfo info = (UserInfo) session.getAttribute("userInfo");
		
		UserDTO dto = dao.getUserInfo(info.getUserId());
		
		dto.setRight(1);
		
		dao.updateUserData(dto);
		
		
		return "success";
	}

}
