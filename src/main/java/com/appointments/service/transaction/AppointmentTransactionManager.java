package com.appointments.service.transaction;

import com.appointments.service.GreekToGreeklish;
import com.appointments.service.model.Appointment;
import com.appointments.service.model.DTO.AllAppointmentsPerUserDTO;
import com.appointments.service.model.DTO.OneAppointmentByIdRequestDTO;
import com.appointments.service.model.DTO.OrganizationRequestDTO;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@Service
public class AppointmentTransactionManager {

//    @Value("${url.db}")
//    private String url;
//
//    @Value("${url.username}")
//    private String username;
//
//    @Value("${url.password}")
//    private String password;


    private Jdbi jdbi = Jdbi.create("jdbc:mysql://localhost:3306/people?serverTimezone=UTC", "root", "root1234");

    public List<Appointment> getAllAppointmentsPerOrganization(OrganizationRequestDTO organizationRequestDTO) throws SQLException {

        String organization = GreekToGreeklish.convert(organizationRequestDTO.getOrganization());;
        Timestamp from = Timestamp.valueOf(organizationRequestDTO.getStartTime());
        Timestamp to = Timestamp.valueOf(organizationRequestDTO.getEndTime());



        String sqlQueryGetAllAppointments = " select * from "+organization+
                                            " where startTime >= " +"'"+from+"'" +
                                            " and endTime < " + "'"+to+"'";

        Handle handle = jdbi.open();

        List<Appointment> appointments = handle.createQuery(sqlQueryGetAllAppointments)
                .map((rs, ctx) -> new Appointment(
                        rs.getString("appointmentId"),
                        rs.getString("organization"),
                        rs.getString("userName"),
                        rs.getString("userId"),
                        rs.getString("adminName"),
                        rs.getString("adminId"),
                        rs.getTimestamp("startTime"),
                        rs.getTimestamp("endTime")
                        ))
                .list();

        handle.close();

        return appointments;
    }

    public Boolean storeAppointment(Appointment appointment){

        String organization = GreekToGreeklish.convert(appointment.getOrganizationSelected());

        String sqlQueryStoreAppointment = " insert into " + organization +
                                          " values (:appointmentId, :organization, " +
                                          " :userName, :userId, :adminName, :adminId, " +
                                          " :timeStart, :timeEnd)";

        String sqlQueryCheckIfTableExists = " SELECT count(*) " +
                " FROM information_schema.TABLES " +
                " WHERE (TABLE_SCHEMA = 'people') AND (TABLE_NAME = '"+organization+"') " ;

        Handle handle = jdbi.open();

        Integer tableExists = handle
                .select(sqlQueryCheckIfTableExists)
                .mapTo(Integer.class)
                .findOnly();

        if (tableExists == 0) {

            handle.execute(" create table " + organization +
                    " ( " +
                    " appointmentId int auto_increment primary key , " +  // TODO: check auto increment
                    " organization varchar(100) null, " +
                    " userName varchar(50) null," +
                    " userId varchar(50) null, " +
                    " adminName varchar(50) null, " +
                    " adminId int null, " +
                    " startTime timestamp null, " +
                    " endTime timestamp null " +
                    " ) "
            );

        }

        handle.createUpdate(sqlQueryStoreAppointment)
                .bind("appointmentId", appointment.getAppointmentId())
                .bind("organization", appointment.getOrganizationSelected())
                .bind("userName", appointment.getUserName())
                .bind("userId", appointment.getUserId())
                .bind("adminName", appointment.getAdminName())
                .bind("adminId", appointment.getAppointmentId())
                .bind("timeStart", appointment.getStartTime())
                .bind("timeEnd", appointment.getEndTime())
                .execute();

        return Boolean.valueOf(true);

    }

    public Appointment getAppointmentPerId(OneAppointmentByIdRequestDTO oneAppointmentByIdRequestDTO) {

        String appointmentId = oneAppointmentByIdRequestDTO.getAppointmentId();
        String organization =  GreekToGreeklish.convert(oneAppointmentByIdRequestDTO.getOrganization());


        String sqlQueryGetAppointmentById = "select * from "+ organization+" where appointmentId = '"+appointmentId+"'";

        Handle handle = jdbi.open();

        Appointment appointment = handle.createQuery(sqlQueryGetAppointmentById)
                .map((rs, ctx) -> new Appointment(
                        rs.getString("appointmentId"),
                        rs.getString("organization"),
                        rs.getString("userName"),
                        rs.getString("userId"),
                        rs.getString("adminName"),
                        rs.getString("adminId"),
                        rs.getTimestamp("startTime"),
                        rs.getTimestamp("endTime")
                ))
                .findOnly();

        handle.close();

        return appointment;

    }

    public List<Appointment> getAllAppointmentsPerUser(AllAppointmentsPerUserDTO allAppointmentsPerUserDTO) {

        String userId = allAppointmentsPerUserDTO.getUserId();
        String organization =  GreekToGreeklish.convert(allAppointmentsPerUserDTO.getOrganization());

        String sqlQueryGetAppointsPerUser = "select * from "+ organization +" where userId = '"+userId+"'";

        Handle handle = jdbi.open();

        List<Appointment> appointments = handle.createQuery(sqlQueryGetAppointsPerUser)
                .map((rs, ctx) -> new Appointment(
                        rs.getString("appointmentId"),
                        rs.getString("organization"),
                        rs.getString("userName"),
                        rs.getString("userId"),
                        rs.getString("adminName"),
                        rs.getString("adminId"),
                        rs.getTimestamp("startTime"),
                        rs.getTimestamp("endTime")
                ))
                .list();

        handle.close();

        return appointments;
    }

    public void deleteAppointmentPerId(OneAppointmentByIdRequestDTO oneAppointmentByIdRequestDTO) {

        String appointmentId = oneAppointmentByIdRequestDTO.getAppointmentId();
        String organization =  GreekToGreeklish.convert(oneAppointmentByIdRequestDTO.getOrganization());


        String sqlQueryDeleteAppointmentById = "delete from "+organization+" where appointmentId = '"+appointmentId+"'";

        Handle handle = jdbi.open();

        handle.createUpdate(sqlQueryDeleteAppointmentById).execute();

    }

}