package ma.mockito.test;

//Note : On peut importer le package statiquement --> import static org.junit.Assert.*; Pour éviter d'écrire Assert à chaque fois.
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
//Note : On peut importer le package statiquement --> import static org.mockito.Mockito.*; Pour éviter d'écrire Mockito à chaque fois.
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import ma.mockito.entities.Flower;
import ma.mockito.exception.PersonalException;

/**
 * Unit test for Flower.
 */

// @RunWith(MockitoJUnitRunner.class) --> Obligatoire pour utiliser avec les annotations -> MOCKITO
@RunWith(MockitoJUnitRunner.class)
public class FlowerTest {

	/** Constante */
	private static final Date SYS_DATE = new Date();

	/** Premiére façon de faire */
	@Mock
	Flower flower_1;

	/** Deuxième façon de faire */
	Flower flower_2 = mock(Flower.class);

	@Test
	public void firstTest() {
		// Given -> ARRANGE = ORGANISATION
		Flower flower = mock(Flower.class);
		when(flower.getName()).thenReturn("JASMINE");
		when(flower.getColor()).thenReturn("BLANCHE");

		// When -> ACT : ACTE , TRAITEMENT
		final String IS_JASMINE = "JASMINE";
		final String IS_WHITE = "BLANCHE";

		// Then -> ASSERT : AFFIRMATION
		assertEquals(IS_JASMINE, flower.getName());
		assertEquals(IS_WHITE, flower.getColor());
	}

	@Test
	public void verifyWatredFlower() {
		// Given
		Flower flower = mock(Flower.class);
		given(flower.getNumberOfFlowerWatered(SYS_DATE)).willReturn(100); // Lorsque je demande cette méthode -> Retourné : 100

		// When
		final int IS_100 = 100;
		final int NBR_FLOWER_WATERED = flower.getNumberOfFlowerWatered(SYS_DATE);

		// Then
		assertEquals(NBR_FLOWER_WATERED, IS_100);
	}

	@Test
	public void verifyNameWithMatchers() {
		// Given
		Flower flower = mock(Flower.class);
		// Note : Lors de l'utilisation des MATCHERS (Mockito.eq , Mockito.startWith ...) dans l'un des paramétres d'une méthode il faut obligatoirement ajouter des
		// Matchers pour les autres paramétres ! Si non le résultat sera toujours FAUX

		/** Première façon : Correcte */
		given(flower.getFlowerByNameAndColor(Mockito.eq("Amaryllis"), Mockito.eq("Rouge"))).willReturn(new Flower("Rouge,Blanche", "Amaryllis"));

		/** Deuxième façon : Correcte */
		// given(flower.getFlowerByNameAndColor("Amaryllis", "Rouge")).willReturn(new Flower("Rouge,Blanche", "Amaryllis"));

		/**
		 * FAUSSE !!! Exception qui sera levée -> org.mockito.exceptions.misusing.InvalidUseOfMatchersException: Invalid use of argument matchers!
		 */
		// given(flower.getFlowerByNameAndColor("Amaryllis", Mockito.eq("Rouge"))).willReturn(new Flower("Rouge,Blanche", "Amaryllis"));

		// When
		final Flower FLOWER_SELECTED = flower.getFlowerByNameAndColor("Amaryllis", "Rouge");
		final Flower FLOWER_TESTED = new Flower("Rouge,Blanche", "Amaryllis");

		// Then
		assertEquals(FLOWER_SELECTED, FLOWER_TESTED);
	}

	/**
	 * Multiple Calls to the Same Method
	 */
	@Test
	public void verifyMultipleForOneMethod() {
		// Given
		Flower flower = mock(Flower.class);
		given(flower.getNumberOfFlowerWatered(SYS_DATE)).willReturn(100, 500, 700);

		// When
		final int IS_100 = 100;
		final int IS_500 = 500;
		final int IS_700 = 700;

		// Then
		assertEquals(IS_100, flower.getNumberOfFlowerWatered(SYS_DATE));
		assertEquals(IS_500, flower.getNumberOfFlowerWatered(SYS_DATE));
		assertEquals(IS_700, flower.getNumberOfFlowerWatered(SYS_DATE));
	}

	/**
	 * Stubbing void method
	 */
	@Test
	@Ignore
	public void generateException() {
		// Given
		Flower flower = mock(Flower.class);
		Mockito.doThrow(PersonalException.class).when(flower).getName();
		// When
		flower.getName();
		// Then
	}

	/**
	 * Note : Un MOCK se souvient de tout les opérations effectués sur lui
	 */
	@Test
	public void verifyMethodIfIsExecuted() {
		// Given
		Flower flower = mock(Flower.class);
		flower.getNumberOfFlowerWatered(SYS_DATE);

		/** Méthode déjà appelé ! aucune exception ! */
		Mockito.verify(flower).getNumberOfFlowerWatered(SYS_DATE);

		/** Génére une exception: WANTED BUT NOT INVOKED */
		// Mockito.verify(flower).getName();
	}

	/**
	 * Vérifier l'ordre d'appel des méthode
	 */

	@Test
	public void verifyCallOrder() {
		// Given
		Flower flower_1 = mock(Flower.class);
		Flower flower_2 = mock(Flower.class);

		// When - L'ordre à vérifer
		/** 1 */
		flower_1.getName();
		/** 2 */
		flower_2.getNumberOfFlowerWatered(SYS_DATE);
		/** 3 */
		flower_1.getColor();

		// Then
		InOrder inOrder = Mockito.inOrder(flower_1, flower_2);
		// Si je change l'ordre de ces 3 lignes, une erreur se déclanchera // org.mockito.exceptions.verification.VerificationInOrderFailure:
		inOrder.verify(flower_1).getName();
		inOrder.verify(flower_2).getNumberOfFlowerWatered(SYS_DATE);
		inOrder.verify(flower_1).getColor();
	}

}