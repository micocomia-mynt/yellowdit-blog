package ph.apper.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ph.apper.domain.Blog;
import ph.apper.domain.Comment;
import ph.apper.exception.BlogNotFoundException;
import ph.apper.exception.InvalidBlogCreationRequestException;
import ph.apper.exception.UserNotFoundException;
import ph.apper.exception.UserNotVerifiedActive;
import ph.apper.payload.BlogCommentRequest;
import ph.apper.payload.BlogCreationRequest;
import ph.apper.payload.BlogData;
import ph.apper.payload.UpdateBlogRequest;
import ph.apper.util.IdService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Profile({"local"})
@Service
public class LocalBlogServiceImpl implements BlogService{

    private static final Logger LOGGER = LoggerFactory.getLogger(LocalUserServiceImpl.class);

    private final List<Blog> blogs = new ArrayList<>();
    private final UserService userService;

    public LocalBlogServiceImpl(UserService userService) {
        this.userService = userService;
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

        blogs.add(newBlog);
        return "Blog created successfully";
    }

    @Override
    public List<BlogData> getAllBlogs(boolean showAll) {
        List<BlogData> blogDataList = new ArrayList<>();
        Stream<Blog> blogStream = blogs.stream();

        if (!showAll) {
            blogStream = blogStream.filter(Blog::isVisible);
        }

        blogStream.forEach(blog -> blogDataList.add(BlogServiceUtil.toBlogData(blog)));

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
        int index = blogs.indexOf(blog);

        if (request.getTitle() != null) {
            blog.setTitle(request.getTitle());
        }

        if (request.getContent() != null) {
            blog.setContent(request.getContent());
        }

        blogs.set(index, blog);
    }

    @Override
    public void deleteBlog(String id) throws BlogNotFoundException {
        Blog blog = getBlogById(id);
        blog.setVisible(false);
    }

    @Override
    public String comment(String id, BlogCommentRequest request) throws BlogNotFoundException {
        Blog blog = getBlogById(id);
        int index = blogs.indexOf(blog);

        if (!blog.isVisible()){
            throw new BlogNotFoundException("Blog does not exist");
        }

        List<Comment> comments = blog.getComments();

        Comment newComment = new Comment();
        newComment.setName(request.getName());
        newComment.setComment(request.getComment());

        blog.getComments().add(newComment);

        blogs.set(index, blog);
        return "Comment added successfully";
    }

    private Blog getBlogById(String id) throws BlogNotFoundException {
        return blogs.stream()
                .filter(blog -> id.equals(blog.getId()))
                .findFirst()
                .orElseThrow(() -> new BlogNotFoundException("Blog " + id + " not found"));
    }
}
