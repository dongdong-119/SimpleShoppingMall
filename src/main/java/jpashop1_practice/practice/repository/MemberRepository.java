package jpashop1_practice.practice.repository;

import jpashop1_practice.practice.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    //회원저장 메소드
    public void save(Member member){
        em.persist(member);
    }

    //단일 회원 찾기
    public Member findOne(Long id){
        return em.find(Member.class, id);
    }

    //모든 회원 찾기
    public List<Member> findAll(){
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }

    public List<Member> findByName(String name){
            return em.createQuery("select m from Member m where m.name =:name", Member.class)
                            .setParameter("name", name)
                            .getResultList();
    }

}
