package com.cafe24.choiyooq1.domain;

import java.util.List;

public class SystemCodeDTO {
	private String plant;
	private String tableName;
	private String originalTableName;
	private String description; 
	private String formatType;
	private int decimalLength;
	private int sizeLimit;
	private String countOfCategory;
	private List<SystemCodeDTO> tableNameVoList = null;
	
	@Override
	public String toString() {
		return "SystemCodeDTO [plant=" + plant + ", tableName=" + tableName + ", originalTableName=" + originalTableName
				+ ", description=" + description + ", formatType=" + formatType + ", decimalLength=" + decimalLength
				+ ", sizeLimit=" + sizeLimit + ", countOfCategory=" + countOfCategory + ", tableNameVoList="
				+ tableNameVoList + "]";
	}
	public String getOriginalTableName() {
		return originalTableName;
	}
	public void setOriginalTableName(String originalTableName) {
		this.originalTableName = originalTableName;
	}
	
	public List<SystemCodeDTO> getTableNameVoList() {
		return tableNameVoList;
	}
	public void setTableNameVoList(List<SystemCodeDTO> tableNameVoList) {
		this.tableNameVoList = tableNameVoList;
	}
	public String getCountOfCategory() {
		return countOfCategory;
	}
	public void setCountOfCategory(String countOfCategory) {
		this.countOfCategory = countOfCategory;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getFormatType() {
		return formatType;
	}
	public void setFormatType(String formatType) {
		this.formatType = formatType;
	}
	public int getDecimalLength() {
		return decimalLength;
	}
	public void setDecimalLength(int decimalLength) {
		this.decimalLength = decimalLength;
	}
	public int getSizeLimit() {
		return sizeLimit;
	}
	public void setSizeLimit(int sizeLimit) {
		this.sizeLimit = sizeLimit;
	}
	
}
