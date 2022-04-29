package com.example.demo.users;

public interface SqlService {

    String getKey(String key) throws SqlRetrievalFailureException;
}
