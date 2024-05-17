package com.backend.parcial.test;

import com.backend.parcial.entity.Odontologo;
import com.backend.parcial.repository.impl.OdontologoDaoEnMemoria;
import com.backend.parcial.repository.impl.OdontologoDaoH2;
import com.backend.parcial.service.impl.OdontologoService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OdontologoServiceTest {

    private OdontologoService odontologoService;

    @Test
    void deberiaRetornarseUnaListaNoVaciaDeOdontologosEnH2(){
        odontologoService = new OdontologoService(new OdontologoDaoH2());
        assertFalse(odontologoService.listarOdontologos().isEmpty());
    }

    @Test
    void deberiaRetornarseUnaListaNoVaciaDeOdontologosEnMemoria(){
        odontologoService = new OdontologoService(new OdontologoDaoEnMemoria());
        Odontologo odontologo1 = new Odontologo(1L,"111","Lola", "Hurtado");
        Odontologo odontologo2 = new Odontologo(2L,"112","Bruno", "Diaz");
        odontologoService.registrarOdontologo(odontologo1);
        odontologoService.registrarOdontologo(odontologo2);
        assertFalse(odontologoService.listarOdontologos().isEmpty());
    }


}