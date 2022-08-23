package jpashop1_practice.practice.service;

import jpashop1_practice.practice.domain.Member;
import jpashop1_practice.practice.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@RunWith(SpringRunner.class)
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    @Transactional
    public void 회원_저장() throws Exception{
        //given
        Member member = new Member();
        member.setName("고동희");


        //when
        Long savedId = memberService.join(member);

        //then
        Assertions.assertThat(member).isEqualTo(memberRepository.findOne(savedId));

    }


    @Test
    @Transactional
    public void 중복_회원_예외() throws Exception{
        //given

        Member member = new Member();
        member.setName("memberA");

        Member member1 = new Member();
        member1.setName("memberA");

        //when
        memberService.join(member);
        org.junit.jupiter.api.Assertions.assertThrows(IllegalStateException.class,
                       ()->memberService.join(member1));
    }

}