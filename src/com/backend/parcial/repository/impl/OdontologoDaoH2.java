package com.backend.parcial.repository.impl;

import com.backend.parcial.dbconnection.H2Connection;
import com.backend.parcial.entity.Odontologo;
import com.backend.parcial.repository.IDao;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OdontologoDaoH2 implements IDao<Odontologo> {

    private final Logger LOGGER = Logger.getLogger(OdontologoDaoH2.class);

    @Override
    public Odontologo registrar(Odontologo odontologo) {
        Connection connection = null;
        Odontologo odontologoRegistrado = null;

        try{
            connection = H2Connection.getConnection();
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO ODONTOLOGOS (NUMERODEMATRICULA, NOMBRE, APELLIDO) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, odontologo.getNumeroDeMatricula());
            preparedStatement.setString(2, odontologo.getNombre());
            preparedStatement.setString(3, odontologo.getApellido());
            preparedStatement.execute();

            odontologoRegistrado = new Odontologo(odontologo.getNumeroDeMatricula(), odontologo.getNombre(), odontologo.getApellido());

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            while(resultSet.next()) {
                odontologoRegistrado.setId(resultSet.getLong("id"));
            }

            connection.commit();

            LOGGER.info("Felicidades, se ha registrado con éxito en la BDD H2 el odontologo: " + odontologoRegistrado);



        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            if (connection != null) {
                try {
                    connection.rollback();
                    LOGGER.info("Lo siento, no se pudo registrar al odontologo con éxito en la BDD H2");
                    LOGGER.error(e.getMessage());
                    e.printStackTrace();
                } catch (SQLException exception) {
                    LOGGER.error(exception.getMessage());
                    exception.printStackTrace();
                }
            }
        } finally {
            try {
                connection.close();
            } catch (Exception ex) {
                LOGGER.error("Lo siento, no fue posible cerrar la conexion: " + ex.getMessage());
            }
        }


        return odontologoRegistrado;
    }

    @Override
    public List<Odontologo> listarTodos() {

        Connection connection = null;
        List<Odontologo> listaDeOdontologos = new ArrayList<>();
        try{
            connection = H2Connection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM ODONTOLOGOS");

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){

                Odontologo odontologo = crearObjetoOdontologo(resultSet);
                listaDeOdontologos.add(odontologo);
            }


        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();

        } finally {
            try {
                connection.close();
            } catch (Exception ex) {
                LOGGER.error("Lo siento, no fue posible cerrar la conexion  con la base de datos H2: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
        LOGGER.info("Listado de todos los odontologos registrados en la BDD H2: " + listaDeOdontologos);

        return listaDeOdontologos;
    }

    private Odontologo crearObjetoOdontologo(ResultSet resultSet) throws SQLException {

        return new Odontologo(resultSet.getLong("id"), resultSet.getString("numeroDeMatricula"), resultSet.getString("nombre"), resultSet.getString("apellido"));
    }

}
