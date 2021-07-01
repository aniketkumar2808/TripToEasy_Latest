package com.TripToEasy.model.parsingModel.bus;

public class SeatingBean {
	private Integer seqNo;
	private Integer row;
	private Integer col;
	private Integer width;
	private Integer height;
	private Integer seatType;
	private String seatNo;
	private Double totalFare;
	private Double baseFare;
	private Integer status;
	private String decks;
	private Integer maxRows;
	private Integer maxCols;
	private Integer isAvailable;
	public String Gender;
	public String isSleeper;

	public SeatingBean(Integer seqNo, Integer row, Integer col, Integer width, Integer height,
					   Integer seatType, String seatNo, Double totalFare, Double baseFare,
					   Integer status, String decks, Integer maxRows, Integer maxCols,
					   Integer isAvailable) {
		this.seqNo = seqNo;
		this.row = row;
		this.col = col;
		this.width = width;
		this.height = height;
		this.seatType = seatType;
		this.seatNo = seatNo;
		this.totalFare = totalFare;
		this.baseFare = baseFare;
		this.status = status;
		this.decks = decks;
		this.maxRows = maxRows;
		this.maxCols = maxCols;
		this.isAvailable = isAvailable;
	}

	public SeatingBean() {
		super();
	}

	public Integer getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(Integer seqNo) {
		this.seqNo = seqNo;
	}

	public Integer getRow() {
		return row;
	}

	public void setRow(Integer row) {
		this.row = row;
	}

	public Integer getCol() {
		return col;
	}

	public void setCol(Integer col) {
		this.col = col;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Integer getSeatType() {
		return seatType;
	}

	public void setSeatType(Integer seatType) {
		this.seatType = seatType;
	}

	public String getSeatNo() {
		return seatNo;
	}

	public void setSeatNo(String seatNo) {
		this.seatNo = seatNo;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getDecks() {
		return decks;
	}

	public void setDecks(String decks) {
		this.decks = decks;
	}

	public Integer getMaxRows() {
		return maxRows;
	}

	public void setMaxRows(Integer maxRows) {
		this.maxRows = maxRows;
	}

	public Integer getMaxCols() {
		return maxCols;
	}

	public void setMaxCols(Integer maxCols) {
		this.maxCols = maxCols;
	}

	public Integer getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(Integer isAvailable) {
		this.isAvailable = isAvailable;
	}

	public String getGender() {
		return Gender;
	}

	public void setGender(String gender) {
		Gender = gender;
	}

	public String getIsSleeper() {
		return isSleeper;
	}

	public void setIsSleeper(String isSleeper) {
		this.isSleeper = isSleeper;
	}

	public Double getTotalFare() {
		return totalFare;
	}

	public void setTotalFare(Double totalFare) {
		this.totalFare = totalFare;
	}

	public Double getBaseFare() {
		return baseFare;
	}

	public void setBaseFare(Double baseFare) {
		this.baseFare = baseFare;
	}
}