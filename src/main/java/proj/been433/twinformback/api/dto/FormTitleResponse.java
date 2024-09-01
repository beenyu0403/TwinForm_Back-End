package proj.been433.twinformback.api.dto;

import lombok.Data;
import proj.been433.twinformback.writeform.FormStatus;

@Data
public class FormTitleResponse {
    private Long id;
    private String title;
    private FormStatus status;

    public FormTitleResponse(Long id, String title, FormStatus status) {
        this.id = id;
        this.title = title;
        this.status = status;
    }
}
