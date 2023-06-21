package com.revature.PureDataBase2.DTO.responses;
import java.util.List;
import lombok.Setter;
import lombok.Getter;

@Setter
@Getter
public class LibrarySummary {
    String name;
    String description;
    String recentVersion;
    String author;
    List<String> tags;
}
