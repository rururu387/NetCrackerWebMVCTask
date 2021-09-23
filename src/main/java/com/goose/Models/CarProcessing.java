package com.goose.Models;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.goose.Entities.Car;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CarProcessing
{
    public static String toJsonTable(List<Car> cars)
    {
        GsonBuilder gsonBuilder = new GsonBuilder().setPrettyPrinting();
        /*gsonBuilder.registerTypeAdapter(LocalDateTime.class, new JsonSerializer<LocalDateTime>()
        {
            @Override
            public JsonElement serialize(LocalDateTime localDateTime, Type type, JsonSerializationContext jsonDeserializationContext) throws JsonParseException
            {
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss, dd.MM.yyyy");
                return new JsonPrimitive(dateTimeFormatter.format(localDateTime));
            }
        });*/

        gsonBuilder.registerTypeAdapter(new TypeToken<List<Car>>() {}.getType(), new JsonSerializer<List<Car>>()
        {
            @Override
            public JsonElement serialize(List<Car> carList, Type type, JsonSerializationContext jsonSerializationContext)
            {
                JsonArray carArrayRepresentation = new JsonArray();

                for (int i = 0; i < carList.size(); i++)
                {
                    JsonObject carRepresentation = new JsonObject();
                    carRepresentation.addProperty(i + "i", carList.get(i).getId());
                    carRepresentation.addProperty(i + "n", carList.get(i).getName());
                    carRepresentation.addProperty(i + "w", carList.get(i).getWeight());
                    carRepresentation.addProperty(i + "wh", carList.get(i).getWheelAm());
                    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss, dd.MM.yyyy");
                    carRepresentation.addProperty(i + "c", dateTimeFormatter.format(carList.get(i).getCreationDate()));
                    carArrayRepresentation.add(carRepresentation);
                }

                return carArrayRepresentation;
            }
        });

        Gson gson = gsonBuilder.create();

        return gson.toJson(cars);
    }

    public static String toJson(Car car)
    {
        GsonBuilder gsonBuilder = new GsonBuilder().setPrettyPrinting();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new JsonSerializer<LocalDateTime>()
        {
            @Override
            public JsonElement serialize(LocalDateTime localDateTime, Type type, JsonSerializationContext jsonDeserializationContext) throws JsonParseException
            {
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss, dd.MM.yyyy");
                return new JsonPrimitive(dateTimeFormatter.format(localDateTime));
            }
        });

        Gson gson = gsonBuilder.create();

        return toJson(car, gson);
    }

    public static String toJson(Car car, Gson gson)
    {
        return gson.toJson(car);
    }
}
