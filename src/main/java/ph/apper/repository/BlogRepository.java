package ph.apper.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ph.apper.domain.Blog;

import java.util.stream.Stream;

@Repository
public interface BlogRepository extends CrudRepository<Blog, String>{
    Stream<Blog> findAllByIsVisible(boolean excludeInvisible);
}
