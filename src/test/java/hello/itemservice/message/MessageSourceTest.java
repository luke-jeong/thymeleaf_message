package hello.itemservice.message;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import java.util.Locale;

@SpringBootTest
public class MessageSourceTest {
    @Autowired
    MessageSource ms;

    @Test
    void helloMessage() {
        String result = ms.getMessage("hello", null, null);
        Assertions.assertThat(result).isEqualTo("안녕");
    }

    @Test
    void notFoundMessageCode(){
        Assertions.assertThatThrownBy(() -> ms.getMessage("no_code", null, null))
                .isInstanceOf(NoSuchMessageException.class);
    }

    @Test
    void argumentMessage() {
        String message = ms.getMessage("hello.name", new Object[]{"Spring"}, null);
        Assertions.assertThat(message).isEqualTo("안녕 Spring");
        //기본값인 안녕에 Object에 넣어준 Spring이 들어가 "안녕 Spring" 이 된다.
    }

    @Test
    void defaultLang(){
        Assertions.assertThat(ms.getMessage("hello", null, null)).isEqualTo("안녕");
        Assertions.assertThat(ms.getMessage("hello", null, Locale.KOREA)).isEqualTo("안녕");
        //기본값이 한글이기때문에 locale 값이 null일때나 한국일때가 같은 값이 출력된다.
    }

    @Test
    void enLang() {
        Assertions.assertThat(ms.getMessage("hello", null, Locale.ENGLISH)).isEqualTo("hello");
        //locale 값이 ENGLISH일때 영어로 설정한 값이 나오는지 확인
    }
}
