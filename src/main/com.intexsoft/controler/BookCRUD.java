package controler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.UUID;

public class BookCRUD {

    public void insert_row(Connection connection, String bookname, String author, int cost_in_byn, int count_in_stock) {
        Statement statement;
        try {
            UUID uuid = UUID.randomUUID();
            String query = "insert into book values ('" + uuid + "', '" + bookname + "', '" + author + "', " + cost_in_byn + ", " + count_in_stock + ");";
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("Insert success");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void read_data(Connection connection) {
        Statement statement;
        ResultSet rs = null;
        try {
            String query = "select * from book";
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next()) {
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    System.out.print(rs.getString(i) + " ");
                }
                System.out.println();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void search_by_book_id(Connection connection, UUID book_id) {
        Statement statement;
        ResultSet rs = null;
        try {
            String query = String.format("select * from book where book_id= '%s'", book_id);
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next()) {
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    System.out.print(rs.getString(i) + " ");
                }
                System.out.println();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public UUID select_book_id_by_bookname(Connection connection, String bookname) {
        Statement statement;
        UUID uuid = null;
        ResultSet rs = null;
        try {
            String query = String.format("select book_id from book where bookname= '%s'", bookname);
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            rs.next();
            uuid = rs.getObject(1, UUID.class);
        } catch (Exception e) {
            System.out.println(e);
        }
        return uuid;
    }
    public UUID select_book_id_by_author(Connection connection, String author) {
        Statement statement;
        UUID uuid = null;
        ResultSet rs = null;
        try {
            String query = String.format("select book_id from book where author= '%s'", author);
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            rs.next();
            uuid = rs.getObject(1, UUID.class);
        } catch (Exception e) {
            System.out.println(e);
        }
        return uuid;
    }
    public UUID select_book_id_by_cost_in_byn(Connection connection, int cost_in_byn) {
        Statement statement;
        UUID uuid = null;
        ResultSet rs = null;
        try {
            String query = String.format("select book_id from book where cost_in_byn= '%s'", cost_in_byn);
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            rs.next();
            uuid = rs.getObject(1, UUID.class);
        } catch (Exception e) {
            System.out.println(e);
        }
        return uuid;
    }
    public UUID select_book_id_by_count_in_stock(Connection connection, int count_in_stock) {
        Statement statement;
        UUID uuid = null;
        ResultSet rs = null;
        try {
            String query = String.format("select book_id from book where count_in_stock= '%s'", count_in_stock);
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            rs.next();
            uuid = rs.getObject(1, UUID.class);
        } catch (Exception e) {
            System.out.println(e);
        }
        return uuid;
    }

    public void search_by_bookname(Connection connection, String bookname) {
        Statement statement;
        ResultSet rs = null;
        try {
            String query = String.format("select * from book where bookname= '%s'", bookname);
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next()) {
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    System.out.print(rs.getString(i) + " ");
                }
                System.out.println();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public String select_bookname_by_book_id(Connection connection, UUID book_id) {
        Statement statement;
       String bookname = null;
        ResultSet rs = null;
        try {
            String query = String.format("select bookname from book where book_id= '%s'",book_id );
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            rs.next();
            bookname = rs.getString(1);
        } catch (Exception e) {
            System.out.println(e);
        }
        return bookname;
    }
    public String select_bookname_by_author(Connection connection, String author) {
        Statement statement;
        String bookname = null;
        ResultSet rs = null;
        try {
            String query = String.format("select bookname from book where author= '%s'",author );
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            rs.next();
            bookname = rs.getString(1);
        } catch (Exception e) {
            System.out.println(e);
        }
        return bookname;
    }
    public String select_bookname_by_cost_in_byn(Connection connection, int cost_in_byn) {
        Statement statement;
        String bookname = null;
        ResultSet rs = null;
        try {
            String query = String.format("select bookname from book where cost_in_byn= '%s'",cost_in_byn );
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            rs.next();
            bookname = rs.getString(1);
        } catch (Exception e) {
            System.out.println(e);
        }
        return bookname;
    }
    public String select_bookname_by_count_in_stock(Connection connection, int count_in_stock) {
        Statement statement;
        String bookname = null;
        ResultSet rs = null;
        try {
            String query = String.format("select bookname from book where count_in_stock= '%s'",count_in_stock );
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            rs.next();
            bookname = rs.getString(1);
        } catch (Exception e) {
            System.out.println(e);
        }
        return bookname;
    }

    public void search_by_author(Connection connection, String author) {
        Statement statement;
        ResultSet rs = null;
        try {
            String query = String.format("select * from book where author= '%s'", author);
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next()) {
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    System.out.print(rs.getString(i) + " ");
                }
                System.out.println();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public String select_author_by_book_id(Connection connection, UUID book_id) {
        Statement statement;
        String author = null;
        ResultSet rs = null;
        try {
            String query = String.format("select author from book where book_id= '%s'",book_id );
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            rs.next();
            author = rs.getString(1);
        } catch (Exception e) {
            System.out.println(e);
        }
        return author;
    }
    public String select_author_by_bookname(Connection connection, String bookname) {
        Statement statement;
        String author = null;
        ResultSet rs = null;
        try {
            String query = String.format("select author from book where bookname= '%s'",bookname );
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            rs.next();
            author= rs.getString(1);
        } catch (Exception e) {
            System.out.println(e);
        }
        return author;
    }
    public String select_author_by_cost_in_byn(Connection connection, int cost_in_byn) {
        Statement statement;
        String author = null;
        ResultSet rs = null;
        try {
            String query = String.format("select author from book where cost_in_byn= '%s'",cost_in_byn );
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            rs.next();
            author= rs.getString(1);
        } catch (Exception e) {
            System.out.println(e);
        }
        return author;
    }
    public String select_author_by_count_in_stock(Connection connection, int count_in_stock) {
        Statement statement;
        String author = null;
        ResultSet rs = null;
        try {
            String query = String.format("select author from book where count_in_stock= '%s'",count_in_stock );
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            rs.next();
            author = rs.getString(1);
        } catch (Exception e) {
            System.out.println(e);
        }
        return author;
    }

    public void search_by_cost_in_byn(Connection connection, int cost_in_byn) {
        Statement statement;
        ResultSet rs = null;
        try {
            String query = String.format("select * from book where cost_in_byn= '%s'", cost_in_byn);
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next()) {
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    System.out.print(rs.getString(i) + " ");
                }
                System.out.println();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public int select_cost_in_byn_by_book_id(Connection connection, UUID book_id) {
        Statement statement;
        int cost_in_byn = 0;
        ResultSet rs = null;
        try {
            String query = String.format("select cost_in_byn from book where book_id= '%s'",book_id );
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            rs.next();
            cost_in_byn= rs.getInt(1);
        } catch (Exception e) {
            System.out.println(e);
        }
        return cost_in_byn;
    }
    public int select_cost_in_byn_by_bookname(Connection connection, String bookname) {
        Statement statement;
        int cost_in_byn = 0;
        ResultSet rs = null;
        try {
            String query = String.format("select cost_in_byn from book where bookname= '%s'",bookname );
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            rs.next();
            cost_in_byn= rs.getInt(1);
        } catch (Exception e) {
            System.out.println(e);
        }
        return cost_in_byn;
    }
    public int select_cost_in_byn_by_author(Connection connection, String author) {
        Statement statement;
        int cost_in_byn = 0;
        ResultSet rs = null;
        try {
            String query = String.format("select cost_in_byn from book where author= '%s'",author );
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            rs.next();
            cost_in_byn= rs.getInt(1);
        } catch (Exception e) {
            System.out.println(e);
        }
        return cost_in_byn;
    }
    public int select_cost_in_byn_by_count_in_stock(Connection connection, int count_in_stock) {
        Statement statement;
        int cost_in_byn = 0;
        ResultSet rs = null;
        try {
            String query = String.format("select cost_in_byn from book where count_in_stock= '%s'",count_in_stock );
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            rs.next();
            cost_in_byn= rs.getInt(1);
        } catch (Exception e) {
            System.out.println(e);
        }
        return cost_in_byn;
    }


    public void search_by_count_in_stock(Connection connection, int count_in_stock) {
        Statement statement;
        ResultSet rs = null;
        try {
            String query = String.format("select * from book where count_in_stock= '%s'", count_in_stock);
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next()) {
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    System.out.print(rs.getString(i) + " ");
                }
                System.out.println();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public int select_count_in_stock_by_book_id(Connection connection, UUID book_id) {
        Statement statement;
        int count_in_stock = 0;
        ResultSet rs = null;
        try {
            String query = String.format("select count_in_stock from book where book_id= '%s'",book_id );
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            rs.next();
            count_in_stock= rs.getInt(1);
        } catch (Exception e) {
            System.out.println(e);
        }
        return count_in_stock;
    }
    public int select_count_in_stock_by_bookname(Connection connection, String bookname) {
        Statement statement;
        int count_in_stock = 0;
        ResultSet rs = null;
        try {
            String query = String.format("select count_in_stock from book where bookname= '%s'",bookname );
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            rs.next();
           count_in_stock= rs.getInt(1);
        } catch (Exception e) {
            System.out.println(e);
        }
        return count_in_stock;
    }
    public int select_count_in_stock_by_author(Connection connection, String author) {
        Statement statement;
        int count_in_stock = 0;
        ResultSet rs = null;
        try {
            String query = String.format("select count_in_stock from book where author= '%s'",author );
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            rs.next();
            count_in_stock= rs.getInt(1);
        } catch (Exception e) {
            System.out.println(e);
        }
        return count_in_stock;
    }
    public int select_count_in_stock_by_cost_in_byn(Connection connection, int cost_in_byn) {
        Statement statement;
        int count_in_stock= 0;
        ResultSet rs = null;
        try {
            String query = String.format("select count_in_stock from book where cost_in_byn= '%s'",cost_in_byn );
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            rs.next();
            count_in_stock= rs.getInt(1);
        } catch (Exception e) {
            System.out.println(e);
        }
        return count_in_stock;
    }

    public void update_book_id_by_book_id(Connection connection, UUID old_book_id, UUID new_book_id) {
        Statement statement;
        try {
            String query = String.format("update book set book_id='%s' where book_id='%s'", new_book_id, old_book_id);
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("data updated");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void update_bookname_by_book_id(Connection connection, UUID book_id, String new_bookname) {
        Statement statement;
        try {
            String query = String.format("update book set bookname='%s' where book_id='%s'", new_bookname, book_id);
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("data updated");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void update_bookname_by_bookname(Connection connection, String old_bookname, String new_bookname) {
        Statement statement;
        try {
            String query = String.format("update book set bookname='%s' where bookname='%s'", new_bookname, old_bookname);
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("data updated");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void update_bookname_by_author(Connection connection, String author, String new_bookname) {
        Statement statement;
        try {
            String query = String.format("update book set bookname='%s' where author='%s'", new_bookname, author);
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("data updated");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void update_bookname_by_cost_in_byn(Connection connection, int cost_in_byn, String new_bookname) {
        Statement statement;
        try {
            String query = String.format("update book set bookname='%s' where cost_in_byn='%s'", new_bookname, cost_in_byn);
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("data updated");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void update_bookname_by_count_in_stock(Connection connection, int count_in_stock, String new_bookname) {
        Statement statement;
        try {
            String query = String.format("update book set bookname='%s' where count_in_stock='%s'", new_bookname, count_in_stock);
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("data updated");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void update_author_by_book_id(Connection connection, UUID book_id, String new_author) {
        Statement statement;
        try {
            String query = String.format("update book set author='%s' where book_id='%s'", new_author, book_id);
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("data updated");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void update_author_by_bookname(Connection connection, String bookname, String new_author) {
        Statement statement;
        try {
            String query = String.format("update book set author='%s' where bookname='%s'", new_author, bookname);
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("data updated");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void update_author_by_author(Connection connection, String old_author, String new_author) {
        Statement statement;
        try {
            String query = String.format("update book set author='%s' where author='%s'", new_author, old_author);
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("data updated");
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    public void update_author_by_cost_in_byn(Connection connection, int cost_in_byn, String new_author) {
        Statement statement;
        try {
            String query = String.format("update book set author='%s' where cost_in_byn='%s'", new_author, cost_in_byn);
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("data updated");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void update_author_by_count_in_stock(Connection connection, int count_in_stock, String new_author) {
        Statement statement;
        try {
            String query = String.format("update book set author='%s' where count_in_stock='%s'", new_author, count_in_stock);
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("data updated");
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    public void update_cost_in_byn_by_book_id(Connection connection, UUID book_id, int new_cost_in_byn) {
        Statement statement;
        try {
            String query = String.format("update book set cost_in_byn='%s' where book_id='%s'", new_cost_in_byn, book_id);
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("data updated");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void update_cost_in_byn_by_bookname(Connection connection, String bookname, int new_cost_in_byn) {
        Statement statement;
        try {
            String query = String.format("update book set cost_in_byn='%s' where bookname='%s'", new_cost_in_byn, bookname);
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("data updated");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void update_cost_in_byn_by_author(Connection connection, String author, int new_cost_in_byn) {
        Statement statement;
        try {
            String query = String.format("update book set cost_in_byn='%s' where author='%s'", new_cost_in_byn, author);
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("data updated");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void update_cost_in_byn_by_cost_in_byn(Connection connection, int old_cost_in_byn, int new_cost_in_byn) {
        Statement statement;
        try {
            String query = String.format("update book set cost_in_byn='%s' where cost_in_byn='%s'", new_cost_in_byn, old_cost_in_byn);
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("data updated");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void update_cost_in_byn_by_count_in_stock(Connection connection, int count_in_stock, int new_cost_in_byn) {
        Statement statement;
        try {
            String query = String.format("update book set cost_in_byn='%s' where count_in_stock='%s'", new_cost_in_byn, count_in_stock);
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("data updated");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void update_count_in_stock_by_book_id(Connection connection, UUID book_id, int new_count_in_stock) {
        Statement statement;
        try {
            String query = String.format("update book set count_in_stock='%s' where book_id='%s'", new_count_in_stock, book_id);
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("data updated");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void update_count_in_stock_by_bookname(Connection connection, String bookname, int new_count_in_stock) {
        Statement statement;
        try {
            String query = String.format("update book set count_in_stock='%s' where bookname='%s'", new_count_in_stock, bookname);
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("data updated");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void update_count_in_stock_by_author(Connection connection, String author, int new_count_in_stock) {
        Statement statement;
        try {
            String query = String.format("update book set count_in_stock='%s' where author='%s'", new_count_in_stock, author);
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("data updated");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void update_count_in_stock_by_cost_in_byn(Connection connection, int cost_in_byn, int new_count_in_stock) {
        Statement statement;
        try {
            String query = String.format("update book set count_in_stock='%s' where cost_in_byn='%s'", new_count_in_stock, cost_in_byn);
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("data updated");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void update_count_in_stock_by_count_in_stock(Connection connection, int old_count_in_stock, int new_count_in_stock) {
        Statement statement;
        try {
            String query = String.format("update book set count_in_stock='%s' where count_in_stock='%s'", new_count_in_stock, old_count_in_stock);
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("data updated");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void delete_row_by_book_id(Connection connection, UUID book_id) {
        Statement statement;
        try {
            String query = String.format("delete from book where book_id= '%s'", book_id);
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("Data deleted");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void delete_row_by_bookname(Connection connection, String bookname) {
        Statement statement;
        try {
            String query = String.format("delete from book where bookname= '%s'", bookname);
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("Data deleted");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void delete_row_by_author(Connection connection, String author) {
        Statement statement;
        try {
            String query = String.format("delete from book where author= '%s'", author);
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("Data deleted");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void delete_row_by_cost_in_byn(Connection connection, int cost_in_byn) {
        Statement statement;
        try {
            String query = String.format("delete from book where cost_in_byn= '%s'", cost_in_byn);
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("Data deleted");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void delete_row_by_count_in_stock(Connection connection, int count_in_stock) {
        Statement statement;
        try {
            String query = String.format("delete from book where count_in_stock= '%s'", count_in_stock);
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("Data deleted");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
