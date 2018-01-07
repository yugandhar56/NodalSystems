package com.nodel.nodalsytems.data.model;

import java.io.Serializable;

/**
 * Created by J.Yugandhar on 23-02-2016.
 */
public class CLTablesItemsDTO implements Serializable
{

    private String sItemName;
    private int sUnit;
    private String sOrderQty;
//    private String sIssueQty;
    private int iProductId;
    private int iRefBodyId;
    private int iRate;
    private int iTax;
    private int iDisCount;
    private int iNetAmount;
    private String sCode;

    public String getsCode() {
        return sCode;
    }

    public void setsCode(String sCode) {
        this.sCode = sCode;
    }

    public int getRefBodyId() {
        return iRefBodyId;
    }

    public void setRefBodyId(int iRefBodyId) {
        this.iRefBodyId = iRefBodyId;
    }


    public int getiRate() {
        return iRate;
    }

    public void setiRate(int iRate) {
        this.iRate = iRate;
    }
    public int getAccountId() {
        return iAccountId;
    }

    public void setAccountId(int iAccountId) {
        this.iAccountId = iAccountId;
    }

    public int getRouteId() {
        return iRouteId;
    }

    public void setRouteId(int iRouteId) {
        this.iRouteId = iRouteId;
    }

    private int iAccountId;
    private int iRouteId;

    public  void setProductId(int iProductId)
    {
        this.iProductId =iProductId;
    }
    public  int getProductId()
    {
        return this.iProductId ;
    }

    public void setsItemName(String sItemName) {
        this.sItemName = sItemName;
    }

    public void setUnit(int sUnit) {
        this.sUnit = sUnit;
    }

    public void setsOrderQty(String sOrderQty) {
        this.sOrderQty = sOrderQty;
    }

//    public void setsIssueQty(String sIssueQty) {
//        this.sIssueQty = sIssueQty;
//    }

    public String getsItemName() {
        return sItemName;
    }

    public int getUnit() {
        return sUnit;
    }

    public String getsOrderQty() {
        return sOrderQty;
    }

//    public String getsIssueQty() {
//        return sIssueQty;
//    }


    public int getTax() {
        return iTax;
    }

    public void setTax(int iTax) {
        this.iTax = iTax;
    }

    public int getDisCount() {
        return iDisCount;
    }

    public void setDisCount(int iDisCount) {
        this.iDisCount = iDisCount;
    }

    public int getNetAmount() {
        return iNetAmount;
    }

    public void setNetAmount(int iNetAmount) {
        this.iNetAmount = iNetAmount;
    }
}
