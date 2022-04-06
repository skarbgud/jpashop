package japbook.jpashop.service;

import japbook.jpashop.domain.Member;
import japbook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
//@Transactional // 기본적으로 트랜잭션 안에서 발생해야 하기 때문에
@Transactional(readOnly = true) // 단순히 읽기에는 readOnly = true 를 넣어준다.(성능을 좀 더 최적화 해준다.)
@RequiredArgsConstructor // final 이 있는 것들은 자동으로 생성자 주입을 해준다.
public class MemberService {

    private final MemberRepository memberRepository;

    // 생성자가 하나가 있는경우에는 @Autowired 를 선언 안해줘도 된다.
//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    /*
     * 회원 가입
     */
    @Transactional // 읽기가 아닌 쓰기에는 readonly = true 를 사용x
    public Long join(Member member) {
        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        // 같은 아이디가 있는지 확인
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    // 회원 전체 조회
//    @Transactional(readOnly = true) // 단순히 읽기에는 readOnly = true 를 넣어준다.(성능을 좀 더 최적화 해준다.)
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    // 회원 단건 조회
//    @Transactional(readOnly = true) // 단순히 읽기에는 readOnly = true 를 넣어준다.(성능을 좀 더 최적화 해준다.)
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}
