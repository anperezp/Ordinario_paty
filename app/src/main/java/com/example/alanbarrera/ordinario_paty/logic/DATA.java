package com.example.alanbarrera.ordinario_paty.logic;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

import com.github.javafaker.*;

public class DATA
{
    static boolean isPrepared = false;
    //SaleRestaurants
    public static ArrayList<SaleRestaurant> SaleRestaurants = new ArrayList<>(10);

    //DinerAddress
    public static ArrayList<DinerAddress> DinerAddresses = new ArrayList<>(10);

    //Preparar los datos
    public static void Prepare()
    {
        //Asignar ID al deliveryman
        Constants.DELIVERYMAN.DeliveryManId = Constants.GUID_DELIVERYMAN_ALAN;
        //Guids para SaleRestaurants
        String[] SaleRestaurantGuids =
        {
            "cd7efa74-1093-462e-bdf8-934089055031",
            "e740503a-9e67-4127-871b-5f832089e4a3",
            "dddcd6ab-09e2-4738-85f8-a33f24dee906",
            "4b5f1f35-dbb3-4814-adf1-39bf95aa56b2",
            "9cff5aa2-da05-43ed-9b3a-7babb9831fcf",
            "91494320-cbdd-4089-8ebe-6f0561c8d9bb",
            "2a17e2ba-b78e-40bb-b660-62ec29a9c8c8",
            "8cd2e28f-f23d-4e70-893b-006acc25fe03",
            "30606798-c974-476a-9702-2eec39aa7a10",
            "56c4104f-0560-4b3f-bfd2-1a95b975397a"
        };

        String[] DinerGuids =
        {
            "2b49a8fd-eaaa-42bf-8a79-45c9d6457205",
            "48381bd8-b772-4bcf-ab06-5b8ec268bdbe",
            "ba180b98-faf6-453e-90d4-37cc154ab1c7",
            "6390b721-b09f-4531-b7fb-d1f86d429d54",
            "752c00c8-0dcf-43a2-b805-a13aa29135a3",
            "5fe9dcbd-0e61-48d9-b804-b7ead6acab3b",
            "0ed9dc6d-4c4f-4cd3-9edc-6dc27a3284e6",
            "ca4a4be1-7f6c-42d1-8a5c-e762ff34f314",
            "12594f1d-dd01-412c-b040-54563d6fb5d2",
            "2f6f21dd-2ceb-465d-8d8d-b00f17953638"
        };

        Random random = new Random();
        Faker faker = new Faker();

        for (int i = 0; i < 5; i++)
        {
            SaleRestaurants.add(new SaleRestaurant(
                    new SaleTotal((Math.round(random.nextDouble() * (200 - 50) + 50))),
                    new ArrayList<SaleDetails>(3),
                    new Diner(faker.name().fullName(), UUID.fromString(DinerGuids[i])),
                    faker.phoneNumber().cellPhone(),
                    Constants.GUID_DELIVERYMAN_ALAN,
                    UUID.fromString(SaleRestaurantGuids[i]),
                    random.nextInt((9999 - 1000) + 1) + 1000,
                    ESaleStatus.CONFIRMED
            ));

            SaleRestaurants.get(i).SaleDetails.add(new SaleDetails(
                    random.nextInt((3 - 1) + 1) + 3,
                    new Product("Enchiladas")
            ));
            SaleRestaurants.get(i).SaleDetails.add(new SaleDetails(
                    random.nextInt((3 - 1) + 1) + 3,
                    new Product("Hamburguesa")
            ));
            SaleRestaurants.get(i).SaleDetails.add(new SaleDetails(
                    random.nextInt((3 - 1) + 1) + 3,
                    new Product("Pizza")
            ));
        }

        for (int i = 5; i < 10; i++)
        {
            SaleRestaurants.add(new SaleRestaurant(
                    new SaleTotal((Math.round(random.nextDouble() * (200 - 50) + 50))),
                    new ArrayList<SaleDetails>(3),
                    new Diner(faker.name().fullName(), UUID.fromString(DinerGuids[i])),
                    faker.phoneNumber().cellPhone(),
                    Constants.GUID_DELIVERYMAN_FULANO,
                    UUID.fromString(SaleRestaurantGuids[i]),
                    random.nextInt((9999 - 1000) + 1) + 1000,
                    ESaleStatus.NEW
            ));

            SaleRestaurants.get(i).SaleDetails.add(new SaleDetails(
                    random.nextInt((3 - 1) + 1) + 3,
                    new Product("Enchiladas")
            ));
            SaleRestaurants.get(i).SaleDetails.add(new SaleDetails(
                    random.nextInt((3 - 1) + 1) + 3,
                    new Product("Hamburguesa")
            ));
            SaleRestaurants.get(i).SaleDetails.add(new SaleDetails(
                    random.nextInt((3 - 1) + 1) + 3,
                    new Product("Pizza")
            ));
        }

        //Direcciones
        for (int i = 0; i < 10; i++)
        {
            DinerAddresses.add(
                    new DinerAddress(
                            "20.9672209",
                            "-89.6228785",
                            "Calle 60",
                            "161",
                            "x61 y 63",
                            "1",
                            "Centro",
                            "Mérida",
                            UUID.fromString(DinerGuids[i])
                    ));
        }
    }
}