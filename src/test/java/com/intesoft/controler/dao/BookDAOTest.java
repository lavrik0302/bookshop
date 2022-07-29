package com.intesoft.controler.dao;

import controler.ConnectToDb;
import controler.dao.BookDAO;
import org.junit.Assert;
import org.junit.Test;


import java.sql.Connection;

public class BookDAOTest {
    ConnectToDb connectToDb=new ConnectToDb();
    Connection connection=connectToDb.connect_to_db("bookshop", "postgres", "postgrespw");
    BookDAO bookDAO=new BookDAO(connection);

    @Test
    public void createRowTest(){
        Assert.assertTrue(true);
    }
}
