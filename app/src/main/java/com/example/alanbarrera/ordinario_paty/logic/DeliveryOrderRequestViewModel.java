package com.example.alanbarrera.ordinario_paty.logic;

public class DeliveryOrderRequestViewModel
{
    public DeliveryMan deliveryMan;
    public ESaleStatus ESaleStatus;

    public DeliveryOrderRequestViewModel(DeliveryMan deliveryMan, ESaleStatus ESaleStatus) {
        this.deliveryMan = deliveryMan;
        this.ESaleStatus = ESaleStatus;
    }
}
