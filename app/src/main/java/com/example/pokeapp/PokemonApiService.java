package com.example.pokeapp;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PokemonApiService {
    @GET("pokemon/{name}")
    Call<Pokemon> getPokemon(@Path("name") String name);

    @GET("pokemon")
    Call<PokemonResponse> getPokemonList(@Query("limit") int limit, @Query("offset") int offset);
}
