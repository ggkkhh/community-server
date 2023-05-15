package com.roydon.web.controller.tool;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Image
 *
 * @AUTHOR: roydon
 * @DATE: 2023/4/25
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Image {
    private String name;
    private String url;

    @Override
    public String toString() {
        return "Image{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
