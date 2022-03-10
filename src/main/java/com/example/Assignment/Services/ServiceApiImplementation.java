package com.example.Assignment.Services;

import com.example.Assignment.Entities.BooksEntities;
import com.example.Assignment.Entities.UserBookHistoryEntities;
import com.example.Assignment.Entities.UserEntities;
import com.example.Assignment.Exception.NoDataFoundException;
import com.example.Assignment.Pojo.Books;
import com.example.Assignment.Pojo.User;
import com.example.Assignment.Pojo.UserBookHistory;
import com.example.Assignment.Repository.BooksRepository;
import com.example.Assignment.Repository.UserBookHistoryRepository;
import com.example.Assignment.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ServiceApiImplementation implements ServiceApi {
    @Autowired
    UserRepository userRepository;

    @Autowired
    BooksRepository booksRepository;

    @Autowired
    UserBookHistoryRepository userBookHistoryRepository;


    public static <T> T mostCommon(List<T> list) {
        Map<T, Integer> map = new HashMap<>();

        for (T t : list) {


            Integer val = map.get(t);
            map.put(t, val == null ? 1 : val + 1);
        }

        Map.Entry<T, Integer> max = null;

        for (Map.Entry<T, Integer> e : map.entrySet()) {
            if (max == null || e.getValue() > max.getValue())
                max = e;
        }

        return max.getKey();
    }

    public static <T> Integer mostCommonFreq(List<T> list) {
        Map<T, Integer> map = new HashMap<>();

        for (T t : list) {
            Integer val = map.get(t);
            map.put(t, val == null ? 1 : val + 1);
        }

        Map.Entry<T, Integer> max = null;

        for (Map.Entry<T, Integer> e : map.entrySet()) {
            if (max == null || e.getValue() > max.getValue())
                max = e;
        }

        return max.getValue();
    }


    public int count() {
        Iterable<UserBookHistoryEntities> iterable = userBookHistoryRepository.findAll();
        List<UserBookHistory> list = new ArrayList<>();
        for (UserBookHistoryEntities i : iterable) {
            list.add(new UserBookHistory(i.getId(), i.getUserId(), i.getBookId(), i.getStartDate()));
        }
        ArrayList<Integer> bookid = new ArrayList<>();
        for (UserBookHistory i : list) {
            bookid.add(i.getBookId());
        }

        Integer maxId = mostCommon(bookid);
        return maxId;
    }

    public int countUser() {
        Iterable<UserBookHistoryEntities> iterable = userBookHistoryRepository.findAll();
        List<UserBookHistory> list = new ArrayList<>();
        for (UserBookHistoryEntities i : iterable) {
            list.add(new UserBookHistory(i.getId(), i.getUserId(), i.getBookId(), i.getStartDate()));
        }
        ArrayList<Integer> bookid = new ArrayList<>();
        for (UserBookHistory i : list) {
            bookid.add(i.getBookId());
        }

        Integer maxId = mostCommon(bookid);
        return maxId;
    }

    @Override
    public void addBook(Books books) {
        booksRepository.save(new BooksEntities(books.getId(), books.getName(), books.getWriter(), books.getSummary(),
                books.getCategory()));

    }

    @Override
    public void addUser(User user) {
        userRepository.save(new UserEntities(user.getId(), user.getName(), user.getAddress(), user.getPhoneNumber()));

    }

    public List<Books> getBooks() {
        Iterable<BooksEntities> booksEntitiesList = booksRepository.findAll();
        List<Books> list = new ArrayList<>();
        for (BooksEntities b : booksEntitiesList) {
            list.add(new Books(b.getId(), b.getName(), b.getWriter(), b.getSummary(), b.getCategory()));
        }
        return list;

    }

    @Override
    public void addIssue(UserBookHistory userBookHistory) {


        userBookHistoryRepository.save(new UserBookHistoryEntities(userBookHistory.getId(), userBookHistory.getUserId(),
                userBookHistory.getBookId(), userBookHistory.getStartDate()));

    }

    @Override
    public UserBookHistory addReturnDate(UserBookHistory userBookHistory) {
        String date = "";
        boolean isPresent = false;
        Iterable<UserBookHistoryEntities> iterable = userBookHistoryRepository.findAll();
        for (UserBookHistoryEntities i : iterable) {
            if (i.getBookId() == userBookHistory.getBookId() && i.getUserId() == userBookHistory.getUserId()) {
                date = i.getStartDate();
                isPresent = true;

            }
        }

        if (isPresent) {
            userBookHistoryRepository.save(new UserBookHistoryEntities(userBookHistory.getId(), userBookHistory.getUserId(),
                    userBookHistory.getBookId()
                    , date, userBookHistory.getEndDate()));
            return userBookHistory;
        } else {
            throw new NoDataFoundException(); //exception
        }
    }

    @Override
    public Books getPopularBook() {


        Integer maxId = count();

        Iterable<BooksEntities> books = booksRepository.findAll();
        Books books1 = new Books();

        for (BooksEntities i : books) {
            if (i.getId() == maxId) {
                books1.setId(i.getId());
                books1.setName(i.getName());
                books1.setWriter(i.getWriter());
                books1.setSummary(i.getSummary());
                books1.setCategory(i.getCategory());

            }
        }


        return books1;


    }

    @Override
    public User getUserWithMaxBook() {


        Integer max = countUser();

        Iterable<UserEntities> userEntities = userRepository.findAll();
        User user = new User();
        for (UserEntities i : userEntities) {
            if (i.getId().equals(max)) {
                user.setId(i.getId());
                user.setName(i.getName());
                user.setAddress(i.getAddress());
                user.setPhoneNumber(i.getPhoneNumber());

            }
        }
        return user;
    }
}
