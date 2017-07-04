package com.duleendra.controller;

import com.duleendra.TremorvideoApplication;
import com.duleendra.model.User;
import com.duleendra.repository.UserRepository;
import com.duleendra.service.UserService;
import com.duleendra.service.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TremorvideoApplication.class)
@WebAppConfiguration
public class UserControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private UserController userController;

    @InjectMocks
    private UserService userServiceMock = new UserServiceImpl();

    @Mock
    @Autowired
    private UserRepository userRepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateUser() throws Exception {
        User user = new User();
        user.setFirstName("Adam");
        user.setLastName("Mills");
        user.setBioData("Bio Data");
        user.setEmail("adam@gmail.com");
        user.setUserType("Normal");

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(this.userController).build();
        userServiceMock.save(user);
        mockMvc.perform(MockMvcRequestBuilders.post("/users/create").param("firstName", "Adam")
                .param("lastName",  "Silva")
                .param("email", "adam@gmail.com")
                .param("userType", "Admin")
                .param("email", "adam@gmail.com")
                .param("dateOfBirth", "1986-12-14")
                .param("bioData", "Bio Data"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(redirectedUrl("/redirect:/users/"));
    }
}
