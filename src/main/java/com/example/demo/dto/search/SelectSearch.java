package com.example.demo.dto.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SelectSearch {
    private String type;
    private Long id;
    private String Name;
    private String avatarUrl;
    private String content;
    private Long groupId;
    private String groupName;
}
