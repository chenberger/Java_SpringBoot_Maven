package com.ccc.ccc_project;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.Collections.emptyList;

@UtilityClass
@Slf4j
public class JsonNodeUtils {

    public static void addAStringFieldRootLevel(JsonNode jsonNode, String key, String value) {
        ((ObjectNode) jsonNode).put(key, value);
    }

    public static void addAStringFieldRootLevel(JsonNode jsonNode, String key, int value) {
        ((ObjectNode) jsonNode).put(key, value);
    }

    public static void addABooleanFieldRootLevel(JsonNode jsonNode, String key, boolean value) {
        ((ObjectNode) jsonNode).put(key, value);
    }

    public static void removeAFieldRootLevel(JsonNode jsonNode, String key) {
        ((ObjectNode) jsonNode).remove(key);
    }

    public static String getStringFieldValueByPath(JsonNode jsonNode, String[] pathFields) {
        JsonNode nestedNode = jsonNode;
        for (String field : pathFields) {
            nestedNode = nestedNode.get(field);
        }
        return nestedNode.textValue();
    }

    public static List<String> getSListFieldValueByPath(JsonNode jsonNode, String[] pathFields) {
        JsonNode nestedNode = jsonNode;
        for (String field : pathFields) {
            nestedNode = nestedNode.get(field);
        }
        return nestedNode == null ? emptyList() : convertToList((ArrayNode) nestedNode);
    }

    private static List<String> convertToList(ArrayNode values) {
        final List<String> result = new ArrayList<>(values.size());
        values.forEach(jsonNode -> result.add(jsonNode.asText()));
        return result;
    }

    public static void addAFieldByPath(JsonNode jsonNode, String[] pathFields, String key, String value) {
        JsonNode nestedNode = jsonNode;
        for (String field : pathFields) {
            nestedNode = nestedNode.get(field);
        }

        if(nestedNode != null) {
            ((ObjectNode) nestedNode).put(key, value);
        }
    }

    public static void removeFieldOfListByPathAndName(JsonNode jsonNode, String[] pathFields, String fieldName) {
        JsonNode nestedNode = jsonNode;
        for (String field : pathFields) {
            nestedNode = nestedNode.get(field);
        }

        if(nestedNode != null) {
            for(JsonNode node : nestedNode) {
                ((ObjectNode) node).remove(fieldName);
            }
        }
    }

    public static void removeFieldByPathAndName(JsonNode jsonNode, String[] pathFields, String fieldName) {
        JsonNode nestedNode = jsonNode;
        for (String field : pathFields) {
            nestedNode = nestedNode.get(field);
        }

        if(nestedNode != null) {
            ((ObjectNode) nestedNode).remove(fieldName);
        }
    }

    public static void removeAFieldByPathFieldsAndNameFieldTwoIsLIstAndFieldToBeRemovedIsFarOneLayer(JsonNode jsonNode, String[] pathFields, String fieldName) {
        JsonNode nestedNode = jsonNode.get(pathFields[0]).get(pathFields[1]);

        for (JsonNode node : nestedNode) {
            nestedNode = node.get(pathFields[2]);
            ((ObjectNode) nestedNode).remove(fieldName);
        }
    }

    public static void removeAFieldByPathFieldsAndNameFieldTwoIsLIst(JsonNode jsonNode, String[] pathFields, String fieldName) {
        JsonNode nestedNode = jsonNode.get(pathFields[0]).get(pathFields[1]);

        for (JsonNode node : nestedNode) {
            ((ObjectNode) node).remove(fieldName);
        }
    }

    public static void convertFieldByPath(JsonNode jsonNode, String[] pathFields, Function<List<String>, String> func) {
        JsonNode nestedNode = jsonNode;
        var pathFieldsArrayLength = pathFields.length;

        for (int i = 0; i < pathFieldsArrayLength - 1; i++) {
            nestedNode = nestedNode.get(pathFields[i]);
        }

        if(nestedNode != null) {
            for(JsonNode node : nestedNode) {
                List<String> oldValue = convertToList((ArrayNode)node.get(pathFields[pathFieldsArrayLength - 1]));
                ((ObjectNode) node).put(pathFields[pathFieldsArrayLength - 1], func.apply(oldValue));
            }
        }
    }

    public static JsonNode getJsonNodeByJsonNodeAndPath(JsonNode jsonNode, String[] pathFieldsPolicyRulesDst) {
        JsonNode nestedNode = jsonNode;

        for (String field : pathFieldsPolicyRulesDst) {
            nestedNode = nestedNode.get(field);
        }

        return nestedNode;
    }

    public static void addPositionByPath(JsonNode jsonNode, String[] pathFields, String fieldName) {
        JsonNode nestedNode = jsonNode;
        for (String field : pathFields) {
            nestedNode = nestedNode.get(field);
        }

        int counter = 0;

        for(JsonNode node : nestedNode) {
            ((ObjectNode) node).put(fieldName, counter);
            counter++;
        }
    }

    public static void addFieldToArrayFieldByPathAndFieldName(JsonNode jsonNode, String[] pathFields, String fieldName, String objectName, Map<String, Boolean> rulesToReadOnlyValues) {
        JsonNode nestedNode = jsonNode;
        for (String field : pathFields) {
            nestedNode = nestedNode.get(field);
        }

        for(JsonNode node : nestedNode) {
            ((ObjectNode) node).put(fieldName, rulesToReadOnlyValues.get(node.get(objectName).asText()));
        }
    }

    public static void addFieldToArrayFieldByPathAndFieldName(JsonNode jsonNode, String[] pathFields, String fieldName, boolean fieldValue) {
        JsonNode nestedNode = jsonNode;
        for (String field : pathFields) {
            nestedNode = nestedNode.get(field);
        }

        for(JsonNode node : nestedNode) {
            ((ObjectNode) node).put(fieldName, fieldValue);
        }
    }

    public static void addFieldToArrayFieldByPathAndFieldName(JsonNode jsonNode, String[] pathFields, String fieldName, String fieldValue) {
        JsonNode nestedNode = jsonNode;
        for (String field : pathFields) {
            nestedNode = nestedNode.get(field);
        }

        for(JsonNode node : nestedNode) {
            ((ObjectNode) node).put(fieldName, fieldValue);
        }
    }

    public static void convertEmptyStringFieldToEmptyMap(String[] pathFields, JsonNode jsonNode, String fieldName1, String fieldName2) {
        JsonNode nestedNode = jsonNode;
        for (String field : pathFields) {
            nestedNode = nestedNode.get(field);
        }

        for(JsonNode node : nestedNode) {
            JsonNode profilesNode = node.get(fieldName1).get(fieldName2);
            if(profilesNode.asText().isEmpty()) {
                ((ObjectNode) node).replace(fieldName1, new ObjectMapper().valueToTree(new HashMap<>()));
            }

        }
    }

    public static void cleanFieldOfTwoEmptyFields(JsonNode jsonNode, String[] pathFields, String fieldOfTwo, String nestedFirst, String nestedSecond) {
        JsonNode nestedNode = jsonNode;
        for (String field : pathFields) {
            nestedNode = nestedNode.get(field);
        }

        for(JsonNode node : nestedNode) {
            JsonNode nodeOfTwo = node.get(fieldOfTwo);
            if(nodeOfTwo.get(nestedFirst).isEmpty() && nodeOfTwo.get(nestedSecond).isEmpty()) {
                ((ObjectNode) node).remove(fieldOfTwo);
            }
        }
    }

    public static void cleanArrayFieldIfArrayIsEmpty(JsonNode jsonNode, String[] pathFields, String[] pathFields2, String fieldName) {
        JsonNode nestedNode1 = jsonNode;
        for (String field : pathFields) {
            nestedNode1 = nestedNode1.get(field);
        }

        for (JsonNode node : nestedNode1) {
            JsonNode nestedNode2 = node;
            for (String field : pathFields2) {
                nestedNode2 = nestedNode2.get(field);
            }

            if (nestedNode2.get(fieldName).isEmpty()) {
                ((ObjectNode) nestedNode2).remove(fieldName);
            }
        }
    }
}

