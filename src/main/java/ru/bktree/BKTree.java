package ru.bktree;

import java.util.HashMap;

public interface BKTree {

    HashMap<String,Integer> query(String searchObject, int threshold);
    HashMap<String,Integer> findBestWordMatchWithDistance(String term);

}
