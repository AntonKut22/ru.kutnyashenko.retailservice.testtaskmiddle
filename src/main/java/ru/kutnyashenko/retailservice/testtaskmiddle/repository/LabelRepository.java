package ru.kutnyashenko.retailservice.testtaskmiddle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kutnyashenko.retailservice.testtaskmiddle.entity.Label;

import java.util.List;
import java.util.Set;

public interface LabelRepository extends JpaRepository<Label, Integer> {

    public List<Label> findByLabelIn(Set<String> labels);
}
