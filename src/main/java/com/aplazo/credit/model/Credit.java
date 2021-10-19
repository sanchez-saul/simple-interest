package com.aplazo.credit.model;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "credit")
public class Credit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer creditId;

	@Column(name = "terms", nullable = false)
	private Integer terms;

	@Column(name = "amount", nullable = false)
	private Double amount;

	@Column(name = "rate", nullable = false)
	private Double rate;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Credit other = (Credit) obj;
		return Objects.equals(creditId, other.creditId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(creditId);
	}
}
