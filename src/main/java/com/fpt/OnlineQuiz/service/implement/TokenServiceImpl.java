package com.fpt.OnlineQuiz.service.implement;

import com.fpt.OnlineQuiz.dao.TokenRepository;
import com.fpt.OnlineQuiz.model.Token;
import com.fpt.OnlineQuiz.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {
    @Autowired
    private TokenRepository tokenRepository;

    @Override
    public void deleteToken(Token token) {
        tokenRepository.deleteToken(token);
    }

    @Override
    public Token findByTokenString(String tokenString) {
        return tokenRepository.findByTokenString(tokenString);
    }
}
