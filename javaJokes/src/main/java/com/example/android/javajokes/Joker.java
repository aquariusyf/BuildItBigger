package com.example.android.javajokes;

import java.util.Random;

public class Joker {

    private static final String[] jokeList = new String[] {
            "What do you call an alligator in a vest? An Investigator.",
            "What do you call a pile of kittens? A meowntain.",
            "What happens if you eat yeast and shoe polish? Every morning you’ll rise and shine!",
            "What do you get from a pampered cow? Spoiled milk.",
            "What gets wetter the more it dries? A towel."
    };

    public String getJoke() {
        String joke = jokeList[new Random().nextInt(jokeList.length)];
        return joke;
    }
}
