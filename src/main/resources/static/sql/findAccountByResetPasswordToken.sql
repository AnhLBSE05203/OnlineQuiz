SELECT a FROM Account a
inner join Token t on a.id = t.account.id
WHERE t.tokenString = :token AND t.tokenType = 'TOKEN_RESET_PASSWORD'