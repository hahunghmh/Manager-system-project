package mockproject.backend.domain.entity.enums;

public enum EPermission {
    CREATE_USER("CREATE_USER"),
    READ_USER("READ_USER"),
    UPDATE_USER("UPDATE_USER"),
    DELETE_USER("DELETE_USER"),
    IMPORT_USER("IMPORT_USER"),

    CREATE_SYLLABUS("CREATE_SYLLABUS"),
    READ_SYLLABUS("READ_SYLLABUS"),
    UPDATE_SYLLABUS("UPDATE_SYLLABUS"),
    DELETE_SYLLABUS("DELETE_SYLLABUS"),
    IMPORT_SYLLABUS("IMPORT_SYLLABUS"),

    CREATE_TRAINING("CREATE_TRAINING"),
    READ_TRAINING("READ_TRAINING"),
    UPDATE_TRAINING("UPDATE_TRAINING"),
    DELETE_TRAINING("DELETE_TRAINING"),
    IMPORT_TRAINING("IMPORT_TRAINING"),

    CREATE_CLASS("CREATE_CLASS"),
    READ_CLASS("READ_CLASS"),
    UPDATE_CLASS("UPDATE_CLASS"),
    DELETE_CLASS("DELETE_CLASS"),
    IMPORT_CLASS("IMPORT_CLASS");

    private final String permission;

    EPermission(String permission) {
        this.permission = permission;
    }

    @Override
    public String toString() {
        return permission;
    }
}
