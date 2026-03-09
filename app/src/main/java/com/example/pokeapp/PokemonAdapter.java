package com.example.pokeapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Adaptador profesional para la lista de Pokémon.
 * Gestiona la visualización de detalles como altura, peso y habilidades especiales.
 */
public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder> {
    private List<Pokemon> pokemonList = new ArrayList<>();

    public void setPokemonList(List<Pokemon> pokemonList) {
        this.pokemonList = pokemonList;
        notifyDataSetChanged();
    }

    public List<Pokemon> getPokemonList() {
        return pokemonList;
    }

    @NonNull
    @Override
    public PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pokemon, parent, false);
        return new PokemonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonViewHolder holder, int position) {
        Pokemon pokemon = pokemonList.get(position);
        
        // Asignación de datos con formato profesional
        holder.tvName.setText(pokemon.getName().toUpperCase());
        holder.tvAbility.setText(pokemon.getMainAbility());
        holder.tvHeight.setText("Altura: " + pokemon.getHeightFormatted());
        holder.tvWeight.setText("Peso: " + pokemon.getWeightFormatted());

        // Carga optimizada de imagen
        Picasso.get()
                .load(pokemon.getImageUrl())
                .placeholder(android.R.drawable.progress_indeterminate_horizontal)
                .error(android.R.drawable.stat_notify_error)
                .into(holder.ivPokemon);
    }

    @Override
    public int getItemCount() {
        return pokemonList.size();
    }

    static class PokemonViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvAbility, tvHeight, tvWeight;
        ImageView ivPokemon;

        public PokemonViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvAbility = itemView.findViewById(R.id.tv_ability);
            tvHeight = itemView.findViewById(R.id.tv_height);
            tvWeight = itemView.findViewById(R.id.tv_weight);
            ivPokemon = itemView.findViewById(R.id.iv_pokemon);
        }
    }
}