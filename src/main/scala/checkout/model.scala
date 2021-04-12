package checkout

object model {
  case class ItemPricingRule(unitPrice: Int, discountQuantity: Int, specialPrice: Int)
  type PricingRules = Map[Char, ItemPricingRule]
}
