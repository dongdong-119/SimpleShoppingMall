package jpashop1_practice.practice.controller;

import jpashop1_practice.practice.controller.form.MemberForm;
import jpashop1_practice.practice.domain.Address;
import jpashop1_practice.practice.domain.Member;
import jpashop1_practice.practice.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberService memberService;


    @RequestMapping("/")
    public String home(){

        return "home";
    }

    @GetMapping("/errorPage")
    public String errorPage(){
        return "error/errorPage";
    }


    @GetMapping("/members/new")
    public String createForm(Model model){

        model.addAttribute("memberForm", new MemberForm());

        return "members/createMemberForm";
    }



    @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult result){


        if (result.hasErrors()){
            return "members/createMemberForm";
        }

        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());

        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);

        memberService.join(member);

        return "redirect:/";
    }


    @GetMapping("/members")
    public String list(Model model){

        List<Member> members = memberService.findAll();
        model.addAttribute("members", members);

        return "members/membersList";
    }

}
