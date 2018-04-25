package com.berstek.orderingappadmin.view.menus;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.berstek.orderingappadmin.R;
import com.berstek.orderingappadmin.callback.RecviewItemClickCallback;
import com.berstek.orderingappadmin.model.Menu;
import com.berstek.orderingappadmin.utils.Utils;

import java.util.ArrayList;


public class MenusAdapter extends RecyclerView.Adapter<MenusAdapter.ListHolder> {

  private ArrayList<Menu> menus;
  private LayoutInflater inflater;
  private Utils utils;
  private RecviewItemClickCallback itemClickCallback;

  public MenusAdapter(Context context, ArrayList<Menu> menus) {
    this.menus = menus;
    inflater = LayoutInflater.from(context);
    utils = new Utils(context);
  }

  @Override
  public ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new ListHolder(inflater.inflate(R.layout.viewholder_menu, parent, false));
  }

  @Override
  public void onBindViewHolder(ListHolder holder, int position) {
    Menu menu = menus.get(position);

    holder.titleTxt.setText(menu.getTitle());
    holder.priceTxt.setText("P " + menu.getPrice());
    holder.priorityTxt.setText("#" + menu.getPriority());
    utils.loadImage(menu.getImg_url(), holder.menuImg, 50);

    holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));


    if(!menu.isAvailable()) {
      holder.itemView.setBackgroundColor(Color.parseColor("#ffa4a2"));
    }
  }

  @Override
  public int getItemCount() {
    return menus.size();
  }

  class ListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView titleTxt, priceTxt, priorityTxt;
    private ImageView menuImg, editImg;

    public ListHolder(View itemView) {
      super(itemView);

      titleTxt = itemView.findViewById(R.id.titleTxt);
      priceTxt = itemView.findViewById(R.id.priceTxt);
      priorityTxt = itemView.findViewById(R.id.priorityTxt);
      menuImg = itemView.findViewById(R.id.menuImg);
      editImg = itemView.findViewById(R.id.editImg);
      editImg.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
      itemClickCallback.onRecviewItemClick(view, getAdapterPosition());
    }
  }

  public void setItemClickCallback(RecviewItemClickCallback itemClickCallback) {
    this.itemClickCallback = itemClickCallback;
  }
}
