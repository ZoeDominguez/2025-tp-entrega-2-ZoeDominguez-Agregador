package ar.edu.utn.dds.k3003.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import ar.edu.utn.dds.k3003.app.Fachada;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collections;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ColeccionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Fachada fachadaMock;

    @Test
    public void listarHechosPorColeccion_DeberiaRetornarNotFoundSiNoHayHechos() throws Exception {
        when(fachadaMock.hechos(anyString())).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/colecciones/test/hechos"))
                .andExpect(status().isNoContent());
    }

}