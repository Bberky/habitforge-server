package cz.cvut.fit.tjv.habitforgeserver.api;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import cz.cvut.fit.tjv.habitforgeserver.model.User;
import cz.cvut.fit.tjv.habitforgeserver.service.UserService;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void createUser() throws Exception {
        var user = new User();

        user.setFullName("test");
        user.setEmail("test@test.test");
        user.setPassword("test");

        Mockito.when(userService.create(user)).thenReturn(user);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                    {
                                        "fullName": "test",
                                        "email": "test@test.test",
                                        "password": "test"
                                    }
                                """))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.fullName").value(user.getFullName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(user.getEmail()));
    }

    @Test
    void createUserWithNoEmail() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                    {
                                        "fullName": "test",
                                        "password": "test"
                                    }
                                """))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
