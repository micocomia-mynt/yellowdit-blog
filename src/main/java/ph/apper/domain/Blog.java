package ph.apper.domain;

import lombok.Data;
import org.apache.tomcat.jni.Local;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Blog {
    @Id
    private String id;
    private String title;
    private String content;
    private String userId;
    private LocalDateTime datePublish;
    private LocalDateTime lastUpdated;
    private boolean isVisible = true;

    @OneToMany(targetEntity = Comment.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "comment_id")
    private List<Comment> comments;

    public Blog(String id) {
        this.id = id;
    }
    public Blog(){
    }
}
