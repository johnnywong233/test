package com.johnny.service.handler.finder;

import java.util.List;

public interface IAlbumURLFinder {
    String getURLRegex();

    List<String> findAlbumURL(String paramString);
}
