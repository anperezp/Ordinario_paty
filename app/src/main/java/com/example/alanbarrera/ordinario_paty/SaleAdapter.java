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

class SaleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    public ArrayList<SaleRestaurant> sales;
    public ArrayList<DinerAddress> dinerAddresses;
    private Context context;
    private IRecyclerviewClickListener mListener;
    boolean DEBUG_isCorrectData;

    public SaleAdapter(Context context, DeliveryOrdersViewModel orders, IRecyclerviewClickListener listener)
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
        SaleViewHolder viewHolder = (SaleViewHolder) holder;
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
        viewHolder.tvTotalPrice.setText("$" + String.valueOf(currentSale.SaleTotals.Total));
        viewHolder.tvAddress.setText(currentAddress.District + ", " + currentAddress.City);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_sale, parent, false);

        SaleViewHolder viewHolder = new SaleViewHolder(itemView, mListener);

        return viewHolder;
    }

    public class SaleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener
    {
        private IRecyclerviewClickListener mListener;

        // Campos respectivos de un item
        public TextView tvFolio;
        public TextView tvAddress;
        public TextView tvTotalPrice;

        SaleViewHolder(View itemView, IRecyclerviewClickListener listener)
        {
            super(itemView);
            tvFolio = itemView.findViewById(R.id.sale_folio);
            tvAddress = itemView.findViewById(R.id.sale_address);
            tvTotalPrice = itemView.findViewById(R.id.sale_total_price);

            mListener = listener;
            itemView.setOnClickListener(this);
            //itemView.Click += (s, e) => clickListener(AdapterPosition);
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


