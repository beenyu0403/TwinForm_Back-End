package proj.been433.twinformback.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import proj.been433.twinformback.member.Member;
import proj.been433.twinformback.repository.MemberRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member); //중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }
    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    public Boolean checkLoginId(String loginId) {
        List<Member> findMember = memberRepository.findOneByLoginId(loginId);
        if (!findMember.isEmpty()) {
            return false;
        }else{
            return true;
        }
    }

    public Member findOneByLoginId(String memberLoginId) {
        return memberRepository.findOneByLoginIdToLogin(memberLoginId);
    }
    public Member findOneById(Long Id) {
        return memberRepository.findOne(Id);
    }
    public String successLogin(String memberLoginId, String memberLoginPw) {
        Boolean isSuccess = checkLoginId(memberLoginId);
        if (!isSuccess) {
            Member member = findOneByLoginId(memberLoginId);
            if ((member.getLogin().getLoginId().equals(memberLoginId)) && (member.getLogin().getLoginPw().equals(memberLoginPw))) {
                return memberLoginId;
            }

        }
        return "";
    }
}
