package com.seaico.corebankingapplication.controllers.v1;

import com.seaico.corebankingapplication.models.User;
import com.seaico.corebankingapplication.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class BaseController {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    public String getUsername() {
        if (authentication != null && authentication.isAuthenticated())
            return authentication.getName();
        else
            return null;
    }

//    public User getUser() {
//        if (authentication != null && authentication.isAuthenticated())
//            return authentication.getName();
//        else
//            return null;
//    }
}
