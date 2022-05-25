fun main(args: Array<String>) {
    val dieselEngine = DieselEngine()
    val dieselCar = Car(dieselEngine)
    /*dieselCar.start()*/
    println(dieselCar.start())
}
class Car(private val engine:EngineInterface){
    fun start(){
        engine.start()
    }
}

interface EngineInterface{
    fun start(): String
}
class Engine{
    fun start(){}
}

class DieselEngine(): EngineInterface{
    override fun start():String {
        return "Engine Diesel Starting"
    }

}