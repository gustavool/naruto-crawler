package webscraping.model.kekkeigenkai;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class KekkeiInfo {
    String description;
    String image;
    String imageSymbol;
    List<String> clan;
    List<String> classification;
    List<String> basicNatures;
    List<String> wielders;
    List<String> jutsus;
}
