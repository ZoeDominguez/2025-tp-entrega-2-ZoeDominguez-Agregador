package ar.edu.utn.dds.k3003.clients;
import ar.edu.utn.dds.k3003.dto.HechoDTO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;

public interface FuenteRetrofitClient {
    @GET("api/colecciones/{id}/hechos")
    Call<List<HechoDTO>> getHechosPorColeccion(@Path("id") String id);

    @GET("api/colecciones/{id}/hechos-sin-solicitudes")
    Call<List<HechoDTO>> getHechosPorColeccionSinSolicitudes(@Path("id") String id);

    @GET("api/hechos")
    Call<List<HechoDTO>> getHechos();
}