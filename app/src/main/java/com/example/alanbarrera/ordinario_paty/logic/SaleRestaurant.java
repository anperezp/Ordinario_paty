package com.example.alanbarrera.ordinario_paty.logic;

import java.util.ArrayList;
import java.util.UUID;

public class SaleRestaurant
{
    public SaleTotal SaleTotals;
    public ArrayList<SaleDetails> SaleDetails;
    public Diner Diner;
    public String Phone;
    public UUID DeliveryManId;
    public UUID SaleRestaurantId;
    public int Folio;
    public ESaleStatus ESaleStatus;

    public SaleRestaurant(SaleTotal saleTotals, ArrayList<SaleDetails> saleDetails, Diner diner, String phone, UUID deliveryManId, UUID saleRestaurantId, int folio, ESaleStatus ESaleStatus)
    {
        SaleTotals = saleTotals;
        SaleDetails = saleDetails;
        Diner = diner;
        Phone = phone;
        DeliveryManId = deliveryManId;
        SaleRestaurantId = saleRestaurantId;
        Folio = folio;
        this.ESaleStatus = ESaleStatus;
    }
}
