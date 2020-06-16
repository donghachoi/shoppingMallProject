package com.cafe24.choiyooq1.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cafe24.choiyooq1.domain.SystemCodeDTO;
import com.cafe24.choiyooq1.domain.SystemCodeDataDTO;

@Mapper
public interface TestSystemCodeMapper {
	
	//상품 삭제
	public void deleteProduct(String category,String tableName,String codeName);
	
	//상품업데이트
	public void updateProduct(List<SystemCodeDataDTO> resultList);
	
	//가격검색
	public List<SystemCodeDataDTO> searchProductByPrice(@Param("sk")String sk,@Param("svprice1")String svprice1,@Param("svprice2")String svprice2,@Param("tableName")String tableName,@Param("category")String category);
	
	//상품 검색 (상품명,브랜드,색상)
	public List<SystemCodeDataDTO> searchProduct(@Param("sk")String sk,@Param("sv")String sv,@Param("tableName")String tableName,@Param("category")String category);
	
	//상품 출고
	public void outProductCount(SystemCodeDataDTO sDataDto);
	
	//상품 입고
	public void insertProducCount(SystemCodeDataDTO sDataDto);
	
	//소분류 리스트
	public List<SystemCodeDataDTO> getProductList(String category,String tableName);
	
	//중분류 설명 수정
	public void updateMiddleCategoryDesc(SystemCodeDTO sCodeDto);
	
	//대분류 삭제여부 확인을 위해 count가져오기
	public int countPlant(String category);
	
	//중분류 삭제 하기
	public void deleteMiddleCate(SystemCodeDTO sCodeDto);
	
	//중분류 이름과 설명가져오기 
	public List<SystemCodeDTO> getMiddleCate(String category);
	
	//대분류 리스트 가져오기
	public List<String> getBigCategoryList();
	
	//상품등록하기
	public void insertProduct(SystemCodeDataDTO dataDto);
	
	//선택된 카테고리 중분류 리스트 가져오기
	public List<SystemCodeDTO> getListOfMiddlcate(String category);
	
	//bigCategory insert
	public void insertBigCategory(List<SystemCodeDTO> resultList);
	
	//완전 메인 첫 화면 대분류 종류별로
	public List<String> getBigCategory();
	
	//systemcode 전체 리스트
	public List<SystemCodeDTO> getList();
	
}
