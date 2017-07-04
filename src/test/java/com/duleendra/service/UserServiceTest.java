package com.duleendra.service;

import com.duleendra.TremorvideoApplication;
import com.duleendra.exception.AppServiceException;
import com.duleendra.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.completion.Completion;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TremorvideoApplication.class)
@WebAppConfiguration
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Before
    public void before() {
        elasticsearchTemplate.deleteIndex(User.class);
        elasticsearchTemplate.createIndex(User.class);
        elasticsearchTemplate.putMapping(User.class);
        elasticsearchTemplate.refresh(User.class);
    }

    @Test
    public void testSaveNormalUser() {
        User user = new User();
        user.setId("NO001");
        user.setFirstName("Adam");
        user.setLastName("Mills");
        user.setBioData("Software Engineer");
        user.setEmail("adam@gmail.com");
        user.setUserType("Normal");
        user.setSearchSuggest( new Completion(new String[] {user.getFirstName(),user.getLastName()}));
        User savedUser = userService.save(user);

        Assert.assertNotNull(savedUser);
        Assert.assertNotNull(savedUser.getId());
        Assert.assertEquals("Normal",savedUser.getUserType());
    }

    @Test
    public void testSaveAdminUser() {
        User adminUser = new User();
        adminUser.setId("NO002");
        adminUser.setFirstName("Nick");
        adminUser.setLastName("Robert");
        adminUser.setBioData("Project Manager");
        adminUser.setEmail("nick@gmail.com");
        adminUser.setUserType("Admin");
        adminUser.setAdminAccessCode(1000);
        adminUser.setSearchSuggest( new Completion(new String[] {adminUser.getFirstName(),adminUser.getLastName()}));
        adminUser.setAdminJobResponsibility("Project Manager");

        User savedUser = userService.save(adminUser);

        Assert.assertNotNull(savedUser);
        Assert.assertNotNull(savedUser.getId());
        Assert.assertEquals("Admin",savedUser.getUserType());
    }

    @Test
    public void testFindUserById() {
        User user = new User();
        user.setId("NO003");
        user.setFirstName("Anne");
        user.setLastName("Wills");
        user.setBioData("QA Manager");
        user.setEmail("anne@gmail.com");
        user.setUserType("Normal");
        user.setSearchSuggest( new Completion(new String[] {user.getFirstName(),user.getLastName()}));
        User savedUser = userService.save(user);
        User searchedUser = userService.findUserById("NO003");

        Assert.assertNotNull(searchedUser.getId());
        Assert.assertEquals(savedUser.getId(),searchedUser.getId());
        Assert.assertEquals(savedUser.getFirstName(),searchedUser.getFirstName());
    }

    @Test
    public void testFindUsers() throws AppServiceException {
        User user = new User();
        user.setId("NO004");
        user.setFirstName("Anne");
        user.setLastName("Wills");
        user.setBioData("QA Manager");
        user.setEmail("anne@gmail.com");
        user.setUserType("Normal");
        user.setSearchSuggest( new Completion(new String[] {user.getFirstName(),user.getLastName()}));

        User user1 = new User();
        user1.setId("NO005");
        user1.setFirstName("Shane");
        user1.setLastName("Wanne");
        user1.setBioData("IT Manager");
        user1.setEmail("shane@gmail.com");
        user1.setUserType("Admin");
        user1.setAdminJobResponsibility("IT Manager");
        user1.setAdminAccessCode(1230);
        user1.setSearchSuggest( new Completion(new String[] {user1.getFirstName(),user1.getLastName()}));

        User user2 = new User();
        user2.setId("NO006");
        user2.setFirstName("Brayn");
        user2.setLastName("Lara");
        user2.setBioData("IT Manager");
        user2.setEmail("brayan@gmail.com");
        user2.setUserType("Admin");
        user2.setAdminJobResponsibility("IT Manager");
        user2.setAdminAccessCode(1231);
        user2.setSearchSuggest( new Completion(new String[] {user2.getFirstName(),user2.getLastName()}));

        User savedUser = userService.save(user);
        User savedUser1 = userService.save(user1);
        User savedUser2 = userService.save(user2);

        PageRequest pageable = new PageRequest(0, 50);
        Page<User> userPage = userService.getUsers(pageable);
        
        Assert.assertEquals(3,userPage.getContent().size());
        Assert.assertEquals(1,userPage.getTotalPages());
    }

    @Test
    public void testSearch() {
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
        user2.setFirstName("Brayn");
        user2.setLastName("Lara");
        user2.setBioData("IT Manager");
        user2.setEmail("brayan@gmail.com");
        user2.setUserType("Admin");
        user2.setAdminJobResponsibility("IT Manager");
        user2.setAdminAccessCode(1231);
        user2.setSearchSuggest( new Completion(new String[] {user2.getFirstName(),user2.getLastName()}));
        User savedUser2 = userService.save(user2);

        User user3 = new User();
        user3.setId("NO006");
        user3.setFirstName("Jack");
        user3.setLastName("Jill");
        user3.setBioData("IT Manager");
        user3.setEmail("brayan@gmail.com");
        user3.setUserType("Admin");
        user3.setAdminJobResponsibility("IT Manager");
        user3.setAdminAccessCode(1231);
        user3.setSearchSuggest( new Completion(new String[] {user3.getFirstName(),user3.getLastName()}));
        User savedUser3 = userService.save(user3);

        Page<User> userPageUserName  = userService.search("Jack");
        Assert.assertEquals(1,userPageUserName.getContent().size());

        Page<User> userPageBioData  = userService.search("IT Manager");
        Assert.assertEquals(2,userPageBioData.getContent().size());
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

        List<String> nameSuggestions = userService.getUserNameSuggestions("ann");
        Assert.assertEquals(2,nameSuggestions.size());
    }

    @Test
    public void testDeleteUser() {
        User user = new User();
        user.setId("NO009");
        user.setFirstName("Anne");
        user.setLastName("Wills");
        user.setBioData("QA Manager");
        user.setEmail("anne@gmail.com");
        user.setUserType("Normal");
        user.setSearchSuggest( new Completion(new String[] {user.getFirstName(),user.getLastName()}));
        User savedUser = userService.save(user);

        Assert.assertNotNull(user);
        Assert.assertNotNull(user.getId());

        userService.delete(savedUser);
        User deletedUser = userService.findUserById(savedUser.getId());
        Assert.assertNull(deletedUser);

    }

    @Test(expected = AppServiceException.class)
    public void testFindUsersError() throws AppServiceException {
        PageRequest pageable = null;
        userService.getUsers(pageable);
    }
}
