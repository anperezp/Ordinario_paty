package com.example.alanbarrera.ordinario_paty.logic;

import java.util.ArrayList;

public class UpdateSaleRequestViewModel
{
    public DeliveryMan DeliveryMan;
    public ArrayList<SaleRestaurant> SaleRestaurantList;
    public ESaleStatus ESaleStatus;

    public UpdateSaleRequestViewModel(DeliveryMan deliveryMan, ArrayList<SaleRestaurant> saleRestaurantList, ESaleStatus ESaleStatus) {
        DeliveryMan = deliveryMan;
        SaleRestaurantList = saleRestaurantList;
        this.ESaleStatus = ESaleStatus;
    }
}
