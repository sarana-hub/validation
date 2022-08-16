package hello.itemservice.web.validation;

import hello.itemservice.web.validation.form.ItemSaveForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**API의 경우 3가지 경우를 나누어 생각해야 한다.
 성공 요청: 성공
 실패 요청: JSON을 객체로 생성하는 것 자체가 실패함
 검증 오류 요청: JSON을 객체로 생성하는 것은 성공했고, 검증에서 실패함*/

@Slf4j
@RestController
@RequestMapping("/validation/api/items")
public class ValidationItemApiController {
    @PostMapping("/add")
    public Object addItem(@RequestBody @Validated ItemSaveForm form, BindingResult bindingResult) {
        //@Valid , @Validated 는 HttpMessageConverter(@RequestBody)에도 적용할 수 있다

        log.info("API 컨트롤러 호출");

        if (bindingResult.hasErrors()) {
            log.info("검증 오류 발생 errors={}", bindingResult);
            return bindingResult.getAllErrors();    //ObjectError 와 FieldError 를 반환
            //스프링이 이 객체를 JSON으로 변환해서 클라이언트에 전달
            /*실제 개발할 때는 이 검증 오류 객체들을 그대로 사용하지 말고,
            필요한 데이터만 뽑아서 별도의 API 스펙을 정의하고 그에 맞는 객체를 만들어서 반환해야 한다*/
        }
        log.info("성공 로직 실행");
        return form;
    }
}
