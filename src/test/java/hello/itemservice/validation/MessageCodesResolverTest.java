package hello.itemservice.validation;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ObjectArrayAssert;
import org.junit.jupiter.api.Test;
import org.springframework.validation.DefaultMessageCodesResolver;
import org.springframework.validation.MessageCodesResolver;

import static org.assertj.core.api.Assertions.*;

public class MessageCodesResolverTest {
    MessageCodesResolver codesResolver = new DefaultMessageCodesResolver();
    //MessageCodesResolver: 검증 오류 코드("required")로 "메시지 코드들을 생성"
    //오류 코드 하나가 아니라 여러 오류 코드를 가질 수 있다-> MessageCodesResolver를 통해 생성된 순서대로 오류 코드를 보관

    @Test
    void messageCodesResolverObject() {
        String[] messageCodes = codesResolver.resolveMessageCodes("required", "item");
        assertThat(messageCodes).containsExactly("required.item", "required");
    }
    @Test
    void messageCodesResolverField() {
        String[] messageCodes = codesResolver.resolveMessageCodes("required", "item", "itemName", String.class);
        assertThat(messageCodes).containsExactly(
                "required.item.itemName",
                "required.itemName",
                "required.java.lang.String",
                "required"
        );
    }
}
