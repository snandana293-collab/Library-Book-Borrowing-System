/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package librarybookborrowingsystem;

/**
 *
 * @author HP
 */
import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

// Custom exception for invalid book ID
class InvalidBookIdException extends Exception {
    public InvalidBookIdException(String message) {
        super(message);
    }
}

// Custom exception when book is unavailable
class BookUnavailableException extends Exception {
    public BookUnavailableException(String message) {
        super(message);
    }
}

// Custom exception for borrowing limit
class BorrowingLimitExceededException extends Exception {
    public BorrowingLimitExceededException(String message) {
        super(message);
    }
}

// Custom exception for invalid return date
class InvalidReturnDateException extends Exception {
    public InvalidReturnDateException(String message) {
        super(message);
    }
}

// Custom exception for invalid member ID
class InvalidMemberIdException extends Exception {
    public InvalidMemberIdException(String message) {
        super(message);
    }
}

// Book class
class Book {
    int bookId;
    String title;
    boolean available;

    Book(int bookId, String title) {
        this.bookId = bookId;
        this.title = title;
        this.available = true;
    }
}

// Member class
class Member {
    int memberId;
    String name;
    int borrowedBooks;

    Member(int memberId, String name) {
        this.memberId = memberId;
        this.name = name;
        this.borrowedBooks = 0;
    }
}

public class Librarybookborrowingsystem {

    static Scanner sc = new Scanner(System.in);

    static ArrayList<Book> books = new ArrayList<>();
    static ArrayList<Member> members = new ArrayList<>();

    static final int MAX_BORROWING_LIMIT = 3;

    // Search book
    static void searchBook() throws InvalidBookIdException {

        System.out.print("Enter Book ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        Book foundBook = null;

        for (Book book : books) {
            if (book.bookId == id) {
                foundBook = book;
                break;
            }
        }

        if (foundBook == null) {
            throw new InvalidBookIdException("Invalid Book ID.");
        }

        System.out.println("\nBook Found!");
        System.out.println("Book ID: " + foundBook.bookId);
        System.out.println("Title: " + foundBook.title);
        System.out.println("Status: " +
                (foundBook.available ? "Available" : "Not Available"));
    }

    // Issue book
    static void issueBook()
            throws InvalidBookIdException,
                   BookUnavailableException,
                   InvalidMemberIdException,
                   BorrowingLimitExceededException {

        System.out.print("Enter Member ID: ");
        int memberId = sc.nextInt();

        Member member = null;

        for (Member m : members) {
            if (m.memberId == memberId) {
                member = m;
                break;
            }
        }

        if (member == null) {
            throw new InvalidMemberIdException("Invalid Member ID.");
        }

        if (member.borrowedBooks >= MAX_BORROWING_LIMIT) {
            throw new BorrowingLimitExceededException(
                    "Maximum borrowing limit of 3 books exceeded.");
        }

        System.out.print("Enter Book ID: ");
        int bookId = sc.nextInt();
        sc.nextLine();

        Book book = null;

        for (Book b : books) {
            if (b.bookId == bookId) {
                book = b;
                break;
            }
        }

        if (book == null) {
            throw new InvalidBookIdException("Invalid Book ID.");
        }

        if (!book.available) {
            throw new BookUnavailableException(
                    "Book is currently unavailable.");
        }

        book.available = false;
        member.borrowedBooks++;

        System.out.println("Book issued successfully!");
        System.out.println("Book: " + book.title);
        System.out.println("Member: " + member.name);
    }

    // Return book
    static void returnBook()
            throws InvalidBookIdException,
                   InvalidMemberIdException,
                   InvalidReturnDateException {

        System.out.print("Enter Member ID: ");
        int memberId = sc.nextInt();

        Member member = null;

        for (Member m : members) {
            if (m.memberId == memberId) {
                member = m;
                break;
            }
        }

        if (member == null) {
            throw new InvalidMemberIdException("Invalid Member ID.");
        }

        System.out.print("Enter Book ID: ");
        int bookId = sc.nextInt();
        sc.nextLine();

        Book book = null;

        for (Book b : books) {
            if (b.bookId == bookId) {
                book = b;
                break;
            }
        }

        if (book == null) {
            throw new InvalidBookIdException("Invalid Book ID.");
        }

        System.out.print("Enter return date (YYYY-MM-DD): ");
        String dateInput = sc.nextLine();

        try {
            LocalDate returnDate = LocalDate.parse(dateInput);

            if (returnDate.isAfter(LocalDate.now())) {
                throw new InvalidReturnDateException(
                        "Return date cannot be a future date.");
            }

        } catch (DateTimeParseException e) {
            throw new InvalidReturnDateException(
                    "Invalid return date format. Use YYYY-MM-DD.");
        }

        if (book.available) {
            System.out.println("This book has not been issued.");
            return;
        }

        book.available = true;

        if (member.borrowedBooks > 0) {
            member.borrowedBooks--;
        }

        System.out.println("Book returned successfully!");
    }

    // Display books
    static void displayBooks() {

        System.out.println("\n===== BOOK LIST =====");

        for (Book book : books) {
            System.out.println(
                    "ID: " + book.bookId +
                    " | Title: " + book.title +
                    " | Status: " +
                    (book.available ? "Available" : "Issued")
            );
        }
    }

    // Display members
    static void displayMembers() {

        System.out.println("\n===== MEMBER LIST =====");

        for (Member member : members) {
            System.out.println(
                    "ID: " + member.memberId +
                    " | Name: " + member.name +
                    " | Borrowed Books: " +
                    member.borrowedBooks
            );
        }
    }

    public static void main(String[] args) {

        // Sample books
        books.add(new Book(101, "Java Programming"));
        books.add(new Book(102, "Data Structures"));
        books.add(new Book(103, "Computer Networks"));
        books.add(new Book(104, "Database Management"));
        books.add(new Book(105, "Operating Systems"));

        // Sample members
        members.add(new Member(1, "Arun"));
        members.add(new Member(2, "Priya"));
        members.add(new Member(3, "Rahul"));

        while (true) {

            System.out.println("\n===== LIBRARY BOOK BORROWING SYSTEM =====");
            System.out.println("1. Search Book");
            System.out.println("2. Issue Book");
            System.out.println("3. Return Book");
            System.out.println("4. Display Books");
            System.out.println("5. Display Members");
            System.out.println("6. Exit");

            System.out.print("Enter your choice: ");

            try {

                int choice = sc.nextInt();
                sc.nextLine();

                switch(choice) {

                    case 1:
                        searchBook();
                        break;

                    case 2:
                        issueBook();
                        break;

                    case 3:
                        returnBook();
                        break;

                    case 4:
                        displayBooks();
                        break;

                    case 5:
                        displayMembers();
                        break;

                    case 6:
                        System.out.println("Thank you for using the Library System!");
                        sc.close();
                        return;

                    default:
                        System.out.println("Invalid choice.");

                }

            } catch (
                    InvalidBookIdException |
                    BookUnavailableException |
                    BorrowingLimitExceededException |
                    InvalidReturnDateException |
                    InvalidMemberIdException e) {

                System.out.println("Exception: " + e.getMessage());

            } catch (InputMismatchException e) {

                System.out.println(
                        "Exception: Please enter a valid number."
                );

                sc.nextLine();
            }
        }
    }
}
