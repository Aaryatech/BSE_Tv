package com.ats.bse_tv.bean;

import java.util.List;

/**
 * Created by MIRACLEINFOTAINMENT on 04/01/18.
 */

public class LastOrderListData {

    private List<LastOrderList> lastOrderList;
    private ErrorMessage errorMessage;

    public List<LastOrderList> getLastOrderList() {
        return lastOrderList;
    }

    public void setLastOrderList(List<LastOrderList> lastOrderList) {
        this.lastOrderList = lastOrderList;
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(ErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "LastOrderListData{" +
                "lastOrderList=" + lastOrderList +
                ", errorMessage=" + errorMessage +
                '}';
    }
}
