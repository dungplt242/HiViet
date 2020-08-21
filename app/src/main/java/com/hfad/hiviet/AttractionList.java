package com.hfad.hiviet;

import org.w3c.dom.Attr;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;

public class AttractionList {

    private static AttractionList attractionList = null;
    private List<Attraction> list = new ArrayList<>();

    private AttractionList(Scanner scanner) {
        while (scanner.hasNextLine()) {
            list.add(new Attraction(scanner));
        }
    }

    public static void loadData(Scanner scanner) {
        if (attractionList == null) attractionList = new AttractionList(scanner);
    }

    //WARNING: Data must be loaded before calling builder
    public static AttractionList builder() {
        return attractionList;
    }

    public static void loadUnlocked(Scanner scanner) {
        int unlockedIndex;
        while (scanner.hasNextLine()) {
            unlockedIndex = Integer.parseInt(scanner.nextLine());
            attractionList.list.get(unlockedIndex).unlock();
        }
    }

    public static List<Attraction> getRandomAttractions(int number) {
        Random rand = new Random();
        HashSet hashSet = new HashSet();
        List<Attraction> result = new ArrayList<>();
        int randomIndex, count = 0;

        while (count <= number) {
            randomIndex = rand.nextInt(attractionList.list.size());
            if (!hashSet.contains(randomIndex)) {
                hashSet.add(randomIndex);
                result.add(attractionList.list.get(randomIndex));
                count++;
            }
        }

        return result;
    }

    public static void updateFavoriteList(PrintStream favoriteFile) {
        for (int i=0; i<attractionList.list.size(); i++) {
            if (attractionList.list.get(i).isFavorite())
                favoriteFile.println(i);
        }
    }

    public static void loadFavorite(Scanner scanner) {
        int favoriteIndex;
        while (scanner.hasNextLine()) {
            favoriteIndex = Integer.parseInt(scanner.nextLine());
            attractionList.list.get(favoriteIndex).addToFavorite();
        }
    }

    public List<Attraction> getList() {
        return list;
    }

    public Attraction getItem(int index){
        return list.get(index);
    }
}
