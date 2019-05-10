package io.chaka.application.web.rest;

import io.chaka.application.ChakabackendApp;
import io.chaka.application.domain.Bonus;
import io.chaka.application.repository.BonusRepository;
import io.chaka.application.service.BonusService;
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
import java.util.List;

import static io.chaka.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link BonusResource} REST controller.
 */
@SpringBootTest(classes = ChakabackendApp.class)
public class BonusResourceIT {

    private static final String DEFAULT_BONUS_ID = "AAAAAAAAAA";
    private static final String UPDATED_BONUS_ID = "BBBBBBBBBB";

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final Double DEFAULT_COINS = 1D;
    private static final Double UPDATED_COINS = 2D;

    @Autowired
    private BonusRepository bonusRepository;

    @Autowired
    private BonusService bonusService;

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

    private MockMvc restBonusMockMvc;

    private Bonus bonus;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BonusResource bonusResource = new BonusResource(bonusService);
        this.restBonusMockMvc = MockMvcBuilders.standaloneSetup(bonusResource)
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
    public static Bonus createEntity(EntityManager em) {
        Bonus bonus = new Bonus()
            .bonusID(DEFAULT_BONUS_ID)
            .userID(DEFAULT_USER_ID)
            .coins(DEFAULT_COINS);
        return bonus;
    }

    @BeforeEach
    public void initTest() {
        bonus = createEntity(em);
    }

    @Test
    @Transactional
    public void createBonus() throws Exception {
        int databaseSizeBeforeCreate = bonusRepository.findAll().size();

        // Create the Bonus
        restBonusMockMvc.perform(post("/api/bonuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bonus)))
            .andExpect(status().isCreated());

        // Validate the Bonus in the database
        List<Bonus> bonusList = bonusRepository.findAll();
        assertThat(bonusList).hasSize(databaseSizeBeforeCreate + 1);
        Bonus testBonus = bonusList.get(bonusList.size() - 1);
        assertThat(testBonus.getBonusID()).isEqualTo(DEFAULT_BONUS_ID);
        assertThat(testBonus.getUserID()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testBonus.getCoins()).isEqualTo(DEFAULT_COINS);
    }

    @Test
    @Transactional
    public void createBonusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bonusRepository.findAll().size();

        // Create the Bonus with an existing ID
        bonus.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBonusMockMvc.perform(post("/api/bonuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bonus)))
            .andExpect(status().isBadRequest());

        // Validate the Bonus in the database
        List<Bonus> bonusList = bonusRepository.findAll();
        assertThat(bonusList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllBonuses() throws Exception {
        // Initialize the database
        bonusRepository.saveAndFlush(bonus);

        // Get all the bonusList
        restBonusMockMvc.perform(get("/api/bonuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bonus.getId().intValue())))
            .andExpect(jsonPath("$.[*].bonusID").value(hasItem(DEFAULT_BONUS_ID.toString())))
            .andExpect(jsonPath("$.[*].userID").value(hasItem(DEFAULT_USER_ID.toString())))
            .andExpect(jsonPath("$.[*].coins").value(hasItem(DEFAULT_COINS.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getBonus() throws Exception {
        // Initialize the database
        bonusRepository.saveAndFlush(bonus);

        // Get the bonus
        restBonusMockMvc.perform(get("/api/bonuses/{id}", bonus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bonus.getId().intValue()))
            .andExpect(jsonPath("$.bonusID").value(DEFAULT_BONUS_ID.toString()))
            .andExpect(jsonPath("$.userID").value(DEFAULT_USER_ID.toString()))
            .andExpect(jsonPath("$.coins").value(DEFAULT_COINS.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingBonus() throws Exception {
        // Get the bonus
        restBonusMockMvc.perform(get("/api/bonuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBonus() throws Exception {
        // Initialize the database
        bonusService.save(bonus);

        int databaseSizeBeforeUpdate = bonusRepository.findAll().size();

        // Update the bonus
        Bonus updatedBonus = bonusRepository.findById(bonus.getId()).get();
        // Disconnect from session so that the updates on updatedBonus are not directly saved in db
        em.detach(updatedBonus);
        updatedBonus
            .bonusID(UPDATED_BONUS_ID)
            .userID(UPDATED_USER_ID)
            .coins(UPDATED_COINS);

        restBonusMockMvc.perform(put("/api/bonuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBonus)))
            .andExpect(status().isOk());

        // Validate the Bonus in the database
        List<Bonus> bonusList = bonusRepository.findAll();
        assertThat(bonusList).hasSize(databaseSizeBeforeUpdate);
        Bonus testBonus = bonusList.get(bonusList.size() - 1);
        assertThat(testBonus.getBonusID()).isEqualTo(UPDATED_BONUS_ID);
        assertThat(testBonus.getUserID()).isEqualTo(UPDATED_USER_ID);
        assertThat(testBonus.getCoins()).isEqualTo(UPDATED_COINS);
    }

    @Test
    @Transactional
    public void updateNonExistingBonus() throws Exception {
        int databaseSizeBeforeUpdate = bonusRepository.findAll().size();

        // Create the Bonus

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBonusMockMvc.perform(put("/api/bonuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bonus)))
            .andExpect(status().isBadRequest());

        // Validate the Bonus in the database
        List<Bonus> bonusList = bonusRepository.findAll();
        assertThat(bonusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBonus() throws Exception {
        // Initialize the database
        bonusService.save(bonus);

        int databaseSizeBeforeDelete = bonusRepository.findAll().size();

        // Delete the bonus
        restBonusMockMvc.perform(delete("/api/bonuses/{id}", bonus.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Bonus> bonusList = bonusRepository.findAll();
        assertThat(bonusList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bonus.class);
        Bonus bonus1 = new Bonus();
        bonus1.setId(1L);
        Bonus bonus2 = new Bonus();
        bonus2.setId(bonus1.getId());
        assertThat(bonus1).isEqualTo(bonus2);
        bonus2.setId(2L);
        assertThat(bonus1).isNotEqualTo(bonus2);
        bonus1.setId(null);
        assertThat(bonus1).isNotEqualTo(bonus2);
    }
}
