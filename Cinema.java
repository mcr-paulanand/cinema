package cinema;

import java.util.Objects;
import java.util.Scanner;

public class Cinema {

    public static void main(String[] args) {
        final char SEAT_STATUS_AVAILABLE = 'S';
        final char SEAT_STATUS_PURCHASED = 'B';
        final int TICKET_PRICE_BASE = 10;
        final int TICKET_PRICE_LOW = 8;

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the number of rows:");
        int numberOfRows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int numberOfSeatsPerRow = scanner.nextInt();

        char[][] seatStatus = initialiseSeats(numberOfRows, numberOfSeatsPerRow, SEAT_STATUS_AVAILABLE);
        int[][] seatPrices = setSeatPrices(numberOfRows, numberOfSeatsPerRow, TICKET_PRICE_BASE, TICKET_PRICE_LOW);


        int option;
        do {
            printOptions();
            option = scanner.nextInt();

            switch (option) {
                case 1 -> printSeats(seatStatus);
                case 2 -> purchaseTickets(seatStatus, seatPrices, SEAT_STATUS_PURCHASED);
                case 3 -> printStatistics(seatStatus, seatPrices, SEAT_STATUS_PURCHASED);
            }
        } while (option != 0);
    }

    public static char[][] initialiseSeats(int numberOfRows, int numberOfSeatsPerRow, char seatStatusAvailable) {
        char[][] seats = new char[numberOfRows][numberOfSeatsPerRow];

        for (int rowNumber = 1; rowNumber <= numberOfRows; rowNumber++) {
            for (int seatNumber = 1; seatNumber <= numberOfSeatsPerRow; seatNumber++) {
                seats[rowNumber - 1][seatNumber - 1] = seatStatusAvailable;
            }
        }
        return seats;
    }

    public static int[][] setSeatPrices(int numberOfRows, int numberOfSeatsPerRow, int ticketPriceBase, int ticketPriceLow) {
        int totalSeats = numberOfRows * numberOfSeatsPerRow;
        int numberOfFrontRows = (numberOfRows / 2);
        int[][] seatPrices = new int[numberOfRows][numberOfSeatsPerRow];

        for (int rowNumber = 1; rowNumber <= numberOfRows; rowNumber++) {
            for (int seatNumber = 1; seatNumber <= numberOfSeatsPerRow; seatNumber++) {
                seatPrices[rowNumber - 1][seatNumber - 1] = totalSeats <= 60 || rowNumber <= numberOfFrontRows ? ticketPriceBase : ticketPriceLow;
            }
        }
        return seatPrices;
    }

    public static void printOptions() {
        System.out.println();
        System.out.println("1. Show the seats");
        System.out.println("2. Buy a ticket");
        System.out.println("3. Statistics");
        System.out.println("0. Exit");
    }

    public static void printSeats(char[][] seatStatus) {
        System.out.println();
        System.out.println("Cinema:");
        System.out.print(" ");
        for (int seatNumber = 1; seatNumber <= seatStatus[0].length; seatNumber++) {
            System.out.print(" " + seatNumber);
        }
        System.out.println();

        for (int rowNumber = 1; rowNumber <= seatStatus.length; rowNumber++) {
            System.out.print(rowNumber);
            for (int seatNumber = 1; seatNumber <= seatStatus[rowNumber - 1].length; seatNumber++) {
                System.out.print(" " + seatStatus[rowNumber - 1][seatNumber - 1]);
            }
            System.out.println();
        }
    }

    public static void purchaseTickets(char[][] seatStatus, int[][] seatPrices, char seatStatusPurchased) {
        Scanner scanner = new Scanner(System.in);

        do {
            System.out.println("Enter a row number:");
            int chosenRowNumber = scanner.nextInt();
            System.out.println("Enter a seat number in that row:");
            int chosenSeatNumber = scanner.nextInt();

            if (isSeatInvalid(chosenRowNumber, chosenSeatNumber, seatStatus)) {
                System.out.println("Wrong input!");
            } else if (isSeatPurchased(chosenRowNumber, chosenSeatNumber, seatStatus, seatStatusPurchased)) {
                System.out.println("That ticket has already been purchased!");
            } else {
                seatStatus[chosenRowNumber - 1][chosenSeatNumber - 1] = seatStatusPurchased;
                System.out.println("Ticket price: $" + seatPrices[chosenRowNumber - 1][chosenSeatNumber - 1]);
                break;
            }

        } while (true);
    }

    public static void printStatistics(char[][] seatStatus, int[][] seatPrices, char seatStatusPurchased) {
        int ticketsPurchased = 0;
        int totalTickets = 0;
        int currentIncome = 0;
        int totalIncome = 0;

        for (int rowNumber = 1; rowNumber <= seatStatus.length; rowNumber++) {
            for (int seatNumber = 1; seatNumber <= seatStatus[rowNumber - 1].length; seatNumber++) {
                totalTickets++;
                totalIncome += seatPrices[rowNumber - 1][seatNumber - 1];

                if (isSeatPurchased(rowNumber, seatNumber, seatStatus, seatStatusPurchased)) {
                    ticketsPurchased++;
                    currentIncome += seatPrices[rowNumber - 1][seatNumber - 1];
                }
            }
        }

        System.out.println();
        System.out.println("Number of purchased tickets: " + ticketsPurchased);
        System.out.println("Percentage: " + String.format("%.2f", (double) ticketsPurchased / totalTickets * 100) + "%");
        System.out.println("Current income: $" + currentIncome);
        System.out.println("Total income: $" + totalIncome);
    }

    public static boolean isSeatPurchased(int rowNumber, int seatNumber,char[][] seatStatus, char seatStatusPurchased) {
        return Objects.equals(seatStatus[rowNumber - 1][seatNumber - 1], seatStatusPurchased);
    }

    public static boolean isSeatInvalid(int rowNumber, int seatNumber,char[][] seatStatus) {
        return !(rowNumber >= 1 && seatNumber >= 1 && rowNumber <= seatStatus.length && seatNumber <= seatStatus[0].length);
    }
}