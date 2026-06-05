package com.fundoo.fundoonotes.repository;

import com.fundoo.fundoonotes.model.Label;
import com.fundoo.fundoonotes.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LabelRepository extends JpaRepository<Label,Integer> {

    Label findByLabelNameAndUser(String labelName, User user);

    List<Label> findByUser(User user);


}
