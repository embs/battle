import scala.util.Random

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
    var choice : Int = 0

    println("Come here you @(*&% beast!")
    while(hp > 0) {
      // Receive damage if attacked.
      serve((true &&& receiveDamage) =?=> {
        damage => {
          println("Warrior receives " + damage + " damage")
        }
      } | after(400) ==> {})
      // Choose between recovering SP or attacking.
      choice = Random.nextInt(2)

      choice match {
        case 0 if sp < WarriorMaxSp => {
          println("Warrior sharpens their sword and recovers 1 SP")
          sp += 1
        }
        case 1 if sp >= SwordStabSpCost => {
          println("Warrior stabs dragon with their sword")
          inflictDamage!SwordStabDamage
          sp -= SwordStabSpCost
        }
      }
    }

    println("Warrior's dead")
  }

  def dragon(inflictDamage: ![Int], receiveDamage: ?[Int]) = proc {
    var hp : Int = 3
    var sp : Int = 3
    var damage: Int = 0
    var choice : Int = 0

    println("Ggggrrrrrrrr")
    while(hp > 0) {
      // Receive damage if attacked.
      serve((true &&& receiveDamage) =?=> {
        damage => {
          println("Dragon receives " + damage + " damage")
        }
      } | after(400) ==> { println("Dragon gave up attacking") })
      // Choose between recovering SP or attacking.
      choice = Random.nextInt(2)

      choice match {
        case 0 if sp < DragonMaxSp => {
          println("Dragon regurgitates fire and recovers 1 SP")
          sp += 1
        }
        case 1 if sp >= FireSpitSpCost => {
          println("Dragon spits fire all over the warrior")
          inflictDamage!FireSpitDamage
          sp -= FireSpitSpCost
        }
      }
    }

    println("Dragon's dead")
  }

  def main(args: Array[String]) {
    println("Let the battle begin!")
    (warrior(damageDragon, damageWarrior) || dragon(damageWarrior, damageDragon))()

    exit
  }
}
