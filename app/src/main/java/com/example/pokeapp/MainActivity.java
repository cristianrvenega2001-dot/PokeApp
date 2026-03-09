package com.example.pokeapp;

import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Clase principal que gestiona la lógica de negocio y la interfaz de usuario.
 * Implementa la carga masiva de datos y búsquedas específicas a través de la API.
 */
public class MainActivity extends AppCompatActivity {
    private PokemonAdapter pokemonAdapter;
    private PokemonApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicialización de componentes de la interfaz
        initView();
        
        // Configuración de la capa de red con Retrofit
        initRetrofit();

        // Ejecución de la carga inicial de datos
        loadInitialPokemon();
    }

    private void initView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        pokemonAdapter = new PokemonAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(pokemonAdapter);

        SearchView searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query != null && !query.trim().isEmpty()) {
                    searchPokemon(query.toLowerCase().trim());
                    searchView.clearFocus();
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void initRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(PokemonApiService.class);
    }

    private void loadInitialPokemon() {
        apiService.getPokemonList(20, 0).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<PokemonResponse> call, @NonNull Response<PokemonResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    for (PokemonResponse.PokemonResult result : response.body().getResults()) {
                        searchPokemon(result.getName());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<PokemonResponse> call, @NonNull Throwable t) {
                Log.e("Network", "Error en carga inicial", t);
            }
        });
    }

    private void searchPokemon(String name) {
        apiService.getPokemon(name).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<Pokemon> call, @NonNull Response<Pokemon> response) {
                if (response.isSuccessful() && response.body() != null) {
                    updateRecyclerView(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Pokemon> call, @NonNull Throwable t) {
                Log.e("Network", "Error al buscar Pokémon: " + name);
            }
        });
    }

    private synchronized void updateRecyclerView(Pokemon pokemon) {
        List<Pokemon> currentData = new ArrayList<>(pokemonAdapter.getPokemonList());
        
        // Evitamos duplicidad de elementos
        boolean isPresent = false;
        for (Pokemon p : currentData) {
            if (p.getName().equalsIgnoreCase(pokemon.getName())) {
                isPresent = true;
                break;
            }
        }

        if (!isPresent) {
            currentData.add(pokemon);
            pokemonAdapter.setPokemonList(currentData);
        } else if (currentData.size() == 1) {
            // Caso de búsqueda individual forzada
            List<Pokemon> singleEntry = new ArrayList<>();
            singleEntry.add(pokemon);
            pokemonAdapter.setPokemonList(singleEntry);
        }
    }
}