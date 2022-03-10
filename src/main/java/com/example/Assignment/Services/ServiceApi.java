package com.example.Assignment.Services;

import com.example.Assignment.Pojo.Books;
import com.example.Assignment.Pojo.User;
import com.example.Assignment.Pojo.UserBookHistory;

public interface ServiceApi {
    public void addBook(Books books);

    public void addUser(User user);

    public void addIssue(UserBookHistory userBookHistory);

    public UserBookHistory addReturnDate(UserBookHistory userBookHistory);

    public Books getPopularBook();

    public User getUserWithMaxBook();
}

