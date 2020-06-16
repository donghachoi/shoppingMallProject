package com.cafe24.choiyooq1.domain;

import java.util.List;

public class SystemCodeDataDTO {
	private String plant;
	private String tableName;
	private String codeName;
	private int codecount;
	private String description;
	private String codebrand;
	private String codeprice;
	private String codecolor;
	private String totalPrice;
	private String realCodeName;
	private List<SystemCodeDataDTO> productListVo;
	
	public List<SystemCodeDataDTO> getProductListVo() {
		return productListVo;
	}
	public void setProductListVo(List<SystemCodeDataDTO> productListVo) {
		this.productListVo = productListVo;
	}
	public String getRealCodeName() {
		return realCodeName;
	}
	public void setRealCodeName(String realCodeName) {
		this.realCodeName = realCodeName;
	}
	public String getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getPlant() {
		return plant;
	}
	public void setPlant(String plant) {
		this.plant = plant;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getCodeName() {
		return codeName;
	}
	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}
	public int getCodecount() {
		return codecount;
	}
	public void setCodecount(int codecount) {
		this.codecount = codecount;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCodebrand() {
		return codebrand;
	}
	public void setCodebrand(String codebrand) {
		this.codebrand = codebrand;
	}
	public String getCodeprice() {
		return codeprice;
	}
	public void setCodeprice(String codeprice) {
		this.codeprice = codeprice;
	}
	public String getCodecolor() {
		return codecolor;
	}
	public void setCodecolor(String codecolor) {
		this.codecolor = codecolor;
	}
	@Override
	public String toString() {
		return "SystemCodeDataDTO [plant=" + plant + ", tableName=" + tableName + ", codeName=" + codeName
				+ ", codecount=" + codecount + ", description=" + description + ", codebrand=" + codebrand
				+ ", codeprice=" + codeprice + ", codecolor=" + codecolor + ", totalPrice=" + totalPrice
				+ ", realCodeName=" + realCodeName + ", productListVo=" + productListVo + "]";
	}

	
}
