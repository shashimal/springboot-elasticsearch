package com.duleendra.repository;

import com.duleendra.TremorvideoApplication;
import com.duleendra.model.User;
import com.duleendra.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.completion.Completion;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TremorvideoApplication.class)
@WebAppConfiguration
public class UserRepositoryCustomTest {

    @Autowired
    private UserService userService;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    @Qualifier(value = "userRepositoryCustom")
    private UserRepositoryCustom userRepositoryCustom;

    @Before
    public void before() {
        elasticsearchTemplate.deleteIndex(User.class);
        elasticsearchTemplate.createIndex(User.class);
        elasticsearchTemplate.putMapping(User.class);
        elasticsearchTemplate.refresh(User.class);
    }

    @Test
    public void testSearch() {
        User user = new User();
        user.setFirstName("Adam");
        user.setLastName("Mills");
        user.setBioData("Bio Data");
        user.setEmail("adam@gmail.com");
        user.setUserType("Normal");
        user.setSearchSuggest( new Completion(new String[] {user.getFirstName(),user.getLastName()}));
        userService.save(user);

        User user2 = new User();
        user2.setFirstName("Killer");
        user2.setLastName("Mills");
        user2.setBioData("Bio Data");
        user2.setEmail("killer@gmail.com");
        user2.setUserType("Normal");
        user2.setSearchSuggest( new Completion(new String[] {user2.getFirstName(),user2.getLastName()}));
        userService.save(user2);


        Page<User> userPages = userRepositoryCustom.search("Adam");
        System.out.println(userPages.getTotalPages());
        Assert.assertEquals("adam@gmail.com", userPages.getContent().get(0).getEmail());
        Assert.assertNotNull(userPages.getContent().get(0).getId());

        userPages = userRepositoryCustom.search("Killer");
        Assert.assertEquals("killer@gmail.com", userPages.getContent().get(0).getEmail());
        Assert.assertNotNull(userPages.getContent().get(0).getId());
    }

    @Test
    public void testUserNameSuggestions() {
        User user = new User();
        user.setId("NO009");
        user.setFirstName("Anne");
        user.setLastName("Wills");
        user.setBioData("QA Manager");
        user.setEmail("anne@gmail.com");
        user.setUserType("Normal");
        user.setSearchSuggest( new Completion(new String[] {user.getFirstName(),user.getLastName()}));
        User savedUser = userService.save(user);

        User user2 = new User();
        user2.setId("NO006");
        user2.setFirstName("Anno");
        user2.setLastName("Lara");
        user2.setBioData("IT Manager");
        user2.setEmail("brayan@gmail.com");
        user2.setUserType("Admin");
        user2.setAdminJobResponsibility("IT Manager");
        user2.setAdminAccessCode(1231);
        user2.setSearchSuggest( new Completion(new String[] {user2.getFirstName(),user2.getLastName()}));
        User savedUser2 = userService.save(user2);

        User user3 = new User();
        user3.setId("NO007");
        user3.setFirstName("Jack");
        user3.setLastName("Jill");
        user3.setBioData("IT Manager");
        user3.setEmail("brayan@gmail.com");
        user3.setUserType("Admin");
        user3.setAdminJobResponsibility("IT Manager");
        user3.setAdminAccessCode(1231);
        user3.setSearchSuggest( new Completion(new String[] {user3.getFirstName(),user3.getLastName()}));
        User savedUser3 = userService.save(user3);

        List<String> nameSuggestions  = userRepositoryCustom.getUserNameSuggestions("Ann");
        Assert.assertEquals(2,nameSuggestions.size());
    }
}

