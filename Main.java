import java.io.*;
import java.util.*;

//  Interfaces 
interface CRUD<T> {
    void add(T obj);
    void view();
    void update(int id);
    void delete(int id);
}

//  Book Class 
class Book {
    int id;
    String title;
    String author;
    boolean isIssued;

    Book(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isIssued = false;
    }

    @Override
    public String toString() {
        return id + "," + title + "," + author + "," + isIssued;
    }

    static Book fromString(String line) {
        String[] parts = line.split(",");
        if (parts.length == 4) {
            Book b = new Book(Integer.parseInt(parts[0]), parts[1], parts[2]);
            b.isIssued = Boolean.parseBoolean(parts[3]);
            return b;
        }
        return null;
    }

    String display() {
        return "[ID: " + id + ", Title: " + title + ", Author: " + author + ", Issued: " + (isIssued ? "Yes" : "No") + "]";
    }
}

//  Member Class 
class Member {
    int id;
    String name;
    String email;

    Member(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    @Override
    public String toString() {
        return id + "," + name + "," + email;
    }

    static Member fromString(String line) {
        String[] parts = line.split(",");
        if (parts.length == 3) {
            return new Member(Integer.parseInt(parts[0]), parts[1], parts[2]);
        }
        return null;
    }

    String display() {
        return "[ID: " + id + ", Name: " + name + ", Email: " + email + "]";
    }
}

//  Book Manager 
class BookManager implements CRUD<Book> {
    List<Book> books = new ArrayList<>();
    String fileName = "books.txt";

    BookManager() {
        loadFromFile();
    }

    public void add(Book book) {
        books.add(book);
        saveToFile();
        System.out.println("Book added successfully!");
    }

    public void view() {
        if (books.isEmpty()) {
            System.out.println("No books available.");
            return;
        }
        books.forEach(b -> System.out.println(b.display()));
    }

    public void update(int id) {
        for (Book b : books) {
            if (b.id == id) {
                Scanner sc = new Scanner(System.in);
                System.out.print("Enter new title: ");
                b.title = sc.nextLine();
                System.out.print("Enter new author: ");
                b.author = sc.nextLine();
                saveToFile();
                System.out.println("Book updated successfully!");
                return;
            }
        }
        System.out.println("Book not found!");
    }

    public void delete(int id) {
        books.removeIf(b -> b.id == id);
        saveToFile();
        System.out.println("Book deleted successfully (if existed).");
    }

    private void saveToFile() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(fileName))) {
            for (Book b : books) {
                pw.println(b.toString());
            }
        } catch (IOException e) {
            System.out.println("Error saving books.");
        }
    }

    private void loadFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                Book b = Book.fromString(line);
                if (b != null) books.add(b);
            }
        } catch (IOException e) {
            // ignore if file not found (Pehla run)
        }
    }
}

//  Member Manager 
class MemberManager implements CRUD<Member> {
    List<Member> members = new ArrayList<>();
    String fileName = "members.txt";

    MemberManager() {
        loadFromFile();
    }

    public void add(Member member) {
        members.add(member);
        saveToFile();
        System.out.println("Member added successfully!");
    }

    public void view() {
        if (members.isEmpty()) {
            System.out.println("No members available.");
            return;
        }
        members.forEach(m -> System.out.println(m.display()));
    }

    public void update(int id) {
        for (Member m : members) {
            if (m.id == id) {
                Scanner sc = new Scanner(System.in);
                System.out.print("Enter new name: ");
                m.name = sc.nextLine();
                System.out.print("Enter new email: ");
                m.email = sc.nextLine();
                saveToFile();
                System.out.println("Member updated successfully!");
                return;
            }
        }
        System.out.println("Member not found!");
    }

    public void delete(int id) {
        members.removeIf(m -> m.id == id);
        saveToFile();
        System.out.println("Member deleted successfully (if existed).");
    }

    private void saveToFile() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(fileName))) {
            for (Member m : members) {
                pw.println(m.toString());
            }
        } catch (IOException e) {
            System.out.println("Error saving members.");
        }
    }

    private void loadFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                Member m = Member.fromString(line);
                if (m != null) members.add(m);
            }
        } catch (IOException e) {
            // ignore if file not found
        }
    }
}

//  Main Class
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BookManager bookManager = new BookManager();
        MemberManager memberManager = new MemberManager();

        while (true) {
            System.out.println("\n=== Library Management System ===");
            System.out.println("1. Manage Books");
            System.out.println("2. Manage Members");
            System.out.println("0. Exit");
            System.out.print("Choose: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    manageBooks(sc, bookManager);
                    break;
                case 2:
                    manageMembers(sc, memberManager);
                    break;
                case 0:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    private static void manageBooks(Scanner sc, BookManager bm) {
        while (true) {
            System.out.println("\n--- Book Management ---");
            System.out.println("1. Add Book");
            System.out.println("2. View Books");
            System.out.println("3. Update Book");
            System.out.println("4. Delete Book");
            System.out.println("0. Back");
            System.out.print("Choose: ");
            int ch = sc.nextInt();

            switch (ch) {
                case 1:
                    System.out.print("Enter ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter Title: ");
                    String title = sc.nextLine();
                    System.out.print("Enter Author: ");
                    String author = sc.nextLine();
                    bm.add(new Book(id, title, author));
                    break;
                case 2:
                    bm.view();
                    break;
                case 3:
                    System.out.print("Enter Book ID to update: ");
                    bm.update(sc.nextInt());
                    break;
                case 4:
                    System.out.print("Enter Book ID to delete: ");
                    bm.delete(sc.nextInt());
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    private static void manageMembers(Scanner sc, MemberManager mm) {
        while (true) {
            System.out.println("\n--- Member Management ---");
            System.out.println("1. Add Member");
            System.out.println("2. View Members");
            System.out.println("3. Update Member");
            System.out.println("4. Delete Member");
            System.out.println("0. Back");
            System.out.print("Choose: ");
            int ch = sc.nextInt();

            switch (ch) {
                case 1:
                    System.out.print("Enter ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter Email: ");
                    String email = sc.nextLine();
                    mm.add(new Member(id, name, email));
                    break;
                case 2:
                    mm.view();
                    break;
                case 3:
                    System.out.print("Enter Member ID to update: ");
                    mm.update(sc.nextInt());
                    break;
                case 4:
                    System.out.print("Enter Member ID to delete: ");
                    mm.delete(sc.nextInt());
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
