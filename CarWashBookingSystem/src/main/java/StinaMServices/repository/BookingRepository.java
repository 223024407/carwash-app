package StinaMServices.repository;

import StinaMServices.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BookingRepository extends JpaRepository<Booking,Long> {
}
