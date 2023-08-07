package com.ccc.ccc_project;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CccProjectApplicationTests {
    private ObjectMapper objectMapper;
    private JsonNode sampleJsonNode;
    @BeforeEach
    void setUp() throws JsonProcessingException {
        objectMapper = new ObjectMapper();

        // Sample JSON data
        String jsonString = "{\"name\": \"John\", \"age\": 30, \"isStudent\": true, " +
                "\"address\": {\"city\": \"New York\", \"zip\": 10001}, " +
                "\"hobbies\": [\"Reading\", " +
                "{\"sports\": \"Football\", \"outdoor\": true}," +
                "{\"sports\": \"Tennis\", \"outdoor\": false}]}";
        sampleJsonNode = objectMapper.readTree(jsonString);
    }

    @Test
    public void testAddAStringFieldRootLevel() {
        JsonNodeUtils.addAStringFieldRootLevel(sampleJsonNode, "country", "USA");
        assertEquals("USA", sampleJsonNode.get("country").asText());
    }
    @Test
    public void testAddAIntFieldRootLevel() {
        JsonNodeUtils.addAStringFieldRootLevel(sampleJsonNode, "NumberOfChildren", 0);
        assertEquals(0, sampleJsonNode.get("NumberOfChildren").asInt());
    }
    @Test
    public void testAddABooleanFieldRootLevel() {
        JsonNodeUtils.addABooleanFieldRootLevel(sampleJsonNode, "isMarried", false);
        assertFalse(sampleJsonNode.get("isMarried").asBoolean());
    }

    @Test
    public void testRemoveAFieldRootLevel() {
        JsonNodeUtils.removeAFieldRootLevel(sampleJsonNode, "age");
        assertFalse(sampleJsonNode.has("age"));
    }

    @Test
    public void testGetStringFieldValueByPath() {


    }

    @Test
    public void testGetSListFieldValueByPath() {

    }

    @Test
    public void testAddAFieldByPath() {

    }

    @Test
    public void testRemoveFieldOfListByPathAndName() {

    }

    @Test
    public void testConvertFieldByPath() {

    }

    @Test
    public void testGetJsonNodeByJsonNodeAndPath() {

    }

    @Test
    public void testCleanFieldOfTwoEmptyFields() {

    }

    @Test
    public void testCleanArrayFieldIfArrayIsEmpty() {

    }

}
