package com.berstek.orderingappadmin.view.menus;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.berstek.orderingappadmin.R;
import com.berstek.orderingappadmin.firebase_da.DA;
import com.berstek.orderingappadmin.firebase_da.MenuDA;
import com.berstek.orderingappadmin.model.Menu;
import com.berstek.orderingappadmin.utils.Utils;
import com.berstek.orderingappadmin.view.global.ConfirmationDialogFragment;

public class EditMenuDialogFragment extends AddMenuDialogFragment {

  private Menu m;

  int p;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    m = (Menu) getArguments().getParcelableArrayList("menu").get(0);
    edit = true;

    super.onCreateView(inflater, container, savedInstanceState);

    p = getArguments().getInt("position");

    titleEdit.setText(m.getTitle());
    detailsEdit.setText(m.getDetails());
    priorityEdit.setText(m.getPriority() + "");
    priceEdit.setText(m.getPrice() + "");

    super.menu.setImg_url(m.getImg_url());

    if (m.isAvailable()) {
      availableCB.setChecked(true);
    } else {
      availableCB.setChecked(false);
    }

    new Utils(getActivity()).loadImage(m.getImg_url(), previewImg, 200, true);

    imgReady = true;

    return view;
  }

  @Override
  void addMenu() {
    super.menu.setKey(m.getKey());

    if (availableCB.isChecked())
      super.menu.setAvailable(true);
    else
      super.menu.setAvailable(false);

    super.addMenu();
  }

  @Override
  public void onClick(View view) {
    super.onClick(view);

    if (view.getId() == R.id.deleteBtn) {
      final DeleteMenuConfirmation deleteMenuConfirmation = new DeleteMenuConfirmation();
      deleteMenuConfirmation.show(getActivity().getFragmentManager(), null);

      deleteMenuConfirmation.setConfirmationDialogCallback(new ConfirmationDialogFragment.ConfirmationDialogCallback() {
        @Override
        public void onContinue() {

          MenuDA.MenuType menuType = MenuDA.MenuType.MENUS;

          switch (p) {
            case 0:
              menuType = MenuDA.MenuType.MENUS;
              break;
            case 1:
              menuType = MenuDA.MenuType.DRINKS;
              break;
            case 2:
              menuType = MenuDA.MenuType.DESSERTS;
              break;
            default:
              break;
          }

          menuDA.deleteMenu(m.getKey(), menuType);
          EditMenuDialogFragment.this.dismiss();
        }

        @Override
        public void onCancel() {
          deleteMenuConfirmation.dismiss();
        }
      });
    }
  }
}
