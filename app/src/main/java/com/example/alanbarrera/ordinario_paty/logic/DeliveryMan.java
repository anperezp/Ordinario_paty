package com.example.alanbarrera.ordinario_paty.logic;

import java.util.UUID;

public class DeliveryMan
{
    public UUID DeliveryManId;
    public String FirstName;
    public String LastName;
    public String UserName;
    public String Password;

    public DeliveryMan(UUID deliveryManId, String firstName, String lastName, String userName, String password) {
        DeliveryManId = deliveryManId;
        FirstName = firstName;
        LastName = lastName;
        UserName = userName;
        Password = password;
    }
}