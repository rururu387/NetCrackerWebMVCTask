package com.goose.Controllers;

import com.goose.Entities.Car;
import com.goose.Views.JsonView;
import com.goose.Models.Repositories.CarRepository;
import com.goose.Models.Repositories.CarSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

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
        System.out.println("/");
        return "index.html";
    }

    private LocalDate formatDateString(String creationDateStr) throws IllegalArgumentException
    {
        if (creationDateStr != null && !creationDateStr.isEmpty())
        {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            return LocalDate.parse(creationDateStr, formatter);
        }
        return null;
    }

    @PostMapping("/list")
    public ResponseEntity<String> listQuery(@RequestParam(name = "id", required = false) Integer id,
                                         @RequestParam(name = "name", required = false) String name,
                                         @RequestParam(name = "wheelAm", required = false) Short wheelAm,
                                         @RequestParam(name = "weight", required = false) Double weight,
                                         @RequestParam(name = "creationDate", required = false) String creationDateStr)
    {
        System.out.println("List all cars");


        LocalDate creationDate = null;
        try
        {
            creationDate = formatDateString(creationDateStr);
        }
        catch (IllegalArgumentException e)
        {
            return new ResponseEntity<>("Date is not well-formed", HttpStatus.BAD_REQUEST);
        }

        try
        {
            List<Car> allCars = carRepository.findAll(Specification.where(CarSpecifications.carHasId(id).and(
                    CarSpecifications.carHasName(name)).and(
                    CarSpecifications.carHasWheelAm(wheelAm)).and(
                            CarSpecifications.carHasWeight(weight)).and(
                    CarSpecifications.carHasCreationDate(creationDate))));

            String responseJson = JsonView.toJsonTable(allCars);
            System.out.println(responseJson);
            //TODO - make view separate
            return new ResponseEntity<>(responseJson, HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new ResponseEntity<>("Error", HttpStatus.I_AM_A_TEAPOT);
        }
    }

    @PutMapping("/insert")
    public ResponseEntity<String> insertQuery(@RequestParam(name = "name") String name,
                                            @RequestParam(name = "wheelAm") Short wheelAm,
                                            @RequestParam(name = "weight") Double weight,
                                            @RequestParam(name = "creationDate") String creationDateStr)
    {
        System.out.println("/insert");

        LocalDate creationDate = null;

        try
        {
            creationDate = formatDateString(creationDateStr);
            if (creationDate == null)
            {
                throw new IllegalArgumentException("Bad date string");
            }
        }
        catch (IllegalArgumentException e)
        {
            return new ResponseEntity<>("Date is not well-formed", HttpStatus.BAD_REQUEST);
        }

        try
        {
            carRepository.save(new Car(name, wheelAm, weight, creationDate));
        }
        catch (Exception e)
        {
            return new ResponseEntity<>("Could not save data", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateQuery(@RequestParam(name = "id", required = false) Integer id,
                            @RequestParam(name = "name", required = false) String name,
                            @RequestParam(name = "wheelAm", required = false) Short wheelAm,
                            @RequestParam(name = "weight", required = false) Double weight,
                            @RequestParam(name = "creationDate", required = false) String creationDateStr)
    {
        LocalDate creationDate = null;
        try
        {
            creationDate = formatDateString(creationDateStr);
        }
        catch (IllegalArgumentException e)
        {
            return new ResponseEntity<>("Date is not well-formed", HttpStatus.BAD_REQUEST);
        }

        Optional<Car> carToUpdate = carRepository.findById(id);

        if (carToUpdate.isEmpty())
        {
            return new ResponseEntity<>("Car with id (" + id + ") is not found", HttpStatus.NOT_FOUND);
        }

        Car carInst = carToUpdate.get();

        if (name != null)
        {
            carInst.setName(name);
        }

        if (wheelAm != null)
        {
            carInst.setWheelAm(wheelAm);
        }

        if (weight != null)
        {
            carInst.setWeight(weight);
        }

        if (creationDate != null)
        {
            carInst.setCreationDate(creationDate);
        }

        carRepository.save(carInst);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteQuery(@RequestParam(name = "id", required = false) Integer id,
                            @RequestParam(name = "name", required = false) String name,
                            @RequestParam(name = "wheelAm", required = false) Short wheelAm,
                            @RequestParam(name = "weight", required = false) Double weight,
                            @RequestParam(name = "creationDate", required = false) String creationDateStr)
    {
        LocalDate creationDate = null;
        if (creationDateStr != null && !creationDateStr.isEmpty())
        {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            creationDate = LocalDate.parse(creationDateStr, formatter);
        }

        if (id == null && name == null && wheelAm == null && weight == null && creationDate == null)
        {
            return new ResponseEntity<>("Specification of at least one parameter is mandatory", HttpStatus.FORBIDDEN);
        }

        try
        {
            List<Car> matches = carRepository.findAll(Specification.where(CarSpecifications.carHasId(id).and(CarSpecifications.carHasName(name)).and(
                    CarSpecifications.carHasWheelAm(wheelAm)).and(CarSpecifications.carHasWeight(weight)).and(
                    CarSpecifications.carHasCreationDate(creationDate))));

            carRepository.deleteAll(matches);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new ResponseEntity<>("Error", HttpStatus.I_AM_A_TEAPOT);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
