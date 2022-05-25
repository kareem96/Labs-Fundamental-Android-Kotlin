import java.util.*

fun main(args: Array<String>) {
    val product = Vegetable()
    val name = product.getName()
    println(name)
}


abstract class Product{
    abstract fun getName(): String?
    /*abstract fun getExpiredDate(): Date?*/


    fun getProductInfo(){}
}

abstract class FoodProduct: Product(){
    abstract fun getExpiredDate(): Date
}

class Vegetable: FoodProduct(){
    override fun getExpiredDate(): Date {
        return Date()
    }

    override fun getName(): String? {
        return "Broccoli"
    }

}
class  Smartphone: Product(){
    override fun getName(): String? {
        return "Iphone 13 Pro Max"
    }

}