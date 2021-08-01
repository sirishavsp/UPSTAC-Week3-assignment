package org.upgrad.upstac.testrequests;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.web.server.ResponseStatusException;
import org.upgrad.upstac.testrequests.lab.CreateLabResult;
import org.upgrad.upstac.testrequests.lab.LabRequestController;
import org.upgrad.upstac.testrequests.lab.LabResult;
import org.upgrad.upstac.testrequests.lab.TestStatus;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
@Slf4j
class LabRequestControllerTest {


    @Autowired
    LabRequestController labRequestController;




    @Autowired
    TestRequestQueryService testRequestQueryService;


    @Test
    @WithUserDetails(value = "tester")
    public void calling_assignForLabTest_with_valid_test_request_id_should_update_the_request_status(){

        TestRequest testRequest = getTestRequestByStatus(RequestStatus.INITIATED);
        TestRequest testRequestUpdated = labRequestController.assignForLabTest(testRequest.requestId);

        assertThat(testRequestUpdated.requestId,equalTo(testRequest.requestId));
        assertThat(testRequestUpdated.getStatus(),equalTo(RequestStatus.LAB_TEST_IN_PROGRESS));
        assertNotNull(testRequestUpdated.getLabResult());
        testRequestUpdated.getLabResult();
        //Implement this method

        //Create another object of the TestRequest method and explicitly assign this object for Lab Test using assignForLabTest() method
        // from labRequestController class. Pass the request id of testRequest object.

        //Use assertThat() methods to perform the following two comparisons
        //  1. the request ids of both the objects created should be same
        //  2. the status of the second object should be equal to 'LAB_TEST_IN_PROGRESS'
        // make use of assertNotNull() method to make sure that the lab result of second object is not null
        // use getLabResult() method to get the lab result


    }

    public TestRequest getTestRequestByStatus(RequestStatus status) {
        return testRequestQueryService.findBy(status).stream().findFirst().get();
    }

    @Test
    @WithUserDetails(value = "tester")
    public void calling_assignForLabTest_with_valid_test_request_id_should_throw_exception(){

        Long InvalidRequestId= -34L;
        ResponseStatusException result = assertThrows(ResponseStatusException.class,()->{

            labRequestController.assignForLabTest(InvalidRequestId);
        });

        try {
            labRequestController.assignForLabTest(InvalidRequestId);
        }
        catch (Exception e)
        {
            assertThat(e.getMessage(),containsString("Invalid ID"));
        }

        //Implement this method


        // Create an object of ResponseStatusException . Use assertThrows() method and pass assignForLabTest() method
        // of labRequestController with InvalidRequestId as Id


        //Use assertThat() method to perform the following comparison
        //  the exception message should be contain the string "Invalid ID"

    }

    @Test
    @WithUserDetails(value = "tester")
    public void calling_updateLabTest_with_valid_test_request_id_should_update_the_request_status_and_update_test_request_details(){

        TestRequest testRequest = getTestRequestByStatus(RequestStatus.LAB_TEST_IN_PROGRESS);
        CreateLabResult labResult = getCreateLabResult(testRequest);


        TestRequest testRequestUpdated = labRequestController.updateLabTest(testRequest.requestId,labResult);
        testRequestUpdated.setStatus(RequestStatus.LAB_TEST_IN_PROGRESS);

        assertThat(testRequestUpdated.requestId,equalTo(testRequest.requestId));
        assertThat(testRequestUpdated.getStatus(),equalTo(testRequest.getStatus()));
        assertThat(testRequestUpdated.getLabResult(),equalTo(testRequest.getLabResult()));
        //Implement this method
        //Create an object of CreateLabResult and call getCreateLabResult() to create the object. Pass the above created object as the parameter

        //Create another object of the TestRequest method and explicitly update the status of this object
        // to be 'LAB_TEST_IN_PROGRESS'. Make use of updateLabTest() method from labRequestController class (Pass the previously created two objects as parameters)

        //Use assertThat() methods to perform the following three comparisons
        //  1. the request ids of both the objects created should be same
        //  2. the status of the second object should be equal to 'LAB_TEST_COMPLETED'
        // 3. the results of both the objects created should be same. Make use of getLabResult() method to get the results.



    }


    @Test
    @WithUserDetails(value = "tester")
    public void calling_updateLabTest_with_invalid_test_request_id_should_throw_exception(){

        TestRequest testRequest = getTestRequestByStatus(RequestStatus.LAB_TEST_IN_PROGRESS);
        CreateLabResult labResult = getCreateLabResult(testRequest);

        ResponseStatusException result = assertThrows(ResponseStatusException.class,()->{

            labRequestController.updateLabTest(-23L, labResult);
        });

        try {
            labRequestController.updateLabTest(-23L, labResult);
        }
        catch (Exception e)
        {
            assertThat(e.getMessage(),containsString("Invalid ID"));
        }
        //Implement this method

        //Create an object of CreateLabResult and call getCreateLabResult() to create the object. Pass the above created object as the parameter

        // Create an object of ResponseStatusException . Use assertThrows() method and pass updateLabTest() method
        // of labRequestController with a negative long value as Id and the above created object as second parameter
        //Refer to the TestRequestControllerTest to check how to use assertThrows() method


        //Use assertThat() method to perform the following comparison
        //  the exception message should be contain the string "Invalid ID"

    }

    @Test
    @WithUserDetails(value = "tester")
    public void calling_updateLabTest_with_invalid_empty_status_should_throw_exception(){

        TestRequest testRequest = getTestRequestByStatus(RequestStatus.LAB_TEST_IN_PROGRESS);
        CreateLabResult labResult = getCreateLabResult(testRequest);
        labResult.setResult(null);

        ResponseStatusException result = assertThrows(ResponseStatusException.class,()->{

            labRequestController.updateLabTest(testRequest.requestId, labResult);
        });

        try {
            labRequestController.updateLabTest(testRequest.requestId, labResult);

        }catch(Exception e)
        {
            assertThat(e.getMessage(),containsString("ConstaintViolationException"));
        }

        //Implement this method

        //Create an object of CreateLabResult and call getCreateLabResult() to create the object. Pass the above created object as the parameter
        // Set the result of the above created object to null.

        // Create an object of ResponseStatusException . Use assertThrows() method and pass updateLabTest() method
        // of labRequestController with request Id of the testRequest object and the above created object as second parameter
        //Refer to the TestRequestControllerTest to check how to use assertThrows() method


        //Use assertThat() method to perform the following comparison
        //  the exception message should be contain the string "ConstraintViolationException"

    }

    public CreateLabResult getCreateLabResult(TestRequest testRequest) {

        //Create an object of CreateLabResult and set all the values
        // Return the object
        CreateLabResult labResult = new CreateLabResult();
        labResult.setResult(testRequest.getLabResult().getResult());
        labResult.setBloodPressure(testRequest.getLabResult().getBloodPressure());
        labResult.setComments(testRequest.getLabResult().getComments());
        labResult.setHeartBeat(testRequest.getLabResult().getHeartBeat());
        labResult.setTemperature(testRequest.getLabResult().getTemperature());
        labResult.setOxygenLevel(testRequest.getLabResult().getOxygenLevel());

        return labResult; // Replace this line with your code
    }

}