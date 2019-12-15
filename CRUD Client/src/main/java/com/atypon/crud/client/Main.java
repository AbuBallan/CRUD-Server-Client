package com.atypon.crud.client;

import com.atypon.crud.client.data.AppDataManager;
import com.atypon.crud.client.data.DataManager;
import com.atypon.crud.client.data.db.AppDBHelper;
import com.atypon.crud.client.data.db.DBHelper;
import com.atypon.crud.client.main.AppMainController;
import com.atypon.crud.client.main.MainController;
import com.atypon.crud.client.main.MainView;
import com.atypon.crud.client.model.Employee;
import com.google.gson.Gson;

/** Main class that runs the application through the main method. */
public class Main {
  public static void main(String[] args) {

    final Gson gson = new Gson();
    final DBHelper<Employee> dbHelper = AppDBHelper.getInstance(gson);
    DataManager dataManager = AppDataManager.getInstance(dbHelper);
    MainController controller = new AppMainController(dataManager);
    MainView mainView = new MainView(controller);
    mainView.start();
  }
}
