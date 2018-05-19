package com.example.alanbarrera.ordinario_paty;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import com.example.alanbarrera.ordinario_paty.logic.*;
import java.util.ArrayList;

public class ActivityMyOrders extends SaleAdapterListenerActivity
{
    DeliveryMan deliveryman;
    DeliveryOrdersViewModel orders;
    FloatingActionButton fab;
    Button start;
    SaleAdapter mSaleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_my_orders);

        deliveryman = Utils.getDeliveryman();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.deliveryman) + " - " + deliveryman.FirstName + " " + deliveryman.LastName);

        fab = findViewById(R.id.my_orders_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ActivityUnassignedOrders.class);
                startActivity(intent);
            }
        });


        ModelResult result = SaleService.GetOrders(new DeliveryOrderRequestViewModel(deliveryman, ESaleStatus.CONFIRMED));

        orders = (DeliveryOrdersViewModel) result.Object;

        if (orders.saleRestaurants.size() > 0)
        {
            //Se declaran y obtienen los Widgets
            RecyclerView mRecyclerView = findViewById(R.id.my_orders_rv_orders);
            start = findViewById(R.id.my_orders_bt_start);

            //Se hacen visibles
            mRecyclerView.setVisibility(View.VISIBLE);
            start.setVisibility(View.VISIBLE);

            //Se llena la lista de pedidos
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
            mSaleAdapter = new SaleAdapter(this, orders, this);
            mRecyclerView.setAdapter(mSaleAdapter);
            mRecyclerView.setLayoutManager(mLayoutManager);

        }
        else
        {
            //Se hace visible el layout
            LinearLayout linearLayout = findViewById(R.id.empty_orders);
            linearLayout.setVisibility(View.VISIBLE);
        }
    }


    public void StartDelivery(View view)
    {
        ArrayList<SaleRestaurant> restaurantList;

        if (isMultipleSelectionActivaded)
        {
            restaurantList = new ArrayList<>();

            for (int index : selectedItemsIndexes)
                restaurantList.add(orders.saleRestaurants.get(index));
        }
        else
        {
            restaurantList = orders.saleRestaurants;
        }

        UpdateSaleRequestViewModel updateSales = new UpdateSaleRequestViewModel(deliveryman, restaurantList, ESaleStatus.INTRANSIT);

        ModelResult result = SaleService.UpdateSales(updateSales);

        if (result.Message.Status == CodeResponse.Ok)
        {
            Intent intent = new Intent(getApplicationContext(), ActivityDeliverOrders.class);
            startActivity(intent);
        }

    }


    @Override
    public void OnSingleSelectionClick() {

    }

    @Override
    public void OnSingleSelectionClick(int position) {
        SingleOrderViewModel order = new SingleOrderViewModel(orders.saleRestaurants.get(position), orders.dinnerAddresses.get(position));
        Intent intent = new Intent(this, ActivitySaleDetail.class);
        intent.putExtra(Constants.EXTRA_DELIVERY_ORDER, Utils.objectToString(order));
        startActivity(intent);
    }

    public void ChangeUI()
    {
        if (isMultipleSelectionActivaded)
            start.setText( getString(R.string.start_selected_deliveries));
        else
            start.setText(getString(R.string.start_all_deliveries));
    }
}
