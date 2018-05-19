package com.example.alanbarrera.ordinario_paty;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alanbarrera.ordinario_paty.logic.*;
import com.google.gson.Gson;

import java.util.ArrayList;

public class ActivitySaleDetail extends Activity
{
    TextView name;
    TextView address;
    TextView total;
    TextView details;

    //Botones
    Button call;
    Button route;
    Button deliveryStatus;

    SingleOrderViewModel order;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_detail);

        Intent intent = getIntent();
        String orderString = intent.getStringExtra(Constants.EXTRA_DELIVERY_ORDER);
        Gson gson = new Gson();
        order = gson.fromJson(orderString, SingleOrderViewModel.class);

        name = findViewById(R.id.textView_nombreComensal);
        address = findViewById(R.id.textView_direccion);
        total = findViewById(R.id.precio);
        details = findViewById(R.id.textView_mostrarPedidos);

        call = findViewById(R.id.buttonContactar);
        route = findViewById(R.id.buttonComoLlegar);
        deliveryStatus = findViewById(R.id.buttonAccionPedido);

        SetDeliveryAction();

        SetWidgetsText(order);

    }

    private void SetWidgetsText(SingleOrderViewModel order)
    {

        name.setText(order.saleRestaurant.Diner.Name);
        address.setText(composeAddress(order.dinerAddress));
        total.setText("Total: $" +  String.valueOf(order.saleRestaurant.SaleTotals.Total));
        details.setText(GetAllDetails(order.saleRestaurant.SaleDetails));
    }

    public void callNumber(View v)
    {
        //Format phoneNumber
        Uri number = Uri.parse("tel:+52" + order.saleRestaurant.Phone);

        //Start call intent
        Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
        startActivity(callIntent);
    }

    public void viewAddress(View v)
    {
        Uri location = Uri.parse("geo:0,0?q=" + order.dinerAddress.Latitude + "," + order.dinerAddress.Longitude);
        Intent callIntent = new Intent(Intent.ACTION_VIEW, location);
        startActivity(callIntent);
    }

    private void SetDeliveryAction()
    {
        switch(order.saleRestaurant.ESaleStatus)
        {
            case CONFIRMED:
                deliveryStatus.setText(getString(R.string.start_delivery));
                deliveryStatus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DeliveryActionDeliver(v);
                    }});
                break;
            case NEW:
                deliveryStatus.setText(getString(R.string.assign_order));
                deliveryStatus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DeliveryActionAssign(v);
                    }});
                call.setEnabled(false);
                break;
            case INTRANSIT:
                deliveryStatus.setText(getString(R.string.mark_as_delivered));
                deliveryStatus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DeliveryActionDelivered(v);
                    }});
                break;
        }

    }

    private void DeliveryActionDeliver(View v)
    {
        UpdateSaleRequestViewModel updateSales = new UpdateSaleRequestViewModel(Utils.getDeliveryman(), new ArrayList<SaleRestaurant>(), ESaleStatus.INTRANSIT);
        updateSales.SaleRestaurantList.add(order.saleRestaurant);

        ModelResult result = SaleService.UpdateSales(updateSales);

        if (result.Message.Status == CodeResponse.Ok)
        {
            DeliveryOrdersViewModel updatedSales = new DeliveryOrdersViewModel((ArrayList<SaleRestaurant>) result.Object, new ArrayList<DinerAddress>());
            updatedSales.dinnerAddresses.add(order.dinerAddress);
            Intent intent = new Intent(this, ActivityDeliverOrders.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra(Constants.EXTRA_DELIVERY_ORDER, Utils.objectToString(updatedSales));
            startActivity(intent);
        }
    }

    private void DeliveryActionAssign(View v)
    {
        UpdateSaleRequestViewModel updateSales = new UpdateSaleRequestViewModel(Utils.getDeliveryman(), new ArrayList<SaleRestaurant>(), ESaleStatus.CONFIRMED);
        updateSales.SaleRestaurantList.add(order.saleRestaurant);

        ModelResult result = SaleService.UpdateSales(updateSales);

        if (result.Message.Status == CodeResponse.Ok)
        {
            Toast.makeText(this, "Pedido agregado", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), ActivityUnassignedOrders.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

    private void DeliveryActionDelivered(View v)
    {
        UpdateSaleRequestViewModel updateSales = new UpdateSaleRequestViewModel(Utils.getDeliveryman(), new ArrayList<SaleRestaurant>(), ESaleStatus.DELIVERED);
        updateSales.SaleRestaurantList.add(order.saleRestaurant);

        ModelResult result = SaleService.UpdateSales(updateSales);

        if (result.Message.Status == CodeResponse.Ok)
        {
            Toast.makeText(this, "Pedido entregado", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), ActivityDeliverOrders.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }



    private String composeAddress(DinerAddress address)
    {
        return address.Street + " " +  address.Number + " " + address.Intersection + " " + address.InternalNumber + ", " + address.District + ", " +address.City;
    }

    public String GetAllDetails(ArrayList<SaleDetails> saleDetails)
    {
        String allDetails = "";

        for(SaleDetails details : saleDetails)
            allDetails += (details.Quantity + " " + details.Product.Name + "\n");

        return allDetails;
    }
}