package Board.Juyoung;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class JuyoungApplication {

    public static void main(String[] args) {
        SpringApplication.run(JuyoungApplication.class, args);
    }
}
