package com.cafe24.choiyooq1.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cafe24.choiyooq1.domain.SystemCodeDTO;
import com.cafe24.choiyooq1.domain.SystemCodeDataDTO;
import com.cafe24.choiyooq1.service.KakaoAPI;
import com.cafe24.choiyooq1.service.TestSystemCodeService;

@Controller
public class Maincontroller {
	
	@Autowired private TestSystemCodeService testSystemCodService;
	@Autowired private KakaoAPI kakao;
	
	//카카오 로그아웃
	@RequestMapping(value = "/logout")
	public String logout(Model model,HttpSession session) {
		System.out.println("logout $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		String accessToken = (String)session.getAttribute("accessToken");
		kakao.logout(accessToken);
		session.invalidate();
		model.addAttribute("bigCategory", testSystemCodService.getBigCategory());
		return "index";
	}
	
	//카카오 로그인
	@RequestMapping(value = "/login")
	public String login(@RequestParam("code") String code
						,Model model
						,HttpSession session) {
		//로그인 성공시 받은 코드로 accessToken을 받아옵니다.
		String accessToken = kakao.getAccessToken(code);
		HashMap<String, Object> resultMap = kakao.getUserInfo(accessToken);
		
		String LoginId = (String) resultMap.get("nickName");
		String profileImage = (String) resultMap.get("profileImage");
		
		session.setAttribute("profileImage", profileImage);
		session.setAttribute("accessToken", accessToken);
		session.setAttribute("LoginId", LoginId);
		return "redirect:/getMainPage";
	}
	
	//메인화면
	@GetMapping("/getMainPage")
	public String getMainPage(Model model,HttpSession session) {

		String accessToken = (String)session.getAttribute("accessToken");
		String LoginId = (String)session.getAttribute("LoginId");
		String profileImage = (String)session.getAttribute("profileImage");
		System.out.println(accessToken);
		System.out.println(LoginId);
		System.out.println(profileImage);
		//상품 큰분류 리스트
		model.addAttribute("bigCategory", testSystemCodService.getBigCategory());
		return "bigCategory/mainpage";
	}
	//첫화면
	@GetMapping("/")
	public String getStarted() {
		return "index";
	}

	//[상품] 삭제
	@GetMapping("/deleteProduct")
	public String deleteProduct(@RequestParam(value = "category")String category
								,@RequestParam(value = "tableName")String tableName
								,@RequestParam(value = "codeName")String codeName
								,Model model) {
		testSystemCodService.deleteProduct(category, tableName, codeName);
		model.addAttribute("proList", testSystemCodService.getProductListForUpdate(category,tableName));
		model.addAttribute("tableName", tableName);
		model.addAttribute("category", category);
		return "middlecategory/updadteProduct";
	}
	//[상품] 수정
	@PostMapping("/updateProduct")
	public String updateProduct(SystemCodeDataDTO sDateDto,Model model) {
		String category = sDateDto.getPlant();
		String tableName = sDateDto.getTableName();
		testSystemCodService.updateProduct(sDateDto);
		model.addAttribute("proList", testSystemCodService.getProductListForUpdate(category,tableName));
		model.addAttribute("tableName", tableName);
		model.addAttribute("category", category);
		return "middlecategory/updadteProduct";
	}
	
	//[상품] 수정 화면으로.
	@GetMapping("/updateProduct")
	public String updateProduct(@RequestParam(value = "category")String category
								,@RequestParam(value = "tableName")String tableName
								,Model model) {
		
		model.addAttribute("proList", testSystemCodService.getProductListForUpdate(category,tableName));
		model.addAttribute("tableName", tableName);
		model.addAttribute("category", category);
		return "middlecategory/updadteProduct";
	}
	
	//[상품]검색
	@GetMapping("/searchProduct")
	public String searchProduct(@RequestParam(value = "searchVale")String sv
								,@RequestParam(value = "searchKey")String sk
								,@RequestParam(value = "tableName")String tableName
								,@RequestParam(value = "category")String category
								,@RequestParam(value = "svprice1")String svprice1
								,@RequestParam(value = "svprice2")String svprice2
								,Model model) {
		Map<String,Object> map = testSystemCodService.searchProduct(sk,sv,tableName,category,svprice1,svprice2);
		model.addAttribute("proList", map.get("resultList"));
		model.addAttribute("proAllCount", map.get("proAllCount"));
		model.addAttribute("totalPrice", map.get("totalPrice"));
		model.addAttribute("tableName", tableName);
		model.addAttribute("category", category);
		model.addAttribute("middlCa", testSystemCodService.getMiddleCategoty(category));
		return "middlecategory/detailOfProduct";
	}
	
	//[상품]출고
	@PostMapping("/outProduct")
	public String outProduct(SystemCodeDataDTO sDataDto,RedirectAttributes redirect,Model model) {
		testSystemCodService.outProductCount(sDataDto);
		String category = sDataDto.getPlant();
		String tableName = sDataDto.getTableName();
		redirect.addAttribute("category", category);
		redirect.addAttribute("tableName", tableName);
		model.addAttribute("middlCa", testSystemCodService.getMiddleCategoty(category));
		model.addAttribute("tableName", tableName);
		model.addAttribute("category", category);
		return "redirect:/getdetailOfProduct";
	}
	
	//[상품]입고
	@PostMapping("/insertProduct")
	public String enterProduct(SystemCodeDataDTO sDataDto,RedirectAttributes redirect,Model model) {
		System.out.println(sDataDto.toString());
		testSystemCodService.insertProducCount(sDataDto);
		String category = sDataDto.getPlant();
		String tableName = sDataDto.getTableName();
		redirect.addAttribute("category", category);
		redirect.addAttribute("tableName", tableName);
		model.addAttribute("middlCa", testSystemCodService.getMiddleCategoty(category));
		model.addAttribute("tableName", tableName);
		model.addAttribute("category", category);
		return "redirect:/getdetailOfProduct";
	}
	
	//[상품 리스트로]
	@GetMapping("/getdetailOfProduct")
	public String getdetailOfProduct(@RequestParam(value = "category")String category
									,@RequestParam(value = "tableName")String tableName
									,Model model) {
		Map<String,Object> map = testSystemCodService.getProductList(category, tableName);
		model.addAttribute("middlCa", testSystemCodService.getMiddleCategoty(category));
		model.addAttribute("proList", map.get("resultList"));
		model.addAttribute("proAllCount", map.get("proAllCount"));
		model.addAttribute("totalPrice", map.get("totalPrice"));
		model.addAttribute("tableName", tableName);
		model.addAttribute("category", category);
		return "middlecategory/detailOfProduct";
	}
	
	//[대분류] 수정
	@PostMapping("/updateMiddleCategory")
	public String updateMiddleCategory(SystemCodeDTO sCodeDto,Model model) {
		String category = sCodeDto.getPlant();
		testSystemCodService.updateMiddleCategory(sCodeDto);
		model.addAttribute("category", category);
		model.addAttribute("md", testSystemCodService.getMiddlecategory(category));
		return "bigCategory/updatebigcategory";
	}
	//[대분류] 삭제 체크
	@GetMapping("/deleteMiddleCategorychech")
	public String deleteMiddleCategorychech(@RequestParam(value = "category")String category
									,@RequestParam(value = "tableName")String tableName
									,Model model) {
		model.addAttribute("result", testSystemCodService.deleteMiddleCategorycheck(category, tableName));
		model.addAttribute("md", testSystemCodService.getMiddlecategory(category));
		model.addAttribute("category", category);
		model.addAttribute("tableName", tableName);
		return "bigCategory/updatebigcategory";
	}
	
	//[대분류] 마지막 중분류 삭제
	@GetMapping("/deleteMiddleCategory")
	public String deleteMiddleCategory(@RequestParam(value = "category")String category
										,@RequestParam(value = "tableName")String tableName
										,Model model) {
		testSystemCodService.deleteMiddleCategory(category, tableName);
		model.addAttribute("bigCategory", testSystemCodService.getBigCategory());
		return "index";
	}

	//[대분류 및 중분류 수정화면으로]
	@GetMapping("/updateBigcategory")
	public String updateBigcategory(@RequestParam(value = "category")String category,Model model) {
		model.addAttribute("md", testSystemCodService.getMiddlecategory(category));
		model.addAttribute("category", category);
		return "bigCategory/updatebigcategory";
	}
	
	//[소분류]상품등록!!!
	@PostMapping("/Insertproduct")
	public String insertProduct(SystemCodeDataDTO dataDto,RedirectAttributes redirect,Model model) {
		redirect.addAttribute("category", dataDto.getPlant());
		model.addAttribute("middlCa", testSystemCodService.getMiddleCategoty(dataDto.getPlant()));
		testSystemCodService.insertProduct(dataDto);
		return "redirect:/middlecategory";
	}
	
	//[상품]상품등록 화면으로
	@GetMapping("/Insertproduct")
	public String insertProduct(@RequestParam(value="category")String category,Model model) {
		model.addAttribute("middlCa", testSystemCodService.getInsertpage(category));
		model.addAttribute("category", category);
		return "middlecategory/Insertproduct";
	}
	
	//[대분류]상품분류 추가 화면으로
	@GetMapping("InsertBigCategoryPage")
	public String getInsertBigCategoryPage() {
		return "bigCategory/insertbigcategory";
	}
	//대분류 및 중분류 추가
	@PostMapping("InsertBigCategoryPage")
	public String InsertBigCategory(SystemCodeDTO sCodeDto,Model model) {
		testSystemCodService.insertBigCategorhy(sCodeDto);
		//상품 큰분류 리스트
		model.addAttribute("bigCategory", testSystemCodService.getBigCategory());
		return "bigCategory/mainpage";
	}
	//카테고리별 화면 
	@GetMapping("/middlecategory")
	public String getListByCategory(@RequestParam(value="category")String category,
									Model model) {
		model.addAttribute("middlCa", testSystemCodService.getMiddleCategoty(category));
		model.addAttribute("category", category);
		return "middlecategory/category";
	}
	
	
	//대분류 관리 페이지
	@GetMapping("/ListOfBigCategoty")
	public String ListOfBigCategoty(Model model) {
		model.addAttribute("categoryList", testSystemCodService.getBicCategoryInfo());
		return "bigCategory/listOfbigcategory";
				
	}
	

	

}