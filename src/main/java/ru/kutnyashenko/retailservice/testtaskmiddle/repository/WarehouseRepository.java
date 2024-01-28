package ru.kutnyashenko.retailservice.testtaskmiddle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kutnyashenko.retailservice.testtaskmiddle.entity.Warehouse;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Integer> {

}
