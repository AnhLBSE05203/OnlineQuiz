package com.fpt.OnlineQuiz.service;

import com.fpt.OnlineQuiz.dto.BlogAdminDto;
import com.fpt.OnlineQuiz.dto.paging.Page;
import com.fpt.OnlineQuiz.dto.paging.PagingRequest;
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
    Page<BlogAdminDto> getAllBlogAdmin(PagingRequest pagingRequest);
    ArrayList<Blog> getBlogByIndexPage(int indexPage);
    Long countBlog();
    Blog getDetailBlog(Integer blogId);
}
