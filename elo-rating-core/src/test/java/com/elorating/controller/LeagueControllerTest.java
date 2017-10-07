package com.elorating.controller;

import com.elorating.model.League;
import com.elorating.repository.LeagueRepository;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

public class LeagueControllerTest extends BaseControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(LeagueControllerTest.class);

    @Mock
    LeagueRepository leagueRepository;

    @InjectMocks
    LeagueController leagueController;

    @Before
    public void setUp() throws Exception {
        mockMvc = webAppContextSetup(webApplicationContext).build();
        league = new League("testID1", "League");

        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(leagueController).build();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGet() throws Exception {
        when(leagueRepository.findOne(league.getId())).thenReturn(league);
        mockMvc.perform(get("/leagues/" + league.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(league.getId())))
                .andExpect(jsonPath("$.name", is("League")));
    }

    @Test
    public void testGetAll() throws Exception {
        when(leagueRepository.findOne(league.getId())).thenReturn(league);
        mockMvc.perform(get("/leagues/" + league.getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void testFindByName() throws Exception {
        List<League> leagues = new ArrayList<>();
        leagues.add(new League("111", "League 1"));
        leagues.add(new League("222", "league 2"));
        when(leagueRepository.findByNameLikeIgnoreCase("Lea")).thenReturn(leagues);
        mockMvc.perform(get("/leagues/find-by-name?name=Lea"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(leagues.size())));
    }

    @Test
    public void testCreate() throws Exception {
        String leagueJson = objectMapper.writeValueAsString(new League(null, "New league"));
        when(leagueRepository.save(any(League.class))).thenReturn(league);
        mockMvc.perform(post("/leagues")
                .content(leagueJson)
                .contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(league.getName())));
    }

    @Test
    public void test_createAndDeleteLeague_success() throws Exception {
        when(leagueRepository.findOne(league.getId())).thenReturn(league);
        mockMvc.perform(get("/leagues/" + league.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/leagues/delete/" + league.getId()))
                .andExpect(status().isOk());

        when(leagueRepository.findOne(league.getId())).thenReturn(null);
        mockMvc.perform(get("/leagues/" + league.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    public void test_createLeagueAndQueue_success() throws Exception {
        League newLeague = new League("testID", "TestLeague");
        String newLeagueJson = objectMapper.writeValueAsString(newLeague);
        when(leagueRepository.save(any(League.class))).thenReturn(newLeague);
        mockMvc.perform(post("/leagues")
                .content(newLeagueJson)
                .contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(newLeague.getName())));


        when(leagueRepository.findOne(newLeague.getId())).thenReturn(newLeague);
        mockMvc.perform(get("/leagues/" + newLeague.getId()))
                .andExpect(status().isOk());
    }
}