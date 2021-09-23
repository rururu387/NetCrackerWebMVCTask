package com.goose.Entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class Car
{
    @Id
    int id;

    String name;

    short wheelAm;

    float weight;

    LocalDate creationDate;

    @Deprecated
    private Car()
    {
        //Only for Hibernate, no human usage!
    }

    public Car(int index, String name, short wheelAm, float weight, LocalDate creationDate)
    {
        this.id = index;
        this.name = name;
        this.wheelAm = wheelAm;
        this.weight = weight;
        this.creationDate = creationDate;
    }

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public short getWheelAm()
    {
        return wheelAm;
    }

    public float getWeight()
    {
        return weight;
    }

    public LocalDate getCreationDate()
    {
        return creationDate;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setWheelAm(short wheelAm)
    {
        this.wheelAm = wheelAm;
    }

    public void setWeight(float weight)
    {
        this.weight = weight;
    }

    public void setCreationDate(LocalDate creationDate)
    {
        this.creationDate = creationDate;
    }
}
