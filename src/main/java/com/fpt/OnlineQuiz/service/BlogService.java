package com.fpt.OnlineQuiz.service;

import com.fpt.OnlineQuiz.model.Blog;

import java.util.ArrayList;

public interface BlogService {
    /**
     * Return list blog
     * @param
     * @return
     */
    ArrayList<Blog> getAllBlog();
    ArrayList<Blog> getBlogByIndexPage(int indexPage);
    Long countBlog();
    Blog getDetailBlog(Integer blogId);
}
