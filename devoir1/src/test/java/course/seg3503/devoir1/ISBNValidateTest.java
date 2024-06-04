/**
 * 
 */
package course.seg3503.devoir1;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * 
 */
public class ISBNValidateTest {

	// Part 1
	@Test
	public void tesAppendCheckDigitToISBN12WithValidISBNStarts978() {
		final String validISBN12BeginingWith978 = "978634673735";
		assertEquals("9786346737350", ISBNValidate.appendCheckDigitToISBN12(validISBN12BeginingWith978));
	}

	@Test
	public void tesAppendCheckDigitToISBN12WithValidISBNStarts979() {
		final String validISBN12BeginingWith979 = "979637893632";
		assertEquals("9796378936324", ISBNValidate.appendCheckDigitToISBN12(validISBN12BeginingWith979));
	}

	@Test(expected = IllegalArgumentException.class)
	public void tesAppendCheckDigitToISBN12WithEmptyInput() {
		final String empty = "";
		ISBNValidate.appendCheckDigitToISBN12(empty);		
	}

	@Test(expected = IllegalArgumentException.class)
	public void tesAppendCheckDigitToISBN12WithLongInput() {
		final String longInputDigits = "5967482596492";
		ISBNValidate.appendCheckDigitToISBN12(longInputDigits);
	}

	@Test
	public void tesAppendCheckDigitToISBN12With12CharNonDigitInput() {
		final String nonDigitInput = "charnjvgyhkm";
		assertEquals( "charnjvgyhkm0", ISBNValidate.appendCheckDigitToISBN12(nonDigitInput));
	}

	// Part 2

	
	@Test
	public void testTidyISBN10or13InsertingDashes_ISBN10Valid() {
		assertEquals("0-12-345678-9", ISBNValidate.tidyISBN10or13InsertingDashes("0123456789"));
	}
	
	@Test
	public void testTidyISBN10or13InsertingDashes_ISBN13Valid() {
		assertEquals("978-3-16148410-0", ISBNValidate.tidyISBN10or13InsertingDashes("9783161484100"));
	}

	@Test
	public void testTidyISBN10or13InsertingDashes_NoISBN() {
		 Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			  ISBNValidate.tidyISBN10or13InsertingDashes("");
		 });
		 assertEquals("no ISBN", exception.getMessage());
	}

	@Test
	public void testTidyISBN10or13InsertingDashes_ISBN10InvalidFormat() {
		 Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			  ISBNValidate.tidyISBN10or13InsertingDashes("012345678");
		 });
		 assertEquals("ISBN must be 10 or 13 digits long.", exception.getMessage());
	}

	@Test
	public void testTidyISBN10or13InsertingDashes_ISBN13InvalidFormat() {
		 Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			  ISBNValidate.tidyISBN10or13InsertingDashes("978012345678");
		 });
		 assertEquals("ISBN must be 10 or 13 digits long.", exception.getMessage());
	}

	@Test
	public void testTidyISBN10or13InsertingDashes_ISBN10InvalidCheckDigit() {
		 Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			  ISBNValidate.tidyISBN10or13InsertingDashes("012345678x");
		 });
		 assertEquals("Bad Check Digit", exception.getMessage());
	}

	@Test
	public void testTidyISBN10or13InsertingDashes_ISBN13InvalidCheckDigit() {
		 Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			  ISBNValidate.tidyISBN10or13InsertingDashes("978012345678x");
		 });
		 assertEquals("Bad check digit.", exception.getMessage());
	}

	@Test
	public void testTidyISBN10or13InsertingDashes_ISBN10Alphanumeric() {
		 Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			  ISBNValidate.tidyISBN10or13InsertingDashes("0-1234-567899");
		 });
		 assertEquals("ISBN must be 10 or 13 digits long.", exception.getMessage());
	}

	@Test
	public void testTidyISBN10or13InsertingDashes_ISBN13Alphanumeric() {
		 Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			  ISBNValidate.tidyISBN10or13InsertingDashes("97801234-56789");
		 });
		 assertEquals("Bad check digit.", exception.getMessage());
	}
	
}
