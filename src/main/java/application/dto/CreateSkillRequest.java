package application.dto;

import lombok.*;

import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class CreateSkillRequest {
    @Size( min = 3, max = 20, message =  "Size must be between 3 and 20" )
    private String name;
}
