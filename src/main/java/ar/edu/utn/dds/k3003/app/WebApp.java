package ar.edu.utn.dds.k3003.app;

import ar.edu.utn.dds.k3003.clients.FuenteProxy;
import ar.edu.utn.dds.k3003.controller.ColeccionController;
import ar.edu.utn.dds.k3003.controller.FuenteController;
import ar.edu.utn.dds.k3003.facades.dtos.Constants;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.javalin.Javalin;
import io.javalin.json.JavalinJackson;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

import static ar.edu.utn.dds.k3003.Evaluador.createObjectMapper;

@SpringBootApplication(scanBasePackages = "ar.edu.utn.dds.k3003")
public class WebApp {

    public static void main(String[] args) {
        var env = System.getenv();
        var objectMapper = createObjectMapper();
        var fachada = new Fachada();
        fachada.setFuenteProxy(new FuenteProxy(objectMapper));

        var port = Integer.parseInt(env.getOrDefault("PORT", "8080"));

        var app = Javalin.create(config -> {
            config.jsonMapper(new JavalinJackson().updateMapper(mapper -> {
                configureObjectMapper(mapper);
            }));
        }).start(port);

        var coleccionController = new ColeccionController(fachada);
        app.get("/colecciones/{coleccion}/hechos", coleccionController::listarHechosPorColeccion);
    }

    public static ObjectMapper createObjectMapper() {
        var objectMapper = new ObjectMapper();
        configureObjectMapper(objectMapper);
        return objectMapper;
    }

    public static void configureObjectMapper(ObjectMapper objectMapper) {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        var sdf = new SimpleDateFormat(Constants.DEFAULT_SERIALIZATION_FORMAT, Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        objectMapper.setDateFormat(sdf);
    }
}
