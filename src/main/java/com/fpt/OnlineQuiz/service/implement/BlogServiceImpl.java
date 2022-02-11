package com.fpt.OnlineQuiz.service.implement;

import com.fpt.OnlineQuiz.dao.BlogRepository;
import com.fpt.OnlineQuiz.model.Blog;
import com.fpt.OnlineQuiz.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Override
    public ArrayList<Blog> getAllBlog() {
        ArrayList<Blog> listBlog = blogRepository.getAllBlog();
        return listBlog;
    }
}
