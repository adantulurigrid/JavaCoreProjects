package contacts;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Pattern;

abstract class Record implements Serializable {

    protected String number = "";
    protected LocalDateTime timeCreated;
    protected LocalDateTime timeLastEdit;

    public Record() {
        timeCreated = LocalDateTime.now().withNano(0);
        timeLastEdit = timeCreated;
    }

    public void setNumber(String number) {
        if (validateNumber(number)) {
            this.number = number;
        } else {
            System.out.println("Wrong number format!");
            this.number = "";
        }
        timeLastEdit = LocalDateTime.now().withNano(0);
    }

    private boolean validateNumber(String number) {
        String regex = "^\\+?(\\([\\w]+\\)|[\\w]+([\\s-]\\([\\w]{2,}\\))?|[\\w]+)([\\s-][\\w]{2,})*$";
        return Pattern.compile(regex).matcher(number).matches();
    }

    public String getNumber() {
        return number.isEmpty() ? "[no number]" : number;
    }

    public abstract String getFullName();

    public abstract List<String> getEditableFields();

    public abstract void setField(String field, String value);

    public abstract String getSearchableText();

    public abstract void printInfo();
}

class Person extends Record {

    private String name;
    private String surname;
    private String birthDate = "";
    private String gender = "";

    public void setName(String name) {
        this.name = name;
        timeLastEdit = LocalDateTime.now().withNano(0);
    }

    public void setSurname(String surname) {
        this.surname = surname;
        timeLastEdit = LocalDateTime.now().withNano(0);
    }

    public void setBirthDate(String birthDate) {
        if (birthDate.isEmpty()) {
            System.out.println("Bad birth date!");
            this.birthDate = "";
        } else {
            this.birthDate = birthDate;
        }
        timeLastEdit = LocalDateTime.now().withNano(0);
    }

    public void setGender(String gender) {
        if (!gender.equals("M") && !gender.equals("F")) {
            System.out.println("Bad gender!");
            this.gender = "";
        } else {
            this.gender = gender;
        }
        timeLastEdit = LocalDateTime.now().withNano(0);
    }

    @Override
    public String getFullName() {
        return name + " " + surname;
    }

    @Override
    public List<String> getEditableFields() {
        return List.of("name", "surname", "birth", "gender", "number");
    }

    @Override
    public void setField(String field, String value) {
        switch (field) {
            case "name": setName(value); break;
            case "surname": setSurname(value); break;
            case "birth": setBirthDate(value); break;
            case "gender": setGender(value); break;
            case "number": setNumber(value); break;
        }
    }

    @Override
    public String getSearchableText() {
        return (name + " " + surname + " " + birthDate + " " + gender + " " + number).toLowerCase();
    }

    @Override
    public void printInfo() {
        System.out.println("Name: " + name);
        System.out.println("Surname: " + surname);
        System.out.println("Birth date: " + (birthDate.isEmpty() ? "[no data]" : birthDate));
        System.out.println("Gender: " + (gender.isEmpty() ? "[no data]" : gender));
        System.out.println("Number: " + getNumber());
        System.out.println("Time created: " + timeCreated);
        System.out.println("Time last edit: " + timeLastEdit);
    }
}

class Organization extends Record {

    private String orgName;
    private String address;

    public void setOrgName(String orgName) {
        this.orgName = orgName;
        timeLastEdit = LocalDateTime.now().withNano(0);
    }

    public void setAddress(String address) {
        this.address = address;
        timeLastEdit = LocalDateTime.now().withNano(0);
    }

    @Override
    public String getFullName() {
        return orgName;
    }

    @Override
    public List<String> getEditableFields() {
        return List.of("name", "address", "number");
    }

    @Override
    public void setField(String field, String value) {
        switch (field) {
            case "name": setOrgName(value); break;
            case "address": setAddress(value); break;
            case "number": setNumber(value); break;
        }
    }

    @Override
    public String getSearchableText() {
        return (orgName + " " + address + " " + number).toLowerCase();
    }

    @Override
    public void printInfo() {
        System.out.println("Organization name: " + orgName);
        System.out.println("Address: " + address);
        System.out.println("Number: " + getNumber());
        System.out.println("Time created: " + timeCreated);
        System.out.println("Time last edit: " + timeLastEdit);
    }
}

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static List<Record> records = new ArrayList<>();
    private static String fileName = null;

    public static void main(String[] args) {

        if (args.length > 0) {
            fileName = args[0];
            load();
        }

        while (true) {

            System.out.print("[menu] Enter action (add, list, search, count, exit): ");
            String action = scanner.nextLine();

            switch (action) {
                case "add": addRecord(); break;
                case "list": listRecords(); break;
                case "search": search(); break;
                case "count":
                    System.out.println("The Phone Book has " + records.size() + " records.");
                    break;
                case "exit": return;
            }

            System.out.println();
        }
    }

    private static void addRecord() {

        System.out.print("Enter the type (person, organization): ");
        String type = scanner.nextLine();

        Record record;

        if (type.equals("person")) {
            Person p = new Person();

            System.out.print("Enter the name: ");
            p.setName(scanner.nextLine());

            System.out.print("Enter the surname: ");
            p.setSurname(scanner.nextLine());

            System.out.print("Enter the birth date: ");
            p.setBirthDate(scanner.nextLine());

            System.out.print("Enter the gender (M, F): ");
            p.setGender(scanner.nextLine());

            System.out.print("Enter the number: ");
            p.setNumber(scanner.nextLine());

            record = p;

        } else {

            Organization o = new Organization();

            System.out.print("Enter the organization name: ");
            o.setOrgName(scanner.nextLine());

            System.out.print("Enter the address: ");
            o.setAddress(scanner.nextLine());

            System.out.print("Enter the number: ");
            o.setNumber(scanner.nextLine());

            record = o;
        }

        records.add(record);
        save();

        System.out.println("The record added.");
    }

    private static void listRecords() {

        for (int i = 0; i < records.size(); i++) {
            System.out.println((i + 1) + ". " + records.get(i).getFullName());
        }

        System.out.print("[list] Enter action ([number], back): ");
        String input = scanner.nextLine();

        if (input.equals("back")) return;

        int index = Integer.parseInt(input) - 1;
        showRecord(records.get(index));
    }

    private static void showRecord(Record record) {

        record.printInfo();

        while (true) {

            System.out.print("[record] Enter action (edit, delete, menu): ");
            String action = scanner.nextLine();

            if (action.equals("menu")) return;

            if (action.equals("delete")) {
                records.remove(record);
                save();
                System.out.println("The record removed!");
                return;
            }

            if (action.equals("edit")) {

                System.out.print("Select a field " + record.getEditableFields() + ": ");
                String field = scanner.nextLine();

                System.out.print("Enter " + field + ": ");
                String value = scanner.nextLine();

                record.setField(field, value);

                save();

                System.out.println("Saved");
                record.printInfo();
            }
        }
    }

    private static void search() {

        System.out.print("Enter search query: ");
        String query = scanner.nextLine();

        Pattern pattern = Pattern.compile(query, Pattern.CASE_INSENSITIVE);

        List<Record> results = new ArrayList<>();

        for (Record r : records) {
            if (pattern.matcher(r.getSearchableText()).find()) {
                results.add(r);
            }
        }

        System.out.println("Found " + results.size() + " results:");

        for (int i = 0; i < results.size(); i++) {
            System.out.println((i + 1) + ". " + results.get(i).getFullName());
        }

        while (true) {

            System.out.print("[search] Enter action ([number], back, again): ");
            String action = scanner.nextLine();

            if (action.equals("back")) return;

            if (action.equals("again")) {
                search();
                return;
            }

            int index = Integer.parseInt(action) - 1;
            showRecord(results.get(index));
        }
    }

    private static void save() {

        if (fileName == null) return;

        try (ObjectOutputStream out =
                     new ObjectOutputStream(new FileOutputStream(fileName))) {

            out.writeObject(records);

        } catch (Exception ignored) {}
    }

    private static void load() {

        try (ObjectInputStream in =
                     new ObjectInputStream(new FileInputStream(fileName))) {

            records = (List<Record>) in.readObject();

        } catch (Exception ignored) {}
    }
}