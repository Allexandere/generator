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
    private final static String CITY_CSV_FILE_PATH = "classpath:city.csv";
    private final static Random rand = new Random();

    private final List<String> cityNames = new ArrayList<>();

    @SneakyThrows
    @PostConstruct
    public void loadCities() {
        File file = ResourceUtils.getFile(CITY_CSV_FILE_PATH);
        try (CSVReader csvReader = new CSVReader(new FileReader(file))) {
            String[] values;
            while ((values = csvReader.readNext()) != null) {
                String cityName = Arrays.asList(values).get(CITY_NAME_INDEX);
                if (isNotEmpty(cityName)) {
                    cityNames.add(cityName);
                }
            }
        }
    }

    @SneakyThrows
    public DepartureGraph generateRecords(GenerateRequest generateRequest) {
        generatorRequestValidator.validate(generateRequest);
        log.info("Got valid " + generateRequest + ". Clearing tables.");
        clearTables();
        List<CityEntity> savedCities = generateCities(generateRequest);
        List<DepartureEntity> savedDepartures = new ArrayList<>();
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
        Set<String> cityNamesCandidates = getCityNameCandidates(generateRequest);
        for (String cityName : cityNamesCandidates) {
            savedCities.add(citiesRepository.save(new CityEntity(cityName)));
        }
        return savedCities;
    }

    private Set<String> getCityNameCandidates(GenerateRequest generateRequest) {
        Set<String> cityNamesCandidates = new HashSet<>();
        for (int i = 0; i < generateRequest.getAmountOfCities(); i++) {
            String cityName = cityNames.get(rand.nextInt(cityNames.size()));
            if (cityNamesCandidates.contains(cityName)) {
                i--;
            } else {
                cityNamesCandidates.add(cityName);
            }
        }
        return cityNamesCandidates;
    }

    private void clearTables() {
        departuresRepository.deleteAll();
        citiesRepository.deleteAll();
    }

}
