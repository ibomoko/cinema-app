package com.me.cinemaapp.specification;

import com.me.cinemaapp.entity.Movie;
import com.me.cinemaapp.entity.metamodel.Movie_;
import com.me.cinemaapp.model.request.MovieFilter;
import com.me.cinemaapp.util.SpecificationHelper;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class MovieSpecification implements Specification<Movie> {

    private final MovieFilter filter;

    @Override
    public Predicate toPredicate(Root<Movie> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (filter == null) {
            return cb.and(predicates.toArray(Predicate[]::new));
        }

        if (filter.title() != null) {
            predicates.add(cb.like(cb.lower(root.get(Movie_.TITLE)), SpecificationHelper.prepareSearchText(filter.title())));
        }

        if (filter.description() != null) {
            predicates.add(cb.like(cb.lower(root.get(Movie_.DESCRIPTION)), SpecificationHelper.prepareSearchText(filter.description())));
        }

        if (filter.genre() != null) {
            predicates.add(cb.like(cb.lower(root.get(Movie_.GENRE)), SpecificationHelper.prepareSearchText(filter.genre())));
        }

        if (filter.durationFrom() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get(Movie_.DURATION), filter.durationFrom()));
        }

        if (filter.durationTo() != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get(Movie_.DURATION), filter.durationTo()));
        }

        if (filter.releaseYearFrom() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get(Movie_.RELEASE_YEAR), filter.releaseYearFrom()));
        }

        if (filter.releaseYearTo() != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get(Movie_.RELEASE_YEAR), filter.releaseYearTo()));
        }

        return cb.and(predicates.toArray(Predicate[]::new));
    }
}
