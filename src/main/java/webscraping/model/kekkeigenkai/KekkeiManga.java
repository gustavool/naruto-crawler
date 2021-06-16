package webscraping.model.kekkeigenkai;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class KekkeiManga {
    String name;
    Double volume;
    Double chapter;
}
