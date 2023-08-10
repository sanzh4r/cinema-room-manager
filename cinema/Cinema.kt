package cinema
import kotlin.system.exitProcess

fun main() {

    println("Enter the number of rows:")
    val preNumOfRows = readln().toString()

    println("Enter the number of seats:")
    val preNumOfSeats= readln().toString()

    // Checking whether there is an exception with NumberFormatException
    try {
        val testFormat = preNumOfRows.toInt()
        val testFormat2 = preNumOfSeats.toInt()
    } catch (e: NumberFormatException) {
        println("Please type only digits between 1-9!")
        main()
    }

    // Converting the user input to Integer
    val numOfRows = preNumOfRows.toInt()
    val numOfSeats = preNumOfSeats.toInt()

    // Create a MutableList with the number of rows and elements in each row defined by the user
    val cinemaRoom = MutableList(numOfRows) {
        MutableList(numOfSeats) { "S" }
    }

    // The count of sold seats
    var currentSeats = 0

    // The count of the current income
    var currentIncome = 0

    // Sending arguments to menu function
    menu(numOfRows, numOfSeats, cinemaRoom, currentSeats, currentIncome)

}

// Creating a function named "menu"
fun menu(nOfRows: Int, nOfSeats: Int, map: MutableList<MutableList<String>>, currentSeats: Int, currentIncome: Int) {
    println("")
    println("1. Show the seats")
    println("2. Buy a ticket")
    println("3. Statistics")
    println("0. Exit")
    val menuOption: String = readln().toString()
    when (menuOption) {
        "1" -> printRoomMap(nOfRows, nOfSeats, map, currentSeats, currentIncome)
        "2" -> bookSeat(nOfRows, nOfSeats, map, currentSeats, currentIncome)
        "3" -> statistics(nOfRows, nOfSeats, map, currentSeats, currentIncome)
        "0" -> exitProcess(0)
        else -> menu(nOfRows, nOfSeats, map, currentSeats, currentIncome)
    }
}

// Printing the map with the seats
fun printRoomMap(nOfRows: Int, nOfSeats: Int, map: MutableList<MutableList<String>>, currentSeats: Int, currentIncome: Int) {
    println("")
    println("Cinema:")
    print(" ")
    for (seat in 1..nOfSeats) {
        print(" " + "$seat")
    }

    println(" ")

    var rowNum: Int = 0
    for (list in map) {
            print(++rowNum)
            print(" ")
            println(list.joinToString(separator = " "))
    }
    menu(nOfRows, nOfSeats, map, currentSeats, currentIncome)
}

// A function that books a seat and estimates the price of a seat,
fun bookSeat(nOfRows: Int, nOfSeats: Int, map: MutableList<MutableList<String>>, currentSeats: Int, currentIncome: Int) {

    if (currentSeats == nOfRows * nOfSeats) {
        println("There are no free seats left! Please come back tomorrow.")
        menu(nOfRows, nOfSeats, map, currentSeats, currentIncome)
    }

    val cinemaRoom: MutableList<MutableList<String>> = map

    // Booking the seat
    println("Enter a row number:")
    val preBookedRow = readln().toString()
    println("Enter a seat number in that row:")
    val preBookedSeat = readln().toString()

    // Checking whether there is no NumberFormatException
    try {
        val testBookedRow = preBookedRow.toInt()
        val testBookedSeat = preBookedSeat.toInt()
    } catch (e: NumberFormatException) {
        println("Please enter only digits between 1 and $nOfRows for rows!")
        println("Please enter only digits between 1 and $nOfSeats for seats!")
        bookSeat(nOfRows, nOfSeats, map, currentSeats, currentIncome)
    }

    // Converting numbers to Integers
    val bookedRow = preBookedRow.toInt()
    val bookedSeat = preBookedSeat.toInt()

    if (bookedRow > nOfRows || bookedRow <= 0 || bookedSeat > nOfSeats || bookedSeat <= 0) {
        println("Wrong input!")
        bookSeat(nOfRows, nOfSeats, map, currentSeats, currentIncome)
        }

    var priceOfTicket: Int = currentIncome
    var bookingEntity: Int = currentSeats

    if (cinemaRoom[bookedRow - 1][bookedSeat - 1] == "B") {
        println("That ticket has already been purchased!\n")
        bookSeat(nOfRows, nOfSeats, map, currentSeats, currentIncome)
    } else if (nOfRows * nOfSeats <= 60) {
        println("Ticket price: $10")
        priceOfTicket += 10
        bookingEntity += 1
    } else if (bookedRow <= (nOfRows / 2)) {
        println("Ticket price: $10")
        priceOfTicket += 10
        bookingEntity += 1
    } else {
        println("Ticket price: $8")
        priceOfTicket += 8
        bookingEntity += 1
    }

        // Replacing an element in mutable list with B, in other words highlighting the booked seat in the cinema room
        cinemaRoom[bookedRow - 1].set(bookedSeat - 1, "B")
        menu(nOfRows, nOfSeats, map, bookingEntity, priceOfTicket)

    }

    fun statistics (nOfRows: Int, nOfSeats: Int, map: MutableList<MutableList<String>>, currentSeats: Int, currentIncome: Int) {

        // Count the total amount of seats in the cinema
        val totalSeats: Int = nOfRows * nOfSeats

        // Counting the percentage of the seats sold
        val percentage = currentSeats.toDouble() * 100.00 / totalSeats.toDouble()
        val formatPercentage = "%.2f".format(percentage)

        // The count of the total income
        var totalIncome: Int = 0
        if (totalSeats <= 60) {
            totalIncome = totalSeats * 10
        } else {
            totalIncome = ((nOfRows / 2) * nOfSeats * 10) + ((nOfRows / 2 + 1) * nOfSeats * 8)
        }

        println("Number of purchased tickets: $currentSeats")
        println("Percentage: $formatPercentage%")
        println("Current income: $" + currentIncome)
        println("Total income: $$totalIncome")
        menu(nOfRows, nOfSeats, map, currentSeats, currentIncome)
    }