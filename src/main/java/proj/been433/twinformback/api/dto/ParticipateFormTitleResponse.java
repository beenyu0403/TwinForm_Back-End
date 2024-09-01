package proj.been433.twinformback.api.dto;

import lombok.Data;
import proj.been433.twinformback.writeform.FormStatus;

@Data
public class ParticipateFormTitleResponse {
    private Long p_id;
    private Long f_id;
    private String title;
    private FormStatus status;

    public ParticipateFormTitleResponse(Long p_id, Long f_id, String title, FormStatus status) {
        this.p_id = p_id;
        this.f_id = f_id;
        this.title = title;
        this.status = status;
    }
}
