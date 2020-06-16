package com.cafe24.choiyooq1.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cafe24.choiyooq1.domain.SystemCodeDTO;
import com.cafe24.choiyooq1.domain.SystemCodeDataDTO;
import com.cafe24.choiyooq1.mapper.TestSystemCodeMapper;
 
@Service
@Transactional
public class TestSystemCodeService {
	
	@Autowired private TestSystemCodeMapper testSystemCodeMapper;
	
	//상품 삭제
	public void deleteProduct(String category,String tableName,String codeName) {
		testSystemCodeMapper.deleteProduct(category, tableName, codeName);
	}
	
	//상품 업데이트
	public void updateProduct(SystemCodeDataDTO sDateDto) {
		List<SystemCodeDataDTO> resultList = sDateDto.getProductListVo();
		for(int i=0;i<resultList.size();i++) {
			resultList.get(i).setPlant(sDateDto.getPlant());
			resultList.get(i).setTableName(sDateDto.getTableName());
		}
		testSystemCodeMapper.updateProduct(resultList);
	}
	
	//상품검색
	public Map<String,Object> searchProduct(String sk,String sv,String tableName,String category,String svprice1,String svprice2){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		List<SystemCodeDataDTO> resultList = new ArrayList<SystemCodeDataDTO>();
		DecimalFormat formatter = new DecimalFormat("###,###"); 
		if(sk.equals("가격")) {						//가격검색일때
			sk = "CODE_GROUP2";
			resultList = testSystemCodeMapper.searchProductByPrice(sk, svprice1, svprice2, tableName, category);
		}else {										//가격검색이아닌다른 검색일떄
			if(sk.equals("상품명")) {					//상품명 검색일때
				sk = "CODE_NAME";
			}
			else if(sk.equals("브랜드")) {			//브랜드 검색일떈
					sk = "CODE_GROUP1";
				}
			else if(sk.equals("색상")) {				//색상 검색일때
					sk = "CODE_GROUP3";
				}
			resultList = testSystemCodeMapper.searchProduct(sk, sv, tableName, category);
			}
		//상품 총갯수 초기화
		int proAllCount = 0;
		//상품 총 갯수 별 가격
		int oneOfProTotalPrice = 0;
		//총 중분류 가격
		int totalPrice =0;
		for(int i = 0 ;i<resultList.size();i++) {
			//상품 종류별 갯수
			int proCount = 0;
			//상품 개당가격 
			int oneOfPrice = 0;
			//상품 총 갯수 더하기
			proAllCount += resultList.get(i).getCodecount();
			//상품당 갯수 가져오기
			proCount = resultList.get(i).getCodecount();
			//상품당 가격 가져오기
			oneOfPrice = Integer.parseInt(resultList.get(i).getCodeprice());
			//해당 상품가격 	=	(상품당 갯수 *상품당 가격) 
			oneOfProTotalPrice 	= (proCount * oneOfPrice);
			//이중분류의 총가격 합치기
			totalPrice 		+=(proCount * oneOfPrice);
			//###,###으로 포맷
			String strOnePrice = formatter.format(oneOfPrice)+"원";
			resultList.get(i).setCodeprice(strOnePrice);
			//###,###으로 포맷
			String total = formatter.format(oneOfProTotalPrice)+"원";
			resultList.get(i).setTotalPrice(total);
		}
		String strTotalPrice = formatter.format(totalPrice)+"원";

		resultMap.put("resultList", resultList);
		resultMap.put("proAllCount", proAllCount);
		resultMap.put("totalPrice", strTotalPrice);
		return resultMap;
	}
	//업데이트를 위한 상품리스트만 가져오기
	public List<SystemCodeDataDTO> getProductListForUpdate(String category,String tableName){
		return testSystemCodeMapper.getProductList(category, tableName);
	}
	
	
	//상품리스트 상품별, 전체 총합 가져오기
		public Map<String,Object> getProductList(String category,String tableName){
			Map<String,Object> resultMap = new HashMap<String,Object>();
			List<SystemCodeDataDTO> resultList = testSystemCodeMapper.getProductList(category, tableName);
			DecimalFormat formatter = new DecimalFormat("###,###"); 
			//상품 총갯수 초기화
			int proAllCount = 0;
			//상품 총 갯수 별 가격
			int oneOfProTotalPrice = 0;
			//총 중분류 가격
			int totalPrice =0;
			for(int i = 0 ;i<resultList.size();i++) {
				//상품 종류별 갯수
				int proCount = 0;
				//상품 개당가격 
				int oneOfPrice = 0;
				//상품 총 갯수 더하기
				proAllCount += resultList.get(i).getCodecount();
				//상품당 갯수 가져오기
				proCount = resultList.get(i).getCodecount();
				//상품당 가격 가져오기
				oneOfPrice = Integer.parseInt(resultList.get(i).getCodeprice());
				//해당 상품가격 	=	(상품당 갯수 *상품당 가격) 
				oneOfProTotalPrice 	= (proCount * oneOfPrice);
				//이중분류의 총가격 합치기
				totalPrice 		+=(proCount * oneOfPrice);
				//###,###으로 포맷
				String strOnePrice = formatter.format(oneOfPrice)+"원";
				resultList.get(i).setCodeprice(strOnePrice);
				//###,###으로 포맷
				String total = formatter.format(oneOfProTotalPrice)+"원";
				resultList.get(i).setTotalPrice(total);
			}
			String strTotalPrice = formatter.format(totalPrice)+"원";

			resultMap.put("resultList", resultList);
			resultMap.put("proAllCount", proAllCount);
			resultMap.put("totalPrice", strTotalPrice);
			return resultMap;
		}
	//상품출고
	public void outProductCount(SystemCodeDataDTO sDataDto) {
		testSystemCodeMapper.outProductCount(sDataDto);
	}
	
	//상품입고
	public void insertProducCount(SystemCodeDataDTO sDataDto) {
		testSystemCodeMapper.insertProducCount(sDataDto);
	}
	
	//중분류 수정 메서드
	public void updateMiddleCategory(SystemCodeDTO sCodeDto) {
		//업데이트
		testSystemCodeMapper.updateMiddleCategoryDesc(sCodeDto);
	}
	
	
	
	//중분류 삭제 메서드
	public String deleteMiddleCategory(String category,String tableName) {
		SystemCodeDTO sCodeDto = new SystemCodeDTO();
		sCodeDto.setPlant(category);
		sCodeDto.setTableName(tableName);
		System.out.println(sCodeDto.toString());
		testSystemCodeMapper.deleteMiddleCate(sCodeDto);
		return tableName+" 이(가) 삭제 되었습니다.";
	}
	
	//중분류 삭제체크을 위한 메서드
	public String deleteMiddleCategorycheck(String category,String tableName) {
		int resultInt = testSystemCodeMapper.countPlant(category);
		String result ="";
		if(resultInt==1) {
			result = "마지막 중분류입니다. 삭제하면 대분류도 삭제됩니다. 정말 삭제하시겠습니까?";
		}else {
			result = deleteMiddleCategory(category, tableName);
		}
		return result;
	}
	
	//큰 카테고리 수정을 위한 리스트 가져오기 
	public List<SystemCodeDTO> getMiddlecategory(String category){
		
		return testSystemCodeMapper.getMiddleCate(category);
	}
	
	//큰 카테고리 관리 리스트 메서드
	public List<String> getBicCategoryInfo(){
		return testSystemCodeMapper.getBigCategoryList();
		
	}
	
	//상품등록 메서드
	public void insertProduct(SystemCodeDataDTO dataDto) {
		System.out.println(dataDto.toString());
		testSystemCodeMapper.insertProduct(dataDto);
	}
	
	//상품등록화면에 대분류별로 다른 화면 
	public List<SystemCodeDTO> getInsertpage(String category){
		return testSystemCodeMapper.getListOfMiddlcate(category);
	}
	
	//중카테고리로 메인화면
	public List<SystemCodeDTO> getMiddleCategoty(String category) {
		List<SystemCodeDTO> resultList = testSystemCodeMapper.getListOfMiddlcate(category);
		for(int i = 0;i<resultList.size();i++) {
			resultList.get(i).setPlant(category);
		}
		return resultList;
	}
	
	//큰카테고리 입력
	public void insertBigCategorhy(SystemCodeDTO sCodeDto) {
		List<SystemCodeDTO> resultList = sCodeDto.getTableNameVoList();
		for(int i = 0; i<resultList.size();i++) {
			resultList.get(i).setPlant(sCodeDto.getPlant());
		}
		testSystemCodeMapper.insertBigCategory(resultList);
	}
	
	
	//큰 메인 페이지의 큰 카테고리 리스트 뿌리기
	public List<String> getBigCategory() {
		return testSystemCodeMapper.getBigCategory();
	}
	
}
