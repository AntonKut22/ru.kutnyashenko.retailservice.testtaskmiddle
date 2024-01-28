package ru.kutnyashenko.retailservice.testtaskmiddle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kutnyashenko.retailservice.testtaskmiddle.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
}
