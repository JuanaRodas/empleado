package com.udea.empleado.controller;

import com.udea.empleado.exception.ModelNotFoundException;
import com.udea.empleado.model.Persona;
import com.udea.empleado.service.PersonaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/persona")
@CrossOrigin("*")

@Api(value = "Employee Management System", description = "Operations pertaining to a person in Employee Management System.")
public class PersonaController {
    @Autowired
    PersonaService personService;
    
    @ApiOperation(value = "Add a person.")
    @PostMapping("/save")
    public ResponseEntity save(@ApiParam(value = "Person object is stored in database table.", required = true) @RequestBody Persona person) {
            personService.save(person);
            return ResponseEntity
                .status(HttpStatus.OK)
                .body("Person successfully added.");
    }

    @ApiOperation(value="Update a person.")
    @PutMapping("/update/{id}")
    public ResponseEntity update(@ApiParam(value="Person object that will replace existing person in the database.", required=true) @RequestBody Persona newPerson, @ApiParam(value="ID of the person that will be update in the database.", required=true) @PathVariable("id") long id){
        Optional<Persona> optionalPerson = personService.listId(id);
        if(optionalPerson.isPresent()){
            Persona person = optionalPerson.get();
            person.setFirstName(newPerson.getFirstName());
            person.setLastName(newPerson.getLastName());
            person.setEmail(newPerson.getEmail());
            person.setPhone (newPerson.getPhone());
            person.setAddress(newPerson.getAddress());
            person.setSalary(newPerson.getSalary());
            person.setDepartment(newPerson.getDepartment());
            person.setPosition(newPerson.getPosition());
            person.setOffice(newPerson.getOffice());
            person.setHiringDate(newPerson.getHiringDate());
            personService.save(person);
            return ResponseEntity
                .status(HttpStatus.OK)
                .body("Person successfully updated.");
        }
        throw new ModelNotFoundException("Invalid person ID.");
    }
    
    @ApiOperation(value="Delete a person.")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@ApiParam(value="ID of the person that will be delete from database.", required=true) @PathVariable("id") int id){
        Optional<Persona> optionalPerson = personService.listId(id);
        if(optionalPerson.isPresent()){
            Persona person = optionalPerson.get();
            personService.delete(person);
            return ResponseEntity
                .status(HttpStatus.OK)
                .body("Person successfully deleted.");
        }
        throw new ModelNotFoundException("Invalid person ID.");
    }
    
    @ApiOperation(value = "View a list of available persons.", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list."),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource."),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden."),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found.")
    })
    @GetMapping("/listAll")
    public Iterable<Persona> listAllPersons() {
        return personService.list();
    }
    
    @ApiOperation(value = "Get a person by Id.")
    @GetMapping("/list/{id}")
    public Persona listPersonById(@ApiParam(value = "ID of the person that will be retrieve from database.", required = true) @PathVariable("id") int id) {
        Optional<Persona> person = personService.listId(id);
        if (person.isPresent()) {
            return person.get();
        }
        throw new ModelNotFoundException("Invalid person ID");}
    
    // increaseSalary
    @ApiOperation(value="Increase person's salary by 10% if hiring date was at least two years ago.")
    @PostMapping("/increaseSalary/{id}")
    public ResponseEntity increaseSalary(@ApiParam(value="ID of the person whose salary is going to be increased if it meets the condition.", required=true) @PathVariable("id") long id){
        Optional<Persona> optionalPerson = personService.listId(id);
        if(optionalPerson.isPresent()){
            Persona person = optionalPerson.get();
            LocalDate currentDate = LocalDate.now();
            Period period = Period.between(currentDate, person.getHiringDate());
            if(period.getYears() <= -2){
                person.setSalary(person.getSalary()+(person.getSalary()*0.1));
                personService.save(person);
                return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("Person's salary increased successfully.");
            }
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Person's hiring date is not at least two years ago.");
        }
        throw new ModelNotFoundException("Invalid person ID.");
    }
    
}

