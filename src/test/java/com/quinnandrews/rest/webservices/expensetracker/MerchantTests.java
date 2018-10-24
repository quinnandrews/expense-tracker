package com.quinnandrews.rest.webservices.expensetracker;

import com.quinnandrews.rest.webservices.expensetracker.entity.Merchant;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MerchantTests {

    private static final String PATH = "/merchants";

    @Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
	}

    @Test
    public void testMerchantCRUD() throws Exception {

        createMerchant_nullNameValidationError();
        createMerchant_blankNameValidationError();
        createMerchant_longNameValidationError();

	    final String uri = createMerchant();

	    final Merchant merchant = getMerchant_postCreate(uri);

        updateMerchant_nullNameValidationError(merchant);
        updateMerchant_blankNameValidationError(merchant);
        updateMerchant_longNameValidationError(merchant);

        updateMerchant(merchant);

        getMerchant_postUpdate(merchant);

        deleteMerchant(merchant);

        getMerchant_notFound(uri);
        updateMerchant_notFound(merchant);
        deleteMerchant_notFound(merchant);

    }

    public void createMerchant_nullNameValidationError() throws Exception {
        final Merchant merchant = new Merchant();
        merchant.setName(null);
        mockMvc.perform(post(PATH)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(merchant)))
                .andExpect(status().isBadRequest());
    }

    public void createMerchant_blankNameValidationError() throws Exception {
        final Merchant merchant = new Merchant();
        merchant.setName(" ");
        mockMvc.perform(post(PATH)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(merchant)))
                .andExpect(status().isBadRequest());
    }

    public void createMerchant_longNameValidationError() throws Exception {
        final Merchant merchant = new Merchant();
        merchant.setName(TestUtil.EXAMPLE_NAME_GREATER_THAN_32);
        mockMvc.perform(post(PATH)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(merchant)))
                .andExpect(status().isBadRequest());
    }

	private String createMerchant() throws Exception {
		final Merchant merchant = new Merchant();
		merchant.setName(TestUtil.EXAMPLE_NAME);
		final MvcResult result = mockMvc.perform(post(PATH)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(merchant)))
				.andExpect(status().isCreated())
				.andReturn();
		return result.getResponse().getRedirectedUrl();
	}

    private Merchant getMerchant_postCreate(final String URI) throws Exception {
        final MvcResult result = mockMvc.perform(get(URI))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").isNotEmpty())
                .andExpect(jsonPath("$.name").isString())
                .andExpect(jsonPath("$.name").value(TestUtil.EXAMPLE_NAME))
                .andReturn();
        return TestUtil.convertJsonStringToObject(result.getResponse().getContentAsString(), Merchant.class);
    }

    private void updateMerchant_nullNameValidationError(final Merchant merchant) throws Exception {
        merchant.setName(null);
        this.mockMvc.perform(put(PATH + "/" + merchant.getId())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(merchant)))
                .andExpect(status().isBadRequest());
    }

    private void updateMerchant_blankNameValidationError(final Merchant merchant) throws Exception {
        merchant.setName(" ");
        this.mockMvc.perform(put(PATH + "/" + merchant.getId())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(merchant)))
                .andExpect(status().isBadRequest());
    }

    private void updateMerchant_longNameValidationError(final Merchant merchant) throws Exception {
        merchant.setName(TestUtil.EXAMPLE_NAME_GREATER_THAN_32);
        this.mockMvc.perform(put(PATH + "/" + merchant.getId())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(merchant)))
                .andExpect(status().isBadRequest());
    }

    private void updateMerchant(final Merchant merchant) throws Exception {
        merchant.setName(TestUtil.EXAMPLE_NAME_MODIFIED);
        mockMvc.perform(put(PATH + "/" + merchant.getId())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(merchant)))
                .andExpect(status().isOk());
    }

    private void getMerchant_postUpdate(final Merchant merchant) throws Exception {
        mockMvc.perform(get(PATH + "/" + merchant.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.id").value(merchant.getId()))
                .andExpect(jsonPath("$.name").isNotEmpty())
                .andExpect(jsonPath("$.name").isString())
                .andExpect(jsonPath("$.name").value(merchant.getName()))
                .andExpect(jsonPath("$.name").value(TestUtil.EXAMPLE_NAME_MODIFIED));
    }

    private void deleteMerchant(final Merchant merchant) throws Exception {
        mockMvc.perform(delete(PATH + "/" + merchant.getId()))
                .andExpect(status().isNoContent());
    }

    private void getMerchant_notFound(final String URL) throws Exception {
        mockMvc.perform(get(URL))
                .andExpect(status().isNotFound());
    }

    private void updateMerchant_notFound(final Merchant merchant) throws Exception {
        mockMvc.perform(put(PATH + "/" + merchant.getId())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(merchant)))
                .andExpect(status().isNotFound());
    }

    private void deleteMerchant_notFound(final Merchant merchant) throws Exception {
        mockMvc.perform(delete(PATH + "/" + merchant.getId()))
                .andExpect(status().isNotFound());
    }

}
