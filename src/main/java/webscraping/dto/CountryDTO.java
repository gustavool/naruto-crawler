package webscraping.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.data.annotation.Id;
import webscraping.model.country.CountryName;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CountryDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    String id;
    CountryName name;
    String description;
//    String image;
}
