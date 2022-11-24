import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static com.jayway.restassured.path.json.JsonPath.*;

public class Test01_verifyName {
	
	//Get JSON response to a string
	String response = get("https://api.tmsandbox.co.nz/v1/Categories/6327/Details.json?catalogue=false").asString();
	
	@Test(priority = 1)	
	void verifyName() {
		
		//Get JSON element to a variable
		String name = from(response).getString("Name");
		//Assert the variable for the expected
		Assert.assertEquals(name, "Carbon credits");
		
		/*Another way of doing this
		given()
			.get("https://api.tmsandbox.co.nz/v1/Categories/6327/Details.json?catalogue=false")
		.then()
			.body("Name", equalTo("Carbon credits"));
		*/
	}
	
	@Test(priority = 2)
	void verifyCanRelist() {
		
		//Get JSON element to a variable
		String name = from(response).getString("CanRelist");
		//Assert the variable for the expected
		Assert.assertEquals(name, "true");
		
		/*Another way of doing this
		given()
			.get("https://api.tmsandbox.co.nz/v1/Categories/6327/Details.json?catalogue=false")
		.then()
			.body("CanRelist", equalTo(true));
		*/			
	}
	
	@Test(priority = 3)
	void verifyPromotion() {		
		
		//Get the array size of JSON element Promotions array
		int PromoSize = from(response).getInt("Promotions.size()");
		
		//Run through each element in promotions array and validate
		for (int i=0; i<PromoSize; i++) {
			
			//Get name element of array item to a variable
			String promoName = from(response).getString("Promotions["+i+"].Name");
			//Get description element of array item to a variable			
			String description = from(response).getString("Promotions["+i+"].Description");
			
			if(promoName.contentEquals("Gallery")) {
				//Assert the description when the name is gallery
				Assert.assertEquals(description, "Good position in category");
			}
		}
	}
	
}
