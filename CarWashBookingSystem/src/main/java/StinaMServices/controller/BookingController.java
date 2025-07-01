package StinaMServices.controller;

import StinaMServices.model.Booking;
import StinaMServices.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class BookingController {

    @Autowired
    private BookingRepository bookingRepo;

    // Show the form
    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("booking", new Booking());
        return "index";
    }

    //Handle form submission
    private static final Logger logger = LoggerFactory.getLogger(BookingController.class);
    @PostMapping("/book")
    public String saveBooking(@ModelAttribute("booking") @Valid Booking booking,
                              BindingResult result) {
        if (result.hasErrors()) {
            logger.warn("Form has errors: {}", result);
            return "index";
        }
        bookingRepo.save(booking);
        logger.info("New booking saved: {}", booking);
        return "success";
    }


    @GetMapping("/bookings")
    public String listBookings(Model model){
        model.addAttribute("bookings", bookingRepo.findAll());
        return "bookings";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // Show edit form
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Booking booking = bookingRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid booking ID: " + id));
        model.addAttribute("booking", booking);
        return "edit";
    }

    // Handle update
    @PostMapping("/update")
    public String updateBooking(@ModelAttribute("booking") @Valid Booking booking,
                                BindingResult result,
                                RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "edit";
        }
        bookingRepo.save(booking);
        redirectAttributes.addFlashAttribute("success", "Booking updated successfully!");
        return "redirect:/bookings";
    }

    // Handle delete
    @GetMapping("/delete/{id}")
    public String deleteBooking(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        bookingRepo.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "Booking deleted successfully!");
        return "redirect:/bookings";
    }

}

