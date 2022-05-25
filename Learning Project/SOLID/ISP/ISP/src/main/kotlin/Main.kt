fun main(args: Array<String>) {
    val motorcycle = Motorcycle()
    val motorBehavior = motorcycle.drive()

    val car = Car()
    val carBehavior = car.drive()

    println(motorBehavior)
    println(carBehavior)
}


interface VehicleInterface{
    fun drive():String
    fun stop()
    fun refuel()
}

interface DoorInterface{
    fun openDoors()
}

class Motorcycle: VehicleInterface{
    override fun drive():String {
        return "Motorcycle driving"
    }

    override fun stop() {
        TODO("Not yet implemented")
    }

    override fun refuel() {
        TODO("Not yet implemented")
    }
}

class Car: VehicleInterface, DoorInterface{
    override fun drive():String {
        return "Car driving"
    }

    override fun stop() {
        TODO("Not yet implemented")
    }

    override fun refuel() {
        TODO("Not yet implemented")
    }

    override fun openDoors() {
        TODO("Not yet implemented")
    }

}