package com.example.alanbarrera.ordinario_paty.logic;

import java.util.UUID;

public class Diner
{
    public String Name;
    public UUID DinerId;

    public Diner(String name, UUID dinerId) {
        Name = name;
        DinerId = dinerId;
    }
}