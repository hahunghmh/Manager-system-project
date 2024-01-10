DELIMITER //

CREATE TRIGGER update_syllabus_duration_after_insert
    AFTER INSERT ON training_content
    FOR EACH ROW
BEGIN
    DECLARE topic_code_var VARCHAR(255);

    -- Get the topic_code from the training_unit table
    SELECT topic_code
    INTO topic_code_var
    FROM training_unit
    WHERE training_unit_id = NEW.training_unit_id;

    -- Update the duration in the syllabus table
    UPDATE syllabus s
    SET duration = (
        SELECT SUM(tc.duration)
        FROM training_content tc
        WHERE tc.training_unit_id IN (
            SELECT tu.training_unit_id
            FROM training_unit tu
            WHERE tu.topic_code = topic_code_var
        )
    )
    WHERE s.topic_code = topic_code_var;
END;
//
DELIMITER ;

CREATE TRIGGER update_syllabus_duration_after_update
    AFTER UPDATE ON training_content
    FOR EACH ROW
BEGIN
    DECLARE topic_code_var VARCHAR(255);

    -- Get the topic_code from the training_unit table
    SELECT topic_code
    INTO topic_code_var
    FROM training_unit
    WHERE training_unit_id = NEW.training_unit_id;

    -- Update the duration in the syllabus table
    UPDATE syllabus s
    SET duration = (
        SELECT SUM(tc.duration)
        FROM training_content tc
        WHERE tc.training_unit_id IN (
            SELECT tu.training_unit_id
            FROM training_unit tu
            WHERE tu.topic_code = topic_code_var
        )
    )
    WHERE s.topic_code = topic_code_var;
END;
//
DELIMITER ;

