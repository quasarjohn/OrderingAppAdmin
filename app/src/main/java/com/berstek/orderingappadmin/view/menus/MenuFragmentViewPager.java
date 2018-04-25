package com.berstek.orderingappadmin.view.menus;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.berstek.orderingappadmin.R;
import com.berstek.orderingappadmin.firebase_da.DA;
import com.berstek.orderingappadmin.model.Menu;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragmentViewPager extends Fragment implements View.OnClickListener {

  private View view;
  private TabLayout tabLayout;
  private ViewPager viewPager;

  private ArrayList<Fragment> fragments;

  private FloatingActionButton addFab, priorityFab, priceFab, nameFab;
  private FloatingActionMenu fam;

  private AddMenuDialogFragment addMenuDialogFragment;

  private OnAddMenuClickListener onAddMenuClickListener;

  private int position = 0;

  public MenuFragmentViewPager() {
    // Required empty public constructor
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    onAddMenuClickListener = (OnAddMenuClickListener) context;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    view = inflater.inflate(R.layout.fragment_menu_fragment_view_pager, container, false);

    getActivity().setTitle("Menu");

    //start init viewpager and its menu fragments
    tabLayout = view.findViewById(R.id.tabLayout);
    viewPager = view.findViewById(R.id.viewpager);

    fragments = new ArrayList<>();

    Fragment menuFragment = new MenuFragment();
    Bundle b1 = new Bundle();
    b1.putString("menu", "menus");
    b1.putInt("position", 0);
    menuFragment.setArguments(b1);

    Fragment drinksFragment = new MenuFragment();
    Bundle b2 = new Bundle();
    b2.putString("menu", "drinks");
    b2.putInt("position", 1);
    drinksFragment.setArguments(b2);

    Fragment dessertsFragment = new MenuFragment();
    Bundle b3 = new Bundle();
    b3.putInt("position", 2);
    b3.putString("menu", "desserts");
    dessertsFragment.setArguments(b3);

    fragments.add(menuFragment);
    fragments.add(drinksFragment);
    fragments.add(dessertsFragment);

    tabLayout.addTab(tabLayout.newTab());
    tabLayout.addTab(tabLayout.newTab());
    tabLayout.addTab(tabLayout.newTab());

    viewPager.setAdapter(new ViewPagerAdapter(getFragmentManager()));
    viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    tabLayout.setupWithViewPager(viewPager);

    tabLayout.getTabAt(0).setText("MAIN MENU");
    tabLayout.getTabAt(1).setText("DRINKS");
    tabLayout.getTabAt(2).setText("DESSERTS");

    tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
      @Override
      public void onTabSelected(TabLayout.Tab tab) {
        position = tab.getPosition();
        viewPager.setCurrentItem(tab.getPosition());
      }

      @Override
      public void onTabUnselected(TabLayout.Tab tab) {

      }

      @Override
      public void onTabReselected(TabLayout.Tab tab) {

      }
    });
    //end init viewpager and its menu fragments

    //start init fab
    addFab = view.findViewById(R.id.addFab);
    priceFab = view.findViewById(R.id.priceFab);
    priorityFab = view.findViewById(R.id.priorityFab);
    nameFab = view.findViewById(R.id.nameFab);
    fam = view.findViewById(R.id.fam);

    addFab.setOnClickListener(this);
    priceFab.setOnClickListener(this);
    priorityFab.setOnClickListener(this);
    nameFab.setOnClickListener(this);
    //end init fab

    return view;
  }

  @Override
  public void onClick(View view) {
    int id = view.getId();

    new DA().log("CLICKED");

    if (id == R.id.addFab) {
      onAddMenuClickListener.onAddMenu(position);
    }
    else {
      OnSortListener onSortListener =
          (OnSortListener) fragments.get(position);
      if (id == R.id.priceFab) {
        onSortListener.onSort(Menu.priceComparator);
      } else if (id == R.id.priorityFab) {
        onSortListener.onSort(Menu.priorityComparator);
      } else if (id == R.id.nameFab) {
        onSortListener.onSort(Menu.titleComparator);
      }
    }
    fam.close(true);
  }

  class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
      super(fm);
    }

    @Override
    public Fragment getItem(int position) {
      return fragments.get(position);
    }

    @Override
    public int getCount() {
      return fragments.size();
    }
  }

  public interface OnSortListener {
    void onSort(Comparator comparator);
  }

  public interface OnAddMenuClickListener {
    void onAddMenu(int position);
  }

  public void setOnAddMenuClickListener(OnAddMenuClickListener onAddMenuClickListener) {
    this.onAddMenuClickListener = onAddMenuClickListener;
  }
}
