package shop.mtcoding.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class IndexController {
    @GetMapping("/index")
    public ResponseEntity<?> index() {
        log.debug("디버그");
        log.info("인포");
        log.warn("경고");
        log.error("에러");
        log.error("에러");
        log.error("에러");

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
