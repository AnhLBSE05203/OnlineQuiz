package com.fpt.OnlineQuiz.service;

import com.fpt.OnlineQuiz.model.Blog;

import java.util.ArrayList;
import java.util.List;

public interface BlogService {
    /**
     * Return list blog
     * @param
     * @return
     */
    ArrayList<Blog> getAllBlog();
    List<Blog> getAllBlogAdmin();
    ArrayList<Blog> getBlogByIndexPage(int indexPage);
    Long countBlog();
    Blog getDetailBlog(Integer blogId);
}
