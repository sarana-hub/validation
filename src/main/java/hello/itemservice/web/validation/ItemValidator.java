package hello.itemservice.web.validation;

import hello.itemservice.domain.item.Item;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
/** Validator 분리
 * 컨트롤러에서 "검증 로직"이 차지하는 부분이 매우 큼-> 별도의 클래스로 역할을 분리하는 것이 좋다
 * */

@Component
public class ItemValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {   /**해당 검증기를 지원하는지 여부를 확인*/
        //supports() {} : 여러 검증기를 등록한다면 그 중에 어떤 검증기가 실행되어야 할지 구분
        return Item.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {    /**검증 대상 객체와 BindingResult*/
        Item item = (Item) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "itemName", "required");
        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
            errors.rejectValue("price", "range", new Object[]{1000, 1000000}, null);
        }
        if (item.getQuantity() == null || item.getQuantity() > 10000) {
            errors.rejectValue("quantity", "max", new Object[]{9999}, null);
        }
        //특정 필드 예외가 아닌 전체 예외
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                errors.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
            }
        }
    }
}
