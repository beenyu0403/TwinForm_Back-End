package proj.been433.twinformback.member;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import proj.been433.twinformback.writeform.WriteForm;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Embedded
    private Login login;


    private String name;

    @OneToMany(mappedBy = "member")
    private List<WriteForm> writeForms = new ArrayList<>();
}
