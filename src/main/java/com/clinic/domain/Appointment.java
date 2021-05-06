package com.clinic.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public final class Appointment {

    @Id
    @NotNull
    @Column(name = "id")
    @GeneratedValue
    private Long id;

    @Column(name = "start")
    private LocalDateTime start;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "treatment_id")
    private Treatment treatment;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "pricing_strategy")
    private String pricingStrategy;

    public static class AppointmentBuilder {
        private Long id;
        private LocalDateTime start;
        private Treatment treatment;
        private Customer customer;
        private Employee employee;
        private BigDecimal price;
        private PricingStrategy pricingStrategy;

        public AppointmentBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public AppointmentBuilder start(LocalDateTime start) {
            this.start = start;
            return this;
        }
        public AppointmentBuilder treatment(Treatment treatment) {
            this.treatment = treatment;
            return this;
        }

        public AppointmentBuilder pricingStrategy(PricingStrategy pricingStrategy) {
            this.pricingStrategy = pricingStrategy;
            return this;
        }

        public AppointmentBuilder customer(Customer customer) {
            this.customer = customer;
            return this;
        }

        public AppointmentBuilder employee(Employee employee) {
            this.employee = employee;
            return this;
        }

        public Appointment build() {
            BigDecimal discount = Optional.ofNullable(pricingStrategy).isEmpty() ? BigDecimal.ONE : pricingStrategy.getDiscount();
            BigDecimal optionalPrice = Optional.ofNullable(treatment).isEmpty() ? BigDecimal.ZERO : treatment.getPrice();
            String pricingStrategyName = Optional.ofNullable(pricingStrategy).isEmpty() ? "None" : pricingStrategy.name();
            this.price = optionalPrice.multiply(discount);
            return new Appointment(start,treatment,customer,employee,price,pricingStrategyName);
        }
    }

    public Appointment(LocalDateTime start, Treatment treatment, Customer customer, Employee employee, BigDecimal price, String
                       pricingStrategy) {
        this.start = start;
        this.treatment = treatment;
        this.customer = customer;
        this.employee = employee;
        this.price = price;
        this.pricingStrategy = pricingStrategy;
    }
}
