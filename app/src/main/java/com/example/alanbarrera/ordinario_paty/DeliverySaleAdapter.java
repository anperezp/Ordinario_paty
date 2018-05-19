package com.example.alanbarrera.ordinario_paty;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.alanbarrera.ordinario_paty.logic.*;

import java.util.ArrayList;

class DeliverySaleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private ArrayList<SaleRestaurant> sales;
    private ArrayList<DinerAddress> dinerAddresses;
    private Context context;
    private IRecyclerviewClickListener mListener;

    public DeliverySaleAdapter(Context context, DeliveryOrdersViewModel orders, IRecyclerviewClickListener listener)
    {
        this.sales = orders.saleRestaurants;
        this.dinerAddresses = orders.dinnerAddresses;
        this.context = context;
        this.mListener = listener;
    }


    @Override
    public int getItemCount() { return sales.size(); }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        DeliverySaleViewHolder viewHolder = (DeliverySaleViewHolder) holder;
        SaleRestaurant currentSale = sales.get(position);
        DinerAddress currentAddress = dinerAddresses.get(position);

        if(mListener != null)
        {
            if (mListener.IsSelected(position))
                holder.itemView.setBackgroundColor(Color.GREEN);
            else
                holder.itemView.setBackgroundColor(Color.WHITE);
        }

        viewHolder.tvFolio.setText(context.getString(R.string.sale_folio) + " " + String.valueOf(currentSale.Folio));
        viewHolder.tvDinerName.setText(currentSale.Diner.Name);
        viewHolder.tvAddressStreets.setText(currentAddress.Street + " " + currentAddress.Number + " " + currentAddress.Intersection + " " + currentAddress.InternalNumber + ".");
        viewHolder.tvAddressDistrict.setText(currentAddress.District + ", " + currentAddress.City);
        viewHolder.tvTotalPrice.setText("$" + String.valueOf(currentSale.SaleTotals.Total));
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_sale_deliver, parent, false);

        DeliverySaleViewHolder viewHolder = new DeliverySaleViewHolder(itemView, mListener);

        return viewHolder;
    }

    public class DeliverySaleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener
    {
        private IRecyclerviewClickListener mListener;

        // Campos respectivos de un item
        private TextView tvFolio;
        private TextView tvDinerName;
        private TextView tvAddressStreets;
        private TextView tvAddressDistrict;
        private TextView tvTotalPrice;


        DeliverySaleViewHolder(View itemView, IRecyclerviewClickListener listener)
        {
            super(itemView);
            tvFolio = itemView.findViewById(R.id.sale_folio);
            tvDinerName = itemView.findViewById(R.id.diner_name);
            tvAddressStreets = itemView.findViewById(R.id.address_streets);
            tvAddressDistrict = itemView.findViewById(R.id.address_district);
            tvTotalPrice = itemView.findViewById(R.id.sale_total_price);

            mListener = listener;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null)
                mListener.OnClick(v, getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            if(mListener != null)
                mListener.OnLongClick(v, getAdapterPosition());
            return true;
        }
    }//end of SaleViewHolder class

}


