package com.revature.PureDataBase2.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.revature.PureDataBase2.repositories.ObjectCommentRepository;
import com.revature.PureDataBase2.entities.ObjectComment;
import com.revature.PureDataBase2.entities.PdObject;
import com.revature.PureDataBase2.entities.User;
import com.revature.PureDataBase2.util.custom_exceptions.CommentNotFoundException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CommentService {
    private final ObjectCommentRepository objectCommentRepo;

    public String create(String comment, PdObject pdObject, User user) {
        return objectCommentRepo.save(new ObjectComment(comment, pdObject, user)).getId();
    }

    public ObjectComment getById(String id) {
        Optional<ObjectComment> optComment = objectCommentRepo.findById(id);
        if (optComment.isEmpty()) throw new CommentNotFoundException("comment " + id + " not found");
        return optComment.get();
    }

    public ObjectComment update(ObjectComment objectComment) {
        return objectCommentRepo.save(objectComment);
    }

    public void delete(String id) {
         objectCommentRepo.deleteById(id);
    }
}
