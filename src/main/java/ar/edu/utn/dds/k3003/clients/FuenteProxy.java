package ar.edu.utn.dds.k3003.clients;

import ar.edu.utn.dds.k3003.app.FachadaFuente;
import ar.edu.utn.dds.k3003.dto.HechoDTO;
import okhttp3.OkHttpClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

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
    public List<HechoDTO> buscarHechosXColeccion(String id) {
        System.out.println("Buscando hechos para colección ID: '" + id + "'");
        try {
            var response = service.getHechosPorColeccion(id).execute();
            System.out.println("Response code: " + response.code());
            if (response.isSuccessful() && response.body() != null) {
                System.out.println("Hechos encontrados: " + response.body().size());
                return response.body();
            }
            throw new RuntimeException("Error al obtener hechos: " + response.code());
        } catch (Exception e) {
            throw new RuntimeException("Fallo en la comunicación con fuente", e);
        }
    }

    @Override
    public List<HechoDTO> buscarHechosXColeccionSinSolicitudes(String id){
        System.out.println("Buscando hechos para colección ID: '" + id + "'");
        try {
            var response = service.getHechosPorColeccionSinSolicitudes(id).execute();
            System.out.println("Response code: " + response.code());
            if (response.isSuccessful() && response.body() != null) {
                System.out.println("Hechos encontrados: " + response.body().size());
                return response.body();
            }
            throw new RuntimeException("Error al obtener hechos: " + response.code());
        } catch (Exception e) {
            throw new RuntimeException("Fallo en la comunicación con fuente", e);
        }
    }

}
