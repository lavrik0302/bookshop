package com.intexsoft;

import com.intexsoft.controler.ConnectToDb;


import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        ConnectToDb dbFunctions = new ConnectToDb();
        Connection connection = dbFunctions.connect_to_db("bookshop", "postgres", "postgrespw");


    }
}
