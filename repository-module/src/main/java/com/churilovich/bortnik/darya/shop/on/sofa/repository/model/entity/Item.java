package com.churilovich.bortnik.darya.shop.on.sofa.repository.model.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "item")
@SqlResultSetMapping(
        name = "reportMapping",
        classes = {
                @ConstructorResult(
                        targetClass = com.churilovich.bortnik.darya.shop.on.sofa.repository.model.element.Report.class,
                        columns = {
                                @ColumnResult(name = "name", type = String.class),
                                @ColumnResult(name = "category", type = String.class),
                                @ColumnResult(name = "price", type = BigDecimal.class),
                                @ColumnResult(name = "amount", type = Long.class)
                        }
                )
        }
)
@NamedNativeQuery(
        name = "getReport",
        query = "SELECT i.name, i.price, count(*) AS amount, ic.name AS category" +
                " FROM item AS i JOIN item_category AS ic ON ic.id = i.item_category_id where i.user_id=:id" +
                " GROUP BY i.name, i.price , ic.name ORDER BY i.name, i.price",
        resultSetMapping = "reportMapping")

@Getter
@Setter
@EqualsAndHashCode(exclude = "user")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "unique_number")
    private String uniqueNumber;

    @Column
    private String name;

    @Column
    private BigDecimal price;

    @Column
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_category_id", nullable = false)
    private ItemCategory category;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
