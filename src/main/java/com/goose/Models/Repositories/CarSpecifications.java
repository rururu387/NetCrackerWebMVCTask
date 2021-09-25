package com.goose.Models.Repositories;

import com.goose.Entities.Car;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CarSpecifications
{
    private static final Specification<Car> overallSpecification = new Specification<Car>()
    {
        @Override
        public Predicate toPredicate(Root<Car> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder)
        {
            //Always true
            return criteriaBuilder.and();
        }
    };

    public static Specification<Car> carHasId(Integer id)
    {
        if (id == null)
        {
            return overallSpecification;
        }
        else
        {
            return new Specification<Car>()
            {
                @Override
                public Predicate toPredicate(Root<Car> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder)
                {
                    return criteriaBuilder.equal(root.get("id"), id);
                }
            };
        }
    }

    public static Specification<Car> carHasName(String name)
    {
        if (name == null || name.isEmpty())
        {
            return overallSpecification;
        }
        else
        {
            return new Specification<Car>()
            {
                @Override
                public Predicate toPredicate(Root<Car> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder)
                {
                    return criteriaBuilder.equal(root.get("name"), name);
                }
            };
        }
    }

    public static Specification<Car> carHasWheelAm(Short wheelAm)
    {
        if (wheelAm == null)
        {
            return overallSpecification;
        }
        else
        {
            return new Specification<Car>()
            {
                @Override
                public Predicate toPredicate(Root<Car> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder)
                {
                    return criteriaBuilder.equal(root.get("name"), wheelAm);
                }
            };
        }
    }

    public static Specification<Car> carHasWeight(Double weight)
    {
        if (weight == null)
        {
            return overallSpecification;
        }
        else
        {
            return new Specification<Car>()
            {
                @Override
                public Predicate toPredicate(Root<Car> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder)
                {
                    return criteriaBuilder.equal(root.get("weight"), weight);
                }
            };
        }
    }

    public static Specification<Car> carHasCreationDate(LocalDate creationDate)
    {
        if (creationDate == null)
        {
            return overallSpecification;
        }
        else
        {
            return new Specification<Car>()
            {
                @Override
                public Predicate toPredicate(Root<Car> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder)
                {
                    return criteriaBuilder.equal(root.<LocalDate>get("creationDate"), creationDate);
                }
            };
        }
    }
}