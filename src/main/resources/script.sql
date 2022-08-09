create database bookshop;
\c bookshop
create table person (
                        person_id uuid PRIMARY KEY,
                        name VARCHAR(255),
                        surname VARCHAR(255),
                        mobilenumber VARCHAR(13)
);

create table book (
                      book_id uuid PRIMARY KEY,
                      bookname VARCHAR(255),
                      author VARCHAR(255),
                      cost_in_byn int,
                      count_in_stock int
);

create table cart (
                      cart_id uuid,
                      person_id uuid,
                      cart_name VARCHAR (255),
                      primary key(cart_id),
                      foreign key (person_id) references person(person_id)
                          ON UPDATE CASCADE
                          ON DELETE CASCADE,
                      UNIQUE(person_id)
);

create table cart_has_book  (
                                cart_id uuid,
                                book_id uuid,
                                book_count int,
                                foreign key (cart_id) references cart(cart_id)
                                    ON UPDATE CASCADE
                                    ON DELETE CASCADE,
                                foreign key (book_id) references book(book_id)
                                    ON UPDATE CASCADE
                                    ON DELETE CASCADE,
                                UNIQUE (cart_id, book_id)
);

create table person_order (
                              order_id uuid,
                              person_id uuid,
                              adress VARCHAR (255),
                              status_id int,
                              primary key(order_id),
                              foreign key(person_id) references person (person_id)
                                  ON UPDATE CASCADE
                                  ON DELETE CASCADE,
                              foreign key(status_id) references order_status (status_id)
);

create table person_order_has_book  (
                                        order_id uuid,
                                        book_id uuid,
                                        book_count int,
                                        foreign key (order_id) references person_order(order_id)
                                            ON UPDATE CASCADE
                                            ON DELETE CASCADE,
                                        foreign key (book_id) references book(book_id),
                                        UNIQUE (order_id, book_id)
);
create table order_status(
                             status_id int,
                             string_status VARCHAR(255),
                             primary key (status_id)
);
