package com.example.TiendaComputadoras.Repository;

import com.example.TiendaComputadoras.model.Dell;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class InterDellRepositoryTest {
//tenemos en esta parte 5 test no puse los de la otra clase porque son iguales
    @Autowired
    private InterDellRepository interDellRepository;
    @Test
    void findAll_Test(){
        Iterable<Dell> dells = interDellRepository.findAll();
        Assertions.assertThat(dells).extracting(Dell::getId);
    }//ahora si que hacemos el selec en sql


    @Test
    void findAllById_Test(){
        //construimos el objeto
        Dell dell = Dell.builder()
                .memoriaRam("8 GB")
                .procesador("intel core i9")
                .disco("500 GB")
                .build();
        interDellRepository.save(dell);
        Optional<Dell> foundDell = interDellRepository.findById(dell.getId());

        assertThat(foundDell.get().getId()).isEqualTo(dell.getId());
        assertThat(foundDell.get().getProcesador()).isEqualTo(dell.getProcesador());
        assertThat(foundDell.get().getMemoriaRam()).isEqualTo(dell.getMemoriaRam());
        assertThat(foundDell.get().getDisco()).isEqualTo(dell.getDisco());
    }


    @Test
    void save_Test(){
        Dell dell = new Dell();
        dell.setProcesador("intel i3");
        dell.setMemoriaRam("2 Gb");
        dell.setDisco("256 GB");
        interDellRepository.save(dell);
        assertThat(dell).hasFieldOrPropertyWithValue("procesador", "intel i3");
    }
    @Test
    void update_Test(){
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
        assertThat(updatedDell).hasFieldOrPropertyWithValue("procesador","intel core i3");
    }

    @Test
    void delete_Test(){
        Dell dell = new Dell();
        dell.setProcesador("intel i11");
        dell.setMemoriaRam("32 Gb");
        dell.setDisco("2 TB");
        interDellRepository.save(dell);
        interDellRepository.deleteById(dell.getId());
        Optional<Dell> foundDell = interDellRepository.findById(dell.getId());
        assertThat(foundDell).isEmpty();
    }


}
