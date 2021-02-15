package ph.apper.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import ph.apper.domain.Comment;

import java.util.List;

@Data
public class BlogData {
    private String id;

    private String title;

    private String content;

    @JsonProperty(value = "user_id")
    private String userId;

    @JsonProperty(value = "date_publish")
    private String datePublish;

    @JsonProperty(value = "last_updated")
    private String lastUpdated;

    @JsonProperty(value = "is_visible")
    private boolean isVisible;

    private List<Comment> comments;
}
