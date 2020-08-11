package com.appointments.service.transaction;

import com.appointments.service.model.Appointment;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class AppointmentTransactionManager {

    private Jdbi jdbi = Jdbi.create("jdbc:mysql://localhost:3306/people?serverTimezone=UTC", "root", "root1234");

    public List<Appointment> getAllAppointmentsPerOrganization(String organization) throws SQLException {

        String sqlQueryCheckIfTableExists = " SELECT count(*) " +
                                            " FROM information_schema.TABLES " +
                                            " WHERE (TABLE_SCHEMA = 'people') AND (TABLE_NAME = '"+organization+"') " ;

        String sqlQueryGetAllAppointments = " select * from " + organization;

        Handle handle = jdbi.open();

        Integer tableExists = handle
                .select(sqlQueryCheckIfTableExists)
                .mapTo(Integer.class)
                .findOnly();

        if (tableExists == 0) {

            handle.execute(" create table " + organization +
                                " ( " +
                                " appointmentId int null, " +
                                " organization varchar(100) null, " +
                                " userName varchar(50) null, " +
                                " adminName varchar(50) null, " +
                                " startTime timestamp null, " +
                                " endTime timestamp null " +
                                " ) "
            );

        }

        List<Appointment> appointments = handle.createQuery(sqlQueryGetAllAppointments)
                .map((rs, ctx) -> new Appointment(
                        rs.getString("appointmentId"),
                        rs.getString("organization"),
                        rs.getString("userName"),
                        rs.getString("adminName"),
                        rs.getTimestamp("startTime"),
                        rs.getTimestamp("endTime")
                        ))
                .list();

        handle.close();

        return appointments;
    }
}