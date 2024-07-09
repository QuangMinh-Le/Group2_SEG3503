import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import ebook.controller.EbookingControl;
import ebook.controller.EbookingEventNotDefineException;
import ebook.simulator.BasicReactions;

public class EbookingControlTest {

    private EbookingControl controller;

    @Before
    public void setUp() {
        controller = new EbookingControl(new BasicReactions());
    }

    @Test
    public void testSuccessfulReservationWithoutBaggage() throws EbookingEventNotDefineException {
        assertEquals(EbookingControl.Status.IDLE, controller.getCurrent());

        controller.reservationNumber();
        assertEquals(EbookingControl.Status.LOOKINGUPRESERVATION, controller.getCurrent());

        controller.found();
        assertEquals(EbookingControl.Status.DISPLAYINGFLIGHT, controller.getCurrent());

        controller.confirm();
        assertEquals(EbookingControl.Status.WAITFORRESPONSE, controller.getCurrent());

        controller.no();
        assertEquals(EbookingControl.Status.WAITFORDOCUMENTSWITHRAWAL, controller.getCurrent());

        controller.withdrawDocuments();
        assertEquals(EbookingControl.Status.IDLE, controller.getCurrent());
    }

    @Test
    public void testUnsuccessfulReservationLookup() throws EbookingEventNotDefineException {
        assertEquals(EbookingControl.Status.IDLE, controller.getCurrent());

        controller.reservationNumber();
        assertEquals(EbookingControl.Status.LOOKINGUPRESERVATION, controller.getCurrent());

        controller.notFound();
        assertEquals(EbookingControl.Status.IDLE, controller.getCurrent());
    }

    @Test
    public void testSuccessfulReservationWithBaggage() throws EbookingEventNotDefineException {
        assertEquals(EbookingControl.Status.IDLE, controller.getCurrent());

        controller.reservationNumber();
        assertEquals(EbookingControl.Status.LOOKINGUPRESERVATION, controller.getCurrent());

        controller.found();
        assertEquals(EbookingControl.Status.DISPLAYINGFLIGHT, controller.getCurrent());

        controller.confirm();
        assertEquals(EbookingControl.Status.WAITFORRESPONSE, controller.getCurrent());

        controller.yes();
        assertEquals(EbookingControl.Status.WAITFORBAGGAGENUMBERS, controller.getCurrent());

        controller.numberOfPieces();
        assertEquals(EbookingControl.Status.WAITFORDOCUMENTSWITHRAWAL, controller.getCurrent());

        controller.withdrawDocuments();
        assertEquals(EbookingControl.Status.IDLE, controller.getCurrent());
    }

    @Test
    public void testTimeoutDuringDocumentWithdrawal() throws EbookingEventNotDefineException {
        assertEquals(EbookingControl.Status.IDLE, controller.getCurrent());

        controller.reservationNumber();
        assertEquals(EbookingControl.Status.LOOKINGUPRESERVATION, controller.getCurrent());

        controller.found();
        assertEquals(EbookingControl.Status.DISPLAYINGFLIGHT, controller.getCurrent());

        controller.confirm();
        assertEquals(EbookingControl.Status.WAITFORRESPONSE, controller.getCurrent());

        controller.no();
        assertEquals(EbookingControl.Status.WAITFORDOCUMENTSWITHRAWAL, controller.getCurrent());

        controller.timeout();
        assertEquals(EbookingControl.Status.SOUNDINGALARM, controller.getCurrent());

        controller.withdrawDocuments();
        assertEquals(EbookingControl.Status.IDLE, controller.getCurrent());
    }

    @Test
    public void testCustomerCancels() throws EbookingEventNotDefineException {
        controller.setCurrent(EbookingControl.Status.DISPLAYINGFLIGHT);
        controller.cancel();
        assertEquals(EbookingControl.Status.IDLE, controller.getCurrent());

        controller.setCurrent(EbookingControl.Status.WAITFORRESPONSE);
        controller.cancel();
        assertEquals(EbookingControl.Status.IDLE, controller.getCurrent());
        
        controller.setCurrent(EbookingControl.Status.WAITFORBAGGAGENUMBERS);
        controller.cancel();
        assertEquals(EbookingControl.Status.IDLE, controller.getCurrent());

        controller.setCurrent(EbookingControl.Status.WAITFORDOCUMENTSWITHRAWAL);
        controller.cancel();
        assertEquals(EbookingControl.Status.IDLE, controller.getCurrent());
    }

    @Test
    public void testConfirmWithoutValidReservation() {
        try {
            controller.confirm();
            fail("Expected EbookingEventNotDefineException to be thrown");
        } catch (EbookingEventNotDefineException e) {
            // Test passes
        }
    }

    @Test
    public void testEnterNumberOfPiecesWithoutConfirmingFlight() {
        try {
            controller.numberOfPieces();
            fail("Expected EbookingEventNotDefineException to be thrown");
        } catch (EbookingEventNotDefineException e) {
            // Test passes
        }
    }

    @Test
    public void testWithdrawDocumentsWithoutPrintingBoardingPass() {
        try {
            controller.withdrawDocuments();
            fail("Expected EbookingEventNotDefineException to be thrown");
        } catch (EbookingEventNotDefineException e) {
            // Test passes
        }
    }
}
