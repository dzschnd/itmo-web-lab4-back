package web.rest.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter @Setter(value = AccessLevel.PACKAGE)
@Builder @NoArgsConstructor @AllArgsConstructor(access = AccessLevel.PACKAGE)
public class Shot {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @NotNull
    private Double x;
    @Column(nullable = false)
    @NotNull
    private Double y;
    @Column(nullable = false)
    @NotNull
    private Double r;
    @Column(nullable = false)
    private Boolean inArea;
    @Column(nullable = false)
    private String shotTime;
    @JoinColumn(nullable = false)
    @ManyToOne(optional = false, cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private User user;

    @PrePersist
    protected void prePersist() {
        this.shotTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        this.inArea = checkHit();
    }

    private boolean checkHit() {
        if (x <= 0 && y <= 0) {
            return checkHitRectangle();
        }
        if (x >= 0 && y >= 0) {
            return checkHitTriangle();
        }
        if (x >= 0 && y <= 0) {
            return checkHitArc();
        }
        return false;
    }
    private boolean checkHitRectangle() {
        return x >= -r && y >= -r;
    }
    private boolean checkHitTriangle() {
        return x <= r/2 && y <= -2*x + r;
    }
    private boolean checkHitArc() {
        return x*x + y*y <= r*r;
    }
}
