package com.berstek.orderingappadmin.view.menus;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.berstek.orderingappadmin.R;
import com.berstek.orderingappadmin.custom_classes.CustomDialogFragment;
import com.berstek.orderingappadmin.firebase_da.DA;
import com.berstek.orderingappadmin.firebase_da.MenuDA;
import com.berstek.orderingappadmin.model.Menu;
import com.berstek.orderingappadmin.utils.RequestCodes;
import com.berstek.orderingappadmin.utils.StringUtils;
import com.berstek.orderingappadmin.utils.Utils;
import com.bumptech.glide.util.Util;

import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddMenuDialogFragment extends CustomDialogFragment
    implements View.OnClickListener, MenuDA.MenuImgUploaderListener,
    CompoundButton.OnCheckedChangeListener {

  Button doneBtn, deleteBtn;
  EditText titleEdit, detailsEdit, priceEdit, priorityEdit;
  ImageView cameraImg, previewImg;
  CheckBox availableCB;

  Utils utils;

  MenuDA menuDA;
  Menu menu;

  TextView percentTxt;

  boolean available;

  int position;

  public boolean imgReady = false;

  public AddMenuDialogFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    view = inflater.inflate(R.layout.fragment_add_menu_dialog, container, false);

    utils = new Utils(getActivity());

    position = getArguments().getInt("position");

    menuDA = new MenuDA();
    menuDA.setMenuImgUploaderListener(this);
    menu = new Menu();

    titleEdit = view.findViewById(R.id.titleEdit);
    detailsEdit = view.findViewById(R.id.detailsEdit);
    priceEdit = view.findViewById(R.id.priceEdit);
    priorityEdit = view.findViewById(R.id.priorityEdit);
    deleteBtn = view.findViewById(R.id.deleteBtn);
    availableCB = view.findViewById(R.id.availableCB);
    percentTxt = view.findViewById(R.id.percentageTxt);

    previewImg = view.findViewById(R.id.previewImg);

    cameraImg = view.findViewById(R.id.camImg);
    cameraImg.setOnClickListener(this);


    doneBtn = view.findViewById(R.id.doneBtn);
    doneBtn.setOnClickListener(this);

    deleteBtn.setOnClickListener(this);
    availableCB.setOnCheckedChangeListener(this);

    return view;
  }


  @Override
  public void onClick(View view) {
    int id = view.getId();

    if (id == R.id.doneBtn) {
      if (noEmptyFields()) {
        if (imgReady) {
          addMenu();
        } else {
          Toast.makeText(getActivity(), "Please upload an image of the menu", Toast.LENGTH_LONG).show();
        }
      } else {
        Toast.makeText(getActivity(), "Make sure no field is empty", Toast.LENGTH_LONG).show();
      }

    } else if (id == R.id.deleteBtn) {

    } else {
      openFileChooser();
    }
  }

  private boolean noEmptyFields() {
    if (titleEdit.getText().toString().length() == 0)
      return false;

    if (priorityEdit.getText().toString().length() == 0)
      return false;

    if (priceEdit.getText().toString().length() == 0)
      return false;

    if (detailsEdit.getText().toString().length() == 0)
      return false;

    return true;
  }

  boolean edit = false;

  void addMenu() {
    MenuDA.MenuType menuType = MenuDA.MenuType.MENUS;

    switch (position) {
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

    menu.setTitle(titleEdit.getText().toString());
    menu.setDetails(detailsEdit.getText().toString());
    menu.setPrice(Double.parseDouble(priceEdit.getText().toString()));
    menu.setPriority(Integer.parseInt(priorityEdit.getText().toString()));

    if (availableCB.isChecked())
      menu.setAvailable(true);
    else
      menu.setAvailable(false);


    if (!edit) {
      menuDA.addMenu(menu, menuType);
    } else {
      menuDA.editMenu(menu, menuType);
    }

    dismiss();
  }

  private void openFileChooser() {
    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
    Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    Intent chooserIntent = Intent.createChooser(intent, "Select File");
    chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});
    startActivityForResult(chooserIntent, RequestCodes.PICK_FILE);
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == RequestCodes.PICK_FILE) {
      menuDA.uploadMenuImage(data, UUID.randomUUID().toString(), getActivity());
    }
  }

  @Override
  public void onImageUploaded(String url) {
    menu.setImg_url(url);
    imgReady = true;
    utils.loadImage(url, previewImg, 300, true);
  }

  @Override
  public void onImageUpdate(double percentage) {
    percentTxt.setVisibility(View.VISIBLE);
    percentTxt.setText(StringUtils.formatDf00(percentage) + "%");
  }

  @Override
  public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
    available = b;
  }
}
