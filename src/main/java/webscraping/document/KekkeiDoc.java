package webscraping.document;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
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
@Document(collection="kekkeiGenkais")
public class KekkeiDoc implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    String id;
    KekkeiName name;
    String description;
    String image;
    String imageSymbol;
    KekkeiDebut debut;
    List<String> clan;
    List<String> classification;
    List<String> basicNatures;
    List<String> wielders;
    List<String> jutsus;
}
