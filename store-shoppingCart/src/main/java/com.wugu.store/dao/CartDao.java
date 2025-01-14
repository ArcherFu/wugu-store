package com.wugu.store.dao;

import com.wugu.store.domain.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class CartDao {

    private final static String insertMessageSql = "insert into message values (?, ?, ?, ?, ?, ?)";

    private JdbcTemplate jdbcTemplate;

    private PhoneDao phoneDao;

    @Autowired
    public void setPhoneDao(PhoneDao phoneDao) {
        this.phoneDao = phoneDao;
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean add(String userName, String phoneName, int number) {
        Message message = new Message();
        UUID messageID = UUID.randomUUID();
        message.setMessageID(messageID);
        message.setUserName(userName);
        message.setPhoneName(phoneName);
        message.setPrice(phoneDao.select(phoneName).getPhonePrice());
        message.setNumber(number);
        message.setState(1);
        return jdbcTemplate.update(insertMessageSql, message.getMessageID(), message.getUserName(), message.getPhoneName(), message.getState()) == 1;
    }
}
