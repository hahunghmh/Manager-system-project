/*
 *@project : BackEnd
 *@Create by: HCM23_FRF_FJB_04_GROUP02
 *@Create time: 11/16/2023 - 3:10 PM
 */

package mockproject.backend.utils.constant;

//All error code send to FE
public interface ErrorCode {
    //
    String EM99 = "Missing error code for user login.";
    String EM98 = "Need update password before continue";

    String EMNEV="Action cannot perform";




    //    OK
    String EM00 = "Successful!";
    String EM01 = "User type is required.";
    String EM02 = "Name is required.";
    String EM03 = "Email address is required.";
    String EM04 = "Email address is existed. Please check and input another email address.";
    String EM05 = "Email address or Password is invalid. Please check and input again.";
    String EM06 = "Phone is required.";
    String EM07 = "Phone is invalid. Please check and input again";
    String EM08 = "Date of birth is required.";
    String EM09 = "User is created successfully.";
    String EM10 = "User is updated successfully.";
    String EM11 = "Role is updated successfully.";
    String EM12 = "Syllabus name is required.";
    String EM13 = "Level is required.";
    String EM14 = "Attendee number is required.";
    String EM15 = "Technical requirement(s) is required.";
    String EM16 = "Course objectives are required.";
    String EM17 = "Unit name is required.";
    String EM18 = "Content name is required.";
    String EM19 = "Output standard is required.";
    String EM20 = "Training time is required.";
    String EM21 = "Delivery type is required.";
    String EM22 = "Delete all content of the Day?";
    String EM23 = "Please input at least one day.";
    String EM24 = "Please input at least one unit into this day.";
    String EM25 = "Please input at least one content into this unit.";
    String EM26 = "The duration exceeds 8 hours per day. Please check again.";
    String EM27 = "Quiz is required.";
    String EM28 = "Assignment is required.";
    String EM29 = "Final is required.";
    String EM30 = "Final Theory is required.";
    String EM31 = "Final Practice is required.";
    String EM32 = "GPA is required.";
    String EM33 = "Total of all assessment is not 100%. Please check again.";
    String EM34 = "File is required.";
    String EM35 = "Program name is required.";
    String EM36 = "General information is required.";
    String EM37 = "Syllabus is required.";
    String EM38 = "List of syllabuses is required.";
    String EM39 = "File is required.";
    String EM40 = "File is invalid. Please check and upload again.";
    String EM41 = "Class name is required.";
    String EM42 = "Class time is required.";
    String EM43 = "Location is required.";
    String EM44 = "Trainer is required.";
    String EM45 = "Admin is required.";
    String EM46 = "FSU is required.";
    String EM47 = "Time frame is required.";
    String EM48 = "Training program is required";
    String EM49 = "If you change training program, list of syllabuses will be replaced by new training program";
    String EM50 = "Do you want to delete <class name> class?";
    String EM51 = "Do you want to update schedule?\nThis session of class only.\nAll Session of this class.";
    String EM52= "User is updated fail.";
    String EM53= "User is delete fail.";
    String EM54 = "User is delete successfully.";
    String EM55  = "Status is required";
    String EM56="Turn on active successfully.";
    String EM57="Turn off active successfully.";
    String EM58 = "Training program is duplicate successfully.";
    String EM59 = "Training program is delete successfully.";
    String EM60 = "Training program is update successfully.";
    String EM61 = "Training program is create successfully.";


}
