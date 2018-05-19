package com.example.alanbarrera.ordinario_paty;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.alanbarrera.ordinario_paty.logic.*;

import java.util.ArrayList;

public class ActivityUnassignedOrders extends SaleAdapterListenerActivity implements IRecyclerviewClickListener
    {
        RecyclerView mRecyclerView;
        SaleAdapter mSaleAdapter;
        RecyclerView.LayoutManager mLayoutManager;
        Button assing;
        DeliveryOrdersViewModel unassignedOrders;

        @Override
        protected void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_unassigned_orders);

            Toolbar toolbar = findViewById(R.id.toolbar);
            toolbar.setTitle(getString(R.string.unassigned_orders));

            DeliveryMan deliveryman = Utils.getDeliveryman();

            //Se asigna nulo al Id para que devuelva todos los pedidos que no tienen repartidor asignado.
            deliveryman.DeliveryManId = null;

            ModelResult result = SaleService.GetOrders(new DeliveryOrderRequestViewModel(deliveryman, ESaleStatus.NEW));
            unassignedOrders = (DeliveryOrdersViewModel) result.Object;

            if(unassignedOrders.saleRestaurants.size() == 0)
            {
                Intent intent = new Intent(getApplicationContext(), ActivityMyOrders.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }

            mRecyclerView = findViewById(R.id.unassigned_orders_recycler_view);
            assing = findViewById(R.id.assign_orders);

            mSaleAdapter = new SaleAdapter(this, unassignedOrders,  this);
            mLayoutManager = new LinearLayoutManager(this);
            mRecyclerView.setAdapter(mSaleAdapter);
            mRecyclerView.setLayoutManager(mLayoutManager);

        }

        @Override
        public void OnSingleSelectionClick() {

        }

        public void AssingOrders(View v)
        {
            ArrayList<SaleRestaurant> RestaurantList = new ArrayList<SaleRestaurant>();

            for (int index : selectedItemsIndexes)
                RestaurantList.add(unassignedOrders.saleRestaurants.get(index));

            UpdateSaleRequestViewModel saleRequest = new UpdateSaleRequestViewModel(Utils.getDeliveryman(), RestaurantList, ESaleStatus.CONFIRMED );

            ModelResult result = SaleService.UpdateSales(saleRequest);

            if(result.Message.Status == CodeResponse.Ok)
            {
                Intent intent = new Intent(this, ActivityMyOrders.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }

        @Override
        public void OnSingleSelectionClick(int position)
        {
            SingleOrderViewModel order = new SingleOrderViewModel(unassignedOrders.saleRestaurants.get(position), unassignedOrders.dinnerAddresses.get(position));
            Intent intent = new Intent(this, ActivitySaleDetail.class);
            intent.putExtra(Constants.EXTRA_DELIVERY_ORDER, Utils.objectToString(order));
            startActivity(intent);
        }

        @Override
        public void ChangeUI()
        {
            if (isMultipleSelectionActivaded)
                assing.setVisibility(View.VISIBLE);
            else
                assing.setVisibility(View.GONE);
        }

        @Override
        public void onBackPressed(){
            Intent intent = new Intent(this, ActivityMyOrders.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }