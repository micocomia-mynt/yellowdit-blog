package ph.apper.service;

import ph.apper.domain.Blog;
import ph.apper.payload.BlogData;

import java.time.format.DateTimeFormatter;
import java.util.Objects;

public final class BlogServiceUtil {

    public static BlogData toBlogData(Blog blog) {
        BlogData blogData = new BlogData();
        blogData.setTitle(blog.getTitle());
        blogData.setContent(blog.getContent());
        blogData.setUserId(blog.getUserId());
        blogData.setDatePublish(blog.getDatePublish().format(DateTimeFormatter.ISO_DATE_TIME));
        blogData.setId(blog.getId());
        blogData.setVisible(blog.isVisible());
        blogData.setComments(blog.getComments());

        if (Objects.nonNull(blog.getLastUpdated())) {
            blogData.setLastUpdated(blog.getLastUpdated().format(DateTimeFormatter.ISO_DATE_TIME));
        }

        return blogData;
    }
}
