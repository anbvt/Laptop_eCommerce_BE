package com.example.demo.listener;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.example.demo.constants.Constants;
import com.example.demo.entity.Users;
import com.example.demo.exception.InvalidRequestParameterException;
import com.example.demo.service.UsersService;

@Component
public class HandleTokenEvent {
    @Autowired
    UsersService usersService;

    @Async
    @EventListener
    public void resetToken(MyEmail email)
            throws InterruptedException, InvalidRequestParameterException {
        Optional<Users> user = usersService.findByEmail(email.getEmail());
        Thread.sleep(Constants.TIMETOKEN_ACTIVE);
        if (user.isPresent()) {
            user.get().setToken(null);
            usersService.update(user.get());
        }
    }
}
