package br.dev.jstec.tyginvestiment.models.specification;


import br.dev.jstec.tyginvestiment.models.AccountHistory;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class AccountHistorySpecifications {

    public static Specification<AccountHistory> hasDateBetween(LocalDate startDate, LocalDate endDate) {
        return (Root<AccountHistory> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.isTrue(criteriaBuilder.literal(true));

            if (startDate != null && endDate != null) {
                predicate = criteriaBuilder.between(root.get("createdDate"), startDate, endDate);
            } else if (startDate != null) {
                predicate = criteriaBuilder.greaterThanOrEqualTo(root.get("createdDate"), startDate);
            } else if (endDate != null) {
                predicate = criteriaBuilder.lessThanOrEqualTo(root.get("createdDate"), endDate);
            }

            return predicate;
        };
    }
}

