package ru.kutnyashenko.retailservice.testtaskmiddle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.kutnyashenko.retailservice.testtaskmiddle.entity.Booking;
import ru.kutnyashenko.retailservice.testtaskmiddle.entity.Result;
import ru.kutnyashenko.retailservice.testtaskmiddle.service.WarehouseService;

@RestController
@RequestMapping("/set")
public class RESTController {

    private final WarehouseService warehouseService;

    @Autowired
    public RESTController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @PostMapping
    public Result sendBookingWarehouse(@RequestBody Booking booking) {
        return warehouseService.getResult(booking);
    }
}
