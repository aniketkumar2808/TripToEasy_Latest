package com.TripToEasy.model.parsingModel.bus;

/**
 * Created by ptblr-1174 on 11/6/18.
 */

public class BusOperatorsInfo {
    String operatorName;
    String operatorCode;
    Boolean isSelected;
    public BusOperatorsInfo(String operatorName,String operatorCode,Boolean isSelected){
        this.operatorName=operatorName;
        this.operatorCode=operatorCode;
        this.isSelected = isSelected;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getOperatorCode() {
        return operatorCode;
    }

    public void setOperatorCode(String operatorCode) {
        this.operatorCode = operatorCode;
    }

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }
}
