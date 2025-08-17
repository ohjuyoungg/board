package Board.Juyoung.repository;

import Board.Juyoung.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @EntityGraph(attributePaths = {"member", "board"})
    Page<Comment> findByBoardIdOrderByCreatedDateAsc(Long boardId, Pageable pageable);
}
