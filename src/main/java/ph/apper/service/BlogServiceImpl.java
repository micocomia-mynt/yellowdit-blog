package ph.apper.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ph.apper.domain.Blog;
import ph.apper.domain.Comment;
import ph.apper.exception.*;
import ph.apper.payload.*;
import ph.apper.repository.BlogRepository;
import ph.apper.util.IdService;


import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Profile({"dev","prod"})
@Service
public class BlogServiceImpl implements BlogService{
    private static final Logger LOGGER = LoggerFactory.getLogger(BlogServiceImpl.class);

    private final UserService userService;
    private final BlogRepository blogRepository;

    public BlogServiceImpl(UserService userService, BlogRepository blogRepository) {
        this.userService = userService;
        this.blogRepository = blogRepository;
    }

    @Override
    public String create(BlogCreationRequest request) throws UserNotVerifiedActive, UserNotFoundException {
        if (!userService.checkVerification(request.getUserId())){
            throw new UserNotVerifiedActive("Provided user ID is not active and verified");
        }

        try{
            userService.getUser(request.getUserId());
        }catch(UserNotFoundException e){
            LOGGER.error("User not found", e.getMessage());
        }

        String blogId = IdService.getNextId();
        Blog newBlog = new Blog(blogId);
        newBlog.setContent(request.getContent());
        newBlog.setTitle(request.getTitle());
        newBlog.setUserId(request.getUserId());
        newBlog.setDatePublish(LocalDateTime.now());

        blogRepository.save(newBlog);
        return "Blog created successfully";
    }

    @Override
    @Transactional
    public List<BlogData> getAllBlogs(boolean showAll){
        List<BlogData> blogDataList = new ArrayList<>();
        Stream<Blog> blogStream = blogRepository.findAllByIsVisible(true);;
        blogStream.forEach(blog -> blogDataList.add(BlogServiceUtil.toBlogData(blog)));

        if(showAll){
            blogStream = blogRepository.findAllByIsVisible(false);;
            blogStream.forEach(blog -> blogDataList.add(BlogServiceUtil.toBlogData(blog)));
        }

        return blogDataList;
    }

    @Override
    public BlogData getBlog(String id) throws BlogNotFoundException {
        Blog blog = getBlogById(id);
        return BlogServiceUtil.toBlogData(blog);
    }

    @Override
    public void updateBlog(String id, UpdateBlogRequest request) throws BlogNotFoundException {
        Blog blog = getBlogById(id);

        if(!blog.isVisible()){
            throw new BlogNotFoundException("Blog does not exist");
        }

        if (request.getTitle() != null) {
            blog.setTitle(request.getTitle());
        }

        if (request.getContent() != null) {
            blog.setContent(request.getContent());
        }

        blog.setLastUpdated(LocalDateTime.now());

        blogRepository.save(blog);
    }

    @Override
    public void deleteBlog(String id) throws BlogNotFoundException {
        Blog blog = getBlogById(id);
        blog.setVisible(false);
        blogRepository.save(blog);
    }

    @Override
    public String comment(String id, BlogCommentRequest request) throws BlogNotFoundException {
        Blog blog = getBlogById(id);

        if (!blog.isVisible()){
            throw new BlogNotFoundException("Blog does not exist");
        }

        Comment newComment = new Comment();
        newComment.setName(request.getName());
        newComment.setComment(request.getComment());

        blog.getComments().add(newComment);

        blogRepository.save(blog);
        return "Comment added successfully";
    }

    private Blog getBlogById(String id) throws BlogNotFoundException {
        return blogRepository.findById(id).orElseThrow(() -> new BlogNotFoundException("Blog " + id + " not found"));
    }


}

