package cn.appsys.pojo;

public class Ad_promotion {
	private int id;
	private int appId;
	private String adPicPath;
	private int adPV;
	private int carouselPosition;
	private String startTime;
	private String endTime;
	private int createdBy;
	private String creationDate;
	private int modifyBy;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAppId() {
		return appId;
	}
	public void setAppId(int appId) {
		this.appId = appId;
	}
	public String getAdPicPath() {
		return adPicPath;
	}
	public void setAdPicPath(String adPicPath) {
		this.adPicPath = adPicPath;
	}
	public int getAdPV() {
		return adPV;
	}
	public void setAdPV(int adPV) {
		this.adPV = adPV;
	}
	public int getCarouselPosition() {
		return carouselPosition;
	}
	public void setCarouselPosition(int carouselPosition) {
		this.carouselPosition = carouselPosition;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public int getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}
	public String getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	public int getModifyBy() {
		return modifyBy;
	}
	public void setModifyBy(int modifyBy) {
		this.modifyBy = modifyBy;
	}
	public String getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}
	private String modifyDate;
}
