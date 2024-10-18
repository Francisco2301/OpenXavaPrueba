package com.tuempresa.facturacion.modelo;

import java.time.*;
import java.util.*;

import javax.persistence.*;
import javax.persistence.Entity;

import org.hibernate.annotations.*;
import org.openxava.annotations.*;
import org.openxava.calculators.*;

import com.tuempresa.facturacion.calculdores.*;

import lombok.*;

@Entity @Getter @Setter
public class Factura {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @Hidden
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(length = 32)
    String oid;
    
    @DefaultValueCalculator(CurrentYearCalculator.class)
    @Column(length = 4)
    int anyo;
    
    @Column(length = 6)
    @DefaultValueCalculator(
        value = CalculadorSiguienteNumeroParaAnyo.class,
        properties = @PropertyValue(name = "anyo", from = "anyo")
    )
    int numero;
    
    @Stereotype("DATE")
    @DefaultValueCalculator(CurrentDateCalculator.class)
    @Required
    @Column(name = "fecha")
    LocalDate fecha;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    Cliente cliente;
    
    @ElementCollection
    @ListProperties("producto.numero, producto.descripcion, cantidad")
    Collection<Detalle> detalles;
    
    @Stereotype("MEMO")
    String observaciones;
}
