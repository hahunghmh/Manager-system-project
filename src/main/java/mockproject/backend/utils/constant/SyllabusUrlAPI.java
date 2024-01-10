/*
 *@project : BackEnd
 *@Create by: HCM23_FRF_FJB_04_GROUP02
 *@Create time: 11/26/2023 - 10:28 PM
 */


package mockproject.backend.utils.constant;

import static mockproject.backend.utils.constant.ApiUrl.API;

public interface SyllabusUrlAPI {
    String SYLLABUS = API + "/syllabus";
    String SYLLABUS_CREATE = SYLLABUS + "/create";

    String SYLLABUS_UPDATE = SYLLABUS + "/update";
    String SYLLABUS_GET_UPDATE = SYLLABUS + "/update/{id}";
    String SYLLABUS_VIEW = SYLLABUS + "/view";
    String SYLLABUS_ALL_LIST = SYLLABUS + "/list";
    String SYLLABUS_BY_TOPICCODE = SYLLABUS + "/topic-code";

   String SYLLABUS_DUPLICATE=SYLLABUS+"/duplicated/{topicCode}";

    String SYLLABUS_DELETE=SYLLABUS+"/delete/{topicCode}";
}
