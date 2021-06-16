package webscraping.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import webscraping.document.CountryDoc;
import webscraping.dto.CountryDTO;

@Repository
public interface CountryRepository extends MongoRepository<CountryDoc, String> {
    CountryDTO findByNameEnglish(String name);
}
