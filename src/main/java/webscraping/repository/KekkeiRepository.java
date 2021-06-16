package webscraping.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import webscraping.document.KekkeiDoc;
import webscraping.dto.KekkeiDTO;

@Repository
public interface KekkeiRepository extends MongoRepository<KekkeiDoc, String> {
    KekkeiDTO findByNameEnglish(String name);
}
