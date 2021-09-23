package com.goose.Repositories;

import com.goose.Entities.Car;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CarRepository extends CrudRepository<Car, Long>
{
    List<Car> findBy();
}
