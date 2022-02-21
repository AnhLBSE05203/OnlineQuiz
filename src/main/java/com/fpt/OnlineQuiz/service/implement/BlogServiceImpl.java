package com.fpt.OnlineQuiz.service.implement;

import com.fpt.OnlineQuiz.dao.BlogRepository;
import com.fpt.OnlineQuiz.dto.BlogAdminDto;
import com.fpt.OnlineQuiz.dto.paging.Page;
import com.fpt.OnlineQuiz.dto.paging.PagingRequest;
import com.fpt.OnlineQuiz.model.Blog;
import com.fpt.OnlineQuiz.service.BlogService;
import com.fpt.OnlineQuiz.utils.Utils;
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
    public Page<BlogAdminDto> getAllBlogAdmin(PagingRequest pagingRequest) {
        List<Blog> listBlog = blogRepository.getAllBlogAdmin(pagingRequest);
        long count = blogRepository.getAllBlogAdminCountTotalRecord(pagingRequest);
        List<BlogAdminDto> listBlogAdminDTO = new ArrayList<>();
        for (Blog blog: listBlog) {
            BlogAdminDto blogAdminDto = new BlogAdminDto();
            Utils.copyNonNullProperties(blog, blogAdminDto);
            listBlogAdminDTO.add(blogAdminDto);
        }
        Page<BlogAdminDto> page = new Page<>(listBlogAdminDTO);
        page.setRecordsFiltered((int) count);
        page.setRecordsTotal((int) count);
        page.setDraw(pagingRequest.getDraw());
        return page;
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
