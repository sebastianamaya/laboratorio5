/*
 * Copyright (C) 2016 hcadavid
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package edu.eci.pdsw.samples.services;

import edu.eci.pdsw.samples.entities.Consulta;
import edu.eci.pdsw.samples.entities.Paciente;
import java.sql.Date;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hcadavid
 */
public class ServiciosPacientesStub extends ServiciosPacientes{

    private final Map<Tupla<Integer,String>,Paciente> pacientes;

    public ServiciosPacientesStub() {
        this.pacientes = new LinkedHashMap<>();
        cargarDatosEstaticos(pacientes);
    }
    
    
    @Override
    public Paciente consultarPaciente(int idPaciente, String tipoid) throws ExcepcionServiciosPacientes {
        Paciente p=pacientes.get(new Tupla<>(idPaciente,tipoid));
        if (p==null){
            throw new ExcepcionServiciosPacientes("Paciente "+idPaciente+" no esta registrado");
        }
        else{
            return p;
        }
        
    }

    @Override
    public void registrarNuevoPaciente(Paciente p) throws ExcepcionServiciosPacientes {
        pacientes.put(new Tupla<>(p.getId(),p.getTipo_id()), p);
    }

    @Override
    public void agregarConsultaAPaciente(int idPaciente, String tipoid, Consulta c) throws ExcepcionServiciosPacientes {
        Paciente p=pacientes.get(new Tupla<>(idPaciente,tipoid));
        if (p==null){
            throw new ExcepcionServiciosPacientes("Paciente "+idPaciente+" no esta registrado");
        }
        else{
            p.getConsultas().add(c);
        }
    }
    

    private void cargarDatosEstaticos(Map<Tupla<Integer,String>,Paciente> pacientes){        
        try {
            registrarNuevoPaciente(new Paciente(123, "CC", "Juan Perez", java.sql.Date.valueOf("2000-01-01")));
            registrarNuevoPaciente(new Paciente(321, "CC", "Maria Rodriguez", java.sql.Date.valueOf("2000-01-01")));
            registrarNuevoPaciente(new Paciente(875, "CC", "Pedro Martinez", java.sql.Date.valueOf("1956-05-01")));
            
        } catch (ExcepcionServiciosPacientes ex) {
            Logger.getLogger(ServiciosPacientesStub.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
   

}


class Tupla<A,B>{
    
    A a;
    B b;

    public Tupla(A a, B b) {
        this.a = a;
        this.b = b;
    }

    public A getA() {
        return a;
    }


    public B getB() {
        return b;
    }



    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.a);
        hash = 71 * hash + Objects.hashCode(this.b);        
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Tupla<?, ?> other = (Tupla<?, ?>) obj;
        if (!Objects.equals(this.a, other.a)) {
            return false;
        }
        if (!Objects.equals(this.b, other.b)) {
            return false;
        }
        return true;
    }
    
    
    
}
