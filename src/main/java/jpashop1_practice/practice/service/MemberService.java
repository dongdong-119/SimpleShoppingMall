package jpashop1_practice.practice.service;


import jpashop1_practice.practice.domain.Member;
import jpashop1_practice.practice.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;


    /**
     * 회원가입 메소드
     * 중복회원일 경우 -> Exception 발생
     */
    @Transactional
    public Long join(Member member){

        validateDuplicateMember(member);

        memberRepository.save(member);
        return member.getId();

    }

    /**
     회원 단건 검색
     */
    public Member findOne(Long id){ return memberRepository.findOne(id); }

    /*
    회원 전체 조회
     */
    public List<Member> findAll(){ return memberRepository.findAll(); }


    /*
    중복 회원 검증 로직
     */
    private void validateDuplicateMember(Member member){

        List<Member> findMembers = memberRepository.findByName(member.getName());

        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }

    }



}
