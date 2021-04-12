import checkout.Model.{ItemPricingRule, PricingRules}

class Checkout(items: Seq[Char], rules: PricingRules) {

    def checkoutItems: Int = {
      filterItems
        .groupBy(identity)
        .map { case (itemId, items) =>
        val pricingRules: ItemPricingRule = rules(itemId)
        calculateTotalForItem(items.size, pricingRules.unitPrice, pricingRules.discountQuantity, pricingRules.specialPrice)
      }.sum
    }

    private def filterItems: Seq[Char] = {
       val actualItems = rules.keys.toSeq
      items.filter(actualItems.contains)
    }

    private def calculateTotalForItem(itemQuantity: Int, unitPrice: Int, discountQuantity: Int, specialPrice: Int): Int = {
      if (discountQuantity == 0) itemQuantity * unitPrice
      else {
        val quantityChargedAtUnitPrice = itemQuantity % discountQuantity
        val quantityChargedAtSpecialPrice = (itemQuantity - quantityChargedAtUnitPrice) / discountQuantity

        (quantityChargedAtUnitPrice * unitPrice) + (quantityChargedAtSpecialPrice * specialPrice)
      }
    }

  }

object Checkout {
    def apply(items: Seq[Char], rules: Map[Char, ItemPricingRule]): Int = {
      val checkout = new Checkout(items, rules)
      checkout.checkoutItems
    }
  }



