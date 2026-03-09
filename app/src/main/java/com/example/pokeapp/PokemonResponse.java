package com.example.pokeapp;

import java.util.List;

public class PokemonResponse {
    private List<PokemonResult> results;

    public List<PokemonResult> getResults() {
        return results;
    }

    public static class PokemonResult {
        private String name;
        private String url;

        public String getName() {
            return name;
        }

        public String getUrl() {
            return url;
        }
    }
}