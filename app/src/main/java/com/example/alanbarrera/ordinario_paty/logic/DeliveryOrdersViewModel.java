package com.example.alanbarrera.ordinario_paty.logic;

import java.util.ArrayList;

public class DeliveryOrdersViewModel
{
    public ArrayList<SaleRestaurant> saleRestaurants;
    public ArrayList<DinerAddress> dinnerAddresses;

    public DeliveryOrdersViewModel(ArrayList<SaleRestaurant> saleRestaurants, ArrayList<DinerAddress> dinnerAddresses) {
        this.saleRestaurants = saleRestaurants;
        this.dinnerAddresses = dinnerAddresses;
    }
}