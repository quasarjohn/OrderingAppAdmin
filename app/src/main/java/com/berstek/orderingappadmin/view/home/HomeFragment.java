package com.berstek.orderingappadmin.view.home;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.berstek.orderingappadmin.R;
import com.berstek.orderingappadmin.firebase_da.DA;
import com.berstek.orderingappadmin.model.BestSeller;
import com.berstek.orderingappadmin.model.Menu;
import com.berstek.orderingappadmin.model.Order;
import com.berstek.orderingappadmin.presentor.home.HomeP;
import com.berstek.orderingappadmin.utils.StringUtils;
import com.berstek.orderingappadmin.utils.Utils;
import com.berstek.orderingappadmin.view.global.DatePickerFragment;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements HomeP.SalesPCallback,
    DatePickerFragment.DatePickerFragmentCallback,
    View.OnClickListener {

  private View view;
  private TextView totalSalesTxt;

  private HomeP homeP;

  private Button dateBtn;

  private DatePickerFragment datePickerFragment;

  private HashMap<String, Double> bestSellers;
  private ArrayList<BestSeller> bestSellersList;

  private RecyclerView bestSellersRecview;
  private BestSellersAdapter bestSellersAdapter;

  public HomeFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    view = inflater.inflate(R.layout.fragment_home, container, false);
    getActivity().setTitle("Home");


    bestSellers = new HashMap<>();
    bestSellersList = new ArrayList<>();
    datePickerFragment = new DatePickerFragment();

    bestSellersRecview = view.findViewById(R.id.bestSellersRecview);
    totalSalesTxt = view.findViewById(R.id.totalSalesTxt);
    bestSellersRecview = view.findViewById(R.id.bestSellersRecview);
    bestSellersRecview.setLayoutManager(new LinearLayoutManager(getContext()));
    bestSellersAdapter = new BestSellersAdapter(bestSellersList, getContext());
    bestSellersRecview.setAdapter(bestSellersAdapter);

    dateBtn = view.findViewById(R.id.dateBtn);
    dateBtn.setOnClickListener(this);
    dateBtn.setText(StringUtils.getCurrentDateString());

    homeP = new HomeP();
    homeP.setSalesPCallback(this);
    try {
      homeP.queryTotalSales(Utils.getTimestapDateOnly());
    } catch (ParseException e) {
      e.printStackTrace();
    }

    return view;
  }

  private double totalSales = 0;

  @Override
  public void onOrderCompleted(Order oder) {

    bestSellersList.clear();
    ArrayList<Menu> menus = oder.getCart();

    for (Menu m : menus) {

      if (bestSellers.get(m.getTitle()) == null) {
        bestSellers.put(m.getTitle(), m.getPrice());
      } else {
        bestSellers.put(m.getTitle(), bestSellers.get(m.getTitle()) + m.getPrice());
      }

      totalSales += m.getPrice();
    }

    for (String k : bestSellers.keySet()) {
      BestSeller bestSeller = new BestSeller();
      bestSeller.setTotalPrice(bestSellers.get(k));
      bestSeller.setTitle(k);
      bestSellersList.add(bestSeller);
    }

    totalSalesTxt.setText(StringUtils.formatWithPesoSign(totalSales));

    bestSellersAdapter.notifyDataSetChanged();
  }

  @Override
  public void onClick(View view) {

    datePickerFragment.show(getActivity().getFragmentManager(), null);
    datePickerFragment.setDatePickerFragmentCallback(this);

  }

  @Override
  public void onDateSet(int year, int month, int day) {
    try {
      bestSellers.clear();
      totalSales = 0;
      dateBtn.setText(StringUtils.intToDateStringMMMMddyyyy(day, month, year));
      homeP.queryTotalSales(Utils.getTimestapDateOnly(StringUtils.intToDate(day, month, year)));
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  private void init() {
    //load data for today
  }
}
