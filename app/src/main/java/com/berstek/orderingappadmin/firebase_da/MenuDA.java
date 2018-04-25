package com.berstek.orderingappadmin.firebase_da;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.berstek.orderingappadmin.model.Menu;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class MenuDA extends DA {

  private StorageReference storageRef;
  private MenuDaCallback menuDaCallback;
  private MenuImgUploaderListener menuImgUploaderListener;

  public MenuDA() {
    storageRef = FirebaseStorage.getInstance().getReference();
  }

  public void queryMenus(MenuType menuType) {
    rootRef.child(menuType.toString().toLowerCase()).
        addChildEventListener(new ChildEventListener() {
          @Override
          public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            String key = dataSnapshot.getKey();
            Menu m = dataSnapshot.getValue(Menu.class);
            m.setKey(key);
            menuDaCallback.onMenuLoaded(m);
          }

          @Override
          public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            String key = dataSnapshot.getKey();
            Menu m = dataSnapshot.getValue(Menu.class);
            m.setKey(key);
            menuDaCallback.onMenuEdited(m);
          }

          @Override
          public void onChildRemoved(DataSnapshot dataSnapshot) {
            String key = dataSnapshot.getKey();
            Menu m = dataSnapshot.getValue(Menu.class);
            m.setKey(key);
            menuDaCallback.onChildRemoved(m);
          }

          @Override
          public void onChildMoved(DataSnapshot dataSnapshot, String s) {

          }

          @Override
          public void onCancelled(DatabaseError databaseError) {

          }
        });
  }

  public void addMenu(Menu menu, MenuType menuType) {
    rootRef.child(menuType.toString().toLowerCase()).push().setValue(menu);
  }


  public void editMenu(Menu menu, MenuType menuType) {

    rootRef.child(menuType.toString().toLowerCase()).child(menu.getKey()).setValue(menu);

  }

  public void deleteMenu(String key, MenuType menuType) {
    rootRef.child(menuType.toString().toLowerCase()).child(key).setValue(null);
  }

  public void uploadMenuImage(Intent data, String title, Activity activity) {
    StorageReference menuRef = storageRef.child("menus_img/" + title + ".jpg");


    UploadTask uploadTask = null;

    ContentResolver cr = activity.getContentResolver();
    try {

      InputStream is = cr.openInputStream(data.getData());
      final double size = is.available();
      uploadTask = menuRef.putStream(is);

      uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
        @Override
        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
          Uri downloadUri = taskSnapshot.getDownloadUrl();
          menuImgUploaderListener.onImageUploaded(downloadUri.toString());
        }
      }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
        @Override
        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
          long bytesTransferred = taskSnapshot.getBytesTransferred();
          double percentage = (bytesTransferred / size) * 100;
          menuImgUploaderListener.onImageUpdate(percentage);
        }
      }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {

        }
      });
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }


  }

  public interface MenuDaCallback {
    void onMenuLoaded(Menu menu);

    void onMenuEdited(Menu menu);

    void onChildRemoved(Menu menu);

    void onProgress(double percentage);

  }

  public interface MenuImgUploaderListener {
    void onImageUploaded(String url);

    void onImageUpdate(double percentage);
  }

  public void setMenuDaCallback(MenuDaCallback menuDaCallback) {
    this.menuDaCallback = menuDaCallback;
  }

  public void setMenuImgUploaderListener(MenuImgUploaderListener menuImgUploaderListener) {
    this.menuImgUploaderListener = menuImgUploaderListener;
  }

  public enum MenuType {
    DRINKS, DESSERTS, MENUS
  }
}
