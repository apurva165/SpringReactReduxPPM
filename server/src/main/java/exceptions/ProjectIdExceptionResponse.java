package exceptions;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProjectIdExceptionResponse {
    private String projectIdentifier;

    public ProjectIdExceptionResponse(String projectIdentifier) {
        this.projectIdentifier = projectIdentifier;
    }
}
