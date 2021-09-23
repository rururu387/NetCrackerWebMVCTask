package com.goose.Controllers;

import com.goose.Entities.Car;
import com.goose.Models.CarProcessing;
import com.goose.Repositories.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
public class MainController
{
    @Autowired
    private CarRepository carRepository;

    private MainController()
    {}

    @GetMapping("/")
    public String mainPage()
    {
        return "index";
    }

    @GetMapping("/findAll")
    public ResponseEntity<String> findAllQuery()
    {
        List<Car> allCars = carRepository.findBy();

        String responseJson = CarProcessing.toJsonTable(allCars);

        return new ResponseEntity<>(responseJson, HttpStatus.OK);
    }

    @GetMapping("/insert")
    public ResponseEntity<Void> insertQuery(@RequestParam(name = "name") String name, @RequestParam(name = "wheelAm") short wheelAm, @RequestParam(name = "weight") float weight, @RequestParam(name = "creationDate")LocalDate creationDate)
    {
        return ResponseEntity.noContent().build();
    }
}
