package ru.kutnyashenko.retailservice.testtaskmiddle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kutnyashenko.retailservice.testtaskmiddle.entity.BookingPosition;

public interface BookingPositionRepository extends JpaRepository<BookingPosition, Integer> {
}
