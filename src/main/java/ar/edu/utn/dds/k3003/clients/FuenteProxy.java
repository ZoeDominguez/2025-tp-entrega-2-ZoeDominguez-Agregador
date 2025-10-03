package ar.edu.utn.dds.k3003.clients;

import ar.edu.utn.dds.k3003.app.FachadaFuente;
import ar.edu.utn.dds.k3003.dto.HechoDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.List;

public class FuenteProxy implements FachadaFuente {

    private final FuenteRetrofitClient service;

    public FuenteProxy(ObjectMapper objectMapper, String baseUrl) {
        var retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl.endsWith("/") ? baseUrl : baseUrl + "/")
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .build();

        this.service = retrofit.create(FuenteRetrofitClient.class);
    }

    // @Override
    // public List<ColeccionDTO> colecciones() {
    //     try {
    //         var response = service.getColecciones().execute();
    //         if (response.isSuccessful() && response.body() != null) {
    //             return response.body();
    //         }
    //         throw new RuntimeException("Error al obtener colecciones: " + response.code());
    //     } catch (Exception e) {
    //         throw new RuntimeException("Fallo en la comunicación con fuente", e);
    //     }
    // }

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

    // @Override
    // public ColeccionDTO agregar(ColeccionDTO coleccionDTO) { return null; }

    // @Override
    // public ColeccionDTO buscarColeccionXId(String id) { return null; }

    // @Override
    // public HechoDTO agregar(HechoDTO hechoDTO) { return null; }

    // @Override
    // public HechoDTO buscarHechoXId(String id) { return null; }

    // @Override
    // public void setProcesadorPdI(FachadaProcesadorPdI fachadaProcesadorPdI) { }

    // @Override
    // public PdIDTO agregar(PdIDTO pdIDTO) { return null; }
}
