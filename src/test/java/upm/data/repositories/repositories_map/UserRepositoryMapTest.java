package upm.data.repositories.repositories_map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import upm.AppSeeder;
import upm.data.models.User;
import upm.data.repositories.UserRepository;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class UserRepositoryMapTest {
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        AppSeeder appSeeder = new AppSeeder();
        appSeeder.seed();
        this.userRepository = appSeeder.getUserRepository();
    }

    @Test
    public void TestCreateAndRead() {
        Optional<User> user = this.userRepository.read(3);
        assertTrue(user.isPresent());
        assertEquals("user3", user.get().getName());
    }

    @Test
    public void testUpdate() {
        assertTrue(this.userRepository.read(3).isPresent());
        User user = this.userRepository.read(3).get();
        user.setAge(54);
        this.userRepository.update(user);
        Optional<User> optionalUser = this.userRepository.read(3);
        assertTrue(optionalUser.isPresent());
        assertEquals(54, optionalUser.get().getAge());
    }

    @Test
    public void testDeleteById() {
        assertTrue(this.userRepository.read(3).isPresent());
        User user = this.userRepository.read(3).get();
        this.userRepository.deleteById(user.getId());
        assertFalse(this.userRepository.read(user.getId()).isPresent());
    }

    @Test
    public void testFindByMobile() {
        Optional<User> user = this.userRepository.findByMobile(154654286);
        assertTrue(user.isPresent());
        assertEquals("user2", user.get().getName());
    }

    @Test
    public void testFindByMobileNotFound() {
        assertFalse(this.userRepository.findByMobile(1).isPresent());
    }

    @Test
    public void testFindByName() {
        Optional<User> user = this.userRepository.findByName("user2");
        assertTrue(user.isPresent());
        assertEquals("user2", user.get().getName());
    }

    @Test
    public void testFindByNameNotFound() {
        assertFalse(this.userRepository.findByName("a").isPresent());
    }

    @Test
    public void testFindByBestAveragePlansRating() {
        assertTrue(this.userRepository.read(4).isPresent());
        assertEquals(this.userRepository.read(4).get(), this.userRepository.usersByRate(this.userRepository.findAll()));
    }

    @Test
    public void testFindByBestAveragePlansRatingNotFound() {
        assertNull(this.userRepository.usersByRate(this.userRepository.findAll()));
    }
}
