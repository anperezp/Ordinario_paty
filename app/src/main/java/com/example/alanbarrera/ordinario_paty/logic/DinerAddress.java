package com.example.alanbarrera.ordinario_paty.logic;

import java.util.UUID;

public class DinerAddress
{
    public String Latitude;
    public String Longitude;
    public String Street;
    public String Number;
    public String Intersection;
    public String InternalNumber;
    public String District;
    public String City;
    public UUID DinerId;

    public DinerAddress(String latitude, String longitude, String street, String number, String intersection, String internalNumber, String district, String city, UUID dinerId) {
        Latitude = latitude;
        Longitude = longitude;
        Street = street;
        Number = number;
        Intersection = intersection;
        InternalNumber = internalNumber;
        District = district;
        City = city;
        DinerId = dinerId;
    }
}
