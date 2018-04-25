package com.berstek.orderingappadmin.view.menus;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.berstek.orderingappadmin.R;
import com.berstek.orderingappadmin.callback.RecviewItemClickCallback;
import com.berstek.orderingappadmin.firebase_da.DA;
import com.berstek.orderingappadmin.firebase_da.MenuDA;
import com.berstek.orderingappadmin.model.Menu;
import com.berstek.orderingappadmin.presentor.menu.MenuP;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment implements MenuP.MenuPCallback,
    MenuFragmentViewPager.OnSortListener, RecviewItemClickCallback {

  private View view;
  private RecyclerView menusRecview;

  private ArrayList<Menu> menus;

  private MenuP menuP;

  private MenusAdapter menusAdapter;

  private String menu;

  private int p;

  private Comparator comparator = Menu.titleComparator;

  public MenuFragment() {
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    view = inflater.inflate(R.layout.fragment_menu, container, false);

    menu = getArguments().getString("menu");
    p = getArguments().getInt("position");

    menusRecview = view.findViewById(R.id.menusRecview);
    menusRecview.setLayoutManager(new LinearLayoutManager(getContext()));


    menus = new ArrayList<>();
    menusAdapter = new MenusAdapter(getContext(), menus);
    menusAdapter.setItemClickCallback(this);
    menusRecview.setAdapter(menusAdapter);

    MenuDA.MenuType menuType;

    switch (menu) {
      case "menus":
        menuType = MenuDA.MenuType.MENUS;
        break;
      case "drinks":
        menuType = MenuDA.MenuType.DRINKS;
        break;
      case "desserts":
        menuType = MenuDA.MenuType.DESSERTS;
        break;
      default:
        menuType = MenuDA.MenuType.MENUS;
        break;
    }

    menuP = new MenuP();
    menuP.setMenuPCallback(this);
    menuP.queryMenus(menuType);

    return view;
  }

  @Override
  public void onMenuLoaded(Menu menu) {
    menus.add(menu);
    Collections.sort(menus, comparator);
    menusAdapter.notifyDataSetChanged();
  }

  @Override
  public void onMenuEdited(Menu menu) {
    for (int i = 0; i < menus.size(); i++) {
      if (menus.get(i).getKey().equals(menu.getKey())) {
        menus.set(i, menu);
        menusAdapter.notifyItemChanged(i);
      }
    }


  }

  @Override
  public void onChildRemoved(Menu menu) {
    for (int i = 0; i < menus.size(); i++) {
      if (menus.get(i).getKey().equals(menu.getKey())) {
        menus.remove(i);
        menusAdapter.notifyItemRemoved(i);
        break;
      }
    }
  }

  @Override
  public void onSort(Comparator comparator) {
    this.comparator = comparator;
    Collections.sort(menus, comparator);
    menusAdapter.notifyDataSetChanged();
  }

  @Override
  public void onRecviewItemClick(View view, int pos) {
    Menu menu = menus.get(pos);

    ArrayList m = new ArrayList<>();
    m.add(menu);
    Bundle bundle = new Bundle();
    bundle.putParcelableArrayList("menu", m);
    bundle.putInt("position", p);
    new DA().log(p);

    EditMenuDialogFragment dialogFragment = new EditMenuDialogFragment();
    dialogFragment.setArguments(bundle);
    dialogFragment.show(getActivity().getFragmentManager(), null);
  }
}
