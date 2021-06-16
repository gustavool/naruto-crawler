package webscraping.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import webscraping.model.kekkeigenkai.KekkeiDebut;
import webscraping.model.kekkeigenkai.KekkeiName;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class KekkeiDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    String id;
    KekkeiName name;
    String description;
    String imageIcon;
    KekkeiDebut debut;
    List<String> clan;
    List<String> classification;
    List<String> basicNatures;
    List<String> wielders;
    List<String> jutsus;
//    String image;
}
