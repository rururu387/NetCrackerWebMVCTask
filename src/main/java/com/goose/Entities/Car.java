package com.goose.Entities;

import org.springframework.lang.NonNull;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
//@Table(name="STUDENT", schema="SCHOOL")
@Table(name = "car", schema = "public")
public class Car
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(length = 50)
    @NotNull
    String name;

    @NotNull
    Short wheelAm;

    @NotNull
    Double weight;

    @NotNull
    LocalDate creationDate;

    @Deprecated
    private Car()
    {
        //Only for Hibernate, no human usage!
    }

    public Car(String name, short wheelAm, double weight, LocalDate creationDate)
    {
        this.name = name;
        this.wheelAm = wheelAm;
        this.weight = weight;
        this.creationDate = creationDate;
    }

    public Car(Integer index, String name, Short wheelAm, Double weight, LocalDate creationDate)
    {
        this(name, wheelAm, weight, creationDate);
        this.id = index;
    }

    public Integer getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public Short getWheelAm()
    {
        return wheelAm;
    }

    public Double getWeight()
    {
        return weight;
    }

    public LocalDate getCreationDate()
    {
        return creationDate;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setWheelAm(Short wheelAm)
    {
        this.wheelAm = wheelAm;
    }

    public void setWeight(Double weight)
    {
        this.weight = weight;
    }

    public void setCreationDate(LocalDate creationDate)
    {
        this.creationDate = creationDate;
    }
}
