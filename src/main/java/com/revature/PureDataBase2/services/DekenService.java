package com.revature.PureDataBase2.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.revature.PureDataBase2.entities.PdLibrary;
import com.revature.PureDataBase2.entities.PdObject;
import com.revature.PureDataBase2.entities.User;

// consider saveAll method in pdLibraryService for saving these objects
@Service
public class DekenService {
    private final WebClient webClient;
    private final PdLibraryService pdLibraryService;
    private final String baseUrl = "https://deken.puredata.info";
    private final String ubaseUrl = "http://puredata.info";

    DekenService (PdLibraryService pdLibraryService) {
        webClient = WebClient.builder().baseUrl(baseUrl)
        .defaultHeader(HttpHeaders.USER_AGENT, MediaType.APPLICATION_JSON_VALUE).build();
        this.pdLibraryService = pdLibraryService;
    }

    public String getUrlForLibrary(String libName) throws Exception {
        RequestHeadersSpec<?> headersSpec = webClient.get().uri("/library/" + libName + "/*/Darwin-amd64-32");
        JsonNode node = headersSpec.retrieve().bodyToMono(ObjectNode.class).block();
        node = node.get("result").get("libraries").get(libName);
        if(node == null) throw new Exception("no matching library");
        Iterator<String> fields = node.fieldNames();
        String highestVersion = "";
        String currentVersion;
        if(fields.hasNext()) highestVersion = fields.next();
        while(fields.hasNext()) {
            currentVersion = fields.next();
            if(currentVersion.compareToIgnoreCase(highestVersion) > 0) {
                highestVersion = currentVersion;
            }
        }
        node = node.get(highestVersion).get(0).get("url");
        return node.textValue().replace(ubaseUrl, "");
    }
    
    // update library from deken
    public List<PdObject> updateLib(String url, User user) throws Exception {
        List<PdObject> ret = new ArrayList<>();
        RequestHeadersSpec<?> headersSpec = webClient.get().uri(baseUrl + "/info.json?url=" + ubaseUrl + url);
        CompletableFuture<ObjectNode> future = headersSpec.retrieve().bodyToMono(ObjectNode.class).toFuture();
        JsonNode node;
        try {
            node = future.get();
        } catch(Exception e) {
            throw new Exception("couldn't get library for url");
        }
        node = node.get("result").get("libraries");
        Iterator<Entry<String, JsonNode>> fields = node.fields();
        if(!fields.hasNext()) throw new Exception("deken: no such library");
        //get library
        Entry<String, JsonNode> field = fields.next();
        String libName = field.getKey();
        fields = field.getValue().fields();
        if(!fields.hasNext()) throw new Exception("deken: no such version for library " + libName);
        // get array of object descriptions
        field = fields.next();
        String version = field.getKey();
        node = field.getValue().get(0).get("objects");
        if(node == null) throw new Exception("deken: no objects for given version of library " + libName);
        Iterator<JsonNode> elements = node.elements();
        PdLibrary lib = pdLibraryService.getByName(libName);
        lib.setRecentVersion(version);
        pdLibraryService.save(lib);
        Set<PdObject> objects = lib.getObjects();
        Map<String, String> descriptions = new HashMap<String, String>();
        while(elements.hasNext()) {
            node = elements.next();
            descriptions.put(node.get("name").textValue(), node.get("description").textValue());
        }
        for(PdObject obj : objects) {
            String name = obj.getName();
            String oldDesc = obj.getDescription();
            String newDesc = descriptions.get(name);
            // only change if different and not a default
            if(newDesc != null) {
                if(!newDesc.equals(oldDesc) && (oldDesc.equals("") || oldDesc.equals("DEKEN GENERATED"))) {
                    obj.setDescription(newDesc);
                    pdLibraryService.saveObject(obj, user);
                    ret.add(obj);
                }
                descriptions.remove(name);
            }
        }
        for(Map.Entry<String, String> newObj : descriptions.entrySet()) {
            PdObject obj = new PdObject(newObj.getKey(), lib, user);
            obj.setDescription(newObj.getValue());
            pdLibraryService.saveObject(obj, user);
            ret.add(obj);
        }

        return ret;
    }
}
