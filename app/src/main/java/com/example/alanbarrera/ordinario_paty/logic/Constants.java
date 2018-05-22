package com.example.alanbarrera.ordinario_paty.logic;

import java.util.UUID;

public class Constants
{
    public static final String EXTRA_DELIVERY_ORDER = "EXTRA_DELIVERY_ORDER";
    public static final UUID GUID_DELIVERYMAN_ALAN = UUID.randomUUID();
    public static final UUID GUID_DELIVERYMAN_FULANO = UUID.randomUUID();
    public static final DeliveryMan DELIVERYMAN = new DeliveryMan(GUID_DELIVERYMAN_ALAN, "Alan", "Barrera", "Username", "Password");
    public static final int RC_SIGN_IN = 1;
}
