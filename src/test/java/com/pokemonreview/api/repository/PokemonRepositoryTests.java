package com.pokemonreview.api.repository;

import com.pokemonreview.api.models.Pokemon;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class PokemonRepositoryTests {
    @Autowired
    private PokemonRepository pokemonRepository;

    @Test
    public void PokemonRepository_SaveAll_ReturnSavedPokemon() {
        // Arrange
        Pokemon pokemon = Pokemon.builder().name("picachu").type("electric").build();

        // Act
        Pokemon savedPokemon = pokemonRepository.save(pokemon);

        // Assert
        Assertions.assertThat(savedPokemon).isNotNull();
        Assertions.assertThat(savedPokemon.getId()).isGreaterThan(0);
    }

    @Test
    public void PokemonRepository_GetAll_ReturnMoreThanOnePokemon() {
        // Arrange
        Pokemon pokemon = Pokemon.builder().name("picachu").type("electric").build();
        Pokemon pokemon2 = Pokemon.builder().name("picachu").type("electric").build();
        pokemonRepository.save(pokemon);
        pokemonRepository.save(pokemon2);

        // Act
        List<Pokemon> pokemonList = pokemonRepository.findAll();

        // Assert
        Assertions.assertThat(pokemonList).isNotNull();
        Assertions.assertThat(pokemonList.size()).isEqualTo(2);
    }

    @Test
    public void PokemonRepository_FindById_ReturnsOnePokemon() {
        // Arrange
        Pokemon pokemon = Pokemon.builder().name("picachu").type("electric").build();
        pokemonRepository.save(pokemon);

        // Act
        Pokemon pokemonGot = pokemonRepository.findById(pokemon.getId()).get();

        // Assert
        Assertions.assertThat(pokemonGot).isNotNull();
    }

    @Test
    public void PokemonRepository_FindByType_ReturnsPokemonNotNull() {
        // Arrange
        Pokemon pokemon = Pokemon.builder().name("picachu").type("electric").build();
        pokemonRepository.save(pokemon);

        // Act
        Pokemon pokemonGot = pokemonRepository.findByType(pokemon.getType()).get();

        // Assert
        Assertions.assertThat(pokemonGot).isNotNull();
    }

    @Test
    public void PokemonRepository_UpdatePokemon_ReturnsPokemonNotNull() {
        // Arrange
        Pokemon pokemon = Pokemon.builder().name("picachu").type("electric").build();
        pokemonRepository.save(pokemon);
        Pokemon pokemonSaved = pokemonRepository.findById(pokemon.getId()).get();
        pokemonSaved.setType("Electric");
        pokemonSaved.setName("Raichu");

        // Act
        Pokemon updatedPokemon = pokemonRepository.save(pokemonSaved);

        // Assert
        Assertions.assertThat(updatedPokemon).isNotNull();
        Assertions.assertThat(updatedPokemon.getType()).isEqualTo("Electric");
        Assertions.assertThat(updatedPokemon.getName()).isEqualTo("Raichu");
    }

    @Test
    public void PokemonRepository_Delete_ReturnedPokemonIsEmpty() {
        // Arrange
        Pokemon pokemon = Pokemon.builder().name("picachu").type("electric").build();
        pokemonRepository.save(pokemon);

        // Act
        pokemonRepository.deleteById(pokemon.getId());
        Optional<Pokemon> pokemonGot = pokemonRepository.findByType(pokemon.getType());

        // Assert
        Assertions.assertThat(pokemonGot).isEmpty();
    }
}
