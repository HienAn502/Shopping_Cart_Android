package com.ecommerce.ecommerceweb.service;

import com.ecommerce.ecommerceweb.model.AuthenticationToken;
import com.ecommerce.ecommerceweb.model.User;
import com.ecommerce.ecommerceweb.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationTokenService {
    @Autowired
    TokenRepository tokenRepository;
    public void saveTokenOfConfirmation(AuthenticationToken authenticationToken) {
        tokenRepository.save(authenticationToken);
    }

    public AuthenticationToken getToken(User user) {
        return tokenRepository.findByUser(user);
    }
}
