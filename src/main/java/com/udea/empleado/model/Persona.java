/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.udea.empleado.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ApiModel(description="All details about the person.")
@ToString
@Entity
public class Persona implements Serializable{
    
    @ApiModelProperty(notes="The database generates the person's ID.")
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="idperson")
    private Long idPerson;
    
    @ApiModelProperty(notes="The person's first name.")
    @Column(name="firstname", nullable=false, length=80)
    private @NonNull String firstName;
    
    @ApiModelProperty(notes="The person's last name.")
    @Column(name="lasttname", nullable=false, length=80)
    private @NonNull String lastName;
    
    @ApiModelProperty(notes="The person's email.")
    @Column(name="email", nullable=false, length=80)
    private @NonNull String email;
    
    @ApiModelProperty(notes="The person's phone number.")
    @Column(name="phone", nullable=false, length=80)
    private int phone;
    
    @ApiModelProperty(notes="The person's address.")
    @Column(name="address", nullable=false, length=80)
    private @NonNull String address;
    
    @ApiModelProperty(notes="The person's salary.")
    @Column(name="salary", nullable=false, length=80)
    private double salary;
    
    @ApiModelProperty(notes="The person's department.")
    @Column(name="department", nullable=false, length=80)
    private @NonNull String department;
    
    @ApiModelProperty(notes="The person's position.")
    @Column(name="position", nullable=false, length=80)
    private @NonNull String position;
        
    @ApiModelProperty(notes="The person's office.")
    @Column(name="office", nullable=false, length=80)
    private @NonNull String office;
    
    @ApiModelProperty(notes="The date the person was hired.")
    @Column(name="hiringdate", nullable=false, length=80)
    private @NonNull LocalDate hiringDate;
}
