package com.revature.PureDataBase2.services;

import java.util.Set;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.revature.PureDataBase2.repositories.LikeRepository;
import com.revature.PureDataBase2.repositories.ObjectTagRepository;
import com.revature.PureDataBase2.repositories.LibraryTagRepository;
import com.revature.PureDataBase2.entities.Like;

import lombok.AllArgsConstructor;

/**
 * The UserService class provides operations related to user management.
 */
@Service
@AllArgsConstructor
public class LikeService {
    private final LikeRepository likeRepo;
    private final ObjectTagRepository objectTagRepo;
    private final LibraryTagRepository libraryTagRepo;

    public List<Like> getAllForUser(String userId) {
        return likeRepo.findByUserIdOrderByDateDesc(userId);
    }

    public List<Like> getObjectLikesForUser(String userId) {
        return likeRepo.findByEntityTypeAndUserIdOrderByDateDesc(Like.EntityType.OBJECT, userId);
    }

    public List<Like> getLibraryLikesForUser(String userId) {
        return likeRepo.findByEntityTypeAndUserIdOrderByDateDesc(Like.EntityType.LIBRARY, userId);
    }

    public List<Like> getAuthorLikesForUser(String userId) {
        return likeRepo.findByEntityTypeAndUserIdOrderByDateDesc(Like.EntityType.AUTHOR, userId);
    }

    public List<Like> getByObjectId(String objectId) {
        return likeRepo.findByEntityTypeAndEntityIdOrderByDateDesc(Like.EntityType.OBJECT, objectId);
    }

    public List<Like> getByLibraryId(String libraryId) {
        return likeRepo.findByEntityTypeAndEntityIdOrderByDateDesc(Like.EntityType.LIBRARY, libraryId);
    }

    public List<Like> getByAuthorName(String authorName) {
        return likeRepo.findByEntityTypeAndEntityIdOrderByDateDesc(Like.EntityType.AUTHOR, authorName);
    }

    public boolean hasUserLikedEntity(String entityId, String userId) {
        Optional<Like> optLike = likeRepo.findByEntityIdAndUserId(entityId, userId);
        return(!optLike.isEmpty());
    }

    public Like create(Like like) {
        // save and return library
        return likeRepo.save(like);
    }

}
