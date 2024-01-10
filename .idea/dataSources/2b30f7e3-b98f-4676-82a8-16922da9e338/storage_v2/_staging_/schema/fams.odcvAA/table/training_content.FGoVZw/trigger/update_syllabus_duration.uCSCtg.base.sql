create definer = root@localhost trigger update_syllabus_duration
    after insert
    on training_content
    for each row
BEGIN
    DECLARE topic_code_var VARCHAR(255);
    DECLARE syllabus_topic_code VARCHAR(255);
    -- Get the topic_code from the training_unit table
    SELECT topic_code
    INTO topic_code_var
    FROM training_unit
    WHERE training_unit_id = NEW.training_unit_id;
    -- Update the duration in the syllabus table
    UPDATE syllabus s
    SET duration = (select sum(fams.training_content.duration)
                    from training_content
                    where training_unit_id in
                          (select fams.training_unit.training_unit_id
                           from fams.training_unit
                           where fams.training_unit.topic_code = topic_code_var))
    where topic_code = topic_code_var;
END;

