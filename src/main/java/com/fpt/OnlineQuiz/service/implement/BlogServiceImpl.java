package com.fpt.OnlineQuiz.service.implement;

import com.fpt.OnlineQuiz.dao.BlogRepository;
import com.fpt.OnlineQuiz.model.Blog;
import com.fpt.OnlineQuiz.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Override
    public ArrayList<Blog> getAllBlog() {
        ArrayList<Blog> listBlog = blogRepository.getAllBlog();
        return listBlog;
    }

    @Override
    public List<Blog> getAllBlogAdmin() {
        return blogRepository.getAllBlogAdmin();
    }

    @Override
    public Blog getDetailBlog(Integer blogId) {
        Blog blog = blogRepository.getDetailBlog(blogId);
        return blog;
    }

    @Override
    public ArrayList<Blog> getBlogByIndexPage(int pageIndex) {
        ArrayList<Blog> listBlog = blogRepository.getBlogByIndexPage(pageIndex);
        return listBlog;
    }

    @Override
    public Long countBlog() {
        long count = blogRepository.countBlog();
        return count;
    }
}
