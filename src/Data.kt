enum class Suit {CLUBS, DIAMONDS, HEARTS, SPADES }

data class Card(val suit: Suit, val rank: Int) {
  override fun toString(): String {
    return when (rank) {
      1 -> "A"
      11 -> "J"
      12 -> "Q"
      13 -> "K"
      else -> rank.toString()
    } + when (suit) {
      Suit.CLUBS -> "C"
      Suit.DIAMONDS -> "D"
      Suit.HEARTS -> "H"
      Suit.SPADES -> "S"
    }
  }
}

class Hand(card1: Card, card2: Card) {
  private val cards: MutableList<Card> = mutableListOf()
  private var aces = 0

  fun showing() = cards[1]

  init {
    add(card1)
    add(card2)
  }

  fun add(card: Card): Hand {
    if (card.rank == 1) {
      aces++
    }
    cards.add(card)
    return this
  }

  fun value(): Int {
    var aces = aces
    var score = cards.map { it.rank }
        .reduce { acc, i ->
          acc + when (i) {
            1 -> 11
            in 10..13 -> 10
            else -> i
          }
        }
    while (score > 21 && aces > 0) {
      score -= 10
      aces--
    }
    return score
  }

  override fun toString(): String {
    return cards.map { it.toString() }
        .joinToString(separator = " ") { it }
  }
}

class Deck {
  val cards: MutableList<Card> = mutableListOf()

  init {
    for (rank in 1..13) {
      Suit.values().mapTo(cards) { Card(it, rank) }
    }
    for (i in 0 until 52) {
      val pick = (Math.random() * (52 - i) + i).toInt()
      val temp = cards[i]
      cards[i] = cards[pick]
      cards[pick] = temp
    }
  }

  fun getCard() = cards.removeAt(cards.size - 1)

  override fun toString(): String {
    return cards.map { it.toString() }
        .joinToString(separator = " ") { it }
  }
}

fun main(args: Array<String>) {
  println(Hand(Card(Suit.CLUBS, 3), Card(Suit.DIAMONDS, 1))
      .add(Card(Suit.SPADES, 12)))
  println(Deck())
}