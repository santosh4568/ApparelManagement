package com.apparel.apparelmanagement.Service;

import com.apparel.apparelmanagement.Model.User;
import com.apparel.apparelmanagement.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepo userRepo;

    public boolean validateLogin(String username, String password) {
        return userRepo.findByUsernameAndPassword(username, password) != null;
    }
    public User findByUsername(String username) {
        return userRepo.findByUsername(username);
    }
}
