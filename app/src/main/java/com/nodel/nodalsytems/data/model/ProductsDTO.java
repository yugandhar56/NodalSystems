package com.nodel.nodalsytems.data.model;

import java.io.Serializable;

/**
 * Created by yugandhar on 1/6/2018.
 */

public class ProductsDTO implements Serializable {

    private String productName;
    private String	packingWeight;
    private String netAvailableQty;
    private int iMRP;
    private int iDelearPrice;
    private int iCGST;
    private int iSGST;
    private int iIGST;
    private int iProdutId;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPackingWeight() {
        return packingWeight;
    }

    public void setPackingWeight(String packingWeight) {
        this.packingWeight = packingWeight;
    }

    public String getNetAvailableQty() {
        return netAvailableQty;
    }

    public void setNetAvailableQty(String netAvailableQty) {
        this.netAvailableQty = netAvailableQty;
    }

    public int getiMRP() {
        return iMRP;
    }

    public void setiMRP(int iMRP) {
        this.iMRP = iMRP;
    }

    public int getiDelearPrice() {
        return iDelearPrice;
    }

    public void setiDelearPrice(int iDelearPrice) {
        this.iDelearPrice = iDelearPrice;
    }

    public int getiCGST() {
        return iCGST;
    }

    public void setiCGST(int iCGST) {
        this.iCGST = iCGST;
    }

    public int getiSGST() {
        return iSGST;
    }

    public void setiSGST(int iSGST) {
        this.iSGST = iSGST;
    }

    public int getiIGST() {
        return iIGST;
    }

    public void setiIGST(int iIGST) {
        this.iIGST = iIGST;
    }

    public int getiProdutId() {
        return iProdutId;
    }

    public void setiProdutId(int iProdutId) {
        this.iProdutId = iProdutId;
    }
}
