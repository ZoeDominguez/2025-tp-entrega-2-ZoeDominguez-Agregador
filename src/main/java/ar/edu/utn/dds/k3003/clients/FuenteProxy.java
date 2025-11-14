package ar.edu.utn.dds.k3003.clients;

import ar.edu.utn.dds.k3003.app.FachadaFuente;
import ar.edu.utn.dds.k3003.dto.HechoDTO;
import okhttp3.OkHttpClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class FuenteProxy implements FachadaFuente {

    private final FuenteRetrofitClient service;

    public FuenteProxy(ObjectMapper objectMapper, String baseUrl) {

         OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)  
                .readTimeout(15, TimeUnit.SECONDS)     
                .writeTimeout(15, TimeUnit.SECONDS)  
                .build();

        var retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl.endsWith("/") ? baseUrl : baseUrl + "/")
                .client(okHttpClient)
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .build();

        this.service = retrofit.create(FuenteRetrofitClient.class);
    }

    @Override
    public List<HechoDTO> buscarHechosXColeccion(String coleccionId) {
        if (coleccionId == null || coleccionId.isEmpty()) {
            throw new IllegalArgumentException("El ID de colección no puede ser nulo o vacío");
        }

        System.out.println("Buscando hechos para colección ID: '" + coleccionId + "'");
        try {
            var response = service.getHechosPorColeccion(coleccionId).execute();
            System.out.println("Response code: " + response.code());
            if (response.isSuccessful() && response.body() != null) {
                System.out.println("Hechos encontrados: " + response.body().size());
                return response.body();
            }
            System.out.println("Fuente sin hechos o sin respuesta válida (" + response.code() + ")");
            return Collections.emptyList();
        } catch (Exception e) {
            throw new RuntimeException("Fallo en la comunicación con fuente", e);
        }
    }

    @Override
    public List<HechoDTO> buscarHechosXColeccionSinSolicitudes(String coleccionId) {
        if (coleccionId == null || coleccionId.isEmpty()) {
            throw new IllegalArgumentException("El ID de colección no puede ser nulo o vacío");
        }

        System.out.println("Buscando hechos (sin solicitudes) para colección ID: '" + coleccionId + "'");
        try {
            var response = service.getHechosPorColeccionSinSolicitudes(coleccionId).execute();
            System.out.println("Response code: " + response.code());
            if (response.isSuccessful() && response.body() != null) {
                System.out.println("Hechos encontrados: " + response.body().size());
                return response.body();
            }
            System.out.println("Fuente sin hechos o sin respuesta válida (" + response.code() + ")");
            return Collections.emptyList();
        } catch (Exception e) {
            throw new RuntimeException("Fallo en la comunicación con fuente", e);
        }
    }

    @Override
    public List<HechoDTO> buscarHechos() {
        System.out.println("Buscando todos los hechos...");
        try {
            var response = service.getHechos().execute();
            System.out.println("Response code: " + response.code());
            if (response.isSuccessful() && response.body() != null) {
                System.out.println("Hechos encontrados: " + response.body().size());
                return response.body();
            }
            System.out.println("Fuente sin hechos o sin respuesta válida (" + response.code() + ")");
            return Collections.emptyList();
        } catch (Exception e) {
            throw new RuntimeException("Fallo en la comunicación con fuente", e);
        }
    }


}
