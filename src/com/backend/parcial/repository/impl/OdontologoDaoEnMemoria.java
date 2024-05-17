package com.backend.parcial.repository.impl;

import com.backend.parcial.entity.Odontologo;
import com.backend.parcial.repository.IDao;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OdontologoDaoEnMemoria implements IDao<Odontologo> {

    private final Logger LOGGER = Logger.getLogger(OdontologoDaoH2.class);
    private static Map<String, Odontologo> listaDeOdontologos = new HashMap<>();

    @Override
    public Odontologo registrar(Odontologo odontologo) {

        Odontologo odontologoRegistrado = null;

        try{
            //definir la key
            String key = odontologo.getNumeroDeMatricula();
            listaDeOdontologos.put(key, odontologo);
            odontologoRegistrado=listaDeOdontologos.get(key);
            LOGGER.info("Felicidades, se ha registrado con éxito en la memoria el odontologo: " + odontologoRegistrado);

        } catch (Exception e) {
            LOGGER.info("Lo siento, no se pudo registrar al odontologo con éxito en la memoria");
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }

        return odontologoRegistrado;
    }

    @Override
    public List<Odontologo> listarTodos() {

        List<Odontologo> odontologos = new ArrayList<>(listaDeOdontologos.values());
        LOGGER.info("Listado de todos los odontologos registrados en la memoria: " + odontologos);

        return odontologos;
    }

}
