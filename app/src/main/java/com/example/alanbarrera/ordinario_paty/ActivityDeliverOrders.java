package com.example.alanbarrera.ordinario_paty;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.example.alanbarrera.ordinario_paty.logic.*;
import java.util.ArrayList;
import java.util.Collections;

public class ActivityDeliverOrders extends SaleAdapterListenerActivity
{
    RecyclerView mRecyclerView;
    Button markAsDelivered;
    DeliveryMan deliveryMan;
    DeliveryOrdersViewModel orders;
    DeliverySaleAdapter mSaleAdapter;
    int lastItemPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliver_orders);

        deliveryMan = Utils.getDeliveryman();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.deliveryman) + " - " + deliveryMan.FirstName);

        ModelResult result = SaleService.GetOrders(new DeliveryOrderRequestViewModel(deliveryMan, ESaleStatus.INTRANSIT));
        orders = (DeliveryOrdersViewModel) result.Object;

        if(orders.saleRestaurants.size() == 0)
        {
            Intent intent = new Intent(getApplicationContext(), ActivityMyOrders.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

        // Obtener widgets
        mRecyclerView = findViewById(R.id.deliver_orders_recycler_view);
        markAsDelivered = findViewById(R.id.mark_as_delivered);

        //RecyclerView
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mSaleAdapter = new DeliverySaleAdapter(this, orders, this);
        mRecyclerView.setAdapter(mSaleAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);

    }

    @Override
    public void OnSingleSelectionClick() {

    }

    public void MarkAsDelivered_Click(View v)
    {
        ArrayList<SaleRestaurant> saleRestaurants = new ArrayList<SaleRestaurant>();

        if (isMultipleSelectionActivaded)
        {
            for (int index : selectedItemsIndexes)
                saleRestaurants.add(orders.saleRestaurants.get(index));
        }
        else
        {
            saleRestaurants = orders.saleRestaurants;
        }

        UpdateSaleRequestViewModel updateSales = new UpdateSaleRequestViewModel(deliveryMan, saleRestaurants,ESaleStatus.DELIVERED);

        ModelResult result = SaleService.UpdateSales(updateSales);

        if(result.Message.Status == CodeResponse.Ok)
        {
            ArrayList<SaleRestaurant> updatedOrders = (ArrayList<SaleRestaurant>) result.Object;

            ArrayList<Integer> indexes = new ArrayList<Integer>();

            for(SaleRestaurant updatedOrder : updatedOrders)
            {
                for(SaleRestaurant sale : orders.saleRestaurants)
                {
                    if (updatedOrder.SaleRestaurantId == sale.SaleRestaurantId)
                    {
                        indexes.add(orders.saleRestaurants.indexOf(sale));
                        break;
                    }
                }
            }

            Collections.sort(indexes);
            Collections.reverse(indexes);

            for(int index : indexes)
            {
                orders.saleRestaurants.remove(index);
                orders.dinnerAddresses.remove(index);
            }
            DeleteSelectedItems();
            mSaleAdapter.notifyDataSetChanged();

            Toast.makeText(this, getString(R.string.delivered_orders), Toast.LENGTH_SHORT).show();
        }

        if(orders.saleRestaurants.size() == 0)
        {
            Intent intent = new Intent(this, ActivityMyOrders.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

    }

    @Override
    public void OnSingleSelectionClick(int position)
    {
        //Last item position
        lastItemPosition = position;

        //See details
        SingleOrderViewModel order = new SingleOrderViewModel(orders.saleRestaurants.get(position), orders.dinnerAddresses.get(position));
        String deliveryOrder = Utils.objectToString(order);
        Intent intent = new Intent(this, ActivitySaleDetail.class);
        intent.putExtra(Constants.EXTRA_DELIVERY_ORDER, deliveryOrder);

        this.startActivity(intent);
    }
    @Override
    public void ChangeUI()
    {
        if (isMultipleSelectionActivaded)
            markAsDelivered.setText(getString(R.string.mark_selected_as_delivered));
        else
            markAsDelivered.setText(getString(R.string.mark_all_as_delivered));
    }

    @Override
    public void onBackPressed(){
        Toast.makeText(this, "¡Entrega tus pedidos!", Toast.LENGTH_SHORT).show();
    }
}
