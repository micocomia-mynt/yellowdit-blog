package ph.apper.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class BlogCommentRequest {
    @NotBlank(message = "name is required")
    private String name;

    @NotBlank(message = "message is required")
    private String comment;
}
