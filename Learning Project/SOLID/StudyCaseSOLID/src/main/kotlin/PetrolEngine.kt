class PetrolEngine(private val oilPipe: OilPipe, private val piston: Piston): EngineInterface{
    override fun move() {
        val oil:Oil = oilPipe.suckOil()
        val energy: Energy = oil.burn()
        energy.push(piston)
    }
}

class Piston{
    fun move(){}
}

class OilPipe{
    fun suckOil(): Oil{
        return Oil()
    }
}

class Oil{
    fun burn(): Energy{
        return Energy()
    }
}
class Energy(){
    fun push(piston: Piston){
        piston.move()
    }
}

class Tank(): StorageInterface<Oil>{
    private lateinit var oil:Oil
    override fun fill(source: Oil) {
        this.oil = source
    }

    override fun getSource(): Oil {
        return oil
    }

}