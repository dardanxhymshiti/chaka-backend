package io.chaka.application.web.rest;

import io.chaka.application.ChakabackendApp;
import io.chaka.application.domain.ChakaUser;
import io.chaka.application.repository.ChakaUserRepository;
import io.chaka.application.service.ChakaUserService;
import io.chaka.application.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static io.chaka.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link ChakaUserResource} REST controller.
 */
@SpringBootTest(classes = ChakabackendApp.class)
public class ChakaUserResourceIT {

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SURNAME = "AAAAAAAAAA";
    private static final String UPDATED_SURNAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_BIRTHDAY = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_BIRTHDAY = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_ZIP = "AAAAAAAAAA";
    private static final String UPDATED_ZIP = "BBBBBBBBBB";

    private static final String DEFAULT_STREET = "AAAAAAAAAA";
    private static final String UPDATED_STREET = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_LANGUAGE = "AAAAAAAAAA";
    private static final String UPDATED_LANGUAGE = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATA_OF_REGISTRATION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_OF_REGISTRATION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ChakaUserRepository chakaUserRepository;

    @Autowired
    private ChakaUserService chakaUserService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restChakaUserMockMvc;

    private ChakaUser chakaUser;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ChakaUserResource chakaUserResource = new ChakaUserResource(chakaUserService);
        this.restChakaUserMockMvc = MockMvcBuilders.standaloneSetup(chakaUserResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ChakaUser createEntity(EntityManager em) {
        ChakaUser chakaUser = new ChakaUser()
            .userID(DEFAULT_USER_ID)
            .name(DEFAULT_NAME)
            .surname(DEFAULT_SURNAME)
            .birthday(DEFAULT_BIRTHDAY)
            .username(DEFAULT_USERNAME)
            .password(DEFAULT_PASSWORD)
            .country(DEFAULT_COUNTRY)
            .city(DEFAULT_CITY)
            .zip(DEFAULT_ZIP)
            .street(DEFAULT_STREET)
            .email(DEFAULT_EMAIL)
            .phone(DEFAULT_PHONE)
            .language(DEFAULT_LANGUAGE)
            .dataOfRegistration(DEFAULT_DATA_OF_REGISTRATION);
        return chakaUser;
    }

    @BeforeEach
    public void initTest() {
        chakaUser = createEntity(em);
    }

    @Test
    @Transactional
    public void createChakaUser() throws Exception {
        int databaseSizeBeforeCreate = chakaUserRepository.findAll().size();

        // Create the ChakaUser
        restChakaUserMockMvc.perform(post("/api/chaka-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chakaUser)))
            .andExpect(status().isCreated());

        // Validate the ChakaUser in the database
        List<ChakaUser> chakaUserList = chakaUserRepository.findAll();
        assertThat(chakaUserList).hasSize(databaseSizeBeforeCreate + 1);
        ChakaUser testChakaUser = chakaUserList.get(chakaUserList.size() - 1);
        assertThat(testChakaUser.getUserID()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testChakaUser.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testChakaUser.getSurname()).isEqualTo(DEFAULT_SURNAME);
        assertThat(testChakaUser.getBirthday()).isEqualTo(DEFAULT_BIRTHDAY);
        assertThat(testChakaUser.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testChakaUser.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testChakaUser.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testChakaUser.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testChakaUser.getZip()).isEqualTo(DEFAULT_ZIP);
        assertThat(testChakaUser.getStreet()).isEqualTo(DEFAULT_STREET);
        assertThat(testChakaUser.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testChakaUser.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testChakaUser.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
        assertThat(testChakaUser.getDataOfRegistration()).isEqualTo(DEFAULT_DATA_OF_REGISTRATION);
    }

    @Test
    @Transactional
    public void createChakaUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = chakaUserRepository.findAll().size();

        // Create the ChakaUser with an existing ID
        chakaUser.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restChakaUserMockMvc.perform(post("/api/chaka-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chakaUser)))
            .andExpect(status().isBadRequest());

        // Validate the ChakaUser in the database
        List<ChakaUser> chakaUserList = chakaUserRepository.findAll();
        assertThat(chakaUserList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllChakaUsers() throws Exception {
        // Initialize the database
        chakaUserRepository.saveAndFlush(chakaUser);

        // Get all the chakaUserList
        restChakaUserMockMvc.perform(get("/api/chaka-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chakaUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].userID").value(hasItem(DEFAULT_USER_ID.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].surname").value(hasItem(DEFAULT_SURNAME.toString())))
            .andExpect(jsonPath("$.[*].birthday").value(hasItem(DEFAULT_BIRTHDAY.toString())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME.toString())))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD.toString())))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].zip").value(hasItem(DEFAULT_ZIP.toString())))
            .andExpect(jsonPath("$.[*].street").value(hasItem(DEFAULT_STREET.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())))
            .andExpect(jsonPath("$.[*].dataOfRegistration").value(hasItem(DEFAULT_DATA_OF_REGISTRATION.toString())));
    }
    
    @Test
    @Transactional
    public void getChakaUser() throws Exception {
        // Initialize the database
        chakaUserRepository.saveAndFlush(chakaUser);

        // Get the chakaUser
        restChakaUserMockMvc.perform(get("/api/chaka-users/{id}", chakaUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(chakaUser.getId().intValue()))
            .andExpect(jsonPath("$.userID").value(DEFAULT_USER_ID.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.surname").value(DEFAULT_SURNAME.toString()))
            .andExpect(jsonPath("$.birthday").value(DEFAULT_BIRTHDAY.toString()))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME.toString()))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD.toString()))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.zip").value(DEFAULT_ZIP.toString()))
            .andExpect(jsonPath("$.street").value(DEFAULT_STREET.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()))
            .andExpect(jsonPath("$.dataOfRegistration").value(DEFAULT_DATA_OF_REGISTRATION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingChakaUser() throws Exception {
        // Get the chakaUser
        restChakaUserMockMvc.perform(get("/api/chaka-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateChakaUser() throws Exception {
        // Initialize the database
        chakaUserService.save(chakaUser);

        int databaseSizeBeforeUpdate = chakaUserRepository.findAll().size();

        // Update the chakaUser
        ChakaUser updatedChakaUser = chakaUserRepository.findById(chakaUser.getId()).get();
        // Disconnect from session so that the updates on updatedChakaUser are not directly saved in db
        em.detach(updatedChakaUser);
        updatedChakaUser
            .userID(UPDATED_USER_ID)
            .name(UPDATED_NAME)
            .surname(UPDATED_SURNAME)
            .birthday(UPDATED_BIRTHDAY)
            .username(UPDATED_USERNAME)
            .password(UPDATED_PASSWORD)
            .country(UPDATED_COUNTRY)
            .city(UPDATED_CITY)
            .zip(UPDATED_ZIP)
            .street(UPDATED_STREET)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .language(UPDATED_LANGUAGE)
            .dataOfRegistration(UPDATED_DATA_OF_REGISTRATION);

        restChakaUserMockMvc.perform(put("/api/chaka-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedChakaUser)))
            .andExpect(status().isOk());

        // Validate the ChakaUser in the database
        List<ChakaUser> chakaUserList = chakaUserRepository.findAll();
        assertThat(chakaUserList).hasSize(databaseSizeBeforeUpdate);
        ChakaUser testChakaUser = chakaUserList.get(chakaUserList.size() - 1);
        assertThat(testChakaUser.getUserID()).isEqualTo(UPDATED_USER_ID);
        assertThat(testChakaUser.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testChakaUser.getSurname()).isEqualTo(UPDATED_SURNAME);
        assertThat(testChakaUser.getBirthday()).isEqualTo(UPDATED_BIRTHDAY);
        assertThat(testChakaUser.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testChakaUser.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testChakaUser.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testChakaUser.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testChakaUser.getZip()).isEqualTo(UPDATED_ZIP);
        assertThat(testChakaUser.getStreet()).isEqualTo(UPDATED_STREET);
        assertThat(testChakaUser.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testChakaUser.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testChakaUser.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
        assertThat(testChakaUser.getDataOfRegistration()).isEqualTo(UPDATED_DATA_OF_REGISTRATION);
    }

    @Test
    @Transactional
    public void updateNonExistingChakaUser() throws Exception {
        int databaseSizeBeforeUpdate = chakaUserRepository.findAll().size();

        // Create the ChakaUser

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChakaUserMockMvc.perform(put("/api/chaka-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chakaUser)))
            .andExpect(status().isBadRequest());

        // Validate the ChakaUser in the database
        List<ChakaUser> chakaUserList = chakaUserRepository.findAll();
        assertThat(chakaUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteChakaUser() throws Exception {
        // Initialize the database
        chakaUserService.save(chakaUser);

        int databaseSizeBeforeDelete = chakaUserRepository.findAll().size();

        // Delete the chakaUser
        restChakaUserMockMvc.perform(delete("/api/chaka-users/{id}", chakaUser.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<ChakaUser> chakaUserList = chakaUserRepository.findAll();
        assertThat(chakaUserList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChakaUser.class);
        ChakaUser chakaUser1 = new ChakaUser();
        chakaUser1.setId(1L);
        ChakaUser chakaUser2 = new ChakaUser();
        chakaUser2.setId(chakaUser1.getId());
        assertThat(chakaUser1).isEqualTo(chakaUser2);
        chakaUser2.setId(2L);
        assertThat(chakaUser1).isNotEqualTo(chakaUser2);
        chakaUser1.setId(null);
        assertThat(chakaUser1).isNotEqualTo(chakaUser2);
    }
}
