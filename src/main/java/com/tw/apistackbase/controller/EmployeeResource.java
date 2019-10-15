package com.tw.apistackbase.controller;

import com.tw.apistackbase.model.Employee;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/employee")
public class EmployeeResource {
    private List<Employee> employeeList = new ArrayList<>();
    private final Logger log = Logger.getLogger(this.getClass().getName());

    @GetMapping(path = "/getAll", produces = {"application/json"})
    public List<Employee> getAll() {
        return employeeList;
    }

    @PostMapping(path = "/add", produces = {"application/json"})
    public ResponseEntity<List<Employee>> addEmployee(@RequestBody List<Employee> employeeList){
        this.employeeList.addAll(employeeList);
        return ResponseEntity.ok(employeeList);
    }

    @PutMapping(path = "/update/{employeeId}", produces = {"application/json"})
    public ResponseEntity<List<Employee>> updateEmployee(@PathVariable int employeeId, @RequestBody Employee employee){
        int index = employeeList.indexOf(getEmployeeUsingId(employeeId));
        employeeList.set(index, employee);
        return ResponseEntity.ok(employeeList);
    }

    @DeleteMapping(path = "/delete/{employeeId}", produces = {"application/json"})
    public ResponseEntity<String> deleteEmployee(@PathVariable int employeeId){
        if(employeeList.remove(getEmployeeUsingId(employeeId)))
            return ResponseEntity.ok("Employee with Id number: " + employeeId + " has been deleted");

        return ResponseEntity.ok("No existing employee with Id number " + employeeId);
    }

    private Employee getEmployeeUsingId(int employeeId) {
        return employeeList.stream()
                .filter(employee -> employee.getId() == employeeId)
                .findAny()
                .orElse(null);
    }
}

