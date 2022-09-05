package jpashop1_practice.practice.controller.form;


import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class MemberForm {

    @NotEmpty(message = "회원이름은 필수입니다.")
    private String name;

    private String city;
    private String street;
    private String zipcode;



}
