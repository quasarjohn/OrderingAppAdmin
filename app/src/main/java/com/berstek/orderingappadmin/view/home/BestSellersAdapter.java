package com.berstek.orderingappadmin.view.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.berstek.orderingappadmin.R;
import com.berstek.orderingappadmin.model.BestSeller;
import com.berstek.orderingappadmin.utils.StringUtils;

import java.util.ArrayList;

public class BestSellersAdapter extends RecyclerView.Adapter<BestSellersAdapter.ListHolder> {

  private LayoutInflater inflater;
  private ArrayList<BestSeller> bestSellers;
  private Context context;

  public BestSellersAdapter(ArrayList<BestSeller> bestSellers, Context context) {
    this.bestSellers = bestSellers;
    this.context = context;
    inflater = LayoutInflater.from(context);
  }

  @Override
  public ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new ListHolder(inflater.inflate(R.layout.viewholder_bestseller, parent, false));
  }

  @Override
  public void onBindViewHolder(ListHolder holder, int position) {
    BestSeller bestSeller = bestSellers.get(position);

    holder.titleTxt.setText(bestSeller.getTitle());
    holder.priceTxt.setText(StringUtils.formatWithPesoSign(bestSeller.getTotalPrice()));
  }

  @Override
  public int getItemCount() {
    return bestSellers.size();
  }

  class ListHolder extends RecyclerView.ViewHolder {

    private TextView titleTxt, priceTxt;

    public ListHolder(View itemView) {
      super(itemView);

      titleTxt = itemView.findViewById(R.id.titleTxt);
      priceTxt = itemView.findViewById(R.id.priceTxt);
    }
  }
}
