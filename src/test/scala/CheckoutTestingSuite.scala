import checkout.Model.ItemPricingRule
import org.scalatest.flatspec.AnyFlatSpec

class CheckoutTestingSuite extends AnyFlatSpec {

  val pricingRules = Map(
    'A' -> ItemPricingRule(50, 3, 130),
    'B' -> ItemPricingRule(30, 2, 45),
    'C' -> ItemPricingRule(50, 0, 0),
    'D' -> ItemPricingRule(50, 0, 0),
  )

  "Checkout" should "return 0 when there are no items" in {
    val actual = Checkout(Seq.empty, pricingRules)
    val expected = 0
    assert(actual === expected)
  }

  it should "return 0 for a single invalid item" in {
    val actual = Checkout(Seq('Z'), pricingRules)
    val expected = 0
    assert(actual === expected)
  }

  it should "return the unit price for a single item" in {
    val actual = Checkout(Seq('A'), pricingRules)
    val expected = 50
    assert(actual === expected)
  }

  it should "return the sum of unit prices for 2 single items" in {
    val actual = Checkout(Seq('A', 'B'), pricingRules)
    val expected = 80
    assert(actual === expected)
  }

  it should "apply the special price correctly" in {
    val actual = Checkout(Seq('A', 'A', 'A'), pricingRules)
    val expected = 130
    assert(actual === expected)
  }

  it should "charge the special price and unit price correctly" in {
    val actual = Checkout(Seq('A', 'A', 'A', 'A'), pricingRules)
    val expected = 180
    assert(actual === expected)
  }

  it should "charge only the unit price for an item with no special price" in {
    val actual = Checkout(Seq('C', 'C', 'C', 'C'), pricingRules)
    val expected = 200
    assert(actual === expected)
  }

  it should "charge correctly when there are multiple distinct items with varying number of discounts" in {
    val actual = Checkout(Seq('A', 'A', 'A', 'B', 'B', 'B', 'C', 'Z'), pricingRules)
    val expected = 255
    assert(actual === expected)

  }
}
