package com.revature.PureDataBase2.services;

import java.util.Collections;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.revature.PureDataBase2.repositories.LikeRepository;
import com.revature.PureDataBase2.repositories.ObjectTagRepository;
import com.revature.PureDataBase2.repositories.LibraryTagRepository;
import com.revature.PureDataBase2.entities.Like;
import com.revature.PureDataBase2.entities.Tag;
import com.revature.PureDataBase2.entities.PdObject;
import com.revature.PureDataBase2.entities.ObjectTag;

import lombok.AllArgsConstructor;

/**
 * The UserService class provides operations related to user management.
 */
@Service
@AllArgsConstructor
public class RecommendationService {
    private final PdLibraryService libraryService;
    private final LikeService likeService;

    private class TagElem {
        String tagName;
        int idx = 0;
    }

    private class TagBase {
        ArrayList<TagElem> tags;
        int idx = 0;
        int lastIdx = -1;
        boolean stopped = false;
    }

    public Set<PdObject> getByObject(String userId) {
        List<Like> likes = likeService.getObjectLikesForUser(userId);
        Set<PdObject> ret = new HashSet<PdObject>();
        if(likes.isEmpty()) return ret;
        // tags we've already gone over
        // <tagName, PdObject list>
        Map<String, List<PdObject>> tagMap = new HashMap<String, List<PdObject>>();
        Set<PdObject> objSeen = new HashSet<PdObject>();
        List<TagBase> likeList = new ArrayList<TagBase>();
        // build structure
        for(Like like : likes) {
            TagBase tagBase = new TagBase();
            PdObject likeObject = libraryService.getObjectByObjectId(like.getEntityId());
            objSeen.add(likeObject);
            tagBase.tags = new ArrayList<TagElem>();
            Set<ObjectTag> objectTags = likeObject.getObjectTags();
            if(objectTags.isEmpty()) continue;
            for(ObjectTag objectTag : objectTags) {
                Tag tag = objectTag.getTag();
                String tagName = tag.getName();
                TagElem tagElem = new TagElem();
                tagElem.tagName = tagName;
                if(tagMap.containsKey(tagName)) {
                    if(!tagMap.get(tagName).isEmpty())
                    tagBase.tags.add(tagElem);
                    continue;
                }
                Set<ObjectTag> tagObjectTags = tag.getObjectTags();
                List<PdObject> objList = new ArrayList<PdObject>();
                for(ObjectTag tagObjectTag : tagObjectTags) {
                    PdObject addObject = tagObjectTag.getObject();
                    if(!objSeen.contains(addObject))
                        objList.add(addObject);
                }
                // recommendations should be a bit different every time
                tagMap.put(tagName, objList);
                if(objList.isEmpty()) continue;
                Collections.shuffle(objList);
                tagBase.tags.add(tagElem);
            }
            Collections.shuffle(tagBase.tags);
            likeList.add(tagBase);
        }
        int likeIdx = 0;
        int exhaustIdx = -1;
        // loop through likes
        while(ret.size() < 5 && (exhaustIdx != likeIdx)) {
            TagBase currentBase = likeList.get(likeIdx);
            if(!currentBase.stopped) {
                // loop through tags
                tagLevel: while(true) {
                    TagElem currentElem = currentBase.tags.get(currentBase.idx);
                    List<PdObject> objects = tagMap.get(currentElem.tagName);
                    // loop through related objects
                    for(; currentElem.idx < objects.size(); currentElem.idx++) {
                        PdObject obj = objects.get(currentElem.idx);
                        if(!objSeen.contains(obj)) {
                            ret.add(obj);
                            objSeen.add(obj);
                            currentBase.lastIdx = currentBase.idx;
                            exhaustIdx = likeIdx;
                            currentElem.idx++;
                            currentBase.idx++;
                            currentBase.idx %= currentBase.tags.size();
                            break tagLevel;
                        }
                    }
                    // last time we were the only one, so now we're stopped
                    if(currentBase.idx == currentBase.lastIdx) {
                        currentBase.stopped = true;
                        break tagLevel;
                    }
                    currentBase.idx++;
                    currentBase.idx %= currentBase.tags.size();
                }
            }
            likeIdx = (likeIdx + 1) % likeList.size();
        }
        return ret;
    }
}
