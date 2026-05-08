package com.yourname.edumanage;

import com.yourname.edumanage.db.DBSetup;
import com.yourname.edumanage.features.main.MainView;

public class Main {
    public static void main(String[] args) {
        DBSetup.createTables();
        new MainView().start();
    }
}
