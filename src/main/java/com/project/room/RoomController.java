package com.project.room;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RoomController {
	
	@Autowired
	@Qualifier("roomDAO")
	RoomDAO dao;
	
	@Autowired
	@Qualifier("myUtil")
	MyUtil myUtil;
	
	@Autowired
	@Qualifier("fileUtil")
	FileUtil fileUtil;
	
	@RequestMapping(value = "/index.action")
	public ModelAndView index() throws Exception {
		
		//http://localhost:8080/meeting/index.action
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("room/index");
		
		return mav;
		
	}
	
	//��ü �� --------------------------------------------------------------
	//�� �����
	@RequestMapping(value = "/created.action")
	public ModelAndView created() throws Exception {
		
		//http://localhost:8080/meeting/created.action
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("room/created");
		
		return mav;
		
	}
	
	@RequestMapping(value = "/created_ok.action", method = {RequestMethod.GET,RequestMethod.POST})
	public String created_ok(RoomDTO dto,MultipartHttpServletRequest mpRequest) throws Exception {
		
		//dao.insertData(dto);
		
		List<Map<String, Object>> lists = fileUtil.parseInsertFileInfo(dto, mpRequest);
		
		int size = lists.size();
		
		for(int i=0;i<size;i++) {
			dao.insertData(lists.get(i));
		}
		
		return "redirect:/list.action";
		
	}
	
	//�� ����Ʈ
	@RequestMapping(value = "/list.action", method = {RequestMethod.GET,RequestMethod.POST})
	public String list(HttpServletRequest request) throws Exception {
		
		//http://localhost:8080/meeting/list.action
		
		String cp = request.getContextPath();
		
		String pageNum = request.getParameter("pageNum");
		
		int currentPage = 1;
		
		if(pageNum!=null) {
			currentPage = Integer.parseInt(pageNum);
		}
		
		String searchKey = request.getParameter("searchKey");
		String searchValue = request.getParameter("searchValue");
		
		if(searchValue==null) {
			searchKey = "subject";
			searchValue = "";
		}else {
			if(request.getMethod().equalsIgnoreCase("GET")) {
				searchValue = URLDecoder.decode(searchValue, "UTF-8");
			}
		}
		
		int dataCount = dao.getDataCount(searchKey, searchValue);
		
		int numPerPage = 5;
		int totalPage = myUtil.getPageCount(numPerPage, dataCount);
		
		if(currentPage>totalPage) {
			currentPage = totalPage;
		}
		
		int start = (currentPage-1)*numPerPage+1;
		int end = currentPage*numPerPage;
		
		List<RoomDTO> lists = dao.getLists(start, end, searchKey, searchValue);
		/*
		Iterator<RoomDTO> it = lists.iterator();
		 
		while(it.hasNext()) {
		 
		 	RoomDTO dto = it.next(); 
		 	
		 	int roomNum = dto.getRoomNum();
		 
		 	List<Map<String,Object>> fileList = dao.selectFileList(roomNum);
		 
		 	request.setAttribute("fileList", fileList);
		  
		}
		*/
		//List<Map<String,Object>> fileList = dao.selectFileList();
		
		//param ����� ����
		String param = "";
		
		if(!searchValue.equals("")) {
			param = "searchKey=" + searchKey;
			param+= "&searchValue=" + URLEncoder.encode(searchValue, "UTF-8");
		}
		
		//url ����� ����
		String listUrl = cp + "/list.action";
		
		if(!param.equals("")) {
			listUrl += "?" + param;
		}
		
		//����¡
		String pageIndexList = myUtil.pageIndexList(currentPage, totalPage, listUrl);
		
		//article ����� ����
		String articleUrl = cp + "/article.action?pageNum=" + currentPage;
		
		if(!param.equals("")) {
			articleUrl += "&" + param;
		}
		
		String imagePath = request.getSession().getServletContext().getRealPath("/resources/upload/");
		
		
		//�������� �������� �ѱ� ������
		request.setAttribute("lists", lists);
		request.setAttribute("pageIndexList", pageIndexList);
		request.setAttribute("dataCount", dataCount);
		request.setAttribute("articleUrl", articleUrl);
		//request.setAttribute("fileList", fileList);
		request.setAttribute("imagePath", imagePath);
		
		return "room/list";
		
	}
	
	//�� ����
	@RequestMapping(value = "/article.action", method = {RequestMethod.GET,RequestMethod.POST})
	public String article(HttpServletRequest request) throws Exception {
		
		String cp = request.getContextPath();
		
		int roomNum = Integer.parseInt(request.getParameter("roomNum"));
		String pageNum = request.getParameter("pageNum");
		String searchKey = request.getParameter("searchKey");
		String searchValue = request.getParameter("searchValue");
		
		if(searchValue!=null) {
			searchValue = URLDecoder.decode(searchValue, "UTF-8");
		}
		
		RoomDTO dto = dao.getReadData(roomNum);
		
		//���μ�
		int lineSu = dto.getIntroduce().split("\n").length;
		
		dto.setIntroduce(dto.getIntroduce().replaceAll("\n", "<br/>"));
		
		String param = "pageNum=" + pageNum;
		if(searchValue!=null) {
			param+= "&searchKey=" + searchKey;
			param+= "&searchValue=" + URLEncoder.encode(searchValue, "UTF-8");
		}
		
		request.setAttribute("dto", dto);
		request.setAttribute("params", param);
		request.setAttribute("lineSu", lineSu);
		request.setAttribute("pageNum", pageNum);
		
		return "room/article";
		
	}
	
	//������ �� �������� ����ϱ�.
	//���� ������ �ǳ� searchkey,searchvalue�ְ� �������� ���� �ȵ�.
	
	//������ ��Ҵ� ��ȿ��� �ϱ�.
	@RequestMapping(value = "/updated.action", method = {RequestMethod.GET,RequestMethod.POST})
	public String updated(HttpServletRequest request) throws Exception {
		
		String cp = request.getContextPath();
		
		//javascript:location.href='<%=cp%>/updated.action?roomNum=${dto.roomNum }&${params }';
		int roomNum = Integer.parseInt(request.getParameter("roomNum"));
		String pageNum = request.getParameter("pageNum");
		String searchKey = request.getParameter("searchKey");
		String searchValue = request.getParameter("searchValue");
				
		if(searchValue!=null) {
				
			if(request.getMethod().equalsIgnoreCase("get")) {
				searchValue = URLDecoder.decode(searchValue, "UTF-8");
			}
					
		}else {
			searchKey = "subject"; 
			searchValue = ""; 
		}
		
		RoomDTO dto = dao.getReadData(roomNum);
		
		if(dto==null) {
			return "redirect:/list.action";
		}
		
		String param = "pageNum=" + pageNum;
				
		if(!searchValue.equals("")) {
			param+= "&searchKey=" + searchKey;
			param+= "&searchValue=" + URLEncoder.encode(searchValue, "UTF-8");
		}
		
		request.setAttribute("dto", dto);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("params", param);
		request.setAttribute("searchKey", searchKey);
		request.setAttribute("searchValue", searchValue);
		
		return "room/updated";
		
	}
	
	@RequestMapping(value = "/updated_ok.action", method = {RequestMethod.GET,RequestMethod.POST})
	public String updated_ok(RoomDTO dto,HttpServletRequest request,MultipartHttpServletRequest mpRequest) throws Exception {
		
		String pageNum = request.getParameter("pageNum");
		String searchKey = request.getParameter("searchKey");
		String searchValue = request.getParameter("searchValue");
		
		List<Map<String, Object>> lists = fileUtil.parseUpdateFileInfo(dto, mpRequest);
		
		int size = lists.size();
		
		for(int i=0;i<size;i++) {
			dao.updateData(lists.get(i));
		}
		
		String param = "pageNum=" + pageNum;
		
		if(!searchValue.equals("")) {
			param += "&searchKey=" + searchKey;
			param += "&searchValue=" + URLEncoder.encode(searchValue, "UTF-8");
		}
		
		return "redirect:/list.action?" + param;
		
	}
	
	//����
	@RequestMapping(value = "/deleted.action", method = {RequestMethod.GET,RequestMethod.POST})
	public String deleted(HttpServletRequest request) throws Exception {
		
		//javascript:location.href='<%=cp%>/deleted.action?roomNum=${dto.roomNum }&${params }';
		int roomNum = Integer.parseInt(request.getParameter("roomNum"));
		String pageNum = request.getParameter("pageNum");
		String searchKey = request.getParameter("searchKey");
		String searchValue = request.getParameter("searchValue");
		
		RoomDTO dto = dao.getReadData(roomNum);
		
		fileUtil.parseDeleteFileInfo(dto.getStoredFileName());
		
		dao.deleteData(roomNum);
		
		String param = "pageNum=" + pageNum;
		
		if(searchValue!=null) {
			param += "&searchKey=" + searchKey;
			param += "&searchValue=" + searchValue;
		}
		
		return "redirect:/list.action?" + param;
		
	}
	
	
	
	//���� �� --------------------------------------------------------------
	@RequestMapping(value = "/travelCreated.action")
	public ModelAndView travelCreated() throws Exception {
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("room/travel/travelCreated");
		
		return mav;
		
	}
	
	@RequestMapping(value = "/travelCreated_ok.action", method = {RequestMethod.GET,RequestMethod.POST})
	public String travelCreated_ok(RoomDTO dto,MultipartHttpServletRequest mpRequest) throws Exception {
		
		List<Map<String, Object>> lists = fileUtil.parseInsertFileInfo(dto, mpRequest);
		
		int size = lists.size();
		
		for(int i=0;i<size;i++) {
			dao.insertData(lists.get(i));
		}
		
		return "redirect:/travelList.action";
		
	}
	
	@RequestMapping(value = "/travelList.action", method = {RequestMethod.GET,RequestMethod.POST})
	public String travelList(HttpServletRequest request) throws Exception {
		
		String cp = request.getContextPath();
		
		String pageNum = request.getParameter("pageNum");
		
		int currentPage = 1;
		
		if(pageNum!=null) {
			currentPage = Integer.parseInt(pageNum);
		}
		
		String searchKey = request.getParameter("searchKey");
		String searchValue = request.getParameter("searchValue");
		
		if(searchValue==null) {
			searchKey = "title";
			searchValue = "";
		}else {
			if(request.getMethod().equalsIgnoreCase("GET")) {
				searchValue = URLDecoder.decode(searchValue, "UTF-8");
			}
		}
		
		int dataCount = dao.travelDataCount(searchKey, searchValue);
		
		int numPerPage = 5;
		int totalPage = myUtil.getPageCount(numPerPage, dataCount);
		
		if(currentPage>totalPage) {
			currentPage = totalPage;
		}
		
		int start = (currentPage-1)*numPerPage+1;
		int end = currentPage*numPerPage;
		
		List<RoomDTO> lists = dao.travelGetLists(start, end, searchKey, searchValue);
		
		//param ����� ����
		String param = "";
		
		if(!searchValue.equals("")) {
			param = "searchKey=" + searchKey;
			param+= "&searchValue=" + URLEncoder.encode(searchValue, "UTF-8");
		}
		
		//url ����� ����
		String listUrl = cp + "/travelList.action";
		
		if(!param.equals("")) {
			listUrl += "?" + param;
		}
		
		//����¡
		String pageIndexList = myUtil.pageIndexList(currentPage, totalPage, listUrl);
		
		//article ����� ����
		String articleUrl = cp + "/travelArticle.action?pageNum=" + currentPage;
		
		if(!param.equals("")) {
			articleUrl += "&" + param;
		}
		
		String imagePath = request.getSession().getServletContext().getRealPath("/resources/upload/");
		
		
		//�������� �������� �ѱ� ������
		request.setAttribute("lists", lists);
		request.setAttribute("pageIndexList", pageIndexList);
		request.setAttribute("dataCount", dataCount);
		request.setAttribute("articleUrl", articleUrl);
		request.setAttribute("imagePath", imagePath);
		
		return "room/travel/travelList";
		
	}
	
	@RequestMapping(value = "/travelArticle.action", method = {RequestMethod.GET,RequestMethod.POST})
	public String travelArticle(HttpServletRequest request) throws Exception {
		
		String cp = request.getContextPath();
		
		int roomNum = Integer.parseInt(request.getParameter("roomNum"));
		String pageNum = request.getParameter("pageNum");
		String searchKey = request.getParameter("searchKey");
		String searchValue = request.getParameter("searchValue");
		
		if(searchValue!=null) {
			searchValue = URLDecoder.decode(searchValue, "UTF-8");
		}
		
		RoomDTO dto = dao.getReadData(roomNum);
		
		//���μ�
		int lineSu = dto.getIntroduce().split("\n").length;
		
		dto.setIntroduce(dto.getIntroduce().replaceAll("\n", "<br/>"));
		
		String param = "pageNum=" + pageNum;
		if(searchValue!=null) {
			param+= "&searchKey=" + searchKey;
			param+= "&searchValue=" + URLEncoder.encode(searchValue, "UTF-8");
		}
		
		request.setAttribute("dto", dto);
		request.setAttribute("params", param);
		request.setAttribute("lineSu", lineSu);
		request.setAttribute("pageNum", pageNum);
		
		return "room/travel/travelArticle";
		
	}
	
	
	
	//���� �� --------------------------------------------------------------
	@RequestMapping(value = "/foodCreated.action")
	public ModelAndView foodCreated() throws Exception {
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("room/food/foodCreated");
		
		return mav;
		
	}
	
	@RequestMapping(value = "/foodCreated_ok.action", method = {RequestMethod.GET,RequestMethod.POST})
	public String foodCreated_ok(RoomDTO dto,MultipartHttpServletRequest mpRequest) throws Exception {
		
		List<Map<String, Object>> lists = fileUtil.parseInsertFileInfo(dto, mpRequest);
		
		int size = lists.size();
		
		for(int i=0;i<size;i++) {
			dao.insertData(lists.get(i));
		}
		
		return "redirect:/foodList.action";
		
	}
	
	@RequestMapping(value = "/foodList.action", method = {RequestMethod.GET,RequestMethod.POST})
	public String foodList(HttpServletRequest request) throws Exception {
		
		String cp = request.getContextPath();
		
		String pageNum = request.getParameter("pageNum");
		
		int currentPage = 1;
		
		if(pageNum!=null) {
			currentPage = Integer.parseInt(pageNum);
		}
		
		String searchKey = request.getParameter("searchKey");
		String searchValue = request.getParameter("searchValue");
		
		if(searchValue==null) {
			searchKey = "title";
			searchValue = "";
		}else {
			if(request.getMethod().equalsIgnoreCase("GET")) {
				searchValue = URLDecoder.decode(searchValue, "UTF-8");
			}
		}
		
		int dataCount = dao.foodDataCount(searchKey, searchValue);
		
		int numPerPage = 5;
		int totalPage = myUtil.getPageCount(numPerPage, dataCount);
		
		if(currentPage>totalPage) {
			currentPage = totalPage;
		}
		
		int start = (currentPage-1)*numPerPage+1;
		int end = currentPage*numPerPage;
		
		List<RoomDTO> lists = dao.foodGetLists(start, end, searchKey, searchValue);
		
		//param ����� ����
		String param = "";
		
		if(!searchValue.equals("")) {
			param = "searchKey=" + searchKey;
			param+= "&searchValue=" + URLEncoder.encode(searchValue, "UTF-8");
		}
		
		//url ����� ����
		String listUrl = cp + "/foodList.action";
		
		if(!param.equals("")) {
			listUrl += "?" + param;
		}
		
		//����¡
		String pageIndexList = myUtil.pageIndexList(currentPage, totalPage, listUrl);
		
		//article ����� ����
		String articleUrl = cp + "/foodArticle.action?pageNum=" + currentPage;
		
		if(!param.equals("")) {
			articleUrl += "&" + param;
		}
		
		String imagePath = request.getSession().getServletContext().getRealPath("/resources/upload/");
		
		
		//�������� �������� �ѱ� ������
		request.setAttribute("lists", lists);
		request.setAttribute("pageIndexList", pageIndexList);
		request.setAttribute("dataCount", dataCount);
		request.setAttribute("articleUrl", articleUrl);
		request.setAttribute("imagePath", imagePath);
		
		return "room/food/foodList";
		
	}
	
	@RequestMapping(value = "/foodArticle.action", method = {RequestMethod.GET,RequestMethod.POST})
	public String foodArticle(HttpServletRequest request) throws Exception {
		
		String cp = request.getContextPath();
		
		int roomNum = Integer.parseInt(request.getParameter("roomNum"));
		String pageNum = request.getParameter("pageNum");
		String searchKey = request.getParameter("searchKey");
		String searchValue = request.getParameter("searchValue");
		
		if(searchValue!=null) {
			searchValue = URLDecoder.decode(searchValue, "UTF-8");
		}
		
		RoomDTO dto = dao.getReadData(roomNum);
		
		//���μ�
		int lineSu = dto.getIntroduce().split("\n").length;
		
		dto.setIntroduce(dto.getIntroduce().replaceAll("\n", "<br/>"));
		
		String param = "pageNum=" + pageNum;
		if(searchValue!=null) {
			param+= "&searchKey=" + searchKey;
			param+= "&searchValue=" + URLEncoder.encode(searchValue, "UTF-8");
		}
		
		request.setAttribute("dto", dto);
		request.setAttribute("params", param);
		request.setAttribute("lineSu", lineSu);
		request.setAttribute("pageNum", pageNum);
		
		return "room/food/foodArticle";
		
	}
	
	
	
	//� �� --------------------------------------------------------------
	@RequestMapping(value = "/sportsCreated.action")
	public ModelAndView sportsCreated() throws Exception {
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("room/sports/sportsCreated");
		
		return mav;
		
	}
	
	@RequestMapping(value = "/sportsCreated_ok.action", method = {RequestMethod.GET,RequestMethod.POST})
	public String sportsCreated_ok(RoomDTO dto,MultipartHttpServletRequest mpRequest) throws Exception {
		
		List<Map<String, Object>> lists = fileUtil.parseInsertFileInfo(dto, mpRequest);
		
		int size = lists.size();
		
		for(int i=0;i<size;i++) {
			dao.insertData(lists.get(i));
		}
		
		return "redirect:/sportsList.action";
		
	}
	
	@RequestMapping(value = "/sportsList.action", method = {RequestMethod.GET,RequestMethod.POST})
	public String sportsList(HttpServletRequest request) throws Exception {
		
		String cp = request.getContextPath();
		
		String pageNum = request.getParameter("pageNum");
		
		int currentPage = 1;
		
		if(pageNum!=null) {
			currentPage = Integer.parseInt(pageNum);
		}
		
		String searchKey = request.getParameter("searchKey");
		String searchValue = request.getParameter("searchValue");
		
		if(searchValue==null) {
			searchKey = "title";
			searchValue = "";
		}else {
			if(request.getMethod().equalsIgnoreCase("GET")) {
				searchValue = URLDecoder.decode(searchValue, "UTF-8");
			}
		}
		
		int dataCount = dao.sportsDataCount(searchKey, searchValue);
		
		int numPerPage = 5;
		int totalPage = myUtil.getPageCount(numPerPage, dataCount);
		
		if(currentPage>totalPage) {
			currentPage = totalPage;
		}
		
		int start = (currentPage-1)*numPerPage+1;
		int end = currentPage*numPerPage;
		
		List<RoomDTO> lists = dao.sportsGetLists(start, end, searchKey, searchValue);
		
		//param ����� ����
		String param = "";
		
		if(!searchValue.equals("")) {
			param = "searchKey=" + searchKey;
			param+= "&searchValue=" + URLEncoder.encode(searchValue, "UTF-8");
		}
		
		//url ����� ����
		String listUrl = cp + "/sportsList.action";
		
		if(!param.equals("")) {
			listUrl += "?" + param;
		}
		
		//����¡
		String pageIndexList = myUtil.pageIndexList(currentPage, totalPage, listUrl);
		
		//article ����� ����
		String articleUrl = cp + "/sportsArticle.action?pageNum=" + currentPage;
		
		if(!param.equals("")) {
			articleUrl += "&" + param;
		}
		
		String imagePath = request.getSession().getServletContext().getRealPath("/resources/upload/");
		
		
		//�������� �������� �ѱ� ������
		request.setAttribute("lists", lists);
		request.setAttribute("pageIndexList", pageIndexList);
		request.setAttribute("dataCount", dataCount);
		request.setAttribute("articleUrl", articleUrl);
		request.setAttribute("imagePath", imagePath);
		
		return "room/sports/sportsList";
		
	}
	
	@RequestMapping(value = "/sportsArticle.action", method = {RequestMethod.GET,RequestMethod.POST})
	public String sportsArticle(HttpServletRequest request) throws Exception {
		
		String cp = request.getContextPath();
		
		int roomNum = Integer.parseInt(request.getParameter("roomNum"));
		String pageNum = request.getParameter("pageNum");
		String searchKey = request.getParameter("searchKey");
		String searchValue = request.getParameter("searchValue");
		
		if(searchValue!=null) {
			searchValue = URLDecoder.decode(searchValue, "UTF-8");
		}
		
		RoomDTO dto = dao.getReadData(roomNum);
		
		//���μ�
		int lineSu = dto.getIntroduce().split("\n").length;
		
		dto.setIntroduce(dto.getIntroduce().replaceAll("\n", "<br/>"));
		
		String param = "pageNum=" + pageNum;
		if(searchValue!=null) {
			param+= "&searchKey=" + searchKey;
			param+= "&searchValue=" + URLEncoder.encode(searchValue, "UTF-8");
		}
		
		request.setAttribute("dto", dto);
		request.setAttribute("params", param);
		request.setAttribute("lineSu", lineSu);
		request.setAttribute("pageNum", pageNum);
		
		return "room/sports/sportsArticle";
		
	}
	
	
	
	//���� �� --------------------------------------------------------------
	@RequestMapping(value = "/studyCreated.action")
	public ModelAndView studyCreated() throws Exception {
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("room/study/studyCreated");
		
		return mav;
		
	}
	
	@RequestMapping(value = "/studyCreated_ok.action", method = {RequestMethod.GET,RequestMethod.POST})
	public String studyCreated_ok(RoomDTO dto,MultipartHttpServletRequest mpRequest) throws Exception {
		
		List<Map<String, Object>> lists = fileUtil.parseInsertFileInfo(dto, mpRequest);
		
		int size = lists.size();
		
		for(int i=0;i<size;i++) {
			dao.insertData(lists.get(i));
		}
		
		return "redirect:/studyList.action";
		
	}
	
	@RequestMapping(value = "/studyList.action", method = {RequestMethod.GET,RequestMethod.POST})
	public String studyList(HttpServletRequest request) throws Exception {
		
		String cp = request.getContextPath();
		
		String pageNum = request.getParameter("pageNum");
		
		int currentPage = 1;
		
		if(pageNum!=null) {
			currentPage = Integer.parseInt(pageNum);
		}
		
		String searchKey = request.getParameter("searchKey");
		String searchValue = request.getParameter("searchValue");
		
		if(searchValue==null) {
			searchKey = "title";
			searchValue = "";
		}else {
			if(request.getMethod().equalsIgnoreCase("GET")) {
				searchValue = URLDecoder.decode(searchValue, "UTF-8");
			}
		}
		
		int dataCount = dao.studyDataCount(searchKey, searchValue);
		
		int numPerPage = 5;
		int totalPage = myUtil.getPageCount(numPerPage, dataCount);
		
		if(currentPage>totalPage) {
			currentPage = totalPage;
		}
		
		int start = (currentPage-1)*numPerPage+1;
		int end = currentPage*numPerPage;
		
		List<RoomDTO> lists = dao.studyGetLists(start, end, searchKey, searchValue);
		
		//param ����� ����
		String param = "";
		
		if(!searchValue.equals("")) {
			param = "searchKey=" + searchKey;
			param+= "&searchValue=" + URLEncoder.encode(searchValue, "UTF-8");
		}
		
		//url ����� ����
		String listUrl = cp + "/studyList.action";
		
		if(!param.equals("")) {
			listUrl += "?" + param;
		}
		
		//����¡
		String pageIndexList = myUtil.pageIndexList(currentPage, totalPage, listUrl);
		
		//article ����� ����
		String articleUrl = cp + "/studyArticle.action?pageNum=" + currentPage;
		
		if(!param.equals("")) {
			articleUrl += "&" + param;
		}
		
		String imagePath = request.getSession().getServletContext().getRealPath("/resources/upload/");
		
		
		//�������� �������� �ѱ� ������
		request.setAttribute("lists", lists);
		request.setAttribute("pageIndexList", pageIndexList);
		request.setAttribute("dataCount", dataCount);
		request.setAttribute("articleUrl", articleUrl);
		request.setAttribute("imagePath", imagePath);
		
		return "room/study/studyList";
		
	}
	
	@RequestMapping(value = "/studyArticle.action", method = {RequestMethod.GET,RequestMethod.POST})
	public String studyArticle(HttpServletRequest request) throws Exception {
		
		String cp = request.getContextPath();
		
		int roomNum = Integer.parseInt(request.getParameter("roomNum"));
		String pageNum = request.getParameter("pageNum");
		String searchKey = request.getParameter("searchKey");
		String searchValue = request.getParameter("searchValue");
		
		if(searchValue!=null) {
			searchValue = URLDecoder.decode(searchValue, "UTF-8");
		}
		
		RoomDTO dto = dao.getReadData(roomNum);
		
		//���μ�
		int lineSu = dto.getIntroduce().split("\n").length;
		
		dto.setIntroduce(dto.getIntroduce().replaceAll("\n", "<br/>"));
		
		String param = "pageNum=" + pageNum;
		if(searchValue!=null) {
			param+= "&searchKey=" + searchKey;
			param+= "&searchValue=" + URLEncoder.encode(searchValue, "UTF-8");
		}
		
		request.setAttribute("dto", dto);
		request.setAttribute("params", param);
		request.setAttribute("lineSu", lineSu);
		request.setAttribute("pageNum", pageNum);
		
		return "room/study/studyArticle";
		
	}

}
