package Board.Juyoung.repository;

import Board.Juyoung.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @EntityGraph(attributePaths = "member")
    Page<Board> findByOrderByCreatedDateDesc(Pageable pageable);
}
