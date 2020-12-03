package com.lambdaschool.countries.controllers;

import com.lambdaschool.countries.models.Country;
import com.lambdaschool.countries.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class EmployeeController
{
    @Autowired
    CountryRepository countryrepos;

    private List<Country> findCountry(List<Country> myList, CheckCountry tester)
    {
        List<Country> tempList = new ArrayList<>();

        for (Country c : myList)
        {
            if (tester.test(c))
            {
                tempList.add(c);
            }
        }

        return tempList;
    }

    @GetMapping(value = "/names/all", produces = "application/json")
    public ResponseEntity<?> listAllCountries()
    {
        List<Country> myList = new ArrayList<>();
        countryrepos.findAll().iterator().forEachRemaining(myList::add);
        myList.sort((e1, e2) -> e1.getName().compareToIgnoreCase(e2.getName()));

        return new ResponseEntity<>(myList, HttpStatus.OK);
    }

    @GetMapping(value = "/names/start/{letter}", produces = "application/json")
    public ResponseEntity<?> listCountriesByName(@PathVariable char letter)
    {
        List<Country> myList = new ArrayList<>();
        countryrepos.findAll().iterator().forEachRemaining(myList::add);
        List<Country> rtnList = findCountry(myList, e -> e.getName().charAt(0) == letter);

        return new ResponseEntity<>(rtnList, HttpStatus.OK);
    }

    @GetMapping(value = "/population/total", produces = "application/json")
    public ResponseEntity<?> calcPopTotal()
    {
        List<Country> myList = new ArrayList<>();
        countryrepos.findAll().iterator().forEachRemaining(myList::add);

        long total = 0;
        for (Country c : myList)
        {
            total += c.getPopulation();
        }

        return new ResponseEntity<>(total, HttpStatus.OK);
    }

    @GetMapping(value = "/population/min", produces = "application/json")
    public ResponseEntity<?> minPopulation()
    {
        List<Country> myList = new ArrayList<>();
        countryrepos.findAll().iterator().forEachRemaining(myList::add);

        myList.sort((c1, c2) -> (int)(c1.getPopulation() - c2.getPopulation()));

        Country minPop = myList.get(0);
        return new ResponseEntity<>(minPop, HttpStatus.OK);
    }

    @GetMapping(value = "/population/max", produces = "application/json")
    public ResponseEntity<?> maxPopulation()
    {
        List<Country> myList = new ArrayList<>();
        countryrepos.findAll().iterator().forEachRemaining(myList::add);

        myList.sort((c1, c2) -> (int)(c2.getPopulation() - c1.getPopulation()));

        Country maxPop = myList.get(0);
        return new ResponseEntity<>(maxPop, HttpStatus.OK);
    }

}
