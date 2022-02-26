package com.fpt.OnlineQuiz.service;

import com.fpt.OnlineQuiz.dto.BlogAdminDTO;
import com.fpt.OnlineQuiz.dto.paging.Page;
import com.fpt.OnlineQuiz.dto.paging.PagingRequest;
import com.fpt.OnlineQuiz.model.Blog;

import java.util.ArrayList;

public interface BlogService {
    /**
     * Return list blog
     *
     * @param
     * @return
     */
    ArrayList<Blog> getAllBlog();

    Page<BlogAdminDTO> getAllBlogAdmin(PagingRequest pagingRequest);

    ArrayList<Blog> getBlogByIndexPage(int indexPage, int pageSize);

    Long countBlog();

    Blog getDetailBlog(Integer blogId);
}
