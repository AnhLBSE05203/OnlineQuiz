package com.fpt.OnlineQuiz.service;

import com.fpt.OnlineQuiz.model.Token;

public interface TokenService {
    void deleteToken(Token token);

    Token findByTokenString(String tokenString);
}
