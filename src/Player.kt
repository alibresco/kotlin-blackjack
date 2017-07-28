sealed class Player(val deck: Deck) {
  protected val hand: Hand = Hand(deck.getCard(), deck.getCard())

  abstract fun play(): Boolean
  fun score() = hand.value()

  fun hit() {
    hand.add(deck.getCard())
  }

  fun isBust(): Boolean {
    return score() > 21
  }
}

class Human(deck: Deck) : Player(deck) {
  override fun play(): Boolean {
    println("Your Turn")
    while (true) {
      println("Your hand: $hand, Score: ${score()}")
      if (isBust()) {
        println("Oh no, you busted")
        return true
      }
      print("Would you like to hit or stand? ")
      if (readLine()?.toLowerCase()?.trim() in listOf("hit", "h")) {
        hit()
      } else {
        return false
      }
    }
  }
}

class Dealer(deck: Deck, val humanScore: Int) : Player(deck) {
  override fun play(): Boolean {
    println("Dealer's Turn")
    while (true) {
      if (isBust()) {
        println("Dealer's hand: $hand, Score: ${score()}")
        println("Dealer busted with " + score())
        return true;
      }
      if (score() < 17 && score() < humanScore) {
        hit()
      } else {
        println("Dealer's hand: $hand, Score: ${score()}")
        return false
      }
    }
  }

  fun showing() = hand.showing()

}

fun main(args: Array<String>) {
  println("Welcome to Blackjack!")
  var playAgain: Boolean
  do {
    val deck = Deck()
    val human = Human(deck)
    val dealer = Dealer(deck, human.score())
    var youWin: Boolean
    println("Dealer is showing " + dealer.showing())
    if (human.play()) {
      youWin = false
    } else if (dealer.play()) {
      youWin = true
    } else {
      youWin = human.score() > dealer.score()
    }
    println(if (youWin) "You win!" else "Dealer wins...")
    print("Play again? (y or n) ")
    playAgain = readLine()?.toLowerCase()?.trim() in listOf("y", "yes")
  } while (playAgain)
  println("Thank you for playing!")
}