package ar.edu.utn.dds.k3003.clients;
import ar.edu.utn.dds.k3003.facades.dtos.ColeccionDTO;
import ar.edu.utn.dds.k3003.facades.dtos.HechoDTO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;

public interface FuenteRetrofitClient {
    @GET("api/colecciones")
    Call<List<ColeccionDTO>> getColecciones();

    @GET("api/colecciones/{id}/hechos")
    Call<List<HechoDTO>> getHechosPorColeccion(@Path("id") String id);
}