package com.fpt.OnlineQuiz.service.implement;

import com.fpt.OnlineQuiz.dao.BlogRepository;
import com.fpt.OnlineQuiz.model.Account;
import com.fpt.OnlineQuiz.model.Blog;
import com.fpt.OnlineQuiz.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private BlogRepository blogRepository;

    @Override
    public void commentBlog(int blogId, String content) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account account = (Account) authentication.getPrincipal();
        Blog blog = blogRepository.getDetailBlog(blogId);
        blogRepository.addComment(blog, account, content);
    }

}
