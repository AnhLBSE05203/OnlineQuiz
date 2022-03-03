package com.fpt.OnlineQuiz.service.implement;

import com.fpt.OnlineQuiz.dao.BlogRepository;
import com.fpt.OnlineQuiz.dto.BlogAdminDTO;
import com.fpt.OnlineQuiz.dto.paging.Page;
import com.fpt.OnlineQuiz.dto.paging.PagingRequest;
import com.fpt.OnlineQuiz.model.Blog;
import com.fpt.OnlineQuiz.service.BlogService;
import com.fpt.OnlineQuiz.utils.Constants;
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
    public Page<BlogAdminDTO> getAllBlogAdmin(PagingRequest pagingRequest) {
        List<Blog> listBlog = blogRepository.getAllBlogAdmin(pagingRequest);
        long count = blogRepository.getAllBlogAdminCountTotalRecord(pagingRequest);
        List<BlogAdminDTO> listBlogAdminDTO = new ArrayList<>();
        for (Blog blog : listBlog) {
            BlogAdminDTO blogAdminDto = new BlogAdminDTO();
            Utils.copyNonNullProperties(blog, blogAdminDto);
            blogAdminDto.setContent(blogAdminDto.getContent().substring(0, 100));
            blogAdminDto.setStatusStr(Constants.blogStatusConversion.get(blogAdminDto.getStatus()));
            listBlogAdminDTO.add(blogAdminDto);
        }
        Page<BlogAdminDTO> page = new Page<>(listBlogAdminDTO);
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
    public ArrayList<Blog> getBlogByIndexPage(int pageIndex, int pageSize) {
        ArrayList<Blog> listBlog = blogRepository.getBlogByIndexPage(pageIndex, pageSize);
        return listBlog;
    }

    @Override
    public Long countBlog() {
        long count = blogRepository.countBlog();
        return count;
    }
}
