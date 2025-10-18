package org.vinn.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "order_history")
public class OrderHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;

    @Column(name = "ordered_date")
    private LocalDate orderedDate;

    public OrderHistory(){}

    public OrderHistory initialize(User user, Product product, int quantity, LocalDate orderedDate){
        this.user = user;
        this.product = product;
        this.quantity = quantity;
        this.orderedDate = orderedDate;
        return this;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }
    public Product getProduct() {
        return product;
    }
    public int getQuantity() {
        return quantity;
    }
    public LocalDate getOrderedDate() {
        return orderedDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public void setProduct(Product product) {
        this.product = product;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public void setOrderedDate(LocalDate orderedDate) {
        this.orderedDate = orderedDate;
    }
}
