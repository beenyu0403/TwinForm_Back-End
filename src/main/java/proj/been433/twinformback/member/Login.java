package proj.been433.twinformback.member;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class Login {
    private String loginId;
    private String loginPw;

    protected Login() {}

    public Login(String loginId, String loginPw) {
        this.loginId = loginId;
        this.loginPw = loginPw;
    }
}
