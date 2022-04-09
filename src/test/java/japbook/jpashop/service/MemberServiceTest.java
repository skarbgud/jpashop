package japbook.jpashop.service;

import japbook.jpashop.domain.Member;
import japbook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional // 스프링에서 기본적으로 Transactional 은 Rollback 한다.
public class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager em;

    @Test
//    @Rollback(false) // Rollback 을 false하면 insert 쿼리가 적용되고 Rollback 이 되지 않는다. (EntityManager 로 flush를 하면 DB에 쿼리가 적용된다.)
    // Rollback을 하는 이유 => 테스트시에는 해당 테스트가 반복해서 되므로 롤백을 통해서 데이터가 계속 쌓이면 안되니까
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("nam");

        //when
        Long saveId = memberService.join(member);

//        em.flush();
        //then
        assertEquals(member, memberRepository.findOne(saveId));
    }

    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("nam");

        Member member2 = new Member();
        member2.setName("nam");

        //when
        memberService.join(member1);
        memberService.join(member2); // 예외가 발생해야 한다!!!
//        try {
//            memberService.join(member2); // 예외가 발생해야 한다!!!
//        } catch (IllegalStateException e) {
//            return;
//        }

        //then
        fail("예외가 발생해야 한다.");
    }
}