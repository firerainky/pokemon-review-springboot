package com.pokemonreview.api.repository;

import com.pokemonreview.api.models.Review;
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
public class ReviewRepositoryTests {
    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    public void ReviewRepository_SaveAll_ReturnSavedReview() {
        // Arrange
        Review review = Review.builder().title("title").content("content").stars(5).build();

        // Act
        Review savedReview = reviewRepository.save(review);

        // Assert
        Assertions.assertThat(savedReview).isNotNull();
        Assertions.assertThat(savedReview.getId()).isGreaterThan(0);
    }

    @Test
    public void ReviewRepository_GetAll_ReturnsMoreThanOneReview() {
        // Arrange
        Review review = Review.builder().title("tt").content("cc").stars(5).build();
        Review review2 = Review.builder().title("ttt").content("ccc").stars(5).build();
        reviewRepository.save(review);
        reviewRepository.save(review2);

        // Act
        List<Review> reviewList = reviewRepository.findAll();

        // Assert
        Assertions.assertThat(reviewList).isNotNull();
        Assertions.assertThat(reviewList.size()).isEqualTo(2);
    }

    @Test
    public void ReviewRepository_FindById_ReturnsOneReview() {
        // Arrange
        Review review = Review.builder().title("tt").content("cc").stars(5).build();
        reviewRepository.save(review);

        // Act
        Review reviewGot = reviewRepository.findById(review.getId()).get();

        // Assert
        Assertions.assertThat(reviewGot).isNotNull();
    }

    @Test
    public void ReviewRepository_UpdateReview_ReturnsReviewNotNull() {
        // Arrange
        Review review = Review.builder().title("picachu").content("electric").stars(5).build();
        reviewRepository.save(review);
        Review reviewSaved = reviewRepository.findById(review.getId()).get();
        reviewSaved.setTitle("Electric");
        reviewSaved.setContent("Raichu");

        // Act
        Review updatedReview = reviewRepository.save(reviewSaved);

        // Assert
        Assertions.assertThat(updatedReview).isNotNull();
        Assertions.assertThat(updatedReview.getTitle()).isEqualTo("Electric");
        Assertions.assertThat(updatedReview.getContent()).isEqualTo("Raichu");
    }

    @Test
    public void ReviewRepository_Delete_ReturnedReviewIsEmpty() {
        // Arrange
        Review review = Review.builder().title("picachu").content("electric").build();
        reviewRepository.save(review);

        // Act
        reviewRepository.deleteById(review.getId());
        Optional<Review> reviewGot = reviewRepository.findById(review.getId());

        // Assert
        Assertions.assertThat(reviewGot).isEmpty();
    }
}
