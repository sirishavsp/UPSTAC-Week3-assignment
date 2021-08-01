package org.upgrad.upstac.testrequests;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.web.server.ResponseStatusException;
import org.upgrad.upstac.testrequests.TestRequest;
import org.upgrad.upstac.testrequests.consultation.Consultation;
import org.upgrad.upstac.testrequests.consultation.ConsultationController;
import org.upgrad.upstac.testrequests.consultation.CreateConsultationRequest;
import org.upgrad.upstac.testrequests.consultation.DoctorSuggestion;
import org.upgrad.upstac.testrequests.lab.CreateLabResult;
import org.upgrad.upstac.testrequests.lab.LabResult;
import org.upgrad.upstac.testrequests.lab.TestStatus;
import org.upgrad.upstac.testrequests.RequestStatus;
import org.upgrad.upstac.testrequests.TestRequestQueryService;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
@Slf4j
class ConsultationControllerTest {


    @Autowired
    ConsultationController consultationController;


    @Autowired
    TestRequestQueryService testRequestQueryService;


    @Test
    @WithUserDetails(value = "doctor")
    public void calling_assignForConsultation_with_valid_test_request_id_should_update_the_request_status(){

        TestRequest testRequest = getTestRequestByStatus(RequestStatus.LAB_TEST_COMPLETED);
        TestRequest testRequest1 = consultationController.assignForConsultation(testRequest.requestId);
        assertThat(testRequest1.requestId,equalTo(testRequest.requestId));
        assertThat(testRequest1.getStatus(),equalTo("DIAGNOSIS_IN_PROGRESS"));
        assertNotNull(testRequest1.consultation);

        LabResult result = testRequest1.getLabResult();
        //Implement this method

        //Create another object of the TestRequest method and explicitly assign this object for Consultation using assignForConsultation() method
        // from consultationController class. Pass the request id of testRequest object.

        //Use assertThat() methods to perform the following two comparisons
        //  1. the request ids of both the objects created should be same
        //  2. the status of the second object should be equal to 'DIAGNOSIS_IN_PROCESS'
        // make use of assertNotNull() method to make sure that the consultation value of second object is not null
        // use getConsultation() method to get the lab result


    }

    public TestRequest getTestRequestByStatus(RequestStatus status) {
        return testRequestQueryService.findBy(status).stream().findFirst().get();
    }

    @Test
    @WithUserDetails(value = "doctor")
    public void calling_assignForConsultation_with_valid_test_request_id_should_throw_exception(){

        Long InvalidRequestId= -34L;
        ResponseStatusException result = assertThrows(ResponseStatusException.class,()->{

            consultationController.assignForConsultation(InvalidRequestId);
        });

        String invalidId = InvalidRequestId.toString();

        try{
           consultationController.assignForConsultation(InvalidRequestId);
        }
        catch (Exception e)
        {
            assertThat(e.getMessage(),containsString("Invalid ID"));
        }
        //Implement this method


        // Create an object of ResponseStatusException . Use assertThrows() method and pass assignForConsultation() method
        // of consultationController with InvalidRequestId as Id


        //Use assertThat() method to perform the following comparison
        //  the exception message should be contain the string "Invalid ID"

    }

    @Test
    @WithUserDetails(value = "doctor")
    public void calling_updateConsultation_with_valid_test_request_id_should_update_the_request_status_and_update_consultation_details(){

        TestRequest testRequest = getTestRequestByStatus(RequestStatus.DIAGNOSIS_IN_PROCESS);
        CreateConsultationRequest consultationRequest = getCreateConsultationRequest(testRequest);
        TestRequest testRequestUpdated = new TestRequest();
        testRequestUpdated.setStatus(RequestStatus.COMPLETED);
        testRequestUpdated = consultationController.updateConsultation(testRequest.getRequestId(),consultationRequest);

        assertThat(testRequest.getRequestId(),equalTo(testRequestUpdated.requestId));
        assertThat(testRequestUpdated.getStatus(),equalTo(RequestStatus.COMPLETED));
        assertThat(testRequestUpdated.consultation.getSuggestion(),equalTo(testRequest.consultation.getSuggestion()));
        //Implement this method
        //Create an object of CreateConsultationRequest and call getCreateConsultationRequest() to create the object. Pass the above created object as the parameter

        //Create another object of the TestRequest method and explicitly update the status of this object
        // to be 'COMPLETED'. Make use of updateConsultation() method from consultationController class
        // (Pass the previously created two objects as parameters)
        // (for the object of TestRequest class, pass its ID using getRequestId())

        //Use assertThat() methods to perform the following three comparisons
        //  1. the request ids of both the objects created should be same
        //  2. the status of the second object should be equal to 'COMPLETED'
        // 3. the suggestion of both the objects created should be same. Make use of getSuggestion() method to get the results.



    }


    @Test
    @WithUserDetails(value = "doctor")
    public void calling_updateConsultation_with_invalid_test_request_id_should_throw_exception(){

        TestRequest testRequest = getTestRequestByStatus(RequestStatus.DIAGNOSIS_IN_PROCESS);
        CreateConsultationRequest consultationRequest = getCreateConsultationRequest(testRequest);
        Long invalidId = -23L;
        ResponseStatusException result = assertThrows(ResponseStatusException.class,()->{

            consultationController.updateConsultation(invalidId,consultationRequest);
        });

        try{
             consultationController.updateConsultation(invalidId,consultationRequest);
        }
        catch (Exception e)
        {
            assertThat(e.getMessage(),containsString("Invalid ID"));
        }
        //Implement this method

        //Create an object of CreateConsultationRequest and call getCreateConsultationRequest() to create the object. Pass the above created object as the parameter

        // Create an object of ResponseStatusException . Use assertThrows() method and pass updateConsultation() method
        // of consultationController with a negative long value as Id and the above created object as second parameter
        //Refer to the TestRequestControllerTest to check how to use assertThrows() method


        //Use assertThat() method to perform the following comparison
        //  the exception message should be contain the string "Invalid ID"

    }

    @Test
    @WithUserDetails(value = "doctor")
    public void calling_updateConsultation_with_invalid_empty_status_should_throw_exception(){

        TestRequest testRequest = getTestRequestByStatus(RequestStatus.DIAGNOSIS_IN_PROCESS);
        CreateConsultationRequest consultationRequest = getCreateConsultationRequest(testRequest);
        consultationRequest.setSuggestion(null);

        ResponseStatusException result = assertThrows(ResponseStatusException.class,()->{

            consultationController.updateConsultation(testRequest.requestId,consultationRequest);
        });


        //Implement this method

        //Create an object of CreateConsultationRequest and call getCreateConsultationRequest() to create the object. Pass the above created object as the parameter
        // Set the suggestion of the above created object to null.

        // Create an object of ResponseStatusException . Use assertThrows() method and pass updateConsultation() method
        // of consultationController with request Id of the testRequest object and the above created object as second parameter
        //Refer to the TestRequestControllerTest to check how to use assertThrows() method


    }

    public CreateConsultationRequest getCreateConsultationRequest(TestRequest testRequest) {

        CreateLabResult labResult = new CreateLabResult();
        labResult.setResult(testRequest.getLabResult().getResult());
        labResult.setBloodPressure(testRequest.getLabResult().getBloodPressure());
        labResult.setComments(testRequest.getLabResult().getComments());
        labResult.setHeartBeat(testRequest.getLabResult().getHeartBeat());
        labResult.setTemperature(testRequest.getLabResult().getTemperature());
        labResult.setOxygenLevel(testRequest.getLabResult().getOxygenLevel());

        CreateConsultationRequest consultationRequest = getCreateConsultationRequest(testRequest);
        if(labResult.equals("Positive"))
            consultationRequest.setSuggestion(DoctorSuggestion.HOME_QUARANTINE);
        else if(labResult.equals("Negative"))
            consultationRequest.setSuggestion(DoctorSuggestion.NO_ISSUES);


        //Create an object of CreateLabResult and set all the values
        // if the lab result test status is Positive, set the doctor suggestion as "HOME_QUARANTINE" and comments accordingly
        // else if the lab result status is Negative, set the doctor suggestion as "NO_ISSUES" and comments as "Ok"
        // Return the object



        return consultationRequest; // Replace this line with your code

    }

}