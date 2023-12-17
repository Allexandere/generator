package com.example.generator.service;

import com.example.generator.model.TransportType;
import com.example.generator.model.dto.GenerateRequest;
import com.example.generator.model.entity.CityEntity;
import com.example.generator.model.entity.DepartureEntity;
import com.example.generator.model.pojo.DepartureGraph;
import com.example.generator.repository.CitiesRepository;
import com.example.generator.repository.DeparturesRepository;
import com.example.generator.validator.GeneratorRequestValidator;
import com.opencsv.CSVReader;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Slf4j
@Service
@RequiredArgsConstructor
public class GeneratorService {

    private final GeneratorRequestValidator generatorRequestValidator;
    private final CitiesRepository citiesRepository;
    private final DeparturesRepository departuresRepository;
    private final GraphMapper graphMapper;

    private final static int CITY_NAME_INDEX = 9;
    private final static int CITY_TIMEZONE_INDEX = 19;
    private final static String CITY_CSV_FILE_PATH = "classpath:city.csv";
    private final static Random rand = new Random();

    private final List<CityCandidate> cityNames = new ArrayList<>();

    @SneakyThrows
    @PostConstruct
    public void loadCities() {
        File file = ResourceUtils.getFile(CITY_CSV_FILE_PATH);
        try (CSVReader csvReader = new CSVReader(new FileReader(file))) {
            String[] values;
            while ((values = csvReader.readNext()) != null) {
                List<String> lineAsList = Arrays.asList(values);
                String cityName = lineAsList.get(CITY_NAME_INDEX);
                String timeZone = lineAsList.get(CITY_TIMEZONE_INDEX);
                if (isNotEmpty(cityName)) {
                    cityNames.add(new CityCandidate(cityName, timeZone));
                }
            }
        }
    }

    @SneakyThrows
    public DepartureGraph generateRecords(GenerateRequest generateRequest) {
        generatorRequestValidator.validate(generateRequest);
        log.info("Got valid " + generateRequest + ". Clearing tables.");
        clearTables();
        //Генерация городов
        List<CityEntity> savedCities = generateCities(generateRequest);
        List<DepartureEntity> savedDepartures = new ArrayList<>();
        //Генерация отправлений из городов
        for (int i = 0; i < generateRequest.getAmountOfRecords(); i++) {
            int randomDepartureCityId = rand.nextInt(savedCities.size());
            int randomArrivalCityId = getRandomArrivalCityId(savedCities, randomDepartureCityId);
            DepartureEntity departureToSave = getRandomDeparture(savedCities.get(randomDepartureCityId), savedCities.get(randomArrivalCityId));
            savedDepartures.add(departuresRepository.save(departureToSave));
        }
        return graphMapper.mapToGraph(savedCities, savedDepartures);
    }

    private int getRandomArrivalCityId(List<CityEntity> savedCities, int randomDepartureCityId) {
        int size = savedCities.size();
        if (randomDepartureCityId == 0) {
            return rand.nextInt(size);
        }
        int leftPool = rand.nextInt(randomDepartureCityId);
        int rightPool = rand.nextInt(randomDepartureCityId, size);
        if (rand.nextInt(2) == 0) {
            return leftPool;
        }
        return rightPool;
    }

    private DepartureEntity getRandomDeparture(CityEntity randomDepartureCity, CityEntity randomArrivalCity) {
        return new DepartureEntity(
                TransportType.getByCode(rand.nextInt(3)),
                LocalDateTime.now().plusDays(rand.nextInt(366)).plus(rand.nextInt(25), ChronoUnit.HOURS),
                rand.nextInt(10, 650),
                randomDepartureCity,
                randomArrivalCity,
                rand.nextInt(10000)
        );
    }

    private List<CityEntity> generateCities(GenerateRequest generateRequest) {
        List<CityEntity> savedCities = new ArrayList<>();
        Set<CityCandidate> cityCandidates = getCityCandidates(generateRequest);
        for (CityCandidate cityCandidate : cityCandidates) {
            CityEntity cityEntity = new CityEntity(cityCandidate.name, cityCandidate.timezone);
            CityEntity savedCity = citiesRepository.save(cityEntity);
            savedCities.add(savedCity);
        }
        return savedCities;
    }

    private Set<CityCandidate> getCityCandidates(GenerateRequest generateRequest) {
        Set<CityCandidate> cityNamesCandidates = new HashSet<>();
        for (int i = 0; i < generateRequest.getAmountOfCities(); i++) {
            CityCandidate city = cityNames.get(rand.nextInt(cityNames.size()));
            if (cityNamesCandidates.contains(city)) {
                i--;
            } else {
                cityNamesCandidates.add(city);
            }
        }
        return cityNamesCandidates;
    }

    private void clearTables() {
        departuresRepository.deleteAll();
        citiesRepository.deleteAll();
    }

    private record CityCandidate(String name, String timezone) {}

}
