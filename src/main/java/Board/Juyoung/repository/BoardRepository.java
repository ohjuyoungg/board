package Board.Juyoung.repository;

import Board.Juyoung.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {

    Page<Board> findByOrderByCreatedDateDesc(Pageable pageable);
}
