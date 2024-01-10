create definer = root@localhost trigger trigger_name
    after insert
    on training_content
    for each row
BEGIN
    DECLARE unit_id INT;
    DECLARE sy_code VARCHAR(255);

    -- Get the training_unit_id from the new row
    SET unit_id = NEW.training_unit_id;

    -- Get the distinct topic_code from the training_unit table
    SELECT DISTINCT tu.topic_code INTO sy_code
    FROM fams.training_unit tu
    WHERE tu.training_unit_id = unit_id;

    -- Update the duration in the syllabus table
    UPDATE syllabus sy
    SET sy.duration = (
        SELECT SUM(tc.duration)
        FROM fams.training_content tc
        WHERE tc.training_unit_id = unit_id
    )
    WHERE sy.topic_code = sy_code;
END;

