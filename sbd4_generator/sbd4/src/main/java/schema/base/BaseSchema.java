package schema.base;

import annotiations.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public abstract class BaseSchema {
    private static final SimpleDateFormat importDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    private static final SimpleDateFormat jsonDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    private String toJson(String[] fieldNamesToExclude) {
        BaseSchema baseSchema = this;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        Field[] declaredFields = Arrays.stream(this.getClass().getDeclaredFields()).filter(field -> {
            Annotation[] annotations = field.getAnnotations();
            field.setAccessible(true);
            for(Annotation annotation : annotations) {
                if(annotation instanceof JsonExclude) {
                    return false;
                }
                if(annotation instanceof JsonObject) {
                    try {
                        if(field.get(baseSchema) == null) {
                            return false;
                        }
                    } catch (IllegalAccessException e) {
                        return false;
                    }
                }
            }
            for(String fieldName : fieldNamesToExclude) {
                if(field.getName().equals(fieldName)) {
                    return false;
                }
            }
            return true;
        }).toArray(Field[]::new);
        for(int i=0; i<declaredFields.length; i++) {
            Field field = declaredFields[i];
            field.setAccessible(true);
            boolean array = false;
            boolean arrayOfValues = false;
            boolean collectionField = false;
            boolean exclude = false;
            boolean primary = false;
            boolean object = false;
            String[] excludeFromObject = new String[]{};
            Annotation[] annotations = field.getAnnotations();
            for(Annotation annotation : annotations) {
                if(annotation instanceof JsonArray) {
                    array = true;
                }
                if(annotation instanceof JsonArrayOfValues) {
                    arrayOfValues = true;
                }
                if(annotation instanceof JsonCollectionField) {
                    collectionField = true;
                }
                if(annotation instanceof JsonExclude) {
                    exclude = true;
                }
                if(annotation instanceof JsonPrimary) {
                    primary = true;
                }
                if(annotation instanceof JsonObject) {
                    JsonObject jsonObject = (JsonObject) annotation;
                    excludeFromObject = jsonObject.excludeFields();
                    object = true;
                }
            }
            if(exclude) {
                continue;
            }
            try {
                if (array) {
                    stringBuilder.append(storeAsArray(field));
                } else if(arrayOfValues) {
                    stringBuilder.append(storeAsArrayOfValues(field));
                } else if (collectionField) {
                    BaseSchema collectionObject = (BaseSchema) field.get(baseSchema);
                    if(collectionObject == null) {
                        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                        continue;
                    }
                    stringBuilder.append("\"").append(field.getName()).append("\": ").append(collectionObject.getPrimaryKey());
                } else if (primary) {
                    stringBuilder.append("\"_id\": ").append(field.get(baseSchema));
                } else if(object) {
                    stringBuilder.append("\"").append(field.getName()).append("\": ").append(((BaseSchema) field.get(baseSchema)).toJson(excludeFromObject));
                } else if(field.get(this) instanceof Date){
                    LocalDateTime date = ((Date)field.get(baseSchema)).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                    String dateString = jsonDateFormat.format((Date) field.get(baseSchema));
                    stringBuilder.append("\"").append(field.getName()).append("\": {\"$date\":\"").append(dateString).append("\"}");
                } else {
                    stringBuilder.append("\"").append(field.getName()).append("\": \"").append(field.get(baseSchema)).append("\"");
                }
                if(i != declaredFields.length - 1) {
                    stringBuilder.append(",");
                }
            } catch (IllegalAccessException e) {
                System.out.println("exception: " + e);
            }
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        return toJson(new String[]{});
    }

    public String toImport() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("insert into ").append(this.getClass().getSimpleName().toLowerCase()).append(" (");
        Field[] declaredFields = Arrays.stream(this.getClass().getDeclaredFields()).filter(field -> {
            Annotation[] annotations = field.getAnnotations();
            for(Annotation annotation : annotations) {
                if(annotation instanceof JsonArray) {
                    return false;
                }
                if(annotation instanceof JsonArrayOfValues) {
                    return false;
                }
            }
            return true;
        }).toArray(Field[]::new);
        for (int i=0; i<declaredFields.length; i++) {
            Field field = declaredFields[i];
            field.setAccessible(true);
            stringBuilder.append(field.getName());
            if(i != declaredFields.length - 1) {
                stringBuilder.append(", ");
            }
        }
        stringBuilder.append(") values (");
        for (int i=0; i<declaredFields.length; i++) {
            Field field = declaredFields[i];
            boolean object = false;
            Annotation[] annotations = field.getAnnotations();
            for(Annotation annotation : annotations) {
                if(annotation instanceof ImportObject) {
                    object = true;
                }
            }
            try {
                if(object) {
                    BaseSchema baseSchema = (BaseSchema) field.get(this);
                    if(baseSchema == null) {
                        stringBuilder.append("null");
                        continue;
                    }
                    stringBuilder.append(baseSchema.getPrimaryKey());
                } else if(field.get(this) instanceof Date){
                    Date date = (Date) field.get(this);
                    stringBuilder.append("'").append(importDateFormat.format(date)).append("'");
                } else {
                    stringBuilder.append("'").append(field.get(this)).append("'");
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if(i != declaredFields.length - 1) {
                stringBuilder.append(", ");
            }
        }
        stringBuilder.append(");\n");
        return stringBuilder.toString();
    }

    private StringBuilder storeAsArray(Field field) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\"").append(field.getName()).append("\": [");
        try {
            List<BaseSchema> list = (List) field.get(this);
            for(int i=0; i<list.size(); i++) {
                BaseSchema obj = list.get(i);
                stringBuilder.append(obj.toString());
                if(i != list.size() - 1) {
                    stringBuilder.append(",");
                }
            }
            stringBuilder.append("]");
        } catch (IllegalAccessException e) {
            System.out.println("exception: " + e);
        }
        return stringBuilder;
    }

    private StringBuilder storeAsArrayOfValues(Field field) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\"").append(field.getName()).append("\": [");
        try {
            List<Long> list = (List) field.get(this);
            for(int i=0; i<list.size(); i++) {
                Long value = list.get(i);
                stringBuilder.append(value);
                if(i != list.size() - 1) {
                    stringBuilder.append(",");
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        stringBuilder.append("]");
        return stringBuilder;
    }

    protected abstract Long getPrimaryKey();
}
