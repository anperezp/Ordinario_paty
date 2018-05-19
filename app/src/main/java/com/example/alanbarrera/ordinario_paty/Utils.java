package com.example.alanbarrera.ordinario_paty;

import com.example.alanbarrera.ordinario_paty.logic.*;
import com.google.gson.Gson;

public class Utils
{
    private static Gson gson = new Gson();

    public static DeliveryMan getDeliveryman()
    {
        return Constants.DELIVERYMAN;
    };

    public static String objectToString(Object object){ return gson.toJson(object); }

}
