package Board.Juyoung.repository;

import Board.Juyoung.entity.Board;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    void testLazyLoading() {
        var boards = boardRepository.findByOrderByCreatedDateDesc(PageRequest.of(0, 1));
        for (Board board : boards.getContent()) {
            System.out.println("제목: " + board.getTitle() + ", 작성자: " + board.getMember().getNickname());
        }
    }
}
