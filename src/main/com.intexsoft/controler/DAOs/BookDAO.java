package controler.DAOs;

import controler.FindRequests.FindBookRequest;
import model.Book;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BookDAO {
    public void createRow(Connection connection, String bookname, String author, int costInByn, int countInStock) {
        Statement statement;
        try {
            UUID uuid = UUID.randomUUID();
            String query = "insert into book values ('" + uuid + "', '" + bookname + "', '" + author + "', " + costInByn + ", " + countInStock + ");";
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("Insert success");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void createBook(Connection connection, Book book) {
        Statement statement;
        try {
            String query = "insert into book values ('" + book.getBookId() + "', '" + book.getBookname() + "', '" + book.getAuthor() + "', " + book.getCostInByn() + ", " + book.getCountInStock() + ");";
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("Insert success");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public List<Book> readAll(Connection connection) {
        List<Book> list = new ArrayList<>();
        Statement statement;
        ResultSet rs = null;
        try {
            String query = "select * from book";
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next()) {
                Book book = new Book();
                book.setBookId(rs.getObject(1, UUID.class));
                book.setBookname(rs.getString(2));
                book.setAuthor(rs.getString(3));
                book.setCostInByn(rs.getInt(4));
                book.setCountInStock(rs.getInt(5));
                list.add(book);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public List<Book> find(Connection connection, FindBookRequest findBookRequest) {
        Statement statement;
        List<Book> list = new ArrayList<>();
        ResultSet rs = null;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("select * from book where ");

            if (!findBookRequest.getBookIds().isEmpty()) {
                sb.append("book_id ");
                if (findBookRequest.getBookIds().size() > 1) {
                    sb.append("in (");
                    for (UUID book_id : findBookRequest.getBookIds()) {
                        sb.append("'" + book_id + "', ");
                    }
                    sb.deleteCharAt(sb.lastIndexOf(","));
                    sb.append(") AND ");
                } else {
                    sb.append("='" + findBookRequest.getBookIds().get(0) + "' AND ");
                }
            }
            if (!findBookRequest.getBookBooknames().isEmpty()) {
                sb.append("bookname ");
                if (findBookRequest.getBookBooknames().size() > 1) {
                    sb.append("in (");
                    for (String bookname : findBookRequest.getBookBooknames()) {
                        sb.append("'" + bookname + "', ");
                    }
                    sb.deleteCharAt(sb.lastIndexOf(","));
                    sb.append(") AND ");
                } else {
                    sb.append("='" + findBookRequest.getBookBooknames().get(0) + "' AND ");
                }
            }
            if (!findBookRequest.getBookAuthors().isEmpty()) {
                sb.append("author ");
                if (findBookRequest.getBookAuthors().size() > 1) {
                    sb.append("in (");
                    for (String author : findBookRequest.getBookAuthors()) {
                        sb.append("'" + author + "', ");
                    }
                    sb.deleteCharAt(sb.lastIndexOf(","));
                    sb.append(") AND ");
                } else {
                    sb.append("='" + findBookRequest.getBookAuthors().get(0) + "' AND ");
                }
            }
            if (!findBookRequest.getBookCostInByns().isEmpty()) {
                sb.append("cost_in_byn ");
                if (findBookRequest.getBookCostInByns().size() > 1) {
                    sb.append("in (");
                    for (int cost_in_byn : findBookRequest.getBookCostInByns()) {
                        sb.append("'" + cost_in_byn + "', ");
                    }
                    sb.deleteCharAt(sb.lastIndexOf(","));
                    sb.append(") AND ");
                } else {
                    sb.append("='" + findBookRequest.getBookCostInByns().get(0) + "' AND ");
                }
            }
            if (!findBookRequest.getBookCountInStocks().isEmpty()) {
                sb.append("count_in_stock ");
                if (findBookRequest.getBookCountInStocks().size() > 1) {
                    sb.append("in (");
                    for (int count_in_stock : findBookRequest.getBookCountInStocks()) {
                        sb.append("'" + count_in_stock + "', ");
                    }
                    sb.deleteCharAt(sb.lastIndexOf(","));
                    sb.append(") AND ");
                } else {
                    sb.append("='" + findBookRequest.getBookCountInStocks().get(0) + "' AND ");
                }
            }

            sb.delete(sb.length() - 4, sb.length());
            sb.append(";");
            System.out.println(sb.toString());
            statement = connection.createStatement();
            rs = statement.executeQuery(sb.toString());
            while (rs.next()) {
                Book book = new Book();
                book.setBookId(rs.getObject(1, UUID.class));
                book.setBookname(rs.getString(2));
                book.setAuthor(rs.getString(3));
                book.setCostInByn(rs.getInt(4));
                book.setCountInStock(rs.getInt(5));
                list.add(book);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }


    public void update(Connection connection, FindBookRequest updateBookRequest, FindBookRequest findBookRequest) {
        Statement statement;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("update book set ");

            if (!updateBookRequest.getBookIds().isEmpty())
                sb.append("book_id" + "='" + updateBookRequest.getBookIds().get(0) + "', ");
            if (!updateBookRequest.getBookBooknames().isEmpty())
                sb.append("bookname" + "='" + updateBookRequest.getBookBooknames().get(0) + "', ");
            if (!updateBookRequest.getBookAuthors().isEmpty())
                sb.append("author" + "='" + updateBookRequest.getBookAuthors().get(0) + "', ");
            if (!updateBookRequest.getBookCostInByns().isEmpty())
                sb.append("cost_in_byn" + "='" + updateBookRequest.getBookCostInByns().get(0) + "', ");
            if (!updateBookRequest.getBookCountInStocks().isEmpty())
                sb.append("count_in_stock" + "='" + updateBookRequest.getBookCountInStocks().get(0) + "', ");
            sb.deleteCharAt(sb.lastIndexOf(","));
            sb.append("where ");
            if (!findBookRequest.getBookIds().isEmpty()) {
                sb.append("book_id ");
                if (findBookRequest.getBookIds().size() > 1) {
                    sb.append("in (");
                    for (UUID book_id : findBookRequest.getBookIds()) {
                        sb.append("'" + book_id + "', ");
                    }
                    sb.deleteCharAt(sb.lastIndexOf(","));
                    sb.append(") AND ");
                } else {
                    sb.append("='" + findBookRequest.getBookIds().get(0) + "' AND ");
                }
            }
            if (!findBookRequest.getBookBooknames().isEmpty()) {
                sb.append("bookname ");
                if (findBookRequest.getBookBooknames().size() > 1) {
                    sb.append("in (");
                    for (String bookname : findBookRequest.getBookBooknames()) {
                        sb.append("'" + bookname + "', ");
                    }
                    sb.deleteCharAt(sb.lastIndexOf(","));
                    sb.append(") AND ");
                } else {
                    sb.append("='" + findBookRequest.getBookBooknames().get(0) + "' AND ");
                }
            }
            if (!findBookRequest.getBookAuthors().isEmpty()) {
                sb.append("author ");
                if (findBookRequest.getBookAuthors().size() > 1) {
                    sb.append("in (");
                    for (String author : findBookRequest.getBookAuthors()) {
                        sb.append("'" + author + "', ");
                    }
                    sb.deleteCharAt(sb.lastIndexOf(","));
                    sb.append(") AND ");
                } else {
                    sb.append("='" + findBookRequest.getBookAuthors().get(0) + "' AND ");
                }
            }
            if (!findBookRequest.getBookCostInByns().isEmpty()) {
                sb.append("cost_in_byn ");
                if (findBookRequest.getBookCostInByns().size() > 1) {
                    sb.append("in (");
                    for (int cost_in_byn : findBookRequest.getBookCostInByns()) {
                        sb.append("'" + cost_in_byn + "', ");
                    }
                    sb.deleteCharAt(sb.lastIndexOf(","));
                    sb.append(") AND ");
                } else {
                    sb.append("='" + findBookRequest.getBookCostInByns().get(0) + "' AND ");
                }
            }
            if (!findBookRequest.getBookCountInStocks().isEmpty()) {
                sb.append("count_in_stock ");
                if (findBookRequest.getBookCountInStocks().size() > 1) {
                    sb.append("in (");
                    for (int count_in_stock : findBookRequest.getBookCountInStocks()) {
                        sb.append("'" + count_in_stock + "', ");
                    }
                    sb.deleteCharAt(sb.lastIndexOf(","));
                    sb.append(") AND ");
                } else {
                    sb.append("='" + findBookRequest.getBookCountInStocks().get(0) + "' AND ");
                }
            }
            sb.delete(sb.length() - 4, sb.length());
            sb.append(";");
            System.out.println(sb.toString());
            statement = connection.createStatement();
            statement.executeUpdate(sb.toString());
            System.out.println("Data updated");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void updateBook(Connection connection, Book book, FindBookRequest findBookRequest) {
        Statement statement;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("update book set book_id='" + book.getBookId() + "', bookname='" + book.getBookname() + "', author='" + book.getAuthor() + "', cost_in_byn='" + book.getCostInByn() + "', count_in_stock='"+book.getCountInStock()+"' ");
            sb.append("where ");
            if (!findBookRequest.getBookIds().isEmpty()) {
                sb.append("book_id='" + findBookRequest.getBookIds().get(0) + "' AND ");
            }
            if (!findBookRequest.getBookBooknames().isEmpty()) {
                sb.append("bookname='" + findBookRequest.getBookBooknames().get(0) + "' AND ");
            }
            if (!findBookRequest.getBookAuthors().isEmpty()) {
                sb.append("author='" + findBookRequest.getBookAuthors().get(0) + "' AND ");
            }
            if (!findBookRequest.getBookCostInByns().isEmpty()) {
                sb.append("cost_in_byn='" + findBookRequest.getBookCostInByns().get(0) + "' AND ");
            }
            if (!findBookRequest.getBookCountInStocks().isEmpty()) {
                sb.append("count_in_stock='" + findBookRequest.getBookCountInStocks().get(0) + "' AND ");
            }
            sb.delete(sb.length() - 4, sb.length());
            sb.append(";");
            System.out.println(sb.toString());
            statement = connection.createStatement();
            statement.executeUpdate(sb.toString());
            System.out.println("Data updated");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void delete(Connection connection, FindBookRequest findBookRequest) {
        Statement statement;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("delete from book where ");
            if (!findBookRequest.getBookIds().isEmpty()) {
                sb.append("book_id ");
                if (findBookRequest.getBookIds().size() > 1) {
                    sb.append("in (");
                    for (UUID book_id : findBookRequest.getBookIds()) {
                        sb.append("'" + book_id + "', ");
                    }
                    sb.deleteCharAt(sb.lastIndexOf(","));
                    sb.append(") AND ");
                } else {
                    sb.append("='" + findBookRequest.getBookIds().get(0) + "' AND ");
                }
            }
            if (!findBookRequest.getBookBooknames().isEmpty()) {
                sb.append("bookname ");
                if (findBookRequest.getBookBooknames().size() > 1) {
                    sb.append("in (");
                    for (String bookname : findBookRequest.getBookBooknames()) {
                        sb.append("'" + bookname + "', ");
                    }
                    sb.deleteCharAt(sb.lastIndexOf(","));
                    sb.append(") AND ");
                } else {
                    sb.append("='" + findBookRequest.getBookBooknames().get(0) + "' AND ");
                }
            }
            if (!findBookRequest.getBookAuthors().isEmpty()) {
                sb.append("author ");
                if (findBookRequest.getBookAuthors().size() > 1) {
                    sb.append("in (");
                    for (String author : findBookRequest.getBookAuthors()) {
                        sb.append("'" + author + "', ");
                    }
                    sb.deleteCharAt(sb.lastIndexOf(","));
                    sb.append(") AND ");
                } else {
                    sb.append("='" + findBookRequest.getBookAuthors().get(0) + "' AND ");
                }
            }
            if (!findBookRequest.getBookCostInByns().isEmpty()) {
                sb.append("cost_in_byn ");
                if (findBookRequest.getBookCostInByns().size() > 1) {
                    sb.append("in (");
                    for (int cost_in_byn : findBookRequest.getBookCostInByns()) {
                        sb.append("'" + cost_in_byn + "', ");
                    }
                    sb.deleteCharAt(sb.lastIndexOf(","));
                    sb.append(") AND ");
                } else {
                    sb.append("='" + findBookRequest.getBookCostInByns().get(0) + "' AND ");
                }
            }

            if (!findBookRequest.getBookCountInStocks().isEmpty()) {
                sb.append("count_in_stock ");
                if (findBookRequest.getBookCountInStocks().size() > 1) {
                    sb.append("in (");
                    for (int count_in_stock : findBookRequest.getBookCountInStocks()) {
                        sb.append("'" + count_in_stock + "', ");
                    }
                    sb.deleteCharAt(sb.lastIndexOf(","));
                    sb.append(") AND ");
                } else {
                    sb.append("='" + findBookRequest.getBookCountInStocks().get(0) + "' AND ");
                }
            }
            sb.delete(sb.length() - 4, sb.length());
            sb.append(";");
            System.out.println(sb.toString());
            statement = connection.createStatement();
            statement.executeUpdate(sb.toString());
            System.out.println("Data deleted");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}