package com.example.demomutijdbc.service;

import com.example.demomutijdbc.exception.RollbackException;

public interface WxService {

    void insertRecord();

    void insertThenRollback() throws RollbackException;

    void invokeInsertThenRollback() throws RollbackException;
}
