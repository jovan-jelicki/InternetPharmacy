package app.service;

import app.dto.UserPasswordDTO;
import app.model.user.Credentials;
import app.model.user.Dermatologist;
import app.repository.DermatologistRepository;
import app.service.impl.DermatologistServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsersServiceTests {

    @Mock
    private DermatologistRepository dermatologistRepository;

    @Mock
    private Dermatologist dermatologistMock;

    @InjectMocks
    private DermatologistServiceImpl dermatologistService;

    @Test
    public void testChangePasswordDermatologist(){
        Credentials credentials = new Credentials();
        credentials.setPassword("Proba1");
        Dermatologist dermatologist = new Dermatologist();
        dermatologist.setCredentials(credentials);

        when(dermatologistRepository.findById(1l)).thenReturn(Optional.of(dermatologist));

        UserPasswordDTO userPasswordDTO = new UserPasswordDTO(1l, "Proba1", "Proba2", "Proba2");
        dermatologistService.changePassword(userPasswordDTO);

        Dermatologist dermatologist2 = dermatologistService.read(1l).get();

        assertThat(dermatologist2.getCredentials().getPassword(), is(equalTo("Proba2")));
    }
}
