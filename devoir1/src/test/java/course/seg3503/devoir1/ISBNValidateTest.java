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

	
}
