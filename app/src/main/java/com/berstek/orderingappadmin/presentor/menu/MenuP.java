package com.berstek.orderingappadmin.presentor.menu;

import com.berstek.orderingappadmin.firebase_da.MenuDA;
import com.berstek.orderingappadmin.model.Menu;

public class MenuP implements MenuDA.MenuDaCallback {

  private MenuDA menuDA;

  private MenuPCallback menuPCallback;

  public MenuP() {
    menuDA = new MenuDA();
    menuDA.setMenuDaCallback(this);
  }

  public void queryMenus(MenuDA.MenuType menuType) {
    menuDA.queryMenus(menuType);
  }

  @Override
  public void onMenuLoaded(Menu menu) {
    menuPCallback.onMenuLoaded(menu);
  }

  @Override
  public void onMenuEdited(Menu menu) {
    menuPCallback.onMenuEdited(menu);
  }

  @Override
  public void onChildRemoved(Menu menu) {
    menuPCallback.onChildRemoved(menu);
  }

  @Override
  public void onProgress(double percentage) {

  }

  public interface MenuPCallback {
    void onMenuLoaded(Menu menu);

    void onMenuEdited(Menu menu);

    void onChildRemoved(Menu menu);
  }

  public void setMenuPCallback(MenuPCallback menuPCallback) {
    this.menuPCallback = menuPCallback;
  }
}
