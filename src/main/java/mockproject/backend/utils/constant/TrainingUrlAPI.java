/*
 *@project : BackEnd
 *@Create by: HCM23_FRF_FJB_04_GROUP02
 *@Create time: 11/26/2023 - 10:23 PM
 */


package mockproject.backend.utils.constant;

import static mockproject.backend.utils.constant.ApiUrl.API;

public interface TrainingUrlAPI {

    String PROGRAM = API + "/training_program";
    //retrieve
    String TRAINING_PROGRAM_VIEW = PROGRAM + "/view";
    String TRAINING_PROGRAM_ALL_LIST = PROGRAM + "/list";
    String TRAINING_PROGRAM_BY_TRAININGCODE_SYLLABUS = PROGRAM + "/getSyllabusByTrainingCode/{trainingProgramCode}";
    String TRAINING_PROGRAM_BY_TRAININGCODE = PROGRAM + "/getByTrainingCode/{trainingProgramCode}";

    //update
    String TRAINING_PROGRAM_UPDATE = PROGRAM + "/update";
    String TRAINING_PROGRAM_STATUS = PROGRAM + "/status/{trainingProgramCode}";



    String TRAINING_PROGRAM_CREATE = PROGRAM + "/create";
    String TRAINING_PROGRAM_DUPLICATE = PROGRAM + "/duplicate/{trainingProgramCode}";

    String TRAINING_PROGRAM_DELETE = PROGRAM + "/delete/{trainingProgramCode}";


}
