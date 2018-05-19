package com.example.alanbarrera.ordinario_paty.logic;

public class SingleOrderViewModel
{
    public SaleRestaurant saleRestaurant;
    public DinerAddress dinerAddress;

    public SingleOrderViewModel(SaleRestaurant saleRestaurant, DinerAddress dinerAddress) {
        this.saleRestaurant = saleRestaurant;
        this.dinerAddress = dinerAddress;
    }
}
