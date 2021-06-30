package com.project.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.dao.RecoDAO;
import com.project.dto.RecoCoDTO;
import com.project.dto.RecoDTO;
import com.project.dto.UserInfo;
import com.project.parse.RecoDBStudyParser;

@Controller
public class RecoController {
	
	@Autowired
	@Qualifier("recoDAO")
	RecoDAO dao;
	
	
	//서브젝트별 리스트 불러오기
	@RequestMapping(value = "/reco", method = RequestMethod.GET)
	public String recoStudy(Model model,
							@RequestParam(value = "searchKey", required = false, defaultValue = "title") String searchKey,
							@RequestParam(value = "searchValue", required = false) String searchValue,
							@RequestParam(value = "subject", required = false) String subject,
							@RequestParam(value = "sort", required = false, defaultValue = "distance") String sort,
							@RequestParam(value = "lat", required = false, defaultValue = "37.5642135") double lat,
							@RequestParam(value = "lon", required = false, defaultValue = "127.0016985") double lon,
							@RequestParam(value = "pageNum", required = false, defaultValue = "1"
							) int pageNum
							)
									throws IOException, InterruptedException {
		
		int maxNum = dao.getMaxNum(subject,searchKey,searchValue);
		List<RecoDTO> lists = dao.getLists(subject,searchKey,sort,searchValue,pageNum,lat,lon);

		model.addAttribute("searchKey",searchKey);
		model.addAttribute("searchValue",searchValue);
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("maxNum", maxNum);
		model.addAttribute("subject", subject);
		model.addAttribute("lists", lists);
		

		return "/reco/reco";
	}
	
	
	//자료 업데이트
	@RequestMapping(value = "/reco_update", method = RequestMethod.GET)
	public String recoUpdate() throws IOException, InterruptedException {
		dao.insertData();

		
		
		return "redirect:/reco";
	}
	
	//하나의 데이터를 불러와서 그 정보와 지도를 호출
	@RequestMapping(value = "/reco_showMap", method = RequestMethod.GET)
	public String showMap(Model model,HttpServletRequest req,
						  @RequestParam(value ="recoNum", required = false) int recoNum){
		
		RecoDTO list = dao.getReadData(recoNum);
		List<RecoCoDTO> commentLists = dao.getComment(recoNum);
		
		HttpSession session = req.getSession();
		UserInfo info = (UserInfo) session.getAttribute("userInfo");
		
		model.addAttribute("recoNum",recoNum);
		model.addAttribute("list", list);
		model.addAttribute("commentLists", commentLists);
		req.setAttribute("info", info);
		
		return "/reco/showMap";

	}
	
	//덧글 입력
	@RequestMapping(value = "/reco_recoComment", method = RequestMethod.GET)
	public String recoComment(Model model,
							  @RequestParam(value = "recoNum", required = true) int recoNum,
							  @RequestParam(value = "userId", required = true) String userId,
							  @RequestParam(value = "star", required = true) double star,
							  @RequestParam(value = "content", required = false) String content) {
		
		dao.insertComment(recoNum,userId,star,content);
		
		return "redirect:/reco_showMap?recoNum=" + recoNum;
		
	}
	
	
	
}
