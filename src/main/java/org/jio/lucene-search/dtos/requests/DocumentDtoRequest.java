package org.jio.lucenedemo.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexOptions;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DocumentDtoRequest {

    private String id;
    private String link;
    private String id_source;
    private String id_reference;
    private String id_parent_comment;
    private String views;
    private String likes;
    private String comments;
    private String shares;
    private String rating_score;
    private String engagement_total;
    private String engagement_s_c;
    private String identity;
    private String identity_name;
    private String mention_type;
    private List<String> search_text;
    private String attachment;
    private boolean isToTopic;

    public Document toDocument() {
        Document doc = new Document();

        for (java.lang.reflect.Field field : this.getClass().getDeclaredFields()) {
            try {
                field.setAccessible(true);
                Object value = field.get(this);

                if (value != null) {
                    String fieldName = field.getName();
                    addFieldToDocument(doc, fieldName, value);
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return doc;
    }

    private void addFieldToDocument(Document doc, String fieldName, Object value) {
        FieldType textFieldType = new FieldType();
        textFieldType.setStored(true);
        textFieldType.setTokenized(true);
        textFieldType.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS);

        if (value instanceof List) {
            List<?> listValue = (List<?>) value;
            doc.add(new Field(fieldName, String.join(" ", (List<String>) listValue), textFieldType));
        } else if (value instanceof String) {
            doc.add(new Field(fieldName, (String) value, textFieldType));
        } else if (value instanceof Boolean) {
            doc.add(new StringField(fieldName, String.valueOf(value), Field.Store.YES));
        }
    }


}
