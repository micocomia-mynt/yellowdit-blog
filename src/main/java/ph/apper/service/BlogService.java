package ph.apper.service;

import ph.apper.domain.Blog;
import ph.apper.exception.*;
import ph.apper.payload.*;

import java.util.List;

public interface BlogService {
    String create(BlogCreationRequest request) throws UserNotVerifiedActive, UserNotFoundException;
    List<BlogData> getAllBlogs(boolean showAll);
    BlogData getBlog(String id) throws BlogNotFoundException;
    void updateBlog(String id, UpdateBlogRequest request) throws BlogNotFoundException;
    void deleteBlog(String id) throws BlogNotFoundException;
    String comment(String id, BlogCommentRequest request) throws BlogNotFoundException;
}

