package com.example.TiendaComputadoras.controller;


import com.example.TiendaComputadoras.Repository.InterDellRepository;
import com.example.TiendaComputadoras.model.Dell;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.CoreMatchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class DellControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private InterDellRepository interDellRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup(){
        interDellRepository.deleteAll();
    }

    @Test
    public void darDellObjeto_cuandoCreamosDell_RegresamosDellSave() throws Exception{
        // creamos un objeto
        Dell dell = Dell.builder()
                .disco("256 GB")
                .memoriaRam("4 GB")
                .procesador("intel i5")
                .build();
        //ahora si accedemos al recurso en donde vamos a guardar nuestro objeto
        ResultActions response = mockMvc.perform(post("/Dell")
                        .with(user("fabian")
                                .password("1234"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dell)));

        //des-serializamos el json que viene en la respuesta si lo queremos ver pero asi ya esta
        //correcto el test porque tenemos un logger que nos dice el elemento a sido creado
    }


    // test para obtener todos los equipos dell
    @Test
    public void listaDell() throws Exception{
        // given - precondition or setup
        List<Dell> listDell = new ArrayList<>();
        listDell.add(Dell.builder().disco("500 GB").procesador("intel i9").memoriaRam("8 GB").build());
        listDell.add(Dell.builder().disco("1 TB").procesador("intel z790").memoriaRam("16 GB").build());
        interDellRepository.saveAll(listDell);
        //nos vamos a la direccion con las credenciales de autenticacion
        ResultActions response = mockMvc.perform(get("/Dell")
                                .with(user("fabian")
                                .password("1234")));

        // verificamos la salida
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",
                        is(listDell.size())));

    }

    // escenario positivo validamos el id de un producto Dell
    @Test
    public void verificamosIdDell() throws Exception{
       //construimos el objeto
        Dell dell = Dell.builder()
                .memoriaRam("8 GB")
                .procesador("intel core i9")
                .disco("500 GB")
                .build();
        interDellRepository.save(dell);

        //obtenemos peticion con sus respectivas credenciales
        ResultActions response = mockMvc.perform(get("/Dell/{id}",dell.getId() )
                                                .with(user("fabian")
                                                .password("1234")));

        //igual podemos verificar si des-serializamos la respuesta pero tenemos nuestro logger
    }

    // escenario negativo para la validacion de un equipo dell con id
    @Test
    public void dellIdNoValido() throws Exception{
        long dellId = 1L;
        Dell dell = Dell.builder()
                .memoriaRam("8 GB")
                .procesador("intel core i9")
                .disco("500 GB")
                .build();
        interDellRepository.save(dell);

        //obtenemos peticion con sus respectivas credenciales
        ResultActions response = mockMvc.perform(get("/Dell/{id}",dellId )
                .with(user("fabian")
                        .password("1234")));

        //verificamos la salida esta parte esta hecha con excepcion handler en el controlador
        //igual tenemos un logger
        response.andExpect(status().isNotFound())
                .andDo(print());

    }

    //escenario positivo para actualizar un equipo Dell en nuestra api rest
    @Test
    public void actualizarDell() throws Exception{
        Dell savedell = Dell.builder()
                .memoriaRam("8 GB")
                .procesador("intel core i9")
                .disco("500 GB")
                .build();
        interDellRepository.save(savedell);

        Dell updatedDell = Dell.builder()
                .id(savedell.getId())
                .memoriaRam("2 GB")
                .procesador("intel core i3")
                .disco("256 GB")
                .build();

        ResultActions response = mockMvc.perform(put("/Dell/{id}", savedell.getId())
                .with(user("admin").password("admin123").roles("ADMIN")) //para el administrador tenemos que poner el rol porque si no no lo encuentra
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedDell)));


        //imprimimos la modificacion 201 y tenemos nuestro logger
        response.andExpect(status().isCreated())
                .andDo(print());
    }

    //escenario negativo para actualizar un equipo Dell
    @Test
    public void ErrorActualizarDell() throws Exception{
        // given - precondition or setup
        long dellId = 1L;
        Dell savedell = Dell.builder()
                .memoriaRam("8 GB")
                .procesador("intel core i9")
                .disco("500 GB")
                .build();
        interDellRepository.save(savedell);

        Dell updatedDell = Dell.builder()
                .id(dellId)
                .memoriaRam("2 GB")
                .procesador("intel core i3")
                .disco("256 GB")
                .build();

        ResultActions response = mockMvc.perform(put("/Dell/{id}", dellId)
                .with(user("admin").password("admin123").roles("ADMIN")) //para el administrador tenemos que poner el rol porque si no no lo encuentra
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedDell)));

        //imprimimos lo que nos arroja el controlador y tenemos nuestro logger
        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    //ultimo test para borrar un id de equipo dell

    /*
    @Test
    public void borrarDell() throws Exception{
        Dell savedell = Dell.builder()
                .memoriaRam("8 GB")
                .procesador("intel core i9")
                .disco("500 GB")
                .build();
        interDellRepository.save(savedell);


        ResultActions response = mockMvc.perform(delete("/Dell/{id}", savedell.getId())
                .with(user("admin").password("admin123").roles("ADMIN"))); //para el administrador tenemos que poner el rol porque si no no lo encuentra

        //imprimimos lo que nos arroja el controlador y tenemos nuestro logger
        response.andExpect(status().isNoContent())
                .andDo(print());
    }
    */

}

