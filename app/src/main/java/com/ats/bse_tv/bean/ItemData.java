package com.ats.bse_tv.bean;

import java.util.List;

/**
 * Created by MIRACLEINFOTAINMENT on 03/01/18.
 */

public class ItemData {

    private List<Item> item;
    private ErrorMessage errorMessage;


    public List<Item> getItem() {
        return item;
    }

    public void setItem(List<Item> item) {
        this.item = item;
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(ErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "ItemData{" +
                "item=" + item +
                ", errorMessage=" + errorMessage +
                '}';
    }
}
