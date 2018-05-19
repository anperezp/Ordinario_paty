package com.example.alanbarrera.ordinario_paty.logic;


import java.util.ArrayList;

public class SaleService
{

    public static ModelResult GetOrders(DeliveryOrderRequestViewModel orderRequest)
    {
        try
        {
            //GENERATE ORDERS
            DeliveryOrdersViewModel orders = new DeliveryOrdersViewModel(new ArrayList<SaleRestaurant>(), new ArrayList<DinerAddress>());

            for (SaleRestaurant serverSale : DATA.SaleRestaurants)
            {
                if (orderRequest.deliveryMan.DeliveryManId != null)
                {
                    if (serverSale.DeliveryManId.equals(orderRequest.deliveryMan.DeliveryManId) && serverSale.ESaleStatus == orderRequest.ESaleStatus)
                    {
                        getOrders(orders, serverSale);
                    }
                }
                else
                {
                    if (serverSale.ESaleStatus == orderRequest.ESaleStatus)
                    {
                        getOrders(orders, serverSale);
                    }
                }
            }

            return new ModelResult( new Message(CodeResponse.Ok), orders);
        }
        catch (Exception ex)
        {
            return new ModelResult(new Message(CodeResponse.Danger), null);
        }
    }

    public static ModelResult UpdateSales(UpdateSaleRequestViewModel updateSales)
    {
        try
        {
            ArrayList<SaleRestaurant> salesToUpdate = updateSales.SaleRestaurantList;
            ArrayList<SaleRestaurant> updatedSales = new ArrayList<>();

            for (SaleRestaurant localSale : salesToUpdate)
            {
                for (SaleRestaurant serverSale : DATA.SaleRestaurants)
                {
                    if (localSale.SaleRestaurantId.equals(serverSale.SaleRestaurantId))
                    {
                        serverSale.ESaleStatus = updateSales.ESaleStatus;

                        if (updateSales.ESaleStatus == ESaleStatus.CONFIRMED)
                            serverSale.DeliveryManId.equals(updateSales.DeliveryMan.DeliveryManId);

                        updatedSales.add(serverSale);
                        break;
                    }
                }
            }

            //When results are ok
            return new ModelResult( new Message(CodeResponse.Ok), updatedSales);
        }
        catch (Exception ex)
        {
            return new ModelResult( new Message(CodeResponse.Danger), null);
        }
    }

    private static void getOrders(DeliveryOrdersViewModel orders, SaleRestaurant serverSale)
    {
        orders.saleRestaurants.add(serverSale);

        for (DinerAddress serverDinerAddress : DATA.DinerAddresses)
        {
            if (serverSale.Diner.DinerId.equals(serverDinerAddress.DinerId))
            {
                orders.dinnerAddresses.add(serverDinerAddress);
                break;
            }
        }
    }
}
