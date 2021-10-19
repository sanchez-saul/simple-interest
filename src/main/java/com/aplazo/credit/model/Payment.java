package com.aplazo.credit.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "payment")
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer paymentId;

	@ManyToOne
	@JoinColumn(name = "credit_id", nullable = false, foreignKey = @ForeignKey(name = "FK_payment_credit"))
	private Credit credit;

	@Column(name = "payment_number", nullable = false)
	private Integer paymentNumber;

	@Column(name = "amount", nullable = false)
	private Double amount;

	@Column(name = "payment_date", nullable = false)
	private LocalDate paymentDate; // ISODate yyyy-mm-dd

	@Override
	public int hashCode() {
		return Objects.hash(paymentId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Payment other = (Payment) obj;
		return Objects.equals(paymentId, other.paymentId);
	}

}
