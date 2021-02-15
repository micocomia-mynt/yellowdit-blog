package ph.apper.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ph.apper.exception.*;
import ph.apper.payload.*;
import ph.apper.service.BlogService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("blog")
public class BlogController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BlogController.class);

    private final BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @PostMapping
    public ResponseEntity<Map<String,String>> create(
            @Valid @RequestBody BlogCreationRequest request,
            @RequestHeader("Reference-Number") String referenceNumber) throws InvalidBlogCreationRequestException, UserNotVerifiedActive, UserNotFoundException {
        LOGGER.info("Blog creation {} received", referenceNumber);

        Map<String,String> response = new HashMap<>();
        String status = blogService.create(request);

        response.put("message", status);
        LOGGER.info("Blog creation {} successful", referenceNumber);

        return ResponseEntity.ok(response);
    }

    @PostMapping("{id}/comment")
    public ResponseEntity<Map<String,String>> comment(@PathVariable("id") String blogId,
            @Valid @RequestBody BlogCommentRequest request,
            @RequestHeader("Reference-Number") String referenceNumber) throws BlogNotFoundException{
        LOGGER.info("Blog comment {} received", referenceNumber);

        Map<String,String> response = new HashMap<>();
        String status = blogService.comment(blogId, request);

        response.put("message", status);
        LOGGER.info("Blog comment {} successful", referenceNumber);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<BlogData>> getAllBlogs(@RequestParam Boolean all) {
        return ResponseEntity.ok(blogService.getAllBlogs(all));
    }

    @GetMapping("{id}")
    public ResponseEntity<BlogData> getBlog(@PathVariable("id") String blogId) throws BlogNotFoundException {
        return ResponseEntity.ok(blogService.getBlog(blogId));
    }

    @PatchMapping("{id}")
    public ResponseEntity<GenericResponse> updateBlog(
            @PathVariable("id") String blogId,
            @RequestHeader("Reference-Number") String referenceNumber,
            @Valid @RequestBody UpdateBlogRequest request) throws BlogNotFoundException {
        LOGGER.info("Update blog with reference number {} received", referenceNumber);

        blogService.updateBlog(blogId, request);

        LOGGER.info("Update blog with reference number {} successful", referenceNumber);

        return ResponseEntity.ok(new GenericResponse("update blog success"));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<GenericResponse> deleteBlog(
            @PathVariable("id") String id,
            @RequestHeader("Reference-Number") String referenceNumber) throws BlogNotFoundException {
        LOGGER.info("Deleting user with reference number {} received", referenceNumber);

        blogService.deleteBlog(id);

        LOGGER.info("Delete user with reference number {} successful", referenceNumber);

        return ResponseEntity.ok(new GenericResponse("delete blog success"));
    }
}
