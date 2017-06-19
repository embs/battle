import io.threadcso._

object Battle {
  val WarriorMaxSp = 10
  val SwordStabSpCost = 3
  val SwordStabDamage = 1

  val DragonMaxSp = 20
  val FireSpitSpCost = 3
  val FireSpitDamage = 1

  val damageWarrior = OneOne[Int]
  val damageDragon = OneOne[Int]

  def warrior(inflictDamage: ![Int], receiveDamage: ?[Int]) = proc {
    var hp : Int = 3
    var sp : Int = 3
    var damage: Int = 0

    println("Come here you @(*&% beast!")
    while(hp > 0) {
      if(sp < WarriorMaxSp) {
        println("Warrior sharpens their sword and recovers 1 SP")
        sp = sp + 1
      } else {
        println("Warrior throws Stopped")
        throw new io.threadcso.processimplementation.Stopped()
      }

      alt(
        (sp >= SwordStabSpCost &&& inflictDamage) =!=> { SwordStabDamage } ==> {
          sp = sp - SwordStabSpCost
          println("Warrior stabs dragon with their sword")
        }
        |
        (hp > 0 &&& receiveDamage) =?=> {
          damage => hp = hp - damage
          println("Warrior receives " + damage + " damage")
        }
      )
    }
    println("Warrior's dead")
  }

  def dragon(inflictDamage: ![Int], receiveDamage: ?[Int]) = proc {
    var hp : Int = 3
    var sp : Int = 3

    println("Ggggrrrrrrrr")
    while(hp > 0) {
      if(sp < DragonMaxSp) {
        sp = sp + 1
        println("Dragon regurgitates fire and recovers 1 SP. Now it has " + sp)
      } else {
        println("Dragon throws Stopped")
        throw new io.threadcso.processimplementation.Stopped()
      }

      alt(
        ((hp > 0 && sp >= FireSpitSpCost) &&& inflictDamage) =!=> { FireSpitDamage } ==> {
          println("Dragon spits fire all over the warrior"); sp = sp - FireSpitSpCost
        }
        |
        (hp > 0 &&& receiveDamage) =?=> {
          damage => hp = hp - damage
          println("Dragon receives " + damage + " damage")
        }
      )

      println("Dragon's HP/SP: " + hp + "/" + sp)
    }
    println("Dragon's dead")
  }

  def main(args: Array[String]) {
    println("Let the battle begin!")
    (warrior(damageDragon, damageWarrior) || dragon(damageWarrior, damageDragon))()

    exit
  }
}
