package ar.edu.utn.dds.k3003.app;

import ar.edu.utn.dds.k3003.clients.FuenteProxy;
import ar.edu.utn.dds.k3003.facades.dtos.Constants;
import ar.edu.utn.dds.k3003.facades.dtos.FuenteDTO;
import jakarta.annotation.PostConstruct;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

@SpringBootApplication(scanBasePackages = "ar.edu.utn.dds.k3003")
public class WebApp {

    @Autowired
    private Fachada fachada;

    @Autowired
    @Lazy
    private ObjectMapper objectMapper;

    public static void main(String[] args) {
        SpringApplication.run(WebApp.class, args);
    }

    @PostConstruct
    public void init() {
        // Fuente remota
        FuenteDTO fuenteDTO = new FuenteDTO("1", "Fuente Remota", "https://two025-tp-juampivan.onrender.com");
        fachada.agregar(fuenteDTO);
        fachada.addFachadaFuentes("1", new FuenteProxy(objectMapper));

        System.out.println("Fuente remota cargada y fachada asignada correctamente.");
    }

    @Bean
    @Lazy
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DEFAULT_SERIALIZATION_FORMAT, Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        objectMapper.setDateFormat(sdf);

        return objectMapper;
    }
}