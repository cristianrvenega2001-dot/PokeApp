package com.example.pokeapp;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Modelo de datos detallado para un Pokémon.
 * Incluye conversiones de unidades y gestión de habilidades.
 */
public class Pokemon {
    private String name;
    private int height; // en decímetros
    private int weight; // en hectogramos
    private Sprites sprites;
    private List<AbilityWrapper> abilities;

    public String getName() {
        return name;
    }

    /**
     * Convierte la altura de decímetros a metros.
     */
    public String getHeightFormatted() {
        double meters = height / 10.0;
        return String.format("%.1f m", meters);
    }

    /**
     * Convierte el peso de hectogramos a kilogramos.
     */
    public String getWeightFormatted() {
        double kg = weight / 10.0;
        return String.format("%.1f kg", kg);
    }

    public String getImageUrl() {
        return sprites != null ? sprites.getFrontDefault() : null;
    }

    /**
     * Obtiene la habilidad principal del Pokémon.
     */
    public String getMainAbility() {
        if (abilities != null && !abilities.isEmpty()) {
            return abilities.get(0).ability.name.toUpperCase();
        }
        return "N/A";
    }

    public static class Sprites {
        @SerializedName("front_default")
        private String frontDefault;

        public String getFrontDefault() {
            return frontDefault;
        }
    }

    public static class AbilityWrapper {
        public Ability ability;
    }

    public static class Ability {
        public String name;
    }
}